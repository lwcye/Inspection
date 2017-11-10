package com.cmcc.inspection.feature.main.mainuser;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.accout.login.LoginActivity;
import com.cmcc.inspection.mvp.MVPBaseFragment;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainUserFragment extends MVPBaseFragment<MainUserContract.View, MainUserPresenter> implements MainUserContract.View, View.OnClickListener {

    public static void start(Context context) {
        Intent starter = new Intent(context, MainUserFragment.class);
        context.startActivity(starter);
    }

    @Override
    protected MainUserPresenter createPresenter() {
        return new MainUserPresenter();
    }

    @Override
    public void initData() {
        mView.findViewById(R.id.tv_user_logout).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_user_logout:
                LoginActivity.start(getBaseActivity());
                break;
            default:
                break;
        }
    }
}
