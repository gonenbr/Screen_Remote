package com.limitscreen.www.viewpager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gonen on 01/12/14.
 */
public class ScheduleActivity extends FragmentActivity implements MultiDeviceDialogFragement.NoticeDialogListener,RecommendedScheduleDialogFragement.NoticeDialogListener{
    public int device_selected=0;
    public String device_selected_string=null;
    //public ProgressDialog prg;
    public Context App_context;
    public Device[] devices;
    public LinearLayout progress_layout=null;

    //public Map<String,Integer> devices_map = new HashMap<>();


    @Override
    public void onRecommendedDialogPositiveClick(android.app.DialogFragment dialog) {
        RecommendedScheduleDialogFragement fragi = (RecommendedScheduleDialogFragement)dialog;
        if(fragi.mSelectedItems.isEmpty())
            return;
        this.preFetchDataAndSend(fragi.mSelectedItems,true);
        Toast toast;
        toast = Toast.makeText(App_context, "Recommended schedule was set", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,-350);
        toast.show();
        finish();
        //new GetScheduleTask().execute();

    }

    @Override
    public void onRecommendedDialogNegativeClick(android.app.DialogFragment dialog) {

    }

    @Override
    public void onDialogPositiveClick(android.app.DialogFragment dialog) {
        MultiDeviceDialogFragement fragi = (MultiDeviceDialogFragement)dialog;
        if(fragi.mSelectedItems.isEmpty())
            return;
        this.preFetchDataAndSend(fragi.mSelectedItems,false);
        Toast toast;
        toast = Toast.makeText(App_context, "Schedule was set", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,-350);
        toast.show();
        finish();
        //fragi.mSelectedItems



    }

    @Override
    public void onDialogNegativeClick(android.app.DialogFragment dialog) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parcelable[] parcel_array=null;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(R.layout.scheduleactivity);
        Intent i = getIntent();
        String title = i.getStringExtra("title");
        device_selected = i.getIntExtra("device_selected",0);

        //device_selected_string = i .getStringExtra("device_selected_string_name");
        //devices = (Device[])i.getParcelableArrayExtra("devices");
        parcel_array = i.getParcelableArrayExtra("devices");
        devices = Arrays.copyOf(parcel_array,parcel_array.length,Device[].class);
        device_selected_string = GetStringDeviceSelected(devices);
        getActionBar().setTitle(title);
        App_context= getApplicationContext();

//        getActionBar().setTitle("Screen Schedule");
        Button apply = (Button)findViewById(R.id.buttonapplyschedule);
        progress_layout = (LinearLayout)findViewById(R.id.progress_layout);
        Button cancel = (Button)findViewById(R.id.buttoncancelschedule);

        Button apply_to_all = (Button)findViewById(R.id.buttonApplytoallDevices);
        Button set_recommended_schedule = (Button)findViewById(R.id.button_set_recommended_schedule);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(device_selected_string);
                preFetchDataAndSend(list,false);
                Toast toast;
                toast = Toast.makeText(App_context, "Schedule was set", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,-350);
                toast.show();
                 finish();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getOverflowMenu();
        apply_to_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiDeviceDialogFragement multi_device_select = new MultiDeviceDialogFragement();
                //MultiDeviceDialogFragement.brandAlertDialog((AlertDialog) multi_device_select.di);


                multi_device_select.show(getFragmentManager(), "Select devices for schedule");

                int num=0;
                num+=1;



            }
        });
        set_recommended_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecommendedScheduleDialogFragement recommended_schedule_set = new RecommendedScheduleDialogFragement();
                //MultiDeviceDialogFragement.brandAlertDialog((AlertDialog) multi_device_select.di);


                recommended_schedule_set.show(getFragmentManager(), "Set recommended schedule on devices");

            }
        });


    }

    private String GetStringDeviceSelected(Device[] devices) {
        for(Device device:devices)
        {
            if(device.Is_selected_for_current_operation)
                return device.Device_name;
        }
        return null;
    }

    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    public void preFetchDataAndSend(ArrayList<String> list_of_devices,boolean is_recommended_schedule_send)
    {
        JSONObject jsn = new JSONObject();
        String d2s,d2e,d3s,d3e,d4s,d4e,d5s,d5e,d6s,d6e,d7s,d7e,d1s,d1e,d1q,d2q,d3q,d4q,d5q,d6q,d7q;
        if(is_recommended_schedule_send)
        {
            d1s="12:00 PM";
            d2s ="12:00 PM";
            d3s="12:00 PM";
            d4s="12:00 PM";
            d5s="12:00 PM";
            d6s="12:00 PM";
            d7s="12:00 PM";

            d1e="16:00 PM";
            d2e="16:00 PM";
            d3e="16:00 PM";
            d4e="16:00 PM";
            d5e="16:00 PM";
            d6e="16:00 PM";
            d7e="16:00 PM";

            d1q="1";
            d2q="1";
            d3q="1";
            d4q="1";
            d5q="1";
            d6q="1";
            d7q="1";

        }
        else
        {
            d1s=(String)((TextView)findViewById(R.id.d1s)).getText().toString();
            d2s =(String)((TextView)findViewById(R.id.d2s)).getText().toString();
            d3s=(String)((TextView)findViewById(R.id.d3s)).getText().toString();
            d4s=(String)((TextView)findViewById(R.id.d4s)).getText().toString();
            d5s=(String)((TextView)findViewById(R.id.d5s)).getText().toString();
            d6s=(String)((TextView)findViewById(R.id.d6s)).getText().toString();
            d7s=(String)((TextView)findViewById(R.id.d7s)).getText().toString();

            d1e=(String)((TextView)findViewById(R.id.d1e)).getText().toString();
            d2e=(String)((TextView)findViewById(R.id.d2e)).getText().toString();
            d3e=(String)((TextView)findViewById(R.id.d3e)).getText().toString();
            d4e=(String)((TextView)findViewById(R.id.d4e)).getText().toString();
            d5e=(String)((TextView)findViewById(R.id.d5e)).getText().toString();
            d6e=(String)((TextView)findViewById(R.id.d6e)).getText().toString();
            d7e=(String)((TextView)findViewById(R.id.d7e)).getText().toString();

            d1q=(String)((TextView)findViewById(R.id.d1q)).getText().toString();
            d2q=(String)((TextView)findViewById(R.id.d2q)).getText().toString();
            d3q=(String)((TextView)findViewById(R.id.d3q)).getText().toString();
            d4q=(String)((TextView)findViewById(R.id.d4q)).getText().toString();
            d5q=(String)((TextView)findViewById(R.id.d5q)).getText().toString();
            d6q=(String)((TextView)findViewById(R.id.d6q)).getText().toString();
            d7q=(String)((TextView)findViewById(R.id.d7q)).getText().toString();

        }


        try {
            jsn.put("d1s", convertToMilTime(d1s));
            jsn.put("d2s", convertToMilTime(d2s));
            jsn.put("d3s", convertToMilTime(d3s));
            jsn.put("d4s", convertToMilTime(d4s));
            jsn.put("d5s", convertToMilTime(d5s));
            jsn.put("d6s", convertToMilTime(d6s));
            jsn.put("d7s", convertToMilTime(d7s));
            jsn.put("d1e", convertToMilTime(d1e));
            jsn.put("d2e", convertToMilTime(d2e));
            jsn.put("d3e", convertToMilTime(d3e));
            jsn.put("d4e", convertToMilTime(d4e));
            jsn.put("d5e", convertToMilTime(d5e));
            jsn.put("d6e", convertToMilTime(d6e));
            jsn.put("d7e", convertToMilTime(d7e));
            jsn.put("d1q", d1q);
            jsn.put("d2q", d2q);
            jsn.put("d3q", d3q);
            jsn.put("d4q", d4q);
            jsn.put("d5q", d5q);
            jsn.put("d6q", d6q);
            jsn.put("d7q", d7q);
        }
        catch (Exception e) {

        }
        for(String device:list_of_devices) {
            //GetDeviceNumber(device);
            try {
                Communication.SetScheduleDataWithString(device, jsn);
            } catch (Exception e) {
                return;
            }
        }

//        try {
//            Communication.SetScheduleData(device_selected, jsn);
//        }
//        catch (Exception e)
//        {
//            return;
//        }




    }

//    private void GetDeviceNumber(String device_to_fetch) {
//        switch (device_to_fetch)
//        {
//            case ""
//        }
//    }

    @Override
    protected void onStart(){
        super.onStart();
        //this.prg = ProgressDialog.show(this, "", "Loading...");
        new GetScheduleTask().execute();
//        String result;
//        try {
//
//            result = Communication.GetScheduleData(device_selected);
//        }
//        catch (Exception e)
//        {
//            return;
//
//        }
//        finally {
//           // this.prg.dismiss();
//        }
//        displayCurrentSchedule(result);

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
//        String result;
//        try {
//
//            result = Communication.GetScheduleData(device_selected);
//        }
//        catch (Exception e)
//        {
//            return;
//
//        }
//        finally {
//            //this.prg.dismiss();
//        }
//        displayCurrentSchedule(result);

    }



    public void displayCurrentSchedule(String result){
        JSONObject jsn;
        String d2s,d2e,d3s,d3e,d4s,d4e,d5s,d5e,d6s,d6e,d7s,d7e,d1s,d1e,d1q,d2q,d3q,d4q,d5q,d6q,d7q;

        try {
            jsn  = new JSONObject(result);



            d1s = convertToStandardTime((String)jsn.optString("d1s"));

            d2s = convertToStandardTime((String)jsn.optString("d2s"));
            //convertToStandardTime(d2s);
            d3s = convertToStandardTime((String)jsn.optString("d3s"));
            d4s = convertToStandardTime((String)jsn.optString("d4s"));
            d5s = convertToStandardTime((String)jsn.optString("d5s"));
            d6s = convertToStandardTime((String)jsn.optString("d6s"));
            d7s = convertToStandardTime((String)jsn.optString("d7s"));

            d1e = convertToStandardTime((String)jsn.optString("d1e"));
            d2e = convertToStandardTime((String)jsn.optString("d2e"));
            d3e = convertToStandardTime((String)jsn.optString("d3e"));
            d4e = convertToStandardTime((String)jsn.optString("d4e"));
            d5e = convertToStandardTime((String)jsn.optString("d5e"));
            d6e = convertToStandardTime((String)jsn.optString("d6e"));
            d7e = convertToStandardTime((String)jsn.optString("d7e"));

            d1q = (String)jsn.optString("d1q");
            d2q = (String)jsn.optString("d2q");
            d3q = (String)jsn.optString("d3q");
            d4q = (String)jsn.optString("d4q");
            d5q = (String)jsn.optString("d5q");
            d6q = (String)jsn.optString("d6q");
            d7q = (String)jsn.optString("d7q");



        }
        catch (Exception e)
        {
            return;

        }


        ((TextView)findViewById(R.id.d1s)).setText(d1s, TextView.BufferType.NORMAL);
        ((TextView)findViewById(R.id.d2s)).setText(d2s);
        ((TextView)findViewById(R.id.d3s)).setText(d3s);
        ((TextView)findViewById(R.id.d4s)).setText(d4s);
        ((TextView)findViewById(R.id.d5s)).setText(d5s);
        ((TextView)findViewById(R.id.d6s)).setText(d6s);
        ((TextView)findViewById(R.id.d7s)).setText(d7s);

        ((TextView)findViewById(R.id.d1e)).setText(d1e);
        ((TextView)findViewById(R.id.d2e)).setText(d2e);
        ((TextView)findViewById(R.id.d3e)).setText(d3e);
        ((TextView)findViewById(R.id.d4e)).setText(d4e);
        ((TextView)findViewById(R.id.d5e)).setText(d5e);
        ((TextView)findViewById(R.id.d6e)).setText(d6e);
        ((TextView)findViewById(R.id.d7e)).setText(d7e);

        ((TextView)findViewById(R.id.d1q)).setText(d1q);
        ((TextView)findViewById(R.id.d2q)).setText(d2q);
        ((TextView)findViewById(R.id.d3q)).setText(d3q);
        ((TextView)findViewById(R.id.d4q)).setText(d4q);
        ((TextView)findViewById(R.id.d5q)).setText(d5q);
        ((TextView)findViewById(R.id.d6q)).setText(d6q);
        ((TextView)findViewById(R.id.d7q)).setText(d7q);



    }

    public void showTimePickerDialog(View v) {
        DialogFragment time_picker_fragment = new TimePickerFragment(v);
        time_picker_fragment.show(getSupportFragmentManager(), "timePicker");
    }

    public  String convertToStandardTime(String mil_time)
    {
        Date date;
        String result;
        if(mil_time=="")
            return "";
        try {
            date = (new SimpleDateFormat("hh:mm")).parse(mil_time);
            SimpleDateFormat result_simple_format_helper = new SimpleDateFormat("h:mm a");
            result = result_simple_format_helper.format(date);

        }
        catch (Exception e) {
            return "";

        }
        return result;
    }

    public  String convertToMilTime(String standard_time)
    {
        Date date;
        String result;
        if(standard_time=="")
            return "";
        try {
            date = (new SimpleDateFormat("hh:mm a")).parse(standard_time);
            SimpleDateFormat result_simple_format_helper = new SimpleDateFormat("kk:mm");
            result = result_simple_format_helper.format(date);

        }
        catch (Exception e) {
            return "";

        }
        return result;
    }


    public class GetScheduleTask extends AsyncTask<Object,Void,String>
    {
        @Override
        protected String doInBackground(Object... params) {
            String result;
            try {

                result = Communication.GetScheduleData(device_selected);
            }
            catch (Exception e)
            {
                return null;

            }
            finally {
                // this.prg.dismiss();
            }
           // displayCurrentSchedule(result);


            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_layout.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            if(result==null)
                return;
            displayCurrentSchedule(result);
            progress_layout.setVisibility(View.GONE);
        }
    }




}



