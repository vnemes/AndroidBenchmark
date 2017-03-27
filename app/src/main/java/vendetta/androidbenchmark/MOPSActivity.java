package vendetta.androidbenchmark;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import benchmark.CPUbenchmark.OPSCPUBenchmark;
import log.myTimeUnit;
import stopwatch.Timer;

public class MOPSActivity extends BaseActivity {
    private OPSCPUBenchmark bench;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_mops);
        bench = new OPSCPUBenchmark();
    }


    public void startMOPSBenchmark(View view){
        TextView resultTV = (TextView) findViewById(R.id.mopsbenchresult);
        EditText size = (EditText) findViewById(R.id.mopsbenchsize);
        Long bSize = Long.parseLong(size.getText().toString());
        double result = bench.computeMOPS(bSize);
        resultTV.setTextSize(30);
        resultTV.setTextColor(Color.parseColor("#29398A"));
        resultTV.setText("Result:\n"
                            + new DecimalFormat("###.###").format(result)
                            + "\nMOPS");
    }
}
