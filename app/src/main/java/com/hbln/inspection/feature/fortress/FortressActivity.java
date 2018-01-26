package com.hbln.inspection.feature.fortress;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cmcc.lib_network.model.FortressHomeModel;
import com.cmcc.lib_network.model.JianDuModel;
import com.cmcc.lib_network.model.WebViewModel;
import com.cmcc.lib_utils.utils.LogUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.fortress.education.FortressHomeFragment;
import com.hbln.inspection.feature.fortress.manager.FortressManagerFragment;
import com.hbln.inspection.feature.main.MainActivity;
import com.hbln.inspection.feature.workarena.workdynamic.WebViewContentActivity;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.hbln.inspection.ui.adapter.FragmentViewPagerAdapter;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.ui.fragment.ListFragment;
import com.hbln.inspection.utils.TitleUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FortressActivity extends MVPBaseActivity<FortressContract.View, FortressPresenter> implements FortressContract.View, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    
    /** 党员教育 */
    private RadioButton mRbFortress0;
    /** 党员监督 */
    private RadioButton mRbFortress1;
    /** 党员管理 */
    private RadioButton mRbFortress2;
    private RadioButton mRbFortress3;
    private RadioGroup mRgFortress;
    private ViewPager mVpForTress;
    private FragmentViewPagerAdapter mAdapter;
    
    private List<JianDuModel.InfoBean> mJianDuModels = new ArrayList<>();
    private RUAdapter<JianDuModel.InfoBean> mJianDuRUAdapter;
    private ListFragment<JianDuModel.InfoBean> mJianDuListFragment = new ListFragment<>();
    
    private List<FortressHomeModel.InfoBean> mJiaoYuModels = new ArrayList<>();
    private RUAdapter<FortressHomeModel.InfoBean> mJiaoYuRUAdapter;
    private ListFragment<FortressHomeModel.InfoBean> mJiaoYuListFragment = new ListFragment<>();
    
    @Override
    protected FortressPresenter createPresenter() {
        return new FortressPresenter();
    }
    
    public static void start(Context context) {
        Intent starter = new Intent(context, FortressActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortress);
        initView();
        mPresenter.loadJianDuData();
        mPresenter.loadJiaoYuData();
    }
    
    private void initView() {
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_home, 0, 0, 0)
            .setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.start(getContext());
                }
            })
            .setTitle("支部堡垒");
        mRbFortress0 = (RadioButton) findViewById(R.id.rb_fortress_0);
        mRbFortress1 = (RadioButton) findViewById(R.id.rb_fortress_1);
        mRbFortress2 = (RadioButton) findViewById(R.id.rb_fortress_2);
        mRbFortress3 = (RadioButton) findViewById(R.id.rb_fortress_3);
        mRgFortress = (RadioGroup) findViewById(R.id.rg_fortress);
        mVpForTress = (ViewPager) findViewById(R.id.vp_fortress);
        mRgFortress.setOnCheckedChangeListener(this);
        
        
        initViewPager();
        mVpForTress.addOnPageChangeListener(this);
    }
    
    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        mJianDuRUAdapter = new RUAdapter<JianDuModel.InfoBean>(getContext(), mJianDuModels, R.layout.item_fortress_img) {
            @Override
            protected void onInflateData(RUViewHolder holder, JianDuModel.InfoBean data, int position) {
                holder.setText(R.id.tv_item_fortress, data.title);
                holder.setText(R.id.tv_item_fortress_date, data.times);
                if (TextUtils.isEmpty(data.author)) {
                    holder.setVisibility(R.id.tv_item_fortress_name, View.GONE);
                } else {
                    holder.setVisibility(R.id.tv_item_fortress_name, View.VISIBLE);
                    holder.setText(R.id.tv_item_fortress_name, data.author);
                }
                if (TextUtils.isEmpty(data.pic)) {
                    holder.setVisibility(R.id.iv_item_fortress, View.GONE);
                } else {
                    holder.setVisibility(R.id.iv_item_fortress, View.VISIBLE);
                    holder.setImageNet(R.id.iv_item_fortress, data.pic);
                }
            }
        };
        mJianDuListFragment.setAdapter(mJianDuRUAdapter);
        mJianDuListFragment.setOnItemClickListener(new RUAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemType, int position) {
                WebViewContentActivity.start(getContext(), mJianDuModels.get(position).id, WebViewModel.TYPE_FORTRESS_JIANDU);
            }
        });
        
        mJiaoYuRUAdapter = new RUAdapter<FortressHomeModel.InfoBean>(getContext(), mJiaoYuModels, R.layout.item_fortress_img) {
            @Override
            protected void onInflateData(RUViewHolder holder, FortressHomeModel.InfoBean data, int position) {
                holder.setText(R.id.tv_item_fortress, data.title);
                holder.setText(R.id.tv_item_fortress_date, data.times);
                if (TextUtils.isEmpty(data.zhibuname)) {
                    holder.setVisibility(R.id.tv_item_fortress_name, View.GONE);
                } else {
                    holder.setVisibility(R.id.tv_item_fortress_name, View.VISIBLE);
                    holder.setText(R.id.tv_item_fortress_name, data.zhibuname);
                }
                if (TextUtils.isEmpty(data.pic)) {
                    holder.setVisibility(R.id.iv_item_fortress, View.GONE);
                } else {
                    holder.setVisibility(R.id.iv_item_fortress, View.VISIBLE);
                    holder.setImageNet(R.id.iv_item_fortress, data.pic);
                }
            }
        };
        
        mJiaoYuListFragment.setAdapter(mJiaoYuRUAdapter);
        mJiaoYuListFragment.setOnItemClickListener(new RUAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemType, int position) {
                WebViewContentActivity.start(getContext(), mJiaoYuModels.get(position).id, WebViewModel.TYPE_FORTRESS_HOME);
            }
        });
        fragmentList.add(new FortressHomeFragment());
        fragmentList.add(mJiaoYuListFragment);
        fragmentList.add(mJianDuListFragment);
        fragmentList.add(new FortressManagerFragment());
        mAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mVpForTress.setAdapter(mAdapter);
    }
    
    
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_fortress_0:
                mVpForTress.setCurrentItem(0);
                break;
            case R.id.rb_fortress_1:
                mVpForTress.setCurrentItem(1);
                break;
            case R.id.rb_fortress_2:
                mVpForTress.setCurrentItem(2);
                break;
            case R.id.rb_fortress_3:
                mVpForTress.setCurrentItem(3);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        
    }
    
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mRbFortress0.setChecked(true);
                break;
            case 1:
                mRbFortress1.setChecked(true);
                break;
            case 2:
                mRbFortress2.setChecked(true);
                break;
            case 3:
                mRbFortress3.setChecked(true);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onPageScrollStateChanged(int state) {
        
    }

    @Override
    public void setJianDuData(JianDuModel jianDuData) {
        LogUtils.e(jianDuData.info.size());
        mJianDuModels = jianDuData.info;
        mJianDuRUAdapter.setData(mJianDuModels);
    }
    
    @Override
    public void setJiaoYuData(FortressHomeModel homeModel) {
        mJiaoYuModels = homeModel.info;
        LogUtils.e(mJiaoYuModels.size());
        mJiaoYuRUAdapter.setData(mJiaoYuModels);
    }
}
