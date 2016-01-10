package com.hackgeny.megps;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity {
    public static String[] useRandom = new String[]{"37.484638146, -122.20357585", "40.7143528, -74.0059731", "42.36196872, -71.06866837",
            "32.73530651, -117.13837624", "32.88232624, -117.23332644", "33.99518087, -118.46883774",
            "37.34068368, -121.89331055", "37.79879782, -122.46356964", "47.58463134, -122.38700867", "37.562124523, -121.9525820"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        confirm();
        //Parse.enableLocalDatastore(this);
        //Parse.initialize(this, "snPf1Daj6Hta1gRTGK6pGMecw5f5xjdgYsNRTE9S", "bms2hMy3FTt8a8aMqx6StFcB8EVQYrybHiu2piBp");
        //ParseObject testObject = new ParseObject("TestObject");
        //testObject.put("foo", "bar");
        //testObject.saveInBackground();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
   public void onBigRed(View view){
	   Intent PressButton = new Intent(this, PressButton.class);
	   startActivity(PressButton);
   }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Intent set = new Intent(this, Preferences.class);
        	startActivity(set);
        	return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    public void onMenu(View view){
        Intent pref = new Intent(this, Preferences.class);
        startActivity(pref);
    }
    public void onMaps(View view){
        Intent maps = new Intent(this, ViewPlaces.class);
        startActivity(maps);
    }
    public void confirm(){
        Intent fTime = new Intent(this, Preferences.class);
        SharedPreferences userInfo = getSharedPreferences("userDataStorage", MODE_PRIVATE);
        if(userInfo.getString("First", "") == null){
            startActivity(fTime);
        }
    }
}
