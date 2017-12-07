package com.steubercreative.www.playover;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by stste on 12/7/2017.
 */

public class ActivityFeedbackArray extends AbstractDBArray<Feedback> {
    private List<Feedback> feedback;
    private int activityUid;

    public ActivityFeedbackArray(int providerUid) {
        super();
        this.activityUid = activityUid;
        feedback = new ArrayList<>();
    }

    @Override
    public void retrieve(Context context) {
        Bundle b = new Bundle();
        b.putString("uid", String.valueOf(activityUid));
        retrieveHelper(context, b, "feedbacks", QueryMapper.ACTION_FETCH_ACTIVITY_FEEDBACK);
    }

    @Override
    protected void addObject(Map<String, String> fields) {
        feedback.add(new Feedback(fields,true));
    }

    @Override
    public List<Feedback> getObjects() {
        return feedback;
    }

}
