package cn.carhouse.base.ui.loading;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


/**
 * 加载布局
 */
public class LoadingLayout extends FrameLayout {
    private View mLoadingView;
    private View mRetryView;
    private View mDataErrorView;
    private View mContentView;
    private View mEmptyView;
    private LayoutInflater mInflater;
    private Context context;
    private static final String TAG = LoadingLayout.class.getSimpleName();

    private OnLoadingListener onLoadingListener;

    private LoadState mLoadState = LoadState.IDLE;

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }


    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadingLayout(Context context) {
        this(context, null);
    }


    public void showLoading() {
        if (isMainThread()) {
            showView(LoadState.LOADING, mLoadingView);
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                showView(LoadState.LOADING, mLoadingView);
            }
        });
    }

    public void showDataError() {
        if (isMainThread()) {
            showView(LoadState.DATA_ERROR, mDataErrorView);
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                showView(LoadState.DATA_ERROR, mDataErrorView);
            }
        });
    }

    public void showRetry() {
        if (isMainThread()) {
            showView(LoadState.RETRY, mRetryView);
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                showView(LoadState.RETRY, mRetryView);
            }
        });
    }

    public void showContent() {
        if (isMainThread()) {
            showView(LoadState.CONTENT, mContentView);
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                showView(LoadState.CONTENT, mContentView);
            }
        });
    }

    public void showEmpty() {
        if (isMainThread()) {
            showView(LoadState.EMPTY, mEmptyView);
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                showView(LoadState.EMPTY, mEmptyView);
            }
        });
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private void showView(LoadState loadState, View view) {
        if (view == null) {
            return;
        }
        if (mLoadState == loadState) {
            return;
        }
        mLoadState = loadState;
        if (view == mLoadingView) {
            mLoadingView.setVisibility(View.VISIBLE);
            if (mRetryView != null)
                mRetryView.setVisibility(View.GONE);
            if (mDataErrorView != null)
                mDataErrorView.setVisibility(View.GONE);
            if (mContentView != null)
                mContentView.setVisibility(View.GONE);
            if (mEmptyView != null)
                mEmptyView.setVisibility(View.GONE);
        } else if (view == mRetryView) {
            mRetryView.setVisibility(View.VISIBLE);
            if (mDataErrorView != null)
                mDataErrorView.setVisibility(View.GONE);
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
            if (mContentView != null)
                mContentView.setVisibility(View.GONE);
            if (mEmptyView != null)
                mEmptyView.setVisibility(View.GONE);
        } else if (view == mContentView) {
            mContentView.setVisibility(View.VISIBLE);
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
            if (mRetryView != null)
                mRetryView.setVisibility(View.GONE);
            if (mEmptyView != null)
                mEmptyView.setVisibility(View.GONE);
            if (mDataErrorView != null)
                mDataErrorView.setVisibility(View.GONE);

        } else if (view == mEmptyView) {
            mEmptyView.setVisibility(View.VISIBLE);
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
            if (mRetryView != null)
                mRetryView.setVisibility(View.GONE);
            if (mContentView != null)
                mContentView.setVisibility(View.GONE);
            if (mDataErrorView != null)
                mDataErrorView.setVisibility(View.GONE);
        } else if (view == mDataErrorView) {
            mDataErrorView.setVisibility(View.VISIBLE);
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
            if (mRetryView != null)
                mRetryView.setVisibility(View.GONE);
            if (mContentView != null)
                mContentView.setVisibility(View.GONE);
            if (mEmptyView != null)
                mEmptyView.setVisibility(View.GONE);
        }
        // 状态回调监听
        if (onLoadingListener != null) {
            onLoadingListener.onLoadingChanged(loadState, this);
        }
    }

    public View setContentView(int layoutId) {
        return setContentView(mInflater.inflate(layoutId, this, false));
    }

    public View setLoadingView(int layoutId) {
        View view = setLoadingView(mInflater.inflate(layoutId, this, false));
        return view;
    }

    public View setEmptyView(int layoutId) {
        return setEmptyView(mInflater.inflate(layoutId, this, false));
    }

    public View setRetryView(int layoutId) {
        return setRetryView(mInflater.inflate(layoutId, this, false));
    }

    public View setDataErrorView(int layoutId) {
        return setDataErrorView(mInflater.inflate(layoutId, this, false));
    }

    public View setLoadingView(View view) {
        View loadingView = mLoadingView;
        if (loadingView != null) {
            Log.w(TAG, "you have already set a loading view and would be instead of this new one.");
        }
        removeView(loadingView);
        addView(view);
        mLoadingView = view;
        return mLoadingView;
    }

    public View setEmptyView(View view) {
        View emptyView = mEmptyView;
        if (emptyView != null) {
            Log.w(TAG, "you have already set a empty view and would be instead of this new one.");
        }
        removeView(emptyView);
        addView(view);
        mEmptyView = view;
        return mEmptyView;
    }

    public View setRetryView(View view) {
        View retryView = mRetryView;
        if (retryView != null) {
            Log.w(TAG, "you have already set a retry view and would be instead of this new one.");
        }
        removeView(mRetryView);
        addView(view);
        mRetryView = view;
        return mRetryView;

    }

    public View setDataErrorView(View view) {
        View dataErrorView = mDataErrorView;
        if (dataErrorView != null) {
            Log.w(TAG, "you have already set a dataErrorView view and would be instead of this new one.");
        }
        removeView(dataErrorView);
        addView(view);
        mDataErrorView = view;
        return mDataErrorView;

    }

    public View setContentView(View view) {
        View contentView = mContentView;
        if (contentView != null) {
            Log.w(TAG, "you have already set a retry view and would be instead of this new one.");
        }
        removeView(contentView);
        addView(view);
        mContentView = view;
        return mContentView;
    }

    public View getRetryView() {
        return mRetryView;
    }

    public View getDataErrorView() {
        return mDataErrorView;
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public View getContentView() {
        return mContentView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }


    public void setOnLoadingListener(OnLoadingListener onLoadingListener) {
        this.onLoadingListener = onLoadingListener;
    }
}
