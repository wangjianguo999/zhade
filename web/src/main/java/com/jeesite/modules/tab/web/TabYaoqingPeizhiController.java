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
import com.jeesite.modules.tab.entity.TabYaoqingPeizhi;
import com.jeesite.modules.tab.service.TabYaoqingPeizhiService;

/**
 * tab_yaoqing_peizhiController
 * @author 1
 * @version 2022-04-23
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabYaoqingPeizhi")
public class TabYaoqingPeizhiController extends BaseController {

	@Autowired
	private TabYaoqingPeizhiService tabYaoqingPeizhiService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabYaoqingPeizhi get(String rowid, boolean isNewRecord) {
		return tabYaoqingPeizhiService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabYaoqingPeizhi:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabYaoqingPeizhi tabYaoqingPeizhi, Model model) {
		model.addAttribute("tabYaoqingPeizhi", tabYaoqingPeizhi);
		return "modules/tab/tabYaoqingPeizhiList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabYaoqingPeizhi:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabYaoqingPeizhi> listData(TabYaoqingPeizhi tabYaoqingPeizhi, HttpServletRequest request, HttpServletResponse response) {
		tabYaoqingPeizhi.setPage(new Page<>(request, response));
		Page<TabYaoqingPeizhi> page = tabYaoqingPeizhiService.findPage(tabYaoqingPeizhi);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabYaoqingPeizhi:view")
	@RequestMapping(value = "form")
	public String form(TabYaoqingPeizhi tabYaoqingPeizhi, Model model) {
		model.addAttribute("tabYaoqingPeizhi", tabYaoqingPeizhi);
		return "modules/tab/tabYaoqingPeizhiForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabYaoqingPeizhi:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabYaoqingPeizhi tabYaoqingPeizhi) {
		tabYaoqingPeizhiService.save(tabYaoqingPeizhi);
		return renderResult(Global.TRUE, text("保存tab_yaoqing_peizhi成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabYaoqingPeizhi:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabYaoqingPeizhi tabYaoqingPeizhi) {
		tabYaoqingPeizhiService.delete(tabYaoqingPeizhi);
		return renderResult(Global.TRUE, text("删除tab_yaoqing_peizhi成功！"));
	}
	
}