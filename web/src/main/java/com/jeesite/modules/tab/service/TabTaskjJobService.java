package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabTaskjJob;
import com.jeesite.modules.tab.dao.TabTaskjJobDao;

/**
 * tab_taskj_jobService
 * @author 1
 * @version 2022-03-21
 */
@Service
@Transactional(readOnly=true)
public class TabTaskjJobService extends CrudService<TabTaskjJobDao, TabTaskjJob> {
	
	/**
	 * 获取单条数据
	 * @param tabTaskjJob
	 * @return
	 */
	@Override
	public TabTaskjJob get(TabTaskjJob tabTaskjJob) {
		return super.get(tabTaskjJob);
	}
	
	/**
	 * 查询分页数据
	 * @param tabTaskjJob 查询条件
	 * @param tabTaskjJob.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabTaskjJob> findPage(TabTaskjJob tabTaskjJob) {
		return super.findPage(tabTaskjJob);
	}
	
	/**
	 * 查询列表数据
	 * @param tabTaskjJob
	 * @return
	 */
	@Override
	public List<TabTaskjJob> findList(TabTaskjJob tabTaskjJob) {
		return super.findList(tabTaskjJob);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabTaskjJob
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabTaskjJob tabTaskjJob) {
		super.save(tabTaskjJob);
	}
	
	/**
	 * 更新状态
	 * @param tabTaskjJob
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabTaskjJob tabTaskjJob) {
		super.updateStatus(tabTaskjJob);
	}
	
	/**
	 * 删除数据
	 * @param tabTaskjJob
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabTaskjJob tabTaskjJob) {
		super.delete(tabTaskjJob);
	}
	
}