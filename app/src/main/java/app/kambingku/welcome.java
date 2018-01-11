package app.kambingku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class welcome extends AppCompatActivity {

    private ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Checking for first time launch - before calling setContentView()
        pager = (ViewPager) findViewById(R.id.view_pager);

        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new welcome1();
                    case 1:
                        return new welcome2();
                    case 2:
                        return new welcome3();
                    default:
                        return null;
                }

            }

            @Override
            public int getCount() {
                return 3;

            }
        };

        pager.setAdapter(adapter);
    }

    public void finishOnboarding() {
        // Get the shared preferences
        SharedPreferences preferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        // Set onboarding_complete to true
        preferences.edit().putBoolean("onboarding_complete",true).apply();
// Launch the main Activity, called MainActivity
        Intent main = new Intent(this, login.class);
        startActivity(main);// Close the OnboardingActivity
        finish();
    }





}
