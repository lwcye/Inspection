package com.cmcc.inspection.feature.regular;


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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.main.MainActivity;
import com.cmcc.inspection.feature.workarena.workdynamic.WebViewContentActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.ui.adapter.FragmentViewPagerAdapter;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;
import com.cmcc.inspection.ui.fragment.ListFragment;
import com.cmcc.inspection.utils.ClickUtils;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.lib_network.model.RegularModel;
import com.cmcc.lib_utils.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 制度规范 </p><br>
 *
 * @author lwc
 * @date 2017/12/16 16:56
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class RegularActivity extends MVPBaseActivity<RegularContract.View, RegularPresenter> implements RegularContract.View, View.OnClickListener, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, TextView.OnEditorActionListener, RUAdapter.OnItemClickListener {
    
    /** 中央 */
    private RadioButton mRbRagular0;
    /** 省 */
    private RadioButton mRbRagular1;
    /** 市 */
    private RadioButton mRbRagular2;
    /** 区 */
    private RadioButton mRbRagular3;
    private RadioGroup mRgRagular;
    /** 请输入内容... */
    private EditText mEtRegulerSeach;
    private ImageView mIvEtSearch;
    private ViewPager mVpRegular;
    
    private int index = 0;
    private List<RegularModel.RegularInfo> mList0 = new ArrayList<>();
    private List<RegularModel.RegularInfo> mList1 = new ArrayList<>();
    private List<RegularModel.RegularInfo> mList2 = new ArrayList<>();
    private List<RegularModel.RegularInfo> mList3 = new ArrayList<>();
    
    private RUAdapter<RegularModel.RegularInfo> mAdapter0;
    private RUAdapter<RegularModel.RegularInfo> mAdapter1;
    private RUAdapter<RegularModel.RegularInfo> mAdapter2;
    private RUAdapter<RegularModel.RegularInfo> mAdapter3;
    
    private ListFragment<RegularModel.RegularInfo> mFragment0 = new ListFragment<>();
    private ListFragment<RegularModel.RegularInfo> mFragment1 = new ListFragment<>();
    private ListFragment<RegularModel.RegularInfo> mFragment2 = new ListFragment<>();
    private ListFragment<RegularModel.RegularInfo> mFragment3 = new ListFragment<>();
    
    @Override
    protected RegularPresenter createPresenter() {
        return new RegularPresenter();
    }
    
    public static void start(Context context) {
        Intent starter = new Intent(context, RegularActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular);
        initView();
        mPresenter.loadData(0, "");
        mPresenter.loadData(1, "");
        mPresenter.loadData(2, "");
        mPresenter.loadData(3, "");
    }
    
    private void initView() {
        TitleUtil.attach(this)
            .setBack(true)
            .setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.start(getContext());
                }
            })
            .setTitle("制度规定");
        
        mRbRagular0 = (RadioButton) findViewById(R.id.rb_ragular_0);
        mRbRagular1 = (RadioButton) findViewById(R.id.rb_ragular_1);
        mRbRagular2 = (RadioButton) findViewById(R.id.rb_ragular_2);
        mRbRagular3 = (RadioButton) findViewById(R.id.rb_ragular_3);
        mRgRagular = (RadioGroup) findViewById(R.id.rg_ragular);
        mRgRagular.setOnCheckedChangeListener(this);
        mEtRegulerSeach = (EditText) findViewById(R.id.et_search);
        mEtRegulerSeach.setOnEditorActionListener(this);
        mIvEtSearch = (ImageView) findViewById(R.id.iv_search);
        mIvEtSearch.setOnClickListener(this);
        mVpRegular = (ViewPager) findViewById(R.id.vp_regular);
        mVpRegular.addOnPageChangeListener(this);
        
        initViewPager();
    }
    
    private void initViewPager() {
        List<Fragment> list = new ArrayList<>();
        
        
        mList0.add(new RegularModel.RegularInfo());
        mList0.add(new RegularModel.RegularInfo());
        mList0.add(new RegularModel.RegularInfo());
        mAdapter0 = new RUAdapter<RegularModel.RegularInfo>(getContext(), mList0, R.layout.item_regular) {
            @Override
            protected void onInflateData(RUViewHolder holder, RegularModel.RegularInfo data, int position) {
                holderView(holder, data, position);
            }
        };
        mAdapter1 = new RUAdapter<RegularModel.RegularInfo>(getContext(), mList1, R.layout.item_regular) {
            @Override
            protected void onInflateData(RUViewHolder holder, RegularModel.RegularInfo data, int position) {
                holderView(holder, data, position);
            }
        };
        mList2.add(new RegularModel.RegularInfo());
        mList2.add(new RegularModel.RegularInfo());
        mList2.add(new RegularModel.RegularInfo());
        mAdapter2 = new RUAdapter<RegularModel.RegularInfo>(getContext(), mList2, R.layout.item_regular) {
            @Override
            protected void onInflateData(RUViewHolder holder, RegularModel.RegularInfo data, int position) {
                holderView(holder, data, position);
            }
        };
        mAdapter3 = new RUAdapter<RegularModel.RegularInfo>(getContext(), mList3, R.layout.item_regular) {
            @Override
            protected void onInflateData(RUViewHolder holder, RegularModel.RegularInfo data, int position) {
                holderView(holder, data, position);
            }
        };
        mFragment0.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mFragment1.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mFragment2.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mFragment3.setLayoutManager(new GridLayoutManager(getContext(), 3));
        
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 0);
            }
        };
        mFragment0.setItemDecoration(itemDecoration);
        mFragment1.setItemDecoration(itemDecoration);
        mFragment2.setItemDecoration(itemDecoration);
        mFragment3.setItemDecoration(itemDecoration);
        
        mFragment0.setAdapter(mAdapter0);
        mFragment1.setAdapter(mAdapter1);
        mFragment2.setAdapter(mAdapter2);
        mFragment3.setAdapter(mAdapter3);
        
        mFragment0.setOnItemClickListener(this);
        mFragment1.setOnItemClickListener(this);
        mFragment2.setOnItemClickListener(this);
        mFragment3.setOnItemClickListener(this);
        
        list.add(mFragment0);
        list.add(mFragment1);
        list.add(mFragment2);
        list.add(mFragment3);
        
        mVpRegular.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(), list));
    }
    
    private void holderView(RUViewHolder holder, RegularModel.RegularInfo data, int position) {
        holder.setText(R.id.tv_item_regular, data.title);
        if (!TextUtils.isEmpty(data.pic)) {
            holder.setImageNet(R.id.iv_item_regular, data.pic);
        }
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                mPresenter.loadData(index, mEtRegulerSeach.getText().toString().trim());
                break;
            default:
                break;
        }
    }
    
    @Override
    public void setData(RegularModel regularModel, int type) {
        switch (type) {
            case 0:
                mList0 = regularModel.info;
                mFragment0.setData(mList0);
                LogUtils.e(mList0.size());
                break;
            case 1:
                mList1 = regularModel.info;
                mFragment1.setData(mList1);
                LogUtils.e(mList1.size());
                break;
            case 2:
                mList2 = regularModel.info;
                mFragment2.setData(mList2);
                LogUtils.e(mList2.size());
                break;
            case 3:
                mList3 = regularModel.info;
                mFragment3.setData(mList3);
                LogUtils.e(mList3.size());
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_ragular_0:
                index = 0;
                mVpRegular.setCurrentItem(0);
                break;
            case R.id.rb_ragular_1:
                index = 1;
                mVpRegular.setCurrentItem(1);
                break;
            case R.id.rb_ragular_2:
                index = 2;
                mVpRegular.setCurrentItem(2);
                break;
            case R.id.rb_ragular_3:
                index = 3;
                mVpRegular.setCurrentItem(3);
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
                mRbRagular0.setChecked(true);
                break;
            case 1:
                mRbRagular1.setChecked(true);
                break;
            case 2:
                mRbRagular2.setChecked(true);
                break;
            case 3:
                mRbRagular3.setChecked(true);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void onPageScrollStateChanged(int state) {
        
    }
    
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (ClickUtils.isFastClick()) {
            return true;
        }
        mPresenter.loadData(index, mEtRegulerSeach.getText().toString().trim());
        return true;
    }
    
    @Override
    public void onItemClick(View view, int itemType, int position) {
        List<RegularModel.RegularInfo> list;
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
            default:
                list = mList3;
                break;
        }
        WebViewContentActivity.start(getContext(), list.get(position).id, WebViewContentActivity.TYPE_REGULAR);
        WebViewContentActivity.hasComment = false;
        WebViewContentActivity.hasZan = false;
    }
}
