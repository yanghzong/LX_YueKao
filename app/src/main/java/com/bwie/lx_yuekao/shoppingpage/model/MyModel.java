package com.bwie.lx_yuekao.shoppingpage.model;


import com.bwie.lx_yuekao.shoppingpage.item.ICallBack;
import com.bwie.lx_yuekao.shoppingpage.utils.OKHttpUtils;

import java.lang.reflect.Type;



public class MyModel {

    //商品请求数据方法
    public void getShoppingDataM(String url, ICallBack iCallBack, Type type){
        //调用网络请求
        OKHttpUtils.getInstance().getData(url,iCallBack,type);
    }

}
