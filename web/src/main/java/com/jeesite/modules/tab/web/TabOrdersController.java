package com.jeesite.modules.tab.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.jeesite.modules.api.ApiController;
import com.jeesite.modules.tab.entity.TabJiesuanLog;
import com.jeesite.modules.tab.service.*;
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
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tab.entity.TabOrderData;
import com.jeesite.modules.tab.entity.TabOrders;
import com.jeesite.modules.tab.entity.TabUserData;
import redis.clients.jedis.Jedis;

/**
 * tab_ordersController
 * @author 1
 * @version 2022-04-10
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabOrders")
public class TabOrdersController extends BaseController {

	@Autowired
	private TabOrdersService tabOrdersService;

	@Autowired
	private TabJiesuanLogService tabJiesuanLogService;

	private Jedis jedis =  new Jedis("41.72.149.115", 6379);

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabOrders get(String rowid, boolean isNewRecord) {
		return tabOrdersService.get(rowid, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabOrders:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabOrders tabOrders, Model model) {
		model.addAttribute("tabOrders", tabOrders);
		return "modules/tab/tabOrdersList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabOrders:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabOrders> listData(TabOrders tabOrders, HttpServletRequest request, HttpServletResponse response) {




		if (tabOrders.getCreatetime_lte() != null) {
			tabOrders.setCreatetime_lte(DateUtils.getOfDayLast(tabOrders.getCreatetime_lte()));

		}

		if (tabOrders.getCreatetime1_lte() != null) {
			tabOrders.setCreatetime1_lte(DateUtils.getOfDayLast(tabOrders.getCreatetime1_lte()));

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
			tabOrders.setShangjilink(acc);
		}
		tabOrders.setPage(new Page<>(request, response));
		Page<TabOrders> page = tabOrdersService.findPage(tabOrders);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabOrders:view")
	@RequestMapping(value = "form")
	public String form(TabOrders tabOrders, Model model) {
		model.addAttribute("tabOrders", tabOrders);
		return "modules/tab/tabOrdersForm";
	}
	@Autowired
	private TabUserDataService tabUserDataService;
	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabOrders:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabOrders tabOrders) {
		tabOrdersService.save(tabOrders);
		return renderResult(Global.TRUE, text("保存tab_orders成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabOrders:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabOrders tabOrders) {
		tabOrdersService.delete(tabOrders);
		return renderResult(Global.TRUE, text("删除tab_orders成功！"));
	}

	//tabOrders

	@RequestMapping(value = "qingChu")
	@ResponseBody
	public String qingChu(HttpServletRequest servletRequest) {

		String rowids=   servletRequest.getParameter("rowids");

		String[] split = rowids.split(",");

		for (String string : split) {

			TabOrders tabOrders  =  tabOrdersService.get(string);

			tabOrdersService.delete(tabOrders);

			String ord =  tabOrders.getOrderid() ;

			TabOrderData tabOrderData  = new TabOrderData();
			tabOrderData.setOrderid(ord); ;

			List<TabOrderData> tabOrderDatas  = tabOrderDataService.findList(tabOrderData);

			if(tabOrderDatas.size() <= 10) {

				for (TabOrderData string2 : tabOrderDatas) {
					tabOrderDataService.delete(string2);;
				}

			}
		}

		return renderResult(Global.TRUE, text("删除tab_orders成功！"));
	}
	private static String isBuyProDuct = "IsBuyProDuct:";

	private static  String JIESUAN_KEY = "JIESUAN:queue";

	@RequestMapping(value = "qingChu2")
	@ResponseBody
	public String qingChu2(HttpServletRequest servletRequest) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String rowids=   servletRequest.getParameter("rowids");

		String[] split = rowids.split(",");

		for (String string : split) {
			TabOrders tabOrders = tabOrdersService.get(string);
			try {
				String orderId = tabOrders.getOrderid();
				if(tabOrders.getIstiyan().equals("0")){
					TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
					tabJiesuanLog.setUserid(tabOrders.getUserid());
					tabJiesuanLog.setCmd("重置订单");
					tabJiesuanLog.setType("重置订单");
					tabJiesuanLog.setAmont(0.0);
					TabJiesuanLog tabJiesuanLog1 = new TabJiesuanLog();
					tabJiesuanLog1.setUserid(tabOrders.getUserid());
					tabJiesuanLog1.setStatus1(tabOrders.getOrderid());
					if(tabOrders.getStatus1().equals("4")){
						tabJiesuanLog1.setCmd("Commission");
						List<TabJiesuanLog> list = tabJiesuanLogService.findList(tabJiesuanLog1);
						if(list.size()>0){
							tabJiesuanLog.setAmont(tabJiesuanLog.getAmont()+list.get(0).getAmont()*-1);
						}
					}else{
						List<TabJiesuanLog> list = tabJiesuanLogService.findList(tabJiesuanLog1);
						for(TabJiesuanLog bean : list){
							if(bean.getType().equals("购买商品")){
								tabJiesuanLog.setAmont(tabJiesuanLog.getAmont() + bean.getAmont());
							}
						}
						tabJiesuanLog.setAmont(tabJiesuanLog.getAmont()*-1+0.0001);
						jedis.del(isBuyProDuct+tabOrders.getUserid());
					}
					tabJiesuanLog.setRowid(UUID.randomUUID().toString());
					tabJiesuanLog.setCreatetime(new ApiController().getjndDate(new Date()));
					tabJiesuanLog.setIsNewRecord(true);
					tabJiesuanLog.setMsg("Reset Order");
					tabJiesuanLog.setStatus1(tabOrders.getOrderid());
					tabJiesuanLog.setShangjilink(tabOrders.getShangjilink());
					tabJiesuanLog.setParentid1(tabOrders.getParentid1());
					tabJiesuanLog.setParentid2(tabOrders.getParentid2());
					tabJiesuanLog.setParentid3(tabOrders.getParentid3());
					tabJiesuanLogService.save(tabJiesuanLog);
					jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));

				}else{
					tabTyjJiesuanService.deleteListByOrderId(orderId);
					if(tabOrders.getStatus1().equals("4")){
						TabJiesuanLog tabJiesuanLog1 = new TabJiesuanLog();
						tabJiesuanLog1.setUserid(tabOrders.getUserid());
						tabJiesuanLog1.setStatus1(tabOrders.getOrderid());
						tabJiesuanLog1.setCmd("Commission");
						List<TabJiesuanLog> list = tabJiesuanLogService.findList(tabJiesuanLog1);
						if(list.size()>0){
							TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
							tabJiesuanLog.setUserid(tabOrders.getUserid());
							tabJiesuanLog.setCmd("重置订单");
							tabJiesuanLog.setType("重置订单");
							tabJiesuanLog.setAmont(list.get(0).getAmont()*-1);
							tabJiesuanLog.setRowid(UUID.randomUUID().toString());
							tabJiesuanLog.setCreatetime(new ApiController().getjndDate(new Date()));
							tabJiesuanLog.setIsNewRecord(true);
							tabJiesuanLog.setMsg("Reset Order");
							tabJiesuanLog.setStatus1(tabOrders.getOrderid());
							tabJiesuanLog.setShangjilink(tabOrders.getShangjilink());
							tabJiesuanLog.setParentid1(tabOrders.getParentid1());
							tabJiesuanLog.setParentid2(tabOrders.getParentid2());
							tabJiesuanLog.setParentid3(tabOrders.getParentid3());
							tabJiesuanLogService.save(tabJiesuanLog);
							jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
						}
					}else{
						jedis.del(isBuyProDuct+tabOrders.getUserid());
					}
				}
				tabOrderDataService.deleteListByOrderId(orderId);

				tabOrdersService.delete(tabOrders);
				//jedis.del(isBuyProDuct+tabOrders1.getUserid());
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		jedis.quit();
		return  "";
	}

	public static Date getjndDate(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -6);

		return calendar.getTime();
	}
	@Autowired
	private UserService  userService;

	@Autowired
	private TabOrderDataService tabOrderDataService ;

	@Autowired
	private TabTyjJiesuanService tabTyjJiesuanService;
}