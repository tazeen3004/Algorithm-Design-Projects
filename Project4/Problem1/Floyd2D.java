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
    public static HashMap<Integer, Integer> b = new HashMap();
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


    }
    //this function is to get path from one start node to end node
    public static void nodepath(int u,int v)
    {
        if (Next[u][v] == -1)
        {
            //System.out.println("No path");
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
                    //System.out.println(path.get(i));

                } else {
                    //System.out.println(path.get(i));
                }

                    if (path.get(i) != u && path.get(i) != v) {

                        b.put(path.get(i), b.get(path.get(i)) + 1);

                }
            }
        }

    }
    public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Integer>> list =
                new LinkedList<Map.Entry<Integer, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        //System.out.println(temp);
       // System.out.println(temp.size());
        for (int i = temp.size(); i < 1407; i--) {

            System.out.println(temp.get(i));
        }
        Set<Map.Entry<Integer, Integer>> mapSet = temp.entrySet();
        int c=1;
        for(int i=1426;i>1406;i--) {

            Map.Entry<Integer, Integer> elementAt = (new ArrayList<Map.Entry<Integer, Integer>>(mapSet)).get(i);

            System.out.println(c+".\t\t\t "+elementAt.getKey()+"\t\t\t "+ hm.get(elementAt.getKey()));
            c++;
        }
        return temp;
    }
    public static void main (String[] args)
    {
        //long startTime = System.nanoTime();
        //Runtime runtime = Runtime.getRuntime();
        ArrayList<ArrayList<String> > adjList = new ArrayList<ArrayList<String> >();
        adjList=readMatrix("/Users/tazeen/Desktop/Project2_Input_Files/Project2_Input_File10.csv");
        int[][] adjacencyMatrix;
        adjacencyMatrix=createAdjmatrix(adjList);
       /* int[][] adjacencyMatrix = { { 0, 4, 0, 0, 0, 0, 0, 8, 0 },
                { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
                { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
                { 0, 0, 7, 0, 9, 14, 0, 0, 0 },
                { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
                { 0, 0, 4, 0, 10, 0, 2, 0, 0 },
                { 0, 0, 0, 14, 0, 2, 0, 1, 6 },
                { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
                { 0, 0, 2, 0, 0, 0, 6, 7, 0 } }; */
        for (int i =0;i<adjacencyMatrix.length;i++)
        {
            b.put(i,-adjacencyMatrix.length+1); //removing matrix length since it takes own values too
        }
        floyd(adjacencyMatrix);
        int c=0;
       for(int i=0;i<adjacencyMatrix.length;i++) {
           for (int j = 0; j < adjacencyMatrix.length; j++) {
               nodepath(i, j);
               c+=1;
           }
       }
       System.out.println("Betweeness Cardinality");
        System.out.println("Highest 20");
       System.out.println("Number \t\t\tNode \t\t\tFrequency");
        sortByValue(b);


    }

}