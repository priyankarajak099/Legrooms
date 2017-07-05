package com.legrooms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.legrooms.Utility.SPUser;
import com.legrooms.Views.Button;
import com.legrooms.Views.EditText;
import com.legrooms.Views.TextViewPlus;

public class AddCardActivity extends AppCompatActivity {

    private ImageView imBack;
    private Button btnSubmit;
    private EditText etCardNumber;
    String a;
    int keyDel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
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


etCardNumber.addTextChangedListener(new TextWatcher() {
    private static final char space =' ';

    @Override
    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        boolean flag = true;
        String eachBlock[] = etCardNumber.getText().toString().split("-");
        for (int j = 0; j < eachBlock.length;j++) {
            if (eachBlock[j].length() > 4) {
                flag = false;
            }
        }
        if (flag) {

            etCardNumber.setOnKeyListener(new View.OnKeyListener() {


                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_DEL)
                        keyDel = 1;
                    return false;
                }
            });

            if (keyDel == 0) {

                if (((etCardNumber.getText().length() + 1) % 5) == 0) {

                    if (etCardNumber.getText().toString().split("-").length <= 3) {
                        etCardNumber.setText(etCardNumber.getText() + "-");
                        etCardNumber.setSelection(etCardNumber.getText().length());
                    }
                }
                a = etCardNumber.getText().toString();
            } else {
                a = etCardNumber.getText().toString();
                keyDel = 0;
            }

        } else {
            etCardNumber.setText(a);
        }


    }

    @Override
    public void afterTextChanged(Editable s) {
        
    }
});


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void initComponent() {
        imBack = (ImageView) findViewById(R.id.im_back);
        etCardNumber = (EditText) findViewById(R.id.et_card_number);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }
}
