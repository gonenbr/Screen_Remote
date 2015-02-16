package com.limitscreen.www.viewpager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by gonen on 28/11/14.
 */
public class DownloadWebpageTask extends AsyncTask<Object, Void, String> {
    public Context App_context;
    public DownloadWebpageTask(Context app_context)
    {
        App_context=app_context;
    }
    public int action_type;
    public int device_number;
    public int number_of_minutes;
    public String text_to_send;
    public Device[] devices;
    public String output_devices_names_for_show="";
    //public ProgressDialog prg;




    @Override
    protected String doInBackground(Object... params) {
        String result=null;
        //prg = ProgressDialog.show(App_context,"","Loading...");
        try {
            device_number = Integer.parseInt((String)params[0]);
            number_of_minutes = Integer.parseInt((String)params[1]);
            action_type = Integer.parseInt((String)params[2]);
            text_to_send = (String)params[3];
            devices = (Device[])params[4];
            for(Device device:devices)
            {
                if(device.Is_selected_for_current_operation)//a device selected
                {
                    output_devices_names_for_show+=device.Device_name+", ";
                    //result = Communication.SendCommandTurnOff(device_number, number_of_minutes, action_type, text_to_send, App_context);//param 2 is action type
                    result = Communication.SendCommandTurnOffWithString(device.Device_name,device_number, number_of_minutes, action_type, text_to_send, App_context);//param 2 is action type
                }
            }
//            return downloadUrl(urls[0])
        } catch (Exception e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
        return result;
    }

    //    @Override
//    public String (String... urls) throws Exception {
//        // params comes from the execute() call: params[0] is the url.
//        try {
//            Communication.SendCommandTurnOff();
////            return downloadUrl(urls[0])
//        } catch (IOException e) {
//            return "Unable to retrieve web page. URL may be invalid.";
//        }
//    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        //prg.dismiss();
       Toast toast;
        if(action_type==1)//turn off
        {
            toast = Toast.makeText(App_context, output_devices_names_for_show+ "will turn off in " + String.valueOf(number_of_minutes) + " minutes", Toast.LENGTH_SHORT);
            //((MainActivity)App_context).Family_Devices
//            if(device_number==0)//main TV
//            {
//                toast = Toast.makeText(App_context, "Main TV, will turn off in " + String.valueOf(number_of_minutes) + " minutes", Toast.LENGTH_SHORT);
//            }
//            else {
//                toast = Toast.makeText(App_context, "Kids PC, will turn off in " + String.valueOf(number_of_minutes) + " minutes", Toast.LENGTH_SHORT);
//            }
        }
        else
        {//probabbly if added time
            toast = Toast.makeText(App_context, String.valueOf(number_of_minutes)+" minutes, were added to "+ output_devices_names_for_show.substring(0,(output_devices_names_for_show.length()-1)-1),  Toast.LENGTH_SHORT);
//            if(device_number==0)//main TV
//            {
//                toast = Toast.makeText(App_context, String.valueOf(number_of_minutes)+" minutes, were added to "+ "Main TV",  Toast.LENGTH_SHORT);
//            }
//            else {
//                toast = Toast.makeText(App_context, String.valueOf(number_of_minutes)+" minutes, were added to "+ "Kids PC",  Toast.LENGTH_SHORT);
//            }
           // Toast toast = Toast.makeText(App_context,"Your changes were applied to the selected device", Toast.LENGTH_SHORT);

        }
       // Toast toast = Toast.makeText(App_context,"Your changes were applied to the selected device", Toast.LENGTH_SHORT);
//        Toast toast;
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,-350);
        toast.show();
        int dummy=0;
        dummy++;
    }
}



