package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabVipConfig;
import com.jeesite.modules.tab.dao.TabVipConfigDao;

/**
 * tab_vip_configService
 * @author 1
 * @version 2021-12-11
 */
@Service
@Transactional(readOnly=true)
public class TabVipConfigService extends CrudService<TabVipConfigDao, TabVipConfig> {
	
	/**
	 * 获取单条数据
	 * @param tabVipConfig
	 * @return
	 */
	@Override
	public TabVipConfig get(TabVipConfig tabVipConfig) {
		return super.get(tabVipConfig);
	}
	
	/**
	 * 查询分页数据
	 * @param tabVipConfig 查询条件
	 * @param tabVipConfig.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabVipConfig> findPage(TabVipConfig tabVipConfig) {
		return super.findPage(tabVipConfig);
	}
	
	/**
	 * 查询列表数据
	 * @param tabVipConfig
	 * @return
	 */
	@Override
	public List<TabVipConfig> findList(TabVipConfig tabVipConfig) {
		return super.findList(tabVipConfig);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabVipConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabVipConfig tabVipConfig) {
		super.save(tabVipConfig);
	}
	
	/**
	 * 更新状态
	 * @param tabVipConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabVipConfig tabVipConfig) {
		super.updateStatus(tabVipConfig);
	}
	
	/**
	 * 删除数据
	 * @param tabVipConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabVipConfig tabVipConfig) {
		super.delete(tabVipConfig);
	}
	
}