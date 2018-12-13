using System;
using System.Collections.Generic;
using System.Text;
using NMF.Models.Changes;
using NMF.Models;
using NMF.Expressions;
using NMF.Expressions.Linq;
using System.Linq;
using TTC2018.LiveContest.SocialNetwork;

namespace TTC2018.LiveContest
{
    public class IncrementalSolutionQ1 : Solution
    {
        private INotifyValue<KeyValuePair<IPost, (int, DateTime)>[]> query;

        protected string QueryResult()
        {
            return string.Join("|", query.Value.Select(tuple => tuple.Key.Id));
        }

        public override string Initial()
        {
            query = Observable.Expression(() =>
                SocialNetwork.Posts.TopX(3, post => ValueTuple.Create(post.Descendants().OfType<Comment>().Sum(c => 10 + c.LikedBy.Count), post.Timestamp))
            );
            return QueryResult();
        }

        public override string Update(ModelChangeSet changes)
        {
            changes.Apply();
            return QueryResult();
        }
    }

    public class TransactionalSolutionQ1 : IncrementalSolutionQ1
    {
        public override string Update(ModelChangeSet changes)
        {
            ExecutionEngine.Current.BeginTransaction();
            changes.Apply();
            ExecutionEngine.Current.CommitTransaction();
            return QueryResult();
        }
    }

    public class ParallelSolutionQ1 : TransactionalSolutionQ1
    {
        public ParallelSolutionQ1()
        {
            ExecutionEngine.Current = new ParallelExecutionEngine();
        }
    }


    public class IncrementalSolutionQ2 : Solution
    {
        private INotifyValue<KeyValuePair<IComment, (int, DateTime)>[]> query;

        protected string QueryResult()
        {
            return string.Join("|", query.Value.Select(tuple => tuple.Key.Id));
        }

        public override string Initial()
        {
            Func<IComment, Func<IUser, IEnumerableExpression<IUser>>> friendsBuilder = c => (u => u.Friends.Intersect(c.LikedBy));
            query = Observable.Expression(() =>
                SocialNetwork.Descendants().OfType<IComment>().TopX(3, comment => ValueTuple.Create(
                    ConnectedComponents<IUser>.Create(comment.LikedBy, friendsBuilder(comment))
                                              .Sum(group => Squared(group.Count())),
                    comment.Timestamp
                ))
            );
            return QueryResult();
        }

        private static int Squared(int arg)
        {
            return arg * arg;
        }

        public override string Update(ModelChangeSet changes)
        {
            changes.Apply();
            return QueryResult();
        }
    }

    public class TransactionalSolutionQ2 : IncrementalSolutionQ2
    {
        public override string Update(ModelChangeSet changes)
        {
            ExecutionEngine.Current.BeginTransaction();
            changes.Apply();
            ExecutionEngine.Current.CommitTransaction();
            return QueryResult();
        }
    }

    public class ParallelSolutionQ2 : TransactionalSolutionQ2
    {
        public ParallelSolutionQ2()
        {
            ExecutionEngine.Current = new ParallelExecutionEngine();
        }
    }
}
