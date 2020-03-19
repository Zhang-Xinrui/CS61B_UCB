package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private KDTree kdtree;
    private Map<Point, Node> pointToNode;//The KDTree class only supports Point, so here is a map.

    private MyTrieSet trieSet;
    private Map<String, List<Node>> nameToNodes;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);

        List<Node> nodes = this.getNodes(); //Construct the kdTree.
        List<Point> points = new LinkedList<>();
        pointToNode = new HashMap<>();
        for (Node node: nodes) {
            if (!this.neighbors(node.id()).isEmpty()) {
                Point nodePoint = new Point(node.lon(), node.lat());
                pointToNode.put(nodePoint, node);
                points.add(nodePoint);
            }
        }
        kdtree = new KDTree(points);

        trieSet = new MyTrieSet();  //Construct the TrieSet.
        nameToNodes = new HashMap<>();
        for (Node node: nodes) {
            if (node.name() != null) {
                String cleanedName = cleanString(node.name());
                trieSet.add(cleanedName);
                if (!nameToNodes.containsKey(cleanedName)) {
                    nameToNodes.put(cleanedName, new LinkedList<>());
                }
                nameToNodes.get(cleanedName).add(node);
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point nearestPoint = kdtree.nearest(lon, lat);
        return pointToNode.get(nearestPoint).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String cleanedPrefix = cleanString(prefix);
        List<String> matchedNames = trieSet.keysWithPrefix(cleanedPrefix);
        Set<String> result = new HashSet<>();//Use a set to avoid duplicates.
        if (matchedNames != null) {
            for (String cleanedName: matchedNames) {
                for (Node node: nameToNodes.get(cleanedName)) {
                    result.add(node.name());
                }
            }
        }
        return new LinkedList<>(result);
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> result = new LinkedList<>();
        String cleanedLocationName = cleanString(locationName);
        if (nameToNodes.containsKey(cleanedLocationName)) {
            for (Node node: nameToNodes.get(cleanedLocationName)) {
                Map<String, Object> locationInfo = new HashMap<>();
                locationInfo.put("lat", node.lat());
                locationInfo.put("lon", node.lon());
                locationInfo.put("name", node.name());
                locationInfo.put("id", node.id());
                result.add(locationInfo);
            }
        }
        return result;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
