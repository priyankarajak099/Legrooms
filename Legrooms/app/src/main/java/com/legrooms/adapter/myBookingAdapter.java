package com.legrooms.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.legrooms.LocationDetailsActivity;
import com.legrooms.R;
import com.legrooms.Views.TextViewPlus;
import com.legrooms.model.ListiingPojo;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;


public class myBookingAdapter extends BaseAdapter {

    Context context;
    private Activity activity;
    private LayoutInflater layoutInflator;
    private ArrayList<ListiingPojo> alSpaces;
    public myBookingAdapter(Activity activity1, ArrayList<ListiingPojo> alSpaces) {
        super();
        this.context = context;
        this.activity = activity1;

        this.alSpaces=alSpaces;
    }

    @Override
    public int getCount() {
        return alSpaces.size();
    }

    @Override
    public String getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;


        if (convertView == null) {
            layoutInflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflator.inflate(R.layout.row_club_name, null);
            holder = new Holder();

            holder.tvPlaceName = (TextViewPlus) convertView.findViewById(R.id.tv_row_user_name);
            holder.tvAddress = (TextViewPlus) convertView.findViewById(R.id.tv_row_address);
            holder.tvLocation = (TextViewPlus) convertView.findViewById(R.id.tv_row_user_location);
            holder.imUser = (ImageView) convertView.findViewById(R.id.im_center_image);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.tvPrices = (TextViewPlus) convertView.findViewById(R.id.tv_row_price);
            holder.tvMap = (TextViewPlus) convertView.findViewById(R.id.tv_maps);
            holder.tvFilters = (TextViewPlus) convertView.findViewById(R.id.tv_filter);


        } else {
            holder = (Holder) convertView.getTag();
        }
        convertView.setTag(holder);

        holder.tvPlaceName.setText(alSpaces.get(position).getLTitle());
        holder.tvAddress.setText(alSpaces.get(position).getAddress());
        holder.tvLocation.setText(alSpaces.get(position).getCity()+","+alSpaces.get(position).getState()+","+alSpaces.get(position).getCountry());
        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        holder.tvPrices.setText(alSpaces.get(position).getPrice()+"");
        try {
            JSONArray jsonArrayImage=new JSONArray(alSpaces.get(position).getImages());
            Glide.with(activity).load(jsonArrayImage.optString(0)).placeholder(R.mipmap.img).dontAnimate().into(holder.imUser);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (alSpaces.get(position).getLatitude()!= 0.0 && (!(alSpaces.get(position).getLatitude() + "").equalsIgnoreCase("NaN")) && alSpaces.get(position).getLongitude() != 0.0 && (!(alSpaces.get(position).getLongitude()+ "").equalsIgnoreCase("NaN"))) {
          holder.tvMap.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                //  send to Map Screen
              }
          });
        }
      /*  convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, LocationDetailsActivity.class).putExtra("location_space", (Serializable) alSpaces.get(position)));

            }
        });*/


        return convertView;
    }

    public static class Holder {
       private TextViewPlus tvPlaceName,tvAddress,tvLocation;
        private ImageView imUser;
        private RatingBar ratingBar;
        private TextViewPlus tvPrices;
        public TextViewPlus tvMap,tvFilters;
    }
}