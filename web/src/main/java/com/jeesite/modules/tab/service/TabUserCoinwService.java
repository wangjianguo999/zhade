package com.jeesite.modules.tab.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.dao.TabUserCoinwDao;
import com.jeesite.modules.tab.dao.TabUserDataDao;
import com.jeesite.modules.tab.entity.TabUserCoinw;
import com.jeesite.modules.tab.entity.TabUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * tab_user_dataService
 * @author 1
 * @version 2021-12-14
 */
@Service
@Transactional(readOnly=true)
public class TabUserCoinwService extends CrudService<TabUserCoinwDao, TabUserCoinw> {

	@Autowired
	private TabUserCoinwDao tabUserCoinwDao;

	@Override
	public TabUserCoinw get(TabUserCoinw entity) {
		return super.get(entity);
	}

	public TabUserCoinw getUserCoinw(TabUserCoinw tabUserCoinw) {
		return tabUserCoinwDao.get(tabUserCoinw);
	}

	@Override
	public long findCount(TabUserCoinw entity) {
		return super.findCount(entity);
	}

	@Override
	public List<TabUserCoinw> findList(TabUserCoinw entity) {
		return super.findList(entity);
	}

	@Override
	public Page<TabUserCoinw> findPage(TabUserCoinw entity) {
		return super.findPage(entity);
	}

	@Transactional
	public void saveUserCoinw(TabUserCoinw entity) {
		super.save(entity);
	}

	@Override
	public void deleteByIds(TabUserCoinw entity) {
		super.deleteByIds(entity);
	}

	public List<TabUserCoinw> findListData(Map<String,Object> map) {
		return tabUserCoinwDao.findListData(map);
	}

	public Long findListDataCount(Map<String,Object> map) {
		return tabUserCoinwDao.findListDataCount(map);
	}
}