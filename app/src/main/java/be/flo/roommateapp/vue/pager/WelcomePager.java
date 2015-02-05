package be.flo.roommateapp.vue.pager;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import be.flo.roommateapp.R;
import be.flo.roommateapp.vue.fragment.Welcome.WelcomeFragment;

/**
 * Created by florian on 3/12/14.
 */
public class WelcomePager extends FragmentStatePagerAdapter {

    public static enum SubElement {
        WELCOME(0, R.string.g_welcome);

        private final int order;
        private final int name;

        SubElement(int order, int name) {
            this.order = order;
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public int getName() {
            return name;
        }
    }

    private Activity activity;

    public WelcomePager(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    /**
     * @return the number of pages to display
     */
    @Override
    public int getCount() {
        return SubElement.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        for (SubElement subElement : SubElement.values()) {
            if (position == subElement.getOrder()) {
                return activity.getString(subElement.getName());
            }
        }
        return null;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        final android.support.v4.app.Fragment fragment;


        fragment = new WelcomeFragment();
        return fragment;
    }
}
