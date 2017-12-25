package com.zng.ticket_manage.znghticketmanage.bean;

import java.io.Serializable;

/**
 * Created by zqh on 2017/12/25.
 */

public class TaxRateBean implements Serializable {

    private String goods_name;
    private String tax_rate_desc;

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getTax_rate_desc() {
        return tax_rate_desc;
    }

    public void setTax_rate_desc(String tax_rate_desc) {
        this.tax_rate_desc = tax_rate_desc;
    }

    @Override
    public String toString() {
        return "TaxRateBean{" +
                "goods_name='" + goods_name + '\'' +
                ", tax_rate_desc='" + tax_rate_desc + '\'' +
                '}';
    }
}
