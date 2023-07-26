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
import com.jeesite.modules.tab.entity.TabFenxiaoConfig;
import com.jeesite.modules.tab.service.TabFenxiaoConfigService;

/**
 * tab_fenxiao_configController
 * @author 32
 * @version 2022-03-18
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabFenxiaoConfig")
public class TabFenxiaoConfigController extends BaseController {

	@Autowired
	private TabFenxiaoConfigService tabFenxiaoConfigService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabFenxiaoConfig get(String rowid, boolean isNewRecord) {
		return tabFenxiaoConfigService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabFenxiaoConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabFenxiaoConfig tabFenxiaoConfig, Model model) {
		model.addAttribute("tabFenxiaoConfig", tabFenxiaoConfig);
		return "modules/tab/tabFenxiaoConfigList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabFenxiaoConfig:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabFenxiaoConfig> listData(TabFenxiaoConfig tabFenxiaoConfig, HttpServletRequest request, HttpServletResponse response) {
		tabFenxiaoConfig.setPage(new Page<>(request, response));
		Page<TabFenxiaoConfig> page = tabFenxiaoConfigService.findPage(tabFenxiaoConfig);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabFenxiaoConfig:view")
	@RequestMapping(value = "form")
	public String form(TabFenxiaoConfig tabFenxiaoConfig, Model model) {
		model.addAttribute("tabFenxiaoConfig", tabFenxiaoConfig);
		return "modules/tab/tabFenxiaoConfigForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabFenxiaoConfig:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabFenxiaoConfig tabFenxiaoConfig) {
		tabFenxiaoConfigService.save(tabFenxiaoConfig);
		return renderResult(Global.TRUE, text("保存tab_fenxiao_config成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabFenxiaoConfig:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabFenxiaoConfig tabFenxiaoConfig) {
		tabFenxiaoConfigService.delete(tabFenxiaoConfig);
		return renderResult(Global.TRUE, text("删除tab_fenxiao_config成功！"));
	}
	
}