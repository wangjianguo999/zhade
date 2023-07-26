package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabSiderData;
import com.jeesite.modules.tab.dao.TabSiderDataDao;

/**
 * tab_sider_dataService
 * @author 3333
 * @version 2021-12-11
 */
@Service
@Transactional(readOnly=true)
public class TabSiderDataService extends CrudService<TabSiderDataDao, TabSiderData> {
	
	/**
	 * 获取单条数据
	 * @param tabSiderData
	 * @return
	 */
	@Override
	public TabSiderData get(TabSiderData tabSiderData) {
		return super.get(tabSiderData);
	}
	
	/**
	 * 查询分页数据
	 * @param tabSiderData 查询条件
	 * @param tabSiderData.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabSiderData> findPage(TabSiderData tabSiderData) {
		return super.findPage(tabSiderData);
	}
	
	/**
	 * 查询列表数据
	 * @param tabSiderData
	 * @return
	 */
	@Override
	public List<TabSiderData> findList(TabSiderData tabSiderData) {
		return super.findList(tabSiderData);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabSiderData
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabSiderData tabSiderData) {
		super.save(tabSiderData);
	}
	
	/**
	 * 更新状态
	 * @param tabSiderData
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabSiderData tabSiderData) {
		super.updateStatus(tabSiderData);
	}
	
	/**
	 * 删除数据
	 * @param tabSiderData
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabSiderData tabSiderData) {
		super.delete(tabSiderData);
	}
	
}