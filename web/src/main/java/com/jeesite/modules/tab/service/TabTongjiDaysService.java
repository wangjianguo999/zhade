package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabTongjiDays;
import com.jeesite.modules.tab.dao.TabTongjiDaysDao;

/**
 * tab_tongji_daysService
 * @author 2
 * @version 2022-04-11
 */
@Service
@Transactional(readOnly=true)
public class TabTongjiDaysService extends CrudService<TabTongjiDaysDao, TabTongjiDays> {
	
	/**
	 * 获取单条数据
	 * @param tabTongjiDays
	 * @return
	 */
	@Override
	public TabTongjiDays get(TabTongjiDays tabTongjiDays) {
		return super.get(tabTongjiDays);
	}
	
	/**
	 * 查询分页数据
	 * @param tabTongjiDays 查询条件
	 * @param tabTongjiDays.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabTongjiDays> findPage(TabTongjiDays tabTongjiDays) {
		return super.findPage(tabTongjiDays);
	}
	
	/**
	 * 查询列表数据
	 * @param tabTongjiDays
	 * @return
	 */
	@Override
	public List<TabTongjiDays> findList(TabTongjiDays tabTongjiDays) {
		return super.findList(tabTongjiDays);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabTongjiDays
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabTongjiDays tabTongjiDays) {
		super.save(tabTongjiDays);
	}
	
	/**
	 * 更新状态
	 * @param tabTongjiDays
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabTongjiDays tabTongjiDays) {
		super.updateStatus(tabTongjiDays);
	}
	
	/**
	 * 删除数据
	 * @param tabTongjiDays
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabTongjiDays tabTongjiDays) {
		super.delete(tabTongjiDays);
	}

	public List<TabTongjiDays> getTongji(Map<String, String> parame) {
		return tabTongjiDaysDao.getTongji(parame);
	}
	
	@Autowired
	private TabTongjiDaysDao  tabTongjiDaysDao ;
	
}