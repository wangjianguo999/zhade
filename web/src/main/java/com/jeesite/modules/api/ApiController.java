package com.jeesite.modules.api;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.common.utils.FileUtil;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.service.DictDataService;
import com.jeesite.modules.tab.entity.*;
import com.jeesite.modules.tab.service.*;
import io.swagger.models.auth.In;
import javafx.scene.control.Tab;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.startup.UserDatabase;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tab.web.GoldpaysUtil;
import com.jeesite.modules.tab.web.HttpServletRequestUtils;
import com.jeesite.modules.view.service.ViewShouchongService;

import net.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForAbstractMethod;
import redis.clients.jedis.Jedis;

@Controller
@Scope("prototype")
@RequestMapping(value = "")
@Slf4j
@CrossOrigin
public class ApiController extends BaseController {

	@Autowired
	private TabRechangeLogService tabRechangeLogService;

	Map<String, Object> map = new HashMap<String, Object>();

	@Autowired
	private TabAutoConfigService tabAutoConfigService;

	@Autowired
	private TabSiderDataService tabSiderDataService;

	@Autowired
	private TabUserDataService tabUserDataService;

	@Autowired
	private TabValidCodeService tabValidCodeService;

	@Autowired
	private TabShouruLogService tabShouruLogService;

	@Autowired
	private TabOrderDataService tabOrderDataService;

	@Autowired
	private TabProductDataService tabProductDataService;

	@Autowired
	private TabTixianLogService tabTixianLogService;

	@Autowired
	private TabFacebookService tabFacebookService;

	@Autowired
	private TabUserAddressService tabUserAddressService;

	@Autowired
	private TabUserBankService tabUserBankService;
	@Autowired
	private TabVipConfigService tabVipConfigService;

	@Autowired
	private TabContaceServiceService tabContaceServiceService;

	@Autowired
	private TabLicaiDataService tabLicaiDataService;

	@Autowired
	private TabSysSkfsService tabSysSkfsService;

	@Autowired
	private TabHuilvConfigService tabHuilvConfigService;

	@Autowired
	private ViewShouchongService viewShouchongService;

	@Autowired
	private UserService userService;

	@Autowired
	private TabTongdaoService tabTongdaoService;
	@Autowired
	private TabJiesuanLogService tabJiesuanLogService;

	@Autowired
	private TabTaskjJobService tabTaskjJobService;

	@Autowired
	private TabYuangongDataService tabYuangongDataService;

	@Autowired
	private TabXiadanSuccessTongjiService tabXiadanSuccessTongjiService;

	@Autowired
	private TabRechangeLogService tabRechargeSuccessTongjiService;

	@Autowired
	private TabTixianLogService tabTixianSuccessTongjiService;

	@Autowired
	private TabYaoqingJiangliLogService tabYaoqingJiangliLogService;

	@Autowired
	private TabYaoqingJiangliService tabYaoqingJiangliService;

	@Autowired
	private TabRechargeJiangliService tabRechargeJiangliService;

	@Autowired
	private TabRechargeJiangliLogService tabRechargeJiangliLogService;

	@Autowired
	private TabOrdersService tabOrdersService;

	@Autowired
	private TabActiveContentService tabActiveContentService;
	@Autowired
	private TabActiveImgsService tabActiveImgsService;

	@Autowired
	private TabTongjiDaysService tabTongjiDaysService;

	@Autowired
	private TabGonggaoService tabGonggaoService;

	@Autowired
	private TabTyjJiesuanService tabTyjJiesuanService;

	@Autowired
	private TabYaoqingJiangliHuodongService tabYaoqingJiangliHuodongService;

	@Autowired
	private TabYaoqingJiangliHuodongLogService tabYaoqingJiangliHuodongLogService;

	@Autowired
	private TabTempsService tabTempsService;

	@Autowired
	private TabFenxiaoConfigService tabFenxiaoConfigService;

	@Autowired
	private TabOrdersLogService tabOrdersLogService;
	//
	@Autowired
	private TabYaoqingPeizhiService tabYaoqingPeizhiService;

	@Autowired
	private TabOrderDataLogService tabOrderDataLogService;

	@Autowired
	private TabLicaiListService tabLicaiListService;

	@Autowired
	private DictDataService dictDataService;

	private static String UserDataByToken = "UserDataByToken:";
	private static String AutoConfig = "AutoConfig";
	private static String VipConfig = "VipConfig:";
	private static  String JIESUAN_KEY = "JIESUAN:queue";
	private static String JIESUAN_KEY1 = "JIESUAN:queue1";
	private static String JIESUAN_KEY2 = "JIESUAN:queue2";
	private static String JIESUAN_KEY3 = "JIESUAN:queue3";
	private static String JIESUAN_KEY4 = "JIESUAN:queue4";
	private static String TOKEN = "TOKEN:";
	private static String isBuyProDuct = "IsBuyProDuct:";
	private static String YUE = "YUE:";
	private static String ProDuct = "ProDuctDetail:";
	class Banks {
		private String code;
		private List<TabUserBank> tabUserBanks;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public List<TabUserBank> getTabUserBanks() {
			return tabUserBanks;
		}

		public void setTabUserBanks(List<TabUserBank> tabUserBanks) {
			this.tabUserBanks = tabUserBanks;
		}
	}



	//private Jedis jedis =  new Jedis("41.72.149.115", 6379);

	public List<TabUserData> getUserDataByToken(TabUserData tabUserData){
		if(StringUtils.isNotBlank(tabUserData.getAccesstoken())){
			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");
			String userDataByToken = jedis.get(UserDataByToken+tabUserData.getAccesstoken());
			List<TabUserData> list = new ArrayList<>();
			if(StringUtils.isBlank(userDataByToken)){
				list = tabUserDataService.findList(tabUserData);
				list.get(0).setLastlogintime(getjndDate(new Date()));
				tabUserDataService.save(list.get(0));
				jedis.set(UserDataByToken+tabUserData.getAccesstoken(), JSON.toJSONString(list));
				jedis.expire(UserDataByToken+tabUserData.getAccesstoken(),7200);
			}else{
				list = JSON.parseObject(userDataByToken, new TypeReference<List<TabUserData>>(){});
			}
			jedis.quit();
			return list;
		}
		return null;
	}

	public TabAutoConfig getAutoConfig(){
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String autoConfigStr = jedis.get(AutoConfig);
		TabAutoConfig tabAutoConfig = new TabAutoConfig();
		if(StringUtils.isBlank(autoConfigStr)){
			List<TabAutoConfig> list = tabAutoConfigService.findList(new TabAutoConfig());
			if(list.size()>0){
				tabAutoConfig = list.get(0);
				jedis.set(AutoConfig, JSON.toJSONString(list.get(0)));
			}
		}else{
			tabAutoConfig = JSON.parseObject(autoConfigStr, new TypeReference<TabAutoConfig>(){});
		}
		jedis.quit();
		return tabAutoConfig;
	}

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

	public List<TabVipConfig> getVipConfigList1(TabVipConfig tabVipConfig,Jedis jedis1){
		List<TabVipConfig> list = new ArrayList<>();
		if(StringUtils.isBlank(tabVipConfig.getVip())){
			tabVipConfig.setVip("list");
		}
		String vipConfigList = jedis1.get(VipConfig+tabVipConfig.getVip());
		if(StringUtils.isBlank(vipConfigList)){
			if("list".equals(tabVipConfig.getVip())){
				list = tabVipConfigService.findList(new TabVipConfig());
			}else{
				list = tabVipConfigService.findList(tabVipConfig);
			}
			if(list == null || list.size() == 0){
				return null;
			}
			jedis1.set(VipConfig+tabVipConfig.getVip(), JSON.toJSONString(list));
			jedis1.expire(VipConfig+tabVipConfig.getVip(),7200);
		}else{
			list = JSON.parseObject(vipConfigList, new TypeReference<List<TabVipConfig>>(){});
		}
		return list;
	}

	@RequestMapping(value = "listDataTabRechangeLog")
	@ResponseBody
	public Map<String, Object> listDataTabRechangeLog(@RequestBody Map<String, Object> request) {
		String token = request.get("token").toString();
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			TabRechangeLog tabRechangeLog = new TabRechangeLog();
			tabRechangeLog.setUserid(list.get(0).getRowid());
			List<TabRechangeLog> tabRechangeLogs = tabRechangeLogService.findList(tabRechangeLog);
			map.put("data", tabRechangeLogs);
		}

		return map;
	}



	@RequestMapping(value = "listDataWithDraw")
	@ResponseBody
	public Map<String, Object> listDataWithDraw(@RequestBody Map<String, Object> request) {
		String token = request.get("token").toString();
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			TabTixianLog tabRechangeLog = new TabTixianLog();
			tabRechangeLog.setUserid(list.get(0).getRowid());
			List<TabTixianLog> tabRechangeLogs = tabTixianLogService.findList(tabRechangeLog);
			map.put("data", tabRechangeLogs);
		}
		return map;
	}

	@RequestMapping(value = "rechangeData")
	@ResponseBody
	public Map<String, Object> rechangeData(@RequestBody Map request,HttpServletRequest httpServletRequest) {
		Object object = request.get("orderType");
		Object tdObject = request.get("td");
		Integer orderType = 0;
		if(object != null){
			orderType = Integer.parseInt(object.toString());
		}
		String amont = request.get("m").toString();
		if(orderType == 1 && Double.parseDouble(amont)>100){
			map.put("code", 1);
			map.put("msg", "Sorry, you can only help the subordinate recharge to a maximum of $100.");
			return map;
		}

		//List<TabHuilvConfig> tabHuilvConfigs = tabHuilvConfigService.findList(new TabHuilvConfig());
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		TabAutoConfig tabAutoConfig = new TabAutoConfig();
		tabAutoConfig.setIsqidong("1");

		Double mj = 0d;
		Double fb = 0D;


		//if (tabAutoConfigs.size() > 0) {
		String autoConfigstr = jedis.get("AutoConfig");
		if(StringUtils.isBlank(autoConfigstr)){
			List<TabAutoConfig> tabAutoConfigs = tabAutoConfigService.findList(tabAutoConfig);
			tabAutoConfig = tabAutoConfigs.get(0);
			jedis.set("AutoConfig",JSON.toJSONString(tabAutoConfig));
		}else{
			tabAutoConfig = JSON.parseObject(autoConfigstr,TabAutoConfig.class);
		}
			String token = request.get("token").toString();
			TabUserData tabUserData = new TabUserData();
			tabUserData.setAccesstoken(token);
			List<TabUserData> list = getUserDataByToken(tabUserData);
			if (list == null || list.size() == 0) {
				map.put("code", 1);
			} else {
				String jinyongIps = jedis.get("jinyongIps");
				List<String> ips = new ArrayList<>();
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
				for(String ip : ips){
					if(getIp(httpServletRequest).equals(ip)){
						map.put("code", 1);
						map.put("msg", "The login IP address has been banned from login!");
						return map;
					}
				}

				tabUserData = list.get(0);

				map.put("code", 0);
				TabRechangeLog tabRechangeLog = new TabRechangeLog();
				tabRechangeLog.setPaytype(tdObject.toString());
				tabRechangeLog.setIsrefund(0);
				mj = Double.valueOf(amont);

				if (mj < tabAutoConfig.getCzzdje()) {
					map.put("code", "1");
					map.put("msg", "The minimum amount is " + tabAutoConfig.getCzzdje() + " USh");
					return map;
				}
				if(orderType == 1){
					Object userid11= request.get("userid1");
					if(userid11 == null){
						map.put("code",1);
						map.put("msg","The lower-level account cannot be empty");
						return map;
					}
					String userid1 = userid11.toString();
					TabUserData tabUserData1 = tabUserDataService.get(userid1);
					if(tabUserData1 == null){
						map.put("code",1);
						map.put("msg","You can only recharge for your Level1-Level3 subordinates");
						return map;
					}
					if(!tabUserData.getRowid().equals(tabUserData1.getParentid()) && !tabUserData.getRowid().equals(tabUserData1.getParentid1()) && !tabUserData.getRowid().equals(tabUserData1.getParenti3())){
						map.put("code",1);
						map.put("msg","You can only recharge for your Level1-Level3 subordinates");
						return map;
					}
					tabRechangeLog.setOrderType(1);
					tabRechangeLog.setUserid(tabUserData1.getRowid());
					tabRechangeLog.setUserid1(tabUserData.getRowid());
					tabRechangeLog.setCreatetime(getjndDate(new Date()));
					try {
						SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
						tabRechangeLog.setRefundtime(format1.parse(format1.format(getjndDate(new Date()))));
					}catch (Exception e){

					}
					tabRechangeLog.setIsNewRecord(true);
					tabRechangeLog.setOrderid(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + getCode());
					tabRechangeLog.setHzhb(mj);
					tabRechangeLog.setParentid1(tabUserData1.getParentid());
					tabRechangeLog.setParentid2(tabUserData1.getParentid1());
					tabRechangeLog.setParentid3(tabUserData1.getParenti3());
					tabRechangeLog.setShangjilink(tabUserData1.getShangjilink());
					tabRechangeLog.setYgzh(tabUserData1.getYgzh());
					tabRechangeLog.setYgzh2(tabUserData1.getYgzh2());
				}else{
					tabRechangeLog.setOrderType(0);
					tabRechangeLog.setUserid(tabUserData.getRowid());
					tabRechangeLog.setCreatetime(getjndDate(new Date()));
					tabRechangeLog.setIsNewRecord(true);
					tabRechangeLog.setOrderid(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + getCode());
					tabRechangeLog.setHzhb(mj);
					tabRechangeLog.setParentid1(tabUserData.getParentid());
					tabRechangeLog.setParentid2(tabUserData.getParentid1());
					tabRechangeLog.setParentid3(tabUserData.getParenti3());
					tabRechangeLog.setShangjilink(tabUserData.getShangjilink());
					tabRechangeLog.setYgzh(tabUserData.getYgzh());
					tabRechangeLog.setYgzh2(tabUserData.getYgzh2());
				}
				Map<String,Object> re = new HashMap<>();

				TabTongdao tabTongdao = new TabTongdao();
				if("1".equals(tabAutoConfig.getShijianlunhuan())){
					String tongdaoStr = jedis.get("Tongdao"+tdObject.toString());
					if(StringUtils.isBlank(tongdaoStr)){
						String tongdaosStr = jedis.rpop("Tongdaos"+tdObject.toString());
						if(tongdaosStr == null || tongdaosStr.equals("OK") || "null".equals(tongdaosStr)){
							List<TabTongdao> tongdaos = tabTongdaoService.getShijianlunhuan(tdObject.toString());
							for(TabTongdao bean : tongdaos){
								if(bean.getType().equals("MTN")){
									jedis.lpush("Tongdaos"+bean.getType(),JSON.toJSONString(bean));
								}else{
									jedis.lpush("Tongdaos"+bean.getType(),JSON.toJSONString(bean));
								}
							}
							tongdaosStr = jedis.rpop("Tongdaos"+tdObject.toString());
						}
						tabTongdao = JSON.parseObject(tongdaosStr,TabTongdao.class);
						jedis.set("Tongdao"+tdObject.toString(),JSON.toJSONString(tabTongdao));
						if(tabAutoConfig.getMinutes() > 0){
							jedis.expire("Tongdao"+tdObject.toString(),tabAutoConfig.getMinutes()*60);
						}else{
							jedis.expire("Tongdao"+tdObject.toString(),600);
						}
					}else{
						tabTongdao = JSON.parseObject(tongdaoStr,TabTongdao.class);
					}
				}else{
					tabTongdao.setDaishou("2");
					tabTongdao.setStatus1(1);
					tabTongdao.setType(tdObject.toString());
					List<TabTongdao> tongdaos = new ArrayList<>();
					if(mj>=tabAutoConfig.getEdu()){
						tabTongdao.setEdu("大额");
						tongdaos = tabTongdaoService.findList(tabTongdao);
						if(tongdaos.size() == 0){
							tabTongdao.setEdu("小额");
							tongdaos = tabTongdaoService.findList(tabTongdao);
						}
					}else{
						tabTongdao.setEdu("小额");
						tongdaos = tabTongdaoService.findList(tabTongdao);
					}
					tabTongdao = tongdaos.get(0);

				}
				re.put("name",tabTongdao.getTongdaoName());
				re.put("type",tabTongdao.getType());
				re.put("number",tabTongdao.getNumber());
				re.put("rechangeId",tabAutoConfig.getRechangeId());
				tabRechangeLog.setType(tabTongdao.getNumber());
				tabRechangeLog.setStatus1("1");
				tabRechangeLog.setBeizhu("支付rechangeData"+getIp(httpServletRequest));
				tabRechangeLogService.save(tabRechangeLog);
				re.put("orderId",tabRechangeLog.getOrderid());
				re.put("amount",tabRechangeLog.getHzhb());
				Map<String, String> parame = new HashMap<>();
				parame.put("orderId", tabRechangeLog.getOrderid());
				parame.put("orderMoney", String.valueOf(tabRechangeLog.getHzhb()));
				parame.put("orderMoney1", String.valueOf(tabRechangeLog.getHzhb()));
				parame.put("userId", tabRechangeLog.getUserid());
				parame.put("notifyUrl", "http://41.72.149.115:8081/api/zfCallData");
				parame.put("type", tabRechangeLog.getPaytype());
				//String tempSign = GoldpaysUtil.getSign2(parame, appset);
				//parame.put("sign", tempSign);
				//System.out.println(" sign " + tempSign);
				String s = GoldpaysUtil.hanYuanUtils("http://41.72.149.115:8081/metaPay/api/createLouisDaiShou", "", parame);

				map.put("code",0);
				map.put("data",re);
				map.put("msg","");
			}
		//}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = "getRechangeLog")
	@ResponseBody
	public Map<String,Object> getRechangeLog(@RequestBody JSONObject jsonObject){
		String token = jsonObject.getString("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			String userid = list.get(0).getRowid();
			TabRechangeLog tabRechangeLog = new TabRechangeLog();
			tabRechangeLog.setUserid1(userid);
			tabRechangeLog.setStatus1("2");
			tabRechangeLog.setOrderBy(" createtime desc ");
			List<TabRechangeLog> rechangeLogList = tabRechangeLogService.findList(tabRechangeLog);
			for(TabRechangeLog rechangeLog : rechangeLogList){
				rechangeLog.setBeizhu("");
				rechangeLog.setShangjilink("");
				if(StringUtils.isNotBlank(rechangeLog.getParentid1()) && rechangeLog.getParentid1().equals(userid)){
					rechangeLog.setParentid1("");
					rechangeLog.setIsrefund(1);
					continue;
				}
				if(StringUtils.isNotBlank(rechangeLog.getParentid2()) && rechangeLog.getParentid2().equals(userid)){
					rechangeLog.setParentid2("");
					rechangeLog.setIsrefund(2);
					continue;
				}
				if(StringUtils.isNotBlank(rechangeLog.getParentid3()) && rechangeLog.getParentid3().equals(userid)){
					rechangeLog.setParentid3("");
					rechangeLog.setIsrefund(3);
					continue;
				}
			}
			map.put("code", 0);
			map.put("data", rechangeLogList);
		}
		return map;
	}

	@RequestMapping(value = "changeDaiShouOrder")
	@ResponseBody
	public Map<String, Object> changeDaiShouOrder(@RequestBody JSONObject jsonObject,HttpServletRequest request){
		String account = jsonObject.getString("account");
		String rechangeId = jsonObject.getString("rechangeId").replace(" ","");
		String orderid = jsonObject.getString("orderId");
		System.out.println("用户提交支付凭证orderId："+orderid+ " account："+account+" rechangeId："+rechangeId);

		TabRechangeLog bean = new TabRechangeLog();
		bean.setPingzheng(StringUtils.isBlank(account) ? rechangeId : account);
		List<TabRechangeLog> beans = tabRechangeLogService.findList(bean);
		if(beans.size() > 0){
			map.put("code",1);
			map.put("msg","Your approval code has been uploaded, please wait or contact online customer service for assistance!");
			return map;
		}
		TabRechangeLog tabRechangeLog = new TabRechangeLog();
		tabRechangeLog.setOrderid(orderid);
		List<TabRechangeLog> orders = tabRechangeLogService.findList(tabRechangeLog);
		if(orders.size() == 0){
			map.put("code",1);
			map.put("msg","Order does not exist, please try again");
			return map;
		}
		tabRechangeLog = orders.get(0);
		if(tabRechangeLog.getStatus1().equals("2")){
			map.put("code",1);
			map.put("msg","Submitted successfully");
			return map;
		}

		Map<String,String> parame1 = new HashMap<>();
		parame1.put("account",account);
		parame1.put("code",rechangeId);
		parame1.put("orderId",orderid);
		parame1.put("paytype",tabRechangeLog.getPaytype());
		tabRechangeLog.setPingzheng(StringUtils.isBlank(account) ? rechangeId : account);
		tabRechangeLogService.save(tabRechangeLog);
		String s = GoldpaysUtil.hanYuanUtils("http://41.72.149.115:8081/metaPay/api/newchangeDaiShouOrder", "", parame1);
		if(!"SUCCESS".equals(s)){
			map.put("code",1);
			map.put("msg","Submitted successfully");
			return map;
		}
		Jedis jedis =  new Jedis("41.72.149.115", 6379);

		jedis.auth("hask071bend");
		if (tabRechangeLog.getStatus1().equals("1")) {
			tabRechangeLog.setStatus1("2");
			tabRechangeLog.setBeizhu("支付成功zfCallData");
			tabRechangeLog.setUpdatetime(getjndDate(new Date()));
			try {
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				tabRechangeLog.setRefundtime(format1.parse(format1.format(getjndDate(new Date()))));
			}catch (Exception e){

			}
			System.out.println("用户自动充值数据zfCallData："+JSONObject.toJSONString(tabRechangeLog));
			TabUserData tabUserData = tabUserDataService.get(tabRechangeLog.getUserid());


			TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
			tabJiesuanLog.setUserid(tabRechangeLog.getUserid());
			tabJiesuanLog.setCmd("Recharger");
			tabJiesuanLog.setType("充值成功");
			if(tabRechangeLog.getOrderType() == 0){
				tabJiesuanLog.setMsg("Recharge successfully");
			}else{
				tabJiesuanLog.setMsg("Superior Recharge");
			}

			tabJiesuanLog.setAmont(tabRechangeLog.getHzhb());
			tabJiesuanLog.setRowid(tabRechangeLog.getRowid());
			tabJiesuanLog.setCreatetime(getjndDate(new Date()));
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
			tabRechangeLogService.save(tabRechangeLog);
			tabJiesuanLogService.save(tabJiesuanLog);

			if(tabUserData.getIstiyan().equals("1")){
				tabUserData.setIstiyan("0");
				tabUserData.setTyj(0D);
				tabUserData.setValidate(DateUtils.addDays(new Date(), 99999));
				tabUserDataService.save(tabUserData);
				List<TabUserData> list = new ArrayList<>();
				list.add(tabUserData);
				jedis.set(UserDataByToken+tabUserData.getAccesstoken(), JSON.toJSONString(list));
				jedis.expire(UserDataByToken+tabUserData.getAccesstoken(), 7200);

				tabTyjJiesuanService.deleteByUserId(tabUserData.getUserid());
			}
			try {
				Thread.sleep(500);
				dd(tabJiesuanLog,jedis);
			}catch (Exception e){

			}

			map.put("code",0);
			map.put("msg","Submitted successfully");
		}else{
			map.put("code",1);
			map.put("msg","Submitted successfully");
		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = "changeDaiShouOrder1")
	@ResponseBody
	public Map<String, Object> changeDaiShouOrder1(HttpServletRequest request){
		String account = request.getParameter("account");
		String rechangeId = request.getParameter("rechangeId").replace(" ","");
		String orderid = request.getParameter("orderId");
		System.out.println("后台提交支付凭证orderId："+orderid+ " account："+account+" rechangeId："+rechangeId);
		return changeDaiShouOrder3(account,rechangeId,orderid);
	}

	public Map<String,Object> changeDaiShouOrder3(String account,String rechangeId,String orderid){
		/*TabRechangeLog bean = new TabRechangeLog();
		bean.setPingzheng(StringUtils.isBlank(account) ? rechangeId : account);
		List<TabRechangeLog> beans = tabRechangeLogService.findList(bean);
		if(beans.size() > 0){
			map.put("code",1);
			map.put("msg","Your approval code has been uploaded, please wait or contact online customer service for assistance!");
			return map;
		}*/
		TabRechangeLog tabRechangeLog = tabRechangeLogService.get(orderid);
		if(tabRechangeLog == null){
			map.put("code",1);
			map.put("msg","Order does not exist, please try again");
			return map;
		}
		if(tabRechangeLog.getStatus1().equals("2")){
			map.put("code",1);
			map.put("msg","Submitted successfully");
			return map;
		}
		tabRechangeLog.setPingzheng(StringUtils.isBlank(account) ? rechangeId : account);
		tabRechangeLogService.save(tabRechangeLog);


		Map<String,String> parame1 = new HashMap<>();
		parame1.put("account",account);
		parame1.put("code",rechangeId);
		parame1.put("orderId",tabRechangeLog.getOrderid());
		parame1.put("paytype",tabRechangeLog.getPaytype());
		String s = GoldpaysUtil.hanYuanUtils("http://41.72.149.115:8081/metaPay/api/newchangeDaiShouOrder", "", parame1);
		if(!"SUCCESS".equals(s)){
			map.put("code",1);
			map.put("msg","Submitted successfully");
			return map;
		}
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		if (tabRechangeLog.getStatus1().equals("1")) {
			tabRechangeLog.setStatus1("2");
			tabRechangeLog.setBeizhu("支付成功zfCallData");
			tabRechangeLog.setUpdatetime(getjndDate(new Date()));
			try {
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				tabRechangeLog.setRefundtime(format1.parse(format1.format(getjndDate(new Date()))));
			}catch (Exception e){

			}
			System.out.println("用户自动充值数据zfCallData："+JSONObject.toJSONString(tabRechangeLog));

			TabUserData tabUserData = tabUserDataService.get(tabRechangeLog.getUserid());
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabRechangeLog.getUserid());

			TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
			tabJiesuanLog.setUserid(tabRechangeLog.getUserid());
			tabJiesuanLog.setCmd("Recharger");
			tabJiesuanLog.setType("充值成功");
			if(tabRechangeLog.getOrderType() == 0){
				tabJiesuanLog.setMsg("Recharge successfully");
			}else{
				tabJiesuanLog.setMsg("Superior Recharge");
			}
			tabJiesuanLog.setAmont(tabRechangeLog.getHzhb());
			tabJiesuanLog.setRowid(tabRechangeLog.getRowid());
			tabJiesuanLog.setCreatetime(new ApiController().getjndDate(new Date()));
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
			tabRechangeLogService.save(tabRechangeLog);
			tabJiesuanLogService.save(tabJiesuanLog);

			if(tabUserData.getIstiyan().equals("1")){
				tabUserData.setIstiyan("0");
				tabUserData.setTyj(0D);
				tabUserData.setValidate(DateUtils.addDays(new Date(), 99999));
				tabUserDataService.save(tabUserData);
				List<TabUserData> list = new ArrayList<>();
				list.add(tabUserData);
				jedis.set(UserDataByToken+tabUserData.getAccesstoken(), JSON.toJSONString(list));
				jedis.expire(UserDataByToken+tabUserData.getAccesstoken(), 7200);

				tabTyjJiesuanService.deleteByUserId(tabUserData.getUserid());
			}

			try {
				Thread.sleep(500);
				dd(tabJiesuanLog,jedis);
			}catch (Exception e){

			}
			//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
			map.put("code",0);
			map.put("msg","Submitted successfully");
		}else{
			map.put("code",1);
			map.put("msg","Submitted successfully");
		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = "changeDaiShouOrder2")
	@ResponseBody
	public Map<String, Object> changeDaiShouOrder2(HttpServletRequest request){
		String userid = request.getParameter("userid");
		String amount = request.getParameter("amount");
		String code = request.getParameter("code");
		System.out.println("后台创建充值订单userid"+userid+" ,amount:"+amount+" ,code:"+code);

		TabUserData tabUserData = tabUserDataService.get(userid);
		if(tabUserData == null){
			map.put("code",-1);
			map.put("msg","用户编号不存在");
			return map;
		}
		TabRechangeLog tabRechangeLog = new TabRechangeLog();
		tabRechangeLog.setOrderType(0);
		tabRechangeLog.setUserid(userid);
		tabRechangeLog.setCreatetime(getjndDate(new Date()));
		tabRechangeLog.setIsNewRecord(true);
		tabRechangeLog.setOrderid(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + getCode());
		tabRechangeLog.setHzhb(Double.parseDouble(amount));
		tabRechangeLog.setParentid1(tabUserData.getParentid());
		tabRechangeLog.setParentid2(tabUserData.getParentid1());
		tabRechangeLog.setParentid3(tabUserData.getParenti3());
		tabRechangeLog.setShangjilink(tabUserData.getShangjilink());
		tabRechangeLog.setYgzh(tabUserData.getYgzh());
		tabRechangeLog.setYgzh2(tabUserData.getYgzh2());
		tabRechangeLog.setType("后台创建订单");
		tabRechangeLog.setStatus1("1");
		tabRechangeLog.setPingzheng(code);
		tabRechangeLogService.save(tabRechangeLog);

		Map<String, String> parame = new HashMap<>();
		parame.put("orderId", tabRechangeLog.getOrderid());
		parame.put("orderMoney", String.valueOf(tabRechangeLog.getHzhb()));
		parame.put("orderMoney1", String.valueOf(tabRechangeLog.getHzhb()));
		parame.put("userId", tabRechangeLog.getUserid());
		parame.put("notifyUrl", "http://41.72.149.115:8081/api/zfCallData");
		parame.put("tongdao", tabRechangeLog.getType());
		String s = GoldpaysUtil.hanYuanUtils("http://41.72.149.115:8081/metaPay/api/createLouisDaiShou", "", parame);

		return changeDaiShouOrder3("",code,tabRechangeLog.getRowid());
	}

	@RequestMapping(value = "registerPost")
	@ResponseBody
	public Map<String, Object> registerPost(@RequestBody Map maps,HttpServletRequest request) {
		String phone = maps.get("phone").toString();
		String PassWord = maps.get("PassWord").toString();
		TabUserData userData22 = tabUserDataService.get(phone);
		String Code = maps.get("Code").toString();
		System.out.println("用户注册使用的邀请码："+phone + " 邀请码："+Code);
		if (userData22 != null) {
			map.put("flag", "false");
			map.put("msg", "The account has been registered, please login directly");
			return map;
		}
		if(StringUtils.isBlank(Code)){
			map.put("flag", "false");
			map.put("msg", "Please enter the invitation code");
			return map;
		}
		TabUserData tabUserData = new TabUserData();
		tabUserData.setCodes(Code);
		List<TabUserData> tabUserDatas = tabUserDataService.findList(tabUserData);
		String p1 = "";
		String ll = "";

		TabAutoConfig tabAutoConfig = new TabAutoConfig();
		tabAutoConfig.setIsqidong("1");
		//tabAutoConfig.setIsauto(1);
		List<TabAutoConfig> tabAutoConfigs = tabAutoConfigService.findList(tabAutoConfig);
		if (tabUserDatas.size() > 0) {
			TabUserData userData = tabUserDatas.get(0);

			if("3".equals(userData.getStatus3())){
				map.put("flag", "false");
				map.put("msg", "Invitation code is locked, please contact customer service");
				return map;
			}

			Map<String, String> parame = new HashMap<String, String>();

			parame.put("userid", userData.getRowid());
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = userData.getCurrentmoney();
			List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
			Double currentMoney = Double.valueOf(10000);
			if(tabVipConfigs.size() > 0){
				TabVipConfig tabVipConfig = tabVipConfigs.get(0);
				currentMoney = tabVipConfig.getCurrentmoney();
			}
			if(m < currentMoney ){
				map.put("flag", "false");
				map.put("msg", "Sorry, your superior is not a VIP employee and is not eligible for invitation");
				return map;
			}
			if (userData.getStatus1() == null || userData.getStatus1().equals("3")) {
				map.put("flag", "false");
				map.put("msg", "The user broke the rules and was unable to register");
				return map;
			}

			if (PassWord.indexOf(" ") > -1) {
				map.put("flag", "false");
				map.put("msg", "The password cannot contain Spaces");
				return map;
			}
			p1 = userData.getParentid();
			ll = userData.getRowid();
			String pid1 = userData.getParentid1();
			tabUserData = new TabUserData();
			tabUserData.setRowid(phone);
			tabUserData.setPassword(PassWord);
			tabUserData.setUsername(phone);
			tabUserData.setUserleval(userData.getUserleval() + 1);
			tabUserData.setCreatetime(getjndDate(new Date()));
			tabUserData.setParentid(userData.getRowid());
			tabUserData.setParentid1(p1);
			tabUserData.setStatus1("2");
			tabUserData.setParenti3(pid1);
			tabUserData.setStatus1("2");
			tabUserData.setUsertype("3");
			tabUserData.setStatus3("2");
			tabUserData.setShangjilink(userData.getShangjilink() + "," + userData.getRowid());

			if (tabAutoConfigs.get(0).getIsauto() == 1) {
				tabUserData.setVip(1L);
				tabUserData.setIstiyan("1");
				tabUserData.setTyj(tabAutoConfigs.get(0).getTyj());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(getjndDate(new Date()));
				calendar.add(Calendar.DAY_OF_MONTH, tabAutoConfigs.get(0).getTianshu());
				tabUserData.setSumBalance(tabAutoConfigs.get(0).getTyj());
				tabUserData.setValidate(calendar.getTime());
				tabUserData.setTycs(1L);

				TabTyjJiesuan obj22 = new TabTyjJiesuan();
				obj22.setRowid(UUID.randomUUID().toString());
				obj22.setUserid(phone);
				obj22.setAmont(tabAutoConfigs.get(0).getTyj());
				obj22.setCreatetime(getjndDate(new Date()));
				obj22.setIsNewRecord(true);
				obj22.setType("自动赠送");
				tabTyjJiesuanService.save(obj22);
			} else {
				tabUserData.setVip(0L);
				tabUserData.setTycs(0L);
				tabUserData.setIstiyan("0");
			}
			tabUserData.setCurrentmoney(0D);
			tabUserData.setIsNewRecord(true);
			tabUserData.setCodes(getCode() + getCode());
			tabUserData.setSumMember(0D);
			tabUserData.setAccesstoken(UUID.randomUUID().toString());

			String ss = tabUserData.getShangjilink();
			String[] s = ss.split(",");
			for (String string : s) {
				if (!StringUtils.isBlank(string)) {
					TabUserData dds = tabUserDataService.get(string.trim());
					if(dds == null){
						continue;
					}
					if (dds.getSumMember() == null) {
						dds.setSumMember(0D);
					}

					dds.setSumMember(dds.getSumMember() + 1);
					tabUserDataService.save(dds);

					TabYuangongData tabYuangongData = new TabYuangongData();
					tabYuangongData.setQtzh(string);
					List<TabYuangongData> tabYuangongDatas = tabYuangongDataService.findList(tabYuangongData);
					if (tabYuangongDatas.size() > 0) {
						tabYuangongData = tabYuangongDatas.get(0);
						tabUserData.setYgzh(tabYuangongData.getQtzh());
						tabUserData.setYgzh2(tabYuangongData.getAcccount());
					}
				}
			}

			tabUserData.setRegisterip(getIp(request));
			tabUserData.setLastloginip(tabUserData.getRegisterip());
			tabUserData.setStatus2(0);
			tabUserData.setCurrentmoney(tabAutoConfigs.get(0).getZhucejl());
			tabUserData.setIszuodan(0);
			tabUserDataService.save(tabUserData);

			TabTaskjJob tasks = new TabTaskjJob();
			tasks.setUserid(tabUserData.getRowid());
			tasks.setShangjis(tabUserData.getShangjilink());
			tasks.setStatus1("1");
			tasks.setCreatetime(new Date());
			tasks.setRowid(UUID.randomUUID().toString());
			tasks.setIsNewRecord(true);

			//tabAutoConfig = new TabAutoConfig();
			//tabAutoConfig.setIsqidong("1");
			//tabAutoConfigs = tabAutoConfigService.findList(tabAutoConfig);

			TabJiesuanLog jiesuanLog = new TabJiesuanLog();
			jiesuanLog.setUserid(tabUserData.getRowid());
			jiesuanLog.setAmont(tabAutoConfigs.get(0).getZhucejl());
			jiesuanLog.setCmd("Registered donations");
			jiesuanLog.setType("Registered donations");
			jiesuanLog.setMsg("Register bonus");
			jiesuanLog.setParentid1(tabUserData.getParentid());
			jiesuanLog.setParentid2(tabUserData.getParentid1());
			jiesuanLog.setParentid3(tabUserData.getParenti3());
			jiesuanLog.setShangjilink(tabUserData.getShangjilink());

			jiesuanLog.setCreatetime(getjndDate(new Date()));
			jiesuanLog.setIsNewRecord(true);
			tabUserData.setHuilv("1000");
			tabJiesuanLogService.save(jiesuanLog);
			//dd(jiesuanLog,jedis);
			//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
			//getUserVip(jiesuanLog.getUserid());
			map.put("flag", "true");
			/*org.json.JSONObject  jsonObject  =  new org.json.JSONObject();
			jsonObject.put("rowid", tabUserData.getRowid());
			//Map<String, String> pp = new HashedMap();
			//pp.put("userid", tabUserData.getRowid());
			jsonObject.put("czje", 0);
			jsonObject.put("txje", 0);
			jsonObject.put("grsr", 0);
			jsonObject.put("tdsr", 0);
			jsonObject.put("ye", 1000);
			jsonObject.put("vip", 0);
			String  json = jsonObject.toString();
			jedis.set(TOKEN+tabUserData.getAccesstoken(), json);
			jedis.expire(TOKEN+tabUserData.getAccesstoken(), 7200);*/
			map.put("data", tabUserData);

		} else {
			map.put("flag", "false");
			map.put("msg", "The invite code is incorrect");
		}

		return map;
	}

	@RequestMapping(value = "/loginPostBody")
	@ResponseBody
	public Map<String, Object> loginPostBody(@RequestBody Map maps, HttpServletRequest httpServletRequest) {
		//判断当前登录ip地址，是否被禁用
		String loginIp = getIp(httpServletRequest);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String jinyongIps = jedis.get("jinyongIps");
		List<String> ips = new ArrayList<>();
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
		for(String ip : ips){
			if(loginIp.equals(ip)){
				jedis.quit();
				map.put("code", 1);
				map.put("msg", "Login IP address has been banned from login!");
				return map;
			}
		}

		String errorCount = jedis.get("errorCount:"+loginIp);
		if(StringUtils.isNotBlank(errorCount)){
			if(Integer.parseInt(errorCount) >= 10){
				map.put("code", 1);
				map.put("msg", "The account password is incorrect for too many times. Please log in again 20 minutes later!");
				return map;
			}
		}

		String mobile = maps.get("phone").toString();
		String password = maps.get("password").toString();
		TabUserData tabUserData = tabUserDataService.get(mobile);
		if (tabUserData == null) {
			if(StringUtils.isNotBlank(errorCount)){
				Integer newErrorCount = Integer.parseInt(errorCount) + 1;
				jedis.set("errorCount:"+loginIp,newErrorCount.toString());
			}else {
				jedis.set("errorCount:"+loginIp,"1");
			}
			jedis.expire("errorCount:"+loginIp, 1200);
			map.put("code", 1);
			map.put("msg", "Incorrect account password");
			jedis.quit();
			return map;
		}
		if("3".equals(tabUserData.getStatus1())){
			map.put("code", 1);
			map.put("msg", "Account is locked, please contact customer service");
			jedis.quit();
			return map;
		}


		if (password.equals(tabUserData.getPassword())) {
			map.put("data", tabUserData.getRowid());
			String accc= tabUserData.getAccesstoken();
			if(StringUtils.isBlank(accc)) {
				accc =   tabUserData.getRowid() ;
				tabUserData.setAccesstoken(accc);
			}

			tabUserData.setLastloginip(getIp(httpServletRequest));
			tabUserData.setLastlogintime(getjndDate(new Date()));
			tabUserDataService.save(tabUserData);
			map.put("accesstoken", tabUserData.getAccesstoken());
			map.put("code", 0);
			map.put("data2", tabUserData);
		} else {
			if(StringUtils.isNotBlank(errorCount)){
				Integer newErrorCount = Integer.parseInt(errorCount) + 1;
				jedis.set("errorCount:"+loginIp,newErrorCount.toString());
			}else {
				jedis.set("errorCount:"+loginIp,"1");
			}
			jedis.expire("errorCount:"+loginIp, 1200);
			map.put("code", 1);
			map.put("msg", "The account or password is incorrect");
			jedis.quit();
			return map;
		}
		jedis.quit();
		return map;
	}


	@RequestMapping(value = "saveIsTiyan")
	@ResponseBody
	public Map saveIsTiyan(){
		List<TabUserData> tabUserDataList = tabUserDataService.getisNotVip();
		TabAutoConfig tabAutoConfig = new TabAutoConfig();
		tabAutoConfig.setIsqidong("1");
		List<TabAutoConfig> tabAutoConfigs = tabAutoConfigService.findList(tabAutoConfig);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		for(TabUserData tabUserData : tabUserDataList){
			tabUserData.setVip(1L);
			tabUserData.setIstiyan("1");
			tabUserData.setTyj(tabAutoConfigs.get(0).getTyj());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getjndDate(new Date()));
			calendar.add(Calendar.DAY_OF_MONTH, tabAutoConfigs.get(0).getTianshu());
			tabUserData.setSumBalance(tabAutoConfigs.get(0).getTyj());
			tabUserData.setValidate(calendar.getTime());
			tabUserDataService.save(tabUserData);
			jedis.set(UserDataByToken+tabUserData.getAccesstoken(),JSON.toJSONString(tabUserData));
			TabTyjJiesuan tabTyjJiesuan = new TabTyjJiesuan();
			tabTyjJiesuan.setUserid(tabUserData.getRowid());
			List<TabTyjJiesuan> tabTyjJiesuanList = tabTyjJiesuanService.findList(tabTyjJiesuan);

			if(tabTyjJiesuanList.size() == 0){
				TabTyjJiesuan obj22 = new TabTyjJiesuan();
				obj22.setRowid(UUID.randomUUID().toString());
				obj22.setUserid(tabUserData.getRowid());
				obj22.setAmont(tabAutoConfigs.get(0).getTyj());
				obj22.setCreatetime(ApiController.getjndDate(new Date()));
				obj22.setIsNewRecord(true);
				tabTyjJiesuanService.save(obj22);
			}
			System.out.println(tabUserData.getRowid()+"已设置体验会员");
		}
		map.put("code",200);
		jedis.quit();
		return map;
	}

	@RequestMapping(value = "listDataTabSiderData")
	@ResponseBody
	public Page<TabSiderData> listDataTabSiderData(@RequestBody Map maps, HttpServletRequest httpServletRequest) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		Page<TabSiderData> page = new Page<TabSiderData>();
		String token = maps.get("token").toString();
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		if (list.size() > 0 ) {
			String rowid =  list.get(0).getRowid();
			Date mmmm  = getjndDate(new Date());
			String ss  =  DateUtils.formatDate(mmmm, "yyyyMMdd");
			TabTemps tabTemps    = new TabTemps();
			tabTemps = 	tabTempsService.get(rowid+(ss+""));
			if(tabTemps != null) {
				tabTempsService.delete(tabTemps);
			}
		}

		List<TabSiderData> tabSiderDatas = new ArrayList<>();

		String tabSiderDatasStr = jedis.get("tabSiderDatasStr");
		if(StringUtils.isBlank(tabSiderDatasStr)){
			tabSiderDatas  = tabSiderDataService.findList(new TabSiderData());
			jedis.set("tabSiderDatasStr",JSON.toJSONString(tabSiderDatas));
		}else{
			tabSiderDatas = JSON.parseObject(tabSiderDatasStr, new TypeReference<List<TabSiderData>>(){});
		}

		page.setList(tabSiderDatas);

		return page;
	}

	@RequestMapping(value = "listDataTabVipConfig")
	@ResponseBody
	public Map<String, Object> listDataTabVipConfig(@RequestBody Map request) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
		map.put("list", tabVipConfigs);
		String token = request.get("token").toString();
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			tabUserData = list.get(0);
			map.put("code", 0);
			Long vip = list.get(0).getVip();
			map.put("vip", vip);
			List<TabUserData> lists = new ArrayList<>();
			lists.add(tabUserData);
		}

		String tabGonggaosStr = jedis.get("tabGonggaos");
		List<TabGonggao> tabGonggaos = new ArrayList<>();
		if(StringUtils.isBlank(tabGonggaosStr)){
			tabGonggaos = tabGonggaoService.findList(new TabGonggao());
			jedis.set("tabGonggaos",JSON.toJSONString(tabGonggaos));
		}else {
			tabGonggaos = JSON.parseObject(tabGonggaosStr, new TypeReference<List<TabGonggao>>(){});
		}

		if(tabGonggaos.size() > 0 ) {
			map.put("gonggao", tabGonggaos.get(0).getImgsrc());
		}else {
			map.put("gonggao", "");
		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = "getUserDetail")
	@ResponseBody
	public Map<String, Object> getUserDetail(@RequestBody Map<String, String> parame) {
		String token = parame.get("token");
		//Jedis jedis =  new Jedis("41.72.149.115", 6379);
		//jedis.auth("hask071bend");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> tabUserDatas  = getUserDataByToken(tabUserData);
		TabUserData tabUserData1 = tabUserDatas.get(0);
		tabUserData1.setPassword("");
		tabUserData1.setTxpassword("");

		TabAutoConfig obj = new TabAutoConfig();
		obj.setIsqidong("1");
		List<TabAutoConfig> tabAutoConfigs = tabAutoConfigService.findList(obj);

		tabUserData1.setZpisqidong(tabAutoConfigs.get(0).getZpisqidong());
		Double m = tabUserData1.getCurrentmoney();
		/*if(m>0){
			String mstr = new BigDecimal(m).setScale(3,BigDecimal.ROUND_UP).toString();
			tabUserData1.setCurrentmoney(Double.parseDouble(mstr.substring(0,mstr.length()-1)));
		}*/

		Double shouru = tabUserData1.getGrsy();
		/*if(shouru>0){
			String shouruStr = new BigDecimal(shouru).setScale(3,BigDecimal.ROUND_UP).toString();
			tabUserData1.setGrsy(Double.parseDouble(shouruStr.substring(0,shouruStr.length()-1)));
		}*/

		tabUserData1.setShangjilink("");
		tabUserData1.setUpdateByName("");
		tabUserData1.setQuanxian("");
		tabUserData1.setYgzh("");
		map.put("data", tabUserData1);
		return map;
	}

	@RequestMapping(value = "getUserBanks")
	@ResponseBody
	public Map<String, Object> getUserBanks(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			tabUserData = list.get(0);
			String rowid = tabUserData.getRowid();
			TabUserBank tabUserBank = new TabUserBank();
			tabUserBank.setUserid(rowid);
			List<TabUserBank> tabUserBanks = tabUserBankService.findList(tabUserBank);
			List<Banks> banks = new ArrayList<ApiController.Banks>();
			Banks banks2 = new Banks();
			banks2.setCode("M-pesa");
			banks2.setTabUserBanks(tabUserBanks);
			banks.add(banks2);

			map.put("data", banks);
		}
		return map;
	}

	@RequestMapping(value = "saveUserDetail3")
	@ResponseBody
	public Map<String, Object> saveUserDetail3(@RequestBody Map<String, String> request) {
		//String imgsrc = request.get("imgsrc");
		String sex = request.get("sex");
		String birthday = request.get("birthday");
		String nickname = request.get("nickname");
		String nationality = request.get("nationality");
		String phone = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(phone);
		List<TabUserData> list =getUserDataByToken(tabUserData);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			tabUserData = list.get(0);
			//tabUserData.setImgsrc(imgsrc);
			tabUserData.setSex(sex);
			tabUserData.setBirthday(birthday);
			tabUserData.setUsername(nickname);
			tabUserData.setNationality(nationality);
			tabUserDataService.save(tabUserData);
			map.put("code", 0);
			map.put("flag", "true");

			List<TabUserData> data = new ArrayList<>();
			data.add(tabUserData);

			jedis.set(UserDataByToken+phone,JSON.toJSONString(data));
			jedis.expire(UserDataByToken+phone,7200);
		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = "saveUserAddress")
	@ResponseBody
	public Map<String, Object> saveUserAddress(@RequestBody TabUserAddress tabUserAddress) {
		String token = tabUserAddress.getToken();
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			map.put("flag", "true");
			map.put("msg","You have successfully bound the address");
			String userId = list.get(0).getRowid();
			TabUserAddress tabUserAddress2 = tabUserAddressService.get(userId);
			if (tabUserAddress2 == null) {
				tabUserAddress.setUserid(userId);
				tabUserAddress.setIsNewRecord(true);
				tabUserAddressService.save(tabUserAddress);
			} else {
				tabUserAddress.setUserid(userId);
				tabUserAddressService.save(tabUserAddress);
			}
			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");
			jedis.set("ADDRESS:" + tabUserAddress.getUserid() + "_address",JSON.toJSONString(tabUserAddress));
			jedis.expire("ADDRESS:" + tabUserAddress.getUserid() + "_address",3600);
			jedis.quit();
		}

		return map;
	}

	@RequestMapping(value = "saveFaceBooks")
	@ResponseBody
	public Map<String, Object> saveFaceBooks(@RequestBody TabFacebook tabFacebook) {
		String token = tabFacebook.getToken();
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			map.put("flag", "true");
			tabUserData = list.get(0);
			String userId = list.get(0).getRowid();
			tabFacebook.setUserid(userId);
			tabFacebook.setRowid(UUID.randomUUID().toString());
			tabFacebook.setCreatetime(getjndDate(new Date()));
			tabFacebook.setIsNewRecord(true);
			tabFacebook.setParentid1(tabUserData.getParentid());
			tabFacebook.setParentid2(tabUserData.getParentid1());
			tabFacebook.setParentid3(tabUserData.getParenti3());
			tabFacebook.setShangjilink(tabUserData.getShangjilink());
			tabFacebook.setYgzh(tabUserData.getYgzh());
			tabFacebook.setYgzh2(tabUserData.getYgzh2());
			tabFacebookService.save(tabFacebook);
		}

		return map;
	}

	@RequestMapping(value = "changePasswordData2")
	@ResponseBody
	public Map<String, Object> changePasswordData2(@RequestBody Map<String, String> request) {
		String token = request.get("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		List<TabUserData> data = new ArrayList<>();
		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			map.put("flag", "true");
			TabUserData userData = list.get(0);
			String oldPassword = request.get("oldPassword");
			String newPassword = request.get("newPassword");
			if (StringUtils.isBlank(userData.getTxpassword())) {
				userData.setTxpassword(newPassword);
				tabUserDataService.save(userData);
			} else {
				if (userData.getTxpassword().equals(oldPassword)) {
					userData.setTxpassword(newPassword);
					tabUserDataService.save(userData);
					map.put("flag", "true");
				} else {
					map.put("flag", "false");
					map.put("msg", "The original password is wrong");
				}
			}
			data.add(userData);
			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");
			jedis.set(UserDataByToken+token,JSON.toJSONString(data));
			jedis.expire(UserDataByToken+token,7200);
			jedis.quit();
		}

		return map;
	}

	@RequestMapping(value = "tixianPostData")
	@ResponseBody
	public Map<String, Object> tixianPostData(@RequestBody Map map2,HttpServletRequest httpServletRequest) {

		String token = map2.get("token").toString();

		TabAutoConfig obj1 = new TabAutoConfig();
		obj1.setIsqidong("1");
		List<TabAutoConfig> tabAutoConfigs = tabAutoConfigService.findList(obj1);
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			tabUserData = list.get(0);
			obj1 = tabAutoConfigs.get(0);
			String userId = list.get(0).getRowid();
			// 查看有没待审核的
			Long cc2 = tabTixianLogService.getShenhe(userId);
			if (cc2 > 0) {
				map.put("flag", "false");
				map.put("msg", "Sorry, the number of withdrawals cannot exceed 3 times per day");
				return map;
			}

			cc2 = tabTixianLogService.getTodays(userId, ApiController.getjndDate(new Date()));
			if (cc2 >= 3) {
				map.put("flag", "false");
				map.put("msg", "More than three times today");
				return map;
			}

			Map<String, String> mapTixian = new HashMap<>();
			mapTixian.put("userid", userId);
			Integer cc = tabTixianLogService.getTotalCount(mapTixian);

			// 首次提现
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData.getCurrentmoney();
			String cardType = map2.get("cardType").toString();
			String type = map2.get("type").toString();
			String money = map2.get("money").toString();
			String txMm = map2.get("txMm").toString();

			if (tabUserData.getTxpassword() == null || !tabUserData.getTxpassword().equals(txMm)) {
				map.put("flag", "false");
				map.put("msg", "Wrong withdrawal password");
				return map;
			}
			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");
			Integer refundDay = 30;
			String refundDayStr = jedis.get("refundDay");
			if(refundDayStr != null){
				refundDay = Integer.parseInt(refundDayStr);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getjndDate(new Date()));
			calendar.add(Calendar.DAY_OF_YEAR, -refundDay);
			Date date = calendar.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = format1.format(date);

			Map<String,String> param = new HashMap<>();
			param.put("userid",userId);
			param.put("time",str);
			Double frozenMoney = tabRechangeLogService.getFrozen(param);
			if (m >= Double.valueOf(money)) {
				if(m - Double.parseDouble(money) < frozenMoney){
					map.put("flag", "false");
					map.put("msg", "Sorry, you can't withdraw funds that have been recharged by your superior");
					return map;
				}
				Double sctx = 0D;
				String hv = "";
				if (cc == 0) {
					sctx = obj1.getSctx();
					hv = obj1.getSctxhuilv();
				} else {
					sctx = obj1.getTxje();
					hv = obj1.getTxhuilv();
				}

				if (Double.valueOf(money) >= sctx) {
					TabTixianLog tabTixianLog = new TabTixianLog();
					tabTixianLog.setCardtype(cardType);
					tabTixianLog.setUserid(userId);
					if(StringUtils.isBlank(type)) {
						TabUserBank tabUserBank  =   tabUserBankService.get(cardType);
						type =    tabUserBank.getTransitnumber() ;
					}
					tabTixianLog.setNote("tixianPostData11"+getIp(httpServletRequest));
					tabTixianLog.setType(type);
					tabTixianLog.setMoney(Double.valueOf(money));
					tabTixianLog.setRowid(UUID.randomUUID().toString());
					tabTixianLog.setStatus1("1");
					tabTixianLog.setCreatetime(getjndDate(new Date()));
					tabTixianLog.setOrderid(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + getCode() + getCode());

					Double pre = Double.valueOf(hv.split(",")[0]);

					Double c = Double.valueOf(hv.split(",")[1]);

					Double dzje = Double.valueOf(money) - Double.valueOf(money) * pre / 100 - c;
					//DecimalFormat df = new DecimalFormat("#.##");
					//String dzjeStr = new BigDecimal(dzje).setScale(3,BigDecimal.ROUND_UP).toString();
					//tabTixianLog.setDzje(Double.parseDouble(dzjeStr.substring(0,dzjeStr.length()-1)));
					tabTixianLog.setDzje(dzje);
					tabTixianLog.setIsNewRecord(true);
					tabTixianLog.setParentid1(tabUserData.getParentid());
					tabTixianLog.setParentid2(tabUserData.getParentid1());
					tabTixianLog.setParentid3(tabUserData.getParenti3());
					tabTixianLog.setShangjilink(tabUserData.getShangjilink());
					tabTixianLog.setYgzh(tabUserData.getYgzh());
					tabTixianLog.setYgzh2(tabUserData.getYgzh2());
					tabTixianLogService.save(tabTixianLog);

					//tabUserData.setVip(0L);
					tabUserDataService.save(tabUserData);

					List<TabUserData> data = new ArrayList<>();
					data.add(tabUserData);
					jedis.set(UserDataByToken+token,JSON.toJSONString(data));
					jedis.expire(UserDataByToken+token,7200);

					// 保存结算明细

					TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();

					tabJiesuanLog.setUserid(tabUserData.getRowid());
					tabJiesuanLog.setAmont(Double.valueOf(money) * -1);
					tabJiesuanLog.setRowid(UUID.randomUUID().toString());
					tabJiesuanLog.setCmd("Withdraw");
					tabJiesuanLog.setType("提现申请");
					tabJiesuanLog.setMsg("Withdraw");
					tabJiesuanLog.setCreatetime(getjndDate(new Date()));
					tabJiesuanLog.setIsNewRecord(true);
					tabJiesuanLog.setParentid1(hv);
					tabJiesuanLog.setParentid1(tabUserData.getParentid());
					tabJiesuanLog.setParentid2(tabUserData.getParentid1());
					tabJiesuanLog.setParentid3(tabUserData.getParenti3());
					tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
					tabJiesuanLog.setStatus1(tabTixianLog.getOrderid());
					tabJiesuanLogService.save(tabJiesuanLog);

					jedis.auth("hask071bend");
					dd(tabJiesuanLog,jedis);
					//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
					//getuservip(tabJiesuanLog.getUserid());
					map.put("flag", "true");

				} else {
					DecimalFormat df = new DecimalFormat("#.##");
					System.err.println(df.format(3000));
					map.put("flag", "false");
					map.put("msg", "The minimum withdrawal amount is " + df.format(sctx) + "USh");
				}
			} else {
				map.put("flag", "false");
				map.put("msg", "Insufficient balance");
			}
			jedis.quit();
		}
		return map;
	}

	@RequestMapping(value = "getTotalSubUsers")
	@ResponseBody
	public Map<String, Object> getTotalSubUsers(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			tabUserData = list.get(0);
			map.put("flag", "true");

			String userId = list.get(0).getRowid();

			// 获取总分销人数
			Map<String, String> parame = new HashMap<>();
			parame.put("userid", userId);
			Long cc = tabUserDataService.findSumData(parame);
			map.put("cc", cc);
		}

		return map;
	}

	@RequestMapping(value = "getSubUsers")
	@ResponseBody
	public Map<String, Object> getSubUsers(@RequestBody Map<String, String> request,HttpServletRequest request1) {
		String token = request.get("token");
		String type = request.get("type");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			map.put("flag", "true");
			tabUserData = list.get(0);
			String userId = list.get(0).getRowid();
			TabUserData userData = new TabUserData();
			if (type.equals("1")) {
				userData.setParentid(userId);
			} else if (type.equals("2")) {
				userData.setParentid1(userId);
			} else if (type.equals("3")) {
				userData.setParenti3(userId);
			}

			List<TabUserData> tabUserDatas = tabUserDataService.findList(userData);
			int ll =0 ;
			List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
			for (TabUserData tabUserData2 : tabUserDatas) {//我的下一级
				Map<String, String> parame = new HashMap<String, String>();
				parame.put("userid", tabUserData2.getRowid());

				//Double m = tabJiesuanLogService.getUserMoney(parame);//我的下一级余额
				Double m =tabUserData2.getCurrentmoney();
				tabUserData2.setHuilv(m + "");


				if(tabUserData2.getIstiyan().equals("0") && tabUserData2.getVip()>0 ) {
					ll++;
				}
			}
			map.put("data", tabUserDatas);
			map.put("vipSum", ll);

		}
		return map;
	}

	@RequestMapping(value = "saveLiCaiData")
	@ResponseBody
	public Map<String, Object> saveLiCaiData(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			map.put("flag", "false");
			map.put("msg", "not open");

			tabUserData = list.get(0);

			String userId = list.get(0).getRowid();

			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);

			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData.getCurrentmoney();
			String rowid = request.get("rowid");
			TabLicaiList licaiList = tabLicaiListService.get(rowid);

			String money = request.get("money");
			String lv = licaiList.getBfb();
			String day = licaiList.getDays();

			if (m >= Double.valueOf(money) && Double.valueOf(money) >= licaiList.getZxje()) {
				BigDecimal bigDecimal = new BigDecimal(m);
				map.put("flag", "true");

				TabLicaiData tabLicaiData = new TabLicaiData();

				tabLicaiData.setRowid(UUID.randomUUID().toString());
				tabLicaiData.setUserid(userId);
				tabLicaiData.setLicaimoney(Double.valueOf(money));
				tabLicaiData.setDays(Long.valueOf(day));
				tabLicaiData.setCurrentpre(Double.valueOf(lv));
				tabLicaiData.setCreatetime(getjndDate(new Date()));

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(getjndDate(new Date()));

				calendar.add(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
				tabLicaiData.setEndtime(calendar.getTime());
				tabLicaiData.setStatus1("1");

				bigDecimal = new BigDecimal(money);
				bigDecimal = bigDecimal.add(
						new BigDecimal(money).multiply(new BigDecimal(Double.valueOf(lv))).divide(new BigDecimal(100)));

				tabLicaiData.setTotalmoney(bigDecimal.doubleValue());

				tabLicaiData.setIsNewRecord(true);
				tabLicaiData.setParentid1(tabUserData.getParentid());
				tabLicaiData.setParentid2(tabUserData.getPassword());
				tabLicaiData.setParentid3(tabUserData.getParenti3());
				tabLicaiData.setShangjilink(tabUserData.getShangjilink());

				tabLicaiDataService.save(tabLicaiData);

				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setRowid(UUID.randomUUID().toString());
				tabJiesuanLog.setUserid(tabUserData.getRowid());
				tabJiesuanLog.setCreatetime(getjndDate(new Date()));
				tabJiesuanLog.setCmd("Achat de produits financiers");
				tabJiesuanLog.setType("购买理财产品");
				tabJiesuanLog.setMsg("");
				tabJiesuanLog.setParentid1(tabUserData.getParentid());
				tabJiesuanLog.setAmont(tabLicaiData.getLicaimoney() * -1); // 购买
				tabJiesuanLog.setStatus1(tabLicaiData.getRowid());
				tabJiesuanLog.setParentid2(tabUserData.getParentid1());
				tabJiesuanLog.setParentid3(tabUserData.getParenti3());
				tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLogService.save(tabJiesuanLog);
				Jedis jedis =  new Jedis("41.72.149.115", 6379);
				jedis.auth("hask071bend");
				dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
				//getuservip(tabJiesuanLog.getUserid());
				jedis.quit();
			} else {
				map.put("flag", "false");
				map.put("msg", "Insufficient balance");
			}
		}

		return map;
	}

	// 获取我能购买的产品

	@RequestMapping(value = "getMyProductData")
	@ResponseBody
	public Map<String, Object> getMyProductData(@RequestBody Map<String, String> request) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String products =  jedis.get("tabProductDatas_" + request.get("vip")) ;
		List<TabProductData> tabProductDatas  = new ArrayList<TabProductData>();
		if(!StringUtils.isBlank(products))  {
			org.json.JSONArray  array =   new org.json.JSONArray(products);
			for (int i = 0; i < array.length(); i++) {
				org.json.JSONObject  jsonObject  =  array.getJSONObject(i) ;
				String  img = jsonObject.getString("imgsrc") ;
				TabProductData tabProductData  = new TabProductData();
				tabProductData.setImgsrc(img);
				tabProductDatas.add(tabProductData);
			}
		}else {
			tabProductDatas = tabProductDataService.findList2(request);
			org.json.JSONArray jsonArray  =   new org.json.JSONArray() ;
			for(TabProductData tabProductData  :tabProductDatas) {
				org.json.JSONObject jsonObject  = new org.json.JSONObject();
				jsonObject.put("imgsrc", tabProductData.getImgsrc()) ;
				jsonArray.put(jsonObject) ;
			}
			jedis.set("tabProductDatas_" + request.get("vip"),  jsonArray.toString()) ;
		}
		map.put("data", tabProductDatas);
		jedis.quit();
		map.put("code", 0);
		map.put("flag", "true");
		return map;
	}

	@RequestMapping(value = "getOrderJisuan")
	@ResponseBody
	public Map<String, Object> getOrderJisuan(@RequestBody Map<String, String> request) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String token = request.get("token");
		TabUserData tabUserData1 = new TabUserData();
		tabUserData1.setAccesstoken(token);
		List<TabUserData> userDatas  = getUserDataByToken(tabUserData1);
		if(userDatas.size() == 0){
			map.put("flag", "false");
			map.put("code", 1);
		}else {
			String vip = request.get("vip");

			TabVipConfig ooo11 = new TabVipConfig();
			ooo11.setVip(vip);

			String  vips = jedis.get("vip" + vip) ;

			if(!StringUtils.isBlank(vips))  {

				org.json.JSONObject jsonObject  = new org.json.JSONObject(vips);

				Double maxgoumai=  jsonObject.getDouble("maxgoumai") ;

				TabVipConfig tabVipConfig  = new TabVipConfig();
				tabVipConfig.setMaxgoumai(maxgoumai);
				map.put("tabVipConfigs", tabVipConfig);

			}else  {
				List<TabVipConfig> tabVipConfigs = getVipConfigList(ooo11);
				map.put("tabVipConfigs", tabVipConfigs.get(0));

				org.json.JSONObject jsonObject   =   new org.json.JSONObject();
				jsonObject.put("maxgoumai", tabVipConfigs.get(0).getMaxgoumai()) ;
				jedis.set("vip" + vip , jsonObject.toString())	;
			}


			map.put("code", 0);
			String userId = userDatas.get(0).getRowid();

			TabOrders tabOrderData = new TabOrders();
			tabOrderData.setUserid(userId);
			tabOrderData.setCreatetime_gte(DateUtils.getOfDayFirst(getjndDate((new Date()))));

			List<TabOrders> tabOrders = tabOrdersService.findList(tabOrderData);

			int i = 0;
			Double totalmoney = 0d;

			if(tabOrders.size() > 0) {
				i = tabOrders.get(0).getOrdercont().intValue();
				Double m  =  tabOrders.get(0).getLirunmonry();
				totalmoney  =  m;
				tabOrderData =     tabOrders.get(0);
				String  status1 =  tabOrderData.getStatus1() ;
				if(!status1.equals("4")) {
					String orderId =  tabOrderData.getOrderid() ;

					TabOrderData orderData   =  new TabOrderData();

					orderData.setOrderid(orderId);;
					orderData.setStatus1("1");

					List<TabOrderData> tabOrderDatas  =  tabOrderDataService.findList(orderData) ;

					i =  i -  tabOrderDatas.size() ;

					if(tabOrderDatas.size() >0) {
						TabOrderData tabOrderData1 = tabOrderDatas.get(0);


						//tabOrderData1.setRebatemoney(new BigDecimal(tabOrderData1.getRebatemoney()).setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
						map.put("wzfOrder", tabOrderData1) ;
					}
				}
				map.put("todayCommission", tabOrders.get(0).getLirunmonry()) ;
			}else{
				map.put("todayCommission", 0) ;
			}

			map.put("totalCount", i);
			//DecimalFormat df = new DecimalFormat("#.##");
			//map.put("sumPre", new BigDecimal(totalmoney).setScale(2,BigDecimal.ROUND_DOWN));
			map.put("sumPre", totalmoney);

			// 计算余额
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			TabUserData tabUserData = new TabUserData();
			tabUserData.setAccesstoken(token);
			List<TabUserData> list = getUserDataByToken(tabUserData);
			Double m = list.get(0).getCurrentmoney();
			if("1".equals(list.get(0).getIstiyan())){
				Long validatetime = list.get(0).getValidate().getTime();
				Long newTime = getjndDate(new Date()).getTime();
				Long day = (validatetime - newTime)/(1000L*60*60*24) + 1;
				map.put("tiyanDay",day);
				Map<String,String> parame1 = new HashMap<String, String>();
				parame1.put("userid", userId);
				Double m1 = tabTyjJiesuanService.getUserMoney(parame1);
				map.put("tiyanBalance",m1);
			}else{
				map.put("tiyanDay",0);
				map.put("tiyanBalance",0);
			}

			//map.put("m", new BigDecimal(m).setScale(2, BigDecimal.ROUND_DOWN));
			map.put("m",m);
			map.put("flag", "true");
		}

		jedis.quit();
		return map;
	}

	// 批量生成订单

	@RequestMapping(value = "piLiangOrder")
	@ResponseBody
	public Map<String, Object> piLiangOrder(@RequestBody Map<String, String> request) {
		Long startTime = new Date().getTime();
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String token = request.get("token");
		String wzfOrder = request.get("wzfOrder");
		if (!StringUtils.isBlank(wzfOrder)) {
			TabOrderData tabOrderData = tabOrderDataService.get(wzfOrder);
			if(tabOrderData.getStatus1().equals("1")) {
				map.put("code", 0);
				map.put("wzfOrder", tabOrderData);
				map.put("flag", "true");
				jedis.quit();
				return map;
			}
		}
		map.put("code", 0);
		/*String  data  = jedis.get(TOKEN+token);
		if (StringUtils.isBlank(data) ||  data.equals("{}")) {
			TabUserData tabUserData = new TabUserData();
			tabUserData.setAccesstoken(token);
			List<TabUserData> tabUserDatas  = getUserDataByToken(tabUserData);

			if(tabUserDatas.size() > 0 ) {
				tabUserData =  tabUserDatas.get(0);
				org.json.JSONObject  jsonObject  =  new org.json.JSONObject();
				jsonObject.put("rowid", tabUserData.getRowid());
				String  json = jsonObject.toString();
				data = json ;
				jedis.set(TOKEN+tabUserData.getAccesstoken(), json);
				jedis.expire(TOKEN+tabUserData.getAccesstoken(), 7200);
			}
		}

		org.json.JSONObject jsonObject = new org.json.JSONObject(data);
		String userId = jsonObject.getString("rowid");*/
		TabUserData bean = new TabUserData();
		bean.setAccesstoken(token);
		List<TabUserData> tabUserDatas  = getUserDataByToken(bean);
		TabUserData tabUserData = tabUserDatas.get(0);
		if(tabUserData.getIszuodan() != null && tabUserData.getIszuodan() == 1){
			map.put("flag", "false");
			map.put("msg", "Prohibition of work!");
			jedis.quit();
			return map;
		}
		String userId = tabUserData.getRowid();

		TabOrders tabOrders  = new TabOrders() ;
		tabOrders.setUserid(userId);
		tabOrders.setCreatetime_gte(DateUtils.getOfDayFirst(getjndDate(new Date())));
		Long cc = tabOrdersService.findCount(tabOrders);
		if (cc == 0) {
			String  istj   =  tabUserData.getIstiyan();
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData.getCurrentmoney();
			List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
			TabVipConfig tabVipConfig = tabVipConfigs.get(0);
			if (m >= tabVipConfig.getCurrentmoney() && istj.equals("0")) {

				map.put("flag", "true");
				//String vip = jsonObject.get("vip").toString();
				//tabUserData.setVip(Long.valueOf(vip));
				TabVipConfig vipss = tabVipConfigs.get(tabUserData.getVip().intValue() - 1);
				String orderId = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + getCode() + getCode();

				Double bj = 0D;
				Double yj = 0D;

				Date mmmm  = getjndDate(new Date());
				String ss  =  DateUtils.formatDate(mmmm, "yyyyMMdd");
				TabTemps tabTemps    = new TabTemps();
				tabTemps.setRowid(userId+(ss+""));
				tabTemps.setUuu("1");
				//tabTemps.setIsNewRecord(true);
				try {
					tabTempsService.save(tabTemps);
				}catch (Exception e) {
					map.put("flag", "false");
					map.put("msg", "Wait please");
					jedis.quit();
					return map;
				}
				List<TabProductData> productDatas = getJisuanProduct(m, tabUserData.getVip(),tabUserData.getRowid());

				List<TabOrderData> datas = new ArrayList<TabOrderData>();
				for (TabProductData tabProductData : productDatas) {

					TabOrderData tabOrderData = new TabOrderData();

					tabOrderData.setRowid(UUID.randomUUID().toString());
					tabOrderData.setUserid(userId);
					tabOrderData.setProductid(tabProductData.getRowid());
					tabOrderData.setOrdermonry(tabProductData.getBuymoney());
					bj = bj + tabProductData.getBuymoney();
					Double m1 = vipss.getMgrwdyj() * tabProductData.getBuymoney();
					yj = yj + m1;
					tabOrderData.setRebatemoney(m1);
					tabOrderData.setOrderid(orderId);
					tabOrderData.setIstiyan("0");
					tabOrderData.setStatus1("1");
					tabOrderData.setIsNewRecord(true);
					tabOrderData.setCreatetime(getjndDate(new Date()));
					tabOrderData.setParentid1(tabUserData.getParentid());
					tabOrderData.setParentid2(tabUserData.getParentid1());
					tabOrderData.setParentid3(tabUserData.getParenti3());
					tabOrderData.setShangjilink(tabUserData.getShangjilink());
					//tabOrderDataService.save(tabOrderData);
					datas.add(tabOrderData);
				}
				tabOrderDataService.saveList(datas);
				tabOrders = new TabOrders();
				tabOrders.setOrderid(orderId);
				tabOrders.setRowid(UUID.randomUUID().toString());
				tabOrders.setStatus1("1");
				tabOrders.setUserid(userId);
				tabOrders.setIsNewRecord(true);

				tabOrders.setOrdercont(10L);
				tabOrders.setCreatetime(getjndDate(new Date()));
				tabOrders.setOrdermoney(bj);
				tabOrders.setLirunmonry(yj);
				tabOrders.setPaycount(0L);
				tabOrders.setParentid1(tabUserData.getParentid());
				tabOrders.setParentid2(tabUserData.getParentid1());
				tabOrders.setParentid3(tabUserData.getParenti3());
				tabOrders.setShangjilink(tabUserData.getShangjilink());
				tabOrders.setYgzh(tabUserData.getYgzh());
				tabOrders.setYgzh2(tabUserData.getYgzh2());
				tabOrders.setVip(String.valueOf(tabUserData.getVip()));
				tabOrders.setIstiyan(tabUserData.getIstiyan());
				try {
					tabOrdersService.save(tabOrders);
				}catch (Exception e){
					e.printStackTrace();
					System.out.println("批量生成订单保存报错用户id："+userId+" 时间戳："+getjndDate(new Date()).getTime());
					map.put("flag", "false");
					map.put("msg", "Wait please");
					jedis.quit();
					return map;
				}

				JSONObject buyJsonObject1 = new JSONObject();
				buyJsonObject1.put("vip",tabUserData.getVip());
				buyJsonObject1.put("userid",userId);
				buyJsonObject1.put("time",getjndDate(new Date()).getTime());
				buyJsonObject1.put("istiyan",tabUserData.getIstiyan());
				jedis.set(isBuyProDuct+userId,buyJsonObject1.toJSONString());
				tabUserData.setXdzt1(1);
				tabUserDataService.save(tabUserData);
				map.put("wzfOrder", datas.get(0));
				map.put("flag", "true");
				Long time = new Date().getTime() - startTime;
				System.out.println("正式用户生成订单userid："+userId + " vip："+tabUserData.getVip() + " 消耗时间："+time);
			} else {

				parame = new HashMap<String, String>();
				parame.put("userid", userId);

				m = tabTyjJiesuanService.getUserMoney(parame);
				map.put("money", m);
				if (m > 35000) {
					m = 35000d;
				}
				tabUserData.setTyj(m);

				if (tabUserData.getIstiyan().equals("1") && tabUserData.getTyj() != null && tabUserData.getTyj() >= 4) {
					String vip =   "0";

					if (vip.equals("0")) {
						vip = "1";
					}

					TabVipConfig vipss = tabVipConfigs.get(Integer.valueOf(vip) - 1);
					m = tabUserData.getTyj();
					Date mmmm  = new Date() ;
					String ss  =  DateUtils.formatDate(mmmm, "yyyyMMdd");
					TabTemps tabTemps    = new TabTemps();

					tabTemps.setRowid(userId+(ss+""));
					tabTemps.setUuu("1");

					tabTemps.setIsNewRecord(true);
					try {
						tabTempsService.save(tabTemps);
					}catch (Exception e) {
						map.put("flag", "false");
						map.put("msg", "Wait please");
						jedis.quit();
						return map;
					}
					List<TabProductData> productDatas = getJisuanProduct(m, 1L,tabUserData.getRowid());

					String orderId = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + getCode() + getCode();

					Double bj = 0D;
					Double yj = 0D;

					List<TabOrderData> datas = new ArrayList<TabOrderData>();
					for (TabProductData tabProductData : productDatas) {

						TabOrderData tabOrderData = new TabOrderData();

						tabOrderData.setRowid(UUID.randomUUID().toString());
						tabOrderData.setUserid(userId);
						tabOrderData.setProductid(tabProductData.getRowid());
						tabOrderData.setOrdermonry(tabProductData.getBuymoney());
						bj = bj + tabProductData.getBuymoney();
						Double m1 = vipss.getMgrwdyj() * tabProductData.getBuymoney();
						yj = yj + m1;
						tabOrderData.setRebatemoney(m1);
						tabOrderData.setOrderid(orderId);
						tabOrderData.setIstiyan("1");
						tabOrderData.setStatus1("1");
						tabOrderData.setIsNewRecord(true);
						tabOrderData.setCreatetime(getjndDate(new Date()));
						tabOrderData.setParentid1(tabUserData.getParentid());
						tabOrderData.setParentid2(tabUserData.getParentid1());
						tabOrderData.setParentid3(tabUserData.getParenti3());
						tabOrderData.setShangjilink(tabUserData.getShangjilink());

						//tabOrderDataService.save(tabOrderData);
						datas.add(tabOrderData);
					}
					tabOrderDataService.saveList(datas);
					tabOrders = new TabOrders();
					tabOrders.setOrderid(orderId);
					tabOrders.setRowid(UUID.randomUUID().toString());
					tabOrders.setStatus1("1");
					tabOrders.setUserid(userId);
					tabOrders.setIsNewRecord(true);

					tabOrders.setOrdercont(10L);
					tabOrders.setCreatetime(getjndDate(new Date()));
					tabOrders.setOrdermoney(bj);
					tabOrders.setLirunmonry(yj);
					tabOrders.setPaycount(0L);
					tabOrders.setYgzh(tabUserData.getYgzh());
					tabOrders.setYgzh2(tabUserData.getYgzh2());
					tabOrders.setParentid1(tabUserData.getParentid());
					tabOrders.setParentid2(tabUserData.getParentid1());
					tabOrders.setParentid3(tabUserData.getParenti3());
					tabOrders.setShangjilink(tabUserData.getShangjilink());
					tabOrders.setVip(vip);
					tabOrders.setIstiyan(tabUserData.getIstiyan());
					try {
						tabOrdersService.save(tabOrders);
					}catch (Exception e){
						e.printStackTrace();
						System.out.println("批量生成订单保存报错用户id："+userId+" 时间戳："+getjndDate(new Date()).getTime());
						map.put("flag", "false");
						map.put("msg", "Wait please");
						jedis.quit();
						return map;
					}

					JSONObject buyJsonObject1 = new JSONObject();
					buyJsonObject1.put("vip",vip);
					buyJsonObject1.put("userid",userId);
					buyJsonObject1.put("time",getjndDate(new Date()).getTime());
					buyJsonObject1.put("istiyan",tabUserData.getIstiyan());
					tabUserData.setXdzt1(1);
					jedis.set(isBuyProDuct+userId,buyJsonObject1.toJSONString());
					tabUserDataService.save(tabUserData);
					map.put("flag", "true");
					map.put("wzfOrder", datas.get(0));
					Long time = new Date().getTime() - startTime;
					System.out.println("体验用户生成订单userid："+userId + " vip："+vip + " 消耗时间："+time);
				} else {
					map.put("flag", "false");
					map.put("msg", "Insufficient balance");
				}
			}
		} else {
			map.put("flag", "false");
			map.put("msg", "Order completed today");
			map.put("code", 0);
		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = "changeOrderData")
	@ResponseBody
	public Map<String, Object> changeOrderData(@RequestBody Map<String, String> request) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		Long startTime = new Date().getTime();
		String rowid = request.get("rowid");
		TabOrderData tabOrderData = tabOrderDataService.get(rowid);
		Double m = 0.0;
		String yue = jedis.get(YUE+tabOrderData.getUserid());
		if(yue != null){
			m = Double.parseDouble(yue);
		}else{
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabOrderData.getUserid());
			TabUserData tabUserData = tabUserDataService.get(tabOrderData.getUserid());
			m = tabUserData.getCurrentmoney();
		}
		TabOrders taborders = new TabOrders();
		taborders.setOrderid(tabOrderData.getOrderid());
		List<TabOrders> tabOrders = tabOrdersService.findList(taborders);
		taborders = tabOrders.get(0);
		if (m >= tabOrderData.getOrdermonry() && tabOrderData.getStatus1().equals("1")
				&& tabOrderData.getIstiyan().equals("0")) {

			tabOrderData.setStatus1("2");
			tabOrderDataService.save(tabOrderData);
			taborders.setPaytime(getjndDate(new Date()));
			taborders.setPaycount(taborders.getPaycount() + 1);
			taborders.setStatus1("2");
			tabOrdersService.save(taborders);
			TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
			tabJiesuanLog.setRowid(tabOrderData.getRowid());
			tabJiesuanLog.setUserid(taborders.getUserid());
			tabJiesuanLog.setCreatetime(getjndDate(new Date()));
			tabJiesuanLog.setCmd("Buy product");
			tabJiesuanLog.setType("购买商品");
			tabJiesuanLog.setMsg("Buy goods");
			tabJiesuanLog.setParentid1(taborders.getParentid1());
			tabJiesuanLog.setAmont(tabOrderData.getOrdermonry() * -1); // 购买
			tabJiesuanLog.setStatus1(tabOrderData.getOrderid());
			tabJiesuanLog.setParentid2(taborders.getParentid2());
			tabJiesuanLog.setParentid3(taborders.getParentid3());
			tabJiesuanLog.setShangjilink(taborders.getShangjilink());
			tabJiesuanLog.setIsNewRecord(true);
			//tabJiesuanLogService.save(tabJiesuanLog);
			dd(tabJiesuanLog,jedis);
			//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
			//getuservip(tabJiesuanLog.getUserid());
			tabOrderData = new TabOrderData();
			tabOrderData.setOrderid(taborders.getOrderid());
			tabOrderData.setStatus1("1");
			List<TabOrderData> tabOrderDatas   =   tabOrderDataService.findList(tabOrderData);
			if(tabOrderDatas.size() > 0 ) {
				map.put("flag", "true");
				map.put("wzfOrder", tabOrderDatas.get(0));
				map.put("orders", tabOrderDatas.get(0));
				String productStr = jedis.get(ProDuct+tabOrderDatas.get(0).getProductid());
				TabProductData tabProductData = null;
				if(StringUtils.isBlank(productStr)){
					tabProductData = tabProductDataService.get(tabOrderDatas.get(0).getProductid());
					jedis.set(ProDuct+tabOrderDatas.get(0).getProductid(),JSON.toJSONString(tabProductData));
				}else{
					tabProductData = JSON.parseObject(productStr,TabProductData.class);
				}

				map.put("tabProductData", tabProductData);
				//map.put("sumPre", new BigDecimal(tabOrderDatas.get(0).getRebatemoney()).setScale(3,BigDecimal.ROUND_DOWN));
				map.put("sumPre",tabOrderDatas.get(0).getRebatemoney());
			}else {
				try {
					TabOrders bean = taborders;
					CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
						jj(bean);
					});

					map.put("sumPre","0");
				}catch (Exception e){
				}
			}
			//map.put("m", new BigDecimal(m +  tabJiesuanLog.getAmont()).setScale(3,BigDecimal.ROUND_DOWN)) ;
			map.put("m",(m +  tabJiesuanLog.getAmont()));
			map.put("totalCount", 10 - tabOrderDatas.size() ) ;
			map.put("flag", "true");
			map.put("code", 0);
			if("1".equals(taborders.getVip())){
				map.put("mgrwdyj", "3%");
			}else if("2".equals(taborders.getVip())){
				map.put("mgrwdyj", "3.5%");
			}else if("3".equals(taborders.getVip())){
				map.put("mgrwdyj", "4%");
			}else if("4".equals(taborders.getVip())){
				map.put("mgrwdyj", "4.5%");
			}else if("5".equals(taborders.getVip())){
				map.put("mgrwdyj", "5%");
			}else if("6".equals(taborders.getVip())){
				map.put("mgrwdyj", "5.5%");
			}else if("7".equals(taborders.getVip())){
				map.put("mgrwdyj", "6%");
			}else if("8".equals(taborders.getVip())){
				map.put("mgrwdyj", "6.5%");
			}
			Long time = new Date().getTime() - startTime;
			System.out.println("正式用户购买商品消耗时间："+time);
		} else {
			if (tabOrderData.getIstiyan() != null && tabOrderData.getIstiyan().equals("1") && tabOrderData.getStatus1().equals("1")) {

				Double tyj = 500000D;
				if (tyj >= tabOrderData.getOrdermonry() && tabOrderData.getStatus1().equals("1")) {
					tabOrderData.setStatus1("2");
					tabOrderDataService.save(tabOrderData);
					map.put("flag", "true");
					map.put("code", 0);

					taborders.setPaytime(getjndDate(new Date()));
					taborders.setPaycount(taborders.getPaycount() + 1);
					taborders.setStatus1("2");

					tabOrdersService.save(taborders);

					TabTyjJiesuan obj22 = new TabTyjJiesuan();
					obj22.setRowid(UUID.randomUUID().toString());
					obj22.setUserid(tabOrderData.getUserid());
					obj22.setAmont(tabOrderData.getOrdermonry() * -1);
					obj22.setCreatetime(getjndDate(new Date()));
					obj22.setIsNewRecord(true);
					obj22.setStatus1(tabOrderData.getOrderid());
					obj22.setParentid1(taborders.getParentid1());
					obj22.setParentid2(taborders.getParentid2());
					obj22.setParentid3(taborders.getParentid3());
					obj22.setShangjilink(taborders.getShangjilink());
					tabTyjJiesuanService.save(obj22);

					tabOrderData = new TabOrderData();
					tabOrderData.setOrderid(taborders.getOrderid());
					tabOrderData.setStatus1("1");

					List<TabOrderData> tabOrderDatas   =   tabOrderDataService.findList(tabOrderData);

					if(tabOrderDatas.size() > 0 ) {
						map.put("flag", "true");
						map.put("wzfOrder", tabOrderDatas.get(0));
						map.put("orders", tabOrderDatas.get(0));
						//map.put("sumPre", new BigDecimal(tabOrderDatas.get(0).getRebatemoney()).setScale(3,BigDecimal.ROUND_DOWN));
						map.put("sumPre",tabOrderDatas.get(0).getRebatemoney());
						TabProductData tabProductData  =  tabProductDataService.get(tabOrderDatas.get(0).getProductid()) ;
						map.put("tabProductData", tabProductData);
					}else {
						map.put("sumPre", "0");
						TabOrders bean = taborders;
						CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
							jj(bean);
						});
					}
					//map.put("m", new BigDecimal(m).setScale(3,BigDecimal.ROUND_DOWN)) ;
					map.put("m",m);
					map.put("totalCount", 10 - tabOrderDatas.size());
				} else {
					map.put("flag", "false");
					map.put("msg", "Lack of experience money");
				}
				if("1".equals(taborders.getVip())){
					map.put("mgrwdyj", "3%");
				}else if("2".equals(taborders.getVip())){
					map.put("mgrwdyj", "3.5%");
				}else if("3".equals(taborders.getVip())){
					map.put("mgrwdyj", "4%");
				}else if("4".equals(taborders.getVip())){
					map.put("mgrwdyj", "4.5%");
				}else if("5".equals(taborders.getVip())){
					map.put("mgrwdyj", "5%");
				}else if("6".equals(taborders.getVip())){
					map.put("mgrwdyj", "5.5%");
				}else if("7".equals(taborders.getVip())){
					map.put("mgrwdyj", "6%");
				}else if("8".equals(taborders.getVip())){
					map.put("mgrwdyj", "6.5%");
				}

				Long time = new Date().getTime() - startTime;
				System.out.println("体验用户购买商品消耗时间："+time);
			} else {
				map.put("flag", "false");
				map.put("msg", "Product already purchased");
			}
		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = "savePingJiaData")
	@ResponseBody
	public Map<String, Object> savePingJiaData(@RequestBody Map<String, String> request) {
		String rowid = request.get("rowid");
		String pjia = request.get("pjia");
		String index = request.get("index");
		String array = request.get("array");
		map.put("flag", "true");
		map.put("code", 0);
		return map;
	}

	@RequestMapping(value = "getOrderList")
	@ResponseBody
	public Map<String, Object> getOrderList(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			String index = request.get("type");
			String userId = list.get(0).getRowid();
			TabOrderData tabOrderData = new TabOrderData();
			tabOrderData.setUserid(userId);
			tabOrderData.setStatus1(index);
			List<TabOrderData> tabOrderDatas = tabOrderDataService.findList(tabOrderData);
			for (TabOrderData tabOrderData2 : tabOrderDatas) {
				String pid = tabOrderData2.getProductid();
				TabProductData tabProductData = tabProductDataService.get(pid);
				//tabOrderData2.setRebatemoney(new BigDecimal(tabOrderData2.getRebatemoney()).setScale(4,BigDecimal.ROUND_DOWN).doubleValue());
				tabOrderData2.setTabProductData(tabProductData);
				if(tabProductData !=null){
					tabOrderData2.setImgsrc(tabProductData.getImgsrc());
				}
			}
			map.put("flag", "true");
			map.put("data", tabOrderDatas);
		}
		return map;
	}

	@RequestMapping(value = "getShouRuList")
	@ResponseBody
	public Map<String, Object> getShouRuList(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			String userId = list.get(0).getRowid();
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);
			List<TabJiesuanLog> tabJiesuanLogs = tabJiesuanLogService.getShouruList(parame);

			/*for(TabJiesuanLog bean : tabJiesuanLogs){
				String str = new BigDecimal(bean.getAmont()).setScale(3,BigDecimal.ROUND_UP).toString();
				bean.setAmont(Double.parseDouble(str.substring(0,str.length()-1)));
			}*/
			map.put("data", tabJiesuanLogs);
		}
		return map;
	}

	@RequestMapping(value = "getZhiChuList")
	@ResponseBody
	public Map<String, Object> getZhiChuList(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			String userId = list.get(0).getRowid();
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);
			List<TabJiesuanLog> tabJiesuanLogs = tabJiesuanLogService.getZhiChuList(parame);

			/*for(TabJiesuanLog bean : tabJiesuanLogs){
				String str = new BigDecimal(bean.getAmont()).setScale(3,BigDecimal.ROUND_UP).toString();
				bean.setAmont(Double.parseDouble(str.substring(0,str.length()-1)));
			}*/
			map.put("data", tabJiesuanLogs);
		}
		return map;
	}

	@RequestMapping(value = "getLiCaiData")
	@ResponseBody
	public Map<String, Object> getLiCaiData(@RequestBody Map<String, String> request) {

		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			TabLicaiData tabLicaiData = new TabLicaiData();

			tabLicaiData.setUserid(list.get(0).getRowid());

			List<TabLicaiData> tabLicaiDatas = tabLicaiDataService.findList(tabLicaiData);

			Double m = 0d;
			Double m1 = 0d;
			for (TabLicaiData tabLicaiData2 : tabLicaiDatas) {
				if (tabLicaiData2.getCreatetime().getTime() >= DateUtils.getOfDayFirst(getjndDate(new Date()))
						.getTime()) {
					m1 = m1 + tabLicaiData2.getLicaimoney();
				}
				m = m + tabLicaiData2.getLicaimoney();
			}

			map.put("curr", m1);
			map.put("total", m);
			map.put("flag", "true");
			map.put("data", tabLicaiDatas);
		}
		return map;
	}



	@RequestMapping(value = "listTongDao")
	@ResponseBody
	public Map<String, Object> listTongDao(HttpServletRequest request, HttpServletResponse response) {
		DictData dictData = new DictData();
		dictData.setDictType("tdlx");
		List<DictData> list = dictDataService.findList(dictData);

		List<TabTongdao> tabTongdaos2 = new ArrayList<TabTongdao>();
		List<TabTongdao> tabTongdaos  = new ArrayList<TabTongdao>();
		/*TabTongdao obj = new TabTongdao();
		obj.setStatus1(1);
		List<TabTongdao> lsit  =  tabTongdaoService.findList(obj);


		for (int i=0;i<lsit.size();i++) {
			if(lsit.get(i).getDaishou().equals("2")) {
				tabTongdaos2.add(lsit.get(i));
			}
			if(lsit.get(i).getDaifu().equals("2") && tabTongdaos.size() < 2) {
				tabTongdaos.add(lsit.get(i));
			}
		}*/
		for(int i=0;i<list.size();i++){
			TabTongdao tabTongdao = new TabTongdao();
			tabTongdao.setRowid(UUID.randomUUID().toString().replaceAll("-",""));
			tabTongdao.setImgsrc(list.get(i).getDescription());
			tabTongdao.setType(list.get(i).getDictValue());

			tabTongdaos2.add(tabTongdao);
			tabTongdaos.add(tabTongdao);
		}

		map.put("data2", tabTongdaos2);
		map.put("data", tabTongdaos);
		return map;
	}

	@RequestMapping(value = "getUserYe")
	@ResponseBody
	public Map<String, Object> getUserYe(@RequestBody Map request) {
		String token = request.get("token").toString();
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			String userId = list.get(0).getRowid();
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = list.get(0).getCurrentmoney();
			map.put("money",m);
			/*if(m>0){
				String mstr = new BigDecimal(m).setScale(3,BigDecimal.ROUND_UP).toString();
				map.put("money", Double.parseDouble(mstr.substring(0,mstr.length()-1)));
			}*/
		}
		return map;
	}

	@RequestMapping(value = "getUserTyj")
	@ResponseBody
	public Map<String, Object> getUserTyj(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			Double m = 0.0d;
			TabUserData userData = list.get(0);
			if("1".equals(userData.getIstiyan())){
				String userId = userData.getRowid();
				Map<String, String> parame = new HashMap<String, String>();
				parame.put("userid", userId);
				m = tabTyjJiesuanService.getUserMoney(parame);
			}

			map.put("money", m);
		}
		return map;
	}

	@RequestMapping(value = "clearUserTIYANStatus")
	public void clearUserTIYANStatus(){
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//String str = format.format(getjndDate(new Date()))+" 00:00:00";
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String str = format1.format(getjndDate(new Date()));
		System.out.println(str+"开始清除vip体验到期状态");

		List<TabUserData> list = tabUserDataService.getListByValidate(str);
		for(TabUserData tabUserData : list ){
			try {
				if(StringUtils.isBlank(tabUserData.getAccesstoken())){
					tabUserData.setAccesstoken(tabUserData.getRowid());
				}
				tabUserData.setIstiyan("0");
				tabUserData.setTyj(0D);
				tabUserData.setVip(0L);
				List<TabUserData> data = new ArrayList<>();
				data.add(tabUserData);
				jedis.set(UserDataByToken+tabUserData.getAccesstoken(), JSON.toJSONString(data));
				jedis.expire(UserDataByToken+tabUserData.getAccesstoken(),7200);

				/*String  data1  = jedis.get(TOKEN+tabUserData.getAccesstoken());
				org.json.JSONObject  jsonObject  =  new org.json.JSONObject();
				if (StringUtils.isBlank(data1) ||  data1.equals("{}")) {
					List<TabUserData> tabUserDatas  = getUserDataByToken(tabUserData);
					tabUserData =  tabUserDatas.get(0);
					jsonObject.put("rowid", tabUserData.getRowid());
					jsonObject.put("vip",tabUserData.getVip().toString());
				}else{
					jsonObject = new org.json.JSONObject(data1);
					jsonObject.put("vip",tabUserData.getVip().toString());
				}
				jedis.set(TOKEN+tabUserData.getAccesstoken(), jsonObject.toString());
				jedis.expire(TOKEN+tabUserData.getAccesstoken(), 7200);*/
			}catch (Exception e){
				System.out.println("清除体验vip状态报错："+tabUserData.getUserid());
				e.printStackTrace();
				continue;
			}


		}
		tabUserDataService.updateListByValidate(str);
		jedis.quit();
	}

	@RequestMapping(value = "clearOrderStatus1")
	public void clearOrderStatus2(){
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String day = format.format(getjndDate(new Date()));

		String str = format1.format(getjndDate(new Date()));
		System.out.println(str+"开始清除前一日未完成订单");
		List<TabOrders> list = tabOrdersService.getListByStatus1(str);
		delValuesByKeys(jedis);

		CompletableFuture<Void> runAsync2 = CompletableFuture.runAsync(() -> {
			try {
				this.clearUserTIYANStatus();
			}catch (Exception e){

			}
		});


		CompletableFuture<Void> runAsync3 = CompletableFuture.runAsync(() -> {
			try {
				taskJob.jisuan31();
			}catch (Exception e){

			}
		});

		CompletableFuture<Void> runAsync4 = CompletableFuture.runAsync(() -> {
			try {
				this.clearOrderStatus();
			}catch (Exception e){

			}
		});

		CompletableFuture<Void> runAsync5 = CompletableFuture.runAsync(() -> {
			this.subordinatesRefund(null);
		});


		for(TabOrders tabOrders1 : list){
			try {
				String orderId = tabOrders1.getOrderid();
				if(tabOrders1.getIstiyan().equals("0")){
					TabOrderData tabOrderData = new TabOrderData();
					tabOrderData.setOrderid(orderId);
					tabOrderData.setStatus1("2");
					List<TabOrderData> orderDatas = tabOrderDataService.findList(tabOrderData);
					BigDecimal amount = new BigDecimal(0);
					for(TabOrderData bean : orderDatas){
						if(bean.getStatus1().equals("2")){
							amount = amount.add(new BigDecimal(bean.getOrdermonry()));
						}
					}

					TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
					tabJiesuanLog.setUserid(tabOrders1.getUserid());
					tabJiesuanLog.setCmd("未完成订单返回本金");
					tabJiesuanLog.setType("未完成订单返回本金");
					tabJiesuanLog.setAmont(amount.doubleValue());
					tabJiesuanLog.setRowid(UUID.randomUUID().toString());
					tabJiesuanLog.setCreatetime(new ApiController().getjndDate(new Date()));
					tabJiesuanLog.setIsNewRecord(true);
					tabJiesuanLog.setMsg("Principal refund");
					tabJiesuanLog.setStatus1(tabOrders1.getOrderid());
					tabJiesuanLog.setShangjilink(tabOrders1.getShangjilink());
					tabJiesuanLog.setParentid1(tabOrders1.getParentid1());
					tabJiesuanLog.setParentid2(tabOrders1.getParentid2());
					tabJiesuanLog.setParentid3(tabOrders1.getParentid3());
					tabJiesuanLogService.save(tabJiesuanLog);
					dd(tabJiesuanLog,jedis);
					//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
				}

				tabOrderDataService.deleteListByOrderId(orderId);

				//tabJiesuanLogService.deleteListByOrderId(orderId);

				tabTyjJiesuanService.deleteListByOrderId(orderId);

				tabOrdersService.delete(tabOrders1);
				//jedis.del(isBuyProDuct+tabOrders1.getUserid());
			}catch (Exception e){
				System.out.println("清除前一日未完成订单报错："+tabOrders1.getUserid());
				e.printStackTrace();
				continue;
			}
		}
		jedis.quit();
		System.out.println("clearOrderStatus1任务执行完成");
	}

	@RequestMapping(value = "subordinatesRefund")
	public void subordinatesRefund(String time){
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		if(time == null){
			Integer refundDay = 30;
			String refundDayStr = jedis.get("refundDay");
			if(refundDayStr != null){
				refundDay = Integer.parseInt(refundDayStr);
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(getjndDate(new Date()));
			calendar.add(Calendar.DAY_OF_YEAR, -refundDay);
			Date date = calendar.getTime();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:05");
			time = format1.format(date);
		}

		List<TabRechangeLog> list = tabRechangeLogService.getTabRechangeLogList(time);
		for(int i=0; i<list.size(); i++){
			TabRechangeLog rechangeLog = list.get(i);
			rechangeLog.setIsrefund(1);
			tabRechangeLogService.save(rechangeLog);

			TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
			tabJiesuanLog.setUserid(rechangeLog.getUserid());
			tabJiesuanLog.setCmd("Superior Recharge Refund");
			tabJiesuanLog.setType("Superior Recharge Refund");
			tabJiesuanLog.setMsg("Superior Recharge Refund");
			tabJiesuanLog.setAmont(rechangeLog.getHzhb()*-1);
			tabJiesuanLog.setRowid(UUID.randomUUID().toString());
			tabJiesuanLog.setCreatetime(getjndDate(new Date()));
			tabJiesuanLog.setIsNewRecord(true);
			tabJiesuanLog.setStatus1(rechangeLog.getOrderid());
			tabJiesuanLog.setShangjilink(rechangeLog.getShangjilink());
			tabJiesuanLog.setParentid1(rechangeLog.getParentid1());
			tabJiesuanLog.setParentid2(rechangeLog.getParentid2());
			tabJiesuanLog.setParentid3(rechangeLog.getParentid3());
			tabJiesuanLogService.save(tabJiesuanLog);
			dd(tabJiesuanLog,jedis);

			TabJiesuanLog tabJiesuanLog1 = new TabJiesuanLog();
			tabJiesuanLog1.setUserid(rechangeLog.getUserid1());
			tabJiesuanLog1.setCmd("Subordinates "+rechangeLog.getUserid()+" Refund");
			tabJiesuanLog1.setType("Subordinates "+rechangeLog.getUserid()+" Refund");
			tabJiesuanLog1.setMsg("Subordinates "+rechangeLog.getUserid()+" Refund");
			tabJiesuanLog1.setAmont(rechangeLog.getHzhb());
			tabJiesuanLog1.setRowid(UUID.randomUUID().toString());
			tabJiesuanLog1.setCreatetime(getjndDate(new Date()));
			tabJiesuanLog1.setIsNewRecord(true);
			tabJiesuanLog1.setStatus1(rechangeLog.getOrderid());
			tabJiesuanLog1.setShangjilink(rechangeLog.getShangjilink());
			tabJiesuanLog1.setParentid1(rechangeLog.getParentid1());
			tabJiesuanLog1.setParentid2(rechangeLog.getParentid2());
			tabJiesuanLog1.setParentid3(rechangeLog.getParentid3());
			tabJiesuanLogService.save(tabJiesuanLog1);
			dd(tabJiesuanLog1,jedis);
		}
		jedis.quit();
	}

	@RequestMapping(value = "clearOrderStatus3")
	public void clearOrderStatus3(){
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String day = format.format(getjndDate(new Date()));

		String str = format1.format(getjndDate(new Date()));
		System.out.println(str+"开始清除前一日未完成订单");
		List<TabOrders> list = tabOrdersService.getListByStatus1(str);


		for(TabOrders tabOrders1 : list){
			try {
				String orderId = tabOrders1.getOrderid();

				if(tabOrders1.getIstiyan().equals("0")){
					TabOrderData tabOrderData = new TabOrderData();
					tabOrderData.setOrderid(orderId);
					tabOrderData.setStatus1("2");
					List<TabOrderData> orderDatas = tabOrderDataService.findList(tabOrderData);
					BigDecimal amount = new BigDecimal(0);
					for(TabOrderData bean : orderDatas){
						if(bean.getStatus1().equals("2")){
							amount = amount.add(new BigDecimal(bean.getOrdermonry()));
						}
					}
					TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
					tabJiesuanLog.setUserid(tabOrders1.getUserid());
					tabJiesuanLog.setCmd("未完成订单返回本金");
					tabJiesuanLog.setType("未完成订单返回本金");
					tabJiesuanLog.setAmont(amount.doubleValue());
					tabJiesuanLog.setRowid(UUID.randomUUID().toString());
					tabJiesuanLog.setCreatetime(new ApiController().getjndDate(new Date()));
					tabJiesuanLog.setIsNewRecord(true);
					tabJiesuanLog.setMsg("Principal refund");
					tabJiesuanLog.setStatus1(tabOrders1.getOrderid());
					tabJiesuanLog.setShangjilink(tabOrders1.getShangjilink());
					tabJiesuanLog.setParentid1(tabOrders1.getParentid1());
					tabJiesuanLog.setParentid2(tabOrders1.getParentid2());
					tabJiesuanLog.setParentid3(tabOrders1.getParentid3());
					tabJiesuanLogService.save(tabJiesuanLog);
					dd(tabJiesuanLog,jedis);
					//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
				}

				tabOrderDataService.deleteListByOrderId(orderId);

				//tabJiesuanLogService.deleteListByOrderId(orderId);

				tabTyjJiesuanService.deleteListByOrderId(orderId);

				tabOrdersService.delete(tabOrders1);

				jedis.del(isBuyProDuct+tabOrders1.getUserid());
			}catch (Exception e){
				System.out.println("清除前一日未完成订单报错："+tabOrders1.getUserid());
				e.printStackTrace();
				continue;
			}
		}
		jedis.quit();
	}

	@RequestMapping(value = "clearUserOrderStatus")
	@ResponseBody
	public void clearOrderStatus(){
		tabUserDataService.clearOrderStatus();
	}


	@RequestMapping(value = "getShouru")
	@ResponseBody
	public Map<String, Object> getShouru(@RequestBody Map<String, String> request) {
		// tab_product_data
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			TabUserData userData = list.get(0);
			String userId = userData.getRowid();
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);
			//Double shouru = tabJiesuanLogService.getShouruData(parame);
			Double shouru = userData.getGrsy();
			map.put("shouru",shouru);
			/*if(shouru>0){
				String shouruStr = new BigDecimal(shouru).setScale(3,BigDecimal.ROUND_UP).toString();
				map.put("shouru",Double.parseDouble(shouruStr.substring(0,shouruStr.length()-1)));
			}*/
		}
		return map;
	}

	@RequestMapping(value = "getShouru2")
	@ResponseBody
	public Map<String, Object> getShouru2(@RequestBody Map<String, String> request) {
		// tab_product_data
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			TabUserData userData = list.get(0);
			String userId = userData.getRowid();
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);
			//Double shouru = tabJiesuanLogService.getShouruDataTuandui(parame);
			Double shouru = userData.getTdsy();
			//map.put("shouru", new BigDecimal(shouru).setScale(2,BigDecimal.ROUND_DOWN));
			map.put("shouru",shouru);
			//获取
			Date mm =  DateUtils.getOfDayFirst(getjndDate(new Date()));
			parame.put("mm", DateUtils.formatDate(mm));
			Double todayShouru = tabJiesuanLogService.getShouruDataTuandui(parame);
			//Double todayShouru = userData.getTdsy();
			//map.put("todayShouru", new BigDecimal(todayShouru).setScale(2,BigDecimal.ROUND_DOWN));
			map.put("todayShouru",todayShouru);
		}
		return map;
	}

	@PostMapping(value = "saveBankData")
	@ResponseBody
	public Map saveBankData(@RequestBody Map<String, String> httpServletRequest) {
		String rowid = httpServletRequest.get("rowid");
		String name = httpServletRequest.get("name");
		String phone = httpServletRequest.get("phone");
		String type = httpServletRequest.get("type");

		String token = httpServletRequest.get("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			TabUserData userData = list.get(0);
			token = userData.getRowid();
			DictData dictData = new DictData();
			dictData.setDictType("tdlx");
			dictData.setDictValue(type);
			List<DictData> dictDataList = dictDataService.findList(dictData);

			if (!StringUtils.isBlank(rowid)) {
				TabUserBank userBank = tabUserBankService.get(rowid);
				userBank.setFirstname(name);
				userBank.setLastname(phone);
				userBank.setTransitnumber(type);
				userBank.setUserid(token);
				userBank.setParentid1(userData.getParentid());
				userBank.setParentid2(userData.getParentid1());
				userBank.setParentid3(userData.getParenti3());
				userBank.setShangjilink(userData.getShangjilink());
				userBank.setYgzh(userData.getYgzh());
				userBank.setYgzh2(userData.getYgzh2());
				userBank.setImgsrc(dictDataList.size()>0?dictDataList.get(0).getDescription():"");
				tabUserBankService.save(userBank);
			} else {
				TabUserBank tabUserBank = new TabUserBank();
				tabUserBank.setFirstname(name);
				tabUserBank.setTransitnumber(type);

				List<TabUserBank> tabUserBanks = tabUserBankService.findList(tabUserBank);
				if (tabUserBanks.size() > 0) {
					map.put("flag", "false");
					map.put("msg", "Wallet address already exists");
				} else {
					TabUserBank userBank = new TabUserBank();
					userBank.setFirstname(name);
					userBank.setLastname(phone);
					userBank.setTransitnumber(type);
					userBank.setUserid(token);
					userBank.setRowid(UUID.randomUUID().toString());
					userBank.setIsNewRecord(true);
					userBank.setCreatetime(getjndDate(new Date()));
					userBank.setParentid1(userData.getParentid());
					userBank.setParentid2(userData.getParentid1());
					userBank.setParentid3(userData.getParenti3());
					userBank.setShangjilink(userData.getShangjilink());
					userBank.setYgzh(userData.getYgzh());
					userBank.setYgzh2(userData.getYgzh2());
					userBank.setImgsrc(dictDataList.size()>0?dictDataList.get(0).getDescription():"");
					tabUserBankService.save(userBank);
					map.put("flag", "true");
				}
			}
		}

		return map;
	}

	@RequestMapping(value = "getTiXian")
	@ResponseBody
	public Map getTiXian(@RequestBody Map<String, String> httpServletRequest) {

		String token = httpServletRequest.get("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list.size() > 0) {
			map.put("code", 0);
			map.put("flag", "true");

			TabUserData userData = list.get(0);

			String tx = userData.getTxpassword();
			if (!StringUtils.isBlank(tx)) {
				map.put("rr", "1");
			} else {
				map.put("rr", "0");

			}
		} else {
			map.put("code", 1);

		}
		return map;
	}

	@RequestMapping(value = "getJinDuTiao")
	@ResponseBody
	public Map getJinDuTiao(@RequestBody Map<String, String> httpServletRequest) {
		String token = httpServletRequest.get("token");

		List<TabRechargeJiangli> tabRechargeJianglis = tabRechargeJiangliService.findList(new TabRechargeJiangli());

		List<TabYaoqingJiangli> tabYaoqingJianglis = tabYaoqingJiangliService.findList(new TabYaoqingJiangli());

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		if (list != null && list.size() > 0) {
			tabUserData = list.get(0);
			String userId = list.get(0).getRowid();

			Map<String, String> parame = new HashMap<String, String>();

			parame.put("parentId", userId);

			Long cc2  =   tabRechangeLogService.getTotalRenshu(parame);

			if(userId.equals("759010248")) {
				cc2++;
			}

			Long max1 = 20L;
			for (TabYaoqingJiangli tabYaoqingJiangli : tabYaoqingJianglis) {
				max1 = tabYaoqingJiangli.getXjrs();
				if (tabYaoqingJiangli.getXjrs() > cc2) {
					break;
				} else {
					TabYaoqingJiangliLog tabYaoqingJiangliLog = new TabYaoqingJiangliLog();

					tabYaoqingJiangliLog.setUserid(userId);

					tabYaoqingJiangliLog.setJlbh(tabYaoqingJiangli.getJlbh());

					Long ccN = tabYaoqingJiangliLogService.findCount(tabYaoqingJiangliLog);

					if (ccN == 0) {

						tabYaoqingJiangliLog.setXjrs(tabYaoqingJiangli.getXjrs());
						tabYaoqingJiangliLog.setJlje(tabYaoqingJiangli.getJlje());
						tabYaoqingJiangliLog.setCreatetime(getjndDate(new Date()));
						tabYaoqingJiangliLog.setRowid(userId + tabYaoqingJiangli.getJlbh());

						tabYaoqingJiangliLog.setParentid1(tabUserData.getParentid());
						tabYaoqingJiangliLog.setParentid2(tabUserData.getParentid1());
						tabYaoqingJiangliLog.setParentid3(tabUserData.getParenti3());
						tabYaoqingJiangliLog.setShangjilink(tabUserData.getShangjilink());
						tabYaoqingJiangliLog.setYgzh(tabUserData.getYgzh());
						tabYaoqingJiangliLog.setYgzh2(tabUserData.getYgzh2());

						tabYaoqingJiangliLog.setIsNewRecord(true);

						// tabUserData

						tabYaoqingJiangliLogService.save(tabYaoqingJiangliLog);

						TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();

						tabJiesuanLog.setUserid(tabUserData.getRowid());
						tabJiesuanLog.setAmont(tabYaoqingJiangli.getJlje());
						tabJiesuanLog.setRowid(UUID.randomUUID().toString());
						tabJiesuanLog.setCmd("Commission");
						tabJiesuanLog.setType("邀请奖励");
						tabJiesuanLog.setMsg("Extra rewards");
						tabJiesuanLog.setCreatetime(getjndDate(new Date()));
						tabJiesuanLog.setIsNewRecord(true);
						tabJiesuanLog.setParentid1(tabUserData.getParentid());
						tabJiesuanLog.setParentid2(tabUserData.getParentid1());
						tabJiesuanLog.setParentid3(tabUserData.getParenti3());
						tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());

						tabJiesuanLogService.save(tabJiesuanLog);

						dd(tabJiesuanLog,jedis);
						//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
						//getuservip(tabJiesuanLog.getUserid());
					}
				}
			}

			map.put("curr", cc2);
			map.put("max1", max1);
			// 获取充值金额
			parame = new HashedMap();
			parame.put("userId", userId);

			Double czje = tabRechangeLogService.getXjcz(parame);

			Double max2 = 0D;

			for (TabRechargeJiangli tabRechargeJiangli : tabRechargeJianglis) {

				max2 = tabRechargeJiangli.getXjrs().doubleValue();
				if (tabRechargeJiangli.getXjrs() > czje.longValue()) {

					break;
				} else {

					TabRechargeJiangliLog tabYaoqingJiangliLog = new TabRechargeJiangliLog();

					tabYaoqingJiangliLog.setUserid(userId);

					tabYaoqingJiangliLog.setJlbh(tabRechargeJiangli.getJlbh());

					Long ccN = tabRechargeJiangliLogService.findCount(tabYaoqingJiangliLog);

					if (ccN == 0) {

						tabYaoqingJiangliLog.setXjrs(tabRechargeJiangli.getXjrs());
						tabYaoqingJiangliLog.setJlje(tabRechargeJiangli.getJlje());
						tabYaoqingJiangliLog.setCreatetime(getjndDate(new Date()));
						tabYaoqingJiangliLog.setRowid(userId + tabRechargeJiangli.getJlbh());

						tabYaoqingJiangliLog.setIsNewRecord(true);

						tabYaoqingJiangliLog.setParentid1(tabUserData.getParentid());
						tabYaoqingJiangliLog.setParentid2(tabUserData.getParentid1());
						tabYaoqingJiangliLog.setParentid3(tabUserData.getParenti3());
						tabYaoqingJiangliLog.setShangjilink(tabUserData.getShangjilink());
						tabYaoqingJiangliLog.setYgzh(tabUserData.getYgzh());
						tabYaoqingJiangliLog.setYgzh2(tabUserData.getYgzh2());

						tabRechargeJiangliLogService.save(tabYaoqingJiangliLog);

						TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();

						tabJiesuanLog.setUserid(tabUserData.getRowid());
						tabJiesuanLog.setAmont(tabRechargeJiangli.getJlje());
						tabJiesuanLog.setRowid(UUID.randomUUID().toString());
						tabJiesuanLog.setCmd("Commission");
						tabJiesuanLog.setType("充值奖励");
						tabJiesuanLog.setMsg("Extra rewards");
						tabJiesuanLog.setCreatetime(getjndDate(new Date()));
						tabJiesuanLog.setIsNewRecord(true);
						tabJiesuanLog.setParentid1(tabUserData.getParentid());
						tabJiesuanLog.setParentid2(tabUserData.getParentid1());
						tabJiesuanLog.setParentid3(tabUserData.getParenti3());
						tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());

						tabJiesuanLogService.save(tabJiesuanLog);

						dd(tabJiesuanLog,jedis);
						//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
						//getuservip(tabJiesuanLog.getUserid());
					}
				}
			}
			map.put("czje", czje);
			map.put("max2", max2);
		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = "getKeFuDh")
	@ResponseBody
	public Map getKeFuDh(@RequestBody Map<String, String> request) throws Exception {
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		if (list.size() > 0) {
			TabUserData userData = list.get(0);
			String ygzh2 = userData.getYgzh2();
			if(StringUtils.isNotBlank(ygzh2)){
				TabContaceService tabContaceService = tabContaceServiceService.get(ygzh2);
				if(tabContaceService != null){
					map.put("data", tabContaceService.getWhatsapp());
				}
			}
			/*String ygzh = userData.getShangjilink();
			String[] ss = ygzh.split(",");
			if (ss.length >= 2) {
				for (String s : ss) {
					System.out.println(ygzh);
					TabYuangongData ooo = new TabYuangongData();
					ooo.setQtzh(s);
					List<TabYuangongData> yuangongDatas = tabYuangongDataService.findList(ooo);
					if (yuangongDatas.size() > 0) {
						TabYuangongData tabYuangongData = yuangongDatas.get(0);
						String daili = tabYuangongData.getDaili();
						System.out.println(daili);
						TabContaceService tabContaceService = tabContaceServiceService.get(daili);
						if (tabContaceService != null) {
							map.put("data", tabContaceService.getWhatsapp());
							break;
						}
					}
				}
			} else {
				for (String s : ss) {
					if (!StringUtils.isBlank(s)) {
						TabContaceService tabContaceService = tabContaceServiceService.get(s);
						if (tabContaceService != null) {
							map.put("data", tabContaceService.getWhatsapp());
							break;
						}
					}
				}
			}*/
		}
		return map;
	}

	@RequestMapping(value = "toWhatsApp")
	@ResponseBody
	public Map toWhatsApp(@RequestBody Map<String, String> request) throws Exception {
		String userid = request.get("userid");
		if(StringUtils.isBlank(userid)){
			map.put("code",0);
			map.put("flag",false);
			map.put("msg","The account cannot be empty!");
			return map;
		}
		TabUserData tabUserData = tabUserDataService.get(userid);
		if (tabUserData == null) {
			map.put("code",0);
			map.put("flag",false);
				map.put("msg","Account does not exist!");
			return map;
		}
		String ygzh2 = tabUserData.getYgzh2();
		if(StringUtils.isNotBlank(ygzh2)){
			TabContaceService tabContaceService = tabContaceServiceService.get(ygzh2);
			if(tabContaceService != null){
				map.put("code",1);
				map.put("flag",true);
				map.put("data", tabContaceService.getWhatsapp());
			}
		}
		return map;
	}


	@RequestMapping(value = "activeHome")
	@ResponseBody
	public Map<String, Object> activeHome(TabTongjiDays tabTongjiDays, HttpServletRequest request,
										  HttpServletResponse response) {
		List<TabActiveImgs> tabActiveImgs = tabActiveImgsService.findList(new TabActiveImgs());
		map.put("list1", tabActiveImgs.get(0));
		tabActiveImgs.remove(0);
		map.put("list2", tabActiveImgs);
		map.put("code", 0);
		map.put("flag", "true");
		return map;
	}

	@RequestMapping(value = "activeHomeDetail")
	@ResponseBody
	public Map<String, Object> activeHomeDetail(@RequestBody Map<String, String> parame) {
		String rowid = parame.get("rowid");
		map.put("code", 0);
		TabActiveImgs tabActiveImgs = tabActiveImgsService.get(rowid);
		Date  c =  getjndDate(new Date()) ;
		map.put("data", tabActiveImgs);
		if (tabActiveImgs.getEndtime().getTime() >= c.getTime()) {
			map.put("flag", "true");
		}else {
			map.put("flag", "false");
			map.put("msg", "The activity is now closed");
		}

		return map;
	}

	@RequestMapping(value = "listHuoDongJiangli")
	@ResponseBody
	public Map<String, Object> listHuoDongJiangli(@RequestBody Map<String, String> request) throws Exception {
		List<TabYaoqingJiangliHuodong> yaoqingJiangliHuodongs = tabYaoqingJiangliHuodongService
				.findList(new TabYaoqingJiangliHuodong());
		String token = request.get("token").toString();
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		if(list.get(0).getJianglicishu() == 0){
			map.put("flag", "false");
			map.put("msg", "");
			map.put("count", 0);
		}else{
			map.put("count", list.get(0).getJianglicishu());
			map.put("flag", "true");
			map.put("msg", "There are currently no raffles");
		}
		map.put("data", yaoqingJiangliHuodongs);
		//查看用户抽奖次数
		map.put("code", 0);
		return map;
	}

	@RequestMapping(value = "getHuoDongJiangLi")
	@ResponseBody
	public Map<String, Object> getHuoDongJiangLi(@RequestBody Map<String, String> request) {
		//String orderId = request.get("orderId");
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		if (list.size() > 0) {
			map.put("code", 0);
			TabUserData userData = list.get(0);
			if(userData.getJianglicishu() < 1){
				map.put("flag", "false");
				map.put("msg", "There is no number of draws");
				return map;
			}
			userData.setJianglicishu(userData.getJianglicishu() - 1);
			List<TabUserData> lists1 = new ArrayList<>();
			lists1.add(userData);
			jedis.set(UserDataByToken+userData.getAccesstoken(),JSON.toJSONString(lists1));
			jedis.expire(UserDataByToken+userData.getAccesstoken(),7200);
			tabUserDataService.save(userData);
			String rowid = userData.getRowid();
			int d = new Random().nextInt(1001);
			//if(!StringUtils.isBlank(orderId)) {
			//TabTemps tabTemps = tabTempsService.get(orderId);
			//if (tabTemps != null) {
			List<TabYaoqingJiangliHuodong> yaoqingJiangliHuodongs = tabYaoqingJiangliHuodongService
					.findList(new TabYaoqingJiangliHuodong());
			TabYaoqingJiangliHuodong result = new TabYaoqingJiangliHuodong();
			Long pp = 0L;
			int index = 0;
			for (TabYaoqingJiangliHuodong tabYaoqingJiangliHuodong : yaoqingJiangliHuodongs) {
				pp = pp + tabYaoqingJiangliHuodong.getJlbl().intValue() * 10;
				if (pp >= d) {
					result = tabYaoqingJiangliHuodong;
					break;
				}

				index++;
			}
			if(StringUtils.isBlank(result.getRowid())) {
				result  =  yaoqingJiangliHuodongs.get(0);
			}
			TabYaoqingJiangliHuodongLog obbb = new TabYaoqingJiangliHuodongLog();
			obbb.setRowid(UUID.randomUUID().toString());
			obbb.setUserid(rowid);
			obbb.setJlbh(result.getJlmc());
			obbb.setJlje(result.getJlje());
			obbb.setCreatetime(getjndDate(new Date()));
			obbb.setIsNewRecord(true);

			obbb.setParentid1(userData.getParentid());
			obbb.setParentid2(userData.getParentid1());
			obbb.setParentid3(userData.getParenti3());
			obbb.setShangjilink(userData.getShangjilink());
			obbb.setYgzh(userData.getYgzh());
			obbb.setYgzh2(userData.getYgzh2());

			tabYaoqingJiangliHuodongLogService.save(obbb);

			TabJiesuanLog jiesuanLog = new TabJiesuanLog();
			jiesuanLog.setUserid(userData.getRowid());
			jiesuanLog.setAmont(result.getJlje());
			jiesuanLog.setCmd("Bonus Roulette");
			jiesuanLog.setType("转盘奖励");
			jiesuanLog.setMsg("Lucky spin reward");
			jiesuanLog.setParentid1(userData.getParentid());
			jiesuanLog.setParentid2(userData.getParentid1());
			jiesuanLog.setParentid3(userData.getParenti3());
			jiesuanLog.setShangjilink(userData.getShangjilink());

			jiesuanLog.setCreatetime(getjndDate(new Date()));
			jiesuanLog.setIsNewRecord(true);
			tabJiesuanLogService.save(jiesuanLog);

			dd(jiesuanLog,jedis);
			//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
			map.put("index", index);
			map.put("data", result);
			map.put("flag", "true");


			//}
			/*}else {
				map.put("flag", "false");
				map.put("msg", "No time");
			}*/
		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = "getContents")
	@ResponseBody
	public Map<String, Object> getContents(TabTongjiDays tabTongjiDays, HttpServletRequest request,
										   HttpServletResponse response) {

		TabYaoqingPeizhi obj = new TabYaoqingPeizhi();
		List<TabYaoqingPeizhi> tabYaoqingPeizhis = tabYaoqingPeizhiService.findList(obj);
		List<TabYaoqingJiangliHuodongLog>  list  =  tabYaoqingJiangliHuodongLogService.findList22();
		if(list.size()> 0) {
			tabYaoqingPeizhis  = new ArrayList<TabYaoqingPeizhi>();
			for (TabYaoqingJiangliHuodongLog tabYaoqingJiangliHuodongLog : list) {
				TabYaoqingPeizhi tabYaoqingPeizhi   = new TabYaoqingPeizhi();
				tabYaoqingPeizhi.setContent(tabYaoqingJiangliHuodongLog.getUserid().substring(0,3 )+"****" + tabYaoqingJiangliHuodongLog.getUserid().substring(7) +"  get lucky spin rewards: " + tabYaoqingJiangliHuodongLog.getJlje() +" USh");
				tabYaoqingPeizhis.add(tabYaoqingPeizhi);
			}
		}

		map.put("data", tabYaoqingPeizhis);
		return map;
	}

	@RequestMapping(value = "getChouJingData")
	@ResponseBody
	public Map<String, Object> getChouJingData(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			// map.put("vip", list.get(0).getVip()) ;
			TabYaoqingJiangliHuodongLog tabTongjiDays = new TabYaoqingJiangliHuodongLog();
			tabTongjiDays.setUserid(list.get(0).getRowid());

			List<TabYaoqingJiangliHuodongLog> tabRechangeLogs = tabYaoqingJiangliHuodongLogService
					.findList(tabTongjiDays);
			map.put("data", tabRechangeLogs);
		}

		return map;
	}

	@RequestMapping(value = "listUserAddress")
	@ResponseBody
	public Map<String, Object> listUserAddress(@RequestBody TabUserAddress tabUserAddress) {
		String token = tabUserAddress.getToken();
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> tabUserDatas  = getUserDataByToken(tabUserData);
		if(tabUserDatas.size() == 0){
			map.put("code", 1);
			map.put("flag", "false");
		}else{
			map.put("code", 0);
			map.put("flag", "true");
			String  redisss  =  jedis.get("ADDRESS:"+tabUserDatas.get(0).getRowid() + "_address");
			TabUserAddress tabUserAddress2  =   null;
			if (StringUtils.isBlank(redisss) ||  redisss.equals("{}")) {
				tabUserAddress2 = tabUserAddressService.get(tabUserDatas.get(0).getRowid());
				if(tabUserAddress2 != null ) {
					org.json.JSONObject object  = new org.json.JSONObject();
					object.put("rowid", tabUserAddress2.getUserid()) ;
					jedis.set("ADDRESS:" + tabUserDatas.get(0).getRowid() + "_address",JSON.toJSONString(tabUserAddress2));
					jedis.expire("ADDRESS:" + tabUserDatas.get(0).getRowid() + "_address",3600);
				}
			}else {
				tabUserAddress2 = JSON.parseObject(redisss,TabUserAddress.class);
			}

			map.put("data", tabUserAddress2);
		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = "orderDetail")
	@ResponseBody
	public Map<String, Object> orderDetail(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		String wzfOrder = request.get("wzfOrder");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			String userId = list.get(0).getRowid();

			TabOrderData tabOrderData = tabOrderDataService.get(wzfOrder);
			TabOrders tabOrders = new TabOrders();
			tabOrders.setOrderid(tabOrderData.getOrderid());
			List<TabOrders> tabOrders1 = tabOrdersService.findList(tabOrders);
			if(tabOrders1 != null && tabOrders1.size()>0){
				if("1".equals(tabOrders1.get(0).getVip())){
					map.put("mgrwdyj","4%");
				}else if("2".equals(tabOrders1.get(0).getVip())){
					map.put("mgrwdyj","4.5%");
				}else if("3".equals(tabOrders1.get(0).getVip())){
					map.put("mgrwdyj","5%");
				}else if("4".equals(tabOrders1.get(0).getVip())){
					map.put("mgrwdyj","5.5%");
				}
			}
			map.put("orders", tabOrderData);

			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");
			String pp = tabOrderData.getProductid();
			String productStr = jedis.get(ProDuct+pp);
			TabProductData tabProductData = null;
			if(StringUtils.isBlank(productStr)){
				tabProductData = tabProductDataService.get(pp);
				jedis.set(ProDuct+pp,JSON.toJSONString(tabProductData));
			}else{
				tabProductData = JSON.parseObject(productStr,TabProductData.class);
			}
			jedis.quit();

			map.put("tabProductData", tabProductData);
			map.put("flag", "true");

			TabOrderData tabOrderData3 = new TabOrderData();
			tabOrderData3.setUserid(userId);
			tabOrderData3.setCreatetime_gte(DateUtils.getOfDayFirst(getjndDate(new Date())));

			List<TabOrderData> tabUserDatas = tabOrderDataService.findList(tabOrderData3);

			int i = 0;
			Double totalmoney = 0d;

			for (TabOrderData tabOrderData2 : tabUserDatas) {
				if (tabOrderData2.getStatus1().equals("2") || tabOrderData2.getStatus1().equals("3")
						|| tabOrderData2.getStatus1().equals("4")) {
					i++;
					totalmoney = totalmoney + tabOrderData2.getRebatemoney();
				}
			}

			map.put("totalCount", i);
			//map.put("sumPre", new BigDecimal(tabOrderData.getRebatemoney()).setScale(3,BigDecimal.ROUND_DOWN));
			map.put("sumPre",tabOrderData.getRebatemoney());
			// 计算余额
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);

			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = list.get(0).getCurrentmoney();
			//map.put("m", new BigDecimal(m).setScale(3,BigDecimal.ROUND_DOWN));
			map.put("m",m);
			map.put("flag", "true");

		}
		return map;
	}

	@RequestMapping(value = "listLiCai")
	@ResponseBody
	public Map<String, Object> listLiCai(@RequestBody Map<String, String> request) {
		TabLicaiList tabLicaiList = new TabLicaiList();
		List<TabLicaiList> tabLicaiLists = tabLicaiListService.findList(tabLicaiList);
		map.put("data", tabLicaiLists);
		map.put("flag", "true");
		map.put("code", 0);
		return map;
	}

	@RequestMapping(value = "listLiCaiDetail")
	@ResponseBody
	public Map<String, Object> listLiCaiDetail(@RequestBody Map<String, String> request) {
		TabLicaiList tabLicaiList = new TabLicaiList();
		String rowid = request.get("rowid");
		tabLicaiList = tabLicaiListService.get(rowid);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getjndDate(new Date()));
		calendar.add(Calendar.DAY_OF_MONTH, Integer.valueOf(tabLicaiList.getDays()));
		tabLicaiList.setDate(calendar.getTime());
		map.put("data", tabLicaiList);
		map.put("flag", "true");
		map.put("code", 0);
		return map;
	}

	@RequestMapping(value = "getUserBanks2")
	@ResponseBody
	public Map<String, Object> getUserBanks2(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			tabUserData = list.get(0);

			map.put("flag", "true");

			String rowid = tabUserData.getRowid();
			TabUserBank tabUserBank = new TabUserBank();
			tabUserBank.setUserid(rowid);
			List<TabUserBank> tabUserBanks = tabUserBankService.findList(tabUserBank);

			/*TabTongdao tabTongdao = new TabTongdao();
			tabTongdao.setDaifu("2");
			List<TabTongdao> tongdaoList = tabTongdaoService.findList(tabTongdao);

			List<TabUserBank> data = new ArrayList<>();
			for(TabUserBank bean : tabUserBanks){
				for(TabTongdao tabTongdao1 : tongdaoList){
					if(bean.getTransitnumber().equals(tabTongdao1.getType())){
						bean.setImgsrc(tabTongdao1.getImgsrc());
						data.add(bean);
						continue;
					}
				}
			}*/

			map.put("data", tabUserBanks);
		}

		return map;
	}

	@RequestMapping(value = "checkVip")
	@ResponseBody
	public Map<String, Object> checkVip(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		List<TabVipConfig> configList = getVipConfigList(new TabVipConfig());

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			tabUserData = list.get(0);
			TabRechangeLog tabRechangeLog = new TabRechangeLog();
			tabRechangeLog.setStatus1("2");
			tabRechangeLog.setUserid(tabUserData.getRowid());
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData.getRowid());
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData.getCurrentmoney();
			map.put("flag", "true");
			map.put("code", 0);

			if ((m >= configList.get(0).getCurrentmoney() && tabUserData.getUsertype().equals("3"))
					|| tabUserData.getUsertype().equals("1") || tabUserData.getUsertype().equals("2")) {
				map.put("isVip", "2");
			} else {
				map.put("isVip", "1");
			}
			map.put("data", tabUserData);
		}
		return map;
	}

	@RequestMapping(value = "getSubUserRecharge")
	@ResponseBody
	public Map<String, Object> getSubUserRecharge(@RequestBody Map request) {
		String token = request.get("token").toString();
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			tabUserData = list.get(0);
			TabUserData  subs =    new TabUserData();
			subs.setParentid(tabUserData.getRowid());
			List<TabUserData>   subList =   tabUserDataService.findList(subs);
			List<TabUserData>    values =  new ArrayList<TabUserData>();
			for (TabUserData tabUserData2 : subList) {
				Map<String, String> parame = new HashMap<String, String>();
				parame.put("userid", tabUserData2.getRowid());
				//Double m = tabRechangeLogService.getSum(parame);
				//tabUserData2.setCzje(m);
				//Double m2 = tabJiesuanLogService.getUserMoney(parame);
				Double m2 = tabUserData2.getCurrentmoney();
				tabUserData2.setHuilv(m2+"");

				if(tabUserData2.getCzje()>=15) {
					values.add(tabUserData2);
				}
			}
			map.put("code", 0);
			map.put("data", values);
			map.put("flag", "true");
		}

		return map;
	}
	//上面是前端用到的接口

	@RequestMapping(value = "refreshVip")
	@ResponseBody
	public Map refreshVip(HttpServletRequest httpServletRequest) throws Exception {
		List<TabUserData> tabUserDatas = tabUserDataService.findList(new TabUserData());
		for(TabUserData tabUserData : tabUserDatas){
			//getuservip(tabUserData.getRowid());
		}

		return null;
	}

	private List<TabVipConfig> getVipConfigs(){
		List<TabVipConfig> tabVipConfigs = null;
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String vipString = jedis.get("vips");

		if(StringUtils.isBlank(vipString)){//缓存里没有vip
			tabVipConfigs = getVipConfigList(new TabVipConfig());
			org.json.JSONArray jsonArray  =   new org.json.JSONArray() ;
			for(TabVipConfig tabVipConfig  :tabVipConfigs) {
				org.json.JSONObject jsonObject  = new org.json.JSONObject();
				jsonObject.put("icon", tabVipConfig.getIcon());
				jsonObject.put("maxgoumai", tabVipConfig.getMaxgoumai());
				jsonObject.put("tishi", tabVipConfig.getTishi());
				jsonObject.put("rowid", tabVipConfig.getRowid());
				jsonObject.put("vip", tabVipConfig.getVip());
				jsonObject.put("name", tabVipConfig.getName());

				jsonObject.put("currentmoney", tabVipConfig.getCurrentmoney());
				jsonObject.put("xiaji", tabVipConfig.getXiaji());
				jsonObject.put("xiajijine", tabVipConfig.getXiajijine());
				jsonObject.put("mgrwdyj", tabVipConfig.getMgrwdyj());
				jsonObject.put("mrlr", tabVipConfig.getMrlr());
				jsonObject.put("maxmoney", tabVipConfig.getMaxmoney());
				jsonArray.put(jsonObject) ;
			}
			jedis.set("vips",  jsonArray.toString()) ;
		}else{
			tabVipConfigs = new ArrayList<>();
			org.json.JSONArray  array =   new org.json.JSONArray(vipString);
			for (int i = 0; i < array.length(); i++) {
				org.json.JSONObject  jsonObject  =  array.getJSONObject(i) ;
				String  rowid = jsonObject.getString("rowid") ;
				String  vip = jsonObject.getString("vip") ;
				String  name = jsonObject.getString("name") ;
				String  icon = jsonObject.getString("icon") ;
				String  tishi = jsonObject.getString("tishi") ;
				Long  xiaji = jsonObject.getLong("xiaji") ;
				Double  maxgoumai = jsonObject.getDouble("maxgoumai") ;
				Double  currentmoney = jsonObject.getDouble("currentmoney") ;
				Double  xiajijine = jsonObject.getDouble("xiajijine") ;
				Double  mgrwdyj = jsonObject.getDouble("mgrwdyj") ;
				Double  mrlr = jsonObject.getDouble("mrlr") ;
				Double  maxmoney = jsonObject.getDouble("maxmoney") ;
				TabVipConfig tabVipConfig  = new TabVipConfig();
				tabVipConfig.setRowid(rowid);
				tabVipConfig.setVip(vip);
				tabVipConfig.setName(name);
				tabVipConfig.setIcon(icon);
				tabVipConfig.setTishi(tishi);
				tabVipConfig.setXiaji(xiaji);
				tabVipConfig.setMaxgoumai(maxgoumai);
				tabVipConfig.setCurrentmoney(currentmoney);
				tabVipConfig.setXiajijine(xiajijine);
				tabVipConfig.setMgrwdyj(mgrwdyj);
				tabVipConfig.setMrlr(mrlr);
				tabVipConfig.setMaxmoney(maxmoney);
				tabVipConfigs.add(tabVipConfig);
			}
		}
		jedis.quit();
		return tabVipConfigs;
	}

	@RequestMapping(value = { "/zhuce2", "" })
	@ResponseBody
	public Map<String, Object> zhuce2(HttpServletRequest servletRequest) {

		String ips = servletRequest.getRemoteAddr();
		String mobile = servletRequest.getParameter("loginCode").toString();
		String password = servletRequest.getParameter("pass").toString();
		String nickName = servletRequest.getParameter("loginCode").toString();
		String sysuserid = servletRequest.getParameter("sysuserid").toString();
		TabUserData tabUserData = tabUserDataService.get(mobile);
		String[] dd = servletRequest.getParameterValues("email[]");
		String email = "";
		for (String string : dd) {
			email = email + string + ",";
		}
		email = email.substring(0, email.length() - 1);

		if (tabUserData == null) {

			tabUserData = new TabUserData();
			tabUserData.setParentid(null);
			tabUserData.setShangjilink("");
			tabUserData.setUserleval(0L);
			tabUserData.setPassword(password);
			tabUserData.setUsername(nickName);
			tabUserData.setRowid(mobile);
			tabUserData.setTycs(0L);
			tabUserData.setSumMember(0D);
			tabUserData.setUsertype("1");
			tabUserData.setCurrentmoney(0D);
			tabUserData.setIsNewRecord(true);
			tabUserData.setCreatetime(getjndDate(new Date()));
			tabUserData.setVip(0L);
			tabUserData.setSysuserid(sysuserid);
			tabUserData.setQuanxian(email);
			tabUserData.setCodes(getCode() + getCode());
			tabUserData.setStatus1("2");
			tabUserDataService.save(tabUserData);

			map.put("data", tabUserData);

			map.put("code", 0);

			// 开始更新

		} else {
			map.put("code", 1);
			map.put("msg", "This account already exists");
		}
		return map;
	}

	@RequestMapping(value = { "/zhuce3", "" })
	@ResponseBody
	public Map<String, Object> zhuce3(HttpServletRequest servletRequest) {

		String ips = servletRequest.getRemoteAddr();
		String mobile = servletRequest.getParameter("loginCode").toString();
		String password = servletRequest.getParameter("pass").toString();
		String nickName = servletRequest.getParameter("loginCode").toString();
		String sysuserid = servletRequest.getParameter("sysuserid").toString();
		TabUserData tabUserData = tabUserDataService.get(mobile);
		String dd = servletRequest.getParameter("email[]");

		if (tabUserData == null) {

			tabUserData = new TabUserData();
			tabUserData.setParentid(null);
			tabUserData.setShangjilink("");
			tabUserData.setUserleval(0L);
			tabUserData.setPassword(password);
			tabUserData.setUsername(nickName);
			tabUserData.setRowid(mobile);
			tabUserData.setTycs(0L);
			tabUserData.setSumMember(0D);
			tabUserData.setUsertype("1");
			tabUserData.setCurrentmoney(0D);
			tabUserData.setIsNewRecord(true);
			tabUserData.setCreatetime(getjndDate(new Date()));
			tabUserData.setVip(0L);
			tabUserData.setSysuserid(sysuserid);
			tabUserData.setQuanxian(dd);
			tabUserData.setCodes(getCode() + getCode());
			tabUserData.setStatus1("2");
			tabUserDataService.save(tabUserData);

			map.put("data", tabUserData);

			map.put("code", 0);

			// 开始更新

		} else {

			map.put("code", 1);
			map.put("msg", "This account already exists");

		}

		return map;

	}

	public static Date getjndDate(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -6);

		return calendar.getTime();
	}

	public String getCode() {

		return (new Random().nextInt(888888) + 100000) + "";
	}

	@RequestMapping(value = "get-sms-code")
	@ResponseBody
	public Map<String, Object> getSmsCode(HttpServletRequest httpServletRequest) {
		String code = getCode();
		String phone = httpServletRequest.getParameter("phone");

		TabUserData tabUserData = tabUserDataService.get(phone);

		if (tabUserData == null) {

			TabValidCode record = new TabValidCode();
			record.setCode(code);
			record.setFlag("1");
			record.setCreatetime(getjndDate(new Date()));
			record.setValidateTime(getValiDate());
			record.setPhone(phone);
			record.setRowid(UUID.randomUUID().toString());
			record.setIsNewRecord(true);

			tabValidCodeService.save(record);

			Map<String, Object> data2 = new HashMap<>();
			data2.put("smsCode", record.getCode());

			map.put("data", data2);
			map.put("code", 0);

		} else {
			map.put("code", 1);
			map.put("msg", "This account already exists");

		}

		return map;
	}

	@RequestMapping(value = "/sms-code-register")
	@ResponseBody
	public Map<String, Object> getSmsCodeRegister(HttpServletRequest httpServletRequest) {

		String mobile = httpServletRequest.getParameter("phone").toString();
		String password = httpServletRequest.getParameter("password").toString();
		String rebateCode = httpServletRequest.getParameter("code1").toString();
		String smsCode = httpServletRequest.getParameter("code").toString();
		String email = httpServletRequest.getParameter("email").toString();
		String account = httpServletRequest.getParameter("account").toString();

		TabUserData tabUserData = tabUserDataService.get(mobile);

		if (tabUserData == null) {
			tabUserData = new TabUserData();

			Map<String, String> parame = new HashMap<>();
			parame.put("phone", mobile);
			parame.put("code", smsCode);

			java.util.List<TabValidCode> tabValidateCodes = tabValidCodeService.checkValidCode(parame);

			if (tabValidateCodes.size() > 0) {
				TabValidCode tabValidCode = tabValidateCodes.get(0);
				tabValidCode.setFlag("2");
				tabValidCodeService.save(tabValidCode);

				TabUserData top = new TabUserData();
				top.setCodes(rebateCode);
				java.util.List<TabUserData> tabUserDatas = tabUserDataService.findList(top);

				if (tabUserDatas.size() > 0) {

					top = tabUserDatas.get(0);
					tabUserData.setParentid(top.getRowid());

					if (!StringUtils.isBlank(top.getParentid())) {
						tabUserData.setParentid(top.getParentid());
						tabUserData.setShangjilink(top.getShangjilink() + "," + top.getRowid());
						tabUserData.setUserleval(top.getUserleval() + 1);

					}
					if (!StringUtils.isBlank(top.getParentid1())) {
						tabUserData.setParentid1(top.getParentid1());
					}

					String ips = httpServletRequest.getRemoteAddr();

					tabUserData.setCodes(getCode());
					tabUserData.setPassword(password);
					tabUserData.setRowid(mobile);
					tabUserData.setTycs(1L);
					tabUserData.setUsername(account);
					tabUserData.setEmail(email);
					tabUserData.setUserid(getCode() + getCode().substring(0, 2));
					tabUserData.setCurrentmoney(0D);
					tabUserData.setIsNewRecord(true);
					tabUserData.setCreatetime(getjndDate(new Date()));
					tabUserData.setVip(1L);
					tabUserData.setSumMember(0D);
					tabUserDataService.save(tabUserData);

					map.put("code", 0);

					String p1 = tabUserData.getParentid();
					String p2 = tabUserData.getParentid1();

					if (!StringUtils.isBlank(p1)) {

						TabUserData userData = tabUserDataService.get(p1);
						if (userData.getSumMember() == null) {
							userData.setSumMember(0D);
						}
						userData.setSumMember(userData.getSumMember() + 1);
						tabUserDataService.save(userData);

					}

					if (!StringUtils.isBlank(p2)) {

						TabUserData userData = tabUserDataService.get(p2);
						if (userData.getSumMember() == null) {
							userData.setSumMember(0D);
						}
						userData.setSumMember(userData.getSumMember() + 1);
						tabUserDataService.save(userData);

					}

				} else {

					map.put("code", 1);
					map.put("msg", "Bad invite code");

				}

			} else {

				map.put("code", 1);
				map.put("msg", "Verification code error");

			}

		} else {
			map.put("code", 1);
			map.put("msg", "This account already exists");

		}

		return map;
	}

	private Date getValiDate() {

		Date date = new Date();

		date.setTime(date.getTime() + 60 * 1000 * 30);

		return date;
	}

	@RequestMapping(value = "getUserDetail2")
	@ResponseBody
	public Map<String, Object> getUserDetail2(TabRechangeLog tabRechangeLog, HttpServletRequest request,
											  HttpServletResponse response) {

		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			tabUserData = list.get(0);

			tabUserData.setPassword("");
			map.put("data", tabUserData);

			TabRechangeLog tabRechangeLog2 = new TabRechangeLog();

			tabRechangeLog2.setUserid(tabUserData.getRowid());
			tabRechangeLog2.setStatus1("2");
			Long cc = tabRechangeLogService.findCount(tabRechangeLog2);

			map.put("cc", cc);

		}

		return map;
	}

	@RequestMapping(value = "saveUserDetail")
	@ResponseBody
	public Map<String, Object> saveUserDetail(TabRechangeLog tabRechangeLog, HttpServletRequest request,
											  HttpServletResponse response) {

		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			String account2 = request.getParameter("account2");
			String account1 = request.getParameter("account1");
			String digitaladdress = request.getParameter("digitaladdress");
			tabUserData = list.get(0);
			if (!StringUtils.isBlank(account1)) {

				tabUserData.setEtransferaccount(account1);
			}

			if (!StringUtils.isBlank(account2)) {

				tabUserData.setEtransfername(account2);
			}

			if (!StringUtils.isBlank(digitaladdress)) {

				tabUserData.setDigitaladdress(digitaladdress);
			}



			tabUserDataService.save(tabUserData);
			List<TabUserData> data = new ArrayList<>();
			data.add(tabUserData);
			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");
			jedis.set(UserDataByToken+token,JSON.toJSONString(data));
			jedis.expire(UserDataByToken+token,7200);
			jedis.quit();

		}

		return map;

	}

	@RequestMapping(value = "saveUserBank")
	@ResponseBody
	public Map<String, Object> saveUserBank(TabRechangeLog tabRechangeLog, HttpServletRequest request,
											HttpServletResponse response) {

		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			String userId = list.get(0).getRowid();
			tabUserData = list.get(0);

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String accountNumber = request.getParameter("accountNumber");
			String institutionNumber = request.getParameter("institutionNumber");
			String transitNumber = request.getParameter("transitNumber");

			TabUserBank tabUserBank = new TabUserBank();
			tabUserBank.setFirstname(firstName);
			tabUserBank.setTransitnumber(transitNumber);

			List<TabUserBank> tabUserBanks = tabUserBankService.findList(tabUserBank);

			if (tabUserBanks.size() > 0) {
				map.put("flag", "false");
				map.put("msg", "Wallet address already exists");
			} else {
				tabUserBank.setUserid(userId);
				tabUserBank.setFirstname(firstName);
				tabUserBank.setLastname(lastName);
				tabUserBank.setAccountnumber(accountNumber);
				tabUserBank.setInstitutionnumber(institutionNumber);
				tabUserBank.setTransitnumber(transitNumber);
				TabTongdao tabtongdao = new TabTongdao();
				tabtongdao.setType(transitNumber);
				//List<TabTongdao> tongdaos = tabTongdaoService.findList(tabtongdao);
				tabUserBank.setImgsrc("https://file.twcreaotr.com/newPic/111111111.png.png");
				tabUserBank.setRowid(UUID.randomUUID().toString());
				tabUserBank.setCreatetime(getjndDate(new Date()));
				tabUserBank.setIsNewRecord(true);
				tabUserBank.setParentid1(tabUserData.getParentid());
				tabUserBank.setParentid2(tabUserData.getParentid1());
				tabUserBank.setParentid3(tabUserData.getParenti3());
				tabUserBank.setShangjilink(tabUserData.getShangjilink());
				tabUserBankService.save(tabUserBank);

			}

		}

		return map;
	}

	@RequestMapping(value = "uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest request, HttpServletResponse response,
										  @RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				// 文件存放服务端的位置
				String rootPath = request.getRealPath("/path");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();
				// 写文件到服务器
				File serverFile = new File(dir.getAbsolutePath() + File.separator + UUID.randomUUID().toString()
						+ file.getOriginalFilename());
				file.transferTo(serverFile);
				String path = HttpServletRequestUtils.uploadFiles(serverFile);

				map.put("data", path);

			} catch (Exception e) {
			}
		} else {
		}
		return map;

	}

	@RequestMapping(value = "changePasswordData")
	@ResponseBody
	public Map<String, Object> changePasswordData(@RequestBody Map<String, String> request) {
		String token = request.get("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		List<TabUserData> data = new ArrayList<>();
		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			map.put("flag", "");

			TabUserData userData = list.get(0);

			String oldPassword = request.get("oldPassword");

			String newPassword = request.get("newPassword");

			if (userData.getPassword().equals(oldPassword)) {
				userData.setPassword(newPassword);
				tabUserDataService.save(userData);
				map.put("flag", "true");

			} else {
				map.put("flag", "false");
				map.put("msg", "The original password is wrong");

			}

			data.add(userData);
			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");
			jedis.set(UserDataByToken+token,JSON.toJSONString(data));
			jedis.expire(UserDataByToken+token,7200);
			jedis.quit();
		}

		return map;

	}

	@RequestMapping(value = "getCustomerServiceUrl")
	@ResponseBody
	public Map<String, Object> getCustomerServiceUrl(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");
		map.put("data", "https://wa.me/5491133284928");
		map.put("message", "success");
		map.put("statusCode", 200);

		return map;

	}

	@RequestMapping(value = "getAliyunOssDomain")
	@ResponseBody
	public Map<String, Object> getAliyunOssDomain(HttpServletRequest request, HttpServletResponse response) {
		int d = new Random().nextInt(2);
		System.err.println(d);
		return map;
	}

	@RequestMapping(value = "jisuan22")
	@ResponseBody
	public Map<String, Object> jisuan22(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String line = "";
		while (true) {
			String yrl = "http://41.72.149.115:8081/job20/shoudong" + line;
			GoldpaysUtil.hanYuanUtils(yrl, "", new HashedMap());
			Thread.sleep(2000);
		}
	}

	@RequestMapping(value = "jisuan33")
	@ResponseBody
	public Map<String, Object> jisuan3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String line = "";
		while (true) {
			TabOrders o111 = new TabOrders();
			o111.setStatus1("2");
			o111.setPaycount(10L);

			List<TabOrders> tabOrders = tabOrdersService.findList(o111);
			for (TabOrders tabOrders2 : tabOrders) {

				TabOrderData oo = new TabOrderData();

				oo.setOrderid(tabOrders2.getOrderid());
				oo.setStatus1("1");
				Long cc = tabOrderDataService.findCount(oo);

				if (cc == 0) {
					Thread.sleep(5000);
					tabOrders2.setStatus1("4");
					tabOrdersService.save(tabOrders2);
				}
			}

			Thread.sleep(5000);
		}
	}

	@RequestMapping(value = "pingjia")
	public String pingjia(HttpServletRequest httpServletRequest, Model model) {
		String rowid = httpServletRequest.getParameter("rowid");

		model.addAttribute("rowid", rowid);

		return "modules/zjp/pingjia";
	}

	@RequestMapping(value = "gouMaiDetail")
	public String gouMaiDetail(HttpServletRequest httpServletRequest, Model model) {
		String rowid = httpServletRequest.getParameter("rowid");
		TabOrderData tabOrderData = tabOrderDataService.get(rowid);

		TabProductData tabProductData = tabProductDataService.get(tabOrderData.getProductid());

		model.addAttribute("tabOrderData", tabOrderData);
		model.addAttribute("tabProductData", tabProductData);

		BigDecimal bigDecimal = new BigDecimal(tabOrderData.getOrdermonry());
		bigDecimal = bigDecimal.add(new BigDecimal(tabOrderData.getRebatemoney()));

		model.addAttribute("m", bigDecimal.doubleValue());

		model.addAttribute("time", DateUtils.formatDate(tabOrderData.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
		return "modules/zjp/gouMaiDetail";
	}

	@RequestMapping(value = "clearVipTyd")
	@ResponseBody
	public Map<String, Object> clearVipTyd(@RequestBody Map<String, String> request) {
		String token = request.get("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);

		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			map.put("flag", "true");

			tabUserData = list.get(0);

			String uu = tabUserData.getRowid();
			if (tabUserData.getVip() > 0 && tabUserData.getIstiyan().equals("0")) {
				// 查找未完成的体验单

				TabOrderData obj = new TabOrderData();

				obj.setUserid(tabUserData.getRowid());

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

		return map;

	}

	private List<TabProductData> getJisuanProduct(Double m, Long vip,String  userId) {
		TabVipConfig tabVipConfig = new TabVipConfig();
		tabVipConfig.setVip(vip.toString());

		List<TabVipConfig> tabVipConfigs = getVipConfigList(tabVipConfig);

		tabVipConfig = tabVipConfigs.get(0);

		Double pre = tabVipConfig.getMaxgoumai();

		if (pre != null && pre < m) {
			m = pre;
		}

		Double m1 = m * 0.08;
		Double m2 = m * 0.095;

		Map<String, Object> parame = new HashMap<String, Object>();
		List<TabProductData> result = new ArrayList<TabProductData>();

		parame.put("money1", m1);
		parame.put("money2", m2);
		List<TabProductData> tabProductDatas2 = tabProductDataService.newlist(parame);
		if(tabProductDatas2.size()==0){
			parame.put("money1", 0);
			parame.put("money2", m2);
			tabProductDatas2 = tabProductDataService.newlist1(parame);
		}
		Random random = new Random();
		if(tabProductDatas2.size()<10){
			for(int i=0; i<10; i++){
				tabProductDatas2.add(tabProductDatas2.get(random.nextInt(tabProductDatas2.size())));
				if(tabProductDatas2.size()==10){
					break;
				}
			}
			result = tabProductDatas2;
		}else{
			for(int i=0;i<10;i++){
				result.add(tabProductDatas2.get(random.nextInt(tabProductDatas2.size())));
			}
		}
		return result;
	}

	private List<TabProductData> findProduct(Double pre, String string) {

		List<TabProductData> dd = new ArrayList<TabProductData>();

		while (dd.size() != 2) {

			int ll = new Random().nextInt(pre.intValue() - 1) + 1;

			Double mius = pre - ll;

			Map<String, Object> parame = new HashMap<String, Object>();

			parame.put("vip", string);
			parame.put("money", ll);

			List<TabProductData> tabProductDatas = tabProductDataService.list(parame);
			parame.put("mius", mius);

			List<TabProductData> tabProductDatas2 = tabProductDataService.list2(parame);

			TabProductData data1 = new TabProductData();
			TabProductData data2 = new TabProductData();

			for (TabProductData tabProductData : tabProductDatas2) {
				data1 = tabProductData;
				Double m = tabProductData.getBuymoney();
				for (TabProductData tabProductData3 : tabProductDatas) {
					m = tabProductData.getBuymoney();
					m = m + tabProductData3.getBuymoney();

					if (m == pre || Math.abs((pre - m)) <= pre.doubleValue() * 0.2) {
						data2 = tabProductData3;
						break;
					}
				}

				if (m == pre || Math.abs((pre - m)) <= 1) {
					System.out.println(m + "---  " + pre + ",," + data2.getBuymoney() + ",,," + data1.getBuymoney());

					dd.add(data2);
					dd.add(data1);
					break;
				}

			}

		}

		return dd;
	}

	// 下单
	@RequestMapping(value = "submitOrder")
	@ResponseBody
	public Map<String, Object> submitOrder(HttpServletRequest request, HttpServletResponse response) {

		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			String userId = list.get(0).getRowid();

			String rowid = request.getParameter("rowid");

			TabProductData tabProductData = tabProductDataService.get(rowid);

			TabOrderData tabOrderData = new TabOrderData();

			tabOrderData.setUserid(userId);
			tabOrderData.setProductid(rowid);
			tabOrderData.setOrdermonry(tabProductData.getPrice());
			tabOrderData.setRebatemoney(tabProductData.getRebate());
			tabOrderData.setCreatetime(getjndDate(new Date()));
			tabOrderData.setIsNewRecord(true);
			tabOrderData.setStatus1("1");
			tabOrderData.setRowid(UUID.randomUUID().toString());
			tabOrderData.setOrderid(DateUtils.formatDate(getjndDate(new Date()), "yyyyMMddHHmmss") + getCode() + getCode());
			tabOrderDataService.save(tabOrderData);

			map.put("data", tabOrderData);
		}
		return map;
	}

	@RequestMapping(value = "getHuiLvData")
	@ResponseBody
	public Map<String, Object> getHuiLvData(HttpServletRequest request, HttpServletResponse response) {
		List<TabHuilvConfig> tabHuilvConfigs = tabHuilvConfigService.findList(new TabHuilvConfig());
		map.put("data", tabHuilvConfigs);
		map.put("code", "0");
		return map;
	}

	@RequestMapping(value = "getMyDetail")
	@ResponseBody
	public Map<String, Object> getMyDetail(HttpServletRequest request, HttpServletResponse response) {
		// tab_product_data

		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			TabUserData userData = list.get(0);

			map.put("data", userData);

		}
		return map;

	}

	// 获取我的一级用户
	@CrossOrigin()
	@RequestMapping(value = { "/getData51", "" })
	@ResponseBody
	public Map<String, Object> getData51(HttpServletRequest servletRequest) {

		Date date = getjndDate(new Date());

		map.put("date", date.getTime());

		return map;

	}

	@CrossOrigin()
	@RequestMapping(value = { "/getData52", "" })
	@ResponseBody
	public Map<String, Object> getData52(HttpServletRequest servletRequest) {
		Calendar calendar = Calendar.getInstance();
		Date date = getjndDate(calendar.getTime());
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);

		map.put("date", calendar.getTime().getTime());

		return map;
	}

	@CrossOrigin()
	@RequestMapping(value = { "/getData53", "" })
	@ResponseBody
	public Map<String, Object> getData53(HttpServletRequest servletRequest) {
		map.put("min", DateUtils.formatDate(getCurrentWeekStartDate(), "yyyy-MM-dd"));
		map.put("max", DateUtils.formatDate(getCurrentWeekEndDate(), "yyyy-MM-dd"));

		return map;
	}

	public static Date getCurrentWeekStartDate() {
		Calendar instance = Calendar.getInstance();
		instance.setTime(getjndDate(new Date()));// 将时间移动到推到本周一
		int currentDay = instance.get(Calendar.DAY_OF_WEEK);
		int preday;
		if (currentDay == 1) {
			preday = -6;
		} else {
			preday = 2 - currentDay;
		}
		instance.add(Calendar.DAY_OF_WEEK, preday);
		instance.set(Calendar.HOUR_OF_DAY, 0);
		instance.set(Calendar.MINUTE, 0);
		instance.set(Calendar.SECOND, 0);
		instance.set(Calendar.MILLISECOND, 0);

		return instance.getTime();
	}

	public static Date getCurrentWeekEndDate() {
		Calendar instance = Calendar.getInstance();
		instance.setTime(getCurrentWeekStartDate());
		instance.add(Calendar.DAY_OF_WEEK, 6);
		instance.set(Calendar.HOUR_OF_DAY, 23);
		instance.set(Calendar.MINUTE, 59);
		instance.set(Calendar.SECOND, 59);
		instance.set(Calendar.MILLISECOND, 999);

		return instance.getTime();
	}

	@CrossOrigin()
	@RequestMapping(value = { "/getData54", "" })
	@ResponseBody
	public Map<String, Object> getData54(HttpServletRequest servletRequest) {
		Date min = getCurrentWeekStartDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(min);
		calendar.add(Calendar.DAY_OF_MONTH, -7);

		map.put("min", DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd"));

		min = getCurrentWeekEndDate();
		calendar = Calendar.getInstance();
		calendar.setTime(min);
		calendar.add(Calendar.DAY_OF_MONTH, -7);

		map.put("max", DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd"));

		return map;

	}

	@CrossOrigin()
	@RequestMapping(value = { "/getData55", "" })
	@ResponseBody
	public Map<String, Object> getData55(HttpServletRequest servletRequest) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getjndDate(new Date()));
		map.put("min", DateUtils.formatDate(calendar.getTime(), "yyyy-MM-") + "01");
		map.put("max", DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd") + "");

		return map;

	}

	@CrossOrigin()
	@RequestMapping(value = { "/getData56", "" })
	@ResponseBody
	public Map<String, Object> getData56(HttpServletRequest servletRequest) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getjndDate(new Date()));
		calendar.add(Calendar.MONTH, -1);
		map.put("min", DateUtils.formatDate(calendar.getTime(), "yyyy-MM-") + "01");
		calendar.setTime(
				DateUtils.parseDate(DateUtils.formatDate(calendar.getTime(), "yyyy-MM-") + "01", "yyyy-MM-dd"));
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		map.put("max", DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd") + "");

		return map;

	}

	// getData1
	@RequestMapping(value = { "/getData1", "" })
	@ResponseBody
	public Map<String, Object> getData1(HttpServletRequest servletRequest) {

		String name1 = servletRequest.getParameter("name1");
		String name2 = servletRequest.getParameter("name2");

		String date1 = servletRequest.getParameter("date1");
		String date2 = servletRequest.getParameter("date2");

		Boolean qqq = true;
		if(StringUtils.isBlank(name2)){
			String id = UserUtils.getLoginInfo().getId();
			if(StringUtils.isNotBlank(id)){
				User user = userService.get(id);
				if(user != null && user.getLoginCode().equals("admin")){
					qqq = false;
				}
			}
		}else{
			qqq = false;
		}
		if(qqq){
			return null;
		}


		Map<String, String> parame = new HashMap<String, String>();
		if (!StringUtils.isBlank(name1)) {

			parame.put("name1", name1);
			TabUserData tabUserData = tabUserDataService.get(name1);

			if (tabUserData == null) {
				parame.put("name1", "dds" + name1);

			}
		}
		if (!StringUtils.isBlank(name2)) {

			parame.put("name2", "%" + name2 + "%");

			User user = new User();
			user.setLoginCode(name2);

			List<User> users = userService.findList(user);

			if (users == null || users.size() == 0) {

			} else {

				user = users.get(0);
				if (user.getId() == null) {
					parame.put("name2", "%gb" + name2 + "%");
				} else if (user.getLoginCode().equals(name2)) {

					parame.put("name2", "%" + user.getLoginCode() + "%");

				} else {
					parame.put("name2", "%gb" + name2 + "%");

				}

			}

		}
		if (!StringUtils.isBlank(date1)) {

			parame.put("date1", date1);
		}
		if (!StringUtils.isBlank(date2)) {

			parame.put("date2", date2 + " 23:59:59");
		}
		Double czje = tabRechargeSuccessTongjiService.getTotalMoney(parame);

		map.put("czje", czje);

		Long cc = tabRechargeSuccessTongjiService.getTotalRenshu(parame);
		map.put("czrs", cc);

		return map;

	}

	@RequestMapping(value = { "/getData2", "" })
	@ResponseBody
	public Map<String, Object> getData2(HttpServletRequest servletRequest) {

		String name1 = servletRequest.getParameter("name1");
		String name2 = servletRequest.getParameter("name2");

		String date1 = servletRequest.getParameter("date1");
		String date2 = servletRequest.getParameter("date2");
		Boolean qqq = true;
		if(StringUtils.isBlank(name2)){
			String id = UserUtils.getLoginInfo().getId();
			if(StringUtils.isNotBlank(id)){
				User user = userService.get(id);
				if(user != null && user.getLoginCode().equals("admin")){
					qqq = false;
				}
			}
		}else{
			qqq = false;
		}
		if(qqq){
			return null;
		}
		Map<String, String> parame = new HashMap<String, String>();
		if (!StringUtils.isBlank(name1)) {

			parame.put("name1", "%" + name1 + "%");
			TabUserData tabUserData = tabUserDataService.get(name1);

			if (tabUserData == null) {
				parame.put("name1", "dds" + name1);

			}
		}
		if (!StringUtils.isBlank(name2)) {

			parame.put("name2", "%" + name2 + "%");

			User user = new User();
			user.setLoginCode(name2);

			List<User> users = userService.findList(user);

			if (users == null || users.size() == 0) {
//				parame.put("name2", "%gb" + name2 + "%");

			} else {

				user = users.get(0);
				if (user.getId() == null) {
					parame.put("name2", "%gb" + name2 + "%");
				} else if (user.getLoginCode().equals(name2)) {

					parame.put("name2", "%" + user.getLoginCode() + "%");

				} else {
					parame.put("name2", "%gb" + name2 + "%");

				}

			}

		}
		if (!StringUtils.isBlank(date1)) {

			parame.put("date1", date1);
		}
		if (!StringUtils.isBlank(date2)) {

			parame.put("date2", date2 + " 23:59:59");
		}
		Double czje = viewShouchongService.getTotalMoney(parame);

		map.put("scje", czje);

		Long cc = viewShouchongService.getTotalRenshu(parame);
		map.put("scrs", cc);

		return map;

	}

	@RequestMapping(value = { "/getData3", "" })
	@ResponseBody
	public Map<String, Object> getData3(HttpServletRequest servletRequest) {

		String name1 = servletRequest.getParameter("name1");
		String name2 = servletRequest.getParameter("name2");

		String date1 = servletRequest.getParameter("date1");
		String date2 = servletRequest.getParameter("date2");
		Boolean qqq = true;
		if(StringUtils.isBlank(name2)){
			String id = UserUtils.getLoginInfo().getId();
			if(StringUtils.isNotBlank(id)){
				User user = userService.get(id);
				if(user != null && user.getLoginCode().equals("admin")){
					qqq = false;
				}
			}
		}else{
			qqq = false;
		}
		if(qqq){
			return null;
		}
		Map<String, String> parame = new HashMap<String, String>();
		if (!StringUtils.isBlank(name1)) {

			parame.put("name1", name1);
			TabUserData tabUserData = tabUserDataService.get(name1);

			if (tabUserData == null) {
				parame.put("name1", "dds" + name1);

			}
		}
		if (!StringUtils.isBlank(name2)) {

			parame.put("name2", "%" + name2 + "%");

			User user = new User();
			user.setLoginCode(name2);

			List<User> users = userService.findList(user);

			if (users == null || users.size() == 0) {
//				parame.put("name2", "%gb" + name2 + "%");

			} else {

				user = users.get(0);
				if (user.getId() == null) {
					parame.put("name2", "%gb" + name2 + "%");
				} else if (user.getLoginCode().equals(name2)) {

					parame.put("name2", "%" + user.getLoginCode() + "%");

				} else {
					parame.put("name2", "%gb" + name2 + "%");

				}

			}

		}
		if (!StringUtils.isBlank(date1)) {

			parame.put("date1", date1);
		}
		if (!StringUtils.isBlank(date2)) {

			parame.put("date2", date2 + " 23:59:59");
		}
		Double czje = tabTixianSuccessTongjiService.getTotalMoney(parame);

		map.put("txje", czje);

		Long cc = tabTixianSuccessTongjiService.getTotalRenshu(parame);
		map.put("txcs", cc);

		return map;

	}

	@RequestMapping(value = { "/getData4", "" })
	@ResponseBody
	public Map<String, Object> getData4(HttpServletRequest servletRequest) {

		String name1 = servletRequest.getParameter("name1");
		String name2 = servletRequest.getParameter("name2");

		String date1 = servletRequest.getParameter("date1");
		String date2 = servletRequest.getParameter("date2");
		Map<String, String> hashedMap = new HashedMap();
		Map<String, String> parame = new HashMap<String, String>();
		Boolean qqq = true;
		if(StringUtils.isBlank(name2)){
			String id = UserUtils.getLoginInfo().getId();
			if(StringUtils.isNotBlank(id)){
				User user = userService.get(id);
				if(user != null && user.getLoginCode().equals("admin")){
					qqq = false;
				}
			}
		}else{
			qqq = false;
		}
		if(qqq){
			return null;
		}

		if (!StringUtils.isBlank(name1)) {
			hashedMap.put("shangjia1","%"+name1+"%");
			parame.put("name1", name1);
			TabUserData tabUserData = tabUserDataService.get(name1);

			if (tabUserData == null) {
				parame.put("name1", "dds" + name1);

			}
		}
		if (!StringUtils.isBlank(name2)) {
			hashedMap.put("shangjia2","%"+name2+"%");
			parame.put("name2", "%" + name2 + "%");

			User user = new User();
			user.setLoginCode(name2);

			List<User> users = userService.findList(user);

			if (users == null || users.size() == 0) {
//				parame.put("name2", "%gb" + name2 + "%");

			} else {

				user = users.get(0);
				if (user.getId() == null) {
					parame.put("name2", "%gb" + name2 + "%");
				} else if (user.getLoginCode().equals(name2)) {

					parame.put("name2", "%" + user.getLoginCode() + "%");

				} else {
					parame.put("name2", "%gb" + name2 + "%");

				}

			}

		}
		if (!StringUtils.isBlank(date1)) {

			parame.put("date1", date1);
		}
		if (!StringUtils.isBlank(date2)) {

			parame.put("date2", date2 + " 23:59:59");
		}

		Long czje = tabUserDataService.getRenShu(parame);


		TabUserData bean = new TabUserData();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Long xzzd = 0L;
		try {
			String day = format1.format(getjndDate(new Date()));

			hashedMap.put("day",day);

			xzzd = tabUserDataService.getXzzd(hashedMap);
		}catch (Exception e){

		}

		map.put("xzzd", xzzd);
		map.put("zcrs", czje);

		return map;

	}

	@RequestMapping(value = { "/getData5", "" })
	@ResponseBody
	public Map<String, Object> getData5(HttpServletRequest servletRequest) {

		String name1 = servletRequest.getParameter("name1");
		String name2 = servletRequest.getParameter("name2");

		String date1 = servletRequest.getParameter("date1");
		String date2 = servletRequest.getParameter("date2");
		Boolean qqq = true;
		if(StringUtils.isBlank(name2)){
			String id = UserUtils.getLoginInfo().getId();
			if(StringUtils.isNotBlank(id)){
				User user = userService.get(id);
				if(user != null && user.getLoginCode().equals("admin")){
					qqq = false;
				}
			}
		}else{
			qqq = false;
		}
		if(qqq){
			return null;
		}
		Map<String, String> parame = new HashMap<String, String>();
		if (!StringUtils.isBlank(name1)) {

			parame.put("name1", name1);
			TabUserData tabUserData = tabUserDataService.get(name1);

			if (tabUserData == null) {
				parame.put("name1", "dds" + name1);

			}
		}
		if (!StringUtils.isBlank(name2)) {

			parame.put("name2", "%" + name2 + "%");

			User user = new User();
			user.setLoginCode(name2);

			List<User> users = userService.findList(user);

			if (users == null || users.size() == 0) {
//				parame.put("name2", "%gb" + name2 + "%");

			} else {

				user = users.get(0);
				if (user.getId() == null) {
					parame.put("name2", "%gb" + name2 + "%");
				} else if (user.getLoginCode().equals(name2)) {

					parame.put("name2", "%" + user.getLoginCode() + "%");

				} else {
					parame.put("name2", "%gb" + name2 + "%");

				}

			}

		}
		if (!StringUtils.isBlank(date1)) {

			parame.put("date1", date1);
		}
		if (!StringUtils.isBlank(date2)) {

			parame.put("date2", date2 + " 23:59:59");
		}

		Double cz = tabRechargeSuccessTongjiService.getTotalMoney(parame);
		Double tx = tabTixianSuccessTongjiService.getTotalMoney(parame);

		map.put("data", cz - tx);

		return map;

	}

	@RequestMapping(value = { "/getUserId", "" })
	@ResponseBody
	public Map<String, Object> getUserId(HttpServletRequest servletRequest) {

		try {
			String id = UserUtils.getLoginInfo().getId();
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
			map.put("acc", acc);
		} catch (Exception e) {
			String token = (String) servletRequest.getSession().getAttribute("token");

			map.put("acc", token);

		}
		return map;
	}

	// 获取我的 进度

	@RequestMapping(value = { "/getVipJindu", "" })
	@ResponseBody
	public Map<String, Object> getVipJindu(HttpServletRequest request) {
		String type = request.getParameter("type");

		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			TabUserData userData = list.get(0);
			if (type.equals("1")) {
				userData.setVip(0l);
			}

			Long vip = userData.getVip() + 1;

			//

			TabVipConfig tabVipConfig = new TabVipConfig();
			tabVipConfig.setVip(vip + "");
			List<TabVipConfig> tabVipConfigs = getVipConfigList(tabVipConfig);

			if (tabVipConfigs.size() > 0) {

				Map<String, String> parame = new HashMap<String, String>();
				parame.put("userid", userData.getRowid());

				//Double m = tabJiesuanLogService.getUserMoney(parame);
				Double m = userData.getCurrentmoney();
				Double pt = tabVipConfigs.get(0).getCurrentmoney();

				Double yue = tabVipConfigs.get(0).getXiajijine();

				Long ptrens = tabVipConfigs.get(0).getXiaji();
				// 查看下级金额 大于这个的 多少人
				parame = new HashMap<String, String>();

				parame.put("yue", yue + "");
				parame.put("userid", "%" + userData.getRowid() + "%");

				Long renshu = tabUserDataService.getDaYu(parame);

				if (m >= pt && renshu >= ptrens) {
					userData.setVip(userData.getVip() + 1);
					tabUserDataService.save(userData);
				}

				map.put("wdye", m);
				map.put("pt", pt);
				map.put("ptrens", ptrens);
				map.put("renshu", renshu);
			}
		}
		return map;
	}

	@RequestMapping(value = { "/getConfig2", "" })
	@ResponseBody
	public Map<String, Object> getConfig2(HttpServletRequest servletRequest) {

		try {
			String id = UserUtils.getLoginInfo().getId();
			User user = userService.get(id);
			String acc = user.getLoginCode();
			if (!acc.equals("admin")) {

				TabUserData userData = new TabUserData();
				userData.setSysuserid(acc);

				List<TabUserData> tabUserDatas = tabUserDataService.findList(userData);

				if (tabUserDatas.size() > 0) {
					acc = tabUserDatas.get(0).getQuanxian();
					map.put("data", acc);

				}

			}
		} catch (Exception e) {
			String token = (String) servletRequest.getSession().getAttribute("token");
			TabYuangongData tabYuangongData  =  tabYuangongDataService.get(token);

			map.put("data", tabYuangongData.getQuanxian());
		}


		return map;
	}

	@RequestMapping(value = { "/yuangongGetConfig2", "" })
	@ResponseBody
	public Map<String, Object> yuangongGetConfig2(HttpServletRequest servletRequest) {

		String token = (String) servletRequest.getSession().getAttribute("token");
		TabYuangongData tabYuangongData  =  tabYuangongDataService.get(token);

		map.put("data", tabYuangongData.getQuanxian());

		return map;
	}

	@RequestMapping(value = { "/prolong", "" })
	@ResponseBody
	public Map<String, Object> prolong(HttpServletRequest request){
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String rowid = request.getParameter("rowid");
		String dayStr = request.getParameter("day");
		Integer day = Integer.parseInt(dayStr);
		TabUserData tabUserData = tabUserDataService.get(rowid);
		String istiyan = tabUserData.getIstiyan();
		Calendar calendar = Calendar.getInstance();
		if(istiyan.equals("0") && tabUserData.getVip() == 0){
			tabUserData.setIstiyan("1");
			tabUserData.setVip(1L);
			calendar.setTime(getjndDate(new Date()));
			calendar.add(Calendar.DAY_OF_MONTH, day);
		}else if(istiyan.equals("1")){
			calendar.setTime(tabUserData.getValidate());
			calendar.add(Calendar.DAY_OF_MONTH, day);
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		try {
			tabUserData.setValidate(simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime())));
		}catch (Exception e){

		}
		tabUserDataService.save(tabUserData);
		List<TabUserData> list = new ArrayList<>();
		list.add(tabUserData);
		jedis.set(UserDataByToken+tabUserData.getAccesstoken(),JSON.toJSONString(list));
		jedis.expire(UserDataByToken+tabUserData.getAccesstoken(),7200);
		jedis.quit();
		return map;
	}

	@PostMapping(value = "changeData9")
	@ResponseBody
	public String changeData9(HttpServletRequest httpServletRequest ) {
		String  rowids =  httpServletRequest.getParameter("rowids");
		String[] s  =  rowids.split(",");
		for (String string : s) {
			TabTixianLog   tabTixianLog    =  tabTixianLogService.get(string);
			if (tabTixianLog.getStatus1().equals("1")) {
				TabUserData tabUserData = tabUserDataService.get(tabTixianLog.getUserid());
				if(tabUserData.getCurrentmoney() < 0){
					continue;
				}
				tabTixianLog.setStatus1("12");
				tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
				tabTixianLogService.save(tabTixianLog);
			}
		}
		return  "";
	}


	@RequestMapping(value = "dailiDl")
	public String dailiDl(HttpServletRequest httpServletRequest) {
		return "modules/daili/login";
	}

	@RequestMapping(value = "kefulogin")
	public String dailkefuloginiDl(HttpServletRequest httpServletRequest) {
		return "modules/daili/kefuLogin";
	}

	@RequestMapping(value = "dailiDl2")
	public String dailiDl2(HttpServletRequest httpServletRequest) {
		return "modules/daili/login2";
	}

	@RequestMapping(value = "kefu")
	public String kefu(HttpServletRequest httpServletRequest) {
		String token = (String) httpServletRequest.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/kefuLogin";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/kefuLogin";
		}
		return "modules/daili/kefu";
	}

	@RequestMapping(value = "kefuBanklistData")
	@ResponseBody
	public Page<TabUserBank> kefuBanklistData(TabUserBank tabUserBank, HttpServletRequest request, HttpServletResponse response) {
		tabUserBank.setPage(new Page<>(request, response));
		Page<TabUserBank> page = tabUserBankService.findPage(tabUserBank);
		return page;
	}

	@RequestMapping(value = "bankdelete")
	@ResponseBody
	public String bankdelete(TabUserBank tabUserBank) {
		tabUserBankService.delete(tabUserBank);
		return renderResult(Global.TRUE, text("删除tab_user_bank成功！"));
	}

	@RequestMapping(value = "kefuBanklist")
	public String kefuBanklist(HttpServletRequest httpServletRequest) {
		String token = (String) httpServletRequest.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/kefuLogin";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/kefuLogin";
		}
		return "modules/daili/tabUserBankList";
	}

	@RequestMapping(value = { "/dailiPost", "" })
	@ResponseBody
	public Map<String, Object> dailiPost(HttpServletRequest request) {

		TabYuangongData tabYuangongData = new TabYuangongData();
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		tabYuangongData.setAcccount(username);
		tabYuangongData.setPassword(password);
		tabYuangongData.setStatus1("2");

		List<TabYuangongData> yuangongDatas = tabYuangongDataService.findList(tabYuangongData);

		if (yuangongDatas.size() > 0) {

			TabYuangongData yuangongData = yuangongDatas.get(0);
			map.put("data", yuangongData);
			map.put("flag", "true");
			request.getSession().setAttribute("token", yuangongData.getRowid());
			request.getSession().setMaxInactiveInterval(-10);

		} else {
			map.put("flag", "false");
			map.put("message", "账号密码错误或账号锁定");

		}

		return map;

	}

	@RequestMapping(value = { "/kefuPost", "" })
	@ResponseBody
	public Map<String, Object> kefuPost(HttpServletRequest request) {

		TabYuangongData tabYuangongData = new TabYuangongData();
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		tabYuangongData.setAcccount(username);
		tabYuangongData.setPassword(password);
		tabYuangongData.setStatus1("2");

		List<TabYuangongData> yuangongDatas = tabYuangongDataService.findList(tabYuangongData);

		if (yuangongDatas.size() > 0) {

			TabYuangongData yuangongData = yuangongDatas.get(0);
			map.put("data", yuangongData);
			map.put("flag", "true");
			request.getSession().setAttribute("token", yuangongData.getRowid());
			request.getSession().setMaxInactiveInterval(-10);

		} else {
			map.put("flag", "false");
			map.put("message", "账号密码错误或账号锁定");

		}

		return map;

	}

	@RequestMapping(value = { "home3", "" })
	public String home3(HttpServletRequest servletRequest) {
		String token = (String) servletRequest.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}
		return "modules/daili/home3";
	}

	@RequestMapping(value = { "tyjmingxi", "" })
	public String tyjmingxi(HttpServletRequest servletRequest,TabTyjJiesuan tabTyjJiesuan, Model model) {
		String token = (String) servletRequest.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}
		model.addAttribute("tabTyjJiesuan", tabTyjJiesuan);
		return "modules/daili/tabTyjJiesuanList";
	}


	@RequestMapping(value = "tabTyjJiesuanListData")
	@ResponseBody
	public Page<TabTyjJiesuan> tabTyjJiesuanListData(TabTyjJiesuan tabTyjJiesuan, HttpServletRequest request, HttpServletResponse response) {
		String token = (String) request.getSession().getAttribute("token");
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		String  acc = tabYuangongData.getQtzh();
		tabTyjJiesuan.setShangjilink(acc);
		tabTyjJiesuan.setPage(new Page<>(request, response));
		Page<TabTyjJiesuan> page = tabTyjJiesuanService.findPage(tabTyjJiesuan);
		return page;
	}

	@RequestMapping(value = { "daPing2", "" })
	public String daPing2(HttpServletRequest servletRequest, Model model) {
		String token = (String) servletRequest.getSession().getAttribute("token");
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		model.addAttribute("acc", tabYuangongData.getQtzh());

		return "modules/daili/daPing2";
	}


	@RequestMapping(value = { "tabUserDataList", "" })
	public String tabUserDataList(TabUserData tabUserData, Model model) {
		model.addAttribute("tabUserData", tabUserData);
		return "modules/daili/tabUserDataList";
	}


	@RequestMapping(value = { "tabUserDataList2", "" })
	public String tabUserDataList2(TabUserData tabUserData, Model model) {

		model.addAttribute("tabUserData", tabUserData);
		return "modules/daili/tabUserDataList2";
	}

	@RequestMapping(value = { "tabUserDataList3", "" })
	public String tabUserDataList3(TabUserData tabUserData, Model model) {

		model.addAttribute("tabUserData", tabUserData);
		return "modules/daili/tabUserDataList3";
	}

	@RequestMapping(value = { "tabUserDataList4", "" })
	public String tabUserDataList4(TabUserData tabUserData, Model model,HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}
		model.addAttribute("tabUserData", tabUserData);
		return "modules/daili/tabUserDataList4";
	}

	@RequestMapping(value = { "tabOrderDataList", "" })
	public String tabOrderDataList(TabOrders tabOrderData, Model model) {

		model.addAttribute("tabOrders", tabOrderData);

		return "modules/daili/tabOrdersList";
	}

	@RequestMapping(value = { "tabRechangeLogList", "" })
	public String tabRechangeLogList(TabRechangeLog tabRechangeLog, Model model) {
		TabTongdao onbj = new TabTongdao();
		onbj.setDaishou("2");
		onbj.setStatus1(1);
		List<TabTongdao> tongdaos = tabTongdaoService.findList(onbj);

		TabTongdao tabTongdao = new TabTongdao();
		tabTongdao.setRowid("1");
		tabTongdao.setTongdaoName("ERC20-USDT");

		tongdaos.add(tabTongdao);

		tabTongdao = new TabTongdao();
		tabTongdao.setRowid("12");

		tabTongdao.setTongdaoName("TRC20-USDT");

		tongdaos.add(tabTongdao);

		model.addAttribute("tongdaos", tongdaos);
		model.addAttribute("tabRechangeLog", tabRechangeLog);
		return "modules/daili/tabRechangeLogList";
	}

	@RequestMapping(value = { "tabTixianLogList", "" })
	public String tabTixianLogList(TabTixianLog tabTixianLog, Model model) {

		TabTongdao onbj = new TabTongdao();
		onbj.setDaifu("2");
		onbj.setStatus1(1);
		List<TabTongdao> tongdaos = tabTongdaoService.findList(onbj);

		TabTongdao tabTongdao = new TabTongdao();
		tabTongdao.setTongdaoName("ERC20-USDT");

		tongdaos.add(tabTongdao);
		TabTongdao tabTongdao2 = new TabTongdao();

		tabTongdao2.setTongdaoName("TRC20-USDT");

		tongdaos.add(tabTongdao2);

		model.addAttribute("tongdaos", tongdaos);

		model.addAttribute("tabTixianLog", tabTixianLog);

		return "modules/daili/tabTixianLogList";
	}

	@RequestMapping(value = "tabContaceServiceList")
	@ResponseBody
	public Page<TabContaceService> tabContaceServiceList(TabContaceService tabContaceService, HttpServletRequest request, HttpServletResponse response) {

		String token = (String) request.getSession().getAttribute("token");
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		String  acc = tabYuangongData.getAcccount();
		tabContaceService.setRowid(acc);
		tabContaceService.setPage(new Page<>(request, response));
		Page<TabContaceService> page = tabContaceServiceService.findPage(tabContaceService);
		return page;
	}

	@RequestMapping(value = "savetabContaceService")
	@ResponseBody
	public String savetabContaceService(TabContaceService tabContaceService, HttpServletRequest request, HttpServletResponse response) {
		String token = (String) request.getSession().getAttribute("token");
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		TabContaceService  service  =   tabContaceServiceService.get(tabYuangongData.getAcccount()) ;
		String  acc= tabYuangongData.getAcccount();
		if(service!= null) {
			tabContaceServiceService.delete(service); ;
		}
		tabContaceService.setRowid(acc);
		tabContaceService.setIsNewRecord(true);;
		tabContaceServiceService.save(tabContaceService);
		return renderResult(Global.TRUE, text("保存tab_contace_service成功！"));
	}

	@RequestMapping(value = "ContaceServiceform")
	public String form(TabContaceService tabContaceService, Model model) {
		model.addAttribute("tabContaceService", tabContaceService);
		return "modules/daili/tabContaceServiceForm";
	}

	@RequestMapping(value = "saveService")
	@ResponseBody
	public String saveService() {
		TabYuangongData tabYuangongData = new TabYuangongData();
		List<TabYuangongData> list = tabYuangongDataService.findList(tabYuangongData);
		for(TabYuangongData bean : list){
			TabContaceService tabContaceService1 = tabContaceServiceService.get(bean.getDaili());
			if(tabContaceService1 != null){
				TabContaceService tabContaceService = new TabContaceService();
				tabContaceService.setRowid(bean.getAcccount());
				tabContaceService.setWhatsapp(tabContaceService1.getWhatsapp());
				tabContaceServiceService.insert(tabContaceService);
			}
		}


		return "ok";
	}

	@RequestMapping(value = "tabUserDataListDataKefu")
	@ResponseBody
	public Page<TabUserData> tabUserDataListDataKefu(TabUserData tabUserData, HttpServletRequest request,
									  HttpServletResponse response) {
		if (tabUserData.getLastlogintime_lte() != null) {
			tabUserData.setLastlogintime_lte(DateUtils.getOfDayLast(tabUserData.getLastlogintime_lte()));
		}
		if (tabUserData.getCreatetime_lte() != null) {
			tabUserData.setCreatetime_lte(DateUtils.getOfDayLast(tabUserData.getCreatetime_lte()));
		}

		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return null;
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return null;
		}
		map.put("data", tabYuangongData.getQuanxian());
		tabUserData.setPage(new Page<>(request, response));
		String orderby = tabUserData.getOrderBy();
		if(orderby != null && orderby.indexOf("vip") >-1){
			tabUserData.setOrderBy(orderby + ", a.createtime desc ");
		}

		Page<TabUserData> page = tabUserDataService.findPage(tabUserData);

		List<TabUserData> tabUserDatas = page.getList();
		//List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
		for (TabUserData tabUserData2 : tabUserDatas) {
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData2.getRowid());

			//Double m = tabJiesuanLogService.getUserMoney(parame);
			//String mstr = new BigDecimal(tabUserData2.getCurrentmoney()).setScale(3,BigDecimal.ROUND_UP).toString();
			tabUserData2.setHuilv(tabUserData2.getCurrentmoney().toString());
			//tabUserData2.setCurrentmoney(Double.parseDouble(mstr.substring(0,mstr.length()-1)));
			Long vip = 0L;

			parame.put("userid", tabUserData2.getRowid() + "");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ApiController.getjndDate(new Date()));
			Date min1 = calendar.getTime();
			String mmm = DateUtils.formatDate(min1);
			parame.put("date1", mmm + "");

			Long count = tabUserDataService.getShangJiLinkCount(tabUserData2.getRowid());
			tabUserData2.setSumMember(count.doubleValue());
		}

		page.setList(tabUserDatas);
		return page;
	}

	@RequestMapping(value = "tabUserDataListData")
	@ResponseBody
	public Page<TabUserData> listData(TabUserData tabUserData, HttpServletRequest request,
									  HttpServletResponse response) {
		if (tabUserData.getLastlogintime_lte() != null) {
			tabUserData.setLastlogintime_lte(DateUtils.getOfDayLast(tabUserData.getLastlogintime_lte()));
		}
		if (tabUserData.getCreatetime_lte() != null) {
			tabUserData.setCreatetime_lte(DateUtils.getOfDayLast(tabUserData.getCreatetime_lte()));
		}

		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return null;
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return null;
		}
		map.put("data", tabYuangongData.getQuanxian());
		String acc = tabYuangongData.getQtzh();
		tabUserData.setShangjilink(acc);
		tabUserData.setPage(new Page<>(request, response));
		String orderby = tabUserData.getOrderBy();
		if(orderby != null && orderby.indexOf("vip") >-1){
			tabUserData.setOrderBy(orderby + ", a.createtime desc ");
		}

		Page<TabUserData> page = tabUserDataService.findPage(tabUserData);

		List<TabUserData> tabUserDatas = page.getList();
		//List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
		for (TabUserData tabUserData2 : tabUserDatas) {
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData2.getRowid());

			//Double m = tabJiesuanLogService.getUserMoney(parame);
			//String mstr = new BigDecimal(tabUserData2.getCurrentmoney()).setScale(3,BigDecimal.ROUND_UP).toString();
			tabUserData2.setHuilv(tabUserData2.getCurrentmoney().toString());
			//tabUserData2.setCurrentmoney(Double.parseDouble(mstr.substring(0,mstr.length()-1)));
			Long vip = 0L;

			parame.put("userid", tabUserData2.getRowid() + "");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ApiController.getjndDate(new Date()));
			Date min1 = calendar.getTime();
			String mmm = DateUtils.formatDate(min1);
			parame.put("date1", mmm + "");

			Long count = tabUserDataService.getShangJiLinkCount(tabUserData2.getRowid());
			tabUserData2.setSumMember(count.doubleValue());
		}

		page.setList(tabUserDatas);
		return page;
	}

	@RequestMapping(value = "tabOrderDataListData")
	@ResponseBody
	public Page<TabOrderData> tabOrderDataListData(TabOrderData tabOrderData, HttpServletRequest request,
												   HttpServletResponse response) {

		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();
		tabOrderData.setShangjilink(acc);

		tabOrderData.setPage(new Page<>(request, response));
		Page<TabOrderData> page = tabOrderDataService.findPage(tabOrderData);
		return page;
	}

	@RequestMapping(value = "tabRechangeLogListData")
	@ResponseBody
	public Page<TabRechangeLog> tabRechangeLogListData(TabRechangeLog tabRechangeLog, HttpServletRequest request,
													   HttpServletResponse response) {

		if (tabRechangeLog.getUpdatetime_lte() != null) {
			tabRechangeLog.setUpdatetime_lte(DateUtils.getOfDayLast(tabRechangeLog.getUpdatetime_lte()));

		}

		if (tabRechangeLog.getCreatetime_lte() != null) {
			tabRechangeLog.setCreatetime_lte(DateUtils.getOfDayLast(tabRechangeLog.getCreatetime_lte()));

		}

		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();
		tabRechangeLog.setShangjilink(acc);

		tabRechangeLog.setPage(new Page<>(request, response));
		Page<TabRechangeLog> page = tabRechangeLogService.findPage(tabRechangeLog);
		return page;
	}

	@RequestMapping(value = "tabTixianLogListData")
	@ResponseBody
	public Page<TabTixianLog> tabTixianLogListData(TabTixianLog tabTixianLog, HttpServletRequest request,
												   HttpServletResponse response) {
		if (tabTixianLog.getUpdatetime_lte() != null) {
			tabTixianLog.setUpdatetime_lte(DateUtils.getOfDayLast(tabTixianLog.getUpdatetime_lte()));

		}
		Date max  =  tabTixianLog.getCreatetime_lte();
		if(max != null) {
			String  s = DateUtils.formatDate(max,"yyyy-MM-dd HH:mm")  ;

			if(s.endsWith("00:00")) {
				tabTixianLog.setCreatetime_lte(DateUtils.getOfDayLast(tabTixianLog.getCreatetime_lte()));
			}
		}


		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();
		tabTixianLog.setShangjilink(acc);

		tabTixianLog.setPage(new Page<>(request, response));
		Page<TabTixianLog> page = tabTixianLogService.findPage(tabTixianLog);

		List<TabTixianLog> tabTixianLogs = page.getList();
		for (TabTixianLog tabTixianLog2 : tabTixianLogs) {

			String cardType = tabTixianLog2.getCardtype();

			TabUserBank tabUserBank = tabUserBankService.get(cardType);

			if (tabUserBank != null) {
				tabTixianLog2.setCardtype(tabUserBank.getFirstname());
			} else {
				tabTixianLog2.setCardtype("账号已删除");

			}
			TabUserData tabUserData = tabUserDataService.get(tabTixianLog2.getUserid());
			tabTixianLog2.setXdzt1(tabUserData.getXdzt1());
		}
		page.setList(tabTixianLogs);
		return page;
	}




	@RequestMapping(value = "tabTixianLogTongJi")
	@ResponseBody
	public Map<String, Object> tabTixianLogTongJi(TabTixianLog tabRechangeLog, HttpServletRequest request,
												  HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (tabRechangeLog.getCreateDate_lte() != null) {
			tabRechangeLog.setCreateDate_lte(DateUtils.getOfDayLast(tabRechangeLog.getCreatetime_lte()));
		}

		if (tabRechangeLog.getCreatetime_gte() != null) {
			tabRechangeLog.setCreatetime_gte(DateUtils.getOfDayLast(tabRechangeLog.getCreatetime_gte()));

		}
		String userid = tabRechangeLog.getUserid();
		Date min = tabRechangeLog.getCreatetime_gte();
		Date max = tabRechangeLog.getCreatetime_lte();

		if (!StringUtils.isBlank(userid)) {

			map.put("userid", userid);
		}

		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();
		tabRechangeLog.setShangjilink(acc);

		String shangjis = tabRechangeLog.getShangjilink();

		if (!StringUtils.isBlank(shangjis)) {

			map.put("shangjis", "%" + shangjis + "%");
		}

		if (min != null) {

			map.put("min", DateUtils.formatDate(min, "yyyy-MM-dd HH:mm:ss"));
		}

		if (max != null) {

			map.put("max", DateUtils.formatDate(max, "yyyy-MM-dd HH:mm:ss"));
		}

		Date min1 = tabRechangeLog.getUpdatetime_gte();
		Date max1 = tabRechangeLog.getUpdatetime_lte();

		if (min1 != null) {

			map.put("min1", DateUtils.formatDate(min1, "yyyy-MM-dd HH:mm:ss"));
		}

		if (max1 != null) {
			max1 = DateUtils.getOfDayLast(max1);

			map.put("max1", DateUtils.formatDate(max1, "yyyy-MM-dd HH:mm:ss"));
		}

		Double zczje = tabTixianLogService.getCzje(map);
		Long bs = tabTixianLogService.getCzbs(map);

		map.put("czje", zczje);
		map.put("bs", bs);

		return map;
	}

	@RequestMapping(value = "tabRechangeLogTongJi")
	@ResponseBody
	public Map<String, Object> tabRechangeLogTongJi(TabRechangeLog tabRechangeLog, HttpServletRequest request,
													HttpServletResponse response) {

		Map<String, Object> map = new HashMap();

		String userid = tabRechangeLog.getUserid();
		Date min = tabRechangeLog.getCreatetime_gte();
		Date max = tabRechangeLog.getCreatetime_lte();

		if (!StringUtils.isBlank(userid)) {

			map.put("userid", userid);
		}
		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();
		tabRechangeLog.setShangjilink(acc);

		String shangjis = tabRechangeLog.getShangjilink();

		if (!StringUtils.isBlank(shangjis)) {

			map.put("shangjis", "%" + shangjis + "%");
		}

		if (min != null) {

			map.put("min", DateUtils.formatDate(min, "yyyy-MM-dd HH:mm:ss"));
		}

		if (max != null) {
			max = DateUtils.getOfDayLast(max);

			map.put("max", DateUtils.formatDate(max, "yyyy-MM-dd HH:mm:ss"));
		}

		Date min1 = tabRechangeLog.getUpdatetime_gte();
		Date max1 = tabRechangeLog.getUpdatetime_lte();

		if (min1 != null) {

			map.put("min1", DateUtils.formatDate(min1, "yyyy-MM-dd HH:mm:ss"));
		}

		if (max1 != null) {
			max1 = DateUtils.getOfDayLast(max1);

			map.put("max1", DateUtils.formatDate(max1, "yyyy-MM-dd HH:mm:ss"));
		}

		Double zczje = tabRechangeLogService.getCzje(map);
		Long bs = tabRechangeLogService.getCzbs(map);

		map.put("czje", zczje);
		map.put("bs", bs);

		return map;

	}

	@RequestMapping(value = { "tabJiesuanLogList", "" })
	public String tabJiesuanLogList(TabJiesuanLog tabJiesuanLog, Model model) {
		model.addAttribute("tabJiesuanLog", tabJiesuanLog);

		return "modules/daili/tabJiesuanLogList";
	}

	@RequestMapping(value = "tabJiesuanLogListData")
	@ResponseBody
	public Page<TabJiesuanLog> tabJiesuanLogListData(TabJiesuanLog tabJiesuanLog, HttpServletRequest request,
													 HttpServletResponse response) {

		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();
		tabJiesuanLog.setShangjilink(acc);

		if (tabJiesuanLog.getCreatetime_lte() != null) {
			tabJiesuanLog.setCreatetime_lte(DateUtils.getOfDayLast(tabJiesuanLog.getCreatetime_lte()));
		}

		tabJiesuanLog.setPage(new Page<>(request, response));
		Page<TabJiesuanLog> page = tabJiesuanLogService.findPage(tabJiesuanLog);
		List<TabJiesuanLog> list = page.getList();
		/*for(TabJiesuanLog bean : list){
			Double d = bean.getAmont();
			String s = new BigDecimal(d).setScale(3,BigDecimal.ROUND_UP).toString();
			bean.setAmont(Double.parseDouble(s.substring(0,s.length()-1)));
		}*/
		return page;
	}

	@RequestMapping(value = "jisuan")
	@ResponseBody
	public Map<String, Object> jisuan(TabJiesuanLog tabJiesuanLog, HttpServletRequest request,
									  HttpServletResponse response) {
		TabUserData tabUserData = new TabUserData();
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		List<TabUserData> tabUserDatas = tabUserDataService.findList(tabUserData);
		for (TabUserData tabUserData2 : tabUserDatas) {
			tabJiesuanLog.setUserid(tabUserData2.getRowid());
			List<TabJiesuanLog> tabJiesuanLogs = tabJiesuanLogService.findList(tabJiesuanLog);

			for (TabJiesuanLog logs : tabJiesuanLogs) {
				logs.setParentid1(tabUserData2.getParentid());
				logs.setParentid2(tabUserData2.getParentid1());
				logs.setParentid3(tabUserData2.getParenti3());
				logs.setShangjilink(tabUserData2.getShangjilink());
				tabJiesuanLogService.save(logs);

				//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(logs));
				//getuservip(tabJiesuanLog.getUserid());
			}
		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = "tabUserDataForm")
	public String tabUserDataForm(TabUserData tabUserData, Model model) {
		tabUserData.setCurrentmoney(0d);
		model.addAttribute("tabUserData", tabUserData);
		return "modules/daili/tabUserDataForm";
	}

	@RequestMapping(value = "tabUserDataForm2")
	public String tabUserDataForm2(TabUserData tabUserData, Model model) {
		tabUserData.setCurrentmoney(0d);
		model.addAttribute("tabUserData", tabUserData);
		return "modules/daili/tabUserDataForm2";
	}

	@PostMapping(value = "tabUserDataSave")
	@ResponseBody
	public String tabUserDataSave(TabUserData tabUserData, HttpServletRequest request, Model model) {
		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return null;
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return null;
		}
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String txpassword = tabUserData.getTxpassword();
		String rowid = tabUserData.getRowid();
		Double m = tabUserData.getCurrentmoney();
		tabUserData = tabUserDataService.get(rowid);
		TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
		String orderId = DateUtils.formatDate(new Date()).replaceAll("-", "") + new ApiController().getCode();
		tabJiesuanLog.setUserid(tabUserData.getRowid());
		tabJiesuanLog.setCmd("System add");
		tabJiesuanLog.setType(txpassword + ":操作人:" + tabYuangongData.getAcccount());
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


		if(tabUserData.getIstiyan().equals("1")){
			tabUserData.setIstiyan("0");
			tabUserData.setTyj(0D);
			tabUserData.setValidate(DateUtils.addDays(new Date(), 99999));
			tabUserDataService.save(tabUserData);
			List<TabUserData> list = new ArrayList<>();
			list.add(tabUserData);
			jedis.set(UserDataByToken+tabUserData.getAccesstoken(), JSON.toJSONString(list));
			jedis.expire(UserDataByToken+tabUserData.getAccesstoken(), 7200);
					/*TabTyjJiesuan tabTyjJiesuan = new TabTyjJiesuan();
					tabTyjJiesuan.setUserid(tabRechangeLog.getUserid());
					List<TabTyjJiesuan> tabTyjJiesuans = tabTyjJiesuanService.findList(tabTyjJiesuan);
					for (TabTyjJiesuan tabTyjJiesuan2 : tabTyjJiesuans) {
						tabTyjJiesuanService.delete(tabTyjJiesuan2);
					}*/
			tabTyjJiesuanService.deleteByUserId(tabUserData.getUserid());
			//}
		}


		tabJiesuanLogService.save(tabJiesuanLog);

		dd(tabJiesuanLog,jedis);
		//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
		//getuservip(tabJiesuanLog.getUserid());
		jedis.quit();

		addzhuanpan(tabJiesuanLog.getUserid());

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

	@PostMapping(value = "tabUserDataSave2")
	@ResponseBody
	public String tabUserDataSave2(@Validated TabUserData tabUserData, HttpServletRequest request) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String txpassword = tabUserData.getTxpassword();
		String rowid = tabUserData.getRowid();
		Double m = tabUserData.getCurrentmoney();
		tabUserData = tabUserDataService.get(rowid);
		tabUserDataService.save(tabUserData);
		TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
		String orderId = DateUtils.formatDate(new Date()).replaceAll("-", "") + getOrder();
		String token = (String) request.getSession().getAttribute("token");
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		tabJiesuanLog.setUserid(tabUserData.getRowid());
		tabJiesuanLog.setCmd("System Gift");
		tabJiesuanLog.setType(txpassword + ":操作人:" + tabYuangongData.getAcccount());
		tabJiesuanLog.setMsg(txpassword);
		tabJiesuanLog.setAmont(m * -1);
		tabJiesuanLog.setRowid(UUID.randomUUID().toString());
		tabJiesuanLog.setCreatetime(getjndDate(new Date()));
		tabJiesuanLog.setCurrentmoney(tabUserData.getCurrentmoney());
		tabJiesuanLog.setIsNewRecord(true);
		tabJiesuanLog.setStatus1(orderId);

		tabJiesuanLog.setParentid1(tabUserData.getParentid());
		tabJiesuanLog.setParentid2(tabUserData.getParentid1());
		tabJiesuanLog.setParentid3(tabUserData.getParenti3());
		tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
		tabJiesuanLogService.save(tabJiesuanLog);

		if(tabUserData.getIstiyan().equals("1")){
			tabUserData.setIstiyan("0");
			tabUserData.setTyj(0D);
			tabUserData.setValidate(DateUtils.addDays(new Date(), 99999));
			tabUserDataService.save(tabUserData);
			List<TabUserData> list = new ArrayList<>();
			list.add(tabUserData);
			jedis.set(UserDataByToken+tabUserData.getAccesstoken(), JSON.toJSONString(list));
			jedis.expire(UserDataByToken+tabUserData.getAccesstoken(), 7200);
					/*TabTyjJiesuan tabTyjJiesuan = new TabTyjJiesuan();
					tabTyjJiesuan.setUserid(tabRechangeLog.getUserid());
					List<TabTyjJiesuan> tabTyjJiesuans = tabTyjJiesuanService.findList(tabTyjJiesuan);
					for (TabTyjJiesuan tabTyjJiesuan2 : tabTyjJiesuans) {
						tabTyjJiesuanService.delete(tabTyjJiesuan2);
					}*/
			tabTyjJiesuanService.deleteByUserId(tabUserData.getUserid());
			//}
		}



		dd(tabJiesuanLog,jedis);
		//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
		//getuservip(tabJiesuanLog.getUserid());
		String uuid2 = tabJiesuanLog.getUserid();
		jedis.quit();
		return renderResult(Global.TRUE, text("下分成功"));
	}

	@RequestMapping(value = { "/tabUserDataUpdatePassword", "" })
	@ResponseBody
	public Map<String, Object> tabUserDataUpdatePassword(HttpServletRequest request) {

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

	@RequestMapping(value = { "/", "" })
	public String jieSuoQuanBu1(HttpServletRequest request) {
		return "modules/daili/404";
	}


	@RequestMapping(value = "gengxinTiYan")
	@ResponseBody
	public String gengxinTiYan(HttpServletRequest servletRequest) {
		String token = (String) servletRequest.getSession().getAttribute("token");
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		String rowids = servletRequest.getParameter("rowids");
		TabAutoConfig obj = new TabAutoConfig();
		obj.setIsqidong("1");
		List<TabAutoConfig> tabAutoConfigs = tabAutoConfigService.findList(obj);

		if (tabAutoConfigs.size() > 0) {
			obj = tabAutoConfigs.get(0);
		}

		String[] split = rowids.split(",");
		for (String string : split) {
			TabUserData tabUserData = tabUserDataService.get(string);
			if(tabUserData.getCurrentmoney()>=15){
				continue;
			}
			TabTyjJiesuan tabTyjJiesuan = new TabTyjJiesuan();
			tabTyjJiesuan.setUserid(tabUserData.getRowid());
			List<TabTyjJiesuan> jiesuanList = tabTyjJiesuanService.findList(tabTyjJiesuan);
			if(jiesuanList.size()>0){
				continue;
			}
			if (tabUserData.getVip() == 0) {
				tabUserData.setVip(1L);
				tabUserData.setTyj(obj.getTyj());
				tabUserData.setIstiyan("1");

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(getjndDate(new Date()));
				calendar.add(Calendar.DAY_OF_MONTH, obj.getTianshu());

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				//tabUserData.setValidate(DateUtils.getOfDayFirst(calendar.getTime()));
				try {
					tabUserData.setValidate(simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime())));
				}catch (Exception e){

				}
				TabTyjJiesuan obj22 = new TabTyjJiesuan();
				obj22.setRowid(UUID.randomUUID().toString());
				obj22.setUserid(tabUserData.getRowid());
				obj22.setAmont(obj.getTyj());
				obj22.setCreatetime(getjndDate(new Date()));
				obj22.setIsNewRecord(true);
				tabUserData.setTycs(1L);
				obj22.setType(tabYuangongData.getAcccount());
				tabUserDataService.save(tabUserData);

				tabTyjJiesuanService.save(obj22);
				Jedis jedis =  new Jedis("41.72.149.115", 6379);
				jedis.auth("hask071bend");
				if(StringUtils.isBlank(tabUserData.getAccesstoken())){
					tabUserData.setAccesstoken(tabUserData.getRowid());
				}

				String  data  = jedis.get(TOKEN+tabUserData.getAccesstoken());
				org.json.JSONObject  jsonObject  =  new org.json.JSONObject();
				if (StringUtils.isBlank(data) ||  data.equals("{}")) {
					List<TabUserData> tabUserDatas  = getUserDataByToken(tabUserData);
					tabUserData =  tabUserDatas.get(0);
					jsonObject.put("rowid", tabUserData.getRowid());
					jsonObject.put("vip",1L);
				}else{
					jsonObject = new org.json.JSONObject(data);
					jsonObject.put("vip",1L);

				}
				List<TabUserData> list = new ArrayList<>();
				list.add(tabUserData);
				if(StringUtils.isBlank(tabUserData.getAccesstoken())){
					tabUserData.setAccesstoken(tabUserData.getRowid());
				}
				jedis.set(TOKEN+tabUserData.getAccesstoken(), jsonObject.toString());
				jedis.expire(TOKEN+tabUserData.getAccesstoken(),7200);

				jedis.set("UserDataByToken:"+tabUserData.getAccesstoken(), JSON.toJSONString(list));
				jedis.expire("UserDataByToken:"+tabUserData.getAccesstoken(),7200);
				jedis.quit();
			}
		}
		// 设置体验会员
		return renderResult(Global.TRUE, text("下分成功"));
	}

	@RequestMapping(value = "tabUserDataListData2")
	@ResponseBody
	public Page<TabUserData> tabUserDataListData2(TabUserData tabUserData, HttpServletRequest request,
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

		//List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
		for (TabUserData tabUserData2 : tabUserDatas) {
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData2.getRowid());
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData2.getCurrentmoney();
			tabUserData2.setHuilv(m.toString());

			Long count = tabUserDataService.getShangJiLinkCount(tabUserData2.getRowid());
			tabUserData2.setSumMember(count.doubleValue());

			/*Long vip = 0L;



			parame.put("userid", tabUserData2.getRowid().toString());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ApiController.getjndDate(new Date()));
			Date min1 = calendar.getTime();
			String mmm = DateUtils.formatDate(min1);
			parame.put("date1", mmm + "");
			String zt = tabOrderDataService.getLastData(parame);
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
			tabUserData2.setXdzt(zt);
			/*if (tabUserDatas.size() == 1) {
				Double shouru = tabJiesuanLogService.getShouruData(parame);
				tabUserData2.setGrsy(shouru);
				m = tabRechangeLogService.getSum(parame);
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

	@RequestMapping(value = "tabUserDataListData3")
	@ResponseBody
	public Page<TabUserData> tabUserDataListData3(TabUserData tabUserData, HttpServletRequest request,
												  HttpServletResponse response) {
		List<TabUserData> tabUserDatas = new ArrayList<TabUserData>();
		TabUserData userData = new TabUserData();
		tabUserData.setShangjilink(tabUserData.getRowid());
		tabUserData.setPage(new Page<>(request, response));
		tabUserData.setRowid(null);
		Page<TabUserData> page = tabUserDataService.findPage(tabUserData);
		tabUserDatas = page.getList();
		List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
		for (TabUserData tabUserData2 : tabUserDatas) {
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData2.getRowid());
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData2.getCurrentmoney();
			tabUserData2.setHuilv(m.toString());



			parame.put("userid", tabUserData2.getRowid() + "");
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

				m = tabRechangeLogService.getSum(parame);
				tabUserData2.setCzje(m);

				Double ztx = tabTixianLogService.getSum(parame);
				tabUserData2.setTxje(ztx);

				Double mm = tabJiesuanLogService.getShouruData(parame);
				tabUserData2.setGrsy(mm);

				Double m1 = tabJiesuanLogService.getTuandui(parame);
				////
				tabUserData2.setTdsy(m1);
			}*/


		}
		page.setList(tabUserDatas);
		return page;
	}

	@RequestMapping(value = "tabUserDataListData4")
	@ResponseBody
	public Page<TabUserData> tabUserDataListData4(TabUserData tabUserData, HttpServletRequest request,
												  HttpServletResponse response) {
		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return null;
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return null;
		}
		TabUserData tabUserData1 = tabUserDataService.get(tabUserData.getRowid());
		if(tabUserData1 == null){
			return null;
		}
		String shangjilink = tabUserData1.getShangjilink();
		Page<TabUserData> page = new Page<>();
		if(shangjilink.indexOf(tabYuangongData.getQtzh()) == -1){
			page.setPageNo(0);
			page.setPageSize(20);
			page.setList(null);
			page.setCount(0);
			return page;
		}
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

	@RequestMapping(value = "tabRechangeLogChangeData")
	@ResponseBody
	public Map<String, Object> tabRechangeLogChangeData(HttpServletRequest request, HttpServletResponse response) {
		String id =  UserUtils.getLoginInfo().getId();
		User user   =  userService.get(id);
		if(user == null || !user.getLoginCode().equals("admin")){
			return null;
		}
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String rowids = request.getParameter("rowids");
		String[] ss = rowids.split(",");
		for (String string : ss) {
			TabRechangeLog tabRechangeLog = tabRechangeLogService.get(string);
			String userId = tabRechangeLog.getUserid();

			if (tabRechangeLog.getStatus1().equals("1") || tabRechangeLog.getStatus1().equals("3")) {
				tabRechangeLog.setStatus1("2");
				tabRechangeLog.setBeizhu("支付成功tabRechangeLogChangeData1");
				tabRechangeLog.setUpdatetime(getjndDate(new Date()));
				try {
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
					tabRechangeLog.setRefundtime(format1.parse(format1.format(getjndDate(new Date()))));
				}catch (Exception e){

				}
				System.out.println("查看充值数据tabRechangeLogChangeData1："+ JSONObject.toJSONString(tabRechangeLog));

				TabUserData tabUserData =tabUserDataService.get(userId);
				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setUserid(tabRechangeLog.getUserid());
				tabJiesuanLog.setCmd("Recharge");
				tabJiesuanLog.setType("充值成功");
				if(tabRechangeLog.getOrderType() == 0){
					tabJiesuanLog.setMsg("Recharge successfully");
				}else{
					tabJiesuanLog.setMsg("Superior Recharge");
				}
				tabJiesuanLog.setAmont(tabRechangeLog.getHzhb());
				tabJiesuanLog.setRowid(UUID.randomUUID().toString());
				tabJiesuanLog.setCreatetime(getjndDate(new Date()));
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
				tabRechangeLogService.save(tabRechangeLog);
				tabJiesuanLogService.save(tabJiesuanLog);



				if(tabUserData.getIstiyan().equals("1")){
					tabUserData.setIstiyan("0");
					tabUserData.setTyj(0D);
					tabUserData.setValidate(DateUtils.addDays(new Date(), 99999));
					tabUserDataService.save(tabUserData);
					List<TabUserData> list = new ArrayList<>();
					list.add(tabUserData);
					jedis.set(UserDataByToken+tabUserData.getAccesstoken(), JSON.toJSONString(list));
					jedis.expire(UserDataByToken+tabUserData.getAccesstoken(), 7200);
					/*TabTyjJiesuan tabTyjJiesuan = new TabTyjJiesuan();
					tabTyjJiesuan.setUserid(tabRechangeLog.getUserid());
					List<TabTyjJiesuan> tabTyjJiesuans = tabTyjJiesuanService.findList(tabTyjJiesuan);
					for (TabTyjJiesuan tabTyjJiesuan2 : tabTyjJiesuans) {
						tabTyjJiesuanService.delete(tabTyjJiesuan2);
					}*/
					tabTyjJiesuanService.deleteByUserId(tabUserData.getUserid());
					//}
				}
				dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));

				//getuservip(tabJiesuanLog.getUserid());
			}
		}
		jedis.quit();
		return null;
	}

	@RequestMapping(value = "tabRechangeLogChangeData2")
	@ResponseBody
	public Map<String, Object> tabRechangeLogChangeData2(HttpServletRequest request, HttpServletResponse response) {

		String id =  UserUtils.getLoginInfo().getId();
		User user   =  userService.get(id);
		if(user == null || !user.getLoginCode().equals("admin")){
			return null;
		}
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String rowids = request.getParameter("rowids");
		String[] ss = rowids.split(",");

		for (String string : ss) {

			TabRechangeLog tabRechangeLog = tabRechangeLogService.get(string);

			String userId = tabRechangeLog.getUserid();
			TabUserData tabUserData = tabUserDataService.get(userId);
			if (tabRechangeLog.getStatus1().equals("2")) {

				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setUserid(tabRechangeLog.getUserid());
				tabJiesuanLog.setCmd("Recharger");
				tabJiesuanLog.setType("充值失败");
				tabJiesuanLog.setMsg("Recharge failed");
				tabJiesuanLog.setAmont(tabRechangeLog.getHzhb() * -1);
				tabJiesuanLog.setRowid(UUID.randomUUID().toString());
				tabJiesuanLog.setCreatetime(getjndDate(new Date()));
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLog.setStatus1(tabRechangeLog.getOrderid());
				tabJiesuanLog.setShangjilink(tabRechangeLog.getShangjilink());
				tabJiesuanLog.setParentid1(tabRechangeLog.getParentid1());
				tabJiesuanLog.setParentid2(tabRechangeLog.getParentid2());
				tabJiesuanLog.setParentid3(tabRechangeLog.getParentid3());

				Map<String, String> parame = new HashMap<String, String>();
				parame.put("userid", tabRechangeLog.getUserid());

				tabJiesuanLogService.save(tabJiesuanLog);
				if(tabUserData.getIstiyan().equals("1")){
					TabVipConfig config = new TabVipConfig();
					config.setVip("1");
					List<TabVipConfig> tabVipConfigs = getVipConfigList(config);
					Double mm = 15.0;
					if(tabVipConfigs.size() > 0){
						mm = tabVipConfigs.get(0).getCurrentmoney();
					}
					if (tabUserData.getCurrentmoney() +tabJiesuanLog.getAmont() >= mm) {
						tabUserData.setIstiyan("0");
						tabUserData.setValidate(DateUtils.addDays(new Date(), 99999));
						tabUserDataService.save(tabUserData);
					}
				}

				//getuservip(tabJiesuanLog.getUserid());
				tabRechangeLog.setStatus1("3");
				tabRechangeLog.setBeizhu("支付失败");
				tabRechangeLog.setUpdatetime(getjndDate(new Date()));
				System.out.println("查看充值数据tabRechangeLogChangeData2："+ JSONObject.toJSONString(tabRechangeLog));
				tabRechangeLogService.save(tabRechangeLog);
				dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
			}
		}
		jedis.quit();
		return null;
	}

	@PostMapping(value = "tabTixianLogChangeData")
	@ResponseBody
	public String tabTixianLogChangeData(HttpServletRequest httpServletRequest) {
		// 设置成功
		String rowids = httpServletRequest.getParameter("rowids");
		String[] s = rowids.split(",");
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		for (String string : s) {
			TabTixianLog tabTixianLog = tabTixianLogService.get(string);
			TabUserData userData = tabUserDataService.get(tabTixianLog.getUserid());
			String status1 = tabTixianLog.getStatus1();
			if (status1.equals("1") || status1.equals("12") || status1.equals("4")) {
				tabTixianLog.setStatus1("2");
				tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
				tabTixianLogService.save(tabTixianLog);
			} else if (status1.equals("3")) {
				tabTixianLog.setStatus1("2");
				tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
				tabTixianLogService.save(tabTixianLog);

				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();

				tabJiesuanLog.setUserid(userData.getRowid());
				tabJiesuanLog.setAmont(Double.valueOf(tabTixianLog.getMoney()) * -1);
				tabJiesuanLog.setRowid(UUID.randomUUID().toString());
				tabJiesuanLog.setCmd("Retrait");
				tabJiesuanLog.setType("提现成功");
				tabJiesuanLog.setMsg("Withdraw successfully");
				tabJiesuanLog.setCreatetime(ApiController.getjndDate(new Date()));
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLog.setParentid1(userData.getParentid());
				tabJiesuanLog.setParentid2(userData.getParentid1());
				tabJiesuanLog.setParentid3(userData.getParenti3());
				tabJiesuanLog.setShangjilink(userData.getShangjilink());
				tabJiesuanLog.setStatus1(tabTixianLog.getOrderid());

				tabJiesuanLogService.save(tabJiesuanLog);

				dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
				//getuservip(tabJiesuanLog.getUserid());
			}
		}
		jedis.quit();
		return renderResult(Global.TRUE, text("保存成功！"));
	}

	@PostMapping(value = "tabTixianLogChangeData2")
	@ResponseBody
	public String tabTixianLogChangeData2(HttpServletRequest httpServletRequest) {
		String rowids = httpServletRequest.getParameter("rowids");
		String[] s = rowids.split(",");
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		for (String string : s) {
			TabTixianLog tabTixianLog = tabTixianLogService.get(string);
			TabUserData tabUserData = tabUserDataService.get(tabTixianLog.getUserid());
			String status1 = tabTixianLog.getStatus1();
			if (status1.equals("1") || status1.equals("7")) {
				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setUserid(tabUserData.getRowid());
				tabJiesuanLog.setAmont(Double.valueOf(tabTixianLog.getMoney()));
				tabJiesuanLog.setRowid(UUID.randomUUID().toString());
				tabJiesuanLog.setCmd("Retrait");
				tabJiesuanLog.setType("提现失败");
				tabJiesuanLog.setMsg("Withdraw failed");
				tabJiesuanLog.setCreatetime(getjndDate(new Date()));
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLog.setParentid1(tabUserData.getParentid());
				tabJiesuanLog.setParentid2(tabUserData.getParentid1());
				tabJiesuanLog.setParentid3(tabUserData.getParenti3());
				tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
				tabJiesuanLog.setStatus1(tabTixianLog.getOrderid());
				tabJiesuanLogService.save(tabJiesuanLog);

				dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
				//getuservip(tabJiesuanLog.getUserid());

				tabTixianLog.setStatus1("3");
				tabTixianLog.setUpdatetime(getjndDate(new Date()));
				tabTixianLogService.save(tabTixianLog);
				String orderid = tabTixianLog.getOrderid();

				tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setStatus1(orderid);
				List<TabJiesuanLog> tabJiesuanLogs = tabJiesuanLogService.findList(tabJiesuanLog);

				for (TabJiesuanLog tabJiesuanLog2 : tabJiesuanLogs) {
					tabJiesuanLog2.setZt("failed ");
					tabJiesuanLogService.save(tabJiesuanLog2);

					//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog2));
					//getuservip(tabJiesuanLog.getUserid());
				}
			}
		}

		return renderResult(Global.TRUE, text("保存成功！"));
	}

	@PostMapping(value = "tabTixianLogChangeData9")
	@ResponseBody
	public String tabTixianLogChangeData9(HttpServletRequest httpServletRequest) {
		String rowid = httpServletRequest.getParameter("rowids");

		TabTixianLog tabTixianLog = tabTixianLogService.get(rowid);

		tabTixianLog.setStatus1("12");
		tabTixianLog.setUpdatetime(getjndDate(new Date()));
		tabTixianLogService.save(tabTixianLog);

		return "";
	}

	@PostMapping(value = "getAllBack")
	@ResponseBody
	public Map getAllBack(@RequestBody Map httpServletRequest) {

		String token = httpServletRequest.get("token").toString();

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			token = list.get(0).getRowid();

			String type = "M-pesa";

			TabUserBank tabUserBank = new TabUserBank();
			tabUserBank.setTransitnumber(type);
			tabUserBank.setUserid(token);
			List<TabUserBank> data1 = tabUserBankService.findList(tabUserBank);

			type = "Airtel Money";

			tabUserBank = new TabUserBank();
			tabUserBank.setTransitnumber(type);
			tabUserBank.setUserid(token);
			List<TabUserBank> data2 = tabUserBankService.findList(tabUserBank);

			type = "Orange Money";

			tabUserBank = new TabUserBank();
			tabUserBank.setTransitnumber(type);
			tabUserBank.setUserid(token);
			List<TabUserBank> data3 = tabUserBankService.findList(tabUserBank);

			map.put("data1", data1);

			map.put("data2", data2);

			map.put("data3", data3);

		}

		return map;

	}

	@RequestMapping(value = "getJisuan22")
	@ResponseBody
	public Map getJisuan22(HttpServletRequest servletRequest) {

		String name1 = servletRequest.getParameter("name1");
		String name2 = servletRequest.getParameter("name2");

		String date1 = servletRequest.getParameter("date1");
		String date2 = servletRequest.getParameter("date2");
		Boolean qqq = true;
		if(StringUtils.isBlank(name2)){
			String id = UserUtils.getLoginInfo().getId();
			if(StringUtils.isNotBlank(id)){
				User user = userService.get(id);
				if(user != null && user.getLoginCode().equals("admin")){
					qqq = false;
				}
			}
		}else{
			qqq = false;
		}
		if(qqq){
			return null;
		}
		Map<String, String> parame = new HashMap<String, String>();
		if (!StringUtils.isBlank(name1)) {

			parame.put("name1", name1);
			TabUserData tabUserData = tabUserDataService.get(name1);

			if (tabUserData == null) {
				parame.put("name1", "dds" + name1);
			}
		}
		if (!StringUtils.isBlank(name2)) {

			parame.put("name2", "%" + name2 + "%");

			User user = new User();
			user.setLoginCode(name2);

			List<User> users = userService.findList(user);

			if (users == null || users.size() == 0) {

			} else {

				user = users.get(0);
				if (user.getId() == null) {
					parame.put("name2", "%gb" + name2 + "%");
				} else if (user.getLoginCode().equals(name2)) {

					parame.put("name2", "%" + user.getLoginCode() + "%");

				} else {
					parame.put("name2", "%gb" + name2 + "%");

				}

			}

		}
		if (!StringUtils.isBlank(date1)) {

			parame.put("date1", date1);
		}
		if (!StringUtils.isBlank(date2)) {

			parame.put("date2", date2 + " 23:59:59");
		}
		parame.put("istiyan","1");
		Double czje = tabOrdersService.getTotalMoney(parame);

		map.put("xdye", czje);

		Long cc = tabOrdersService.getTotalRenshu(parame);
		map.put("xdrs", cc);

		return map;

	}

	@RequestMapping(value = "getJisuan23")
	@ResponseBody
	public Map getJisuan23(HttpServletRequest servletRequest) {
		String name1 = servletRequest.getParameter("name1");
		String name2 = servletRequest.getParameter("name2");

		String date1 = servletRequest.getParameter("date1");
		String date2 = servletRequest.getParameter("date2");
		Boolean qqq = true;
		if(StringUtils.isBlank(name2)){
			String id = UserUtils.getLoginInfo().getId();
			if(StringUtils.isNotBlank(id)){
				User user = userService.get(id);
				if(user != null && user.getLoginCode().equals("admin")){
					qqq = false;
				}
			}
		}else{
			qqq = false;
		}
		if(qqq){
			return null;
		}
		Map<String, String> parame = new HashMap<String, String>();
		if (!StringUtils.isBlank(name1)) {

			parame.put("name1", name1);
			TabUserData tabUserData = tabUserDataService.get(name1);

			if (tabUserData == null) {
				parame.put("name1", "dds" + name1);

			}
		}
		if (!StringUtils.isBlank(name2)) {

			parame.put("name2", "%" + name2 + "%");

			User user = new User();
			user.setLoginCode(name2);

			List<User> users = userService.findList(user);

			if (users == null || users.size() == 0) {

			} else {

				user = users.get(0);
				if (user.getId() == null) {
					parame.put("name2", "%gb" + name2 + "%");
				} else if (user.getLoginCode().equals(name2)) {

					parame.put("name2", "%" + user.getLoginCode() + "%");

				} else {
					parame.put("name2", "%gb" + name2 + "%");

				}

			}

		}
		if (!StringUtils.isBlank(date1)) {

			parame.put("date1", date1);
		}
		if (!StringUtils.isBlank(date2)) {

			parame.put("date2", date2 + " 23:59:59");
		}
		parame.put("istiyan","0");
		Double czje = tabOrdersService.getTotalMoney(parame);

		map.put("xdyeVip2", czje);

		Long cc = tabOrdersService.getTotalRenshu(parame);
		map.put("xdyeVip", cc);

		return map;

	}

	@RequestMapping(value = "getHys")
	@ResponseBody
	public Map getHys(HttpServletRequest servletRequest) {
		List<TabProductData> tabProductDatas = tabProductDataService.findList(new TabProductData());
		for (TabProductData tabProductData : tabProductDatas) {
			String name = tabProductData.getName();
			name = name.split("\\$")[0];
			System.out.println(name);
			tabProductData.setName(name);
			tabProductDataService.save(tabProductData);
		}

		return map;
	}



	@RequestMapping(value = "testJisuan")
	@ResponseBody
	public Map testJisuan(HttpServletRequest httpServletRequest) throws Exception {
		List<TabUserData> tabUserDatas = tabUserDataService.findList(new TabUserData());
		TabUserData obj = new TabUserData();
		obj.setRowid("-1");
		obj.setUsertype("1");
		tabUserDatas.add(0, obj);

		for (TabUserData tabUserData : tabUserDatas) {

			if (tabUserData.getUsertype().equals("1") || tabUserData.getUsertype().equals("2")) {

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(getjndDate(new Date()));

				calendar.add(Calendar.DAY_OF_MONTH, -3);
				Date min = DateUtils.getOfDayFirst(calendar.getTime());

				String userId = tabUserData.getRowid();

				System.out.println(DateUtils.formatDate(min));

				Date date1 = min;

				Date date2 = DateUtils.getOfDayLast(date1);

				Map<String, String> parame = new HashMap<String, String>();
				if (!userId.equals("-1")) {

					parame.put("name2", "%" + userId + "%");
				}
				parame.put("date1", DateUtils.formatDate(date1, "yyyy-MM-dd HH:mm:ss"));
				parame.put("date2", DateUtils.formatDate(date2, "yyyy-MM-dd HH:mm:ss"));

				Double czje = tabRechangeLogService.getTotalMoney(parame);

				Long cc = tabRechangeLogService.getTotalRenshu(parame);

				Double scje = viewShouchongService.getTotalMoney(parame);

				Long scrs = viewShouchongService.getTotalRenshu(parame);

				Double txje = tabTixianLogService.getTotalMoney(parame);

				Long txcs = tabTixianLogService.getTotalRenshu(parame);

				Long zcrs = tabUserDataService.getRenShu(parame);

				Double txc = czje - txje;

				Long zdrs = tabOrdersService.getZdrs(parame);

				TabTongjiDays obj11 = new TabTongjiDays();

				obj11.setUserid(userId);
				obj11.setDays(min);
				obj11.setCzje(czje);
				obj11.setCzrs(cc);
				obj11.setTxje(txje);
				obj11.setTxrs(txcs);
				obj11.setZcrs(zcrs);
				obj11.setCqc(txc);
				obj11.setZdrs(zdrs);
				obj11.setScrs(scrs);
				obj11.setScje(scje);

				obj11.setRowid(UUID.randomUUID().toString());
				obj11.setParentid1(tabUserData.getParentid());
				obj11.setParentid2(tabUserData.getParentid1());
				obj11.setParentid3(tabUserData.getParenti3());
				obj11.setShangjilink(tabUserData.getShangjilink());
				obj11.setYgbh(tabUserData.getYgzh());
				obj11.setYgbh2(tabUserData.getYgzh2());
				obj11.setIsNewRecord(true);

				tabTongjiDaysService.save(obj11);

			}

		}

		return map;

	}


	@RequestMapping(value = { "caiWu", "" })
	public String caiWu(TabTongjiDays tabTongjiDays, Model model) {
		model.addAttribute("tabTongjiDays", tabTongjiDays);
		return "modules/daili/tabTongjiDaysList";
	}

	@RequestMapping(value = "tabTongjiDaysListData")
	@ResponseBody
	public Page<TabTongjiDays> tabTongjiDaysListData(TabTongjiDays tabTongjiDays, HttpServletRequest request,
													 HttpServletResponse response) {

		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();

		tabTongjiDays.setUserid(acc);

		Map<String, String> parame = new HashedMap();

		if (!StringUtils.isBlank(tabTongjiDays.getShangjilink())) {

			parame.put("userId", "%" + tabTongjiDays.getShangjilink() + "%");

		}

		if (tabTongjiDays.getDays_gte() != null) {
			parame.put("min", DateUtils.formatDate(tabTongjiDays.getDays_gte(), "yyyy-MM-dd HH:mm:ss"));

		}

		if (tabTongjiDays.getDays_lte() != null) {
			parame.put("max", DateUtils.formatDate(tabTongjiDays.getDays_lte(), "yyyy-MM-dd HH:mm:ss"));

		}

		tabTongjiDays.setPage(new Page<>(request, response));

		Page<TabTongjiDays> page = tabTongjiDaysService.findPage(tabTongjiDays);

		return page;
	}

	@RequestMapping(value = "zfCallData")
	@ResponseBody
	public String zfCallData(TabTongjiDays tabTongjiDays, HttpServletRequest request, HttpServletResponse response) {
		String admin = request.getParameter("admin");
		String orderId = request.getParameter("orderId");
		String status = request.getParameter("status");
		String amount = request.getParameter("amount");
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		System.out.println("支付后台充值回调参数：admin,orderId,status："+admin+orderId+";"+status);
		if(admin == null  || StringUtils.isBlank(admin) || !"admin".equals(admin)){
			return "The Order Could Not Be Found";
		}
		TabRechangeLog tabRechangeLog = new TabRechangeLog();
		tabRechangeLog.setOrderid(orderId);
		List<TabRechangeLog> tabRechangeLogs = tabRechangeLogService.findList(tabRechangeLog);
		if (status.equals("2") && tabRechangeLogs.size() > 0) {
			tabRechangeLog = tabRechangeLogs.get(0);
			if (tabRechangeLog.getStatus1().equals("1")) {
				tabRechangeLog.setStatus1(status);
				tabRechangeLog.setBeizhu("支付成功zfCallData");
				tabRechangeLog.setUpdatetime(getjndDate(new Date()));
				tabRechangeLog.setHzhb(Double.valueOf(amount));
				try {
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
					tabRechangeLog.setRefundtime(format1.parse(format1.format(getjndDate(new Date()))));
				}catch (Exception e){

				}
				System.out.println("短信进来自动充值数据zfCallData："+JSONObject.toJSONString(tabRechangeLog));

				TabUserData tabUserData = tabUserDataService.get(tabRechangeLog.getUserid());
				Map<String, String> parame = new HashMap<String, String>();
				parame.put("userid", tabRechangeLog.getUserid());

				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setUserid(tabRechangeLog.getUserid());
				tabJiesuanLog.setCmd("Recharger");
				tabJiesuanLog.setType("充值成功");
				if(tabRechangeLog.getOrderType() == 0){
					tabJiesuanLog.setMsg("Recharge successfully");
				}else{
					tabJiesuanLog.setMsg("Superior Recharge");
				}

				tabJiesuanLog.setAmont(Double.valueOf(amount));
				tabJiesuanLog.setRowid(tabRechangeLog.getRowid());
				tabJiesuanLog.setCreatetime(getjndDate(new Date()));
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
				tabRechangeLogService.save(tabRechangeLog);
				tabJiesuanLogService.save(tabJiesuanLog);
				if(tabUserData.getIstiyan().equals("1")){
					tabUserData.setIstiyan("0");
					tabUserData.setTyj(0D);
					tabUserData.setValidate(DateUtils.addDays(new Date(), 99999));
					tabUserDataService.save(tabUserData);
					List<TabUserData> list = new ArrayList<>();
					list.add(tabUserData);
					jedis.set(UserDataByToken+tabUserData.getAccesstoken(), JSON.toJSONString(list));
					jedis.expire(UserDataByToken+tabUserData.getAccesstoken(), 7200);

					tabTyjJiesuanService.deleteByUserId(tabUserData.getUserid());

				}
				try {
					Thread.sleep(500);
					dd(tabJiesuanLog,jedis);
				}catch (Exception e){

				}
				jedis.quit();
				return "SUCCESS";
			}else{
				jedis.quit();
				return "Recharge Has Been Completed";
			}
		}
		jedis.quit();
		return "The Order Could Not Be Found";
	}



	@RequestMapping(value = "zfCallData2")
	@ResponseBody
	public String zfCallData2(TabTongjiDays tabTongjiDays, HttpServletRequest request, HttpServletResponse response) {
		String orderId = request.getParameter("orderId");
		String status = request.getParameter("status");
		String globalpaybankdata = request.getParameter("globalpaybankdata");
		String code = "";
		TabTixianLog tabRechangeLog = new TabTixianLog();
		if(StringUtils.isNotBlank(globalpaybankdata)){
			code = globalpaybankdata.replace("运营商消息,","").replace(", 确定","").replace("取消, 发送","");

		}
		tabRechangeLog.setOrderid(orderId);
		List<TabTixianLog> tabRechangeLogs = tabTixianLogService.findList(tabRechangeLog);
		System.out.println("支付后台自动代付通知参数：orderID："+orderId+" status: "+status);
		if (status.equals("2")){
			tabRechangeLog = tabRechangeLogs.get(0);
			if (tabRechangeLog.getStatus1().equals("1") || tabRechangeLog.getStatus1().equals("4")) {
				tabRechangeLog.setStatus1(status);
				tabRechangeLog.setCode(code);
				tabRechangeLog.setUpdatetime(getjndDate(new Date()));
				tabTixianLogService.save(tabRechangeLog);
				System.out.println("代付成功通知参数：orderID："+orderId+" status: "+status);
				return "SUCCESS";
			}
			//HashMap parame = new HashMap<String, String>();
			//parame.put("userid", tabRechangeLog.getUserid());
			//Double ztx = tabTixianLogService.getSum(parame);
			//TabUserData tabUserData = tabUserDataService.get(tabRechangeLog.getUserid());
			//tabUserData.setTxje(ztx);
			//tabUserDataService.save(tabUserData);
		} else if(status.equals("3")){
			tabRechangeLog = tabRechangeLogs.get(0);
			if (tabRechangeLog.getStatus1().equals("1") || tabRechangeLog.getStatus1().equals("4")) {
				tabRechangeLog.setStatus1(status);
				tabRechangeLog.setUpdatetime(getjndDate(new Date()));
				tabTixianLogService.save(tabRechangeLog);
				tabRechangeLog.setCode(code);
				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setUserid(tabRechangeLog.getUserid());
				tabJiesuanLog.setAmont(Double.valueOf(tabRechangeLog.getMoney()));
				tabJiesuanLog.setRowid(tabRechangeLog.getRowid()+"____tixianshibai");
				tabJiesuanLog.setCmd("Withdraw");
				tabJiesuanLog.setType("提现失败");
				tabJiesuanLog.setMsg("Withdraw failed");
				tabJiesuanLog.setCreatetime(getjndDate(new Date()));
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLog.setParentid1(tabRechangeLog.getParentid1());
				tabJiesuanLog.setParentid2(tabRechangeLog.getParentid2());
				tabJiesuanLog.setParentid3(tabRechangeLog.getParentid3());
				tabJiesuanLog.setShangjilink(tabRechangeLog.getShangjilink());
				tabJiesuanLog.setStatus1(tabRechangeLog.getOrderid());

				//getuservip(tabJiesuanLog.getUserid());
				tabJiesuanLog.setYgzh(status);
				tabJiesuanLog.setYgzh2(status);
				tabJiesuanLogService.save(tabJiesuanLog);
				Jedis jedis =  new Jedis("41.72.149.115", 6379);
				jedis.auth("hask071bend");
				dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
				System.out.println("代付失败通知参数：orderID："+orderId+" status: "+status);
				jedis.quit();
				return "SUCCESS";
			}
		}
		return "The Order Has Already Been Withdrawn";
	}

	@RequestMapping(value = "listHuoDongJiangli2")
	@ResponseBody
	public Map<String, Object> listHuoDongJiangli2(@RequestBody Map<String, String> request) {

		List<TabYaoqingJiangliHuodong> yaoqingJiangliHuodongs = tabYaoqingJiangliHuodongService
				.findList(new TabYaoqingJiangliHuodong());

		map.put("data", yaoqingJiangliHuodongs);

		//查看用户抽奖次数
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);
		if (list.size() > 0) {
			TabUserData userData = list.get(0);
			String rowid = userData.getRowid();
			int z = 0;
			Map<String, String>  parame = new HashMap<String, String>();
			parame.put("parentid", rowid);

			List<TabTongji> tabTongjis  =  tabRechangeLogService.getList(parame);

			System.out.println("tabTongjis======"  +  tabTongjis.size());
			List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
			Double mm = 15.0;
			if(tabVipConfigs.size() > 0){
				mm = tabVipConfigs.get(0).getCurrentmoney();
			}
			for (TabTongji tabTongji : tabTongjis) {
				parame = new HashMap<String, String>();
				parame.put("userid", tabTongji.getUserId());
				//Double	m = tabJiesuanLogService.getUserMoney(parame);
				Double m = userData.getCurrentmoney();
				if (m >= mm) {
					z++;
				}
			}

			TabYaoqingJiangliHuodongLog obj11 = new TabYaoqingJiangliHuodongLog();
			obj11.setUserid(rowid);

			Long y = tabYaoqingJiangliHuodongLogService.findCount(obj11);

			if (z > y) {
				map.put("dqcs", z - y);
				map.put("flag", "true");
				String orderId = DateUtils.formatDate(getjndDate(new Date()), "yyyyMMddHHmmss") + getCode() + getCode();
				map.put("orderId", orderId);
				TabTemps dda = new TabTemps();
				dda.setRowid(orderId);
				dda.setUuu("1");
				dda.setIsNewRecord(true);
				tabTempsService.save(dda);
			} else {
				map.put("flag", "false");
				map.put("msg", "There are currently no raffles");
			}
		}
		map.put("code", 0);
		return map;
	}

	@RequestMapping(value = "tabYaoqingJiangliLogListData")
	@ResponseBody
	public Page<TabYaoqingJiangliLog> tabYaoqingJiangliLogListData(TabYaoqingJiangliLog tabYaoqingJiangliLog,
																   HttpServletRequest request, HttpServletResponse response) {

		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();
		tabYaoqingJiangliLog.setShangjilink(acc);

		tabYaoqingJiangliLog.setPage(new Page<>(request, response));
		Page<TabYaoqingJiangliLog> page = tabYaoqingJiangliLogService.findPage(tabYaoqingJiangliLog);
		return page;
	}


	@RequestMapping(value = "tabRechargeJiangliLogListData")
	@ResponseBody
	public Page<TabRechargeJiangliLog> tabRechargeJiangliLogListData(TabRechargeJiangliLog tabRechargeJiangliLog,
																	 HttpServletRequest request, HttpServletResponse response) {

		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();
		tabRechargeJiangliLog.setShangjilink(acc);

		tabRechargeJiangliLog.setPage(new Page<>(request, response));
		Page<TabRechargeJiangliLog> page = tabRechargeJiangliLogService.findPage(tabRechargeJiangliLog);
		return page;
	}

	@RequestMapping(value = "tabFacebookListData")
	@ResponseBody
	public Page<TabFacebook> tabFacebookListData(TabFacebook tabFacebook, HttpServletRequest request,
												 HttpServletResponse response) {

		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();
		tabFacebook.setShangjilink(acc);

		tabFacebook.setPage(new Page<>(request, response));
		Page<TabFacebook> page = tabFacebookService.findPage(tabFacebook);
		return page;
	}

	@RequestMapping(value = "tabYaoqingJiangliHuodongLogListData")
	@ResponseBody
	public Page<TabYaoqingJiangliHuodongLog> tabYaoqingJiangliHuodongLogListData(
			TabYaoqingJiangliHuodongLog tabFacebook, HttpServletRequest request, HttpServletResponse response) {

		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();
		tabFacebook.setShangjilink(acc);

		tabFacebook.setPage(new Page<>(request, response));
		Page<TabYaoqingJiangliHuodongLog> page = tabYaoqingJiangliHuodongLogService.findPage(tabFacebook);
		return page;
	}

	@RequestMapping(value = "dlas")
	@ResponseBody
	public Page<TabYaoqingJiangliHuodongLog> dlass(TabYaoqingJiangliHuodongLog tabFacebook, HttpServletRequest request,
												   HttpServletResponse response) {

		TabUserBank ll = new TabUserBank();
		List<TabUserBank> tabJiesuanLogs = tabUserBankService.findList(ll);

		for (TabUserBank tabJiesuanLog : tabJiesuanLogs) {

			String userId = tabJiesuanLog.getUserid();
			TabUserData tabUserData = tabUserDataService.get(userId);
			tabJiesuanLog.setParentid1(tabUserData.getParentid());
			tabJiesuanLog.setParentid2(tabUserData.getParentid1());
			tabJiesuanLog.setParentid3(tabUserData.getParenti3());
			tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
			tabUserBankService.save(tabJiesuanLog);

		}

		Page<TabYaoqingJiangliHuodongLog> page = tabYaoqingJiangliHuodongLogService.findPage(tabFacebook);
		return page;
	}

	@RequestMapping(value = "showVideo")
	public String showVideo(HttpServletRequest httpServletRequest, Model model) {
		String links = httpServletRequest.getParameter("links");
		model.addAttribute("links", "https://beimei.caiyaoren.ltd/" + links + ".mp4");

		return "modules/zjp/showVideo";
	}

	@RequestMapping(value = "jis211")
	@ResponseBody
	public String jis21(HttpServletRequest httpServletRequest, Model model) throws Exception {
		System.err.println(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));

		List<TabOrderData> tabOrderDatas = tabOrderDataService.findList2();
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		Map<String, TabOrderData> map = new HashMap<String, TabOrderData>();
		for (TabOrderData tabOrderData : tabOrderDatas) {
			map.put(tabOrderData.getOrderid(), tabOrderData);
		}

		Set<String> keys = map.keySet();
		for (String string : keys) {
			System.err.println(string);
			TabOrderData oo = new TabOrderData();

			oo.setOrderid(string);
			oo.setStatus1("1");
			Long cc = tabOrderDataService.findCount(oo);

			if (cc == 0) {

				// 开始更新订单
				Map<String, String> parame = new HashMap<String, String>();

				parame.put("orderid", string);

				tabOrderDataService.updateJiesuan(parame);

				TabOrderData tabOrderData = map.get(string);

				TabOrders taborders = new TabOrders();

				taborders.setOrderid(tabOrderData.getOrderid());
				List<TabOrders> tabOrders = tabOrdersService.findList(taborders);

				taborders = tabOrders.get(0);
				taborders.setPaycount(taborders.getOrdercont());
				taborders.setStatus1("4");
				tabOrdersService.save(taborders);

				// 更新订单

				// 获取总的 金额

				Double m = tabOrderDataService.getBenJin(parame);

				Double m1 = tabOrderDataService.getYOujin2(parame);

				System.out.println(m + ",," + m1);

				String userId = tabOrderData.getUserid();

				TabUserData tabUserData = tabUserDataService.get(userId);

				//
				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setUserid(userId);
				tabJiesuanLog.setRowid(userId + tabOrderData.getOrderid() + "yj");
				tabJiesuanLog.setType("Commission");
				tabJiesuanLog.setCmd("Commission");
				tabJiesuanLog.setMsg("Daily commission");
				tabJiesuanLog.setCreatetime(getjndDate(new Date()));
				tabJiesuanLog.setAmont(m1);
				tabJiesuanLog.setParentid1(tabUserData.getParentid());
				tabJiesuanLog.setParentid2(tabUserData.getParentid1());
				tabJiesuanLog.setStatus1(tabOrderData.getOrderid());

				tabJiesuanLog.setParentid3(tabUserData.getParenti3());
				tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLogService.save(tabJiesuanLog);

				dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));

				// 开始给一级 分销
				//if("0".equals(tabUserData.getIstiyan())){
				TabFenxiaoConfig tabFenxiaoConfig = tabFenxiaoConfigService.get("1");

				if (!StringUtils.isBlank(tabUserData.getParentid())) {
					// 一级数据

					TabUserData useTabUserDataLeval1 = tabUserDataService.get(tabUserData.getParentid());

					if (useTabUserDataLeval1 != null) {

						tabJiesuanLog = new TabJiesuanLog();
						tabJiesuanLog.setUserid(useTabUserDataLeval1.getRowid());
						tabJiesuanLog.setRowid(useTabUserDataLeval1.getRowid() + tabOrderData.getOrderid() + "yj");
						tabJiesuanLog.setType("Commission1");
						tabJiesuanLog.setCmd("Commission");
						tabJiesuanLog.setMsg("Subordinate rebate（L1）");
						tabJiesuanLog.setCreatetime(getjndDate(new Date()));
						tabJiesuanLog.setAmont(m1 * tabFenxiaoConfig.getLeval1() / 100.0);
						tabJiesuanLog.setParentid1(useTabUserDataLeval1.getParentid());
						tabJiesuanLog.setStatus1(tabOrderData.getOrderid());

						tabJiesuanLog.setParentid2(useTabUserDataLeval1.getParentid1());
						tabJiesuanLog.setParentid3(useTabUserDataLeval1.getParenti3());
						tabJiesuanLog.setShangjilink(useTabUserDataLeval1.getShangjilink());
						tabJiesuanLog.setIsNewRecord(true);
						tabJiesuanLogService.save(tabJiesuanLog);

						dd(tabJiesuanLog,jedis);
						//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));

					}
				}

				if (!StringUtils.isBlank(tabUserData.getParentid1())) {
					// 二级数据

					TabUserData useTabUserDataLeval2 = tabUserDataService.get(tabUserData.getParentid1());

					if (useTabUserDataLeval2 != null) {

						tabJiesuanLog = new TabJiesuanLog();
						tabJiesuanLog.setUserid(useTabUserDataLeval2.getRowid());
						tabJiesuanLog.setRowid(useTabUserDataLeval2.getRowid() + tabOrderData.getOrderid() + "yj");
						tabJiesuanLog.setType("Commission2");
						tabJiesuanLog.setCmd("Commission");
						tabJiesuanLog.setMsg("Subordinate rebate（L2）");
						tabJiesuanLog.setCreatetime(getjndDate(new Date()));
						tabJiesuanLog.setAmont(m1 * tabFenxiaoConfig.getLeval2() / 100.0);
						tabJiesuanLog.setParentid1(useTabUserDataLeval2.getParentid());
						tabJiesuanLog.setStatus1(tabOrderData.getOrderid());

						tabJiesuanLog.setParentid2(useTabUserDataLeval2.getParentid1());
						tabJiesuanLog.setParentid3(useTabUserDataLeval2.getParenti3());
						tabJiesuanLog.setShangjilink(useTabUserDataLeval2.getShangjilink());
						tabJiesuanLog.setIsNewRecord(true);
						tabJiesuanLogService.save(tabJiesuanLog);

						dd(tabJiesuanLog,jedis);
						//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
					}
				}

				if (!StringUtils.isBlank(tabUserData.getParenti3())) {
					// 二级数据

					TabUserData useTabUserDataLeval3 = tabUserDataService.get(tabUserData.getParenti3());

					if (useTabUserDataLeval3 != null) {

						tabJiesuanLog = new TabJiesuanLog();
						tabJiesuanLog.setUserid(useTabUserDataLeval3.getRowid());
						tabJiesuanLog.setRowid(useTabUserDataLeval3.getRowid() + tabOrderData.getOrderid() + "yj");
						tabJiesuanLog.setType("Commission3");
						tabJiesuanLog.setCmd("Commission");
						tabJiesuanLog.setMsg("Subordinate rebate（L3）");
						tabJiesuanLog.setCreatetime(getjndDate(new Date()));
						tabJiesuanLog.setAmont(m1 * tabFenxiaoConfig.getLeval3() / 100.0);
						tabJiesuanLog.setParentid1(useTabUserDataLeval3.getParentid());
						tabJiesuanLog.setParentid2(useTabUserDataLeval3.getParentid1());
						tabJiesuanLog.setStatus1(tabOrderData.getOrderid());

						tabJiesuanLog.setParentid3(useTabUserDataLeval3.getParenti3());
						tabJiesuanLog.setShangjilink(useTabUserDataLeval3.getShangjilink());
						tabJiesuanLog.setIsNewRecord(true);
						tabJiesuanLogService.save(tabJiesuanLog);

						dd(tabJiesuanLog,jedis);
						//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
					}
				}
				//}

				if (tabOrderData.getIstiyan() != null && tabOrderData.getIstiyan().equals("1")) {
					// 这个是 体验订单
					TabTyjJiesuan obj22 = new TabTyjJiesuan();
					obj22.setRowid(tabOrderData.getOrderid() + tabOrderData.getUserid());
					obj22.setUserid(tabOrderData.getUserid());
					obj22.setAmont(m);
					obj22.setCreatetime(getjndDate(new Date()));
					obj22.setIsNewRecord(true);
					tabTyjJiesuanService.save(obj22);
				} else {
					// 不是体验订单 需要返回本金
					System.out.println("非体坛订单");
					tabJiesuanLog = new TabJiesuanLog();
					tabJiesuanLog.setUserid(userId);
					tabJiesuanLog.setRowid(userId + tabOrderData.getOrderid() + "bj");
					tabJiesuanLog.setType("capital city");
					tabJiesuanLog.setCmd("capitale");
					tabJiesuanLog.setMsg("Principal refund");
					tabJiesuanLog.setCreatetime(getjndDate(new Date()));
					tabJiesuanLog.setAmont(m);
					tabJiesuanLog.setParentid1(tabUserData.getParentid());
					tabJiesuanLog.setParentid2(tabUserData.getParentid1());
					tabJiesuanLog.setParentid3(tabUserData.getParenti3());
					tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
					tabJiesuanLog.setIsNewRecord(true);
					tabJiesuanLog.setStatus1(tabOrderData.getOrderid());
					tabJiesuanLogService.save(tabJiesuanLog);
					dd(tabJiesuanLog,jedis);
					//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
				}
			}
		}
		jedis.quit();
		return "modules/zjp/showVideo";
	}

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

	@RequestMapping(value = { "/jisuanrenshu", "" })
	@ResponseBody
	public Map<String, Object> jisuanrenshu(HttpServletRequest request) {

		List<TabOrders> tabOrders = tabOrdersService.findList(new TabOrders());

		for (TabOrders tabOrders2 : tabOrders) {
			String oreer = tabOrders2.getOrderid();

			TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
			tabJiesuanLog.setStatus1(oreer);

			List<TabJiesuanLog> tabJiesuanLogs = tabJiesuanLogService.findList(tabJiesuanLog);

			for (TabJiesuanLog tabOrders3 : tabJiesuanLogs) {
				tabJiesuanLog = tabOrders3;
				if (tabJiesuanLog.getType().equals("Retour à la Commission")) {
					tabJiesuanLog.setType("Commission");

				} else if (tabJiesuanLog.getType().indexOf("niveau 1") > 0) {
					tabJiesuanLog.setType("Commission1");

				} else if (tabJiesuanLog.getType().indexOf("niveau 2") > 0) {
					tabJiesuanLog.setType("Commission2");

				} else if (tabJiesuanLog.getType().indexOf("niveau 3") > 0) {
					tabJiesuanLog.setType("Commission3");

				} else if (tabJiesuanLog.getType().indexOf("boursement du prin") > 0) {
					tabJiesuanLog.setType("capitale");

				}
			}

		}

		return map;
	}


	public void jj(TabOrders tabOrders2) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String string = tabOrders2.getOrderid();
		TabOrderData oo = new TabOrderData();
		oo.setOrderid(string);
		oo.setStatus1("1");
		Long cc = tabOrderDataService.findCount(oo);

		if (cc == 0) {
			tabOrders2.setPaycount(10L);
			tabOrders2.setStatus1("4");
			//oo.setOrderid(string);
			//List<TabOrderData> llll = tabOrderDataService.findList(oo);
			// 更新订单
			// 获取总的 金额
			Double m = tabOrders2.getOrdermoney();//tabOrderDataService.getBenJin(parame);
			Double m1 = tabOrders2.getLirunmonry();//tabOrderDataService.getYOujin2(parame);
			String userId = tabOrders2.getUserid();
			TabUserData tabUserData = tabUserDataService.get(userId);
			// 开始更新订单
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("orderid", string);
			tabOrderDataService.updateJiesuan(parame);


			if (tabOrders2.getIstiyan() != null && tabOrders2.getIstiyan().equals("1")) {
				// 这个是 体验订单
				TabTyjJiesuan obj22 = new TabTyjJiesuan();
				obj22.setRowid(tabOrders2.getOrderid() + tabOrders2.getUserid());
				obj22.setUserid(tabOrders2.getUserid());
				obj22.setAmont(m);
				obj22.setCreatetime(getjndDate(new Date()));
				obj22.setIsNewRecord(true);
				obj22.setStatus1(tabOrders2.getOrderid());

				tabTyjJiesuanService.save(obj22);
			} else {
				// 不是体验订单 需要返回本金
				System.out.println("非体验订单 -- 返回本金");
				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setUserid(tabOrders2.getUserid());
				tabJiesuanLog.setRowid(UUID.randomUUID() + "bj");
				tabJiesuanLog.setType("结算返回本金");
				tabJiesuanLog.setCmd("结算返回本金");
				tabJiesuanLog.setMsg("Principal refund");
				tabJiesuanLog.setCreatetime(getjndDate(new Date()));
				tabJiesuanLog.setAmont(tabOrders2.getOrdermoney());
				tabJiesuanLog.setParentid1(tabOrders2.getParentid1());
				tabJiesuanLog.setStatus1(tabOrders2.getOrderid());
				tabJiesuanLog.setParentid2(tabOrders2.getParentid2());
				tabJiesuanLog.setParentid3(tabOrders2.getParentid3());
				tabJiesuanLog.setShangjilink(tabOrders2.getShangjilink());
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLogService.save(tabJiesuanLog);
				try {
					Thread.sleep(100);
					dd(tabJiesuanLog,jedis);
				}catch (Exception e){

				}

				//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
			}



			TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
			tabJiesuanLog.setUserid(userId);
			tabJiesuanLog.setRowid(userId + tabOrders2.getOrderid() + "yj");
			tabJiesuanLog.setType("Commission");
			tabJiesuanLog.setCmd("Commission");
			tabJiesuanLog.setMsg("Daily commission");
			tabJiesuanLog.setCreatetime(getjndDate(new Date()));
			tabJiesuanLog.setAmont(m1);
			tabJiesuanLog.setParentid1(tabUserData.getParentid());
			tabJiesuanLog.setParentid2(tabUserData.getParentid1());
			tabJiesuanLog.setStatus1(tabOrders2.getOrderid());

			tabJiesuanLog.setParentid3(tabUserData.getParenti3());
			tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
			tabJiesuanLog.setIsNewRecord(true);
			try {
				tabJiesuanLogService.save(tabJiesuanLog);
				Thread.sleep(100);
				dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
			} catch (Exception e) {
			}

			// 开始给一级 分销
			TabFenxiaoConfig tabFenxiaoConfig = tabFenxiaoConfigService.get("1");
			if (!StringUtils.isBlank(tabUserData.getParentid())) {
				// 一级数据
				TabUserData useTabUserDataLeval1 = tabUserDataService.get(tabUserData.getParentid());
				if (useTabUserDataLeval1 != null) {
					tabJiesuanLog = new TabJiesuanLog();
					tabJiesuanLog.setUserid(useTabUserDataLeval1.getRowid());
					tabJiesuanLog.setRowid(useTabUserDataLeval1.getRowid() + tabOrders2.getOrderid() + "yj");
					tabJiesuanLog.setType("Commission1");
					tabJiesuanLog.setCmd("Commission");
					tabJiesuanLog.setMsg("Subordinate rebate（L1）");
					tabJiesuanLog.setCreatetime(getjndDate(new Date()));
					tabJiesuanLog.setAmont(m1 * tabFenxiaoConfig.getLeval1() / 100.0);
					tabJiesuanLog.setParentid1(useTabUserDataLeval1.getParentid());
					tabJiesuanLog.setStatus1(tabOrders2.getOrderid());
					tabJiesuanLog.setSubordinate(tabUserData.getUserid());
					tabJiesuanLog.setParentid2(useTabUserDataLeval1.getParentid1());
					tabJiesuanLog.setParentid3(useTabUserDataLeval1.getParenti3());
					tabJiesuanLog.setShangjilink(useTabUserDataLeval1.getShangjilink());
					tabJiesuanLog.setIsNewRecord(true);
					try {
						tabJiesuanLogService.save(tabJiesuanLog);
						Thread.sleep(100);
						dd(tabJiesuanLog,jedis);
						//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
					} catch (Exception e) {
					}
						/*String uuid2 = tabJiesuanLog.getUserid();
						new Runnable() {
							@Override
							public void run() {
								Map<String, String> parame2 = new HashedMap();
								GoldpaysUtil.hanYuanUtils(
										"https://e-creatoerzw.com/api/initHuancun?userid=" + uuid2, "",
										parame2);
							}
						}.run();*/
				}
			}

			if (!StringUtils.isBlank(tabUserData.getParentid1())) {
				// 二级数据

				TabUserData useTabUserDataLeval2 = tabUserDataService.get(tabUserData.getParentid1());

				if (useTabUserDataLeval2 != null) {

					tabJiesuanLog = new TabJiesuanLog();
					tabJiesuanLog.setUserid(useTabUserDataLeval2.getRowid());
					tabJiesuanLog.setRowid(useTabUserDataLeval2.getRowid() + tabOrders2.getOrderid() + "yj");
					tabJiesuanLog.setType("Commission2");
					tabJiesuanLog.setCmd("Commission");
					tabJiesuanLog.setMsg("Subordinate rebate（L2）");
					tabJiesuanLog.setCreatetime(getjndDate(new Date()));
					tabJiesuanLog.setAmont(m1 * tabFenxiaoConfig.getLeval2() / 100.0);
					tabJiesuanLog.setParentid1(useTabUserDataLeval2.getParentid());
					tabJiesuanLog.setStatus1(tabOrders2.getOrderid());
					tabJiesuanLog.setSubordinate(tabUserData.getUserid());
					tabJiesuanLog.setParentid2(useTabUserDataLeval2.getParentid1());
					tabJiesuanLog.setParentid3(useTabUserDataLeval2.getParenti3());
					tabJiesuanLog.setShangjilink(useTabUserDataLeval2.getShangjilink());
					tabJiesuanLog.setIsNewRecord(true);
					tabJiesuanLogService.save(tabJiesuanLog);
					try {
						Thread.sleep(100);
						dd(tabJiesuanLog,jedis);
					}catch (Exception e){

					}
					//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));

				}
			}

			if (!StringUtils.isBlank(tabUserData.getParenti3())) {
				// 二级数据

				TabUserData useTabUserDataLeval3 = tabUserDataService.get(tabUserData.getParenti3());

				if (useTabUserDataLeval3 != null) {

					tabJiesuanLog = new TabJiesuanLog();
					tabJiesuanLog.setUserid(useTabUserDataLeval3.getRowid());
					tabJiesuanLog.setRowid(useTabUserDataLeval3.getRowid() + tabOrders2.getOrderid() + "yj");
					tabJiesuanLog.setType("Commission3");
					tabJiesuanLog.setCmd("Commission");
					tabJiesuanLog.setMsg("Subordinate rebate（L3）");
					tabJiesuanLog.setCreatetime(getjndDate(new Date()));
					tabJiesuanLog.setAmont(m1 * tabFenxiaoConfig.getLeval3() / 100.0);
					tabJiesuanLog.setParentid1(useTabUserDataLeval3.getParentid());
					tabJiesuanLog.setParentid2(useTabUserDataLeval3.getParentid1());
					tabJiesuanLog.setStatus1(tabOrders2.getOrderid());
					tabJiesuanLog.setSubordinate(tabUserData.getUserid());
					tabJiesuanLog.setParentid3(useTabUserDataLeval3.getParenti3());
					tabJiesuanLog.setShangjilink(useTabUserDataLeval3.getShangjilink());
					tabJiesuanLog.setIsNewRecord(true);
					try {
						tabJiesuanLogService.save(tabJiesuanLog);
						Thread.sleep(100);
						dd(tabJiesuanLog,jedis);
						//jedis.lpush(JIESUAN_KEY, JSON.toJSONString(tabJiesuanLog));
					} catch (Exception e) {
					}
						/*String uuid2 = tabJiesuanLog.getUserid();
						new Runnable() {
							@Override
							public void run() {
								Map<String, String> parame2 = new HashedMap();
								GoldpaysUtil.hanYuanUtils(
										"https://e-creatoerzw.com/api/initHuancun?userid=" + uuid2, "",
										parame2);
							}
						}.run();*/
				}
			}
		}
		tabOrdersService.save(tabOrders2);
		jedis.quit();
	}

	@RequestMapping(value = { "/shoudong", "" })
	@ResponseBody
	public Map<String, Object> shoudong(HttpServletRequest request) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		TabOrders o111 = new TabOrders();
		o111.setStatus1("2");
		o111.setPaycount(10L);
		List<TabOrders> tabOrders = tabOrdersService.findList(o111);
		for (TabOrders tabOrders2 : tabOrders) {
			try {
				jj(tabOrders2);
			} catch (Exception e) {
			}

		}
		o111.setPaycount(21L);
		tabOrders = tabOrdersService.findList(o111);
		for (TabOrders tabOrders2 : tabOrders) {
			try {
				jj(tabOrders2);
			} catch (Exception e) {
			}

		}
		o111.setPaycount(19L);

		tabOrders = tabOrdersService.findList(o111);
		for (TabOrders tabOrders2 : tabOrders) {
			try {
				jj(tabOrders2);
			} catch (Exception e) {
			}

		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = { "/shoudong33", "" })
	@ResponseBody
	public Map<String, Object> shoudong33(HttpServletRequest request) {

		TabOrders o111 = new TabOrders();
		o111.setStatus1("4");
		List<TabOrders> tabOrders = tabOrdersService.findList(o111);
		for (TabOrders tabOrders2 : tabOrders) {

			String ord = tabOrders2.getOrderid();

			TabOrderData tabOrderData = new TabOrderData();
			tabOrderData.setOrderid(ord);
			tabOrderData.setStatus1("2");

			Long cc = tabOrderDataService.findCount(tabOrderData);

			if (cc > 0) {
				tabOrders2.setStatus1("2");
				tabOrdersService.save(tabOrders2);
			}
		}

		return map;
	}

	@RequestMapping(value = { "/shoudong2", "" })
	@ResponseBody
	public Map<String, Object> shoudong2(HttpServletRequest request) {

		TabOrders ooo = new TabOrders();
		ooo.setPaycount(19L);
		ooo.setStatus1("2");
		ooo.setCreatetime_gte(DateUtils.getOfDayFirst(getjndDate(new Date())));

		List<TabOrders> tabOrders = tabOrdersService.findList(ooo);

		for (TabOrders tabOrders2 : tabOrders) {
			System.err.println(111);
			String orderId = tabOrders2.getOrderid();

			TabOrderData tabOrderData = new TabOrderData();
			tabOrderData.setOrderid(orderId);
			tabOrderData.setStatus1("1");
			Long cc = tabOrderDataService.findCount(tabOrderData);
			if (cc == 0) {
				tabOrders2.setPaycount(10L);
				tabOrdersService.save(tabOrders2);
			}
		}

		return map;
	}

	// 清除充值的数据
	@RequestMapping(value = { "/czdata", "" })
	@ResponseBody
	public Map<String, Object> czdata(HttpServletRequest request) {
		TabOrdersLog tabOrdersLog = new TabOrdersLog();
		tabOrdersLog.setStatus1("5");
		String orderid = request.getParameter("orderid");
		orderid = "1";
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		if (!StringUtils.isBlank(orderid)) {
			List<TabOrdersLog> tabOrdersLogs = tabOrdersLogService.findList(tabOrdersLog);
			for (TabOrdersLog tabOrdersLog2 : tabOrdersLogs) {
				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setStatus1(tabOrdersLog2.getOrderid());
				tabJiesuanLog.setCmd("Achat de produits");
				List<TabJiesuanLog> tabJiesuanLogs = tabJiesuanLogService.findList(tabJiesuanLog);
				tabJiesuanLog.setCmd("capitale");
				List<TabJiesuanLog> logs = tabJiesuanLogService.findList(tabJiesuanLog);
				tabOrdersLog2.setStatus1("5");
				tabOrdersLogService.save(tabOrdersLog2);
				tabJiesuanLogs.addAll(logs);
				for (TabJiesuanLog logs2 : tabJiesuanLogs) {
					logs2.setUserid(logs2.getUserid() + "-1");
					tabJiesuanLogService.save(logs2);
				}
			}
		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = { "/initHuancun", "" })
	@ResponseBody
	public Map<String, Object> initHuancun(HttpServletRequest request) {

		TabUserData dd1 = new TabUserData();
		String userid = request.getParameter("userid");
		dd1.setRowid(userid);
		List<TabUserData> tabUserDatas = tabUserDataService.findList(dd1);
		List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
		for (TabUserData tabUserData : tabUserDatas) {
			Map<String, String> pp = new HashedMap();
			pp.put("userid", tabUserData.getRowid());

			Double m = tabRechangeLogService.getSum(pp);
			tabUserData.setCzje(m);
			tabUserData.setHuilv( tabUserData.getCurrentmoney() + "");
			tabUserDataService.save(tabUserData);


		}

		return map;
	}



	@RequestMapping(value = "t21")
	@ResponseBody
	public Map<String, Object> t21() throws Exception {
		TabProductData ll = new TabProductData();
		List<TabProductData> tabProductDatas = tabProductDataService.findList(ll);
		for (TabProductData tabProductData : tabProductDatas) {
			if (!tabProductData.getImgsrc().startsWith("http", 0)) {
				tabProductData.setImgsrc("https://file.twcreaotr.com/" + tabProductData.getImgsrc());
				tabProductDataService.save(tabProductData);
			}
		}

		return map;
	}

	@RequestMapping(value = "t22")
	@ResponseBody
	public Map<String, Object> t22() throws Exception {
		File file = new File("/Users/gaofeng/Downloads/dsdsdds/");
		String[]  lll = file.list();
		for (String string : lll) {
			File fffff =   new File("/Users/gaofeng/Downloads/dsdsdds/" + string);

			TabProductData tabProductData  = new TabProductData();
			System.err.println(string);
			tabProductData.setName(string)			;
			String[]  name= string.split(" ");
			String lastN = name[name.length-1];
			String n =  string.replaceAll(lastN, "");
			tabProductData.setLeval("6");
			tabProductData.setImgsrc(string);
			String mm  =   lastN.toUpperCase().replaceAll("TZS.PNG", "");
			tabProductData.setBuymoney(Double.valueOf(mm));
			TabProductData  productData  = new TabProductData();
			productData.setName(n) ;
			Long    cc =   tabProductDataService.findCount(productData) ;
			if(cc ==0 ) {
				String urls=	HttpServletRequestUtils.uploadFiles(fffff);
				tabProductData.setName(n);
				tabProductData.setRowid(UUID.randomUUID().toString());
				tabProductData.setIsNewRecord(true);
				tabProductData.setImgsrc(urls);
				System.out.println(n.length() +",," + lastN);
				tabProductDataService.save(tabProductData);
				fffff.delete();
			}else {
				fffff.delete();
			}
		}
		return map;
	}

	/*@RequestMapping(value = "getUserBanks3")
	@ResponseBody
	public Map<String, Object> getUserBanks3(HttpServletRequest request) {

		String  orderid = request.getParameter("orderid");

		if(!StringUtils.isBlank(orderid)) {
			TabOrders tabOrders =  new TabOrders() ;

			tabOrders.setOrderid(orderid); ;

			List<TabOrders> orders  = tabOrdersService.findList(tabOrders);

			if(orders.size() ==1) {
				for (TabOrders tabOrders2 : orders) {
					tabOrdersService.delete(tabOrders2);
				}
			}

			TabOrderData tabOrderData  = 	  new TabOrderData() ;

			tabOrderData.setOrderid(orderid);

			List<TabOrderData> tabOrderDatas   =  tabOrderDataService.findList(tabOrderData);

			if(tabOrderDatas.size()   <= 20 ) {
				for (TabOrderData tabOrderData2 : tabOrderDatas) {
					tabOrderDataService.delete(tabOrderData2);
				}
			}

			TabJiesuanLog tabJiesuanLog  = new TabJiesuanLog();
			tabJiesuanLog.setStatus1(orderid);
			List<TabJiesuanLog> tabJiesuanLogs  =   tabJiesuanLogService.findList(tabJiesuanLog);
			for (TabJiesuanLog tabJiesuanLog2 : tabJiesuanLogs) {
				tabJiesuanLogService.delete(tabJiesuanLog2); ;
			}
		}
		return map;
	}*/

	@RequestMapping(value = "getUserBanks4")
	@ResponseBody
	public Map<String, Object> getUserBanks4(HttpServletRequest request) {
		TabTixianLog obj = new TabTixianLog();
		String userid  =  request.getParameter("userid");
		obj.setUserid(userid);
		List<TabTixianLog> tabTixianLogs  = tabTixianLogService.findList(obj );
		for (TabTixianLog tabTixianLog : tabTixianLogs) {

			String car =   tabTixianLog.getCardtype() ;

			TabUserBank tabUserBank  = new TabUserBank();

			tabUserBank.setRowid(car);

			List<TabUserBank> tabUserBanks   = tabUserBankService.findList(tabUserBank);

			if (tabUserBanks.size() ==1   ) {
				tabTixianLog.setType(tabUserBanks.get(0).getTransitnumber());
				tabTixianLogService.save(tabTixianLog);
			}
		}
		return map;
	}


	@RequestMapping(value = "getInfo")
	@ResponseBody
	public Map<String, Object> getInfo(HttpServletRequest request) throws Exception {
		User user =  UserUtils.getUser();
		map.put("user", user);
		return map;
	}


	/*@RequestMapping(value = "redisData")
	@ResponseBody
	public Map<String, Object> redisData(HttpServletRequest request) throws Exception {
		String  rowid =  request.getParameter("rowid");
		TabUserData oo = new TabUserData();
		oo.setRowid(rowid);
		List<TabUserData> tabUserDatas  = tabUserDataService.findList(oo );
		jedis.auth("hask071bend");
		for (TabUserData tabUserData : tabUserDatas) {
			tabUserData.setTycs(12L);
			tabUserDataService.save(tabUserData);
			org.json.JSONObject  jsonObject  =  new org.json.JSONObject();
			jsonObject.put("rowid", tabUserData.getRowid());
			Map<String, String> pp = new HashedMap();
			pp.put("userid", tabUserData.getRowid());
			Double m = tabRechangeLogService.getSum(pp);
			jsonObject.put("czje", m);
			Double ztx = tabTixianLogService.getSum(pp);
			jsonObject.put("txje", ztx);
			//Double mm = tabJiesuanLogService.getShouruData(pp);
			Double mm = tabUserData.getGrsy();
			jsonObject.put("grsr", mm);
			//Double m1 = tabJiesuanLogService.getTuandui(pp);
			Double m1 = tabUserData.getTdsy();
			jsonObject.put("tdsr", m1);
			//Double  ye =  tabJiesuanLogService.getUserMoney(pp) ;
			Double ye = tabUserData.getCurrentmoney();
			jsonObject.put("ye", ye);
			jsonObject.put("vip", 0);
			String  json = jsonObject.toString();
			jedis.set(TOKEN+tabUserData.getAccesstoken(), json);
			jedis.expire(TOKEN+tabUserData.getAccesstoken(), 7200);
			System.out.println("-----zhengzai 更新" +  json);
		}
		return map;
	}*/

	@RequestMapping(value = "budan")
	@ResponseBody
	public Map<String, Object > budan(  HttpServletRequest request, HttpServletResponse response) {
		String  orderId = request.getParameter("orderId");
		TabRechangeLog  tabRechangeLog= new TabRechangeLog();
		tabRechangeLog.setOrderid(orderId);
		List<TabRechangeLog> rechangeLogs =    tabRechangeLogService.findList(tabRechangeLog);
		tabRechangeLog = rechangeLogs.get(0);
		Map<String, String> parame = new HashMap<>();
		String appid = "b7b31605-7172-4acb-957c-15d401fae6d9";
		String appset = "d18c4c73-3565-4aab-9431-1587255dd230";
		parame.put("appid", appid);
		parame.put("orderId", tabRechangeLog.getOrderid());
		parame.put("orderMoney", tabRechangeLog.getHzhb() + "");
		parame.put("orderMoney1", tabRechangeLog.getHzhb() + "");
		parame.put("notifyUrl", "http://41.72.149.115:8081/api/zfCallData");
		parame.put("type", tabRechangeLog.getPaytype());
		parame.put("tongdao", tabRechangeLog.getType());
		String tempSign = GoldpaysUtil.getSign2(parame, appset);
		parame.put("sign", tempSign);
		System.out.println(" sign " + tempSign);
		String s = GoldpaysUtil
				.hanYuanUtils("http://41.72.149.115:8081/metaPay/api/createLouisDaiShou", "", parame);

		System.out.println(s);
		return null;
	}

	@PostMapping(value = "notify")
	public void notify(@RequestBody JSONObject object,HttpServletResponse response){
		System.out.println("三方提现回调参数:"+object.toJSONString());
		Integer code = object.getInteger("code");
		String msg =object.getString("msg");
		JSONObject data = object.getJSONObject("data");
		String orderId = data.getString("out_order_sn");
		TabTixianLog tabTixianLog =tabTixianLogService.getByOrderId(orderId);
		if(!tabTixianLog.getStatus1().equals("5")){
			try {
				PrintWriter out1;
				out1 = response.getWriter();
				out1.print("ok");
				out1.flush();
				out1.close();
				out1 = null;
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

		if(code == 1 && "success".equals(msg)){
			//提现成功
			tabTixianLog.setStatus1("6");
			tabTixianLogService.save(tabTixianLog);
		}else{
			//提现失败
			tabTixianLog.setStatus1("7");
			tabTixianLogService.save(tabTixianLog);
		}

		PrintWriter out;
		try {
			out = response.getWriter();
			out.print("ok");
			out.flush();
			out.close();
			out = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "cinetPayPaidNotify")
	public void cinetPayPaidNotify(@RequestParam("client_transaction_id") String orderId,@RequestParam("sending_status") Integer status,HttpServletResponse response){
		System.out.println("cinetPayPaidNotify回调参数:orderId："+orderId+",status："+status);
		TabTixianLog tabTixianLog =tabTixianLogService.getByOrderId(orderId);
		if(!tabTixianLog.getStatus1().equals("8")){
			try {
				PrintWriter out1;
				out1 = response.getWriter();
				out1.print("ok");
				out1.flush();
				out1.close();
				out1 = null;
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

		String str = "apikey=1377593702635fa6beb6b106.48602372&password=Hrk196883@1106";
		CloseableHttpClient httpclient3 = HttpClients.createDefault();

		HttpPost httpPost3 = new HttpPost("https://client.cinetpay.com/v1/auth/login");// 创建httpPost
		httpPost3.setHeader("Accept", "application/json");
		httpPost3.setHeader("Content-Type", "application/x-www-form-urlencoded");
		String charSet = "UTF-8";
		StringEntity entity3 = new StringEntity(str, charSet);
		httpPost3.setEntity(entity3);
		String token = null;
		try {
			CloseableHttpResponse response3 = null;
			response3 = httpclient3.execute(httpPost3);
			HttpEntity responseEntity3 = response3.getEntity();
			String jsonString3 = EntityUtils.toString(responseEntity3);
			System.out.println("cinetPayPaid代付获取token："+jsonString3);
			com.alibaba.fastjson.JSONObject jsonObject3 = com.alibaba.fastjson.JSONObject.parseObject(jsonString3);
			token = jsonObject3.getJSONObject("data").getString("token");

			HttpGet httpGet = new HttpGet("https://client.cinetpay.com/v1/transfer/check/money?token="+token+"&client_transaction_id="+orderId);
			response3 = httpclient3.execute(httpGet);
			HttpEntity responseEntity2 = response3.getEntity();
			String jsonString2 = EntityUtils.toString(responseEntity2);
			System.out.println("cinetPayPaid代付获取token："+jsonString2);

			JSONObject object = JSON.parseObject(jsonString2);
			if(object.getInteger("code") == 0 && object.getString("message").equals("OPERATION_SUCCES") ){
				//提现成功
				if(object.getJSONArray("data").getJSONObject(0).getString("treatment_status").equals("VAL") ){
					tabTixianLog.setStatus1("9");
					tabTixianLogService.save(tabTixianLog);
				}else if(object.getJSONArray("data").getJSONObject(0).getString("treatment_status").equals("NEW") || object.getJSONArray("data").getJSONObject(0).getString("treatment_status").equals("REC") ){
					//tabTixianLog.setStatus1("8");
					//tabTixianLogService.save(tabTixianLog);
				}else{
					tabTixianLog.setStatus1("10");
					tabTixianLogService.save(tabTixianLog);
				}
			}

			PrintWriter out;
			try {
				out = response.getWriter();
				out.print("ok");
				out.flush();
				out.close();
				out = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch (Exception e){

		}

	}

	@RequestMapping(value = "queryTixianOrderStatus")
	public void queryTixianOrderStatus(){

		try {
			TabTixianLog data = new TabTixianLog();
			data.setStatus1("8");
			List<TabTixianLog> list = tabTixianLogService.findList(data);
			if(list.size() == 0){
				return;
			}
			String str = "apikey=1377593702635fa6beb6b106.48602372&password=Hrk196883@1106";
			CloseableHttpClient httpclient3 = HttpClients.createDefault();
			HttpPost httpPost3 = new HttpPost("https://client.cinetpay.com/v1/auth/login");// 创建httpPost
			httpPost3.setHeader("Accept", "application/json");
			httpPost3.setHeader("Content-Type", "application/x-www-form-urlencoded");
			String charSet = "UTF-8";
			StringEntity entity3 = new StringEntity(str, charSet);
			httpPost3.setEntity(entity3);
			String token = null;
			CloseableHttpResponse response3 = null;
			response3 = httpclient3.execute(httpPost3);
			HttpEntity responseEntity3 = response3.getEntity();
			String jsonString3 = EntityUtils.toString(responseEntity3);
			System.out.println("cinetPayPaid代付获取token："+jsonString3);
			com.alibaba.fastjson.JSONObject jsonObject3 = com.alibaba.fastjson.JSONObject.parseObject(jsonString3);
			token = jsonObject3.getJSONObject("data").getString("token");

			for(TabTixianLog tabTixianLog : list){
				HttpGet httpGet = new HttpGet("https://client.cinetpay.com/v1/transfer/check/money?token="+token+"&client_transaction_id="+tabTixianLog.getOrderid());
				response3 = httpclient3.execute(httpGet);
				HttpEntity responseEntity2 = response3.getEntity();
				String jsonString2 = EntityUtils.toString(responseEntity2);
				System.out.println("cinetPayPaid代付获取token："+jsonString2);

				JSONObject object = JSON.parseObject(jsonString2);
				if(object.getInteger("code") == 0 && object.getString("message").equals("OPERATION_SUCCES") ){
					//提现成功

					if(object.getJSONArray("data").getJSONObject(0).getString("treatment_status").equals("VAL") ){
						tabTixianLog.setStatus1("9");
						tabTixianLogService.save(tabTixianLog);
					}else if(object.getJSONArray("data").getJSONObject(0).getString("treatment_status").equals("NEW") || object.getJSONArray("data").getJSONObject(0).getString("treatment_status").equals("REC") ){
						//tabTixianLog.setStatus1("8");
						//tabTixianLogService.save(tabTixianLog);
					}else{
						tabTixianLog.setStatus1("10");
						tabTixianLogService.save(tabTixianLog);
					}
				}
			}

		}catch (Exception e){

		}
		System.out.println("queryTixianOrderStatus完成");

	}

	@RequestMapping(value = {"rewardDetails", ""})
	public Map<String, Object> rewardDetails(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = getUserDataByToken(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			String userId = list.get(0).getRowid();
			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);
			List<TabJiesuanLog> tabJiesuanLogs = tabJiesuanLogService.rewardDetails(parame);

			for(TabJiesuanLog bean : tabJiesuanLogs){
				//String str = new BigDecimal(bean.getAmont()).setScale(3,BigDecimal.ROUND_UP).toString();
				//bean.setAmont(Double.parseDouble(str.substring(0,str.length()-1)));
				bean.setType("");
			}
			map.put("data", tabJiesuanLogs);
		}
		return map;
	}

	private static final String apikey = "1377593702635fa6beb6b106.48602372";
	private static final String site_id = "584424";
	private static final String currency = "XOF";
	private static final String description = "cinetPay在线支付";
	private static final String notify_url = "https://e-creatoerzw.com/api/cinetPayNotify";
	private static final String return_url = "https://e-creatoerzw.com/#/pages/views/tab/personnels";
	private static final String channels = "MOBILE_MONEY";
	private static final String payUrl = "https://api-checkout.cinetpay.com/v2/payment";
	private static final String checkUrl = "https://api-checkout.cinetpay.com/v2/payment/check";
	private static org.json.JSONObject payData ;
	private static org.json.JSONObject checkData;
	private static final String UNKNOWN = "unknown";
	static {
		org.json.JSONObject jsonObject = new org.json.JSONObject();
		jsonObject.put("apikey",apikey);
		jsonObject.put("site_id",site_id);
		jsonObject.put("currency",currency);
		jsonObject.put("description",description);
		jsonObject.put("notify_url",notify_url);
		jsonObject.put("return_url",return_url);
		jsonObject.put("channels",channels);
		payData = jsonObject;

		org.json.JSONObject jsonObject1 = new org.json.JSONObject();
		jsonObject1.put("apikey",apikey);
		jsonObject1.put("site_id",site_id);
		checkData = jsonObject1;
	}

	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		String comma = ",";
		String localhost = "127.0.0.1";
		if (ip.contains(comma)) {
			ip = ip.split(",")[0];
		}
		if (localhost.equals(ip)) {
			// 获取本机真正的ip地址
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
			}
		}
		return ip;
	}

	@RequestMapping(value = "crawler")
	@ResponseBody
	public void crawler(@RequestBody JSONObject object ) throws Exception{
		JSONArray array = object.getJSONObject("data").getJSONArray("sections").getJSONObject(0).getJSONObject("data").getJSONArray("item");
		Random r = new Random();
		for(int i=0;i<array.size();i++){
			JSONObject jsonObject = array.getJSONObject(i);
			String name = jsonObject.getString("name");
			//JSONArray images = jsonObject.getJSONArray("images");
			String image = jsonObject.getString("image");
			//for(int k=0;k<images.size();k++){
				String imgname ="https://cf.shopee.co.id/file/"+ image+"_tn";
				//String src = FileUtil.downloadFile(imgname,"/www/wwwroot/file/file.e-creatoerzw.com/wgdnewPic/");
				TabProductData tabProductData = new TabProductData();
				//tabProductData.setImgsrc(src);
				tabProductData.setRowid(UUID.randomUUID().toString());
				//Double d = Math.random()*100;
				int d = new Random().nextInt(1000000)+1550;
				tabProductData.setBuymoney(new BigDecimal(d).setScale(4,BigDecimal.ROUND_DOWN).doubleValue());
				tabProductData.setPrice(new BigDecimal(d).setScale(4,BigDecimal.ROUND_DOWN).doubleValue());
				tabProductData.setName(name);
				Integer leval = r.nextInt(3)+5;
				tabProductData.setLeval(leval.toString());
				//tabProductDataService.insert(tabProductData);
			//}
		}
	}

	@Autowired
	private Translate translate;

	@RequestMapping(value = "translate")
	@ResponseBody
	public void translate(Integer count){
		TabProductData tabProductData = new TabProductData();
		tabProductData.setLeval("1");
		tabProductData.setOrderBy("price asc");
		List<TabProductData> list = tabProductDataService.findList(tabProductData);
		for(int i = 106;i<list.size();i++){
			TabProductData bean = list.get(i);
			try {
				String str = translate.test(bean.getName());
				if(StringUtils.isBlank(str)){
					return;
				}
				System.out.println(bean.getRowid()+"，翻译：" + str);
				bean.setName(str);
				tabProductDataService.save(bean);
				Thread.sleep(4000);
			}catch (Exception e){
				e.printStackTrace();
				System.out.println("第"+i+"个出错");
				return;
			}

		}
		System.out.println("完成");
	}

	/*@RequestMapping(value = "translate")
	@ResponseBody
	public void translate(){
		TabProductData tabProductData = new TabProductData();
		tabProductData.setLeval("0");
		List<TabProductData> tabProductData1 = tabProductDataService.findList(tabProductData);
		BigDecimal d = new BigDecimal(0.4D);
		BigDecimal d2 = new BigDecimal(8D);
		BigDecimal d3 = new BigDecimal(40D);
		BigDecimal d4 = new BigDecimal(160D);
		for(int i =0 ;i<3252;i++){
			System.out.println(i);
			TabProductData tabProductData2 = tabProductData1.get(i);
			if(i<900){
				d = d.add(new BigDecimal(0.01));
				tabProductData2.setLeval("1");
				tabProductData2.setBuymoney(d.setScale(2,BigDecimal.ROUND_UP).doubleValue());
				tabProductData2.setPrice(tabProductData2.getBuymoney());
			}
			if(i<1700 && i>=900){
				d2 = d2.add(new BigDecimal(0.05));
				tabProductData2.setLeval("2");
				tabProductData2.setBuymoney(d2.setScale(2,BigDecimal.ROUND_UP).doubleValue());
				tabProductData2.setPrice(tabProductData2.getBuymoney());
			}

			if(i<2500 && i>=1700){
				d3 = d3.add(new BigDecimal(0.2));
				tabProductData2.setLeval("3");
				tabProductData2.setBuymoney(d3.setScale(2,BigDecimal.ROUND_UP).doubleValue());
				tabProductData2.setPrice(tabProductData2.getBuymoney());
			}
			if(i>=2500 ){
				d4 = d4.add(new BigDecimal(0.42));
				tabProductData2.setLeval("4");
				tabProductData2.setBuymoney(d4.setScale(2,BigDecimal.ROUND_UP).doubleValue());
				tabProductData2.setPrice(tabProductData2.getBuymoney());
			}
			tabProductDataService.save(tabProductData2);
		}
	}*/

	public void dd(TabJiesuanLog tabJiesuanLog,Jedis jedis){
		//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
		TabAutoConfig tabAutoConfig = getAutoConfig();
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

	@Autowired
	private TaskJob taskJob;

	@RequestMapping(value = "jisuan31")
	@ResponseBody
	public void jisuan31(){
		taskJob.jisuan31();
	}

	private void delValuesByKeys(Jedis jedis){
		String key = "IsBuyProDuct:*";
		Set<String> keys = jedis.keys(key);
		keys.stream().forEach(s -> {
			jedis.del(s);
		});
	}
	@RequestMapping(value = "updateTongDaoStatus1")
	@ResponseBody
	public HashedMap updateTongDaoStatus1(String number,Integer status1){
		List<TabAutoConfig> configList = tabAutoConfigService.findList(new TabAutoConfig());
		if(configList.size() == 0 || "0".equals(configList.get(0).getHuanka())){
			return null;
		}
		HashedMap map = new HashedMap();
		if(StringUtils.isBlank(number)){
			map.put("msg","number is null");
			map.put("code",0);
			return map;
		}

		try {
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println("更换卡号通知时间："+format1.format(getjndDate(new Date()))+"，卡号："+number);
		}catch (Exception e){

		}


		TabTongdao tabTongdao = new TabTongdao();
		tabTongdao.setNumber(number);
		List<TabTongdao> list = tabTongdaoService.findList(tabTongdao);
		if(list.size() == 0){
			map.put("msg","tongdao is null");
			map.put("code",0);
			return map;
		}
		tabTongdao = list.get(0);
		if(tabTongdao.getStatus1() == 1){
			String shijianlunhun = tabTongdao.getShijianlunhuan();
			tabTongdao.setStatus1(2);
			tabTongdao.setShijianlunhuan("0");
			tabTongdao.setOffshelftime(getjndDate(new Date()));
			tabTongdaoService.save(tabTongdao);
			List<TabTongdao> tongdaoList = new ArrayList<>();
			if("1".equals(shijianlunhun)){
				Jedis jedis =  new Jedis("41.72.149.115", 6379);
				jedis.auth("hask071bend");
				jedis.del("Tongdaos");
				jedis.del("Tongdao");
				jedis.quit();
			}else{
				if("大额".equals(tabTongdao.getEdu())){
					tongdaoList = tabTongdaoService.getEduList();
					if(tongdaoList.size() == 0){
						map.put("msg","SUCCESS");
						map.put("code",1);
						return map;
					}
				}else{
					tongdaoList = tabTongdaoService.getList();
				}
			}

			if(tongdaoList.size() >0){
				TabTongdao tabTongdao1 = tongdaoList.get(0);
				tabTongdao1.setStatus1(1);
				tabTongdao1.setShelftime(getjndDate(new Date()));
				tabTongdaoService.save(tabTongdao1);
			}
		}

		map.put("msg","SUCCESS");
		map.put("code",1);
		return map;
	}

	@RequestMapping(value = "test1133")
	@ResponseBody
	public void test1133(){
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		List<TabOrders> list = tabOrdersService.getListByStatus1("2023-06-01 00:00:00");
		for(int i=0;i<list.size();i++){
			TabOrders tabOrders1 = list.get(i);
			JSONObject buyJsonObject1 = new JSONObject();
			buyJsonObject1.put("vip",tabOrders1.getVip());
			buyJsonObject1.put("userid",tabOrders1.getUserid());
			buyJsonObject1.put("time",getjndDate(new Date()).getTime());
			buyJsonObject1.put("istiyan",tabOrders1.getIstiyan());
			jedis.set(isBuyProDuct+tabOrders1.getUserid(),buyJsonObject1.toJSONString());
		}
		jedis.quit();
	}

	@ResponseBody
	@RequestMapping("/socketTranslate")
	public Map<String,Object> socketTranslate(@RequestBody JSONObject object){
		Integer type = object.getInteger("type");
		String  msg = object.getString("msg");
		String message = "";
		try {
			message = fanyi(type,msg);
			if(StringUtils.isBlank(message)){
				message = "翻译出错，请重新尝试，或者发送已翻译后的内容";
			}
		}catch (Exception e){
			message = "翻译出错，请重新尝试，或者发送已翻译后的内容";
		}

		Map<String,Object> map = new HashMap<>();
		map.put("code",1);
		map.put("msg",message);
		return map;
	}

	private String fanyi(Integer type,String msg) throws Exception{
		String url = null;
		//msg = URLEncoder.encode(msg, "GBK");
		Map<String,String> parame1 = new HashMap<>();
		parame1.put("client","gtx");
		parame1.put("dt","t");
		parame1.put("q",msg);
		if(type == 0){
			parame1.put("sl","en");
			parame1.put("tl","zh-CN");
			//英文翻译成中文
			url = "https://translate.google.com/translate_a/single?client=gtx&sl=en&tl=zh-CN&dt=t&q="+ msg;
		}else{
			parame1.put("sl","zh-CN");
			parame1.put("tl","en");
			//中文翻译成英文
			url = "https://translate.google.com/translate_a/single?client=gtx&sl=zh-CN&tl=en&dt=t&q="+ msg;
		}
		String str = GoldpaysUtil.hanYuanUtils("https://translate.google.com/translate_a/single", "", parame1);
		//String str = HttpClientUtils.get(url);

		//String newStr = URLDecoder.decode(str,"GBK");
		if(StringUtils.isNotBlank(str)){
			JSONArray jsonArray = JSONArray.parseArray(str);
			JSONArray array = jsonArray.getJSONArray(0);
			String message = "";
			for(int i=0;i<array.size();i++){
				message =message + array.getJSONArray(i).getString(0);
			}
			if(type == 0){
				System.out.println("英文翻译成中文msg："+msg+"  ,message："+message);
			}else{
				System.out.println("中文翻译成英文msg："+msg+"  ,message："+message);
			}
			return message;
		}
		return null;
	}

	@RequestMapping(value = "ztReset")
	@ResponseBody
	public void ztReset(){
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		Long lo = new Date().getTime()-1000*60*5;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = format1.format(getjndDate(new Date(lo)));
		List<TabJiesuanLog> list = tabJiesuanLogService.ztReset(date);
		for(int i =0 ;i<list.size();i++){
			TabJiesuanLog bean = list.get(i);
			jedis.lpush(JIESUAN_KEY,JSON.toJSONString(bean));
		}
		jedis.quit();
	}



	@RequestMapping(value = "readExcel")
	@ResponseBody
	public void ReadExcel(){
		try {
			String fileName = "D:\\gouzi/1.xls"; // Excel文件所在路径
			File file = new File(fileName); // 创建文件对象
			Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
			Sheet sheet = wb.getSheet(0);
			for (int i = 1; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容
				Cell tongdaoName = sheet.getCell(0, i);

				Cell tondgaoNumber = sheet.getCell(1, i);

				TabTongdao tabTongdao1 = new TabTongdao();
				tabTongdao1.setNumber("0"+tondgaoNumber.getContents());
				List<TabTongdao> list = tabTongdaoService.findList(tabTongdao1);
				if(list.size() > 0){
					System.out.println(tondgaoNumber.getContents()+"号码已存在");
					continue;
				}

				Cell idss = sheet.getCell(2, i);

				Cell kahao = sheet.getCell(5, i);

				Cell order = sheet.getCell(6, i);
				TabTongdao tabTongdao = new TabTongdao();
				tabTongdao.setTongdaoName(tongdaoName.getContents().toUpperCase());
				tabTongdao.setNumber("0"+tondgaoNumber.getContents());
				tabTongdao.setIdss(idss.getContents());
				tabTongdao.setStatus1(2);
				tabTongdao.setKahao(kahao.getContents());
				tabTongdao.setShelftime(null);
				tabTongdao.setOffshelftime(getjndDate(new Date()));
				tabTongdao.setImgsrc("https://file.twcreaotr.com/newPic/111111111.png.png");
				tabTongdao.setDaishou("2");
				tabTongdao.setDaifu("2");
				tabTongdao.setType("Eco Cash");
				tabTongdao.setEdu("小额");
				tabTongdao.setShijianlunhuan("0");
				tabTongdao.setOrderindex(Long.parseLong(order.getContents()));
				tabTongdaoService.save(tabTongdao);
				/*for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);

				}*/
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	@RequestMapping(value = {"Meeting", ""})
	public Map<String, Object> Meeting(TabUserData userData) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		List<String> list = new ArrayList<>();

		try {
			String fileName = "D:\\gouzi/上分模板.xls"; // Excel文件所在路径
			File file = new File(fileName); // 创建文件对象
			Workbook wb = Workbook.getWorkbook(file); // 从文件流中获取Excel工作区对象（WorkBook）
			Sheet sheet = wb.getSheet(0);
			for (int i = 1; i < sheet.getRows(); i++) { // 循环打印Excel表中的内容
				Cell userid = sheet.getCell(0, i);
				System.out.println(userid.getContents());

				TabUserData tabUserData = tabUserDataService.get(userid.getContents());
				if(tabUserData == null){
					System.out.println(userid.getContents()+"用户不存在");
					continue;
				}

				Cell msg = sheet.getCell(1, i);
				System.out.println(msg.getContents());

				Cell amont = sheet.getCell(2, i);
				System.out.println(amont.getContents());

				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setUserid(userid.getContents());
				tabJiesuanLog.setCmd("System add");
				tabJiesuanLog.setType("模板上分");
				tabJiesuanLog.setAmont(Double.parseDouble(amont.getContents()));
				tabJiesuanLog.setMsg(msg.getContents());
				tabJiesuanLog.setRowid(UUID.randomUUID().toString());
				tabJiesuanLog.setCreatetime(getjndDate(new Date()));
				tabJiesuanLog.setCurrentmoney(tabUserData.getCurrentmoney());
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLog.setStatus1(tabJiesuanLog.getRowid());
				tabJiesuanLog.setParentid1(tabUserData.getParentid());
				tabJiesuanLog.setParentid2(tabUserData.getParentid1());
				tabJiesuanLog.setParentid3(tabUserData.getParenti3());
				tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
				tabJiesuanLogService.save(tabJiesuanLog);
				dd(tabJiesuanLog,jedis);
			}
		}catch (Exception e){

		}
		jedis.quit();
		return map;
	}

	@RequestMapping(value = {"testqq", ""})
	public void testqq(){
		TabProductData tabProductData = new TabProductData();
		List<TabProductData> list = tabProductDataService.findList(tabProductData);
		for(int i=0;i<list.size();i++){
			System.out.println(i);
			TabProductData bean = list.get(i);
			bean.setPrice(bean.getPrice()*3800);
			bean.setBuymoney(bean.getBuymoney()*3800);
			tabProductDataService.save(bean);
		}
	}

	@Autowired
	private TabUserCoinwService tabUserCoinwService;

	@Autowired
	private TabUserCoinwOrderService tabUserCoinwOrderService;

	@RequestMapping(value = {"futuresapi", ""})
	public void futuresapi(){
		Map<String,Object> map = new HashMap<>();
		map.put("page",1);
		map.put("pageSize",999);
		map.put("reqcounts",21_1_1);
		Map<String,Object> queryParameter = new HashMap<>();
		queryParameter.put("leaderKind",2);
		map.put("queryParameter",queryParameter);
		String str = GoldpaysUtil.hanYuanUtils1("https://futuresapi.coinw.com/follow/v1/leader/newLeaderList",map);
		JSONObject jsonObject = JSONObject.parseObject(str);
		//查询所有带单人员
		JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("rows");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i =0;i<jsonArray.size();i++){
			try {
				String userid = jsonArray.getJSONObject(i).getString("leaderId");//用户id
				String nickName = jsonArray.getJSONObject(i).getString("nickName");//用户名称
				System.out.println("带单名称："+nickName);
				String followTotalCount = jsonArray.getJSONObject(i).getString("followTotalCount");//累计跟单人数
				String followerCount = jsonArray.getJSONObject(i).getString("followerCount");//当前跟单人数
				String totalFollowerBalance = jsonArray.getJSONObject(i).getString("totalFollowerBalance");//跟单资金
				String settleInTime = jsonArray.getJSONObject(i).getString("settleInTime");//交易天数
				TabUserCoinw tabUserCoinw = new TabUserCoinw();
				tabUserCoinw.setRowid(userid);
				tabUserCoinw = tabUserCoinwService.getUserCoinw(tabUserCoinw);
				if(tabUserCoinw == null){
					tabUserCoinw = new TabUserCoinw();
					tabUserCoinw.setRowid(userid);
					tabUserCoinw.setUsername(nickName);
					tabUserCoinw.setIsNewRecord(true);
				}
				tabUserCoinw.setFollowtotalcount(Integer.parseInt(followTotalCount));
				tabUserCoinw.setFollowercount(Integer.parseInt(followerCount));
				tabUserCoinw.setTotalfollowerbalance(Double.parseDouble(totalFollowerBalance));
				tabUserCoinw.setSettleintime(Integer.parseInt(settleInTime));
				tabUserCoinwService.saveUserCoinw(tabUserCoinw);

				Map<String,Object> map1 = new HashMap<>();
				map1.put("dataType","GetMerchandiserLeaderHistoryFollowList");
				map1.put("queryParameter",userid);
				map1.put("reqcounts","33_1_1");
				map1.put("page",1);
				map1.put("pageNum",1);
				map1.put("pageSize",10);
				String orderstr = GoldpaysUtil.hanYuanUtils1("https://futuresapi.coinw.com/follow/v1/leader/leaderHistoryFollowList",map1);
				JSONObject jsonObject1 = JSONObject.parseObject(orderstr);
				JSONArray orderlist = jsonObject1.getJSONObject("data").getJSONArray("rows");
				for(int k=0; k<orderlist.size();k++){
					String orderBookId = orderlist.getJSONObject(k).getString("id");//订单号
					String followProfit = orderlist.getJSONObject(k).getString("followProfit");//跟单人收益
					String instrument = orderlist.getJSONObject(k).getString("instrument");//币种
					String quantity = orderlist.getJSONObject(k).getString("quantity");//交易员本金
					String leverage = orderlist.getJSONObject(k).getString("leverage");//杠杆
					String profitRate = orderlist.getJSONObject(k).getString("profitRate");//收益率
					String profit = orderlist.getJSONObject(k).getString("profit");//收益
					String followNum = orderlist.getJSONObject(k).getString("followNum");//跟单人数
					String openPrice = orderlist.getJSONObject(k).getString("openPrice");//开仓价格
					String openTime = orderlist.getJSONObject(k).getString("openTime");//开仓时间
					String closePrice = orderlist.getJSONObject(k).getString("closePrice");//平仓价格
					String closeTime = orderlist.getJSONObject(k).getString("closeTime");//平仓时间

					TabUserCoinwOrder tabUserCoinwOrder1 = new TabUserCoinwOrder();
					tabUserCoinwOrder1.setOrderid(orderBookId);
					List<TabUserCoinwOrder> orderList = tabUserCoinwOrderService.findList(tabUserCoinwOrder1);
					if(orderList.size()>0){
						break;
					}
					TabUserCoinwOrder tabUserCoinwOrder = new TabUserCoinwOrder();
					tabUserCoinwOrder.setOrderid(orderBookId);
					tabUserCoinwOrder.setUserid(userid);
					tabUserCoinwOrder.setUsername(nickName);
					tabUserCoinwOrder.setInstrument(instrument);
					tabUserCoinwOrder.setFollowProfit(Double.parseDouble(followProfit));
					tabUserCoinwOrder.setQuantity(Double.parseDouble(quantity));
					tabUserCoinwOrder.setLeverage(Integer.parseInt(leverage));
					tabUserCoinwOrder.setProfitRate(Double.parseDouble(profitRate)*100);
					tabUserCoinwOrder.setProfit(Double.parseDouble(profit));
					tabUserCoinwOrder.setFollowNum(Integer.parseInt(followNum));
					tabUserCoinwOrder.setOpenPrice(Double.parseDouble(openPrice));
					tabUserCoinwOrder.setClosePrice(Double.parseDouble(closePrice));
					tabUserCoinwOrder.setOpenTime(format1.format(new Date(Long.parseLong(openTime))));
					tabUserCoinwOrder.setCloseTime(format1.format(new Date(Long.parseLong(closeTime))));
					tabUserCoinwOrder.setOpenTimenumber(Long.parseLong(openTime));
					tabUserCoinwOrder.setCloseTimenumber(Long.parseLong(closeTime));
					tabUserCoinwOrderService.saveUserCoinwOrder(tabUserCoinwOrder);

				}
			}catch (Exception e){
				continue;
			}
		}
		futuresapi1();
	}

	@RequestMapping(value = {"futuresapi1", ""})
	public void futuresapi1(){
		List<TabUserCoinw> list = tabUserCoinwService.findList(new TabUserCoinw());
		for(int i=0;i<list.size();i++){
			TabUserCoinw tabUserCoinw = list.get(i);
			String rowid = tabUserCoinw.getRowid();
			try {
				Map<String,String> map = tabUserCoinwOrderService.getTongji(rowid);
				JSONObject json = JSONObject.parseObject(JSON.toJSONString(map));
				Double maxprofitRate = json.getDouble("maxprofitRate");
				Double minprofitRate = json.getDouble("minprofitRate");
				Double avgprofitRate = json.getDouble("avgprofitRate");
				Double profit = json.getDouble("profit");
				Double profitRate = json.getDouble("profitRate");
				Double maxquantity = json.getDouble("maxquantity");
				Double minquantity = json.getDouble("minquantity");
				Double avgquantity = json.getDouble("avgquantity");
				Double maxprofit = json.getDouble("maxprofit");
				Double minprofit = json.getDouble("minprofit");
				Double avgprofit = json.getDouble("avgprofit");
				Integer count = json.getInteger("count");
				Double maxtime = json.getDouble("maxtime");
				Double mintime = json.getDouble("mintime");
				Double avgtime = json.getDouble("avgtime");

				tabUserCoinw.setMaxgprofitrate(maxprofitRate);
				tabUserCoinw.setMingprofitrate(minprofitRate);
				tabUserCoinw.setAvgprofitrate(avgprofitRate);
				tabUserCoinw.setProfitamount(profit);
				tabUserCoinw.setTotalprofitrate(profitRate);
				tabUserCoinw.setMaxquantity(maxquantity);
				tabUserCoinw.setMinquantity(minquantity);
				tabUserCoinw.setAvgquantity(avgquantity);
				tabUserCoinw.setMaxgprofit(maxprofit);
				tabUserCoinw.setMingprofit(minprofit);
				tabUserCoinw.setAvgprofit(avgprofit);
				tabUserCoinw.setOrdercount(count);
				tabUserCoinw.setMintime(mintime/(1000*60*60));
				tabUserCoinw.setAvgtime(avgtime/(1000*60*60));
				tabUserCoinw.setMaxtime(maxtime/(1000*60*60));

				Long lo = tabUserCoinwOrderService.getProfitcount(rowid);
				tabUserCoinw.setProfitcount(lo.intValue());
				tabUserCoinw.setLosscount(tabUserCoinw.getOrdercount() - tabUserCoinw.getProfitcount());
				tabUserCoinw.setProfitCountrate(0D);
				if(tabUserCoinw.getOrdercount()>0){
					tabUserCoinw.setProfitCountrate(tabUserCoinw.getProfitcount().doubleValue() / tabUserCoinw.getOrdercount().doubleValue() * 100);
				}
				tabUserCoinwService.save(tabUserCoinw);
			}catch (Exception e){
				System.out.println(rowid+"统计报错");
				e.printStackTrace();
				continue;
			}
		}
		System.out.println("数据处理完成");
	}

	@RequestMapping(value = {"tofuturesapi1", ""})
	public String tofuturesapi1(Model model,TabUserCoinw tabUserCoinw){
		model.addAttribute("tabUserCoinw",tabUserCoinw);
		return "modules/daili/tofuturesapi1";
	}

	@RequestMapping(value = {"tofuturesapiData", ""})
	@ResponseBody
	public Page<TabUserCoinw> tofuturesapiData(TabUserCoinw tabUserCoinw,HttpServletRequest request,HttpServletResponse response){
		tabUserCoinw.setPage(new Page<>(request, response));
		Map<String,Object> map = new HashMap<>();
		if(tabUserCoinw.getProfitamount() != null){
			map.put("profitamount",tabUserCoinw.getProfitamount());
		}
		if(tabUserCoinw.getTotalprofitrate() != null){
			map.put("totalprofitrate",tabUserCoinw.getTotalprofitrate());
		}
		if(tabUserCoinw.getSettleintime() != null){
			map.put("settleintime",tabUserCoinw.getSettleintime());
		}
		if(tabUserCoinw.getAvgprofitrate() != null){
			map.put("avgprofitrate",tabUserCoinw.getAvgprofitrate());
		}
		if(tabUserCoinw.getAvgquantity() != null){
			map.put("avgquantity",tabUserCoinw.getAvgquantity());
		}
		if(tabUserCoinw.getAvgprofitrate() != null){
			map.put("avgprofitrate",tabUserCoinw.getAvgprofitrate());
		}
		if(tabUserCoinw.getAvgprofit() != null){
			map.put("avgprofit",tabUserCoinw.getAvgprofit());
		}
		if(tabUserCoinw.getOrdercount() != null){
			map.put("ordercount",tabUserCoinw.getOrdercount());
		}
		if(tabUserCoinw.getAvgprofit() != null){
			map.put("avgprofit",tabUserCoinw.getAvgprofit());
		}
		if(tabUserCoinw.getProfitCountrate() != null){
			map.put("profitCountrate",tabUserCoinw.getProfitCountrate());
		}

		List<TabUserCoinw> data = tabUserCoinwService.findListData(map);
		Page<TabUserCoinw> page = new Page<>();
		page.setList(data);
		Long count = tabUserCoinwService.findListDataCount(map);
		page.setCount(count);
		page.setPageNo(1);
		page.setPageSize(999);
		return page;
	}
}