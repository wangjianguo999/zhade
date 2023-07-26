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
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tab.entity.TabFacebook;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.service.TabFacebookService;
import com.jeesite.modules.tab.service.TabUserDataService;

/**
 * tab_facebookController
 * @author 32
 * @version 2021-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabFacebook")
public class TabFacebookController extends BaseController {

	@Autowired
	private TabFacebookService tabFacebookService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabFacebook get(String rowid, boolean isNewRecord) {
		return tabFacebookService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabFacebook:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabFacebook tabFacebook, Model model) {
		model.addAttribute("tabFacebook", tabFacebook);
		return "modules/tab/tabFacebookList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabFacebook:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabFacebook> listData(TabFacebook tabFacebook, HttpServletRequest request, HttpServletResponse response) {
		
		
		

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
			tabFacebook.setShangjilink(acc);
		}
		
		
		tabFacebook.setPage(new Page<>(request, response));
		Page<TabFacebook> page = tabFacebookService.findPage(tabFacebook);
		return page;
	}
	@Autowired
	private UserService  userService;
	@Autowired
	private TabUserDataService  tabUserDataService;
	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabFacebook:view")
	@RequestMapping(value = "form")
	public String form(TabFacebook tabFacebook, Model model) {
		model.addAttribute("tabFacebook", tabFacebook);
		return "modules/tab/tabFacebookForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabFacebook:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabFacebook tabFacebook) {
		tabFacebookService.save(tabFacebook);
		return renderResult(Global.TRUE, text("保存tab_facebook成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabFacebook:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabFacebook tabFacebook) {
		tabFacebookService.delete(tabFacebook);
		return renderResult(Global.TRUE, text("删除tab_facebook成功！"));
	}
	
}