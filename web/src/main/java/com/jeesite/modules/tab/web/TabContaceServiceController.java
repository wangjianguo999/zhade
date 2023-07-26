package com.jeesite.modules.tab.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.tab.entity.TabYuangongData;
import com.jeesite.modules.tab.service.TabYuangongDataService;
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
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tab.entity.TabContaceService;
import com.jeesite.modules.tab.service.TabContaceServiceService;

/**
 * tab_contace_serviceController
 * @author 3
 * @version 2021-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabContaceService")
public class TabContaceServiceController extends BaseController {

	@Autowired
	private TabContaceServiceService tabContaceServiceService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabContaceService get(String rowid, boolean isNewRecord) {
		return tabContaceServiceService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabContaceService:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabContaceService tabContaceService, Model model) {
		model.addAttribute("tabContaceService", tabContaceService);
		return "modules/tab/tabContaceServiceList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabContaceService:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabContaceService> listData(TabContaceService tabContaceService, HttpServletRequest request, HttpServletResponse response) {
		
		String id= UserUtils.getLoginInfo().getId();
		
		User user   =  userService.get(id);
		
		String  acc= user.getLoginCode();
		tabContaceService.setRowid(acc);
		tabContaceService.setPage(new Page<>(request, response));
		Page<TabContaceService> page = tabContaceServiceService.findPage(tabContaceService);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	//@RequiresPermissions("tab:tabContaceService:view")
	@RequestMapping(value = "form")
	public String form(TabContaceService tabContaceService, Model model) {
		//model.addAttribute("tabContaceService", tabContaceService);
		return "modules/daili/tabContaceServiceForm";
	}

	@Autowired
	private TabYuangongDataService tabYuangongDataService;

	/**
	 * 保存数据
	 */
	//@RequiresPermissions("tab:tabContaceService:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabContaceService tabContaceService,HttpServletRequest request) {

		String token = (String) request.getSession().getAttribute("token");
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		System.out.println("员工名称："+tabYuangongData.getAcccount());
		TabContaceService  service  =   tabContaceServiceService.get(tabYuangongData.getAcccount()) ;

		if(service!= null) {
		
			tabContaceServiceService.delete(service); ;
		}
		tabContaceService.setRowid(tabYuangongData.getAcccount()) ;
		tabContaceService.setIsNewRecord(true);;
		tabContaceServiceService.save(tabContaceService);
		return renderResult(Global.TRUE, text("保存tab_contace_service成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabContaceService:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabContaceService tabContaceService) {
		tabContaceServiceService.delete(tabContaceService);
		return renderResult(Global.TRUE, text("删除tab_contace_service成功！"));
	}
	
	@Autowired
	private  UserService  userService ;
	
}