package com.steubercreative.www.playover;

import android.content.Context;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Sean on 12/6/2017.
 */

public class Activity extends AbstractModifiableDBObject {
    private int uid;
    private String name;
    private String description;
    private String airport;
    private String location;
    private int providerUid;
    private float cost;
    private int rating;
    private int rates;
    private int duration;
    private Status status;
    private Set<String> unmodifiable;
    private Set<String> modifiable;

    public Activity() { this(false); }
    public Activity(Map<String, String> fields){ this(fields, false); }
    public Activity(Map<String, String> fields, boolean readOnly) {
        super(readOnly);
        setupModifiable();
        setupUnmodifiable();
        clear();
        populate(fields);
    }
    public Activity(boolean readOnly) {
        super(readOnly);
        setupModifiable();
        setupUnmodifiable();
        clear();
    }

    @Override
    protected Set<String> unmodifiableFields() { return unmodifiable; }
    @Override
    protected Set<String> modifiableFields() { return modifiable; }
    @Override
    protected void populate(Map<String, String> fields) {
        for(String key : fields.keySet()) {
            if(key.equals("uid"))
                uid = Integer.valueOf(fields.get(key));
            else if(key.equals("provider"))
                providerUid = Integer.valueOf(fields.get(key));
            else if(key.equals("name"))
                name = fields.get(key);
            else if(key.equals("description"))
                description = fields.get(key);
            else if(key.equals("location"))
                location = fields.get(key);
            else if(key.equals("contact"))
                airport = fields.get(key);
            else if(key.equals("rating"))
                rating = Integer.valueOf(fields.get(key));
            else if(key.equals("rates"))
                rates = Integer.valueOf(fields.get(key));
            else if(key.equals("duration"))
                duration = Integer.valueOf(fields.get(key));
            else if(key.equals("cost"))
                cost = Float.valueOf(fields.get(key));
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
        else if(id.equals("provider"))
            return String.valueOf(providerUid);
        else if(id.equals("location"))
            return location;
        else if(id.equals("name"))
            return name;
        else if(id.equals("description"))
            return description;
        else if(id.equals("airport"))
            return airport;
        else if(id.equals("cost"))
            return String.valueOf(cost);
        else if(id.equals("duration"))
            return String.valueOf(duration);
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
    public void save(Context context) {
        saveHelper(context, QueryMapper.ACTION_UPDATE_ACTIVITY_INFO);
    }
    @Override
    public void retrieve(Context context) {
        retrieveHelper(context, QueryMapper.ACTION_FETCH_ACTIVITY_INFO);
    }
    @Override
    public void delete(Context context) {
        setStatus(Status.DISCONTINUED);
        save(context);
    }
    @Override
    public void create(Context context) {
        createHelper(context, QueryMapper.ACTION_CREATE_ACTIVITY);
    }

    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAirport() {
        return airport;
    }
    public void setAirport(String airport) {
        this.airport = airport;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getProviderUid() {
        return providerUid;
    }
    public void setProviderUid(int providerUid) {
        this.providerUid = providerUid;
    }
    public float getCost() {
        return cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getRates() {
        return rates;
    }
    public void setRates(int rates) {
        this.rates = rates;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }


    private void setupModifiable() {
        modifiable = new TreeSet<>();
        modifiable.add("name");
        modifiable.add("description");
        modifiable.add("airport");
        modifiable.add("location");
        modifiable.add("cost");
        modifiable.add("status");
        modifiable.add("duration");
    }
    private void setupUnmodifiable() {
        unmodifiable = new TreeSet<>();
        unmodifiable.add("uid");
        unmodifiable.add("rates");
        unmodifiable.add("rating");
        unmodifiable.add("provider");
    }
    private void clear() {
        uid = 0;
        name = "";
        description = "";
        airport = "";
        providerUid = 0;
        cost = 0;
        rating = 0;
        rates = 0;
        duration = 0;
        location = "";
        status = Status.DISCONTINUED;
    }
}
