package com.steubercreative.www.playover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by stste on 12/6/2017.
 */

public class LayoutLoader {

    protected Context _context;

    public LayoutLoader(Context context){
        _context = context;
    }

    public void addStars(ViewGroup layout, int count){
        for (int i = 0; i < count; i++){
            View activityView;
            activityView = LayoutInflater.from(_context).inflate(R.layout.feedback_star, layout, false);
            layout.addView(activityView);
        };
    }
}
