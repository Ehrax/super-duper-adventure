package de.in.uulm.map.tinder.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexanderrasputin on 02.05.17.
 */

public class MainPageAdapter extends FragmentPagerAdapter {

    public static final String TAB_TITLE = "tab-title";

    private List<Fragment> mFragments;
    private List<Integer> mItemIds;

    /**
     * basic constructor for the Adapter
     */
    public MainPageAdapter(FragmentManager fm) {

        super(fm);
        this.mFragments = new ArrayList<>();
        this.mItemIds = new ArrayList<>();
    }

    /**
     * Use this method to add a Fragment to the adapter.
     * @param fragment the fragment to be added
     * @param itemId the itemId in the bottom menu
     */
    public void addFragment(Fragment fragment, int itemId) {

        mFragments.add(fragment);
        mItemIds.add(itemId);
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
     * This method is used to get the index of a fragment by a given item id.
     * @param itemId the itemId in the bottom menu
     * @return the index of the fragment
     */
    public int getIndexById(int itemId) {

        return mItemIds.indexOf(itemId);
    }

    /**
     * This method is used to the the item id for a given fragment.
     * @param itemIndex the index of the fragment you want to know the id of
     * @return the itemId in the bottom menu
     */
    public int getIdByIndex(int itemIndex) {

        return mItemIds.get(itemIndex);
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

        return args.getString(TAB_TITLE);
    }
}
