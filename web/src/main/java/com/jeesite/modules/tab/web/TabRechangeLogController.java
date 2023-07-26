package com.jeesite.modules.tab.web;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jeesite.modules.tab.entity.*;
import com.jeesite.modules.tab.service.*;
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
import com.jeesite.modules.api.ApiController;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import redis.clients.jedis.Jedis;

/**
 * tab_rechange_logController
 * @author 3
 * @version 2021-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabRechangeLog")
public class TabRechangeLogController extends BaseController {

	@Autowired
	private TabRechangeLogService tabRechangeLogService;
	private static String JIESUAN_KEY = "JIESUAN:queue";

	private static String TOKEN = "TOKEN:";
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabRechangeLog get(String rowid, boolean isNewRecord) {
		return tabRechangeLogService.get(rowid, isNewRecord);
	}

	private Jedis jedis =  new Jedis("41.72.149.115", 6379);
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabRechangeLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabRechangeLog tabRechangeLog, Model model) {
		model.addAttribute("tabRechangeLog", tabRechangeLog);
		List<User> users   =  userService.findList(new User());
		model.addAttribute("users", users);


		TabTongdao onbj = new TabTongdao();
		onbj.setDaishou("2");
		List<TabTongdao > tongdaos  =   tabTongdaoService.findList(onbj );

		TabTongdao tabTongdao  = new TabTongdao();
		tabTongdao.setRowid("1");
		tabTongdao.setTongdaoName("ERC20-USDT");

		tongdaos.add(tabTongdao) ;

		tabTongdao  = new TabTongdao();
		tabTongdao.setRowid("12");

		tabTongdao.setTongdaoName("TRC20-USDT");

		tongdaos.add(tabTongdao) ;

		model.addAttribute("tongdaos", tongdaos);

		return "modules/tab/tabRechangeLogList";
	}

	/**
	 * 查询列表数据
	 */
	@Autowired
	private UserService  userService;
	@Autowired
	private TabUserDataService  tabUserDataService;
	@RequiresPermissions("tab:tabRechangeLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabRechangeLog> listData(TabRechangeLog tabRechangeLog, HttpServletRequest request, HttpServletResponse response) {


		if (tabRechangeLog.getUpdatetime_lte() != null) {
			tabRechangeLog.setUpdatetime_lte(DateUtils.getOfDayLast(tabRechangeLog.getUpdatetime_lte()));

		}

		if (tabRechangeLog.getCreatetime_lte() != null) {
			tabRechangeLog.setCreatetime_lte(DateUtils.getOfDayLast(tabRechangeLog.getCreatetime_lte()));

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
			tabRechangeLog.setShangjilink(acc);
		}else {
			String shangjilink =  tabRechangeLog.getShangjilink();
			if(!StringUtils.isBlank(shangjilink)) {
				TabUserData tabUserData  = new TabUserData();
				tabUserData.setSysuserid(shangjilink); ;
				List<TabUserData> tabUserDatas  =  tabUserDataService.findList(tabUserData);
				if (tabUserDatas.size() >  0 ) {
					tabRechangeLog.setShangjilink(tabUserDatas.get(0).getRowid());

				}
			}
		}


		tabRechangeLog.setPage(new Page<>(request, response));
		Page<TabRechangeLog> page = tabRechangeLogService.findPage(tabRechangeLog);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabRechangeLog:view")
	@RequestMapping(value = "form")
	public String form(TabRechangeLog tabRechangeLog, Model model) {
		model.addAttribute("tabRechangeLog", tabRechangeLog);
		return "modules/tab/tabRechangeLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabRechangeLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabRechangeLog tabRechangeLog) {
		tabRechangeLog.setBeizhu("支付成功tab:tabRechangeLog:edit");
		System.out.println("查看充值数据tabRechangeLog:edit："+ JSONObject.toJSONString(tabRechangeLog));
		tabRechangeLogService.save(tabRechangeLog);
		return renderResult(Global.TRUE, text("保存tab_rechange_log成功！"));
	}
	private static String VipConfig = "VipConfig:";
	public List<TabVipConfig> getVipConfigList(TabVipConfig tabVipConfig){
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		List<TabVipConfig> list = new ArrayList<>();
		if(StringUtils.isBlank(tabVipConfig.getVip())){
			tabVipConfig.setVip("list");
		}
		String vipConfigList = jedis.get(VipConfig+tabVipConfig.getVip());
		if(StringUtils.isBlank(vipConfigList)){
			if("list".equals(tabVipConfig.getVip())){
				list = tabVipConfigService.findList(new TabVipConfig());
			}else{
				list = tabVipConfigService.findList(tabVipConfig);
			}

			jedis.set(VipConfig+tabVipConfig.getVip(), JSON.toJSONString(list));
			jedis.expire(VipConfig+tabVipConfig.getVip(),7200);
		}else{
			list = JSON.parseObject(vipConfigList, new TypeReference<List<TabVipConfig>>(){});
		}
		jedis.quit();
		return list;
	}

	@Autowired
	private TabActiveImgsService tabActiveImgsService;

	@Autowired
	private TabTempsService tabTempsService;

	private static String UserDataByToken = "UserDataByToken:";


	@RequestMapping(value = "changeData")
	@ResponseBody
	public Map<String, Object> changeData(  HttpServletRequest request, HttpServletResponse response) {
		String id =  UserUtils.getLoginInfo().getId();
		User user   =  userService.get(id);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		if(user == null || !user.getLoginCode().equals("admin")){
			return null;
		}
		String rowids  =   request.getParameter("rowids");

		String[] ss=  rowids.split(",");

		for (String string : ss) {

			TabRechangeLog tabRechangeLog  =  tabRechangeLogService.get(string);

			String userId =  tabRechangeLog.getUserid();

			TabUserData tabUserData =tabUserDataService.get(userId);

			if(tabRechangeLog.getStatus1().equals("1") || tabRechangeLog.getStatus1().equals("3") ) {
				tabRechangeLog.setStatus1("2");
				tabRechangeLog.setBeizhu("支付成功changeData");
				tabRechangeLog.setUpdatetime(new ApiController().getjndDate( new Date()));
				try {
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
					tabRechangeLog.setRefundtime(format1.parse(format1.format(getjndDate(new Date()))));
				}catch (Exception e){

				}
				System.out.println("查看充值数据changeData："+ JSONObject.toJSONString(tabRechangeLog));



				TabJiesuanLog tabJiesuanLog   = new TabJiesuanLog();
				tabJiesuanLog.setUserid(tabRechangeLog.getUserid());
				tabJiesuanLog.setCmd("Recharge");
				tabJiesuanLog.setType("充值成功");
				tabJiesuanLog.setAmont(tabRechangeLog.getHzhb());
				tabJiesuanLog.setRowid(UUID.randomUUID().toString());
				tabJiesuanLog.setCreatetime(new ApiController().getjndDate(new Date()));
				if(tabRechangeLog.getOrderType() == 0){
					tabJiesuanLog.setMsg("Recharge successfully");
				}else{
					tabJiesuanLog.setMsg("Superior Recharge");
				}
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLog.setStatus1(tabRechangeLog.getOrderid());
				tabJiesuanLog.setShangjilink(tabRechangeLog.getShangjilink());
				tabJiesuanLog.setParentid1(tabRechangeLog.getParentid1());
				tabJiesuanLog.setParentid2(tabRechangeLog.getParentid2());
				tabJiesuanLog.setParentid3(tabRechangeLog.getParentid3());


				TabActiveImgs tabActiveImgs = new TabActiveImgs();
				tabActiveImgs.setIsrechange(1);
				List<TabActiveImgs> activeImgsList = tabActiveImgsService.findList(tabActiveImgs);
				if(activeImgsList.size() > 0){
					tabActiveImgs = activeImgsList.get(0);
					if(tabActiveImgs.getRowid().equals("43770824705")){
						TabTemps tabTemps = new TabTemps();
						//获取转盘奖励次数
						if(tabTempsService.get(tabUserData.getRowid()) == null){
							//if(tabUserData.getIstiyan().equals("0") && tabUserData.getVip() > 0){
							tabTemps.setRowid(tabUserData.getRowid());
							tabTemps.setUuu("111");
							tabTempsService.insert(tabTemps);

							String parentid = tabUserData.getParentid();
							TabUserData pa = tabUserDataService.get(parentid);
							if(pa != null){
								pa.setJianglicishu(pa.getJianglicishu()+1);
								pa.setIsNewRecord(false);
								tabUserDataService.save(pa);

								List<TabUserData> tabUserData11 = new ArrayList<>();
								tabUserData11.add(pa);
								jedis.set(UserDataByToken+pa.getAccesstoken(),JSON.toJSONString(tabUserData11));
								jedis.expire(UserDataByToken+pa.getAccesstoken(),7200);
							}
							//}
						}
					}else{
						if(tabRechangeLog.getCreatetime().getTime() > tabActiveImgs.getStarttime().getTime() && tabRechangeLog.getCreatetime().getTime() < tabActiveImgs.getEndtime().getTime()){
							Map<String,Object> map = new HashMap<>();
							map.put("userid",tabRechangeLog.getUserid());
							map.put("status1","2");
							map.put("starttime",tabActiveImgs.getStarttime());
							map.put("endtime",tabActiveImgs.getEndtime());
							Double rechangeLogDouble = tabRechangeLogService.getactiveSum(map);
							if(rechangeLogDouble == 0){
								Double reward = 0.0;
								if(StringUtils.isBlank(tabActiveImgs.getRechangerewardscale()) || Double.parseDouble(tabActiveImgs.getRechangerewardscale()) ==0){
									String rechangestr = tabActiveImgs.getRechangestr();
									//15-30&30-50&50-200
									String[] rechangelevel = rechangestr.split("&");
									String[] sss= rechangelevel[0].split("-");
									if(tabRechangeLog.getHzhb() >= Integer.parseInt(sss[0])){
										int qq = 0;
										for(int i=0; i<rechangelevel.length;i++){
											String[] edu = rechangelevel[i].split("-");
											if(tabRechangeLog.getHzhb() >= Integer.parseInt(edu[0]) && tabRechangeLog.getHzhb() < Integer.parseInt(edu[1])){
												qq = i;
												break;
											}
										}
										String rechangereward = tabActiveImgs.getRechangereward();
										//2&3&5
										String[] rechangreward1 = rechangereward.split("&");
										reward = Double.parseDouble(rechangreward1[qq]);
									}

								}else{
									Double scale = Double.parseDouble(tabActiveImgs.getRechangerewardscale());
									reward = tabRechangeLog.getHzhb() * scale /100;
								}
								if(reward > 0){
									TabJiesuanLog tabJiesuanLogreward = new TabJiesuanLog();
									tabJiesuanLogreward.setUserid(tabRechangeLog.getUserid());
									tabJiesuanLogreward.setCmd("Recharge rewards");
									tabJiesuanLogreward.setType("Recharge rewards");
									tabJiesuanLogreward.setMsg("Recharge rewards");
									tabJiesuanLogreward.setAmont(reward);
									tabJiesuanLogreward.setRowid(UUID.randomUUID().toString());
									tabJiesuanLogreward.setCreatetime(getjndDate(new Date()));
									tabJiesuanLogreward.setIsNewRecord(true);
									tabJiesuanLogreward.setStatus1(tabRechangeLog.getOrderid());
									tabJiesuanLogreward.setShangjilink(tabRechangeLog.getShangjilink());
									tabJiesuanLogreward.setParentid1(tabRechangeLog.getParentid1());
									tabJiesuanLogreward.setParentid2(tabRechangeLog.getParentid2());
									tabJiesuanLogreward.setParentid3(tabRechangeLog.getParentid3());
									tabJiesuanLogService.save(tabJiesuanLogreward);
									jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLogreward));
								}
							}
						}
					}
				}
				tabJiesuanLogService.save(tabJiesuanLog);
				jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));

				tabRechangeLogService.save(tabRechangeLog);

				if(tabUserData.getIstiyan().equals("1")){
					Double money = tabUserData.getCurrentmoney() + tabJiesuanLog.getAmont();

					List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
					Double mm = 10000.0;
					if(tabVipConfigs.size() > 0){
						mm = tabVipConfigs.get(0).getCurrentmoney();
					}
					if (money >= mm) {
						tabUserData.setIstiyan("0");
						tabUserData.setTyj(0D);
						tabUserData.setValidate(DateUtils.addDays(new Date(), 99999));
						tabUserDataService.save(tabUserData);

						TabTyjJiesuan tabTyjJiesuan = new TabTyjJiesuan();
						tabTyjJiesuan.setUserid(tabRechangeLog.getUserid());
						List<TabTyjJiesuan> tabTyjJiesuans = tabTyjJiesuanService.findList(tabTyjJiesuan);
						for (TabTyjJiesuan tabTyjJiesuan2 : tabTyjJiesuans) {
							tabTyjJiesuanService.delete(tabTyjJiesuan2);
						}
					}
				}
			}
		}
		jedis.quit();
		return null;
	}


	@RequestMapping(value = "tuikuan")
	@ResponseBody
	public Map<String, Object> tuikuan(  HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<>();
		String id =  UserUtils.getLoginInfo().getId();
		User user = userService.get(id);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		if(user == null || !user.getLoginCode().equals("admin")){
			map.put("code",0);
			map.put("msg","操作账号不是admin账号");
			return map;
		}
		String rowids  =   request.getParameter("rowids");

		String[] ss=  rowids.split(",");

		for (String string : ss) {
			TabRechangeLog tabRechangeLog  =  tabRechangeLogService.get(string);
			if(tabRechangeLog.getOrderType() == 1){
				map.put("code",0);
				map.put("msg","该订单是上级给下级充值，联系技术退款");
				return map;
			}
			Map<String, String> parame = new HashMap<>();
			parame.put("orderId", tabRechangeLog.getOrderid());
			parame.put("userId", tabRechangeLog.getUserid());
			String s = GoldpaysUtil.hanYuanUtils("http://41.72.149.115:8081/metaPay/api/tuikuan", "", parame);

			if(!"SUCCESS".equals(s)){
				map.put("code",0);
				map.put("msg","支付后台返回结果异常，联系技术："+s);
				return map;
			}

			tabRechangeLog.setStatus1("1");
			tabRechangeLog.setIsNewRecord(false);
			tabRechangeLogService.save(tabRechangeLog);

			TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
			tabJiesuanLog.setUserid(tabRechangeLog.getUserid());
			tabJiesuanLog.setCmd("System Gift");
			tabJiesuanLog.setType("后台退款");
			tabJiesuanLog.setMsg("Refund");
			tabJiesuanLog.setAmont(tabRechangeLog.getHzhb()*-1);
			tabJiesuanLog.setRowid("tk"+tabRechangeLog.getRowid());
			tabJiesuanLog.setCreatetime(getjndDate(new Date()));
			tabJiesuanLog.setIsNewRecord(true);
			tabJiesuanLog.setStatus1(tabRechangeLog.getOrderid());
			tabJiesuanLog.setShangjilink(tabRechangeLog.getShangjilink());
			tabJiesuanLog.setParentid1(tabRechangeLog.getParentid1());
			tabJiesuanLog.setParentid2(tabRechangeLog.getParentid2());
			tabJiesuanLog.setParentid3(tabRechangeLog.getParentid3());
			tabJiesuanLogService.save(tabJiesuanLog);
			jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));

		}
		jedis.quit();
		map.put("code",1);
		map.put("msg","退款成功");
		return map;
	}

	@Autowired
	private  TabJiesuanLogService tabJiesuanLogService;

	public static Date getjndDate(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -6);

		return calendar.getTime();
	}

	@RequestMapping(value = "tongJi")
	@ResponseBody
	public Map<String, Object > tongJi(TabRechangeLog tabRechangeLog, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap();

		String userid=  tabRechangeLog.getUserid();
		Date min  =  tabRechangeLog.getCreatetime_gte();
		Date max =  tabRechangeLog.getCreatetime_lte();

		Date min1  =  tabRechangeLog.getUpdatetime_gte();

		Date max1 =  tabRechangeLog.getUpdatetime_lte();


		if (!StringUtils.isBlank(userid)) {

			map.put("userid", userid);
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
			tabRechangeLog.setShangjilink(acc);
		}


		String  shangjis   =  tabRechangeLog.getShangjilink();


		if (!StringUtils.isBlank(shangjis)) {

			TabUserData tabUserData  = new TabUserData();
			tabUserData.setSysuserid(shangjis); ;
			List<TabUserData> tabUserDatas  =  tabUserDataService.findList(tabUserData);
			if (tabUserDatas.size() >  0 ) {
				map.put("shangjis", "%" +  tabUserDatas.get(0).getRowid() +"%");


			}
		}

		if (min != null) {

			map.put("min", DateUtils.formatDate(min, "yyyy-MM-dd HH:mm:ss"));
		}

		if (min1 != null) {

			map.put("min1", DateUtils.formatDate(min1, "yyyy-MM-dd HH:mm:ss"));
		}

		if (max != null) {
			max = DateUtils.getOfDayLast(max);

			map.put("max", DateUtils.formatDate(max, "yyyy-MM-dd HH:mm:ss"));
		}

		if (max1 != null) {
			max1 = DateUtils.getOfDayLast(max1);

			map.put("max1", DateUtils.formatDate(max1, "yyyy-MM-dd HH:mm:ss"));
		}




		Double  zczje =     tabRechangeLogService.getCzje(map);
		Long  bs  = tabRechangeLogService.getCzbs(map);

		map.put("czje", zczje);
		map.put("bs", bs);


		return  map ;

	}
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabRechangeLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabRechangeLog tabRechangeLog) {
		tabRechangeLogService.delete(tabRechangeLog);
		return renderResult(Global.TRUE, text("删除tab_rechange_log成功！"));
	}
	@Autowired
	private  TabTongdaoService tabTongdaoService;

	public  String    getUserVip(String  userId )   {
		TabUserData tabUserData =     tabUserDataService.get(userId);
		if (!StringUtils.isBlank(tabUserData.getIstiyan())  &&   tabUserData.getIstiyan().equals("1") ) {
			tabUserData.setVip(  1L);
			tabUserDataService.save(tabUserData);
			return "1";
		}else {
			Long vip = 0L;
			List<TabVipConfig> tabVipConfigs = tabVipConfigService.findList(new TabVipConfig());
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData.getRowid());
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData.getCurrentmoney();

			int new_one_level = 0; //当前余额落在哪个区间
			for(int i = tabVipConfigs.size() - 1; i >= 0; i--){
				TabVipConfig vip_config = tabVipConfigs.get(i);

				if(vip_config.getMaxmoney() > 0 && (m >= (vip_config.getMaxmoney() == null ? 5000 : vip_config.getMaxmoney()))){
					new_one_level = Integer.valueOf(vip_config.getVip());
					break;
				}
			}
			int new_two_level = 0; //当前余额落在哪个区间
			for(int i = tabVipConfigs.size() - 1; i >= 0; i--){
				TabVipConfig vip_config = tabVipConfigs.get(i);
				if(m >= vip_config.getCurrentmoney()){
					if(i >= 1){
						for(int j = i - 1; j >= 0; j--){
							TabVipConfig tabVipConfig = tabVipConfigs.get(j);
							// 查看下级vip有多少人
							parame = new HashMap<String, String>();
							parame.put("yue", tabVipConfig.getVip());
							parame.put("userid", tabUserData.getRowid());
							Long renshu = tabUserDataService.getVipLevelCount(parame);
							if(renshu >= vip_config.getXiaji() && vip_config.getXiaji() >0){
								new_two_level = Integer.valueOf(tabVipConfig.getVip()) + 1;
								break;
							}
						}
					}else{
						new_two_level = 1;
					}

					if(new_two_level > 0){
						break;
					}
				}
				vip = (long) (new_one_level > new_two_level ? new_one_level : new_two_level);
			}
			if(tabUserData.getVip() != vip){
				tabUserData.setVip(vip);
				tabUserDataService.save(tabUserData);
			}
			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");
			/*org.json.JSONObject jsonObject = new org.json.JSONObject();
			jsonObject.put("rowid",tabUserData.getRowid());
			jsonObject.put("vip",String.valueOf(tabUserData.getVip()));*/
			List<TabUserData> list = new ArrayList();
			list.add(tabUserData);
			if(StringUtils.isBlank(tabUserData.getAccesstoken())){
				tabUserData.setAccesstoken(tabUserData.getRowid());
			}

			/*jedis.set(TOKEN+tabUserData.getAccesstoken(), jsonObject.toString());
			jedis.expire(TOKEN+tabUserData.getAccesstoken(),7200);*/

			jedis.set("UserDataByToken:"+tabUserData.getAccesstoken(), JSON.toJSONString(list));
			jedis.expire("UserDataByToken:"+tabUserData.getAccesstoken(),7200);

			if(tabUserData.getVip() > 0){
				//查找未完成的体验单
				TabOrderData obj  = new TabOrderData ();
				obj.setUserid(userId);
				obj.setStatus1("1");
				obj.setIstiyan("1");

				List<TabOrderData> tabOrderDatas  =   tabOrderDataService.findList(obj  );

				if (tabOrderDatas.size() >   0  ) {
					String orderId =   tabOrderDatas.get(0).getOrderid() ;
					obj= new TabOrderData();
					obj.setOrderid(orderId);

					tabOrderDatas =  tabOrderDataService.findList(obj);
					for (TabOrderData tabOrderData : tabOrderDatas) {
						tabOrderDataService.delete(tabOrderData);
					}

					TabOrders ooooo  = new TabOrders();
					ooooo.setOrderid(orderId);
					List<TabOrders>  tabOrders =  tabOrdersService.findList(ooooo  );

					for (TabOrders tabOrders2 : tabOrders) {
						tabOrdersService.delete(tabOrders2);
					}
				}
			}
		}
		jedis.quit();
		return "0";
	}

	/**
	 */



	@Autowired
	private TabOrdersService tabOrdersService;

	@Autowired
	private TabOrderDataService tabOrderDataService;

	@Autowired
	private  TabVipConfigService  tabVipConfigService;
	@Autowired
	TabTyjJiesuanService  tabTyjJiesuanService ;

}