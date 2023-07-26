package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabFenxiaoConfig;
import com.jeesite.modules.tab.dao.TabFenxiaoConfigDao;

/**
 * tab_fenxiao_configService
 * @author 32
 * @version 2022-03-18
 */
@Service
@Transactional(readOnly=true)
public class TabFenxiaoConfigService extends CrudService<TabFenxiaoConfigDao, TabFenxiaoConfig> {
	
	/**
	 * 获取单条数据
	 * @param tabFenxiaoConfig
	 * @return
	 */
	@Override
	public TabFenxiaoConfig get(TabFenxiaoConfig tabFenxiaoConfig) {
		return super.get(tabFenxiaoConfig);
	}
	
	/**
	 * 查询分页数据
	 * @param tabFenxiaoConfig 查询条件
	 * @param tabFenxiaoConfig.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabFenxiaoConfig> findPage(TabFenxiaoConfig tabFenxiaoConfig) {
		return super.findPage(tabFenxiaoConfig);
	}
	
	/**
	 * 查询列表数据
	 * @param tabFenxiaoConfig
	 * @return
	 */
	@Override
	public List<TabFenxiaoConfig> findList(TabFenxiaoConfig tabFenxiaoConfig) {
		return super.findList(tabFenxiaoConfig);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabFenxiaoConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabFenxiaoConfig tabFenxiaoConfig) {
		super.save(tabFenxiaoConfig);
	}
	
	/**
	 * 更新状态
	 * @param tabFenxiaoConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabFenxiaoConfig tabFenxiaoConfig) {
		super.updateStatus(tabFenxiaoConfig);
	}
	
	/**
	 * 删除数据
	 * @param tabFenxiaoConfig
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabFenxiaoConfig tabFenxiaoConfig) {
		super.delete(tabFenxiaoConfig);
	}
	
}