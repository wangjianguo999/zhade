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
import com.jeesite.modules.tab.entity.TabRechargeJiangli;
import com.jeesite.modules.tab.service.TabRechargeJiangliService;

/**
 * tab_recharge_jiangliController
 * @author 1
 * @version 2022-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabRechargeJiangli")
public class TabRechargeJiangliController extends BaseController {

	@Autowired
	private TabRechargeJiangliService tabRechargeJiangliService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabRechargeJiangli get(String rowid, boolean isNewRecord) {
		return tabRechargeJiangliService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabRechargeJiangli:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabRechargeJiangli tabRechargeJiangli, Model model) {
		model.addAttribute("tabRechargeJiangli", tabRechargeJiangli);
		return "modules/tab/tabRechargeJiangliList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabRechargeJiangli:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabRechargeJiangli> listData(TabRechargeJiangli tabRechargeJiangli, HttpServletRequest request, HttpServletResponse response) {
		tabRechargeJiangli.setPage(new Page<>(request, response));
		Page<TabRechargeJiangli> page = tabRechargeJiangliService.findPage(tabRechargeJiangli);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabRechargeJiangli:view")
	@RequestMapping(value = "form")
	public String form(TabRechargeJiangli tabRechargeJiangli, Model model) {
		model.addAttribute("tabRechargeJiangli", tabRechargeJiangli);
		return "modules/tab/tabRechargeJiangliForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabRechargeJiangli:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabRechargeJiangli tabRechargeJiangli) {
		tabRechargeJiangliService.save(tabRechargeJiangli);
		return renderResult(Global.TRUE, text("保存tab_recharge_jiangli成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabRechargeJiangli:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabRechargeJiangli tabRechargeJiangli) {
		tabRechargeJiangliService.delete(tabRechargeJiangli);
		return renderResult(Global.TRUE, text("删除tab_recharge_jiangli成功！"));
	}
	
}