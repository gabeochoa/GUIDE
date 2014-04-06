/*
author: Gabriel Ochoa & Shane Thompson
@start-date: 12/3/2013
*/
//
#ifndef GRAPH_H
#define GRAPH_H

#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#include <utility>
#include <map>
#include <set>
#include <sstream>
#include <algorithm>
#include <iterator>
#include <queue>
#include <thread>
#include <stdexcept>
#include <stack>
#include <cmath>
#include <list>

#define DEBUGH 0

class Graph
{

private:

struct Vertex
{
	std::string id; //Identification String
	float value;//Value of Vertex
	int matrixId; //Its row number in adj matrix.

	int relationsToward; //number of vertexes that point to this one
	int relationsAway; 
	//number of vertexes that this one points to (connVert.size())
	
	std::map<std::string, int> connVert;
	//idNums of the vertexes that this vertex
	// points to and weight of edge that connects them.

	Vertex(){}
	
	Vertex(std::string id, float value, int matrixId)
	: id(id), value(value), matrixId(matrixId), relationsToward(0), relationsAway(0)
	{
		
	}

	bool operator<(const Vertex& other) const
	{
		return this->id.compare(other.id) == -1; //Return if this id is less than compared id.
	}

	//Should only be used for try/catch statements.
	Vertex& operator=(const Vertex& other){
		if(this != &other){

			this->id = other.id;
			this->value = other.value;
			this->matrixId = other.matrixId;
			this->relationsToward = other.relationsToward;
			this->relationsAway = other.relationsAway;

			this->connVert = other.connVert;
		}
		
		return *this;
	}
};

struct Graphoozzle
{
	int numberVert; //Number of Verticies in entire graph
	int numberEdges; //Number of edges in entire graph
	int maxRow;//checked
	std::map<std::string,Vertex> vertArr; //vector of Vertex Structs
	std::map<std::string, int> stringtoMatrx;//checked
	std::map<int, std::string> matrxToString;//checked
	std::vector< std::vector<int> > weightedAdj;//checked
	std::map<float, std::string> valToID;//chekc	
//	std::vector< std::set<Graph::Vertex> > curKruskals;
//	int lastKrus;
	Graphoozzle() {
		maxRow = -1;
//		lastKrus = -1;
	}
};

	std::vector<std::string> splitWordByDelim(std::string sentence, char delim);
	bool depthFirstTraversal(Graph::Vertex & v, std::string &val, std::vector<Graph::Vertex> & visited);

	bool check_edge(const Graph& g);

	bool check_vert(const Graph& g);

	bool cmp_edgepair(
	const std::pair<std::pair<std::string, std::string> , int> &a,
	const std::pair<std::pair<std::string, std::string> , int> &b
	) const ;
public:
	Graphoozzle graphoo;

	Graph();
	~Graph();	

	void print();

	//Helper function: returns true if the edge has non-valid vertex id1 .
	inline bool is_No_Edge(/*const*/std::string & id1);

	//Returns an the integer weight of previous identical edge
	inline int edge_Conflict(std::string origin, std::string destin);

	void edge_Counselor(std::vector<std::string> origin, std::vector<std::string> destin);

	std::string eraseLeadingSpaces(std::string & stringVec);
	bool isSpace(char c);

	bool delete_vertex(std::string id); 
	//deletes the vertex with the id passed in. returns true if deleted
	
	bool delete_edge(std::string id1, std::string id2); 
	//deletes the edge that is id1->id2. returns true if deleted

	Vertex search_for_vertex(std::string id) const;
	//searches for and returns the vertex with the id passed in.

	std::vector<Vertex> search_for_edge(std::string id1, std::string id2); 
	//searches for and returns the edge id1->id2. 

	std::vector< std::set<Graph::Vertex> > kruskal(Graph &g) const;
	//returns the resultant test from kruskal's alogrithm
    
    void aux_dijkstra(Graph::Vertex root, Graph::Vertex & dest, int & currentpath, std::vector<std::string> & path, std::map<std::string, int> & dist, std::map<std::string, bool> & visited);
    
    std::vector<std::string> dijkstra(std::string id1, std::string id2);

	//Finds the total value of the path by summing the weights between the vertexes
	int sumPath(std::vector<std::string> & path);

	//Given an array of path values, find the min.
	int findMinPathV(std::vector<int> pathValues);

	//Read A Graph From A File
	void readFromFile(std::string file);
	//Write A Graph To A File
	void writeToFile(std::string file);
	//Empty
	bool empty();
	//Add Edge
	void addEdge(std::string id1, std::string id2, int weight);
	//Add Vertex
	void addVertex(std::string id, float val);
	//Increase the collizzle of each adjacency matrix.
	void incCol();
	//Count Connected Components
	int numConnectedComponents();
	//Tree Check
	bool tree();
	//Minimum Weight Components
	void minWeightComponent(std::string src);
	//Depth First Search
	bool DFS(std::string originId, std::string destinId);
	//Auxilary Method for DFS
	void aux_DFS(int originMatEnt, int destinMatEnt, bool & isDestinReachable, std::vector<bool> & unvisited);//Auxilary Method for DFS
	//Breadth First Search
	bool BFS(std::string originId, std::string destinId);
	//Closeness
	int closeness(std::string v1, std::string v2);
	//Partition
	bool partitionable();
	//* Subgraph Test
	bool isSubGraph(const Graph& g);

	//Print Valued Path
	void printPathCloseVal(float value);
	
	//Returns a vector copying dupus minus its duplicate entries.
	std::vector<std::vector<std::string> > deDupify(std::vector<std::vector<std::string> > & dupus);

	bool isPathDup(std::vector<std::string> /*const*/ & dupless, std::vector<std::string> /*const*/ & potDupper);

	//eliminates the inferior invalid permutations will be purged utilizating this function.
	void validify(/*const*/ std::vector<std::vector<std::string> > & invalidSack, std::vector<std::vector<std::string> > & glorySackOfCorrect);

	//aux function for purging
	bool isValidConnect(std::string id1, std::string id2);

	void getFloatPathValues(std::vector<float> &floatValContainer, /*const*/ std::vector<std::vector<std::string > > & stringPathContainer);

	//returns closest path value.
	float findCloseAux(float desiredVal, const std::vector<float> & listOfPathVals);

	//Stores the paths with path value 'closestVal' in closestPathStringHolder.
	void getPathsWithVal(float closestVal, std::vector<std::vector<std::string> > & closestPathStringHolder, std::vector<std::vector<std::string> > & pathPermutates); 

	//Yet another aux for closest paths; prints the closests paths in desired format.
	void printFormatedClosestPaths(std::vector<std::vector<std::string> > & closestPaths);

	//Are all graphs reachable?
	bool isConnected();

	//No redundant connections.
	bool is_acyclic();

	void edgeSort(std::map< std::pair<std::string,std::string>,int> edgeSet) const ;
	void unionSet(std::set<Graph::Vertex> & set1,  std::set<Graph::Vertex> & set2) const;
	
	std::vector< std::set<Graph::Vertex> >::iterator searchForSet(std::string u, std::vector<std::set<Graph::Vertex> > & setList) const;

	std::vector<std::vector<std::string>> computeAllPaths();

	void bruteForcePath(float value);
	
	bool recur_follow(std::vector<std::string> & testpath);
};

#endif //GRAPH_H

