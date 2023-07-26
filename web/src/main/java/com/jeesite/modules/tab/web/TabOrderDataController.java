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
import com.jeesite.modules.tab.entity.TabOrderData;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.service.TabOrderDataService;
import com.jeesite.modules.tab.service.TabUserDataService;

/**
 * tab_order_dataController
 * @author 32
 * @version 2021-12-18
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabOrderData")
public class TabOrderDataController extends BaseController {

	@Autowired
	private TabOrderDataService tabOrderDataService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabOrderData get(String rowid, boolean isNewRecord) {
		return tabOrderDataService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabOrderData:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabOrderData tabOrderData, Model model) {
		
		List<User> users   =  userService.findList(new User());
		model.addAttribute("users", users);
		
		model.addAttribute("tabOrderData", tabOrderData);
		return "modules/tab/tabOrderDataList";
	}
	
	/**
	 * 查询列表数据
	 */
	@Autowired
	private TabUserDataService tabUserDataService ;
	@Autowired
	private UserService userService;
	@RequiresPermissions("tab:tabOrderData:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabOrderData> listData(TabOrderData tabOrderData, HttpServletRequest request, HttpServletResponse response) {
		

		String  id = 	UserUtils.getLoginInfo().getId();
		if (tabOrderData.getCreatetime_lte() != null) {
			tabOrderData.setCreatetime_lte(DateUtils.getOfDayLast(tabOrderData.getCreatetime_lte()));
			
 		}

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
			tabOrderData.setShangjilink(acc);
		}
		tabOrderData.setPage(new Page<>(request, response));
		Page<TabOrderData> page = tabOrderDataService.findPage(tabOrderData);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabOrderData:view")
	@RequestMapping(value = "form")
	public String form(TabOrderData tabOrderData, Model model) {
		model.addAttribute("tabOrderData", tabOrderData);
		return "modules/tab/tabOrderDataForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabOrderData:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabOrderData tabOrderData) {
		tabOrderDataService.save(tabOrderData);
		return renderResult(Global.TRUE, text("保存tab_order_data成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabOrderData:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabOrderData tabOrderData) {
		tabOrderDataService.delete(tabOrderData);
		return renderResult(Global.TRUE, text("删除tab_order_data成功！"));
	}

	public static void main(String[] args) {
	}
	
}