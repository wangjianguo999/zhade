package com.jeesite.modules.tab.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabTixianLog;
import com.jeesite.modules.api.ApiController;
import com.jeesite.modules.tab.dao.TabTixianLogDao;

/**
 * tab_tixian_logService
 * @author 1
 * @version 2021-12-17
 */
@Service
@Transactional(readOnly=true)
public class TabTixianLogService extends CrudService<TabTixianLogDao, TabTixianLog> {
	
	/**
	 * 获取单条数据
	 * @param tabTixianLog
	 * @return
	 */
	@Override
	public TabTixianLog get(TabTixianLog tabTixianLog) {
		return super.get(tabTixianLog);
	}

	public TabTixianLog getByOrderId(String str){
		return tabTixianLogDao.getByOrderId(str);
	}
	
	/**
	 * 查询分页数据
	 * @param tabTixianLog 查询条件
	 * @param tabTixianLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabTixianLog> findPage(TabTixianLog tabTixianLog) {
		return super.findPage(tabTixianLog);
	}
	
	/**
	 * 查询列表数据
	 * @param tabTixianLog
	 * @return
	 */
	@Override
	public List<TabTixianLog> findList(TabTixianLog tabTixianLog) {
		return super.findList(tabTixianLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabTixianLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabTixianLog tabTixianLog) {
		super.save(tabTixianLog);
	}
	
	/**
	 * 更新状态
	 * @param tabTixianLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabTixianLog tabTixianLog) {
		super.updateStatus(tabTixianLog);
	}
	
	/**
	 * 删除数据
	 * @param tabTixianLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabTixianLog tabTixianLog) {
		super.delete(tabTixianLog);
	}

	public Double getXiaJiaData(Map<String, String> parame) {
		return tabTixianLogDao.getXiaJiaData(parame);
	}
	
	@Autowired
	private  TabTixianLogDao  tabTixianLogDao ;

	public Long getTixianRenShu(Map<String, String> parame) {
		return tabTixianLogDao.getTixianRenShu(parame);
	}

	public Double getTixianMoney(Map<String, String> parame) {
		// TODO Auto-generated method stub
		return tabTixianLogDao.getTixianMoney(parame);
	}

	public Double getSum(Map<String, String> parame) {
		return tabTixianLogDao.getSum(parame);

	}

	public Long getShenhe(String userId) {
		TabTixianLog oo = new TabTixianLog();  
		oo.setUserid(userId); 
		oo.setStatus1("1");
		oo.setCreatetime_gte(DateUtils.getOfDayFirst(ApiController.getjndDate(new Date())));
		Long cc =  tabTixianLogDao.findCount(oo );
		
		oo.setStatus1("12");
		cc =  cc +   tabTixianLogDao.findCount(oo );  
		return cc;
	}

	public Long getTodays(String userId, Date date) {
		TabTixianLog oo = new TabTixianLog();  
		oo.setUserid(userId); 
		oo.setCreatetime_gte(DateUtils.getOfDayFirst(date));
		
		Long cc =  tabTixianLogDao.findCount(oo );

		
		return cc;
	}

	public Double getCzje(Map<String, Object> map) {
		return tabTixianLogDao.getCzje(map);
	}

	public Long getCzbs(Map<String, Object> map) {
		return tabTixianLogDao.getCzbs(map);
	}

	public Double getTotalMoney(Map<String, String> parame) {
		return tabTixianLogDao.getTotalMoney(parame);
	}

	public Long getTotalRenshu(Map<String, String> parame) {
		return tabTixianLogDao.getTotalRenshu(parame);
	}
	@Transactional(readOnly=false)
	public void updateYgzh(Map<String, String> parame) {
		tabTixianLogDao.updateYgzh(parame);
	}

	public Double getCzje2(Map<String, Object> map) {
		return tabTixianLogDao.getCzje2(map);
	}

	public Double getTdtx(String rowid) {
		return tabTixianLogDao.getTdtx(rowid);
	}

	public Integer getTotalCount(Map<String, String> parame){
		return tabTixianLogDao.getTotalCount(parame);
	}
}