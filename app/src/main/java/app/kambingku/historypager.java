package app.kambingku;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Win 10 on 1/9/2018.
 */

public class historypager extends FragmentPagerAdapter {

    public historypager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                proses proses = new proses();
                return proses;
            case 1:
                selesai selesai = new selesai();
                return selesai;





            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {
            case 0:
                return "Sedang Proses";
            case 1:
                return "Selesai";



            default:
                return null;
        }

    }
}

