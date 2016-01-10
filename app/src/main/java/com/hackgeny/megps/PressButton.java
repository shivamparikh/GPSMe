package com.hackgeny.megps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

public class PressButton extends Activity{
	public static String friend = "Hello, I am currently using the GPSMe app to send this message to let you know " +
			"that I may be in trouble right now";
    public static String friendOne = "If I do not get in contact with you soon, please take actions to help me.";
    public static String gottaCancel = "Sorry, false alarm on my part. Please disregard.";
    public static String policeCancel = "police.sendMessage(cancel_event)";
	public String numOne;
	public String numTwo;
	public String police;
	private String DataDestination = "19169136423";
    protected LocationManager locationManager;
    protected Context context;
    private String lastLong;
    private String lastLat;
    public String finalCalc;
    public double latitude;
    public double longitude;
    public boolean cancelled;
    public boolean disabled;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_press_button);
        grabData();
        sendTwo();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new MyLocationListener());
        finalCalc = getCurrentLocation();
        Toast.makeText(PressButton.this,finalCalc,Toast.LENGTH_LONG).show();
        //sendImportant();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.press_button, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    public void sendTwo(){
        sendEmergencyOne();
        if(numTwo.length() >= 6){
            sendEmergencyTwo();
        }
    }
    public void totalCancel(View view){
        SmsManager mySMS = SmsManager.getDefault();
        String message = (policeCancel);
        mySMS.sendTextMessage(DataDestination, null, message, null, null);
        SmsManager smsCancel = SmsManager.getDefault();
        String mesCancel = gottaCancel;
        smsCancel.sendTextMessage(numOne, null, mesCancel, null, null);
        if(numTwo.length() >= 5) {
            smsCancel.sendTextMessage(numTwo, null, mesCancel, null, null);
        }
        cancelled = true;
        finish();
    }
    public void sendImportant(){
        SmsManager gpsSMS = SmsManager.getDefault();
        String messages = finalCalc;
        gpsSMS.sendTextMessage(DataDestination, null, messages, null, null);
    }
	public void sendParse(){
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "snPf1Daj6Hta1gRTGK6pGMecw5f5xjdgYsNRTE9S", "bms2hMy3FTt8a8aMqx6StFcB8EVQYrybHiu2piBp");
        ParseObject sending = new ParseObject("Response");
        String forSend = (police);
        sending.put("user_info", forSend);
        sending.put("Total", finalCalc);
        sending.put("latitude", latitude);
        sending.put("longitude", longitude);
        sending.saveInBackground();

	}
	public void sendEmergencyOne(){
		SmsManager SMSONE = SmsManager.getDefault();
		String message = friend;
		SMSONE.sendTextMessage(numOne, null, message, null, null);
        String messageOne = friendOne;
        SMSONE.sendTextMessage(numOne, null, messageOne, null, null);
	}
	public void sendEmergencyTwo(){
		SmsManager SMSTWO = SmsManager.getDefault();
		String message = friend;
		SMSTWO.sendTextMessage(numTwo, null, message, null, null);
        String messageOne = friendOne;
        SMSTWO.sendTextMessage(numTwo, null, messageOne, null, null);
	}
	public void grabData(){
		SharedPreferences userInfo = getSharedPreferences("userDataStorage", MODE_PRIVATE);
		police = userInfo.getString("total", "This didn't work");
		numOne = userInfo.getString("First", "This didn't work");
        numTwo = userInfo.getString("Second", "none of it worked.");
        String test = userInfo.getString("Receive", "");
        if(test.length() >= 5) {
            DataDestination = userInfo.getString("Receive", "");
        }
        SmsManager mySMS = SmsManager.getDefault();
        String message = (police);
        mySMS.sendTextMessage(DataDestination, null, message, null, null);
	}
    public String getCurrentLocation(){
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(disabled) return "blank";
        String foop = "failed";
        if (location != null) {
            double log = location.getLongitude();
            double lat = location.getLatitude();
            latitude = lat;
            longitude = log;
            lastLat = Double.toString(lat);
            lastLong = Double.toString(log);
            foop = (lat + ", " + log);
        }
        else{
            Toast.makeText(PressButton.this,"darn",Toast.LENGTH_SHORT).show();
            String tempoire = (lastLat + "," + lastLong);
            foop = (tempoire);
        }
        System.out.println("COORDINATES HERE: " + foop);
        return foop;
    }
    public void leave(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private class MyLocationListener implements LocationListener {
        int count = 0;
        SmsManager mySMS = SmsManager.getDefault();
        public void onLocationChanged(Location location) {
            mySMS.sendTextMessage(DataDestination, null, finalCalc, null, null);
            count++;
            System.out.println("TextSent" + count);
            //sendParse();
            Toast.makeText(PressButton.this, finalCalc, Toast.LENGTH_SHORT).show();
            if(count == 2){
                mySMS = null;
                locationManager = null;
                //leave();
                finish();
                System.exit(0);
                System.out.println("DiDNOTFiNiSh" + count);
            }
        }
        public void onStatusChanged(String s, int i, Bundle b) {
        }
        public void onProviderDisabled(String s) {
            Toast.makeText(PressButton.this, "Provider disabled by the user. GPS turned off", Toast.LENGTH_LONG).show();
            disabled = true;
        }
        public void onProviderEnabled(String s) {
            Toast.makeText(PressButton.this,"Provider enabled by the user. GPS turned on",Toast.LENGTH_LONG).show();
        }
    }
}
