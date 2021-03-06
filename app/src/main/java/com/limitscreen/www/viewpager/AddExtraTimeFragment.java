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
public class AddExtraTimeFragment extends Fragment {

    SeekBar seek_bar;
    EditText text_turn_off_value;
    Button button_cancel;
    Button button_apply;
    EditText text_message;
    ClearableEditText text_message_to_send_clearable;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getActionBar().setTitle("                SCREEN on");
        View rootView = inflater.inflate(R.layout.addmoretime, container, false);
        seek_bar = (SeekBar)rootView.findViewById(R.id.seekBar_addextra);
        text_message = (EditText)rootView.findViewById(R.id.edit_box_add_more_time_message);
        text_turn_off_value = (EditText)rootView.findViewById(R.id.edit_text_add_extra_minutes);
        text_message_to_send_clearable = (ClearableEditText)rootView.findViewById(R.id.clearable_edit_text_message_to_send);
        text_turn_off_value.setFilters(new textnumericfilter[]{new textnumericfilter("0", "120")});
        button_cancel = (Button)rootView.findViewById(R.id.button_cancel);
        button_cancel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getFragmentManager().popBackStack();
                return false;
            }
        });
        button_apply = (Button)rootView.findViewById(R.id.button_apply);
        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Device[] devices_copy =  new Device[((MainActivity)getActivity()).Family_Devices.length];
                    for(int index=0;index<devices_copy.length;index++)
                    {
                        devices_copy[index] = new Device(((MainActivity)getActivity()).Family_Devices[index]);
                    }
                    if(!IsDeviceWithAction(devices_copy))
                    {
                        getFragmentManager().popBackStack();
                        return;
                    }
//                                    Communication.SendCommandTurnOff();
                    DownloadWebpageTask task = new DownloadWebpageTask(getActivity().getApplicationContext());
//                    task.execute(Integer.toString(((MainActivity)getActivity()).device_selected),text_turn_off_value.getText().toString(),"2",text_message.getText().toString());//the 2 is for add more time action
                    task.execute(Integer.toString(((MainActivity)getActivity()).device_selected),text_turn_off_value.getText().toString(),"2",text_message_to_send_clearable.getText().toString(),devices_copy);//the 2 is for add more time action
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getFragmentManager().popBackStack();

            }
        });

//        button_apply.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                getFragmentManager().popBackStack();
//                try {
////                                    Communication.SendCommandTurnOff();
//                    DownloadWebpageTask task = new DownloadWebpageTask(getActivity().getApplicationContext());
////                    task.execute(Integer.toString(((MainActivity)getActivity()).device_selected),text_turn_off_value.getText().toString(),"2",text_message.getText().toString());//the 2 is for add more time action
//                    task.execute(Integer.toString(((MainActivity)getActivity()).device_selected),text_turn_off_value.getText().toString(),"2",text_message_to_send_clearable.getText().toString());//the 2 is for add more time action
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//        });
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
                if(value>=0 && value <=120)
                {
                    seek_bar.setProgress(value);
                }




            }
        });
        return rootView;
    }
    private boolean IsDeviceWithAction(Device[] devices_copy) {
        for(Device device:devices_copy)
        {
            if(device.Is_selected_for_current_operation)
                return true;
        }
        return false;
    }
}
