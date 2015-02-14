package com.limitscreen.www.viewpager;



import android.content.res.ColorStateList;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParserFactory;
import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by gonen on 22/11/14.
 */
public class Circularinfo extends RelativeLayout
{
    public Circularinfo(android.content.Context context, android.util.AttributeSet attrs)
        {
            super(context,attrs);
            LayoutInflater inflater = LayoutInflater.from(context);
            inflater.inflate(R.layout.circular, this, true);
            progressbar = (ProgressBar)findViewById(R.id.progress);
            devicename = (TextView)findViewById(R.id.devicename);
            timeleft = (TextView)findViewById(R.id.timeleft);
            remaining = (TextView)findViewById(R.id.remaining_label);
            progressbar.setProgress(50);
            progressbar.setEnabled(false);





        }

    public  static Animation getAnimationforCircular(boolean is_active_device)
    {
        if(is_active_device)
        {

                Animation an_active_device = new RotateAnimation(0,270, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
                an_active_device.setFillAfter(true);
                //an.setDuration(1000);
                //an_active_device=an;


            return an_active_device;
        }
        else{

                Animation an_non_active_device = new RotateAnimation(0,270, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
                //an.setDuration(0);
                //an_non_active_device=an;
            an_non_active_device.setFillAfter(true);
                return an_non_active_device;
            }




    }
    public void setHighlightfromScreencaptureChoose(boolean is_highlighted)
    {
        if(is_highlighted) {
            this.setBackgroundResource(R.drawable.backcircleviewselected);

        }
        else
        {
            this.setBackground(null);
        }
    }


    public void Selectdevice(boolean is_selected)
    {
        if(!is_selected)
        {

            progressbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbarnotselected));
            devicename.setTextColor(0xffb2b2b2);
          //  devicename.setAlpha(0.4f);
            //remaining.setTextColor(0xffb2b2b2);
            remaining.setTextColor(0x00b2b2b2);
          //  remaining.setAlpha(0.4f);

            timeleft.setTextColor(0xffb2b2b2);
            timeleft.setTextSize(20);

         //   timeleft.setAlpha(0.4f);
//            timeleft.setText("Selected");
//            timeleft.setTextSize(14);
            progressbar.setProgress(0);
            this.setAlpha(0.3f);
        //    progressbar.setAlpha(0.4f);
        }
        else
        {
            progressbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbarnotselected));
            devicename.setTextColor(0xffb2b2b2);

            //  devicename.setAlpha(0.4f);
            //remaining.setTextColor(0xffb2b2b2);
            remaining.setTextColor(0x00ffffff);
            //  remaining.setAlpha(0.4f);

            timeleft.setTextColor(getResources().getColor(R.color.Spurple));
            timeleft.setText("Selected");
            timeleft.setTextSize(14);
            progressbar.setProgress(0);
            //gressbar.setProgress(0);
            this.setAlpha(1);
       //     SetTextColorsSelectedandEnabled();

        }

    }
    public void SetTextColorsSelectedandEnabled()
    {
        devicename.setTextColor(0xff605d4c);
//        remaining.setTextColor(0xffb2b2b2);
        remaining.setTextColor(0x00ffffff);
        timeleft.setTextColor(0xff12d3f4);
    }


    public void SetNotOffline(boolean is_not_offline) {
        Animation an_active_device = new RotateAnimation(0,270, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        an_active_device.setDuration(500);

        an_active_device.setFillAfter(true);

        if(is_not_offline) {
            progressbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar));
            timeleft.setTextSize(18);
            devicename.setTextColor(0xff605d4c);
            //remaining.setTextColor(0xffb2b2b2);
            remaining.setTextColor(0x00ffffff);
            //timeleft.setTextColor(0xff12d3f4);
            timeleft.setTextColor(getResources().getColor(R.color.Sgray));
            timeleft.setText(this.time_remaining);
            progressbar.setProgress(this.current_progress);
            progressbar.setAnimation(an_active_device);
            progressbar.animate();
            this.setAlpha(1);

        }
        else {
            devicename.setTextColor(0xffb2b2b2);

            timeleft.setTextColor(0xffb2b2b2);
            progressbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbardisabled));
            timeleft.setTextSize(18);
            progressbar.setAnimation(an_active_device);
            progressbar.setProgress(this.current_progress);
            progressbar.animate();
            this.setAlpha(1);
//            progressbar.setAlpha(0.4f);

        }
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
     int dummy=0;
        dummy++;

//        FireMissilesDialogFragment misile = new FireMissilesDialogFragment();

//        this.getContext().
//       misile.show(((FragmentActivity)(getContext().getApplicationContext())).getSupportFragmentManager(),"that");
//        FragmentTransaction ft = getS().beginTransaction();
//        misile.show(misile.getFragmentManager(), "test1");
        return false;
    }

    public void SetProgress(int progress)
    {
        this.current_progress=progress;
        this.progressbar.setProgress(this.current_progress);

    }
    public void SetTimeRemaining(String remaining_time) {

        this.time_remaining = remaining_time;
        this.timeleft.setText(this.time_remaining);
    }

    private Animation an_active_device=null;
    private Animation an_non_active_device=null;




    public ProgressBar progressbar;
    public TextView devicename;
    public TextView timeleft;
    public TextView remaining;
    public String time_remaining="03:01";
    public String device_name="default device";
    public int current_progress = 80;
}
