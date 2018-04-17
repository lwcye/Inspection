package com.hbln.inspection.feature.accout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.base.MyActivity;
import com.hbln.inspection.network.http.HttpComplete;
import com.hbln.inspection.network.http.HttpError;
import com.hbln.inspection.network.http.HttpRequest;
import com.hbln.inspection.network.http.HttpResult;
import com.hbln.inspection.network.model.ObjectModel;
import com.hbln.inspection.utils.TitleUtil;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2018/3/1
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ModifyPsdActivity extends MyActivity implements View.OnClickListener {
    /** 请输入身份证号码 */
    private EditText mEtModifySfid;
    /** 请输入您的账号 */
    private EditText mEtModifyPhone;
    /** 请输入新密码 */
    private EditText mEtModifyPsd;
    /** 请再次输入您的新密码 */
    private EditText mEtModifyRePsd;
    /** 确认 */
    private Button mTvModifyConfirm;

    public static void start(Context context) {
        Intent starter = new Intent(context, ModifyPsdActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_update_psd);
        initView();
    }

    private void initView() {
        TitleUtil.attach(this).setBack(true)
                .setTitle("修改密码");
        mEtModifySfid = (EditText) findViewById(R.id.et_modify_sfid);
        mEtModifyPhone = (EditText) findViewById(R.id.et_modify_phone);
        mEtModifyPsd = (EditText) findViewById(R.id.et_modify_psd);
        mEtModifyRePsd = (EditText) findViewById(R.id.et_modify_re_psd);
        mTvModifyConfirm = (Button) findViewById(R.id.tv_modify_confirm);
        mTvModifyConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_modify_confirm:
                handleDate();
                break;
            default:
                break;
        }
    }

    /**
     * 处理数据
     */
    private void handleDate() {
        String sfid = mEtModifySfid.getText().toString().trim();
        String phone = mEtModifyPhone.getText().toString().trim();
        String psd = mEtModifyPsd.getText().toString().trim();
        String pePsd = mEtModifyRePsd.getText().toString().trim();
        if (TextUtils.isEmpty(sfid)) {
            ToastUtils.showShortToastSafe("请输入身份证号码");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShortToastSafe("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(psd)) {
            ToastUtils.showShortToastSafe("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(pePsd)) {
            ToastUtils.showShortToastSafe("请再次输入新密码");
            return;
        }
        if (!pePsd.equals(psd)) {
            ToastUtils.showShortToastSafe("两次输入的密码不一致");
            return;
        }
        showLoading("请稍等");
        HttpRequest.getUserService().changepassword(sfid, phone, psd)
                .compose(getBaseActivity().<ObjectModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<ObjectModel>() {
                    @Override
                    public void result(ObjectModel objectModel) {
                        ToastUtils.showShortToastSafe(objectModel.info.toString());
                        finish();
                    }
                }, new HttpError(this), new HttpComplete(this));
    }
}
