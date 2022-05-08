// import types for the engine
use differential_datalog::api::HDDlog; //The DDLog Database engine itself
use snq1_ddlog::Relations;
use snq1_ddlog::relid2name;

// import all types defined by the datalog program itself
use snq1_ddlog::typedefs::*;

// Trait that must be implemented by an instance of a DDlog program. 
// Type that represents a set of changes to DDlog relations.
// Returned by `DDlog::transaction_commit_dump_changes()`.
use differential_datalog::{
    DDlog, 
    DDlogDynamic
}; 
use differential_datalog::DeltaMap; // A trait representing the changes resulting from a given update.
use differential_datalog::ddval::DDValue; // A generic DLog value type
use differential_datalog::ddval::DDValConvert; //Another helper trair
use differential_datalog::program::config::{Config, ProfilingConfig};
use differential_datalog::program::RelId; // Numeric relations id
use differential_datalog::program::Update; // A type representing updates to the database
use differential_datalog::record::{FromRecord, IntoRecord}; // A type representing individual facts

pub struct DDLogSNQ1{
    hddlog: HDDlog,
}

impl DDLogSNQ1 {
    pub fn new()  -> Result<DDLogSNQ1, String> {
        let number_threads = 1;
        let config = Config::new()
            .with_timely_workers(number_threads)
            .with_profiling_config(ProfilingConfig::SelfProfiling {
                // Directory to store profiles under or `None` for current directory.
                profile_directory: None
            });

        // Instantiate the DDlog program with this configuration.
        // The second argument of `run_with_config` is a Boolean flag that indicates
        // whether DDlog will track the complete snapshot of output relations.  It
        // should only be set for debugging in order to dump the contents of output
        // tables using `HDDlog::dump_table()`.  Otherwise, indexes are the preferred
        // way to achieve this.
        let (hddlog, _init_state) = snq1_ddlog::run_with_config(config, false)?;

        return Ok(Self{hddlog});
    }

    pub fn flush_updates(&mut self, updates: Vec<Update<DDValue>>) -> Result<DeltaMap<DDValue>, String> {
        self.hddlog.transaction_start()?;
        self.hddlog.apply_updates(&mut updates.into_iter())?;
        let delta = self.hddlog.transaction_commit_dump_changes()?;
        return Ok(delta); 
    }

    pub fn create_users(&mut self, users: Vec<Vec<String>>) -> Vec<Update<DDValue>> {
        let updates = users.into_iter().map(|strs| {
            let mut drains = strs.into_iter();
            let id = drains.next().unwrap().parse::<u64>().expect("Must be a number");
            let name = drains.next().unwrap(); 
            Update::Insert {
                relid: Relations::User as RelId,
                v: User { id: id, name: name }.into_ddvalue(),
            }
        }).collect::<Vec<_>>();

        updates
    } 

    pub fn create_likes(&mut self, likes: Vec<Vec<String>>) -> Vec<Update<DDValue>> {
        let updates = likes.into_iter().map(|strs| {
            let mut drains = strs.into_iter();
            let src_user = drains.next().unwrap().parse::<u64>().expect("Must be a number");
            let dst_comment = drains.next().unwrap().parse::<u64>().expect("Must be a number");
            Update::Insert {
                relid: Relations::Likes as RelId,
                v: Likes { srcUser: src_user, dstComment: dst_comment }.into_ddvalue(),
            }
        }).collect::<Vec<_>>();

        updates
    }

    pub fn create_friends(&mut self, friendships: Vec<Vec<String>>) -> Vec<Update<DDValue>> {
        let updates = friendships.into_iter().map(|strs| {
            let mut drains = strs.into_iter();
            let src = drains.next().unwrap().parse::<u64>().expect("Must be a number");
            let dst = drains.next().unwrap().parse::<u64>().expect("Must be a number");
            Update::Insert {
                relid: Relations::Friend as RelId,
                v: Friend { src, dst }.into_ddvalue(),
            }
        }).collect::<Vec<_>>();

        updates
    }

    pub fn create_posts(&mut self, posts: Vec<Vec<String>>) -> Vec<Update<DDValue>> {
        let updates = posts.into_iter().map(|strs| {
            let mut drains = strs.into_iter();
            let post_id = drains.next().unwrap().parse::<u64>().expect("Must be a number");
            let timestamp = drains.next().unwrap();
            let content = drains.next().unwrap();
            let creator = drains.next().unwrap().parse::<u64>().expect("Must be a number");
            Update::Insert {
                relid: Relations::Posts as RelId,
                v: Posts { 
                    id: post_id, 
                    timestamp: timestamp, 
                    content: content, 
                    submitter: creator
                }.into_ddvalue(),
            }
        }).collect::<Vec<_>>();

        updates
    }

    pub fn create_comments(&mut self, comments: Vec<Vec<String>>) -> Vec<Update<DDValue>> {
        let updates = comments.into_iter().map(|strs| {
            let mut drains = strs.into_iter();
            let comment_id = drains.next().unwrap().parse::<u64>().expect("Must be a number");
            let timestamp = drains.next().unwrap();
            let content = drains.next().unwrap();
            let creator_id = drains.next().unwrap().parse::<u64>().expect("Must be a number");
            // Comment can comment on other comments so the parent could be either post or comment.
            let parent_id = drains.next().unwrap().parse::<u64>().expect("Must be a number");
            Update::Insert {
                relid: Relations::Comments as RelId,
                v: Comments { 
                    id: comment_id, 
                    timestamp: timestamp, 
                    content: content,
                    creator: creator_id,
                    parent: parent_id
                }.into_ddvalue(),
            }
        }).collect::<Vec<_>>();

        updates
    }

    pub fn dump_delta(delta: &DeltaMap<DDValue>) {
        for (rel, changes) in delta.iter() {
            println!("Changes to relation {}", relid2name(*rel).unwrap());
            for (val, weight) in changes.iter() {
                println!("{} {:+}", val, weight);
            }
        }
    }

    pub fn dump_delta_by_relid(delta: &DeltaMap<DDValue>, relid: RelId) {
        let changes = delta.try_get_rel(relid).unwrap();
        for (val, weight) in changes.iter() {
            println!("{} {:+}", val, weight);
        }
    }

    pub fn stop(&mut self){
        self.hddlog.stop().unwrap();
    }
}


fn load_data(filename: &str) -> Vec<Vec<String>> {
    // Standard io/fs boilerplate.
    use std::io::{BufRead, BufReader};
    use std::fs::File;

    let mut data = Vec::new();
    let file = BufReader::new(File::open(filename).expect("Could open file"));
    let lines = file.lines();

    for (_, readline) in lines.enumerate() {
        if let Ok(line) = readline {
            let text : Vec<String> =
            line.split('|')
                .map(|x| x.to_string())
                .collect();

            data.push(text);
        }
    }

    data
}

fn main() {
    let mut timer = std::time::Instant::now();
    let mut bse = DDLogSNQ1::new().unwrap();
    // We hardcode some parameters here as default options.
    let change_path = std::env::var("ChangePath").unwrap_or("None".to_string());
    // Run how many times for the same query? Only once by default.
    let run_index = std::env::var("RunIndex").unwrap_or("1".to_string());
    let sequences = std::env::var("Sequences").unwrap_or("20".to_string()).parse::<usize>().expect("Couldn't parse Sequences as an integer");
    let change_set = std::env::var("ChangeSet").unwrap_or("None".to_string());
    let query = std::env::var("Query").unwrap_or("Q2".to_string());
    let tool = std::env::var("Tool").unwrap_or("Differential-Datalog".to_string());

    // You can pass the path as argument in your own testing on different size of models and when `HARNESS` is specified the program
    // will read environmental arguments to be used in benchmark.
    let mut path = std::env::args().nth(1).expect("Must describe path");
    if path.as_str() == "HARNESS" {
        path = format!("{}/", change_path);
    }

    println!("{:?};{:?};{};{};0;\"Initialization\";\"Time\";{}", tool, query, change_set, run_index, timer.elapsed().as_nanos());
    timer = std::time::Instant::now();

    // Return lists of lists of strings.
    let comms = load_data(&format!("{}csv-comments-initial.csv", path));
    let knows = load_data(&format!("{}csv-friends-initial.csv", path));
    let likes = load_data(&format!("{}csv-likes-initial.csv", path));
    let posts = load_data(&format!("{}csv-posts-initial.csv", path));
    let users = load_data(&format!("{}csv-users-initial.csv", path));

    // Merge all updates here from different relations.
    let mut updates = vec![];
    let user_updates = bse.create_users(users);
    let like_updates = bse.create_likes(likes);
    let know_updates = bse.create_friends(knows); 
    let comment_updates = bse.create_comments(comms);
    let post_updates = bse.create_posts(posts); 

    updates.extend(user_updates);
    updates.extend(like_updates);
    updates.extend(know_updates);
    updates.extend(comment_updates);
    updates.extend(post_updates);

    println!("{:?};{:?};{};{};0;\"Load\";\"Time\";{}", tool, query, change_set, run_index, timer.elapsed().as_nanos());
    timer = std::time::Instant::now();

    let mut delta = bse.flush_updates(updates).unwrap();

    // DDLogSNQ1::dump_delta_by_relid(&delta, Relations::Comments as RelId);
    // DDLogSNQ1::dump_delta_by_relid(&delta, Relations::PostCommentScore as RelId);
    // DDLogSNQ1::dump_delta_by_relid(&delta, Relations::PostLikeScore as RelId);
    // DDLogSNQ1::dump_delta_by_relid(&delta, Relations::PostTotalScore as RelId);

    // Print out the delta after the changes to relations.
    // DDLogSNQ1::dump_delta(&delta);

    let mut three_score_str = "WRONG".to_string();

    let top3_changes = delta.get_rel(Relations::Top3PostScore as RelId);
    for (val, _weight) in top3_changes.clone() {
        let three_score: Top3PostScore = Top3PostScore::from_record(&val.into_record()).unwrap();
        three_score_str = format!("{}|{}|{}", three_score.first, three_score.second, three_score.third);
        println!("{:?};\"Q1\";{};{};0;\"Initial\";\"Elements\";{:?}", tool, change_set, run_index, three_score_str);    
    }

    // println!("Execution time: {}", timer.elapsed().as_millis());

    println!("{:?};{:?};{};{};0;\"Initial\";\"Time\";{}", tool, query, change_set, run_index, timer.elapsed().as_nanos());
    timer = std::time::Instant::now();

    for round in 1 .. (sequences + 1) {
        let filename = format!("{}change{:02}.csv", path, round);
        let changes = load_data(&filename);
        let mut sequence_updates = vec![];

        for mut change in changes {
            let collection = change.remove(0);
            match collection.as_str() {
                "Comments" => { sequence_updates.push(bse.create_comments(vec![change]).into_iter().next().unwrap()); },
                "Friends" => { sequence_updates.push(bse.create_friends(vec![change]).into_iter().next().unwrap()); },
                "Likes" => { sequence_updates.push(bse.create_likes(vec![change]).into_iter().next().unwrap()); },
                "Posts" => { sequence_updates.push(bse.create_posts(vec![change]).into_iter().next().unwrap()); },
                "Users" => { sequence_updates.push(bse.create_users(vec![change]).into_iter().next().unwrap()); },
                x => { panic!("Weird enum variant: {}", x); },
            }
        }

        let mut delta = bse.flush_updates(sequence_updates).unwrap();

        // DDLogSNQ1::dump_delta(&delta);

        let top3_changes = delta.get_rel(Relations::Top3PostScore as RelId);

        if top3_changes.len() == 0 {
            println!("{:?};\"Q1\";{};{};{};\"Update\";\"Elements\";{:?}", tool, change_set, run_index, round, three_score_str);    
        } else {
            for (val, weight) in top3_changes.clone() {
                if weight > 0 {
                    let three_score: Top3PostScore = Top3PostScore::from_record(&val.into_record()).unwrap();
                    three_score_str = format!("{}|{}|{}", three_score.first, three_score.second, three_score.third);
                    println!("{:?};\"Q1\";{};{};{};\"Update\";\"Elements\";{:?}", tool, change_set, run_index, round, three_score_str);    
                }
            }
        }

        println!("{:?};{:?};{};{};{};\"Update\";\"Time\";{}", tool, query, change_set, run_index, round, timer.elapsed().as_nanos());
        timer = std::time::Instant::now();
    }
}