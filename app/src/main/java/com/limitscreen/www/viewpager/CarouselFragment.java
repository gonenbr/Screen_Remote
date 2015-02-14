package com.limitscreen.www.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by gonen on 14/12/14.
 */
public class CarouselFragment extends Fragment {

    // =============================================================================
    // Supertype overrides
    // =============================================================================


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.carousel, container, false);
        FancyCoverFlow fancyCoverFlow = (FancyCoverFlow)rootView.findViewById(R.id.carouselcoverflow);
        fancyCoverFlow.setAdapter(new ViewGroupExampleAdapter());
        fancyCoverFlow.setUnselectedAlpha(0.8f);
        fancyCoverFlow.setUnselectedSaturation(0.0f);
        fancyCoverFlow.setUnselectedScale(0.001f);
        fancyCoverFlow.setSpacing(0);
        fancyCoverFlow.setMaxRotation(100);
        fancyCoverFlow.setScaleDownGravity(0.2f);
        fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
        fancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int dummy=0;
//                dummy++;
//                MainActivity activ = (MainActivity)getActivity();
//                ((Circularinfo)((GridLayout)activ.device_page1).getChildAt(activ.last_focus_device)).setHighlightfromScreencaptureChoose(false);
//                ((Circularinfo)((GridLayout)activ.device_page1).getChildAt(position)).setHighlightfromScreencaptureChoose(true);
//                activ.last_focus_device=position;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        fancyCoverFlow.setReflectionEnabled(true);
//        fancyCoverFlow.setReflectionRatio(0.3f);
//        fancyCoverFlow.setReflectionGap(0);


        return rootView;
    }



    // =============================================================================
    // Private classes
    // =============================================================================

    private static class ViewGroupExampleAdapter extends FancyCoverFlowAdapter {

        // =============================================================================
        // Private members
        // =============================================================================

        //private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6,};
        private int[] images = {R.drawable.statefarmdoratheexplorerlarge4, R.drawable.website, R.drawable.pcword,R.drawable.minecraft, R.drawable.xboxgame,};
        private int[] images_type = {0,1,2,1,0};//pc=2 mobile = 1 tv=0
        private String[] device_name_for_image_display = {"Main TV","Anna's phone","Kids PC", "Sam's phone", "xBox", "PlayStation",};
        // =============================================================================
        // Supertype overrides
        // =============================================================================

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Integer getItem(int i) {
            return images[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getCoverFlowItem(int position, View reusableView, ViewGroup parent) {
            CustomViewGroup2 customViewGroup = null;
            if (reusableView != null) {
                customViewGroup = (CustomViewGroup2) reusableView;
            } else {
                customViewGroup = new CustomViewGroup2(parent.getContext(),images_type[position]);
                //customViewGroup.device_name.setLayoutParams(new ViewGroup.LayoutParams(200,100));
                customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(350, 400));
                customViewGroup.getImageView().setImageResource(this.getItem(position));
                customViewGroup.device_name.setText(device_name_for_image_display[position]);
                //customViewGroup.device_name.setLayoutParams(new LinearLayout.LayoutParams(600,400));
                //customViewGroup.device_name.setText("this is test");


            }

           // customViewGroup.getImageView().setImageResource(this.getItem(position));
            //customViewGroup.getTextView().setText(String.format("Item %d", i));
            //customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(700, 500));

            return customViewGroup;
           // return null;
        }

        //        @Override
//        public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
//            CustomViewGroup customViewGroup = null;
//
//            if (reuseableView != null) {
//                customViewGroup = (CustomViewGroup) reuseableView;
//            } else {
//                customViewGroup = new CustomViewGroup(viewGroup.getContext());
//                customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(500, 600));
//            }
//
//            customViewGroup.getImageView().setImageResource(this.getItem(i));
//            //customViewGroup.getTextView().setText(String.format("Item %d", i));
//
//            return customViewGroup;
//        }
    }


    private static class CustomViewGroup2 extends LinearLayout{
        private ImageView image;
        private TextView device_name;
        private CustomViewGroup2(Context context,int figure_type)
        {
            super(context);
            this.setOrientation(VERTICAL);

            LayoutParams params;
            //device_name = new TextView(context);
            switch (figure_type){
                case 0://means tv
                    LayoutInflater inflater = LayoutInflater.from(context);
                    inflater.inflate(R.layout.imagesquaretv,this);

                    //View.inflate(context,R.layout.imagesquaretv,this);


                    //image = this.get
                    image = (ImageView)this.findViewById(R.id.imageView);
                    device_name = (TextView)this.findViewById(R.id.devicenamecaptureimage);
                    //image.setLayoutParams(new ViewGroup.LayoutParams());
                    params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    params.setMargins(105,7,0,0);
                    //image.setLayoutParams(params);
                    //image.setLayoutParams(new LayoutParams(MarginLayoutParams.));
                   // this.setLayoutParams(new FancyCoverFlow.LayoutParams(1000, 700));

                    break;
                case 1://means mobile
                    View.inflate(context,R.layout.imagesquaremobile,this);

                  //  params = new LayoutParams(700,500);
//                    params.setMargins(30,10,0,0);
                    image = (ImageView)this.findViewById(R.id.imageView);
                    device_name = (TextView)this.findViewById(R.id.devicenamecaptureimage);
                    //image.setLayoutParams(params);
                    //this.setLayoutParams(new FancyCoverFlow.LayoutParams(1000, 700));
                    //image.setLayoutParams(new ViewGroup.LayoutParams(260,150));
                    break;
                case 2:
                    View.inflate(context,R.layout.imagesquarepc,this);
                    //params = new LayoutParams(700,500);
//                    params.setMargins(30,10,0,0);
                    image = (ImageView)this.findViewById(R.id.imageView);
                    device_name = (TextView)this.findViewById(R.id.devicenamecaptureimage);
                    //image.setLayoutParams(params);
                   // this.setLayoutParams(new FancyCoverFlow.LayoutParams(1000, 700));
                    //image = (ImageView)this.findViewById(R.id.imageView);
                 //   image.setLayoutParams(new ViewGroup.LayoutParams(260,150));
                    break;
                default:
                    View.inflate(context,R.layout.imagesquaretv,this);

                    image = (ImageView)this.findViewById(R.id.imageView);
                    device_name = (TextView)this.findViewById(R.id.devicenamecaptureimage);
                  //  this.setLayoutParams(new FancyCoverFlow.LayoutParams(1000, 700));
                  //  image.setLayoutParams(new ViewGroup.LayoutParams(260,150));
                    break;
            }

            //this.addView(device_name);
            //device_name.setText("device name");

            //device_name.setTextSize(15);
            //device_name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
            //customViewGroup.setLayoutParams();








            //image = (ImageView)this.findViewById(R.id.imageView);



        }
        private ImageView getImageView() {
            return image;
        }


    }



    private static class CustomViewGroup extends LinearLayout {


        // =============================================================================
        // Child views
        // =============================================================================

        private TextView textView;

        private ImageView imageView;

        private Button button;

        // =============================================================================
        // Constructor
        // =============================================================================

        private CustomViewGroup(Context context) {
            super(context);

            //this.setOrientation(VERTICAL);

            //this.textView = new TextView(context);
            this.imageView = new ImageView(context);
            //this.button = new Button(context);

            LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //this.textView.setLayoutParams(layoutParams);
            this.imageView.setLayoutParams(layoutParams);
            //this.button.setLayoutParams(layoutParams);

            //this.textView.setGravity(Gravity.CENTER);

            this.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            this.imageView.setAdjustViewBounds(true);

            //this.button.setText("Goto GitHub");
//            this.button.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://davidschreiber.github.com/FancyCoverFlow"));
//                    view.getContext().startActivity(i);
//                }
//            });

//            this.addView(this.textView);
            this.addView(this.imageView);
 //           this.addView(this.button);
        }

        // =============================================================================
        // Getters
        // =============================================================================

        private TextView getTextView() {
            return textView;
        }

        private ImageView getImageView() {
            return imageView;
        }
    }


}
