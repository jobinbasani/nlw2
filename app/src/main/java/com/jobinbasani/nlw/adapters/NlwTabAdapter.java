package com.jobinbasani.nlw.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.jobinbasani.nlw.R;
import com.jobinbasani.nlw.fragments.WeekendList;

import java.util.Locale;

/**
 * Created by jobinbasani on 2/3/15.
 */
public class NlwTabAdapter extends FragmentPagerAdapter {

    private Context context;

    public NlwTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return WeekendList.newInstance(getPageTitle(position).toString());
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return context.getString(R.string.title_section2).toUpperCase(l);
        }
        return null;
    }
}
