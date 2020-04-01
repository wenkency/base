package cn.carhouse.base.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class FragmentUtils {
    // 切换Fragment
    public static void changeFragment(FragmentManager fragmentManager, int layoutId, Fragment currentFragment) {
        if (fragmentManager == null || currentFragment == null) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            transaction.hide(fragment);
        }
        // 查找
        String tag = Fragment.class.getSimpleName() + currentFragment.hashCode();
        Fragment fragmentByTag = fragmentManager.findFragmentByTag(tag);
        if (fragmentByTag == null) {
            // 添加并打TAG
            transaction.add(layoutId, currentFragment, tag);
        } else {
            transaction.show(fragmentByTag);
        }
        transaction.commitAllowingStateLoss();
    }

    public static void replaceFragment(FragmentManager fragmentManager, int layoutId, Fragment currentFragment) {
        if (fragmentManager == null || currentFragment == null) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 添加并打TAG
        transaction.replace(layoutId, currentFragment, Fragment.class.getSimpleName() + currentFragment.hashCode());
        transaction.commitAllowingStateLoss();
    }
}
