package com.jeesite.modules.tab.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.service.TabUserDataService;
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
import com.jeesite.modules.tab.entity.TabTyjJiesuan;
import com.jeesite.modules.tab.service.TabTyjJiesuanService;

import java.util.List;

/**
 * tab_tyj_jiesuanController
 * @author 1
 * @version 2022-04-12
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabTyjJiesuan")
public class TabTyjJiesuanController extends BaseController {

	@Autowired
	private TabTyjJiesuanService tabTyjJiesuanService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabTyjJiesuan get(String rowid, boolean isNewRecord) {
		return tabTyjJiesuanService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	//@RequiresPermissions("tab:tabTyjJiesuan:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabTyjJiesuan tabTyjJiesuan, Model model) {
		model.addAttribute("tabTyjJiesuan", tabTyjJiesuan);
		return "modules/tab/tabTyjJiesuanList";
	}
	@Autowired
	private TabUserDataService tabUserDataService;

	@Autowired
	private UserService userService;

	/**
	 * 查询列表数据
	 */
	//@RequiresPermissions("tab:tabTyjJiesuan:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabTyjJiesuan> listData(TabTyjJiesuan tabTyjJiesuan, HttpServletRequest request, HttpServletResponse response) {
		String id = UserUtils.getLoginInfo().getId();
		User user = userService.get(id);
		String acc = user.getLoginCode();
		if(!"admin".equals(acc)){
			TabUserData userData = new TabUserData();
			userData.setSysuserid(acc);
			List<TabUserData> tabUserDatas = tabUserDataService.findList(userData);
			if (tabUserDatas.size() > 0) {
				acc = tabUserDatas.get(0).getRowid();
			}
		}
		if (!acc.equals("admin")) {
			tabTyjJiesuan.setShangjilink(acc);
		}

		tabTyjJiesuan.setPage(new Page<>(request, response));
		Page<TabTyjJiesuan> page = tabTyjJiesuanService.findPage(tabTyjJiesuan);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabTyjJiesuan:view")
	@RequestMapping(value = "form")
	public String form(TabTyjJiesuan tabTyjJiesuan, Model model) {
		model.addAttribute("tabTyjJiesuan", tabTyjJiesuan);
		return "modules/tab/tabTyjJiesuanForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabTyjJiesuan:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabTyjJiesuan tabTyjJiesuan) {
		tabTyjJiesuanService.save(tabTyjJiesuan);
		return renderResult(Global.TRUE, text("保存tab_tyj_jiesuan成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabTyjJiesuan:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabTyjJiesuan tabTyjJiesuan) {
		tabTyjJiesuanService.delete(tabTyjJiesuan);
		return renderResult(Global.TRUE, text("删除tab_tyj_jiesuan成功！"));
	}
	
}