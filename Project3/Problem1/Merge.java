import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;

public class Merge
{

    public  int[][] MergeGraph(String G1file, String G2file) throws FileNotFoundException
    {
        Scanner inputf1 = new Scanner(System.in);
        File file = new File(G1file);
        inputf1 = new Scanner(file);
        inputf1.nextLine();
        ArrayList<Integer> G1node = new ArrayList<Integer>();
        ArrayList<Integer> G1targetNode = new ArrayList<Integer>();
        ArrayList<Integer> G1dist = new ArrayList<Integer>();
        ArrayList<String> G1cord = new ArrayList<String>();
        while(inputf1.hasNextLine())
        {
            //parsing the csv
            String line = inputf1.nextLine();
            String col[] = line.split(",");
            int node = Integer.parseInt(col[0]);
            int targetnode = Integer.parseInt(col[1]);
            int distance = Integer.parseInt(col[2]);
            String coord = col[3]+","+col[4];
            G1node.add(node);
            G1targetNode.add(targetnode);
            G1dist.add(distance);
            G1cord.add(coord);
        }
        Scanner inputf2 = new Scanner(System.in);
        File file_2 = new File(G2file);
        inputf2 = new Scanner(file_2);
        inputf2.nextLine();
        ArrayList<Integer> G2node = new ArrayList<Integer>();
        ArrayList<Integer> G2targetNode = new ArrayList<Integer>();
        ArrayList<Integer> G2dist = new ArrayList<Integer>();
        ArrayList<String> G2cord = new ArrayList<String>();
        while(inputf2.hasNextLine())
        {
            String line_2 = inputf2.nextLine();
            String col_2[] = line_2.split(",");
            // Parsing the .CSV file for NODES and DISTANCE
            int node = Integer.parseInt(col_2[0]);
            int targetnode = Integer.parseInt(col_2[1]);
            int distance = Integer.parseInt(col_2[2]);
            String coord = col_2[3]+","+col_2[4];
            G2node.add(node);
            G2targetNode.add(targetnode);
            G2dist.add(distance);
            G2cord.add(coord);
        }
        int G2cordSize = G2cord.size();
        ArrayList<Integer> node_to_delete = new ArrayList<Integer>();
        for(int i=0; i<G2cordSize; i++)
        {
            if(G1cord.contains(G2cord.get(i)))
            {
                G1node.add(G1node.get(G1cord.indexOf(G2cord.get(i))));
                G1targetNode.add(G2targetNode.get(i)+534);
                G1dist.add(G2dist.get(i));
                node_to_delete.add(G2node.get(i));
            }
        }
        Scanner inputf3 = new Scanner(System.in);
        inputf3 = new Scanner(file_2);
        inputf3.nextLine();
        while(inputf3.hasNextLine())
        {
            String line_3 = inputf3.nextLine();
            String col_3[] = line_3.split(",");
            int node = Integer.parseInt(col_3[0]);
            int targetnode = Integer.parseInt(col_3[1]);
            int distance = Integer.parseInt(col_3[2]);
            if(node_to_delete.contains(node))
            {
                //skip
            }
            else
            {
                G1node.add(node+534);
                G1targetNode.add(targetnode+534);
                G1dist.add(distance);
            }
        }
        int size_G1node=G1node.size();
        int size_connected = G1targetNode.size();
        int size_G1dist = G1dist.size();
        int mat_size = G1node.get(size_G1node-1);
        Integer[] G1node_arr = new Integer[size_G1node];
        G1node_arr = G1node.toArray(G1node_arr);
        Integer[] G1targetNode_arr = new Integer[size_connected];
        G1targetNode_arr = G1targetNode.toArray(G1targetNode_arr);
        Integer[] G1dist_arr = new Integer[size_G1dist];
        G1dist_arr = G1dist.toArray(G1dist_arr);
        //Initializing matrix of G
        int G[][]= new int[mat_size+2][mat_size+2];
        for(int i=0; i<mat_size+1 ;i++)
        {
            for(int j=0; j<mat_size+1; j++)
            {
                G[i][j]=Integer.MAX_VALUE;
            }
        }
        // Building Adjacency Matrix
        for(int i=0; i<=size_G1node-1 ;i++)
        {
            G[G1node_arr[i]][G1targetNode_arr[i]]=G1dist_arr[i];
        }
        return G;
    }
}