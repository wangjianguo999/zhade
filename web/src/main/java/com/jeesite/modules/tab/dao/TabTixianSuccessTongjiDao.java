package com.jeesite.modules.tab.dao;

import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabTixianSuccessTongji;

/**
 * tab_tixian_success_tongjiDAO接口
 * @author 32
 * @version 2022-04-04
 */
@MyBatisDao
public interface TabTixianSuccessTongjiDao extends CrudDao<TabTixianSuccessTongji> {

	Long getTotalRenshu(Map<String, String> parame);

	Double getTotalMoney(Map<String, String> parame);
	
}