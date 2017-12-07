package com.steubercreative.www.playover;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by stste on 12/7/2017.
 */

public class ProviderFeedbackArray extends AbstractDBArray<Feedback> {
    private List<Feedback> feedback;
    private int providerUid;

    public ProviderFeedbackArray(int providerUid) {
        super();
        this.providerUid = providerUid;
        feedback = new ArrayList<>();
    }

    @Override
    public void retrieve(Context context) {
        Bundle b = new Bundle();
        b.putString("uid", String.valueOf(providerUid));
        retrieveHelper(context, b, "feedbacks", QueryMapper.ACTION_FETCH_PROVIDER_FEEDBACK);
    }

    @Override
    protected void addObject(Map<String, String> fields) {
        feedback.add(new Feedback(fields));
    }

    @Override
    public List<Feedback> getObjects() {
        return feedback;
    }
}
