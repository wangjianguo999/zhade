package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabYaoqingPeizhi;
import com.jeesite.modules.tab.dao.TabYaoqingPeizhiDao;

/**
 * tab_yaoqing_peizhiService
 * @author 1
 * @version 2022-04-23
 */
@Service
@Transactional(readOnly=true)
public class TabYaoqingPeizhiService extends CrudService<TabYaoqingPeizhiDao, TabYaoqingPeizhi> {
	
	/**
	 * 获取单条数据
	 * @param tabYaoqingPeizhi
	 * @return
	 */
	@Override
	public TabYaoqingPeizhi get(TabYaoqingPeizhi tabYaoqingPeizhi) {
		return super.get(tabYaoqingPeizhi);
	}
	
	/**
	 * 查询分页数据
	 * @param tabYaoqingPeizhi 查询条件
	 * @param tabYaoqingPeizhi.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabYaoqingPeizhi> findPage(TabYaoqingPeizhi tabYaoqingPeizhi) {
		return super.findPage(tabYaoqingPeizhi);
	}
	
	/**
	 * 查询列表数据
	 * @param tabYaoqingPeizhi
	 * @return
	 */
	@Override
	public List<TabYaoqingPeizhi> findList(TabYaoqingPeizhi tabYaoqingPeizhi) {
		return super.findList(tabYaoqingPeizhi);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabYaoqingPeizhi
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabYaoqingPeizhi tabYaoqingPeizhi) {
		super.save(tabYaoqingPeizhi);
	}
	
	/**
	 * 更新状态
	 * @param tabYaoqingPeizhi
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabYaoqingPeizhi tabYaoqingPeizhi) {
		super.updateStatus(tabYaoqingPeizhi);
	}
	
	/**
	 * 删除数据
	 * @param tabYaoqingPeizhi
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabYaoqingPeizhi tabYaoqingPeizhi) {
		super.delete(tabYaoqingPeizhi);
	}
	
}