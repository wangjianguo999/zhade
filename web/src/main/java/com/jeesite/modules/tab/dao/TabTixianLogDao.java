package com.jeesite.modules.tab.dao;

import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabTixianLog;

/**
 * tab_tixian_logDAO接口
 * @author 1
 * @version 2021-12-17
 */
@MyBatisDao
public interface TabTixianLogDao extends CrudDao<TabTixianLog> {

	Double getXiaJiaData(Map<String, String> parame);

	Long getTixianRenShu(Map<String, String> parame);

	Double getTixianMoney(Map<String, String> parame);

	Double getSum(Map<String, String> parame);

	Double getCzje(Map<String, Object> map);

	Long getCzbs(Map<String, Object> map);

	Double getTotalMoney(Map<String, String> parame);

	Long getTotalRenshu(Map<String, String> parame);

	void updateYgzh(Map<String, String> parame);

	Integer getTotalCount(Map<String, String> parame);

	Double getCzje2(Map<String, Object> map);

	TabTixianLog getByOrderId(String str);

	Double getTdtx(String rowid);
}