package com.cmcc.inspection.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * <p>ViewPager用于Fragment的适配器</p><br>
 *
 * @author lwc
 * @date 2017/3/6 17:00
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    /** Fragment列表 */
    private List<Fragment> mFragmentList;
    private List<String> Titles;


    /**
     * 构造类，初始化数据列表
     *
     * @param fm FragmentManager
     * @param fragments Fragment列表
     */
    public FragmentViewPagerAdapter(FragmentManager fm, @NonNull List<Fragment> fragments) {
        super(fm);
        mFragmentList = fragments;
    }

    /**
     * 设置数据并且刷新界面
     *
     * @param fragmentList Fragment列表
     */
    public void setData(List<Fragment> fragmentList) {
        this.mFragmentList = fragmentList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (Titles != null) {
            return Titles.get(position);
        }
        return super.getPageTitle(position);
    }

    public void setTitles(List<String> titles) {
        Titles = titles;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //这里Destroy的是Fragment的视图层次，并不是Destroy Fragment对象
        //提高用户体验感，进入页面不摧毁Fragment,全部缓存下来
        //super.destroyItem(container, position, object);
    }
}
