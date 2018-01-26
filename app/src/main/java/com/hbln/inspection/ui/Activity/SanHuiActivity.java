package com.hbln.inspection.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.cmcc.lib_common.base.BaseActivity;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.FortressHomeModel;
import com.cmcc.lib_network.model.WebViewModel;
import com.cmcc.lib_utils.utils.LogUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.workarena.workdynamic.WebViewContentActivity;
import com.hbln.inspection.ui.adapter.FragmentViewPagerAdapter;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.ui.fragment.ListFragment;
import com.hbln.inspection.utils.TitleUtil;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/12/19
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class SanHuiActivity extends BaseActivity implements RUAdapter.OnItemClickListener {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<FortressHomeModel.InfoBean> mList0 = new ArrayList<>();
    private RUAdapter<FortressHomeModel.InfoBean> mAdapter0;
    private ListFragment<FortressHomeModel.InfoBean> mFragment0 = new ListFragment<>();

    private List<FortressHomeModel.InfoBean> mList1 = new ArrayList<>();
    private RUAdapter<FortressHomeModel.InfoBean> mAdapter1;
    private ListFragment<FortressHomeModel.InfoBean> mFragment1 = new ListFragment<>();

    private List<FortressHomeModel.InfoBean> mList2 = new ArrayList<>();
    private RUAdapter<FortressHomeModel.InfoBean> mAdapter2;
    private ListFragment<FortressHomeModel.InfoBean> mFragment2 = new ListFragment<>();

    private List<FortressHomeModel.InfoBean> mList3 = new ArrayList<>();
    private RUAdapter<FortressHomeModel.InfoBean> mAdapter3;
    private ListFragment<FortressHomeModel.InfoBean> mFragment3 = new ListFragment<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, SanHuiActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_hui);
        initView();

        loadData(0);
        loadData(1);
        loadData(2);
        loadData(3);
    }

    private void initView() {
        TitleUtil.attach(this)
                .setBack(true)
                .setTitle("三会一课");
        mTabLayout = (TabLayout) findViewById(R.id.tl_sanhui);
        mViewPager = (ViewPager) findViewById(R.id.vp_san_hui);
        mTabLayout.addTab(mTabLayout.newTab().setText("支部党员大会"));
        mTabLayout.addTab(mTabLayout.newTab().setText("党支部委员会"));
        mTabLayout.addTab(mTabLayout.newTab().setText("党小组会"));
        mTabLayout.addTab(mTabLayout.newTab().setText("党课"));
        mTabLayout.setupWithViewPager(mViewPager);

        initViewPager();

    }

    private void initViewPager() {
        mAdapter0 = new RUAdapter<FortressHomeModel.InfoBean>(getContext(), mList0, R.layout.item_fortress_img) {
            @Override
            protected void onInflateData(RUViewHolder holder, FortressHomeModel.InfoBean data, int position) {
                setItemData(holder, data);
            }
        };
        mAdapter0.setOnItemClickListener(this);
        mFragment0.setAdapter(mAdapter0);

        mAdapter1 = new RUAdapter<FortressHomeModel.InfoBean>(getContext(), mList1, R.layout.item_fortress_img) {
            @Override
            protected void onInflateData(RUViewHolder holder, FortressHomeModel.InfoBean data, int position) {
                setItemData(holder, data);
            }
        };
        mAdapter1.setOnItemClickListener(this);
        mFragment1.setAdapter(mAdapter1);

        mAdapter2 = new RUAdapter<FortressHomeModel.InfoBean>(getContext(), mList2, R.layout.item_fortress_img) {
            @Override
            protected void onInflateData(RUViewHolder holder, FortressHomeModel.InfoBean data, int position) {
                setItemData(holder, data);
            }
        };
        mAdapter2.setOnItemClickListener(this);
        mFragment2.setAdapter(mAdapter2);

        mAdapter3 = new RUAdapter<FortressHomeModel.InfoBean>(getContext(), mList3, R.layout.item_fortress_img) {
            @Override
            protected void onInflateData(RUViewHolder holder, FortressHomeModel.InfoBean data, int position) {
                setItemData(holder, data);
            }
        };
        mAdapter3.setOnItemClickListener(this);
        mFragment3.setAdapter(mAdapter3);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(mFragment0);
        fragments.add(mFragment1);
        fragments.add(mFragment2);
        fragments.add(mFragment3);
        FragmentViewPagerAdapter fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments);
        List<String> titles = new ArrayList<>();
        titles.add("支部党员大会");
        titles.add("党支部委员会");
        titles.add("党小组会");
        titles.add("党课");
        fragmentViewPagerAdapter.setTitles(titles);
        mViewPager.setAdapter(fragmentViewPagerAdapter);
    }

    private void setItemData(RUViewHolder holder, FortressHomeModel.InfoBean data) {
        holder.setText(R.id.tv_item_fortress, data.title);
        holder.setText(R.id.tv_item_fortress_date, data.times);
        if (TextUtils.isEmpty(data.author)) {
            holder.setVisibility(R.id.tv_item_fortress_name, View.GONE);
        } else {
            holder.setVisibility(R.id.tv_item_fortress_name, View.VISIBLE);
            holder.setText(R.id.tv_item_fortress_name, data.author);
        }
        if (TextUtils.isEmpty(data.pic)) {
            holder.setVisibility(R.id.iv_item_fortress, View.GONE);
        } else {
            holder.setVisibility(R.id.iv_item_fortress, View.VISIBLE);
            holder.setImageNet(R.id.iv_item_fortress, data.pic);
        }
    }

    private void loadData(final int index) {
        showLoading("");
        String type;
        if (index == 0) {
            type = "支部党员大会";
        } else if (index == 1) {
            type = "支部委员会";
        } else if (index == 2) {
            type = "党小组会";
        } else {
            type = "党课";
        }
        HttpRequest.getFortressService().sanhuiyikelist(type)
                .compose(NetWorkInterceptor.<FortressHomeModel>retrySessionCreator())
                .compose(getBaseActivity().<FortressHomeModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<FortressHomeModel>() {
                    @Override
                    public void result(FortressHomeModel objectModel) {
                        setSanHuiData(index, objectModel);
                    }
                }, new HttpError(this) {
                    @Override
                    public void call(Throwable throwable) {
                    }
                }, new HttpComplete(this));
    }

    private void setSanHuiData(int index, FortressHomeModel objectModel) {
        LogUtils.e("index", index, "size", objectModel.info.size());
        if (index == 0) {
            mList0 = objectModel.info;
            mAdapter0.setData(mList0);
        } else if (index == 1) {
            mList1 = objectModel.info;
            mAdapter1.setData(mList1);
        } else if (index == 2) {
            mList2 = objectModel.info;
            mAdapter2.setData(mList2);
        } else {
            mList3 = objectModel.info;
            mAdapter3.setData(mList3);
        }
    }


    @Override
    public void onItemClick(View view, int itemType, int position) {
        if (mViewPager.getCurrentItem() == 0) {
            WebViewContentActivity.start(getContext(), mList0.get(position).id, WebViewModel.TYPE_FORTRESS_SAN_HUI);
        } else if (mViewPager.getCurrentItem() == 1) {
            WebViewContentActivity.start(getContext(), mList1.get(position).id, WebViewModel.TYPE_FORTRESS_SAN_HUI);
        } else if (mViewPager.getCurrentItem() == 2) {
            WebViewContentActivity.start(getContext(), mList2.get(position).id, WebViewModel.TYPE_FORTRESS_SAN_HUI);
        } else {
            WebViewContentActivity.start(getContext(), mList3.get(position).id, WebViewModel.TYPE_FORTRESS_SAN_HUI);
        }
    }
}
