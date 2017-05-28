package vendetta.androidbenchmark;

/**
 * Created by Vendetta on 09-Mar-17.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;

import benchmark.IBenchmark;
import database.Database;
import database.Score;
import stopwatch.Timer;

/**
 * Measures CPU performance.
 */
public class BenchmarkActivity extends BaseActivity {
    private IBenchmark benchmark = null;
    private TextView benchDescriptionTV;
    private ProgressBar progressBar;
    private String benchName;
    private TextView benchNameTV;
    private AsyncTask benchAsyncTask;
    private Button startBenchmark;
    private Button viewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_benchmark);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        benchDescriptionTV = (TextView) findViewById(R.id.benchDescriptionTV);
        startBenchmark = (Button) findViewById(R.id.cpubenchstart);
        viewResults = (Button) findViewById(R.id.viewscores);
        benchNameTV = (TextView) findViewById(R.id.benchNameTV);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

        Intent intent = getIntent();
        benchName = intent.getStringExtra(BENCH_NAME);
        try {
            Log.d("Debug: ", benchName);
            benchmark = (IBenchmark) Class.forName("benchmark." + benchName.toLowerCase() + "." + benchName).getConstructor().newInstance();
            benchmark.initialize();
            benchDescriptionTV.setText(benchmark.getInfo());
            benchNameTV.setText(benchName);
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
        final TextView benchDescriptionFinalTV = (TextView) findViewById(R.id.benchDescriptionTV);
        progressBar.setVisibility(View.VISIBLE);
        viewResults.setClickable(false);
        startBenchmark.setClickable(false);

        final DrawerLayout drawerToggle = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        benchDescriptionFinalTV.append("\nRunning!");
        Toast.makeText(this,benchName+" started",Toast.LENGTH_LONG).show();


        benchAsyncTask = new AsyncTask<Void, Void, Score>() {
            protected Score doInBackground(Void... params) {
                benchmark.run();
                return benchmark.getScore();
            }

            protected void onPostExecute(Score score) {
                benchDescriptionFinalTV.setText("Uploading your results to the cloud.\nPlease wait...");
                Database.postBenchScore(score);
                progressBar.setVisibility(View.GONE);
                viewResults.setClickable(true);
                startBenchmark.setClickable(true);
                drawerToggle.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                Intent scoreActivityIntent = new Intent(getApplicationContext(), ScoreActivity.class);
                scoreActivityIntent.putExtra(BENCH_NAME, benchName);
                scoreActivityIntent.putExtra(BENCH_RUN, true);
                startActivity(scoreActivityIntent);
                finish();
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
                        if (benchAsyncTask != null)
                            benchAsyncTask.cancel(true);
                        benchmark.stop();
                        benchmark.clean();
                        finish();
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

    public void viewScores(View view){
        Intent scoreActivityIntent = new Intent(getApplicationContext(), ScoreActivity.class);
        scoreActivityIntent.putExtra(BENCH_NAME, benchName);
        scoreActivityIntent.putExtra(BENCH_RUN, false);
        startActivity(scoreActivityIntent);
        finish();
    }
}
