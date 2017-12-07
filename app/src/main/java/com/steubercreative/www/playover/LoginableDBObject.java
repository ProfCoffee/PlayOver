package com.steubercreative.www.playover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Sean on 12/6/2017.
 */

public abstract class LoginableDBObject extends AbstractModifiableDBObject {

    private QueryReceiver loginReceiver = new QueryReceiver() {
        @Override
        public void processQuery(Intent intent) {
            if(Integer.valueOf(intent.getStringExtra("success")) != 1) {
                defaultReceiver.processQuery(intent);
            }
            else {
                throw new IllegalArgumentException("Login failed");
            }
        }
    };
    protected LoginableDBObject() { this(false);}
    protected LoginableDBObject(boolean readOnly) {
        super(readOnly);
    }

    public abstract void login(Context context);
    public abstract void logout(Context context);

    protected void loginHelper(Context context, String queryId) {
        Bundle b = new Bundle();
        b.putString("username", getData("username"));
        b.putString("password", getData("password"));
        Set<String> output = new TreeSet<>();
        output.add("success");
        output.add("uid");
        query(context, b, output, queryId, loginReceiver);
        retrieve(context);
    }

}
