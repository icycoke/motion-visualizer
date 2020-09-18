package com.icycoke.motion_visualizer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.List;

/**
 * Activity plots graph based on the collected data
 */
public class PlotActivity extends AppCompatActivity {

    private XYPlot plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot_acitivity);

        makeGraph();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * Make the graph based on data
     */
    private void makeGraph() {
        plot = findViewById(R.id.plot);

        plot.getDomainTitle().setText("Time(s)");
        plot.getRangeTitle().setText("Value");

        String xTitle = "x";
        String yTitle = "y";
        String zTitle = "z";

        Intent intent = getIntent();
        List<Float> xList = (List<Float>) intent.getSerializableExtra(RecordingActivity.EXTRA_X_LIST);
        List<Float> yList = (List<Float>) intent.getSerializableExtra(RecordingActivity.EXTRA_Y_LIST);
        List<Float> zList = (List<Float>) intent.getSerializableExtra(RecordingActivity.EXTRA_Z_LIST);
        List<Float> timeList = (List<Float>) intent.getSerializableExtra(RecordingActivity.EXTRA_TIME_LIST);

        XYSeries xSeries = new SimpleXYSeries(timeList, xList, xTitle);
        XYSeries ySeries = new SimpleXYSeries(timeList, yList, yTitle);
        XYSeries zSeries = new SimpleXYSeries(timeList, zList, zTitle);

        plot.addSeries(xSeries, new LineAndPointFormatter(Color.RED, null, null, null));
        plot.addSeries(ySeries, new LineAndPointFormatter(Color.GREEN, null, null, null));
        plot.addSeries(zSeries, new LineAndPointFormatter(Color.BLUE, null, null, null));
    }
}