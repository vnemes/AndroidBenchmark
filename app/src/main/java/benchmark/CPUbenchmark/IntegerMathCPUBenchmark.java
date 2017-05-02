package benchmark.CPUbenchmark;

import benchmark.IBenchmark;

/**
 * Created by Vendetta on 07-Mar-17.
 */

public class IntegerMathCPUBenchmark implements IBenchmark {
    private Long poolSize = Long.MAX_VALUE;
    private boolean shouldTestRun;

    @Override
    public void initialize(){}

    @Override
    public void initialize(Long size) {
        this.poolSize = size;
        this.initialize();
    }

    @Override
    public void warmup(){
        Long prevSize = this.poolSize;
        this.poolSize = 10000L;
        for (int i=0; i<50; i++){
            run();
        }
        this.poolSize = prevSize;
    }

    @Override
    public void run(Object... param) {
        this.run();
    }

    /**
     * Does a lot of fixed point operations.
     */
    @Override
    public void run() {
        this.shouldTestRun = true;
        long result = 1L;
        for (long i = 0L; i < this.poolSize && shouldTestRun; i++) {
            result += i / 256L;
            result *= i % 3L + 1L;
            result /= i % 2L + 1L;
        }
    }

    @Override
    public void stop() {
        this.shouldTestRun = false;
    }

    @Override
    public void clean() {}
}
