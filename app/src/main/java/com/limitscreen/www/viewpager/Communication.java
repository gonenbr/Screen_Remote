package com.limitscreen.www.viewpager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.TimeUtils;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import static android.os.StrictMode.ThreadPolicy;

/**
 * Created by gonen on 28/11/14.
 */



public class Communication {



    public static String SendCommandTurnOffWithString(String device_name, int device, int quata_number, int action_type/*1 for turn off 2 for add minutes*/,String text_to_send,Context c) throws Exception {
        ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
//              THIS IS THE PART THAT CHECKS IF THE CONNECTIVITY IS OK ITS IS COMMENTED BECAUSE THERE IS NO CONTEXT HERE
//        ConnectivityManager connMgr = (ConnectivityManager)
//                (Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            // fetch data
//        } else {
//            // display error
//        }
        String result;
        // Date current = new Date();
        //String date = current.toString();
        StrictMode.setThreadPolicy(policy);
        URL url;
        HttpsURLConnection urlConnection;
        //url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7f02e4b07420882b41d4/");
        try {
            switch(device_name)
            {
                case "Main TV":
                    url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7f02e4b07420882b41d4");
                    break;
                case "Kids PC":
                    url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7ae7e4b07420882b41cf");
                    break;
                default://will make kids pc for now
                    url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7ae7e4b07420882b41cf");
                    break;

            }


//            if(device==0) {
//                url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7f02e4b07420882b41d4");
//            }
//            else
//            {
//                url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7ae7e4b07420882b41cf");
//            }
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw(e);
        }
        // ProgressDialog prg=null;

        try {
            urlConnection.setRequestMethod("PUT");
            // urlConnection.setChunkedStreamingMode(10);
            urlConnection.setRequestProperty("X-Appery-Database-Id","5397ecf9e4b0a7e2630f3c86");
            urlConnection.setRequestProperty("Content-Type","application/json");

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(out,"UTF-8");
//            writer.write("action_quota:14");
            Date current_date = new Date();
            Calendar right_now = Calendar.getInstance();
            String time_string = ((Integer)(right_now.get(Calendar.YEAR))).toString()+"-"+((Integer)(right_now.get(Calendar.MONTH)+1)).toString()+"-"+((Integer)(right_now.get(Calendar.DATE))).toString()+" "+((Integer)(right_now.get(Calendar.HOUR_OF_DAY))).toString()+":"+((Integer)(right_now.get(Calendar.MINUTE))).toString()+":"+((Integer)(right_now.get(Calendar.SECOND))).toString();



            JSONObject quata = new JSONObject();
            quata.put("action_quota",quata_number);
            quata.put("time",time_string);
            quata.put("action", action_type);//1 for turn off 2 for add more time
            quata.put("message",text_to_send);
            quata.put("ow_date","00/00/0000");

            writer.write(quata.toString());
            writer.flush();
            //prg = ProgressDialog.show(c,"","Loading...");

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //readStream(in);
            Reader reader = new InputStreamReader(in, "UTF-8");
            char[] buffer = new char[2000];

            reader.read(buffer);
            result =  new String(buffer);
        }
        catch(Exception e) {
            int dummy=0;
            dummy++;

        }
        finally {
            urlConnection.disconnect();
//                if(prg!=null) {
//                    prg.dismiss();
//                }

        }




        return null;

    }



    public static String SendCommandTurnOff(int device, int quata_number, int action_type/*1 for turn off 2 for add minutes*/,String text_to_send,Context c) throws Exception {
        ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
//              THIS IS THE PART THAT CHECKS IF THE CONNECTIVITY IS OK ITS IS COMMENTED BECAUSE THERE IS NO CONTEXT HERE
//        ConnectivityManager connMgr = (ConnectivityManager)
//                (Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            // fetch data
//        } else {
//            // display error
//        }
        String result;
       // Date current = new Date();
        //String date = current.toString();
        StrictMode.setThreadPolicy(policy);
        URL url;
        HttpsURLConnection urlConnection;
       //url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7f02e4b07420882b41d4/");
        try {
            if(device==0) {
                url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7f02e4b07420882b41d4");
            }
            else
            {
                url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7ae7e4b07420882b41cf");
            }
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw(e);
        }
       // ProgressDialog prg=null;

        try {
            urlConnection.setRequestMethod("PUT");
           // urlConnection.setChunkedStreamingMode(10);
            urlConnection.setRequestProperty("X-Appery-Database-Id","5397ecf9e4b0a7e2630f3c86");
            urlConnection.setRequestProperty("Content-Type","application/json");

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(out,"UTF-8");
//            writer.write("action_quota:14");
            Date current_date = new Date();
            Calendar right_now = Calendar.getInstance();
           String time_string = ((Integer)(right_now.get(Calendar.YEAR))).toString()+"-"+((Integer)(right_now.get(Calendar.MONTH)+1)).toString()+"-"+((Integer)(right_now.get(Calendar.DATE))).toString()+" "+((Integer)(right_now.get(Calendar.HOUR_OF_DAY))).toString()+":"+((Integer)(right_now.get(Calendar.MINUTE))).toString()+":"+((Integer)(right_now.get(Calendar.SECOND))).toString();



            JSONObject quata = new JSONObject();
            quata.put("action_quota",quata_number);
            quata.put("time",time_string);
            quata.put("action", action_type);//1 for turn off 2 for add more time
            quata.put("message",text_to_send);
            quata.put("ow_date","00/00/0000");

            writer.write(quata.toString());
            writer.flush();
            //prg = ProgressDialog.show(c,"","Loading...");

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //readStream(in);
            Reader reader = new InputStreamReader(in, "UTF-8");
            char[] buffer = new char[2000];

            reader.read(buffer);
            result =  new String(buffer);
        }
        catch(Exception e) {
            int dummy=0;
            dummy++;

        }
        finally {
                urlConnection.disconnect();
//                if(prg!=null) {
//                    prg.dismiss();
//                }

            }




        return null;

    }
    public static  void SetScheduleData(int device, JSONObject data_to_send) throws Exception
    {
        ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
//              THIS IS THE PART THAT CHECKS IF THE CONNECTIVITY IS OK ITS IS COMMENTED BECAUSE THERE IS NO CONTEXT HERE
//        ConnectivityManager connMgr = (ConnectivityManager)
//                (Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            // fetch data
//        } else {
//            // display error
//        }
        String result;
        // Date current = new Date();
        //String date = current.toString();
        StrictMode.setThreadPolicy(policy);
        URL url;
        HttpsURLConnection urlConnection;
        //url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7f02e4b07420882b41d4/");
        try {
            if(device==0) {
                url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7f02e4b07420882b41d4");
            }
            else
            {
                url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7ae7e4b07420882b41cf");
            }
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw(e);
        }

        try {
            urlConnection.setRequestMethod("PUT");
            //urlConnection.setChunkedStreamingMode(10);
            urlConnection.setRequestProperty("X-Appery-Database-Id","5397ecf9e4b0a7e2630f3c86");
            urlConnection.setRequestProperty("Content-Type","application/json");

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(out,"UTF-8");
//            writer.write("action_quota:14");
            Date current_date = new Date();
            Calendar right_now = Calendar.getInstance();
            String time_string = ((Integer)(right_now.get(Calendar.YEAR))).toString()+"-"+((Integer)(right_now.get(Calendar.MONTH)+1)).toString()+"-"+((Integer)(right_now.get(Calendar.DATE))).toString()+" "+((Integer)(right_now.get(Calendar.HOUR_OF_DAY))).toString()+":"+((Integer)(right_now.get(Calendar.MINUTE))).toString()+":"+((Integer)(right_now.get(Calendar.SECOND))).toString();



           // JSONObject quata = new JSONObject();
            //quata.put("action_quota",quata_number);
            //quata.put("time",time_string);
            //quata.put("action", action_type);//1 for turn off 2 for add more time
            //quata.put("message",text_to_send);
            data_to_send.put("ow_date","00/00/0000");
            writer.write(data_to_send.toString());
            writer.flush();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //readStream(in);
            Reader reader = new InputStreamReader(in, "UTF-8");
            char[] buffer = new char[200];

            reader.read(buffer);
            result =  new String(buffer);
        }
        catch(Exception e) {
            int dummy=0;
            dummy++;

        }
        finally {
            urlConnection.disconnect();
        }




        return;

    }


    public static String GetScheduleData(int device)  throws Exception
    {
        ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
        String result="";
        // Date current = new Date();
        //String date = current.toString();
        StrictMode.setThreadPolicy(policy);
        URL url;
        HttpsURLConnection urlConnection;
        //url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7f02e4b07420882b41d4/");
        try {
            if(device==0) {
                url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7f02e4b07420882b41d4");
            }
            else
            {
                url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7ae7e4b07420882b41cf");
            }
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw(e);
        }

        try {
            urlConnection.setRequestMethod("GET");
            //urlConnection.setChunkedStreamingMode(10);
            urlConnection.setRequestProperty("X-Appery-Database-Id","5397ecf9e4b0a7e2630f3c86");
            urlConnection.connect();

            //urlConnection.setRequestProperty("Content-Type","application/json");

            //OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            //OutputStreamWriter writer = new OutputStreamWriter(out,"UTF-8");
            //writer.write("");
            //writer.flush();
//            writer.write("action_quota:14");
           // Date current_date = new Date();
            //Calendar right_now = Calendar.getInstance();
            //String time_string = ((Integer)(right_now.get(Calendar.YEAR))).toString()+"-"+((Integer)(right_now.get(Calendar.MONTH)+1)).toString()+"-"+((Integer)(right_now.get(Calendar.DATE))).toString()+" "+((Integer)(right_now.get(Calendar.HOUR_OF_DAY))).toString()+":"+((Integer)(right_now.get(Calendar.MINUTE))).toString()+":"+((Integer)(right_now.get(Calendar.SECOND))).toString();



            //JSONObject quata = new JSONObject();
            //quata.put("name","Main TV");
           // quata.put("time",time_string);
            //quata.put("action", action_type);//1 for turn off 2 for add more time
            //quata.put("message",text_to_send);

            //writer.write("where="+quata.toString());
            //writer.flush();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //readStream(in);
            Reader reader = new InputStreamReader(in, "UTF-8");
            char[] buffer = new char[10000];

            reader.read(buffer);
            result =  new String(buffer);
        }
        catch(Exception e) {
            int dummy=0;
            dummy++;

        }
        finally {
            urlConnection.disconnect();
        }




        return result;







        //return null;
    }


    public static void SetScheduleDataWithString(String device, JSONObject data_to_send) throws Exception {
        ThreadPolicy policy = new ThreadPolicy.Builder().permitAll().build();
//              THIS IS THE PART THAT CHECKS IF THE CONNECTIVITY IS OK ITS IS COMMENTED BECAUSE THERE IS NO CONTEXT HERE
//        ConnectivityManager connMgr = (ConnectivityManager)
//                (Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            // fetch data
//        } else {
//            // display error
//        }
        String result;
        // Date current = new Date();
        //String date = current.toString();
        StrictMode.setThreadPolicy(policy);
        URL url;
        HttpsURLConnection urlConnection;
        //url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7f02e4b07420882b41d4/");
        try {
            switch(device)
            {
                case "Main TV":
                    url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7f02e4b07420882b41d4");
                    break;
                case "Kids PC":
                    url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7ae7e4b07420882b41cf");
                    break;
                default://will make kids pc for now
                    url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7ae7e4b07420882b41cf");
                    break;

            }
//            if(device=="Main TV") {
//                url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7f02e4b07420882b41d4");
//            }
//            else
//            {
//                url = new URL("https://api.appery.io/rest/1/db/collections/Screens/53ae7ae7e4b07420882b41cf");
//            }
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw(e);
        }

        try {
            urlConnection.setRequestMethod("PUT");
            //urlConnection.setChunkedStreamingMode(10);
            urlConnection.setRequestProperty("X-Appery-Database-Id","5397ecf9e4b0a7e2630f3c86");
            urlConnection.setRequestProperty("Content-Type","application/json");

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(out,"UTF-8");
//            writer.write("action_quota:14");
            Date current_date = new Date();
            Calendar right_now = Calendar.getInstance();
            String time_string = ((Integer)(right_now.get(Calendar.YEAR))).toString()+"-"+((Integer)(right_now.get(Calendar.MONTH)+1)).toString()+"-"+((Integer)(right_now.get(Calendar.DATE))).toString()+" "+((Integer)(right_now.get(Calendar.HOUR_OF_DAY))).toString()+":"+((Integer)(right_now.get(Calendar.MINUTE))).toString()+":"+((Integer)(right_now.get(Calendar.SECOND))).toString();



            // JSONObject quata = new JSONObject();
            //quata.put("action_quota",quata_number);
            //quata.put("time",time_string);
            //quata.put("action", action_type);//1 for turn off 2 for add more time
            //quata.put("message",text_to_send);
            data_to_send.put("ow_date","00/00/0000");
            writer.write(data_to_send.toString());
            writer.flush();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //readStream(in);
            Reader reader = new InputStreamReader(in, "UTF-8");
            char[] buffer = new char[200];

            reader.read(buffer);
            result =  new String(buffer);
        }
        catch(Exception e) {
            int dummy=0;
            dummy++;

        }
        finally {
            urlConnection.disconnect();
        }




        return;

    }
}
