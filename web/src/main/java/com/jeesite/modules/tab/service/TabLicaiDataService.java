package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabLicaiData;
import com.jeesite.modules.tab.dao.TabLicaiDataDao;

/**
 * tab_licai_dataService
 * @author 1
 * @version 2021-12-17
 */
@Service
@Transactional(readOnly=true)
public class TabLicaiDataService extends CrudService<TabLicaiDataDao, TabLicaiData> {
	
	/**
	 * 获取单条数据
	 * @param tabLicaiData
	 * @return
	 */
	@Override
	public TabLicaiData get(TabLicaiData tabLicaiData) {
		return super.get(tabLicaiData);
	}
	
	/**
	 * 查询分页数据
	 * @param tabLicaiData 查询条件
	 * @param tabLicaiData.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabLicaiData> findPage(TabLicaiData tabLicaiData) {
		return super.findPage(tabLicaiData);
	}
	
	/**
	 * 查询列表数据
	 * @param tabLicaiData
	 * @return
	 */
	@Override
	public List<TabLicaiData> findList(TabLicaiData tabLicaiData) {
		return super.findList(tabLicaiData);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabLicaiData
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabLicaiData tabLicaiData) {
		super.save(tabLicaiData);
	}
	
	/**
	 * 更新状态
	 * @param tabLicaiData
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabLicaiData tabLicaiData) {
		super.updateStatus(tabLicaiData);
	}
	
	/**
	 * 删除数据
	 * @param tabLicaiData
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabLicaiData tabLicaiData) {
		super.delete(tabLicaiData);
	}
	@Transactional(readOnly=false)
	public void updateYgzh(Map<String, String> parame) {
		tabLicaiDataDao.updateYgzh(parame);
	}
	
	@Autowired
	private TabLicaiDataDao  tabLicaiDataDao ;
	
}