import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.List;
import java.io.*;
public class krushkallinked
{
    static int[] parent;
    static int INF = Integer.MAX_VALUE;
    static class Edge {
        int source;
        int destination;
        int distance;

        public Edge(int source, int destination, int distance) {
            this.source = source;
            this.destination = destination;
            this.distance = distance;
        }
    }
    static class Graph
    {
        int nodes;
        ArrayList<Edge> allEdges = new ArrayList<>();

        Graph(int nodes) {
            this.nodes = nodes;
        }

        public void addEgde(int source, int destination, int distance) {
            Edge edge = new Edge(source, destination, distance);
            allEdges.add(edge); //add to total edges
        }

        public void kruskalMST(){
            PriorityQueue<Edge> pq = new PriorityQueue<>(allEdges.size(), Comparator.comparingInt(o -> o.distance));
            //add all the edges to priority queue and sort the edges on distances
            for (int i = 0; i <allEdges.size() ; i++) {
                pq.add(allEdges.get(i));
            }
            int [] parent = new int[nodes];
            //makeset
            makeSet(parent);
            ArrayList<Edge> mst = new ArrayList<>();
            //process nodes - 1 edges
            int index = 0;
            while(index<nodes-1){
                Edge edge = pq.remove();
                //check if adding this edge creates a cycle
                int x_set = search(parent, edge.source);
                int y_set = search(parent, edge.destination);

                if(x_set==y_set){
                    //ignore this will create cycle
                }
                else {
                    mst.add(edge);
                    index++;
                    join(parent,x_set,y_set);
                }
            }
            System.out.println("Minimum Spanning Tree: ");
            printGraph(mst);
        }

        public void makeSet(int [] parent)
        {
            for (int i = 0; i <nodes ; i++) {
                parent[i] = i;
            }
        }

        public int search(int [] parent, int vertex){
            if(parent[vertex]!=vertex)
                return search(parent, parent[vertex]);;
            return vertex;
        }

        public void join(int [] parent, int x, int y)
        {
            int xSetParent = search(parent, x);
            int ySetParent = search(parent, y);
            parent[ySetParent] = xSetParent;
        }
        public void printGraph(ArrayList<Edge> edgeList)
        {
            int sum=0;
            System.out.println("Kruskal using Linked List");
            System.out.println("EdgeCount \t\t\t Node \t\t\t Node \t\t\t Distance");
            for (int i = 0; i <edgeList.size() ; i++) {
                Edge edge = edgeList.get(i);
                System.out.println(i +" \t\t\t"+ edge.source + " \t\t\t" + edge.destination +" \t\t\t" + edge.distance);
                sum += edge.distance;
            }
            System.out.println("Total MST Cost is: "+sum);
        }
    }
    public static void main(String[] args) throws FileNotFoundException
    {
        Runtime runtime = Runtime.getRuntime();
        Merge m=new Merge();
        int[][] G=m.MergeGraph("/Users/tazeen/Desktop/Project3_G1_Data.csv","/Users/tazeen/Desktop/Project3_G2_Data.csv");
        int nodes = G.length-1;
        Graph graph = new Graph(nodes);
        // add edges to linked list
        for(int i=0; i<nodes;i++)
        {
            for(int j=0; j<nodes;j++)
            {
                if(G[i][j]!=INF)
                {
                    graph.addEgde(i, j, G[i][j]);
                }
            }
        }
        graph.kruskalMST();
        System.out.println();
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        //double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
        //System.out.println("memoryUsedPercent: " + memoryUsedPercent);

    }
}