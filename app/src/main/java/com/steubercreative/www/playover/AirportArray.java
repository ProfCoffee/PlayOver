package com.steubercreative.www.playover;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Sean on 12/6/2017.
 */

public class AirportArray extends AbstractDBArray<Airport> {
    private List<Airport> dbObjects;

    public AirportArray() {
        super();
        dbObjects = new ArrayList<>();
    }

    @Override
    public List<Airport> getObjects() {
        return dbObjects;
    }
    @Override
    public void retrieve(Context context) {
        Bundle b = new Bundle();
        retrieveHelper(context, b, "airports", QueryMapper.ACTION_GET_AIRPORTS);
    }
    @Override
    protected void addObject(Map<String, String> row) {
        dbObjects.add(new Airport(row));
    }
}
