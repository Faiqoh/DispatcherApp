package com.example.appdispatcher;

import android.view.View;

public class Animations {
    public static boolean toggleArrow(View view, boolean isExpanded) {
        if (isExpanded) {
            view.animate().setDuration(200).rotation(90);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }
}
