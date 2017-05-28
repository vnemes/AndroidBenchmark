package vendetta.androidbenchmark;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import database.Database;
import database.Score;
import info.DeviceInfo;

/**
 * Created by Vendetta on 23-May-17.
 */

public class ScoreActivity extends BaseActivity {
    private static TextView scoreTv;
    private static TextView extraTV;
    private static LinearLayout rankingsContainer;
    private static ProgressBar progressBar;
    private static boolean wasBenchRun;
    private String benchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_score);
        TextView result = (TextView) findViewById(R.id.textView);
        scoreTv = (TextView) findViewById(R.id.scoreTV);
        extraTV = (TextView) findViewById(R.id.extraTV);
        rankingsContainer = (LinearLayout) findViewById(R.id.resultScrollContainerLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        benchName = intent.getStringExtra(BENCH_NAME);
        wasBenchRun = intent.getBooleanExtra(BENCH_RUN, false);
        if (wasBenchRun) Database.getBenchScore(benchName);
        else {
            scoreTv.setText(benchName);
            extraTV.setText("Here are the scores other users have gotten for this Benchmark:");
            result.setText("Rankings for");
        }
        Database.getRankings(benchName, this);
    }

    public static void updateResult(Score score) {
        scoreTv.setText(score.getResult());
        extraTV.setText(score.getExtra());
    }

    public static void updateRanking(HashMap<String, String> scores, Context context) {
        boolean isDifferent = false;
        rankingsContainer.removeAllViewsInLayout();
        if (wasBenchRun)
            scores.put("Your device: " + DeviceInfo.getFullDeviceName(), scoreTv.getText().toString());
        SortedSet<Map.Entry<String, String>> resultSet = new TreeSet<>(
                new Comparator<Map.Entry<String, String>>() {
                    @Override
                    public int compare(Map.Entry<String, String> e1, Map.Entry<String, String> e2) {
                        return Double.parseDouble(e1.getValue()) < (Double.parseDouble(e2.getValue())) ? 1 : -1;
                    }
                }
        );
        resultSet.addAll(scores.entrySet());
        for (final Map.Entry<String, String> entry : resultSet) {

            LinearLayout currentLayout = new LinearLayout(context);
            currentLayout.setOrientation(LinearLayout.HORIZONTAL);
            if (isDifferent) currentLayout.setBackgroundColor(Color.parseColor("#e8eaf6"));
            else currentLayout.setBackgroundColor(Color.parseColor("#a9b4f2"));
            if (entry.getKey().startsWith("Your device"))
                currentLayout.setBackgroundColor(Color.parseColor("#ff93a9"));
            currentLayout.setPadding(5, 5, 5, 0);
            isDifferent = !isDifferent;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            currentLayout.setLayoutParams(lp);

            TextView benchNameTView = new TextView(context);
            benchNameTView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
            TableRow.LayoutParams tParams = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            benchNameTView.setLayoutParams(tParams);
            benchNameTView.setMinWidth(60);
            benchNameTView.setText(entry.getKey());

            TextView ScoreValTView = new TextView(context);
            ScoreValTView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
            tParams = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f);
            ScoreValTView.setLayoutParams(tParams);
            ScoreValTView.setGravity(Gravity.RIGHT);
            ScoreValTView.setText(entry.getValue());

            currentLayout.addView(benchNameTView);
            currentLayout.addView(ScoreValTView);
            rankingsContainer.addView(currentLayout);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
