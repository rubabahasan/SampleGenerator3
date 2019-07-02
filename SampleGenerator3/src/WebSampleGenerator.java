import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public class WebSampleGenerator implements SampleGenerator{


    @Override
    public long nextRand() {
        return 0;
    }

    @Override
    public ArrayList<Long> generateSample(int N) {
        return null;
    }

    @Override
    public double[] calculateStats(ArrayList<Long> alist) {
        return new double[0];
    }

    @Override
    public void reset(RealMatrix tau, RealMatrix T) {

    }
}
