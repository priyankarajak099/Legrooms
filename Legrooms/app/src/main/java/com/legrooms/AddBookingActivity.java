package com.legrooms;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.legrooms.Utility.CustomHttpClient;
import com.legrooms.Utility.EmailValidator;
import com.legrooms.Utility.SPUser;
import com.legrooms.Utility.ShowDialog;
import com.legrooms.Utility.WebUrl;
import com.legrooms.Views.Button;
import com.legrooms.Views.EditText;
import com.legrooms.Views.TextViewPlus;
import com.legrooms.model.ListiingPojo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddBookingActivity extends AppCompatActivity {

    private ShowDialog showMessage;
    private ListiingPojo locationSpace;
    private ImageView imBack;
    private ImageView imLocation;
    private TextViewPlus tvHeader;
    private TextViewPlus tvRate;
    private TextViewPlus tvLocationName;
    private TextViewPlus tvDescription;
    private TextViewPlus tvSelectDate;
    private TextViewPlus tvSelectTime;
    private Spinner tvAttendes;
    private TextViewPlus tvAddCreditCard;
    private EditText etMessage;
    private EditText etAnySpecialRequest;
    private RadioGroup rgAlcohol;
    private RadioButton rbYes;
    private RadioButton rbNo;
    private Button tvBooking;
    private int mYear, mMonth, mDay;
    private int mHour, mMinute;
    private String isAlcohol;
    private String attendes;
    private boolean alcoholValue;
    private TextViewPlus tvSelectEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);
        initComponents();
        initListener();

    }

    private void initListener() {
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidAllFields()) {
                    new AddBookingAsyncTask().execute();
                }
            }
        });
        tvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddBookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvSelectDate.setText(WebUrl.convertIntoDateFormat(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        tvSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddBookingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                tvSelectTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        tvSelectEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddBookingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                tvSelectEnd.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        tvAttendes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                attendes = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rgAlcohol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                 @Override
                                                 public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                     RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                                                     isAlcohol = radioButton.getText().toString();
                                                     if (isAlcohol.equalsIgnoreCase("Yes")) {
                                                         alcoholValue = true;
                                                     } else {
                                                         alcoholValue = false;
                                                     }
                                                 }
                                             }
        );

        tvAddCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddBookingActivity.this,AddCardActivity.class));

            }
        });
    }

    private void initComponents() {

        showMessage = new ShowDialog();
        locationSpace = (ListiingPojo) getIntent().getSerializableExtra("location_space");
        imBack = (ImageView) findViewById(R.id.im_back);
        imLocation = (ImageView) findViewById(R.id.im_center_image);
        tvHeader = (TextViewPlus) findViewById(R.id.tv_header_text);
        tvRate = (TextViewPlus) findViewById(R.id.tv_rate);
        tvLocationName = (TextViewPlus) findViewById(R.id.tv_location_name);
        tvDescription = (TextViewPlus) findViewById(R.id.tv_description);
        tvSelectDate = (TextViewPlus) findViewById(R.id.tv_select_date);
        tvSelectTime = (TextViewPlus) findViewById(R.id.tv_select_time);
        tvSelectEnd = (TextViewPlus) findViewById(R.id.tv_select_end);
        tvAttendes = (Spinner) findViewById(R.id.tv_attendes_value);
        tvAddCreditCard = (TextViewPlus) findViewById(R.id.tv_add_credit_card);
        etMessage = (EditText) findViewById(R.id.et_message);
        etAnySpecialRequest = (EditText) findViewById(R.id.et_any_special_request);
        rgAlcohol = (RadioGroup) findViewById(R.id.rg_alcohol);
        rbYes = (RadioButton) findViewById(R.id.rb_yes);
        rbNo = (RadioButton) findViewById(R.id.rb_no);
        tvBooking = (Button) findViewById(R.id.tv_booking);
        tvLocationName.setText(locationSpace.getLTitle());
        tvRate.setText(locationSpace.getPrice() + "");
        tvDescription.setText(locationSpace.getDesc());

        try {
            JSONArray jsonArrayImage = new JSONArray(locationSpace.getImages());
            Glide.with(AddBookingActivity.this).load(jsonArrayImage.optString(0)).placeholder(R.mipmap.img).dontAnimate().into(imLocation);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            categories.add(i + "");
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        tvAttendes.setAdapter(dataAdapter);


    }

    public boolean isValidAllFields() {

        boolean flag = true;

        if (tvSelectDate.getText().toString().length() == 0 || (tvSelectDate.getText().toString().equalsIgnoreCase("Select Date"))) {
            showMessage.showMsg(AddBookingActivity.this, "Please Select is Date.");
            flag = false;
        } else if (tvSelectTime.getText().toString().length() == 0  ||  (tvSelectTime.getText().toString().equalsIgnoreCase("Start Time"))) {
            showMessage.showMsg(AddBookingActivity.this, "Please Select is Start Time.");
            flag = false;
        } else if (tvSelectEnd.getText().toString().length() == 0  ||  (tvSelectEnd.getText().toString().equalsIgnoreCase("End Time"))) {
            showMessage.showMsg(AddBookingActivity.this, "Please Select is End Time.");
            flag = false;
        } else if (etMessage.getText().toString().length() == 0) {
            showMessage.showMsg(AddBookingActivity.this, "Please Tell your host how you 'll use this space.");
            flag = false;
        } else if (etMessage.getText().toString().length() == 0) {
            showMessage.showMsg(AddBookingActivity.this, "Please fill your host how you 'll use this space.");
            flag = false;
        }
        return flag;
    }

    class AddBookingAsyncTask extends AsyncTask<Void, Void, Void> {
        private String response;
        private ProgressDialog progressDialog;
        private JSONObject jsObject, jsonRequest;
        private JSONObject jsonObject;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();



            jsonObject = new JSONObject();
            try {
                JSONArray jsonArrayTime =new JSONArray();
                JSONObject jsonObjectTime=new JSONObject();
                jsonObjectTime.put("day",tvSelectDate.getText().toString());
                jsonObjectTime.put("start",tvSelectTime.getText().toString());
                jsonObjectTime.put("end",tvSelectEnd.getText().toString());
                jsonArrayTime.put(jsonObjectTime);
                jsonObject.put("msg", etMessage.getText().toString() + "");
                jsonObject.put("attendees", Integer.parseInt(attendes));
                jsonObject.put("alcohol", alcoholValue);
                jsonObject.put("lId", locationSpace.getId());
                jsonObject.put("timings", jsonArrayTime );
                jsonObject.put("price", locationSpace.getPrice());
                jsonObject.put("email",SPUser.getValue(AddBookingActivity.this,SPUser.USER_EMAIL));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog = new ProgressDialog(AddBookingActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            response = CustomHttpClient.executeHttpPostRawData(AddBookingActivity.this, WebUrl.ADD_BOOKING, jsonObject + "");
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
                        Toast.makeText(AddBookingActivity.this,  jsonObject.optString("message")+"", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddBookingActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    } else {
                        showMessage.showMsg(AddBookingActivity.this, jsonObject.optString("message"));
                    }

                } else {
                    Toast.makeText(AddBookingActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }


}
