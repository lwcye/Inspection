package com.cmcc.inspection.feature.fortress.education;

import com.cmcc.inspection.R;
import com.cmcc.inspection.model.CellBean;
import com.cmcc.inspection.mvp.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FortressEducationPresenter extends BasePresenterImpl<FortressEducationContract.View> implements FortressEducationContract.Presenter {
    @Override
    public void loadBtnData() {
        List<CellBean> list = new ArrayList<>();
        CellBean bean;
        bean = new CellBean();
        bean.left = "文体活动";
        bean.leftResId = R.drawable.icon_fortress_btn_1;
        list.add(bean);
        bean = new CellBean();
        bean.left = "缴纳党费";
        bean.leftResId = R.drawable.icon_fortress_btn_2;
        list.add(bean);
        bean = new CellBean();
        bean.left = "群众工作";
        bean.leftResId = R.drawable.icon_fortress_btn_3;
        list.add(bean);
        bean = new CellBean();
        bean.left = "三会一课";
        bean.leftResId = R.drawable.icon_fortress_btn_0;
        list.add(bean);
//        bean = new CellBean();
//        bean.left = "支部换届";
//        bean.leftResId = R.drawable.icon_fortress_btn_4;
//        list.add(bean);
//        bean = new CellBean();
//        bean.left = "组织管理";
//        bean.leftResId = R.drawable.icon_fortress_btn_5;
//        list.add(bean);
//        bean = new CellBean();
//        bean.left = "阵地建设";
//        bean.leftResId = R.drawable.icon_fortress_btn_6;
//        list.add(bean);
//        bean = new CellBean();
//        bean.left = "全部";
//        bean.leftResId = R.drawable.icon_fortress_btn_7;
//        list.add(bean);
        getView().showBtn(list);
    }
    
    @Override
    public void loadNewsData() {
        List<CellBean> list = new ArrayList<>();
        CellBean bean;
        bean = new CellBean();
        bean.left = "强筋骨、明纪律 铸造执纪铁军";
        bean.leftResId = R.drawable.img_fortress_news_0;
        list.add(bean);
        bean = new CellBean();
        bean.left = "宣传加惩处 确保“六条禁令”落地生威";
        bean.leftResId = R.drawable.img_fortress_news_1;
        list.add(bean);
        getView().showNews(list);
    }
}
