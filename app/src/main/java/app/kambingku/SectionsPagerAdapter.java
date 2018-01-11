package app.kambingku;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Win 10 on 12/23/2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                deskripsi_frag deskripsi_frag = new deskripsi_frag();
                return deskripsi_frag;




            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {
            case 0:
                return "Deskripsi";



            default:
                return null;
        }

    }
}
