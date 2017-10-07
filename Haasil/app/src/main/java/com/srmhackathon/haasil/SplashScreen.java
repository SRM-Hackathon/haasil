package com.srmhackathon.haasil;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.r0adkll.slidr.Slidr;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_DISPLAY_LENGTH = 2000;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);




        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this,IntroActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                Slidr.attach(SplashScreen.this);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}