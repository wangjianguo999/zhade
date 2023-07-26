package com.jeesite.modules.tab.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabTongdao;

import java.util.List;
import java.util.Map;

/**
 * 通道DAO接口
 * @author 1
 * @version 2022-03-29
 */
@MyBatisDao
public interface TabTongdaoDao extends CrudDao<TabTongdao> {

    List<TabTongdao> getList();

    List<TabTongdao> getEduList();

    List<TabTongdao> getShijianlunhuan(String type);
}