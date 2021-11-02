import java.util.*; 
import java.math.*;
public class Problem2Ala {
    private static void Multiplication(BigInteger x, BigInteger y)
    {
        Hashtable<BigInteger, BigInteger> map = new Hashtable<BigInteger, BigInteger>();
        BigInteger product,operan,sign;
        product=new BigInteger("0");
        operan=new BigInteger("2");
        //to set sign
         int comparevalue = x.compareTo(product);
         int comparevalue1 = y.compareTo(product);
         sign = new BigInteger("1"); 
         if (comparevalue==-1 && comparevalue1==1){
             sign=new BigInteger("-1");  
         }
         else if ( comparevalue==1 && comparevalue1==-1){
            sign= new BigInteger("-1");    
         }
         else{
            sign= new BigInteger("1");
         }
         //using abs values
         BigInteger absolutevalue1 =x.abs();
         BigInteger absolutevalue2 =y.abs();
         //checking if no is even or odd
         BigInteger modulus=absolutevalue1.mod(BigInteger.TWO);
         if(!modulus.equals(BigInteger.ZERO))
         {
             map.put(absolutevalue1, absolutevalue2);

         }
         //implementing ala carte method only inserting odd values to the dictionary
         while (!absolutevalue1.equals(BigInteger.ZERO)){
            modulus=absolutevalue1.mod(BigInteger.TWO);
            if(!modulus.equals(BigInteger.ZERO))
            {
                map.put(absolutevalue1, absolutevalue2);
            }
            absolutevalue1=absolutevalue1.divide(BigInteger.TWO);
            absolutevalue2=absolutevalue2.multiply(BigInteger.TWO);
         }
        //adding all values for final product
        for (BigInteger val:map.values()){
            product= product.add(val);   
        }
        System.out.println(product.multiply(sign));
    }
     
    public static void main(String[] args) 
    {
        System.out.println("ALa Carte Multiplication");
        //TESTCASE1
        System.out.println("----TESTCASE1----"); 
        Multiplication(new BigInteger("7000"),new BigInteger("7294"));
        System.out.println();
        //TESTCASE2
        System.out.println("----TESTCASE2----"); 
        Multiplication(new BigInteger("25"),new BigInteger("5038385"));
        System.out.println();
        //TESTCASE3
        System.out.println("----TESTCASE3----"); 
        Multiplication(new BigInteger("-59724"),new BigInteger("783"));
        System.out.println();
        //TESTCASE4
        System.out.println("----TESTCASE4----"); 
        Multiplication(new BigInteger("8516"),new BigInteger("-82147953548159344"));
        System.out.println();
        System.out.println("----TESTCASE5----"); 
        //TESTCASE5
        Multiplication(new BigInteger("45952456856498465985"),new BigInteger("98654651986546519856"));
        System.out.println();
        System.out.println("----TESTCASE6----"); 
        //TESTCASE6
        Multiplication(new BigInteger("-45952456856498465985"),new BigInteger("-98654651986546519856"));
   
    }
}