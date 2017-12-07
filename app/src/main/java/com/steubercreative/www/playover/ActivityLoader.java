package com.steubercreative.www.playover;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by stste on 12/6/2017.
 */


public class ActivityLoader extends LayoutLoader{
    public ActivityLoader(Context context){
        super(context);
    }

    public void populateLayout(ViewGroup layout, String airport, int userUID, int count) {
        final int uidAlias = userUID;
        final ViewGroup layoutAlias = layout;
        layout.removeAllViews();
        Log.d("populate", airport);
        final ActivityViewArray activities = new ActivityViewArray(airport);
        activities.onCompletion(new Procedure() {
            @Override
            public void run() {
                for(Activity a : activities.getObjects()) {
                    addRow(layoutAlias, a, uidAlias);
                }
            }
        });
        activities.retrieve(this._context);
    }

    public void populateEditLayout(ViewGroup layout, int feedbackCode, int count, int uid) {
        layout.removeAllViews();
        final int uidAlias = uid;
        final ViewGroup layoutAlias = layout;
        layout.removeAllViews();
        final ActivityArray activities = new ActivityArray(uid);
        activities.onCompletion(new Procedure() {
            @Override
            public void run() {
                for(Activity a : activities.getObjects()) {
                    addEditRow(layoutAlias, a, uidAlias);
                }
            }
        });
        activities.retrieve(this._context);

    }


    private void addRow(ViewGroup layout, Activity activity, int userUID){
        final int userUIDAlias = userUID;
        View activityView = LayoutInflater.from(_context).inflate(R.layout.activity_item, layout, false);

        TextView activityNameTextView = activityView.findViewById(R.id.ActivityName);
        final TextView activityProviderNameTextView = activityView.findViewById(R.id.ProviderName);
        TextView activityCostTextView = activityView.findViewById(R.id.Cost);

        activityNameTextView.setText(activity.getName());

        final Provider provider = new Provider(true);
        provider.setUid(activity.getProviderUid());
        provider.onCompletion(new Procedure() {
            @Override
            public void run() {
                activityProviderNameTextView.setText(provider.getName());
            }
        });

        activityCostTextView.setText("$"+activity.getCost());

        Button viewButton = activityView.findViewById(R.id.view_button);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, ActivityPageActivity.class);

                intent.putExtra("uid", 1);
                intent.putExtra("userUID", userUIDAlias);

                _context.startActivity(intent);
            }
        });

        TextView providerLink = activityView.findViewById(R.id.ProviderName);
        providerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, ProviderPageActivity.class);

                intent.putExtra("uid", 1);

                _context.startActivity(intent);
            }
        });

        LinearLayout ratingStars_layout = activityView.findViewById(R.id.stars);
        addStars(ratingStars_layout, activity.getRating());

        layout.addView(activityView);
    }

    private void addEditRow(ViewGroup layout, final Activity activity,  int uid){
        View activityView = LayoutInflater.from(_context).inflate(R.layout.activity_item, layout, false);

        TextView activityNameTextView = activityView.findViewById(R.id.ActivityName);
        final TextView activityProviderNameTextView = activityView.findViewById(R.id.ProviderName);
        TextView activityCostTextView = activityView.findViewById(R.id.Cost);

        activityNameTextView.setText(activity.getName());

        final  Provider provider= new Provider(true);
        provider.setUid(uid);
        provider.onCompletion(new Procedure() {
            @Override
            public void run() {
                activityProviderNameTextView.setText(provider.getName());
            }
        });
        provider.retrieve(this._context);

        activityCostTextView.setText("$"+activity.getCost());

        Button viewButton = activityView.findViewById(R.id.view_button);
        viewButton.setText("Edit");
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, ProviderModifiyActivity.class);

                intent.putExtra("ActivityID", activity.getUid());

                _context.startActivity(intent);
            }
        });

        LinearLayout ratingStars_layout = activityView.findViewById(R.id.stars);
        addStars(ratingStars_layout, activity.getRating());

        layout.addView(activityView);
    }
}
