import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class Floyd2D
{

    final static int INF = 99999;
    public static int[][] dist;
    public static int[][] Next;
    //This function reads the file and places the target node and distance in arraylist of string
    //in the format "targetnode.distance"
    public static ArrayList<ArrayList<String>> readMatrix(String filename) {
        String line;
        int node=0;
        ArrayList<ArrayList<String> > adjList = new ArrayList<ArrayList<String> >();
        List<Integer> tmp = new ArrayList<Integer>();
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
    //floyd algorithm implementation alog with creating distance matrix, next vector stores the path from one node to another
    public static void floyd(int adjmat[][])
    {

        int V = adjmat.length;
        dist= new int[V][V];
        Next= new int [V][V];
        for (int i = 0; i < V; i++)
        {
            for (int j = 0; j < V; j++)
            {
                if(adjmat[i][j]==0){
                    dist[i][j]=INF;
                    Next[i][j]=-1;
                }
                else
                {
                    dist[i][j] = adjmat[i][j];
                    Next[i][j]=j;
                }
            }
        }
        for (int k = 0; k < V; k++)
        {
            for (int i = 0; i < V; i++)
            {
                for (int j = 0; j < V; j++)
                {
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                    {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        Next[i][j] = Next[i][k];
                    }
                }
            }
        }
        for (int k = 0; k < dist.length; k++) {
            for (int i = 0; i < dist.length; i++) {
                System.out.print(dist[k][i]+" \t");

            }
            System.out.println();
        }


    }
    //this function is to get path from one start node to end node
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

    public static void main (String[] args)
    {
        //long startTime = System.nanoTime();
        //Runtime runtime = Runtime.getRuntime();
        ArrayList<ArrayList<String> > adjList = new ArrayList<ArrayList<String> >();
        adjList=readMatrix("/Users/tazeen/Desktop/Project 4_Problem 2_InputData.csv");
        int[][] adj1mat;
        adj1mat=createAdjmatrix(adjList);
        floyd(adj1mat);
        /*long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
        System.out.println("memoryUsedPercent: " + memoryUsedPercent);
        System.out.println("---Test Case 1---");
        System.out.println("Distance is "+dist[197][27]+" FEET");
        System.out.println("Path from 197 to 27 is: ");
        nodepath(197,27);
        System.out.println();
        System.out.println("---Test Case 2---");
        System.out.println("Distance is "+dist[65][280]+" FEET");
        System.out.println("Path from 65 to 280 is:");
        nodepath(65,280);
        System.out.println();
        System.out.println("---Test Case 3---");
        System.out.println("Distance is "+dist[187][68]+" FEET");
        System.out.println("Path from 187 to 68 is:");
        nodepath(187,68);
        System.out.println();*/

    }

}