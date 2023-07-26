package com.jeesite.modules.tab.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
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
import com.jeesite.common.shiro.realm.LoginInfo;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.ApiController;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;

import redis.clients.jedis.Jedis;

/**
 * tab_user_dataController
 *
 * @author 1
 * @version 2021-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabUserData")
public class TabUserDataController extends BaseController {

	//private Jedis jedis =  new Jedis("41.72.149.115", 6379);
	private static String JIESUAN_KEY = "JIESUAN:queue";
	@Autowired
	private TabUserDataService tabUserDataService;
	private static String TOKEN = "TOKEN:";

	@Autowired
	private TabActiveImgsService tabActiveImgsService;

	@Autowired
	private TabTempsService tabTempsService;
	/**
	 * 获取数据
	 */

	@RequestMapping(value = "listData2")
	@ResponseBody
	public Page<TabUserData> listData2(TabUserData tabUserData, HttpServletRequest request,
									   HttpServletResponse response) {
		Page<TabUserData> page = new Page<>();
		tabUserData = tabUserDataService.get(tabUserData.getRowid());

		List<TabUserData> tabUserDatas = new ArrayList<TabUserData>();
		String string = tabUserData.getShangjilink();
		if (!StringUtils.isBlank(string)) {
			String[] ll = string.split(",");
			for (String string2 : ll) {
				if (!string2.equals("null")) {
					TabUserData userData = tabUserDataService.get(string2);
					if (userData != null) {
						tabUserDatas.add(userData);
					}
				}
			}
		}
		//List<TabVipConfig> tabVipConfigs = tabVipConfigService.findList(new TabVipConfig());
		for (TabUserData tabUserData2 : tabUserDatas) {//我的所有上级
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData2.getRowid());
			tabUserData2.setHuilv(String.valueOf(tabUserData2.getCurrentmoney()));


			parame.put("userid", tabUserData2.getRowid().toString());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ApiController.getjndDate(new Date()));
			Date min1 = calendar.getTime();
			String mmm = DateUtils.formatDate(min1);
			parame.put("date1", mmm.toString());
			Long count = tabUserDataService.getShangJiLinkCount(tabUserData2.getRowid());
			tabUserData2.setSumMember(count.doubleValue());

			/*String zt = tabOrderDataService.getLastData(parame);
			if (zt == null) {
				zt = "0";
			}

			if (zt.equals("0")) {
				zt = "未下单";
			} else if (zt.equals("1")) {
				zt = "已下单,未支付";
			} else if (zt.equals("2")) {
				zt = "部分支付";
			} else {
				zt = "已完成";
			}*/
			//tabUserData2.setVip(vip);
			//tabUserData2.setXdzt(zt);
			/*if (tabUserDatas.size() == 1) {
				Double shouru = tabJiesuanLogService.getShouruData(parame);
				tabUserData2.setGrsy(shouru);
				Double m = tabRechangeLogService.getSum(parame);
				tabUserData2.setCzje(m);
				Double ztx = tabTixianLogService.getSum(parame);
				tabUserData2.setTxje(ztx);
				Double mm = tabJiesuanLogService.getShouruData(parame);
				tabUserData2.setGrsy(mm);
				Double m1 = tabJiesuanLogService.getTuandui(parame);
				tabUserData2.setTdsy(m1);
			}*/
		}
		page.setList(tabUserDatas);
		return page;
	}

	@RequestMapping(value = "listData3")
	@ResponseBody
	public Page<TabUserData> listData3(TabUserData tabUserData, HttpServletRequest request,
									   HttpServletResponse response) {
		List<TabUserData> tabUserDatas = new ArrayList<TabUserData>();
		TabUserData userData = new TabUserData();
		userData.setShangjilink(tabUserData.getRowid());
		userData.setPage(new Page<>(request, response));
		userData.setRowid(null);
		Page<TabUserData> page = tabUserDataService.findPage(userData);
		tabUserDatas = page.getList();
		List<TabVipConfig> tabVipConfigs = tabVipConfigService.findList(new TabVipConfig());
		for (TabUserData tabUserData2 : tabUserDatas) {
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData2.getRowid());
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			tabUserData2.setHuilv(String.valueOf(tabUserData2.getCurrentmoney()));


			parame.put("userid", tabUserData2.getRowid().toString());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ApiController.getjndDate(new Date()));
			Date min1 = calendar.getTime();
			String mmm = DateUtils.formatDate(min1);
			parame.put("date1", mmm + "");

			Long count = tabUserDataService.getShangJiLinkCount(tabUserData2.getRowid());
			tabUserData2.setSumMember(count.doubleValue());

			/*String zt = tabOrderDataService.getLastData(parame);
			if (zt == null) {
				zt = "0";
			}

			if (zt.equals("0")) {
				zt = "未下单";
			} else if (zt.equals("1")) {
				zt = "已下单,未支付";
			} else if (zt.equals("2")) {
				zt = "部分支付";
			} else {
				zt = "已完成";
			}
			tabUserData2.setXdzt(zt);*/
			/*if (tabUserDatas.size() == 1) {
				Double shouru = tabJiesuanLogService.getShouruData(parame);
				tabUserData2.setGrsy(shouru);
				Double m2 = tabRechangeLogService.getSum(parame);
				tabUserData2.setCzje(m2);
				Double ztx = tabTixianLogService.getSum(parame);
				tabUserData2.setTxje(ztx);
				Double mm = tabJiesuanLogService.getShouruData(parame);
				tabUserData2.setGrsy(mm);
				Double m1 = tabJiesuanLogService.getTuandui(parame);
				tabUserData2.setTdsy(m1);
			}*/
		}
		page.setList(tabUserDatas);
		return page;
	}

	@RequestMapping(value = "shouchonglist")
	@ResponseBody
	public Page<TabUserData> shouchonglist(TabUserData tabUserData, HttpServletRequest request,
									   HttpServletResponse response) {
		String pageNostr = request.getParameter("pageNo");
		String pageSizestr = request.getParameter("pageSize");
		Integer pageNo = 0;
		Integer pageSize = 0;
		if(StringUtils.isBlank(pageNostr)){
			pageNo = 1;
		}else{
			pageNo = Integer.parseInt(pageNostr);
		}

		if(StringUtils.isBlank(pageSizestr)){
			pageSize = 20;
		}else{
			pageSize = Integer.parseInt(pageSizestr);
		}
		Map<String,Object> map = new HashMap<>();
		if(tabUserData.getCreatetime_gte() != null){
			map.put("updatetime1",DateUtils.formatDate(tabUserData.getCreatetime_gte(), "yyyy-MM-dd HH:mm:ss"));
		}

		if (tabUserData.getCreatetime_lte() != null) {
			tabUserData.setCreatetime_lte(DateUtils.getOfDayLast(tabUserData.getCreatetime_lte()));
			map.put("updatetime2",DateUtils.formatDate(tabUserData.getCreatetime_lte(), "yyyy-MM-dd HH:mm:ss"));
		}

		map.put("userid",tabUserData.getRowid());
		map.put("pageNo",(pageNo-1)*pageSize);
		map.put("pageSize",pageSize);
		List<TabUserData> list = tabUserDataService.getshouchonglist(map);
		Long count = tabUserDataService.getshouchonglistCount(map);
		Page<TabUserData> page = new Page<>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setList(list);
		page.setCount(count);
		return page;
	}

	@RequestMapping(value = "shouchonglistCount")
	@ResponseBody
	public Map<String, Object> shouchonglistCount(TabUserData tabUserData, HttpServletRequest request,
													HttpServletResponse response) {
		Map<String,Object> data = new HashMap<>();
		Map<String,Object> map = new HashMap<>();
		if(tabUserData.getCreatetime_gte() != null){
			map.put("updatetime1",DateUtils.formatDate(tabUserData.getCreatetime_gte(), "yyyy-MM-dd HH:mm:ss"));
		}

		if (tabUserData.getCreatetime_lte() != null) {
			tabUserData.setCreatetime_lte(DateUtils.getOfDayLast(tabUserData.getCreatetime_lte()));
			map.put("updatetime2",DateUtils.formatDate(tabUserData.getCreatetime_lte(), "yyyy-MM-dd HH:mm:ss"));
		}
		map.put("userid",tabUserData.getRowid());

		map.put("dayu","dayu");
		Long tdcz = tabUserDataService.getshouchonglistyuecount(map);
		data.put("tdcz",tdcz);
		map.remove("dayu");
		map.put("xiaoyu","xiaoyu");
		Long tdtx = tabUserDataService.getshouchonglistyuecount(map);
		data.put("tdtx",tdtx);
		return data;
	}

	@RequestMapping(value = "aveUpdatePassword")
	@ResponseBody
	public String aveUpdatePassword(TabUserData tabUserData) {
		tabUserDataService.save(tabUserData);
		return renderResult(Global.TRUE, text("更新成功"));
	}

	@ModelAttribute
	public TabUserData get(String rowid, boolean isNewRecord) {
		return tabUserDataService.get(rowid, isNewRecord);
	}

	/**
	 */
	@RequiresPermissions("tab:tabUserData:view")
	@RequestMapping(value = { "list", "" })
	public String list(TabUserData tabUserData, Model model) {
		List<User> users = userService.findList(new User());
		model.addAttribute("users", users);

		model.addAttribute("tabUserData", tabUserData);
		return "modules/tab/tabUserDataList";
	}

	/**
	 * 查询列表数据
	 */

	@Autowired
	private UserService userService;

	@RequiresPermissions("tab:tabUserData:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabUserData> listData(TabUserData tabUserData, HttpServletRequest request,
									  HttpServletResponse response) {
		String id = UserUtils.getLoginInfo().getId();
		if (tabUserData.getLastlogintime_lte() != null) {
			tabUserData.setLastlogintime_lte(DateUtils.getOfDayLast(tabUserData.getLastlogintime_lte()));
		}
		if (tabUserData.getCreatetime_lte() != null) {
			tabUserData.setCreatetime_lte(DateUtils.getOfDayLast(tabUserData.getCreatetime_lte()));
		}

		tabUserData.setRowid(tabUserData.getRowid().replaceAll(" ",""));
		User user = userService.get(id);
		String acc = user.getLoginCode();
		if (!acc.equals("admin")) {
			TabUserData userData = new TabUserData();
			userData.setSysuserid(acc);
			List<TabUserData> tabUserDatas = tabUserDataService.findList(userData);
			if (tabUserDatas.size() > 0) {
				acc = tabUserDatas.get(0).getRowid();
			}
		}

		if (!acc.equals("admin")) {
			tabUserData.setShangjilink(acc);
		}

		tabUserData.setPage(new Page<>(request, response));
		Page<TabUserData> page = tabUserDataService.findPage(tabUserData);
		List<TabUserData> tabUserDatas = page.getList();
		if (tabUserDatas.size() == 0 && (!StringUtils.isBlank(tabUserData.getRowid()))) {
			tabUserData = tabUserDataService.get(tabUserData.getRowid());
			if (tabUserData != null && !acc.equals("admin")) {
				if (tabUserData.getShangjilink().indexOf(acc) >= 0) {
					tabUserDatas.add(tabUserData);
				}
			}

			if (acc.equals("admin") && tabUserData != null) {
				tabUserDatas.add(tabUserData);
			}
		}
		List<TabVipConfig> tabVipConfigs = tabVipConfigService.findList(new TabVipConfig());
		for (TabUserData tabUserData2 : tabUserDatas) {
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData2.getRowid());
			tabUserData2.setHuilv(String.valueOf(tabUserData2.getCurrentmoney()));


			parame.put("userid", tabUserData2.getRowid().toString());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ApiController.getjndDate(new Date()));
			Date min1 = calendar.getTime();
			String mmm = DateUtils.formatDate(min1);
			parame.put("date1", mmm.toString());
			/*String zt = tabOrderDataService.getLastData(parame);
			if (zt == null) {
				zt = "0";
			}

			if (zt.equals("0")) {
				zt = "未下单";
			} else if (zt.equals("1")) {
				zt = "已下单,未支付";
			} else if (zt.equals("2")) {
				zt = "部分支付";
			} else {
				zt = "已完成";
			}
			tabUserData2.setXdzt(zt);*/
			Long count = tabUserDataService.getShangJiLinkCount(tabUserData2.getRowid());
			tabUserData2.setSumMember(count.doubleValue());

			tabUserData2.setHuilv(String.valueOf(tabUserData2.getCurrentmoney()));
			/*if (tabUserDatas.size() == 1) {
			Double shouru = tabJiesuanLogService.getShouruData(parame);
			tabUserData2.setGrsy(shouru);
			Double m1 = tabRechangeLogService.getSum(parame);
			tabUserData2.setCzje(m1);
			Double ztx = tabTixianLogService.getSum(parame);
			tabUserData2.setTxje(ztx);
			Double mm = tabJiesuanLogService.getUserMoney(parame);

			tabUserData2.setHuilv(mm.toString());
			Double m2 = tabJiesuanLogService.getTuandui(parame);
			tabUserData2.setTdsy(m2);
			}

			tabUserData2.setGrsy(tabUserData2.getGrsy()!=null ? Double.valueOf(tabUserData2.getGrsy().intValue()) :0D);
			tabUserData2.setCzje(tabUserData2.getCzje()!=null ? Double.valueOf(tabUserData2.getCzje().intValue()) :0D);
			tabUserData2.setTxje(tabUserData2.getTdsy()!=null ? Double.valueOf(tabUserData2.getTxje().intValue()) :0D);
			tabUserData2.setTdsy(tabUserData2.getTdsy()!=null ? Double.valueOf(tabUserData2.getTdsy().intValue()) :0D);
			tabUserData2.setHuilv(StringUtils.isNotBlank(tabUserData2.getHuilv()) ? Double.valueOf(tabUserData2.getHuilv()).intValue()+"" :"0");
			*/
		}

		page.setList(tabUserDatas);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabUserData:view")
	@RequestMapping(value = "form")
	public String form(TabUserData tabUserData, Model model) {
		tabUserData.setCurrentmoney(0d);
		model.addAttribute("tabUserData", tabUserData);
		return "modules/tab/tabUserDataForm";
	}

	@RequiresPermissions("tab:tabUserData:view")
	@RequestMapping(value = "form2")
	public String form2(TabUserData tabUserData, Model model) {
		tabUserData.setCurrentmoney(0d);

		model.addAttribute("tabUserData", tabUserData);
		return "modules/tab/tabUserDataForm2";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabUserData:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabUserData tabUserData,Integer istongji) {
		String txpassword = tabUserData.getTxpassword();
		String rowid = tabUserData.getRowid();

		Double m = tabUserData.getCurrentmoney();
		tabUserData = tabUserDataService.get(rowid);
		TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
		String orderId = DateUtils.formatDate(new Date()).replaceAll("-", "") + getOrder();

		tabJiesuanLog.setUserid(tabUserData.getRowid());
		tabJiesuanLog.setCmd("System add");
		tabJiesuanLog.setType(txpassword + ":操作人:" + UserUtils.getLoginInfo().getId());
		tabJiesuanLog.setAmont(m);
		tabJiesuanLog.setMsg(txpassword);
		tabJiesuanLog.setRowid(UUID.randomUUID().toString());
		tabJiesuanLog.setCreatetime(ApiController.getjndDate(new Date()));
		tabJiesuanLog.setCurrentmoney(tabUserData.getCurrentmoney());
		tabJiesuanLog.setIsNewRecord(true);
		tabJiesuanLog.setStatus1(orderId);

		tabJiesuanLog.setParentid1(tabUserData.getParentid());
		tabJiesuanLog.setParentid2(tabUserData.getParentid1());
		tabJiesuanLog.setParentid3(tabUserData.getParenti3());
		tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());

		tabJiesuanLogService.save(tabJiesuanLog);
		if(UserUtils.getLoginInfo().getId().equals("system")){
			if(istongji !=null && istongji == 1){
				TabRechangeLog tabRechangeLog = new TabRechangeLog();
				tabRechangeLog.setOrderType(0);
				tabRechangeLog.setUserid(tabUserData.getRowid());
				tabRechangeLog.setCreatetime(getjndDate(new Date()));
				tabRechangeLog.setIsNewRecord(true);
				tabRechangeLog.setOrderid(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + getCode());
				tabRechangeLog.setHzhb(m);
				tabRechangeLog.setStatus1("2");
				tabRechangeLog.setIstongji(1);
				tabRechangeLog.setParentid1(tabUserData.getParentid());
				tabRechangeLog.setParentid2(tabUserData.getParentid1());
				tabRechangeLog.setParentid3(tabUserData.getParenti3());
				tabRechangeLog.setShangjilink(tabUserData.getShangjilink());
				tabRechangeLog.setYgzh(tabUserData.getYgzh());
				tabRechangeLog.setYgzh2(tabUserData.getYgzh2());
				tabRechangeLogService.save(tabRechangeLog);
			}
		}

		//Map<String, String> parame2 = new HashMap<String, String>();
		//parame2.put("userid", tabUserData.getRowid());
		//Double money = tabJiesuanLogService.getUserMoney(parame2);
		//String uuid2 = tabUserData.getRowid();
		//tabUserData.setHuilv(tabUserData.getCurrentmoney()+m);




		if(tabUserData.getIstiyan().equals("1")){
			List<TabVipConfig> tabVipConfigs = tabVipConfigService.findList(new TabVipConfig());
			Double mm = 10000.0;
			if(tabVipConfigs.size() > 0){
				mm = tabVipConfigs.get(0).getCurrentmoney();
			}
			if (tabUserData.getCurrentmoney()+m >= mm) {
				//tabUserData.setVip(vip);
				tabUserData.setIstiyan("0");
				tabUserData.setValidate(DateUtils.addDays(new Date(), 99999));
				tabUserDataService.save(tabUserData);

				TabTyjJiesuan tabTyjJiesuan = new TabTyjJiesuan();
				tabTyjJiesuan.setUserid(tabUserData.getRowid());
				List<TabTyjJiesuan> tabTyjJiesuans = tabTyjJiesuanService.findList(tabTyjJiesuan);
				for (TabTyjJiesuan tabTyjJiesuan2 : tabTyjJiesuans) {
					tabTyjJiesuanService.delete(tabTyjJiesuan2);
				}
			}
		}
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
		dd(tabJiesuanLog,jedis);
		jedis.quit();

		addzhuanpan(tabUserData.getRowid());

		return renderResult(Global.TRUE, text("上分成功"));
	}

	public void addzhuanpan(String userid){
		TabActiveImgs tabActiveImgs = new TabActiveImgs();
		tabActiveImgs.setIsrechange(1);
		List<TabActiveImgs> activeImgsList = tabActiveImgsService.findList(tabActiveImgs);
		if(activeImgsList.size() > 0){
			tabActiveImgs = activeImgsList.get(0);
			if(tabActiveImgs.getRowid().equals("43770824705")){
				TabTemps tabTemps = new TabTemps();
				if(tabTempsService.get(userid) == null){
					tabTemps.setRowid(userid);
					tabTemps.setUuu("111");
					tabTempsService.insert(tabTemps);
					TabUserData tabUserData = tabUserDataService.get(userid);
					String parentid = tabUserData.getParentid();
					TabUserData pa = tabUserDataService.get(parentid);
					if(pa != null){
						pa.setJianglicishu(pa.getJianglicishu()+1);
						pa.setIsNewRecord(false);
						tabUserDataService.save(pa);
						List<TabUserData> tabUserData11 = new ArrayList<>();
						tabUserData11.add(pa);
						Jedis jedis =  new Jedis("41.72.149.115", 6379);
						jedis.auth("hask071bend");
						jedis.set(UserDataByToken+pa.getAccesstoken(),JSON.toJSONString(tabUserData11));
						jedis.expire(UserDataByToken+pa.getAccesstoken(),7200);
						jedis.quit();
					}
				}
			}
		}
	}


	public String getCode() {

		return (new Random().nextInt(888888) + 100000) + "";
	}

	@Autowired
	private ApiController apiController;

	public void dd(TabJiesuanLog tabJiesuanLog,Jedis jedis){
		//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
		TabAutoConfig tabAutoConfig = apiController.getAutoConfig();
		String jiesuankey = tabAutoConfig.getJiesuankey();
		if(StringUtils.isBlank(jiesuankey)){
			jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
		}else{
			String lastOne = tabJiesuanLog.getUserid().substring(tabJiesuanLog.getUserid().length()-1,tabJiesuanLog.getUserid().length());
			String[] keys = jiesuankey.split("&");
			for(int i =0; i<keys.length; i++){
				if(keys[i].indexOf(lastOne) > -1){
					if(i==0){
						jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
					}else{
						jedis.lpush(JIESUAN_KEY+i,JSON.toJSONString(tabJiesuanLog));
					}
					return;
				}
			}
		}
	}

	private String getOrder() {

		String val = "";
		Random random = new Random();

		// 参数length，表示生成几位随机数
		for (int i = 0; i < 8; i++) {

			String charOrNum = i > 6 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}

		return val;

	}

	@Autowired
	private TabJiesuanLogService tabJiesuanLogService;

	@PostMapping(value = "save2")
	@ResponseBody
	public String save2(@Validated TabUserData tabUserData) {

		String txpassword = tabUserData.getTxpassword();

		String rowid = tabUserData.getRowid();

		Double m = tabUserData.getCurrentmoney();

		tabUserData = tabUserDataService.get(rowid);
		//tabUserData.setVip(0L);

		//tabUserDataService.save(tabUserData);

		TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();

		String orderId = DateUtils.formatDate(new Date()).replaceAll("-", "") + getOrder();

		tabJiesuanLog.setUserid(tabUserData.getRowid());
		tabJiesuanLog.setCmd("System Gift");
		tabJiesuanLog.setType(txpassword + ":操作人:" + UserUtils.getLoginInfo().getId());
		tabJiesuanLog.setMsg(txpassword);
		tabJiesuanLog.setAmont(m * -1);
		tabJiesuanLog.setRowid(UUID.randomUUID().toString());
		tabJiesuanLog.setCreatetime(ApiController.getjndDate(new Date()));
		tabJiesuanLog.setCurrentmoney(tabUserData.getCurrentmoney());
		tabJiesuanLog.setIsNewRecord(true);
		tabJiesuanLog.setStatus1(orderId);

		tabJiesuanLog.setParentid1(tabUserData.getParentid());
		tabJiesuanLog.setParentid2(tabUserData.getParentid1());
		tabJiesuanLog.setParentid3(tabUserData.getParenti3());
		tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
		tabJiesuanLogService.save(tabJiesuanLog);
		List<TabVipConfig> tabVipConfigs = tabVipConfigService.findList(new TabVipConfig());
		Double mm = 10000.0;
		if(tabVipConfigs.size() > 0){
			mm = tabVipConfigs.get(0).getCurrentmoney();
		}

		if(tabUserData.getIstiyan().equals("1")){
			if (tabUserData.getCurrentmoney()+m >= mm) {
				tabUserData.setIstiyan("0");
				tabUserData.setValidate(DateUtils.addDays(new Date(), 99999));
				tabUserDataService.save(tabUserData);
				TabTyjJiesuan tabTyjJiesuan = new TabTyjJiesuan();
				tabTyjJiesuan.setUserid(tabUserData.getRowid());
				List<TabTyjJiesuan> tabTyjJiesuans = tabTyjJiesuanService.findList(tabTyjJiesuan);
				for (TabTyjJiesuan tabTyjJiesuan2 : tabTyjJiesuans) {
					tabTyjJiesuanService.delete(tabTyjJiesuan2);
				}
			}
		}

		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
		/*new Runnable() {

			@Override
			public void run() {
				Map<String, String> parame2 = new HashedMap();
				GoldpaysUtil.hanYuanUtils("https://e-creatoerzw.com/api/initHuancun?userid=" + uuid2, "",
						parame2);
			}
		}.run();*/

		//getuservip(tabJiesuanLog.getUserid());
		jedis.quit();
		return renderResult(Global.TRUE, text("下分成功"));
	}

	@Autowired
	private TabOrderDataService tabOrderDataService;

	@RequiresPermissions("tab:tabUserData:view")
	@RequestMapping(value = "form3")
	public String form3(TabUserData tabUserData, Model model) {

		Map<String, String> parame = new HashMap<>();
		parame.put("userid", tabUserData.getRowid());

		parame.put("times", DateUtils.formatDate(ApiController.getjndDate(new Date()), "yyyy-MM-dd"));
		Double jrgwje = tabOrderDataService.getGwje(parame);
		tabUserData.setJrgwje(jrgwje);

		Double yj = tabOrderDataService.getYouJin(parame);
		tabUserData.setJryjje(yj);

		parame.remove("times");

		Double zgwje = tabOrderDataService.getGwje(parame);
		tabUserData.setTotalGwje(zgwje);

		Double tdgwje = tabOrderDataService.getTotal(parame);

		tabUserData.setTdZgwje(tdgwje);

		parame.put("times", DateUtils.formatDate(ApiController.getjndDate(new Date()), "yyyy-MM-dd"));

		Double jrtdgwje = tabOrderDataService.getTotal(parame);

		tabUserData.setJrtdgwje(jrtdgwje);

		parame = new HashMap<>();

		parame.put("userid", tabUserData.getRowid());

		Double czzje = tabRechangeLogService.getSum(parame);
		tabUserData.setSumBalance(czzje);

		Double ztx = tabTixianLogService.getSum(parame);
		tabUserData.setTotalmoney(ztx);

		TabUserData entity1 = new TabUserData();
		entity1.setParentid(tabUserData.getRowid());
		entity1.setCreatetime_gte(DateUtils.getOfDayFirst(ApiController.getjndDate(new Date())));
		Long yiji = tabUserDataService.findCount(entity1);
		tabUserData.setYiji(yiji * 1D);

		entity1 = new TabUserData();
		entity1.setParentid1(tabUserData.getRowid());
		entity1.setCreatetime_gte(DateUtils.getOfDayFirst(ApiController.getjndDate(new Date())));
		Long erji = tabUserDataService.findCount(entity1);
		tabUserData.setErji(erji * 1D);

		// 获取下下级提现金额

		parame = new HashMap<>();
		parame.put("userId", tabUserData.getRowid());

		Double v = tabTixianLogService.getXiaJiaData(parame);
		tabUserData.setTxje(v);

		// 今日下级提现人数 总下级提现人数 今日下级提现金额 总下级提现金额
		parame.put("times", DateUtils.formatDate(ApiController.getjndDate(new Date()), "yyyy-MM-dd"));

		Long jrtxrs = tabTixianLogService.getTixianRenShu(parame);

		parame.remove("times");
		Long totalTxrs = tabTixianLogService.getTixianRenShu(parame);

		tabUserData.setJrtxrs(jrtxrs);
		tabUserData.setTotalTxrs(totalTxrs);
		parame.put("times", DateUtils.formatDate(ApiController.getjndDate(new Date()), "yyyy-MM-dd"));

		Double jrtdtxje = tabTixianLogService.getTixianMoney(parame);
		tabUserData.setJrtdtxje(jrtdtxje);

		parame.remove("times");

		Double totalTdtxje = tabTixianLogService.getTixianMoney(parame);
		tabUserData.setTotalTdtxje(totalTdtxje);

		parame.put("times", DateUtils.formatDate(ApiController.getjndDate(new Date()), "yyyy-MM-dd"));

		Long jrxjczrs = tabRechangeLogService.getXiajiRenshu(parame);

		tabUserData.setJrxjczrs(jrxjczrs);

		parame.remove("times");

		Long totalXjczrs = tabRechangeLogService.getXiajiRenshu(parame);

		tabUserData.setTotalXjczrs(totalXjczrs);

		parame.put("times", DateUtils.formatDate(ApiController.getjndDate(new Date()), "yyyy-MM-dd"));

		Double jrczje = tabRechangeLogService.getXiajiJine(parame);
		tabUserData.setJrczje(jrczje);

		parame.remove("times");
		Double totalCzje = tabRechangeLogService.getXiajiJine(parame);
		tabUserData.setTotalCzje(totalCzje);
		model.addAttribute("tabUserData", tabUserData);

		return "modules/tab/tabUserDataForm3";

	}

	@RequiresPermissions("tab:tabUserData:view")
	@RequestMapping(value = { "list2", "" })
	public String list2(TabUserData tabUserData, Model model) {
		model.addAttribute("tabUserData", tabUserData);
		return "modules/tab/tabUserDataList2";
	}

	@RequiresPermissions("tab:tabUserData:view")
	@RequestMapping(value = { "list3", "" })
	public String list3(TabUserData tabUserData, Model model) {
		model.addAttribute("tabUserData", tabUserData);
		return "modules/tab/tabUserDataList3";
	}

	@RequiresPermissions("tab:tabUserData:view")
	@RequestMapping(value = { "shouchong", "" })
	public String shouchong(TabUserData tabUserData, Model model) {
		model.addAttribute("tabUserData", tabUserData);
		return "modules/tab/shouchong";
	}

	@Autowired
	private TabTixianLogService tabTixianLogService;

	@Autowired
	private TabRechangeLogService tabRechangeLogService;

	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabUserData:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabUserData tabUserData) {
		tabUserDataService.delete(tabUserData);
		return renderResult(Global.TRUE, text("下分成功"));
	}

	private static String UserDataByToken = "UserDataByToken:";
	public List<TabUserData> getUserDataByToken(TabUserData tabUserData){
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String userDataByToken = jedis.get(UserDataByToken+tabUserData.getAccesstoken());
		List<TabUserData> list = new ArrayList<>();
		if(StringUtils.isBlank(userDataByToken)){
			list = tabUserDataService.findList(tabUserData);
			jedis.set(UserDataByToken+tabUserData.getAccesstoken(), JSON.toJSONString(list));
			jedis.expire(UserDataByToken+tabUserData.getAccesstoken(),7200);
		}else{
			list = JSON.parseObject(userDataByToken, new TypeReference<List<TabUserData>>(){});
		}
		jedis.quit();
		return list;
	}

	@RequestMapping(value = "gengxinTiYan2")
	@ResponseBody
	public String gengxinTiYan2(HttpServletRequest servletRequest) {
		String rowids = servletRequest.getParameter("rowids");
		String[] split = rowids.split(",");

		for (String string : split) {
			TabUserData tabUserData = tabUserDataService.get(string);
			tabUserData.setValidate(tabUserData.getCreatetime());
			tabUserData.setIstiyan("0");
			tabUserData.setVip(0L);
			tabUserDataService.save(tabUserData);
			Map<String, String> pp = new HashedMap();
			pp.put("userid", tabUserData.getRowid());
			tabTyjJiesuanService.deleteYc2(pp);
			//getuservip(tabUserData.getRowid());
			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");
			String  data  = jedis.get(TOKEN+tabUserData.getAccesstoken());
			org.json.JSONObject jsonObject = new org.json.JSONObject();
			if (StringUtils.isBlank(data) ||  data.equals("{}")) {
				List<TabUserData> tabUserDatas  = getUserDataByToken(tabUserData);
				tabUserData =  tabUserDatas.get(0);
				jsonObject  =  new org.json.JSONObject();
				jsonObject.put("rowid", tabUserData.getRowid());
				jsonObject.put("vip",tabUserData.getVip().toString());
			}else{
				jsonObject = new org.json.JSONObject(data);
				jsonObject.put("vip",tabUserData.getVip().toString());
			}
			jedis.set(TOKEN+tabUserData.getAccesstoken(), jsonObject.toString());
			jedis.expire(TOKEN+tabUserData.getAccesstoken(), 7200);


		}
		jedis.quit();
		return "取消体验客户成功";
	}

	@RequestMapping(value = "jinyongIp")
	@ResponseBody
	public String jinyongIp(HttpServletRequest servletRequest) {
		List<String> ips = new ArrayList<>();
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String jinyongIps = jedis.get("jinyongIps");
		if(StringUtils.isBlank(jinyongIps)){
			TabUserData tabUserData1 = new TabUserData();
			tabUserData1.setStatus2(3);
			List<TabUserData> userDataList = tabUserDataService.findList(tabUserData1);
			for(TabUserData bean : userDataList){
				ips.add(bean.getLastloginip());
			}
		}else{
			ips = JSON.parseObject(jinyongIps, new TypeReference<List<String>>(){});
		}

		String rowids = servletRequest.getParameter("rowids");
		String[] split = rowids.split(",");

		for (String string : split) {
			TabUserData tabUserData = tabUserDataService.get(string);
			tabUserData.setStatus2(3);
			tabUserDataService.save(tabUserData);
			if(StringUtils.isEmpty(tabUserData.getLastloginip())){
				ips.add(tabUserData.getRegisterip());
			}else{
				ips.add(tabUserData.getLastloginip());
			}
		}
		jedis.set("jinyongIps",JSON.toJSONString(ips));
		jedis.quit();
		return "禁用ip成功";
	}

	@RequestMapping(value = "qiyongIp")
	@ResponseBody
	public String qiyongIp(HttpServletRequest servletRequest) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		List<String> ips = new ArrayList<>();
		String rowids = servletRequest.getParameter("rowids");
		String[] split = rowids.split(",");
		for (String string : split) {
			TabUserData tabUserData = tabUserDataService.get(string);
			tabUserData.setStatus2(2);
			tabUserDataService.save(tabUserData);
		}
		TabUserData tabUserData1 = new TabUserData();
		tabUserData1.setStatus2(3);
		List<TabUserData> userDataList = tabUserDataService.findList(tabUserData1);
		for(TabUserData bean : userDataList){
			if(StringUtils.isEmpty(bean.getLastloginip())){
				ips.add(bean.getRegisterip());
			}else{
				ips.add(bean.getLastloginip());
			}

		}

		jedis.set("jinyongIps",JSON.toJSONString(ips));
		jedis.quit();
		return "启用ip成功";
	}

	@RequestMapping(value = "suoding")
	@ResponseBody
	public String suoding(HttpServletRequest servletRequest) {
		String rowids = servletRequest.getParameter("rowids");
		String[] split = rowids.split(",");
		for (String string : split) {
			TabUserData tabUserData = tabUserDataService.get(string);
			tabUserData.setStatus1("3");
			tabUserDataService.save(tabUserData);
		}
		return "锁定账号成功";
	}

	@RequestMapping(value = "jiesuo")
	@ResponseBody
	public String jiesuo(HttpServletRequest servletRequest) {
		String rowids = servletRequest.getParameter("rowids");
		String[] split = rowids.split(",");
		for (String string : split) {
			TabUserData tabUserData = tabUserDataService.get(string);
			tabUserData.setStatus1("2");
			tabUserDataService.save(tabUserData);
		}
		return "锁定账号成功";
	}

	@RequestMapping(value = "suodingcode")
	@ResponseBody
	public String suodingcode(HttpServletRequest servletRequest) {
		String rowids = servletRequest.getParameter("rowids");
		String[] split = rowids.split(",");
		for (String string : split) {
			TabUserData tabUserData = tabUserDataService.get(string);
			tabUserData.setStatus3("3");
			tabUserDataService.save(tabUserData);
		}
		return "锁定账号成功";
	}

	@RequestMapping(value = "jiesuocode")
	@ResponseBody
	public String jiesuocode(HttpServletRequest servletRequest) {
		String rowids = servletRequest.getParameter("rowids");
		String[] split = rowids.split(",");
		for (String string : split) {
			TabUserData tabUserData = tabUserDataService.get(string);
			tabUserData.setStatus3("2");
			tabUserDataService.save(tabUserData);
		}
		return "锁定账号成功";
	}

	@RequestMapping(value = "jiancha")
	@ResponseBody
	public Map<String,Object> jiancha(HttpServletRequest servletRequest) {
		String rowid = servletRequest.getParameter("rowid");
		TabUserData tabUserData = tabUserDataService.get(rowid);
		if(tabUserData != null){
			map.put("msg","已注册在其他团队");
		}else{
			map.put("msg","未注册");
		}
		return map;
	}

	private Jedis jedis =  new Jedis("41.72.149.115", 6379);

	@RequestMapping(value = "gengxinTiYan")
	@ResponseBody
	public String gengxinTiYan(HttpServletRequest servletRequest) {
		String id = UserUtils.getLoginInfo().getId();
		User user = userService.get(id);
		String acc = user.getLoginCode();
		String rowids = servletRequest.getParameter("rowids");
		TabAutoConfig obj = new TabAutoConfig();
		obj.setIsqidong("1");
		List<TabAutoConfig> tabAutoConfigs = tabAutoConfigService.findList(obj);

		if (tabAutoConfigs.size() > 0) {
			obj = tabAutoConfigs.get(0);
		}

		String[] split = rowids.split(",");

		for (String string : split) {

			TabTyjJiesuan bean = new TabTyjJiesuan();
			bean.setUserid(string);
			List<TabTyjJiesuan> data = tabTyjJiesuanService.findList(bean);

			if(data != null && data.size()>0){
				continue;
			}

			TabUserData tabUserData = tabUserDataService.get(string);

			tabUserData.setVip(1L);
			tabUserData.setIstiyan("1");

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getjndDate(new Date()));
			calendar.add(Calendar.DAY_OF_MONTH, obj.getTianshu());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			try {
				tabUserData.setValidate(simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime())));
			}catch (Exception e){

			}
			tabUserData.setTyj(obj.getTyj());
			tabUserData.setTycs(1L);
			tabUserDataService.save(tabUserData);

			List<TabUserData> list = new ArrayList<>();
			list.add(tabUserData);
			jedis.auth("hask071bend");
			jedis.set("UserDataByToken:"+tabUserData.getAccesstoken(), JSON.toJSONString(list));
			jedis.expire("UserDataByToken:"+tabUserData.getAccesstoken(),7200);

			TabTyjJiesuan obj22 = new TabTyjJiesuan();
			obj22.setRowid(UUID.randomUUID().toString());
			obj22.setUserid(string);
			obj22.setAmont(obj.getTyj());
			obj22.setCreatetime(ApiController.getjndDate(new Date()));
			obj22.setIsNewRecord(true);
			obj22.setType(acc);
			tabTyjJiesuanService.save(obj22);
			jedis.auth("hask071bend");
			org.json.JSONObject jsonObject = new org.json.JSONObject();
			String data1 = jedis.get(TOKEN+tabUserData.getAccesstoken());
			if (StringUtils.isBlank(data1) ||  data1.equals("{}")) {
				List<TabUserData> tabUserDatas  = getUserDataByToken(tabUserData);
				tabUserData =  tabUserDatas.get(0);
				jsonObject  =  new org.json.JSONObject();
				jsonObject.put("rowid", tabUserData.getRowid());
				jsonObject.put("vip",tabUserData.getVip().toString());
			}else{
				jsonObject = new org.json.JSONObject(data1);
				jsonObject.put("vip",tabUserData.getVip().toString());
			}
			jedis.set(TOKEN+tabUserData.getAccesstoken(), jsonObject.toString());
			jedis.expire(TOKEN+tabUserData.getAccesstoken(), 7200);

		}

		// 设置体验会员

		return renderResult(Global.TRUE, text("下分成功"));
	}

	public static Date getjndDate(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -6);

		return calendar.getTime();
	}

	@Autowired
	private TabAutoConfigService tabAutoConfigService;
	Map<String, Object> map = new HashMap<String, Object>();

	@RequestMapping(value = { "/updatePassword", "" })
	@ResponseBody
	public Map<String, Object> updatePassword(HttpServletRequest request) {

		String rowids = request.getParameter("rowids");

		TabUserData tabUserData = tabUserDataService.get(rowids);

		tabUserData.setPassword(new ApiController().getCode());

		tabUserDataService.save(tabUserData);
		List<TabUserData> list = new ArrayList<>();
		list.add(tabUserData);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		jedis.set(UserDataByToken+tabUserData.getAccesstoken(), JSON.toJSONString(list));
		jedis.expire(UserDataByToken+tabUserData.getAccesstoken(), 7200);
		map.put("data", tabUserData);
		jedis.quit();
		return map;
	}

	//

	@RequestMapping(value = { "/updatePassword2", "" })
	@ResponseBody
	public Map<String, Object> updatePassword2(HttpServletRequest request) {

		String rowids = request.getParameter("rowids");

		TabUserData tabUserData = tabUserDataService.get(rowids);

		tabUserData.setTxpassword(new ApiController().getCode());

		tabUserDataService.save(tabUserData);
		List<TabUserData> list = new ArrayList<>();
		list.add(tabUserData);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		jedis.set(UserDataByToken+tabUserData.getAccesstoken(), JSON.toJSONString(list));
		jedis.expire(UserDataByToken+tabUserData.getAccesstoken(), 7200);
		map.put("data", tabUserData);
		jedis.quit();
		return map;
	}

	// fengSuoDanGe

	@RequestMapping(value = { "/fengSuoDanGe", "" })
	@ResponseBody
	public Map<String, Object> fengSuoDanGe(HttpServletRequest request) {

		String rowids = request.getParameter("rowids");

		String[] split = rowids.split(",");

		for (String string : split) {

			TabUserData tabUserData = tabUserDataService.get(string);

			tabUserData.setStatus1("3");

			tabUserDataService.save(tabUserData);

		}

		return map;
	}

	@Autowired
	private TabTyjJiesuanService tabTyjJiesuanService;
	// jieSuoDanGe

	@RequestMapping(value = { "/jieSuoDanGe", "" })
	@ResponseBody
	public Map<String, Object> jieSuoDanGe(HttpServletRequest request) {

		String rowids = request.getParameter("rowids");

		String[] split = rowids.split(",");

		for (String string : split) {

			TabUserData tabUserData = tabUserDataService.get(string);

			tabUserData.setStatus1("2");

			tabUserDataService.save(tabUserData);

		}

		return map;
	}

	@RequestMapping(value = { "/fengSuoQuanBu", "" })
	@ResponseBody
	public Map<String, Object> fengSuoQuanBu(HttpServletRequest request) {

		String rowids = request.getParameter("rowids");

		String[] split = rowids.split(",");

		for (String string : split) {

			TabUserData tabUserData = tabUserDataService.get(string);

			tabUserData.setStatus1("3");

			tabUserDataService.save(tabUserData);
			String xjs = tabUserData.getShangji2s();

			if (!StringUtils.isBlank(xjs)) {

				String[] s = xjs.split(",");

				tabUserDataService.updateXiaJi(s);
			}

		}

		return map;
	}

	@RequestMapping(value = { "/jieSuoQuanBu", "" })
	@ResponseBody
	public Map<String, Object> jieSuoQuanBu(HttpServletRequest request) {

		String rowids = request.getParameter("rowids");

		String[] split = rowids.split(",");

		for (String string : split) {

			TabUserData tabUserData = tabUserDataService.get(string);

			tabUserData.setStatus1("2");

			tabUserDataService.save(tabUserData);
			String xjs = tabUserData.getShangji2s();

			if (!StringUtils.isBlank(xjs)) {

				String[] s = xjs.split(",");

				tabUserDataService.updateXiaJi2(s);
			}

		}

		return map;
	}

	public String getUserVip(String userId) {
		TabUserData tabUserData = tabUserDataService.get(userId);

		if (!StringUtils.isBlank(tabUserData.getIstiyan()) && tabUserData.getIstiyan().equals("1")) {
			tabUserData.setVip(1L);
			tabUserDataService.save(tabUserData);

			return "1";
		} else {
			Long vip = 0L;
			tabUserData.setVip(0L);
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
				vip = (long)(new_one_level > new_two_level ? new_one_level : new_two_level);
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
			if (tabUserData.getVip() > 0) {
				// 查找未完成的体验单

				TabOrderData obj = new TabOrderData();

				obj.setUserid(userId);

				obj.setStatus1("1");
				obj.setIstiyan("1");
				List<TabOrderData> tabOrderDatas = tabOrderDataService.findList(obj);

				if (tabOrderDatas.size() > 0) {

					String orderId = tabOrderDatas.get(0).getOrderid();

					obj = new TabOrderData();
					obj.setOrderid(orderId);

					tabOrderDatas = tabOrderDataService.findList(obj);

					for (TabOrderData tabOrderData : tabOrderDatas) {

						tabOrderDataService.delete(tabOrderData);

					}

					TabOrders ooooo = new TabOrders();
					ooooo.setOrderid(orderId);
					List<TabOrders> tabOrders = tabOrdersService.findList(ooooo);

					for (TabOrders tabOrders2 : tabOrders) {
						tabOrdersService.delete(tabOrders2);
					}

				}

			}

		}
		jedis.quit();
		return "0";
	}

	@RequestMapping(value = { "/getUserVipCount", "" })
	@ResponseBody
	public Map<String, Object> getUserVipCount(HttpServletRequest request) {

		String  rowid =   request.getParameter("rowid");

		Double tdcz = tabRechangeLogService.getTdcz(rowid);
		map.put("tdcz", tdcz);

		Double tdtx = tabTixianLogService.getTdtx(rowid);
		map.put("tdtx", tdtx);

		Map<String,Object> param = new HashMap<>();
		param.put("rowid",rowid);
		param.put("vip",1);
		Long vip1 = tabUserDataService.getVipRenshu(param);
		map.put("vip1", vip1);

		param.put("vip",2);
		Long vip2 = tabUserDataService.getVipRenshu(param);
		map.put("vip2", vip2);

		map.put("cc", 0);
		return  map;
	}



	@Autowired
	private TabVipConfigService tabVipConfigService;

	@Autowired
	private TabOrdersService tabOrdersService;

}