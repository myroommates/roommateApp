package be.flo.roommateapp.vue.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import be.flo.roommateapp.R;
import be.flo.roommateapp.model.util.Storage;
import be.flo.roommateapp.vue.fragment.NavigationDrawerFragment;
import be.flo.roommateapp.vue.pager.*;
import be.flo.roommateapp.vue.technical.slidingBar.SliderBar;


public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final String INTENT_TAB = "intent_tab";
    public static final String INTENT_MENU = "intent_menu";

    NavigationDrawerFragment mNavigationDrawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //sliderBar = new SliderBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recover roommateDTO id
        Intent i = getIntent();

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        if (i.getIntExtra(INTENT_MENU,-1) != -1) {
            mNavigationDrawerFragment.setPosition(i.getIntExtra(INTENT_MENU, -1));
            i.removeExtra(INTENT_MENU);
        }

    }

    @Override
    protected void onResume() {
        Log.w("status", "onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {

        Log.w("status", "onStart");
        super.onStart();


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //reload data is needed
        if (!Storage.testStorage()) {
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Log.w("status", "onNavigationDrawerItemSelected:" + position);
        // update the main content by replacing fragments
        PagerAdapter pagerAdapter = null;

        if (position == NavigationDrawerFragment.MenuElement.MENU_EL_WELCOME.getOrder()) {
            pagerAdapter = new WelcomePager(getSupportFragmentManager(), this);
        } else if (position == NavigationDrawerFragment.MenuElement.MENU_EL_COUNT.getOrder()) {
            pagerAdapter = new CountPager(getSupportFragmentManager(), this);
        } else if (position == NavigationDrawerFragment.MenuElement.MENU_EL_SHOPPING.getOrder()) {
            pagerAdapter = new ShoppingPager(getSupportFragmentManager(), this);
        } else if (position == NavigationDrawerFragment.MenuElement.MENU_EL_CONFIG.getOrder()) {
            pagerAdapter = new ConfigPager(getSupportFragmentManager(), this);
        } else if (position == NavigationDrawerFragment.MenuElement.MENU_EL_PROFILE.getOrder()) {
            pagerAdapter = new ProfilePager(getSupportFragmentManager(), this);
        } else if (position == NavigationDrawerFragment.MenuElement.MENU_EL_LOGOUT.getOrder()) {
            logout();
        }
        Log.w("status", "salut batard, je te file ça : " + pagerAdapter);


        if (pagerAdapter != null) {
            FragmentManager fragmentManager = getFragmentManager();
            SliderBar sliderBar = new SliderBar();
            sliderBar.setPagerAdapter(pagerAdapter);

            Log.w("status", "salut batard, je te file ça : " + pagerAdapter);

            fragmentManager.beginTransaction()
                    .replace(R.id.container, sliderBar)
                    .commit();
        }
    }

    public void logout() {
        Storage.clean(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
