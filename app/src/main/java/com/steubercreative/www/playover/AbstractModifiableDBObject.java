package com.steubercreative.www.playover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Sean on 12/6/2017.
 */

public abstract class AbstractModifiableDBObject implements ModifiableDBObject {

    protected final QueryReceiver defaultReceiver = new QueryReceiver() {
        @Override
        public void processQuery(Intent intent) {
            extractData(intent);
        }
    };
    private final boolean isReadOnly;
    private Context currentContext;
    private QueryReceiver receiver;
    private final BroadcastReceiver broadcastReceiver;
    private Procedure after;

    abstract protected Set<String> modifiableFields();
    abstract protected Set<String> unmodifiableFields();
    abstract protected String getData(String name);
    abstract protected void populate(Map<String, String> fields);

    protected AbstractModifiableDBObject() { this(false); }
    protected AbstractModifiableDBObject(boolean readOnly) {
        after = null;
        isReadOnly = readOnly;
        broadcastReceiver =  new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                receiver.processQuery(intent);
                LocalBroadcastManager.getInstance(currentContext).unregisterReceiver(broadcastReceiver);
                receiver = null;
                currentContext = null;
                if(after != null) {
                    after.run();
                    after = null;
                }
            }
        };
        receiver = null;
        currentContext = null;
    }

    @Override
    public void onCompletion(Procedure procedure) {
        after = procedure;
    }

    protected void extractData(Intent intent) {

        if(!intent.getExtras().keySet().contains("success") || Integer.valueOf(intent.getStringExtra("success")) == 1) {
            Map<String, String> data = new TreeMap<>();
            for(String key : intent.getExtras().keySet()) {
                if(unmodifiableFields().contains(key) || modifiableFields().contains(key)) {
                    data.put(key, intent.getStringExtra(key));
                }
            }
            populate(data);
        }
        else throw new RuntimeException("Request failed");
    }

    /**
     * helpers for methods inherited from ModifiableDBObject.
     * They should be called from their corresponding public methods, and passed the additional
     * queryId - the unique identifier for this type of query
     */

    protected void retrieveHelper(Context context, String queryId) {
        Bundle b = new Bundle();
        b.putString("uid", getData("uid"));
        Set<String> output = new TreeSet<String>();
        output.addAll(modifiableFields());
        output.addAll(unmodifiableFields());
        query(context, b, output, queryId, defaultReceiver);
    }
    protected void createHelper(Context context, String queryId) {
        if(isReadOnly)
            throw new UnsupportedOperationException("Cannot create database object in read-only mode");
        Bundle b = new Bundle();
        for(String key : modifiableFields()) {
            b.putString(key, getData(key));
        }
        Set<String> output = new TreeSet<String>();
        output.addAll(modifiableFields());
        output.addAll(unmodifiableFields());
        query(context, b, output, queryId, defaultReceiver);
    }
    protected void saveHelper(Context context, String queryId) {
        if(isReadOnly)
            throw new UnsupportedOperationException("Cannot update database object in read-only mode");
        for(String key : modifiableFields()) {
            Bundle b = new Bundle();
            b.putString(key, getData(key));
            Set<String> output = new TreeSet<>();
            output.add("success");
            query(context, b, output, queryId, defaultReceiver);
        }
    }
    protected void deleteHelper(Context context, String queryId) {
        if(isReadOnly)
            throw new UnsupportedOperationException("Cannot delete database object in read-only mode");
        Bundle b = new Bundle();
        b.putString("uid", getData("uid"));
        Set<String> output = new TreeSet<String>();
        output.add("success");
        query(context, b, output, queryId, defaultReceiver);
    }


    protected void query(Context context, Bundle input, Set<String> output, String queryType,
                         QueryReceiver rec) {
        receiver = rec;
        currentContext = context;
        LocalBroadcastManager.getInstance(context)
                .registerReceiver(broadcastReceiver, new IntentFilter(QueryMapper.getQueryReturnAction(queryType)));
        DBConnectionService.startAction(context, input, output, queryType);
    }
}
