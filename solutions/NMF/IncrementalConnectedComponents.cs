using System;
using System.Collections.Generic;
using System.Text;
using NMF.Expressions.Linq;
using NMF.Expressions;
using System.Linq;
using System.Linq.Expressions;
using System.Collections;
using NMF.Analyses;
using System.Collections.Specialized;

namespace TTC2018.LiveContest
{
    public class ConnectedComponents<T> : IEnumerableExpression<IEnumerable<T>>
    {
        private IList<ICollection<T>> components;
        private IEnumerableExpression<T> nodes;
        private Func<T, IEnumerableExpression<T>> edges;

        private ConnectedComponents(IEnumerableExpression<T> nodes, Func<T, IEnumerableExpression<T>> edges)
        {
            this.nodes = nodes;
            this.edges = edges;
        }

        public static IEnumerableExpression<IEnumerable<T>> Create(IEnumerableExpression<T> nodes, Func<T, IEnumerableExpression<T>> edges)
        {
            return new ConnectedComponents<T>(nodes, edges);
        }

        public INotifyEnumerable<IEnumerable<T>> AsNotifiable()
        {
            return new IncrementalComponents(nodes.AsNotifiable(), edges);
        }

        public IEnumerator<IEnumerable<T>> GetEnumerator()
        {
            if (components == null)
            {
                components = Layering<T>.CreateLayers(nodes, edges);
            }
            return components.GetEnumerator();
        }

        INotifyEnumerable IEnumerableExpression.AsNotifiable()
        {
            return AsNotifiable();
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        private class IncrementalComponents : ObservableEnumerable<IEnumerable<T>>
        {
            private INotifyEnumerable<T> nodes;
            private Dictionary<T, INotifyEnumerable<T>> edgeCache = new Dictionary<T, INotifyEnumerable<T>>();
            private Dictionary<INotifiable, T> edgeCacheInverse = new Dictionary<INotifiable, T>();
            private Func<T, IEnumerableExpression<T>> edges;

            private Dictionary<T, IEnumerable<T>> components = new Dictionary<T, IEnumerable<T>>();

            public IncrementalComponents(INotifyEnumerable<T> nodes, Func<T, IEnumerableExpression<T>> edges)
            {
                this.nodes = nodes;
                this.edges = edges;
            }

            protected override void OnAttach()
            {
                nodes.Successors.Set(this);
                foreach (var item in edgeCache.Values)
                {
                    item.Successors.Set(this);
                }
                Recompute();
            }

            protected override void OnDetach()
            {
                nodes.Successors.Unset(this);
                foreach (var item in edgeCache.Values)
                {
                    item.Successors.Unset(this);
                }
            }

            private void Recompute()
            {
                components.Clear();
                var layers = Layering<T>.CreateLayers(nodes, GetEdges);
                foreach (var layer in layers)
                {
                    foreach (var item in layer)
                    {
                        components.Add(item, layer);
                    }
                }
                OnCleared();
            }

            private IEnumerable<T> GetEdges(T node)
            {
                INotifyEnumerable<T> cachedEdges;
                if (!edgeCache.TryGetValue(node, out cachedEdges))
                {
                    cachedEdges = edges(node).AsNotifiable();
                    edgeCache.Add(node, cachedEdges);
                    cachedEdges.Successors.Set(this);
                    edgeCacheInverse.Add(cachedEdges, node);
                }
                return cachedEdges;
            }

            public override IEnumerable<INotifiable> Dependencies
            {
                get
                {
                    return edgeCache.Values.Concat(Enumerable.Repeat(nodes, 1));
                }
            }

            public override IEnumerator<IEnumerable<T>> GetEnumerator()
            {
                return components.Values.Distinct().GetEnumerator();
            }

            public override INotificationResult Notify(IList<INotificationResult> sources)
            {
                if (ShouldRecompute(sources))
                {
                    Recompute();
                    return CollectionChangedNotificationResult<IEnumerable<T>>.Create(this, isReset: true);
                }
                else
                {
                    return UnchangedNotificationResult.Instance;
                }
            }

            private bool ShouldRecompute(IList<INotificationResult> sources)
            {
                foreach (var change in sources)
                {
                    if (change.Source == nodes)
                    {
                        var nodesChange = change as ICollectionChangedNotificationResult<T>;
                        if (nodesChange.IsReset)
                        {
                            return true;
                        }
                        else
                        {
                            if (nodesChange.RemovedItems != null && nodesChange.RemovedItems.Count > 0)
                            {
                                return true;
                            }
                            if (nodesChange.AddedItems != null)
                            {
                                foreach (var added in nodesChange.AddedItems)
                                {
                                    if (!edgeCache.ContainsKey(added))
                                    {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        var source = edgeCacheInverse[change.Source];
                        var targetChange = change as ICollectionChangedNotificationResult<T>;
                        if (!targetChange.IsReset && components.TryGetValue(source, out IEnumerable<T> sourceComponent))
                        {
                            foreach (var removed in targetChange.RemovedItems)
                            {
                                if (components.TryGetValue(removed, out IEnumerable<T> removedComponent) && removedComponent == sourceComponent)
                                {
                                    return true;
                                }
                            }
                            foreach (var added in targetChange.AddedItems)
                            {
                                if (components.TryGetValue(added, out IEnumerable<T> addedComponent) && addedComponent != sourceComponent)
                                {
                                    return true;
                                }
                            }
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
                return false;
            }
        }
    }
}
