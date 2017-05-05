package benchmark;

/**
 * Created by Vendetta on 07-Mar-17.
 */
public interface IBenchmark {

    public void initialize();

    public void initialize(Long size);

    public void warmup();

    public void run(Object...param);

    public void run();

    public void stop();

    public void clean();
}
