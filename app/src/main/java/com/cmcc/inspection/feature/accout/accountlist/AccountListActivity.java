package com.cmcc.inspection.feature.accout.accountlist;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AccountListActivity extends MVPBaseActivity<AccountListContract.View, AccountListPresenter> implements AccountListContract.View {
    
    @Override
    protected AccountListPresenter createPresenter() {
        return new AccountListPresenter();
    }
    
    public static void start(Context context) {
        Intent starter = new Intent(context, AccountListActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
    }
}
