package com.jeesite.modules.tab.dao;

import java.util.List;
import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabValidCode;

/**
 * tab_valid_codeDAO接口
 * @author 3
 * @version 2021-12-10
 */
@MyBatisDao
public interface TabValidCodeDao extends CrudDao<TabValidCode> {

	List<TabValidCode> checkValidCode(Map<String, String> parame);
	
}