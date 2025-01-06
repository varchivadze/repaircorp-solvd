package com.solvd.repaircorpsolvd.custom_linked_list;

import java.util.Objects;

public class Node<N> {

    private Node<N> prevNode;
    private N value;
    private Node<N> nextNode;

    public Node(N newNode) {
        this.value = newNode;
    }

    public Node<N> getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Node<N> prevNode) {
        this.prevNode = prevNode;
    }

    public N getValue() {
        return value;
    }

    public void setValue(N value) {
        this.value = value;
    }

    public Node<N> getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node<N> nextNode) {
        this.nextNode = nextNode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        // in case of usage contains() later:
        // We have to skip getClass() != obj.getClass()?? as the getClass() returns Node, and  obj.getClass() might not be a node
        // We also have to skip (object == null)?? as some node.value may be null
        if (object == null) {
            return false;
        }

        if (object instanceof Node<?> compareNode) {
            return Objects.equals(value, compareNode.getValue());
        }

        return Objects.equals(value, object);
    }
}
