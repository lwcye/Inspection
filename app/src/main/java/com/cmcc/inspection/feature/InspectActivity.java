package com.cmcc.inspection.feature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cmcc.inspection.R;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.lib_common.base.BaseActivity;

import java.util.List;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/11/10
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class InspectActivity extends BaseActivity implements View.OnClickListener {
    /** 轨迹申报 */
    private RadioButton mRbInspect0;
    /** 线上家访 */
    private RadioButton mRbInspect1;
    private RadioGroup mRgInspect;
    private RecyclerView mRvInspect;
    private RUAdapter<Integer> mAdapter;
    private List<Integer> mList;
    public static void start(Context context) {
        Intent starter = new Intent(context, InspectActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect);
        initView();
    }

    private void initView() {
        mRbInspect0 = (RadioButton) findViewById(R.id.rb_inspect_0);
        mRbInspect0.setOnClickListener(this);
        mRbInspect1 = (RadioButton) findViewById(R.id.rb_inspect_1);
        mRbInspect1.setOnClickListener(this);
        mRgInspect = (RadioGroup) findViewById(R.id.rg_inspect);
        mRgInspect.setOnClickListener(this);
        mRvInspect = (RecyclerView) findViewById(R.id.rv_inspect);
        mRvInspect.setOnClickListener(this);

        initRecylerView();
    }

    private void initRecylerView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_inspect_0:
                break;
            case R.id.rb_inspect_1:
                break;
            case R.id.rg_inspect:
                break;
            case R.id.rv_inspect:
                break;
            default:
                break;
        }
    }
}
