use std::ops;
use ddlog_std::{
    Option,
    Group,
    option_unwrap_or_default, 
    group_max, group_first, 
    group_key, 
    group_nth, 
    group_count_distinct,
    tuple2,
    tuple3
};

pub fn top_three<K, V: Ord + Default + Clone>(g: &Group<K, V>) -> tuple3<V, V, V> {
    let size = group_count_distinct(g);
    let first = group_nth(g, &(size-1));
    let second = group_nth(g, &(size-2));
    let third = group_nth(g, &(size-3));
    tuple3::from(
        (option_unwrap_or_default(first), option_unwrap_or_default(second), option_unwrap_or_default(third))
    )
}