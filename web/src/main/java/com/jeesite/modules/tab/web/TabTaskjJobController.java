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
import com.jeesite.modules.tab.entity.TabTaskjJob;
import com.jeesite.modules.tab.service.TabTaskjJobService;

/**
 * tab_taskj_jobController
 * @author 1
 * @version 2022-03-21
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabTaskjJob")
public class TabTaskjJobController extends BaseController {

	@Autowired
	private TabTaskjJobService tabTaskjJobService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabTaskjJob get(String rowid, boolean isNewRecord) {
		return tabTaskjJobService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabTaskjJob:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabTaskjJob tabTaskjJob, Model model) {
		model.addAttribute("tabTaskjJob", tabTaskjJob);
		return "modules/tab/tabTaskjJobList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabTaskjJob:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabTaskjJob> listData(TabTaskjJob tabTaskjJob, HttpServletRequest request, HttpServletResponse response) {
		tabTaskjJob.setPage(new Page<>(request, response));
		Page<TabTaskjJob> page = tabTaskjJobService.findPage(tabTaskjJob);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabTaskjJob:view")
	@RequestMapping(value = "form")
	public String form(TabTaskjJob tabTaskjJob, Model model) {
		model.addAttribute("tabTaskjJob", tabTaskjJob);
		return "modules/tab/tabTaskjJobForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabTaskjJob:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabTaskjJob tabTaskjJob) {
		tabTaskjJobService.save(tabTaskjJob);
		return renderResult(Global.TRUE, text("保存tab_taskj_job成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabTaskjJob:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabTaskjJob tabTaskjJob) {
		tabTaskjJobService.delete(tabTaskjJob);
		return renderResult(Global.TRUE, text("删除tab_taskj_job成功！"));
	}
	
}