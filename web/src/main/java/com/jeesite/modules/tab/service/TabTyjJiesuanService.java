package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabTyjJiesuan;
import com.jeesite.modules.tab.dao.TabTyjJiesuanDao;

/**
 * tab_tyj_jiesuanService
 * @author 1
 * @version 2022-04-12
 */
@Service
@Transactional(readOnly=true)
public class TabTyjJiesuanService extends CrudService<TabTyjJiesuanDao, TabTyjJiesuan> {
	
	/**
	 * 获取单条数据
	 * @param tabTyjJiesuan
	 * @return
	 */
	@Override
	public TabTyjJiesuan get(TabTyjJiesuan tabTyjJiesuan) {
		return super.get(tabTyjJiesuan);
	}
	
	/**
	 * 查询分页数据
	 * @param tabTyjJiesuan 查询条件
	 * @param tabTyjJiesuan.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabTyjJiesuan> findPage(TabTyjJiesuan tabTyjJiesuan) {
		return super.findPage(tabTyjJiesuan);
	}

	@Transactional
	public void deleteListByOrderId(String orderId){
		tabTyjJiesuanDao.deleteListByOrderId(orderId);
	}

	/**
	 * 查询列表数据
	 * @param tabTyjJiesuan
	 * @return
	 */
	@Override
	public List<TabTyjJiesuan> findList(TabTyjJiesuan tabTyjJiesuan) {
		return super.findList(tabTyjJiesuan);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabTyjJiesuan
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabTyjJiesuan tabTyjJiesuan) {
		super.save(tabTyjJiesuan);
	}
	
	/**
	 * 更新状态
	 * @param tabTyjJiesuan
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabTyjJiesuan tabTyjJiesuan) {
		super.updateStatus(tabTyjJiesuan);
	}
	
	/**
	 * 删除数据
	 * @param tabTyjJiesuan
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabTyjJiesuan tabTyjJiesuan) {
		super.delete(tabTyjJiesuan);
	}

	public Double getUserMoney(Map<String, String> parame) {
 		return tabTyjJiesuanDao.getUserMoney(parame);
	}
	
	@Autowired
	private TabTyjJiesuanDao  tabTyjJiesuanDao ;
	@Transactional(readOnly=false)
	public void deleteYc(Map<String, String> parame) {
		tabTyjJiesuanDao.deleteYc(parame);
	}
	@Transactional(readOnly=false)
	public void deleteYc2(Map<String, String> parame) {
		tabTyjJiesuanDao.deleteYc2(parame);
		
	}

	@Transactional
	public void deleteByUserId(String userid){
		tabTyjJiesuanDao.deleteByUserId(userid);
	}
	
}