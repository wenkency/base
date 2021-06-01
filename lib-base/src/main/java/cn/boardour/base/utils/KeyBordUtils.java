package cn.boardour.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


/**
 * 键盘工具类
 * 1. 打开键盘
 * 2. 关闭键盘
 * 3. 监听键盘打开或者关闭
 */
public class KeyBordUtils {
    /**
     * 关闭软键盘
     */
    public static void closeKeyBord(Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view instanceof TextView) {
            InputMethodManager mInputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeyBord(View view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        InputMethodManager mInputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    /**
     * 打开软键盘
     */
    public static void openKeyBord(final View view) {
        try {
            if (view != null) {
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.requestFocus();
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }, 100);
            }
        } catch (Throwable e) {
        }
    }


    /**
     * 监听软键盘关闭和打开状态
     *
     * @param activity
     * @param keyBoardListener
     */
    public static void attach(final Activity activity, final KeyBoardListener keyBoardListener) {
        final ViewGroup contentView = activity.findViewById(android.R.id.content);
        final int statusBarHeight = StateBarUtils.getStatusHeight(activity);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    public int fullDisplayHeight;
                    boolean isKeyboardVisible;

                    @Override
                    public void onGlobalLayout() {
                        ViewGroup contentView = activity.findViewById(android.R.id.content);
                        View userRootView = contentView.getChildAt(0);
                        // 计算userRootView的高度
                        Rect r = new Rect();
                        userRootView.getWindowVisibleDisplayFrame(r);
                        int displayHeight = r.bottom - r.top;

                        if (fullDisplayHeight == 0) {
                            fullDisplayHeight = displayHeight;
                            return;
                        }
                        int keyboardHeight = Math.abs(displayHeight - fullDisplayHeight);
                        if (keyboardHeight == 0) {
                            if (isKeyboardVisible) {
                                isKeyboardVisible = false;
                                if (keyBoardListener != null)
                                    keyBoardListener.keyboardShowingChanged(isKeyboardVisible);
                            }
                            return;
                        }


                        // 当前变化由，非全屏到全屏导致，此时应该更新fullDisplayHeight
                        if (keyboardHeight == statusBarHeight) {
                            fullDisplayHeight = displayHeight;
                            return;
                        }

                        if (keyBoardListener != null)
                            keyBoardListener.keyboardHeight(keyboardHeight);

                        if (!isKeyboardVisible) {
                            isKeyboardVisible = true;
                            if (keyBoardListener != null)
                                keyBoardListener.keyboardShowingChanged(isKeyboardVisible);
                        }
                    }
                });
    }

    public interface KeyBoardListener {
        void keyboardHeight(int keyboardHeight);

        void keyboardShowingChanged(boolean visible);

    }


}
