package com.limitscreen.www.viewpager;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gonen on 05/12/14.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public TimePickerFragment(View v) {
        current_edit_text_view = (TextView)v;
        Date date;
        String result;
        String standard_time = current_edit_text_view.getText().toString();

        if(standard_time=="") {
            hour_main=12;
            minute_main=0;
        }

        try {
            date = (new SimpleDateFormat("hh:mm a")).parse(standard_time);
            hour_main = date.getHours();
            minute_main = date.getMinutes();
            //SimpleDateFormat result_simple_format_helper = new SimpleDateFormat("kk:mm");
            //result = result_simple_format_helper.format(date);

        }
        catch (Exception e) {


        }

    }
    TextView current_edit_text_view;
    int hour_main;
    int minute_main;
    boolean is_cleared=false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        TimePickerDialog dial = new TimePickerDialog(getActivity(), this, hour_main, minute_main,false);
        dial.setButton(TimePickerDialog.BUTTON_NEGATIVE,"Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });
        dial.setButton(TimePickerDialog.BUTTON_NEUTRAL,"Clear",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                current_edit_text_view.setText("");
                is_cleared=true;

            }
        });


        return dial;
    }



    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(!is_cleared) {
            current_edit_text_view.setText(String.format("%d:%02d", (hourOfDay > 11) ? hourOfDay - 12 : hourOfDay, minute) + ((hourOfDay > 11) ? " PM" : " AM"));
        }
        // Do something with the time chosen by the user
       // View dummy_view = view.getRootView();
        //int dummy=0;
        //dummy++;
       // hour_main=hourOfDay;
       // minute_main=minute;

    }
}
