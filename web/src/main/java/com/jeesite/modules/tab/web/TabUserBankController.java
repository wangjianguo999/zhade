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
import com.jeesite.modules.tab.entity.TabUserBank;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.service.TabUserBankService;
import com.jeesite.modules.tab.service.TabUserDataService;

/**
 * tab_user_bankController
 * @author 3
 * @version 2022-04-09
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabUserBank")
public class TabUserBankController extends BaseController {

	@Autowired
	private TabUserBankService tabUserBankService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabUserBank get(String rowid, boolean isNewRecord) {
		return tabUserBankService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabUserBank:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabUserBank tabUserBank, Model model) {
		model.addAttribute("tabUserBank", tabUserBank);
		return "modules/tab/tabUserBankList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabUserBank:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabUserBank> listData(TabUserBank tabUserBank, HttpServletRequest request, HttpServletResponse response) {
		

		
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
			tabUserBank.setShangjilink(acc);
		}
		tabUserBank.setPage(new Page<>(request, response));
		Page<TabUserBank> page = tabUserBankService.findPage(tabUserBank);
		return page;
	}
	@Autowired
	private UserService  userService ;

	@Autowired
	private TabUserDataService tabUserDataService;

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabUserBank:view")
	@RequestMapping(value = "form")
	public String form(TabUserBank tabUserBank, Model model) {
		model.addAttribute("tabUserBank", tabUserBank);
		return "modules/tab/tabUserBankForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabUserBank:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabUserBank tabUserBank) {
		tabUserBankService.save(tabUserBank);
		return renderResult(Global.TRUE, text("保存tab_user_bank成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabUserBank:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabUserBank tabUserBank) {
		tabUserBankService.delete(tabUserBank);
		return renderResult(Global.TRUE, text("删除tab_user_bank成功！"));
	}
	
}