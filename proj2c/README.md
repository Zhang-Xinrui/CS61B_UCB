# BearMap

  This project is from Berkeley's course CS61B(2019 spring). It builds a map similar to Google Maps, which supports the functions below.
<ol>
<li>Map rastering.</li>
<li>Autocomplete and Search place.</li>
<li>Navigation</li>
</ol>

## Project Structure
  
  - [proj2c]
  * [bearmaps]
    + [graph]: 
      - [AStarGraph]: Defines the graph that can use A* search.
      - [AStarSolver](#astarsolver)
      - [AugmentedStreetMapGraph](#augmentedstreetmapgraph)
      - [GraphBuildingHandler](#graphbuildinghandler)
      - [Node]: Vertex representation for the graph.
      - [ShortestPathsSolver](#shortestpathssolver)
      - [SolverOutcome](#solveroutcome)
      - [StreetMapGraph]: Implements *AStarGraph*.
      - [WeightedEdge]: Edge representation for the graph.
    + [proj2c]
      - [KDTree](#kdtree)
      - [MinPQ](#minpq)
      - [server.handler](#serverhandler)
      - [TrieSet](#trieset)
      - [util](#util)
      - [MapServer]: The entrance of the whole project.
      - [MapServerInitializer]: Initialization statements
  * [static/page]: Front end files.

## Demos 

Map rastering<br>
<img src="https://media.giphy.com/media/gdNtnEYJpli6GJ3kXM/giphy.gif" >

Autocomplete and search<br>
<img src="https://media.giphy.com/media/mF49G0H2YzxoDUl4n6/giphy.gif" >

Navigation<br>
<img src="https://media.giphy.com/media/J5YeArVoe51PTftMtE/giphy.gif" >
