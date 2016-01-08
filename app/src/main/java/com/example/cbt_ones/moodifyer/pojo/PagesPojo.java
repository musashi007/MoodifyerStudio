package com.example.cbt_ones.moodifyer.pojo;

import com.example.cbt_ones.moodifyer.R;

/**
 * Created by cbt_ones on 1/6/2016.
 */
public enum PagesPojo {

    RED(R.string.app_name, R.layout.content_scrolling),
    BLUE(R.string.app_name, R.layout.mood_playlist);

    private int mTitleResId;
    private int mLayoutResId;

    PagesPojo(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
