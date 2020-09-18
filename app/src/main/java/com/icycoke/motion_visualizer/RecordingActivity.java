package com.icycoke.motion_visualizer;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.hardware.Sensor.TYPE_ALL;

/**
 * Activity shown in the foreground while recording data
 */
public class RecordingActivity extends AppCompatActivity {

    public static final String EXTRA_X_LIST = "com.icycoke.motion_visualizer.EXTRA_X_LIST";
    public static final String EXTRA_Y_LIST = "com.icycoke.motion_visualizer.EXTRA_Y_LIST";
    public static final String EXTRA_Z_LIST = "com.icycoke.motion_visualizer.EXTRA_Z_LIST";
    public static final String EXTRA_TIME_LIST = "com.icycoke.motion_visualizer.EXTRA_TIME_LIST";

    private SensorManager sensorManager;
    private Sensor selectedSensor;
    private SensorEventListener listener;
    private Intent intent;

    private List<Float> xList;
    private List<Float> yList;
    private List<Float> zList;
    private List<Float> timeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        getSelectedSensor();
        startRecording();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    /**
     * Start a {@link PlotActivity} and send collected data by a {@link Intent}
     *
     * @param view Stop button in this {@link RecordingActivity}
     */
    public void stopOnClick(View view) {
        sensorManager.unregisterListener(listener, selectedSensor);
        intent = new Intent(this, PlotActivity.class);

        intent.putExtra(EXTRA_X_LIST, (Serializable) xList);
        intent.putExtra(EXTRA_Y_LIST, (Serializable) yList);
        intent.putExtra(EXTRA_Z_LIST, (Serializable) zList);
        intent.putExtra(EXTRA_TIME_LIST, (Serializable) timeList);

        startActivity(intent);
    }

    /**
     * Get the sensor selected by user
     */
    private void getSelectedSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(TYPE_ALL);
        String sensorName = getIntent().getStringExtra(MainActivity.EXTRA_SENSOR_NAME);
        for (Sensor sensor : sensorList) {
            if (sensorName.equals(sensor.getName())) {
                selectedSensor = sensor;
            }
        }
    }

    /**
     * Start recording data
     */
    private void startRecording() {
        xList = new ArrayList<>();
        yList = new ArrayList<>();
        zList = new ArrayList<>();
        timeList = new ArrayList<>();

        final long startTimeMillis = System.currentTimeMillis();

        File file = new File(getFilesDir().getAbsolutePath() + "/data.csv");
        try {
            final FileWriter fileWriter = new FileWriter(file);

            listener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    try {
                        StringBuilder sb = new StringBuilder();
                        long curTimeMillis = System.currentTimeMillis();
                        float curTime = ((float) (curTimeMillis - startTimeMillis)) / 1000;

                        xList.add(event.values[0]);
                        yList.add(event.values[1]);
                        zList.add(event.values[2]);
                        timeList.add(curTime);

                        sb.append(curTime)
                                .append(" ");
                        for (float value : event.values) {
                            sb.append(value)
                                    .append(" ");
                        }
                        sb.append('\n');

                        fileWriter.write(sb.toString());
                        fileWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
            registerListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Register the event listener
     */
    private void registerListener() {
        sensorManager.registerListener(listener, selectedSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }
}