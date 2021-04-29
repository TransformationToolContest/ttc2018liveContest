use std::ops;
// use crate::ddlog_std::Option;
// use crate::ddlog_std::Group;
use ddlog_std::{
    Option,
    Group,
    option_unwrap_or_default, 
    group_max, 
    group_first, 
    group_key, 
    group_nth, 
    group_count,
    tuple2,
    tuple3
};

// The same as `group_sum()` but with key returned too.
pub fn group_sum_with_key<K: Clone, V: Clone + ops::Add<Output = V>>(g: &Group<K, V>) -> tuple2<K, V> {
    let mut res = group_first(g);
    for v in g.val_iter().skip(1) {
        res = res + v;
    }
    tuple2::from(
        (group_key(g), res)
    )
}

// pub fn top_three<K, V>(g: &Group<K, V>) -> (Option<V>, Option<V>, Option<V>) {
pub fn top_three<K, V: Ord + Default + Clone>(g: &Group<K, V>) -> tuple3<V, V, V> {
    let size = group_count(g);
    let first = group_nth(g, &(size-1));
    let second = group_nth(g, &(size-2));
    let third = group_nth(g, &(size-3));
    tuple3::from(
        (
            option_unwrap_or_default(&first), 
            option_unwrap_or_default(&second), 
            option_unwrap_or_default(&third)
        )
    )
}

// pub fn top_k<K, V: Ord>(g: &Group<K, V>, k: usize) -> Vec<V> {
//     let topk: Vec<V> = g.iter().take(k).collect();
//     topk
// }