package com.legrooms;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.legrooms.Utility.CustomHttpClient;
import com.legrooms.Utility.NetworkConnection;
import com.legrooms.Utility.SPUser;
import com.legrooms.Utility.ShowDialog;
import com.legrooms.Utility.WebUrl;
import com.legrooms.Views.EditText;
import com.legrooms.Views.TextViewPlus;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SignupTwoActivity extends Activity {

    private String email, password;
    private TextViewPlus tvClose;
    private EditText etEmail;
    private EditText etPassword;
    private NetworkConnection networkConnection;
    private ShowDialog showMessage;
    private TextViewPlus tvUpdate;
    private int STORAGE_PERMISSION_CODE = 23;
    public static final int MEDIA_TYPE_IMAGE = 2;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String IMAGE_DIRECTORY_NAME = "Legrooms";
    private Uri fileUri;
    private String selectedImagePath;
    private ImageView imBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        initComponent();
        initListener();
    }
    private void initComponent() {
        try {
            networkConnection = new NetworkConnection();
            showMessage = new ShowDialog();
            email = getIntent().getExtras().getString("email", "");
            password = getIntent().getExtras().getString("password", "");
            tvClose = (TextViewPlus) findViewById(R.id.tv_header_close);
            etEmail = (EditText) findViewById(R.id.et_email);
            tvUpdate = (TextViewPlus) findViewById(R.id.tv_upload);
            etPassword = (EditText) findViewById(R.id.et_password);
            imBack=(ImageView)findViewById(R.id.im_back);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListener() {

        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new RegisterAsyncTask().execute();
            }
        });
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isReadStorageAllowed()){
                    //If permission is already having then showing the toast
                CharSequence colors[] = new CharSequence[]{"Gallery",
                        "Camera"};

                AlertDialog.Builder builder = new AlertDialog.Builder(SignupTwoActivity.this);
                builder.setTitle("Upload photo from");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                selectImage();
                                break;
                            case 1:
                                captureImage();
                                break;

                            default:
                                break;
                        }
                    }
                });
                builder.show();
                    //Existing the method with return
                    return;
                }

                //If the app has not the permission then asking for the permission
                requestStoragePermission();
            }
        });

    }
    /*
         * Capturing Camera Image will lauch camera app requrest image capture
         */
    private void captureImage() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            try {
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    private void selectImage() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            try {

                startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"), 1);
            } catch (Exception e) {
                e.printStackTrace();
            }


    }



    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {
        Log.e("before directory", "yes");
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            Log.e("storage directory", "yes");
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        Log.e("Create file", "yes");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        Log.e("mediaFile", mediaFile + "");
        return mediaFile;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // successfully captured the image
                // bimatp factory
                try {

                    selectedImagePath = fileUri.getPath();



                } catch (Exception e) {
                    e.printStackTrace();
                }
                // //

            } else if (resultCode == RESULT_CANCELED) {

            } else {
                // failed to capture image
                Toast.makeText(SignupTwoActivity.this,
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {

                    Uri selectedImageUri = data.getData();

                    selectedImagePath = getPath(selectedImageUri);



                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }


    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission

            Snackbar snackbar = Snackbar
                    .make(tvUpdate, "Storage Permission is needed to save your images in device", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(SignupTwoActivity.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

                        }
                    });

            snackbar.show();
        }

        //And finally ask for the permission
       // ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a
                Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }



    class RegisterAsyncTask extends AsyncTask<Void, Void, Void> {
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
                jsonObject.put("email", email + "");
                jsonObject.put("password", password + "");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog = new ProgressDialog(SignupTwoActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            response = CustomHttpClient.executeHttpPostRawData(SignupTwoActivity.this, WebUrl.REGISTER, jsonObject + "");
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
                        SPUser.setBooleanValue(SignupTwoActivity.this, SPUser.IS_LOGIN, true);
                        SPUser.setValue(SignupTwoActivity.this,SPUser.USER_EMAIL,email);
                        SPUser.setValue(SignupTwoActivity.this, SPUser.USER_TOKEN, jsonObject.optString("token"));
                        startActivity(new Intent(SignupTwoActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    } else {
                        showMessage.showMsg(SignupTwoActivity.this, jsonObject.optString("message"));
                    }

                } else {
                    Toast.makeText(SignupTwoActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}
