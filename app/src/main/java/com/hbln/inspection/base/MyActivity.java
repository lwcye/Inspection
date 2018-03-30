package com.hbln.inspection.base;

import com.cmcc.lib_common.base.BaseActivity;
import com.tencent.stat.StatService;

/**
 * <p>基类</p><br>
 *
 * @author - lwc
 * @date - 2018/3/30
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class MyActivity extends BaseActivity{
    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }
}
