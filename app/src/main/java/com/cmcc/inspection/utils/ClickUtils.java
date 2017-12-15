package com.cmcc.inspection.utils;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/15 23:48
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ClickUtils {
    public static long timeTemp;
    
    public static boolean isFastClick() {
        if (System.currentTimeMillis() - timeTemp > 500) {
            timeTemp = System.currentTimeMillis();
            return false;
        }
        return true;
    }
}
