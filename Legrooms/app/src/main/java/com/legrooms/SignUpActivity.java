package com.legrooms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.legrooms.Utility.CustomHttpClient;
import com.legrooms.Utility.EmailValidator;
import com.legrooms.Utility.NetworkConnection;
import com.legrooms.Utility.SPUser;
import com.legrooms.Utility.ShowDialog;
import com.legrooms.Utility.WebUrl;
import com.legrooms.Views.EditText;
import com.legrooms.Views.TextViewPlus;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignUpActivity extends Activity {

    private EditText etEmail;
    private EditText etPassword;
    private TextViewPlus tvSignin;
    private TextViewPlus tvForgotPassword;
    private NetworkConnection networkConnection;
    private ShowDialog showMessage;
    private TextViewPlus tvProfile;
    private TextViewPlus tvNext;
    private CheckBox cbTermsConditions;
    private ImageView imBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initComponets();
        initListners();
    }

    private void initListners() {
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cbTermsConditions.isChecked()) {
                    if (isValidAllFields()) {
                        startActivity(new Intent(new Intent(SignUpActivity.this, SignupTwoActivity.class).putExtra("email", etEmail.getText().toString() + "").putExtra("password", etPassword.getText().toString() + "")));
                        finish();

                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Please select terms and conditions", Toast.LENGTH_SHORT).show();
                }
            }
        });
       /* tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (networkConnection.isOnline(getApplicationContext())) {
                    if (isValidAllFields()) {
                        new LoginAsyncTask().execute();
                    }
                } else {
                    Toast.makeText(getBaseContext(),
                            "Please check your network connection.", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });*/
       /* tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvProfile.setSelected(true);
            }
        });*/
    }

    private void initComponets() {
        networkConnection = new NetworkConnection();
        showMessage = new ShowDialog();
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        // tvSignin=(TextViewPlus)findViewById(R.id.tv_signin);
        // tvProfile=(TextViewPlus)findViewById(R.id.tv_profile);
        imBack = (ImageView) findViewById(R.id.im_back);
        tvNext = (TextViewPlus) findViewById(R.id.tv_header_next);
        cbTermsConditions = (CheckBox) findViewById(R.id.cb_terms_and_conditions);
        etEmail.setText(SPUser.getValue(SignUpActivity.this, SPUser.USER_EMAIL));

    }

    public boolean isValidAllFields() {

        boolean flag = true;

        if (etEmail.getText().toString().length() == 0) {
            showMessage.showMsg(SignUpActivity.this, "Email is required.");
            flag = false;
        } else if (!EmailValidator.isValid(etEmail.getText().toString())) {
            showMessage.showMsg(SignUpActivity.this, "Vaild Email is required.");
            flag = false;
        } else if (etPassword.getText().toString().length() == 0) {
            showMessage.showMsg(SignUpActivity.this, "Password is required.");
            flag = false;
        }
        return flag;
    }

    class LoginAsyncTask extends AsyncTask<Void, Void, Void> {
        private String response;
        private ProgressDialog progressDialog;
        private JSONObject jsObject, jsonRequest;
        private ArrayList<NameValuePair> urlParameter;
        private JSONObject jsonObject;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();


            jsonObject = new JSONObject();
            try {
                jsonObject.put("email", etEmail.getText().toString() + "");
                jsonObject.put("password", etPassword.getText().toString() + "");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            response = CustomHttpClient.executeHttpPostRawData(SignUpActivity.this, WebUrl.REGISTER, jsonObject + "");
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

                        SPUser.setBooleanValue(SignUpActivity.this, SPUser.IS_LOGIN, true);

                        SPUser.setValue(SignUpActivity.this, SPUser.USER_EMAIL, etEmail.getText().toString());
                        SPUser.setValue(SignUpActivity.this, SPUser.USER_TOKEN, jsonObject.optString("token"));
                        startActivity(new Intent(SignUpActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    } else {
                        showMessage.showMsg(SignUpActivity.this, jsonObject.optString("message"));
                    }

                } else {
                    Toast.makeText(SignUpActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

}
