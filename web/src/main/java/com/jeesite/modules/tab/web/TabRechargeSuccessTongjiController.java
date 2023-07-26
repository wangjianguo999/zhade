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
import com.jeesite.modules.tab.entity.TabRechargeSuccessTongji;
import com.jeesite.modules.tab.service.TabRechargeSuccessTongjiService;

/**
 * tab_recharge_success_tongjiController
 * @author 1
 * @version 2022-04-04
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabRechargeSuccessTongji")
public class TabRechargeSuccessTongjiController extends BaseController {

	@Autowired
	private TabRechargeSuccessTongjiService tabRechargeSuccessTongjiService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabRechargeSuccessTongji get(String rowidva, boolean isNewRecord) {
		return tabRechargeSuccessTongjiService.get(rowidva, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabRechargeSuccessTongji:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabRechargeSuccessTongji tabRechargeSuccessTongji, Model model) {
		model.addAttribute("tabRechargeSuccessTongji", tabRechargeSuccessTongji);
		return "modules/tab/tabRechargeSuccessTongjiList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabRechargeSuccessTongji:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabRechargeSuccessTongji> listData(TabRechargeSuccessTongji tabRechargeSuccessTongji, HttpServletRequest request, HttpServletResponse response) {
		tabRechargeSuccessTongji.setPage(new Page<>(request, response));
		Page<TabRechargeSuccessTongji> page = tabRechargeSuccessTongjiService.findPage(tabRechargeSuccessTongji);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabRechargeSuccessTongji:view")
	@RequestMapping(value = "form")
	public String form(TabRechargeSuccessTongji tabRechargeSuccessTongji, Model model) {
		model.addAttribute("tabRechargeSuccessTongji", tabRechargeSuccessTongji);
		return "modules/tab/tabRechargeSuccessTongjiForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabRechargeSuccessTongji:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabRechargeSuccessTongji tabRechargeSuccessTongji) {
		tabRechargeSuccessTongjiService.save(tabRechargeSuccessTongji);
		return renderResult(Global.TRUE, text("保存tab_recharge_success_tongji成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabRechargeSuccessTongji:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabRechargeSuccessTongji tabRechargeSuccessTongji) {
		tabRechargeSuccessTongjiService.delete(tabRechargeSuccessTongji);
		return renderResult(Global.TRUE, text("删除tab_recharge_success_tongji成功！"));
	}
	
}