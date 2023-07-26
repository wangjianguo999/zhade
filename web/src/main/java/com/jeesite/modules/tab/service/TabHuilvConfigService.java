package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabHuilvConfig;
import com.jeesite.modules.tab.dao.TabHuilvConfigDao;

/**
 * tab_huilv_configService
 * @author 11
 * @version 2021-12-24
 */
@Service
@Transactional(readOnly=true)
public class TabHuilvConfigService extends CrudService<TabHuilvConfigDao, TabHuilvConfig> {
	
	/**
	 * 获取单条数据
	 * @param tabHuilvConfig
	 * @return
	 */
	@Override
	public TabHuilvConfig get(TabHuilvConfig tabHuilvConfig) {
		return super.get(tabHuilvConfig);
	}
	
	/**
	 * 查询分页数据
	 * @param tabHuilvConfig 查询条件
	 * @param tabHuilvConfig.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabHuilvConfig> findPage(TabHuilvConfig tabHuilvConfig) {
		return super.findPage(tabHuilvConfig);
	}
	
	/**
	 * 查询列表数据
	 * @param tabHuilvConfig
	 * @return
	 */
	@Override
	public List<TabHuilvConfig> findList(TabHuilvConfig tabHuilvConfig) {
		return super.findList(tabHuilvConfig);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabHuilvConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabHuilvConfig tabHuilvConfig) {
		super.save(tabHuilvConfig);
	}
	
	/**
	 * 更新状态
	 * @param tabHuilvConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabHuilvConfig tabHuilvConfig) {
		super.updateStatus(tabHuilvConfig);
	}
	
	/**
	 * 删除数据
	 * @param tabHuilvConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabHuilvConfig tabHuilvConfig) {
		super.delete(tabHuilvConfig);
	}
	
}