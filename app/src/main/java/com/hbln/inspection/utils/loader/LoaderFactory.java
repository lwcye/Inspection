package com.hbln.inspection.utils.loader;

/**
 * <p>加载工厂，可定制图片加载框架</p><br>
 *
 * @author lwc
 * @date 2017/3/3 10:19
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class LoaderFactory {
    /** 实体 */
    private static ILoader loader;

    /**
     * 获得加载器
     *
     * @return 加载器
     */
    public static ILoader getLoader() {
        if (loader == null) {
            synchronized (LoaderFactory.class) {
                if (loader == null) {
                    loader = new GlideLoader();
                }
            }
        }
        return loader;
    }
}
