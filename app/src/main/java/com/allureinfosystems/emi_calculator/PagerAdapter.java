package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numOfTab;

    public PagerAdapter(@NonNull FragmentManager fm,int numOfTab) {

        super(fm);
        this.numOfTab = numOfTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            case 0:return new simpleInterestFragment();
            case 1:return new compoundInterestFragment();

            default:
               return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTab;
    }
}
