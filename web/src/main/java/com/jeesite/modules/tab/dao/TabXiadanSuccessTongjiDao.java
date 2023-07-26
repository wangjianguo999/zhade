package com.jeesite.modules.tab.dao;

import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabXiadanSuccessTongji;

/**
 * tab_xiadan_success_tongjiDAO接口
 * @author 1
 * @version 2022-04-05
 */
@MyBatisDao
public interface TabXiadanSuccessTongjiDao extends CrudDao<TabXiadanSuccessTongji> {

	Double getTotalMoney(Map<String, String> parame);

	Long getTotalRenshu(Map<String, String> parame);
	
}