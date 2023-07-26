package com.jeesite.modules.tab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabOrdersLog;
import com.jeesite.modules.tab.dao.TabOrdersLogDao;

/**
 * tab_orders_logService
 * @author 3
 * @version 2022-05-13
 */
@Service
@Transactional(readOnly=true)
public class TabOrdersLogService extends CrudService<TabOrdersLogDao, TabOrdersLog> {
	
	/**
	 * 获取单条数据
	 * @param tabOrdersLog
	 * @return
	 */
	@Override
	public TabOrdersLog get(TabOrdersLog tabOrdersLog) {
		return super.get(tabOrdersLog);
	}
	
	/**
	 * 查询分页数据
	 * @param tabOrdersLog 查询条件
	 * @param tabOrdersLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabOrdersLog> findPage(TabOrdersLog tabOrdersLog) {
		return super.findPage(tabOrdersLog);
	}
	
	/**
	 * 查询列表数据
	 * @param tabOrdersLog
	 * @return
	 */
	@Override
	public List<TabOrdersLog> findList(TabOrdersLog tabOrdersLog) {
		return super.findList(tabOrdersLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabOrdersLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabOrdersLog tabOrdersLog) {
		super.save(tabOrdersLog);
	}
	
	/**
	 * 更新状态
	 * @param tabOrdersLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabOrdersLog tabOrdersLog) {
		super.updateStatus(tabOrdersLog);
	}
	
	/**
	 * 删除数据
	 * @param tabOrdersLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabOrdersLog tabOrdersLog) {
		super.delete(tabOrdersLog);
	}

	public List<TabOrdersLog> findList3() {
 		return tabOrdersLogDao.findList3();
	}
	@Autowired
	private  TabOrdersLogDao tabOrdersLogDao ;
	
}