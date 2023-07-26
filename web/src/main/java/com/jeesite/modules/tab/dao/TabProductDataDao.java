package com.jeesite.modules.tab.dao;

import java.util.List;
import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabProductData;

/**
 * tab_product_dataDAO接口
 * @author 1
 * @version 2021-12-18
 */
@MyBatisDao
public interface TabProductDataDao extends CrudDao<TabProductData> {

	List<TabProductData> findList2(Map<String, Object> parame);

	List<TabProductData> newlist(Map<String, Object> parame);

	List<TabProductData> newlist1(Map<String, Object> parame);

	List<TabProductData> list2(Map<String, Object> parame);

	List<TabProductData> list33(Map<String, String> request);
	
}