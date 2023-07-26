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
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.entity.TabYaoqingJiangliLog;
import com.jeesite.modules.tab.service.TabUserDataService;
import com.jeesite.modules.tab.service.TabYaoqingJiangliLogService;

/**
 * tab_yaoqing_jiangli_logController
 * @author 3
 * @version 2022-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabYaoqingJiangliLog")
public class TabYaoqingJiangliLogController extends BaseController {

	@Autowired
	private TabYaoqingJiangliLogService tabYaoqingJiangliLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabYaoqingJiangliLog get(String rowid, boolean isNewRecord) {
		return tabYaoqingJiangliLogService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabYaoqingJiangliLog tabYaoqingJiangliLog, Model model) {
		model.addAttribute("tabYaoqingJiangliLog", tabYaoqingJiangliLog);
		return "modules/tab/tabYaoqingJiangliLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabYaoqingJiangliLog> listData(TabYaoqingJiangliLog tabYaoqingJiangliLog, HttpServletRequest request, HttpServletResponse response) {
		
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
			tabYaoqingJiangliLog.setShangjilink(acc);
		}
		
		tabYaoqingJiangliLog.setPage(new Page<>(request, response));
		Page<TabYaoqingJiangliLog> page = tabYaoqingJiangliLogService.findPage(tabYaoqingJiangliLog);
		return page;
	}
	@Autowired
private UserService userService; 
	@Autowired
	private TabUserDataService tabUserDataService;
	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliLog:view")
	@RequestMapping(value = "form")
	public String form(TabYaoqingJiangliLog tabYaoqingJiangliLog, Model model) {
		model.addAttribute("tabYaoqingJiangliLog", tabYaoqingJiangliLog);
		return "modules/tab/tabYaoqingJiangliLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabYaoqingJiangliLog tabYaoqingJiangliLog) {
		tabYaoqingJiangliLogService.save(tabYaoqingJiangliLog);
		return renderResult(Global.TRUE, text("保存tab_yaoqing_jiangli_log成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabYaoqingJiangliLog tabYaoqingJiangliLog) {
		tabYaoqingJiangliLogService.delete(tabYaoqingJiangliLog);
		return renderResult(Global.TRUE, text("删除tab_yaoqing_jiangli_log成功！"));
	}
	
}