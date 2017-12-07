package com.steubercreative.www.playover;

import android.content.Context;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Sean on 12/6/2017.
 */

public class Airport extends AbstractModifiableDBObject {
    private int uid;
    private String code;
    private String airport;
    private Set<String> modifiable;
    private Set<String> unmodifiable;

    public Airport() {
        super(true);
        modifiable = new TreeSet<>();
        unmodifiable = new TreeSet<>();
        unmodifiable.add("uid");
        unmodifiable.add("code");
        unmodifiable.add("airport");
    }
    public Airport(Map<String,String> fields) {
        this();
        populate(fields);
    }
    public String getCode() { return code; }
    public String getAirport() { return airport; }

    @Override
    protected Set<String> modifiableFields() { return modifiable; }
    @Override
    protected Set<String> unmodifiableFields() { return unmodifiable; }
    @Override
    public void retrieve(Context context) {}
    @Override
    public void save(Context context) {}
    @Override
    public void create(Context context) {}
    @Override
    public void delete(Context context) {}

    @Override
    protected String getData(String name) {
        if(name.equals("uid"))
            return String.valueOf(uid);
        else if(name.equals("code"))
            return code;
        else if(name.equals("airports"))
            return airport;
        else throw new RuntimeException("No such field " + name + " in airport");
    }

    @Override
    protected void populate(Map<String, String> fields) {
        for(String key : fields.keySet()) {
            if(key.equals("uid"))
                uid = Integer.valueOf(fields.get(key));
            else if(key.equals("code"))
                code = fields.get(key);
            else if(key.equals("airport"))
                airport = fields.get(key);
            else throw new RuntimeException("No such field " + key + " in airport");
        }
    }
}
