use timely::dataflow::Scope;
use timely::dataflow::ProbeHandle;
use differential_dataflow::lattice::Lattice;
use differential_dataflow::ExchangeData;
use differential_dataflow::Collection;

use differential_dataflow::operators::arrange::TraceAgent;
use differential_dataflow::trace::implementations::ord::OrdKeySpine;
use differential_dataflow::operators::Iterate;

type Date = i64;
type Person = usize;
type Submission = usize;
type Comment = (Submission,Date,String,Person,Submission);
type Know = (Person, Person);
type Like = (Person, Submission);
type Post = (Submission,Date,String,Person);
type User = (Person, String);

fn main() {

    timely::execute_from_args(std::env::args(), |worker| {

        let mut timer = worker.timer();
        let index = worker.index();
        let peers = worker.peers();

        let change_path = std::env::var("ChangePath").unwrap_or("None".to_string());
        let run_index = std::env::var("RunIndex").unwrap_or("None".to_string());
        let sequences = std::env::var("Sequences").unwrap_or("20".to_string()).parse::<usize>().expect("Couldn't parse Sequences as an integer");
        let change_set = std::env::var("ChangeSet").unwrap_or("None".to_string());
        let query = std::env::var("Query").unwrap_or("Q2".to_string());
        let tool = std::env::var("Tool").unwrap_or("None".to_string());

        // Prepare input path information.
        let mut path = std::env::args().nth(1).expect("Must describe path");
        if path.as_str() == "HARNESS" {
            path = format!("{}/", change_path);
        }

        // track the progress of our computations
        let mut probe = timely::dataflow::ProbeHandle::new();

        // Build a dataflow for the queries.
        let 
            (
                mut comms_input, 
                mut knows_input, 
                mut likes_input, 
                mut posts_input, 
                mut users_input,
                mut q1_trace,           // Option<Trace>
                mut q2_trace,           // Option<Trace>
            ) =
        worker.dataflow::<usize,_,_>(|scope| {

            use differential_dataflow::input::Input;

            let (comms_input, comms) = scope.new_collection::<Comment,_>();
            let (knows_input, knows) = scope.new_collection::<Know,_>();
            let (likes_input, likes) = scope.new_collection::<Like,_>();
            let (posts_input, posts) = scope.new_collection::<Post,_>();
            let (users_input,_users) = scope.new_collection::<User,_>();

            // Query 1: Posts score by comments, and comment likes.
            let q1_trace = 
            if query == "Q1".to_string() {
                Some(query_1(&comms, &posts, &likes, &mut probe))
            }
            else {
                None
            };

            // Query 2: 
            let q2_trace =
            if query == "Q2".to_string() {
                Some(query_2(&comms, &knows, &likes, &mut probe))
            }
            else {
                None
            };

            (comms_input, knows_input, likes_input, posts_input, users_input, q1_trace, q2_trace)
        });

        if index == 0 {
            println!("{:?};{:?};{};{};0;\"Initialization\";\"Time\";{}", tool, query, change_set, run_index, timer.elapsed().as_nanos());
            timer = std::time::Instant::now();
        }

        let comms = load_data(&format!("{}csv-comments-initial.csv", path), index, peers);
        let knows = load_data(&format!("{}csv-friends-initial.csv", path), index, peers);
        let likes = load_data(&format!("{}csv-likes-initial.csv", path), index, peers);
        let posts = load_data(&format!("{}csv-posts-initial.csv", path), index, peers);
        let users = load_data(&format!("{}csv-users-initial.csv", path), index, peers);

        if index == 0 {
            println!("{:?};{:?};{};{};0;\"Load\";\"Time\";{}", tool, query, change_set, run_index, timer.elapsed().as_nanos());
            timer = std::time::Instant::now();
        }

        for comm in comms {
            comms_input.insert(strings_to_comm(comm));
        }

        for know in knows {
            knows_input.insert(strings_to_know(know));
        }

        for like in likes {
            likes_input.insert(strings_to_like(like));
        }

        for post in posts {
            posts_input.insert(strings_to_post(post));
        }

        for user in users {
            users_input.insert(strings_to_user(user));
        }

        comms_input.advance_to(1); comms_input.flush();
        knows_input.advance_to(1); knows_input.flush();
        likes_input.advance_to(1); likes_input.flush();
        posts_input.advance_to(1); posts_input.flush();
        users_input.advance_to(1); users_input.flush();

        while probe.less_than(comms_input.time()) {
            worker.step();
        }

        use timely::order::PartialOrder;
        use differential_dataflow::trace::TraceReader;
        use differential_dataflow::trace::cursor::Cursor;

        if let Some(trace) = &mut q1_trace {
            if let Some((mut cursor, storage)) = trace.cursor_through(&[1]) {
                while let Some(key) = cursor.get_key(&storage) {
                    while let Some(&()) = cursor.get_val(&storage) {
                        let mut count: isize = 0;
                        cursor.map_times(&storage, |time, &diff| {
                            if time.less_equal(&0) {
                                count += diff;
                            }
                        });
                        if count > 0 {
                            println!("{:?};\"Q1\";{};{};0;\"Initial\";\"Elements\";{:?}", tool, change_set, run_index, key);    
                        }
                        cursor.step_val(&storage)
                    }
                    cursor.step_key(&storage);
                }
            }
            else {
                println!("COULDN'T GET CURSOR")
            }
        }
        if let Some(trace) = &mut q2_trace {
            if let Some((mut cursor, storage)) = trace.cursor_through(&[1]) {
                while let Some(key) = cursor.get_key(&storage) {
                    while let Some(&()) = cursor.get_val(&storage) {
                        let mut count: isize = 0;
                        cursor.map_times(&storage, |time, &diff| {
                            if time.less_equal(&0) {
                                count += diff;
                            }
                        });
                        if count > 0 {
                            println!("{:?};\"Q2\";{};{};0;\"Initial\";\"Elements\";{:?}", tool, change_set, run_index, key);    
                        }
                        cursor.step_val(&storage)
                    }
                    cursor.step_key(&storage);
                }
            }
            else {
                println!("COULDN'T GET CURSOR")
            }
        }
        if index == 0 {
            println!("{:?};{:?};{};{};0;\"Initial\";\"Time\";{}", tool, query, change_set, run_index, timer.elapsed().as_nanos());
            timer = std::time::Instant::now();
        }

        for round in 1 .. (sequences + 1) {

            // Insert new records!
            let filename = format!("{}change{:02}.csv", path, round);
            let changes = load_data(&filename, index, peers);
            for mut change in changes {
                let collection = change.remove(0);
                match collection.as_str() {
                    "Comments" => { comms_input.insert(strings_to_comm(change)); },
                    "Friends" => { knows_input.insert(strings_to_know(change)); },
                    "Likes" => { likes_input.insert(strings_to_like(change)); },
                    "Posts" => { posts_input.insert(strings_to_post(change)); },
                    "Users" => { users_input.insert(strings_to_user(change)); },
                    x => { panic!("Weird enum variant: {}", x); },
                }
            }

            comms_input.advance_to(round + 1); comms_input.flush();
            knows_input.advance_to(round + 1); knows_input.flush();
            likes_input.advance_to(round + 1); likes_input.flush();
            posts_input.advance_to(round + 1); posts_input.flush();
            users_input.advance_to(round + 1); users_input.flush();

            while probe.less_than(comms_input.time()) {
                worker.step();
            }

            if let Some(trace) = &mut q1_trace {
                if let Some((mut cursor, storage)) = trace.cursor_through(&[round+1]) {
                    while let Some(key) = cursor.get_key(&storage) {
                        while let Some(&()) = cursor.get_val(&storage) {
                            let mut count: isize = 0;
                            cursor.map_times(&storage, |time, &diff| {
                                if time.less_equal(&round) {
                                    count += diff;
                                }
                            });
                            if count > 0 {
                                println!("{:?};\"Q1\";{};{};{};\"Update\";\"Elements\";{:?}", tool, change_set, run_index, round, key);    
                            }
                            cursor.step_val(&storage)
                        }
                        cursor.step_key(&storage);
                    }
                }
                else {
                    println!("COULDN'T GET CURSOR")
                }
            }
            if let Some(trace) = &mut q2_trace {
                if let Some((mut cursor, storage)) = trace.cursor_through(&[round+1]) {
                    while let Some(key) = cursor.get_key(&storage) {
                        while let Some(&()) = cursor.get_val(&storage) {
                            let mut count: isize = 0;
                            cursor.map_times(&storage, |time, &diff| {
                                if time.less_equal(&round) {
                                    count += diff;
                                }
                            });
                            if count > 0 {
                                println!("{:?};\"Q2\";{};{};{};\"Update\";\"Elements\";{:?}", tool, change_set, run_index, round, key);    
                            }
                            cursor.step_val(&storage)
                        }
                        cursor.step_key(&storage);
                    }
                }
                else {
                    println!("COULDN'T GET CURSOR")
                }
            }
            if index == 0 {
                println!("{:?};{:?};{};{};{};\"Update\";\"Time\";{}", tool, query, change_set, run_index, round, timer.elapsed().as_nanos());
                timer = std::time::Instant::now();
            }        
        }

    }).expect("Timely computation failed");
}

fn load_data(filename: &str, index: usize, peers: usize) -> Vec<Vec<String>> {

    // Standard io/fs boilerplate.
    use std::io::{BufRead, BufReader};
    use std::fs::File;

    let mut data = Vec::new();
    let file = BufReader::new(File::open(filename).expect("Could open file"));
    let lines = file.lines();

    for (count, readline) in lines.enumerate() {
        if count % peers == index {
            if let Ok(line) = readline {
                let text : Vec<String> =
                line.split('|')
                    .map(|x| x.to_string())
                    .collect();

                data.push(text);
            }
        }
    }
    data
}

fn strings_to_comm(comm: Vec<String>) -> Comment {
    let mut iter = comm.into_iter();
    let id = iter.next().unwrap().parse::<Submission>().unwrap();
    let ts = iter.next().unwrap();
    let mut split = ts.split_whitespace();
    let date = split.next().unwrap();
    let time = split.next().unwrap();
    let ts = format!("{}T{}+00:00", date, time);
    let ts = chrono::DateTime::parse_from_rfc3339(ts.as_str()).expect("Failed to parse DateTime").timestamp();
    let content = iter.next().unwrap();
    let creator = iter.next().unwrap().parse::<Person>().unwrap();
    let parent = iter.next().unwrap().parse::<Submission>().unwrap();
    (id, ts, content, creator, parent)
}

fn strings_to_know(know: Vec<String>) -> Know {
    let mut iter = know.into_iter();
    let person1 = iter.next().unwrap().parse::<Person>().unwrap();
    let person2 = iter.next().unwrap().parse::<Person>().unwrap();
    (person1, person2)
}

fn strings_to_like(like: Vec<String>) -> Like {
    let mut iter = like.into_iter();
    let person = iter.next().unwrap().parse::<Person>().unwrap();
    let comment = iter.next().unwrap().parse::<Submission>().unwrap();
    (person, comment)
}

fn strings_to_post(post: Vec<String>) -> Post {
    let mut iter = post.into_iter();
    let id = iter.next().unwrap().parse::<Submission>().unwrap();
    let ts = iter.next().unwrap();
    let mut split = ts.split_whitespace();
    let date = split.next().unwrap();
    let time = split.next().unwrap();
    let ts = format!("{}T{}+00:00", date, time);
    let ts = chrono::DateTime::parse_from_rfc3339(ts.as_str()).expect("Failed to parse DateTime").timestamp();
    let content = iter.next().unwrap();
    let creator = iter.next().unwrap().parse::<Person>().unwrap();
    (id, ts, content, creator)
}

fn strings_to_user(user: Vec<String>) -> User {
    let mut iter = user.into_iter();
    let person = iter.next().unwrap().parse::<Person>().unwrap();
    let name = iter.next().unwrap();
    (person, name)
}

/// Implement the logic for query 1, return trace of the results.
fn query_1<G, Submission, Person, Date>(
    comms: &Collection<G, (Submission,Date,String,Person,Submission)>,
    posts: &Collection<G, (Submission,Date,String,Person)>,
    likes: &Collection<G, (Person, Submission)>,
    probe: &mut ProbeHandle<G::Timestamp>,
) -> TraceAgent<OrdKeySpine<String, G::Timestamp, isize>>
where
    G: Scope,               // timely dataflow scope
    G::Timestamp: Lattice,  // differential dataflow timestamp constraint
    Submission: ExchangeData+std::hash::Hash+std::fmt::Display,
    Person: ExchangeData+std::hash::Hash,
    Date: ExchangeData+std::hash::Hash,
{
    use differential_dataflow::operators::reduce::Threshold;
    use differential_dataflow::operators::join::Join;
    use differential_dataflow::operators::arrange::arrangement::ArrangeBySelf;
    use timely::dataflow::operators::probe::Probe;

    let comms_parents = comms.map(|(id, _, _, _, parent)| (parent, id));

    let direct_replies = posts
        .map(|(pid, _, _, _)| (pid.clone(), ()))  // extract post ids
        .join_map(&comms_parents,
                  |post_id, _dummy, comm_id| (post_id.clone(), comm_id.clone())
        )  // -> (post_id, comment_id)
        ;

    let all_replies = direct_replies
        .iterate(|transitive| {
            let comms_parents = comms_parents.enter(&transitive.scope());
            let direct_replies = direct_replies.enter(&transitive.scope());

            return transitive
                .map(|(post_id, comment_id)| (comment_id.clone(), post_id.clone()))
                .join_map(&comms_parents,
                         |_parent_comment, post_id, child_comment| (post_id.clone(), child_comment.clone())
                )
                .concat(&direct_replies)
                .distinct()
                ;
        }) // -> (post_id, reply_id)
       ;

    let liked_comments =
    likes
        .distinct() // Remove duplicate likes (maybe not necessary?)
        .map(|(_user,comm)| (comm, ()))
        .join_map(
            &all_replies.map(|(post_id, comment_id)| (comment_id, post_id)),
            |_comment_id, _dummy, post_id| post_id.clone()
        )
        ;

    let comms_theyselves = all_replies.explode(|(post_id, _comment_id)| Some((post_id, 10)));

    use differential_dataflow::trace::implementations::ord::OrdValSpine;
    use differential_dataflow::operators::reduce::ReduceCore;

    let post_score = 
    posts
        .map(|post| post.0)         // ensure all posts get a score.
        .concat(&liked_comments)    // likes contribute to posts.
        .concat(&comms_theyselves)  // comments contribute to posts.
        .arrange_by_self()
        .reduce_abelian::<_,OrdValSpine<_,_,_,_>>("count", |_k,s,t| t.push((s[0].1.clone(), 1)))
        .join_map(
            &posts.map(|post| (post.0, post.1.clone())), 
            |post, count, ts| (post.clone(), (*count-1,ts.clone())),
        );

    let arrangement = 
    limit(&post_score, 3)
        .map(|vec| format!("{}|{}|{}", vec[0], vec[1], vec[2]))
        .arrange_by_self();

    arrangement.stream.probe_with(probe);
    arrangement.trace
}

fn query_2<G, Submission, Person, Date>(
    comms: &Collection<G, (Submission,Date,String,Person,Submission)>,
    knows: &Collection<G, (Person, Person)>,
    likes: &Collection<G, (Person, Submission)>,
    probe: &mut ProbeHandle<G::Timestamp>,
) -> TraceAgent<OrdKeySpine<String, G::Timestamp, isize>>
where 
    G: Scope,               // timely dataflow scope
    G::Timestamp: Lattice,  // differential dataflow timestamp constraint
    Submission: ExchangeData+std::hash::Hash+std::fmt::Display,
    Person: ExchangeData+std::hash::Hash,
    Date: ExchangeData+std::hash::Hash,
{
    use timely::dataflow::operators::probe::Probe;
    use differential_dataflow::operators::reduce::Count;
    use differential_dataflow::operators::join::Join;
    use differential_dataflow::operators::reduce::Reduce;
    use differential_dataflow::operators::arrange::arrangement::ArrangeBySelf;

    let labels: Collection<_, ((Person, Submission), Person)> =
    likes                   // node    label   comment
        .filter(|_| false)
        .map(|(user, comm)| ((user.clone(), comm), user))
        .iterate(|labels| {

            let knows = knows.enter(&labels.scope());
            let likes = likes.enter(&labels.scope());

            labels
                .map(|((node, comment), label)| (node, (label, comment)))
                .join_map(&knows, |_node, (label, comment), dest| ((dest.clone(), comment.clone()), label.clone()))
                .concat(&likes.map(|(user, comm)| ((user.clone(), comm), user)))
                .reduce(|_key, input, output| {
                    // only produce output, if `input` contains `_key.0`
                    if input.iter().any(|(label,_wgt)| *label == &_key.0) {
                        output.push((input[0].0.clone(), 1));
                    }
                })
        });

    use differential_dataflow::trace::implementations::ord::OrdValSpine;
    use differential_dataflow::operators::reduce::ReduceCore;

    let comm_score =
    labels
        .map(|((_node, comment), label)| (label, comment))
        .count()
        .explode(|((_label, comment), count)| Some((comment, count * count)))
        .concat(&comms.map(|comm| comm.0.clone()))
        .arrange_by_self()
        .reduce_abelian::<_,OrdValSpine<_,_,_,_>>("ra",|_k,s,t| t.push((s[0].1.clone(), 1)))
        .join_map(
            &comms.map(|comm| (comm.0.clone(), comm.1.clone())),
            |post, count, ts| (post.clone(), (*count-1,ts.clone())),
        );

    let arrangement = 
    limit(&comm_score, 3)
        .map(|vec| format!("{}|{}|{}", vec[0], vec[1], vec[2]))
        .arrange_by_self();

    arrangement.stream.probe_with(probe);
    arrangement.trace
}

/// Return the top `limit` distinct keys in input, by value.
fn limit<G, K, V>(input: &Collection<G,(K,V)>, limit: usize) -> Collection<G,Vec<K>> 
where
    G: Scope,
    G::Timestamp: Lattice,
    K: ExchangeData+std::hash::Hash,
    V: ExchangeData,
{
    use differential_dataflow::hashable::Hashable;
    use differential_dataflow::operators::reduce::Reduce;

    let input = input.map(|(key,val)| (key.hashed(),(val,key)));

    let top_k =
    input
        .map(|(hash,(val,key))| (hash % 10000, (val,key)))
        .reduce(move |_key, input, output| {
            for ((val, key), _wgt) in input.iter().rev().take(limit) {
                output.push(((val.clone(), key.clone()), 1));
            }
        })
        .map(|(hash, (val,key))| (hash % 100, (val,key)))
        .reduce(move |_key, input, output| {
            for ((val, key), _wgt) in input.iter().rev().take(limit) {
                output.push(((val.clone(), key.clone()), 1));
            }
        })
        .map(|(hash, (val,key))| (hash % 1, (val,key)));

    top_k
        .reduce(move |_zero, input, output| {
            let mut result = Vec::new();
            result.extend(input.iter().rev().take(limit).map(|((_val,key),_wgt)| key.clone()));
            output.push((result, 1));
        })
        .map(|(_hash,vec)| vec)
}