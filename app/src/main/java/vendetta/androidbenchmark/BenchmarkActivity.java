package vendetta.androidbenchmark;

/**
 * Created by Vendetta on 09-Mar-17.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;

import benchmark.IBenchmark;
import log.myTimeUnit;
import stopwatch.Timer;

/**
 * Measures CPU performance.
 */
public class BenchmarkActivity extends BaseActivity {
    private IBenchmark benchmark = null;
    private TextView result;
//    private IntegerMathCPUBenchmark intBench;
//    private FloatingPointMathCPUBenchmark floatBench;
//    private PiDigitsCPUBenchmark piBench;
//    private DownloadSpeedNetworkBenchmark networkSpeedBench;
//    private HashingBenchmark hashingBench;
//    private ConsoleLogger logger = new ConsoleLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_cpubench);
        result = (TextView) findViewById(R.id.cpubenchresult);

        Intent intent = getIntent();
        String benchName = intent.getStringExtra(BENCH_NAME);
        try {
            Log.d("Debug: ",benchName);
            benchmark = (IBenchmark) Class.forName("benchmark."+benchName.toLowerCase()+"."+benchName).getConstructor().newInstance();
            benchmark.initialize();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


//        intBench = new IntegerMathCPUBenchmark();
//        floatBench = new FloatingPointMathCPUBenchmark();
//        piBench = new PiDigitsCPUBenchmark();
//        networkSpeedBench = new DownloadSpeedNetworkBenchmark();
//        hashingBench = new HashingBenchmark();
//
//        hashingBench.initialize(100L);
//        networkSpeedBench.initialize(1024 * 1024 * 128L);
//        intBench.initialize(1000000000L);
//        floatBench.initialize(1000000000L);
//        piBench.initialize(10000L);


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
//                logger.write("Benchmark is starting...");
                timer.start();
                benchmark.run();
                return timer.stop();
            }

            protected void onPostExecute(Long time) {
                result.append("Hashing: " + myTimeUnit.convertTime(time, myTimeUnit.MilliSecond));
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to go back?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        benchmark.stop();
                        benchmark.clean();
                        BenchmarkActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
