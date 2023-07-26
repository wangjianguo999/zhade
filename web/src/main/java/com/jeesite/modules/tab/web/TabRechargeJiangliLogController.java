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
import com.jeesite.modules.tab.entity.TabRechargeJiangliLog;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.service.TabRechargeJiangliLogService;
import com.jeesite.modules.tab.service.TabUserDataService;

/**
 * tab_recharge_jiangli_logController
 * @author 4
 * @version 2022-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabRechargeJiangliLog")
public class TabRechargeJiangliLogController extends BaseController {

	@Autowired
	private TabRechargeJiangliLogService tabRechargeJiangliLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabRechargeJiangliLog get(String rowid, boolean isNewRecord) {
		return tabRechargeJiangliLogService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabRechargeJiangliLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabRechargeJiangliLog tabRechargeJiangliLog, Model model) {
		model.addAttribute("tabRechargeJiangliLog", tabRechargeJiangliLog);
		return "modules/tab/tabRechargeJiangliLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabRechargeJiangliLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabRechargeJiangliLog> listData(TabRechargeJiangliLog tabRechargeJiangliLog, HttpServletRequest request, HttpServletResponse response) {
		
		
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
			tabRechargeJiangliLog.setShangjilink(acc);
		}
		
		tabRechargeJiangliLog.setPage(new Page<>(request, response));
		Page<TabRechargeJiangliLog> page = tabRechargeJiangliLogService.findPage(tabRechargeJiangliLog);
		return page;
	}

	@Autowired
	private UserService  userService;
	@Autowired
	private TabUserDataService  tabUserDataService;
	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabRechargeJiangliLog:view")
	@RequestMapping(value = "form")
	public String form(TabRechargeJiangliLog tabRechargeJiangliLog, Model model) {
		model.addAttribute("tabRechargeJiangliLog", tabRechargeJiangliLog);
		return "modules/tab/tabRechargeJiangliLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabRechargeJiangliLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabRechargeJiangliLog tabRechargeJiangliLog) {
		tabRechargeJiangliLogService.save(tabRechargeJiangliLog);
		return renderResult(Global.TRUE, text("保存tab_recharge_jiangli_log成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabRechargeJiangliLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabRechargeJiangliLog tabRechargeJiangliLog) {
		tabRechargeJiangliLogService.delete(tabRechargeJiangliLog);
		return renderResult(Global.TRUE, text("删除tab_recharge_jiangli_log成功！"));
	}
	
}