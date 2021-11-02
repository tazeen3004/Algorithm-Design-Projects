import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Dijkstra2D{

    private static final int NO_PARENT = -1;
    public static int[][] Distance;
    public static int[][] path;
    public static ArrayList<ArrayList<String>> readMatrix(String filename) {
        String line;
        int node=0;
        ArrayList<ArrayList<String> > adjList = new ArrayList<ArrayList<String> >();
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
                    adjList.add(arrlist);
                    arrlist =new ArrayList<>();
                    arrlist.add(s);
                }
            }
            adjList.add(arrlist);
            return adjList;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return adjList;
    }
    //This function creates adjancency matrix from arraylist
    public static int[][] createAdjmatrix(ArrayList<ArrayList<String> > adjList)
    {
        int n = adjList.size();
        int [][] adjmat = new int[n][n];
        int col = 0;
        for (int i = 0; i < adjList.size(); i++) {
            List<String> list = adjList.get(i);
            for (int j = 0; j < list.size(); j++) {
                String s = list.get(j);
                String[] split = s.split("\\.");
                col = Integer.parseInt(split[0]);
                adjmat[i][col] = Integer.parseInt(split[1]);
            }
        }

        return adjmat;
    }
    private static void dijkstra(int[][] adjacencyMatrix)
    {
        int n = adjacencyMatrix.length;
        Distance = new int[n][n];

        path = new int[n][n];
        for(int startNode=0; startNode<n;startNode++){
            boolean[] added = new boolean[n];
            for (int nodeIndex = 0; nodeIndex < n; nodeIndex++)
            {
                Distance[startNode][nodeIndex] = Integer.MAX_VALUE;
                added[nodeIndex] = false;
            }
            Distance[startNode][startNode] = 0;

            path[startNode][startNode] = NO_PARENT;
            int nearestVertex = -1;
            for (int i = 1; i < n; i++)
            {

                int shortestDistance = Integer.MAX_VALUE;
                for (int nodeIndex = 0; nodeIndex < n; nodeIndex++)
                {
                    if (!added[nodeIndex] && Distance[startNode][nodeIndex] < shortestDistance)
                    {
                        nearestVertex = nodeIndex;
                        shortestDistance = Distance[startNode][nodeIndex];
                    }
                }
                added[nearestVertex] = true;
                for (int nodeIndex = 0; nodeIndex < n; nodeIndex++)
                {
                    int edgeDistance = adjacencyMatrix[nearestVertex][nodeIndex];

                    if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < Distance[startNode][nodeIndex]))
                    {
                        path[startNode][nodeIndex] = nearestVertex;
                        Distance[startNode][nodeIndex] = shortestDistance + edgeDistance;
                    }
                }
            }
        }

    }
    private static void printSolution(int startNode, int endNode)
    {
        System.out.println("Distance is "+Distance[startNode][endNode] + " FEET");
        System.out.println("Path from " +startNode +" to "+endNode+" is:");
        printPath(endNode,startNode);
    }

    private static void printPath(int currNode,int start)
    {
        if (currNode == NO_PARENT)
        {
            return;
        }
        printPath(path[start][currNode],start);
        System.out.println(currNode + " ");
    }

    // Driver Code
    public static void main(String[] args)
    {
       // long startTime = System.nanoTime();
        //Runtime runtime = Runtime.getRuntime();
        ArrayList<ArrayList<String>> adjList = new ArrayList<ArrayList<String> >();
        adjList=readMatrix("/Users/tazeen/Desktop/Project2_Input_Files/Project2_Input_File3.csv");
        int[][] adj1mat;
        adj1mat=createAdjmatrix(adjList);
        /*long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
        System.out.println("memoryUsedPercent: " + memoryUsedPercent); */
        dijkstra(adj1mat);
        System.out.println("Dijsktra with 2D array");
        System.out.println("---Test Case 1---");
        printSolution(197, 27);
        System.out.println();
        System.out.println("---Test Case 2---");
        printSolution(65, 280);
        System.out.println();
        System.out.println("---Test Case 3---");
        printSolution(187, 68);
        System.out.println();
    }
}


