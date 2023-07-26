package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabYaoqingJiangliLog;
import com.jeesite.modules.tab.dao.TabYaoqingJiangliLogDao;

/**
 * tab_yaoqing_jiangli_logService
 * @author 3
 * @version 2022-04-06
 */
@Service
@Transactional(readOnly=true)
public class TabYaoqingJiangliLogService extends CrudService<TabYaoqingJiangliLogDao, TabYaoqingJiangliLog> {
	
	/**
	 * 获取单条数据
	 * @param tabYaoqingJiangliLog
	 * @return
	 */
	@Override
	public TabYaoqingJiangliLog get(TabYaoqingJiangliLog tabYaoqingJiangliLog) {
		return super.get(tabYaoqingJiangliLog);
	}
	
	/**
	 * 查询分页数据
	 * @param tabYaoqingJiangliLog 查询条件
	 * @param tabYaoqingJiangliLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabYaoqingJiangliLog> findPage(TabYaoqingJiangliLog tabYaoqingJiangliLog) {
		return super.findPage(tabYaoqingJiangliLog);
	}
	
	/**
	 * 查询列表数据
	 * @param tabYaoqingJiangliLog
	 * @return
	 */
	@Override
	public List<TabYaoqingJiangliLog> findList(TabYaoqingJiangliLog tabYaoqingJiangliLog) {
		return super.findList(tabYaoqingJiangliLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabYaoqingJiangliLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabYaoqingJiangliLog tabYaoqingJiangliLog) {
		super.save(tabYaoqingJiangliLog);
	}
	
	/**
	 * 更新状态
	 * @param tabYaoqingJiangliLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabYaoqingJiangliLog tabYaoqingJiangliLog) {
		super.updateStatus(tabYaoqingJiangliLog);
	}
	
	/**
	 * 删除数据
	 * @param tabYaoqingJiangliLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabYaoqingJiangliLog tabYaoqingJiangliLog) {
		super.delete(tabYaoqingJiangliLog);
	}
	
}