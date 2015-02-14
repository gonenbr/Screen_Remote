package com.limitscreen.www.viewpager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gonen on 12/02/15.
 */
public class MultiDeviceDialogFragement extends DialogFragment {

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    public ArrayList mSelectedItems = new ArrayList();  // Where we track the selected items

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public static void brandAlertDialog(AlertDialog dialog) {
        try {
            Resources resources = dialog.getContext().getResources();
            int color = resources.getColor(R.color.Spurple); // your color here

//            int alertTitleId = resources.getIdentifier("alertTitle", "id", "android");
//            TextView alertTitle = (TextView) dialog.getWindow().getDecorView().findViewById(alertTitleId);
//            alertTitle.setTextColor(color); // change title text color

            int titleDividerId = resources.getIdentifier("titleDivider", "id", "android");
            //dialog.getActionBar().
            View titleDivider = dialog.getWindow().getDecorView().findViewById(titleDividerId);
            titleDivider.setBackgroundColor(color); // change divider color
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setCustomTitle(getActivity().getLayoutInflater().inflate(R.layout.alert_dialog_title,null));
        final CharSequence[] devices_names = SetDeviceNamesStrings();
        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogPositiveClick(MultiDeviceDialogFragement.this);

            }
        });
        builder.setCancelable(true);
        builder.setTitle("Apply to other devices");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMultiChoiceItems(devices_names, new boolean[devices_names.length], new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    // If the user checked the item, add it to the selected items
                    mSelectedItems.add(devices_names[which]);
                } else if (mSelectedItems.contains(devices_names[which])) {
                    // Else, if the item is already in the array, remove it
                    mSelectedItems.remove(devices_names[which]);
                }

            }
        });
//        builder.setItems(SetDeviceNamesStrings(), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        AlertDialog dialog = builder.create();
        //MultiDeviceDialogFragement.brandAlertDialog(dialog);
        return dialog;
        //return super.onCreateDialog(savedInstanceState);
    }

    private CharSequence[] SetDeviceNamesStrings() {
        int counter=0;

        for(Device device:((ScheduleActivity)getActivity()).devices)
        {
            if(device.Is_available)
            {
                counter++;
            }
        }
        //CharSequence[] device_list = new CharSequence[counter+1];//the +1 is for the apply to all
        CharSequence[] device_list = new CharSequence[counter];//the is for the apply to all
        counter=0;
        for(Device device:((ScheduleActivity)getActivity()).devices )
        {

            if(device.Is_available)
            {
                device_list[counter]=device.Device_name;
            }
            counter++;
        }
        //device_list[device_list.length-1]="Choose all";

      //  ((MainActivity)getActivity()).Family_Devices
        return device_list;
    }

}
