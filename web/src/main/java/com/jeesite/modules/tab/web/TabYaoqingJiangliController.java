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
import com.jeesite.modules.tab.entity.TabYaoqingJiangli;
import com.jeesite.modules.tab.service.TabYaoqingJiangliService;

/**
 * tab_yaoqing_jiangliController
 * @author 1
 * @version 2022-04-06
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabYaoqingJiangli")
public class TabYaoqingJiangliController extends BaseController {

	@Autowired
	private TabYaoqingJiangliService tabYaoqingJiangliService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabYaoqingJiangli get(String rowid, boolean isNewRecord) {
		return tabYaoqingJiangliService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabYaoqingJiangli:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabYaoqingJiangli tabYaoqingJiangli, Model model) {
		model.addAttribute("tabYaoqingJiangli", tabYaoqingJiangli);
		return "modules/tab/tabYaoqingJiangliList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabYaoqingJiangli:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabYaoqingJiangli> listData(TabYaoqingJiangli tabYaoqingJiangli, HttpServletRequest request, HttpServletResponse response) {
		tabYaoqingJiangli.setPage(new Page<>(request, response));
		Page<TabYaoqingJiangli> page = tabYaoqingJiangliService.findPage(tabYaoqingJiangli);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabYaoqingJiangli:view")
	@RequestMapping(value = "form")
	public String form(TabYaoqingJiangli tabYaoqingJiangli, Model model) {
		model.addAttribute("tabYaoqingJiangli", tabYaoqingJiangli);
		return "modules/tab/tabYaoqingJiangliForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabYaoqingJiangli:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabYaoqingJiangli tabYaoqingJiangli) {
		tabYaoqingJiangliService.save(tabYaoqingJiangli);
		return renderResult(Global.TRUE, text("保存tab_yaoqing_jiangli成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabYaoqingJiangli:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabYaoqingJiangli tabYaoqingJiangli) {
		tabYaoqingJiangliService.delete(tabYaoqingJiangli);
		return renderResult(Global.TRUE, text("删除tab_yaoqing_jiangli成功！"));
	}
	
}