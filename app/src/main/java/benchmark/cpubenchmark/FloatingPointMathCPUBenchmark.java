package benchmark.cpubenchmark;

import benchmark.Benchmarks;
import benchmark.IBenchmark;
import database.Score;
import log.myTimeUnit;
import stopwatch.Timer;

/**
 * Created by Vendetta on 16-Mar-17.
 */

public class FloatingPointMathCPUBenchmark implements IBenchmark {
    private Long size = Long.MAX_VALUE;
    private boolean shouldTestRun;
    private String extra;
    private long result;

    @Override
    public void initialize() {
        this.size = 1000000000L;
        this.result = 0;
    }

    @Override
    public void initialize(Long size) {
        this.size = size;
        this.result = 0;
    }

    @Override
    public void warmup() {
        Long prevSize = this.size;
        this.size = 1000L;
        for (int i=1; i<=3; i++){
            run();
            this.size *= 10;
        }
        this.size = prevSize;
    }

    @Override
    public void run(Object... param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        final Timer timer = new Timer();
        this.warmup();
        timer.start();
        this.compute();
        this.result = timer.stop();
    }

    /**
     * Approximates square roots using the Babylonian Method.
     */
    @Override
    public void compute() {
        this.shouldTestRun = true;
        double sqrtPi = 1.0; // sqrt of PI
        double sqrtE = 1.0;  // sqrt of Euler's number
        double temp;
        for (double i = 0.0; i < this.size && this.shouldTestRun; i++){
            temp = sqrtPi + Math.PI / sqrtPi;
            sqrtPi = temp * 0.5;
            temp = sqrtE + Math.E / sqrtE;
            sqrtE = temp * 0.5;
        }
        this.shouldTestRun = false;
    }

    @Override
    public void stop() {
        this.shouldTestRun = false;
    }

    @Override
    public void clean() {}

    @Override
    public String getInfo(){
        return "";
    }

    @Override
    public Score getScore() {
        return new Score(
                Benchmarks.FloatingPointBenchmark.toString(),
                Long.valueOf(myTimeUnit.convertTime(this.result, myTimeUnit.MilliSecond)).toString(),
                this.extra
        );
    }
}