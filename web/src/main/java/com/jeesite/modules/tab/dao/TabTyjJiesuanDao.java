package com.jeesite.modules.tab.dao;

import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabTyjJiesuan;

/**
 * tab_tyj_jiesuanDAO接口
 * @author 1
 * @version 2022-04-12
 */
@MyBatisDao
public interface TabTyjJiesuanDao extends CrudDao<TabTyjJiesuan> {

	Double getUserMoney(Map<String, String> parame);

	void deleteYc(Map<String, String> parame);

	void deleteYc2(Map<String, String> parame);

	void deleteListByOrderId(String orderId);

	void deleteByUserId(String userid);
}