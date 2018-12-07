package com.example.mlibrary;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

public class DepthPageTransformer2 implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        if (position < -1) {
            position = -1;
        } else if (position > 1) {
            position = 1;
        }
        float tempScale = position < 0 ? 1 + position : 1 - position;
        float slope = (1.0f - 0.9f) / 1;
        //一个公式
        float scaleValue = 0.9f + tempScale * slope;
        page.setScaleX(1.0f);
        page.setScaleY(scaleValue);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            page.getParent().requestLayout();
        }
    }
}


