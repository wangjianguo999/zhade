package com.jeesite.modules.tab.dao;

import java.util.List;
import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabJiesuanLog;

/**
 * tab_jiesuan_logDAO接口
 * @author 23
 * @version 2022-02-03
 */
@MyBatisDao
public interface TabJiesuanLogDao extends CrudDao<TabJiesuanLog> {

	Double getUserMoney(Map<String, String> parame);

	Double getShouruData(Map<String, String> parame);

	List<TabJiesuanLog> getShouruList(Map<String, String> parame);

	List<TabJiesuanLog> getZhiChuList(Map<String, String> parame);

	List<TabJiesuanLog> rewardDetails(Map<String, String> parame);

	Double getTuandui(Map<String, String> parame);

	void updateYgzh(Map<String, String> parame);

	Double getShouruDataTuandui(Map<String, String> parame);

	void deleteListByOrderId(String orderId);

	List<TabJiesuanLog> ztReset(String date);
	
}