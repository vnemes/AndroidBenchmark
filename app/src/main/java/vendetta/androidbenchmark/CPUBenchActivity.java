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
        //piBench = new PiDigitsCPUBenchmark();

        intBench.initialize(10000000L);
        floatBench.initialize(10000000L);
        //pibench.initialize(45L);
    }

    /**
     * Run benchmarks and measure the time.
     */
    public void startBenchmark(View view) {
        logger.write("Bench started");
        TextView result = (TextView) findViewById(R.id.cpubenchresult);
        Timer timer = new Timer();

        timer.start();
        intBench.run();
        Long intBenchResult = timer.stop();
        logger.write("int bench over");

        timer.start();
        floatBench.run();
        Long floatBenchResult = timer.stop();
        logger.write("float bench over");

        result.setText("int: " +  String.format(java.util.Locale.US,"%.2f",myTimeUnit.convertTime(intBenchResult, myTimeUnit.Second))
                + "\nfloat: " + String.format(java.util.Locale.US,"%.2f",myTimeUnit.convertTime(floatBenchResult, myTimeUnit.Second)));
    }
/*
        public void startBenchmark1(View view){
            TextView result = (TextView) findViewById(R.id.cpubenchresult);
            Timer timer = new Timer();
            //ArrayList<Long> timevalues = new ArrayList<>();
//            for (int i=3; i<4; i++){
//                pibench.initialize(50 * (long)Math.pow(10,i));
//                timer.start();
//                //bench.run();
//                pibench.run();
//                timevalues.add(myTimeUnit.convertTime(timer.stop(),myTimeUnit.MiliSecond));
//            }
            pibench.warmup();
            pibench.initialize(2000L);
            timer.start();
            //bench.run();
            pibench.run();
            Long res = timer.stop();
            result.setTextSize(30);
            result.setText("Result: " + myTimeUnit.convertTime(res,myTimeUnit.MiliSecond) + "ms");
            //result.setText(timevalues.toString());
        }
*/


}
