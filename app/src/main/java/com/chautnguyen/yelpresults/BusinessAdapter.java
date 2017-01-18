package com.chautnguyen.yelpresults;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BusinessAdapter extends ArrayAdapter<YelpBusiness> {

    public BusinessAdapter(Context context, ArrayList<YelpBusiness> businesses) {
        super(context, 0, businesses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        YelpBusiness biz = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.yelp_result_card, parent, false);
        }

        TextView businessTitle = (TextView) convertView.findViewById(R.id.yelp_business_title);
        TextView reviewCount = (TextView) convertView.findViewById(R.id.yelp_review_count);
        TextView displayAddress = (TextView) convertView.findViewById(R.id.yelp_display_address);
        ImageView icon = (ImageView) convertView.findViewById(R.id.yelp_image_icon);
        ImageView rating = (ImageView) convertView.findViewById(R.id.yelp_rating_img_url);

        businessTitle.setText(biz.getName());
        reviewCount.setText(Integer.toString(biz.getReviewCount()) + " reviews");
        displayAddress.setText(biz.getDisplayAddress());

        new DownloadImageTask(icon).execute(biz.getImageUrl());
        new DownloadImageTask(rating).execute(biz.getRatingImageUrl());

        return convertView;
    }

}