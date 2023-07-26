package com.jeesite.modules.tab.dao;

import java.util.List;
import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabOrders;

/**
 * tab_ordersDAO接口
 * @author 1
 * @version 2022-04-10
 */
@MyBatisDao
public interface TabOrdersDao extends CrudDao<TabOrders> {

	Long  getZdrs(Map<String, String> parame);

	List<TabOrders> findList33(TabOrders tabOrders);

	List<TabOrders>  getListByStatus1(String day);

	Double getTotalMoney(Map<String, String> parame);

	Long getTotalRenshu(Map<String, String> parame);
	
}