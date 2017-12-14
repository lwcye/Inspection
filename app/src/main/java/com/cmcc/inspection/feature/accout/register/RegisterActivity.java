package com.cmcc.inspection.feature.accout.register;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.lib_network.constans.URLs;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.model.DwLianDongModel;
import com.cmcc.lib_network.model.LoginModel;
import com.cmcc.lib_utils.utils.ToastUtils;

import rx.functions.Action0;


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
    private TextView mEtRegistDw0;
    /** 请选择职务 */
    private TextView mEtRegistDw1;
    /** 请输入密码 */
    private EditText mEtRegistPassword;
    /** 请再次输入密码 */
    private EditText mEtRegistRepassword;
    private AlertDialog mAlertDialog0;
    private DwLianDongModel.InfoBean mInfoBean0;
    private DwLianDongModel.InfoBean mInfoBean1;
    private AlertDialog mAlertDialog1;

    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    /**
     * 初始化
     */
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
        mTvRegistVerfi.setOnClickListener(this);
        mEtRegistPhone = (EditText) findViewById(R.id.et_regist_phone);
        mEtRegistDw0 = (TextView) findViewById(R.id.et_regist_dw_0);
        mEtRegistDw0.setOnClickListener(this);
        mEtRegistDw1 = (TextView) findViewById(R.id.et_regist_dw_1);
        mEtRegistDw1.setOnClickListener(this);
        mEtRegistPassword = (EditText) findViewById(R.id.et_regist_password);
        mEtRegistRepassword = (EditText) findViewById(R.id.et_regist_repassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_regist_verfi:
                mPresenter.verificationSfid(mEtRegistName.getText().toString().trim(),
                        mEtRegistSfid.getText().toString().trim(),
                        null);
                break;
            case R.id.tv_register_confirm:
                mPresenter.verificationSfid(mEtRegistName.getText().toString().trim(),
                        mEtRegistSfid.getText().toString().trim(),
                        new Action0() {
                            @Override
                            public void call() {
                                mPresenter.requestRegist(mEtRegistPhone.getText().toString().trim(),
                                        mEtRegistPassword.getText().toString().trim(),
                                        mEtRegistRepassword.getText().toString().trim(),
                                        "",
                                        mEtRegistName.getText().toString().trim(),
                                        mEtRegistSfid.getText().toString().trim(),
                                        mInfoBean1.id,
                                        URLs.HTTP_TOKEN);
                            }
                        });
                break;
            case R.id.et_regist_dw_0:
                showDwLianList();
                break;
            case R.id.et_regist_dw_1:
                showDwLianListOther(mInfoBean0);
                break;
            default:
                break;
        }
    }

    /**
     * 第二个单位
     *
     * @param infoBean0
     */
    private void showDwLianListOther(DwLianDongModel.InfoBean infoBean0) {
        if (infoBean0 == null || TextUtils.isEmpty(infoBean0.id)) {
            ToastUtils.showShortToastSafe("请选择单位");
            return;
        }
        mPresenter.loadDwData(infoBean0.id, new HttpResult<DwLianDongModel>() {
            @Override
            public void result(final DwLianDongModel dwLianDongModel) {
                String[] strings = new String[dwLianDongModel.info.size()];
                for (int i = 0; i < dwLianDongModel.info.size(); i++) {
                    strings[i] = dwLianDongModel.info.get(i).name;
                }
                mAlertDialog1 = new AlertDialog.Builder(getContext()).setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mInfoBean1 = dwLianDongModel.info.get(which);
                        mEtRegistDw1.setText(mInfoBean1.name);
                    }
                }).create();
                mAlertDialog1.show();
            }
        });
    }

    /**
     * 展示单位的第一个
     */
    private void showDwLianList() {
        if (mAlertDialog0 == null) {
            mPresenter.loadDwData(null, new HttpResult<DwLianDongModel>() {
                @Override
                public void result(final DwLianDongModel dwLianDongModel) {
                    String[] strings = new String[dwLianDongModel.info.size()];
                    for (int i = 0; i < dwLianDongModel.info.size(); i++) {
                        strings[i] = dwLianDongModel.info.get(i).name;
                    }
                    mAlertDialog0 = new AlertDialog.Builder(getContext()).setItems(strings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mInfoBean0 = dwLianDongModel.info.get(which);
                            mEtRegistDw0.setText(mInfoBean0.name);
                        }
                    }).create();
                    mAlertDialog0.show();
                }
            });
        } else {
            mAlertDialog0.show();
        }
    }

    @Override
    public void resultRegist(LoginModel objectModel) {
        URLs.UID = objectModel.uid;
        URLs.ACCESS_TOKEN = objectModel.access_token;
        RegisterResultActivity.start(getContext());
        finish();
    }
}
