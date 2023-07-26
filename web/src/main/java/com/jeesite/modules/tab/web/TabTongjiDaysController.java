package com.jeesite.modules.tab.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
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
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tab.entity.TabTongjiDays;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.service.TabTongjiDaysService;
import com.jeesite.modules.tab.service.TabUserDataService;

/**
 * tab_tongji_daysController
 * @author 2
 * @version 2022-04-11
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabTongjiDays")
public class TabTongjiDaysController extends BaseController {

	@Autowired
	private TabTongjiDaysService tabTongjiDaysService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabTongjiDays get(String rowid, boolean isNewRecord) {
		return tabTongjiDaysService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabTongjiDays:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabTongjiDays tabTongjiDays, Model model) {
		model.addAttribute("tabTongjiDays", tabTongjiDays);
		return "modules/tab/tabTongjiDaysList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabTongjiDays:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabTongjiDays> listData(TabTongjiDays tabTongjiDays, HttpServletRequest request, HttpServletResponse response) {
		
		
		String id= UserUtils.getLoginInfo().getId();
		
		User user   =  userService.get(id);
			
			String  acc= user.getLoginCode();
			
			if (acc.equals("admin")) {
				
		
		
		if(StringUtils.isBlank(tabTongjiDays.getUserid())) {
			tabTongjiDays.setUserid("-1");
		}
			}else {
				
				if(StringUtils.isBlank(tabTongjiDays.getUserid())) {
					TabUserData tabUserData = new TabUserData() ;
					tabUserData.setSysuserid(acc);
					
					List<TabUserData> tabUserDatas  = tabUserDataService.findList(tabUserData);
					
					if (tabUserDatas.size() >  0 ) {
						tabTongjiDays.setUserid(tabUserDatas.get(0).getRowid());

					}
					
 				}
			}
		
		
		if (tabTongjiDays.getDays() != null) {
			tabTongjiDays.setDays(DateUtils.getOfDayLast(tabTongjiDays.getDays()));
 		}
		
		
		
		
		
		Map<String, String> parame = new HashedMap();
		
		if (!StringUtils.isBlank(tabTongjiDays.getShangjilink())) {
			
			parame.put("userId",  "%" + tabTongjiDays.getShangjilink() +"%");
			
		}
		
		
		if (tabTongjiDays.getDays_gte() !=   null  ) {
			parame.put("min",  DateUtils.formatDate(tabTongjiDays.getDays_gte(),  "yyyy-MM-dd HH:mm:ss"));

		}
		
		if (tabTongjiDays.getDays_lte() !=   null  ) {
			parame.put("max",  DateUtils.formatDate(tabTongjiDays.getDays_lte(), "yyyy-MM-dd HH:mm:ss"));

		}
		
		
		
		tabTongjiDays.setPage(new Page<>(request, response));
		
		
		
		
		
		
		Page<TabTongjiDays> page =  tabTongjiDaysService.findPage(tabTongjiDays);
		
		
		List<TabTongjiDays>  listss = page.getList();
		List<TabTongjiDays> list  =  new ArrayList<TabTongjiDays>();
		Map<String, TabTongjiDays> map  = new HashMap<String, TabTongjiDays>();
		for (TabTongjiDays tabTongjiDays2 : listss) {
			map.put(DateUtils.formatDate(tabTongjiDays2.getDays()), tabTongjiDays2);
		}
		Set<String> strings  = map.keySet(); 
		for (String string : strings) {
			list.add(map.get(string));
			
		}
//		page.setList(list);
		
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabTongjiDays:view")
	@RequestMapping(value = "form")
	public String form(TabTongjiDays tabTongjiDays, Model model) {
		model.addAttribute("tabTongjiDays", tabTongjiDays);
		return "modules/tab/tabTongjiDaysForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabTongjiDays:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabTongjiDays tabTongjiDays) {
		tabTongjiDaysService.save(tabTongjiDays);
		return renderResult(Global.TRUE, text("保存tab_tongji_days成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabTongjiDays:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabTongjiDays tabTongjiDays) {
		tabTongjiDaysService.delete(tabTongjiDays);
		return renderResult(Global.TRUE, text("删除tab_tongji_days成功！"));
	}
	
	@Autowired
	private   UserService userService;
	
	@Autowired
	private TabUserDataService tabUserDataService ;
	
}