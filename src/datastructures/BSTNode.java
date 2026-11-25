package datastructures;

public class BSTNode<K, T> {
    public K key;
    public T data;
    public BSTNode<K, T> left, right;

    // Creates a new instance of BSTNode
    public BSTNode(K k, T val) {
        key = k;
        data = val;
        left = right = null;
    }

    public BSTNode(K k, T val, BSTNode<K, T> l, BSTNode<K, T> r) {
        key = k;
        data = val;
        left = l;
        right = r;
    }
}
