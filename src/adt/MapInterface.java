package adt;

import java.util.Iterator;

/**
 *
 * @author Ching Wei Hong
 */
public interface MapInterface<K, V> {

    public void put(K key, V value);

    public V get(K key);

    public void remove(K key);
    
    public void clear();

    public boolean containsKey(K key);
    
    public boolean containsValue(V value);

    public int size();

    public boolean isEmpty();
   
    public boolean equals(MapInterface<K, V> map);
    
    public MapInterface<K, V> keySet();
    
    Iterator<Entry<K, V>> getIterator();
}