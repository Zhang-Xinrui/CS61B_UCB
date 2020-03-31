# BearMap

  This project is from Berkeley's course CS61B(2019 spring). It builds a map similar to Google Maps, which supports the functions below.
<ol>
<li>Map rastering.</li>
<li>Autocomplete and Search place.</li>
<li>Navigation</li>
</ol>

## Project Structure
  
  
  * [bearmaps]
    + [graph]: 
      - [Node]: Vertex representation for the graph.
      - [WeightedEdge]: Edge representation for the graph.
      - [AStarGraph]: Interface for the graph that can use A* search.
      - [StreetMapGraph]: Implements **AStarGraph**. Uses **GraphBuildingHandler** to help build the graph from the XML file. 
      - [AugmentedStreetMapGraph]: Extends **StreetMapGraph**. Supports prefix-match and find-closest Using **TrieSet** and **KDTree**.
      - [GraphBuildingHandler]: Helps to build the graph from the XML file.
      - [ShortestPathsSolver]: Interface for shortest path solvers.
      - [AStarSolver]: Implements **ShortestPathsSolver** using A* search and **MinPQ**.
      - [SolverOutcome]: An enum class expressing different types of A* results: solved, unsolvable and timeout.
    + [proj2c]
      - [KDTree]
      - [MinPQ]: A special type of priority queue. The priority is extrinsic to the object and we can change the priority of a object in the queue.
      - [TrieSet]
      - [server.handler]
        * [APIRouteHandler]: The base class that defines the procedure for handling an API request.
        * [impl]：Classes for handling different types of API requests.
        * [APIRouteHandlerFactory]: Defines the action that needs to be taken in case of each request.
        * [Router]: A helper for the **RoutingAPIHandler**.
      - [util]
      - [MapServer]: The entrance of the whole project.
      - [MapServerInitializer]: Initialization statements before the server main loop. Builds the graph of the bearmap and instantiates all types of APIhandlers.
  * [static/page]: Front end files.

## Demos 

Map rastering<br>
<img src="https://media.giphy.com/media/gdNtnEYJpli6GJ3kXM/giphy.gif" >

Autocomplete and search<br>
<img src="https://media.giphy.com/media/mF49G0H2YzxoDUl4n6/giphy.gif" >

Navigation<br>
<img src="https://media.giphy.com/media/J5YeArVoe51PTftMtE/giphy.gif" >
