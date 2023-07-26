package com.jeesite.modules.tab.dao;

import java.util.List;
import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabOrderData;

/**
 * tab_order_dataDAO接口
 * @author 32
 * @version 2021-12-18
 */
@MyBatisDao
public interface TabOrderDataDao extends CrudDao<TabOrderData> {

	Double getGwje(Map<String, String> parame);

	Double getYouJin(Map<String, String> parame);

	Double getTotal(Map<String, String> parame);

	List<TabOrderData> findList2();

	void updateJiesuan(Map<String, String> parame);

	Double getBenJin(Map<String, String> parame);

	Double getYOujin2(Map<String, String> parame);

	 void updateYgzh(Map<String, String> parame);

	Double getTotalMoney(Map<String, String> parame);

	Long getTotalRenshu(Map<String, String> parame);

	String getLastData(Map<String, String> parame);

	Double getTotalMoney2(Map<String, String> parame);

	Long getTotalRenshu2(Map<String, String> parame);

	void deleteListByOrderId(String orderId);
	
}