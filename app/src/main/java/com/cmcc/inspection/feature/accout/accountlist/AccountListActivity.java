package com.cmcc.inspection.feature.accout.accountlist;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.ui.adapter.FragmentViewPagerAdapter;
import com.cmcc.inspection.ui.fragment.ListFragment;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.lib_network.model.MailModel;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AccountListActivity extends MVPBaseActivity<AccountListContract.View, AccountListPresenter> implements AccountListContract.View, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

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
    private List<MailModel> mList0 = new ArrayList<>();
    private List<MailModel> mList1 = new ArrayList<>();
    private List<MailModel> mList2 = new ArrayList<>();
    private List<MailModel> mList3 = new ArrayList<>();

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
        initView();
        TitleUtil.attach(this)
                .setTitle("通讯薄")
                .setBack(true);
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

        List<Fragment> list = new ArrayList<>();
        ListFragment listFragment = new ListFragment<MailModel>();
        mVpMail.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(), list));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_mail_0:
                mVpMail.setCurrentItem(0);
                break;
            case R.id.rb_mail_1:
                mVpMail.setCurrentItem(1);
                break;
            case R.id.rb_mail_2:
                mVpMail.setCurrentItem(2);
                break;
            case R.id.rb_mail_3:
                mVpMail.setCurrentItem(3);
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
    public void setMailData(int index) {
    }
}
