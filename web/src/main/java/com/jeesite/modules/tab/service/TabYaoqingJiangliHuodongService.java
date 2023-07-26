package com.jeesite.modules.tab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.entity.TabYaoqingJiangliHuodong;
import com.jeesite.modules.tab.dao.TabYaoqingJiangliHuodongDao;

/**
 * tab_yaoqing_jiangli_huodongService
 * @author 3
 * @version 2022-04-21
 */
@Service
@Transactional(readOnly=true)
public class TabYaoqingJiangliHuodongService extends CrudService<TabYaoqingJiangliHuodongDao, TabYaoqingJiangliHuodong> {
	
	/**
	 * 获取单条数据
	 * @param tabYaoqingJiangliHuodong
	 * @return
	 */
	@Override
	public TabYaoqingJiangliHuodong get(TabYaoqingJiangliHuodong tabYaoqingJiangliHuodong) {
		return super.get(tabYaoqingJiangliHuodong);
	}
	
	/**
	 * 查询分页数据
	 * @param tabYaoqingJiangliHuodong 查询条件
	 * @param tabYaoqingJiangliHuodong.page 分页对象
	 * @return
	 */
	@Override
	public Page<TabYaoqingJiangliHuodong> findPage(TabYaoqingJiangliHuodong tabYaoqingJiangliHuodong) {
		return super.findPage(tabYaoqingJiangliHuodong);
	}
	
	/**
	 * 查询列表数据
	 * @param tabYaoqingJiangliHuodong
	 * @return
	 */
	@Override
	public List<TabYaoqingJiangliHuodong> findList(TabYaoqingJiangliHuodong tabYaoqingJiangliHuodong) {
		return super.findList(tabYaoqingJiangliHuodong);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tabYaoqingJiangliHuodong
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TabYaoqingJiangliHuodong tabYaoqingJiangliHuodong) {
		super.save(tabYaoqingJiangliHuodong);
	}
	
	/**
	 * 更新状态
	 * @param tabYaoqingJiangliHuodong
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TabYaoqingJiangliHuodong tabYaoqingJiangliHuodong) {
		super.updateStatus(tabYaoqingJiangliHuodong);
	}
	
	/**
	 * 删除数据
	 * @param tabYaoqingJiangliHuodong
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TabYaoqingJiangliHuodong tabYaoqingJiangliHuodong) {
		super.delete(tabYaoqingJiangliHuodong);
	}
	
}