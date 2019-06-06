import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SeparableEnemySolver {

    Graph g;

    /**
     * Creates a SeparableEnemySolver for a file with name filename. Enemy
     * relationships are biderectional (if A is an enemy of B, B is an enemy of A).
     */
    SeparableEnemySolver(String filename) throws java.io.FileNotFoundException {
        this.g = graphFromFile(filename);
    }

    /** Alterntive constructor that requires a Graph object. */
    SeparableEnemySolver(Graph g) {
        this.g = g;
    }

    /**
     * Returns true if input is separable, false otherwise.
     */
    public boolean isSeparable() {
        Set<String> s1, s2, nows, others;
        s1 = new HashSet<>();
        s2 = new HashSet<>();
        Set<Graph.Node> contains = new HashSet<>();
        for (String s : g.labels()) {
            if (s1.contains(s) || s2.contains(s)) {
                continue;
            } else {
                s1.add(s);
                Graph.Node curr = g.getNode(s);
                nows = s2;
                others = s1;
                if (!helpseparable(nows, others, curr, contains))
                    return false;
            }
        }
        return true;
    }

    /**
     *轮番将与curr相连的Node加入到s1 and s2中，无法完成则返回false，否则true
     */
    private boolean helpseparable(Set<String> nows, Set<String> others, Graph.Node curr, Set<Graph.Node> contains) {
        contains.add(curr);
        for (Graph.Node newone : curr.getNeighbors()) {
            if (others.contains(newone.getLabel())) {
                return false;
            }
            nows.add(newone.getLabel());
        }
        for (Graph.Node newone : curr.getNeighbors()) {
            if (!contains.contains(newone) && !helpseparable(others, nows, newone, contains)) {
                return false;
            }
        }
        return true;
    }

    /* HELPERS FOR READING IN CSV FILES. */

    /**
     * Creates graph from filename. File should be comma-separated. The first line
     * contains comma-separated names of all people. Subsequent lines each have two
     * comma-separated names of enemy pairs.
     */
    private Graph graphFromFile(String filename) throws FileNotFoundException {
        List<List<String>> lines = readCSV(filename);
        Graph input = new Graph();
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                for (String name : lines.get(i)) {
                    input.addNode(name);
                }
                continue;
            }
            assert(lines.get(i).size() == 2);
            input.connect(lines.get(i).get(0), lines.get(i).get(1));
        }
        return input;
    }

    /**
     * Reads an entire CSV and returns a List of Lists. Each inner
     * List represents a line of the CSV with each comma-seperated
     * value as an entry. Assumes CSV file does not contain commas
     * except as separators.
     * Returns null if invalid filename.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<List<String>> readCSV(String filename) throws java.io.FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
        }
        return records;
    }

    /**
     * Reads one line of a CSV.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(",");
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next().trim());
        }
        return values;
    }

    /* END HELPERS  FOR READING IN CSV FILES. */

}
