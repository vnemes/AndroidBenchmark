package benchmark.benchmarksuite;

import android.util.Log;

import java.util.ArrayList;

import benchmark.Benchmarks;
import benchmark.IBenchmark;
import database.Database;
import database.Score;

/**
 * Created by Vendetta on 24-May-17.
 */

public class BenchmarkSuite implements IBenchmark {
    ArrayList<IBenchmark> benchmarkArr;
    String extra;

    public BenchmarkSuite() {
        benchmarkArr = new ArrayList<>();
        extra = "A suite of benchmarks composed of:\n" ;
    }

    @Override
    public void initialize() {
        StringBuilder sb = new StringBuilder();
        for (Benchmarks bench : Benchmarks.values()) {
            if (bench != Benchmarks.BenchmarkSuite) {
                try {
                    Log.d("Debug: ", bench.toString());
                    sb.append(bench.toString()+'\n');
                    IBenchmark benchmark = (IBenchmark) Class.forName("benchmark." + bench.toString().toLowerCase() + "." + bench.toString()).getConstructor().newInstance();
                    benchmark.initialize();
                    benchmarkArr.add(benchmark);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        extra += sb.toString();
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
        for (IBenchmark benchmark : benchmarkArr) {
            benchmark.run();
        }

    }

    @Override
    public void stop() {
        for (IBenchmark benchmark : benchmarkArr) {
            benchmark.stop();
        }
    }

    @Override
    public void clean() {

    }

    @Override
    public String getInfo() {
        return extra;
    }

    @Override
    public Score getScore() {
        double result = 1;
        int cnt = 0;
        StringBuilder sb = new StringBuilder();
        for (IBenchmark benchmark : benchmarkArr) {
            Score current = benchmark.getScore();
            Database.postBenchScore(current);
            if (Double.parseDouble(current.getResult()) != 0) {
                result *= Double.parseDouble(current.getResult());
                cnt++;
            }
            sb.append(current.getExtra()+'\n');
        }
        result = Math.pow(result, 1.0 / cnt);
        return new Score(Benchmarks.BenchmarkSuite.toString(),
                Long.toString((long)result),
                sb.toString());
    }

    @Override
    public void compute() {

    }

}
