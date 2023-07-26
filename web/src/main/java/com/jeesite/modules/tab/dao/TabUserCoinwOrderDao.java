package com.jeesite.modules.tab.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabUserCoinw;
import com.jeesite.modules.tab.entity.TabUserCoinwOrder;

import java.util.Map;

/**
 * tab_user_dataDAO接口
 * @author 1
 * @version 2021-12-14
 */
@MyBatisDao
public interface TabUserCoinwOrderDao extends CrudDao<TabUserCoinwOrder> {

    Map<String,String> getTongji(String userid);

    Long getProfitcount(String userid);
}