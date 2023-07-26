package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabActiveImgs;
import com.jeesite.modules.tab.dao.TabActiveImgsDao;

/**
 * tab_active_imgsService
 * @author 额
 * @version 2022-04-21
 */
@Service
@Transactional(readOnly=true)
public class TabActiveImgsService extends CrudService<TabActiveImgsDao, TabActiveImgs> {
	
	/**
	 * 获取单条数据
	 * @param tabActiveImgs
	 * @return
	 */
	@Override
	public TabActiveImgs get(TabActiveImgs tabActiveImgs) {
		return super.get(tabActiveImgs);
	}
	
	/**
	 * 查询分页数据
	 * @param tabActiveImgs 查询条件
	 * @param tabActiveImgs.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabActiveImgs> findPage(TabActiveImgs tabActiveImgs) {
		return super.findPage(tabActiveImgs);
	}
	
	/**
	 * 查询列表数据
	 * @param tabActiveImgs
	 * @return
	 */
	@Override
	public List<TabActiveImgs> findList(TabActiveImgs tabActiveImgs) {
		return super.findList(tabActiveImgs);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabActiveImgs
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabActiveImgs tabActiveImgs) {
		super.save(tabActiveImgs);
	}
	
	/**
	 * 更新状态
	 * @param tabActiveImgs
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabActiveImgs tabActiveImgs) {
		super.updateStatus(tabActiveImgs);
	}
	
	/**
	 * 删除数据
	 * @param tabActiveImgs
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabActiveImgs tabActiveImgs) {
		super.delete(tabActiveImgs);
	}
	
}