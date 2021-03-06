package svenmeier.coxswain.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 */
public class PermissionActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {

	static final String ACTION = "svenmeier.coxswain.util.permission.GRANTED";

	static final String PERMISSIONS = "permissions";

	static final String GRANTED = "granted";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String[] permissions = getIntent().getStringArrayExtra("permissions");

		ActivityCompat.requestPermissions(this, permissions, 1);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		boolean granted = true;
		for (int grantResult : grantResults) {
			granted &= (grantResult == PackageManager.PERMISSION_GRANTED);
		}

		Intent intent = new Intent();
		intent.setAction(ACTION);
		intent.putExtra(PERMISSIONS, permissions);
		intent.putExtra(GRANTED, granted);
		sendBroadcast(intent);

		finish();
	}

	public static void start(Context context, String[] permissions) {
		Intent intent = new Intent(context, PermissionActivity.class);

		// required for activity started from non-activity
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		intent.putExtra(PERMISSIONS, permissions);

		context.startActivity(intent);
	}
}
