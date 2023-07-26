package com.jeesite.modules.view.web;

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
import com.jeesite.modules.view.entity.ViewShouchong;
import com.jeesite.modules.view.service.ViewShouchongService;

/**
 * VIEWController
 * @author 22
 * @version 2022-03-20
 */
@Controller
@RequestMapping(value = "${adminPath}/view/viewShouchong")
public class ViewShouchongController extends BaseController {

	@Autowired
	private ViewShouchongService viewShouchongService;
	
	/**
	 * 获取数据
	 */
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("view:viewShouchong:view")
	@RequestMapping(value = {"list", ""})
	public String list(ViewShouchong viewShouchong, Model model) {
		model.addAttribute("viewShouchong", viewShouchong);
		return "modules/view/viewShouchongList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("view:viewShouchong:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<ViewShouchong> listData(ViewShouchong viewShouchong, HttpServletRequest request, HttpServletResponse response) {
		viewShouchong.setPage(new Page<>(request, response));
		Page<ViewShouchong> page = viewShouchongService.findPage(viewShouchong);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("view:viewShouchong:view")
	@RequestMapping(value = "form")
	public String form(ViewShouchong viewShouchong, Model model) {
		model.addAttribute("viewShouchong", viewShouchong);
		return "modules/view/viewShouchongForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("view:viewShouchong:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ViewShouchong viewShouchong) {
		viewShouchongService.save(viewShouchong);
		return renderResult(Global.TRUE, text("保存VIEW成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("view:viewShouchong:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ViewShouchong viewShouchong) {
		viewShouchongService.delete(viewShouchong);
		return renderResult(Global.TRUE, text("删除VIEW成功！"));
	}
	
}