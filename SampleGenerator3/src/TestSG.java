import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Assert;
import org.junit.Test;

public class TestSG {
    @Test
    public void testValue() {

        double[] expected = {2,6,24};
        //moments = 2,6,24
        double[][] tau_raw = {{1,0}};
        double[][] T_raw = {{-1,1},{0,-1}};
        RealMatrix tau = MatrixUtils.createRealMatrix(tau_raw);
        RealMatrix T = MatrixUtils.createRealMatrix(T_raw);

        SampleGenerator sg = new SampleGeneratorPH(tau, T);
        double[] result = sg.calculateStats(sg.generateSample(10000000));
        Assert.assertEquals(expected[0], result[0], 0.001); //1st moment
        Assert.assertEquals(expected[1], result[1], 0.01); //2nd moment
        Assert.assertEquals(expected[2], result[2], 0.05); //3rd moment
//        Assert.assertArrayEquals(expected, sg.calculateStats(sg.generateSample(10000000)), 0.05);

        //moments = 3,30,3000

    }
}

