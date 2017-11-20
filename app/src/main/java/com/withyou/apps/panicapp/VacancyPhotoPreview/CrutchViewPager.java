package com.withyou.apps.panicapp.VacancyPhotoPreview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/*
    Костыль, из-за крашей при взаимодействии PhotoView с ViewPager. Взят с оф. страницы либы.
 */
public class CrutchViewPager extends ViewPager {

    public CrutchViewPager(Context context) {
        super(context);
    }

    public CrutchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
