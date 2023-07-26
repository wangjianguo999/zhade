package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabUserBank;
import com.jeesite.modules.tab.dao.TabUserBankDao;

/**
 * tab_user_bankService
 * @author 3
 * @version 2022-04-09
 */
@Service
@Transactional(readOnly=true)
public class TabUserBankService extends CrudService<TabUserBankDao, TabUserBank> {
	
	/**
	 * 获取单条数据
	 * @param tabUserBank
	 * @return
	 */
	@Override
	public TabUserBank get(TabUserBank tabUserBank) {
		return super.get(tabUserBank);
	}
	
	/**
	 * 查询分页数据
	 * @param tabUserBank 查询条件
	 * @param tabUserBank.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabUserBank> findPage(TabUserBank tabUserBank) {
		return super.findPage(tabUserBank);
	}
	
	/**
	 * 查询列表数据
	 * @param tabUserBank
	 * @return
	 */
	@Override
	public List<TabUserBank> findList(TabUserBank tabUserBank) {
		return super.findList(tabUserBank);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabUserBank
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabUserBank tabUserBank) {
		super.save(tabUserBank);
	}
	
	/**
	 * 更新状态
	 * @param tabUserBank
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabUserBank tabUserBank) {
		super.updateStatus(tabUserBank);
	}
	
	/**
	 * 删除数据
	 * @param tabUserBank
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabUserBank tabUserBank) {
		super.delete(tabUserBank);
	}
	
}