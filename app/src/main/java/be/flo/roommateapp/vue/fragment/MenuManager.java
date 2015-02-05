package be.flo.roommateapp.vue.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import be.flo.roommateapp.R;
import be.flo.roommateapp.vue.fragment.Welcome.WelcomeFragment;
import be.flo.roommateapp.vue.fragment.admin.RoommateFragment;
import be.flo.roommateapp.vue.fragment.count.ResumeFragment;
import be.flo.roommateapp.vue.fragment.count.TicketListFragment;
import be.flo.roommateapp.vue.fragment.profile.MyProfileFragment;
import be.flo.roommateapp.vue.fragment.shopping.ShoppingItemListFragment;
import be.flo.roommateapp.vue.pager.*;

/**
 * Created by florian on 5/02/15.
 */
public class MenuManager {

    public static enum MenuElement {
        MENU_EL_WELCOME(R.string.nav_drawer_welcome, 0, WelcomePager.class, SubMenuElement.WELCOME),
        MENU_EL_COUNT(R.string.nav_drawer_count, 1, CountPager.class, SubMenuElement.COUNT_RESUME, SubMenuElement.COUNT_TICKET_LIST),
        MENU_EL_SHOPPING(R.string.nav_drawer_shopping, 2, ShoppingPager.class, SubMenuElement.SHOPPING_LIST),
        MENU_EL_CONFIG(R.string.nav_drawer_config, 3, ConfigPager.class, SubMenuElement.ADMIN_ROOMMATE_LIST),
        MENU_EL_PROFILE(R.string.nav_drawer_my_profile, 4, ProfilePager.class, SubMenuElement.PROFILE_MY_PROFILE),
        MENU_EL_LOGOUT(R.string.nav_drawer_logout, 5, null);

        private final int name;
        private final int order;
        private final SubMenuElement[] subMenuElements;
        private final Class<? extends FragmentStatePagerAdapter> pagerClass;

        private MenuElement(int name, int order, Class<? extends FragmentStatePagerAdapter> pagerClass, SubMenuElement... subMenuElements) {
            this.name = name;
            this.order = order;
            this.pagerClass = pagerClass;
            this.subMenuElements = subMenuElements;
        }

        public static MenuElement getByClass(Class<?> pagerClass) {
            for (MenuElement menuElement : values()) {
                if (menuElement.pagerClass.equals(pagerClass)) {
                    return menuElement;
                } else {
                    for (SubMenuElement subMenuElement : menuElement.subMenuElements) {
                        if (subMenuElement.fragmentClass.equals(pagerClass)) {
                            return menuElement;
                        }
                    }

                }
            }
            return null;
        }

        public int getName() {
            return name;
        }

        public int getOrder() {
            return order;
        }

        public SubMenuElement[] getSubMenuElements() {
            return subMenuElements;
        }

        public Class<? extends FragmentStatePagerAdapter> getPagerClass() {
            return pagerClass;
        }
    }


    public static enum SubMenuElement {
        ADMIN_ROOMMATE_LIST(0, R.string.nav_config_roommate, RoommateFragment.class),

        COUNT_RESUME(0, R.string.nav_count_resume, ResumeFragment.class),
        COUNT_TICKET_LIST(1, R.string.nav_count_ticket, TicketListFragment.class),

        PROFILE_MY_PROFILE(0, R.string.nav_drawer_my_profile, MyProfileFragment.class),

        SHOPPING_LIST(0, R.string.nav_shopping_item, ShoppingItemListFragment.class),

        WELCOME(0, R.string.g_welcome, WelcomeFragment.class);

        private final int order;
        private final int name;
        private final Class<? extends Fragment> fragmentClass;

        SubMenuElement(int order, int name, Class<? extends Fragment> fragmentClass) {
            this.order = order;
            this.name = name;
            this.fragmentClass = fragmentClass;
        }

        public int getOrder() {
            return order;
        }

        public int getName() {
            return name;
        }

        public static SubMenuElement getByClass(Class<?> pagerClass) {
            for (SubMenuElement subMenuElement : values()) {
                if (subMenuElement.fragmentClass.equals(pagerClass)) {
                    return subMenuElement;
                }
            }
            return null;
        }

        public Class<? extends Fragment> getFragmentClass() {
            return fragmentClass;
        }
    }


}
