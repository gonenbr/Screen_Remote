package com.limitscreen.www.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by gonen on 27/11/14.
 */
public class NormalFunctionalityFragment  extends Fragment{
    public NormalFunctionalityFragment(){

    }

    public Button kids_pc;
    public Button main_TV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.function_normal, container, false);
        kids_pc = (Button)rootView.findViewById(R.id.buttonKidsPC);
        String s= "Kids PC Minecraft +2";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,8, 0); // set size
        //ss1.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, 8, 0);// set color
       // TextView tv= (TextView) rootView.findViewById(R.id.textview);
        kids_pc.setText(ss1);

        main_TV = (Button)rootView.findViewById(R.id.buttonMainTV);
        s= "Main TV Ninjago";
        ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,8, 0); // set size
        //ss1.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, 8, 0);// set color
        // TextView tv= (TextView) rootView.findViewById(R.id.textview);
        main_TV.setText(ss1);


        //main_TV = rootView.findViewById(R.id.buttonMainTV);
        return rootView;



//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
