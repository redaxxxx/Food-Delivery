package com.prof.reda.android.project.fooddelivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.ViewPagerBinding;

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater inflater;

    private ViewPagerBinding binding;

    private final int[] images = {
            R.drawable.onboarding2,
            R.drawable.onboarding3
    };

    private final String[] titles = {
            "Find your Comfort" +"\n"+ "Food here",
            "Food Ninja is Where Your" +"\n"+ "Comfort Food Lives"
    };

    private final String[] descs = {
            "Here You Can find a chef or dish for every" + "\n"+ "taste and color. Enjoy!",
            "Enjoy a fast and smooth food delivery at" +"\n"+ "your doorstep"
    };

    public ViewPagerAdapter(@NonNull Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);

        binding = DataBindingUtil.inflate(inflater, R.layout.view_pager, container, false);

        // init views
        binding.imageView2.setImageResource(images[position]);
        binding.title.setText(titles[position]);
        binding.descs.setText(descs[position]);

        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
