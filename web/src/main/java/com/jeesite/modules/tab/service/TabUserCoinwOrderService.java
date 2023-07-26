package com.jeesite.modules.tab.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.tab.dao.TabUserCoinwDao;
import com.jeesite.modules.tab.dao.TabUserCoinwOrderDao;
import com.jeesite.modules.tab.entity.TabUserCoinw;
import com.jeesite.modules.tab.entity.TabUserCoinwOrder;
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
public class TabUserCoinwOrderService extends CrudService<TabUserCoinwOrderDao, TabUserCoinwOrder> {

	@Autowired
	private TabUserCoinwOrderDao tabUserCoinwOrderDao;

	public TabUserCoinwOrder getUserCoinwOrder(TabUserCoinwOrder tabUserCoinwOrder) {
		return tabUserCoinwOrderDao.get(tabUserCoinwOrder);
	}

	@Transactional
	public void saveUserCoinwOrder(TabUserCoinwOrder tabUserCoinwOrder) {
		super.save(tabUserCoinwOrder);
	}

	public Map<String,String> getTongji(String userid) {
		return tabUserCoinwOrderDao.getTongji(userid);
	}

	public Long getProfitcount(String userid) {
		return tabUserCoinwOrderDao.getProfitcount(userid);
	}
}