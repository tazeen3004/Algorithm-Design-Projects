import java.util.Arrays; 
public class Problem1 {
    private static int[] sortMerge(int[] A, int[] A1)
    {
        int[] Aux = new int[A.length + A1.length];
        int x=0, y=0, z=0;  
        while (x < A.length && y < A1.length)
        {
            if (A[x] < A1[y]) 
            {
                Aux[z] = A[x];
                x++;
                z++;
            } 
            else
            {
                Aux[z] = A1[y];
                y++;
                z++;
            }
        }             
        while (x < A.length) 
        {
            Aux[z] = A[x];
            x++;
            z++;
        }            
        while (y < A1.length) 
        {
            Aux[z] = A1[y];
            y++;
            z++;
        } 
        A=Aux;     
        return A;
    }
     
    public static void main(String[] args) 
    {
        int[] arrayA = new int[] {}; 
        int[] A1 = new int[] {3,7,9};
        int[] Sorted = sortMerge(arrayA, A1);
        System.out.println("----TESTCASE 1----"); 
        System.out.println(Arrays.toString(Sorted));
        System.out.println();
        arrayA = new int[] {2,7,9}; 
        A1 = new int[] {1};
        Sorted = sortMerge(arrayA, A1);
        System.out.println("----TESTCASE 2----");
        System.out.println(Arrays.toString(Sorted));
        System.out.println();
        arrayA = new int[] {1,7,10,15}; 
        A1 = new int[] {3, 8, 12, 18};
        Sorted = sortMerge(arrayA, A1);
        System.out.println("----TESTCASE 3----");
        System.out.println(Arrays.toString(Sorted));
        System.out.println();
        arrayA = new int[] {1, 3, 5, 5, 15, 18, 21}; 
        A1 = new int[] {5, 5, 6, 8, 10, 12, 16, 17, 17, 20, 25, 28};
        Sorted = sortMerge(arrayA, A1);
        System.out.println("----TESTCASE 4----");
        System.out.println(Arrays.toString(Sorted));
        System.out.println();
    }
}


