package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabFacebook;
import com.jeesite.modules.tab.dao.TabFacebookDao;

/**
 * tab_facebookService
 * @author 32
 * @version 2021-12-13
 */
@Service
@Transactional(readOnly=true)
public class TabFacebookService extends CrudService<TabFacebookDao, TabFacebook> {
	
	/**
	 * 获取单条数据
	 * @param tabFacebook
	 * @return
	 */
	@Override
	public TabFacebook get(TabFacebook tabFacebook) {
		return super.get(tabFacebook);
	}
	
	/**
	 * 查询分页数据
	 * @param tabFacebook 查询条件
	 * @param tabFacebook.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabFacebook> findPage(TabFacebook tabFacebook) {
		return super.findPage(tabFacebook);
	}
	
	/**
	 * 查询列表数据
	 * @param tabFacebook
	 * @return
	 */
	@Override
	public List<TabFacebook> findList(TabFacebook tabFacebook) {
		return super.findList(tabFacebook);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabFacebook
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabFacebook tabFacebook) {
		super.save(tabFacebook);
	}
	
	/**
	 * 更新状态
	 * @param tabFacebook
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabFacebook tabFacebook) {
		super.updateStatus(tabFacebook);
	}
	
	/**
	 * 删除数据
	 * @param tabFacebook
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabFacebook tabFacebook) {
		super.delete(tabFacebook);
	}
	
}