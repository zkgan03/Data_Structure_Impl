package adt;

import java.util.Iterator;

/**
 *
 * @author Gan Zhi Ken
 */
public interface SetInterface<T> {

    public boolean add(T element);

    public boolean addAll(SetInterface<T> set);

    public boolean remove(T element);

    public boolean removeAll(SetInterface<T> set);

    public boolean retainAll(SetInterface<T> set);

    public void clear();

    public T get(T element);

    public boolean contains(T element);

    public boolean containsAll(SetInterface<T> set);

    public boolean isEmpty();

    public int size();

    public boolean equal(SetInterface<T> set);

    public T[] toArray();

    public Iterator<T> getIterator();

}
