package com.legrooms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.legrooms.Utility.SPUser;


public class LandingActivity extends AppCompatActivity {

    private TextView btnLogin, btnSignUp;
    private TextView tvTryAppNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        if (SPUser.getBooleanValue(LandingActivity.this, SPUser.IS_LOGIN)) {
            setContentView(R.layout.activity_blank);
            startActivity(new Intent(LandingActivity.this, HomeActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_landing);
            initComponent();
            initListener();

        }


    }

    private void initComponent() {
        btnLogin = (TextView) findViewById(R.id.tv_login);
        btnSignUp = (TextView) findViewById(R.id.tv_signup);
        tvTryAppNow = (TextView) findViewById(R.id.tv_try_app_now);

    }

    private void initListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SPUser.getBooleanValue(LandingActivity.this, SPUser.IS_LOGIN)) {
                    startActivity(new Intent(LandingActivity.this, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(LandingActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SPUser.getBooleanValue(LandingActivity.this, SPUser.IS_LOGIN)) {
                    startActivity(new Intent(LandingActivity.this, SignUpActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(LandingActivity.this, HomeActivity.class));
                    finish();

                }

            }
        });
        tvTryAppNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingActivity.this, HomeActivity.class));
                finish();

            }
        });
    }
}
