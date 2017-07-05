package com.legrooms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.legrooms.Utility.SPUser;
import com.legrooms.Utility.ShowDialog;
import com.legrooms.Views.TextViewPlus;
import com.legrooms.adapter.myBookingAdapter;
import com.legrooms.model.ListiingPojo;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    private ShowDialog showMessage;
    private ArrayList<ListiingPojo> alListing;
    private myBookingAdapter bookingAdapter;

    private View viewSignup, viewNoBooking, footer;
    private TextViewPlus tvSignup;
    private TextViewPlus tvSignupHaveAccount, tvStartExploring, tvFooterSearch, tvMessage, tvBooking, tvProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initComponent();
        initListner();
    }

    private void initListner() {
        tvSignupHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this, LoginActivity.class));
                finish();
            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this, SignUpActivity.class));
                finish();
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this, SignUpActivity.class));
                finish();
            }
        });
        tvStartExploring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessageActivity.this, HomeActivity.class));
                finish();
            }
        });
        tvFooterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvFooterSearch.setSelected(true);
                startActivity(new Intent(MessageActivity.this, HomeActivity.class));
                finish();
            }
        });
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMessage.setSelected(true);
            }
        });
        tvBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBooking.setSelected(true);
                startActivity(new Intent(MessageActivity.this, BookingActivity.class));
                finish();
            }
        });
        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvProfile.setSelected(true);
                startActivity(new Intent(MessageActivity.this, ProfileScreen.class));
                finish();
            }
        });
    }

    private void initComponent() {
        alListing = new ArrayList<ListiingPojo>();
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
        tvMessage.setSelected(true);

        if (!SPUser.getBooleanValue(MessageActivity.this, SPUser.IS_LOGIN)) {
            viewNoBooking.setVisibility(View.GONE);
            viewSignup.setVisibility(View.VISIBLE);
        } else{
            viewNoBooking.setVisibility(View.VISIBLE);
            viewSignup.setVisibility(View.GONE);
        }
    }




}
