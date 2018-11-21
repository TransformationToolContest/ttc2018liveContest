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
    class ObjectWithId : IEquatable<ObjectWithId>
    {
        public string Id;

        public bool Equals(ObjectWithId other)
        {
            return Id == other.Id;
        }
    }
    class Edge : IEquatable<Edge>
    {
        public string From;
        public string To;

        public bool Equals(Edge other)
        {
            return From == other.From && To == other.To;
        }
    }
    class JoinableEdge : Edge, IEquatable<JoinableEdge>
    {
        public JoinableEdge(string from, string to)
        {
            From = from;
            To = to;
            Length = 1;
        }
        public JoinableEdge(string from, string to, int length)
        {
            From = from;
            To = to;
            Length = length;
        }
        public int Length;
        public bool Equals(JoinableEdge other)
        {
            return Equals(this, other);
        }
    }

    class User : ObjectWithId, IEquatable<User>
    {

        public bool Equals(User other)
        {
            return Equals(this, other);
        }
    }
    class Post : ObjectWithId, IEquatable<Post>
    {

        public bool Equals(Post other)
        {
            return Equals(this, other);
        }
    }
    class Comment : ObjectWithId, IEquatable<Comment>
    {

        public bool Equals(Comment other)
        {
            return Equals(this, other);
        }
    }

    class CommentedEdge : JoinableEdge, IEquatable<CommentedEdge>
    {
        public CommentedEdge(string from, string to) : base(from, to) { }
        public CommentedEdge(string from, string to, int length) : base(from, to, length) { }
        public bool Equals(CommentedEdge other)
        {
            return Equals(this, other);
        }
    }
    class LikesEdge : JoinableEdge, IEquatable<LikesEdge>
    {
        LikesEdge(string from, string to) : base(from, to) { }
        LikesEdge(string from, string to, int length) : base(from, to, length) { }
        public bool Equals(LikesEdge other)
        {
            return Equals(this, other);
        }
    }
    class FriendsEdge : JoinableEdge, IEquatable<FriendsEdge>
    {
        FriendsEdge(string from, string to) : base(from, to) { }
        FriendsEdge(string from, string to, int length) : base(from, to, length) { }
        public bool Equals(FriendsEdge other)
        {
            return Equals(this, other);
        }
    }

    class NaiadSolutionQ1 : Solution
    {

        public override string Initial()
        {
            var args = new string[0];
            using (var computation = NewComputation.FromArgs(ref args))
            {
                var usersInput = computation.NewInputCollection<User>();
                var postsInput = computation.NewInputCollection<Post>();
                var commentedInput = computation.NewInputCollection<CommentedEdge>();
                var likesInput = computation.NewInputCollection<LikesEdge>();

                var reachedComments = commentedInput.GeneralFixedPoint((lc, x) => x.Join(commentedInput.EnterLoop(lc),
                                                                   edge => edge.To, 
                                                                   edge => edge.From, 
                                                                   (e1, e2) => new CommentedEdge(e1.From, e2.To, e1.Length + e2.Length ))
                                                               .Concat(x)
                                                               .Consolidate(), 
                                                             edge => edge.Length, 
                                                             edge => edge.From, 
                                                             3);
                var output = reachedComments.Consolidate().Subscribe(x =>
                {
                    Console.Error.WriteLine("AAAASD");
                    foreach (var result in x.OrderBy(r => r.weight))
                        Console.Error.WriteLine(result.weight + "    " + result.record.From + " ---(" + result.record.Length + ")---> " + result.record.To);
                });


                computation.Activate();
                CommentedEdge[] edges = {
                    new CommentedEdge("1", "2"),
                    new CommentedEdge("2", "3"),
                    new CommentedEdge("3", "4"),
                    new CommentedEdge("4", "5"),
                    new CommentedEdge("5", "6")
                };
                commentedInput.OnNext(edges);
                output.Sync(0);
                commentedInput.OnCompleted();
                computation.Join();
            }
            throw new NotImplementedException();

        }

        public override string Update(ModelChangeSet changes)
        {
            throw new NotImplementedException();
        }
    }
    class NaiadSolutionQ2 : Solution
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
