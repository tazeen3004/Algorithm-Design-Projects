import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TSPBB_ONED
{

    static int N = 0;
    static int path[];
    static boolean visited[];
    static int[][] a;
    static int cost = Integer.MAX_VALUE;
    final static int INF = 99999;

    public static void copyToFinal(int cpath[])
    {
        for (int i = 0; i < N; i++)
            path[i] = cpath[i];
        path[N] = cpath[0];
    }
    public static int min(int oneD[], int i)
    {
        int min = Integer.MAX_VALUE;
        for (int k = 0; k < N; k++)
            if (oneD[i*(i - 1)/2+k] < min && i != k)
                min = oneD[i*(i - 1)/2+k];
        return min;
    }
    public static int sMin(int oneD[], int i)
    {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int j=0; j<N; j++)
        {
            if (i == j)
                continue;

            if (oneD[i*(i - 1)/2+j] <= first)
            {
                second = first;
                first = oneD[i*(i - 1)/2+j];
            }
            else if (oneD[i*(i - 1)/2+j] <= second &&
                    oneD[i*(i - 1)/2+j] != first)
                second = oneD[i*(i - 1)/2+j];
        }
        return second;
    }
    public static void TSPCalculate(int oneD[], int cbound, int cweight, int level, int cpath[])
    {
        if (level == N)
        {
            int j=cpath[level-1];
            int l=cpath[0];
            if (oneD[j*(j-1)/2+l] != 0)
            {
                int cres = cweight + oneD[j*(j-1)/2+l];
                if (cres < cost)
                {
                    copyToFinal(cpath);
                    cost = cres;
                }
            }
            return;
        }
        for (int i = 0; i < N; i++)
        {
            int f=cpath[level-1];
            if (oneD[f*(f-1)/2+i] != 0 && visited[i] == false)
            {
                int temp = cbound;
                cweight += oneD[f*(f-1)/2+i];
                if (level==1)
                    cbound -= ((min(oneD, cpath[level - 1]) + min(oneD, i))/2);
                else
                    cbound -= ((sMin(oneD, cpath[level - 1]) + min(oneD, i))/2);
                if (cbound + cweight < cost)
                {
                    cpath[level] = i;
                    visited[i] = true;
                    TSPCalculate(oneD, cbound, cweight, level + 1, cpath);
                }
                cweight -= oneD[f*(f-1)/2+i];
                cbound = temp;
                Arrays.fill(visited,false);
                for (int j = 0; j <= level - 1; j++)
                    visited[cpath[j]] = true;
            }
        }
    }
    public static void TSP(int oneD[])
    {
        int cpath[] = new int[N + 1];
        int cbound = 0;
        Arrays.fill(cpath, -1);
        Arrays.fill(visited, false);
        for (int i = 0; i < N; i++)
            cbound += (min(oneD, i) +
                    sMin(oneD, i));

        cbound = (cbound==1)? cbound/2 + 1 : cbound/2;
        visited[0] = true;
        cpath[0] = 0;
        TSPCalculate(oneD, cbound, 0, 1, cpath);
    }
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
        a=adjmat;
        return adjmat;
    }
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


    // Driver code

    public static void main(String[] args)
    {
        ArrayList<ArrayList<String> > adjList = new ArrayList<ArrayList<String> >();
        adjList=readMatrix("/Users/tazeen/Desktop/Project 4_Problem 2_InputData.csv");
        int[][] adjacencyMatrix;
        adjacencyMatrix=createAdjmatrix(adjList);
        N=adjacencyMatrix.length;
        path = new int[N + 1];
        visited = new boolean[N];
        int[] oneD=new int[N*(N+1)/2];
        //int[] next=new int[N*N];
        //Copy the upper triangular matrix to 1D array
        for(int i=0;i<N;i++)
            for(int j=0;j<N;j++){
                if(j<i) {
                    oneD[i*(i - 1)/2+j] = adjacencyMatrix[i][j];
                }

            }
        TSP(oneD);
        System.out.println("TSP with Branch and Bound with 1D array");
        System.out.println("Minimum cost : " +cost);
        System.out.printf("Path Taken : ");
        for (int i = 0; i <= N; i++)
        {
            System.out.printf("%d ", path[i]);
        }
    }
}
