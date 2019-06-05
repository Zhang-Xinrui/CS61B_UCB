import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class BSTMapNode {
        private BSTMapNode left;
        private BSTMapNode right;
        private K key;
        private V value;
        private int size;
        BSTMapNode(K keyy, V valuee, int sizee) {
            key = keyy;
            value = valuee;
            size = sizee;
        }
    }

     private BSTMapNode root;

    BSTMap() {
        root = null;
    }

    public void clear(){
        root = null;
    }

    public boolean containsKey(K keyy) {
        BSTMapNode current = root;
        while (current != null) {
            if (current.key.equals(keyy)) {
                return true;
            } else if (current.key.compareTo(keyy) < 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return false;
    }

    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        }
        BSTMapNode current = root;
        while (true) {
            if (current.key.equals(key)) {
                break;
            } else if (current.key.compareTo(key) < 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return current.value;        
    }

    public int size() {
        if (root == null) {
            return 0;
        }
        return root.size;
    }

    public void put(K key, V value) {
        BSTMapNode curr = root;
        if (this.containsKey(key)) {
            while (curr != null) {
                if (curr.key.equals(key)) {
                    curr.value = value;
                    return;
                } else if (curr.key.compareTo(key) < 0) {
                    curr = curr.right;
                } else {
                    curr = curr.left;
                }
            }
        } else {
            while (curr != null) {
                curr.size += 1;
                if (curr.key.compareTo(key) < 0 && curr.right != null) {
                    curr = curr.right;
                } else if (curr.key.compareTo(key) > 0 && curr.left != null) {
                    curr = curr.left;
                } else {
                    break;
                }
            }
            BSTMapNode newone = new BSTMapNode(key, value, 1);
            if (root == null) {
                root = newone;
            }else if (curr.key.compareTo(key) < 0) {
                curr.right = newone;
            } else {
                curr.left = newone;
            }
        }
    }
    private void addset(BSTMapNode curr, Set<K> newset) {
        if (curr == null) {
            return;
        }
        newset.add(curr.key);
        addset(curr.left, newset);
        addset(curr.right, newset);
    }
    public Set<K> keySet() {
        Set<K> newset = new HashSet<>();
        addset(root, newset);
        return newset;
    }

    private BSTMapNode min(BSTMapNode curr, BSTMapNode parent) {
        while (curr.left != null) { //注意，这里要换上去的最小叶的size没有发生变化，不过这无关紧要
            curr.size--;
            parent = curr;
            curr = curr.left;
        }
        if (parent.right.equals(curr)) {
            parent.right = curr.right;
        } else {
            parent.left = curr.right;
        }
        return curr;
    }
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        BSTMapNode curr = root;
        BSTMapNode parent = null;
        V val;
        while (true) {
            curr.size--;
            if (curr.key.equals(key)) {
                val = curr.value;
                if (curr.left == null && curr.right == null) {
                    if (curr.equals(root)) {
                        root = null;
                    } else if(parent.right.equals(curr)) {
                        parent.right = null;
                    } else {
                        parent.left = null;
                    }
                } else if (curr.left != null && curr.right == null) {
                    if (curr.equals(root)) {
                        root = curr.left;
                    } else if (parent.right != null && parent.right.equals(curr)) {
                        parent.right = curr.left;
                    } else {
                        parent.left = curr.left;
                    }
                } else if (curr.left == null) {
                    if (curr.equals(root)) {
                        root = curr.right;
                    } else if (parent.left != null && parent.left.equals(curr)) {
                        parent.left = curr.right;
                    } else {
                        parent.right = curr.right;
                    }
                } else {
                    BSTMapNode minone = min(curr.right, curr);
                    if (curr.equals(root)) {
                        root = minone;
                    } else if (parent.left != null && parent.left.equals(curr)) {
                        parent.left = minone;
                    } else {
                        parent.right = minone;
                    }
                    minone.left = curr.left;
                    minone.right = curr.right;
                    minone.size = curr.size;
                }
                curr = null;
                break;
            } else if (curr.key.compareTo(key) < 0) {
                parent = curr;
                curr = curr.right;
            } else {
                parent = curr;
                curr = curr.left;
            }
        }
        return val;
    }

    public V remove(K key, V value) {
        if (get(key).equals(value)) {
            return remove(key);
        }
        return null;
     }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    void printInOrder() {
        
    }
}