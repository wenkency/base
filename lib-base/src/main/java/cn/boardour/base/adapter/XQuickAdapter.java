package cn.boardour.base.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * RecyclerView、ListView、GridView通用的适配器
 * ListView、GridView请调用：{@link #setListView(boolean)}
 */

public abstract class XQuickAdapter<T> extends XBaseAdapter {
    protected Activity mActivity;
    protected List<T> mData;
    private int mLayoutId;
    private XQuickMultiSupport<T> mSupport;
    private int mPosition;
    // 粘附的Key
    private static final int STACK_KEY = 10000;
    /**
     * 是不是ListView
     */
    private boolean isListView;

    public XQuickAdapter(Activity activity, List<T> data, int layoutId) {
        this.mActivity = activity;
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.mLayoutId = layoutId;
    }

    public XQuickAdapter(Activity activity, int layoutId) {
        this(activity, null, layoutId);
    }

    public XQuickAdapter(Activity activity, List<T> data, XQuickMultiSupport<T> support) {
        this.mActivity = activity;
        this.mData = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.mSupport = support;
    }

    public XQuickAdapter(Activity context, XQuickMultiSupport<T> support) {
        this(context, null, support);
    }

    public Activity getAppActivity() {
        return mActivity;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        isListView = true;
        XQuickViewHolder holder;
        if (convertView == null) {
            int layoutId = mLayoutId;
            // 多条目的
            if (mSupport != null) {
                layoutId = mSupport.getLayoutId(mData.get(position), position);
            }
            // 创建ViewHolder
            holder = createListHolder(parent, layoutId);
        } else {
            holder = (XQuickViewHolder) convertView.getTag();
            // 防止失误，还要判断
            if (mSupport != null) {
                int layoutId = mSupport.getLayoutId(mData.get(position), position);
                // 如果布局ID不一样，又重新创建
                if (layoutId != holder.getLayoutId()) {
                    // 创建ViewHolder
                    holder = createListHolder(parent, layoutId);
                }
            }

        }
        // 绑定View的数据
        convert(holder, mData.get(position), position);
        return holder.itemView;
    }

    /**
     * 创建ListView的Holer
     */
    @NonNull
    private XQuickViewHolder createListHolder(ViewGroup parent, int layoutId) {
        XQuickViewHolder holder;
        View itemView = LayoutInflater.from(mActivity).inflate(layoutId, parent, false);
        holder = new XQuickViewHolder(itemView, layoutId);
        itemView.setTag(holder);
        return holder;
    }

    /**
     * ViewType的数量
     */
    @Override
    public int getViewTypeCount() {
        // 多条目的
        if (mSupport != null) {
            return mSupport.getViewTypeCount() + super.getViewTypeCount();
        }
        return super.getViewTypeCount();
    }

    /**
     * 这个方法是共用的
     */
    @Override
    public int getItemViewType(int position) {
        mPosition = position;
        // 多条目的
        if (mSupport != null) {
            return mSupport.getItemViewType(mData.get(position), position);
        }
        return super.getItemViewType(position);
    }


    // RecyclerView=================================================================================
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        isListView = false;
        // 如果是多条目，viewType就是布局ID
        View view;
        if (mSupport != null) {
            Object tagPosition = parent.getTag(STACK_KEY);
            int layoutId = mSupport.getLayoutId(mData.get(mPosition), mPosition);
            // 滚动布局
            if (tagPosition != null) {
                int position = (int) tagPosition;
                layoutId = mSupport.getLayoutId(mData.get(position), position);
            }
            view = LayoutInflater.from(mActivity).inflate(layoutId, parent, false);
        } else {
            view = LayoutInflater.from(mActivity).inflate(mLayoutId, parent, false);
        }

        XQuickViewHolder holder = new XQuickViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof XQuickViewHolder) {
            convert((XQuickViewHolder) holder, mData.get(position), position);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (mSupport == null || recyclerView == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            // 如果设置合并单元格就占用SpanCount那个多个位置
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 优先处理这个
                    int spanSize = mSupport.getSpanSize(mData.get(position), position);
                    if (spanSize != 0) {
                        return spanSize;
                    }
                    if (mSupport.isSpan(mData.get(position))) {
                        return gridLayoutManager.getSpanCount();
                    } else if (spanSizeLookup != null) {
                        return spanSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        try {
            if (mSupport == null) {
                return;
            }
            int position = holder.getLayoutPosition();
            // 如果设置合并单元格
            if (mSupport.isSpan(mData.get(position))) {
                ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                    p.setFullSpan(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // RecyclerView=================================================================================


    /**
     * 绑定View的数据
     */
    protected abstract void convert(XQuickViewHolder holder, T item, int position);


    //==========================================数据相关================================================
    public void add(int index, T elem) {
        if (mData != null && mData.size() > 0) {
            mData.add(index, elem);
            if (!isListView) {
                notifyItemInserted(index);
                return;
            }
        } else {
            if (!isListView) {
                add(elem);
                return;
            }
        }
        if (isListView) {
            notifyListDataSetChanged();
        }
    }

    public void add(T elem) {
        if (elem != null) {
            mData.add(elem);
        }
        if (isListView) {
            notifyListDataSetChanged();
            return;
        }
        notifyItemInserted(getDataSize() - 1);
    }


    public void addAll(List<T> data) {
        if (data == null || data.size() <= 0) {
            return;
        }
        mData.addAll(data);
        if (isListView) {
            notifyListDataSetChanged();
            return;
        }
        notifyDataSetChanged();
    }

    public void addAll(int index, List<T> data) {
        if (data == null || data.size() <= 0) {
            return;
        }
        if (mData != null && mData.size() > 0) {
            mData.addAll(index, data);
        } else {
            mData.addAll(data);
        }
        if (isListView) {
            notifyListDataSetChanged();
            return;
        }
        notifyDataSetChanged();
    }


    public void addFirst(T elem) {
        mData.add(0, elem);
        if (isListView) {
            notifyListDataSetChanged();
            return;
        }
        notifyItemInserted(0);
    }

    public void set(T oldElem, T newElem) {
        int index = mData.indexOf(oldElem);
        if (index == -1) {
            return;
        }
        set(index, newElem);
        if (isListView) {
            notifyListDataSetChanged();
            return;
        }
        notifyItemChanged(index);
    }

    public void set(int index, T elem) {
        mData.set(index, elem);
        if (isListView) {
            notifyListDataSetChanged();
            return;
        }
        notifyItemChanged(index);
    }

    public void remove(T elem) {
        int index = mData.indexOf(elem);
        if (index == -1) {
            return;
        }
        mData.remove(elem);
        if (isListView) {
            notifyListDataSetChanged();
            return;
        }
        notifyItemRemoved(index);
    }

    public T remove(int index) {
        T item = mData.remove(index);
        if (item == null) {
            return null;
        }
        if (isListView) {
            notifyListDataSetChanged();
            return item;
        }
        notifyItemRemoved(index);
        return item;
    }

    public void removeAll(List<T> elem) {
        if (elem != null && elem.size() > 0) {
            mData.removeAll(elem);
        }
        if (isListView) {
            notifyListDataSetChanged();
            return;
        }
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        mData.clear();
        if (elem != null && elem.size() > 0) {
            mData.addAll(elem);
        }
        if (isListView) {
            notifyListDataSetChanged();
            return;
        }
        notifyDataSetChanged();
    }

    /**
     * 位置交换
     */
    public void swap(int fromPosition, int toPosition) {
        Collections.swap(mData, fromPosition, toPosition);
        if (isListView) {
            notifyListDataSetChanged();
            return;
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 清除
     */
    public void clear() {
        mData.clear();
        if (isListView) {
            notifyListDataSetChanged();
            return;
        }
        notifyDataSetChanged();
    }


    public boolean contains(T elem) {
        return mData.contains(elem);
    }


    /**
     * 获取集合
     */
    public List<T> getData() {
        return mData;
    }

    /**
     * 获取集合的长度
     */
    public int getDataSize() {
        return mData.size();
    }

    /**
     * 获取集合第一个元素
     */
    public T getFirst() {
        if (mData != null && mData.size() > 0) {
            return mData.get(0);
        }
        return null;
    }

    /**
     * 获取集合最后一个元素
     */
    public T getLast() {
        if (mData != null && mData.size() > 0) {
            return mData.get(mData.size() - 1);
        }
        return null;
    }

    public boolean isListView() {
        return isListView;
    }

    public void setListView(boolean isListView) {
        this.isListView = isListView;
    }

    /**
     * 是不是最后一个条目
     */
    public boolean isLast(int position) {
        return position == mData.size() - 1;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param value 虚拟像素
     * @return 像素
     */
    public int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 打开Activity
     */
    public final void startActivity(Class<? extends Activity> clazz) {
        startActivity(clazz, null);
    }

    /**
     * 打开Activity
     */
    public final void startActivity(Class<?> clazz, @Nullable Bundle options) {
        if (mActivity == null) {
            return;
        }
        Intent intent = new Intent(mActivity, clazz);
        if (options != null) {
            intent.putExtras(options);
        }
        mActivity.startActivity(intent);
    }
}
