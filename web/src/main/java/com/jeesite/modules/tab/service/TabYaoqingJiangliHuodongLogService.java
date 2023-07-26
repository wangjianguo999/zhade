package com.jeesite.modules.tab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabYaoqingJiangliHuodongLog;
import com.jeesite.modules.tab.dao.TabYaoqingJiangliHuodongLogDao;

/**
 * tab_yaoqing_jiangli_huodong_logService
 * @author 3
 * @version 2022-04-21
 */
@Service
@Transactional(readOnly=true)
public class TabYaoqingJiangliHuodongLogService extends CrudService<TabYaoqingJiangliHuodongLogDao, TabYaoqingJiangliHuodongLog> {
	
	/**
	 * 获取单条数据
	 * @param tabYaoqingJiangliHuodongLog
	 * @return
	 */
	@Override
	public TabYaoqingJiangliHuodongLog get(TabYaoqingJiangliHuodongLog tabYaoqingJiangliHuodongLog) {
		return super.get(tabYaoqingJiangliHuodongLog);
	}
	
	/**
	 * 查询分页数据
	 * @param tabYaoqingJiangliHuodongLog 查询条件
	 * @param tabYaoqingJiangliHuodongLog.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabYaoqingJiangliHuodongLog> findPage(TabYaoqingJiangliHuodongLog tabYaoqingJiangliHuodongLog) {
		return super.findPage(tabYaoqingJiangliHuodongLog);
	}
	
	/**
	 * 查询列表数据
	 * @param tabYaoqingJiangliHuodongLog
	 * @return
	 */
	@Override
	public List<TabYaoqingJiangliHuodongLog> findList(TabYaoqingJiangliHuodongLog tabYaoqingJiangliHuodongLog) {
		return super.findList(tabYaoqingJiangliHuodongLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabYaoqingJiangliHuodongLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabYaoqingJiangliHuodongLog tabYaoqingJiangliHuodongLog) {
		super.save(tabYaoqingJiangliHuodongLog);
	}
	
	/**
	 * 更新状态
	 * @param tabYaoqingJiangliHuodongLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabYaoqingJiangliHuodongLog tabYaoqingJiangliHuodongLog) {
		super.updateStatus(tabYaoqingJiangliHuodongLog);
	}
	
	/**
	 * 删除数据
	 * @param tabYaoqingJiangliHuodongLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabYaoqingJiangliHuodongLog tabYaoqingJiangliHuodongLog) {
		super.delete(tabYaoqingJiangliHuodongLog);
	}

	public List<TabYaoqingJiangliHuodongLog> findList22() {
		return tabYaoqingJiangliHuodongLogDao.findList22();
	}
	
	@Autowired
	private TabYaoqingJiangliHuodongLogDao tabYaoqingJiangliHuodongLogDao ;
}