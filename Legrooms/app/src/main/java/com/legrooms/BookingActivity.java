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
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.legrooms.Utility.CustomHttpClient;
import com.legrooms.Utility.SPUser;
import com.legrooms.Utility.ShowDialog;
import com.legrooms.Utility.WebUrl;
import com.legrooms.Views.TextViewPlus;
import com.legrooms.adapter.ClubListAdapter;
import com.legrooms.adapter.myBookingAdapter;
import com.legrooms.model.ListiingPojo;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.legrooms.R.id.tv_profile;

public class BookingActivity extends AppCompatActivity {

    private ShowDialog showMessage;
    private ArrayList<ListiingPojo> alListing;
    private ListView lvSpaces;
    private myBookingAdapter bookingAdapter;
    private RadioGroup rgBooking;
    private View viewSignup, viewNoBooking, footer;
    private TextViewPlus tvSignup;
    private TextViewPlus tvSignupHaveAccount, tvStartExploring, tvFooterSearch, tvMessage, tvBooking, tvProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        initComponent();
        initListner();
        new GetBookingListAsyncTask().execute();
    }

    private void initListner() {
        tvSignupHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookingActivity.this, LoginActivity.class));
                finish();
            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookingActivity.this, SignUpActivity.class));
                finish();
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookingActivity.this, SignUpActivity.class));
                finish();
            }
        });
        tvStartExploring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookingActivity.this, HomeActivity.class));
                finish();
            }
        });
        tvFooterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvFooterSearch.setSelected(true);
                startActivity(new Intent(BookingActivity.this, HomeActivity.class));
                finish();
            }
        });
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMessage.setSelected(true);
                startActivity(new Intent(BookingActivity.this, MessageActivity.class));
                finish();
            }
        });
        tvBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBooking.setSelected(true);
                startActivity(new Intent(BookingActivity.this, BookingActivity.class));
                finish();
            }
        });
        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvProfile.setSelected(true);
                startActivity(new Intent(BookingActivity.this, ProfileScreen.class));
                finish();
            }
        });


    }

    private void initComponent() {
        alListing = new ArrayList<ListiingPojo>();
        rgBooking = (RadioGroup) findViewById(R.id.rg_booking);
        lvSpaces = (ListView) findViewById(R.id.lv_clubs);
        viewSignup = (View) findViewById(R.id.view_signup);
        tvSignup = (TextViewPlus) findViewById(R.id.tv_signup);
        tvSignupHaveAccount = (TextViewPlus) findViewById(R.id.tv_signup_have_account);
        viewNoBooking = (View) findViewById(R.id.view_no_booking);
        tvStartExploring = (TextViewPlus) findViewById(R.id.tv_start_exploring);
        footer = (View) findViewById(R.id.footer);
        tvFooterSearch = (TextViewPlus) findViewById(R.id.tv_footer_search);
        tvMessage = (TextViewPlus) findViewById(R.id.tv_message);
        tvBooking = (TextViewPlus) findViewById(R.id.tv_booking);
        tvProfile = (TextViewPlus) findViewById(R.id.tv_profile);
        tvBooking.setSelected(true);

        if (!SPUser.getBooleanValue(BookingActivity.this, SPUser.IS_LOGIN)) {
            rgBooking.setVisibility(View.GONE);
            lvSpaces.setVisibility(View.GONE);
            viewNoBooking.setVisibility(View.GONE);
            viewSignup.setVisibility(View.VISIBLE);
        } else {
            rgBooking.setVisibility(View.VISIBLE);

        }
    }


    class GetBookingListAsyncTask extends AsyncTask<Void, Void, Void> {
        private String response;
        private ProgressDialog progressDialog;
        private JSONObject jsObject, jsonRequest;
        private ArrayList<NameValuePair> urlParameter;
        private JSONObject jsonObject;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            progressDialog = new ProgressDialog(BookingActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            response = CustomHttpClient.executeHttpGet(BookingActivity.this, WebUrl.ADD_BOOKING + "/email/" + SPUser.getValue(BookingActivity.this, SPUser.USER_EMAIL));
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

                        JSONArray jsonArray = jsonObject.optJSONArray("bookings");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.optJSONObject(i);

                            new GetBookingListDetailAsyncTask(jObject).execute();

                        }
                        if (jsonArray.length() > 0) {
                            lvSpaces.setVisibility(View.VISIBLE);
                            viewNoBooking.setVisibility(View.GONE);
                            viewSignup.setVisibility(View.GONE);
                        } else {
                            lvSpaces.setVisibility(View.GONE);
                            viewNoBooking.setVisibility(View.VISIBLE);
                            viewSignup.setVisibility(View.GONE);
                        }


                    } else {
                        showMessage.showMsg(BookingActivity.this, jsonObject.optString("message"));
                    }

                } else {
                    Toast.makeText(BookingActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    class GetBookingListDetailAsyncTask extends AsyncTask<Void, Void, Void> {
        private final JSONObject jObjectList;
        private String response;
        private ProgressDialog progressDialog;
        private JSONObject jsObject, jsonRequest;
        private ArrayList<NameValuePair> urlParameter;
        private JSONObject jsonObject;

        public GetBookingListDetailAsyncTask(JSONObject jObjectList) {
            this.jObjectList = jObjectList;
        }


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            progressDialog = new ProgressDialog(BookingActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            response = CustomHttpClient.executeHttpGet(BookingActivity.this, WebUrl.SEARCH_LISTING + "/id/" + jObjectList.optString("listId"));
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

                            JSONObject jObject = jsonObject.optJSONObject("listing");

                            ListiingPojo listiingPojo = new ListiingPojo();
                            listiingPojo.setId(jObject.optString("_id"));
                            listiingPojo.setActivity(jObject.optString("activity"));
                            listiingPojo.setAttendees(jObject.optInt("attendees"));
                            listiingPojo.setHours(jObject.optInt("hours"));
                            listiingPojo.setPrice(jObject.optInt("price"));
                            listiingPojo.setLRules(jObject.optString("lRules"));
                            listiingPojo.setDesc(jObject.optString("desc"));

                            listiingPojo.setLTitle(jObject.optString("lTitle"));
                            listiingPojo.setAddress(jObject.optString("address"));
                            listiingPojo.setNeighborhood(jObject.optString("neighborhood"));
                            listiingPojo.setCity(jObject.optString("city"));
                            listiingPojo.setState(jObject.optString("state"));
                            listiingPojo.setCountry(jObject.optString("country"));
                            listiingPojo.setEmail(jObject.optString("email"));
                            listiingPojo.setV(jObject.optInt("__v"));
                            listiingPojo.setAmenities(jObject.optString("amenities"));
                            listiingPojo.setAvailability(jObject.optString("availability"));
                            listiingPojo.setImages(jObject.optString("images"));
                            listiingPojo.setLongitude(jObject.optDouble("longitude"));
                            listiingPojo.setLatitude(jObject.optDouble("latitude"));
                            alListing.add(listiingPojo);



                        lvSpaces.setVisibility(View.VISIBLE);
                        bookingAdapter = new myBookingAdapter(BookingActivity.this, alListing);
                        lvSpaces.setAdapter(bookingAdapter);
                      //  bookingAdapter.notifyDataSetChanged();


                    } else {
                        showMessage.showMsg(BookingActivity.this, jsonObject.optString("message"));
                    }

                } else {
                    Toast.makeText(BookingActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

}
