package com.jeesite.modules.view.dao;

import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.view.entity.ViewShouchong;

/**
 * VIEWDAO接口
 * @author 22
 * @version 2022-03-20
 */
@MyBatisDao
public interface ViewShouchongDao extends CrudDao<ViewShouchong> {

	Long getTotalRenshu(Map<String, String> parame);

	Double getTotalMoney(Map<String, String> parame);
	
}