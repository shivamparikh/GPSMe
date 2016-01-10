package com.hackgeny.megps;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class Preferences extends Activity {
	public static String sex;
	public String compiled;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
        SharedPreferences userInfo = getSharedPreferences("userDataStorage", MODE_PRIVATE);
        if(isFilled()){
            setStrings();
        }
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferences, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    public boolean isFilled(){
        SharedPreferences userInfo = getSharedPreferences("userDataStorage", MODE_PRIVATE);
        if(userInfo.getString("fName", "This didn't work") == null) return false;
        else if(userInfo.getString("perNum", "This didn't work") == null) return false;
        else if(userInfo.getString("gender", "This didn't work") == null) return false;
        else if(userInfo.getString("First", "This didn't work") == null) return false;
        else if(userInfo.getString("Receive", "This didn't work") == null) return false;
        else{
            return true;
        }
    }
    public void setStrings(){
        SharedPreferences userInfo = getSharedPreferences("userDataStorage", MODE_PRIVATE);
        EditText findNum = (EditText) findViewById(R.id.phoneNumber);
        EditText findName = (EditText) findViewById(R.id.fullName);
        EditText findSex = (EditText) findViewById(R.id.Gender);
        EditText findEOne = (EditText) findViewById(R.id.ePhone1);
        EditText findETwo = (EditText) findViewById(R.id.ePhone2);
        EditText reception = (EditText) findViewById(R.id.policeServer);
        findNum.setText(userInfo.getString("perNum", "This didn't work"));
        findName.setText(userInfo.getString("fName", "This didn't work"));
        findSex.setText(userInfo.getString("gender", "This didn't work"));
        findEOne.setText(userInfo.getString("First", "This didn't work"));
        reception.setText(userInfo.getString("Receive", "This didn't work"));
        if(userInfo.getString("Second", "none of it worked.")==null) findETwo.setHint("Emergency Contact Two");
        else findETwo.setText(userInfo.getString("Second", "none of it worked."));
    }
	public void concat(View view){
        SharedPreferences userInfo = getSharedPreferences("userDataStorage", MODE_PRIVATE);
        EditText findNum = (EditText) findViewById(R.id.phoneNumber);
        EditText findName = (EditText) findViewById(R.id.fullName);
        EditText findSex = (EditText) findViewById(R.id.Gender);
        EditText findEOne = (EditText) findViewById(R.id.ePhone1);
        EditText findETwo = (EditText) findViewById(R.id.ePhone2);
        EditText reception = (EditText) findViewById(R.id.policeServer);
		String name = findName.getText().toString();
		String num = (findNum.getText().toString());
		String gen = findSex.getText().toString();
		char firstGen = gen.toLowerCase().charAt(0);
		if(firstGen == ('f')){
			sex = " Female";
		}
		else{
			sex = " Male";
		}
		compiled = name + ", " + num + ", " + sex;
		String emergencyOne = findEOne.getText().toString();
		String emergencyTwo = findETwo.getText().toString();
        String deliverance = reception.getText().toString();
		Editor uEditor = userInfo.edit();
		uEditor.putString("total",compiled);
        uEditor.putString("fName", name);
        uEditor.putString("perNum", num);
        uEditor.putString("gender", sex);
		uEditor.putString("First", emergencyOne);
		uEditor.putString("Second", emergencyTwo + "");
        uEditor.putString("Receive", deliverance);
		uEditor.apply();
		if(!chBlank()) {
            Intent goBack = new Intent(this, MainActivity.class);
            startActivity(goBack);
        }
	}
    public boolean chBlank(){
        SharedPreferences userInfo = getSharedPreferences("userDataStorage", MODE_PRIVATE);
        if(userInfo.getString("fName", "").equals("") || userInfo.getString("fName", "") == null) return true;
        else if(userInfo.getString("perNum", "").equals("") || userInfo.getString("perNum", "") == null) return true;
        else if(userInfo.getString("gender", "").equals("") || userInfo.getString("gender", "") == null) return true;
        else if(userInfo.getString("First", "").equals("") || userInfo.getString("First", "") == null) return true;
        else if(userInfo.getString("perNum", "").equals("") || userInfo.getString("perNum", "") == null) return true;
        else return false;
    }
}
