package cn.boardour.base.alert;

import android.app.Activity;
import android.view.WindowManager;

public class DimUtils {
    /**
     * 给整个屏幕添加阴影背景
     *
     * @param activity
     * @param isDim    TRUE  添加  false 不添加
     */
    public static void setWindowDim(Activity activity, boolean isDim) {
        if (null != activity) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = isDim ? .7f : 1.0f;
            if (isDim) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            } else {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
            activity.getWindow().setAttributes(lp);
        }
    }
}
