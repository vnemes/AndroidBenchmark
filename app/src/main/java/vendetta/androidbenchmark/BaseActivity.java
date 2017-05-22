package vendetta.androidbenchmark;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import benchmark.Benchmarks;
import info.DeviceInfo;

/**
 * Created by Vendetta on 20-Mar-17.
 */

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String BENCH_NAME = "benchName";
    private FrameLayout view_stub; //This is the framelayout to keep your content view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //DeviceInfo deviceInfo = new DeviceInfo();
        view_stub = (FrameLayout) findViewById(R.id.view_stub);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView manufacturerTextView = (TextView) header.findViewById(R.id.manufacturertxtview);
        TextView modelTextView = (TextView) header.findViewById(R.id.modeltxtview);
        Log.d("Manufacturer: ", DeviceInfo.getManufacturer());
        Log.d("Model: ",DeviceInfo.getModel());
        manufacturerTextView.setText("Manufacturer: " +DeviceInfo.getManufacturer());
        modelTextView.setText("Model: " +DeviceInfo.getModel());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void setContentView(int layoutResID) {
        if (view_stub != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, view_stub, false);
            view_stub.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub != null) {
            view_stub.addView(view, params);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true; //TODO go to info screen
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent benchActivityIntent = new Intent(getApplicationContext(),BenchmarkActivity.class);

        if (id == R.id.nav_cpubench) {
            benchActivityIntent.putExtra(BENCH_NAME, Benchmarks.CPUBenchmark.toString());
        } else if (id == R.id.nav_hashbench) {
            benchActivityIntent.putExtra(BENCH_NAME, Benchmarks.HashingBenchmark.toString());
        } else if (id == R.id.nav_filebench) {
            benchActivityIntent.putExtra(BENCH_NAME, Benchmarks.HDDBenchmark.toString());
        } else if (id == R.id.nav_netbench) {
            benchActivityIntent.putExtra(BENCH_NAME, Benchmarks.NetworkBenchmark.toString());
        } else if (id == R.id.nav_share) {
            benchActivityIntent = new Intent(getApplicationContext(),BenchmarkActivity.class);
        } else if (id == R.id.nav_leaderboard) { //TODO update share and leaderboard to launch other activities
            benchActivityIntent = new Intent(getApplicationContext(),BenchmarkActivity.class);
        }

        startActivity(benchActivityIntent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
