package com.jeesite.modules.tab.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tab.entity.TabLicaiData;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.service.TabLicaiDataService;
import com.jeesite.modules.tab.service.TabUserDataService;

/**
 * tab_licai_dataController
 * @author 1
 * @version 2021-12-17
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabLicaiData")
public class TabLicaiDataController extends BaseController {

	@Autowired
	private TabLicaiDataService tabLicaiDataService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabLicaiData get(String rowid, boolean isNewRecord) {
		return tabLicaiDataService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabLicaiData:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabLicaiData tabLicaiData, Model model) {
		model.addAttribute("tabLicaiData", tabLicaiData);
		
		List<User> users   =  userService.findList(new User());
		model.addAttribute("users", users);
		
		return "modules/tab/tabLicaiDataList";
	}
	
	
	@Autowired
	private UserService userService;
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabLicaiData:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabLicaiData> listData(TabLicaiData tabLicaiData, HttpServletRequest request, HttpServletResponse response) {
		
		

		if (tabLicaiData.getCreatetime_lte() != null) {
			tabLicaiData.setCreatetime_lte(DateUtils.getOfDayLast(tabLicaiData.getCreatetime_lte()));

		}
		String  id = 	UserUtils.getLoginInfo().getId();
		 
		User user   =  userService.get(id);
		
		String  acc= user.getLoginCode();
if (!acc.equals("admin")) {
			
			TabUserData userData  = new TabUserData();
			userData.setSysuserid(acc);
			
			List<TabUserData> tabUserDatas   =   tabUserDataService.findList(userData);
			
			if (tabUserDatas.size() >  0  ) {
				acc  =  tabUserDatas.get(0).getRowid();  
			}
			
			
		}
		
		if (!acc.equals("admin")) {
			tabLicaiData.setShangjilink(acc);
		}
		
		
		tabLicaiData.setPage(new Page<>(request, response));
		Page<TabLicaiData> page = tabLicaiDataService.findPage(tabLicaiData);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabLicaiData:view")
	@RequestMapping(value = "form")
	public String form(TabLicaiData tabLicaiData, Model model) {
		model.addAttribute("tabLicaiData", tabLicaiData);
		return "modules/tab/tabLicaiDataForm";
	}
	
	@Autowired
	private TabUserDataService tabUserDataService;

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabLicaiData:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabLicaiData tabLicaiData) {
		tabLicaiDataService.save(tabLicaiData);
		return renderResult(Global.TRUE, text("保存tab_licai_data成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabLicaiData:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabLicaiData tabLicaiData) {
		tabLicaiDataService.delete(tabLicaiData);
		return renderResult(Global.TRUE, text("删除tab_licai_data成功！"));
	}
	
}