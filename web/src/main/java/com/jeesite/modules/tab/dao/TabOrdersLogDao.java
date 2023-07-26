package com.jeesite.modules.tab.dao;

import java.util.List;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabOrdersLog;

/**
 * tab_orders_logDAO接口
 * @author 3
 * @version 2022-05-13
 */
@MyBatisDao
public interface TabOrdersLogDao extends CrudDao<TabOrdersLog> {

	List<TabOrdersLog> findList3();
	
}