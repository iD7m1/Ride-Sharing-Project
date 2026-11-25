package interfaces;
import datastructures.LinkedList;
/**
 * A Binary Search Tree whose key can be any comparable type: the generic
 * version of the BST class developed in class. Keys are unique and must be
 * compared only with compareTo. n is the number of keys, h the height.
 */
public interface IBST<K extends Comparable<K>, T> {

    // Returns true if the tree has no keys. O(1).
    boolean empty();

    // Returns the number of keys in the tree. O(1).
    int size();

    // Searches for the key; if found, the current node becomes its node and true is returned. O(h).
    boolean findKey(K key);

    // Returns the data of the current node (valid only after a successful findKey or insert).
    T retrieve();

    // Inserts the key with its data; returns false (no change) if the key already exists. O(h).
    boolean insert(K key, T data);

    // Removes the key and its data; returns false if the key does not exist. O(h).
    boolean removeKey(K key);

    // Returns the data of all nodes in increasing order of key. O(n).
    LinkedList<T> inOrder();

    // Returns, in increasing order of key, the data of every node with low <= key <= high,
    // visiting only the subtrees that can contain such keys. O(h + k), k = number returned.
    LinkedList<T> rangeSearch(K low, K high);

    // Returns the number of nodes on the longest root-to-leaf path (0 if empty).
    int height();

    // Returns the number of nodes visited by a search for the key (one comparison per node,
    // 0 if empty). Does not change the tree or the current node.
    int nbKeyComp(K key);
}
