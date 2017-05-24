package benchmark.cpubenchmark;

import benchmark.IBenchmark;
import database.Score;

/**
 * Created by Vendetta on 24-May-17.
 */

public class CPUBenchmark implements IBenchmark {
    private FloatingPointMathCPUBenchmark floatBench = new FloatingPointMathCPUBenchmark();
    private IntegerMathCPUBenchmark intBench = new IntegerMathCPUBenchmark();
    private PiDigitsCPUBenchmark piBench = new PiDigitsCPUBenchmark();


    @Override
    public void initialize() {
        floatBench.initialize();
        intBench.initialize();
        piBench.initialize();

    }

    @Override
    public void initialize(Long size) {

    }

    @Override
    public void warmup() {

    }

    @Override
    public void run(Object... param) {

    }

    @Override
    public void run() {
        intBench.run();
        floatBench.run();
        piBench.run();

    }

    @Override
    public void stop() {
        intBench.stop();
        floatBench.stop();
        piBench.stop();
    }

    @Override
    public void clean() {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Score getScore() {
        return null;
    }

    @Override
    public void compute() {

    }

    @Override
    public Object getResult() {
        return null;
    }
}
