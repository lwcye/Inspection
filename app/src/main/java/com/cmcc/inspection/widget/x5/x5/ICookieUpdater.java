package com.cmcc.inspection.widget.x5.x5;

/**
 * Cookie更新器
 *
 * @author mos
 * @date 2017.04.17
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public interface ICookieUpdater {
    /**
     * 更新Cookie
     *
     * @param url url
     * @param cookie cookie
     */
    public void updateCookie(String url, String cookie);
}
