package app.kambingku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    public static boolean login = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                SharedPreferences preferences =getSharedPreferences("my_preferences", MODE_PRIVATE);
                // Check if onboarding_complete is false
                if(!preferences.getBoolean("onboarding_complete",false))

                {// Start the onboarding Activity
                    Intent onboarding = new Intent(splash.this,welcome.class);
                    startActivity(onboarding);

// Close the main Activity
                    finish();

                }
                else {

                /* Create an Intent that will start the Menu-Activity. */


                    Intent mainIntent = new Intent(splash.this, login.class);
                    splash.this.startActivity(mainIntent);
                    splash.this.finish();
                /*
                else
                {
                    Intent mainIntent = new Intent(splash.this, login_drawer.class);
                    splash.this.startActivity(mainIntent);
                    splash.this.finish();
                }*/

                }
            }
        }, SPLASH_DISPLAY_LENGTH);


    }
}
