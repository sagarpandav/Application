package com.example.mitul.application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int imageArray[] = {R.drawable.slide_1, R.drawable.slide_2, R.drawable.slide_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewPager_imageSlider);
        MySliderAdapter adapter = new MySliderAdapter(this);
        viewPager.setAdapter(adapter);
    }

    class MySliderAdapter extends PagerAdapter {

        Context context;
        LayoutInflater inflater;
        ImageView imageId;
        TextView title;

        public MySliderAdapter(Context context) {
            this.context =  context;
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View iteView = inflater.inflate(R.layout.image_slider_single, container, false);

            imageId = iteView.findViewById(R.id.image_slider_img);
            imageId.setImageResource(imageArray[position]);

            viewPager.addView(iteView);
            return iteView;
        }

        @Override
        public int getCount() {
            return imageArray.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);

            Log.e("Destroyed", ""+position);
        }
    }
}
