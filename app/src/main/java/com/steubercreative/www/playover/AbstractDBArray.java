package com.steubercreative.www.playover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Sean on 12/6/2017.
 */

abstract public class AbstractDBArray<T extends ModifiableDBObject> {
    private String arrayFieldName;
    private boolean pending;
    private BroadcastReceiver broadcastReceiver;
    private Context currentContext;

    protected AbstractDBArray() {
        pending = false;
        currentContext = null;
        broadcastReceiver =  new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Integer.valueOf(intent.getStringExtra("success")) == 1) {
                    Collection<Map<String, String>> rows = (Collection<Map<String, String>>) intent.getSerializableExtra(arrayFieldName);
                    for(Map<String, String> row : rows) {
                        addObject(row);
                    }
                }
                else throw new RuntimeException("Array query failed");
                arrayFieldName = null;
                LocalBroadcastManager.getInstance(currentContext).unregisterReceiver(broadcastReceiver);
                currentContext = null;
                pending = false;
            }
        };

    }

    abstract public List<T> getObjects();
    abstract public void retrieve(Context context);
    abstract protected void addObject(Map<String, String> fields);

    protected void retrieveHelper(Context context, Bundle input, String arrayField, String queryType) {
        Set<String> strings = new TreeSet<>();
        strings.add("success");
        query(context, input, strings, arrayField, queryType);
    }

    private boolean isWaiting() {
        return pending;
    }
    private void waitForDBResponse() {
        while(isWaiting()) {}
    }

    protected void query(Context context, Bundle input, Set<String> outputStrings,
                         String arrayField, String queryType) {
        arrayFieldName = arrayField;
        Set<String> arrays = new TreeSet<>();
        arrays.add(arrayFieldName);
        currentContext = context;
        pending = true;
        LocalBroadcastManager.getInstance(context)
                .registerReceiver(broadcastReceiver, new IntentFilter(QueryMapper.getQueryReturnAction(queryType)));
        DBConnectionService.startArrayAction(context, input, outputStrings, arrays, queryType);
        waitForDBResponse();
    }

}
