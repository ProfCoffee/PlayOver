package com.steubercreative.www.playover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by stste on 12/5/2017.
 */

public class FeedbackLoader extends LayoutLoader{
    boolean _isProvider = false;

    public FeedbackLoader(Context context){
        super(context);
    }
    public FeedbackLoader(Context context, boolean isProvider){
        super(context);
        _isProvider = isProvider;
    }

    public void PopulateLayout (ViewGroup layout, int uid, int count){
        for (int i = 0; i < count; i++){
            AddRow(layout);
        }
    }

    public void AddRow(ViewGroup layout){
        View activityView;
        activityView = LayoutInflater.from(_context).inflate(R.layout.feedback_row, layout, false);

        LinearLayout feedBackStars_layout = activityView.findViewById(R.id.feedback_stars);
        addStars(feedBackStars_layout, 5);

        layout.addView(activityView);
    }
}
