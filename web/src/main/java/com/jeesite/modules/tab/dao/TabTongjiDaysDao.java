package com.jeesite.modules.tab.dao;

import java.util.List;
import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabTongjiDays;

/**
 * tab_tongji_daysDAO接口
 * @author 2
 * @version 2022-04-11
 */
@MyBatisDao
public interface TabTongjiDaysDao extends CrudDao<TabTongjiDays> {

	List<TabTongjiDays> getTongji(Map<String, String> parame);
	
}