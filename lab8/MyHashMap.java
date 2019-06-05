import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private Set<K> keys;    //hold all the keys
    private int initialSize = 16;
    private double loadFactor = 0.75;
    private double nowFactor;
    private List<List> lists;

    private static int size = 16;
    private static double factor = 0.75;

    private class Node {
        private K key;
        private V val;

        Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    /*public class MyHashMapIterator implements Iterator<K> {
        public MyHashMapIterator() {

        }
        public boolean hasNext() {

        }
        public K
    }*/

    public MyHashMap() {
        this(size, factor);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, factor);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.keys = new HashSet<>();
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.nowFactor = 0;
        this.lists = new ArrayList<>();
        for (int i = 0; i < this.initialSize; i++) {
            this.lists.add(new LinkedList<Node>());
        }
    }

    public void clear() {
        lists = new ArrayList<>();
        keys = new HashSet<>();
        nowFactor = 0;
        for (int i = 0; i < this.initialSize; i++) {
            lists.add(new LinkedList());
        }
    }

    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    public V get(K key) {
        int index = (Integer.MAX_VALUE & key.hashCode()) % initialSize;
        List curr = lists.get(index);
        for (int i = 0; i < curr.size(); i++) {
            Node here = (Node) curr.get(i);
            if (here.key.equals(key)) {
                return here.val;
            }
        }
        return null;
    }

    public int size() {
        return keys.size();
    }

    public void put(K key, V value) {
        int code = (Integer.MAX_VALUE & key.hashCode());
        List curr = lists.get(code % initialSize);
        if (keys.contains(key)) {
            for (int i = 0; i < curr.size(); i++) {
                Node here = (Node) curr.get(i);
                if (here.key.equals(key)) {
                    here.val = value;
                }
            }
        } else {
            keys.add(key);
            nowFactor = (double) (keys.size() + 1) / (double) initialSize;
            if (nowFactor > loadFactor) {
                initialSize *= 2;
                nowFactor /= 2;
                List oldlists = lists;
                lists = new ArrayList<>();
                for (int i = 0; i < initialSize; i++) {
                    lists.add(new LinkedList<Node>());
                }
                for (int i = 0; i < initialSize; i++) {
                    Iterator<Node> iter;
                    if (i < initialSize / 2) {
                        iter = lists.get(i).iterator();
                    } else {
                        iter = lists.get(i - initialSize / 2).iterator();
                    }
                    List nowlist = lists.get(i);
                    while (iter.hasNext()) {
                        Node current = iter.next();
                        if (((Integer.MAX_VALUE & current.key.hashCode()) % initialSize == i)) {
                            nowlist.add(current);
                        }
                    }
                }
            }
            List here = lists.get(((Integer.MAX_VALUE & key.hashCode())) % initialSize);
            Node newone = new Node(key, value);
            here.add(new Node(key, value));
        }
    }
    public Set<K> keySet() {
        return this.keys;
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }
}
