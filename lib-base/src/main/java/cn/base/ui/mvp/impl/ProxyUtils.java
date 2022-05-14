package cn.base.ui.mvp.impl;

public class ProxyUtils {
    /**
     * 处理代理异常
     * Class<?>[] interfaces = view.getClass().getInterfaces();
     * Class<?>[] classes = new Class[interfaces.length + 1];
     * classes[0] = IView.class;
     * System.arraycopy(interfaces, 0, classes, 1, interfaces.length);
     */
    public static Class<?>[] getProxyClass(Object object, Class proxyClass) {
        Class<?>[] interfaces = object.getClass().getInterfaces();
        Class<?>[] classes = new Class[interfaces.length + 1];
        classes[0] = proxyClass;
        System.arraycopy(interfaces, 0, classes, 1, interfaces.length);
        return classes;
    }
}
