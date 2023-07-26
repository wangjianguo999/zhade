package com.jeesite.modules.tab.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabUserCoinw;
import com.jeesite.modules.tab.entity.TabUserData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * tab_user_dataDAO接口
 * @author 1
 * @version 2021-12-14
 */
@MyBatisDao
public interface TabUserCoinwDao extends CrudDao<TabUserCoinw> {

    List<TabUserCoinw> findListData(Map<String,Object> map);

    Long findListDataCount(Map<String,Object> map);
}