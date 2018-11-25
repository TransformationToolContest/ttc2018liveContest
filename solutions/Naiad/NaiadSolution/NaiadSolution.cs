using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Research.Naiad;
using Microsoft.Research.Naiad.Input;
using Microsoft.Research.Naiad.Frameworks.GraphLINQ;
using Microsoft.Research.Naiad.Frameworks.DifferentialDataflow;
using NMF.Models.Changes;
using TTC2018.LiveContest;
using TTC2018.LiveContest.SocialNetwork;
using Microsoft.Research.Naiad.Dataflow;

namespace Naiad
{
    abstract class ObjectWithId : IEquatable<ObjectWithId>
    {
        public string Id;

        public ObjectWithId(string id)
        {
            Id = id;
        }
        public bool Equals(ObjectWithId other)
        {
            return Id == other.Id;
        }

        override public int GetHashCode()
        {
            return Id.GetHashCode();
        }
    }
    class Edge : IEquatable<Edge>
    {
        public string From;
        public string To;

        public Edge(string from, string to)
        {
            From = from;
            To = to;
        }
        public bool Equals(Edge other)
        {
            return From == other.From && To == other.To;
        }

        override public int GetHashCode()
        {
            return From.GetHashCode() ^ To.GetHashCode();
        }
    }

    class User : ObjectWithId, IEquatable<User>
    {
        public string Name;
        public User(string id, string name) : base(id)
        {
            Name = name;
        }
        public bool Equals(User other)
        {
            return Equals(this, other);
        }
    }

    class Submission : ObjectWithId, IEquatable<Submission>
    {
        public DateTime Timestamp;
        public string Content;

        public Submission(string id, DateTime timestamp, string content) : base(id)
        {
            Timestamp = timestamp;
            Content = content;
        }
        public bool Equals(Submission other)
        {
            return Equals(this, other);
        }
    }
    class Post : Submission, IEquatable<Post>
    {
        public Post(string id, DateTime timestamp, string content) : base(id, timestamp, content)
        {
        }
        public bool Equals(Post other)
        {
            return Equals(this, other);
        }
    }
    class Comment : Submission, IEquatable<Comment>
    {
        public Comment(string id, DateTime timestamp, string content) : base(id, timestamp, content)
        {
        }
        public bool Equals(Comment other)
        {
            return Equals(this, other);
        }
    }

    /*
     * original -> reply 
     */
    class CommentedEdge : Edge, IEquatable<CommentedEdge>
    {
        public CommentedEdge(string from, string to) : base(from, to) { }
        public bool Equals(CommentedEdge other)
        {
            return Equals(this, other);
        }
    }

    /*
     * user -> comment     
    */
    class LikesEdge : Edge, IEquatable<LikesEdge>
    {
        public LikesEdge(string from, string to) : base(from, to) { }
        public bool Equals(LikesEdge other)
        {
            return Equals(this, other);
        }
    }

    /*
     * comment -> rootPost
     */
    class PostEdge : Edge, IEquatable<PostEdge>
    {
        public PostEdge(string from, string to) : base(from, to) { }
        public bool Equals(PostEdge other)
        {
            return Equals(this, other);
        }
    }

    /*
     * submission -> user
     */
    class SubmitterEdge : Edge, IEquatable<SubmitterEdge>
    {
        public SubmitterEdge(string from, string to) : base(from, to) { }
        public bool Equals(SubmitterEdge other)
        {
            return Equals(this, other);
        }
    }

    /*
     * If a friend of b, then b friend of a. Despite of that, only one of the two edges will be exist.
     */
    class FriendEdge : Edge, IEquatable<FriendEdge>
    {
        public FriendEdge(string from, string to) : base(from, to) { }
        public bool Equals(FriendEdge other)
        {
            return Equals(this, other);
        }
    }

    class EquatableTriple<T1, T2, T3> : IEquatable<EquatableTriple<T1, T2, T3>>
        where T1 : IEquatable<T1>
        where T2 : IEquatable<T2>
        where T3 : IEquatable<T3>
    {
        public T1 First { get; }
        public T2 Second { get; }
        public T3 Third { get; }

        public EquatableTriple(T1 item1, T2 item2, T3 item3)
        {
            First = item1;
            Second = item2;
            Third = item3;
        }
        public bool Equals(EquatableTriple<T1, T2, T3> other)
        {
            return First.Equals(other.First) && Second.Equals(other.Second) && Third.Equals(other.Third);
        }

        override public int GetHashCode()
        {
            return First.GetHashCode() ^ Second.GetHashCode() ^ Third.GetHashCode();
        }

    }

    abstract class NaiadSolution : Solution, IDisposable
    {

        private ICollection<User> rawUsers;
        private ICollection<Post> rawPosts;
        private ICollection<Comment> rawComments;
        private ICollection<CommentedEdge> rawCommentedEdges;
        private ICollection<LikesEdge> rawLikesEdges;
        private ICollection<PostEdge> rawPostEdges;
        private ICollection<SubmitterEdge> rawSubmitterEdges;
        private ICollection<FriendEdge> rawFriendEdges;

        protected OneOffComputation computation;

        protected InputCollection<User> users;
        protected InputCollection<Post> posts;
        protected InputCollection<Comment> comments;
        protected InputCollection<CommentedEdge> commentedEdges;
        protected InputCollection<LikesEdge> likesEdges;
        protected InputCollection<PostEdge> postEdges;
        protected InputCollection<SubmitterEdge> submitterEdges;
        protected InputCollection<FriendEdge> friendEdges;

        protected int actualEpoch;

        public NaiadSolution()
        {
            rawUsers = new List<User>();
            rawPosts = new List<Post>();
            rawComments = new List<Comment>();
            rawCommentedEdges = new List<CommentedEdge>();
            rawLikesEdges = new List<LikesEdge>();
            rawPostEdges = new List<PostEdge>();
            rawSubmitterEdges = new List<SubmitterEdge>();
            rawFriendEdges = new List<FriendEdge>();

            computation = NewComputation.FromConfig(new Configuration());

            users = computation.NewInputCollection<User>();
            posts = computation.NewInputCollection<Post>();
            comments = computation.NewInputCollection<Comment>();
            commentedEdges = computation.NewInputCollection<CommentedEdge>();
            likesEdges = computation.NewInputCollection<LikesEdge>();
            postEdges = computation.NewInputCollection<PostEdge>();
            submitterEdges = computation.NewInputCollection<SubmitterEdge>();
            friendEdges = computation.NewInputCollection<FriendEdge>();

            actualEpoch = -1;
        }
        public void Dispose()
        {
            computation.Dispose();
        }

        protected void ProcessUser(IUser user)
        {
            rawUsers.Add(new User(user.Id, user.Name));
            foreach (var f in user.Friends)
            {
                if (f.Id.CompareTo(user.Id) < 0)
                {
                    rawFriendEdges.Add(new FriendEdge(user.Id, f.Id));
                }
            }
        }

        protected void ProcessSubmission(ISubmission submission)
        {
            rawSubmitterEdges.Add(new SubmitterEdge(submission.Id, submission.Submitter.Id));
            foreach (var c in submission.Comments)
            {
                rawCommentedEdges.Add(new CommentedEdge(submission.Id, c.Id));
                ProcessComment(c);
            }

        }
        protected void ProcessPost(IPost post)
        {
            rawPosts.Add(new Post(post.Id, post.Timestamp, post.Content));
            ProcessSubmission(post);

        }
        protected void ProcessComment(IComment comment)
        {

            rawComments.Add(new Comment(comment.Id, comment.Timestamp, comment.Content));
            rawPostEdges.Add(new PostEdge(comment.Id, comment.Post.Id));
            foreach (var user in comment.LikedBy)
            {
                rawLikesEdges.Add(new LikesEdge(user.Id, comment.Id));

            }
            ProcessSubmission(comment);

        }
        private void CallOnNext()
        {
            users.OnNext(rawUsers);
            // users.OnCompleted();
            posts.OnNext(rawPosts);
            //posts.OnCompleted();
            comments.OnNext(rawComments);
            //comments.OnCompleted();
            commentedEdges.OnNext(rawCommentedEdges);
            //commentedEdges.OnCompleted();
            likesEdges.OnNext(rawLikesEdges);
            //likesEdges.OnCompleted();
            postEdges.OnNext(rawPostEdges);
            //postEdges.OnCompleted();
            submitterEdges.OnNext(rawSubmitterEdges);
            //submitterEdges.OnCompleted();
            friendEdges.OnNext(rawFriendEdges);
            //friendEdges.OnCompleted();

            ++actualEpoch;
        }
        protected void LoadModel(SocialNetworkRoot model)
        {
            foreach (var u in model.Users)
            {
                ProcessUser(u);
            }
            foreach (var p in model.Posts)
            {
                ProcessPost(p);
            }

            computation.Activate();
            CallOnNext();
        }

        protected void UpdateInputs(ModelChangeSet changes)
        {
            rawUsers.Clear();
            rawPosts.Clear();
            rawComments.Clear();
            rawCommentedEdges.Clear();
            rawLikesEdges.Clear();
            rawPostEdges.Clear();
            rawSubmitterEdges.Clear();
            rawFriendEdges.Clear();

            foreach (var change in changes.Changes)
            {
                if (change is AssociationCollectionInsertion)
                {
                    var aci = change as AssociationCollectionInsertion;
                    if (aci.AddedElement is IComment)
                    {
                        var comment = aci.AddedElement as IComment;
                        ProcessComment(comment);
                    }
                    else if (aci.AddedElement is IPost)
                    {
                        var post = aci.AddedElement as IPost;
                        ProcessPost(post);
                    }
                    else if (aci.AddedElement is IUser)
                    {
                        var user = aci.AddedElement as IUser;
                        ProcessUser(user);
                    }
                }
            }
            CallOnNext();
        }

        protected void Sync()
        {
            computation.Sync(actualEpoch);

        }
        ~NaiadSolution()
        {
            Dispose();
        }
    }

    class NaiadSolutionQ1 : NaiadSolution
    {
        //protected Collection<CommentedEdge> startigCommentedEdges;
        private string resultString;
        private Collection<Edge, Epoch> startigCommentedEdges;
        private Collection<Edge, Epoch> reachedComments;
        private Collection<Pair<string, int>, Epoch> commentLikes;
        private Collection<EquatableTriple<string, int, DateTime>, Epoch> result;
        private Subscription subcription;
        public override string Initial()
        {
            /* initial   post -> comment   edges */
            /* DoNotUse can be null sometimes, should be investigated...*/
            startigCommentedEdges = posts.Join(commentedEdges, p => p.Id, e => e.From, p => p.Id, e => e.To, (pId, DoNotUse, to) => new Edge(pId, to));

            /* pair ->1..* comment   edges*/
            reachedComments = startigCommentedEdges.FixedPoint((lc, x) => x.Join(commentedEdges.EnterLoop(lc),
                                                               edge => edge.To,
                                                               edge => edge.From,
                                                               (e1, e2) => new Edge(e1.From, e2.To))
                                                           .Concat(x)
                                                           .Distinct());
            /* comment, likeCount pairs */
            commentLikes = comments.CoGroupBy(
                likesEdges,
                c => c.Id,
                l => l.To,
                (cId, cs, ls) => new List<Pair<string, int>> { new Pair<string, int>(cId, ls.Count()) });

            result = reachedComments
                .Join(commentLikes,
                    e => e.To,
                    l => l.First,
                    (e, l) => new EquatableTriple<string, string, int>(e.From, e.To, l.Second))
                .GroupBy(
                    t => t.First,
                    (pId, ls) =>
                    {
                        var count = 0;
                        foreach (var l in ls)
                        {
                            count += (l.Third + 10);
                        }
                        return new List<Pair<string, int>> { new Pair<string, int>(pId, count) };
                    })
                .Join(posts, s => s.First, p => p.Id, s => s.Second, p => p.Timestamp, (pId, count, timestamp) => new EquatableTriple<string, int, DateTime>(pId, count, timestamp))
                .Concat(posts.Select(x => new EquatableTriple<string, int, DateTime>(x.Id, 0, x.Timestamp)))
                .Max(triple => triple.First, triple => triple.Second);

            resultString = "";
            subcription = result.Subscribe(x =>
               {
                   foreach (var r in x.OrderByDescending(r => r.record.Third).OrderByDescending(r => r.record.Second).Take(3))
                   {
                       resultString += r.record.First + "|";
                       Console.Error.WriteLine(r.weight + " " + r.record.First + " point: " + r.record.Second);
                   }
                   //resultString = resultString.Substring(0, resultString.Length - 1);
               });

            LoadModel(SocialNetwork);
            Sync();
            return resultString;

        }

        public override string Update(ModelChangeSet changes)
        {
            resultString = "";
            subcription = result.Subscribe(x =>
            {
                foreach (var r in x.OrderByDescending(r => r.record.Third).OrderByDescending(r => r.record.Second).Take(3))
                {
                    resultString += r.record.First + "|";
                    Console.Error.WriteLine(r.weight + " " + r.record.First + " point: " + r.record.Second);
                }
                //resultString = resultString.Substring(0, resultString.Length - 1);
            });
            UpdateInputs(changes);
            Sync();
            return resultString;
        }
    }
    class NaiadSolutionQ2 : NaiadSolution
    {
        public override string Initial()
        {
            Console.WriteLine("InitialCalled");
            return "Initial";
        }

        public override string Update(ModelChangeSet changes)
        {
            Console.WriteLine("UpdateCalled");
            return "Update";
        }
    }
}
