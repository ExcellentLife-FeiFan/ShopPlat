package com.ytxd.spp.model;

import com.ytxd.spp.util.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XY on 2017/6/15.
 */

public class ShoppingCartM implements Serializable {
    public MerchantM merchantM;
    public List<Goods> goods = new ArrayList<>();

    public MerchantM getMerchantM() {
        return merchantM;
    }

    public void setMerchantM(MerchantM merchantM) {
        this.merchantM = merchantM;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public void addGood(GoodM goodM) {
        boolean isCont = false;
        for (int i = 0; i < goods.size(); i++) {
            if (goods.get(i).getGoodM().getGoodsCode().equals(goodM.getGoodsCode())) {
                isCont = true;
                goods.get(i).setCount(goods.get(i).count + 1);
                break;
            }

        }
        if (!isCont) {
            Goods good = new Goods();
            good.setCount(1);
            good.setGoodM(goodM);
            goods.add(good);
        }
    }

    public void removeGood(GoodM goodM) {
        boolean isCont = false;
        for (int i = 0; i < goods.size(); i++) {
            if (goods.get(i).getGoodM().getGoodsCode().equals(goodM.getGoodsCode())) {
                isCont = true;
                if (goods.get(i).getCount() == 1) {
                    goods.remove(i);
                } else {
                    goods.get(i).setCount(goods.get(i).count - 1);
                }
                break;
            }

        }
        if (!isCont) {
        }
    }

    public String getPirceTotal() {
        String priceS = "";
        double price = 0;
        try {
            for (int i = 0; i < goods.size(); i++) {
                price = goods.get(i).getCount() * Double.valueOf(goods.get(i).getGoodM().getXPrice());
            }
            priceS= CommonUtils.getFloatString2(Float.valueOf(price+""));
            return priceS;
        } catch (Exception e) {
            e.printStackTrace();
            return price+"";
        }

    }

    public int getGoodsCounts() {
        int count = 0;
        try {
            for (int i = 0; i < goods.size(); i++) {
                count = count + goods.get(i).getCount();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    public void clearGoods(){
        goods.clear();
    }


    public static class Goods implements Serializable {
        private int count;
        private GoodM goodM;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public GoodM getGoodM() {
            return goodM;
        }

        public void setGoodM(GoodM goodM) {
            this.goodM = goodM;
        }
    }
}
