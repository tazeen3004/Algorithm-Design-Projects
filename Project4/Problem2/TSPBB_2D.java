import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class TSPBB_2D
{

    static int N = 0;
    static int path[];
    static boolean visited[];
    static int cost = Integer.MAX_VALUE;
    final static int INF = 99999;
    static void copyToFinal(int cPath[])
    {
        for (int i = 0; i < N; i++)
            path[i] = cPath[i];
        path[N] = cPath[0];
    }
    static int min(int adj[][], int i)
    {
        int min = Integer.MAX_VALUE;
        for (int k = 0; k < N; k++)
            if (adj[i][k] < min && i != k)
                min = adj[i][k];
        return min;
    }
    static int secMin(int adj[][], int i)
    {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int j=0; j<N; j++)
        {
            if (i == j)
                continue;

            if (adj[i][j] <= first)
            {
                second = first;
                first = adj[i][j];
            }
            else if (adj[i][j] <= second &&
                    adj[i][j] != first)
                second = adj[i][j];
        }
        return second;
    }
    static void TSPCalculate(int adjacencyMat[][], int cbound, int cweight,
                             int level, int cPath[])
    {
        if (level == N)
        {
            if (adjacencyMat[cPath[level - 1]][cPath[0]] != 0)
            {
                int curr_res = cweight +
                        adjacencyMat[cPath[level-1]][cPath[0]];
                if (curr_res < cost)
                {
                    copyToFinal(cPath);
                    cost = curr_res;
                }
            }
            return;
        }
        for (int i = 0; i < N; i++)
        {
            if (adjacencyMat[cPath[level-1]][i] != 0 &&
                    visited[i] == false)
            {
                int temp = cbound;
                cweight += adjacencyMat[cPath[level - 1]][i];
                if (level==1)
                    cbound -= ((min(adjacencyMat, cPath[level - 1]) +
                            min(adjacencyMat, i))/2);
                else
                    cbound -= ((secMin(adjacencyMat, cPath[level - 1]) +
                            min(adjacencyMat, i))/2);
                if (cbound + cweight < cost)
                {
                    cPath[level] = i;
                    visited[i] = true;
                    TSPCalculate(adjacencyMat, cbound, cweight, level + 1,
                            cPath);
                }
                cweight -= adjacencyMat[cPath[level-1]][i];
                cbound = temp;
                Arrays.fill(visited,false);
                for (int j = 0; j <= level - 1; j++)
                    visited[cPath[j]] = true;
            }
        }
    }
    static void TSP(int adjacencyMat[][])
    {
        int cPath[] = new int[N + 1];
        int cbound = 0;
        Arrays.fill(cPath, -1);
        Arrays.fill(visited, false);
        for (int i = 0; i < N; i++)
            cbound += (min(adjacencyMat, i) +
                    secMin(adjacencyMat, i));

        cbound = (cbound==1)? cbound/2 + 1 : cbound/2;
        visited[0] = true;
        cPath[0] = 0;
        TSPCalculate(adjacencyMat, cbound, 0, 1, cPath);
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
        TSP(adjacencyMatrix);
        System.out.println("TSP with Branch and Bound with 2D array");
        System.out.printf("Minimum travelling  cost : %d\n", cost);
        System.out.printf("Path taken to visit all nodes : ");
        for (int i = 0; i <= N; i++) {
            if (i < N) {
                System.out.printf("%d ->", path[i]);
            } else {
                System.out.printf("%d", path[i]);
            }
        }
    }
}
