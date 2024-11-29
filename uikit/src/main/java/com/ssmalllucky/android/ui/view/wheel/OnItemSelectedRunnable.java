package com.ssmalllucky.android.ui.view.wheel;

public final class OnItemSelectedRunnable implements Runnable {
    final STWheelView loopView;

    public OnItemSelectedRunnable(STWheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getCurrentItem());
    }
}
