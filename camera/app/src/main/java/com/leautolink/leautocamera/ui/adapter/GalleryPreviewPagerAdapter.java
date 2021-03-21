package com.leautolink.leautocamera.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.leautolink.leautocamera.ui.base.BaseFragment;

import java.util.List;

/**
 * Created by tianwei1 on 2016/3/8.
 */
public class GalleryPreviewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mFragments;

    public GalleryPreviewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
