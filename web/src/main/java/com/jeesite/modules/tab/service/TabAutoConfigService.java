package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabAutoConfig;
import com.jeesite.modules.tab.dao.TabAutoConfigDao;

/**
 * tab_auto_configService
 * @author 1
 * @version 2022-03-14
 */
@Service
@Transactional(readOnly=true)
public class TabAutoConfigService extends CrudService<TabAutoConfigDao, TabAutoConfig> {
	
	/**
	 * 获取单条数据
	 * @param tabAutoConfig
	 * @return
	 */
	@Override
	public TabAutoConfig get(TabAutoConfig tabAutoConfig) {
		return super.get(tabAutoConfig);
	}
	
	/**
	 * 查询分页数据
	 * @param tabAutoConfig 查询条件
	 * @param tabAutoConfig.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabAutoConfig> findPage(TabAutoConfig tabAutoConfig) {
		return super.findPage(tabAutoConfig);
	}
	
	/**
	 * 查询列表数据
	 * @param tabAutoConfig
	 * @return
	 */
	@Override
	public List<TabAutoConfig> findList(TabAutoConfig tabAutoConfig) {
		return super.findList(tabAutoConfig);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabAutoConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabAutoConfig tabAutoConfig) {
		super.save(tabAutoConfig);
	}
	
	/**
	 * 更新状态
	 * @param tabAutoConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabAutoConfig tabAutoConfig) {
		super.updateStatus(tabAutoConfig);
	}
	
	/**
	 * 删除数据
	 * @param tabAutoConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabAutoConfig tabAutoConfig) {
		super.delete(tabAutoConfig);
	}
	
}