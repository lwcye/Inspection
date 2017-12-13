package com.cmcc.inspection.feature.accout.register;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.model.DwLianDongModel;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterActivity extends MVPBaseActivity<RegisterContract.View, RegisterPresenter> implements RegisterContract.View, View.OnClickListener {
    
    /** 确认 */
    private Button mTvRegisterConfirm;
    /** 请输入姓名 */
    private EditText mEtRegistName;
    /** 请输入身份证号码 */
    private EditText mEtRegistSfid;
    /** 验证 */
    private TextView mTvRegistVerfi;
    /** 请输入手机号码 */
    private EditText mEtRegistPhone;
    /** 请选择单位 */
    private EditText mEtRegistDw0;
    /** 请选择职务 */
    private EditText mEtRegistDw1;
    /** 请输入密码 */
    private EditText mEtRegistPassword;
    /** 请再次输入密码 */
    private EditText mEtRegistRepassword;
    private AlertDialog mAlertDialog0;
    private DwLianDongModel.InfoBean mInfoBean0;
    private AlertDialog mAlertDialog1;
    
    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }
    
    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    
    private void initView() {
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_back, 0, 0, 0)
            .setColor(Color.WHITE, 255)
            .setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            })
            .setTitle("注册");
        mTvRegisterConfirm = (Button) findViewById(R.id.tv_register_confirm);
        mTvRegisterConfirm.setOnClickListener(this);
        mEtRegistName = (EditText) findViewById(R.id.et_regist_name);
        mEtRegistSfid = (EditText) findViewById(R.id.et_regist_sfid);
        mTvRegistVerfi = (TextView) findViewById(R.id.tv_regist_verfi);
        mEtRegistPhone = (EditText) findViewById(R.id.et_regist_phone);
        mEtRegistDw0 = (EditText) findViewById(R.id.et_regist_dw_0);
        mEtRegistDw0.setOnClickListener(this);
        mEtRegistDw1 = (EditText) findViewById(R.id.et_regist_dw_1);
        mEtRegistDw1.setOnClickListener(this);
        mEtRegistPassword = (EditText) findViewById(R.id.et_regist_password);
        mEtRegistRepassword = (EditText) findViewById(R.id.et_regist_repassword);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register_confirm:
                RegisterResultActivity.start(getContext());
                finish();
                break;
            case R.id.et_regist_dw_0:
                if (mAlertDialog0 == null) {
                    mPresenter.loadDwData(null, new HttpResult<DwLianDongModel>() {
                        @Override
                        public void result(final DwLianDongModel dwLianDongModel) {
                            String[] strings = new String[dwLianDongModel.info.size()];
                            for (int i = 0; i < dwLianDongModel.info.size(); i++) {
                                strings[i] = dwLianDongModel.info.get(i).name;
                            }
                            mAlertDialog0=new AlertDialog.Builder(getContext()).setItems(strings, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mInfoBean0 = dwLianDongModel.info.get(which);
                                }
                            }).create();
                            mAlertDialog0.show();
                        }
                    });
                } else {
                    mAlertDialog0.show();
                }
                
                break;
            case R.id.et_regist_dw_1:
                break;
        }
    }
}
