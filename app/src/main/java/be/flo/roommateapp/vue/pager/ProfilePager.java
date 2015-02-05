package be.flo.roommateapp.vue.pager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import be.flo.roommateapp.R;
import be.flo.roommateapp.vue.fragment.profile.MyProfileFragment;

/**
 * Created by florian on 3/12/14.
 */
public class ProfilePager extends AbstractPager {


    public ProfilePager(FragmentManager fm, Activity activity) {
        super(fm,activity);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if (position == 0) {
            fragment = new MyProfileFragment();
        }
        return fragment;
    }
}
