package com.developer.amien.spbu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        File f = new File(
                "/data/data/com.developer.amien.spbu/shared_prefs/spbu.xml");
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("spbu", 0); // 0 - for private mode
        if (!f.exists()) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("spbu", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("id_user", "0");
            editor.commit();
            if(pref.getString("id_user",null).equalsIgnoreCase("0")) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                        Intent mainIntent = new Intent(SplashScreen.this, Loginactivity.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }else{
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                        Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }
        }else{
            if(pref1.getString("id_user",null).equalsIgnoreCase("0")) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                        Intent mainIntent = new Intent(SplashScreen.this, Loginactivity.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }else{
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                        Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }
        }


    }
}
