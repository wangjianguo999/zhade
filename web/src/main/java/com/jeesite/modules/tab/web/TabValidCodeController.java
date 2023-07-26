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
import com.jeesite.modules.tab.entity.TabValidCode;
import com.jeesite.modules.tab.service.TabValidCodeService;

/**
 * tab_valid_codeController
 * @author 3
 * @version 2021-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabValidCode")
public class TabValidCodeController extends BaseController {

	@Autowired
	private TabValidCodeService tabValidCodeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabValidCode get(String rowid, boolean isNewRecord) {
		return tabValidCodeService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabValidCode:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabValidCode tabValidCode, Model model) {
		model.addAttribute("tabValidCode", tabValidCode);
		return "modules/tab/tabValidCodeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabValidCode:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabValidCode> listData(TabValidCode tabValidCode, HttpServletRequest request, HttpServletResponse response) {
		tabValidCode.setPage(new Page<>(request, response));
		Page<TabValidCode> page = tabValidCodeService.findPage(tabValidCode);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabValidCode:view")
	@RequestMapping(value = "form")
	public String form(TabValidCode tabValidCode, Model model) {
		model.addAttribute("tabValidCode", tabValidCode);
		return "modules/tab/tabValidCodeForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabValidCode:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabValidCode tabValidCode) {
		tabValidCodeService.save(tabValidCode);
		return renderResult(Global.TRUE, text("保存tab_valid_code成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabValidCode:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabValidCode tabValidCode) {
		tabValidCodeService.delete(tabValidCode);
		return renderResult(Global.TRUE, text("删除tab_valid_code成功！"));
	}
	
}