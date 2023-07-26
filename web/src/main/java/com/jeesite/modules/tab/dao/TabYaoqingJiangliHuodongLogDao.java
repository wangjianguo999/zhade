package com.jeesite.modules.tab.dao;

import java.util.List;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabYaoqingJiangliHuodongLog;

/**
 * tab_yaoqing_jiangli_huodong_logDAO接口
 * @author 3
 * @version 2022-04-21
 */
@MyBatisDao
public interface TabYaoqingJiangliHuodongLogDao extends CrudDao<TabYaoqingJiangliHuodongLog> {

	List<TabYaoqingJiangliHuodongLog> findList22();
	
}