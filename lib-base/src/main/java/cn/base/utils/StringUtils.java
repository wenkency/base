package cn.base.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String处理工具类
 */
public class StringUtils {
    /**
     * 判断字符串是否有值
     */
    public static boolean isEmpty(String value) {
        return !TextUtils.isEmpty(value) && !"null".equalsIgnoreCase(value) && !"NaN".equalsIgnoreCase(value);
    }

    /**
     * 验证6-20位密码
     */
    public static boolean checkPassword(String password) {
        String regex = "^\\S{6,20}$";
        return Pattern.matches(regex, password);
    }

    /**
     * 检查email格式是否正确
     *
     * @param email
     * @return true正确，false错误
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码和座机号
     *
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        if (isEmpty(mobile)) {
            return false;
        }
        String regex = "(^(0[0-9]{2,3})?\\d{7,8}$|^(\\+\\d+)?1[0-9]\\d{9}$)";
        return Pattern.matches(regex, mobile);

    }

    /**
     * 验证手机号码
     *
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        String regex = "(\\+\\d+)?1[0-9]\\d{9}$";
        return Pattern.matches(regex, phone);
    }
}
