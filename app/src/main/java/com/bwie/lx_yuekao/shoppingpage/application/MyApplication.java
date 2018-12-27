package com.bwie.lx_yuekao.shoppingpage.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.bwie.lx_yuekao.db.DaoMaster;
import com.bwie.lx_yuekao.db.DaoSession;
import com.bwie.lx_yuekao.shoppingpage.utils.NetWorkUtils;
import com.facebook.drawee.backends.pipeline.Fresco;



public class MyApplication extends Application{
    private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        //1 Fresco全局配置
        initFresco();
        //2 greenDao配置
        initGreenDao();
        //3 网络判断
        initNetWork();
    }

    /**
     * //3 网络判断
     * */
    private void initNetWork() {
        boolean b = NetWorkUtils.isNetWorkAvailable(getApplicationContext());
        if(b){//网络正常
            Toast.makeText(getApplicationContext(),"网络正常",Toast.LENGTH_LONG).show();
        }else{
            //网络异常
            Toast.makeText(getApplicationContext(),"网络异常",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * //2 greenDao配置
     * */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "customer.db");
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster dm = new DaoMaster(database);
        daoSession = dm.newSession();
    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }

    /**
     * //1 Fresco全局配置
     * */
    private void initFresco() {
        Fresco.initialize(this);
    }
}
