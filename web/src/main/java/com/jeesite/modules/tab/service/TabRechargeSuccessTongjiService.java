package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabRechargeSuccessTongji;
import com.jeesite.modules.tab.dao.TabRechargeSuccessTongjiDao;

/**
 * tab_recharge_success_tongjiService
 * @author 1
 * @version 2022-04-04
 */
@Service
@Transactional(readOnly=true)
public class TabRechargeSuccessTongjiService extends CrudService<TabRechargeSuccessTongjiDao, TabRechargeSuccessTongji> {
	
	/**
	 * 获取单条数据
	 * @param tabRechargeSuccessTongji
	 * @return
	 */
	@Override
	public TabRechargeSuccessTongji get(TabRechargeSuccessTongji tabRechargeSuccessTongji) {
		return super.get(tabRechargeSuccessTongji);
	}
	
	/**
	 * 查询分页数据
	 * @param tabRechargeSuccessTongji 查询条件
	 * @param tabRechargeSuccessTongji.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabRechargeSuccessTongji> findPage(TabRechargeSuccessTongji tabRechargeSuccessTongji) {
		return super.findPage(tabRechargeSuccessTongji);
	}
	
	/**
	 * 查询列表数据
	 * @param tabRechargeSuccessTongji
	 * @return
	 */
	@Override
	public List<TabRechargeSuccessTongji> findList(TabRechargeSuccessTongji tabRechargeSuccessTongji) {
		return super.findList(tabRechargeSuccessTongji);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabRechargeSuccessTongji
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabRechargeSuccessTongji tabRechargeSuccessTongji) {
		super.save(tabRechargeSuccessTongji);
	}
	
	/**
	 * 更新状态
	 * @param tabRechargeSuccessTongji
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabRechargeSuccessTongji tabRechargeSuccessTongji) {
		super.updateStatus(tabRechargeSuccessTongji);
	}
	
	/**
	 * 删除数据
	 * @param tabRechargeSuccessTongji
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabRechargeSuccessTongji tabRechargeSuccessTongji) {
		super.delete(tabRechargeSuccessTongji);
	}

	public Long getTotalRenshu(Map<String, String> parame) {
		return tabRechargeSuccessTongjiDao.getTotalRenshu(parame);
	}
	
	@Autowired
	private TabRechargeSuccessTongjiDao tabRechargeSuccessTongjiDao ;

	public Double getTotalMoney(Map<String, String> parame) {
		return tabRechargeSuccessTongjiDao.getTotalMoney(parame);
	}
	
}