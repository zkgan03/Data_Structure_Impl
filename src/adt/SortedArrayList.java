package adt;

import java.io.Serializable;
import java.util.Iterator;

/**
 * MODIFIED
 *
 * SortedArrayList - Implements the ADT Sorted List using an array.
 * - Note: Some methods are not implemented yet and have been left as practical exercises
 */
public class SortedArrayList<T extends Comparable<T>> implements SortedListInterface<T>, Serializable {

    private T[] array;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 25;

    public SortedArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public SortedArrayList(int initialCapacity) {
        numberOfEntries = 0;
        array = (T[]) new Comparable[initialCapacity];
    }

    @Override
    public boolean add(T newEntry) {

        if (isArrayFull()) {
            doubleArray();
        }

        int i = 0;

        while (i < numberOfEntries && newEntry.compareTo(array[i]) > 0) {
            i++;
        }

        makeRoom(i + 1);

        array[i] = newEntry;
        numberOfEntries++;

        return true;
    }

    @Override
    public boolean addAll(SortedListInterface newEntrys) {
        Iterator i = newEntrys.getIterator();
        while (i.hasNext()) {
            add((T) i.next());
        }
        return true;
    }

    @Override
    public boolean remove(T anEntry) {

        int index = 0;

        while (index < numberOfEntries) {
            if (array[index].compareTo(anEntry) >= 0) {
                break;
            } else {
                index++;
            }
        }
        if (index < numberOfEntries && array[index].compareTo(anEntry) == 0) {
            removeGap(index + 1);
            numberOfEntries--;
            return true;
        }

        return false;
    }

    @Override
    public void clear() {
        numberOfEntries = 0;
    }

    @Override
    public T get(T anEntry) {
        for (int index = 0; index < numberOfEntries; index++) {
            if (anEntry.equals(array[index])) {
                return array[index];
            }
        }
        return null;
    }

     @Override
    public boolean contains(T anEntry){
        return get(anEntry) != null;
    }

    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    public String toString() {
        String outputStr = "";
        for (int index = 0; index < numberOfEntries; ++index) {
            outputStr += array[index] + "\n";
        }

        return outputStr;
    }

    private boolean isArrayFull() {
        return numberOfEntries == array.length;
    }

    private void doubleArray() {
        T[] oldList = array;
        int oldSize = oldList.length;

        array = (T[]) new Comparable[2 * oldSize];

        for (int index = 0; index < oldSize; index++) {
            array[index] = oldList[index];
        }
    }

    private void makeRoom(int newPosition) {
        int newIndex = newPosition - 1;
        int lastIndex = numberOfEntries - 1;

        for (int index = lastIndex; index >= newIndex; index--) {
            array[index + 1] = array[index];
        }
    }

    private void removeGap(int givenPosition) {
        int removedIndex = givenPosition - 1;
        int lastIndex = numberOfEntries - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            array[index] = array[index + 1];
        }
    }

    @Override
    public Iterator<T> getIterator() {
        return new SortedIterator();
    }

    private class SortedIterator implements Iterator<T>, Serializable {

        int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < numberOfEntries;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }

            return array[currentIndex++];
        }

    }

}
