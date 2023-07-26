package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabOrderDataLog;
import com.jeesite.modules.tab.dao.TabOrderDataLogDao;

/**
 * tab_order_data_logService
 * @author 1
 * @version 2022-05-10
 */
@Service
@Transactional(readOnly=true)
public class TabOrderDataLogService extends CrudService<TabOrderDataLogDao, TabOrderDataLog> {
	
	/**
	 * 获取单条数据
	 * @param tabOrderDataLog
	 * @return
	 */
	@Override
	public TabOrderDataLog get(TabOrderDataLog tabOrderDataLog) {
		return super.get(tabOrderDataLog);
	}
	
	/**
	 * 查询分页数据
	 * @param tabOrderDataLog 查询条件
	 * @param tabOrderDataLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabOrderDataLog> findPage(TabOrderDataLog tabOrderDataLog) {
		return super.findPage(tabOrderDataLog);
	}
	
	/**
	 * 查询列表数据
	 * @param tabOrderDataLog
	 * @return
	 */
	@Override
	public List<TabOrderDataLog> findList(TabOrderDataLog tabOrderDataLog) {
		return super.findList(tabOrderDataLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabOrderDataLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabOrderDataLog tabOrderDataLog) {
		super.save(tabOrderDataLog);
	}
	
	/**
	 * 更新状态
	 * @param tabOrderDataLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabOrderDataLog tabOrderDataLog) {
		super.updateStatus(tabOrderDataLog);
	}
	
	/**
	 * 删除数据
	 * @param tabOrderDataLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabOrderDataLog tabOrderDataLog) {
		super.delete(tabOrderDataLog);
	}
	@Transactional(readOnly=false)
	public void inss(Map<String, String> mm) {
		tabOrderDataLogDao.ins(mm);
	}
	@Autowired
	TabOrderDataLogDao   tabOrderDataLogDao ;
	
}