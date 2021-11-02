import java.util.*;
import java.io.*;
import java.math.*;
import java.lang.Math.*;

public class Queens {

    public static int totalSol=0;
    public static boolean promising(int i, ArrayList<ArrayList<Integer>> place) {
        int k = 0;
        boolean flag = true;

        while(k < i && flag) {
            if((place.get(i).get(0) == place.get(k).get(0) && place.get(i).get(1) == place.get(k).get(1)) ||
                    (Math.abs(place.get(i).get(0) - place.get(k).get(0)) == i-k && Math.abs(place.get(i).get(1) - place.get(k).get(1)) == i - k) ||
                    (place.get(i).get(0) == place.get(k).get(0) && Math.abs(place.get(i).get(1) - place.get(k).get(1)) == i-k) ||
                    (place.get(i).get(1) == place.get(k).get(1) && Math.abs(place.get(i).get(0) - place.get(k).get(0)) == i-k)) {
                flag = false;
            }
            k++;
        }
        return flag;
    }
    public static void nqueens(int i, int n, ArrayList<ArrayList<Integer>> place) {
        if(promising(i, place)) {
            if(i == n - 1) {
                //System.out.println("Solution: "place);
                totalSol++;
            }
            else {
                for (int j = 0; j < n; j++) {
                    for (int l = 0; l < n; l++) {
                        place.get(i + 1).set(0, j);
                        place.get(i + 1).set(1, l);
                        nqueens(i + 1, n, place);
                    }
                }
            }
        }
    }

    public static void main(String[] args)
    {
        int n = 2;
        System.out.println("Placing n queens in n*n*n cells");
        System.out.println();
        ArrayList<ArrayList<Integer>> place = new ArrayList<ArrayList<Integer>>();
        for (int k = 0; k < n; k++)
        {
            place.add(new ArrayList<Integer>());
            place.get(k).add(0);
            place.get(k).add(0);
        }
        nqueens(-1, n, place);
        System.out.println("When n=2");
        System.out.println("Placing 2 queens in 8 cells");
        System.out.println("Legal number of queen solutions : " + totalSol);
        System.out.println();
        ArrayList<ArrayList<Integer>> place2 = new ArrayList<ArrayList<Integer>>();
        totalSol=0;
        n=3;
        for (int k = 0; k < n; k++) {
            place2.add(new ArrayList<Integer>());
            place2.get(k).add(0);
            place2.get(k).add(0);
        }
        nqueens(-1, n, place2);
        System.out.println("When n=3");
        System.out.println("Placing 3 queens in 27 cells");
        System.out.println("Legal number of queen solutions : " + totalSol);
        System.out.println();
        ArrayList<ArrayList<Integer>> place3 = new ArrayList<ArrayList<Integer>>();
        totalSol=0;
        n=4;
        for (int k = 0; k < n; k++) {
            place3.add(new ArrayList<Integer>());
            place3.get(k).add(0);
            place3.get(k).add(0);
        }
        nqueens(-1, n, place3);
        System.out.println("When n=4");
        System.out.println("Placing 4 queens in 64 cells");
        System.out.println("Legal number of queen solutions : " + totalSol);
        System.out.println();
        ArrayList<ArrayList<Integer>> place4 = new ArrayList<ArrayList<Integer>>();
        totalSol=0;
        n=5;
        for (int k = 0; k < n; k++) {
            place4.add(new ArrayList<Integer>());
            place4.get(k).add(0);
            place4.get(k).add(0);
        }
        nqueens(-1, n, place4);
        System.out.println("When n=5");
        System.out.println("Placing 5 queens in 125 cells");
        System.out.println("Legal number of queen solutions : " + totalSol);

    }

}
