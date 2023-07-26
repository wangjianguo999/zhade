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
import com.jeesite.modules.tab.entity.TabYaoqingJiangliHuodongLog;
import com.jeesite.modules.tab.service.TabUserDataService;
import com.jeesite.modules.tab.service.TabYaoqingJiangliHuodongLogService;

/**
 * tab_yaoqing_jiangli_huodong_logController
 * @author 3
 * @version 2022-04-21
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabYaoqingJiangliHuodongLog")
public class TabYaoqingJiangliHuodongLogController extends BaseController {

	@Autowired
	private TabYaoqingJiangliHuodongLogService tabYaoqingJiangliHuodongLogService;
	
	@Autowired
	private UserService  userService;
	@Autowired
	private TabUserDataService  tabUserDataService;
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabYaoqingJiangliHuodongLog get(String rowid, boolean isNewRecord) {
		return tabYaoqingJiangliHuodongLogService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliHuodongLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabYaoqingJiangliHuodongLog tabYaoqingJiangliHuodongLog, Model model) {
		model.addAttribute("tabYaoqingJiangliHuodongLog", tabYaoqingJiangliHuodongLog);
		return "modules/tab/tabYaoqingJiangliHuodongLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliHuodongLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabYaoqingJiangliHuodongLog> listData(TabYaoqingJiangliHuodongLog tabYaoqingJiangliHuodongLog, HttpServletRequest request, HttpServletResponse response) {


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
			tabYaoqingJiangliHuodongLog.setShangjilink(acc);
		}
		tabYaoqingJiangliHuodongLog.setPage(new Page<>(request, response));
		Page<TabYaoqingJiangliHuodongLog> page = tabYaoqingJiangliHuodongLogService.findPage(tabYaoqingJiangliHuodongLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliHuodongLog:view")
	@RequestMapping(value = "form")
	public String form(TabYaoqingJiangliHuodongLog tabYaoqingJiangliHuodongLog, Model model) {
		model.addAttribute("tabYaoqingJiangliHuodongLog", tabYaoqingJiangliHuodongLog);
		return "modules/tab/tabYaoqingJiangliHuodongLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliHuodongLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabYaoqingJiangliHuodongLog tabYaoqingJiangliHuodongLog) {
		tabYaoqingJiangliHuodongLogService.save(tabYaoqingJiangliHuodongLog);
		return renderResult(Global.TRUE, text("保存tab_yaoqing_jiangli_huodong_log成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliHuodongLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabYaoqingJiangliHuodongLog tabYaoqingJiangliHuodongLog) {
		tabYaoqingJiangliHuodongLogService.delete(tabYaoqingJiangliHuodongLog);
		return renderResult(Global.TRUE, text("删除tab_yaoqing_jiangli_huodong_log成功！"));
	}
	
}