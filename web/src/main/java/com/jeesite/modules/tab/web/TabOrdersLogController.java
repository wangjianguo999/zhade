package com.jeesite.modules.tab.web;

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
import com.jeesite.modules.tab.entity.TabOrdersLog;
import com.jeesite.modules.tab.service.TabOrdersLogService;

/**
 * tab_orders_logController
 * @author 3
 * @version 2022-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabOrdersLog")
public class TabOrdersLogController extends BaseController {

	@Autowired
	private TabOrdersLogService tabOrdersLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabOrdersLog get(String rowid, boolean isNewRecord) {
		return tabOrdersLogService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabOrdersLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabOrdersLog tabOrdersLog, Model model) {
		model.addAttribute("tabOrdersLog", tabOrdersLog);
		return "modules/tab/tabOrdersLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabOrdersLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabOrdersLog> listData(TabOrdersLog tabOrdersLog, HttpServletRequest request, HttpServletResponse response) {
		tabOrdersLog.setPage(new Page<>(request, response));
		Page<TabOrdersLog> page = tabOrdersLogService.findPage(tabOrdersLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabOrdersLog:view")
	@RequestMapping(value = "form")
	public String form(TabOrdersLog tabOrdersLog, Model model) {
		model.addAttribute("tabOrdersLog", tabOrdersLog);
		return "modules/tab/tabOrdersLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabOrdersLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabOrdersLog tabOrdersLog) {
		tabOrdersLogService.save(tabOrdersLog);
		return renderResult(Global.TRUE, text("保存tab_orders_log成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabOrdersLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabOrdersLog tabOrdersLog) {
		tabOrdersLogService.delete(tabOrdersLog);
		return renderResult(Global.TRUE, text("删除tab_orders_log成功！"));
	}
	
}