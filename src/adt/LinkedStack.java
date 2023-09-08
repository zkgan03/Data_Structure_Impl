/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.util.Iterator;

/**
 *
 * @author taruc
 */
public class LinkedStack<T> implements StackInterface<T> {

    private class Node {

        T data;
        Node next;

        public Node(T data) {
            this.data = data;
        }

        public Node() {
        }

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

    }

    private Node topNode;

    public LinkedStack() {
        topNode = null;
    }

    @Override
    public String toString() {
        String str = "";
        Node currentNode = topNode;

        while (currentNode != null) {
            str += currentNode.data + "\n";
            currentNode = currentNode.next;
        }

        return str;
    }

    @Override
    public void push(T newEntry) {
        Node newNode = new Node(newEntry);
        newNode.next = topNode;
        topNode = newNode;

    }

    @Override
    public T pop() {

        if (isEmpty()) {
            return null;
        }

        T topElement = topNode.data;
        topNode = topNode.next;

        return topElement;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }

        return topNode.data;
    }

    @Override
    public boolean isEmpty() {
        return topNode == null;
    }

    @Override
    public void clear() {
        topNode = null;
    }

    public Iterator<T> getIterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {

        private Node current = topNode;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }

            T data = current.data;
            current = current.next;
            return data;
        }
    }

}
