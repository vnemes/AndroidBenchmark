package benchmark.CPUbenchmark;

import benchmark.IBenchmark;

/**
 * Created by Vendetta on 16-Mar-17.
 */

public class FloatingPointMathCPUBenchmark implements IBenchmark {
    private Long poolSize = Long.MAX_VALUE;
    private boolean shouldTestRun;

    @Override
    public void initialize() {}

    @Override
    public void initialize(Long size) {
        this.poolSize = size;
    }

    @Override
    public void warmup() {
        Long prevSize = this.poolSize;
        this.poolSize = 10000L;
        for (int i=0; i<50; i++){
            run();
        }
        this.poolSize = prevSize;
    }

    @Override
    public void run(Object... param) {}

    /**
     * Approximates square roots using the Babylonian Method.
     */
    @Override
    public void run() {
        this.shouldTestRun = true;
        double sqrtPi = 1.0; // sqrt of PI
        double sqrtE = 1.0;  // sqrt of Euler's number
        double temp;
        for (double i=0.0; i< this.poolSize && shouldTestRun; i++){
            temp = sqrtPi + 3.14159265359 / sqrtPi;
            sqrtPi = temp * 0.5;
            temp = sqrtE + 2.71828182845 / sqrtE;
            sqrtE = temp * 0.5;
        }
    }

    @Override
    public void stop() {
        this.shouldTestRun = false;
    }

    @Override
    public void clean() {}
}