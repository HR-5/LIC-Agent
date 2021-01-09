package com.example.licagent.AddClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<Fragment> fragmentList = new ArrayList<>();
    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //return fragmentList.get(position);
        switch (position){
            case 0:
                return new Page1();

            case 1:
                return new Page2();

            case 2:
                return new Page3();

            default:
                return new Page1();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public void addFragments(Fragment fragment){
        fragmentList.add(fragment);
    }
}
