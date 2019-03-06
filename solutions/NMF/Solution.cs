using NMF.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using TTC2018.LiveContest.SocialNetwork;
using NMF.Models.Changes;
using NMF.Analyses;

namespace TTC2018.LiveContest
{
    public abstract class Solution
    {
        public SocialNetworkRoot SocialNetwork { get; set; }

        public abstract string Initial();

        public abstract string Update(ModelChangeSet changes);
    }

    public class BatchSolutionQ1 : Solution
    {
        private string QueryResult()
        {
            return string.Join("|", (from post in SocialNetwork.Posts
                                     let score = post.Descendants().OfType<Comment>().Sum(c => 10 + c.LikedBy.Count)
                                     orderby (score, post.Timestamp) descending
                                     select post.Id).Take(3));
                                    
        }

        public override string Initial()
        {
            return QueryResult();
        }

        public override string Update(ModelChangeSet changes)
        {
            changes.Apply();
            return QueryResult();
        }
    }

    public class BatchSolutionQ2 : Solution
    {

        public override string Initial()
        {
            return QueryResult();
        }

        public override string Update(ModelChangeSet changes)
        {
            changes.Apply();
            return QueryResult();
        }

        private string QueryResult()
        {
            return string.Join("|", (from comment in SocialNetwork.Descendants().OfType<Comment>()
                                     let layering = Layering<IUser>.CreateLayers(comment.LikedBy, u => u.Friends.Intersect(comment.LikedBy))
                                     let score = layering.Sum(l => Square(l.Count))
                                     orderby (score, comment.Timestamp) descending
                                     select comment.Id).Take(3));
        }

        private int Square(int arg)
        {
            return arg * arg;
        }
    }
}
