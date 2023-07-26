package com.jeesite.modules.tab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabShouruLog;
import com.jeesite.modules.tab.dao.TabShouruLogDao;

/**
 * tab_shouru_logService
 * @author 1
 * @version 2021-12-18
 */
@Service
@Transactional(readOnly=true)
public class TabShouruLogService extends CrudService<TabShouruLogDao, TabShouruLog> {
	
	/**
	 * 获取单条数据
	 * @param tabShouruLog
	 * @return
	 */
	@Override
	public TabShouruLog get(TabShouruLog tabShouruLog) {
		return super.get(tabShouruLog);
	}
	
	/**
	 * 查询分页数据
	 * @param tabShouruLog 查询条件
	 * @param tabShouruLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabShouruLog> findPage(TabShouruLog tabShouruLog) {
		return super.findPage(tabShouruLog);
	}
	
	/**
	 * 查询列表数据
	 * @param tabShouruLog
	 * @return
	 */
	@Override
	public List<TabShouruLog> findList(TabShouruLog tabShouruLog) {
		return super.findList(tabShouruLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabShouruLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabShouruLog tabShouruLog) {
		super.save(tabShouruLog);
	}
	
	/**
	 * 更新状态
	 * @param tabShouruLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabShouruLog tabShouruLog) {
		super.updateStatus(tabShouruLog);
	}
	
	/**
	 * 删除数据
	 * @param tabShouruLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabShouruLog tabShouruLog) {
		super.delete(tabShouruLog);
	}

	public Double getSumData(TabShouruLog tabShouruLog) {
 		return tabShouruLogDao.getSumData(tabShouruLog);
	}
	
	@Autowired
	private  TabShouruLogDao tabShouruLogDao ;
	
}