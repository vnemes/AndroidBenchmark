package benchmark.CPUbenchmark;

import java.math.BigDecimal;

import benchmark.IBenchmark;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Created by Vendetta on 09-Mar-17.
 */

public class PiDigitsCPUBenchmark implements IBenchmark {
    private static final BigDecimal ONE = new BigDecimal(1);
    private static final BigDecimal TWO = new BigDecimal(2);
    private static final BigDecimal FOUR = new BigDecimal(4);
    private int SCALE = 50; //default value 50 digits
    private BigDecimal piResult;

    @Override
    public void initialize() {

    }

    @Override
    public void initialize(Long size) {
        this.SCALE = size.intValue();
        this.initialize();
    }


    // the Babylonian square root method (Newton's method)
    public static BigDecimal sqrt(BigDecimal A, final int SCALE) {
        BigDecimal x0 = new BigDecimal("0");
        BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));

        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = A.divide(x0, SCALE, ROUND_HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(TWO, SCALE, ROUND_HALF_UP);
        }
        return x1;
    }

    public void warmup(){
        int prevSCALE = this.SCALE;
        this.SCALE = 60;
        for (int i =0; i<40; i++)
            this.run();
        this.SCALE = prevSCALE;
    }

    @Override
    public void run(Object... param) {
        this.run();
    }

    @Override
    public void run() {
        BigDecimal a = ONE;
        BigDecimal b = ONE.divide(sqrt(TWO, SCALE), SCALE, ROUND_HALF_UP);
        BigDecimal t = new BigDecimal(0.25);
        BigDecimal x = ONE;
        BigDecimal y;

        while (!a.equals(b)) {
            y = a;
            a = a.add(b).divide(TWO, SCALE, ROUND_HALF_UP);
            b = sqrt(b.multiply(y), SCALE);
            t = t.subtract(x.multiply(y.subtract(a).multiply(y.subtract(a))));
            x = x.multiply(TWO);
        }
        this.piResult = a.add(b).multiply(a.add(b)).divide(t.multiply(FOUR), SCALE, ROUND_HALF_UP);
    }

    @Override
    public void stop() {

    }

    public BigDecimal getPi(){
        return this.piResult;
    }

    @Override
    public void clean() {

    }
}
