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
        this(context, true);
    }
    public FeedbackLoader(Context context, boolean isProvider){
        super(context);
        _isProvider = isProvider;
    }

    public void populateLayout(ViewGroup layout, int uid, int count){
        final ViewGroup layoutAlias = layout;
        final AbstractDBArray<Feedback> feedback =
                (_isProvider)
                ? new ProviderFeedbackArray(uid)
                : new ActivityFeedbackArray(uid);
        feedback.onCompletion(new Procedure() {
            @Override
            public void run() {
                for(Feedback item : feedback.getObjects())
                    addRow(item, layoutAlias);
            }
        });
        //feedback.retrieve(this._context);
    }

    public void addRow(Feedback feedback, ViewGroup layout){
        final View activityView = LayoutInflater.from(_context).inflate(R.layout.feedback_row, layout, false);
        final User author = new User(true);
        author.setUid(feedback.getAuthor());
        author.onCompletion(new Procedure() {
            @Override
            public void run() {
                TextView text = activityView.findViewById(R.id.FeedBackAuthor);
                text.setText(author.getName());
            }
        });
        author.retrieve(this._context);

        //TODO
        LinearLayout feedBackStars_layout = activityView.findViewById(R.id.feedback_stars);
        addStars(feedBackStars_layout, feedback.getRating());  //rating

        TextView date_thing = activityView.findViewById(R.id.FeedbackDate);
        date_thing.setText(feedback.getDate());

        TextView descriptionBox = activityView.findViewById(R.id.FeedBackDescription);
        descriptionBox.setText(feedback.getContent());

        layout.addView(activityView);
    }
}
