package com.hbln.inspection.feature.workarena.workininspection;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cmcc.lib_network.model.WorkTypeModel;
import com.cmcc.lib_utils.utils.TimeUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.main.MainActivity;
import com.hbln.inspection.feature.workarena.workdynamic.WorkDynamicActivity;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.hbln.inspection.ui.adapter.FragmentViewPagerAdapter;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.ui.fragment.ListFragment;
import com.hbln.inspection.utils.TitleUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkIninspectionActivity extends MVPBaseActivity<WorkIninspectionContract.View, WorkIninspectionPresenter> implements WorkIninspectionContract.View, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, RUAdapter.OnItemClickListener, View.OnClickListener {
    public static String TITLE = "派驻纪检组";
    /** 纪律审查榜 */
    private RadioButton mRvWorkIn0;
    /** 信息数量榜 */
    private RadioButton mRvWorkIn1;
    /** 外宣数量榜 */
    private RadioButton mRvWorkIn2;
    private RadioGroup mRgWorkIn;
    
    private RUAdapter<WorkTypeModel.InfoBean> mAdapter0;
    private List<WorkTypeModel.InfoBean> mList0 = new ArrayList<>();
    private ListFragment<WorkTypeModel.InfoBean> mFragment0 = new ListFragment<>();
    private RUAdapter<WorkTypeModel.InfoBean> mAdapter1;
    private List<WorkTypeModel.InfoBean> mList1 = new ArrayList<>();
    private ListFragment<WorkTypeModel.InfoBean> mFragment1 = new ListFragment<>();
    private RUAdapter<WorkTypeModel.InfoBean> mAdapter2;
    private List<WorkTypeModel.InfoBean> mList2 = new ArrayList<>();
    private ListFragment<WorkTypeModel.InfoBean> mFragment2 = new ListFragment<>();
    private ViewPager mVpWorkIn;
    /** 索引 */
    private int index = 0;
    /** 2017年12月 */
    private TextView mTvWorkInMonth;
    private String[] month;
    
    public static void start(Context context) {
        Intent starter = new Intent(context, WorkIninspectionActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected WorkIninspectionPresenter createPresenter() {
        return new WorkIninspectionPresenter();
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_in_inspection);
        initView();
        mPresenter.loadData(0, null);
        mPresenter.loadData(1, null);
        mPresenter.loadData(2, null);
    }
    
    private void initView() {
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_home, 0, 0, 0)
            .setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.start(getContext());
                }
            })
            .setColor(Color.WHITE, 255)
            .setRightDrawable(R.drawable.icon_work_dynamic, 0, 0, 0)
            .setRightClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WorkDynamicActivity.start(getContext());
                }
            })
            .setTitle(TITLE);
        mTvWorkInMonth = (TextView) findViewById(R.id.tv_work_in_month);
        mTvWorkInMonth.setOnClickListener(this);
        mTvWorkInMonth.setText(TimeUtils.getNowTimeString("yyyy年MM月"));
        mRvWorkIn0 = (RadioButton) findViewById(R.id.rv_work_in_0);
        mRvWorkIn1 = (RadioButton) findViewById(R.id.rv_work_in_1);
        mRvWorkIn2 = (RadioButton) findViewById(R.id.rv_work_in_2);
        mRgWorkIn = (RadioGroup) findViewById(R.id.rg_work_in);
        mRgWorkIn.setOnCheckedChangeListener(this);
        mVpWorkIn = (ViewPager) findViewById(R.id.vp_work_in);
        mVpWorkIn.addOnPageChangeListener(this);
        onCheckedChanged(mRgWorkIn, R.id.rv_work_in_0);
        
        initViewPager();
    }
    
    private void initViewPager() {
        mAdapter0 = new RUAdapter<WorkTypeModel.InfoBean>(getContext(), mList0, R.layout.item_work_in) {
            @Override
            protected void onInflateData(RUViewHolder holder, WorkTypeModel.InfoBean data, int position) {
                setItemData(holder, data, position);
                holder.setText(R.id.tv_item_work_in_type, "纪律数量");
                ProgressBar bar = holder.getViewById(R.id.pb_item_work_in);
                bar.setProgress((data.nums * 100 / mList0.get(0).nums));
            }
        };
        mFragment0.setAdapter(mAdapter0);
        mFragment0.setOnItemClickListener(this);
        
        mAdapter1 = new RUAdapter<WorkTypeModel.InfoBean>(getContext(), mList1, R.layout.item_work_in) {
            @Override
            protected void onInflateData(RUViewHolder holder, WorkTypeModel.InfoBean data, int position) {
                setItemData(holder, data, position);
                holder.setText(R.id.tv_item_work_in_type, "案件数量");
                ProgressBar bar = holder.getViewById(R.id.pb_item_work_in);
                bar.setProgress((data.nums * 100 / mList1.get(0).nums));
            }
        };
        mFragment1.setAdapter(mAdapter1);
        mFragment1.setOnItemClickListener(this);
        
        mAdapter2 = new RUAdapter<WorkTypeModel.InfoBean>(getContext(), mList2, R.layout.item_work_in) {
            @Override
            protected void onInflateData(RUViewHolder holder, WorkTypeModel.InfoBean data, int position) {
                setItemData(holder, data, position);
                holder.setText(R.id.tv_item_work_in_type, "外宣数量");
                ProgressBar bar = holder.getViewById(R.id.pb_item_work_in);
                bar.setProgress((data.nums * 100 / mList2.get(0).nums));
            }
        };
        mFragment2.setAdapter(mAdapter2);
        mFragment2.setOnItemClickListener(this);
        
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(mFragment0);
        fragmentList.add(mFragment1);
        fragmentList.add(mFragment2);
        mVpWorkIn.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList));
    }
    
    private void setItemData(RUViewHolder holder, WorkTypeModel.InfoBean data, int position) {
        holder.setText(R.id.tv_item_work_in_name, data.danwei);
        TextView diff = holder.getViewById(R.id.tv_item_work_in_diff);
        if (data.diff >= 0) {
            diff.setText("+" + data.diff + "");
            diff.setTextColor(Color.parseColor("#3C7F7D"));
        } else {
            diff.setText("-" + (-data.diff) + "");
            diff.setTextColor(Color.parseColor("#FF0041"));
        }
        holder.setText(R.id.tv_item_work_in_num, data.nums + "");
        
        if (data.paiming == 1) {
            holder.setBackgroundResource(R.id.iv_work_in_rank, R.drawable.img_work_in_num_0);
        } else if (data.paiming == 2) {
            holder.setBackgroundResource(R.id.iv_work_in_rank, R.drawable.img_work_in_num_1);
        } else if (data.paiming == 3) {
            holder.setBackgroundResource(R.id.iv_work_in_rank, R.drawable.img_work_in_num_2);
        } else {
            holder.setBackgroundResource(R.id.iv_work_in_rank, R.drawable.img_work_in_num_3);
            holder.setText(R.id.iv_work_in_rank, (position + 1) + "");
        }
    }
    
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rv_work_in_0:
                index = 0;
                break;
            case R.id.rv_work_in_1:
                index = 1;
                break;
            case R.id.rv_work_in_2:
                index = 2;
                break;
            default:
                break;
        }
        mVpWorkIn.setCurrentItem(index);
    }
    
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        
    }
    
    @Override
    public void onPageSelected(int position) {
        index = position;
        switch (index) {
            case 0:
                mRvWorkIn0.setChecked(true);
                break;
            case 1:
                mRvWorkIn1.setChecked(true);
                break;
            case 2:
                mRvWorkIn2.setChecked(true);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onPageScrollStateChanged(int state) {
        
    }
    
    @Override
    public void setData(WorkTypeModel data, int type) {
        data.initModel();
        if (type == 0) {
            mList0 = data.info;
            mAdapter0.setData(mList0);
        } else if (type == 1) {
            mList1 = data.info;
            mAdapter1.setData(mList1);
        } else {
            mList2 = data.info;
            mAdapter2.setData(mList2);
        }
    }
    
    @Override
    public void onItemClick(View view, int itemType, int position) {
        
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_work_in_month:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                
                int mm = Integer.valueOf(TimeUtils.getNowTimeString("MM"));
                month = new String[mm];
                for (int i = 0; i < mm; i++) {
                    month[i] = TimeUtils.getNowTimeString("yyyy年") + (i + 1) + "月";
                }
                builder.setItems(month, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvWorkInMonth.setText(month[which]);
                        String date = TimeUtils.date2String(TimeUtils.string2Date(month[which], "yyyy年MM月"), "yyyy-MM");
                        mPresenter.loadData(0, date);
                        mPresenter.loadData(1, date);
                        mPresenter.loadData(2, date);
                    }
                });
                builder.create().show();
                break;
            default:
                break;
        }
    }
}
