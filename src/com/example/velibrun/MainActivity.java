package com.example.velibrun;

import com.loopj.android.http.RequestParams;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Activity used to sign user into race
 */
public class MainActivity extends RestActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			EditText editText = (EditText) findViewById(R.id.editText1);
				String race = "1"; // Race number hard coded, need to be dynamized
				signIn(race, editText.getText().toString());
				Intent intent = new Intent(MainActivity.this, RankingActivity.class);
				intent.putExtra("race", race);
				startActivity(intent);
			}
		});
	}

	/**
	 * Function to join a race with a user name
	 * @param race
	 * @param name
	 * @param callback
	 */
	private void signIn(String race, String name) {
		debug("Signin event.");
		RequestParams params = new RequestParams();
		params.put("name", name);
		httpPost("/races/" + race + "/users", params);
	}
}