package be.flo.roommateapp.vue.technical;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import be.flo.roommateapp.vue.fragment.MenuManager;

/**
 * Created by florian on 8/02/15.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    MenuManager.MenuElement menuElement;
    Activity activity;
    int tabSelected;

    public MyPagerAdapter(Activity activity,MenuManager.MenuElement menuElement, FragmentManager fm) {
        super(fm);
        this.menuElement= menuElement;
        this.activity=activity;
    }

    @Override
    public Fragment getItem(int i) {
        tabSelected = i;
        return menuElement.getSubMenuElements()[tabSelected].getFragment();
    }

    @Override
    public int getCount() {
        return menuElement.getSubMenuElements().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return activity.getString(menuElement.getSubMenuElements()[tabSelected].getName());
    }

}
