package com.jeesite.modules.tab.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabValidCode;
import com.jeesite.modules.tab.dao.TabValidCodeDao;

/**
 * tab_valid_codeService
 * @author 3
 * @version 2021-12-10
 */
@Service
@Transactional(readOnly=true)
public class TabValidCodeService extends CrudService<TabValidCodeDao, TabValidCode> {
	
	/**
	 * 获取单条数据
	 * @param tabValidCode
	 * @return
	 */
	@Override
	public TabValidCode get(TabValidCode tabValidCode) {
		return super.get(tabValidCode);
	}
	
	/**
	 * 查询分页数据
	 * @param tabValidCode 查询条件
	 * @param tabValidCode.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabValidCode> findPage(TabValidCode tabValidCode) {
		return super.findPage(tabValidCode);
	}
	
	/**
	 * 查询列表数据
	 * @param tabValidCode
	 * @return
	 */
	@Override
	public List<TabValidCode> findList(TabValidCode tabValidCode) {
		return super.findList(tabValidCode);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabValidCode
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabValidCode tabValidCode) {
		super.save(tabValidCode);
	}
	
	/**
	 * 更新状态
	 * @param tabValidCode
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabValidCode tabValidCode) {
		super.updateStatus(tabValidCode);
	}
	
	/**
	 * 删除数据
	 * @param tabValidCode
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabValidCode tabValidCode) {
		super.delete(tabValidCode);
	}

	public List<TabValidCode> checkValidCode(Map<String, String> parame) {
		
		return tabValidCodeDao.checkValidCode(parame);
	}
	
	@Autowired
	private TabValidCodeDao tabValidCodeDao ;
	
}