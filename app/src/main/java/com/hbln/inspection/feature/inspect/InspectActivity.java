package com.hbln.inspection.feature.inspect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cmcc.lib_common.base.BaseActivity;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.inspect.track.InspectTrackFragment;
import com.hbln.inspection.feature.inspect.visit.InspectVisitFragment;
import com.hbln.inspection.feature.main.MainActivity;
import com.hbln.inspection.ui.adapter.FragmentViewPagerAdapter;
import com.hbln.inspection.utils.TitleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/11/10
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class InspectActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    /** 轨迹申报 */
    private RadioButton mRbInspect0;
    /** 线上家访 */
    private RadioButton mRbInspect1;
    private RadioGroup mRgInspect;
    private ViewPager mVpInspect;
    private FragmentViewPagerAdapter mAdapter;
    
    public static void start(Context context) {
        Intent starter = new Intent(context, InspectActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect);
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
        mRbInspect0 = (RadioButton) findViewById(R.id.rb_inspect_0);
        mRbInspect0.setOnClickListener(this);
        mRbInspect1 = (RadioButton) findViewById(R.id.rb_inspect_1);
        mRbInspect1.setOnClickListener(this);
        mRgInspect = (RadioGroup) findViewById(R.id.rg_inspect);
        mRgInspect.setOnClickListener(this);
        mVpInspect = (ViewPager) findViewById(R.id.vp_inspect);
        mVpInspect.addOnPageChangeListener(this);
        
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new InspectTrackFragment());
        fragmentList.add(new InspectVisitFragment());
        mAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mVpInspect.setAdapter(mAdapter);
    }
    
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_inspect_0:
                mVpInspect.setCurrentItem(0);
                break;
            case R.id.rb_inspect_1:
                mVpInspect.setCurrentItem(1);
                break;
            case R.id.rg_inspect:
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
                mRbInspect0.setChecked(true);
                break;
            case 1:
                mRbInspect1.setChecked(true);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onPageScrollStateChanged(int state) {
        
    }
}
