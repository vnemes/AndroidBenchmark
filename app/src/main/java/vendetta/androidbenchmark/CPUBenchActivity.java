package vendetta.androidbenchmark;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import benchmark.CPUbenchmark.FloatingPointMathCPUBenchmark;
import benchmark.CPUbenchmark.IntegerMathCPUBenchmark;
import benchmark.CPUbenchmark.PiDigitsCPUBenchmark;
import log.myTimeUnit;
import stopwatch.Timer;

/**
 * Created by Vendetta on 09-Mar-17.
 */

public class CPUBenchActivity extends BaseActivity {

    IntegerMathCPUBenchmark intBench;
    FloatingPointMathCPUBenchmark floatBench;
    PiDigitsCPUBenchmark pibench;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.content_cpubench);

            intBench = new IntegerMathCPUBenchmark();
            floatBench = new FloatingPointMathCPUBenchmark();

            intBench.initialize(10000L);
            floatBench.initialize(10000L);


            pibench = new PiDigitsCPUBenchmark();

            //pibench.initialize(45L);


        }

        public void startBenchmark(View view){
            TextView result = (TextView) findViewById(R.id.cpubenchresult);
            Timer timer = new Timer();
            timer.start();
            intBench.run();
            Long res1 = timer.stop();
            timer.start();
            floatBench.run();
            Long res2 = timer.stop();
            result.setText("int: "+  myTimeUnit.convertTime(res1, myTimeUnit.Second)+"\nfloat: "+ myTimeUnit.convertTime(res2, myTimeUnit.Second));
        }

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



}
