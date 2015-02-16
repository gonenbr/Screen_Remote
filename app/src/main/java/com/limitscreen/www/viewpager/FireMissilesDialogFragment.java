package com.limitscreen.www.viewpager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Toast;

/**
 * Created by gonen on 26/11/14.
 */
public class FireMissilesDialogFragment extends DialogFragment {
    public Context app_context;

    public boolean was_canceled=false;
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        MainActivity act = (MainActivity)getActivity();
        this.was_canceled=true;
        FragmentManager manager = act.getSupportFragmentManager();
        Circularinfo cir;
        if(manager.getBackStackEntryCount()==0) {
            GridLayout root_view = (GridLayout)((FrameLayout)act.mViewPager.getChildAt(0)).getChildAt(0);
            //  ((PlaceholderFragment)mViewPager.getCurrentItem()).
            int number_of_children = root_view.getChildCount();
            for (int index=0;index<number_of_children;index++)
            {
                cir = (Circularinfo)root_view.getChildAt(index);
                cir.SetNotOffline(false);
            }
            ((Circularinfo)root_view.getChildAt(0)).SetNotOffline(true);
            ((Circularinfo)root_view.getChildAt(1)).SetNotOffline(true);
            ((Circularinfo)root_view.getChildAt(2)).SetNotOffline(true);

            act.InitDevicesData();//this will init the devices flags so the action flag will be reset.


            return;
        }


    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(R.array.device_options_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//               Object dummy =  ((MainActivity)getActivity()).mViewPager.getChildAt(0);
                FragmentManager fragmentManager;
                android.support.v4.app.FragmentTransaction fragmentTransaction;
                switch (which) {
                    case 0://shut down
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        TurnOffElectronicsFragment fgm = new TurnOffElectronicsFragment();
                        fragmentTransaction.replace(R.id.main_functions_entry_point, fgm, "functions_tag");
                        fragmentTransaction.addToBackStack(null);
                        fgm.setArguments(getArguments());
                        fragmentTransaction.commit();

//                                try {
////                                    Communication.SendCommandTurnOff();
//                                    new DownloadWebpageTask().execute("dummy");
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
                        break;
                    case 1://add time
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        AddExtraTimeFragment adtfgmt = new AddExtraTimeFragment();
                        fragmentTransaction.replace(R.id.main_functions_entry_point, adtfgmt, "addtime_tag");
                        fragmentTransaction.addToBackStack(null);
                        adtfgmt.setArguments(getArguments());
                        fragmentTransaction.commit();

//                                try {
////                                    Communication.SendCommandTurnOff();
//                                    new DownloadWebpageTask().execute("dummy");
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
                        break;

                    case 2:
                        Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                        if(((MainActivity)getActivity()).device_selected==0) {
                            intent.putExtra("title","SCREEN schedule - Main TV");
                            intent.putExtra("device_selected",0);

                        }
                        else
                        {//Kids PC

                            intent.putExtra("title","SCREEN schedule - Kids PC");
                            intent.putExtra("device_selected",1);
                        }

                        intent.putExtra("devices",((MainActivity)getActivity()).Family_Devices);
                        startActivity(intent);
                        dialog.cancel();

                        int dummy=0;
                        dummy++;
                        break;

                    default:
                        Activity a = getActivity();
                        dialog.cancel();
                        Toast toast = Toast.makeText(a ,"Sorry, not available yet...", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,-350);
                        toast.show();
                        break;
                }
                // The 'which' argument contains the index position
                // of the selected item
            }
        });
        return builder.create();
    }
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the Builder class for convenient dialog construction
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage("test")
//                .setPositiveButton("Fire", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // FIRE ZE MISSILES!
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User cancelled the dialog
//                    }
//                });
//        // Create the AlertDialog object and return it
//        return builder.create();
//    }
}
