package com.cmcc.inspection.feature.fortress;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.fortress.education.FortressEducationFragment;
import com.cmcc.inspection.feature.fortress.inspect.ForTressInspectFragment;
import com.cmcc.inspection.feature.fortress.manager.FortressManagerFragment;
import com.cmcc.inspection.feature.main.MainActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.ui.adapter.FragmentViewPagerAdapter;
import com.cmcc.inspection.utils.TitleUtil;

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
    private RadioGroup mRgFortress;
    private ViewPager mVpForTress;
    private FragmentViewPagerAdapter mAdapter;
    
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
    }
    
    private void initView() {
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_home, 0, 0, 0)
            .setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.start(getContext());
                }
            })
            .setTitle("制度规定");
        mRbFortress0 = (RadioButton) findViewById(R.id.rb_fortress_0);
        mRbFortress1 = (RadioButton) findViewById(R.id.rb_fortress_1);
        mRbFortress2 = (RadioButton) findViewById(R.id.rb_fortress_2);
        mRgFortress = (RadioGroup) findViewById(R.id.rg_fortress);
        mVpForTress = (ViewPager) findViewById(R.id.vp_fortress);
        mRgFortress.setOnCheckedChangeListener(this);
        
        
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FortressEducationFragment());
        fragmentList.add(new ForTressInspectFragment());
        fragmentList.add(new FortressManagerFragment());
        mAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mVpForTress.setAdapter(mAdapter);
        mVpForTress.addOnPageChangeListener(this);
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
            default:
                break;
        }
    }
    
    @Override
    public void onPageScrollStateChanged(int state) {
        
    }
}
