package com.hbln.inspection.network.model;

import java.util.List;

/**
 * <p>交流体会</p><br>
 *
 * @author - lwc
 * @date - 2017/12/20
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class TiHuiModel extends ResultModel {
    public InfoBean info;

    public static class InfoBean {
        /**
         * id : 41
         * name : 第一党小组
         * pid : 0
         * sort : 1
         * lingdao : 覃 密
         * jianjie : <p><br></p>
         * renyuan : [{"id":1,"sort":0,"zhiwu":"贾汪区区委常委、区纪委书记","name":"袁邦庆"},{"id":105,"sort":0,"zhiwu":"组员","name":"卢传琛"},{"id":104,"sort":0,"zhiwu":"副组长","name":"吴军"},{"id":100,"sort":0,"zhiwu":"组员","name":"李佳根"},{"id":99,"sort":0,"zhiwu":"组员","name":"张迪"},{"id":98,"sort":0,"zhiwu":"副组长","name":"杜姣"},{"id":97,"sort":0,"zhiwu":"副组长","name":"覃密"},{"id":96,"sort":0,"zhiwu":"副组长","name":"张梅"},{"id":95,"sort":0,"zhiwu":"副组长","name":"王波"},{"id":94,"sort":0,"zhiwu":"副组长","name":"于琴"},{"id":93,"sort":0,"zhiwu":"副组长","name":"王春玲"},{"id":92,"sort":0,"zhiwu":"副组长","name":"秦灵芝"},{"id":91,"sort":0,"zhiwu":"组长","name":"马继勇"},{"id":90,"sort":0,"zhiwu":"组长","name":"汪磊"},{"id":89,"sort":0,"zhiwu":"组长","name":"段绪国"},{"id":88,"sort":0,"zhiwu":"科员","name":"徐莉"},{"id":87,"sort":0,"zhiwu":"副主任","name":"郭丽娜"},{"id":86,"sort":0,"zhiwu":"主任","name":"张宗山"},{"id":106,"sort":0,"zhiwu":"科员","name":"李立"}]
         */

        public String id;
        public String title;
        public String content;
        public String create_time;
        public String nums;
        public String times;
        public List<InfosBean> infos;

        public static class InfosBean {
            public String id;
            public String uid;
            public String content;
            public String nums;
            public String create_time;
            public String tid;
            public String pic;
            public String name;
        }
    }
}
