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
import com.jeesite.modules.tab.entity.TabUserAddress;
import com.jeesite.modules.tab.service.TabUserAddressService;

/**
 * tab_user_addressController
 * @author 1
 * @version 2021-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabUserAddress")
public class TabUserAddressController extends BaseController {

	@Autowired
	private TabUserAddressService tabUserAddressService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabUserAddress get(String userid, boolean isNewRecord) {
		return tabUserAddressService.get(userid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabUserAddress:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabUserAddress tabUserAddress, Model model) {
		model.addAttribute("tabUserAddress", tabUserAddress);
		return "modules/tab/tabUserAddressList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabUserAddress:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabUserAddress> listData(TabUserAddress tabUserAddress, HttpServletRequest request, HttpServletResponse response) {
		tabUserAddress.setPage(new Page<>(request, response));
		Page<TabUserAddress> page = tabUserAddressService.findPage(tabUserAddress);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabUserAddress:view")
	@RequestMapping(value = "form")
	public String form(TabUserAddress tabUserAddress, Model model) {
		model.addAttribute("tabUserAddress", tabUserAddress);
		return "modules/tab/tabUserAddressForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabUserAddress:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabUserAddress tabUserAddress) {
		tabUserAddressService.save(tabUserAddress);
		return renderResult(Global.TRUE, text("保存tab_user_address成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabUserAddress:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabUserAddress tabUserAddress) {
		tabUserAddressService.delete(tabUserAddress);
		return renderResult(Global.TRUE, text("删除tab_user_address成功！"));
	}
	
}