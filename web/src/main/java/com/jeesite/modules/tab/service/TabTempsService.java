package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabTemps;
import com.jeesite.modules.tab.dao.TabTempsDao;

/**
 * tab_tempsService
 * @author 32
 * @version 2022-04-21
 */
@Service
@Transactional(readOnly=true)
public class TabTempsService extends CrudService<TabTempsDao, TabTemps> {
	
	/**
	 * 获取单条数据
	 * @param tabTemps
	 * @return
	 */
	@Override
	public TabTemps get(TabTemps tabTemps) {
		return super.get(tabTemps);
	}
	
	/**
	 * 查询分页数据
	 * @param tabTemps 查询条件
	 * @param tabTemps.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabTemps> findPage(TabTemps tabTemps) {
		return super.findPage(tabTemps);
	}
	
	/**
	 * 查询列表数据
	 * @param tabTemps
	 * @return
	 */
	@Override
	public List<TabTemps> findList(TabTemps tabTemps) {
		return super.findList(tabTemps);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabTemps
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabTemps tabTemps) {
		super.save(tabTemps);
	}
	
	/**
	 * 更新状态
	 * @param tabTemps
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabTemps tabTemps) {
		super.updateStatus(tabTemps);
	}
	
	/**
	 * 删除数据
	 * @param tabTemps
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabTemps tabTemps) {
		super.delete(tabTemps);
	}
	
}