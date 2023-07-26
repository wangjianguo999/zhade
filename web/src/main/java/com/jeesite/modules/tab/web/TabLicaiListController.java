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
import com.jeesite.modules.tab.entity.TabLicaiList;
import com.jeesite.modules.tab.service.TabLicaiListService;

/**
 * tab_licai_listController
 * @author 32
 * @version 2022-06-15
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabLicaiList")
public class TabLicaiListController extends BaseController {

	@Autowired
	private TabLicaiListService tabLicaiListService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabLicaiList get(String rowid, boolean isNewRecord) {
		return tabLicaiListService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabLicaiList:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabLicaiList tabLicaiList, Model model) {
		model.addAttribute("tabLicaiList", tabLicaiList);
		return "modules/tab/tabLicaiListList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabLicaiList:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabLicaiList> listData(TabLicaiList tabLicaiList, HttpServletRequest request, HttpServletResponse response) {
		tabLicaiList.setPage(new Page<>(request, response));
		Page<TabLicaiList> page = tabLicaiListService.findPage(tabLicaiList);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabLicaiList:view")
	@RequestMapping(value = "form")
	public String form(TabLicaiList tabLicaiList, Model model) {
		model.addAttribute("tabLicaiList", tabLicaiList);
		return "modules/tab/tabLicaiListForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabLicaiList:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabLicaiList tabLicaiList) {
		tabLicaiListService.save(tabLicaiList);
		return renderResult(Global.TRUE, text("保存tab_licai_list成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabLicaiList:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabLicaiList tabLicaiList) {
		tabLicaiListService.delete(tabLicaiList);
		return renderResult(Global.TRUE, text("删除tab_licai_list成功！"));
	}
	
}