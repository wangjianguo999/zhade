package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabSysSkfs;
import com.jeesite.modules.tab.dao.TabSysSkfsDao;

/**
 * tab_sys_skfsService
 * @author 1
 * @version 2021-12-23
 */
@Service
@Transactional(readOnly=true)
public class TabSysSkfsService extends CrudService<TabSysSkfsDao, TabSysSkfs> {
	
	/**
	 * 获取单条数据
	 * @param tabSysSkfs
	 * @return
	 */
	@Override
	public TabSysSkfs get(TabSysSkfs tabSysSkfs) {
		return super.get(tabSysSkfs);
	}
	
	/**
	 * 查询分页数据
	 * @param tabSysSkfs 查询条件
	 * @param tabSysSkfs.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabSysSkfs> findPage(TabSysSkfs tabSysSkfs) {
		return super.findPage(tabSysSkfs);
	}
	
	/**
	 * 查询列表数据
	 * @param tabSysSkfs
	 * @return
	 */
	@Override
	public List<TabSysSkfs> findList(TabSysSkfs tabSysSkfs) {
		return super.findList(tabSysSkfs);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabSysSkfs
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabSysSkfs tabSysSkfs) {
		super.save(tabSysSkfs);
	}
	
	/**
	 * 更新状态
	 * @param tabSysSkfs
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabSysSkfs tabSysSkfs) {
		super.updateStatus(tabSysSkfs);
	}
	
	/**
	 * 删除数据
	 * @param tabSysSkfs
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabSysSkfs tabSysSkfs) {
		super.delete(tabSysSkfs);
	}
	
}