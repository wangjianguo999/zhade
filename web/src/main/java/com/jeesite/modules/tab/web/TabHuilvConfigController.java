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
import com.jeesite.modules.tab.entity.TabHuilvConfig;
import com.jeesite.modules.tab.service.TabHuilvConfigService;

/**
 * tab_huilv_configController
 * @author 11
 * @version 2021-12-24
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabHuilvConfig")
public class TabHuilvConfigController extends BaseController {

	@Autowired
	private TabHuilvConfigService tabHuilvConfigService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabHuilvConfig get(String rowid, boolean isNewRecord) {
		return tabHuilvConfigService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabHuilvConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabHuilvConfig tabHuilvConfig, Model model) {
		model.addAttribute("tabHuilvConfig", tabHuilvConfig);
		return "modules/tab/tabHuilvConfigList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabHuilvConfig:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabHuilvConfig> listData(TabHuilvConfig tabHuilvConfig, HttpServletRequest request, HttpServletResponse response) {
		tabHuilvConfig.setPage(new Page<>(request, response));
		Page<TabHuilvConfig> page = tabHuilvConfigService.findPage(tabHuilvConfig);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabHuilvConfig:view")
	@RequestMapping(value = "form")
	public String form(TabHuilvConfig tabHuilvConfig, Model model) {
		model.addAttribute("tabHuilvConfig", tabHuilvConfig);
		return "modules/tab/tabHuilvConfigForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabHuilvConfig:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabHuilvConfig tabHuilvConfig) {
		tabHuilvConfigService.save(tabHuilvConfig);
		return renderResult(Global.TRUE, text("保存tab_huilv_config成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabHuilvConfig:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabHuilvConfig tabHuilvConfig) {
		tabHuilvConfigService.delete(tabHuilvConfig);
		return renderResult(Global.TRUE, text("删除tab_huilv_config成功！"));
	}

	public static void main(String[] args) {
	}
	
}