package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabYuangongData;
import com.jeesite.modules.tab.dao.TabYuangongDataDao;

/**
 * tab_yuangong_dataService
 * @author 1
 * @version 2022-03-22
 */
@Service
@Transactional(readOnly=true)
public class TabYuangongDataService extends CrudService<TabYuangongDataDao, TabYuangongData> {
	
	/**
	 * 获取单条数据
	 * @param tabYuangongData
	 * @return
	 */
	@Override
	public TabYuangongData get(TabYuangongData tabYuangongData) {
		return super.get(tabYuangongData);
	}
	
	/**
	 * 查询分页数据
	 * @param tabYuangongData 查询条件
	 * @param tabYuangongData.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabYuangongData> findPage(TabYuangongData tabYuangongData) {
		return super.findPage(tabYuangongData);
	}
	
	/**
	 * 查询列表数据
	 * @param tabYuangongData
	 * @return
	 */
	@Override
	public List<TabYuangongData> findList(TabYuangongData tabYuangongData) {
		return super.findList(tabYuangongData);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabYuangongData
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabYuangongData tabYuangongData) {
		super.save(tabYuangongData);
	}
	
	/**
	 * 更新状态
	 * @param tabYuangongData
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabYuangongData tabYuangongData) {
		super.updateStatus(tabYuangongData);
	}
	
	/**
	 * 删除数据
	 * @param tabYuangongData
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabYuangongData tabYuangongData) {
		super.delete(tabYuangongData);
	}
	
}