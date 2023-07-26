package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabShoukuanZhanghao;
import com.jeesite.modules.tab.dao.TabShoukuanZhanghaoDao;

/**
 * tab_shoukuan_zhanghaoService
 * @author 1
 * @version 2022-03-16
 */
@Service
@Transactional(readOnly=true)
public class TabShoukuanZhanghaoService extends CrudService<TabShoukuanZhanghaoDao, TabShoukuanZhanghao> {
	
	/**
	 * 获取单条数据
	 * @param tabShoukuanZhanghao
	 * @return
	 */
	@Override
	public TabShoukuanZhanghao get(TabShoukuanZhanghao tabShoukuanZhanghao) {
		return super.get(tabShoukuanZhanghao);
	}
	
	/**
	 * 查询分页数据
	 * @param tabShoukuanZhanghao 查询条件
	 * @param tabShoukuanZhanghao.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabShoukuanZhanghao> findPage(TabShoukuanZhanghao tabShoukuanZhanghao) {
		return super.findPage(tabShoukuanZhanghao);
	}
	
	/**
	 * 查询列表数据
	 * @param tabShoukuanZhanghao
	 * @return
	 */
	@Override
	public List<TabShoukuanZhanghao> findList(TabShoukuanZhanghao tabShoukuanZhanghao) {
		return super.findList(tabShoukuanZhanghao);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabShoukuanZhanghao
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabShoukuanZhanghao tabShoukuanZhanghao) {
		super.save(tabShoukuanZhanghao);
	}
	
	/**
	 * 更新状态
	 * @param tabShoukuanZhanghao
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabShoukuanZhanghao tabShoukuanZhanghao) {
		super.updateStatus(tabShoukuanZhanghao);
	}
	
	/**
	 * 删除数据
	 * @param tabShoukuanZhanghao
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabShoukuanZhanghao tabShoukuanZhanghao) {
		super.delete(tabShoukuanZhanghao);
	}
	
}