package com.cmcc.inspection.feature.fortress.education;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.model.CellBean;
import com.cmcc.inspection.mvp.MVPBaseFragment;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;
import com.cmcc.lib_utils.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FortressHomeFragment extends MVPBaseFragment<FortressEducationContract.View, FortressEducationPresenter> implements FortressEducationContract.View {
    
    private View view;
    private RecyclerView mRvFortressModuleBtn;
    private RecyclerView mRvFortressModuleNews;
    private RUAdapter<CellBean> mAdapterBtn;
    private RUAdapter<CellBean> mAdapterNews;
    private List<CellBean> mListBtn = new ArrayList<>();
    private List<CellBean> mListNews = new ArrayList<>();
    
    @Override
    protected FortressEducationPresenter createPresenter() {
        return new FortressEducationPresenter();
    }
    
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_fortress_education;
    }
    
    public void initView(View view) {
        mRvFortressModuleBtn = (RecyclerView) view.findViewById(R.id.rv_fortress_module_btn);
        mRvFortressModuleNews = (RecyclerView) view.findViewById(R.id.rv_fortress_module_news);
        initRecyclerView();
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
        
        mRvFortressModuleNews.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapterNews = new RUAdapter<CellBean>(getContext(), mListNews, R.layout.item_fortress_news) {
            @Override
            protected void onInflateData(RUViewHolder holder, CellBean data, int position) {
                holder.setImageView(R.id.iv_item_fortress_news, data.leftResId);
                holder.setText(R.id.tv_item_fortress_news_title, data.left);
            }
        };
        mRvFortressModuleNews.setAdapter(mAdapterNews);
    }
    
    @Override
    public void initData() {
        initView(mView);
        mPresenter.loadBtnData();
        mPresenter.loadNewsData();
    }
    
    @Override
    public void showBtn(List<CellBean> btnBean) {
        mListBtn = btnBean;
        mAdapterBtn.setData(mListBtn);
    }
    
    @Override
    public void showNews(List<CellBean> newsBean) {
        mListNews = newsBean;
        mAdapterNews.setData(mListNews);
    }
}
