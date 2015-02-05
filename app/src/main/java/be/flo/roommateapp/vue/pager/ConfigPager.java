package be.flo.roommateapp.vue.pager;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import be.flo.roommateapp.R;
import be.flo.roommateapp.vue.fragment.admin.RoommateFragment;

/**
 * Created by florian on 3/12/14.
 */
public class ConfigPager extends FragmentStatePagerAdapter {

    public static enum SubElement {
        ROOMMATE_LIST(0, R.string.nav_config_roommate);

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

    public ConfigPager(FragmentManager fm, Activity activity) {
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


        fragment = new RoommateFragment();
        return fragment;
    }
}
