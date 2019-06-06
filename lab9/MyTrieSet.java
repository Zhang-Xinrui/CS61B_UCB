import java.util.*;

public class MyTrieSet implements TrieSet61B{
    private Node root;

    private class Node {
        Map<Character, Node> map;
        boolean isKey;
        private Node(boolean judge) {
            this.isKey = judge;
            this.map = new HashMap<>();
        }
    }

    public MyTrieSet() {
        this.root = new Node(false);
    }

    /** Clears all items out of Trie */
    public void clear() {
        root = new Node(false);
    }
    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key) {
        Node curr = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);
        }
        return true;
    }
    /** Inserts string KEY into Trie */

    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    /** Returns a list of all words that start with PREFIX */
    public List<String> keysWithPrefix(String prefix) {
        Node curr = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (curr.map.containsKey(c)) {
                curr = curr.map.get(c);
            } else {
                return null;
            }
        }
        List<String> strings = new LinkedList<>();
        return helpprefix(curr, strings, prefix);
    }
    private List<String> helpprefix(Node curr, List<String> strings, String prefix) {
        if (curr == null) {
            return strings;
        }
        if (curr.isKey) {
            strings.add(prefix);
        }
        for (char c = 'A'; c < 'Z' + 1; c++) {
            if (curr.map.containsKey(c)) {
                String newone = prefix + c;
                helpprefix(curr.map.get(c), strings, newone);
            }
        }
        for (char c = 'a'; c < 'z' + 1; c++) {
            if (curr.map.containsKey(c)) {
                String newone = prefix + c;
                helpprefix(curr.map.get(c), strings, newone);
            }
        }
        return strings;
    }
    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
        }

}
