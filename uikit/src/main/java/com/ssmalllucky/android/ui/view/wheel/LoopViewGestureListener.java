package com.ssmalllucky.android.ui.view.wheel;

import android.view.MotionEvent;

public final class LoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

    final STWheelView loopView;

    public LoopViewGestureListener(STWheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        loopView.scrollBy(velocityY);
        return true;
    }
}
