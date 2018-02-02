package com.hbln.inspection.feature.fortress.education;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmcc.lib_common.utils.loader.LoaderFactory;
import com.cmcc.lib_network.model.FortressHomeModel;
import com.cmcc.lib_network.model.WebViewModel;
import com.cmcc.lib_utils.utils.ViewUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.workarena.workdynamic.WebViewContentActivity;
import com.hbln.inspection.model.CellBean;
import com.hbln.inspection.mvp.MVPBaseFragment;
import com.hbln.inspection.ui.Activity.ListActivity;
import com.hbln.inspection.ui.Activity.SanHuiActivity;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FortressHomeFragment extends MVPBaseFragment<FortressHomeContract.View, FortressHomePresenter> implements FortressHomeContract.View, RUAdapter.OnItemClickListener {
    private View view;
    private RecyclerView mRvFortressModuleBtn;
    private Banner mBannerFortressImage;
    private RecyclerView mRvFortressModuleNews;
    private RUAdapter<CellBean> mAdapterBtn;
    private RUAdapter<FortressHomeModel.InfoBean> mAdapterNews;
    private List<CellBean> mListBtn = new ArrayList<>();
    private List<FortressHomeModel.InfoBean> mListNews = new ArrayList<>();
    private List<FortressHomeModel.InfoBean> mListBanner = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected FortressHomePresenter createPresenter() {
        return new FortressHomePresenter();
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_fortress_home;
    }

    @Override
    public void initView(View view) {
        mBannerFortressImage = (Banner) view.findViewById(R.id.banner_fortress_image);
        mRvFortressModuleBtn = (RecyclerView) view.findViewById(R.id.rv_fortress_module_btn);
        mRvFortressModuleNews = (RecyclerView) view.findViewById(R.id.rv_fortress_module_news);
        initRecyclerView();
        initBanner();
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        if (mBannerFortressImage != null) {
            mBannerFortressImage.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (mBannerFortressImage != null) {
            mBannerFortressImage.stopAutoPlay();
        }
    }

    private void initBanner() {
        // 设置图片加载器
        mBannerFortressImage.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object o, ImageView imageView) {
                if (o == null || TextUtils.isEmpty(o.toString())) {
                    LoaderFactory.getLoader().loadResource(imageView, R.drawable.img_shcool_item_img);
                } else {
                    LoaderFactory.getLoader().loadNet(imageView, o.toString());
                }
            }
        });
        // 设置Banner样式
        mBannerFortressImage.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        mBannerFortressImage.setIndicatorGravity(BannerConfig.CENTER);
        mBannerFortressImage.setDelayTime(6000);
        //设置指示器位置（当banner模式中有指示器时）
        mBannerFortressImage.setIndicatorGravity(BannerConfig.CENTER);
        // 点击监听
        mBannerFortressImage.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                WebViewContentActivity.start(getContext(), mListBanner.get(position).id, WebViewModel.TYPE_FORTRESS_SAN_HUI);
            }
        });
    }

    private void initRecyclerView() {
        mRvFortressModuleBtn.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mAdapterBtn = new RUAdapter<CellBean>(getContext(), mListBtn, R.layout.item_fortress_btn) {
            @Override
            protected void onInflateData(RUViewHolder holder, CellBean data, int position) {
                TextView btn = holder.getViewById(R.id.tv_item_fortress_btn);
                btn.setText(data.left);
                ViewUtils.setTextDrawable(btn, 0, data.leftResId, 0, 0, getContext());
            }
        };
        mRvFortressModuleBtn.setHasFixedSize(true);
        mRvFortressModuleBtn.setAdapter(mAdapterBtn);
        mAdapterBtn.setOnItemClickListener(new RUAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemType, int position) {
                if (position == 3) {
                    SanHuiActivity.start(getContext());
                } else {
                    ListActivity.start(getContext(), position);
                }
            }
        });

        mRvFortressModuleNews.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvFortressModuleNews.setNestedScrollingEnabled(false);

        mAdapterNews = new RUAdapter<FortressHomeModel.InfoBean>(getContext(), mListNews, R.layout.item_fortress_news) {
            @Override
            protected void onInflateData(RUViewHolder holder, FortressHomeModel.InfoBean data, int position) {
                holder.setImageNet(R.id.iv_item_fortress_news, data.picpath);
                holder.setText(R.id.tv_item_fortress_news_title, data.title);
                holder.setText(R.id.tv_item_fortress_news_name, data.zhibuname);
                holder.setText(R.id.tv_item_fortress_news_date, data.times);
            }
        };
        mAdapterNews.setOnItemClickListener(this);
        mRvFortressModuleNews.setAdapter(mAdapterNews);
    }

    @Override
    public void initData() {
        initView(mView);
        mPresenter.loadBtnData();
        mPresenter.loadWenTiData();
        mPresenter.loadBannerData();
    }

    @Override
    public void setWenTiData(FortressHomeModel homeModel) {
        mListNews = homeModel.info;
        mAdapterNews.setData(mListNews);
    }

    @Override
    public void setBannerData(FortressHomeModel homeModel) {
        mListBanner = homeModel.info;
        images = new ArrayList<>();
        titles = new ArrayList<>();
        if (homeModel.info != null && homeModel.info.size() > 0) {
            for (FortressHomeModel.InfoBean infoBean : homeModel.info) {
                images.add(infoBean.picpath);
                titles.add(infoBean.title);
            }
        }
        // 设置图片集合
        mBannerFortressImage.setImages(images);
        mBannerFortressImage.setBannerTitles(titles);
        // 开始下载
        mBannerFortressImage.start();
    }

    @Override
    public void showBtn(List<CellBean> btnBean) {
        mListBtn = btnBean;
        mAdapterBtn.setData(mListBtn);
    }

    @Override
    public void onItemClick(View view, int itemType, int position) {
        WebViewContentActivity.start(getContext(), mListNews.get(position).id, WebViewModel.TYPE_FORTRESS_HOME);
    }
}
