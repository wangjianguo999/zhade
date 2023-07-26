package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabProductData;
import com.jeesite.modules.tab.dao.TabProductDataDao;

/**
 * tab_product_dataService
 * @author 1
 * @version 2021-12-18
 */
@Service
@Transactional(readOnly=true)
public class TabProductDataService extends CrudService<TabProductDataDao, TabProductData> {
	
	/**
	 * 获取单条数据
	 * @param tabProductData
	 * @return
	 */
	@Override
	public TabProductData get(TabProductData tabProductData) {
		return super.get(tabProductData);
	}
	
	/**
	 * 查询分页数据
	 * @param tabProductData 查询条件
	 * @param tabProductData.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabProductData> findPage(TabProductData tabProductData) {
		return super.findPage(tabProductData);
	}
	
	/**
	 * 查询列表数据
	 * @param tabProductData
	 * @return
	 */
	@Override
	public List<TabProductData> findList(TabProductData tabProductData) {
		return super.findList(tabProductData);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabProductData
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabProductData tabProductData) {
		super.save(tabProductData);
	}
	
	/**
	 * 更新状态
	 * @param tabProductData
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabProductData tabProductData) {
		super.updateStatus(tabProductData);
	}
	
	/**
	 * 删除数据
	 * @param tabProductData
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabProductData tabProductData) {
		super.delete(tabProductData);
	}

	public List<TabProductData> list(Map<String, Object> parame) {
 		return tabProductDataDao.findList2(parame);
	}

	public List<TabProductData> newlist(Map<String, Object> parame) {
		return tabProductDataDao.newlist(parame);
	}

	public List<TabProductData> newlist1(Map<String, Object> parame) {
		return tabProductDataDao.newlist1(parame);
	}

	@Autowired
	private  TabProductDataDao  tabProductDataDao ;

	public List<TabProductData> list2(Map<String, Object> parame) {
 		return tabProductDataDao.list2(parame);
	}

	public List<TabProductData> findList2(Map<String, String> request) {
 		return tabProductDataDao.list33(request);
	}
	
}