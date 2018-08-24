package com.github.xenteros.hash.collections.hashmap;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyRealHashMap<K, V> implements Map<K, V> {

    private static int INITIAL_SIZE = 16;
    private static int INCREASE_SIZE_RATIO = 2;
    private static int MAX_SIZE_TO_BUCKETS_COUNT_RATIO = 4;
    private int currentSize = 0;

    private Object[] table;

    public MyRealHashMap() {
        this.table = new Object[INITIAL_SIZE];
    }


    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int hash = key.hashCode();
        int bucket = Math.abs(hash % table.length);

        if (table[bucket] == null) {
            return false;
        }

        Set<Entry<K, V>> bucketSet = (Set<Entry<K, V>>) table[bucket];
        return bucketSet.stream()
                .anyMatch(e -> e.getKey().equals(key));
    }

    @Override
    public boolean containsValue(Object value) {
        return Arrays.stream(this.table)
                .filter(Objects::nonNull)
                .flatMap(bucket -> ((Set<MyEntry<K, V>>) bucket).stream())
                .anyMatch(e -> e.getValue().equals(value));
    }

    @Override
    public V get(Object key) {
        int hash = key.hashCode();
        int bucket = Math.abs(hash % table.length);

        if (table[bucket] == null) {
            return null;
        }
        Set<MyEntry<K, V>> bucketSet = (Set<MyEntry<K, V>>) table[bucket];
        return bucketSet.stream()
                .filter(e -> e.getKey().equals(key))
                .map(MyEntry::getValue)
                .findFirst().orElse(null);
    }

    @Override
    public V put(K key, V value) {


        int hash = key.hashCode();
        int bucket = Math.abs(hash % table.length);

        if (table[bucket] == null) {
            table[bucket] = new HashSet<MyEntry<K, V>>();
        }

        Set<MyEntry<K, V>> bucketSet = (Set<MyEntry<K, V>>) table[bucket];
        MyEntry<K, V> found = null;
        for (MyEntry<K, V> kvMyEntry : bucketSet) {
            if (kvMyEntry.getKey().equals(key)) {
                found = kvMyEntry;
            }
        }

        if (found == null) {
            bucketSet.add(new MyEntry<>(key, value));
            this.currentSize += 1;
            return value;
        } else {
            V previous = found.getValue();
            found.setValue(value);
            return previous;
        }
    }

    @Override
    public V remove(Object key) {
        int hash = key.hashCode();
        int bucket = Math.abs(hash % table.length);

        if (table[bucket] == null) {
            return null;
        }
        Set<Entry<K, V>> bucketSet = (Set<Entry<K, V>>) table[bucket];
        V v = get(key);
        bucketSet.removeIf(e -> e.getKey().equals(key));
        return v;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach((k, v) -> this.put(k, v));
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.table.length; i++) {
            this.table[i] = null;
        }
        this.currentSize = 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }

    @Override
    public V replace(K key, V value) {
        return null;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append(Arrays.stream(table)
                .filter(Objects::nonNull)
                .flatMap(o -> ((Set<MyEntry<K, V>>) o).stream())
                .map(MyEntry::toString)
                .collect(Collectors.joining(",")));
        sb.append("]");
        return sb.toString();
    }

    private void rearrange() {
        List<MyEntry<K, V>> elements = new ArrayList<>(currentSize);
        Arrays.stream(table)
                .filter(Objects::nonNull)
                .forEach(bucket -> elements.addAll((Set<MyEntry<K, V>>) bucket));

        this.table = new Object[this.table.length * INCREASE_SIZE_RATIO];
        this.currentSize = 0;
        elements.forEach(this::put);
    }

    private void put(MyEntry<K, V> entry) {
        put(entry.getKey(), entry.getValue());
    }

}
