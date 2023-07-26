package com.jeesite.modules.tab.dao;

import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabLicaiData;

/**
 * tab_licai_dataDAO接口
 * @author 1
 * @version 2021-12-17
 */
@MyBatisDao
public interface TabLicaiDataDao extends CrudDao<TabLicaiData> {

	void updateYgzh(Map<String, String> parame);
	
}