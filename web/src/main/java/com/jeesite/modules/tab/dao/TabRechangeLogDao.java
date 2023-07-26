package com.jeesite.modules.tab.dao;

import java.util.List;
import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabRechangeLog;
import com.jeesite.modules.tab.entity.TabTongji;

/**
 * tab_rechange_logDAO接口
 * @author 3
 * @version 2021-12-12
 */
@MyBatisDao
public interface TabRechangeLogDao extends CrudDao<TabRechangeLog> {

	Double getSum(Map<String, String> parame);

	Long getXiajiRenshu(Map<String, String> parame);

	Double getXiajiJine(Map<String, String> parame);

	Long getCzbs(Map<String, Object> map);

	Double getCzje(Map<String, Object> map);

	Double getTotalMoney(Map<String, String> parame);

	Long getTotalRenshu(Map<String, String> parame);

	void updateYgzh(Map<String, String> parame);

	Double getXjcz(Map<String, String> parame);

	List<TabTongji> getList(Map<String, String> parame);

	Integer getUserRechangeLog(String userid,String date);

	Double gettdcz(String rowid);

	Double getFrozen(Map<String, String> parame);

	List<TabRechangeLog> getTabRechangeLogList(String time);

	Double getactiveSum(Map<String, Object> parame);
	
}