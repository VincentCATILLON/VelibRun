package com.example.velibrun;

import com.loopj.android.http.RequestParams;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

/**
 * Activity that track geolocation of the user
 */
public class GeolocationActivity extends RestActivity implements LocationListener {

	private LocationManager lm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0,
				this);
		if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0,
				this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		lm.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		double altitude = location.getAltitude();
		float accuracy = location.getAccuracy();
		float speed = location.getSpeed();
		updateGeolocation(latitude, longitude, altitude, accuracy, speed);
	}

	/**
	 * Geolocation updater
	 * @param latitude
	 * @param longitude
	 * @param altitude
	 * @param accuracy
	 * @param speed
	 */
	private void updateGeolocation(double latitude, double longitude, double altitude, float accuracy, float speed) {
		RequestParams params = new RequestParams();
		params.put("lat", latitude);
		params.put("lon", longitude);
		params.put("alt", altitude);
		params.put("accur", accuracy);
		params.put("speed", speed);
		httpPost("/races/" + getIntent().getStringExtra("race") + "/checkpoints", params);
	}

	@Override
	public void onProviderDisabled(String provider) {
		debug(provider + " offline.");
	}

	@Override
	public void onProviderEnabled(String provider) {
		debug(provider + " online.");
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		String newStatus = "";
		switch (status) {
			case LocationProvider.OUT_OF_SERVICE:
				newStatus = "OUT_OF_SERVICE";
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				newStatus = "TEMPORARILY_UNAVAILABLE";
				break;
			case LocationProvider.AVAILABLE:
				newStatus = "AVAILABLE";
				break;
		}
		debug(provider + " offline: " + newStatus + ".");
	}
}