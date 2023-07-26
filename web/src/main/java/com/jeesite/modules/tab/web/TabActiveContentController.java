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
import com.jeesite.modules.tab.entity.TabActiveContent;
import com.jeesite.modules.tab.service.TabActiveContentService;

/**
 * tab_active_contentController
 * @author 32
 * @version 2022-04-21
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabActiveContent")
public class TabActiveContentController extends BaseController {

	@Autowired
	private TabActiveContentService tabActiveContentService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabActiveContent get(String rowid, boolean isNewRecord) {
		return tabActiveContentService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabActiveContent:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabActiveContent tabActiveContent, Model model) {
		model.addAttribute("tabActiveContent", tabActiveContent);
		return "modules/tab/tabActiveContentList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabActiveContent:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabActiveContent> listData(TabActiveContent tabActiveContent, HttpServletRequest request, HttpServletResponse response) {
		tabActiveContent.setPage(new Page<>(request, response));
		Page<TabActiveContent> page = tabActiveContentService.findPage(tabActiveContent);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabActiveContent:view")
	@RequestMapping(value = "form")
	public String form(TabActiveContent tabActiveContent, Model model) {
		model.addAttribute("tabActiveContent", tabActiveContent);
		return "modules/tab/tabActiveContentForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabActiveContent:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabActiveContent tabActiveContent) {
		tabActiveContentService.save(tabActiveContent);
		return renderResult(Global.TRUE, text("保存tab_active_content成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabActiveContent:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabActiveContent tabActiveContent) {
		tabActiveContentService.delete(tabActiveContent);
		return renderResult(Global.TRUE, text("删除tab_active_content成功！"));
	}
	
}