package be.flo.roommateapp.vue.pager;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import be.flo.roommateapp.R;
import be.flo.roommateapp.vue.fragment.admin.RoommateFragment;

/**
 * Created by florian on 3/12/14.
 */
public class ConfigPager extends AbstractPager{

    public ConfigPager(FragmentManager fm, Activity activity) {
        super(fm,activity);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        final android.support.v4.app.Fragment fragment;


        fragment = new RoommateFragment();
        return fragment;
    }
}
