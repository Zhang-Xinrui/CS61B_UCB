package bearmaps.proj2c.TrieSet;

import java.util.*;

public class MyTrieSet implements TrieSet61B{
    private Node root;

    public MyTrieSet() {
        this.root = new Node(false);
    }

    /** Clears all items out of Trie */
    public void clear() {
        root = null;
    }
    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key) {
        if (key == null || key.length() == 0 || root == null) {
            return false;
        }
        Node curr = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!curr.children.containsKey(c)) {
                return false;
            }
            curr = curr.children.get(c);
        }
        return true;
    }
    /** Inserts string KEY into Trie */

    public void add(String key) {
        if (key == null || key.length() < 1 || root == null) {
            return;
        }
        Node curr = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!curr.children.containsKey(c)) {
                curr.children.put(c, new Node(false));
            }
            curr = curr.children.get(c);
        }
        curr.isLeaf = true;
    }

    /** Returns a list of all words that start with PREFIX */
    public List<String> keysWithPrefix(String prefix) {
        if (prefix == null || prefix.length() == 0 || root == null) {
            throw new IllegalArgumentException();
        }
        Node curr = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (curr.children.containsKey(c)) {
                curr = curr.children.get(c);
            } else {
                return null;
            }
        }
        List<String> strings = new LinkedList<>();
        helpprefix(curr, strings, prefix);
        return strings;
    }
    private void helpprefix(Node curr, List<String> keys, String prefix) {
        if (curr.isLeaf) {
            keys.add(prefix);
        }
        for (Map.Entry<Character, Node> child: curr.children.entrySet()) {
            helpprefix(child.getValue(), keys, prefix + child.getKey());
        }
    }

    private class Node {
        Map<Character, Node> children;
        boolean isLeaf;
        private Node(boolean judge) {
            this.isLeaf = judge;
            this.children = new HashMap<>();
        }
    }    
}
