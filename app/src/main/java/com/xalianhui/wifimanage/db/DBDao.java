package com.xalianhui.wifimanage.db;

import android.content.Context;
import android.util.Log;


import com.xalianhui.wifimanage.db.enty.SlideItem;

import org.xutils.DbManager;
import org.xutils.db.Selector;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class DBDao {
    private Context context;
    private DbManager db;

    public DBDao(Context context) {
        this.context = context;
        /**
         * 初始化DaoConfig配置
         */
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                //设置数据库名，默认xutils.db
                .setDbName("ejwifi.db")
                //设置数据库的版本号
                .setDbVersion(1)
                //设置数据库打开的监听
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        //开启数据库支持多线程操作，提升性能，对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                //设置数据库更新的监听
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    }
                })
                //设置表创建的监听
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table){
                        Log.i("JAVA", "onTableCreated：" + table.getName());
                    }
                });
        //设置是否允许事务，默认true
        //.setAllowTransaction(true)

         db = x.getDb(daoConfig);
    }

    public DbManager getDBUtils() {
        return db;
    }

    public List<SlideItem> getShowMenu() {
        List<SlideItem> pmentryEntities = null;
        try {
             pmentryEntities = db.selector(SlideItem.class).where("isShow", "=", 1).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (pmentryEntities == null)
            pmentryEntities = new ArrayList<>();
        return pmentryEntities;
    }
    public List<SlideItem> getAllMenu() {
        List<SlideItem> pmentryEntities = null;
        try {
             pmentryEntities = db.findAll(SlideItem.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (pmentryEntities == null)
            pmentryEntities = new ArrayList<>();
        return pmentryEntities;
    }


}
