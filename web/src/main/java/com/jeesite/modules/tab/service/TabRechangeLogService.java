package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabRechangeLog;
import com.jeesite.modules.tab.entity.TabTongji;
import com.jeesite.modules.tab.dao.TabRechangeLogDao;

/**
 * tab_rechange_logService
 * @author 3
 * @version 2021-12-12
 */
@Service
@Transactional(readOnly=true)
public class TabRechangeLogService extends CrudService<TabRechangeLogDao, TabRechangeLog> {
	
	/**
	 * 获取单条数据
	 * @param tabRechangeLog
	 * @return
	 */
	@Override
	public TabRechangeLog get(TabRechangeLog tabRechangeLog) {
		return super.get(tabRechangeLog);
	}
	
	/**
	 * 查询分页数据
	 * @param tabRechangeLog 查询条件
	 * @param tabRechangeLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabRechangeLog> findPage(TabRechangeLog tabRechangeLog) {
		return super.findPage(tabRechangeLog);
	}
	
	/**
	 * 查询列表数据
	 * @param tabRechangeLog
	 * @return
	 */
	@Override
	public List<TabRechangeLog> findList(TabRechangeLog tabRechangeLog) {
		return super.findList(tabRechangeLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabRechangeLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabRechangeLog tabRechangeLog) {
		super.save(tabRechangeLog);
	}
	
	/**
	 * 更新状态
	 * @param tabRechangeLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabRechangeLog tabRechangeLog) {
		super.updateStatus(tabRechangeLog);
	}
	
	/**
	 * 删除数据
	 * @param tabRechangeLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabRechangeLog tabRechangeLog) {
		super.delete(tabRechangeLog);
	}

	public Double getSum(Map<String, String> parame) {
			return tabRechangeLogDao.getSum(parame);
	}
	
	@Autowired
	private TabRechangeLogDao tabRechangeLogDao ;

	public Long getXiajiRenshu(Map<String, String> parame) {
 		return tabRechangeLogDao.getXiajiRenshu(parame);
	}

	public Double getXiajiJine(Map<String, String> parame) {
		return tabRechangeLogDao.getXiajiJine(parame);

	}

	public Integer getUserRechangeLog(String userid,String date){
		return tabRechangeLogDao.getUserRechangeLog(userid,date);
	}

	public Long getCzbs(Map<String, Object> map) {
		return tabRechangeLogDao.getCzbs(map);
	}

	public Double getCzje(Map<String, Object> map) {
		return tabRechangeLogDao.getCzje(map);
	}


	public Double getTotalMoney(Map<String, String> parame) {
		if (!StringUtils.isBlank(parame.get("name1"))) {
			parame.put("name1", "%" +parame.get("name1") +"%");
		}
   		return tabRechangeLogDao.getTotalMoney(parame);
	}

	public Long getTotalRenshu(Map<String, String> parame) {
		if (!StringUtils.isBlank(parame.get("name1"))) {
			parame.put("name1", "%" +parame.get("name1") +"%");
		}
   		return tabRechangeLogDao.getTotalRenshu(parame);

	}
	@Transactional(readOnly=false)
	public void updateYgzh(Map<String, String> parame) {
		tabRechangeLogDao.updateYgzh(parame);
	}

	public Double getXjcz(Map<String, String> parame) {
   		return tabRechangeLogDao.getXjcz(parame);
	}

	public List<TabTongji> getList(Map<String, String> parame) {
		return tabRechangeLogDao.getList(parame);
	}

	public Double getTdcz(String rowid){
		return tabRechangeLogDao.gettdcz(rowid);
	}

	public Double getFrozen(Map<String, String> parame){
		return tabRechangeLogDao.getFrozen(parame);
	}

	public List<TabRechangeLog> getTabRechangeLogList(String time){
		return tabRechangeLogDao.getTabRechangeLogList(time);
	}

	public Double getactiveSum(Map<String, Object> parame){
		return tabRechangeLogDao.getactiveSum(parame);
	}
}