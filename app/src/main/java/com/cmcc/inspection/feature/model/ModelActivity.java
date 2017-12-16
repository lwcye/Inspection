package com.cmcc.inspection.feature.model;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.workarena.workdynamic.WorkDynamicActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.ui.adapter.FragmentViewPagerAdapter;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;
import com.cmcc.inspection.ui.fragment.ListFragment;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.lib_network.model.ModelModel;
import com.cmcc.lib_utils.utils.ConvertUtils;
import com.cmcc.lib_utils.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 先锋模范 </p><br>
 *
 * @author lwc
 * @date 2017/12/16 19:11
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ModelActivity extends MVPBaseActivity<ModelContract.View, ModelPresenter> implements ModelContract.View, View.OnClickListener, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, RUAdapter.OnItemClickListener {
    
    private ImageView mIvImg;
    /** 先进个人 */
    private RadioButton mRbModel0;
    /** 先进集体 */
    private RadioButton mRbModel3;
    private RadioGroup mRgModel;
    private ViewPager mVpModel;
    
    private int index = 0;
    private List<ModelModel.ModelInfo> mList0 = new ArrayList<>();
    private List<ModelModel.ModelInfo> mList1 = new ArrayList<>();
    private RUAdapter<ModelModel.ModelInfo> mAdapter0;
    private RUAdapter<ModelModel.ModelInfo> mAdapter1;
    private ListFragment<ModelModel.ModelInfo> mFragment0 = new ListFragment<>();
    private ListFragment<ModelModel.ModelInfo> mFragment1 = new ListFragment<>();
    
    public static void start(Context context) {
        Intent starter = new Intent(context, ModelActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected ModelPresenter createPresenter() {
        return new ModelPresenter();
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
        initView();
        mPresenter.loadGeRenData();
        mPresenter.loadJiTiData();
    }
    
    private void initView() {
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_home, 0, 0, 0)
            .setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WorkDynamicActivity.start(getContext());
                }
            })
            
            .setTitle("先锋模范");
        mRbModel0 = (RadioButton) findViewById(R.id.rb_model_0);
        mRbModel3 = (RadioButton) findViewById(R.id.rb_model_3);
        mRgModel = (RadioGroup) findViewById(R.id.rg_model);
        mRgModel.setOnCheckedChangeListener(this);
        mVpModel = (ViewPager) findViewById(R.id.vp_model);
        mVpModel.addOnPageChangeListener(this);
        initViewPage();
    }
    
    private void initViewPage() {
        
        
        mAdapter0 = new RUAdapter<ModelModel.ModelInfo>(getContext(), mList0, R.layout.item_model) {
            @Override
            protected void onInflateData(RUViewHolder holder, ModelModel.ModelInfo data, int position) {
                setHolderData(holder, data, position, 0);
            }
        };
        mAdapter1 = new RUAdapter<ModelModel.ModelInfo>(getContext(), mList1, R.layout.item_model) {
            @Override
            protected void onInflateData(RUViewHolder holder, ModelModel.ModelInfo data, int position) {
                setHolderData(holder, data, position, 1);
            }
        };
        mFragment0.setAdapter(mAdapter0);
        mFragment1.setAdapter(mAdapter1);
        
        mFragment0.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mFragment1.setLayoutManager(new GridLayoutManager(getContext(), 2));
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 0);
            }
        };
        mFragment0.setItemDecoration(itemDecoration);
        mFragment1.setItemDecoration(itemDecoration);
        
        mFragment0.setOnItemClickListener(this);
        mFragment1.setOnItemClickListener(this);
        List<Fragment> list = new ArrayList<>();
        list.add(mFragment0);
        list.add(mFragment1);
        mVpModel.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(), list));
    }
    
    private void setHolderData(RUViewHolder holder, ModelModel.ModelInfo data, int position, int index) {
        holder.setImageNet(R.id.iv_item_model, data.pic);
        if (index == 0) {
            holder.setText(R.id.tv_item_model, data.name);
        } else {
            holder.setText(R.id.tv_item_model, data.danweitwo);
        }
        View viewById = holder.getViewById(R.id.rl_item_model);
        if (position % 2 == 0) {
            if (position % 4 == 0) {
                viewById.setRotation(-10f);
            } else {
                viewById.setRotation(10f);
            }
        } else {
            viewById.setTranslationY(ConvertUtils.dp2px(30));
            if (position % 4 == 1) {
                viewById.setRotation(10f);
            } else {
                viewById.setRotation(-10f);
            }
        }
    }
    
    @Override
    public void onClick(View v) {
        
    }
    
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_model_0:
                index = 0;
                break;
            case R.id.rb_model_3:
                index = 1;
                break;
        }
        mVpModel.setCurrentItem(index);
    }
    
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        
    }
    
    @Override
    public void onPageSelected(int position) {
        index = position;
        if (index == 0) {
            mRbModel0.setChecked(true);
        }
        if (index == 1) {
            mRbModel3.setChecked(true);
        }
    }
    
    @Override
    public void onPageScrollStateChanged(int state) {
        
    }
    
    @Override
    public void setGeRenData(ModelModel model) {
        mList0 = model.info;
        mAdapter0.setData(mList0);
    }
    
    @Override
    public void setJiTiData(ModelModel model) {
        mList1 = model.info;
        mAdapter1.setData(mList1);
    }
    
    @Override
    public void onItemClick(View view, int itemType, int position) {
        List<ModelModel.ModelInfo> list = mList0;
        if (index == 1) {
            list = mList1;
        }
        ModelDetailActivity.start(getContext(), list.get(position).id, index);
    }
}
