package com.steubercreative.www.playover;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * provider view of activities
 */

public class ActivityArray extends AbstractDBArray<Activity> {
    private List<Activity> activities;
    private int providerUid;

    public ActivityArray(int providerUid) {
        super();
        activities = new ArrayList<>();
        this.providerUid = providerUid;
    }

    public List<Activity> getObjects() { return activities; }
    public void retrieve(Context context) {
        Bundle b = new Bundle();
        b.putString("provider", String.valueOf(providerUid));
        retrieveHelper(context, b, "activities", QueryMapper.ACTION_FETCH_PROVIDER_ACTIVITIES);
    }
    protected void addObject(Map<String, String> fields) {
        activities.add(new Activity(fields));
    }

}
