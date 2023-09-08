package adt;

import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author Gan Zhi Ken
 */
public class AVLTreeSet<T extends Comparable<T>> implements SetInterface<T>, Serializable {

    private class Node implements Serializable {

        private T element;
        private Node left;
        private Node right;
        private Node parent;
        private int height;

        public Node(T element) {
            this.element = element;
            this.height = 1;
        }

    }

    private Node root;
    private int numberOfElement;

    public AVLTreeSet() {
    }

    //get the height of node given
    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    //calculate height diff of children
    private int calcHeightDiff(Node node) {
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    //return the higher value between a and b
    private int max(int a, int b) {
        return a > b ? a : b;
    }

    //update the height of node
    private void updateHeight(Node node) {
        node.height = max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    private void balanceTree(Node node) {

        while (node != null) {

            updateHeight(node);

            //get the height different at left node and right node
            //positive value = left node height > right node height
            //negative value = left node height < right node height
            int heightDifferent = calcHeightDiff(node);

            if (heightDifferent > 1) {

                //LR
                if (calcHeightDiff(node.left) < 0) {
                    leftRotate(node.left);
                }

                //LL
                node = rightRotate(node);

            } else if (heightDifferent < -1) {

                //RL
                if (calcHeightDiff(node.right) > 0) {
                    rightRotate(node.right);
                }

                //RR
                node = leftRotate(node);
            }

            node = node.parent;
        }
    }

    private Node leftRotate(Node node) {

        Node parent = node.parent;
        Node rightNode = node.right;
        Node rightLeftNode = rightNode.left;

        //let the parent of current node point to right node
        if (parent != null) {
            if (node.element.compareTo(parent.element) > 0) {
                parent.right = rightNode;
            } else {
                parent.left = rightNode;
            }
        } else {
            root = rightNode; //parent = null which mean is the root
        }
        //let the right node parent = current node parent
        rightNode.parent = parent;

        //let the left of right node = current node, and change the parent of current node
        rightNode.left = node;
        node.parent = rightNode;

        if (rightLeftNode != null) {
            rightLeftNode.parent = node;
        }
        //let the left child of right node = right of current node
        node.right = rightLeftNode;

        //update the height
        updateHeight(node);
        updateHeight(rightNode);

        return rightNode; // return root of subtree

    }

    private Node rightRotate(Node node) {

        Node parent = node.parent;
        Node leftNode = node.left;
        Node leftRightNode = leftNode.right;

        //let the parent of current node point to left node
        if (parent != null) {
            if (node.element.compareTo(parent.element) > 0) {
                parent.right = leftNode;
            } else {
                parent.left = leftNode;
            }
        } else {
            root = leftNode; //parent = null which mean is the root
        }
        //let the left node parent = current node parent
        leftNode.parent = parent;

        //let the right of left node = current node, and change the parent of current node
        leftNode.right = node;
        node.parent = leftNode;

        if (leftRightNode != null) {
            leftRightNode.parent = node;
        }

        //let the right child of left node = left of current node
        node.left = leftRightNode;

        //update the height
        updateHeight(node);
        updateHeight(leftNode);

        return leftNode; //return root of subtree

    }

    @Override
    public boolean add(T element) {

        Node newNode = new Node(element);
        Node parent = null;

        if (isEmpty()) {
            root = newNode;
            numberOfElement++;
            return true;
        }

        Node currentNode = root;

        // go down through the tree until get the parent of new node
        while (currentNode != null) {
            parent = currentNode;

            //do not add into tree if same element
            if (element.compareTo(currentNode.element) == 0) {
                return false;

                //if inserted value > currentnode, go to right of the node
                //if the right is not null, go to the node and compare agn
            } else if (element.compareTo(currentNode.element) > 0) {
                currentNode = currentNode.right;

                //if inserted value < currentnode, go to left of the node
                //if the left is not null, go to the node and compare agn
            } else if (element.compareTo(currentNode.element) < 0) {
                currentNode = currentNode.left;
            }
        }

        newNode.parent = parent;

        //place the node in left or right of parent
        if (element.compareTo(parent.element) > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        balanceTree(parent);

        numberOfElement++;
        return true;
    }

    @Override
    public boolean addAll(SetInterface<T> set) {
        if (set.isEmpty()) {
            return false;
        }

        Iterator<T> iterator = set.getIterator();

        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
        return true;
    }

    @Override
    public boolean remove(T element) {

        if (isEmpty()) {
            return false;
        }

        Node deleteNode = findNode(element);

        //element not found
        if (deleteNode == null) {
            return false;
        }

        //case 1: have no child, set parent's child reference (left or right) to null
        if (deleteNode.left == null && deleteNode.right == null) {

            if (deleteNode == root) {
                root = null;
            } else {
                if (deleteNode == deleteNode.parent.left) {
                    deleteNode.parent.left = null;
                } else {
                    deleteNode.parent.right = null;
                }
            }

            numberOfElement--;
            balanceTree(deleteNode.parent);

        } //case 2 : one child
        else if (deleteNode.left == null || deleteNode.right == null) {

            Node child = deleteNode.left == null ? deleteNode.right : deleteNode.left;

            if (deleteNode == root) {
                child.parent = null;
                root = child;
            } else {

                child.parent = deleteNode.parent;

                if (deleteNode == deleteNode.parent.left) {
                    deleteNode.parent.left = child;
                } else {
                    deleteNode.parent.right = child;
                }

            }

            numberOfElement--;
            balanceTree(child);

        } // case 3 : 2 child
        else {
            Node inorderSuccessor = deleteNode.right;

            while (inorderSuccessor.left != null) {
                inorderSuccessor = inorderSuccessor.left;
            }

            //remove the element (will be always case 1 or case 2)
            remove(inorderSuccessor.element);

            //copy the value of element to node needed to be deleted
            deleteNode.element = inorderSuccessor.element;
        }

        return true;
    }

    @Override
    public boolean removeAll(SetInterface<T> set) {
        if (this.isEmpty() || set.isEmpty()) {
            return false;
        }

        Iterator<T> iterator = set.getIterator();

        while (iterator.hasNext()) {
            this.remove(iterator.next());
        }
        return true;
    }

    @Override
    public boolean retainAll(SetInterface<T> set) {
        if (this.isEmpty() || set.isEmpty()) {
            return false;
        }

        Iterator<T> setB = set.getIterator();
        AVLTreeSet<T> newSet = new AVLTreeSet();

        while (setB.hasNext()) {
            T element = setB.next();

            if (this.contains(element)) {
                newSet.add(element);
            }
        }

        this.clear();
        this.root = newSet.root;
        this.numberOfElement = newSet.numberOfElement;

        return true;
    }

    @Override
    public void clear() {

        clearParentReference(root);
        numberOfElement = 0;
        root = null;

    }

    //clear the doubly reference, inorder to let jvm garbage collector to collect it
    private void clearParentReference(Node subtreeRoot) {
        if (subtreeRoot == null) {
            return;
        }

        subtreeRoot.parent = null;
        //go down left and right subtree to clear parent reference
        clearParentReference(subtreeRoot.left);
        clearParentReference(subtreeRoot.right);
    }

    @Override
    public T get(T element) {

        Node node = findNode(element);
        if (node == null) {
            return null;
        } else {
            return node.element;
        }
    }

    private Node findNode(T element) {
        Node currentNode = root;

        while (currentNode != null) {
            if (element.compareTo(currentNode.element) < 0) {
                currentNode = currentNode.left;
            } else if (element.compareTo(currentNode.element) > 0) {
                currentNode = currentNode.right;
            } else {
                return currentNode;
            }
        }

        return null;
    }

    @Override
    public boolean contains(T element) {
        return get(element) != null;
    }

    @Override
    public boolean containsAll(SetInterface<T> set) {
        Iterator<T> iterator = set.getIterator();

        while (iterator.hasNext()) {
            if (!this.contains(iterator.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return numberOfElement;
    }

    @Override
    public boolean equal(SetInterface<T> set) {
        if (this.size() != set.size()) {
            return false;
        }
        Iterator setA = getIterator();
        Iterator setB = set.getIterator();

        while (setA.hasNext()) {
            if (!setA.next().equals(setB.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T[] toArray() {
        T[] a = (T[]) new Comparable[size()];

        Iterator<T> iterator = getIterator();
        for (int i = 0; iterator.hasNext(); i++) {
            a[i] = iterator.next();
        }

        return a;
    }

    @Override
    public String toString() {
        T[] a = this.toArray();

        String outputStr = "";
        for (T a1 : a) {
            outputStr += a1 + "\n";
        }

        return outputStr;
    }

    @Override
    public Iterator<T> getIterator() {
        return new InorderIterator();
    }

    private class InorderIterator implements Iterator<T>, Serializable {

        StackInterface<Node> stack;

        public InorderIterator() {
            stack = new LinkedStack();
            pushLeftNodeOf(root);
        }

        private void pushLeftNodeOf(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }

            Node currentNode = stack.pop();
            pushLeftNodeOf(currentNode.right);

            return currentNode.element;
        }
    }
}
