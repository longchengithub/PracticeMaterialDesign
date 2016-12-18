package com.example.chenlong.practicematerialdesign.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by chenlong on 2016/12/11.
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter
{
    private Fragment[] mFragments;
    private String[] titles = {"直播", "推荐", "番剧", "分区", "关注", "发现"};

    public void setmFragments(Fragment[] mFragments)
    {
        this.mFragments = mFragments;
    }

    public FragmentViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return mFragments[position];
    }

    @Override
    public int getCount()
    {
        return mFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return titles[position];
    }
}
