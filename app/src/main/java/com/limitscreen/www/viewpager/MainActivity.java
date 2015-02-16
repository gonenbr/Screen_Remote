package com.limitscreen.www.viewpager;

import java.lang.reflect.Field;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
//import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
//import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import static android.support.v4.app.FragmentManager.*;


public class MainActivity extends FragmentActivity /*implements ActionBar.TabListener */{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    public int device_selected=0;
    com.viewpagerindicator.CirclePageIndicator titleIndicator;
    PlaceholderFragment device_fragment ;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        //final ActionBar actionBar = getActionBar();
       // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), getApplicationContext());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        titleIndicator = (CirclePageIndicator)findViewById(R.id.titles);
        //device_fragment = (PlaceholderFragment)findViewById(R.id.)
        mViewPager.setAdapter(mSectionsPagerAdapter);



        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
//                actionBar.setSelectedNavigationItem(position);

                titleIndicator= (CirclePageIndicator)findViewById(R.id.titles);
                titleIndicator.setViewPager(mViewPager);
            }
        });
        titleIndicator.setViewPager(mViewPager);

        // For each of the sections in the app, add a tab to the action bar.
//        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
//            // Create a tab with text corresponding to the page title defined by
//            // the adapter. Also specify this Activity object, which implements
//            // the TabListener interface, as the callback (listener) for when
//            // this tab is selected.
//            actionBar.addTab(
//                    actionBar.newTab()
//                            .setText(mSectionsPagerAdapter.getPageTitle(i))
//                            .setTabListener(this));
//        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
               FragmentManager manager = getSupportFragmentManager();
                Circularinfo cir;
                if(manager.getBackStackEntryCount()==0) {
                    getActionBar().setTitle("                SCREEN now");
                    GridLayout root_view = (GridLayout)((FrameLayout)mViewPager.getChildAt(0)).getChildAt(0);
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

                    InitDevicesData();
                    return;
                }
               String currrent_fragment_name = manager.getBackStackEntryAt(manager.getBackStackEntryCount()-1).getName();
                if(currrent_fragment_name!="main_down_fragment") {



                    return;

                }

            }
        });
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        NormalFunctionalityFragment fgm = new NormalFunctionalityFragment();
//        fragmentTransaction.add(R.id.main_functions_entry_point, fgm,"main_down_fragment");
        CarouselFragment fgm = new CarouselFragment();
        fragmentTransaction.add(R.id.main_functions_entry_point, fgm,"main_down_fragment");
        fragmentTransaction.commit();
//        SpannableString s = new SpannableString("My Title");
//        s.setSpan(new TypefaceSpan(this, ".otf"), 0, s.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getActionBar().setTitle("                SCREEN now");
        getOverflowMenu();
        SetupDataStructures();


    }
    public Device[] Family_Devices=null;
    public static final int MAXIMUM_NUMBER_OF_DEVICES=12;
    private void SetupDataStructures() {
        Family_Devices = new Device[MAXIMUM_NUMBER_OF_DEVICES];
        int index;
        for(index=0;index<Family_Devices.length;index++)
        {
            Family_Devices[index] = new Device();
        }
        InitDevicesData();


    }

    public void InitDevicesData() {
        Family_Devices[0].Device_name="Main TV";
        Family_Devices[0].Is_available=true;
        Family_Devices[0].Is_selected_for_current_operation=false;

        Family_Devices[1].Device_name="xBox";
        Family_Devices[1].Is_available=true;
        Family_Devices[1].Is_selected_for_current_operation=false;
        Family_Devices[2].Device_name="Kids PC";
        Family_Devices[2].Is_available=true;
        Family_Devices[2].Is_selected_for_current_operation=false;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings_action) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//        // When the given tab is selected, switch to the corresponding page in
//        // the ViewPager.
//        mViewPager.setCurrentItem(tab.getPosition());
//    }

//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }

//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//    }




    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public Context app_context;
        public SectionsPagerAdapter(FragmentManager fm, Context app_context) {
            super(fm);
            app_context=app_context;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            PlaceholderFragment fragi = PlaceholderFragment.newInstance(position + 1);

          //  Bundle bundi = new Bundle();

            //fragi.setArguments();
            return fragi;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }
    GridLayout device_page1;
    GridLayout device_page2;
    int last_focus_device=0;

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends android.support.v4.app.Fragment {
        /**
         * The fragment argument representing the section number for this
         *
         * fragment.
         */
        public static int[] devices = {R.id.device1,R.id.device2,R.id.device3,R.id.device4,R.id.device5, R.id.device6};
        public void SetfocusDevice(int device) {
            View root_view = this.getView();
            Circularinfo circle = (Circularinfo)root_view.findViewById(devices[device]);
            //call for set focus on specific device

            //this.getView()
        }
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        public Context app_context;
        public PlaceholderFragment() {
        }

        @Override
        public void onStart()
        {
            super.onStart();
//            View rootView = this.getView();
//            RotateAnimation an = new RotateAnimation(0,-90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
//            Circularinfo circleview = (Circularinfo)rootView.findViewById(R.id.device1);
//            an.setDuration(10000);
//            circleview.progressbar.setAnimation(an);
//
//            circleview.progressbar.setProgress(80);
//
//            circleview.progressbar.animate();
        }


        @Override
        public void onResume()
        {
            super.onResume();
//            View rootView = this.getView();
//            RotateAnimation an = new RotateAnimation(0,-90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
//            Circularinfo circleview = (Circularinfo)rootView.findViewById(R.id.device1);
////            an.setDuration(10000);
//            an.setFillAfter(true);
//            circleview.progressbar.setAnimation(an);
//
//            circleview.progressbar.setProgress(99);
//
//            circleview.progressbar.animate();

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            RotateAnimation an = new RotateAnimation(0,270, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
            an.setFillAfter(true);
            int page_number = this.getArguments().getInt(ARG_SECTION_NUMBER);
            if(page_number==1) {
                ((MainActivity)getActivity()).device_page1 = (GridLayout)rootView;
            }
            else
            {
                ((MainActivity)getActivity()).device_page2 = (GridLayout)rootView;
            }
            SetupPage(rootView, an, page_number);


//            for (int i=0;i<6;i++) {
//                rootView.
//            }
            switch(page_number)
            {
               case 0:
                   rootView.setTag("0");
                   break;
                case 1:
                    rootView.setTag("1");
                    break;
                default:
                    break;
            }

            return rootView;
        }




        private void SetupPage(View rootView, RotateAnimation an, int page_number) {

            if(page_number==1) {

                Circularinfo circleview = (Circularinfo) rootView.findViewById(R.id.device1);
                circleview.progressbar.setAnimation(an);
                circleview.devicename.setText("Main TV");
                //circleview.progressbar.setProgress(80);
                circleview.SetProgress(80);
                circleview.SetTimeRemaining("03:02");
                circleview.SetNotOffline(true);
                circleview.progressbar.animate();



               circleview.setOnTouchListener(new View.OnTouchListener() {
                   @Override
                   public boolean onTouch(View v, MotionEvent event) {
                       Circularinfo cir;
                       GridLayout grid = (GridLayout)v.getParent();
                       FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        if(fragmentManager.getBackStackEntryCount()==0) {
                            ResetDevicesToUnSelected(grid);
                        }
                       SetDeviceVisabiltyAndActionFlag((Circularinfo) v,0);
                       // ((Circularinfo)v).Selectdevice(true);

//

                       ((MainActivity)getActivity()).device_selected = 0;

                       StartDialogForActionsOnDevicesClicked();
//                       if(misile.was_canceled)
//                       {
//                           for (int index=0;index<number_of_children;index++)
//                           {
//                               cir = (Circularinfo)grid.getChildAt(index);
//                               cir.SetNotOffline(false);
////                               cir = (Circularinfo)grid.getChildAt(3);
//
//                           }
//                           cir = (Circularinfo)grid.getChildAt(1);
//                           cir.SetNotOffline(true);
//                           cir = (Circularinfo)grid.getChildAt(3);
//                           cir.SetNotOffline(true);
//                       }

                       return false;
                   }
               });
//                the start of the replacement section between xbox and device number 2
                circleview = (Circularinfo) rootView.findViewById(R.id.device5);


                //circleview.progressbar.startAnimation(an);
                circleview.devicename.setText("xBox");
//                circleview.SetNotOffline(false);
                circleview.SetNotOffline(true);
                circleview.SetTimeRemaining("00:54");
                circleview.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Circularinfo cir;
                        GridLayout grid = (GridLayout)v.getParent();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        if(fragmentManager.getBackStackEntryCount()==0) {
                            ResetDevicesToUnSelected(grid);
                        }
                        SetDeviceVisabiltyAndActionFlag((Circularinfo) v,0);
                        // ((Circularinfo)v).Selectdevice(true);

//

                        ((MainActivity)getActivity()).device_selected = 0;

                        StartDialogForActionsOnDevicesClicked();
//                       if(misile.was_canceled)
//                       {
//                           for (int index=0;index<number_of_children;index++)
//                           {
//                               cir = (Circularinfo)grid.getChildAt(index);
//                               cir.SetNotOffline(false);
////                               cir = (Circularinfo)grid.getChildAt(3);
//
//                           }
//                           cir = (Circularinfo)grid.getChildAt(1);
//                           cir.SetNotOffline(true);
//                           cir = (Circularinfo)grid.getChildAt(3);
//                           cir.SetNotOffline(true);
//                       }

                        return false;
//                        Circularinfo cir;
//                        GridLayout grid = (GridLayout)v.getParent();
//                        int number_of_children = grid.getChildCount();
//                        for (int index=0;index<number_of_children;index++)
//                        {
//                            cir = (Circularinfo)grid.getChildAt(index);
//                            cir.Selectdevice(false);
//                        }
//                        ((Circularinfo)v).Selectdevice(true);
//                        FireMissilesDialogFragment misile = new FireMissilesDialogFragment();
////
//                        Bundle bundi = new Bundle();
//                        ((MainActivity)getActivity()).device_selected = 0;
//                        ((MainActivity)getActivity()).Family_Devices[0].Is_selected_for_current_operation=true;
//
//                        bundi.putInt("device_selected",0);
////                       bundi.putSerializable("context",getActivity().getApplicationContext());
////                       misile.setArguments(bundi);
//                        misile.app_context = getActivity().getApplicationContext();
//                        misile.setArguments(bundi);
//
//                        misile.show(getFragmentManager(), "test");
////                       if(misile.was_canceled)
////                       {
////                           for (int index=0;index<number_of_children;index++)
////                           {
////                               cir = (Circularinfo)grid.getChildAt(index);
////                               cir.SetNotOffline(false);
//////                               cir = (Circularinfo)grid.getChildAt(3);
////
////                           }
////                           cir = (Circularinfo)grid.getChildAt(1);
////                           cir.SetNotOffline(true);
////                           cir = (Circularinfo)grid.getChildAt(3);
////                           cir.SetNotOffline(true);
////                       }
//
//                        return false;
                    }
                });
//                circleview = (Circularinfo) rootView.findViewById(R.id.device2);
//
//                circleview.SetNotOffline(false);
//                circleview.SetTimeRemaining("01:54");
//                circleview.progressbar.startAnimation(an);

                //circleview.Selectdevice(false);
//              circleview.progressbar.setProgress(100);
                circleview = (Circularinfo) rootView.findViewById(R.id.device3);
                circleview.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Circularinfo cir;
                        GridLayout grid = (GridLayout)v.getParent();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        if(fragmentManager.getBackStackEntryCount()==0) {
                            ResetDevicesToUnSelected(grid);
                        }
                        SetDeviceVisabiltyAndActionFlag((Circularinfo) v,2);
                        // ((Circularinfo)v).Selectdevice(true);

//

                        ((MainActivity)getActivity()).device_selected = 1;//probablly not relevant any more due to changes in the mechanism

                        StartDialogForActionsOnDevicesClicked();
//                       if(misile.was_canceled)
//                       {
//                           for (int index=0;index<number_of_children;index++)
//                           {
//                               cir = (Circularinfo)grid.getChildAt(index);
//                               cir.SetNotOffline(false);
////                               cir = (Circularinfo)grid.getChildAt(3);
//
//                           }
//                           cir = (Circularinfo)grid.getChildAt(1);
//                           cir.SetNotOffline(true);
//                           cir = (Circularinfo)grid.getChildAt(3);
//                           cir.SetNotOffline(true);
//                       }

                        return false;
//                        Circularinfo cir;
//                        GridLayout grid = (GridLayout)v.getParent();
//                        int number_of_children = grid.getChildCount();
//                        for (int index=0;index<number_of_children;index++)
//                        {
//                            cir = (Circularinfo)grid.getChildAt(index);
//                            cir.Selectdevice(false);
//                        }
//                        ((Circularinfo)v).Selectdevice(true);
//                        FireMissilesDialogFragment misile = new FireMissilesDialogFragment();
////
//                        Bundle bundi = new Bundle();
//                        ((MainActivity)getActivity()).device_selected = 1;
//                        ((MainActivity)getActivity()).Family_Devices[2].Is_selected_for_current_operation=true;
//                        bundi.putInt("device_selected",1);//mean kids pc
//
////                       bundi.putSerializable("context",getActivity().getApplicationContext());
////                       misile.setArguments(bundi);
//                        misile.app_context = getActivity().getApplicationContext();
//                        misile.setArguments(bundi);
//
//                        misile.show(getFragmentManager(), "test");
////                       if(misile.was_canceled)
////                       {
////                           for (int index=0;index<number_of_children;index++)
////                           {
////                               cir = (Circularinfo)grid.getChildAt(index);
////                               cir.SetNotOffline(false);
//////                               cir = (Circularinfo)grid.getChildAt(3);
////
////                           }
////                           cir = (Circularinfo)grid.getChildAt(1);
////                           cir.SetNotOffline(true);
////                           cir = (Circularinfo)grid.getChildAt(3);
////                           cir.SetNotOffline(true);
////                       }
//
//                        return false;
                    }
                });

                circleview.devicename.setText("Kids PC");
                circleview.SetTimeRemaining("05:42");
                circleview.progressbar.startAnimation(an);
                circleview.SetNotOffline(true);
                circleview.SetProgress(30);
                circleview = (Circularinfo) rootView.findViewById(R.id.device4);


                //circleview.progressbar.startAnimation(an);
                circleview.devicename.setText("Sam's phone");
                circleview.SetNotOffline(false);
                circleview.SetTimeRemaining("00:02");
                circleview.SetProgress(99);
                //circleview.SetProgress(100);
//                the next lines are replacements between device 2 and xbox
                circleview = (Circularinfo) rootView.findViewById(R.id.device2);

                circleview.SetNotOffline(false);
                circleview.SetTimeRemaining("01:54");
                circleview.progressbar.startAnimation(an);
//                end of the replacement section

                //circleview.SetProgress(100);
                circleview = (Circularinfo) rootView.findViewById(R.id.device6);
                circleview.progressbar.startAnimation(an);
                circleview.devicename.setText("PlayStation");
               // circleview.SetTimeRemaining("N/A");
                //circleview.remaining.setText("");
                circleview.SetNotOffline(false);
                circleview.SetProgress(15);
                an.setFillAfter(true);
            }
            else
            {
                Circularinfo circleview = (Circularinfo) rootView.findViewById(R.id.device1);
                circleview.progressbar.setAnimation(an);
                circleview.devicename.setText("Wii");
               // circleview.SetTimeRemaining("N/A");
                //circleview.remaining.setText("");
                circleview.SetNotOffline(false);
                circleview.SetTimeRemaining("0:30");
                circleview.SetProgress(45);
                circleview.progressbar.animate();

                circleview = (Circularinfo) rootView.findViewById(R.id.device2);

                circleview.devicename.setText("Dan's phone");
              //  circleview.SetTimeRemaining("N/A");
              //  circleview.remaining.setText("");
                circleview.SetTimeRemaining("05:02");
                circleview.progressbar.startAnimation(an);
                circleview.SetNotOffline(false);
                circleview.SetProgress(95);
                circleview = (Circularinfo) rootView.findViewById(R.id.device3);

                circleview.devicename.setText("Dan's laptop");
                circleview.SetTimeRemaining("N/A");
                circleview.remaining.setText("");
                circleview.progressbar.startAnimation(circleview.getAnimationforCircular(false));
                circleview.SetNotOffline(false);
                circleview.SetProgress(100);
                circleview = (Circularinfo) rootView.findViewById(R.id.device4);

                circleview.devicename.setText("Sam's Mac");
                circleview.SetTimeRemaining("N/A");
                //circleview.remaining.setText("");
                circleview.progressbar.startAnimation(circleview.getAnimationforCircular(false));
                circleview.SetNotOffline(false);
                circleview.SetProgress(100);
                circleview = (Circularinfo) rootView.findViewById(R.id.device5);
                circleview.devicename.setText("Anna's PC");
                circleview.SetTimeRemaining("N/A");
                circleview.remaining.setText("");

                circleview.progressbar.startAnimation(circleview.getAnimationforCircular(false));
                circleview.SetNotOffline(false);
                circleview.SetProgress(100);
                circleview = (Circularinfo) rootView.findViewById(R.id.device6);
                circleview.devicename.setText("Dan's TV");
                circleview.SetTimeRemaining("N/A");
                circleview.remaining.setText("");
                circleview.progressbar.startAnimation(circleview.getAnimationforCircular(false));
                circleview.SetNotOffline(false);
                circleview.SetProgress(100);
                an.setFillAfter(true);
            }


        }

        private void ResetDevicesToUnSelected(GridLayout grid) {
            Circularinfo cir;
            int number_of_children = grid.getChildCount();
            for (int index=0;index<number_of_children;index++)
            {
                cir = (Circularinfo)grid.getChildAt(index);
                cir.Selectdevice(false);
            }
        }

        private void StartDialogForActionsOnDevicesClicked() {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if(fragmentManager.getBackStackEntryCount()==0)//incase this is the first time user touches the device
            {
                FireMissilesDialogFragment misile = new FireMissilesDialogFragment();
                Bundle bundi = new Bundle();
                bundi.putInt("device_selected", 0);
//                       bundi.putSerializable("context",getActivity().getApplicationContext());
//                       misile.setArguments(bundi);
                misile.app_context = getActivity().getApplicationContext();
                misile.setArguments(bundi);

                misile.show(getFragmentManager(), "test");
            }
        }

        private void SetDeviceVisabiltyAndActionFlag(Circularinfo v, int index) {
            if(((MainActivity)getActivity()).Family_Devices[index].Is_selected_for_current_operation==true)
            {
                ((Circularinfo)v).Selectdevice(false);
                ((MainActivity)getActivity()).Family_Devices[index].Is_selected_for_current_operation=false;

            }
            else
            {
                ((Circularinfo)v).Selectdevice(true);
                ((MainActivity)getActivity()).Family_Devices[index].Is_selected_for_current_operation=true;

            }
        }
    }



}
