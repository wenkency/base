package cn.carhouse.base.ui.loading;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

/**
 * 加载类
 */
public class LoadingManager {
    public static final int NO_LAYOUT_ID = 0;
    // 加载时的页面
    public static int BASE_LOADING_LAYOUT_ID = NO_LAYOUT_ID;
    // 重试的页面
    public static int BASE_RETRY_LAYOUT_ID = NO_LAYOUT_ID;
    // 后台请求出错的页面
    public static int BASE_EMPTY_LAYOUT_ID = NO_LAYOUT_ID;
    // 空页面
    public static int BASE_DATA_ERROR_LAYOUT_ID = NO_LAYOUT_ID;


    public LoadingLayout mLoadingLayout;


    public OnLoadingListener DEFAULT_LISTENER = new OnLoadingListener() {
        @Override
        public void setRetryEvent(View retryView) {

        }
    };


    public LoadingManager(Object activityOrFragmentOrView, OnLoadingListener listener) {
        if (listener == null) {
            listener = DEFAULT_LISTENER;
        }

        ViewGroup contentParent = null;
        Context context;
        if (activityOrFragmentOrView instanceof Activity) {
            Activity activity = (Activity) activityOrFragmentOrView;
            context = activity;
            contentParent = activity.findViewById(android.R.id.content);
        } else if (activityOrFragmentOrView instanceof Fragment) {
            Fragment fragment = (Fragment) activityOrFragmentOrView;
            context = fragment.getActivity();
            contentParent = (ViewGroup) (fragment.getView().getParent());
        } else if (activityOrFragmentOrView instanceof View) {
            View view = (View) activityOrFragmentOrView;
            contentParent = (ViewGroup) (view.getParent());
            context = view.getContext();
        } else {
            throw new IllegalArgumentException("the argument's type must be Fragment or Activity: init(mContext)");
        }
        int childCount = contentParent.getChildCount();
        //get contentParent
        int index = 0;
        View oldContent;
        if (activityOrFragmentOrView instanceof View) {
            oldContent = (View) activityOrFragmentOrView;
            for (int i = 0; i < childCount; i++) {
                if (contentParent.getChildAt(i) == oldContent) {
                    index = i;
                    break;
                }
            }
        } else {
            oldContent = contentParent.getChildAt(0);
        }


        LoadingLayout loadingLayout = new LoadingLayout(context);


        // setup loading,retry,empty layout
        setupLoadingLayout(listener, loadingLayout);
        setupRetryLayout(listener, loadingLayout);
        setupDataErrorLayout(listener, loadingLayout);
        setupEmptyLayout(listener, loadingLayout);
        // callback
        listener.setRetryEvent(loadingLayout.getRetryView());
        listener.setDataErrorEvent(loadingLayout.getDataErrorView());
        listener.setLoadingEvent(loadingLayout.getLoadingView());
        listener.setEmptyEvent(loadingLayout.getEmptyView());

        //setup content layout
        ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
        // 1. 移除旧的
        contentParent.removeView(oldContent);
        // 旧的添加到自定义布局
        loadingLayout.setContentView(oldContent);
        // 2. 添加新的到原来位置
        contentParent.addView(loadingLayout, index, lp);

        mLoadingLayout = loadingLayout;
        // 设置监听
        mLoadingLayout.setOnLoadingListener(listener);

    }

    private void setupEmptyLayout(OnLoadingListener listener, LoadingLayout loadingAndRetryLayout) {
        if (listener.isSetEmptyLayout()) {
            int layoutId = listener.generateEmptyLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setEmptyView(layoutId);
            } else {
                loadingAndRetryLayout.setEmptyView(listener.generateEmptyLayout());
            }
        } else {
            if (BASE_EMPTY_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setEmptyView(BASE_EMPTY_LAYOUT_ID);
        }
    }

    private void setupLoadingLayout(OnLoadingListener listener, LoadingLayout loadingAndRetryLayout) {
        if (listener.isSetLoadingLayout()) {
            int layoutId = listener.generateLoadingLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setLoadingView(layoutId);
            } else {
                loadingAndRetryLayout.setLoadingView(listener.generateLoadingLayout());
            }
        } else {
            if (BASE_LOADING_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setLoadingView(BASE_LOADING_LAYOUT_ID);
        }
    }

    private void setupRetryLayout(OnLoadingListener listener, LoadingLayout loadingAndRetryLayout) {
        if (listener.isSetRetryLayout()) {
            int layoutId = listener.generateRetryLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setLoadingView(layoutId);
            } else {
                loadingAndRetryLayout.setLoadingView(listener.generateRetryLayout());
            }
        } else {
            if (BASE_RETRY_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setRetryView(BASE_RETRY_LAYOUT_ID);
        }
    }

    private void setupDataErrorLayout(OnLoadingListener listener, LoadingLayout loadingAndRetryLayout) {
        if (listener.isSetDataErrorLayout()) {
            int layoutId = listener.generateDataErrorLayoutId();
            if (layoutId != NO_LAYOUT_ID) {
                loadingAndRetryLayout.setDataErrorView(layoutId);
            } else {
                loadingAndRetryLayout.setDataErrorView(listener.generateRetryLayout());
            }
        } else {
            if (BASE_RETRY_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setDataErrorView(BASE_DATA_ERROR_LAYOUT_ID);
        }
    }

    public static LoadingManager generate(Object activityOrFragment, OnLoadingListener listener) {
        return new LoadingManager(activityOrFragment, listener);
    }

    public void showLoading() {
        mLoadingLayout.showLoading();
    }

    public void showRetry() {
        mLoadingLayout.showRetry();
    }

    public void showDataError() {
        mLoadingLayout.showDataError();
    }

    public void showContent() {
        mLoadingLayout.showContent();
    }

    public void showEmpty() {
        mLoadingLayout.showEmpty();
    }


    // 区分是网络错误还是请求数据返回数据解析错误
    public void setNetOrData(Context context) {
        try {
            if (isNet(context)) {
                showDataError();
            } else {
                showRetry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean isNet(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                if (networkInfo.isConnected()) {
                    if (networkInfo.getTypeName().equals("WIFI")) {

                    }
                    if (networkInfo.getTypeName().equals("MOBILE")) {

                    }
                    return true;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
