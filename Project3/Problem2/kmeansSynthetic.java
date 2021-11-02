import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class kmeansSynthetic
{
    static class ReadData
    {

        protected static List<double[]> features=new ArrayList<>();
        protected static int numberOfFeatures;

        public List<double[]> getFeatures()
        {
            return features;
        }

        void read(String s) throws NumberFormatException, IOException {

            File file=new File(s);

            try {
                BufferedReader br=new BufferedReader(new FileReader(file));
                String line;
                br.readLine();
                while((line=br.readLine()) != null)
                {

                    String[] split = line.split(",");
                    double[] feature = new double[split.length - 1];
                    numberOfFeatures = split.length-1;
                    for (int i = 0; i < split.length - 1; i++)
                        feature[i] = Double.parseDouble(split[i]);
                    features.add(feature);
                }
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }

        }

    }

    public static double CalculateEucledianDistance(double[] point1, double[] point2)
    {
        double sum = 0.0;
        for(int i = 0; i < point1.length; i++)
        {
            sum += ((point1[i] - point2[i]) * (point1[i] - point2[i]));
        }
        return Math.sqrt(sum);
    }
    //method to calculate centroids
    public static double[] calculateCentroid(List<double[]> a) {

        int count = 0;
        double sum=0.0;
        double[] centroids = new double[ReadData.numberOfFeatures];
        for (int i = 0; i < ReadData.numberOfFeatures; i++) {
            sum=0.0;
            count = 0;
            for(double[] x:a){
                count++;
                sum = sum + x[i];
            }
            centroids[i] = sum / count;
        }

        return centroids;

    }

    //method for putting features to clusters and reassignment of clusters.
    public static Map<double[], Integer> kmeans(List<double[]> features, Map<Integer, double[]> centroids, int k) {
        Map<double[], Integer> clusters = new HashMap<>();
        int k1 = 0;
        double dist=0.0;
        for(double[] x:features) {
            double minimum = 999999.0;
            for (int j = 0; j < k; j++) {

                dist = CalculateEucledianDistance(centroids.get(j), x);


                if (dist < minimum) {
                    minimum = dist;
                    k1 = j;
                }

            }
            clusters.put(x, k1);
        }
        return clusters;

    }
    public static void main(String args[]) throws IOException {
        ReadData r1 = new ReadData();
        r1.features.clear();
        Scanner sc = new Scanner(System.in);
        r1.read("/Users/tazeen/Desktop/Project3_Test_Case.csv"); //load data
        int k = 2;
        int max_iterations = 50;
        //Hashmap to store centroids with index
        Map<Integer, double[]> centroids = new HashMap<>();
        // calculating initial centroids
        double[] x1 = new double[ReadData.numberOfFeatures];
        int r =0;
        for (int i = 0; i < k; i++) {

            x1=r1.features.get(r++);
            centroids.put(i, x1);

        }

        //Hashmap for finding cluster indexes
        Map<double[], Integer> clusters = new HashMap<>();
        clusters = kmeans(r1.features, centroids, k);
        double db[] = new double[ReadData.numberOfFeatures];
        //reassigning to new clusters
        for (int i = 0; i < max_iterations; i++) {
            for (int j = 0; j < k; j++) {
                List<double[]> list = new ArrayList<>();
                for (double[] key : clusters.keySet())
                {
                    if (clusters.get(key)==j) {
                        list.add(key);
                    }
                }
                db = calculateCentroid(list);
                centroids.put(j, db);
            }
            clusters.clear();

            clusters = kmeans(r1.features, centroids, k);

        }

        int c1=0,c2=0;
        for (double[] key : clusters.keySet()) {
            for (int i = 0; i < key.length; i++) {
                // System.out.print(key[i] + "\t \t");
            }
            //System.out.print(clusters.get(key) + "\n");
            if(clusters.get(key)==0){
                c1+=1;
            }
            else{
                c2+=1;
            }
        }
        double loss=0;

        for(int i=0;i<k;i++)
        {
            double dist=0;
            for (double[] key : clusters.keySet()) {
                if (clusters.get(key)==i) {
                    dist+=Math.pow(CalculateEucledianDistance(key, centroids.get(i)),2);
                }
            }
            loss+=dist;
        }
        loss=loss/(clusters.size());
        System.out.println("Dataset 1");
        System.out.println("K means Clustering of Data");
        System.out.println("Cluster 1");
        System.out.println("Centroid points: ");
        System.out.println(Arrays.toString(centroids.get(0)));
        System.out.println("Number of points in cluster 1 is "+c1);
        System.out.println("Cluster 2");
        System.out.println("Centroid points: ");
        System.out.println(Arrays.toString(centroids.get(1)));
        System.out.println("Number of points in cluster 2 is "+c2);
        System.out.println("Loss: "+loss);
        System.out.println("Iterations: "+max_iterations);
        System.out.println("Number of Clusters: "+k);
    }
}
