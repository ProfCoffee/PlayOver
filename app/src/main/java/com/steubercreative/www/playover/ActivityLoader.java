package com.steubercreative.www.playover;

import android.content.Context;
import android.content.Intent;
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

    public void PopulateLayout (ViewGroup layout, int feedbackId, int count) {
        layout.removeAllViews();

        for (int i = 0; i < count; i++) {
            AddRow(layout, i + " Pandas eating bamboo", "Jortdan Calsean the " + i + "rd", 17 + i, 5);
        }
    }

    private void AddRow(ViewGroup layout, final String activityName, String providerName, float cost, int rating){
        View activityView = LayoutInflater.from(_context).inflate(R.layout.activity_item, layout, false);

        TextView activityNameTextView = activityView.findViewById(R.id.ActivityName);
        TextView activityProviderNameTextView = activityView.findViewById(R.id.ProviderName);
        TextView activityCostTextView = activityView.findViewById(R.id.Cost);

        activityNameTextView.setText(activityName);
        activityProviderNameTextView.setText(providerName);
        activityCostTextView.setText("$"+cost);

        Button viewButton = activityView.findViewById(R.id.view_button);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, ActivityPageActivity.class);

                intent.putExtra("ActivityName", activityName);

                _context.startActivity(intent);
            }
        });

        TextView providerLink = activityView.findViewById(R.id.ProviderName);
        providerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, ProviderPageActivity.class);

                intent.putExtra("providerID", 12312);

                _context.startActivity(intent);
            }
        });

        LinearLayout ratingStars_layout = activityView.findViewById(R.id.stars);
        addStars(ratingStars_layout, rating);

        layout.addView(activityView);
    }
}
