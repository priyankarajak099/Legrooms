package com.legrooms.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
//import android.widget.Toast;

public class ShowDialog {
	
	public void showMsg(Activity activity, String message){
//		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
		
		new AlertDialog.Builder(activity)

		.setMessage(message)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).create().show();
	}
}
