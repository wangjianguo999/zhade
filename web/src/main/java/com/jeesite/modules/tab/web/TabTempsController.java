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
import com.jeesite.modules.tab.entity.TabTemps;
import com.jeesite.modules.tab.service.TabTempsService;

/**
 * tab_tempsController
 * @author 32
 * @version 2022-04-21
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabTemps")
public class TabTempsController extends BaseController {

	@Autowired
	private TabTempsService tabTempsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabTemps get(String rowid, boolean isNewRecord) {
		return tabTempsService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabTemps:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabTemps tabTemps, Model model) {
		model.addAttribute("tabTemps", tabTemps);
		return "modules/tab/tabTempsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabTemps:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabTemps> listData(TabTemps tabTemps, HttpServletRequest request, HttpServletResponse response) {
		tabTemps.setPage(new Page<>(request, response));
		Page<TabTemps> page = tabTempsService.findPage(tabTemps);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabTemps:view")
	@RequestMapping(value = "form")
	public String form(TabTemps tabTemps, Model model) {
		model.addAttribute("tabTemps", tabTemps);
		return "modules/tab/tabTempsForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabTemps:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabTemps tabTemps) {
		tabTempsService.save(tabTemps);
		return renderResult(Global.TRUE, text("保存tab_temps成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabTemps:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabTemps tabTemps) {
		tabTempsService.delete(tabTemps);
		return renderResult(Global.TRUE, text("删除tab_temps成功！"));
	}
	
}