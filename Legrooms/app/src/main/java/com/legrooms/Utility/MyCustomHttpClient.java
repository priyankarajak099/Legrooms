package com.legrooms.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;




public class MyCustomHttpClient {
    private static NetworkConnection networkConnection;
    private Activity activity;

    public MyCustomHttpClient(Activity activity) {
        this.activity = activity;
    }

    public interface OnSuccess {
        void onSucess(String result);
    }

    public interface OnFailure {
        void onFailure(String result);
    }

    public void executeHttp(final String strUrl, final ArrayList<NameValuePair> postParameters, final ProgressDialog progress, final OnSuccess success, final OnFailure failure, final Method type) {
        final Handler handler = new Handler();
        if (progress != null) {
            progress.show();
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                new Thread() {
                    @Override
                    public void run() {
                        processInBackground(strUrl, postParameters, progress, success, failure, type);
                    }
                }.start();
            }
        });
    }

    private void processInBackground(String strUrl, ArrayList<NameValuePair> postParameters, final ProgressDialog progress, final OnSuccess success, final OnFailure failure, Method type) {
        try {
            if (NetworkConnection.getInstance(activity).isOnline(activity)) {
                String result = "";
                System.out.println("Url for execute " + strUrl);
                HttpClient client = new DefaultHttpClient();
                HttpResponse r;
                if (type == Method.POST) {
                    HttpPost post = new HttpPost(strUrl);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                            postParameters, "UTF-8");
                    post.setEntity(entity);

                    r = client.execute(post);
                } else {
                    HttpGet get = new HttpGet(strUrl);
                    r = client.execute(get);
                }
                final String value = EntityUtils.toString(r.getEntity());
                String s = "";
                for (NameValuePair param : postParameters) {
                    s = s + param.getName() + " = " + param.getValue() + " ";
                }
                if (value != null) {
                    System.out.println("From " + strUrl +" Parameters "+s+ " get " + value);
                }

                result = value;
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.optBoolean("status")) {
                            showToastAndSuccess(jsonObject.optString("message").trim(), success, result, progress);
                        } else {
                            showToastAndFailure(jsonObject.optString("message"), failure, result, progress);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToastAndFailure(e.getMessage(), failure, "{\"status\":false,\"message\":\"" + e.getMessage() + "\"}", progress);
                    }
                } else {
                    showToastAndFailure("Network Error.", failure, "{\"status\":false,\"message\":\"Network Error.\"}", progress);
                }
            } else {
                showToastAndFailure("Please Provide Internet.", failure, "{\"status\":false,\"message\":\"Please Provide Internet.\"}", progress);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showToastAndFailure(e.getMessage(), failure, "{\"status\":false,\"message\":\"" + e.getMessage() + "\"}", progress);
        }
    }

    private void showToastAndSuccess(final String string, final OnSuccess success, final String result, final ProgressDialog progress) {
        activity.runOnUiThread(new Thread(new Runnable() {
            public void run() {
                try {
                    if (progress != null) {
                        progress.cancel();
                    }
                } catch (Exception e) {
                }
                if (string.length() > 0) {
                    Toast.makeText(activity, string, Toast.LENGTH_LONG).show();
                }
                if (success != null) {
                    success.onSucess(result);
                }
            }
        }));
    }

    private void showToastAndFailure(final String string, final OnFailure failure, final String result, final ProgressDialog progress) {
        activity.runOnUiThread(new Thread(new Runnable() {
            public void run() {
                try {
                    if (progress != null) {
                        progress.cancel();
                    }
                } catch (Exception e) {
                }
                if (string.length() > 0) {
                    Toast.makeText(activity, string, Toast.LENGTH_LONG).show();
                }
                if (failure != null) {
                    failure.onFailure(result);
                }
            }
        }));
    }

    public enum Method {GET, POST}
    public static String executeHttpGet(Activity activity, String url) {

        try {
            networkConnection = new NetworkConnection();
            if (networkConnection.isOnline(activity)) {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url);
                HttpResponse result = client.execute(get);
                String value = EntityUtils.toString(result.getEntity());

                if (value != null) {
                   // WebUrl.ShowLog("From " + url + " get " + value.trim());
                    return value;
                } else {
                //    WebUrl.ShowLog("From " + url + " get null");
                    return null;
                }
            } else {
                return "{\"status\":false,\"message\":\"No Internet Connection\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
          /*  WebUrl.ShowLog("From " + url + " get Exception"
                    + e.getMessage());
            WebUrl.ShowLog(e.getMessage());*/

            return null;
        }
    }
}