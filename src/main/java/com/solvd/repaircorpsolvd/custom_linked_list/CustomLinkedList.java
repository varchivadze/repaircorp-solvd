package com.solvd.repaircorpsolvd.custom_linked_list;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CustomLinkedList<N> implements List<N> {

    private Node<N> head;
    private Node<N> tail;
    private int size;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomLinkedList.class);

    public CustomLinkedList(N initNode) {
        this.head = new Node<>(initNode);
        this.tail = this.head;
        this.size = 1;
    }

    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public Node<N> getHead() {
        return head;
    }

    public Node<N> getTail() {
        return tail;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public boolean contains(Object object) {
        Node<N> current = head;
        while (current != null) {
            if (current.equals(object)) {
                return true;
            }
            current = current.getNextNode();
        }
        return false;
    }

    @Override
    public Iterator<N> iterator() {
        return new Iterator<N>() {
            private Node<N> currentNode = head;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public N next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                N value = currentNode.getValue();
                currentNode = currentNode.getNextNode();
                return value;
            }
        };
    }

    @Override
    public Object[] toArray() {

        // not good for ref type
        Node<N> currentNode = head;
        Object[] array = new Object[size];
        int indexOf = 0;
        while (currentNode != null) {
            array[indexOf] = currentNode.getValue();
            currentNode = currentNode.getNextNode();
            indexOf++;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // could not fix it
            return (T[]) Arrays.copyOf(toArray(), size, a.getClass());
        }

        Node<N> currentNode = head;
        int index = 0;
        while (currentNode != null) {
            // could not fix it
            a[index++] = (T) currentNode.getValue();
            currentNode = currentNode.getNextNode();
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(N n) {
        if (n == null) {
            LOGGER.warn("Object cannot be null");
            return false;
        }

        Node<N> node = new Node<>(n);
        if (size == 0) {
            this.head = node;
            this.tail = this.head;
            this.size = 1;
            return true;
        }
        node.setPrevNode(this.tail);
        this.tail.setNextNode(node);
        this.tail = node;
        if (this.size == 1) {
            this.head.setNextNode(this.tail);
        }
        ++this.size;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<N> current = head;
        while (current != null) {
            if (current.equals(o)) {
                Node<N> prev = current.getPrevNode();
                Node<N> next = current.getNextNode();
                if (prev == null && next == null) {
                    head = null;
                    tail = null;
                    size = 0;

                } else if (prev == null) {
                    head = next;
                    next.setPrevNode(null);
                    size--;

                } else if (next == null) {
                    tail = prev;
                    prev.setNextNode(null);
                    size--;

                } else {
                    prev.setNextNode(next);
                    next.setPrevNode(prev);
                    size--;
                }

                return true;
            }
            current = current.getNextNode();
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Set<?> set = new HashSet<>(c);
        for (Node<N> current = head; current != null; current = current.getNextNode()) {
            if (!set.contains(current.getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends N> c) {
        for (N current : c) {
            if (current != null) {
                add(current);
            }
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends N> c) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is less than 0 or more than List size");
        }
        if (c == null || c.isEmpty()) {
            return false;
        }
        if (index >= getSize() - 1) {
            for (N elem : c) {
                add(elem);
            }
            return true;

        } else if (index == 0) {
            for (N elem : c) {
                Node<N> newNode = new Node<>(elem);
                head.setPrevNode(newNode);
                newNode.setNextNode(head);
                head = newNode;
                size++;
            }
            return true;

        } else {
            Node<N> curNode = head;
            int increm = 0;
            while (increm != index) {
                curNode = curNode.getNextNode();
                increm++;
            }
            Node<N> prevNode = curNode.getPrevNode();
            for (N elem : c) {
                Node<N> newNode = new Node<>(elem);
                prevNode.setNextNode(newNode);
                newNode.setPrevNode(prevNode);
                prevNode = prevNode.getNextNode();
                size++;
            }
            curNode.setPrevNode(prevNode);
            prevNode.setNextNode(curNode);
            return true;
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Set<?> set = new HashSet<>(c);
        boolean wasChanged = false;

        for (Node<N> currentNode = head; currentNode != null; currentNode = currentNode.getNextNode()) {
            if (set.contains((currentNode.getValue()))) {
                remove(currentNode);
                wasChanged = true;
                size--;
            }
        }
        return wasChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Set<?> set = new HashSet<>(c);
        boolean wasChanged = false;

        for (Node<N> currentNode = head; currentNode != null; currentNode = currentNode.getNextNode()) {
            if (!set.contains((currentNode.getValue()))) {
                remove(currentNode);
                wasChanged = true;
                size--;
            }
        }
        return wasChanged;
    }

    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public N get(int index) {
        if (head == null) {
            return null;
        }
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is less than 0 or more than List size");
        }
        int increm = 0;
        for (Node<N> node = head; node != null; node = node.getNextNode()) {
            if (increm == index) {
                return node.getValue();
            }
        }

        return head.getValue();
    }

    @Override
    public N set(int index, N element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is less than 0 or more than List size");
        }
        int increment = 0;
        N toRet = head.getValue();
        for (Node<N> node = head; node != null; node = node.getNextNode()) {
            if (increment == index) {
                toRet = node.getValue();
                node.setValue(element);
                return toRet;
            }
        }
        return toRet;
    }

    @Override
    public void add(int index, N element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is less than 0 or more than List size");
        }

        if (index >= getSize() - 1) {
            add(element);

        } else if (index == 0) {
            Node<N> newNode = new Node<>(element);
            head.setPrevNode(newNode);
            newNode.setNextNode(head);
            head = newNode;
            size++;

        } else {
            Node<N> curNode = head;
            int increm = 0;
            while (increm != index) {
                curNode = curNode.getNextNode();
                increm++;
            }
            Node<N> prevNode = curNode.getPrevNode();
            Node<N> newNode = new Node<>(element);
            prevNode.setNextNode(newNode);
            newNode.setPrevNode(prevNode);
            prevNode = prevNode.getNextNode();
            curNode.setPrevNode(prevNode);
            prevNode.setNextNode(curNode);
            size++;
        }
    }

    @Override
    public N remove(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is less than 0 or more than List size");
        }
        Node<N> current = head;
        N toRet = head.getValue();
        int increm = 0;
        while (current != null) {
            if (increm == index) {
                Node<N> prev = current.getPrevNode();
                Node<N> next = current.getNextNode();
                if (prev == null && next == null) {
                    head = null;
                    tail = null;

                } else if (prev == null) {
                    head = next;
                    next.setPrevNode(null);
                } else if (next == null) {
                    tail = prev;
                    prev.setNextNode(null);
                } else {
                    prev.setNextNode(next);
                    next.setPrevNode(prev);
                }
                size--;
                return toRet;
            }
            current = current.getNextNode();
            increm++;
        }
        return null;
    }

    @Override
    public int indexOf(Object object) {
        Node<N> current = head;
        int increm = 0;
        while (current != null) {
            if (current.equals(object)) {
                return increm;
            }
            current = current.getNextNode();
            increm++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        Node<N> current = head;
        int increm = 0;
        int ret = -1;
        while (current != null) {
            if (current.equals(object)) {
                ret = increm;
            }
            current = current.getNextNode();
            increm++;
        }
        return ret;
    }

    @Override
    public ListIterator<N> listIterator() {
        return new ListIterator<N>() {
            private Node<N> currentNode = head;
            private int index = 0;

            @Override
            public int nextIndex() {
                return index;
            }

            @Override
            public int previousIndex() {
                return index - 1;
            }

            @Override
            public void remove() {
                if (currentNode == null) {
                    throw new IllegalStateException("No current node to remove.");
                }
                Node<N> nextNode = currentNode.getNextNode();
                Node<N> prevNode = currentNode.getPrevNode();

                if (prevNode != null) {
                    prevNode.setNextNode(nextNode);
                } else {
                    head = nextNode;
                }

                if (nextNode != null) {
                    nextNode.setPrevNode(prevNode);
                }

                currentNode = nextNode;
                index--;
            }

            @Override
            public void set(N n) {
                if (currentNode == null) {
                    throw new IllegalStateException("No current node to set.");
                }
                currentNode.setValue(n);
            }

            @Override
            public void add(N n) {
                Node<N> newNode = new Node<>(n);
                if (currentNode == null) {
                    head = newNode;
                } else {
                    newNode.setNextNode(currentNode.getNextNode());
                    newNode.setPrevNode(currentNode);

                    if (currentNode.getNextNode() != null) {
                        currentNode.getNextNode().setPrevNode(newNode);
                    }
                    currentNode.setNextNode(newNode);
                }
                currentNode = newNode;
                index++;
            }

            @Override
            public boolean hasNext() {
                return currentNode != null && currentNode.getNextNode() != null;
            }

            @Override
            public boolean hasPrevious() {
                return currentNode != null && currentNode.getPrevNode() != null;
            }

            @Override
            public N next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                currentNode = currentNode.getNextNode();
                index++;
                return currentNode.getValue();
            }

            @Override
            public N previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                currentNode = currentNode.getPrevNode();
                index--;
                return currentNode.getValue();
            }
        };
    }

    @Override
    public ListIterator<N> listIterator(int index) {
        return new ListIterator<N>() {
            private Node<N> currentNode = head;
            private int indexCurr = 0;
            private boolean movedToIndex = false;

            @Override
            public int nextIndex() {
                return indexCurr;
            }

            @Override
            public int previousIndex() {
                return indexCurr - 1;
            }

            @Override
            public void remove() {
                if (currentNode == null) {
                    throw new IllegalStateException("No current node to remove.");
                }
                Node<N> nextNode = currentNode.getNextNode();
                Node<N> prevNode = currentNode.getPrevNode();

                if (prevNode != null) {
                    prevNode.setNextNode(nextNode);
                } else {
                    head = nextNode;
                }

                if (nextNode != null) {
                    nextNode.setPrevNode(prevNode);
                }

                currentNode = nextNode;
                indexCurr--;
            }

            @Override
            public void set(N n) {
                if (currentNode == null) {
                    throw new IllegalStateException("No current node to set.");
                }
                currentNode.setValue(n);
            }

            @Override
            public void add(N n) {
                Node<N> newNode = new Node<>(n);
                if (currentNode == null) {
                    head = newNode;
                } else {
                    newNode.setNextNode(currentNode.getNextNode());
                    newNode.setPrevNode(currentNode);

                    if (currentNode.getNextNode() != null) {
                        currentNode.getNextNode().setPrevNode(newNode);
                    }
                    currentNode.setNextNode(newNode);
                }
                currentNode = newNode;
                indexCurr++;
            }

            @Override
            public boolean hasNext() {
                return currentNode != null && currentNode.getNextNode() != null;
            }

            @Override
            public boolean hasPrevious() {
                return currentNode != null && currentNode.getPrevNode() != null;
            }

            @Override
            public N next() {
                if (!movedToIndex) {
                    while ((indexCurr < index)) {
                        currentNode = currentNode.getNextNode();
                        indexCurr++;
                    }
                    movedToIndex = true;
                }

                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                currentNode = currentNode.getNextNode();
                indexCurr++;
                return currentNode.getValue();
            }

            @Override
            public N previous() {
                if (!movedToIndex) {
                    while (indexCurr < index) {
                        currentNode = currentNode.getNextNode();
                        indexCurr++;
                    }
                    movedToIndex = true;
                }
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                currentNode = currentNode.getPrevNode();
                indexCurr--;
                return currentNode.getValue();
            }
        };
    }


    @Override
    public List<N> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Invalid index/indexes");
        }

        List<N> newArray = new ArrayList<>();
        int iter = 0;
        for (Node<N> currentNode = head; currentNode != null; currentNode = currentNode.getNextNode()) {
            if (iter >= fromIndex && iter < toIndex) {
                newArray.add(currentNode.getValue());
            }
            iter++;
        }
        return newArray;
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        for (Node<N> currentNode = head; currentNode != null; currentNode = currentNode.getNextNode()) {
            toReturn.append(currentNode.getValue()).append(" ");
        }
        return toReturn.toString();
    }
}
