package vendetta.androidbenchmark;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;

import benchmark.Benchmarks;
import database.Database;
import database.UserScores;

public class MainActivity extends BaseActivity {
    private static LinearLayout scoresContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        scoresContainer = (LinearLayout) findViewById(R.id.scoreContainerLayout);
        Database.establishConnection(this);
    }

    public static void updateScores(UserScores userScores, Context context) {
        boolean isDifferent = false;
        scoresContainer.removeAllViewsInLayout();
        for (final Map.Entry<String, String> entry : userScores.getScoreMap().entrySet()) {

            LinearLayout currentLayout = new LinearLayout(context);
            currentLayout.setOrientation(LinearLayout.HORIZONTAL);
            if (isDifferent) currentLayout.setBackgroundColor(Color.parseColor("#e8eaf6"));
            else currentLayout.setBackgroundColor(Color.parseColor("#a9b4f2"));
            isDifferent = !isDifferent;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            currentLayout.setLayoutParams(lp);

            TextView benchNameTView = new TextView(context);
            benchNameTView.setTextAppearance(context, android.R.style.TextAppearance_Large);
            TableRow.LayoutParams tParams = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            benchNameTView.setLayoutParams(tParams);
            benchNameTView.setMinWidth(60);
            benchNameTView.setText(entry.getKey());

            TextView ScoreValTView = new TextView(context);
            ScoreValTView.setTextAppearance(context, android.R.style.TextAppearance_Large);
            tParams = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f);
            ScoreValTView.setLayoutParams(tParams);
            ScoreValTView.setGravity(Gravity.RIGHT);
            ScoreValTView.setText(entry.getValue());
            ScoreValTView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent benchActivityIntent = new Intent(v.getContext(), BenchmarkActivity.class);
                    benchActivityIntent.putExtra(BENCH_NAME, entry.getKey());
                    v.getContext().startActivity(benchActivityIntent);
                }
            });

            currentLayout.addView(benchNameTView);
            currentLayout.addView(ScoreValTView);
            scoresContainer.addView(currentLayout);
        }
    }

    public void startFullBenchmark(View v) {
        //TODO launh full benchmark suite here
        Log.d("debug", "should start the benchmark suite");
    }

}
