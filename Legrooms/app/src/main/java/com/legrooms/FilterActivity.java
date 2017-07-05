package com.legrooms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.legrooms.Utility.SPUser;
import com.legrooms.Views.Button;
import com.legrooms.Views.EditText;
import com.legrooms.Views.TextViewPlus;

public class FilterActivity extends AppCompatActivity {

    private EditText etLocation;
    private TextViewPlus tvCancel, tvClearAll;
    private Button btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initComponent();
        initListener();
    }

    private void initListener() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etLocation.setText("");
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etLocation.getText().toString().length()!=0){
                    SPUser.setValue(FilterActivity.this,SPUser.FILTER_LOCATION,etLocation.getText().toString());
                }
                startActivity(new Intent(FilterActivity.this, HomeActivity.class));
                finish();
            }
        });

    }


    private void initComponent() {
        tvCancel = (TextViewPlus) findViewById(R.id.tv_cancel);
        tvClearAll = (TextViewPlus) findViewById(R.id.tv_clearall);
        etLocation = (EditText) findViewById(R.id.et_location);
        btnApply = (Button) findViewById(R.id.btn_apply);
        if(!SPUser.getValue(FilterActivity.this,SPUser.FILTER_LOCATION).equalsIgnoreCase("")){
            etLocation.setText(SPUser.getValue(FilterActivity.this,SPUser.FILTER_LOCATION));
        }
    }
}
