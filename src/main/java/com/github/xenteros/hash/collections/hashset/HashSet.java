package com.github.xenteros.hash.collections.hashset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @since 1.8
 */
public class HashSet<T> {

    private static int INITIAL_SIZE = 16;
    private static int INCREASE_SIZE_RATIO = 2;
    private static int MAX_SIZE_TO_BUCKETS_COUNT_RATIO = 4;

    private int currentSize = 0;

    private Object[] table = new Object[INITIAL_SIZE];

    public void add(T t) {

        if (currentSize >= table.length * MAX_SIZE_TO_BUCKETS_COUNT_RATIO) {
            rearrange();
        }

        int hash = t.hashCode();
        int bucket = Math.abs(hash % table.length);

        if (table[bucket] == null) {
            table[bucket] = new ArrayList<T>();
        }

        List<T> bucketList = (List<T>) table[bucket];
        for (T t1 : bucketList) {
            if (t1.equals(t)) {
                return;
            }
        }
        bucketList.add(t);
        currentSize += 1;
    }

    public boolean contains(T t) {
        int hash = t.hashCode();
        int bucket = Math.abs(hash % table.length);

        if (table[bucket] == null) {
            return false;
        }
        List<T> bucketList = (List<T>) table[bucket];
        for (T t1 : bucketList) {
            if (t1.equals(t)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append(Arrays.stream(table)
                .filter(Objects::nonNull)
                .flatMap(o -> ((List<T>) o).stream())
                .map(T::toString)
                .collect(Collectors.joining(",")));
        sb.append("]");
        return sb.toString();
    }

    private void rearrange() {
        List<T> elements = new ArrayList<>(currentSize);
        for (Object o : table) {
            if (o != null) {
                List<T> bucket = (List<T>) o;
                elements.addAll(bucket);
            }
        }

        this.table = new Object[this.table.length * INCREASE_SIZE_RATIO];
        this.currentSize = 0;
        elements.forEach(this::add);
    }
}
