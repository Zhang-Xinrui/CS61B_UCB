package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/** An implementation of the ExtrinsicMinPQ using heap.
 *  You may find it very similar to NaiveMinPQ, but
 *  according to the "is-a" rule, I didn't use "extends".
 *  @author  <ZXR> 03-17-20
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> items;
    private HashMap<T, Integer> map;

    public ArrayHeapMinPQ() {
        this.items = new ArrayList<>();
        this.map = new HashMap<>();
    }
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("item already exists");
        }
        map.put(item, size());
        items.add(new PriorityNode(item, priority));
        rise(size() - 1);
    }
    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
    }
    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("no elements in the PQ");
        }
        return items.get(0).getItem();
    }
    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T removeSmallest() {
        T removed = getSmallest();
        map.remove(removed);
        swap(0, size() - 1);
        items.remove(size() - 1);
        sink(0);
        return removed;
    }
    /* Returns the number of items in the PQ. */
    public int size() {
        return items.size();
    }
    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("item not found");
        }
        int index = map.get(item); 
        double oldpriority = items.get(index).priority;
        items.set(index, new PriorityNode(item, priority));
        if (priority < oldpriority) {
            rise(index);
        } else {
            sink(index);
        }
    }
    
    private void swap(int index1, int index2) {
        PriorityNode temp = items.get(index1);
        items.set(index1, items.get(index2));
        items.set(index2, temp);
        map.put(items.get(index1).item, index1);
        map.put(items.get(index2).item, index2);
    }

    private void rise(int index) {
        int child = index, parent = parent(index);
        while (parent >= 0 && compare(child, parent)) {
            swap(child, parent);
            child = parent; parent = parent(child);
        }
    }
    private void sink(int index) {
        int parent = index, child = leftchild(index), min;
        while (child < size()) {
            min = parent;
            if (compare(child, min)) {
                min = child;
            }
            child++;
            if (child < size() && compare(child, min)) {
                min = child;
            }
            if (min == parent) {
                return;
            }
            swap(min, parent); parent = min; child = leftchild(parent);
        }
    }
    private boolean compare(int index1, int index2) {
        return (items.get(index1).compareTo(items.get(index2)) < 0);
    }
    private int leftchild(int index) { return index * 2 + 1;}
    private int parent(int index) { return (index - 1) / 2;}

    /**
     *  This class is totally copied from NaiveMinPQ.java
     */
    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
