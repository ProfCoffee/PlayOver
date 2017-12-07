package com.steubercreative.www.playover;

import android.content.Context;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Sean on 12/7/2017.
 */

public class Feedback extends AbstractModifiableDBObject {
    private int uid;
    private int author;
    private int provider;
    private int activity;
    private String content;
    private int rating;
    private String date;
    private Set<String> modifiable;
    private Set<String> unmodifiable;

    public Feedback() { this(false); }
    public Feedback(Map<String, String> fields){ this(fields, false); }
    public Feedback(Map<String, String> fields, boolean readOnly) {
        super(readOnly);
        setupModifiable();
        setupUnmodifiable();
        clear();
        populate(fields);
    }
    public Feedback(boolean readOnly) {
        super(readOnly);
        setupModifiable();
        setupUnmodifiable();
        clear();
    }

    @Override
    protected Set<String> unmodifiableFields() {
        return unmodifiable;
    }
    @Override
    protected Set<String> modifiableFields() {
        return modifiable;
    }
    @Override
    protected void populate(Map<String, String> fields) {
        for(String key : fields.keySet()) {
            if(key.equals("uid"))
                uid = Integer.valueOf(fields.get(key));
            else if(key.equals("author"))
                author = Integer.valueOf(fields.get(key));
            else if(key.equals("provider"))
                provider = Integer.valueOf(fields.get(key));
            else if(key.equals("activity"))
                activity = Integer.valueOf(fields.get(key));
            else if(key.equals("rating"))
                rating = Integer.valueOf(fields.get(key));
            else if(key.equals("content"))
                content = fields.get(key);
            else if(key.equals("date"))
                date = fields.get(key);
        }
    }
    @Override
    protected String getData(String name) {
        if(name.equals("uid"))
            return String.valueOf(uid);
        else if(name.equals("author"))
            return String.valueOf(author);
        else if(name.equals("provider"))
            return String.valueOf(provider);
        else if(name.equals("activity"))
            return String.valueOf(activity);
        else if(name.equals("rating"))
            return String.valueOf(rating);
        else if(name.equals("content"))
            return content;
        else if(name.equals("date"))
            return date;
        else throw new RuntimeException("No such field " + name + " in feedback.");
    }

    @Override
    public void retrieve(Context context) {
        if(activity != 0)
            retrieveHelper(context, QueryMapper.ACTION_FETCH_ACTIVITY_FEEDBACK);
        else retrieveHelper(context, QueryMapper.ACTION_FETCH_PROVIDER_FEEDBACK);
    }

    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public int getAuthor() {
        return author;
    }
    public void setAuthor(int author) {
        this.author = author;
    }
    public int getProvider() {
        return provider;
    }
    public void setProvider(int provider) {
        this.provider = provider;
    }
    public int getActivity() {
        return activity;
    }
    public void setActivity(int activity) {
        this.activity = activity;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    @Override
    public void create(Context context) {
        createHelper(context, QueryMapper.ACTION_CREATE_FEEDBACK);
    }

    @Override
    public void delete(Context context) {}

    @Override
    public void save(Context context) {}

    private void setupModifiable() {
        modifiable = new TreeSet<>();
        modifiable.add("content");
        modifiable.add("rating");
        modifiable.add("author");
        modifiable.add("activity");
        modifiable.add("provider");
        modifiable.add("date");
    }
    private void setupUnmodifiable() {
        unmodifiable = new TreeSet<>();
        unmodifiable.add("uid");
    }
    private void clear() {
        uid = 0;
        author = 0;
        content = "";
        rating = 0;
        provider = 0;
        activity = 0;
    }
}
