package be.flo.roommateapp.vue.pager;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import be.flo.roommateapp.R;
import be.flo.roommateapp.vue.fragment.MenuManager;
import be.flo.roommateapp.vue.fragment.admin.RoommateFragment;

/**
 * Created by florian on 3/12/14.
 */
public abstract class AbstractPager extends FragmentStatePagerAdapter {

    private Activity activity;

    public AbstractPager(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    /**
     * @return the number of pages to display
     */
    @Override
    public int getCount() {
        return MenuManager.MenuElement.getByClass(this.getClass()).getSubMenuElements().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        for (MenuManager.SubMenuElement subElement : MenuManager.MenuElement.getByClass(this.getClass()).getSubMenuElements()) {
            if (position == subElement.getOrder()) {
                return activity.getString(subElement.getName());
            }
        }
        return null;
    }
}
