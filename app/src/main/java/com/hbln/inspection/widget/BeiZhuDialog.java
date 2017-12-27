package com.hbln.inspection.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.hbln.inspection.R;
import com.hbln.inspection.utils.BaiduMapUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
public class BeiZhuDialog extends AlertDialog implements View.OnClickListener {
    private View.OnClickListener mOnClickListener;
    private EditText mEtBeizhuName;
    private TextView mTvBeizhuLoc;
    /** 确认 */
    private Button mBtnBeizhuOk;
    /** 取消 */
    private Button mBtnBeizhuCancel;

    public BeiZhuDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_beizhu);
        initView();
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    private void initView() {
        mEtBeizhuName = (EditText) findViewById(R.id.et_beizhu_name);
        mBtnBeizhuOk = (Button) findViewById(R.id.btn_beizhu_ok);
        mBtnBeizhuCancel = (Button) findViewById(R.id.btn_beizhu_cancel);
        mTvBeizhuLoc = (TextView) findViewById(R.id.tv_beizhu_loc);
        if (mBtnBeizhuOk != null) {
            mBtnBeizhuOk.setOnClickListener(this);
        }
        if (mBtnBeizhuCancel != null) {
            mBtnBeizhuCancel.setOnClickListener(this);
        }
        BaiduMapUtils.getInstance().beginLocation(null, false, new BaiduMapUtils.OnLocationListener() {
            @Override
            public void locationListener(BDLocation location) {
                Observable.just(location)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<BDLocation>() {
                            @Override
                            public void call(BDLocation bdLocation) {
                                mTvBeizhuLoc.setText("位置：" + bdLocation.getAddrStr());
                            }
                        });
            }
        });
    }

    public String getBeiZhu() {
        return mEtBeizhuName.getText().toString().trim();
    }
    /**
     * ok的监听
     *
     * @param onClickListener
     */
    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_beizhu_ok:
                cancel();
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(v);
                }
                break;
            case R.id.btn_beizhu_cancel:
                cancel();
                break;
            default:
                break;
        }
    }
}
