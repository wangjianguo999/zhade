package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabOrderData;
import com.jeesite.modules.tab.dao.TabOrderDataDao;

/**
 * tab_order_dataService
 * @author 32
 * @version 2021-12-18
 */
@Service
@Transactional(readOnly=true)
public class TabOrderDataService extends CrudService<TabOrderDataDao, TabOrderData> {
	
	/**
	 * 获取单条数据
	 * @param tabOrderData
	 * @return
	 */
	@Override
	public TabOrderData get(TabOrderData tabOrderData) {
		return super.get(tabOrderData);
	}
	
	/**
	 * 查询分页数据
	 * @param tabOrderData 查询条件
	 * @param tabOrderData.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabOrderData> findPage(TabOrderData tabOrderData) {
		return super.findPage(tabOrderData);
	}
	
	/**
	 * 查询列表数据
	 * @param tabOrderData
	 * @return
	 */
	@Override
	public List<TabOrderData> findList(TabOrderData tabOrderData) {
		return super.findList(tabOrderData);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabOrderData
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabOrderData tabOrderData) {
		super.save(tabOrderData);
	}

	@Transactional(readOnly=false)
	public void saveList(List<TabOrderData> list) {
		tabOrderDataDao.insertBatch(list);
	}

	@Transactional
	public void deleteListByOrderId(String orderId){
		tabOrderDataDao.deleteListByOrderId(orderId);
	}

	/**
	 * 更新状态
	 * @param tabOrderData
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabOrderData tabOrderData) {
		super.updateStatus(tabOrderData);
	}
	
	/**
	 * 删除数据
	 * @param tabOrderData
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabOrderData tabOrderData) {
		super.delete(tabOrderData);
	}

	public Double getGwje(Map<String, String> parame) {
 		return tabOrderDataDao.getGwje(parame);
	}
	
	@Autowired
	private  TabOrderDataDao  tabOrderDataDao;

	public Double getYouJin(Map<String, String> parame) {
		return tabOrderDataDao.getYouJin(parame);
	}

	public Double getTotal(Map<String, String> parame) {
		return tabOrderDataDao.getTotal(parame);

	}

	public List<TabOrderData> findList2() {
		return tabOrderDataDao.findList2();
	}
	@Transactional(readOnly=false)
	public void updateJiesuan(Map<String, String> parame) {
		tabOrderDataDao.updateJiesuan(parame);
	}

	public Double getBenJin(Map<String, String> parame) {
		// TODO Auto-generated method stub
		return tabOrderDataDao.getBenJin(parame);
	}

	public Double getYOujin2(Map<String, String> parame) {
		return tabOrderDataDao.getYOujin2(parame);
	}
	
	@Transactional(readOnly=false)
	public void updateYgzh(Map<String, String> parame) {
		  tabOrderDataDao.updateYgzh(parame);

	}

	public Double getTotalMoney(Map<String, String> parame) {
 		return 		  tabOrderDataDao.getTotalMoney(parame);
	}

	public Long getTotalRenshu(Map<String, String> parame) {
		return 		  tabOrderDataDao.getTotalRenshu(parame);
	}

	public String getLastData(Map<String, String> parame) {
		return 		  tabOrderDataDao.getLastData(parame);
	}

	public Double getTotalMoney2(Map<String, String> parame) {
 		return 		  tabOrderDataDao.getTotalMoney2(parame);

	}

	public Long getTotalRenshu2(Map<String, String> parame) {
 		return 		  tabOrderDataDao.getTotalRenshu2(parame);

	}	
}