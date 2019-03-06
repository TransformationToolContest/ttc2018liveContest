#define ONNEXT_CALL_AS_PARAM
#undef ONNEXT_CALL_AS_PARAM
#define CLEAR_AT_EVERY_UPDATE
//#undef CLEAR_AT_EVERY_UPDATE

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
using NMF.Models;
using Microsoft.Research.Naiad.Dataflow.StandardVertices;

namespace Naiad
{
    public abstract class NaiadSolutionBase : Solution, IDisposable
    {

        #region IDisposable Support
        private bool disposedValue = false; // To detect redundant calls

        protected virtual void Dispose(bool disposing)
        {
            if (!disposedValue)
            {
                if (disposing)
                {
                    // TODO: dispose managed state (managed objects).
                }

                // TODO: free unmanaged resources (unmanaged objects) and override a finalizer below.
                // TODO: set large fields to null.

                disposedValue = true;
            }
        }

        // TODO: override a finalizer only if Dispose(bool disposing) above has code to free unmanaged resources.
        ~NaiadSolutionBase()
        {
            // Do not change this code. Put cleanup code in Dispose(bool disposing) above.
            Dispose(false);
        }

        // This code added to correctly implement the disposable pattern.
        public void Dispose()
        {
            // Do not change this code. Put cleanup code in Dispose(bool disposing) above.
            Dispose(true);
            // TODO: uncomment the following line if the finalizer is overridden above.
            // GC.SuppressFinalize(this);
        }
        #endregion
    }
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
            return base.Equals(other);
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
            return base.Equals(other);
        }
    }
    class Post : Submission, IEquatable<Post>
    {
        public Post(string id, DateTime timestamp, string content) : base(id, timestamp, content)
        {
        }
        public bool Equals(Post other)
        {
            return base.Equals(other);
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
            return base.Equals(other);
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
            return base.Equals(other);
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
            return base.Equals(other);
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
            return base.Equals(other);
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
            return base.Equals(other);
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
    class CommentDependentKnows : IEquatable<CommentDependentKnows>
    {
        public string CommentId;
        public string UserId1;
        public string UserId2;

        public CommentDependentKnows(string commentId, string userId1, string userId2)
        {
            CommentId = commentId;
            UserId1 = userId1;
            UserId2 = userId2;
        }

        public bool Equals(CommentDependentKnows other)
        {
            return CommentId == other.CommentId && UserId1 == other.UserId1 && UserId2 == other.UserId2;
        }
    }
    class CommentDependentLabeledUser : IEquatable<CommentDependentLabeledUser>
    {
        public string CommentId;
        public string UserId;
        public string Label;

        public CommentDependentLabeledUser(string commentId, string userId1, string userId2)
        {
            CommentId = commentId;
            UserId = userId1;
            Label = userId2;
        }

        public bool Equals(CommentDependentLabeledUser other)
        {
            return CommentId == other.CommentId && UserId == other.UserId && Label == other.Label;
        }
    }
    interface Identifiable
    {
        string GetId();

        int GetValue();
    }
    class Task1PostInfo : IComparable<Task1PostInfo>, IEquatable<Task1PostInfo>, Identifiable
    {
        public string PostId;
        public int Score;
        public DateTime Timestamp;

        public Task1PostInfo(string postId, int score, DateTime timestamp)
        {
            PostId = postId;
            Score = score;
            Timestamp = timestamp;
        }

        // x.CompareTo(y) < 0 ==> x < y
        // x.CompareTo(y) > 0 ==> x > y
        public int CompareTo(Task1PostInfo other)
        {
            var scoreResult = Score.CompareTo(other.Score);
            if (scoreResult != 0)
            {
                return scoreResult;
            }
            return Timestamp.CompareTo(other.Timestamp);
        }

        public bool Equals(Task1PostInfo other)
        {
            return Score == other.Score && Timestamp == other.Timestamp && PostId == other.PostId;
        }

        public string GetId()
        {
            return PostId;
        }

        public int GetValue()
        {
            return Score;
        }
    }
    class Task2CommentInfo : IComparable<Task2CommentInfo>, IEquatable<Task2CommentInfo>, Identifiable
    {
        public string CommentId;
        public int LargestComponentSize;
        public DateTime Timestamp;

        public Task2CommentInfo(string commentId, int largestComponentSize, DateTime timestamp)
        {
            CommentId = commentId;
            LargestComponentSize = largestComponentSize;
            Timestamp = timestamp;
        }

        // x.CompareTo(y) < 0 ==> x < y
        // x.CompareTo(y) > 0 ==> x > y
        public int CompareTo(Task2CommentInfo other)
        {
            var scoreResult = LargestComponentSize.CompareTo(other.LargestComponentSize);
            if (scoreResult != 0)
            {
                return scoreResult;
            }
            return Timestamp.CompareTo(other.Timestamp);
        }

        public bool Equals(Task2CommentInfo other)
        {
            return CommentId == other.CommentId && LargestComponentSize == other.LargestComponentSize;
        }

        public string GetId()
        {
            return CommentId;
        }

        public int GetValue()
        {
            return LargestComponentSize;
        }
    }
    abstract class NaiadSolution<T> : NaiadSolutionBase, IDisposable
        where T : IComparable<T>, IEquatable<T>, Identifiable
    {
        private ICollection<User> rawUsers;
        private ICollection<Post> rawPosts;
        private ICollection<Comment> rawComments;
        private ICollection<CommentedEdge> rawCommentedEdges;
        private ICollection<LikesEdge> rawLikesEdges;
        private ICollection<PostEdge> rawPostEdges;
        private ICollection<SubmitterEdge> rawSubmitterEdges;
        private ICollection<FriendEdge> rawFriendEdges;

        private readonly int TopCount = 3;

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

        protected IList<T> topValues;
        protected Dictionary<string, int> idToPlace;

        protected bool isDisposed;

        public NaiadSolution(string[] args)
        {
            rawUsers = new List<User>();
            rawPosts = new List<Post>();
            rawComments = new List<Comment>();
            rawCommentedEdges = new List<CommentedEdge>();
            rawLikesEdges = new List<LikesEdge>();
            rawPostEdges = new List<PostEdge>();
            rawSubmitterEdges = new List<SubmitterEdge>();
            rawFriendEdges = new List<FriendEdge>();

            computation = NewComputation.FromArgs(ref args);

            users = computation.NewInputCollection<User>();
            posts = computation.NewInputCollection<Post>();
            comments = computation.NewInputCollection<Comment>();
            commentedEdges = computation.NewInputCollection<CommentedEdge>();
            likesEdges = computation.NewInputCollection<LikesEdge>();
            postEdges = computation.NewInputCollection<PostEdge>();
            submitterEdges = computation.NewInputCollection<SubmitterEdge>();
            friendEdges = computation.NewInputCollection<FriendEdge>();

            actualEpoch = -1;

            isDisposed = false;
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
#if (!ONNEXT_CALL_AS_PARAM)
            CallOnNext();
#endif
        }
        protected void Init()
        {
            topValues = new List<T>();
            idToPlace = new Dictionary<string, int>();
        }
        protected void AddNewComment(ISubmission original, IComment comment)
        {
            rawComments.Add(new Comment(comment.Id, comment.Timestamp, comment.Content));
            rawCommentedEdges.Add(new CommentedEdge(original.Id, comment.Id));
            rawPostEdges.Add(new PostEdge(comment.Id, comment.Post.Id));
            rawSubmitterEdges.Add(new SubmitterEdge(comment.Id, comment.Submitter.Id));
            foreach (var childComment in comment.Comments)
            {
                AddNewComment(comment, childComment);
            }
        }
        protected void AddNewPost(IPost post)
        {
            rawPosts.Add(new Post(post.Id, post.Timestamp, post.Content));
            rawSubmitterEdges.Add(new SubmitterEdge(post.Id, post.Submitter.Id));
            foreach (var comment in post.Comments)
            {
                AddNewComment(post, comment);
            }
        }
        protected void ProcessChange(IModelChange change)
        {
            if (change is IAssociationCollectionInsertion)
            {
                var aci = change as IAssociationCollectionInsertion;
                switch (aci.Feature.Name)
                {
                    case "likedBy":
                        {
                            var comment = aci.AffectedElement as IComment;
                            var user = aci.AddedElement as IUser;
                            rawLikesEdges.Add(new LikesEdge(user.Id, comment.Id));
                            break;
                        }
                    case "likes":
                        {
                            // E.g.:
                            // <changes xsi:type="changes:ChangeTransaction">
                            //   <sourceChange xsi:type="changes:AssociationCollectionInsertion" addedElement="social:Comment initial.xmi#406915" affectedElement="social:User initial.xmi#1259" feature="ecore:EReference https://www.transformation-tool-contest.eu/2018/social_media#//User/likes" />
                            //   <nestedChanges xsi:type="changes:AssociationCollectionInsertion" addedElement="social:User initial.xmi#1259" affectedElement="social:Comment initial.xmi#406915" feature="ecore:EReference https://www.transformation-tool-contest.eu/2018/social_media#//Comment/likedBy" />
                            // </changes>
                            // We handle the opposite direction in above statement
                            break;
                        }
                    case "submissions":
                        {
                            // E.g.:
                            // <changes xsi:type="changes:AssociationCollectionInsertion" addedElement="social:Post #//@changes.3/@addedElement" affectedElement="social:User initial.xmi#1269" feature="ecore:EReference https://www.transformation-tool-contest.eu/2018/social_media#//User/submissions" />
                            // <changes xsi:type="changes:CompositionListInsertion" index="889" affectedElement="social:SocialNetworkRoot initial.xmi#/" feature="ecore:EReference https://www.transformation-tool-contest.eu/2018/social_media#//SocialNetworkRoot/posts">
                            //   <addedElement xmlns:social="https://www.transformation-tool-contest.eu/2018/social_media" xsi:type="social:Post" id="255833" timestamp="2010-03-10T15:43:59" content="photo255833.jpg" submitter="initial.xmi#1269" />
                            // </changes>
                            // We handle the opposite direction in ICompositionListInsertion->posts and comments 
                            break;
                        }
                    case "friends":
                        {
                            var user1 = aci.AffectedElement as IUser;
                            var user2 = aci.AddedElement as IUser;
                            if (user1.Id.CompareTo(user2.Id) < 0)
                            {
                                rawFriendEdges.Add(new FriendEdge(user1.Id, user2.Id));
                            }
                            break;
                        }
                    default:
                        throw new Exception(aci.Feature.Name);
                }
            }
            else if (change is IAssociationPropertyChange)
            {
                var apc = change as IAssociationPropertyChange;
                switch (apc.Feature.Name)
                {
                    case "commented":
                        {
                            // E.g.:
                            //< changes xsi: type = "changes:ChangeTransaction" >
                            //    < sourceChange xsi: type = "changes:CompositionListInsertion" index = "1" affectedElement = "social:Comment initial.xmi#725040" feature = "ecore:EReference https://www.transformation-tool-contest.eu/2018/social_media#//Submission/comments" >
                            //        < addedElement xmlns: social = "https://www.transformation-tool-contest.eu/2018/social_media" xsi: type = "social:Comment" post = "initial.xmi#723156" id = "725041" timestamp = "2010-03-10T15:31:35" content = "About Joseph Haydn, Michael Haydn, himself . About Frédéric Chopin, are for solo piano,." submitter = "initial.xmi#4281" />
                            //    </ sourceChange >
                            //    < nestedChanges xsi: type = "changes:AssociationPropertyChange" newValue = "social:Comment initial.xmi#725040" affectedElement = "social:Comment #//@changes.1/@sourceChange/@addedElement" feature = "ecore:EReference https://www.transformation-tool-contest.eu/2018/social_media#//Comment/commented" />
                            //</ changes >
                            // We handle the opposite direction in ICompositionListInsertion->comments 
                            break;
                        }
                    default:
                        throw new Exception(apc.Feature.Name);
                }
            }
            else if (change is IChangeTransaction)
            {
                var ct = change as IChangeTransaction;
                ProcessChange(ct.SourceChange);
                foreach (var nestedChange in ct.NestedChanges)
                {
                    ProcessChange(nestedChange);
                }
            }
            else if (change is ICompositionListInsertion)
            {
                var clt = change as ICompositionListInsertion;
                switch (clt.Feature.Name)
                {
                    case "posts":
                        {
                            var post = clt.AddedElement as IPost;
                            AddNewPost(post);
                            break;
                        }
                    case "comments":
                        {
                            var submission = clt.AffectedElement as ISubmission;
                            var comment = clt.AddedElement as IComment;
                            AddNewComment(submission, comment);
                            break;
                        }
                    case "users":
                        {
                            var user = clt.AddedElement as IUser;
                            rawUsers.Add(new User(user.Id, user.Name));
                            break;
                        }
                    default:
                        throw new Exception(clt.Feature.Name);
                }
            }
            else if (change is IAttributePropertyChange)
            {
                var apc = change as IAttributePropertyChange;
                switch (apc.Feature.Name)
                {
                    case "name":
                        {
                            //var user = apc.AffectedElement as IUser;
                            //rawUsers.Add(new User(user.Id, user.Name));
                            break;
                        }
                    default:
                        throw new Exception(apc.Feature.Name);
                }
            }
            else
            {
                throw new Exception(change.GetType().ToString());
            }

        }
        protected void HandleResultUpdates(Weighted<T>[] resultUpdates)
        {
            foreach (var r in resultUpdates.OrderBy(r => r.weight))
            {
                if (r.weight < 0)
                {
                    if (idToPlace.ContainsKey(r.record.GetId()))
                    {
                        int place = idToPlace[r.record.GetId()];
                        idToPlace.Remove(r.record.GetId());

                        for (var i = topValues.Count - 1; i > place; i--)
                        {
                            idToPlace[topValues.ElementAt(i).GetId()] = i - 1;
                        }
                        topValues.RemoveAt(place);
                    }
                }
                if (r.weight > 0)
                {
                    if (topValues.Count < 3 || r.record.CompareTo(topValues.Last()) > 0)
                    {
                        var i = topValues.Count - 1;
                        for (; i >= 0; i--)
                        {
                            if (topValues.ElementAt(i).CompareTo(r.record) < 0)
                            {
                                idToPlace[topValues.ElementAt(i).GetId()] = idToPlace[topValues.ElementAt(i).GetId()] + 1;
                            }
                            else
                            {
                                break;
                            }
                        }
                        topValues.Insert(i + 1, r.record);
                        idToPlace.Add(r.record.GetId(), i + 1);

                        if (topValues.Count > TopCount)
                        {
                            var itemToRemove = topValues.Last();
                            idToPlace.Remove(itemToRemove.GetId());
                            topValues.RemoveAt(TopCount);
                        }
                    }
                }
            }
        }
        protected string GetResultString()
        {
            var resultString = "";
            foreach (var r in topValues)
            {
                resultString += r.GetId() + "|";
            }
            if (resultString.Length > 2)
            {
                resultString = resultString.Substring(0, resultString.Length - 1);
            }
            return resultString;
        }
        protected void UpdateInputs(ModelChangeSet changes, bool callOnNext)
        {
#if CLEAR_AT_EVERY_UPDATE
            rawUsers.Clear();
            rawPosts.Clear();
            rawComments.Clear();
            rawCommentedEdges.Clear();
            rawLikesEdges.Clear();
            rawPostEdges.Clear();
            rawSubmitterEdges.Clear();
            rawFriendEdges.Clear();
#endif

            foreach (var change in changes.Changes)
            {
                ProcessChange(change);
            }
            if (callOnNext)
            {
                CallOnNext();
            }
        }
        protected void Sync()
        {
            computation.Sync(actualEpoch);

        }
        #region IDisposable Support
        private bool disposedValue = false; // To detect redundant calls
        protected override void Dispose(bool disposing)
        {
            if (!disposedValue)
            {
                if (disposing)
                {
                    users.OnCompleted();
                    posts.OnCompleted();
                    comments.OnCompleted();
                    commentedEdges.OnCompleted();
                    likesEdges.OnCompleted();
                    postEdges.OnCompleted();
                    submitterEdges.OnCompleted();
                    friendEdges.OnCompleted();
                    computation.Join();
                    computation.Dispose();
                    rawUsers.Clear();
                    rawPosts.Clear();
                    rawComments.Clear();
                    rawCommentedEdges.Clear();
                    rawLikesEdges.Clear();
                    rawPostEdges.Clear();
                    rawSubmitterEdges.Clear();
                    rawFriendEdges.Clear();
                }

                // TODO: free unmanaged resources (unmanaged objects) and override a finalizer below.
                // TODO: set large fields to null.

                disposedValue = true;
                base.Dispose(disposing);
            }
        }

        // TODO: override a finalizer only if Dispose(bool disposing) above has code to free unmanaged resources.
        ~NaiadSolution()
        {
            // Do not change this code. Put cleanup code in Dispose(bool disposing) above.
            Dispose(false);
        }

        // This code added to correctly implement the disposable pattern.
        public new void Dispose()
        {
            // Do not change this code. Put cleanup code in Dispose(bool disposing) above.
            Dispose(true);
            // TODO: uncomment the following line if the finalizer is overridden above.
            // GC.SuppressFinalize(this);
        }
        #endregion
    }

    class NaiadSolutionQ1 : NaiadSolution<Task1PostInfo>
    {
        private Collection<Edge, Epoch> reachedComments;
        private Collection<Pair<string, int>, Epoch> commentLikes;
        private Collection<Task1PostInfo, Epoch> result;
        private Subscription subscription;

        public NaiadSolutionQ1(string[] args) : base(args)
        {
        }
        public override string Initial()
        {
            base.Init();
            /* initial   post -> comment   edges */
            /* DoNotUse can be null sometimes, should be investigated...*/

            /* post ->1..* comment   edges*/
            reachedComments = commentedEdges.Select(ce => new Edge(ce.From, ce.To)).FixedPoint((lc, x) => x.Join(commentedEdges.EnterLoop(lc),
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

            result = reachedComments.Join(commentLikes,
                   e => e.To,
                   l => l.First,
                   // post, comment, likeCount
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
                .Join(posts, s => s.First, p => p.Id, (l, p) => new Task1PostInfo(p.Id, l.Second, p.Timestamp))
                .Concat(posts.Select(x => new Task1PostInfo(x.Id, 0, x.Timestamp)))
                .Max(triple => triple.PostId, triple => triple.Score);


            subscription = result.Subscribe(x =>
               {
                   HandleResultUpdates(x);
               });

            LoadModel(SocialNetwork);
#if (!ONNEXT_CALL_AS_PARAM)
            Sync();
#endif
            return GetResultString();

        }

        public override string Update(ModelChangeSet changes)
        {
#if (ONNEXT_CALL_AS_PARAM)
            bool callOnChanged = false;//Int32.Parse(changes.AbsoluteUri.AbsolutePath.Substring(changes.AbsoluteUri.AbsolutePath.Length - 6, 2)) > 19;
#else
            bool callOnChanged = true;
#endif
            UpdateInputs(changes, callOnChanged);
            if (callOnChanged)
            {
                Sync();
                return GetResultString();
            }
            return "";
        }
        #region IDisposable Support
        private bool disposedValue = false; // To detect redundant calls

        protected override void Dispose(bool disposing)
        {
            if (!disposedValue)
            {
                if (disposing)
                {
                    subscription.Dispose();
                }

                // TODO: free unmanaged resources (unmanaged objects) and override a finalizer below.
                // TODO: set large fields to null.

                disposedValue = true;
                base.Dispose(disposing);
            }
        }

        // TODO: override a finalizer only if Dispose(bool disposing) above has code to free unmanaged resources.
        ~NaiadSolutionQ1()
        {
            // Do not change this code. Put cleanup code in Dispose(bool disposing) above.
            Dispose(false);
        }

        // This code added to correctly implement the disposable pattern.
        public new void Dispose()
        {
            // Do not change this code. Put cleanup code in Dispose(bool disposing) above.
            Dispose(true);
            // TODO: uncomment the following line if the finalizer is overridden above.
            // GC.SuppressFinalize(this);
        }
        #endregion
    }
    class NaiadSolutionQ2 : NaiadSolution<Task2CommentInfo>
    {
        private Collection<CommentDependentKnows, Epoch> commentDependentKnows;
        private Collection<CommentDependentLabeledUser, Epoch> commentDependentUsers;
        private Collection<CommentDependentLabeledUser, Epoch> commentDependentLabelGraph;
        private Collection<Task2CommentInfo, Epoch> commentComponentSizes;
        private Collection<FriendEdge, Epoch> duplicatedFriends;
        private Subscription subscription;
        public NaiadSolutionQ2(string[] args) : base(args)
        {
        }
        public Collection<CommentDependentLabeledUser, IterationIn<Epoch>> LocalMin(
                    Collection<CommentDependentLabeledUser, IterationIn<Epoch>> users,
                    Collection<CommentDependentKnows, IterationIn<Epoch>> edges)
        {
            return users
                .Join(
                    edges,
                    cdlu => new Pair<string, string>(cdlu.CommentId, cdlu.UserId),
                    cdk => new Pair<string, string>(cdk.CommentId, cdk.UserId1),
                    (cdlu, cdk) => new CommentDependentLabeledUser(cdlu.CommentId, cdk.UserId2, cdlu.Label))
                .Concat(users)
                .Min(cdlu => new Pair<string, string>(cdlu.CommentId, cdlu.UserId), cdlu => cdlu.Label);

        }
        public override string Initial()
        {
            base.Init();

            commentDependentUsers = likesEdges.Select(l => new CommentDependentLabeledUser(l.To, l.From, l.From));
            duplicatedFriends = friendEdges.Select(f => new FriendEdge(f.To, f.From)).Concat(friendEdges);

            commentDependentKnows = likesEdges
                .Select(l => new CommentDependentLabeledUser(l.To, l.From, l.From))
                .Join(duplicatedFriends, cdk => cdk.UserId, f => f.From, (cdk, f) => new CommentDependentKnows(cdk.CommentId, cdk.UserId, f.To))
                .Join(likesEdges, cdk => cdk.UserId2, l => l.From, (cdk, l) => new Pair<CommentDependentKnows, LikesEdge>(cdk, l))
                .Where(p => p.First.CommentId == p.Second.To).Select(p => p.First);

            commentDependentLabelGraph = commentDependentUsers.FixedPoint((lc, x) => LocalMin(x, commentDependentKnows.EnterLoop(lc)));

            commentComponentSizes = commentDependentLabelGraph
                .Min(u => new Pair<string, string>(u.CommentId, u.UserId), u => u.Label)
                .Distinct()
                .GroupBy(
                    cdlu => new Pair<string, string>(cdlu.CommentId, cdlu.Label),
                    (commentAndLabel, cdlus) =>
                        {
                            return new List<Pair<string, int>> { new Pair<string, int>(commentAndLabel.First, cdlus.Count()) };
                        }
                )
                .Sum(ci => ci.First, ci => ci.Second * ci.Second, (commentId, sumValue) => new Pair<string, int>(commentId, sumValue))
                .Join(comments, cs => cs.First, c => c.Id, (cs, c) => new Task2CommentInfo(c.Id, cs.Second, c.Timestamp))
                .Concat(comments.Select(c => new Task2CommentInfo(c.Id, 0, c.Timestamp)))
                .Max(ci => ci.CommentId, ci => ci.LargestComponentSize);

            subscription = commentComponentSizes.Subscribe((componentSizes) =>
            {
                HandleResultUpdates(componentSizes);
            });

            LoadModel(SocialNetwork);
#if (!ONNEXT_CALL_AS_PARAM)
            Sync();
#endif
            return GetResultString();
        }

        public override string Update(ModelChangeSet changes)
        {
#if (ONNEXT_CALL_AS_PARAM)
            bool callOnChanged = Int32.Parse(changes.AbsoluteUri.AbsolutePath.Substring(changes.AbsoluteUri.AbsolutePath.Length - 6, 2)) > 19;
#else
            bool callOnChanged = true;
#endif
            UpdateInputs(changes, callOnChanged);
            if (callOnChanged)
            {
                Sync();
                return GetResultString();
            }
            return "";
        }
        #region IDisposable Support
        private bool disposedValue = false; // To detect redundant calls

        protected override void Dispose(bool disposing)
        {
            if (!disposedValue)
            {
                if (disposing)
                {
                    subscription.Dispose();
                }

                // TODO: free unmanaged resources (unmanaged objects) and override a finalizer below.
                // TODO: set large fields to null.

                disposedValue = true;
                base.Dispose(disposing);
            }
        }

        // TODO: override a finalizer only if Dispose(bool disposing) above has code to free unmanaged resources.
        ~NaiadSolutionQ2()
        {
            // Do not change this code. Put cleanup code in Dispose(bool disposing) above.
            Dispose(false);
        }

        // This code added to correctly implement the disposable pattern.
        public new void Dispose()
        {
            // Do not change this code. Put cleanup code in Dispose(bool disposing) above.
            Dispose(true);
            // TODO: uncomment the following line if the finalizer is overridden above.
            // GC.SuppressFinalize(this);
        }
        #endregion
    }
}
