package app.kambingku;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class history extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private historypager historypager;
    private SharedPreferences sp,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        sp = this.getSharedPreferences("login", this.MODE_PRIVATE);


        mViewPager = (ViewPager) findViewById(R.id.pager);
        historypager = new historypager(getSupportFragmentManager());

        mViewPager.setAdapter(historypager);

        mTabLayout = (TabLayout) findViewById(R.id.tl);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
