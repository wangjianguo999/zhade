package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabYaoqingJiangli;
import com.jeesite.modules.tab.dao.TabYaoqingJiangliDao;

/**
 * tab_yaoqing_jiangliService
 * @author 1
 * @version 2022-04-06
 */
@Service
@Transactional(readOnly=true)
public class TabYaoqingJiangliService extends CrudService<TabYaoqingJiangliDao, TabYaoqingJiangli> {
	
	/**
	 * 获取单条数据
	 * @param tabYaoqingJiangli
	 * @return
	 */
	@Override
	public TabYaoqingJiangli get(TabYaoqingJiangli tabYaoqingJiangli) {
		return super.get(tabYaoqingJiangli);
	}
	
	/**
	 * 查询分页数据
	 * @param tabYaoqingJiangli 查询条件
	 * @param tabYaoqingJiangli.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabYaoqingJiangli> findPage(TabYaoqingJiangli tabYaoqingJiangli) {
		return super.findPage(tabYaoqingJiangli);
	}
	
	/**
	 * 查询列表数据
	 * @param tabYaoqingJiangli
	 * @return
	 */
	@Override
	public List<TabYaoqingJiangli> findList(TabYaoqingJiangli tabYaoqingJiangli) {
		return super.findList(tabYaoqingJiangli);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabYaoqingJiangli
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabYaoqingJiangli tabYaoqingJiangli) {
		super.save(tabYaoqingJiangli);
	}
	
	/**
	 * 更新状态
	 * @param tabYaoqingJiangli
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabYaoqingJiangli tabYaoqingJiangli) {
		super.updateStatus(tabYaoqingJiangli);
	}
	
	/**
	 * 删除数据
	 * @param tabYaoqingJiangli
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabYaoqingJiangli tabYaoqingJiangli) {
		super.delete(tabYaoqingJiangli);
	}
	
}