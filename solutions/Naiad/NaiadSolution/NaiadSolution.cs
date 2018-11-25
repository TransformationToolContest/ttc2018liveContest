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
            try { 
            return From.GetHashCode() ^ To.GetHashCode();
            } catch (Exception e)
            {
                Console.WriteLine(GetType().ToString());
                return 0;
            }
        }
    }

    class User : ObjectWithId, IEquatable<User>
    {
        public User(string id) : base(id) { }
        public bool Equals(User other)
        {
            return Equals(this, other);
        }
    }
    class Post : ObjectWithId, IEquatable<Post>
    {
        public DateTime Timestamp;
        public Post(string id, DateTime timestamp) : base(id) {
            Timestamp = timestamp;
        }
        public bool Equals(Post other)
        {
            return Equals(this, other);
        }
    }
    class Comment : ObjectWithId, IEquatable<Comment>
    {
        public Comment(string id) : base(id) { }
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
    class FriendsEdge : Edge, IEquatable<FriendsEdge>
    {
        FriendsEdge(string from, string to) : base(from, to) { }
        public bool Equals(FriendsEdge other)
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

        protected InputCollection<Post> posts;
        protected InputCollection<Comment> comments;
        protected InputCollection<CommentedEdge> commentedEdges;
        protected InputCollection<LikesEdge> likesEdges;
        protected OneOffComputation computation;
        public void Dispose()
        {
            computation.Dispose();
        }

        protected static void AddCommentedsAndLikesFromPost(IPost post, ICollection<Comment> comments, ICollection<CommentedEdge> commenteds, ICollection<LikesEdge> likes)
        {
            foreach (var c in post.Comments)
            {
                commenteds.Add(new CommentedEdge(post.Id, c.Id));
                AddCommentedsAndLikesFromComment(c, comments, commenteds, likes);
            }

        }
        protected static void AddCommentedsAndLikesFromComment(IComment comment, ICollection<Comment> comments, ICollection<CommentedEdge> commenteds, ICollection<LikesEdge> likes)
        {

            comments.Add(new Comment(comment.Id));
            foreach (var user in comment.LikedBy)
            {
                likes.Add(new LikesEdge(user.Id, comment.Id));

            }
            foreach (var c in comment.Comments)
            {
                commenteds.Add(new CommentedEdge(comment.Id, c.Id));
                AddCommentedsAndLikesFromComment(c, comments, commenteds, likes);
            }

        }
        ~NaiadSolution()
        {
            Dispose();
        }
    }

    class NaiadSolutionQ1 : NaiadSolution
    {
        //protected Collection<CommentedEdge> startigCommentedEdges;

        public override string Initial()
        {
            computation = NewComputation.FromConfig(new Configuration());
            posts = computation.NewInputCollection<Post>();
            comments = computation.NewInputCollection<Comment>();
            commentedEdges = computation.NewInputCollection<CommentedEdge>();
            likesEdges = computation.NewInputCollection<LikesEdge>();

            /* initial   post -> comment   edges */
            var startigCommentedEdges = posts.Join(commentedEdges, p => p.Id, e => e.From, p => p.Id, e => e.To, (pId, from, to) => new Edge(from, to));

            /* pair ->1..* comment   edges*/
            var reachedComments = startigCommentedEdges.FixedPoint((lc, x) => x.Join(commentedEdges.EnterLoop(lc),
                                                               edge => edge.To,
                                                               edge => edge.From,
                                                               (e1, e2) => new Edge(e1.From, e2.To))
                                                           .Concat(x)
                                                           .Distinct());
            /* comment, likeCount pairs */
            var commentLikes = comments.CoGroupBy(
                likesEdges,
                c => c.Id,
                l => l.To,
                (cId, cs, ls) => new List<Pair<string, int>> { new Pair<string, int>(cId, ls.Count()) });

            var result = reachedComments
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
                .Join(posts, s => s.First, p => p.Id, s=> s.Second, p => p.Timestamp, (pId, count, timestamp) => new EquatableTriple<string, int, DateTime>(pId, count, timestamp));

            result.Subscribe(x =>
                {
                    Console.Error.WriteLine("AAAASD");
                    foreach (var r in x.OrderByDescending(r => r.record.Third).OrderByDescending(r => r.record.Second).Take(3))
                        Console.Error.WriteLine(r.weight + " " + r.record.First + " point: " + r.record.Second);
                });


            var rawPosts = new List<Post>();
            var rawComments = new List<Comment>();
            var rawLikes = new List<LikesEdge>();
            var rawCommenteds = new List<CommentedEdge>();
            foreach (var p in SocialNetwork.Posts)
            {
                rawPosts.Add(new Post(p.Id, p.Timestamp));
                AddCommentedsAndLikesFromPost(p, rawComments, rawCommenteds, rawLikes);
            }

            computation.Activate();
            posts.OnNext(rawPosts);
            posts.OnCompleted();
            comments.OnNext(rawComments);
            comments.OnCompleted();
            commentedEdges.OnNext(rawCommenteds);
            commentedEdges.OnCompleted();
            likesEdges.OnNext(rawLikes);
            likesEdges.OnCompleted();
            computation.Sync(1);
            computation.Join();

            //throw new NotImplementedException();
            return "";

        }

        public override string Update(ModelChangeSet changes)
        {
            throw new NotImplementedException();
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
