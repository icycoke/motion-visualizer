package com.icycoke.motion_visualizer;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.icycoke.motion_visualizer.dialog.NoSensorDialogFragment;
import com.icycoke.motion_visualizer.dialog.SensorListDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Main activity
 */
public class MainActivity extends AppCompatActivity
        implements SensorListDialogFragment.SensorListDialogFragmentListener {

    public static final String EXTRA_SENSOR_NAME = "com.icycoke.motion_visualizer.EXTRA_SENSOR_NAME";

    private FragmentManager fragmentManager;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        exitTime = 0;
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000)
        {
            Toast.makeText(getApplicationContext(), R.string.press_again_to_quit, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    public void startOnClick(View view) {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = new ArrayList<>();
        sensorList.addAll(sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER));
        sensorList.addAll(sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE));
        if (sensorList == null || sensorList.size() == 0) {
            noSensorAlert();
        } else {
            showSensorList(sensorList);
        }
    }

    /**
     * Show the dialog alerting there is no sensor detected
     */
    private void noSensorAlert() {
        DialogFragment noSensorDialogFragment = new NoSensorDialogFragment();
        noSensorDialogFragment.show(fragmentManager, "NoSensorDialogFragment");
    }

    /**
     * Show all detected sensors for user to select
     * @param sensorList All detected sensors
     */
    private void showSensorList(List<Sensor> sensorList) {
        DialogFragment sensorListDialogFragment = new SensorListDialogFragment(sensorList);
        sensorListDialogFragment.show(fragmentManager, "SensorListDialogFragment");
    }

    /**
     * Start recording by selected sensor
     * @param selectedSensor Sensor selected by the user
     */
    @Override
    public void selectOnClick(Sensor selectedSensor) {
        Intent intent = new Intent(this, RecordingActivity.class);
        intent.putExtra(EXTRA_SENSOR_NAME, selectedSensor.getName());
        startActivity(intent);
    }
}