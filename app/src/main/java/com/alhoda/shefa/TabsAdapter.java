package com.alhoda.shefa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.lang.Override;

/**
 * Created by Hassan on 05/10/2014.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return new MainTab();
            case 1:
                return new SettingTab();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
