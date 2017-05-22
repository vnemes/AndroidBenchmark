package vendetta.androidbenchmark;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;

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

    public static void updateScores(UserScores userScores, Context context){
        scoresContainer.removeAllViewsInLayout();
        for (Map.Entry<String,String> entry: userScores.getScoreMap().entrySet()){

            LinearLayout currentLayout = new LinearLayout(context);
            currentLayout.setOrientation(LinearLayout.HORIZONTAL);
//            currentLayout.setPadding(5,2,5,2);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            currentLayout.setLayoutParams(lp);

            TextView BenchNameTView = new TextView(context);
            BenchNameTView.setTextAppearance(context,android.R.style.TextAppearance_Large);
            TableRow.LayoutParams tParams = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
            BenchNameTView.setLayoutParams(tParams);
            BenchNameTView.setMinWidth(60);
            BenchNameTView.setText(entry.getKey());

            TextView ScoreValTView = new TextView(context);
            ScoreValTView.setTextAppearance(context,android.R.style.TextAppearance_Large);
            tParams = new TableRow.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,2f);
            ScoreValTView.setLayoutParams(tParams);
            ScoreValTView.setGravity(Gravity.RIGHT);
            ScoreValTView.setText(entry.getValue());

            currentLayout.addView(BenchNameTView);
            currentLayout.addView(ScoreValTView);
            scoresContainer.addView(currentLayout);
        }
    }

}
