package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabContaceService;
import com.jeesite.modules.tab.dao.TabContaceServiceDao;

/**
 * tab_contace_serviceService
 * @author 3
 * @version 2021-12-10
 */
@Service
@Transactional(readOnly=true)
public class TabContaceServiceService extends CrudService<TabContaceServiceDao, TabContaceService> {
	
	/**
	 * 获取单条数据
	 * @param tabContaceService
	 * @return
	 */
	@Override
	public TabContaceService get(TabContaceService tabContaceService) {
		return super.get(tabContaceService);
	}
	
	/**
	 * 查询分页数据
	 * @param tabContaceService 查询条件
	 * @param tabContaceService.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabContaceService> findPage(TabContaceService tabContaceService) {
		return super.findPage(tabContaceService);
	}
	
	/**
	 * 查询列表数据
	 * @param tabContaceService
	 * @return
	 */
	@Override
	public List<TabContaceService> findList(TabContaceService tabContaceService) {
		return super.findList(tabContaceService);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabContaceService
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabContaceService tabContaceService) {
		super.save(tabContaceService);
	}
	
	/**
	 * 更新状态
	 * @param tabContaceService
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabContaceService tabContaceService) {
		super.updateStatus(tabContaceService);
	}
	
	/**
	 * 删除数据
	 * @param tabContaceService
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabContaceService tabContaceService) {
		super.delete(tabContaceService);
	}
	
}