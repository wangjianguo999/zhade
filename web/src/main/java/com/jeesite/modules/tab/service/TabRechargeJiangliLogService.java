package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabRechargeJiangliLog;
import com.jeesite.modules.tab.dao.TabRechargeJiangliLogDao;

/**
 * tab_recharge_jiangli_logService
 * @author 4
 * @version 2022-04-08
 */
@Service
@Transactional(readOnly=true)
public class TabRechargeJiangliLogService extends CrudService<TabRechargeJiangliLogDao, TabRechargeJiangliLog> {
	
	/**
	 * 获取单条数据
	 * @param tabRechargeJiangliLog
	 * @return
	 */
	@Override
	public TabRechargeJiangliLog get(TabRechargeJiangliLog tabRechargeJiangliLog) {
		return super.get(tabRechargeJiangliLog);
	}
	
	/**
	 * 查询分页数据
	 * @param tabRechargeJiangliLog 查询条件
	 * @param tabRechargeJiangliLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabRechargeJiangliLog> findPage(TabRechargeJiangliLog tabRechargeJiangliLog) {
		return super.findPage(tabRechargeJiangliLog);
	}
	
	/**
	 * 查询列表数据
	 * @param tabRechargeJiangliLog
	 * @return
	 */
	@Override
	public List<TabRechargeJiangliLog> findList(TabRechargeJiangliLog tabRechargeJiangliLog) {
		return super.findList(tabRechargeJiangliLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabRechargeJiangliLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabRechargeJiangliLog tabRechargeJiangliLog) {
		super.save(tabRechargeJiangliLog);
	}
	
	/**
	 * 更新状态
	 * @param tabRechargeJiangliLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabRechargeJiangliLog tabRechargeJiangliLog) {
		super.updateStatus(tabRechargeJiangliLog);
	}
	
	/**
	 * 删除数据
	 * @param tabRechargeJiangliLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabRechargeJiangliLog tabRechargeJiangliLog) {
		super.delete(tabRechargeJiangliLog);
	}
	
}