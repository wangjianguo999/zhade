package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabRechargeJiangli;
import com.jeesite.modules.tab.dao.TabRechargeJiangliDao;

/**
 * tab_recharge_jiangliService
 * @author 1
 * @version 2022-04-08
 */
@Service
@Transactional(readOnly=true)
public class TabRechargeJiangliService extends CrudService<TabRechargeJiangliDao, TabRechargeJiangli> {
	
	/**
	 * 获取单条数据
	 * @param tabRechargeJiangli
	 * @return
	 */
	@Override
	public TabRechargeJiangli get(TabRechargeJiangli tabRechargeJiangli) {
		return super.get(tabRechargeJiangli);
	}
	
	/**
	 * 查询分页数据
	 * @param tabRechargeJiangli 查询条件
	 * @param tabRechargeJiangli.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabRechargeJiangli> findPage(TabRechargeJiangli tabRechargeJiangli) {
		return super.findPage(tabRechargeJiangli);
	}
	
	/**
	 * 查询列表数据
	 * @param tabRechargeJiangli
	 * @return
	 */
	@Override
	public List<TabRechargeJiangli> findList(TabRechargeJiangli tabRechargeJiangli) {
		return super.findList(tabRechargeJiangli);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabRechargeJiangli
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabRechargeJiangli tabRechargeJiangli) {
		super.save(tabRechargeJiangli);
	}
	
	/**
	 * 更新状态
	 * @param tabRechargeJiangli
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabRechargeJiangli tabRechargeJiangli) {
		super.updateStatus(tabRechargeJiangli);
	}
	
	/**
	 * 删除数据
	 * @param tabRechargeJiangli
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabRechargeJiangli tabRechargeJiangli) {
		super.delete(tabRechargeJiangli);
	}
	
}