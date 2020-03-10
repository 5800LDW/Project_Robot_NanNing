package com.tecsun.tsb.network.bean.param;

import com.tecsun.tsb.network.common.Def;

/**
 * Created by _chen on 2016/10/25.
 */

public class ListParam extends IdNameParam {

    public int pageno;

    public int pagesize = Def.PAGE_SIZE;

    public ListParam(String xm, String sfzh, int pageno) {
        this.xm = xm;
        this.sfzh = sfzh;
        this.pageno = pageno;
        this.pagesize = Def.PAGE_SIZE;
    }

    public ListParam() {
    }
}
