package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabOrders;
import com.jeesite.modules.tab.dao.TabOrdersDao;

/**
 * tab_ordersService
 * @author 1
 * @version 2022-04-10
 */
@Service
@Transactional(readOnly=true)
public class TabOrdersService extends CrudService<TabOrdersDao, TabOrders> {
	
	/**
	 * 获取单条数据
	 * @param tabOrders
	 * @return
	 */
	@Override
	public TabOrders get(TabOrders tabOrders) {
		return super.get(tabOrders);
	}

	public List<TabOrders> getListByStatus1(String day){
		return tabOrdersDao.getListByStatus1(day);
	}

	/**
	 * 查询分页数据
	 * @param tabOrders 查询条件
	 * @param tabOrders.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabOrders> findPage(TabOrders tabOrders) {
		return super.findPage(tabOrders);
	}
	
	/**
	 * 查询列表数据
	 * @param tabOrders
	 * @return
	 */
	@Override
	public List<TabOrders> findList(TabOrders tabOrders) {
		return super.findList(tabOrders);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabOrders
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabOrders tabOrders) {
		super.save(tabOrders);
	}
	
	/**
	 * 更新状态
	 * @param tabOrders
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabOrders tabOrders) {
		super.updateStatus(tabOrders);
	}
	
	/**
	 * 删除数据
	 * @param tabOrders
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabOrders tabOrders) {
		super.delete(tabOrders);
	}

	public Long getZdrs(Map<String, String> parame) {
 		return tabOrdersDao.getZdrs(parame);
	}
	
	@Autowired
	private TabOrdersDao tabOrdersDao ;

	public List<TabOrders> findList33(TabOrders tabOrders) {
 		return tabOrdersDao.findList33(tabOrders);
	}

	public Long getTotalRenshu(Map<String, String> parame) {
		return 		  tabOrdersDao.getTotalRenshu(parame);
	}

	public Double getTotalMoney(Map<String, String> parame) {
		return 		  tabOrdersDao.getTotalMoney(parame);
	}


}