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
import com.jeesite.modules.tab.entity.TabYaoqingJiangliHuodong;
import com.jeesite.modules.tab.service.TabYaoqingJiangliHuodongService;

/**
 * tab_yaoqing_jiangli_huodongController
 * @author 3
 * @version 2022-04-21
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabYaoqingJiangliHuodong")
public class TabYaoqingJiangliHuodongController extends BaseController {

	@Autowired
	private TabYaoqingJiangliHuodongService tabYaoqingJiangliHuodongService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabYaoqingJiangliHuodong get(String rowid, boolean isNewRecord) {
		return tabYaoqingJiangliHuodongService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliHuodong:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabYaoqingJiangliHuodong tabYaoqingJiangliHuodong, Model model) {
		model.addAttribute("tabYaoqingJiangliHuodong", tabYaoqingJiangliHuodong);
		return "modules/tab/tabYaoqingJiangliHuodongList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliHuodong:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabYaoqingJiangliHuodong> listData(TabYaoqingJiangliHuodong tabYaoqingJiangliHuodong, HttpServletRequest request, HttpServletResponse response) {
		tabYaoqingJiangliHuodong.setPage(new Page<>(request, response));
		Page<TabYaoqingJiangliHuodong> page = tabYaoqingJiangliHuodongService.findPage(tabYaoqingJiangliHuodong);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliHuodong:view")
	@RequestMapping(value = "form")
	public String form(TabYaoqingJiangliHuodong tabYaoqingJiangliHuodong, Model model) {
		model.addAttribute("tabYaoqingJiangliHuodong", tabYaoqingJiangliHuodong);
		return "modules/tab/tabYaoqingJiangliHuodongForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliHuodong:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabYaoqingJiangliHuodong tabYaoqingJiangliHuodong) {
		tabYaoqingJiangliHuodongService.save(tabYaoqingJiangliHuodong);
		return renderResult(Global.TRUE, text("保存tab_yaoqing_jiangli_huodong成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabYaoqingJiangliHuodong:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabYaoqingJiangliHuodong tabYaoqingJiangliHuodong) {
		tabYaoqingJiangliHuodongService.delete(tabYaoqingJiangliHuodong);
		return renderResult(Global.TRUE, text("删除tab_yaoqing_jiangli_huodong成功！"));
	}
	
}