package com.cmcc.inspection.feature.main.mainhome;


import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.brand.BrandActivity;
import com.cmcc.inspection.feature.fortress.FortressActivity;
import com.cmcc.inspection.feature.inspect.InspectActivity;
import com.cmcc.inspection.feature.inspect.track.InspectTrackPresenter;
import com.cmcc.inspection.feature.inspect.trackdetail.InspectTrackDetailActivity;
import com.cmcc.inspection.feature.model.ModelActivity;
import com.cmcc.inspection.feature.regular.RegularActivity;
import com.cmcc.inspection.feature.school.school.SchoolActivity;
import com.cmcc.inspection.feature.workarena.WorkArenaActivity;
import com.cmcc.inspection.feature.workarena.workdynamic.WorkDynamicActivity;
import com.cmcc.inspection.mvp.MVPBaseFragment;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.inspection.widget.BeiZhuDialog;
import com.cmcc.lib_network.model.LoginModel;
import com.cmcc.lib_network.model.UserInfoModel;
import com.hbln.lib_views.DrawableCenterTextView;

import rx.functions.Action1;

import static com.cmcc.inspection.feature.school.school.SchoolActivity.start;


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
    /** 先锋模范 */
    private TextView mTvHomeNickname;
    private TextView mTvHomeDw;
    private TextView mTvHomeMobile;
    private ImageView mIvHomeSign;
    
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
        
    }
    
    @Override
    public void onResume() {
        super.onResume();
        LoginModel.getUserInfo(new Action1<UserInfoModel.UserInfo>() {
            @Override
            public void call(UserInfoModel.UserInfo userInfo) {
                mTvHomeNickname.setText(userInfo.nickname);
                mTvHomeDw.setText(userInfo.danwei);
                mTvHomeMobile.setText(userInfo.mobile);
            }
        });
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }
    
    private BeiZhuDialog mBeiZhuDialog;
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_sign:
                //签到
                if (mBeiZhuDialog == null) {
                    mBeiZhuDialog = new BeiZhuDialog(getContext());
                }
                mBeiZhuDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.postTrackData(mBeiZhuDialog.getBeiZhu());
                    }
                });
                mBeiZhuDialog.show();
                break;
            case R.id.tv_home_tab_work_arena:
                //工作擂台
                WorkArenaActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_school:
                //纪检学堂
                start(getBaseActivity());
                break;
            case R.id.tv_home_regular:
                //制度规范
                RegularActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_brand:
                //品牌创新
                BrandActivity.start(getBaseActivity());
                break;
            case R.id.rl_home_inspect:
                //监督管理
                InspectActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_fortress:
                //支部堡垒
                FortressActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_model:
                //先锋模范
                ModelActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_work_dynamic:
                //工作动态
                WorkDynamicActivity.start(getBaseActivity());
                break;
            case R.id.tv_home_school_left:
                //清风讲堂
                start(getBaseActivity(), SchoolActivity.INTENT_INDEX_SCHOOL);
                break;
            case R.id.tv_home_school_right:
                //业务练兵
                start(getBaseActivity(), SchoolActivity.INTENT_INDEX_ANSWER);
                break;
            case R.id.tv_home_track:
                //业务练兵
                InspectTrackDetailActivity.start(getBaseActivity(), null);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void initView(View view) {
        TitleUtil.attach(view)
            .setLeft("贾汪区").setLeftColor(Color.WHITE)
            .setLeftDrawable(R.drawable.ic_location, 0, 0, 0)
            .setRightDrawable(R.drawable.ic_qrcode, 0, 0, 0)
            .setColor(R.color.white, 0)
            .setShadow(false);
        
        mTvHomeTabWorkArena = (DrawableCenterTextView) view.findViewById(R.id.tv_home_tab_work_arena);
        mTvHomeTabWorkArena.setOnClickListener(this);
        mTvHomeNickname = (TextView) view.findViewById(R.id.tv_home_nickname);
        mTvHomeDw = (TextView) view.findViewById(R.id.tv_home_dw);
        mTvHomeMobile = (TextView) view.findViewById(R.id.tv_home_mobile);
        mTvHomeSchool = (DrawableCenterTextView) view.findViewById(R.id.tv_home_school);
        mTvHomeSchool.setOnClickListener(this);
        mTvHomeRegular = (DrawableCenterTextView) view.findViewById(R.id.tv_home_regular);
        mTvHomeRegular.setOnClickListener(this);
        mTvHomeBrand = (DrawableCenterTextView) view.findViewById(R.id.tv_home_brand);
        mTvHomeBrand.setOnClickListener(this);
        view.findViewById(R.id.tv_home_fortress).setOnClickListener(this);
        mRlHomeInspect = (RelativeLayout) view.findViewById(R.id.rl_home_inspect);
        mRlHomeInspect.setOnClickListener(this);
        mTvHomeModel = (DrawableCenterTextView) view.findViewById(R.id.tv_home_model);
        mTvHomeModel.setOnClickListener(this);
        view.findViewById(R.id.tv_home_work_dynamic).setOnClickListener(this);
        view.findViewById(R.id.tv_home_school_left).setOnClickListener(this);
        view.findViewById(R.id.tv_home_school_right).setOnClickListener(this);
        view.findViewById(R.id.tv_home_track).setOnClickListener(this);
        view.findViewById(R.id.iv_home_sign).setOnClickListener(this);
    }
}
