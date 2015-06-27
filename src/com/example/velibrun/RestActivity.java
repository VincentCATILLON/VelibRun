package com.example.velibrun;

import org.apache.http.Header;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Activity with Rest API functions
 */
public class RestActivity extends Activity {

	private static AsyncHttpClient client = new AsyncHttpClient();
	private String uiid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rest);
		TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
		uiid = tm.getDeviceId();
		if (Boolean.valueOf(getString(R.string.debug))) {
			uiid = "123456789";
		}
		debug("Uiid: " + uiid);
	}

	/**
	 * Function to realize a HTTP Post call
	 * @param url
	 * @param RequestParams
	 */
	protected void httpPost(String url, RequestParams params) {
		params.put("uiid", uiid);
		debug("URL: " + url + ".");
		client.post(getBaseContext(), getString(R.string.api) + url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				debug("Failure POST.");
			}
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				debug("Success POST.");
			}
		});
	}

	/**
	 * Function called to show toasts when debug is enabled
	 * @param msg
	 */
	protected void debug(String msg) {
		if (Boolean.valueOf(getString(R.string.debug))) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
	}
}