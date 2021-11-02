public class Problem2Rectangle {
	public static void rectangle(String x, String y) {
        //removing - if input contains - sign to avoid multiplication error
        String xsign="+",ysign="+";
        if (x.substring(0, 1).equals("-"))
        {
            x=x.substring(1);
            xsign="-";  
        }
        if (y.substring(0,1).equals("-"))
        {
            y=y.substring(1);
            ysign="-";
        }
        if(x.length()!=y.length()){
            int xLen = x.length();
            int yLen = y.length();
            if(x.length()>y.length()){
                for(int i=0;i<xLen-yLen;i++){
                    y = "0" + y;
                }
            }
            else
            {
                for(int i=0;i<yLen-xLen;i++){
                    x = "0" + x;
                }
            }
        }
        int result[] = new int[y.length()+x.length()];
        String sign="+";
        String x1 = new StringBuffer(y).reverse().toString();
        String y1 = new StringBuffer(x).reverse().toString();
        //reversing the no
        int n[] = new int[y.length()];
        int m[] = new int[x.length()];
        int size_of_array = 0;
        for(int i=0;i<y.length();i++){
            n[i] = Integer.parseInt(String.valueOf(x1.charAt(i)));
            m[i] = Integer.parseInt(String.valueOf(y1.charAt(i)));
        }
        size_of_array = (m.length*2)*2;
        int soa = size_of_array;
        int tempArray[] = new int[size_of_array*m.length];
        //placing the numbers in a single array
        int oddValue =3;
        int index = 0;
        tempArray[index++] = 0;
        for(int i=0;i<m.length;i++){
            for(int j=0;j<n.length;j++){
                tempArray[index++] = (n[j] * m[i]) % 10;
                tempArray[index] = (n[j] * m[i]) / 10;   
                index++;
            }
            size_of_array=(i+1)*soa;
            index = size_of_array;
            index+=i+oddValue;
            oddValue++;  
        }
        //adding the numbers and also dealing with carry
        int count=0;int ind = 0;int res = 0;int carry =0;int tempInd = 0;
        for(int i=0;i<m.length*2;i++){
            tempInd = ind;
            for(int k=0;k<m.length;k++){
                res = tempArray[ind] + tempArray[ind+1] + res + carry;
                ind = ind + soa ;
                carry = 0;   
            }
            result[count++] = res %10;
            carry = res /10;
            res = 0;
            ind = tempInd+2;
        }
        // index to store the first  
        ind = -1; 
        int a[]=new int [result.length];
        int j = result.length;
        for (int i = 0; i < result.length; i++) { 
        a[j - 1] = result[i]; 
        j = j - 1; 
        } 
        int l=a.length;
        // traverse in the array and find the first non-zero number 
        for (int i = 0; i < l; i++) { 
        if (a[i] != 0) { 
            ind = i; 
            break; 
        } 
        }       
        // if no non-zero number is there 
        if (ind == -1) { 
            System.out.print("Array has leading zeros only"); 
        return; 
        } 
        int[] b = new int[l - ind]; 
        // store the numbers removing leading zeros 
           for (int i = 0; i < l - ind; i++) 
            b[i] = a[ind + i]; 
        if (xsign.equals("+") && ysign.equals("-"))
        {
            sign="-";
        }
        else if (xsign.equals("-") && ysign.equals("+")){
            sign="-";
        }
        else{
            sign="";
        }
        System.out.print("The product is "+sign);
        for(int i = 0; i < l - ind; i++){
            System.out.print(b[i]); 
        }   
    }
    public static void main(String[] args) 
    { 
        System.out.println("Rectangle Multiplication");
        System.out.println("-----TESTCASE 1 -----");
        rectangle("7000","7294");
        System.out.println();
        System.out.println();
        System.out.println("-----TESTCASE 2 -----");
        rectangle("25","5038385");
        System.out.println();
        System.out.println();
        System.out.println("-----TESTCASE 3 -----");
        rectangle("-59724","783");
        System.out.println();
        System.out.println();
        System.out.println("-----TESTCASE 4 -----");
        rectangle("8516","-82147953548159344");
        System.out.println();
        System.out.println();
        System.out.println("-----TESTCASE 5 -----");
        rectangle("45952456856498465985","98654651986546519856");
        System.out.println();
        System.out.println();
        System.out.println("-----TESTCASE 6 -----");
        rectangle("-45952456856498465985","-98654651986546519856");
        System.out.println();
    }	
}
