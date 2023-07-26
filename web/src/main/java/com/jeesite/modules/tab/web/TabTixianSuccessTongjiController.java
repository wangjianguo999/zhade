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
import com.jeesite.modules.tab.entity.TabTixianSuccessTongji;
import com.jeesite.modules.tab.service.TabTixianSuccessTongjiService;

/**
 * tab_tixian_success_tongjiController
 * @author 32
 * @version 2022-04-04
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabTixianSuccessTongji")
public class TabTixianSuccessTongjiController extends BaseController {

	@Autowired
	private TabTixianSuccessTongjiService tabTixianSuccessTongjiService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabTixianSuccessTongji get(String rowidva, boolean isNewRecord) {
		return tabTixianSuccessTongjiService.get(rowidva, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabTixianSuccessTongji:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabTixianSuccessTongji tabTixianSuccessTongji, Model model) {
		model.addAttribute("tabTixianSuccessTongji", tabTixianSuccessTongji);
		return "modules/tab/tabTixianSuccessTongjiList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabTixianSuccessTongji:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabTixianSuccessTongji> listData(TabTixianSuccessTongji tabTixianSuccessTongji, HttpServletRequest request, HttpServletResponse response) {
		tabTixianSuccessTongji.setPage(new Page<>(request, response));
		Page<TabTixianSuccessTongji> page = tabTixianSuccessTongjiService.findPage(tabTixianSuccessTongji);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabTixianSuccessTongji:view")
	@RequestMapping(value = "form")
	public String form(TabTixianSuccessTongji tabTixianSuccessTongji, Model model) {
		model.addAttribute("tabTixianSuccessTongji", tabTixianSuccessTongji);
		return "modules/tab/tabTixianSuccessTongjiForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabTixianSuccessTongji:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabTixianSuccessTongji tabTixianSuccessTongji) {
		tabTixianSuccessTongjiService.save(tabTixianSuccessTongji);
		return renderResult(Global.TRUE, text("保存tab_tixian_success_tongji成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabTixianSuccessTongji:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabTixianSuccessTongji tabTixianSuccessTongji) {
		tabTixianSuccessTongjiService.delete(tabTixianSuccessTongji);
		return renderResult(Global.TRUE, text("删除tab_tixian_success_tongji成功！"));
	}
	
}