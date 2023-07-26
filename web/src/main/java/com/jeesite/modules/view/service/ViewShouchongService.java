package com.jeesite.modules.view.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.view.entity.ViewShouchong;
import com.jeesite.modules.view.dao.ViewShouchongDao;

/**
 * VIEWService
 * @author 22
 * @version 2022-03-20
 */
@Service
@Transactional(readOnly=true)
public class ViewShouchongService extends CrudService<ViewShouchongDao, ViewShouchong> {
	
	/**
	 * 获取单条数据
	 * @param viewShouchong
	 * @return
	 */
	@Override
	public ViewShouchong get(ViewShouchong viewShouchong) {
		return super.get(viewShouchong);
	}
	
	/**
	 * 查询分页数据
	 * @param viewShouchong 查询条件
	 * @param viewShouchong.page 分页对象
	 * @return
	 */
	@Override
	public Page<ViewShouchong> findPage(ViewShouchong viewShouchong) {
		return super.findPage(viewShouchong);
	}
	
	/**
	 * 查询列表数据
	 * @param viewShouchong
	 * @return
	 */
	@Override
	public List<ViewShouchong> findList(ViewShouchong viewShouchong) {
		return super.findList(viewShouchong);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param viewShouchong
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ViewShouchong viewShouchong) {
		super.save(viewShouchong);
	}
	
	/**
	 * 更新状态
	 * @param viewShouchong
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ViewShouchong viewShouchong) {
		super.updateStatus(viewShouchong);
	}
	
	/**
	 * 删除数据
	 * @param viewShouchong
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ViewShouchong viewShouchong) {
		super.delete(viewShouchong);
	}

	public Double getTotalMoney(Map<String, String> parame) {
		return viewShouchongDao.getTotalMoney(parame);
	}
	
	@Autowired
	private ViewShouchongDao viewShouchongDao ;

	public Long getTotalRenshu(Map<String, String> parame) {
		if (!StringUtils.isBlank(parame.get("name1"))) {
			parame.put("name1", "%" +parame.get("name1") +"%");
		}
		return viewShouchongDao.getTotalRenshu(parame);
	}
	
	
}