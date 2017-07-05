package com.legrooms.Utility;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;

public class CustomHttpClient {
    private static Activity activity;
    private static NetworkConnection networkConnection;

    public static HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    public static String executeHttpPostRawData(Activity activity,String url, String data) {
        CustomHttpClient.activity = activity;

            String value = "{\"status\":false,\"message\":\"Server Timeout, connection problem, Please try later\"}";
            try {
                networkConnection = new NetworkConnection();
                if (networkConnection.isOnline(activity)) {
                    HttpClient client = getNewHttpClient();
                    HttpPost post = new HttpPost(url);

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(url);

                    try {
                        httppost.setHeader("Content-Type", "application/json");
                        if (SPUser.getValue(activity,SPUser.USER_TOKEN).length()!=0)
                        {
                            httppost.setHeader("x-access-token",SPUser.getValue(activity,SPUser.USER_TOKEN)+ "");

                        }

                        httppost.setEntity(new ByteArrayEntity(data.getBytes()));

                        HttpResponse result = httpclient.execute(httppost);

                        HttpEntity resultEntity = result.getEntity();
                        value = EntityUtils.toString(resultEntity);

                    } catch (ClientProtocolException e) {
     /**/
                    } catch (IOException e) {
     /**/
                    }

                    //String s = "";
            /*for (NameValuePair param : postParameters) {
				s = s + param.getName() + " = " + param.getValue() + " ";
			}*/
                    if (value != null) {
                        System.out.println("From " + url + " paramertes " + data + " get "
                                + value);
                        return value;
                    } else {
                        return null;
                    }
                } else {
                    return "{\"status\":false,\"message\":\"No Internet Connection\"}";
                }


            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

    }

        public static String executeHttpPost (Activity activity, String url, ArrayList < NameValuePair > postParameters){
            CustomHttpClient.activity = activity;
            String value = "{\"status\":false,\"message\":\"Server Timeout, connection problem, Please try later\"}";
            try {
                networkConnection = new NetworkConnection();
                if (networkConnection.isOnline(activity)) {

				/*
				 * postParameters.add(new BasicNameValuePair("device_id",
				 * ((TelephonyManager) activity.getApplicationContext()
				 * .getSystemService(Context.TELEPHONY_SERVICE))
				 * .getDeviceId())); postParameters.add(new
				 * BasicNameValuePair("device_type", "android"));
				 */

                    postParameters.add(new BasicNameValuePair("device_type", "android"));
                    HttpClient client = getNewHttpClient();
                    ;
                    HttpPost post = new HttpPost(url);
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                            postParameters, "UTF-8");
                    // entity.setContentEncoding(HTTP.UTF_8);
                    post.setEntity(entity);
                    HttpResponse result = client.execute(post);

                    value = EntityUtils.toString(result.getEntity());
                    String s = "";
                    for (NameValuePair param : postParameters) {
                        s = s + param.getName() + " = " + param.getValue() + " ";
                    }
                    if (value != null) {
                        WebUrl.ShowLog("From " + url + " parameters" + s
                                + " Response : " + value);

                        return value;
                    } else {

                        return value;
                    }
                } else {
                    return "{\"status\":false,\"message\":\"No Internet Connection\"}";
                }

            } catch (Exception e) {
                e.printStackTrace();

                return value;
            }
        }

        public static String executeHttpGet (Activity activity, String url){
            CustomHttpClient.activity = activity;
            try {
                networkConnection = new NetworkConnection();
                if (networkConnection.isOnline(activity)) {
                    HttpClient client = getNewHttpClient();
                    ;
                    HttpGet get = new HttpGet(url);
                    HttpResponse result = client.execute(get);
                    String value = EntityUtils.toString(result.getEntity());

                    if (value != null) {
                        WebUrl.ShowLog("From " + url + " get " + value);

                        return value;
                    } else {
                        WebUrl.ShowLog("From " + url + " get null");

                        return null;
                    }
                } else {
                    return "{\"status\":false,\"message\":\"No Internet Connection\"}";
                }
            } catch (Exception e) {
                e.printStackTrace();
                WebUrl.ShowLog("From " + url + " get Exception"
                        + e.getMessage());
                WebUrl.ShowLog(e.getMessage());

                return null;
            }
        }

    }