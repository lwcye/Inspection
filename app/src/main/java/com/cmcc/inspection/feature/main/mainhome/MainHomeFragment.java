package com.cmcc.inspection.feature.main.mainhome;


import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.InspectActivity;
import com.cmcc.inspection.feature.brand.BrandActivity;
import com.cmcc.inspection.feature.model.ModelActivity;
import com.cmcc.inspection.feature.regular.RegularActivity;
import com.cmcc.inspection.feature.school.school.SchoolActivity;
import com.cmcc.inspection.feature.workarena.WorkArenaActivity;
import com.cmcc.inspection.feature.workarena.workdynamic.WorkDynamicActivity;
import com.cmcc.inspection.mvp.MVPBaseFragment;
import com.cmcc.inspection.utils.TitleUtil;
import com.hbln.lib_views.DrawableCenterTextView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainHomeFragment extends MVPBaseFragment<MainHomeContract.View, MainHomePresenter> implements MainHomeContract.View, View.OnClickListener {
    private View view;
    /** 工作擂台 */
    private DrawableCenterTextView mTvHomeTabWorkArena;
    /** 纪检学堂 */
    private DrawableCenterTextView mTvHomeSchool;
    /** 制度规范 */
    private DrawableCenterTextView mTvHomeRegular;
    /** 品牌创新 */
    private DrawableCenterTextView mTvHomeBrand;
    private RelativeLayout mRlHomeInspect;
    /** 先锋模范 */
    private DrawableCenterTextView mTvHomeModel;

    /**
     * 创建Fragment实体
     *
     * @return MainActiveFragment
     */
    public static MainHomeFragment newInstance() {
        return new MainHomeFragment();
    }

    @Override
    protected MainHomePresenter createPresenter() {
        return new MainHomePresenter();
    }

    @Override
    public void initData() {
        TitleUtil.attach(mView)
                .setLeft("贾汪区").setLeftColor(Color.WHITE)
                .setLeftDrawable(R.drawable.ic_location, 0, 0, 0)
                .setRightDrawable(R.drawable.ic_qrcode, 0, 0, 0)
                .setColor(R.color.white, 0);

        initView(mView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home_tab_work_arena:
                WorkArenaActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_school:
                SchoolActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_regular:
                RegularActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_brand:
                BrandActivity.start(getBaseActivity());
                break;
            case R.id.rl_home_inspect:
                InspectActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_model:
                ModelActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_work_dynamic:
                WorkDynamicActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_school_left:
                SchoolActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_school_right:
                SchoolActivity.start(getBaseActivity());
                break;
            default:
                break;
        }
    }

    public void initView(View view) {
        mTvHomeTabWorkArena = (DrawableCenterTextView) view.findViewById(R.id.tv_home_tab_work_arena);
        mTvHomeTabWorkArena.setOnClickListener(this);
        mTvHomeSchool = (DrawableCenterTextView) view.findViewById(R.id.tv_home_school);
        mTvHomeSchool.setOnClickListener(this);
        mTvHomeRegular = (DrawableCenterTextView) view.findViewById(R.id.tv_home_regular);
        mTvHomeRegular.setOnClickListener(this);
        mTvHomeBrand = (DrawableCenterTextView) view.findViewById(R.id.tv_home_brand);
        mTvHomeBrand.setOnClickListener(this);
        mRlHomeInspect = (RelativeLayout) view.findViewById(R.id.rl_home_inspect);
        mRlHomeInspect.setOnClickListener(this);
        mTvHomeModel = (DrawableCenterTextView) view.findViewById(R.id.tv_home_model);
        mTvHomeModel.setOnClickListener(this);
        view.findViewById(R.id.tv_home_work_dynamic).setOnClickListener(this);
        view.findViewById(R.id.tv_home_school_left).setOnClickListener(this);
        view.findViewById(R.id.tv_home_school_right).setOnClickListener(this);
    }
}
