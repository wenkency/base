package cn.carhouse.base.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * Fragment管理类
 */
public class FragmentUtils {
    /**
     * 切换Fragment
     */
    public final static void changeFragment(FragmentManager manager, int layoutId, Fragment currentFragment) {
        if (manager == null || currentFragment == null) {
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        // 先隐藏
        List<Fragment> fragments = manager.getFragments();
        for (Fragment fragment : fragments) {
            transaction.hide(fragment);
        }
        // 查找
        String tag = getTag(currentFragment);
        Fragment fragmentByTag = manager.findFragmentByTag(tag);
        if (fragmentByTag == null) {
            // 添加并打TAG
            transaction.add(layoutId, currentFragment, tag);
        } else {
            transaction.show(fragmentByTag);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 布局替换成Fragment
     */
    public final static void replaceFragment(FragmentManager manager, int layoutId, Fragment fragment) {
        if (manager == null || fragment == null) {
            return;
        }
        manager.beginTransaction()
                .replace(layoutId, fragment, getTag(fragment))
                .commitAllowingStateLoss();
    }

    /**
     * 移除所有fragment
     */
    public final static void removeAllFragments(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                removeAllFragments(fragment.getChildFragmentManager());
                removeFragment(fragmentManager, fragment);
            }
        }
    }

    /**
     * 移除指定的fragment
     */
    public final static void removeFragment(FragmentManager manager, Fragment destFragment) {
        if (manager == null || destFragment == null) {
            return;
        }
        if (destFragment != null && destFragment.isRemoving()) {
            return;
        }
        manager.beginTransaction()
                .remove(destFragment)
                .commitAllowingStateLoss();
    }

    /**
     * 给每个Fragment打个Tag
     */
    public final static String getTag(Fragment fragment) {
        return fragment.getClass().getSimpleName() + fragment.hashCode();
    }
}
