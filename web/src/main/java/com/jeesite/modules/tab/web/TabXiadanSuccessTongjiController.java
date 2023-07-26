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
import com.jeesite.modules.tab.entity.TabXiadanSuccessTongji;
import com.jeesite.modules.tab.service.TabXiadanSuccessTongjiService;

/**
 * tab_xiadan_success_tongjiController
 * @author 1
 * @version 2022-04-05
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabXiadanSuccessTongji")
public class TabXiadanSuccessTongjiController extends BaseController {

	@Autowired
	private TabXiadanSuccessTongjiService tabXiadanSuccessTongjiService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabXiadanSuccessTongji get(String rowidva, boolean isNewRecord) {
		return tabXiadanSuccessTongjiService.get(rowidva, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabXiadanSuccessTongji:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabXiadanSuccessTongji tabXiadanSuccessTongji, Model model) {
		model.addAttribute("tabXiadanSuccessTongji", tabXiadanSuccessTongji);
		return "modules/tab/tabXiadanSuccessTongjiList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabXiadanSuccessTongji:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabXiadanSuccessTongji> listData(TabXiadanSuccessTongji tabXiadanSuccessTongji, HttpServletRequest request, HttpServletResponse response) {
		tabXiadanSuccessTongji.setPage(new Page<>(request, response));
		Page<TabXiadanSuccessTongji> page = tabXiadanSuccessTongjiService.findPage(tabXiadanSuccessTongji);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabXiadanSuccessTongji:view")
	@RequestMapping(value = "form")
	public String form(TabXiadanSuccessTongji tabXiadanSuccessTongji, Model model) {
		model.addAttribute("tabXiadanSuccessTongji", tabXiadanSuccessTongji);
		return "modules/tab/tabXiadanSuccessTongjiForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabXiadanSuccessTongji:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabXiadanSuccessTongji tabXiadanSuccessTongji) {
		tabXiadanSuccessTongjiService.save(tabXiadanSuccessTongji);
		return renderResult(Global.TRUE, text("保存tab_xiadan_success_tongji成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabXiadanSuccessTongji:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabXiadanSuccessTongji tabXiadanSuccessTongji) {
		tabXiadanSuccessTongjiService.delete(tabXiadanSuccessTongji);
		return renderResult(Global.TRUE, text("删除tab_xiadan_success_tongji成功！"));
	}
	
}