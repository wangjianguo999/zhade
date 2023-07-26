package com.jeesite.modules.tab.web;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
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
import com.jeesite.modules.tab.entity.TabJiesuanLog;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.service.TabJiesuanLogService;
import com.jeesite.modules.tab.service.TabUserDataService;
import redis.clients.jedis.Jedis;

/**
 * tab_jiesuan_logController
 * @author 23
 * @version 2022-02-03
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabJiesuanLog")
public class TabJiesuanLogController extends BaseController {

	@Autowired
	private TabJiesuanLogService tabJiesuanLogService;
	private static String JIESUAN_KEY = "JIESUAN:queue";

	private Jedis jedis =  new Jedis("41.72.149.115", 6379);
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabJiesuanLog get(String rowid, boolean isNewRecord) {
		return tabJiesuanLogService.get(rowid, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabJiesuanLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabJiesuanLog tabJiesuanLog, Model model) {
		model.addAttribute("tabJiesuanLog", tabJiesuanLog);

		List<User> users   =  userService.findList(new User());
		model.addAttribute("users", users);


		return "modules/tab/tabJiesuanLogList";
	}


	@Autowired
	private TabUserDataService  tabUserDataService ;

	@Autowired
	private UserService  userService;
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabJiesuanLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabJiesuanLog> listData(TabJiesuanLog tabJiesuanLog, HttpServletRequest request, HttpServletResponse response) {



		if (tabJiesuanLog.getCreatetime_lte() != null) {
			tabJiesuanLog.setCreatetime_lte(DateUtils.getOfDayLast(tabJiesuanLog.getCreatetime_lte()));

		}

		String  id = 	UserUtils.getLoginInfo().getId();


		User user   =  userService.get(id);

		String  acc= user.getLoginCode();
		if (!acc.equals("admin")) {

			TabUserData userData  = new TabUserData();
			userData.setSysuserid(acc);

			List<TabUserData> tabUserDatas   =   tabUserDataService.findList(userData);

			if (tabUserDatas.size() >  0  ) {
				acc  =  tabUserDatas.get(0).getRowid();
			}


		}

		if (!acc.equals("admin")) {
			tabJiesuanLog.setShangjilink(acc);
		}else {

			String shangjilink =  tabJiesuanLog.getShangjilink();
			if(!StringUtils.isBlank(shangjilink)) {
				TabUserData tabUserData  = new TabUserData();
				tabUserData.setSysuserid(shangjilink); ;
				List<TabUserData> tabUserDatas  =  tabUserDataService.findList(tabUserData);
				if (tabUserDatas.size() >  0 ) {
					tabJiesuanLog.setShangjilink(tabUserDatas.get(0).getRowid());

				}
			}

		}

		tabJiesuanLog.setPage(new Page<>(request, response));
		Page<TabJiesuanLog> page = tabJiesuanLogService.findPage(tabJiesuanLog);
		List<TabJiesuanLog> lists = page.getList();
		/*for(TabJiesuanLog bean : lists){
			String ss = new BigDecimal(bean.getAmont()).setScale(3,BigDecimal.ROUND_UP).toString();
			bean.setAmont(Double.parseDouble(ss.substring(0,ss.length()-1)));
		}*/
		page.setList(lists);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabJiesuanLog:view")
	@RequestMapping(value = "form")
	public String form(TabJiesuanLog tabJiesuanLog, Model model) {
		model.addAttribute("tabJiesuanLog", tabJiesuanLog);
		return "modules/tab/tabJiesuanLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabJiesuanLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabJiesuanLog tabJiesuanLog) {
		tabJiesuanLogService.save(tabJiesuanLog);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
		jedis.quit();
		return renderResult(Global.TRUE, text("保存交易明细成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabJiesuanLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabJiesuanLog tabJiesuanLog) {
		tabJiesuanLogService.delete(tabJiesuanLog);
		return renderResult(Global.TRUE, text("删除交易明细成功！"));
	}

}