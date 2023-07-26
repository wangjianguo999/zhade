package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabActiveContent;
import com.jeesite.modules.tab.dao.TabActiveContentDao;

/**
 * tab_active_contentService
 * @author 32
 * @version 2022-04-21
 */
@Service
@Transactional(readOnly=true)
public class TabActiveContentService extends CrudService<TabActiveContentDao, TabActiveContent> {
	
	/**
	 * 获取单条数据
	 * @param tabActiveContent
	 * @return
	 */
	@Override
	public TabActiveContent get(TabActiveContent tabActiveContent) {
		return super.get(tabActiveContent);
	}
	
	/**
	 * 查询分页数据
	 * @param tabActiveContent 查询条件
	 * @param tabActiveContent.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabActiveContent> findPage(TabActiveContent tabActiveContent) {
		return super.findPage(tabActiveContent);
	}
	
	/**
	 * 查询列表数据
	 * @param tabActiveContent
	 * @return
	 */
	@Override
	public List<TabActiveContent> findList(TabActiveContent tabActiveContent) {
		return super.findList(tabActiveContent);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabActiveContent
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabActiveContent tabActiveContent) {
		super.save(tabActiveContent);
	}
	
	/**
	 * 更新状态
	 * @param tabActiveContent
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabActiveContent tabActiveContent) {
		super.updateStatus(tabActiveContent);
	}
	
	/**
	 * 删除数据
	 * @param tabActiveContent
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabActiveContent tabActiveContent) {
		super.delete(tabActiveContent);
	}
	
}