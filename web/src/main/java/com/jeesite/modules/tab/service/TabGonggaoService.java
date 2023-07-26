package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabGonggao;
import com.jeesite.modules.tab.dao.TabGonggaoDao;

/**
 * tab_gonggaoService
 * @author 1
 * @version 2022-04-10
 */
@Service
@Transactional(readOnly=true)
public class TabGonggaoService extends CrudService<TabGonggaoDao, TabGonggao> {
	
	/**
	 * 获取单条数据
	 * @param tabGonggao
	 * @return
	 */
	@Override
	public TabGonggao get(TabGonggao tabGonggao) {
		return super.get(tabGonggao);
	}
	
	/**
	 * 查询分页数据
	 * @param tabGonggao 查询条件
	 * @param tabGonggao.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabGonggao> findPage(TabGonggao tabGonggao) {
		return super.findPage(tabGonggao);
	}
	
	/**
	 * 查询列表数据
	 * @param tabGonggao
	 * @return
	 */
	@Override
	public List<TabGonggao> findList(TabGonggao tabGonggao) {
		return super.findList(tabGonggao);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabGonggao
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabGonggao tabGonggao) {
		super.save(tabGonggao);
	}
	
	/**
	 * 更新状态
	 * @param tabGonggao
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabGonggao tabGonggao) {
		super.updateStatus(tabGonggao);
	}
	
	/**
	 * 删除数据
	 * @param tabGonggao
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabGonggao tabGonggao) {
		super.delete(tabGonggao);
	}
	
}