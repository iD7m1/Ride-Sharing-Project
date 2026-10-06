package datastructures;

public class LinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> current;
    private int size;

    public LinkedList() {
        head = current = null;
        size = 0;
    }

    public boolean empty() {
        return head == null;
    }

    public boolean last() {
        return current.next == null;
    }

    public boolean first () {
        return current.previous == null;
    }

    public boolean full() {
        return false;
    }

    public void findFirst() {
        current = head;
    }

    public void findPrevious () {
        current = current.previous;
    }

    public void findNext() {
        current = current.next;
    }

    public T retrieve() {
        return current.data;
    }

    public void update(T val) {
        current.data = val;
    }

    public void insert(T val) {
        Node<T> tmp = new Node<T>(val);
        if(empty()) {
            current = head = tmp;
        }
        else {
            tmp.next = current.next;
            tmp.previous = current;
            if(current.next != null)
                current.next.previous = tmp;
            current.next = tmp;
            current = tmp;
        }
        size++;
    }

    // Add an element and maintains alphabetical ordering requires this method
    public void insertFirst (T val) {
        Node<T> element = new Node<>(val);

        if (empty()) {
            head = current = element;
        } else {
            element.next = head;
            head.previous = element;
            head = element;
        }
            size++;
    }

    public void remove() {
        if(current == head) {
            head = head.next;
            if(head != null) head.previous = null;
        } else {
            current.previous.next = current.next;
            if(current.next != null) current.next.previous = current.previous;
        }

        if (current.next == null) current = head;
        else current = current.next;

        size--;
    }

    public int size () {
        return size;
    }
}
