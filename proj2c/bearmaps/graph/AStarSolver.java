package bearmaps.graph;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

import bearmaps.proj2c.MinPQ.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private LinkedList<Vertex> solution;
    private double timeSpent;
    private int numStates;
    private final double INF = Double.POSITIVE_INFINITY;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        ArrayHeapMinPQ<Vertex> PQ = new ArrayHeapMinPQ<>();
        solution = new LinkedList<>();
        Map<Vertex, Double> distToStart = new HashMap<>();
        Map<Vertex, Double> distToEnd = new HashMap<>();
        Map<Vertex, Vertex> edgeTo = new HashMap<>();
        Stopwatch sw = new Stopwatch();

        PQ.add(start, 0);
        distToStart.put(start, 0.0);
        while (PQ.size() > 0) {
            if (PQ.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                solution.addFirst(PQ.getSmallest());
                solutionWeight = distToStart.get(PQ.getSmallest());
                while(!solution.getFirst().equals(start)) {
                    solution.addFirst(edgeTo.get(solution.getFirst()));
                }
                timeSpent = sw.elapsedTime();
                return;
            }
            numStates++;
            if (sw.elapsedTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solutionWeight = 0;
                timeSpent = sw.elapsedTime();
            }
            List<WeightedEdge<Vertex>> neighbours = input.neighbors(PQ.removeSmallest());
            for (WeightedEdge<Vertex> edge: neighbours) {
                if (!distToStart.containsKey(edge.to())) {
                    distToStart.put(edge.to(), INF);
                }
                if (!distToEnd.containsKey(edge.to())) {
                    distToEnd.put(edge.to(), input.estimatedDistanceToGoal(edge.to(), end));
                }
                if (distToStart.get(edge.from()) + edge.weight() < distToStart.get(edge.to())) {
                    distToStart.put(edge.to(), distToStart.get(edge.from()) + edge.weight());
                    edgeTo.put(edge.to(), edge.from());
                    if (PQ.contains(edge.to())) {
                        PQ.changePriority(edge.to(), distToStart.get(edge.to()) + distToEnd.get(edge.to()));
                    } else {
                        PQ.add(edge.to(), distToStart.get(edge.to()) + distToEnd.get(edge.to()));
                    }
                }
            }
        }
        outcome = SolverOutcome.UNSOLVABLE;
        timeSpent = sw.elapsedTime();
        solutionWeight = 0;
    }
    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        return solution;
    }
    public double solutionWeight() {
        return solutionWeight;
    }
    public int numStatesExplored() {
        return numStates;
    }
    public double explorationTime() {
        return timeSpent;
    }
}
