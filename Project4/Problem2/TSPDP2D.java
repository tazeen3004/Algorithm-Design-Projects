import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TSPDP2D {

    private final int N;
    private final int START_NODE;
    private final int FINISHED_STATE;
    private double[][] dist;
    private double minTravellingCost = Double.POSITIVE_INFINITY;
    private List<Integer> tour = new ArrayList<>();
    private boolean ranSolver = false;

    public TSPDP2D(double[][] dist)
    {
        this(0, dist);
    }

    public TSPDP2D(int startNode, double[][] dist)
    {
        this.dist = dist;
        N = dist.length;
        START_NODE = startNode;
        FINISHED_STATE = (1 << N) - 1;
    }
    public List<Integer> getTravelling()
    {
        if (!ranSolver) solve();
        return tour;
    }
    public double getTravellingCost()
    {
        if (!ranSolver) solve();
        return minTravellingCost;
    }

    public void solve()
    {
        int state = 1 << START_NODE;
        Double[][] memo = new Double[N][1 << N];
        Integer[][] prev = new Integer[N][1 << N];
        minTravellingCost = tsp(START_NODE, state, memo, prev);
        //for path
        int index = START_NODE;
        while (true) {
            tour.add(index);
            Integer nextIndex = prev[index][state];
            if (nextIndex == null) break;
            int nextState = state | (1 << nextIndex);
            state = nextState;
            index = nextIndex;
        }
        tour.add(START_NODE);
        ranSolver = true;
    }

    private double tsp(int i, int state, Double[][] memo, Integer[][] prev)
    {
        // tour completed returning back to start node with cost
        if (state == FINISHED_STATE) return dist[i][START_NODE];
        if (memo[i][state] != null) return memo[i][state];
        double minCost = Double.POSITIVE_INFINITY;
        int index = -1;
        for (int next = 0; next < N; next++) {

            if ((state & (1 << next)) != 0) continue;

            int nextState = state | (1 << next);
            double newCost = dist[i][next] + tsp(next, nextState, memo, prev);
            if (newCost < minCost) {
                minCost = newCost;
                index = next;
            }
        }

        prev[i][state] = index;
        return memo[i][state] = minCost;
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
    public static double[][] createAdjmatrix(ArrayList<ArrayList<String> > adjList) {
        int n = adjList.size();
        double[][] adjmat = new double[n][n];
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

    public static void main(String[] args) {

        // Create adjacency matrix
        int n = 24;
        ArrayList<ArrayList<String> > adjList = new ArrayList<ArrayList<String> >();
        adjList=readMatrix("/Users/tazeen/Desktop/Project 4_Problem 2_InputData.csv");
        double[][] dist;
        dist=createAdjmatrix(adjList);
        n= dist.length;
        /*double[][] graph =
                {{0,34, 45, 37, 23, 83, 12, 15 ,99, 11},
                        {11 ,0, 22, 33, 44, 55, 66, 77 ,88, 99},
                        {33 ,23, 0, 12, 53, 23, 87, 27, 54, 91},
                        {21 ,13, 81, 0 ,64, 81, 81, 45, 53, 51},
                        {64, 87, 54, 16, 0, 87, 42, 31, 46, 51},
                        {91, 36, 65, 52, 48, 0, 15, 75 ,32, 30},
                        {78, 13, 16 ,54 ,32, 76, 0 ,49, 35, 15},
                        {49, 76, 45 ,82, 39, 47, 18 ,0, 19, 53},
                        {75, 15, 95 ,45, 86, 45, 48, 75, 0 ,14},
                        {34, 68, 49 ,78, 49, 68, 84, 19, 37, 0}};*/
       /* double[][] graph1 = {{0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}};*/

        TSPDP2D solve = new TSPDP2D(dist);
        System.out.println("TSP with Dynamic Programming  with 2D array");
        System.out.println("Minimum travelling  cost: " + solve.getTravellingCost());
        System.out.println("Path taken to visit all nodes: " + solve.getTravelling());
    }
}