package vendetta.androidbenchmark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import database.Database;
import database.RankScore;
import database.Score;

/**
 * Created by Vendetta on 23-May-17.
 */

public class ScoreActivity extends BaseActivity {
    private static TextView scoreTv;
    private static TextView extraTV;
    private static LinearLayout rankingsContainer;
    private static ProgressBar progressBar;
    private String benchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_score);
        scoreTv = (TextView) findViewById(R.id.scoreTV);
        extraTV = (TextView) findViewById(R.id.extraTV);
        rankingsContainer = (LinearLayout) findViewById(R.id.resultScrollContainerLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        benchName = intent.getStringExtra(BENCH_NAME);
        Database.getBenchScore(benchName);
    }

    public static void updateResult(Score score){
        scoreTv.setText(score.getResult());
        extraTV.setText(score.getExtra());
        progressBar.setVisibility(View.GONE);
    }

    public static void updateRanking(ArrayList<RankScore> scores){
        //TODO Add to rankingsContainer the values from the scores
    }
}
