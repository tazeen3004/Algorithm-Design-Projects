
import java.util.*;
import java.io.*;


public class kruskal2D {


    static void kruskalMST(int cost[][], int N)
    {
        int mincost = 0;
        int[] parent = new int[N];
        // Initialize sets of disjoint sets.
        for (int i = 0; i < N; i++)
        {
            parent[i] = i;
        }
        // Include minimum weight edges one by one
        int edgeCount = 0;
        while (edgeCount < N - 1)
        {
            int min = Integer.MAX_VALUE, a = -1, b = -1;
            for (int i = 0; i < N; i++)
            {
                for (int j = 0; j < N; j++)
                {
                    if (search(i, parent) != search(j, parent) && cost[i][j] < min)
                    {
                        min = cost[i][j];
                        a = i;
                        b = j;
                    }
                }
            }
            join(a, b, parent);
            System.out.println(edgeCount++ +" \t\t\t\t "+a+" \t\t\t " +b+ " \t\t\t "+ min);
            mincost += min;
        }
        System.out.println("MST cost is:"+ mincost);
    }

    static int search(int i, int parent[])
    {

        while (parent[i] != i)
            i = parent[i];
        return i;
    }

    static void join(int i, int j, int parent[])
    {
        int a = search(i, parent);
        int b = search(j, parent);
        parent[a] = b;
    }


    public static void main(String args[])throws FileNotFoundException
    {
        Runtime runtime = Runtime.getRuntime();
        Merge m=new Merge();
        int[][] G=m.MergeGraph("/Users/tazeen/Desktop/Project3_G1_Data.csv","/Users/tazeen/Desktop/Project3_G2_Data.csv");
        System.out.println("EdgeNumber \t\t Node \t\t Node \t\t Distance");
        kruskalMST(G, G.length-1);
        System.out.println();
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        //double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
        //System.out.println("memoryUsedPercent: " + memoryUsedPercent);

    }

}

