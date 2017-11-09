package com.cmcc.inspection.feature.main.mainhome;


import android.graphics.Color;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseFragment;
import com.cmcc.inspection.utils.TitleUtil;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainHomeFragment extends MVPBaseFragment<MainHomeContract.View, MainHomePresenter> implements MainHomeContract.View {
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
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }
}
