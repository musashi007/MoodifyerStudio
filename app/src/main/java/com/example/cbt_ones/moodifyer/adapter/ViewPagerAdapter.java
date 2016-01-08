package com.example.cbt_ones.moodifyer.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbt_ones.moodifyer.R;
import com.example.cbt_ones.moodifyer.pojo.PagesPojo;

/**
 * Created by cbt_ones on 1/6/2016.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;

    public ViewPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        PagesPojo pagespojo = PagesPojo.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(pagespojo.getLayoutResId(), collection, false);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return PagesPojo.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        PagesPojo pagespojo = PagesPojo.values()[position];
        return mContext.getString(pagespojo.getTitleResId());
    }
}
