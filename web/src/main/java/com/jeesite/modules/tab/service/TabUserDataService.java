package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.dao.TabUserDataDao;

/**
 * tab_user_dataService
 * @author 1
 * @version 2021-12-14
 */
@Service
@Transactional(readOnly=true)
public class TabUserDataService extends CrudService<TabUserDataDao, TabUserData> {
	
	/**
	 * 获取单条数据
	 * @param tabUserData
	 * @return
	 */
	@Override
	public TabUserData get(TabUserData tabUserData) {
		return super.get(tabUserData);
	}
	
	/**
	 * 查询分页数据
	 * @param tabUserData 查询条件
	 * @param tabUserData.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabUserData> findPage(TabUserData tabUserData) {
		return super.findPage(tabUserData);
	}
	
	/**
	 * 查询列表数据
	 * @param tabUserData
	 * @return
	 */
	@Override
	public List<TabUserData> findList(TabUserData tabUserData) {
		return super.findList(tabUserData);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabUserData
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabUserData tabUserData) {
		super.save(tabUserData);
	}
	
	/**
	 * 更新状态
	 * @param tabUserData
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabUserData tabUserData) {
		super.updateStatus(tabUserData);
	}

	@Transactional
	public void updateListByValidate(String str){
		tabUserDataDao.updateListByValidate(str);
	}

	public List<TabUserData> getListByValidate(String str){
		return tabUserDataDao.getListByValidate(str);
	}

	public List<TabUserData> getisNotVip(){
		return tabUserDataDao.getisNotVip();
	}
	/**
	 * 删除数据
	 * @param tabUserData
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabUserData tabUserData) {
		super.delete(tabUserData);
	}

	public Long findSumData(Map<String, String> parame) {
 		return tabUserDataDao.findSumData(parame);
	}
	
	@Autowired
	private TabUserDataDao tabUserDataDao ;

	public Long getRenShu(Map<String, String> parame) {
		return tabUserDataDao.getRenShu(parame);
	}
	@Transactional(readOnly=false)
	public void clearTyhy(Map<String, String> parame) {
		tabUserDataDao.clearTyhy(parame);
		
	}

	public Long getDaYu(Map parame) {
		Long  cc =  tabUserDataDao.getDaYu(parame);
		return cc ;
	}

	public Long getVipLevelCount(Map parame) {
		Long  cc =  tabUserDataDao.getVipLevelCount(parame);
		return cc ;
	}

	public List<TabUserData> findList2(Map<String, String> parame) {
		return tabUserDataDao.findList2(parame);
	}
	@Transactional(readOnly=false)
	public void updateXiaJi(String[] s) {
		tabUserDataDao.updateXiaJi(s);
	}
	@Transactional(readOnly=false)
	public void updateXiaJi2(String[] s) {
		tabUserDataDao.updateXiaJi2(s);
		
	}

	public Long getVipCount(Map<String, String> parame) {
		return tabUserDataDao.getVipCount(parame);
	}

	public List<TabUserData> findListYg() {
		return tabUserDataDao.findListYg();
	}

	public void updateUserDatamoney(Map<String, String> parame){
		tabUserDataDao.updateUserDatamoney(parame);
	}

	@Transactional
	public void updateVip(String rowid ,Long vip){
		tabUserDataDao.updateVip(rowid,vip);
	}

	public TabUserData getByUserId(String userid){
		return tabUserDataDao.getByUserId(userid);
	}

	@Transactional
	public void clearOrderStatus(){
		 tabUserDataDao.clearOrderStatus();
	}

	@Transactional
	public void clearOrderStatus1(){
		tabUserDataDao.clearOrderStatus1();
	}

	public Long getXzzd(Map<String, String> map){
		return tabUserDataDao.getXzzd(map);
	}

	public Long getShangJiLinkCount(String link){
		return tabUserDataDao.getShangJiLinkCount("%"+link+"%");
	}

	public List<TabUserData> getshouchonglist(Map<String,Object> map){
		return tabUserDataDao.getshouchonglist(map);
	}

	public Long getshouchonglistCount(Map<String,Object> map){
		return tabUserDataDao.getshouchonglistCount(map);
	}

	public Long getshouchonglistyuecount(Map<String,Object> map){
		return tabUserDataDao.getshouchonglistyuecount(map);
	}

	public Long getVipRenshu(Map<String, Object> map){
		return tabUserDataDao.getVipRenshu(map);
	}
}