package com.ytxd.spp.model;

import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.Table;

/**
 * Created by XY on 2017/6/15.
 */

@Table("shopping_cart")
public class LocalShoppingCartM extends BaseSQLiteBean {
    public static String CARTCODE = "code";

    @NotNull
    public String code;
    @NotNull
    public ShoppingCartM shoppingCartM;

    public LocalShoppingCartM(String code, ShoppingCartM shoppingCartM) {
        this.code = code;
        this.shoppingCartM = shoppingCartM;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ShoppingCartM getShoppingCartM() {
        return shoppingCartM;
    }

    public void setShoppingCartM(ShoppingCartM shoppingCartM) {
        this.shoppingCartM = shoppingCartM;
    }
}
