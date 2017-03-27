package benchmark.CPUbenchmark;

import java.util.ArrayList;
import java.util.Random;

import benchmark.IBenchmark;

/**
 * Created by Vendetta on 07-Mar-17.
 */

public class IntegerMathCPUBenchmark implements IBenchmark {
    private Long poolSize = Long.MAX_VALUE;
    private boolean shouldTestRun;

    @Override
    public void initialize(){

    }

    @Override
    public void initialize(Long size) {
        this.poolSize = size;
        this.initialize();
    }
    @Override
    public void warmup(){Long prevSize = this.poolSize;
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

    @Override
    public void run() {
        this.shouldTestRun = true;
        Long result = 1L;
        for (Long i=0L; i< this.poolSize && shouldTestRun; i++){
            result += i/256L;
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
