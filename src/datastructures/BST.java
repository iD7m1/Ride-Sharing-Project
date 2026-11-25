package datastructures;

import interfaces.IBST;

public class BST<K extends Comparable<K>, T> implements IBST<K, T> {
    BSTNode<K, T> root, current;
    private int size;

    // Creates a new instance of BST
    public BST() {
        root = current = null;
        size = 0;
    }

    @Override
    public boolean empty() {
        return root == null;
    }

    public boolean full() {
        return false;
    }

    @Override
    public int size() {
        // TODO: return number of keys in O(1) (update size in insert/removeKey)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public T retrieve() {
        return current.data;
    }

    @Override
    public boolean findKey(K tkey) {
        BSTNode<K, T> p = root, q = root;
        if (empty())
            return false;
        while (p != null) {
            q = p;
            int cmp = tkey.compareTo(p.key);
            if (cmp == 0) {
                current = p;
                return true;
            } else if (cmp < 0)
                p = p.left;
            else
                p = p.right;
        }
        current = q;
        return false;
    }

    @Override
    public boolean insert(K k, T val) {
        BSTNode<K, T> p, q = current;
        if (findKey(k)) {
            current = q; // findKey() modified current
            return false; // key already in the BST
        }
        p = new BSTNode<K, T>(k, val);
        if (empty()) {
            root = current = p;
            size++;
            return true;
        } else {
            // current is pointing to parent of the new key
            if (k.compareTo(current.key) < 0)
                current.left = p;
            else
                current.right = p;
            current = p;
            size++;
            return true;
        }
    }

    // Method removeKey: iterative
    @Override
    public boolean removeKey(K k) {
        BSTNode<K, T> p = root;
        BSTNode<K, T> q = null; // Parent of p
        boolean found = false;
        // Search for k
        while ((p != null) && (!found)) {
            int res = k.compareTo(p.key);
            if (res < 0) {
                q = p;
                p = p.left;
            } else if (res > 0) {
                q = p;
                p = p.right;
            } else // Found the key
                found = true;
        }
        if (found) {
            // Check the three cases
            if ((p.left != null) && (p.right != null)) { // Case 3: two children
                // Search for the min in the right subtree
                BSTNode<K, T> min = p.right;
                q = p;
                while (min.left != null) {
                    q = min;
                    min = min.left;
                }
                p.key = min.key;
                p.data = min.data;
                deleteNode(min, q);
            } else // case 1 and 2
                deleteNode(p, q);
            current = root;
            size--;
            return true;
        }
        return false; // Not found
    }

    private void deleteNode(BSTNode<K, T> n, BSTNode<K, T> parent) {
        // parent is the parent of n. The method deletes node n (case 1 and 2)
        BSTNode<K, T> child;
        if (n.left != null)
            child = n.left;
        else
            child = n.right;
        if (parent == null) { // No parent for p, root must change
            root = child;
        } else {
            if (n.key.compareTo(parent.key) < 0) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        }
    }

    @Override
    public LinkedList<T> inOrder() {
        // TODO: return all node data in increasing order of key in O(n)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public LinkedList<T> rangeSearch(K low, K high) {
        // TODO: return all node data with low <= key <= high in O(h + k)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int height() {
        // TODO: return number of nodes on longest root-to-leaf path (0 if empty)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int nbKeyComp(K key) {
        // TODO: return number of nodes visited searching for key without modifying tree or current
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
