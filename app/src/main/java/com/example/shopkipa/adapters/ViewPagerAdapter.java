package com.example.shopkipa.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.shopkipa.R;
import com.example.shopkipa.models.GetStockInTypeModel;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    private Context context;
    private LayoutInflater layoutInflater;
    public static String[] images;
    private GestureDetector mGestureDetector;
// ={R.drawable.pager1, R.drawable.pager2, R.drawable.pager4}

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.image_view_pager, null);
        ImageView imageView = view.findViewById(R.id.itemimage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(context, FullscreenActivity.class);
//                intent.putExtra("product",images[position]);
//                context.startActivity(intent);
            }
        });
//        imageView.setImageResource(images[position]);
        Glide.with(context).load(images[position])
                .into(imageView);
        ViewPager viewPager = (ViewPager)container;
        viewPager.addView(view);
        mGestureDetector = new GestureDetector(context, this);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager viewPager = (ViewPager)container;
        View view =(View)object;
        viewPager.removeView(view);

    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
