package com.github.xenteros.hash.collections.hashmap;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class HashMap<K, V> {

    private static int INITIAL_SIZE = 16;
    private static int INCREASE_SIZE_RATIO = 2;
    private static int MAX_SIZE_TO_BUCKETS_COUNT_RATIO = 4;

    private Object[] table;

    public HashMap() {
        this.table = new Object[INITIAL_SIZE];
    }

    public V get(K key) {
        return getEntry(key)
                .map(MyEntry::getValue)
                .orElse(null);
    }


    public boolean containsKey(K key) {
        int hash = key.hashCode();
        int bucket = Math.abs(hash % table.length);

        if (table[bucket] == null) {
            return false;
        }

        Set<MyEntry<K,V>> bucketSet = (Set<MyEntry<K, V>>) table[bucket];
        return bucketSet.stream()
                .anyMatch(e -> e.getKey().equals(key));
    }

    public void add(K key, V value) {
        int hash = key.hashCode();
        int bucket = Math.abs(hash % table.length);

        if (table[bucket] == null) {
            table[bucket] = new HashSet<MyEntry<K,V>>();
        }

        Set<MyEntry<K,V>> bucketSet = (Set<MyEntry<K, V>>) table[bucket];

        if (this.containsKey(key)) {
            getEntry(key).ifPresent(entry -> {
                entry.setValue(value);
            });
        } else {
            bucketSet.add(new MyEntry<>(key, value));
        }
    }

    private Optional<MyEntry<K, V>> getEntry(K key) {
        int hash = key.hashCode();
        int bucket = Math.abs(hash % table.length);

        if (table[bucket] == null) {
            return Optional.empty();
        }
        Set<MyEntry<K,V>> bucketSet = (Set<MyEntry<K, V>>) table[bucket];
        return bucketSet.stream()
                .filter(entry -> entry.getKey().equals(key))
                .findFirst();
    }
}








