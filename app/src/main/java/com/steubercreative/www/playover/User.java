package com.steubercreative.www.playover;

import android.content.Context;
import android.os.Bundle;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Sean on 12/6/2017.
 */

public class User extends LoginableDBObject {
    private int uid;
    private String name;
    private String username;
    private String password;
    private Set<String> modifiable;
    private Set<String> unmodifiable;

    public User() {
        super();
        setupModifiable();
        setupUnmodifiable();
        clear();
    }

    @Override
    public void login(Context context) {
        loginHelper(context, QueryMapper.ACTION_USER_LOGIN);
    }
    @Override
    public void logout(Context context) {
        clear();
    }

    @Override
    public void retrieve(Context context) {
        retrieveHelper(context, QueryMapper.ACTION_FETCH_USER_INFO);
    }
    @Override
    public void save(Context context) {
        saveHelper(context, QueryMapper.ACTION_UPDATE_USER_INFO);
    }
    @Override
    public void create(Context context) {
        createHelper(context, QueryMapper.ACTION_CREATE_USER);
    }
    @Override
    public void delete(Context context) {
        deleteHelper(context, QueryMapper.ACTION_DELETE_USER);
        clear();
    }

    protected String getData(String id) {
        if(id.equals("name"))
            return name;
        else if(id.equals("username"))
            return username;
        else if(id.equals("password"))
            return password;
        else if(id.equals("uid"))
            return String.valueOf(uid);
        else throw new IllegalArgumentException("No such data field: " + id);
    }
    @Override
    protected Set<String> modifiableFields() {
        return modifiable;
    }
    @Override
    protected Set<String> unmodifiableFields() {
        return unmodifiable;
    }
    @Override
    protected void populate(Map<String, String> fields) {
        for(String key : fields.keySet()) {
            if(key.equals("uid"))
                uid = Integer.valueOf(fields.get(key));
            else if(key.equals("name"))
                name = fields.get(key);
            else if(key.equals("username"))
                username = fields.get(key);
            else if(key.equals("password"))
                password = fields.get(key);
        }
    }


    public int getUid() { return uid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    private void clear() {
        uid = 0;
        name = "";
        username = "";
        password = "";
    }
    private void setupModifiable() {
        modifiable = new TreeSet<>();
        modifiable.add("name");
        modifiable.add("username");
        modifiable.add("password");
    }
    private void setupUnmodifiable() {
        unmodifiable = new TreeSet<>();
        unmodifiable.add("uid");
    }

}
