package com.jeesite.modules.tab.dao;

import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabOrderDataLog;

/**
 * tab_order_data_logDAO接口
 * @author 1
 * @version 2022-05-10
 */
@MyBatisDao
public interface TabOrderDataLogDao extends CrudDao<TabOrderDataLog> {

	void ins(Map<String, String> mm);
	
}