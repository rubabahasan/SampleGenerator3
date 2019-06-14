import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;
import java.util.Random;
import org.apache.commons.math3.distribution.ExponentialDistribution;

public class SampleGeneratorPH implements SampleGenerator{

//    int n;
//    double p, p_x;
//    double lambda_x1, lambda_x2, lambda_y;

    //states 1 to n and 0 is an absorption state. tau.columnDimension = n

    RealMatrix tau, T;

    double[] lambda_table;

//        ExponentialDistribution expDist_lambda_y, expDist_lambda_x1, expDist_lambda_x2;
    ExponentialDistribution expDist;
    Random rand;

    public SampleGeneratorPH(RealMatrix tau, RealMatrix T) {


//        n = T.getColumnDimension();
        /********************************
         p = tau.getEntry(0, 0);
         lambda_y = T.getEntry(0,1);
         lambda_x2 = (-1)*T.getEntry(n-1, n-1);

         double p_x_lambda_x1 = T.getEntry(n-2, n-1);

         lambda_x1 = -(T.getEntry(n-2, n-2));
         p_x = T.getEntry(n-2, n-1) / lambda_x1;


        expDist_lambda_y = new ExponentialDistribution(this.lambda_y);
         expDist_lambda_x1 = new ExponentialDistribution(this.lambda_x1);
         expDist_lambda_x2 = new ExponentialDistribution(this.lambda_x2);
         *********************************/


        this.tau = tau;
        this.T = T;
        expDist = new ExponentialDistribution(1);

        lambda_table = new double[tau.getColumnDimension()];

        rand = new Random();

        for (int i = 0; i < this.T.getRowDimension(); i++) {
            double sum = 0;
            for (int j = 0; j < this.T.getColumnDimension(); j++) {
                sum += this.T.getEntry(i, j);
            }
            lambda_table[i] = -this.T.getEntry(i, i);
            this.T.setEntry(i, i, -sum);
        }


        /***************** Print lambda_table ******************/
        System.out.println("Lambda_table");
        for(int i=0; i<lambda_table.length; i++)
        {
            System.out.println(lambda_table[i]);
        }
        System.out.println("-------------------");
    }

    @Override
    public String toString() {
        return "SampleGeneratorPH{\n" +
//                "n=" + n +
//                ", \np=" + p +
//                ", \np_x=" + p_x +
//                ", \nlambda_x1=" + lambda_x1 +
//                ", \nlambda_x2=" + lambda_x2 +
//                ", \nlambda_y=" + lambda_y +
                '}';
    }

    public Long generateSingleSample() {
        //RealMatrix tau;
        double var = 0;
        double r_p = rand.nextDouble();

        int state;

        for (state = 0; state < this.tau.getColumnDimension(); state++) {
            double p = tau.getEntry(0, state);
            if (r_p < p) {
                break;
            }
            r_p -= p;
        }
        //state is the starting state
//        System.out.println("\n\nInitial state is " + state + " when r_p is "+ r_p);

        while (state < this.tau.getColumnDimension()) {
            double lambda = lambda_table[state];
//            System.out.println("Lambda = " + lambda);
            var += expDist.sample() / lambda;
            double r = rand.nextDouble() * lambda;
            int next_state;
            for (next_state = 0; next_state < this.T.getColumnDimension(); next_state++) {
                double p = this.T.getEntry(state, next_state);
                if (r < p) {
                    break;
                }
                r -= p;
            }
            if (state == next_state) {
                state = this.tau.getColumnDimension();
            } else {
                state = next_state;
            }
//            System.out.println("State is " + state + " var is " + var);
        }


/*
        if(r_p < this.p)
        {
            for(int i=0; i<(n-2); i++)
            {
                var += (long)(expDist_lambda_y.sample() );
            }

            var += (long) (expDist_lambda_x1.sample() );

            double r_p_x = rand.nextDouble();
            if(r_p_x < this.p_x)
            {
                var += (long) (expDist_lambda_x2.sample() );
            }
        }
*/
        return (long)(var * 1000000000); // scale from sec to ns
//        return (long) var;

    }

    @Override
    public ArrayList<Long> generateSample(int N) {
        ArrayList<Long> alist = new ArrayList<Long>();
        for(int i=0; i<N; i++)
        {
            alist.add(generateSingleSample());
        }
        return alist;
    }
}
