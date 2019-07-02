import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SampleGeneratorDriver {
    public static void writeToFile(ArrayList<Long> samples, String filename){

        Collections.sort(samples);

        try (FileWriter writer = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(writer)) {

            for(int i=0; i<samples.size(); i++)
            {
                bw.write(samples.get(i)+"\n");
            }


        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    public static void main(String[] args)
    {
        String outputFile = "sampleOutput.csv";
        int N = 100000000; //number of samples

        //moments = 2,6,24
        //double[][] tau_raw = {{1,0}};
        //double[][] T_raw = {{-1,1},{0,-1}};

        //moments = 3, 30, 3000
//        double[][] tau_raw = {{1,0}};
//        double[][] T_raw = {{-0.3431, 0.0004}, {0, -0.0137}};

        //moments = 3.5833, 16.1389, 87.7639
        double[][] tau_raw = {{1,0,0,0,0}};
        double[][] T_raw = {{-1,1,0,0,0}, {0,-1,1,0,0}, {0,0,-1,1,0},  {0,0,0,-3,1.5}, {0,0,0,0,-2}};


        //moments = 13.6876, 250.1553, 5199.5
//        double[][] tau_raw = {{0.7877,0,0,0,0,0,0,0,0,0.2123}};
//        double[][] T_raw = {{-0.4811,.4811,0,0,0, 0,0,0,0,0}, {0,-0.4811,.4811,0,0,0, 0,0,0,0}, {0,0,-0.4811,.4811,0,0,0, 0,0,0},  {0,0,0,-0.4811,.4811,0,0, 0,0,0}, {0,0,0,0,-0.4811,.4811,0, 0,0,0}, {0,0,0,0,0,-0.4811,.4811,0, 0,0}, {0,0,0,0,0,0,-0.4811,.4811, 0,0}, { 0,0,0,0,0,0,0,-0.4960,0.0145,0}, {0, 0,0,0,0,0,0,0, -.4656, 0.4656}, {0, 0,0,0,0,0,0,0,0,-.3995}};


        RealMatrix tau = MatrixUtils.createRealMatrix(tau_raw);
        RealMatrix T = MatrixUtils.createRealMatrix(T_raw);

        SampleGenerator sg = new SampleGeneratorPH(tau, T);
//        System.out.println(sg);
//        ArrayList<Long> samples = sg.generateSample(N);

        double sum1 = 0, sum2 = 0, sum3 = 0;
        for(int i=0; i<N; i++)
        {

//            double sample = samples.get(i)/1000000000.0;
            double sample = (double)sg.nextRand() / 1000000000.0;
//            System.out.println("Sample "  + sample);
            sum1 += sample;
            sum3 += Math.pow(sample, 3);
            sum2 += Math.pow(sample, 2);
//            sum3 += Math.pow(samples.get(i), 3);
        }

//        for(int i=0; i<N; i++)
//        {
//            double sample = samples.get(i)/1000000000.0;
//            sum2 += Math.pow(sample, 2);
////            sum2 += Math.pow(samples.get(i), 2);
//        }
//        System.out.println("Mean = " + (sum1) + "\n 2nd Moment = " +  (sum2) + "\n3rd Moment = " + (sum3));

        System.out.println("Mean = " + (sum1/(double)N) + "\n 2nd Moment = " +  (sum2/(double)N) + "\n3rd Moment = " + (sum3/(double)N));

//        writeToFile(samples, outputFile);
    }

}
