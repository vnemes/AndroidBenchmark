package benchmark;

import database.Score;

/**
 * Created by Vendetta on 07-Mar-17.
 */
public interface IBenchmark {

    /**
     * Initializes the benchmark using the default settings.
     */
    public void initialize();

    /**
     * Initializes the benchmark with a custom size.
     *
     * @param size the size of the benchmark. A greater size results in a heavier benchmark.
     */
    public void initialize(Long size);

    /**
     * Prepares the benchmark.
     */
    public void warmup();

    /**
     * Run the benchmark.
     *
     * @param param used to customize the benchmark. Not needed by all benchmarks.
     */
    public void run(Object...param);

    /**
     * Run the default benchmark. Some benchmarks need to take parameters in order to run.
     */
    public void run();

    /**
     * Stops the benchmark and all processes associated with it.
     */
    public void stop();

    /**
     * Free resources.
     */
    public void clean();

    /**
     *
     * @return Information regarding the benchmark
     */
    public String getInfo();

    /**
     * @return Benchmark score
     */
    public Score getScore();

    /**
     * Does the actual computation.
     */
    public void compute();

    public Object getResult();
}
