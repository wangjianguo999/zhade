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
import com.jeesite.modules.tab.entity.TabShouruLog;
import com.jeesite.modules.tab.service.TabShouruLogService;

/**
 * tab_shouru_logController
 * @author 1
 * @version 2021-12-18
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabShouruLog")
public class TabShouruLogController extends BaseController {

	@Autowired
	private TabShouruLogService tabShouruLogService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabShouruLog get(String rowid, boolean isNewRecord) {
		return tabShouruLogService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabShouruLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabShouruLog tabShouruLog, Model model) {
		model.addAttribute("tabShouruLog", tabShouruLog);
		return "modules/tab/tabShouruLogList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabShouruLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabShouruLog> listData(TabShouruLog tabShouruLog, HttpServletRequest request, HttpServletResponse response) {
		tabShouruLog.setPage(new Page<>(request, response));
		Page<TabShouruLog> page = tabShouruLogService.findPage(tabShouruLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabShouruLog:view")
	@RequestMapping(value = "form")
	public String form(TabShouruLog tabShouruLog, Model model) {
		model.addAttribute("tabShouruLog", tabShouruLog);
		return "modules/tab/tabShouruLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabShouruLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabShouruLog tabShouruLog) {
		tabShouruLogService.save(tabShouruLog);
		return renderResult(Global.TRUE, text("保存tab_shouru_log成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabShouruLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabShouruLog tabShouruLog) {
		tabShouruLogService.delete(tabShouruLog);
		return renderResult(Global.TRUE, text("删除tab_shouru_log成功！"));
	}
	
}