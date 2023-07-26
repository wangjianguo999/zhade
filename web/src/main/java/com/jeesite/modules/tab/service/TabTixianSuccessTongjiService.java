package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabTixianSuccessTongji;
import com.jeesite.modules.tab.dao.TabTixianSuccessTongjiDao;

/**
 * tab_tixian_success_tongjiService
 * @author 32
 * @version 2022-04-04
 */
@Service
@Transactional(readOnly=true)
public class TabTixianSuccessTongjiService extends CrudService<TabTixianSuccessTongjiDao, TabTixianSuccessTongji> {
	
	/**
	 * 获取单条数据
	 * @param tabTixianSuccessTongji
	 * @return
	 */
	@Override
	public TabTixianSuccessTongji get(TabTixianSuccessTongji tabTixianSuccessTongji) {
		return super.get(tabTixianSuccessTongji);
	}
	
	/**
	 * 查询分页数据
	 * @param tabTixianSuccessTongji 查询条件
	 * @param tabTixianSuccessTongji.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabTixianSuccessTongji> findPage(TabTixianSuccessTongji tabTixianSuccessTongji) {
		return super.findPage(tabTixianSuccessTongji);
	}
	
	/**
	 * 查询列表数据
	 * @param tabTixianSuccessTongji
	 * @return
	 */
	@Override
	public List<TabTixianSuccessTongji> findList(TabTixianSuccessTongji tabTixianSuccessTongji) {
		return super.findList(tabTixianSuccessTongji);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabTixianSuccessTongji
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabTixianSuccessTongji tabTixianSuccessTongji) {
		super.save(tabTixianSuccessTongji);
	}
	
	/**
	 * 更新状态
	 * @param tabTixianSuccessTongji
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabTixianSuccessTongji tabTixianSuccessTongji) {
		super.updateStatus(tabTixianSuccessTongji);
	}
	
	/**
	 * 删除数据
	 * @param tabTixianSuccessTongji
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabTixianSuccessTongji tabTixianSuccessTongji) {
		super.delete(tabTixianSuccessTongji);
	}

	public Double getTotalMoney(Map<String, String> parame) {
		return tabTixianSuccessTongjiDao.getTotalMoney(parame);
	}

	public Long getTotalRenshu(Map<String, String> parame) {
 		return tabTixianSuccessTongjiDao.getTotalRenshu(parame);
	}
	
	@Autowired
	private TabTixianSuccessTongjiDao tabTixianSuccessTongjiDao ;
	
}