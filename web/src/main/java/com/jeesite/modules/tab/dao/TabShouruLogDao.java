package com.jeesite.modules.tab.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabShouruLog;

/**
 * tab_shouru_logDAO接口
 * @author 1
 * @version 2021-12-18
 */
@MyBatisDao
public interface TabShouruLogDao extends CrudDao<TabShouruLog> {

	Double getSumData(TabShouruLog tabShouruLog);
	
}