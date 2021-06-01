package cn.boardour.base.alert;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;


/**
 * 自定义的Dialog
 */

public class QuickDialog extends Dialog {
    private QuickViewHelper mViewHelper;
    protected View mContentView;
    protected int widthPixels;
    protected int heightPixels;
    protected int mContentWidth;

    QuickDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        heightPixels = context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 根据布局ID设置文本信息
     */
    public QuickDialog setText(int viewId, CharSequence text) {
        if (mViewHelper != null) {
            mViewHelper.setText(viewId, text);
        }
        return this;
    }

    /**
     * 根据布局ID设置点击事件
     */
    public QuickDialog setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        if (mViewHelper != null) {
            mViewHelper.setOnClickListener(viewId, onClickListener);
        }
        return this;
    }


    @Override
    public void dismiss() {
        closeKeyboard();
        super.dismiss();
    }


    /**
     * 关闭软键盘
     */
    private void closeKeyboard() {
        View view = getCurrentFocus();
        if (view instanceof TextView) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    public void apply(QuickBuilder.QuickParams params) {
        // 1.设置布局
        if (params.mLayoutId != 0) {
            mViewHelper = new QuickViewHelper(getContext(), params.mLayoutId);
        }
        if (params.mContentView != null) {
            mViewHelper = new QuickViewHelper(params.mContentView);
        }
        if (mViewHelper == null) {
            throw new IllegalArgumentException("请调用setContentView方法设置布局");
        }
        mContentView = mViewHelper.getContentView();
        onViewCreated(mContentView);
        setContentView(mContentView);
        // 2.设置文本
        int textSize = params.mTextArray.size();
        for (int i = 0; i < textSize; i++) {
            mViewHelper.setText(params.mTextArray.keyAt(i), params.mTextArray.valueAt(i));
        }
        // 3.设置点击
        int clickSize = params.mClickArray.size();
        for (int i = 0; i < clickSize; i++) {
            mViewHelper.setOnClickListener(params.mClickArray.keyAt(i), params.mClickArray.valueAt(i));
        }

        // 4.设置Window
        Window window = getWindow();
        // 位置
        window.setGravity(params.mGravity);
        // 动画
        if (params.mAnimation != 0) {
            window.setWindowAnimations(params.mAnimation);
        }
        // 宽高
        WindowManager.LayoutParams lp = window.getAttributes();
        mContentWidth = lp.width = (int) (params.mWidth * params.mScale);
        lp.height = params.mHeight;
        window.setAttributes(lp);
        // 设置Window背景
        if (params.mIsSetBg) {
            if (params.mBgDrawable != null) {
                window.setBackgroundDrawable(params.mBgDrawable);
            } else {
                final GradientDrawable bg = new GradientDrawable();
                int radius = dp2px(getContext(), params.mBgRadius);
                // 1 2 3 4(顺时针)
                bg.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});
                bg.setColor(params.mBgColor);
                window.setBackgroundDrawable(bg);
            }
        }

        // 设置背景是否模糊
        if (!params.mIsDimEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                window.setDimAmount(0f);
            }
        }

        // 设置外面能不能点击
        setCancelable(params.mCancelable);
        setCanceledOnTouchOutside(params.mCancelableTouchOutside);
        // 设置事件监听
        setOnCancelListener(params.mOnCancelListener);
        setOnDismissListener(params.mOnDismissListener);
        setOnKeyListener(params.mOnKeyListener);
    }


    protected void onViewCreated(View contentView) {

    }

    @Override
    public void show() {
        super.show();
    }

    public void show(View view, int gravity, int offsetX, int offsetY, boolean isResetHeight) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0] + offsetX;
        // 窗口居中
        if (gravity == Gravity.CENTER) {
            x = (widthPixels - mContentWidth) / 2;
        }
        int y = location[1] - QuickUtils.getStatusBarHeight(getContext()) + view.getHeight() + offsetY;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = gravity;
        params.x = x;
        params.y = y;
        if (isResetHeight) {
            params.height = heightPixels - y - QuickUtils.getStatusBarHeight(getContext());
        }
        window.setAttributes(params);
        this.show();
    }

    /**
     * 显示在View的下面
     *
     * @param isResetHeight 重新设置高度，针对特殊的显示：如上面背景不模糊这种场景
     */
    public void show(View view, boolean isResetHeight) {
        show(view, Gravity.TOP | Gravity.START, 0, 0, isResetHeight);
    }

    /**
     * 显示在View的下面
     */
    public void show(View view) {
        show(view, false);
    }

    /**
     * 显示在View正中间
     */
    public void showViewCenter(View view, int offsetX, int offsetY, boolean isResetHeight) {
        int x = (mContentWidth - view.getWidth()) / 2 + offsetX;
        show(view, Gravity.TOP | Gravity.START, -x, dp2px(getContext(), offsetY), isResetHeight);
    }

    /**
     * 显示在View正中间
     */
    public void showViewCenter(View view, boolean isResetHeight) {
        showViewCenter(view, 0, 0, isResetHeight);
    }

    /**
     * 显示在View正中间
     */
    public void showViewCenter(View view) {
        showViewCenter(view, false);
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


}
