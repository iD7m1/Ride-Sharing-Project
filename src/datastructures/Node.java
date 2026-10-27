package datastructures;

public class Node<T> {
    public T data;
    public Node<T> next;
    public Node<T> previous;

    public Node () {
        data = null;
        next = previous = null;
    }

    public Node (T val) {
        data = val;
        next = previous = null;
    }

    public T getData () {
        return data;
    }

    public void setData (T data) {
        this.data = data;
    }

    public Node<T> getNext () {
        return next;
    }

    public void setNext (Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrevious () {
        return previous;
    }

    public void setPrevious (Node<T> previous) {
        this.previous = previous;
    }
}
