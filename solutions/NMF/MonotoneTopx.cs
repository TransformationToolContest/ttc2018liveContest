using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Text;
using NMF.Expressions;
using NMF.Expressions.Linq;

namespace TTC2018.LiveContest
{
    public static class MonotoneExtensions
    {

        [ObservableProxy(typeof(MonotoneTopX<,>), "Create")]
        public static KeyValuePair<T, TKey>[] MonotoneTopX<T, TKey>(this INotifyEnumerable<T> source, int x, Expression<Func<T, TKey>> keySelector)
        {
            return source.TopX(x, keySelector);
        }

        [ObservableProxy(typeof(MonotoneTopX<,>), "CreateFromExpression")]
        public static KeyValuePair<T, TKey>[] MonotoneTopX<T, TKey>(this IEnumerableExpression<T> source, int x, Expression<Func<T, TKey>> keySelector)
        {
            return source.TopX(x, keySelector);
        }
    }

    internal class MonotoneTopX<T, TKey> : NotifyExpression<KeyValuePair<T, TKey>[]>
    {
        public static INotifyValue<KeyValuePair<T, TKey>[]> Create(INotifyEnumerable<T> source, int x, Expression<Func<T, TKey>> keySelector)
        {
            var result = new MonotoneTopX<T, TKey>(source, keySelector, x, null);
            result.Successors.SetDummy();
            return result;
        }
        public static INotifyValue<KeyValuePair<T, TKey>[]> CreateFromExpression(IEnumerableExpression<T> source, int x, Expression<Func<T, TKey>> keySelector)
        {
            return Create(source.AsNotifiable(), x, keySelector);
        }

        private readonly INotifyEnumerable<T> _source;
        private readonly ObservingFunc<T, TKey> _keySelector;
        private readonly Dictionary<T, INotifyValue<TKey>> _keyDictionary = new Dictionary<T, INotifyValue<TKey>>();
        private readonly Dictionary<INotifiable, T> _keyDictionaryInverse = new Dictionary<INotifiable, T>();
        private readonly int _x;
        private readonly List<T> _items = new List<T>();
        private readonly List<TKey> _keys = new List<TKey>();
        private readonly IComparer<TKey> _keyComparer;

        public MonotoneTopX(INotifyEnumerable<T> source, ObservingFunc<T, TKey> keySelector, int x, IComparer<TKey> keyComparer)
        {
            _source = source;
            _keySelector = keySelector;
            _x = x;
            _keyComparer = keyComparer ?? new ReverseComparer<TKey>( Comparer<TKey>.Default );

            foreach (var item in _source)
            {
                AddItem(item);
            }
        }

        public override bool IsParameterFree => true;

        public override IEnumerable<INotifiable> Dependencies
        {
            get
            {
                yield return _source;
                foreach (var item in _keyDictionary.Values)
                {
                    yield return item;
                }
            }
        }

        protected override INotifyExpression<KeyValuePair<T, TKey>[]> ApplyParametersCore(IDictionary<string, object> parameters, IDictionary<INotifiable, INotifiable> trace)
        {
            return this;
        }

        public override INotificationResult Notify(IList<INotificationResult> sources)
        {
            var anyChanges = false;
            foreach (var change in sources)
            {
                if (change.Source == _source)
                {
                    if (change is ICollectionChangedNotificationResult<T> collectionChange)
                    {
                        anyChanges |= HandleSourceCollectionChange(collectionChange);
                    }
                }
                else if (change is IValueChangedNotificationResult<TKey> valueChange)
                {
                    var oldIndex = _keys.BinarySearch(valueChange.OldValue, _keyComparer);
                    if (oldIndex >= 0)
                    {
                        _keys.RemoveAt(oldIndex);
                        _items.RemoveAt(oldIndex);
                    }
                    var index = _keys.BinarySearch(valueChange.NewValue, _keyComparer);
                    if (index < 0)
                    {
                        index = ~index;
                    }
                    if (index < _x)
                    {
                        anyChanges = true;
                        _keys.Insert(index, valueChange.NewValue);
                        _items.Insert(index, _keyDictionaryInverse[valueChange.Source]);
                    }
                }
            }
            if (!anyChanges)
            {
                return UnchangedNotificationResult.Instance;
            }
            while (_items.Count > _x)
            {
                _items.RemoveAt(_items.Count - 1);
                _keys.RemoveAt(_keys.Count - 1);
            }
            return base.Notify(sources);
        }

        private bool HandleSourceCollectionChange(ICollectionChangedNotificationResult<T> collectionChange)
        {
            var anyChanges = false;
            if (collectionChange.IsReset)
            {
                throw new NotSupportedException();
            }
            if (collectionChange.RemovedItems != null)
            {
                foreach (var item in collectionChange.RemovedItems)
                {
                    if (_keyDictionary.TryGetValue(item, out var notifiable))
                    {
                        var index = _keys.BinarySearch(notifiable.Value, _keyComparer);
                        if (index >= 0)
                        {
                            _keys.RemoveAt(index);
                            _items.RemoveAt(index);
                            anyChanges = true;
                        }
                        _keyDictionary.Remove(item);
                        _keyDictionaryInverse.Remove(notifiable);
                        notifiable.Successors.Unset(this);
                    }
                }
            }
            if (collectionChange.AddedItems != null)
            {
                foreach (var item in collectionChange.AddedItems)
                {
                    anyChanges |= AddItem(item);
                }
            }
            return anyChanges;
        }

        private bool AddItem(T item)
        {
            var notifiable = _keySelector.Observe(item);
            _keyDictionary.Add(item, notifiable);
            _keyDictionaryInverse.Add(notifiable, item);
            if (IsAttached)
            {
                notifiable.Successors.Set(this);
            }
            var index = _keys.BinarySearch(notifiable.Value, _keyComparer);
            if (index < 0)
            {
                index = ~index;
            }
            if (index < _x)
            {
                _keys.Insert(index, notifiable.Value);
                _items.Insert(index, item);
                if (_keys.Count > _x)
                {
                    _keys.RemoveAt(_keys.Count - 1);
                    _items.RemoveAt(_items.Count - 1);
                }
                return true;
            }

            return false;
        }

        protected override KeyValuePair<T, TKey>[] GetValue()
        {
            var returnVal = new KeyValuePair<T, TKey>[_items.Count];
            for (int i = 0; i < _items.Count; i++)
            {
                returnVal[i] = new KeyValuePair<T, TKey>(_items[i], _keys[i]);
            }
            return returnVal;
        }
    }
}
