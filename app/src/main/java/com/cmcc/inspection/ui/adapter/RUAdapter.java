package com.cmcc.inspection.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cmcc.inspection.R;
import com.cmcc.lib_common.utils.loader.LoaderFactory;

import java.util.List;

/**
 * RecyclerView的通用适配器(RecyclerView Universal Adapter)
 *
 * @author mos
 * @date 2017.02.27
 * @note T为数据类型的模板
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public abstract class RUAdapter<T> extends RecyclerView.Adapter {
    /** 普通数据类型 */
    public static final int ITEM_TYPE_NORMAL = 0;
    /** 空数据类型 */
    public static final int ITEM_TYPE_EMPTY = 1;
    /** 加载更多类型 */
    public static final int ITEM_TYPE_LOAD_MORE = 2;
    
    /** 数据的数组 */
    private List<T> mData;
    /** 布局id */
    private int mLayoutId;
    /** 上下文 */
    private Context mContext;
    /** 监听item点击事件 */
    private OnItemClickListener mOnItemClickListener;
    /** 数据为空时的布局id */
    private int mDataEmptyLayoutId = R.layout.layout_empty;
    /** 是否数据为空 */
    private boolean mIsDataEmpty;
    /** 加载更多的布局id */
    private int mLoadMoreLayoutId = 0;
    /** 加载更多监听 */
    private OnLoadMoreListener mLoadMoreListener;
    private boolean isLoadMore = false;
    private RecyclerView.ViewHolder mLoadMoreHolder;
    
    /**
     * 数据适配器构造函数
     *
     * @param context 上下文
     * @param data 数据列表
     * @param layoutId 资源id
     */
    public RUAdapter(Context context, List<T> data, int layoutId) {
        mData = data;
        mLayoutId = layoutId;
        mContext = context;
    }
    
    @Override
    public int getItemViewType(int position) {
        if (mIsDataEmpty && mDataEmptyLayoutId != 0) {
            // 数据为空
            return ITEM_TYPE_EMPTY;
        }
        
        int total = getItemCount();
        if (total - 1 == position && mLoadMoreLayoutId != 0) {
            // 加载更多
            return ITEM_TYPE_LOAD_MORE;
        }
        
        // 普通数据
        return ITEM_TYPE_NORMAL;
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final RecyclerView.ViewHolder holder;
        
        if (viewType == ITEM_TYPE_EMPTY) {
            // 数据为空时的ViewHolder
            holder = RUViewHolder.getHolder(this, mContext, parent, mDataEmptyLayoutId);
            
        } else if (viewType == ITEM_TYPE_LOAD_MORE) {
            // 加载更多时的ViewHolder
            holder = RUViewHolder.getHolder(this, mContext, parent, mLoadMoreLayoutId);
            mLoadMoreHolder = holder;
        } else {
            holder = RUViewHolder.getHolder(this, mContext, parent, mLayoutId);
            
            if (mOnItemClickListener != null) {
                // 设置整行点击监听
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, holder.getItemViewType(), pos);
                    }
                });
            }
        }
        
        return holder;
    }
    
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RUViewHolder ruHolder = (RUViewHolder) holder;
        int itemType = holder.getItemViewType();
        
        if (itemType == ITEM_TYPE_EMPTY) {
            // 填充空数据的布局
            onInflateEmptyLayout(ruHolder);
        } else if (itemType == ITEM_TYPE_LOAD_MORE) {
            // 填充加载更多布局
            onInflateLoadMoreLayout(ruHolder);
        } else {
            // 填充普通数据布局
            onInflateData(ruHolder, mData.get(position), position);
        }
    }
    
    @Override
    public int getItemCount() {
        int count;
        if (mData == null || mData.size() == 0) {
            // 数据为空
            if (mDataEmptyLayoutId != 0) {
                count = 1;
            } else {
                count = 0;
            }
            mIsDataEmpty = true;
            
        } else {
            // 数据不为空
            if (mLoadMoreLayoutId != 0) {
                // 显示加载更多
                count = mData.size() + 1;
            } else {
                count = mData.size();
            }
            mIsDataEmpty = false;
        }
        
        return count;
    }
    
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        
        // 监听滚动
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                
                if (mLoadMoreLayoutId == 0 || mLoadMoreListener == null) {
                    
                    return;
                }
                
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    // 线性管理器的处理
                    LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
                    int totalItemCount = recyclerView.getAdapter().getItemCount();
                    int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                    int visibleItemCount = recyclerView.getChildCount();
                    
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {
                        if (!isLoadMore)
                            mLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }
    
    /**
     * 在指定位置添加数据
     *
     * @param position 索引位置
     * @param item 子数据
     */
    public void addData(int position, T item) {
        mData.add(position, item);
        notifyItemInserted(position);
    }
    
    /**
     * 在数据尾部添加数据
     *
     * @param item 子数据
     */
    public void addDataLast(T item) {
        int position = mData.size();
        mData.add(position, item);
        notifyItemInserted(position);
    }
    
    /**
     * 在指定位置删除数据
     *
     * @param position 索引位置
     */
    public void removeData(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }
    
    /**
     * 删除子数据
     *
     * @param item 子数据
     */
    public void removeData(T item) {
        int i = mData.indexOf(item);
        if (i > 0) {
            mData.remove(i);
            notifyItemRemoved(i);
        }
    }
    
    /**
     * 设置item的监听事件
     *
     * @param onItemClickListener 监听器
     */
    public RUAdapter setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        
        return this;
    }
    
    /**
     * 给ViewHolder填充数据
     *
     * @param holder ViewHolder
     * @param data 数据
     * @param position 数据位置
     */
    protected abstract void onInflateData(RUViewHolder holder, T data, int position);
    
    /**
     * 给ViewHolder填充空数据的布局
     *
     * @param holder ViewHolder
     * @note 可选择实现此方法，给空数据的布局设置提示
     */
    protected void onInflateEmptyLayout(RUViewHolder holder) {
        ImageView imageView = holder.getViewById(R.id.iv_empty);
        if (imageView != null) {
            LoaderFactory.getLoader().loadAssetsGif(imageView, "null_data.gif");
        }
    }
    
    /**
     * 给ViewHolder填充加载更多的布局
     *
     * @param holder ViewHolder
     * @note 可选择实现此方法，给加载更多的布局设置提示
     */
    protected void onInflateLoadMoreLayout(RUViewHolder holder) {
    }
    
    /**
     * View被缓存后的回调,必须调用父方法，因为实现了onItemClick
     *
     * @param holder 缓存view的holder对象
     * @param view view
     * @param resId view对应的资源id
     * @note 1. 此函数在第一次加载view(holder.getViewById)时被调用。
     * 2. 由于ConvertView可以重复利用，故其中的view只需要添加一次监听即可。
     * 子类可以重写此函数，在第一次加载该view的时候，给它添加相应的监听事件。
     */
    protected void onViewCached(final RUViewHolder holder, View view, int resId) {
    }
    
    /**
     * 设置数据
     *
     * @param data 数据
     */
    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }
    
    /**
     * 追加数据
     *
     * @param data 数据
     */
    public void appendData(List<T> data) {
        if (mData == null) {
            mData = data;
        } else {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }
    
    /**
     * 设置空数据时的布局
     *
     * @param layoutId 布局id
     * @return adapter对象
     */
    public RUAdapter setDataEmptyLayoutId(int layoutId) {
        mDataEmptyLayoutId = layoutId;
        
        return this;
    }
    
    /**
     * 设置加载更多的布局
     *
     * @param layoutId 布局id
     * @return adapter对象
     */
    public RUAdapter setLoadMoreLayoutId(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        
        return this;
    }
    
    /**
     * 设置加载更多监听
     *
     * @param onLoadMoreListener 加载更多监听
     * @return adapter对象
     */
    public RUAdapter setLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mLoadMoreListener = onLoadMoreListener;
        
        return this;
    }
    
    /**
     * 加载更多监听
     */
    public interface OnLoadMoreListener {
        /**
         * 加载更多
         */
        public void onLoadMore();
    }
    
    /**
     * item的监听接口
     */
    public interface OnItemClickListener {
        /**
         * 项目被点击
         *
         * @param view 视图
         * @param itemType 类型(参见ITEM_TYPE_NORMAL等)
         * @param position 位置
         */
        void onItemClick(View view, int itemType, int position);
    }
}
