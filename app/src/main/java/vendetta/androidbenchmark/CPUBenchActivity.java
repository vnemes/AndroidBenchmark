package vendetta.androidbenchmark;

/**
 * Created by Vendetta on 09-Mar-17.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import benchmark.CPUbenchmark.FloatingPointMathCPUBenchmark;
import benchmark.CPUbenchmark.IntegerMathCPUBenchmark;
import benchmark.CPUbenchmark.PiDigitsCPUBenchmark;
import benchmark.HashingBenchmark.BCryptHashingBenchmark;
import benchmark.NetworkBenchmark.DownloadSpeedNetworkBenchmark;
import log.myTimeUnit;
import stopwatch.Timer;
import log.ConsoleLogger;

/**
 * Measures CPU performance.
 */
public class CPUBenchActivity extends BaseActivity {
    private IntegerMathCPUBenchmark intBench;
    private FloatingPointMathCPUBenchmark floatBench;
    private PiDigitsCPUBenchmark piBench;
    private DownloadSpeedNetworkBenchmark networkSpeedBench;
    private BCryptHashingBenchmark hashingBench;
    private ConsoleLogger logger = new ConsoleLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cpubench);

        intBench = new IntegerMathCPUBenchmark();
        floatBench = new FloatingPointMathCPUBenchmark();
        piBench = new PiDigitsCPUBenchmark();
        networkSpeedBench = new DownloadSpeedNetworkBenchmark();
        hashingBench = new BCryptHashingBenchmark();

        hashingBench.initialize(100L);
        networkSpeedBench.initialize(1024 * 1024 * 128L);
        intBench.initialize(1000000000L);
        floatBench.initialize(1000000000L);
        piBench.initialize(10000L);
    }

    /**
     * Run benchmarks and measure the time.
     */
    public void startBenchmark(View view) {
        final TextView result = (TextView) findViewById(R.id.cpubenchresult);
        final Timer timer = new Timer();

        /*
        intBench.warmup();
        timer.start();
        intBench.run();
        Long intBenchResult = timer.stop();
        result.setText("int: " + myTimeUnit.convertTime(intBenchResult, myTimeUnit.MilliSecond));

        floatBench.warmup();
        timer.start();
        floatBench.run();
        Long floatBenchResult = timer.stop();
        result.append("\nfloat: " + myTimeUnit.convertTime(floatBenchResult, myTimeUnit.MilliSecond));

        piBench.warmup();
        timer.start();
        piBench.run();
        Long piBenchResult = timer.stop();

        logger.write(piBench.getPi().toString());
        result.append("\nPI: " + myTimeUnit.convertTime(piBenchResult, myTimeUnit.MilliSecond));
        */
        /*
        new AsyncTask<Void, Void, Double>() {
            protected Double doInBackground(Void... params) {
                networkSpeedBench.run();
                return networkSpeedBench.getResult();
            }

            protected void onPostExecute(Double mbs) {
                result.append("Network: " + String.format(java.util.Locale.US,"%.3f", mbs));
            }
        }.execute();
        */
        new AsyncTask<Void, Void, Long>() {
            protected Long doInBackground(Void... params) {
                logger.write("Benchmark is starting...");
                timer.start();
                hashingBench.run();
                return timer.stop();
            }

            protected void onPostExecute(Long time) {
                result.append("Hashing: " + myTimeUnit.convertTime(time, myTimeUnit.MilliSecond));
            }
        }.execute();
    }
}
