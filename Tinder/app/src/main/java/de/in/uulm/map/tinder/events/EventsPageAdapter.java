package de.in.uulm.map.tinder.events;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public class EventsPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    /**
     * basic constructor for the Adapter
     */
    public EventsPageAdapter(FragmentManager fm) {

        super(fm);
        this.mFragments = new ArrayList<>();
    }

    /**
     * Use this method to add a Fragment to the adapter.
     * @param fragment the fragment to be added
     */
    public void addFragment(Fragment fragment) {

        mFragments.add(fragment);
    }

    /**
     * This method is returning a new fragment if the view has changed
     * @return see Fragment
     */
    @Override
    public Fragment getItem(int position) {

        return mFragments.get(position);
    }

    /**
     * This method is returning the size of the Fragments list
     * @return size of Fragments list
     */
    @Override
    public int getCount() {

        return mFragments.size();
    }

    /**
     * This method is setting the tab titles
     * @return see EventsActivity.TAB_TITLE
     */
    @Override
    public CharSequence getPageTitle(int position) {

        Fragment fragment = mFragments.get(position);
        Bundle args = fragment.getArguments();

        return args.getString(EventsActivity.TAB_TITLE);
    }

}
