package com.bwie.lx_yuekao.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.bwie.lx_yuekao.shoppingpage.bean.HuanCunBean;

import com.bwie.lx_yuekao.db.HuanCunBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig huanCunBeanDaoConfig;

    private final HuanCunBeanDao huanCunBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        huanCunBeanDaoConfig = daoConfigMap.get(HuanCunBeanDao.class).clone();
        huanCunBeanDaoConfig.initIdentityScope(type);

        huanCunBeanDao = new HuanCunBeanDao(huanCunBeanDaoConfig, this);

        registerDao(HuanCunBean.class, huanCunBeanDao);
    }
    
    public void clear() {
        huanCunBeanDaoConfig.clearIdentityScope();
    }

    public HuanCunBeanDao getHuanCunBeanDao() {
        return huanCunBeanDao;
    }

}
