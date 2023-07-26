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
import com.jeesite.modules.tab.entity.TabVipConfig;
import com.jeesite.modules.tab.service.TabVipConfigService;

/**
 * tab_vip_configController
 * @author 1
 * @version 2021-12-11
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabVipConfig")
public class TabVipConfigController extends BaseController {

	@Autowired
	private TabVipConfigService tabVipConfigService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabVipConfig get(String rowid, boolean isNewRecord) {
		return tabVipConfigService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabVipConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabVipConfig tabVipConfig, Model model) {
		model.addAttribute("tabVipConfig", tabVipConfig);
		return "modules/tab/tabVipConfigList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabVipConfig:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabVipConfig> listData(TabVipConfig tabVipConfig, HttpServletRequest request, HttpServletResponse response) {
		tabVipConfig.setPage(new Page<>(request, response));
		Page<TabVipConfig> page = tabVipConfigService.findPage(tabVipConfig);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabVipConfig:view")
	@RequestMapping(value = "form")
	public String form(TabVipConfig tabVipConfig, Model model) {
		model.addAttribute("tabVipConfig", tabVipConfig);
		return "modules/tab/tabVipConfigForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabVipConfig:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabVipConfig tabVipConfig) {
		tabVipConfigService.save(tabVipConfig);
		return renderResult(Global.TRUE, text("保存tab_vip_config成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabVipConfig:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabVipConfig tabVipConfig) {
		tabVipConfigService.delete(tabVipConfig);
		return renderResult(Global.TRUE, text("删除tab_vip_config成功！"));
	}
	
}