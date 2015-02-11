package be.flo.roommateapp.vue.technical.slidingBar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import be.flo.roommateapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A basic sample which shows how to use {@link TabLayout}
 * to display a custom {@link android.support.v4.view.ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class SliderBar extends Fragment {

    Date d = new Date();
    DateFormat df = new SimpleDateFormat(":mm:ss:SSSS");

    private PagerAdapter pagerAdapter;

    // A custom {@link android.support.v4.view.ViewPager} title strip which looks much like Tabs present in Android v4.0 and
    //above, but is designed to give continuous feedback to the user when scrolling.
    private TabLayout mSlidingTabLayout;

    // A {@link android.support.v4.view.ViewPager} which will be used in conjunction with the {@link TabLayout} above.
    private ViewPager mViewPager;

    public void setPagerAdapter(PagerAdapter pagerAdapter) {
        Log.w("status", "SliderBar.setPagerAdapter : " + pagerAdapter + "<=" + df.format(this.d));
        this.pagerAdapter = pagerAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Log.w("status", "SliderBar.onViewCreated : " + pagerAdapter + "/" + mViewPager + "<=" + df.format(this.d));

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(pagerAdapter);

        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    public PagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }
}