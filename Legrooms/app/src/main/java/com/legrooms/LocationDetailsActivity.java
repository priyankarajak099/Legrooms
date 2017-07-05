package com.legrooms;

import android.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.legrooms.Utility.CustomHttpClient;
import com.legrooms.Utility.ShowDialog;
import com.legrooms.Utility.WebUrl;
import com.legrooms.Views.TextViewPlus;
import com.legrooms.adapter.ClubListAdapter;
import com.legrooms.model.ListiingPojo;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class LocationDetailsActivity extends AppCompatActivity {

    private ImageView imBack, imLocation, imOwner, imFavourite, imShare;
    private TextViewPlus tvHeader, tvRate, tvOwnerName, tvOwnerEmail, tvOwnerAbout, tvUseType, tvAttendes, tvAvailibity, tvNeighbourHood, tvReviews, tvDescription, tvAmenities, tvRules, tvRequestBooking;
    private ListiingPojo locationSpace;
    private ShowDialog showMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        initComponents();
        initListener();
        new GetOwnerInfoAsyncTask().execute();
    }

    private void initListener() {
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvRequestBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LocationDetailsActivity.this,AddBookingActivity.class).putExtra("location_space", (Serializable) locationSpace));
            }
        });
    }

    private void initComponents() {
        showMessage = new ShowDialog();

        locationSpace = (ListiingPojo) getIntent().getSerializableExtra("location_space");
        imBack = (ImageView) findViewById(R.id.im_back);
        imLocation = (ImageView) findViewById(R.id.im_center_image);
        imOwner = (ImageView) findViewById(R.id.im_owner);
        imFavourite = (ImageView) findViewById(R.id.im_favourite);
        imShare = (ImageView) findViewById(R.id.im_share);
        tvHeader = (TextViewPlus) findViewById(R.id.tv_header_text);
        tvRate = (TextViewPlus) findViewById(R.id.tv_rate);
        tvOwnerName = (TextViewPlus) findViewById(R.id.tv_owner_name);
        tvOwnerEmail = (TextViewPlus) findViewById(R.id.tv_owner_email);
        tvOwnerAbout = (TextViewPlus) findViewById(R.id.tv_owner_about);
        tvUseType = (TextViewPlus) findViewById(R.id.tv_usetype_value);
        tvAttendes = (TextViewPlus) findViewById(R.id.tv_attendes_value);
        tvAvailibity = (TextViewPlus) findViewById(R.id.tv_availability_value);
        tvNeighbourHood = (TextViewPlus) findViewById(R.id.tv_neighbourhood_value);
        tvReviews = (TextViewPlus) findViewById(R.id.tv_reviews_value);
        tvDescription = (TextViewPlus) findViewById(R.id.tv_desc_value);
        tvAmenities = (TextViewPlus) findViewById(R.id.tv_amenities_value);
        tvRules = (TextViewPlus) findViewById(R.id.tv_rules_value);
        tvRequestBooking = (TextViewPlus) findViewById(R.id.tv_request_booking);

        tvHeader.setText(locationSpace.getLTitle());
        tvRate.setText(locationSpace.getPrice() + "");
        tvUseType.setText("Production");
        tvAttendes.setText(locationSpace.getAttendees() + " attendees");
        tvNeighbourHood.setText(locationSpace.getNeighborhood());
        tvReviews.setText("No Reviews");
        tvDescription.setText(locationSpace.getDesc());

        tvRules.setText(locationSpace.getLRules() + "");
        try {
            JSONArray jsonArrayImage = new JSONArray(locationSpace.getImages());
            Glide.with(LocationDetailsActivity.this).load(jsonArrayImage.optString(0)).placeholder(R.mipmap.img).dontAnimate().into(imLocation);

            JSONArray jsonArrayAmmenities = new JSONArray(locationSpace.getAmenities());
            StringBuffer ammenities = new StringBuffer("");
            for (int i = 0; i < jsonArrayAmmenities.length(); i++) {
                ammenities.append(jsonArrayAmmenities.optString(i) + "\n");
            }
            tvAmenities.setText(ammenities.substring(0,ammenities.length()-1) + "");

            JSONArray jsonArrayAvailibity= new JSONArray(locationSpace.getAvailability());
            StringBuffer availibity = new StringBuffer("");
            for (int j = 0; j < jsonArrayAvailibity.length(); j++) {
                availibity.append(jsonArrayAvailibity.optString(j) + "\n");
            }
            tvAvailibity.setText(availibity.substring(0,availibity.length()-1) + "");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    class GetOwnerInfoAsyncTask extends AsyncTask<Void, Void, Void> {
        private String response;
        private ProgressDialog progressDialog;
        private JSONObject jsObject, jsonRequest;
        private ArrayList<NameValuePair> urlParameter;
        private JSONObject jsonObject;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            progressDialog = new ProgressDialog(LocationDetailsActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            response = CustomHttpClient.executeHttpGet(LocationDetailsActivity.this, WebUrl.GET_USER_INFO + "/" + locationSpace.getEmail());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();

            try {
                if (response != null) {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optBoolean("success")) {

                        tvOwnerEmail.setText(jsonObject.optString("email"));
                        tvOwnerName.setText(jsonObject.optString("fname")+" "+jsonObject.optString("lname"));

                    } else {
                        showMessage.showMsg(LocationDetailsActivity.this, jsonObject.optString("message"));
                    }

                } else {
                    Toast.makeText(LocationDetailsActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}
