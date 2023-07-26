package com.jeesite.modules.tab.dao;

import java.util.List;
import java.util.Map;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.tab.entity.TabUserData;
import org.apache.ibatis.annotations.Param;

/**
 * tab_user_dataDAO接口
 * @author 1
 * @version 2021-12-14
 */
@MyBatisDao
public interface TabUserDataDao extends CrudDao<TabUserData> {

	Long findSumData(Map<String, String> parame);

	Long getRenShu(Map<String, String> parame);

	void clearTyhy(Map<String, String> parame);

	Long getDaYu(Map<String, String> parame);

	Long getVipLevelCount(Map<String, String> parame);

	List<TabUserData> findList2(Map<String, String> parame);

	void updateXiaJi(String[] s);

	void updateXiaJi2(String[] s);

	void save22(Map<String, String> parame);

	Long getVipCount(Map<String, String> parame);

	List<TabUserData> findListYg();

	void updateListByValidate(String str);

	List<TabUserData> getListByValidate(String str);

	List<TabUserData> getisNotVip();

	void updateUserDatamoney(Map<String, String> parame);

	TabUserData getByUserId(String userid);

	void updateVip(@Param("rowid") String rowid , @Param("vip")  Long vip);

	void clearOrderStatus();

	void clearOrderStatus1();

	Long getXzzd(Map<String, String> map);

	Long getShangJiLinkCount(String link);

	Long getVipRenshu(Map<String, Object> map);

	List<TabUserData> getshouchonglist(Map<String,Object> map);

	Long getshouchonglistCount(Map<String,Object> map);

	Long getshouchonglistyuecount(Map<String,Object> map);
}