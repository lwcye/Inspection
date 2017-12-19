package com.cmcc.inspection.feature.accout.accountlist.accountlistdetail;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.lib_common.base.BaseApp;
import com.cmcc.lib_common.utils.loader.LoaderFactory;
import com.cmcc.lib_common.utils.loader.Options;
import com.cmcc.lib_network.model.MailModel;
import com.cmcc.lib_utils.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.cmcc.lib_common.utils.loader.Options.RES_NONE;
import static com.cmcc.lib_common.utils.loader.Options.TYPE_CIRCLE;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AccountListDetailActivity extends MVPBaseActivity<AccountListDetailContract.View, AccountListDetailPresenter> implements AccountListDetailContract.View {
    public static final String INTENT_INFO = "mailInfo";
    private MailModel.MailInfo mInfo;
    private List<MailModel.MailInfo.XingxiBean> mList = new ArrayList<>();
    private RUAdapter<MailModel.MailInfo.XingxiBean> mAdapter;
    private RecyclerView mRvAccountListDetail;

    public static void start(Context context, MailModel.MailInfo mailInfo) {
        Intent starter = new Intent(context, AccountListDetailActivity.class);
        starter.putExtra(INTENT_INFO, mailInfo);
        context.startActivity(starter);
    }

    @Override
    protected AccountListDetailPresenter createPresenter() {
        return new AccountListDetailPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list_detail);
        mInfo = getIntent().getParcelableExtra(INTENT_INFO);

        TitleUtil.attach(this)
                .setTitle("通讯录-" + mInfo.name)
                .setBack(true);
        initView();

    }

    private void initView() {
        mRvAccountListDetail = (RecyclerView) findViewById(R.id.rv_account_list_detail);
        mRvAccountListDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = mInfo.xingxi;
        mAdapter = new RUAdapter<MailModel.MailInfo.XingxiBean>(getContext(), mList, R.layout.item_account_list_detail) {
            @Override
            protected void onInflateData(RUViewHolder holder, final MailModel.MailInfo.XingxiBean data, int position) {
                holder.setText(R.id.tv_item_account_list_detail_name, data.name);
                holder.setText(R.id.tv_item_account_list_detail_dw, "所属单位：" + mInfo.name);
                holder.setText(R.id.tv_item_account_list_detail_zw, "职务：" + data.zhiwu);
                holder.setText(R.id.tv_item_account_list_detail_moblie, "联系电话：" + data.mobile);
                if (!TextUtils.isEmpty(data.pic)) {
                    ImageView viewById = holder.getViewById(R.id.civ_item_account_list_detail);
                    LoaderFactory.getLoader().loadNet(viewById, data.pic, new Options(RES_NONE, RES_NONE, TYPE_CIRCLE));
                }

                holder.setOnClickListener(R.id.iv_item_account_list_detail_moblie, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(data.mobile)) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + data.mobile));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            BaseApp.getInstance().startActivity(intent);
                        } else {
                            ToastUtils.showShortToastSafe("没有联系电话");
                        }
                    }
                });

            }
        };
        mAdapter.setDataEmptyLayoutId(R.layout.layout_empty);
        mRvAccountListDetail.setAdapter(mAdapter);
    }
}
