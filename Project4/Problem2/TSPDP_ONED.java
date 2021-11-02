import static java.lang.Integer.min;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class TSPDP_ONED {

    public static int INT_MAX=999999;

    public static int n = 4;
    public static int root;
    public static int N = 4;
    //public static int dist[][]= {{0, 10, 15, 20},{10, 0, 35, 25}, {15, 35, 0, 30},{20, 25, 30, 0}};
    public static int oneD[];
    //= {{0, 10, 15, 20},{10, 0, 35, 25}, {15, 35, 0, 30},{20, 25, 30, 0}};
    //16,777,216
    public static int VISITED_ALL = (1 << n) - 1;
    public static int[][] path;

    public static int[][] dp;


    static int tsp(int mask, int pos) {

        if (mask == VISITED_ALL) {
            return oneD[pos*(pos-1)/2+0];
        }
        if (dp[mask][pos] != -1) {
            return dp[mask][pos];
        }

        int cost = INT_MAX;
        int k=0;
        for (int city = 0; city < n; city++) {

            if ((mask & (1 << city)) == 0) {
                int newCost = oneD[pos*(pos-1)/2+city] + tsp(mask | (1 << city), city);
                // cost = min(cost, newCost);
                if (cost > newCost) {
                    cost = newCost;
                    k = city;
                }
            }

        }
        path[mask][pos] = k;

        return dp[mask][pos] = cost;
    }

    static void display_path(int source) {
        int count = 0, i = 0;
        int[] brr=new int [N];
        for (i = 0; i < N; i++) {
            brr[i] = -1;
        }
        System.out.print("Path taken: " +source+"->");
        while (count < N - 1) {
            for (i = 0; i < (1 << N); i++) {
                if (path[i][source] != -1 && brr[path[i][source]] == -1) {
                    brr[path[i][source]]++;
                    source = path[i][source];
                    System.out.printf(" %d -> ", source + 1);
                    count++;

                    break;
                }
            }
        }
        System.out.printf("%d\n\n", root);

    }
    public static ArrayList<ArrayList<String>> readMatrix(String filename)
    {
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
    public static int[][] createAdjmatrix(ArrayList<ArrayList<String> > adjList) {
        int n = adjList.size();
        int[][] adjmat = new int[n][n];
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
    public static void main(String[] args)
    {
        ArrayList<ArrayList<String> > adjList = new ArrayList<ArrayList<String> >();
        adjList=readMatrix("/Users/tazeen/Desktop/Project 4_Problem 2_InputData.csv");
        int[][] adjacencyMatrix;
        adjacencyMatrix=createAdjmatrix(adjList);
        N=adjacencyMatrix.length;
        n=adjacencyMatrix.length;
        int val=(int)Math.pow(2,N);
        path=new int[val][N];
        dp=new int[val][N];
        oneD=new int[n*(n+1)/2];
        for(int i=0;i<adjacencyMatrix.length;i++)
        {
            for (int j = 0; j < adjacencyMatrix.length; j++)
            {
                if (j < i)
                {
                    oneD[i * (i - 1) / 2 + j] = adjacencyMatrix[i][j];
                }

            }
        }
        for (int i = 0; i < (1 << n); i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = -1;
                path[i][j] = -1;

            }
        }
        root = 0;
        System.out.println("TSP with Dynamic Programming with 1D array");
        System.out.println("Minimum travelling  cost : " +tsp(1, 0));
        display_path(root);


    }
}