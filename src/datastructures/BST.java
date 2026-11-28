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
        // size is updated by insert and removeKey, so the tree is not traversed
        return size;
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

    // Returns the data of all nodes in increasing order of key.
    // Every node is visited exactly once: O(n)
    @Override
    public LinkedList<T> inOrder() {
        LinkedList<T> result = new LinkedList<T>();
        inOrder(root, result);
        return result;
    }

    // In-order traversal of the subtree rooted at node:
    // left subtree, then the node itself, then right subtree
    private void inOrder(BSTNode<K, T> node, LinkedList<T> result) {
        if (node == null)
            return;
        inOrder(node.left, result);
        // insert() adds after the list's current element, which is always
        // the last one here, so the data is appended in O(1)
        result.insert(node.data);
        inOrder(node.right, result);
    }

    // Returns, in increasing order of key, the data of every node with
    // low <= key <= high. Only the subtrees that can contain such keys
    // are visited: O(h + k), where k is the number of nodes returned
    @Override
    public LinkedList<T> rangeSearch(K low, K high) {
        LinkedList<T> result = new LinkedList<T>();
        // No key can be inside a missing or an empty range
        if (low == null || high == null || low.compareTo(high) > 0)
            return result;
        rangeSearch(root, low, high, result);
        return result;
    }

    // In-order traversal that skips the subtrees outside [low, high]
    private void rangeSearch(BSTNode<K, T> node, K low, K high, LinkedList<T> result) {
        if (node == null)
            return;
        int cmpLow = low.compareTo(node.key);   // negative when low < key
        int cmpHigh = high.compareTo(node.key); // positive when key < high

        // The left subtree holds the keys smaller than this key:
        // it can contain keys of the range only if low < key
        if (cmpLow < 0)
            rangeSearch(node.left, low, high, result);

        // low <= key <= high
        if (cmpLow <= 0 && cmpHigh >= 0)
            result.insert(node.data);

        // The right subtree holds the keys greater than this key:
        // it can contain keys of the range only if key < high
        if (cmpHigh > 0)
            rangeSearch(node.right, low, high, result);
    }

    // Returns the number of nodes on the longest root-to-leaf path (0 if empty)
    @Override
    public int height() {
        return height(root);
    }

    // Height of the subtree rooted at node: the node itself
    // plus the taller of its two subtrees
    private int height(BSTNode<K, T> node) {
        if (node == null)
            return 0;
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        if (leftHeight > rightHeight)
            return leftHeight + 1;
        else
            return rightHeight + 1;
    }

    // Returns the number of nodes visited by a search for the key
    // (one comparison per node, 0 if empty). O(h)
    @Override
    public int nbKeyComp(K key) {
        int count = 0;
        if (key == null)
            return count;
        // A local pointer follows the same path as findKey,
        // so the tree and current are left unchanged
        BSTNode<K, T> p = root;
        while (p != null) {
            count++; // one comparison for the node being visited
            int cmp = key.compareTo(p.key);
            if (cmp == 0)
                return count; // key found: the search stops here
            else if (cmp < 0)
                p = p.left;
            else
                p = p.right;
        }
        return count; // key not found: the search fell off the tree
    }
}
