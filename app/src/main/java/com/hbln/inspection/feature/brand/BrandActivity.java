package com.hbln.inspection.feature.brand;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cmcc.lib_utils.utils.ViewUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.brand.branddetail.BrandDetailActivity;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.hbln.inspection.network.model.BrandModel;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.utils.TitleUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * <p> 品牌创新 </p><br>
 *
 * @author lwc
 * @date 2017/12/16 23:01
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class BrandActivity extends MVPBaseActivity<BrandContract.View, BrandPresenter> implements BrandContract.View, View.OnClickListener, RUAdapter.OnItemClickListener {
    private RecyclerView mRvBrand;
    private List<BrandModel.Info> mList = new ArrayList<>();
    private RUAdapter<BrandModel.Info> mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, BrandActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected BrandPresenter createPresenter() {
        return new BrandPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        initView();
        mPresenter.loadData();
    }

    private void initView() {
        TitleUtil.attach(this)
                .setBack(true)
                .setTitle("品牌创新");
        mRvBrand = (RecyclerView) findViewById(R.id.rv_brand);

        initRecylerView();
    }

    private void initRecylerView() {

        mAdapter = new RUAdapter<BrandModel.Info>(getContext(), mList, R.layout.item_brand) {
            @Override
            protected void onInflateData(RUViewHolder holder, BrandModel.Info data, final int position) {
                int resId = R.drawable.icon_brand_tab_3;
                if (position % 3 == 0) {
                    resId = R.drawable.icon_brand_tab_0;
                }
                if (position % 3 == 1) {
                    resId = R.drawable.icon_brand_tab_1;
                }
                if (position % 3 == 2) {
                    resId = R.drawable.icon_brand_tab_2;
                }
                TextView viewById = holder.getViewById(R.id.dtv_item_brand);
                if (position % 6 == 2) {
                    ViewUtils.setTextDrawable(viewById, resId, 0, 0, 0, getContext());
                } else if (position % 6 == 3) {
                    ViewUtils.setTextDrawable(viewById, 0, 0, resId, 0, getContext());
                } else {
                    ViewUtils.setTextDrawable(viewById, 0, resId, 0, 0, getContext());
                }

                if (position % 9 == 0) {
                    holder.setBackgroundColor(R.id.dtv_item_brand, Color.parseColor("#5D5F6E"));
                } else if (position % 9 == 1) {
                    holder.setBackgroundColor(R.id.dtv_item_brand, Color.parseColor("#D4BD91"));
                } else if (position % 9 == 2) {
                    holder.setBackgroundColor(R.id.dtv_item_brand, Color.parseColor("#B2D8E5"));
                } else if (position % 9 == 3) {
                    holder.setBackgroundColor(R.id.dtv_item_brand, Color.parseColor("#47B5CE"));
                } else if (position % 9 == 4) {
                    holder.setBackgroundColor(R.id.dtv_item_brand, Color.parseColor("#DA6290"));
                } else if (position % 9 == 5) {
                    holder.setBackgroundColor(R.id.dtv_item_brand, Color.parseColor("#5D5F6E"));
                } else if (position % 9 == 6) {
                    holder.setBackgroundColor(R.id.dtv_item_brand, Color.parseColor("#D4BD91"));
                } else if (position % 9 == 7) {
                    holder.setBackgroundColor(R.id.dtv_item_brand, Color.parseColor("#DA6290"));
                } else if (position % 9 == 8) {
                    holder.setBackgroundColor(R.id.dtv_item_brand, Color.parseColor("#47B5CE"));
                }

                holder.setText(R.id.dtv_item_brand, data.title);
            }
        };
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 6 == 2) {
                    return 2;
                }
                if (position % 6 == 3) {
                    return 2;
                }
                return 1;
            }
        });
        mRvBrand.setLayoutManager(gridLayoutManager);
        mRvBrand.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClickListener(this);
        mRvBrand.setAdapter(mAdapter);
    }


    @Override
    public void setData(BrandModel data) {
        mList = data.info;
        mAdapter.setData(mList);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(View view, int itemType, int position) {
        BrandDetailActivity.start(getContext(), mList.get(position).id);
    }
}

