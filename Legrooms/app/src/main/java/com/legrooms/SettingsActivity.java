package com.legrooms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.legrooms.Utility.SPUser;
import com.legrooms.Views.Button;
import com.legrooms.Views.TextViewPlus;

public class SettingsActivity extends AppCompatActivity {

    private ImageView imBack;
    private TextViewPlus tvAccountDetails, tvInviteFriends, tvHelp;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initComponent();
        initListener();
    }

    private void initListener() {
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvAccountDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,SignUpActivity.class));
                finish();
            }
        });

        tvInviteFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingsActivity.this,HelpActivity.class));
                finish();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUser.clear(SettingsActivity.this);
                startActivity(new Intent(SettingsActivity.this,LandingActivity.class));
                finish();
            }
        });

    }


    private void initComponent() {
        imBack = (ImageView) findViewById(R.id.im_back);
        tvAccountDetails = (TextViewPlus) findViewById(R.id.tv_account_detail);
        tvInviteFriends = (TextViewPlus) findViewById(R.id.tv_invite_friends);
        tvHelp = (TextViewPlus) findViewById(R.id.tv_help);
        btnLogout = (Button) findViewById(R.id.btn_logout);
    }
}
