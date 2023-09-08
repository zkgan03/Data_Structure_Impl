package adt;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Ching Wei Hong
 * @param <K>
 * @param <V>
 */
public class HashMap<K extends Comparable<K>, V> implements MapInterface<K, V>, Serializable {

    private static final int DEFAULT_CAPACITY = 32;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private double loadFactor;
    private Node<K, V>[] buckets;
    private int size;

    public HashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashMap(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public HashMap(int capacity, double loadFactor) {
        buckets = new Node[capacity];
        size = 0;
        this.loadFactor = loadFactor;
    }

    private int getIndex(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % buckets.length;
    }

    private void resize() {
        int newCapacity = buckets.length * 2;
        Node<K, V>[] newBuckets = new Node[newCapacity];

        for (Node<K, V> node : buckets) {
            Node<K, V> current = node;
            while (current != null) {
                int newIndex = Math.abs(current.key.hashCode()) % newCapacity;
                Node<K, V> newNode = new Node<>(current.key, current.value);
                newNode.next = newBuckets[newIndex];
                newBuckets[newIndex] = newNode;
                current = current.next;
            }
        }

        buckets = newBuckets;
    }


    @Override
    public void put(K key, V value) {
        int index = getIndex(key);
        Node<K, V> newNode = new Node<>(key, value);

        if (size > buckets.length * loadFactor) {
            resize(); // Resize the array if load factor is exceeded
        }

        if (buckets[index] == null) {
            buckets[index] = newNode;
        } else {
            Node<K, V> current = buckets[index];
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value; // Update value if key already exists
                    return;
                }
                current = current.next;
            }
            if (current.key.equals(key)) {
                current.value = value; // Update value if key already exists
            } else {
                current.next = newNode;
            }
        }

        size++;
    }

    @Override
    public V get(K key) {
        int index = getIndex(key);
        Node<K, V> current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public void remove(K key) {
        int index = getIndex(key);
        Node<K, V> current = buckets[index];
        Node<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    @Override
    public void clear() {
        // Clear all buckets by setting them to null
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }

        // Reset the size to 0
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int index = getIndex(key);
        Node<K, V> current = buckets[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for (Node<K, V> current : buckets) {
            while (current != null) {
                if (current.value.equals(value)) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean equals(MapInterface<K, V> map) {
        if (this == map) {
            return true;
        }
        if (map == null || getClass() != map.getClass()) {
            return false;
        }

        @SuppressWarnings("unchecked")
        HashMap<K, V> otherMap = (HashMap<K, V>) map;

        if (size() != otherMap.size()) {
            return false;
        }

        for (Node<K, V> current : buckets) {
            while (current != null) {
                K key = current.key;
                V value = current.value;
                V otherValue = otherMap.get(key);

                if (otherValue == null || !value.equals(otherValue)) {
                    return false;
                }

                current = current.next;
            }
        }
        return true;
    }

    @Override
    public MapInterface<K, V> keySet() {
        MapInterface<K, V> keyMap = new HashMap<>();

        for (Node<K, V> current : buckets) {
            while (current != null) {
                keyMap.put(current.key, current.value);
                current = current.next;
            }
        }

        return keyMap;
    }

    @Override
    public Iterator<Entry<K, V>> getIterator() {
        return new HashMapIterator();
    }

    private class Node<K, V> implements Serializable {

        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private class HashMapIterator implements Iterator<Entry<K, V>> {

        private int currentIndex = 0;
        private Node<K, V> current = buckets[currentIndex];

        @Override
        public boolean hasNext() {
            if (current != null && current.next != null) {
                return true;
            }
            for (int i = currentIndex + 1; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException(); // Handle the case when there are no more elements
            }

            if (current != null && current.next != null) {
                current = current.next;
            } else {
                do {
                    current = buckets[++currentIndex];
                } while (currentIndex < buckets.length && buckets[currentIndex] == null);
            }

            if (current != null) {
                return new Entry<>(current.key, current.value);
            } else {
                throw new NoSuchElementException(); // Handle the case when current is null
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Node<K, V> current : buckets) {
            while (current != null) {
                sb.append(current.value).append("\n");
                current = current.next;
            }
        }

        return sb.toString();
    }

}