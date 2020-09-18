package com.icycoke.motion_visualizer.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.icycoke.motion_visualizer.R;

import java.util.List;

/**
 * Dialog menu of all detected sensors
 */
public class SensorListDialogFragment extends DialogFragment {

    /**
     * Listener of this dialog fragment
     */
    public interface SensorListDialogFragmentListener {
        void selectOnClick(Sensor selectedSensor);
    }

    private SensorListDialogFragmentListener listener;
    private List<Sensor> sensorList;

    public SensorListDialogFragment(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.pick_a_sensor);

        String[] sensorNameArr = new String[sensorList.size()];
        int index = 0;
        for (Sensor sensor : sensorList) {
            sensorNameArr[index] = sensor.getName();
            index++;
        }
        builder.setItems(sensorNameArr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.selectOnClick(sensorList.get(i));
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SensorListDialogFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement SensorListDialogFragmentListener");
        }
    }
}
