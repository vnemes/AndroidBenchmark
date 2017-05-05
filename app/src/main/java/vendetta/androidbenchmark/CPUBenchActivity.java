package vendetta.androidbenchmark;

/**
 * Created by Vendetta on 09-Mar-17.
 */

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import benchmark.CPUbenchmark.FloatingPointMathCPUBenchmark;
import benchmark.CPUbenchmark.IntegerMathCPUBenchmark;
import benchmark.CPUbenchmark.PiDigitsCPUBenchmark;
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
    private ConsoleLogger logger = new ConsoleLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cpubench);

        intBench = new IntegerMathCPUBenchmark();
        floatBench = new FloatingPointMathCPUBenchmark();
        piBench = new PiDigitsCPUBenchmark();

        intBench.initialize(1000000000L);
        floatBench.initialize(1000000000L);
        piBench.initialize(10000L);
    }

    /**
     * Run benchmarks and measure the time.
     */
    public void startBenchmark(View view) {
        TextView result = (TextView) findViewById(R.id.cpubenchresult);
        Timer timer = new Timer();

        timer.start();
        intBench.run();
        Long intBenchResult = timer.stop();
        result.setText("int: " + String.format(java.util.Locale.US,"%.2f", myTimeUnit.convertTime(intBenchResult, myTimeUnit.Second)));

        timer.start();
        floatBench.run();
        Long floatBenchResult = timer.stop();
        result.append("\nfloat: " + String.format(java.util.Locale.US,"%.2f", myTimeUnit.convertTime(floatBenchResult, myTimeUnit.Second)));

        timer.start();
        piBench.run();
        Long piBenchResult = timer.stop();
        logger.write(piBench.getPi().toString());
        result.append("\nPI: " + String.format(java.util.Locale.US,"%.2f", myTimeUnit.convertTime(piBenchResult, myTimeUnit.Second)));
    }
}
