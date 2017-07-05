package com.legrooms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.legrooms.Utility.CustomHttpClient;
import com.legrooms.Utility.SPUser;
import com.legrooms.Utility.ShowDialog;
import com.legrooms.Utility.WebUrl;
import com.legrooms.Views.TextViewPlus;
import org.apache.http.NameValuePair;
import org.json.JSONObject;
import java.util.ArrayList;

public class ProfileScreen extends AppCompatActivity {

    private TextViewPlus tvStartExploring, tvFooterSearch, tvMessage, tvBooking, tvProfile;
    private TextViewPlus tvName;
    private ShowDialog showMessage;
    private ImageView imSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        initComponent();
        initListner();

    }

    private void initListner() {


        tvStartExploring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileScreen.this, HomeActivity.class));
                finish();
            }
        });
        tvFooterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvFooterSearch.setSelected(true);
                startActivity(new Intent(ProfileScreen.this, HomeActivity.class));
                finish();
            }
        });
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMessage.setSelected(true);
                startActivity(new Intent(ProfileScreen.this, MessageActivity.class));
                finish();
            }
        });
        tvBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBooking.setSelected(true);
                startActivity(new Intent(ProfileScreen.this, BookingActivity.class));
                finish();
            }
        });
        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvProfile.setSelected(true);
            }
        });
        imSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileScreen.this, SettingsActivity.class));
                finish();
            }
        });

    }

    private void initComponent() {
        showMessage = new ShowDialog();
        tvName= (TextViewPlus) findViewById(R.id.tv_name);
        tvStartExploring = (TextViewPlus) findViewById(R.id.tv_start_exploring);
        tvFooterSearch = (TextViewPlus) findViewById(R.id.tv_footer_search);
        tvMessage = (TextViewPlus) findViewById(R.id.tv_message);
        tvBooking = (TextViewPlus) findViewById(R.id.tv_booking);
        tvProfile = (TextViewPlus) findViewById(R.id.tv_profile);
        imSetting = (ImageView) findViewById(R.id.im_setting);
        tvProfile.setSelected(true);

        if (!SPUser.getBooleanValue(ProfileScreen.this, SPUser.IS_LOGIN)) {
            tvName.setText("Guest");

        } else {
           new GetOwnerInfoAsyncTask().execute();
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

            progressDialog = new ProgressDialog(ProfileScreen.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            response = CustomHttpClient.executeHttpGet(ProfileScreen.this, WebUrl.GET_USER_INFO + "/" + SPUser.getValue(ProfileScreen.this,SPUser.USER_EMAIL));
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

                        tvName.setText(jsonObject.optString("fname")+" "+jsonObject.optString("lname"));

                    } else {
                        showMessage.showMsg(ProfileScreen.this, jsonObject.optString("message"));
                    }

                } else {
                    Toast.makeText(ProfileScreen.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}
