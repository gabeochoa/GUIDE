/*
author: Gabriel Ochoa & Shane Thompson
@start-date: 12/3/2013
*/
//
#define DEBUG 0
#define DEBUGK 0
#define DEBUGR 0
#define DEBUGPV 0

#include "Graph.h"

/*
	Constructor for Graph object, its pretty cool
	it initializes some variables to 0
*/
Graph::Graph()
{
	graphoo.numberVert = 0;
	graphoo.numberEdges = 0;	
}

Graph::~Graph()
{

}

inline void Graph::incCol()
{
	for(int i = 0; i < graphoo.maxRow - 1; ++i)
		graphoo.weightedAdj.at(i).push_back(0);
}

void Graph::addVertex(std::string id, float value){
	#if DEBUG
	std::cout << "Inserting: " << id << std::endl;
	#endif
	
	if(value <= 0) //Invalid vertex value.
		return;
	Vertex v(id, value, ++(graphoo.maxRow));
	graphoo.vertArr.insert(std::pair<std::string, Vertex>(id,v));
	//Add last row.
	std::vector<int> newbie;
	for(int curCol = 0; curCol <= graphoo.maxRow; ++curCol){//initialize the columns of this new row.
		newbie.push_back(0);
	}

	graphoo.weightedAdj.push_back(newbie);

	/*Make matrxToString and stringtoMatrx*/
	std::pair<int, std::string> mat2str(graphoo.maxRow, id);
	std::pair<std::string, int> str2mat(id, graphoo.maxRow);

	graphoo.matrxToString.insert(mat2str);
	graphoo.stringtoMatrx.insert(str2mat);

	std::pair<float, std::string> vti(value, id);
	graphoo.valToID.insert(vti);

	incCol();
	graphoo.numberVert++;

	/*std::pair<int,std::string> p1(nextIndex, id);
	std::pair<std::string, int> p2(id, nextIndex);		
	graphoo.matrxToString.insert(p1);
	graphoo.stringtoMatrx.insert(p2);*/
//	if(graphoo.numberVert >= 5+graphoo.lastKrus)
//	{
//		graphoo.curKruskals = kruskal(*this);
//		graphoo.lastKrus = graphoo.numberVert;
//	}
}

void Graph::addEdge(std::string id1, std::string id2, int weight){
	#define Vert_temp1 graphoo.vertArr.at(id1)
	#define Vert_temp2 graphoo.vertArr.at(id2)

	std::pair<std::string, int> edgefor1(id2, weight);
	Vert_temp1.connVert.insert(edgefor1);	
	
	#if DEBUG
	std::cout << "(ADDEDGE) Size of connVert= " << Vert_temp1.connVert.size() << std::endl;
	#endif

	Vert_temp1.relationsAway++;
	Vert_temp2.relationsToward++;

	graphoo.numberEdges++;
} 

inline bool Graph::is_No_Edge(/*const*/std::string & id1){
	try{
		Graph::Vertex a;
		a = graphoo.vertArr.at(id1);
	}
	catch(const std::out_of_range &oor){
		return true;
	}

	return false;
}

inline int Graph::edge_Conflict(std::string origin, std::string destin){
	int testWeight;
	try{
		testWeight = graphoo.vertArr.at(origin).connVert.at(destin);
	}
	catch(const std::out_of_range &oor){
		testWeight = 0;
	}

	return testWeight;
		

}

//Uses search in order to be lazily concise.
bool Graph::delete_vertex(std::string id)
{
	return false;/*		
	const auto NOT_FOUND = nullptr;
	bool deleted = false;
	//make sure to delete all connVerts that contain 
	Vertex victim = search_for_vertex(id);
	if(victim != NOT_FOUND)
	{
		victim.connVert.~map();
		deleted = true;
	}
	return deleted;
	*/
}

bool Graph::delete_edge(std::string id1, std::string id2)
{
	return false;
	/*
	const auto NOT_FOUND = nullptr;
	bool deleted = false;
	Vertex brother = search_for_vertex(id1);
	Vertex sister = search_for_vertex(id2);

	if(brother != NOT_FOUND && sister != NOT_FOUND)
	{
		if(brother.connVert.find(id2) != connVert.end())
		brother.connVert.erase(id2);
		if(sister.connVert.find(id1) != connVert.end())
		sister.connVert.erase(id1);

		deleted = true;
	}
	return deleted;
	*/
}

//Empty
bool Graph::empty()
{
	return graphoo.numberVert==0;
}

//Count Connected Components
int Graph::numConnectedComponents()
{
	return (kruskal(*this).size());
}

std::vector< std::set<Graph::Vertex> > Graph::kruskal(Graph &g) const {
//	if(graphoo.lastKrus >= graphoo.numberVert - 5)
//	return graphoo.curKruskals;
	
	std::vector<std::set<Graph::Vertex> > setList;//A = set of vectors.
	std::map< std::pair<std::string,std::string>, int > edgeSet; //idPair to weight
	std::map< std::pair<std::string,std::string>, int > mstEdges;

	#if DEBUGK
	std::cout << "\n\nStarting Kruskals" << std::endl;
	#endif

	#if DEBUGK
	std::cout << "\tCreating set for all V" << std::endl;
	#endif
	for(auto iter = graphoo.vertArr.begin(); iter != graphoo.vertArr.end(); iter++)
	{
		std::set<Graph::Vertex> vs;
		vs.insert(iter->second);
		setList.push_back(vs);
	}
#if DEBUGK
	std::cout << "\t\tFinished Creating Sets" << std::endl;
#endif

#if DEBUGK
	std::cout << "\tCreating EdgeSets " << std::endl;
#endif
	for(int i = 0; i < graphoo.maxRow; i++){
		for(int j = 0; j < graphoo.maxRow; j++)
		{
#if DEBUGK
std::cout << "\t\t Iteration [" << i << " , " << j << "]"<< std::endl;
#endif
#if DEBUGK
std::cout << "\t\t\t Getting id1 from matrix" << std::endl;
#endif
			
			std::string iddy1 = graphoo.matrxToString.at(i);
#if DEBUGK			
std::cout << "\t\t\t Getting id2 from matrix: " << std::endl;
#endif
			std::string iddy2 = graphoo.matrxToString.at(j);
#if DEBUGK
std::cout << "\t\t\t Creating Pairs from id's" << std::endl;
#endif
			std::pair<std::string,std::string> edgePair(iddy1, iddy2);
#if DEBUGK
std::cout << "\t\t\t Pairing pairs with weight" << std::endl;
#endif
			std::pair<std::pair<std::string,std::string>, int> edgePairedWeight(edgePair, graphoo.weightedAdj[i][j]);
#if DEBUGK
std::cout << "\t\t\t Inserting paired weights to edgeSet" << std::endl;
#endif
			edgeSet.insert(edgePairedWeight);
		}
	}
	#if DEBUGK
	std::cout << "\t\tFinished Placing all edges in set" << std::endl;

#endif
	edgeSort(edgeSet);

	#if DEBUGK
	std::cout << "EdgeSet sorted" << std::endl;
	#endif
//	int counter = 0;
	for(auto iter = edgeSet.begin(); iter != edgeSet.end(); iter++)
	{
	//	std::cout << ++counter << std::endl;
		#define tempItty iter->first
		if(tempItty.first == tempItty.second)
		{
			mstEdges.insert(*iter); //a+=edge
			auto id1Set = searchForSet(tempItty.first, setList);
			auto id2Set = searchForSet(tempItty.second, setList);
			unionSet(*id1Set, *id2Set); //union u v
			//id2Set.~set(); //delete v
			edgeSet.erase(iter); //delete edge from sorted edge list
			setList.erase(id2Set); //delete redudent set
			
			//for(auto it = setList.begin(); 
			//	it != setList.end(); it++){
			//	Graph::Vertex secondIdV = graphoo.vertArr.at(temp.second); 
			//	auto loc = it->find(secondIdV);
			//	auto curSet = it->end();
			//	if( loc == it->begin() || loc--->id != curSet--->id) //Tricky way of checking if loc = end iterator of set.
			//	{
			//		setList.erase(it);
			//		break;
			//	}
			//}
		}
	}
#if DEBUGK
std::cout << "END OF KRUSKALS" << std::endl;
#endif
	return setList;
}
//Tree Check
bool Graph::tree()
{
	return (graphoo.numberEdges == graphoo.numberVert - 1 && isConnected() && is_acyclic()); 
}

bool Graph::isConnected()
{
	bool answer = true;
	if(graphoo.numberVert > 1)
	{
		std::vector< std::set<Graph::Vertex> > possiblePaths = kruskal(*this);
		if(possiblePaths.size() != 1)
			answer = false;
	} 
	
	return answer;
}

bool Graph::is_acyclic() {
	bool answer = false;
	std::vector< std::vector<int> > adjM2 = graphoo.weightedAdj;
	int mxR = graphoo.maxRow;

	int k;
	for(k = 0; k < mxR; ++k)
	{
		int i;
		for(i = 0; i < mxR; ++i)
		{
			int j;
			for(j = 0; j < mxR; ++j)
				adjM2[k][i] = adjM2[k][j] * adjM2[j][i];
		}
	}
	std::vector< std::vector<int > > adjM3 = graphoo.weightedAdj;
	for(k = 0;  k < mxR; ++k)
	{
		int i;
		for(i = 0; i < mxR; ++i)
		{
			int j;
			for(j =0; j < mxR; ++j)
				adjM3[j][i] = adjM3[k][j] * adjM2[j][i];
		}
	}

	for(k = 0; k < mxR; ++k)
	{
		if(adjM3[k][k] != 0)
			answer = false;
	}
	
	return answer;
}


void Graph::edgeSort(std::map< std::pair<std::string,std::string>,int> edgeSet) const
{
	std::vector<
		std::pair< 
			std::pair<std::string, std::string> , int
		>
	> pairs(edgeSet.begin(), edgeSet.end());

	std::sort(pairs.begin(), pairs.end() );//(cmp_edgepair));
	edgeSet.clear();

	for(auto iter = pairs.begin(); iter != pairs.end(); iter++)
	edgeSet.insert(*iter);

	
}

bool Graph::cmp_edgepair(
	const std::pair<std::pair<std::string, std::string> , int> &a,
	const std::pair<std::pair<std::string, std::string> , int> &b
	) const
{
	return (a.second < b.second);
}


void Graph::unionSet(std::set<Graph::Vertex> & set1,  std::set<Graph::Vertex> & set2) const
{
	for(auto iter = set2.begin(); iter != set2.end(); iter++)
		set1.insert(*iter);
}

std::vector<std::set<Graph::Vertex> >::iterator Graph::searchForSet(std::string u, std::vector<std::set<Graph::Vertex> > & setList) const
{
	Vertex uVec = search_for_vertex(u);
	
	for(auto iter = setList.begin(); iter != setList.end(); iter++)
	{
		auto it = iter->find(uVec);
		if(it != iter->end())
			return iter;
		else
			continue;
	}
	return setList.begin(); //Should never happen, since only used in kruskal
}



void Graph::minWeightComponent(std::string src)
{
	std::vector< std::set<Graph::Vertex> > minSpanTree = kruskal(*this);
	auto iterToSrc = searchForSet(src, minSpanTree);
	std::cout << "{{";
	/*Min Span Tree */
	for(auto iter = (*iterToSrc).begin(); iter != (*iterToSrc).end(); iter++){
		std::cout << (*iter).id << ", ";
	}
	std::cout << "}";

	for(auto iter = (*iterToSrc).begin(); iter != (*iterToSrc).end(); /*Iterator comes later.*/){
		std::cout << "(" << (*iter).id << ", ";

		if(++iter != (*iterToSrc).end()){
			std::cout << (*iter).id << ")} ";
		}
	}
}

//Depth First Search
bool Graph::DFS(std::string originId, std::string destinId){

	//Trivial cases.
	if(graphoo.numberVert == 0){
		return false;
	}
	else if(originId.compare(destinId) == 0){
		return true;
	}
	//aux works with ints for efficiency reasons, so get matrix entry numbers.
	int oriMatId = graphoo.stringtoMatrx.at(originId);
	int destMatId = graphoo.stringtoMatrx.at(destinId);
	
	/*Use a vector of bools wherein boolVec.at(i) = true means Vert at row i is not yet visited
	 *Note that we can't use maxRow, because maxRow is always numVert -1.
	 */
	std::vector<bool> unvisited(graphoo.numberVert, true);//All entries are initially true.

	bool isDestReachable = false; //Way of breaking the recursion;

	aux_DFS(oriMatId, destMatId, isDestReachable, unvisited);

	return isDestReachable;
}

void Graph::aux_DFS(int originMatEnt, int destinMatEnt, bool & isDestinReachable, std::vector<bool> & unvisited){

	if(isDestinReachable){//Break recursion if we are finished.
		return;
	}
	unvisited.at(originMatEnt) = false; //Set the vertex that we are currently visiting as visited.
	
	for(int i = 0; i <= graphoo.maxRow; ++i){

		if(unvisited.at(i) && graphoo.weightedAdj.at(originMatEnt).at(i) != 0){//If a connection exists, and we haven't been there before.
			if(i == destinMatEnt){//if connection is with desired destination.
				isDestinReachable = true;//we are done
				break;
			}
			else{
				aux_DFS(i, destinMatEnt, isDestinReachable, unvisited);//Recurse, carrying along the shared vector and bool var.
			}
		}
	}
}



//Breadth First Search
bool Graph::BFS(std::string originId, std::string destinId){
	#define vertexArray graphoo.vertArr
	#define originVertex graphoo.vertArr.at(originId)

	std::queue<Graph::Vertex> Q;
	std::vector<Graph::Vertex> visited;
	Graph::Vertex w;

	Q.push(originVertex);
	visited.push_back(originVertex);

	while(!Q.empty())
	{
	  w = Q.front();
	  Q.pop();
	  if(w.id == destinId)
		return true;

	  visited.push_back(w);
	  for(auto iter = w.connVert.begin();
	    iter != w.connVert.end(); iter++)
	  {

		 bool a = false;

		for(int i = 0; i < (int) visited.size(); i++){
		 if(visited.at(i).id == (iter->first)){
			a = true;
			break;   					
			}
		}
	   if(!a)
		{
			Vertex F = search_for_vertex(iter->first);
			Q.push(F);
			visited.push_back(F);	
		}			
	  }
	}

	return false;
}

int sumPath(std::vector<std::string> & path){
	return 0;
}

inline int findMinPathV(std::vector<int> pathValues){
	int minPathVal = pathValues.at(0);

	auto iter = pathValues.begin();
	while(++iter != pathValues.end()){ //while iter < begin(), iter++
		if(*iter < minPathVal){
			minPathVal = *iter;
		}
	}

	return minPathVal;
}

//Closeness:
int Graph::closeness(std::string v1, std::string v2)
{
	//One of the edges is invalid.
	if(is_No_Edge(v1) || is_No_Edge(v2))
		return -1;

	/*else*/ if(v1.compare(v2) == 0)
			return 0;

	/*else*/
	std::vector<std::string> path = dijkstra(v1, v2);
	
	int closeness = path.size();
	if(closeness == 0){//No path exists
		return -1;
	}
	
	return closeness;
}
//Partition
bool Graph::partitionable()
{
	return is_acyclic() && graphoo.numberVert > 1;
}

//* Subgraph Test
bool Graph::isSubGraph(const Graph& g)
{
	//Test to see if g has any vertexes that grapoo doesnt have
	bool vert = check_vert(g);
	if(!vert)
	return false;

	bool edge = check_edge(g);
	return vert && edge;
}

bool Graph::check_edge(const Graph& g)
{
	bool edgesbool = true;

	//Check if the edges of g' are in graphoo 
	std::vector<std::pair<std::string,std::string>> edges;
	
	for(auto iter = g.graphoo.vertArr.begin(); iter != g.graphoo.vertArr.end(); iter++)
	{
		auto mapE = (*iter).second.connVert;	
		for(auto iiter = mapE.begin(); iiter != mapE.end(); iiter++)
		{
			std::pair<std::string, std::string> e( (*iter).first ,(*iiter).first);
			edges.push_back(e);	
		}
	}
	
	for(auto iter = edges.begin(); iter != edges.end(); iter++)
	{
			std::string v1 = (*iter).first;
			std::string v2 = (*iter).second;
		try{
			Vertex v = graphoo.vertArr.at(v1);
			int we = v.connVert.at(v2);
			++we;
		}catch(const std::out_of_range &oor)
		{
			edgesbool =  false;
		}
	}	

	return edgesbool;

}

bool Graph::check_vert(const Graph& g)
{
	bool t = true;
	for(auto iter = g.graphoo.vertArr.begin(); iter !=  g.graphoo.vertArr.end(); iter++)
	{
		try
		{
			Vertex a = graphoo.vertArr.at((*iter).first);
		}
		catch(const std::out_of_range &oor)
		{
			t = false;
			break;
		}
	}
	return t;
}

inline bool Graph::recur_follow(std::vector<std::string> & testpath)
{
//	std::cout << "SIZE : " << testpath.size() << std::endl;

	switch(testpath.size())
	{
		case 0:
			{
				return false;
				break;
			}
		case 1:
			{
				return true;
				break;
			}
		case 2:{
			       std::string id1;
			       std::string id2;

			       id1 = *(testpath.begin());	
			       id2 = *(testpath.begin()+1);

			       if(isValidConnect(id1,id2))
			       {
				       return true;
			       }	
			       return false;
			       break;
		       }
		case 3:{
			       std::string id1 = *(testpath.begin());	
			       std::string id2 = *(testpath.begin()+1);
			       std::string id3 = *(testpath.begin()+2);		

			       if(isValidConnect(id1,id2))
			       {
				       if(isValidConnect(id2,id3))
					       return true;
			       }
			       return false;
			       break;
		       }
		case 4:{
			       std::string id1 = *(testpath.begin());	
			       std::string id2 = *(testpath.begin()+1);
			       std::string id3 = *(testpath.begin()+2);		
			       std::string id4 = *(testpath.begin()+3);

			       if(isValidConnect(id1,id2))
				       if(isValidConnect(id2,id3))
					       if(isValidConnect(id3, id4))
						       return true;
			       return false;
			       break;
		       }
		case 5:{
			       std::string id1 = *(testpath.begin());	
			       std::string id2 = *(testpath.begin()+1);
			       std::string id3 = *(testpath.begin()+2);		
			       std::string id4 = *(testpath.begin()+3);
			       std::string id5 = *(testpath.begin()+4);

			       if(isValidConnect(id1,id2))
				       if(isValidConnect(id2,id3))
					       if(isValidConnect(id3, id4))
						       if(isValidConnect(id4, id5))
							       return true;
			       return false;
			       break;
		       }
		case 6:{
			       std::string id1 = *(testpath.begin());	
			       std::string id2 = *(testpath.begin()+1);
			       std::string id3 = *(testpath.begin()+2);		
			       std::string id4 = *(testpath.begin()+3);
			       std::string id5 = *(testpath.begin()+4);
			       std::string id6 = *(testpath.begin()+5);

			       if(isValidConnect(id1,id2))
				       if(isValidConnect(id2,id3))
					       if(isValidConnect(id3, id4))
						       if(isValidConnect(id4, id5))
							       if(isValidConnect(id5,id6))
								       return true;
			       return false;
			       break;
		       }
		case 7:{
			       std::string id1 = *(testpath.begin());	
			       std::string id2 = *(testpath.begin()+1);
			       std::string id3 = *(testpath.begin()+2);		
			       std::string id4 = *(testpath.begin()+3);
			       std::string id5 = *(testpath.begin()+4);
			       std::string id6 = *(testpath.begin()+5);
			       std::string id7 = *(testpath.begin()+6);

			       if(isValidConnect(id1,id2))
				       if(isValidConnect(id2,id3))
					       if(isValidConnect(id3, id4))
						       if(isValidConnect(id4, id5))
							       if(isValidConnect(id5,id6))
								       if(isValidConnect(id6, id7))
									       return true;
			       return false;
			       break;
		       }
		case 8:{
			       std::string id1 = *(testpath.begin());	
			       std::string id2 = *(testpath.begin()+1);
			       std::string id3 = *(testpath.begin()+2);		
			       std::string id4 = *(testpath.begin()+3);
			       std::string id5 = *(testpath.begin()+4);
			       std::string id6 = *(testpath.begin()+5);
			       std::string id7 = *(testpath.begin()+6);
			       std::string id8 = *(testpath.begin()+7);

			       if(isValidConnect(id1,id2))
				       if(isValidConnect(id2,id3))
					       if(isValidConnect(id3, id4))
						       if(isValidConnect(id4, id5))
							       if(isValidConnect(id5,id6))
								       if(isValidConnect(id6, id7))
									       if(isValidConnect(id7, id8))
										       return true;
			       return false;
			       break;
		       }
		case 9:{
			       std::string id1 = *(testpath.begin());	
			       std::string id2 = *(testpath.begin()+1);
			       std::string id3 = *(testpath.begin()+2);		
			       std::string id4 = *(testpath.begin()+3);
			       std::string id5 = *(testpath.begin()+4);
			       std::string id6 = *(testpath.begin()+5);
			       std::string id7 = *(testpath.begin()+6);
			       std::string id8 = *(testpath.begin()+7);
			       std::string id9 = *(testpath.begin()+8);

			       if(isValidConnect(id1,id2))
				       if(isValidConnect(id2,id3))
					       if(isValidConnect(id3, id4))
						       if(isValidConnect(id4, id5))
							       if(isValidConnect(id5,id6))
								       if(isValidConnect(id6, id7))
									       if(isValidConnect(id7, id8))
										       if(isValidConnect(id8, id9))
											       return true;
			       return false;
			       break;
		       }

		case 10:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													return true;
				return false;
				break;
			}

		case 11:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														return true;
				return false;
				break;
			}


		case 12:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);
				std::string id12 = *(testpath.begin()+11);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														if(isValidConnect(id11, id12))
															return true;
				return false;
				break;
			}

		case 13:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);
				std::string id12 = *(testpath.begin()+11);
				std::string id13 = *(testpath.begin()+12);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														if(isValidConnect(id11, id12))
															if(isValidConnect(id12, id13))
																return true;
				return false;
				break;
			}

		case 14:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);
				std::string id12 = *(testpath.begin()+11);
				std::string id13 = *(testpath.begin()+12);
				std::string id14 = *(testpath.begin()+13);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														if(isValidConnect(id11, id12))
															if(isValidConnect(id12, id13))
																if(isValidConnect(id13, id14))
																	return true;
				return false;
				break;
			}

		case 15:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);
				std::string id12 = *(testpath.begin()+11);
				std::string id13 = *(testpath.begin()+12);
				std::string id14 = *(testpath.begin()+13);
				std::string id15 = *(testpath.begin()+14);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														if(isValidConnect(id11, id12))
															if(isValidConnect(id12, id13))
																if(isValidConnect(id13, id14))
																	if(isValidConnect(id14, id15))
																		return true;
				return false;
				break;
			}


		case 16:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);
				std::string id12 = *(testpath.begin()+11);
				std::string id13 = *(testpath.begin()+12);
				std::string id14 = *(testpath.begin()+13);
				std::string id15 = *(testpath.begin()+14);
				std::string id16 = *(testpath.begin()+15);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														if(isValidConnect(id11, id12))
															if(isValidConnect(id12, id13))
																if(isValidConnect(id13, id14))
																	if(isValidConnect(id14, id15))
																		if(isValidConnect(id15, id16))
																			return true;
				return false;
				break;
			}



		case 17:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);
				std::string id12 = *(testpath.begin()+11);
				std::string id13 = *(testpath.begin()+12);
				std::string id14 = *(testpath.begin()+13);
				std::string id15 = *(testpath.begin()+14);
				std::string id16 = *(testpath.begin()+15);
				std::string id17 = *(testpath.begin()+16);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														if(isValidConnect(id11, id12))
															if(isValidConnect(id12, id13))
																if(isValidConnect(id13, id14))
																	if(isValidConnect(id14, id15))
																		if(isValidConnect(id15, id16))
																			if(isValidConnect(id16, id17))
																				return true;
				return false;
				break;
			}


		case 18:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);
				std::string id12 = *(testpath.begin()+11);
				std::string id13 = *(testpath.begin()+12);
				std::string id14 = *(testpath.begin()+13);
				std::string id15 = *(testpath.begin()+14);
				std::string id16 = *(testpath.begin()+15);
				std::string id17 = *(testpath.begin()+16);
				std::string id18 = *(testpath.begin()+17);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														if(isValidConnect(id11, id12))
															if(isValidConnect(id12, id13))
																if(isValidConnect(id13, id14))
																	if(isValidConnect(id14, id15))
																		if(isValidConnect(id15, id16))
																			if(isValidConnect(id16, id17))
																				if(isValidConnect(id17, id18))
																					return true;
				return false;
				break;
			}



		case 19:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);
				std::string id12 = *(testpath.begin()+11);
				std::string id13 = *(testpath.begin()+12);
				std::string id14 = *(testpath.begin()+13);
				std::string id15 = *(testpath.begin()+14);
				std::string id16 = *(testpath.begin()+15);
				std::string id17 = *(testpath.begin()+16);
				std::string id18 = *(testpath.begin()+17);
				std::string id19 = *(testpath.begin()+18);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														if(isValidConnect(id11, id12))
															if(isValidConnect(id12, id13))
																if(isValidConnect(id13, id14))
																	if(isValidConnect(id14, id15))
																		if(isValidConnect(id15, id16))
																			if(isValidConnect(id16, id17))
																				if(isValidConnect(id17, id18))
																					if(isValidConnect(id18, id19))
																						return true;
				return false;
				break;
			}




		case 20:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);
				std::string id12 = *(testpath.begin()+11);
				std::string id13 = *(testpath.begin()+12);
				std::string id14 = *(testpath.begin()+13);
				std::string id15 = *(testpath.begin()+14);
				std::string id16 = *(testpath.begin()+15);
				std::string id17 = *(testpath.begin()+16);
				std::string id18 = *(testpath.begin()+17);
				std::string id19 = *(testpath.begin()+18);
				std::string id20 = *(testpath.begin()+19);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														if(isValidConnect(id11, id12))
															if(isValidConnect(id12, id13))
																if(isValidConnect(id13, id14))
																	if(isValidConnect(id14, id15))
																		if(isValidConnect(id15, id16))
																			if(isValidConnect(id16, id17))
																				if(isValidConnect(id17, id18))
																					if(isValidConnect(id18, id19))
																						if(isValidConnect(id19, id20))
																							return true;
				return false;
				break;
			}


		case 21:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);
				std::string id12 = *(testpath.begin()+11);
				std::string id13 = *(testpath.begin()+12);
				std::string id14 = *(testpath.begin()+13);
				std::string id15 = *(testpath.begin()+14);
				std::string id16 = *(testpath.begin()+15);
				std::string id17 = *(testpath.begin()+16);
				std::string id18 = *(testpath.begin()+17);
				std::string id19 = *(testpath.begin()+18);
				std::string id20 = *(testpath.begin()+19);
				std::string id21 = *(testpath.begin()+20);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														if(isValidConnect(id11, id12))
															if(isValidConnect(id12, id13))
																if(isValidConnect(id13, id14))
																	if(isValidConnect(id14, id15))
																		if(isValidConnect(id15, id16))
																			if(isValidConnect(id16, id17))
																				if(isValidConnect(id17, id18))
																					if(isValidConnect(id18, id19))
																						if(isValidConnect(id19, id20))
																							if(isValidConnect(id20, id21))
																								return true;
				return false;
				break;
			}


		case 22:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);
				std::string id12 = *(testpath.begin()+11);
				std::string id13 = *(testpath.begin()+12);
				std::string id14 = *(testpath.begin()+13);
				std::string id15 = *(testpath.begin()+14);
				std::string id16 = *(testpath.begin()+15);
				std::string id17 = *(testpath.begin()+16);
				std::string id18 = *(testpath.begin()+17);
				std::string id19 = *(testpath.begin()+18);
				std::string id20 = *(testpath.begin()+19);
				std::string id21 = *(testpath.begin()+20);
				std::string id22 = *(testpath.begin()+21);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														if(isValidConnect(id11, id12))
															if(isValidConnect(id12, id13))
																if(isValidConnect(id13, id14))
																	if(isValidConnect(id14, id15))
																		if(isValidConnect(id15, id16))
																			if(isValidConnect(id16, id17))
																				if(isValidConnect(id17, id18))
																					if(isValidConnect(id18, id19))
																						if(isValidConnect(id19, id20))
																							if(isValidConnect(id20, id21))
																								if(isValidConnect(id21, id22))
																									return true;
				return false;
				break;
			}

		case 23:{
				std::string id1 = *(testpath.begin());
				std::string id2 = *(testpath.begin()+1);
				std::string id3 = *(testpath.begin()+2);
				std::string id4 = *(testpath.begin()+3);
				std::string id5 = *(testpath.begin()+4);
				std::string id6 = *(testpath.begin()+5);
				std::string id7 = *(testpath.begin()+6);
				std::string id8 = *(testpath.begin()+7);
				std::string id9 = *(testpath.begin()+8);
				std::string id10 = *(testpath.begin()+9);
				std::string id11 = *(testpath.begin()+10);
				std::string id12 = *(testpath.begin()+11);
				std::string id13 = *(testpath.begin()+12);
				std::string id14 = *(testpath.begin()+13);
				std::string id15 = *(testpath.begin()+14);
				std::string id16 = *(testpath.begin()+15);
				std::string id17 = *(testpath.begin()+16);
				std::string id18 = *(testpath.begin()+17);
				std::string id19 = *(testpath.begin()+18);
				std::string id20 = *(testpath.begin()+19);
				std::string id21 = *(testpath.begin()+20);
				std::string id22 = *(testpath.begin()+21);
				std::string id23 = *(testpath.begin()+22);

				if(isValidConnect(id1,id2))
					if(isValidConnect(id2,id3))
						if(isValidConnect(id3, id4))
							if(isValidConnect(id4, id5))
								if(isValidConnect(id5,id6))
									if(isValidConnect(id6, id7))
										if(isValidConnect(id7, id8))
											if(isValidConnect(id8, id9))
												if(isValidConnect(id9, id10))
													if(isValidConnect(id10, id11))
														if(isValidConnect(id11, id12))
															if(isValidConnect(id12, id13))
																if(isValidConnect(id13, id14))
																	if(isValidConnect(id14, id15))
																		if(isValidConnect(id15, id16))
																			if(isValidConnect(id16, id17))
																				if(isValidConnect(id17, id18))
																					if(isValidConnect(id18, id19))
																						if(isValidConnect(id19, id20))
																							if(isValidConnect(id20, id21))
																								if(isValidConnect(id21, id22))
																									if(isValidConnect(id22, id23))
																										return true;
				return false;
				break;
			}



	}


	std::string id1;
	std::string id2;


	 auto iter = testpath.begin();
	if( iter+1 != testpath.end())
	{
		id1 = *iter;
		iter++;
		id2 = *iter;
	}
	else
	{
		return false;
	}
	
	do{ 
		if(isValidConnect(id1,id2))
		{
			id1 = id2;
			iter++;
			id2 = *iter;
		}
		else
		{
			return false;
		}	
	}
	while( iter != testpath.end() );
return true;
}


void Graph::bruteForcePath(float value)
{

	std::vector<std::string> A;

	for(auto iter = graphoo.vertArr.begin(); iter != graphoo.vertArr.end(); iter++)
	{
	        std::string fl = iter->first;
       		A.push_back(fl);
	}

	std::vector<std::vector<std::string>> allposs;
	do
	{
		for(unsigned i = 1; i<A.size(); i++)
		{
			std::vector<std::string> temp(A.begin(), A.begin()+i);
		
			if(recur_follow(temp))
				allposs.push_back(temp);
		}
		if(recur_follow(A))	
		allposs.push_back(A);
	}while ( std::next_permutation( A.begin(),A.end())  );

	

//	for(auto iter = allposs.begin(); iter != allposs.end(); iter++)
//	{
//		for(auto iiter =  (*iter).begin(); iiter !=  (*iter).end(); iiter++)
//		{
//			std::cout << *iiter << " -> ";
//		} 
//		std::cout << std::endl;
//	}

	std::vector<std::vector<std::string>> realposs;

	for(auto iter = allposs.begin(); iter != allposs.end(); iter++)
	{
		if( recur_follow(*iter) )
		{
			realposs.push_back(*iter);
		}
	}
//	realposs = allposs;

//	for(auto iter = realposs.begin(); iter != allposs.end(); iter++)
//	{
//		for(auto iiter =  (*iter).begin(); iiter !=  (*iter).end(); iiter++)
//		{
//			std::cout << *iiter << " -> ";
//		} 
//		std::cout << std::endl;
//	}
//

	int sumPath = 0;
	Vertex v;

	std::vector<std::vector<std::string>> closepath;
	std::stack< std::pair<float , std::vector<std::string> > > ed;

	for(auto iter = realposs.begin(); iter != realposs.end(); iter++)
	{
		for(auto iiter = (*iter).begin(); iiter != (*iter).end(); iiter++)
		{
			v = graphoo.vertArr.at(*iiter);
			sumPath += v.value;
		}
		if(ed.top().first > sumPath)
		{
			std::pair<float, std::vector<std::string> > paaf(sumPath,*iter);
			ed.push(paaf);
		}
		
//		if( (sumPath <= value + x) && (sumPath >= value-x) )
//		{
//			closepath.push_back(*iter);
//		}
		sumPath = 0;
	}
	
	int shortest = -1;

	while(!ed.empty())
	{
		if(shortest == -1)
		{
			shortest = ed.top().first;
		}
		if(shortest > ed.top().first)
		{
			shortest = ed.top().first;
		}
		if(shortest == ed.top().first)
		{
			closepath.push_back(ed.top().second);
		}
		ed.pop();
	}

	for(auto iter = closepath.begin(); iter != closepath.end(); iter++)
	{
		for(auto iiter = (*iter).begin(); iiter != (*iter).end(); iiter++)
		{
			std::cout << *iiter << " -> ";
		}
		std::cout << std::endl;
	}

}

//Print Valued Path
void Graph::printPathCloseVal(float value){
	
	//bruteForcePath(value);	
		//return;
	//insert base cases here

	std::vector<std::string> vertexVec;
	
	for(auto iter = graphoo.vertArr.begin(); iter != graphoo.vertArr.end(); iter++){
		std::string vertty = iter->first;
		vertexVec.push_back(vertty);
	}

	/*Get the permutations that we need.*/
	std::vector<std::vector<std::string> > allPathPermutes;
	do{
		for(unsigned i = 0; i < vertexVec.size(); i++){
			std::vector<std::string> temp(vertexVec.begin(), vertexVec.begin() + i);
			allPathPermutes.push_back(temp);
		}
		allPathPermutes.push_back(vertexVec);
	} while(std::next_permutation(vertexVec.begin(), vertexVec.end()));

	std::vector<std::vector<std::string> > nonDups;
	
	nonDups = deDupify(allPathPermutes);

	std::vector<std::vector<std::string> > validPermutes;

	validify(nonDups, validPermutes);
#if DEBUGPV
    std::cout << "After validify, before getFloatPathValues" << std::endl;
#endif
    
	std::vector<float> pathValues(validPermutes.size(), 0.0);
	getFloatPathValues(pathValues, validPermutes);

#if DEBUGPV
	std::cout << "After getFloat, before getPathsWithVal" << std::endl;
#endif
    
	/*Sort the list to prepare for the brute force val checking*/
	std::sort(pathValues.begin(), pathValues.end());
	//pathValues.sort();

	std::vector<std::vector<std::string> > closeValStrings;
    
    
	float closestValue = findCloseAux(value, pathValues);
 
#if DEBUGPV
    std::cout << "Closest Value is: " << closestValue << std::endl;
#endif
    
    getPathsWithVal(closestValue, closeValStrings, validPermutes);

	/*std::cout << "After getPathsVal, before getFloatPathValues" << std::endl;
	for(auto iter = validPermutes.begin(); iter != validPermutes.end(); iter++)
	{
		for(auto iiter = (*iter).begin(); iiter != (*iter).end(); iiter++)
		{
			std::cout << *iiter << " -> ";
		}
		std::cout << std::endl;
	}*/
	/*std::cout << "see above, cept now it's closeValStrings." << std::endl;
	for(auto iter = closeValStrings.begin(); iter != closeValStrings.end(); iter++)
	{
		for(auto iiter = (*iter).begin(); iiter != (*iter).end(); iiter++)
		{
			std::cout << *iiter << " -> ";
		}
		std::cout << std::endl;
	}*/
	//Finally, we are done and can output.
	printFormatedClosestPaths(closeValStrings);
}
//Assumes sorted listOfPathVals
float Graph::findCloseAux(float desiredVal, const std::vector<float> & listOfPathVals)
{
    if(desiredVal < 0)
        return -1;
    
    
	float minPathVal = listOfPathVals.front();
	float maxPathVal = listOfPathVals.back();
	
	float maxMinusVal = std::abs(maxPathVal - desiredVal);
	float valMinusMin = std::abs(desiredVal - minPathVal);
	
#if DEBUGPV
    std::cout << "Desired: " << desiredVal << std::endl;
    std::cout << "MxPV: " << maxPathVal << " MnPV: " << minPathVal << std::endl;
    std::cout << "MMV: " << maxMinusVal << " VMM: " << valMinusMin << std::endl;


    if(0 <= maxMinusVal)
    {}
    else
    {std::cout << "LOLOLOL" << std::endl;}
    if(0 >= valMinusMin)
    {}
    else
    {std::cout << "LOLOLOL2" << std::endl;}
#endif
    
    if(maxPathVal < desiredVal)
    {
        desiredVal = maxPathVal;
    }
    
    
    
	double i;
	for(i = 0; (i <= maxMinusVal || i >= valMinusMin); i+= .1){
     
#if DEBUGPV
        std::cout << "Looking for: " << desiredVal+i << " or " << desiredVal-i << std::endl;
#endif

        
        //Alternate checking steps ahead and steps back.
        if(i <= maxMinusVal){
			if(std::binary_search(listOfPathVals.begin(), listOfPathVals.end(), desiredVal + i)){//If in there, return it
                return desiredVal + i;
			}
		}
		if(i >= valMinusMin){
			std::binary_search(listOfPathVals.begin(), listOfPathVals.end(), desiredVal - i);//If in there, return it
            return desiredVal - i;
        }
    }
    
	return -1; //Will never happen, mathematically, but compiler complains otherwise
}




std::vector<std::vector<std::string> > Graph::deDupify(std::vector<std::vector<std::string> > & dupus){
	std::vector<std::vector<std::string> > deDupped;
	deDupped.push_back(dupus.front());//The first element can't be a dup.

	unsigned dupusInd = 0;
	while(dupusInd < dupus.size()){

		bool doAdd = true;//Helper bool, determines whether the thing is a dup.
		unsigned deDuppedInd = 0;//For each path in deDupped, compare it to given path dupus.at(index=dupusInd)
		while(deDuppedInd < deDupped.size()){//For each entry in vector that matters
			if(isPathDup(deDupped.at(deDuppedInd), dupus.at(dupusInd))){//See if the potential path in dupus is already in deDupped
				doAdd = false;
				break;
			}
			++deDuppedInd;//else, increment and continue the loop
		}
		if(doAdd){
			deDupped.push_back(dupus.at(dupusInd));
		}
		++dupusInd;//Go to next string in dupus
#if DEBUGPV
        std::cout << "size of deDupped: " << deDupped.size() << " index: " << deDuppedInd << std::endl;
		std::cout << "size of dupus: " << dupus.size() << " index: " << dupusInd << std::endl;
#endif
    }
	return deDupped;
}

//See's if the path in potDupper is equal to dupless
bool Graph::isPathDup(std::vector<std::string> & dupless, std::vector<std::string> & potDupper){
	if(dupless.size() != potDupper.size() || potDupper.size() == 0){//Trivial case, plus the latter or part checks for errors.
		return false;
	}
	
	for(int i = (int) dupless.size() - 1; i >= 0; --i){
		//std::cout << "Size of current dupless path" << dupless.size() << std::endl;
		if(dupless.at(i) != potDupper.at(i)){
			return false;
		}
	}
	
	//else
	return true;
}

 void Graph::validify(/*const*/ std::vector<std::vector<std::string> > & invalidSack, std::vector<std::vector<std::string> > & glorySackOfCorrect){

		for(unsigned pathIndex = 0; pathIndex < invalidSack.size(); ++pathIndex){
			bool shouldAdd = true;
			unsigned stringIndex = 0;

			while(stringIndex < invalidSack.at(pathIndex).size() - 1){
				try{//Poor man's if to prevent out of range errors.
					std::string & id1 = invalidSack.at(pathIndex).at(stringIndex);//Get id1
					++stringIndex;//get the next id, which exists by while condition
					std::string & id2 = invalidSack.at(pathIndex).at(stringIndex);//next id
					bool validConnect = isValidConnect(id1, id2);//the actual thing we are checking

					if(!validConnect){//If connection is not possible, break this stuff son.
						shouldAdd = false;
						break;
					}
				}
				catch(const std::out_of_range &oor){
					break;
				}
			}
			if(shouldAdd){
				glorySackOfCorrect.push_back(invalidSack.at(pathIndex));//It is pure enough for addition
			}	
		}
}

bool Graph::isValidConnect(std::string id1, std::string id2){
	Vertex x;
	Vertex y;
	try{
		x = graphoo.vertArr.at(id1);
		y = graphoo.vertArr.at(id2);
	}
	catch(const std::out_of_range &oor){
		return false;
	}

	if(x.connVert.find(id2) != x.connVert.end()){
		return true;
	}
	//else
	return false;
}
	
	/*try{
		Vertex v = graphoo.vertArr.at(v1);
		int we = v.connVert.at(v2);
		++we;
	}
	catch(const std::out_of_range &oor){
		return false;
	}*/
void Graph::getFloatPathValues(std::vector<float> & floatValContainer, /*const*/ std::vector<std::vector<std::string> > & stringPathContainer){
	int floatValIndex = 0;
	for(unsigned pathIndex = 0; pathIndex < stringPathContainer.size(); ++pathIndex){ //For each path.
		float curPathVal = 0;
		for(unsigned vertIndex = 0; vertIndex < stringPathContainer.at(pathIndex).size(); ++vertIndex){//For each vertex in path
			curPathVal += graphoo.vertArr.at(stringPathContainer.at(pathIndex).at(vertIndex)).value;//sum the values of the vertices in the path
		}
		floatValContainer.at(floatValIndex) = curPathVal;//Add the new path
		++floatValIndex;
	}
}

void Graph::getPathsWithVal(float closestVal, std::vector<std::vector<std::string> > & closestPathStringHolder, std::vector<std::vector<std::string> > & pathPermutates){
	for(unsigned pathInd = 0; pathInd < pathPermutates.size(); ++pathInd){
		float curPathVal = 0;
		for(unsigned vertInd = 0; vertInd < pathPermutates.at(pathInd).size(); ++vertInd){//For each vertex in path
			curPathVal += graphoo.vertArr.at(pathPermutates.at(pathInd).at(vertInd)).value;//sum the values of each vertex
		}
		if(curPathVal == closestVal){
			closestPathStringHolder.push_back(pathPermutates.at(pathInd));//if the path value is equal to closest, add it to path Collection.
		}
	}
}

void Graph::printFormatedClosestPaths(std::vector<std::vector<std::string> > & closestPaths){
	//Print first path, for simplification of outputting, this is seperate from other print loop.
    
  
    
	for(unsigned pathInd = 0; pathInd < closestPaths.size(); ++pathInd){//for each path in set
		for(unsigned vertInd = 0; vertInd < closestPaths.at(pathInd).size(); ++vertInd){//For each vert in path
			std::cout << closestPaths.at(pathInd).at(vertInd);
			if(vertInd < closestPaths.at(pathInd).size() - 1){//If we are at the last vert, we dont need an arrow
				std::cout << " -> ";
			}
		}
		std::cout << std::endl;
		if(pathInd < closestPaths.size() - 1){//If we are at last path in the set, then we don't need a an extra '---' line
			std::cout << "path index: " << pathInd << std::endl;
			std::cout << "closesPaths.size(): " << closestPaths.size() << std::endl;
			std::cout << "---" << std::endl;
		}
	}
}

Graph::Vertex Graph::search_for_vertex(std::string id) const
{
	return graphoo.vertArr.at(id);
}

std::vector<Graph::Vertex> Graph::search_for_edge(std::string id1, std::string id2)
{
	std::vector<Graph::Vertex> vertexes;
	Vertex iddy1 = graphoo.vertArr.at(id1);
	vertexes.push_back(iddy1);
	if(iddy1.connVert.find(id2) != iddy1.connVert.end())
		vertexes.push_back(graphoo.vertArr.at(id2));
	return vertexes;		
}

 bool Graph::isSpace(char c){
	switch(c){
		case ' ':
		case '\t':
		case '\n':
		case '\v':
		case '\f':
		case '\r':
			return true;
		default:		
		return false;
	}

	return false;
}

 std::string Graph::eraseLeadingSpaces(std::string & stringVec){
	while(isSpace(stringVec[0])){
			stringVec.erase(stringVec.begin());
	}
	return stringVec;		
}
/*

   @param string the file name to read from
 */
void Graph::readFromFile(std::string file_name)
{
	std::ifstream infile(file_name);
	std::vector<std::string> lines;

	for(std::string line = ""; std::getline(infile, line); line.erase())
		lines.push_back(line);

	//the first line of the file will contain the number of vertices in the graph, N.
	int N;
	N = atoi(lines.at(0).c_str());

	std::vector<int> clear;
	int i;
	for(i = 0; i < N; ++i)
		clear.push_back(0);

	// the next N lines of the file will contain:   name, value
	//    where name is a string (that does not contain a comma) and value is a floating point value, which may or may not be positive
	std::vector<std::string> splitVec;

	i = 1;
	for(; i < N+1; i++){
		graphoo.weightedAdj.push_back(clear);

		splitVec.clear();
		splitVec = splitWordByDelim(lines.at(i), ',');
		if(!splitVec.empty()){
			addVertex(splitVec.at(0), ::atof(splitVec.at(1).c_str()));

//#if DEBUG
//			std::cout << "Added vertex: " << splitVec[0];
//			std::cout << std::endl;
//#endif

		}
	}

	//the remainder of the file (each line after N+1) will contain the following information:     name1, name2, weight
	// where name1 and name2 are strings (neither of which will contain a comma) and the weight is an integer value which may or may not be positive.

	for(; i < (int) lines.size(); i++){
			//split line by commas
			splitVec.clear();
			splitVec = splitWordByDelim(lines[i], ',');
			if(!splitVec.empty()){

			//If splitVec is not empty, add the edge

			/*Weight of edge*/
			float toBeValue = atof(splitVec[2].c_str());

			//Before we add edge, we need to check if both of the vertexes exsist
			bool iNE1 = is_No_Edge(splitVec.at(0)); //true if vertex doesnt exsist
			bool iNE2 = is_No_Edge(splitVec.at(1));
		
			if(toBeValue <= 0) //edge weight is incorrect
			{
				#if DEBUGR
				std::cout << "Edge Weight is Incorrect";
				std::cout << "  " << toBeValue;
				std::cout << "\n";
				#endif				
				continue;
			}
			if(iNE1 || iNE2) //One or both vertexes doesnt exsist
			{
				#if DEBUGR
				std::cout << "One of the vericies is Incorrect";
				if(iNE1)				
				std::cout << "  Str1: "<< splitVec.at(0);
				if(iNE2)				
				std::cout << "  Str2: "<< splitVec.at(1);
				std::cout << "\n";
				#endif		
				continue;
			}
			//If vertexes exsist we need to get them to be able to add the weight
			Vertex a;
			int indexA = -1;
			int indexB = -1;
			int i = 0;

			for(auto iter = graphoo.vertArr.begin(); 
			    iter != graphoo.vertArr.end(); iter++)
			{
				a = iter->second;
				if(a.id == splitVec.at(0))
					indexA = i;						
				if(a.id == splitVec.at(1))
					indexB = i;						
				if(indexA != -1 && indexB != -1)
					break;
			}

			//Check if there is already an edge between the two vertexes
			const int NOT_CONFLICT = 0;
			//Returns weight if both exsist
			int possibleEdgeWeight = edge_Conflict(splitVec.at(0), splitVec.at(1));
		
			if(possibleEdgeWeight == NOT_CONFLICT)
			{
				//there is not already an edge		
				addEdge(splitVec.at(0), splitVec.at(1), toBeValue);
				(graphoo.weightedAdj)[indexA][indexB] = toBeValue;		
			}
			else //there is already an edge here
			{
				//check if new weight is less
				if(toBeValue < possibleEdgeWeight)
				{
					//update
					(graphoo.weightedAdj)[indexA][indexB] = toBeValue;
				}
				else
				{
					//ignore
					continue;
				}
			}

		}
		
	}
}
			
//Writes the grpah to the file.
void Graph::writeToFile(std::string file)
{
	std::vector<std::string> edges; 

	std::ofstream outfile(file);
	
	outfile << graphoo.numberVert << "\n";

	Vertex* a;
	std::string b;

	for(auto iter = graphoo.vertArr.begin(); iter != graphoo.vertArr.end(); iter++)
	{
		outfile << iter->first; //the string.
		outfile << ", ";
		a =  &((*iter).second);
		outfile << a->value << "\n";
		for(auto it = a->connVert.begin(); it != a->connVert.end(); it++)
		{
			edges.push_back(a->id);
			edges.push_back(", ");
			edges.push_back(""+it->first);
			edges.push_back(", ");
			edges.push_back(""+it->second);
			edges.push_back("\n");
		}
	}

	for(auto iter = edges.begin(); iter != edges.end(); iter++)
	{
		outfile << *iter;
	}
	outfile << std::endl;	
	
}

std::vector<std::string> Graph::splitWordByDelim(std::string sentence, char delim)
{
	std::vector<std::string> split;

	//#if DEBUG	
	//	std::cout << "\tSpliting " << sentence;
	//	std::cout << " by " << delim << "\t";
	//#endif		||||| 		|||||
	

	split.clear();

	std::stringstream ss(sentence);
	std::string item;

	while (std::getline(ss, item, delim)) {
		//std::cout << item << std::endl;    
		 item = eraseLeadingSpaces(item);   		
		//std::cout  << item << std::endl;  	
		 split.push_back(item);
	 }
	return split;
}

void Graph::print()
{
    
	auto vA = graphoo.vertArr;

	std::vector<Vertex> v;
	
	for( auto it = vA.begin(); it != vA.end(); ++it ) {
		v.push_back( it->second );
	}

	for(auto iter = v.begin(); iter != v.end(); iter++)
	{		
		 std::cout << "ID: " << (*iter).id;
		 std::cout << " Value: " << (*iter).value;
		 std::cout << std::endl;
	}

}



std::vector<std::string> Graph::dijkstra(std::string id1, std::string id2)
{
    //visited
    std::map<std::string, bool> visited;
    //path
    std::vector<std::string> path;
    path.push_back(id1);
    std::map<std::string, int> dist;
    int currentpath = 0;
    
    Vertex a = search_for_vertex(id1);
    Vertex b = search_for_vertex(id2);
    
    std::string name;
    
    for(auto iter = graphoo.vertArr.begin(); iter != graphoo.vertArr.end(); iter++)
    {
		bool v = false;
		int d = -1;
		
		name = iter->first;
		
		if(id1 == name)
		{
		    d = 0;
		    v = true;
		}
		
		visited.insert(std::pair<std::string, bool>(name, v));
		dist.insert(std::pair<std::string, int>(name, d));
    }
    
    aux_dijkstra(a,b, currentpath, path, dist, visited);
    return path;
}

void Graph::aux_dijkstra(
                         Graph::Vertex root,
                         Graph::Vertex & dest,
                         int & currentpath,
                         std::vector<std::string> & path,
                         std::map<std::string, int> & dist,
                         std::map<std::string, bool> & visited
                         )
{
    std::string neigh;
    int weight;
    
    auto neighbors = root.connVert;
  // std::cout << "Size of connvert= " << root.connVert.size() << std::endl;
  // std::cout << "ROOT: "<< root.id << std::endl;  
    std::string sV = "";
    int smallest = 0;
    
    for(auto iter = neighbors.begin(); iter != neighbors.end(); iter++)
    {
		neigh = iter->first;
		weight = iter->second;
		
		
		if(smallest == 0)
		{
		    smallest = dist.at(neigh);
		    sV = neigh;
		}
		
		if(dist.at(neigh) > (currentpath + weight))
		{
		    continue;
		}
		else//if the dist needs to be updated
		{
		    if(visited.at(neigh))
		    {
		        continue;
		    }
		    else
		    {
		        currentpath += weight;
		        dist.at(neigh) = currentpath;
		    }
		}

		if(dist.at(neigh) < smallest)
		{
		    smallest = dist.at(neigh);
		    sV = neigh;
		}
    }
   // std::cout << "sV is : " << sV << " : end string" <<std::endl;
   
    if(sV != " ")
    {
	visited.at(sV) = true;
	path.push_back(sV);
    }

    
    if(sV == dest.id)
	return;
    
    
    aux_dijkstra(search_for_vertex(sV), dest, currentpath,  path, dist, visited);
}

