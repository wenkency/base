package cn.boardour.base.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.lang.reflect.Field;

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
    public final static void closeKeyBord(Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view instanceof TextView) {
            InputMethodManager mInputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    public final static void fixInputMethodManagerLeak(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field field = null;
        Object objGet = null;
        for (int i = 0; i < arr.length; i++) {
            try {
                String param = arr[i];
                field = imm.getClass().getDeclaredField(param);
                if (field.isAccessible() == false) {
                    field.setAccessible(true);
                }
                objGet = field.get(imm);
                if (objGet != null && objGet instanceof View) {
                    View view = (View) objGet;
                    // 被InputMethodManager持有引用的context是想要目标销毁的
                    if (view.getContext() == activity) {
                        field.set(imm, null); // 置空,破坏掉path to gc节点
                    } else {
                        break;
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭软键盘
     */
    public final static void closeKeyBord(View view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        InputMethodManager mInputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    /**
     * 打开软键盘
     */
    public final static void openKeyBord(final View view) {
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
    public final static void attach(final Activity activity, final KeyBoardListener keyBoardListener) {
        final ViewGroup contentView = activity.findViewById(android.R.id.content);
        final int statusBarHeight = getStatusHeight(activity);
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

    /**
     * 获得状态栏的高度
     */
    private final static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

}
