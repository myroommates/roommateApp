package be.flo.roommateapp.vue.pager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import be.flo.roommateapp.R;
import be.flo.roommateapp.vue.fragment.count.ResumeFragment;
import be.flo.roommateapp.vue.fragment.count.TicketListFragment;

/**
 * Created by florian on 3/12/14.
 */
public class CountPager extends AbstractPager {

    public CountPager(FragmentManager fm, Activity activity) {
        super(fm,activity);
    }

    @Override
    public Fragment getItem(int position) {
        final Fragment fragment;

        if (position == 0) {
            fragment = new ResumeFragment();
        } else {
            fragment = new TicketListFragment();
        }
        return fragment;
    }
}


