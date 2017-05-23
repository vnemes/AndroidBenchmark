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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;

import benchmark.IBenchmark;
import database.Database;
import database.Score;
import log.myTimeUnit;
import stopwatch.Timer;

/**
 * Measures CPU performance.
 */
public class BenchmarkActivity extends BaseActivity {
    private IBenchmark benchmark = null;
    private TextView result;
    private ProgressBar progressBar;
    private String benchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_benchmark);
        result = (TextView) findViewById(R.id.cpubenchresult);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

        Intent intent = getIntent();
        benchName = intent.getStringExtra(BENCH_NAME);
        try {
            Log.d("Debug: ",benchName);
            benchmark = (IBenchmark) Class.forName("benchmark."+benchName.toLowerCase()+"."+benchName).getConstructor().newInstance();
            benchmark.initialize();
            result.setText(benchmark.getInfo());
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
    }

    /**
     * Run benchmarks and measure the time.
     */
    public void startBenchmark(View view) {
        final TextView result = (TextView) findViewById(R.id.cpubenchresult);
        final Timer timer = new Timer();
        progressBar.setVisibility(View.VISIBLE);
        result.append("\n\nRunning!");


        new AsyncTask<Void, Void, Score>() {
            protected Score doInBackground(Void... params) {
                benchmark.run();
                return benchmark.getScore();
            }

//        new AsyncTask<Void, Void, Score>() {
//            protected Score doInBackground(Void... params) {
////                logger.write("Benchmark is starting...");
//                benchmark.run();
//                benchmark.clean();
//                return benchmark.getScore();
//            }
//
//            protected void onPostExecute(Score score) {
//                result.append("Hashing: " + score.getResult());
//            }

            protected void onPostExecute(Score score) {
                result.setText(benchmark.getScore().toString());
                Database.postBenchScore(benchmark.getScore());
                progressBar.setVisibility(View.GONE);
                Intent scoreActivityIntent = new Intent(getApplicationContext(),ScoreActivity.class);
                scoreActivityIntent.putExtra(BENCH_NAME,benchName);
                startActivity(scoreActivityIntent);
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
