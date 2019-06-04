public class UnionFind {

    int[] vertices;
    int size;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        vertices = new int[n];
        size = n;
        for (int i = 0; i < n; i++) {
            vertices[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0 || vertex >= size) {
            throw new IllegalArgumentException("input not valid");
        }
        return;
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
       return -vertices[find(v1)];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return vertices[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (find(v1) == find(v2)) {
            return true;
        }
        return false;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        int size1, size2;
        size1 = sizeOf(v1);
        size2 = sizeOf(v2);
        if (size1 <= size2) {
            vertices[find(v2)] += vertices[find(v1)];
            vertices[find(v1)] = find(v2);
        } else {
            vertices[find(v1)] += vertices[find(v2)];
            vertices[find(v2)] = find(v1);
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int v1) {
        validate(v1);
        while (vertices[v1] >= 0) {
            v1 = vertices[v1];
        }
        return v1;
    }

}
