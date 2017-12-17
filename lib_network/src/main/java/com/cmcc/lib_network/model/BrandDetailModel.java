package com.cmcc.lib_network.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/17 23:11
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class BrandDetailModel extends ResultModel {
    
    /**
     * info : {"id":2,"title":"1、监督执纪问责平台（办公室、案管室）","create_time":1513353600,"uid":16,"pic":null,"model":"1,4,5,3","sort":1,"jianjie":{"id":2,"ppid":2,"content":"<p><style>\n@font-face{\nfont-family:\"Times New Roman\";\n}\n\n@font-face{\nfont-family:\"宋体\";\n}\n\n@font-face{\nfont-family:\"微软雅黑\";\n}\n\n@font-face{\nfont-family:\"Tahoma\";\n}\n\n@font-face{\nfont-family:\"Symbol\";\n}\n\n@font-face{\nfont-family:\"Arial\";\n}\n\n@font-face{\nfont-family:\"黑体\";\n}\n\n@font-face{\nfont-family:\"Courier New\";\n}\n\n@font-face{\nfont-family:\"Wingdings\";\n}\n\n@font-face{\nfont-family:\"Calibri\";\n}\n\n@font-face{\nfont-family:\"仿宋_GB2312\";\n}\n\n@font-face{\nfont-family:\"仿宋\";\n}\n\np.p0{\nmargin:0pt;\nmargin-bottom:0.0001pt;\nmargin-bottom:0pt;\nmargin-top:0pt;\ntext-align:justify;\nfont-size:10.5000pt; font-family:'Calibri'; }\n@page{mso-page-border-surround-header:no;\n\tmso-page-border-surround-footer:no;}@page Section0{\nmargin-top:72.0000pt;\nmargin-bottom:72.0000pt;\nmargin-left:90.0000pt;\nmargin-right:90.0000pt;\nsize:595.3000pt 841.9000pt;\nlayout-grid:15.6000pt;\n}\ndiv.Section0{page:Section0;}<\/style><\/p><div><p class=\"p0\"><spanyes'; font-weight:normal;=\"\" font-size:18.0000pt;=\"\" font-family:'仿宋_gb2312';=\"\" background:rgb(255,255,255);=\"\" mso-shading:rgb(255,255,255);=\"\" \"=\"\">该平台与\u201c阳光扶贫\u201d监管系统深度融合，使监管系统进一步扩容、下探、增效，擦亮打响\u201c阳光扶贫\u201d平台。采取\u201c前台监督、后台执纪\u201d的形式，全面收集主体信息、事项信息、专项信息，以身份证号为索引，设置关联、异常、矛盾三种比对模式，搭建精准扶贫和粮食直补数据分析比对模型，通过对海量的扶贫资金数据进行分析，找异常、找关系，重点围绕虚假扶贫、优亲厚友、截留私分、虚报冒领等情况，实现查询、比对、分析、预警、监督五大功能，让群众在\u201c前台\u201d举报有依据可寻、纪委在\u201c后台\u201d执纪有线索可查。平台运行以来，共梳理存量线索53条，立案查处31件，追缴违纪资金400余万元，平均初核时间节省了2/3，同时平台的建立对村干部也起到很好的震慑作用。<spanyes'; font-size:10.5000pt;=\"\" font-family:'calibri';=\"\" \"=\"\"><o:p><\/o:p><\/spanyes';><\/spanyes';><\/p><\/div>","pic":null},"waixuangaojian":[],"fenguanlingdao":{"name":"贺广久","pic":"http://58.218.171.12:1003/ganbujiandu/uploads/image/20171216/d211a070c7004b57986045f04b4b082d2bbf77.jpg"},"chengbankeshi":[{"id":2,"ppid":2,"dwid":8,"zhuren":"张恒科","gugan":"李娜，陈辉","create_time":1513401582,"zhurenpic":null,"guguanshuzu":[{"name":"李娜，陈辉","pic":null}]},{"id":1,"ppid":2,"dwid":6,"zhuren":"王海涛","gugan":"王健","create_time":1513401523,"zhurenpic":null,"guguanshuzu":[{"name":"王健","pic":null}]}]}
     */
    
    public InfoBean info;
    
    public static class InfoBean {
        /**
         * id : 2
         * title : 1、监督执纪问责平台（办公室、案管室）
         * create_time : 1513353600
         * uid : 16
         * pic : null
         * model : 1,4,5,3
         * sort : 1
         * jianjie : {"id":2,"ppid":2,"content":"<p><style>\n@font-face{\nfont-family:\"Times New Roman\";\n}\n\n@font-face{\nfont-family:\"宋体\";\n}\n\n@font-face{\nfont-family:\"微软雅黑\";\n}\n\n@font-face{\nfont-family:\"Tahoma\";\n}\n\n@font-face{\nfont-family:\"Symbol\";\n}\n\n@font-face{\nfont-family:\"Arial\";\n}\n\n@font-face{\nfont-family:\"黑体\";\n}\n\n@font-face{\nfont-family:\"Courier New\";\n}\n\n@font-face{\nfont-family:\"Wingdings\";\n}\n\n@font-face{\nfont-family:\"Calibri\";\n}\n\n@font-face{\nfont-family:\"仿宋_GB2312\";\n}\n\n@font-face{\nfont-family:\"仿宋\";\n}\n\np.p0{\nmargin:0pt;\nmargin-bottom:0.0001pt;\nmargin-bottom:0pt;\nmargin-top:0pt;\ntext-align:justify;\nfont-size:10.5000pt; font-family:'Calibri'; }\n@page{mso-page-border-surround-header:no;\n\tmso-page-border-surround-footer:no;}@page Section0{\nmargin-top:72.0000pt;\nmargin-bottom:72.0000pt;\nmargin-left:90.0000pt;\nmargin-right:90.0000pt;\nsize:595.3000pt 841.9000pt;\nlayout-grid:15.6000pt;\n}\ndiv.Section0{page:Section0;}<\/style><\/p><div><p class=\"p0\"><spanyes'; font-weight:normal;=\"\" font-size:18.0000pt;=\"\" font-family:'仿宋_gb2312';=\"\" background:rgb(255,255,255);=\"\" mso-shading:rgb(255,255,255);=\"\" \"=\"\">该平台与\u201c阳光扶贫\u201d监管系统深度融合，使监管系统进一步扩容、下探、增效，擦亮打响\u201c阳光扶贫\u201d平台。采取\u201c前台监督、后台执纪\u201d的形式，全面收集主体信息、事项信息、专项信息，以身份证号为索引，设置关联、异常、矛盾三种比对模式，搭建精准扶贫和粮食直补数据分析比对模型，通过对海量的扶贫资金数据进行分析，找异常、找关系，重点围绕虚假扶贫、优亲厚友、截留私分、虚报冒领等情况，实现查询、比对、分析、预警、监督五大功能，让群众在\u201c前台\u201d举报有依据可寻、纪委在\u201c后台\u201d执纪有线索可查。平台运行以来，共梳理存量线索53条，立案查处31件，追缴违纪资金400余万元，平均初核时间节省了2/3，同时平台的建立对村干部也起到很好的震慑作用。<spanyes'; font-size:10.5000pt;=\"\" font-family:'calibri';=\"\" \"=\"\"><o:p><\/o:p><\/spanyes';><\/spanyes';><\/p><\/div>","pic":null}
         * waixuangaojian : []
         * fenguanlingdao : {"name":"贺广久","pic":"http://58.218.171.12:1003/ganbujiandu/uploads/image/20171216/d211a070c7004b57986045f04b4b082d2bbf77.jpg"}
         * chengbankeshi : [{"id":2,"ppid":2,"dwid":8,"zhuren":"张恒科","gugan":"李娜，陈辉","create_time":1513401582,"zhurenpic":null,"guguanshuzu":[{"name":"李娜，陈辉","pic":null}]},{"id":1,"ppid":2,"dwid":6,"zhuren":"王海涛","gugan":"王健","create_time":1513401523,"zhurenpic":null,"guguanshuzu":[{"name":"王健","pic":null}]}]
         */
        
        public String id;
        public String title;
        public String create_time;
        public String uid;
        public String pic;
        public String model;
        public String sort;
        public JianjieBean jianjie;
        public FenguanlingdaoBean fenguanlingdao;
        public List<?> waixuangaojian;
        public List<ChengbankeshiBean> chengbankeshi;
        
        public static class JianjieBean {
            /**
             * id : 2
             * ppid : 2
             * content :   * pic : null
             */
            
            public String id;
            public String ppid;
            public String content;
            public String pic;
        }
        
        public static class FenguanlingdaoBean {
            /**
             * name : 贺广久
             * pic : http://58.218.171.12:1003/ganbujiandu/uploads/image/20171216/d211a070c7004b57986045f04b4b082d2bbf77.jpg
             */
            
            public String name;
            public String pic;
        }
        
        public static class ChengbankeshiBean {
            /**
             * id : 2
             * ppid : 2
             * dwid : 8
             * zhuren : 张恒科
             * gugan : 李娜，陈辉
             * create_time : 1513401582
             * zhurenpic : null
             * guguanshuzu : [{"name":"李娜，陈辉","pic":null}]
             */
            
            public String id;
            public String ppid;
            public String dwid;
            public String zhuren;
            public String gugan;
            public String create_time;
            public String zhurenpic;
            public List<GuguanshuzuBean> guguanshuzu;
            
            public static class GuguanshuzuBean {
                /**
                 * name : 李娜，陈辉
                 * pic : null
                 */
                
                public String name;
                public String pic;
            }
            
            public static class ChengBan {
                public String title;
                public String name;
                public String pic;
                
                public ChengBan(String title, String name, String pic) {
                    this.title = title;
                    this.name = name;
                    this.pic = pic;
                }
            }
            
            public List<ChengBan> initList() {
                List<ChengBan> mList = new ArrayList<>();
                mList.add(new ChengBan("室主任", zhuren, zhurenpic));
                String[] names = guguanshuzu.get(0).name.split("，");
                String[] pics = new String[names.length];
                if (!TextUtils.isEmpty(guguanshuzu.get(0).pic)) {
                    pics = guguanshuzu.get(0).pic.split("，");
                }
                for (int i = 0; i < names.length; i++) {
                    mList.add(new ChengBan("业务骨干", names[i], pics[i]));
                }
                return mList;
            }
        }
    }
}
