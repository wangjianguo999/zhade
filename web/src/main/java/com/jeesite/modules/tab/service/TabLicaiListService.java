package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabLicaiList;
import com.jeesite.modules.tab.dao.TabLicaiListDao;

/**
 * tab_licai_listService
 * @author 32
 * @version 2022-06-15
 */
@Service
@Transactional(readOnly=true)
public class TabLicaiListService extends CrudService<TabLicaiListDao, TabLicaiList> {
	
	/**
	 * 获取单条数据
	 * @param tabLicaiList
	 * @return
	 */
	@Override
	public TabLicaiList get(TabLicaiList tabLicaiList) {
		return super.get(tabLicaiList);
	}
	
	/**
	 * 查询分页数据
	 * @param tabLicaiList 查询条件
	 * @param tabLicaiList.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabLicaiList> findPage(TabLicaiList tabLicaiList) {
		return super.findPage(tabLicaiList);
	}
	
	/**
	 * 查询列表数据
	 * @param tabLicaiList
	 * @return
	 */
	@Override
	public List<TabLicaiList> findList(TabLicaiList tabLicaiList) {
		return super.findList(tabLicaiList);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabLicaiList
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabLicaiList tabLicaiList) {
		super.save(tabLicaiList);
	}
	
	/**
	 * 更新状态
	 * @param tabLicaiList
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabLicaiList tabLicaiList) {
		super.updateStatus(tabLicaiList);
	}
	
	/**
	 * 删除数据
	 * @param tabLicaiList
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabLicaiList tabLicaiList) {
		super.delete(tabLicaiList);
	}
	
}