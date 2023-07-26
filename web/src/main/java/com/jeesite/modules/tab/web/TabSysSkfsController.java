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
import com.jeesite.modules.tab.entity.TabSysSkfs;
import com.jeesite.modules.tab.service.TabSysSkfsService;

/**
 * tab_sys_skfsController
 * @author 1
 * @version 2021-12-23
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabSysSkfs")
public class TabSysSkfsController extends BaseController {

	@Autowired
	private TabSysSkfsService tabSysSkfsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabSysSkfs get(String rowid, boolean isNewRecord) {
		return tabSysSkfsService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabSysSkfs:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabSysSkfs tabSysSkfs, Model model) {
		model.addAttribute("tabSysSkfs", tabSysSkfs);
		return "modules/tab/tabSysSkfsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabSysSkfs:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabSysSkfs> listData(TabSysSkfs tabSysSkfs, HttpServletRequest request, HttpServletResponse response) {
		tabSysSkfs.setPage(new Page<>(request, response));
		Page<TabSysSkfs> page = tabSysSkfsService.findPage(tabSysSkfs);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabSysSkfs:view")
	@RequestMapping(value = "form")
	public String form(TabSysSkfs tabSysSkfs, Model model) {
		model.addAttribute("tabSysSkfs", tabSysSkfs);
		return "modules/tab/tabSysSkfsForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabSysSkfs:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabSysSkfs tabSysSkfs) {
		tabSysSkfsService.save(tabSysSkfs);
		return renderResult(Global.TRUE, text("保存tab_sys_skfs成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabSysSkfs:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabSysSkfs tabSysSkfs) {
		tabSysSkfsService.delete(tabSysSkfs);
		return renderResult(Global.TRUE, text("删除tab_sys_skfs成功！"));
	}
	
}