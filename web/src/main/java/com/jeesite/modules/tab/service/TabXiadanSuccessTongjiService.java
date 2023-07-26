package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabXiadanSuccessTongji;
import com.jeesite.modules.tab.dao.TabXiadanSuccessTongjiDao;

/**
 * tab_xiadan_success_tongjiService
 * @author 1
 * @version 2022-04-05
 */
@Service
@Transactional(readOnly=true)
public class TabXiadanSuccessTongjiService extends CrudService<TabXiadanSuccessTongjiDao, TabXiadanSuccessTongji> {
	
	/**
	 * 获取单条数据
	 * @param tabXiadanSuccessTongji
	 * @return
	 */
	@Override
	public TabXiadanSuccessTongji get(TabXiadanSuccessTongji tabXiadanSuccessTongji) {
		return super.get(tabXiadanSuccessTongji);
	}
	
	/**
	 * 查询分页数据
	 * @param tabXiadanSuccessTongji 查询条件
	 * @param tabXiadanSuccessTongji.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabXiadanSuccessTongji> findPage(TabXiadanSuccessTongji tabXiadanSuccessTongji) {
		return super.findPage(tabXiadanSuccessTongji);
	}
	
	/**
	 * 查询列表数据
	 * @param tabXiadanSuccessTongji
	 * @return
	 */
	@Override
	public List<TabXiadanSuccessTongji> findList(TabXiadanSuccessTongji tabXiadanSuccessTongji) {
		return super.findList(tabXiadanSuccessTongji);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabXiadanSuccessTongji
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabXiadanSuccessTongji tabXiadanSuccessTongji) {
		super.save(tabXiadanSuccessTongji);
	}
	
	/**
	 * 更新状态
	 * @param tabXiadanSuccessTongji
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabXiadanSuccessTongji tabXiadanSuccessTongji) {
		super.updateStatus(tabXiadanSuccessTongji);
	}
	
	/**
	 * 删除数据
	 * @param tabXiadanSuccessTongji
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabXiadanSuccessTongji tabXiadanSuccessTongji) {
		super.delete(tabXiadanSuccessTongji);
	}

	public Double getTotalMoney(Map<String, String> parame) {
		return tabXiadanSuccessTongjiDao.getTotalMoney(parame);
	}
	
	@Autowired
	private  TabXiadanSuccessTongjiDao tabXiadanSuccessTongjiDao ;

	public Long getTotalRenshu(Map<String, String> parame) {
		return tabXiadanSuccessTongjiDao.getTotalRenshu(parame);
	}
	
}