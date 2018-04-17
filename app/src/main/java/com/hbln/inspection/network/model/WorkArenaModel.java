package com.hbln.inspection.network.model;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/26 21:49
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class WorkArenaModel extends ResultModel {
    
    /**
     * info : {"lanmu1":{"lanmu":"纪律审查","zongshu":33,"paiming":1,"nums":9},"lanmu2":{"lanmu":"信息数量","zongshu":8,"paiming":8,"nums":0},"lanmu3":{"lanmu":"外宣数量","zongshu":12,"paiming":7,"nums":0}}
     * keshi : 纪检监察一室
     */
    
    public InfoBean info;
    public String keshi;
    
    public static class InfoBean {
        public Lanmu1Bean lanmu1;
        public Lanmu1Bean lanmu2;
        public Lanmu1Bean lanmu3;
        
        public static class Lanmu1Bean {
            /**
             * lanmu : 纪律审查
             * zongshu : 33
             * paiming : 1
             * nums : 9
             */
            
            public String lanmu;
            public int zongshu;
            public int paiming;
            public int nums;
        }
    }
}
