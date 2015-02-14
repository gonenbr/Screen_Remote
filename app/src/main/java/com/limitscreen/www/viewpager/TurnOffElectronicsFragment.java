package com.limitscreen.www.viewpager;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

/**
 * Created by gonen on 27/11/14.
 */
public class TurnOffElectronicsFragment extends Fragment {

    SeekBar seek_bar;
    EditText text_turn_off_value;
    EditText text_message;
    Button button_cancel;
    Button button_apply;
    ClearableEditText text_message_to_send_clearable;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getActionBar().setTitle("                SCREEN off");
        View rootView = inflater.inflate(R.layout.turnoff_electronics, container, false);
        seek_bar = (SeekBar)rootView.findViewById(R.id.seekBar_turnoff);
        text_message_to_send_clearable = (ClearableEditText)rootView.findViewById(R.id.clearable_edit_text_message_to_send);
        text_message = (EditText)rootView.findViewById(R.id.edit_text_turn_off_message);
        text_turn_off_value = (EditText)rootView.findViewById(R.id.edit_text_turn_off_minutes);
        text_turn_off_value.setFilters(new textnumericfilter[]{new textnumericfilter("0", "60")});
        button_cancel = (Button)rootView.findViewById(R.id.button_cancel);
        button_cancel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getFragmentManager().popBackStack();
                return false;
            }
        });
        button_apply = (Button)rootView.findViewById(R.id.button_apply);
        button_apply.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                getFragmentManager().popBackStack();
                try {
//                                    Communication.SendCommandTurnOff();
                    DownloadWebpageTask task = new DownloadWebpageTask(getActivity().getApplicationContext());
                    //task.execute(Integer.toString(((MainActivity)getActivity()).device_selected),text_turn_off_value.getText().toString(),"1",text_message.getText().toString());// the one is for turn off action
                    task.execute(Integer.toString(((MainActivity)getActivity()).device_selected),text_turn_off_value.getText().toString(),"1",text_message_to_send_clearable.getText().toString());// the one is for turn off action
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value;
                value = seek_bar.getProgress();

                text_turn_off_value.setText(Integer.toString(value));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//        seek_bar.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int value;
//                if(event.getAction() == MotionEvent.ACTION_MOVE)
//                {
//                    value = seek_bar.getProgress();
//
//                    text_turn_off_value.setText(Integer.toString(value));
//
//                }
//                if(event.getAction() == MotionEvent.ACTION_DOWN)
//                {
//                    value = seek_bar.getProgress();
//
//                    text_turn_off_value.setText(Integer.toString(value));
//
//                }
//                return false;
//            }
//        });
        text_turn_off_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int value;
                try {
                    value = Integer.parseInt(text_turn_off_value.getText().toString());
                }
                catch (Exception e) {
                    return;
                }
                //gonen need to remove they should be global variables
                if(value>=0 && value <=60)
                {
                    seek_bar.setProgress(value);
                }




            }
        });
        this.text_message.selectAll();
        return rootView;
    }
}
