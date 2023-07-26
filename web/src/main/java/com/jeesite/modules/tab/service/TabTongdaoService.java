package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import com.jeesite.modules.tab.dao.TabTixianLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabTongdao;
import com.jeesite.modules.tab.dao.TabTongdaoDao;

/**
 * 通道Service
 * @author 1
 * @version 2022-03-29
 */
@Service
@Transactional(readOnly=true)
public class TabTongdaoService extends CrudService<TabTongdaoDao, TabTongdao> {

	@Autowired
	private TabTongdaoDao tabTongdao ;

	/**
	 * 获取单条数据
	 * @param tabTongdao
	 * @return
	 */
	@Override
	public TabTongdao get(TabTongdao tabTongdao) {
		return super.get(tabTongdao);
	}
	
	/**
	 * 查询分页数据
	 * @param tabTongdao 查询条件
	 * @param tabTongdao.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabTongdao> findPage(TabTongdao tabTongdao) {
		return super.findPage(tabTongdao);
	}
	
	/**
	 * 查询列表数据
	 * @param tabTongdao
	 * @return
	 */
	@Override
	public List<TabTongdao> findList(TabTongdao tabTongdao) {
		return super.findList(tabTongdao);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabTongdao
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabTongdao tabTongdao) {
		super.save(tabTongdao);
	}
	
	/**
	 * 更新状态
	 * @param tabTongdao
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabTongdao tabTongdao) {
		super.updateStatus(tabTongdao);
	}
	
	/**
	 * 删除数据
	 * @param tabTongdao
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabTongdao tabTongdao) {
		super.delete(tabTongdao);
	}

	public List<TabTongdao> getList() {
		return tabTongdao.getList();
	}


	public List<TabTongdao> getEduList() {
		return tabTongdao.getEduList();
	}

	public List<TabTongdao> getShijianlunhuan(String type){
		return tabTongdao.getShijianlunhuan(type);
	}
}