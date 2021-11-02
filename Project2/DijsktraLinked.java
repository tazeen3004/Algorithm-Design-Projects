import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.lang.*;




public class DijsktraLinked {

    final static int MAXVALUE =100000;
    private static final int NO_PARENT = -1;
    public static int[][] adjmat;
    public static int[][] parents;
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

    public static LinkedList<Integer>[] DijkstraForAllNodes(Graph graph, int n)
    {
        LinkedList<Integer>[] shortestPaths = new LinkedList[n];
        LinkedList<Integer>[] shortestPa;
        for (int i = 0; i < n; i++) {
            shortestPaths[i] = new LinkedList<Integer>();
            shortestPa=DijkstraShortestPath(graph,n,i);
            for (int j = 0; j < n; j++) {
                shortestPaths[i].add(shortestPa[i].get(j));
            }
        }
        return shortestPaths;
    }

    public static LinkedList<Integer>[] DijkstraShortestPath(Graph graph, int n, int s) {

        LinkedList<Integer>[] shortestPaths = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            shortestPaths[i] = new LinkedList<Integer>();
        }
        int[] shortestDistances = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                shortestPaths[i].add(graph.adjancencyList[i].get(j).distance);
            }
        }
        parents= new int[n][n];

        int dist[] = new int[n];
        Boolean sptSet[] = new Boolean[n];
        for (int i = 0; i < n; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;

        }

        dist[s] = 0;
        shortestPaths[s].set(s,0);
        parents[s][s]= -1;
        int nearestv=-1;
        for (int count = 1; count < n; count++) {

            int shortdist=Integer.MAX_VALUE;
            for (int vertexI=0; vertexI<n;vertexI++){
                if(!sptSet[vertexI] && shortestPaths[s].get(vertexI)< shortdist){
                    nearestv=vertexI;
                    shortdist=shortestPaths[s].get(vertexI);
                }
            }
            sptSet[nearestv] = true;

            for (int v = 0; v < n; v++) {
                int edgeDist=shortestPaths[nearestv].get(v);
                if(edgeDist>0 && ((shortdist + edgeDist) < shortestPaths[s].get(v)))
                {
                    parents[s][v]=nearestv;
                    shortestPaths[s].set(v,shortdist + edgeDist);
                }
            }
        }


        return shortestPaths;
    }
    public static void printPath(int startNode,int currNode)
    {
        if (currNode ==0)
        {
            System.out.println(startNode);
            return;
        }
        else {
            printPath(startNode,parents[startNode][currNode]);
            System.out.println(currNode);
        }
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


    public static void main(String[] args) {

        //long startTime = System.nanoTime();
        //Runtime runtime = Runtime.getRuntime();
        int[][] adjmat;
        adjmat=readMatrix("/Users/tazeen/Desktop/Project2_Input_Files/Project2_Input_File3.csv");
        Graph testCase1AL = convertToLinkedList(adjmat);
        LinkedList<Integer>[] shortestDistAll =  DijkstraForAllNodes(testCase1AL, adjmat.length);
        /*long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
        System.out.println("memoryUsedPercent: " + memoryUsedPercent);*/
        System.out.println("Dijsktra Linked List");
        System.out.println("--Test Case 1--");
        System.out.println("Path from 197 to 27 is: ");
        LinkedList<Integer>[] shortestDist1 = DijkstraShortestPath(testCase1AL, adjmat.length,197);
        System.out.println("Distance is: "+shortestDist1[197].get(27)+ " FEET");
        printPath(197,27);
        System.out.println();
        System.out.println("--Test Case 2--");
        LinkedList<Integer>[] shortestDist2 = DijkstraShortestPath(testCase1AL, adjmat.length,65);
        System.out.println("Distance is: "+shortestDist2[65].get(280)+ " FEET");
        System.out.println("Path from 65 to 28 is: ");
        printPath(65,280);
        System.out.println();
        System.out.println("--Test Case 3--");
        LinkedList<Integer>[] shortestDist3 = DijkstraShortestPath(testCase1AL, adjmat.length,187);
        System.out.println("Distance is: "+shortestDist3[187].get(68)+ " FEET");
        System.out.println("Path from 187 to 68 is: ");
        printPath(187,68);


    }

}
