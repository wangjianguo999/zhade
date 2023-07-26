package com.jeesite.modules.tab.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeesite.common.lang.StringUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.mock.MockParameterMetaData.Parameter;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabJiesuanLog;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.dao.TabJiesuanLogDao;
import com.jeesite.modules.tab.dao.TabUserDataDao;

/**
 * tab_jiesuan_logService
 * @author 23
 * @version 2022-02-03
 */
@Service
@Transactional(readOnly=true)
public class TabJiesuanLogService extends CrudService<TabJiesuanLogDao, TabJiesuanLog> {
	
	/**
	 * 获取单条数据
	 * @param tabJiesuanLog
	 * @return
	 */
	@Override
	public TabJiesuanLog get(TabJiesuanLog tabJiesuanLog) {
		return super.get(tabJiesuanLog);
	}
	
	/**
	 * 查询分页数据
	 * @param tabJiesuanLog 查询条件
	 * @param tabJiesuanLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabJiesuanLog> findPage(TabJiesuanLog tabJiesuanLog) {
		return super.findPage(tabJiesuanLog);
	}
	
	/**
	 * 查询列表数据
	 * @param tabJiesuanLog
	 * @return
	 */
	@Override
	public List<TabJiesuanLog> findList(TabJiesuanLog tabJiesuanLog) {
		return super.findList(tabJiesuanLog);
	}

	@Transactional
	public void deleteListByOrderId(String orderId){
		tabJiesuanLogDao.deleteListByOrderId(orderId);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param tabJiesuanLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabJiesuanLog tabJiesuanLog) {
		if(StringUtils.isBlank(tabJiesuanLog.getYgzh()) || StringUtils.isBlank(tabJiesuanLog.getYgzh2())){
			TabUserData obj =   new  TabUserData();
			obj.setRowid(tabJiesuanLog.getUserid());
			TabUserData  tabUserData  =   tabUserDataDao.get(obj);
			if(tabUserData != null) {
				tabJiesuanLog.setYgzh(tabUserData.getYgzh());
				tabJiesuanLog.setYgzh2(tabUserData.getYgzh2());
			}
		}
		super.save(tabJiesuanLog);
	}
	
	
	@Autowired
	private TabUserDataDao tabUserDataDao ;
	/**
	 * 更新状态
	 * @param tabJiesuanLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabJiesuanLog tabJiesuanLog) {
		super.updateStatus(tabJiesuanLog);
	}
	
	/**
	 * 删除数据
	 * @param tabJiesuanLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabJiesuanLog tabJiesuanLog) {
		super.delete(tabJiesuanLog);
	}

	public Double getUserMoney(Map<String, String> parame) {
		
 		return tabJiesuanLogDao.getUserMoney(parame);
	}
	
	@Autowired
	private TabJiesuanLogDao  tabJiesuanLogDao ;

	public Double getShouruData(Map<String, String> parame) {
		return tabJiesuanLogDao.getShouruData(parame);
	}

	public List<TabJiesuanLog> getShouruList(Map<String, String> parame) {
		return tabJiesuanLogDao.getShouruList(parame);

	}

	public List<TabJiesuanLog> getZhiChuList(Map<String, String> parame) {
		return tabJiesuanLogDao.getZhiChuList(parame);

	}

	public List<TabJiesuanLog> rewardDetails(Map<String, String> parame) {
		return tabJiesuanLogDao.rewardDetails(parame);

	}

	public Double getTuandui(Map<String, String> parame) {
		String userid =   parame.get("userid");
		userid= userid.replaceAll("%", "");
		
		parame.put("userid", userid);
		
		return tabJiesuanLogDao.getTuandui(parame);
	}
	@Autowired
	private TabUserDataService dataService;
	
	public static void main(String[] args) {
		String s = "11%";
		s= s.replaceAll("%", "");
		
		System.out.println(s);
	}
	@Transactional(readOnly=false)
	public void updateYgzh(Map<String, String> parame) {
		tabJiesuanLogDao.updateYgzh(parame);
	}

	public Double getShouruDataTuandui(Map<String, String> parame) {
		return tabJiesuanLogDao.getShouruDataTuandui(parame);
	}

	public List<TabJiesuanLog> ztReset(String data){
		return tabJiesuanLogDao.ztReset(data);
	}
	
}