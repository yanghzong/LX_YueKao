package com.bwie.lx_yuekao.shoppingpage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bwie.lx_yuekao.R;
import com.bwie.lx_yuekao.db.HuanCunBeanDao;
import com.bwie.lx_yuekao.shoppingpage.adapter.ShoppingAdapter;
import com.bwie.lx_yuekao.shoppingpage.application.MyApplication;
import com.bwie.lx_yuekao.shoppingpage.bean.HuanCunBean;
import com.bwie.lx_yuekao.shoppingpage.bean.ShoppingBean;
import com.bwie.lx_yuekao.shoppingpage.maplodingpage.MapLodingActivity;
import com.bwie.lx_yuekao.shoppingpage.presenter.ShopPresenter;
import com.bwie.lx_yuekao.shoppingpage.utils.NetWorkUtils;
import com.bwie.lx_yuekao.shoppingpage.xiangqingpage.XiangQingActivity;
import com.bwie.lx_yuekao.shoppingpage.xiangqingpage.bean.ShopXQBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IView, View.OnClickListener {

    private Button btnMap;
    private XRecyclerView xlvShop;
    private List<ShoppingBean.DataBean> mList;
    private ShoppingAdapter mShoppingAdapter;
    private ShopPresenter mShopPresenter;
    private HuanCunBeanDao mHuanCunBeanDao;
    private long mInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 初始化数据
        initView();
        //6 数据库缓存
        initHuanCun();
        //2 初始化presenter
        initShopPresenter();
        //3 初始化list 和  adapter
        initListAndAdapter();
        //4 条目点击事件
        setOnItemClickListener();
        //5 网络判断
        initNetWork();


    }

    /**
     * //5 网络判断
     * */
    private void initNetWork() {


    }

    /**
     * //6 数据库缓存
     * */
    private void initHuanCun() {
        mHuanCunBeanDao = MyApplication.getDaoSession().getHuanCunBeanDao();
    }

    /**
     * //4 条目点击事件
     * */
    private void setOnItemClickListener() {
        mShoppingAdapter.setMLSSOnClickListener(new ShoppingAdapter.MLSSOnClickListener() {
            @Override
            public void onChanger(String id) {
                //进行跳转
                Intent intent = new Intent(MainActivity.this, XiangQingActivity.class);
                intent.putExtra("pid",id);
                startActivity(intent);
            }
        });
    }

    /**
     * //3 初始化list 和  adapter
     * */
    private void initListAndAdapter() {
        mList = new ArrayList<>();
        mShoppingAdapter = new ShoppingAdapter(this, mList);
        xlvShop.setAdapter(mShoppingAdapter);
    }

    /**
     * //2 初始化presenter
     * */
    private void initShopPresenter() {
        mShopPresenter = new ShopPresenter();
        mShopPresenter.attach(this);
        //先判断网络状态
        boolean b = NetWorkUtils.isNetWorkAvailable(MainActivity.this);
        if(b){//如果网络可用  就请求网络
            mShopPresenter.getShopDataP();
        }else{//如果网络不可用  就进行数据库读取
            List<HuanCunBean> huanCunBeans = mHuanCunBeanDao.loadAll();//查询所有
            mList.clear();
            for (int j = 0; j < huanCunBeans.size(); j++) {
                ShoppingBean.DataBean dataBean = mList.get(j);
                dataBean.setTitle(dataBean.getTitle());
                dataBean.setBargainPrice(dataBean.getPrice());
                dataBean.setCreatetime(dataBean.getCreatetime());
                dataBean.setDetailUrl(dataBean.getDetailUrl());
                dataBean.setImages(dataBean.getImages());
                dataBean.setPid(dataBean.getPid());
                dataBean.setPrice(dataBean.getPrice());
                mList.set(j,dataBean);
            }
            mShoppingAdapter.notifyDataSetChanged();
        }


    }

    /**
     * //1 初始化数据
     * */
    private void initView() {
        btnMap = (Button) findViewById(R.id.btn_map);
        xlvShop = (XRecyclerView) findViewById(R.id.xlv_shop);
        //设置xlvshop布局管理器
        //4 瀑布流  瀑布流管理器，只有两个参数的构造方法，第一个是列数，第二个是方向
        StaggeredGridLayoutManager layoutManager2 = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        xlvShop.setLayoutManager(layoutManager2);

        btnMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_map://点击跳转到map
                 Intent intent = new Intent(MainActivity.this, MapLodingActivity.class);
                 startActivity(intent);
                break;
        }
    }

    /**
     * 实现IView接口 覆写他的方法
     * */
    @Override
    public void ShoppingData(List<ShoppingBean.DataBean> list) {
        if(list!=null){
            mList.clear();
            mList.addAll(list);
            mShoppingAdapter.notifyDataSetChanged();

            HuanCunBean huanCunBean = new HuanCunBean();
            for (int i = 0; i <list.size() ; i++) {
                ShoppingBean.DataBean dataBean = list.get(i);
                huanCunBean.setTitle(dataBean.getTitle());
                huanCunBean.setBargainPrice(dataBean.getPrice());
                huanCunBean.setCreatetime(dataBean.getCreatetime());
                huanCunBean.setDetailUrl(dataBean.getImages());
                huanCunBean.setPid(dataBean.getPid());
                huanCunBean.setPrice(dataBean.getPrice());
                //添加进数据库
                mInsert = mHuanCunBeanDao.insert(huanCunBean);
            }

            if(mInsert!=0){
                Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void ShoppingXQData(ShopXQBean shopXQBean) {

    }

    @Override
    public void Failder(Exception e) {
        Toast.makeText(this,"网络请求错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mShopPresenter!=null){
            mShopPresenter.datach();
        }
    }
}
