package com.cmcc.inspection.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.lib_network.model.ManagerModel;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/12/15
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ManagerDialog extends AlertDialog implements View.OnClickListener {
    /** 确认 */
    private Button mBtnBeizhuOk;
    private ManagerModel.InfoBean mInfoBean;
    private int position;
    private View mVDialogShadow;
    private TextView mTvManagerName;
    private TextView mTvManagerZhiwu;
    private TextView mTvManagerDanwei;
    
    public ManagerDialog(@NonNull Context context) {
        super(context);
    }
    
    public ManagerDialog(@NonNull Context context, ManagerModel.InfoBean infoBean, int position) {
        super(context);
        mInfoBean = infoBean;
        this.position = position;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_manager);
        initView();
    }
    
    private void initView() {
        mTvManagerName = (TextView) findViewById(R.id.tv_manager_name);
        mTvManagerZhiwu = (TextView) findViewById(R.id.tv_manager_zhiwu);
        mTvManagerDanwei = (TextView) findViewById(R.id.tv_manager_danwei);
        mBtnBeizhuOk = (Button) findViewById(R.id.btn_beizhu_ok);
        if (mBtnBeizhuOk != null) {
            mBtnBeizhuOk.setOnClickListener(this);
        }
    
        mTvManagerName.setText(mInfoBean.renyuan.get(position).name);
        mTvManagerZhiwu.setText(mInfoBean.renyuan.get(position).zhiwu);
        mTvManagerDanwei.setText(mInfoBean.name);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_beizhu_ok:
                cancel();
                break;
            default:
                break;
        }
    }
}
