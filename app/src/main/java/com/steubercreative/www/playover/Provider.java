package com.steubercreative.www.playover;

import android.content.Context;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Sean on 12/5/2017.
 */

public class Provider extends LoginableDBObject {
    private Set<String> modifiable;
    private Set<String> unmodifiable;
    private String name;
    private String username;
    private String password;
    private int uid;
    private String about;
    private Status status;
    private String contact;
    private int rating;
    private int rates;

    public Provider() {
        super();
        clear();
        setupModifiable();
        setupUnmodifiable();
    }

    @Override
    public void create(Context context) {
        createHelper(context, QueryMapper.ACTION_CREATE_PROVIDER);
    }
    @Override
    public void login(Context context) {
        loginHelper(context, QueryMapper.ACTION_PROVIDER_LOGIN);
    }
    @Override
    public void logout(Context context) {
        clear();
    }
    @Override
    public void save(Context context) {
        saveHelper(context, QueryMapper.ACTION_UPDATE_PROVIDER_INFO);
    }
    @Override
    public void retrieve(Context context) {
        retrieveHelper(context, QueryMapper.ACTION_FETCH_PROVIDER_INFO);
    }
    @Override
    public void delete(Context context) {
        deleteHelper(context, QueryMapper.ACTION_DELETE_PROVIDER);
        clear();
    }

    @Override
    protected void populate(Map<String, String> fields) {
        for(String key : fields.keySet()) {
            if(key.equals("uid"))
                uid = Integer.valueOf(fields.get(key));
            else if(key.equals("username"))
                username = fields.get(key);
            else if(key.equals("name"))
                name = fields.get(key);
            else if(key.equals("password"))
                password = fields.get(key);
            else if(key.equals("about"))
                about = fields.get(key);
            else if(key.equals("contact"))
                contact = fields.get(key);
            else if(key.equals("rating"))
                rating = Integer.valueOf(fields.get(key));
            else if(key.equals("rates"))
                rates = Integer.valueOf(fields.get(key));
            else if(key.equals("status")) {
                String s = fields.get(key);
                if (s.equals("DISCONTINUED"))
                    status = Status.DISCONTINUED;
                else if (s.equals("ONLINE"))
                    status = Status.ONLINE;
                else if (s.equals("OFFLINE"))
                    status = Status.OFFLINE;
            }
        }
    }
    @Override
    protected String getData(String id) {
        if(id.equals("uid"))
            return String.valueOf(uid);
        else if(id.equals("username"))
            return username;
        else if(id.equals("name"))
            return name;
        else if(id.equals("password"))
            return password;
        else if(id.equals("about"))
            return about;
        else if(id.equals("contact"))
            return contact;
        else if(id.equals("rating"))
            return String.valueOf(rating);
        else if(id.equals("rates"))
            return String.valueOf(rates);
        else if(id.equals("status")) {
            if (status == Status.DISCONTINUED)
                return "DISCONTINUED";
            else if (status == Status.OFFLINE)
                return "OFFLINE";
            else return "ONLINE";
        }
        else throw new RuntimeException("No such data field " + id + " for provider.");
    }
    @Override
    protected Set<String> unmodifiableFields(){ return unmodifiable; }
    @Override
    protected Set<String> modifiableFields() { return modifiable; }

    public int getRating() { return rating; }
    public int getRates() { return rates; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getUid() {
        return uid;
    }
    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    private void setupModifiable(){
        modifiable = new TreeSet<>();
        modifiable.add("name");
        modifiable.add("username");
        modifiable.add("password");
        modifiable.add("about");
        modifiable.add("status");
        modifiable.add("contact");
    }
    private void setupUnmodifiable() {
        unmodifiable = new TreeSet<>();
        unmodifiable.add("uid");
        unmodifiable.add("rating");
        unmodifiable.add("rates");
    }
    private void clear() {
        //unset all info
        name="";
        username="";
        password="";
        uid=0;
        about="";
        status=Status.DISCONTINUED;
        String contact="";
        rates = 0;
        rating = 0;
    }

}
