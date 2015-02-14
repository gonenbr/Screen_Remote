package com.limitscreen.www.viewpager;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by gonen on 07/12/14.
 */
public class DownLoadScheduleHandler extends AsyncTask<String, Void, String> {
    public Context App_context;
    public DownLoadScheduleHandler(Context app_context)
    {
        App_context=app_context;
    }
    public int action_type;
    public int device_number;
    public int number_of_minutes;
    public String text_to_send;
    //public ProgressDialog prg;

    @Override
    protected String doInBackground(String... params) {
        String result;
        //prg = ProgressDialog.show(App_context,"","Loading...");
        try {
            device_number = Integer.parseInt(params[0]);
            number_of_minutes = Integer.parseInt(params[1]);
            action_type = Integer.parseInt(params[2]);
            text_to_send = params[3];
            result = Communication.SendCommandTurnOff(device_number,number_of_minutes, action_type,text_to_send,App_context);//param 2 is action type
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
            if(device_number==0)//main TV
            {
                toast = Toast.makeText(App_context, "Main TV, will turn off in " + String.valueOf(number_of_minutes) + " minutes", Toast.LENGTH_SHORT);
            }
            else {
                toast = Toast.makeText(App_context, "Kids PC, will turn off in " + String.valueOf(number_of_minutes) + " minutes", Toast.LENGTH_SHORT);
            }
        }
        else
        {//probabbly if added time
            if(device_number==0)//main TV
            {
                toast = Toast.makeText(App_context, String.valueOf(number_of_minutes)+" minutes, were added to "+ "Main TV",  Toast.LENGTH_SHORT);
            }
            else {
                toast = Toast.makeText(App_context, String.valueOf(number_of_minutes)+" minutes, were added to "+ "Kids PC",  Toast.LENGTH_SHORT);
            }
            // Toast toast = Toast.makeText(App_context,"Your changes were applied to the selected device", Toast.LENGTH_SHORT);

        }
        // Toast toast = Toast.makeText(App_context,"Your changes were applied to the selected device", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,-350);
        toast.show();
        int dummy=0;
        dummy++;
    }
}