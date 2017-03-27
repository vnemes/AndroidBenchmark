package benchmark.CPUbenchmark;

import benchmark.IBenchmark;

/**
 * Created by Vendetta on 16-Mar-17.
 */

public class FloatingPointMathCPUBenchmark implements IBenchmark {
    private Long poolSize = Long.MAX_VALUE;
    private boolean shouldTestRun;

    @Override
    public void initialize() {

    }

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
    public void run(Object... param) {

    }

    @Override
    public void run() {
        this.shouldTestRun = true;
        Double result = 0.0;
        for (Long i=0L; i< this.poolSize && shouldTestRun; i++){
            result += (double)i /256.0;
        }
    }

    @Override
    public void stop() {
        this.shouldTestRun = false;
    }

    @Override
    public void clean() {

    }
}
