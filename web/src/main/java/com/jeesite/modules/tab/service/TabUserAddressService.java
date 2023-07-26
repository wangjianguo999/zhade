package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabUserAddress;
import com.jeesite.modules.tab.dao.TabUserAddressDao;

/**
 * tab_user_addressService
 * @author 1
 * @version 2021-12-13
 */
@Service
@Transactional(readOnly=true)
public class TabUserAddressService extends CrudService<TabUserAddressDao, TabUserAddress> {
	
	/**
	 * 获取单条数据
	 * @param tabUserAddress
	 * @return
	 */
	@Override
	public TabUserAddress get(TabUserAddress tabUserAddress) {
		return super.get(tabUserAddress);
	}
	
	/**
	 * 查询分页数据
	 * @param tabUserAddress 查询条件
	 * @param tabUserAddress.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabUserAddress> findPage(TabUserAddress tabUserAddress) {
		return super.findPage(tabUserAddress);
	}
	
	/**
	 * 查询列表数据
	 * @param tabUserAddress
	 * @return
	 */
	@Override
	public List<TabUserAddress> findList(TabUserAddress tabUserAddress) {
		return super.findList(tabUserAddress);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabUserAddress
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabUserAddress tabUserAddress) {
		super.save(tabUserAddress);
	}
	
	/**
	 * 更新状态
	 * @param tabUserAddress
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabUserAddress tabUserAddress) {
		super.updateStatus(tabUserAddress);
	}
	
	/**
	 * 删除数据
	 * @param tabUserAddress
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabUserAddress tabUserAddress) {
		super.delete(tabUserAddress);
	}
	
}