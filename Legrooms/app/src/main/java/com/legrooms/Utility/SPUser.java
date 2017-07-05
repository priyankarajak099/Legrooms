package com.legrooms.Utility;

import android.content.Context;

public class SPUser {

	public static final String USER_ID= "user_id";
	public static final String USER_TYPE= "usertype";
	public static final String USER_FULLNAME= "fullname";
	public static final String USER_NAME= "username";
	public static final String USER_EMAIL= "useremail";
	public static final String IS_LOGIN= "is_login";
	public static final String FILTER_LOCATION= "location_filter";



	public  static final String USER_TOKEN="user_token";

    public static long getLongValue(Context context, String key) {

		return context.getSharedPreferences("USER", Context.MODE_PRIVATE)
				.getLong(key, 0);
	}

	public static void setLongValue(Context context, String key, Long value) {

		context.getSharedPreferences("USER", Context.MODE_PRIVATE).edit()
				.putLong(key, value).commit();
	}
	
	
	
	public static String getValue(Context context, String key) {

		return context.getSharedPreferences("USER", Context.MODE_PRIVATE)
				.getString(key, "");
	}

	public static void setValue(Context context, String key, String value) {

		context.getSharedPreferences("USER", Context.MODE_PRIVATE).edit()
				.putString(key, value).commit();
	}

	public static int getIntValue(Context context, String key) {

		return context.getSharedPreferences("USER", Context.MODE_PRIVATE)
				.getInt(key, 0);
	}

	public static void setIntValue(Context context, String key, int value) {

		context.getSharedPreferences("USER", Context.MODE_PRIVATE).edit()
				.putInt(key, value).commit();
	}

	public static boolean getBooleanValue(Context context, String key) {

		return context.getSharedPreferences("USER", Context.MODE_PRIVATE)
				.getBoolean(key, false);
	}

	public static void setBooleanValue(Context context, String key,
                                       boolean value) {

		context.getSharedPreferences("USER", Context.MODE_PRIVATE).edit()
				.putBoolean(key, value).commit();
	}

	
	public static float getFloatValue(Context context, String key) {

		return context.getSharedPreferences("USER", Context.MODE_PRIVATE)
				.getFloat(key, 0.0f);
	}

	public static void setFloatValue(Context context, String key,
                                     float value) {

		context.getSharedPreferences("USER", Context.MODE_PRIVATE).edit()
				.putFloat(key, value).commit();
	}

	public static boolean clear(Context context) {

		return context.getSharedPreferences("USER", Context.MODE_PRIVATE).edit().clear().commit();
	}

	public static void setIntValueProgress(Context context, String key, int value) {

		context.getSharedPreferences("USER", Context.MODE_PRIVATE).edit()
				.putInt(key, value).commit();
	}

	public static int getIntValueProgress(Context context, String key) {

		return context.getSharedPreferences("USER", Context.MODE_PRIVATE)
				.getInt(key, 50);
	}


}
