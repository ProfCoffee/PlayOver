package com.steubercreative.www.playover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Semaphore;

/**
 * Created by Sean on 12/6/2017.
 */

abstract public class AbstractDBArray<T extends ModifiableDBObject> {
    private String arrayFieldName;
    private Semaphore pending;
    private BroadcastReceiver broadcastReceiver;
    private Context currentContext;
    private Procedure after;
    private boolean perror;

    protected AbstractDBArray() {
        after = null;
        perror = false;
        pending = new Semaphore(1);
        currentContext = null;
        broadcastReceiver =  new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(!intent.hasExtra("success") || Integer.valueOf(intent.getStringExtra("success")) == 1) {
                    Collection<Map<String, String>> rows = (Collection<Map<String, String>>) intent.getSerializableExtra(arrayFieldName);
                    for(Map<String, String> row : rows)
                        addObject(row);
                }
                else setError();
                arrayFieldName = null;
                LocalBroadcastManager.getInstance(currentContext).unregisterReceiver(broadcastReceiver);
                currentContext = null;
                pending.release();
                if(after != null) {
                    after.run();
                    after = null;
                }
            }
        };

    }

    public void onCompletion(Procedure p) {
        after = p;
    }
    public boolean error(){ return perror; }

    abstract public List<T> getObjects();
    abstract public void retrieve(Context context);
    abstract protected void addObject(Map<String, String> fields);

    protected void setError() { perror = true; }
    protected void retrieveHelper(Context context, Bundle input, String arrayField, String queryType) {
        Set<String> strings = new TreeSet<>();
        strings.add("success");
        try {
            query(context, input, strings, arrayField, queryType);
        } catch(RuntimeException e) {
            setError();
        }
    }

    protected void query(Context context, Bundle input, Set<String> outputStrings,
                         String arrayField, String queryType) {
        arrayFieldName = arrayField;
        Set<String> arrays = new TreeSet<>();
        arrays.add(arrayFieldName);
        currentContext = context;
        try {
            pending.acquire();
        } catch (InterruptedException e) {}
        LocalBroadcastManager.getInstance(context)
                .registerReceiver(broadcastReceiver, new IntentFilter(QueryMapper.getQueryReturnAction(queryType)));
        DBConnectionService.startArrayAction(context, input, outputStrings, arrays, queryType);
    }

}
