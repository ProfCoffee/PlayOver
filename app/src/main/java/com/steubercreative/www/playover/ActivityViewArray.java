package com.steubercreative.www.playover;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * View of activities by airport, unable to
 */

public class ActivityViewArray extends AbstractDBArray<Activity> {
    private String airportCode;
    private List<Activity> activities;

    public ActivityViewArray(String airportCode) {
        super();
        this.airportCode = airportCode;
        activities = new ArrayList<>();
    }

    public List<Activity> getObjects() { return activities; }
    public void retrieve(Context context) {
        Bundle b = new Bundle();
        b.putString("code", airportCode);
        retrieveHelper(context, b, "activities", QueryMapper.ACTION_FETCH_AIRPORT_ACTIVITIES);
    }
    protected void addObject(Map<String, String> fields) {
        activities.add(new Activity(fields, true));
    }


}
