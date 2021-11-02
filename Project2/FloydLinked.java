import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.lang.*;


public class FloydLinked {

    final static int MAXVALUE =100000;
    public static int[][] adjmat;
    public static int Next[][];
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
    static class Graph {
        int vertices;
        LinkedList<Edge>[] adjancencyList;

        public Graph(int vertices) {
            this.vertices = vertices;
            adjancencyList = new LinkedList[vertices];

            for (int i = 0; i < vertices; i++) {
                adjancencyList[i] = new LinkedList<Edge>();
            }
        }
        public void addEdge(int source, int destination, int weight) {
            Edge edge = new Edge(source, destination, weight);
            adjancencyList[source].add(edge);
        }
    }
    public static int[][] readMatrix(String filename) {
        String line;
        int node=0;
        ArrayList<ArrayList<String> > TempadjList = new ArrayList<ArrayList<String> >();
        ArrayList<String> arrlist = new ArrayList<String>();
        String s="";
        try
        {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(filename));
            br.readLine();
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] l = line.split(",");    // use comma as separator
                s = (l[1]).concat(".").concat(l[2]); //concating target with weight
                if (node == Integer.parseInt(l[0]))
                {
                    arrlist.add(s);
                }
                else {
                    node = Integer.parseInt(l[0]);
                    TempadjList.add(arrlist);
                    arrlist =new ArrayList<>();
                    arrlist.add(s);
                }
            }
            TempadjList.add(arrlist);


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        adjmat = new int[TempadjList.size()][TempadjList.size()];
        int col = 0;
        for (int i = 0; i < TempadjList.size(); i++) {
            List<String> list = TempadjList.get(i);
            for (int j = 0; j < list.size(); j++) {
                String st = list.get(j);
                String[] split = st.split("\\.");
                col = Integer.parseInt(split[0]);
                adjmat[i][col] = Integer.parseInt(split[1]);
            }
        }
        for (int i=0;i<adjmat.length;i++){
            for (int j=0;j<adjmat.length;j++){
                if(adjmat[i][j]==0)
                {
                    adjmat[i][j]=MAXVALUE;
                }

            }
        }
        return adjmat;
    }
    public static LinkedList<Integer>[] FloydShortestPath(Graph graph, int n) {

        LinkedList<Integer>[] shortestPaths = new LinkedList[n];
        Next=new int [n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if(adjmat[i][j]==MAXVALUE){

                    Next[i][j]=-1;
                }
                else
                {
                    Next[i][j]=j;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            shortestPaths[i] = new LinkedList<Integer>();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                shortestPaths[i].add(graph.adjancencyList[i].get(j).distance);
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(shortestPaths[i].get(k) + shortestPaths[k].get(j) < shortestPaths[i].get(j))
                    {
                        shortestPaths[i].set(j, shortestPaths[i].get(k) + shortestPaths[j].get(k));
                        Next[i][j] = Next[i][k];

                    }
                }
            }
        }
        return shortestPaths;
    }

    public static Graph convertToLinkedList(int[][] adjmat) {
        Graph adjacencyList = new Graph(adjmat.length);
        Edge edge;

        for (int i = 0; i < adjmat.length; i++) {
            for (int j = 0; j < adjmat.length; j++) {
                edge = new Edge(i, j, adjmat[i][j]);
                adjacencyList.addEdge(i, j, adjmat[i][j]);
            }
        }
        return adjacencyList;
    }
    public static void nodepath(int u,int v)
    {
        if (Next[u][v] == -1)
        {
            System.out.println("No path");
            return;
        }
        else {
            Vector<Integer> path = new Vector<Integer>();
            path.add(u);

            while (u != v) {
                u = Next[u][v];
                path.add(u);
            }
            for(int i=0;i< path.size();i++) {
                if (i == (path.size() - 1)) {
                    System.out.println(path.get(i));
                } else {
                    System.out.println(path.get(i));
                }
            }
        }

    }
    public static void main(String[] args) {

        //long startTime = System.nanoTime();
        //Runtime runtime = Runtime.getRuntime();
        int[][] adjmat;
        adjmat=readMatrix("/Users/tazeen/Desktop/Project2_Input_Files/Project2_Input_File3.csv");
        Graph adjacencyList=convertToLinkedList(adjmat); //converts matrix to linked list
        LinkedList<Integer>[] shortestPath = FloydShortestPath(adjacencyList, adjmat.length);
        /*long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
        System.out.println("memoryUsedPercent: " + memoryUsedPercent);*/
        System.out.println("Floyd Linked List");
        System.out.println("---Test Case 1---");
        System.out.println("Distance is: " +shortestPath[197].get(27) + " FEET");
        System.out.println("Path from 197 to 27 is: ");
        nodepath(197, 27);
        System.out.println();
        System.out.println("---Test Case 2---");
        System.out.println("Distance is: " +shortestPath[65].get(280) + " FEET");
        System.out.println("Path from 65 to 280 is:");
        nodepath(65, 280);
        System.out.println();
        System.out.println("---Test Case 3---");
        System.out.println("Distance is: " +shortestPath[187].get(68) + " FEET");
        System.out.println("Path from 187 to 68 is:");
        nodepath(187, 68);
        System.out.println();


    }

}