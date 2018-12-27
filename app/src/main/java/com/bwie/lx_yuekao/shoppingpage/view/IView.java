package com.bwie.lx_yuekao.shoppingpage.view;

import com.bwie.lx_yuekao.shoppingpage.bean.ShoppingBean;
import com.bwie.lx_yuekao.shoppingpage.xiangqingpage.bean.ShopXQBean;

import java.util.List;



public interface IView {

    //商品
    void ShoppingData(List<ShoppingBean.DataBean> list);
    //商品详情
    void ShoppingXQData(ShopXQBean shopXQBean);
    void Failder(Exception e);

}
