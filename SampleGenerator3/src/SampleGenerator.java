import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public interface SampleGenerator {
    public long nextRand();
    public ArrayList<Long> generateSample(int N);
    public double[] calculateStats( ArrayList<Long> alist);
    public void reset(RealMatrix tau, RealMatrix T);
}
