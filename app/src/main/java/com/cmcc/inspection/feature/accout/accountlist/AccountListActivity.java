package com.cmcc.inspection.feature.accout.accountlist;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.accout.accountlist.accountlistdetail.AccountListDetailActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.ui.adapter.FragmentViewPagerAdapter;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;
import com.cmcc.inspection.ui.fragment.ListFragment;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.lib_network.model.MailModel;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AccountListActivity extends MVPBaseActivity<AccountListContract.View, AccountListPresenter> implements AccountListContract.View, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, RUAdapter.OnItemClickListener {
    
    /** 科室 */
    private RadioButton mRbMail0;
    /** 派驻纪检组 */
    private RadioButton mRbMail1;
    /** 乡镇 */
    private RadioButton mRbMail2;
    /** 巡查机构 */
    private RadioButton mRbMail3;
    private RadioGroup mRgMail;
    /** 请输入内容... */
    private EditText mEtMailSearch;
    private ViewPager mVpMail;
    private int index = 0;
    private List<MailModel.MailInfo> mList0 = new ArrayList<>();
    private List<MailModel.MailInfo> mList1 = new ArrayList<>();
    private List<MailModel.MailInfo> mList2 = new ArrayList<>();
    private List<MailModel.MailInfo> mList3 = new ArrayList<>();
    private ListFragment<MailModel.MailInfo> mFragment0 = new ListFragment<>();
    private ListFragment<MailModel.MailInfo> mFragment1 = new ListFragment<>();
    private ListFragment<MailModel.MailInfo> mFragment2 = new ListFragment<>();
    private ListFragment<MailModel.MailInfo> mFragment3 = new ListFragment<>();
    private RUAdapter<MailModel.MailInfo> mAdapter0;
    private RUAdapter<MailModel.MailInfo> mAdapter1;
    private RUAdapter<MailModel.MailInfo> mAdapter2;
    private RUAdapter<MailModel.MailInfo> mAdapter3;
    
    public static void start(Context context) {
        Intent starter = new Intent(context, AccountListActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected AccountListPresenter createPresenter() {
        return new AccountListPresenter();
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        TitleUtil.attach(this)
            .setTitle("通讯录")
            .setBack(true);
        initView();
        
        mPresenter.getMailData(0);
        mPresenter.getMailData(1);
        mPresenter.getMailData(2);
        mPresenter.getMailData(3);
    }
    
    private void initView() {
        mRbMail0 = (RadioButton) findViewById(R.id.rb_mail_0);
        mRbMail1 = (RadioButton) findViewById(R.id.rb_mail_1);
        mRbMail2 = (RadioButton) findViewById(R.id.rb_mail_2);
        mRbMail3 = (RadioButton) findViewById(R.id.rb_mail_3);
        mRgMail = (RadioGroup) findViewById(R.id.rg_mail);
        mEtMailSearch = (EditText) findViewById(R.id.et_mail_search);
        mVpMail = (ViewPager) findViewById(R.id.vp_mail);
        
        mRgMail.setOnCheckedChangeListener(this);
        mVpMail.addOnPageChangeListener(this);
        
        initViewPager();
    }
    
    private void initViewPager() {
        List<Fragment> list = new ArrayList<>();
        mAdapter0 = new RUAdapter<MailModel.MailInfo>(getContext(), mList0, R.layout.item_account_list) {
            @Override
            protected void onInflateData(RUViewHolder holder, MailModel.MailInfo data, int position) {
                setHolderData(holder, data);
            }
        };
        mAdapter1 = new RUAdapter<MailModel.MailInfo>(getContext(), mList1, R.layout.item_account_list) {
            @Override
            protected void onInflateData(RUViewHolder holder, MailModel.MailInfo data, int position) {
                setHolderData(holder, data);
            }
        };
        mAdapter2 = new RUAdapter<MailModel.MailInfo>(getContext(), mList2, R.layout.item_account_list) {
            @Override
            protected void onInflateData(RUViewHolder holder, MailModel.MailInfo data, int position) {
                setHolderData(holder, data);
            }
        };
        mAdapter3 = new RUAdapter<MailModel.MailInfo>(getContext(), mList3, R.layout.item_account_list) {
            @Override
            protected void onInflateData(RUViewHolder holder, MailModel.MailInfo data, int position) {
                setHolderData(holder, data);
            }
        };
        mFragment0.setAdapter(mAdapter0);
        mFragment1.setAdapter(mAdapter1);
        mFragment2.setAdapter(mAdapter2);
        mFragment3.setAdapter(mAdapter3);
        list.add(mFragment0);
        list.add(mFragment1);
        list.add(mFragment2);
        list.add(mFragment3);
        mFragment0.setOnItemClickListener(this);
        mFragment1.setOnItemClickListener(this);
        mFragment2.setOnItemClickListener(this);
        mFragment3.setOnItemClickListener(this);
        mVpMail.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(), list));
    }
    
    private void setHolderData(RUViewHolder holder, MailModel.MailInfo data) {
        holder.setText(R.id.tv_item_account_list_name, data.name);
        holder.setText(R.id.tv_item_account_list_count, data.xingxi.size() + "人");
    }
    
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_mail_0:
                mVpMail.setCurrentItem(0);
                index = 0;
                break;
            case R.id.rb_mail_1:
                mVpMail.setCurrentItem(1);
                index = 1;
                break;
            case R.id.rb_mail_2:
                mVpMail.setCurrentItem(2);
                index = 2;
                break;
            case R.id.rb_mail_3:
                mVpMail.setCurrentItem(3);
                index = 3;
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
                mRbMail0.setChecked(true);
                break;
            case 1:
                mRbMail1.setChecked(true);
                break;
            case 2:
                mRbMail2.setChecked(true);
                break;
            case 3:
                mRbMail3.setChecked(true);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onPageScrollStateChanged(int state) {
        
    }
    
    @Override
    public void setMailData(int index, MailModel mailModel) {
        switch (index) {
            case 0:
                mList0 = mailModel.info;
                mAdapter0.setData(mList0);
                break;
            case 1:
                mList1 = mailModel.info;
                mAdapter1.setData(mList1);
                break;
            case 2:
                mList2 = mailModel.info;
                mAdapter2.setData(mList2);
                break;
            case 3:
                mList3 = mailModel.info;
                mAdapter3.setData(mList3);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onItemClick(View view, int itemType, int position) {
        List<MailModel.MailInfo> list = null;
        switch (index) {
            case 0:
                list = mList0;
                break;
            case 1:
                list = mList1;
                break;
            case 2:
                list = mList2;
                break;
            case 3:
                list = mList3;
                break;
            default:
                list = mList0;
                break;
        }
        AccountListDetailActivity.start(getContext(), list.get(position));
    }
}
