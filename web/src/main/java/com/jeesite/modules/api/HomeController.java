package com.jeesite.modules.api;

import java.io.File;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jeesite.modules.tab.entity.*;
import com.jeesite.modules.tab.service.*;
import org.apache.catalina.startup.UserDatabase;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.sql.visitor.functions.If;
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
import redis.clients.jedis.Jedis;

@Controller
@Scope("prototype")
@RequestMapping(value = "/api")
public class HomeController extends BaseController {
	private Long leval1 = 16L;
	private Long leval2 = 8L;
	private Long leval3 = 4L;
	private static String AutoConfig = "AutoConfig";
	private static  String JIESUAN_KEY = "JIESUAN:queue";
	private static String JIESUAN_KEY1 = "JIESUAN:queue:1";
	private static String JIESUAN_KEY2 = "JIESUAN:queue:2";
	private static String JIESUAN_KEY3 = "JIESUAN:queue:3";
	private static String TOKEN = "TOKEN:";

	@Autowired
	private TabActiveImgsService tabActiveImgsService;

	@RequestMapping(value = "login")
	public String login(HttpServletRequest httpServletRequest) {
		return "modules/zjp/login";
	}
	//
	@RequestMapping(value = "footer")
	public String footer(HttpServletRequest httpServletRequest) {
		return "modules/zjp/footer";
	}


	// register.html

	@RequestMapping(value = "register")
	public String register(HttpServletRequest httpServletRequest) {
		return "modules/zjp/register";
	}

	//

	@RequestMapping(value = "verturlcoin")
	public String verturlcoin(HttpServletRequest httpServletRequest, Model model) {
//		String rowid = httpServletRequest.getParameter("rowid");
//
//		TabRechangeLog tabRechangeLog = tabRechangeLogService.get(rowid);
//
//		TabSysSkfs tabSysSkfs = tabSysSkfsService.get("1474012056675893248");
//
//		String ewmnr = "";
//
//		if (tabRechangeLog.getType().equals("USDT")) {
//			ewmnr = tabSysSkfs.getUsdt();
//		} else if (tabRechangeLog.getType().equals("BTC")) {
//			ewmnr = tabSysSkfs.getBtc();
//		} else if (tabRechangeLog.getType().equals("ETH")) {
//			ewmnr = tabSysSkfs.getEth();
//		}
//
//		model.addAttribute("type", tabRechangeLog.getType());
//
//		model.addAttribute("ewmnr", ewmnr);
//
//		// 获取汇率
//
//		TabHuilvConfig tabHuilvConfig = tabHuilvConfigService.get(tabRechangeLog.getType());

		String rowid = httpServletRequest.getParameter("rowid");

		TabRechangeLog tabRechangeLog = tabRechangeLogService.get(rowid);

		model.addAttribute("data", tabRechangeLog);

		TabShoukuanZhanghao tabShoukuanZhanghao = tabShoukuanZhanghaoService.get("1503977959912316928");

		if (tabRechangeLog.getPaytype().equals("ERC-USDT")) {
			model.addAttribute("msg",
					"Hint: This address is for ETHEREUM only, please do not use other public network assets at this address");

			model.addAttribute("imgsrc", tabShoukuanZhanghao.getImg2());
			model.addAttribute("codes", "" + tabShoukuanZhanghao.getErcaddress());

		} else {
			model.addAttribute("msg",
					"Hint: This address is for TRON only, please do not use other public network assets at this address");
			model.addAttribute("imgsrc", tabShoukuanZhanghao.getImg1());
			model.addAttribute("codes", tabShoukuanZhanghao.getTrcaddress());

		}

		return "modules/zjp/verturlcoin";
	}

	@RequestMapping(value = { "/changePzData", "" })
	@ResponseBody
	public Map<String, Object> changePzData(HttpServletRequest httpServletRequest) {
		String rowid = httpServletRequest.getParameter("rowid");
		String pz = httpServletRequest.getParameter("pz");

		TabRechangeLog tabRechangeLog = tabRechangeLogService.get(rowid);
		tabRechangeLog.setStatus1("1");
		tabRechangeLog
				.setOrderid(DateUtils.formatDate(getjndDate(new Date()), "yyyyMMddHHmmss") + getCode() + getCode());

		tabRechangeLog.setPingzheng(pz);
		System.out.println("查看充值数据changePzData："+ JSONObject.toJSONString(tabRechangeLog));
		tabRechangeLogService.save(tabRechangeLog);

		return map;

	}
	// robotorder

	@Autowired
	private TabShoukuanZhanghaoService tabShoukuanZhanghaoService;

	@RequestMapping(value = "robotorder")
	public String robotorder(HttpServletRequest httpServletRequest) {
		return "modules/zjp/robotorder";
	}

//oederhistory

	@RequestMapping(value = "oederhistory")
	public String oederhistory(HttpServletRequest httpServletRequest) {
		return "modules/zjp/oederhistory";
	}

	//
	@RequestMapping(value = "address")
	public String address(HttpServletRequest httpServletRequest) {
		return "modules/zjp/address";
	}

	// profithistory
	@RequestMapping(value = "profithistory")
	public String profithistory(HttpServletRequest httpServletRequest) {
		return "modules/zjp/profithistory";
	}

	@RequestMapping(value = "calculator")
	public String calculator(HttpServletRequest httpServletRequest) {
		return "modules/zjp/calculator";
	}

	// knowledge
	@RequestMapping(value = "knowledge")
	public String knowledge(HttpServletRequest httpServletRequest) {
		return "modules/zjp/knowledge";
	}

	//
	@RequestMapping(value = "video")
	public String video(HttpServletRequest httpServletRequest) {
		return "modules/zjp/video";
	}

	// helpcenter
	@RequestMapping(value = "helpcenter")
	public String helpcenter(HttpServletRequest httpServletRequest) {
		return "modules/zjp/helpcenter";
	}

	// about
	@RequestMapping(value = "about")
	public String about(HttpServletRequest httpServletRequest) {
		return "modules/zjp/about";
	}

	// myteam
	@RequestMapping(value = "myteam")
	public String myteam(HttpServletRequest httpServletRequest) {
		return "modules/zjp/myteam";
	}

	@RequestMapping(value = "recharge")
	public String recharge(HttpServletRequest httpServletRequest) {
		return "modules/zjp/recharge";
	}

	//

	@RequestMapping(value = "changepass")
	public String changepass(HttpServletRequest httpServletRequest) {
		return "modules/zjp/changepass";
	}

	//
	@RequestMapping(value = "changeapypass")
	public String changeapypass(HttpServletRequest httpServletRequest,Model model) {
		String rr=  httpServletRequest.getParameter("rr");

		if(!StringUtils.isBlank(rr)) {
			if(rr.equals("1")) {
				model.addAttribute("title", "Mot de passe de retrait");
				return "modules/zjp/changeapypass";

			}else {
				model.addAttribute("title", "Centre  de sécurité");

			}
		}

		return "modules/zjp/changeapypass2";
	}

	// feedback
	@RequestMapping(value = "feedback")
	public String feedback(HttpServletRequest httpServletRequest) {
		return "modules/zjp/feedback";
	}

	//
	@RequestMapping(value = "setting")
	public String setting(HttpServletRequest httpServletRequest) {
		return "modules/zjp/setting";
	}

	@RequestMapping(value = "userdetial")
	public String userdetial(HttpServletRequest httpServletRequest) {
		return "modules/zjp/userdetial";
	}
	// userdetial

	@RequestMapping(value = "aboutus")
	public String aboutus(HttpServletRequest httpServletRequest) {
		return "modules/zjp/aboutus";
	}

	//
	@RequestMapping(value = "message")
	public String message(HttpServletRequest httpServletRequest) {
		return "modules/zjp/message";
	}

	// orderscenter
	@RequestMapping(value = "orderscenter")
	public String orderscenter(HttpServletRequest httpServletRequest, Model model) {
		String vip = httpServletRequest.getParameter("vip");

		TabVipConfig tabVipConfig = new TabVipConfig();
		tabVipConfig.setVip(vip);
		List<TabVipConfig> tabVipConfigs = tabVipConfigService.findList(tabVipConfig);

		if (tabVipConfigs.size() > 0) {
			model.addAttribute("tabVipConfig", tabVipConfigs.get(0));

		}

		model.addAttribute("vip", vip);

		return "modules/zjp/orderscenter";
	}

	//
	@RequestMapping(value = "robotlist")
	public String robotlist(HttpServletRequest httpServletRequest) {
		return "modules/zjp/robotlist";
	}

	// zhuanpan
	@RequestMapping(value = "zhuanpan")
	public String zhuanpan(HttpServletRequest httpServletRequest) {
		return "modules/zjp/zhuanpan";
	}

	// zhuanpanhistory
	@RequestMapping(value = "zhuanpanhistory")
	public String zhuanpanhistory(HttpServletRequest httpServletRequest) {
		return "modules/zjp/zhuanpanhistory";
	}
	// rule

	@RequestMapping(value = "rule")
	public String rule(HttpServletRequest httpServletRequest) {
		return "modules/zjp/rule";
	}

	// buy
	@RequestMapping(value = "buy")
	public String buy(HttpServletRequest httpServletRequest, Model model) {
		String day = httpServletRequest.getParameter("day");
		String lv = httpServletRequest.getParameter("lv");
		model.addAttribute("day", day);

		model.addAttribute("lv", lv);

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_MONTH, Integer.valueOf(day));

		Date d = calendar.getTime();

		String t = DateUtils.formatDate(d, "yyyy-MM-dd");
		model.addAttribute("t", t);

		return "modules/zjp/buy";
	}

	// transmoney
	@RequestMapping(value = "transmoney")
	public String transmoney(HttpServletRequest httpServletRequest) {
		return "modules/zjp/transmoney";
	}

	@RequestMapping(value = "bindcard")
	public String bindcard(HttpServletRequest httpServletRequest) {
		return "modules/zjp/bindcard";
	}

	// bindcoin

	@RequestMapping(value = "banklist")
	public String banklist(HttpServletRequest httpServletRequest, Model model) {

		String type = httpServletRequest.getParameter("type");

		model.addAttribute("type", type);

		return "modules/zjp/banklist";
	}

	@RequestMapping(value = "invite")
	public String invite(HttpServletRequest httpServletRequest) {
		return "modules/zjp/invite";
	}

	// safemanage

	@RequestMapping(value = "safemanage")
	public String safemanage(HttpServletRequest httpServletRequest) {
		return "modules/zjp/safemanage";
	}
	// bindcoin

	@RequestMapping(value = "bindcoin")
	public String bindcoin(HttpServletRequest httpServletRequest) {
		return "modules/zjp/bindcoin";
	}

	//
	@RequestMapping(value = "accountmng")
	public String accountmng(HttpServletRequest httpServletRequest) {
		return "modules/zjp/accountmng";
	}

	//
	@RequestMapping(value = "active")
	public String active(HttpServletRequest httpServletRequest) {
		return "modules/zjp/active";
	}

	// mine

	@RequestMapping(value = "mine")
	public String mine(HttpServletRequest httpServletRequest) {
		return "modules/zjp/mine";
	}

	//
	@RequestMapping(value = "team")
	public String team(HttpServletRequest httpServletRequest) {
		return "modules/zjp/team";
	}

	//
	@RequestMapping(value = "withdraw")
	public String withdraw(HttpServletRequest httpServletRequest) {
		return "modules/zjp/withdraw";
	}

	// .html
	@RequestMapping(value = "withdrawhistory")
	public String withdrawhistory(HttpServletRequest httpServletRequest) {
		return "modules/zjp/withdrawhistory";
	}

	// mailaccountbind

	@RequestMapping(value = "mailaccountbind")
	public String mailaccountbind(HttpServletRequest httpServletRequest, Model model) {

		String type = httpServletRequest.getParameter("type");

		model.addAttribute("type", type);

		String rowid = httpServletRequest.getParameter("rowid");

		if (StringUtils.isBlank(rowid)) {
			rowid = "";
			model.addAttribute("data", new TabUserBank());

		} else {
			TabUserBank tabUserBank = tabUserBankService.get(rowid);
			model.addAttribute("type", tabUserBank.getTransitnumber());
			model.addAttribute("data", tabUserBank);

		}
		model.addAttribute("rowid", rowid);

		return "modules/zjp/mailaccountbind";
	}

	// rechargehistory

	@RequestMapping(value = "rechargehistory")
	public String rechargehistory(HttpServletRequest httpServletRequest) {
		return "modules/zjp/rechargehistory";
	}

	@RequestMapping(value = "listDataTabRechangeLog")
	@ResponseBody
	public Map<String, Object> listDataTabRechangeLog(TabRechangeLog tabRechangeLog, HttpServletRequest request,
													  HttpServletResponse response) {

		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			// map.put("vip", list.get(0).getVip()) ;

			tabRechangeLog.setUserid(list.get(0).getRowid());

			List<TabRechangeLog> tabRechangeLogs = tabRechangeLogService.findList(tabRechangeLog);

			map.put("data", tabRechangeLogs);

		}

		return map;
	}

	@RequestMapping(value = "listDataWithDraw")
	@ResponseBody
	public Map<String, Object> listDataWithDraw(TabTixianLog tabRechangeLog, HttpServletRequest request,
												HttpServletResponse response) {

		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			// map.put("vip", list.get(0).getVip()) ;

			tabRechangeLog.setUserid(list.get(0).getRowid());

			List<TabTixianLog> tabRechangeLogs = tabTixianLogService.findList(tabRechangeLog);

			map.put("data", tabRechangeLogs);

		}

		return map;
	}

	//

	@RequestMapping(value = "rechangeData")
	@ResponseBody
	public Map<String, Object> rechangeData(TabRechangeLog tabRechangeLog, HttpServletRequest request,
											HttpServletResponse response) {

		String amont = request.getParameter("m");

		List<TabHuilvConfig> tabHuilvConfigs = tabHuilvConfigService.findList(new TabHuilvConfig());

		TabAutoConfig tabAutoConfig = new TabAutoConfig();
		tabAutoConfig.setIsqidong("1");

		Double mj = 0d;

		Double fb = 0D;

		List<TabAutoConfig> tabAutoConfigs = tabAutoConfigService.findList(tabAutoConfig);
		TabHuilvConfig tabHuilvConfig = tabHuilvConfigs.get(0);

		if (tabAutoConfigs.size() > 0) {

			tabAutoConfig = tabAutoConfigs.get(0);

			String token = request.getParameter("token");
			TabUserData tabUserData = new TabUserData();
			tabUserData.setAccesstoken(token);
			List<TabUserData> list = tabUserDataService.findList(tabUserData);

			if (list == null || list.size() == 0) {
				map.put("code", 1);
			} else {
				map.put("code", 0);

				tabUserData = list.get(0);

				tabRechangeLog = new TabRechangeLog();

				String payType = request.getParameter("zfPay");
				String type = request.getParameter("td");
				tabRechangeLog.setPaytype(payType);
				tabRechangeLog.setType(type);

				Double hl = Double.valueOf(tabHuilvConfig.getCad());

				if (payType.equals("CDF")) {

					// 发布 除 汇率

					mj = Double.valueOf(amont) * 1.0 / hl;

					fb = Double.valueOf(amont);

				} else {
					mj = Double.valueOf(amont);
					fb = Double.valueOf(amont) * hl;

				}

				if (mj < tabAutoConfig.getCzzdje()) {

					map.put("code", "1");
					map.put("msg", "Le montant minimale est de " + tabAutoConfig.getCzzdje() + " $");

					return map;
				}

				tabRechangeLog.setAmont(fb);
				tabRechangeLog.setBeizhu("支付成功rechangeData2");
				tabRechangeLog.setUserid(tabUserData.getRowid());
				tabRechangeLog.setStatus1("1");
				tabRechangeLog.setCreatetime(getjndDate(new Date()));
				tabRechangeLog.setIsNewRecord(true);
				tabRechangeLog.setOrderid(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + getCode());

				map.put("code", "0");

				tabRechangeLog.setHzhb(mj);

				tabRechangeLog.setParentid1(tabUserData.getParentid());
				tabRechangeLog.setParentid2(tabUserData.getParentid1());
				tabRechangeLog.setParentid3(tabUserData.getParenti3());
				tabRechangeLog.setShangjilink(tabUserData.getShangjilink());
				System.out.println("查看充值数据rechangeData2："+ JSONObject.toJSONString(tabRechangeLog));
				tabRechangeLogService.save(tabRechangeLog);

				map.put("data", tabRechangeLog);

////
				Map<String, String> parame = new HashMap<>();
				String appid = "b7b31605-7172-4acb-957c-15d401fae6d9";
				String appset = "d18c4c73-3565-4aab-9431-1587255dd230";

				parame.put("appid", appid);
				parame.put("orderId", tabRechangeLog.getOrderid());

				parame.put("orderMoney", tabRechangeLog.getHzhb() + "");
//					 parame.put("customMobile", "account");
				//
				parame.put("notifyUrl", "http://41.72.149.115:8081/api/zfCallData");
				//
				parame.put("type", tabRechangeLog.getPaytype());
				parame.put("tongdao", tabRechangeLog.getType());

				//
//
				String tempSign = GoldpaysUtil.getSign2(parame, appset);
				parame.put("sign", tempSign);
				System.out.println(" sign " + tempSign);
				//
				String s = GoldpaysUtil.hanYuanUtils("http://41.72.149.115:8081/metaPay/api/createLouisDaiShou", "",
						parame);

				System.out.println(s);

				map.put("url", s);

				// 提交到

			}
		}

		return map;
	}

	@Autowired
	private TabRechangeLogService tabRechangeLogService;

	@RequestMapping(value = "bannerDetail")
	public String bannerDetail(HttpServletRequest httpServletRequest, Model model) {

		String rowid = httpServletRequest.getParameter("rowid");

		TabSiderData tabSiderData = tabSiderDataService.get(rowid);

		if (tabSiderData == null) {

			tabSiderData = new TabSiderData();

		}
		model.addAttribute("data", tabSiderData);

		return "modules/zjp/bannerDetail";
	}

	//
	@RequestMapping(value = "serverList")
	public String serverList(HttpServletRequest httpServletRequest) {
		return "modules/zjp/serverList";
	}

	@RequestMapping(value = "home")
	public String home(HttpServletRequest httpServletRequest) {
		return "modules/zjp/home";
	}

	//
	@RequestMapping(value = "agency")
	public String agency(HttpServletRequest httpServletRequest) {
		return "modules/zjp/agency";
	}

	@RequestMapping(value = "agency2")
	public String agency2(HttpServletRequest httpServletRequest) {
		return "modules/zjp/agency2";
	}

	//
	@RequestMapping(value = "agreement")
	public String agreement(HttpServletRequest httpServletRequest) {
		return "modules/zjp/agreement";
	}

	@RequestMapping(value = "yins")
	public String yins(HttpServletRequest httpServletRequest) {
		return "modules/zjp/yins";
	}

	@Autowired
	private TabSiderDataService tabSiderDataService;

	//

	@RequestMapping(value = "regiser")
	public String regiser(HttpServletRequest httpServletRequest, Model model) {
		String code = httpServletRequest.getParameter("code");
		if (StringUtils.isBlank(code)) {
			code = "";
		}
		model.addAttribute("code", code);

		return "modules/zjp/regiser";
	}

	// forgetPassword
	@RequestMapping(value = "forgetPassword")
	public String forgetPassword(HttpServletRequest httpServletRequest) {
		return "modules/zjp/forgetPassword";
	}

	@RequestMapping(value = "listDataTabContaceService")
	@ResponseBody
	public Page<TabContaceService> listDataTabContaceService(TabContaceService tabContaceService,
															 HttpServletRequest request, HttpServletResponse response) {
		tabContaceService.setPage(new Page<>(request, response));
		Page<TabContaceService> page = tabContaceServiceService.findPage(tabContaceService);
		return page;
	}

	Map<String, Object> map = new HashMap<String, Object>();

	@RequestMapping(value = "sendMsgCode")
	@ResponseBody
	public Map<String, Object> sendMsgCode(HttpServletRequest request, HttpServletResponse response) {

		return map;
	}

	//

	@Autowired
	private TabAutoConfigService tabAutoConfigService;

	@RequestMapping(value = "registerPost")
	@ResponseBody
	public Map<String, Object> registerPost(HttpServletRequest request, HttpServletResponse response) {

		String phone = request.getParameter("phone");

		String PassWord = request.getParameter("PassWord");

		TabUserData userData22 = tabUserDataService.get(phone);

		if (userData22 != null) {

			map.put("flag", "false");
			map.put("msg", "Le numéro de compte est le même");

			return map;

		}

		TabAutoConfig tabAutoConfig = new TabAutoConfig();
		tabAutoConfig.setIsqidong("1");
		tabAutoConfig.setIsauto(1);
		List<TabAutoConfig> tabAutoConfigs = tabAutoConfigService.findList(tabAutoConfig);

		String Code = request.getParameter("Code");

		TabUserData tabUserData = new TabUserData();

		tabUserData.setCodes(Code);

		List<TabUserData> tabUserDatas = tabUserDataService.findList(tabUserData);

		String p1= "";
		String ll = "";
		if (tabUserDatas.size() > 0 ) {
			TabUserData userData = tabUserDatas.get(0);

			if (userData.getStatus1() == null || userData.getStatus1().equals("3")  ||  userData.getVip()==0) {

				map.put("flag", "false");
				map.put("msg", "L'utilisateur a enfreint la règle et ne peut pas s'inscrire");

				return map;
			}

			p1 = userData.getParentid();

			ll=  userData.getRowid();

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

			tabUserData.setShangjilink(userData.getShangjilink() + "," + userData.getRowid());

			if (tabAutoConfigs.size() > 0) {
				tabUserData.setVip(1L);
				tabUserData.setIstiyan("1");
				tabUserData.setTyj(tabAutoConfigs.get(0).getTyj());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(getjndDate(new Date()));
				calendar.add(Calendar.DAY_OF_MONTH, tabAutoConfigs.get(0).getTianshu());
				tabUserData.setSumBalance(tabAutoConfigs.get(0).getTyj());
				tabUserData.setValidate(calendar.getTime());

			} else {

				tabUserData.setVip(0L);
				tabUserData.setIstiyan("0");

			}
			tabUserData.setTycs(0L);
			tabUserData.setCurrentmoney(0D);
			tabUserData.setIsNewRecord(true);
			tabUserData.setCodes(getCode() + getCode());
			tabUserData.setSumMember(0D);
			tabUserData.setAccesstoken(UUID.randomUUID().toString());
			tabUserDataService.save(tabUserData);



			// 执行

			TabTaskjJob tasks = new TabTaskjJob();

			tasks.setUserid(tabUserData.getRowid());
			tasks.setShangjis(tabUserData.getShangjilink());
			tasks.setStatus1("1");
			tasks.setCreatetime(new Date());
			tasks.setRowid(UUID.randomUUID().toString());
			tasks.setIsNewRecord(true);

			tabTaskjJobService.save(tasks);

			if (tabAutoConfigs.size() > 0) {

				TabJiesuanLog jiesuanLog = new TabJiesuanLog();
				jiesuanLog.setUserid(tabUserData.getRowid());
				jiesuanLog.setAmont(tabAutoConfigs.get(0).getZhucejl());
				jiesuanLog.setCmd("Registered donations\n" + "\n" + "");
				jiesuanLog.setType("Registered donations");
				jiesuanLog.setMsg("Extra rewards");
				jiesuanLog.setParentid1(tabUserData.getParentid());
				jiesuanLog.setParentid2(tabUserData.getParentid1());
				jiesuanLog.setParentid3(tabUserData.getParenti3());
				jiesuanLog.setShangjilink(tabUserData.getShangjilink());

				jiesuanLog.setCreatetime(getjndDate(new Date()));
				jiesuanLog.setIsNewRecord(true);
				tabJiesuanLogService.save(jiesuanLog);
				Jedis jedis =  new Jedis("41.72.149.115", 6379);
				jedis.auth("hask071bend");
				this.dd(jiesuanLog,jedis);
				jedis.quit();
				//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
				//getUserVip(jiesuanLog.getUserid());
			}

			map.put("flag", "true");
			map.put("data", tabUserData);

		} else {
			map.put("flag", "false");
			map.put("msg", "Code d'invitation n'est pas correcte					");

		}

		return map;
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
			tabUserData.setCreatetime(getjndDate((new Date())));
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
			tabUserData.setParentid(null);
			tabUserData.setUserleval(0L);
			tabUserData.setPassword(password);
			tabUserData.setUsername(nickName);
			tabUserData.setRowid(mobile);
			tabUserData.setTycs(0L);
			tabUserData.setUsertype("1");
			tabUserData.setCurrentmoney(0D);
			tabUserData.setSysuserid(sysuserid);
			tabUserData.setQuanxian(email);
			tabUserData.setStatus1("2");
			tabUserDataService.save(tabUserData);

			map.put("data", tabUserData);

			map.put("code", 1);
			map.put("msg", "essa conta já existe");

		}

		return map;

	}

	public static Date getjndDate(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -7);

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
			record.setCreatetime(new Date());
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
			map.put("msg", "Le compte existe déjà");

		}

		return map;
	}

	@RequestMapping(value = "/loginPost")
	@ResponseBody
	public Map<String, Object> loginPost(HttpServletRequest httpServletRequest) {

		String mobile = httpServletRequest.getParameter("phone").toString();
		String password = httpServletRequest.getParameter("password").toString();

		TabUserData tabUserData = tabUserDataService.get(mobile);

		if (tabUserData == null) {
			map.put("code", 1);
			map.put("msg", "Mot de passe de compte incorrect");
			return map;

		}

		if (password.equals(tabUserData.getPassword())) {

			map.put("data", tabUserData.getRowid());
			tabUserData.setAccesstoken(UUID.randomUUID().toString());
			tabUserData.setLastlogintime(getjndDate(new Date()));

			tabUserDataService.save(tabUserData);

			map.put("accesstoken", tabUserData.getAccesstoken());
			map.put("code", 0);

		} else {

			map.put("code", 1);
			map.put("msg", "compte ou mot de passe est incorrecte");

			return map;

		}

		return map;

	}

	// home

	@RequestMapping(value = "listDataTabSiderData")
	@ResponseBody
	public Page<TabSiderData> listDataTabSiderData(TabSiderData tabSiderData, HttpServletRequest request,
												   HttpServletResponse response) {
		tabSiderData.setPage(new Page<>(request, response));
		Page<TabSiderData> page = tabSiderDataService.findPage(tabSiderData);
		return page;
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
					map.put("msg", "Mauvais code d'invitation");

				}

			} else {

				map.put("code", 1);
				map.put("msg", "Erreur de code de vérification");

			}

		} else {
			map.put("code", 1);
			map.put("msg", "Le compte existe déjà");

		}

		return map;
	}

	@Autowired
	private TabUserDataService tabUserDataService;

	@Autowired
	private TabValidCodeService tabValidCodeService;

	private Date getValiDate() {

		Date date = new Date();

		date.setTime(date.getTime() + 60 * 1000 * 30);

		return date;
	}

	@RequestMapping(value = "listDataTabVipConfig")
	@ResponseBody
	public Map<String, Object> listDataTabVipConfig(TabVipConfig tabVipConfig, HttpServletRequest request,
													HttpServletResponse response) {

		List<TabVipConfig> tabVipConfigs = tabVipConfigService.findList(tabVipConfig);
		map.put("list", tabVipConfigs);

		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			map.put("vip", list.get(0).getVip());

		}

		return map;
	}

	@RequestMapping(value = "getUserDetail")
	@ResponseBody
	public Map<String, Object> getUserDetail(TabRechangeLog tabRechangeLog, HttpServletRequest request,
											 HttpServletResponse response) {

		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			tabUserData = list.get(0);

			tabUserData.setPassword("");
			map.put("data", tabUserData);

			TabShouruLog tabShouruLog = new TabShouruLog();
			tabShouruLog.setUserid(tabUserData.getRowid());

			Double totalM = tabShouruLogService.getSumData(tabShouruLog);
			map.put("totalM", totalM);

		}

		return map;
	}

	@RequestMapping(value = "getUserDetail2")
	@ResponseBody
	public Map<String, Object> getUserDetail2(TabRechangeLog tabRechangeLog, HttpServletRequest request,
											  HttpServletResponse response) {

		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

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
			Long  cc =  tabRechangeLogService.findCount(tabRechangeLog2 );

			map.put("cc", cc);



		}

		return map;
	}

	// 获取 用户银行卡信息

	@RequestMapping(value = "getUserBanks")
	@ResponseBody
	public Map<String, Object> getUserBanks(TabRechangeLog tabRechangeLog, HttpServletRequest request,
											HttpServletResponse response) {

		String token = request.getParameter("token");
		String type = request.getParameter("type");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			tabUserData = list.get(0);

			String rowid = tabUserData.getRowid();

			TabUserBank tabUserBank = new TabUserBank();
			tabUserBank.setTransitnumber(type);
			tabUserBank.setUserid(rowid);
			List<TabUserBank> tabUserBanks = tabUserBankService.findList(tabUserBank);

			map.put("data", tabUserBanks);

		}

		return map;

	}

	//

	@RequestMapping(value = "saveUserDetail")
	@ResponseBody
	public Map<String, Object> saveUserDetail(TabRechangeLog tabRechangeLog, HttpServletRequest request,
											  HttpServletResponse response) {

		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

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

		}

		return map;

	}

	@RequestMapping(value = "saveUserDetail3")
	@ResponseBody
	public Map<String, Object> saveUserDetail3(TabRechangeLog tabRechangeLog, HttpServletRequest request,
											   HttpServletResponse response) {

		String imgsrc = request.getParameter("imgsrc");
		String sex = request.getParameter("sex");

		String birthday = request.getParameter("birthday");

		String nickname = request.getParameter("nickname");

		String nationality = request.getParameter("nationality");

		String phone = request.getParameter("rowid");

		TabUserData tabUserData = tabUserDataService.get(phone);
		tabUserData.setImgsrc(imgsrc);
		tabUserData.setSex(sex);
		tabUserData.setBirthday(birthday);
		tabUserData.setUsername(nickname);
		tabUserData.setNationality(nationality);

		tabUserDataService.save(tabUserData);

		return map;

	}

	// saveUserBank

	@RequestMapping(value = "saveUserBank")
	@ResponseBody
	public Map<String, Object> saveUserBank(TabRechangeLog tabRechangeLog, HttpServletRequest request,
											HttpServletResponse response) {

		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			String userId = list.get(0).getRowid();

			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String accountNumber = request.getParameter("accountNumber");
			String institutionNumber = request.getParameter("institutionNumber");
			String transitNumber = request.getParameter("transitNumber");

			TabUserBank tabUserBank = new TabUserBank();
			tabUserBank.setFirstname(firstName);

			List<TabUserBank> tabUserBanks = tabUserBankService.findList(tabUserBank);

			if (tabUserBanks.size() > 0) {
				map.put("flag", "false");
				map.put("msg", "L'adresse du portefeuille existe déjà.");
			} else {
				tabUserBank.setUserid(userId);
				tabUserBank.setFirstname(firstName);
				tabUserBank.setLastname(lastName);
				tabUserBank.setAccountnumber(accountNumber);
				tabUserBank.setInstitutionnumber(institutionNumber);
				tabUserBank.setTransitnumber(transitNumber);
				tabUserBank.setRowid(UUID.randomUUID().toString());
				tabUserBank.setCreatetime(getjndDate(new Date()));
				tabUserBank.setIsNewRecord(true);
				tabUserBankService.save(tabUserBank);

			}

		}

		return map;
	}

	@RequestMapping(value = "saveUserAddress")
	@ResponseBody
	public Map<String, Object> saveUserAddress(TabUserAddress tabUserAddress, HttpServletRequest request,
											   HttpServletResponse response) {

		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			map.put("flag", "");

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

		}

		return map;
	}

	@RequestMapping(value = "saveFaceBooks")
	@ResponseBody
	public Map<String, Object> saveFaceBooks(TabFacebook tabFacebook, HttpServletRequest request,
											 HttpServletResponse response) {

		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			map.put("flag", "");

			String userId = list.get(0).getRowid();

			tabFacebook.setUserid(userId);

			tabFacebook.setRowid(UUID.randomUUID().toString());
			tabFacebook.setCreatetime(getjndDate(new Date()));
			tabFacebook.setIsNewRecord(true);

			tabFacebookService.save(tabFacebook);

		}

		return map;

	}

	// uploadFile
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

	// changePasswordData

	@RequestMapping(value = "changePasswordData")
	@ResponseBody
	public Map<String, Object> changePasswordData(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			map.put("flag", "");

			TabUserData userData = list.get(0);

			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");

			if (userData.getPassword().equals(oldPassword)) {
				userData.setPassword(newPassword);
				tabUserDataService.save(userData);
				map.put("flag", "true");

			} else {
				map.put("flag", "false");
				map.put("msg", "Le mot de passe d'origine est erroné");

			}

		}
		return map;

	}

	@RequestMapping(value = "changePasswordData2")
	@ResponseBody
	public Map<String, Object> changePasswordData2(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			map.put("flag", "");

			TabUserData userData = list.get(0);

			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");

			if (StringUtils.isBlank(userData.getTxpassword())) {
				userData.setTxpassword(newPassword);
				tabUserDataService.save(userData);

			}else {

				if (userData.getTxpassword().equals(oldPassword)) {
					userData.setTxpassword(newPassword);
					tabUserDataService.save(userData);
					map.put("flag", "true");

				} else {
					map.put("flag", "false");
					map.put("msg", "Le mot de passe d'origine est erroné");

				}
			}
		}
		return map;

	}

	@RequestMapping(value = "tixianPostData")
	@ResponseBody
	public Map<String, Object> tixianPostData(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");

		TabAutoConfig obj1 = new TabAutoConfig();
		obj1.setIsqidong("1");
		List<TabAutoConfig> tabAutoConfigs = tabAutoConfigService.findList(obj1);
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			tabUserData = list.get(0);

			obj1 = tabAutoConfigs.get(0);

			String userId = list.get(0).getRowid();

			//

			// 查看有没待审核的

			Long cc2 = tabTixianLogService.getShenhe(userId);

			if (cc2 > 0) {

				map.put("flag", "false");
				map.put("msg", "retrait d'aujourd'hui est Plus de trois fois\n" + "\n" + "");
				return map;

			}

			cc2 = tabTixianLogService.getTodays(userId, HomeController.getjndDate(new Date()));

			if (cc2 >= 3) {

				map.put("flag", "false");
				map.put("msg", "Plus de trois fois aujourd'hui\n" + "\n" + "");
				return map;

			}

			TabTixianLog obj2 = new TabTixianLog();
			obj2.setStatus1("2");
			obj2.setUserid(userId);

			Long cc = tabTixianLogService.findCount(obj2);

			// 首次提现

			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);

			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData.getCurrentmoney();
			String cardType = request.getParameter("cardType");

			String type = request.getParameter("type");

			String money = request.getParameter("money");

			String txMm = request.getParameter("txMm");

			if(tabUserData.getTxpassword()==null ||  !tabUserData.getTxpassword().equals(txMm)) {


				map.put("flag", "false");
				map.put("msg", "Mot de passe de retrait incorrect" );

				return  map;
			}


			if (m >= Double.valueOf(money)) {
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

					tabTixianLog.setNote("tixianPostData22"+getIp(request));
					tabTixianLog.setCardtype(cardType);
					tabTixianLog.setUserid(userId);
					tabTixianLog.setType(type);
					tabTixianLog.setMoney(Double.valueOf(money));
					tabTixianLog.setRowid(UUID.randomUUID().toString());
					tabTixianLog.setStatus1("1");
					tabTixianLog.setCreatetime(getjndDate(new Date()));
					tabTixianLog.setOrderid(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + getCode() + getCode());

					Double pre = Double.valueOf(hv.split(",")[0]);

					Double c = Double.valueOf(hv.split(",")[1]);

					Double dzje = Double.valueOf(money) - Double.valueOf(money) * pre / 100 - c;
					tabTixianLog.setDzje(dzje);
					tabTixianLog.setIsNewRecord(true);
					tabTixianLog.setParentid1(tabUserData.getParentid());
					tabTixianLog.setParentid2(tabUserData.getParentid1());
					tabTixianLog.setParentid3(tabUserData.getParenti3());
					tabTixianLog.setShangjilink(tabUserData.getShangjilink());

					tabTixianLogService.save(tabTixianLog);

					tabUserData.setVip(0L);
					tabUserDataService.save(tabUserData);

					// 保存结算明细

					TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();

					tabJiesuanLog.setUserid(tabUserData.getRowid());
					tabJiesuanLog.setAmont(Double.valueOf(money) * -1);
					tabJiesuanLog.setRowid(UUID.randomUUID().toString());
					tabJiesuanLog.setCmd("Retrait");
					tabJiesuanLog.setType("提现申请");
					tabJiesuanLog.setMsg("Withdraw");
					tabJiesuanLog.setCreatetime(getjndDate(new Date()));
					tabJiesuanLog.setIsNewRecord(true);
					tabJiesuanLog.setParentid1(hv);
					tabJiesuanLog.setParentid1(tabUserData.getParentid());
					tabJiesuanLog.setParentid2(tabUserData.getParentid1());
					tabJiesuanLog.setParentid3(tabUserData.getParenti3());
					tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());

					tabJiesuanLogService.save(tabJiesuanLog);
					Jedis jedis =  new Jedis("41.72.149.115", 6379);
					jedis.auth("hask071bend");
					this.dd(tabJiesuanLog,jedis);
					//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
					//getuservip(tabJiesuanLog.getUserid());


					jedis.quit();
					map.put("flag", "true");

				} else {

					map.put("flag", "false");
					map.put("msg", " Sélectionnez le portefeuille de retrait" + sctx);

				}

			} else {

				map.put("flag", "false");
				map.put("msg", "Solde est insuffisant");

			}

		}

		return map;

	}

	// getsubData

	@RequestMapping(value = "getTotalSubUsers")
	@ResponseBody
	public Map<String, Object> getTotalSubUsers(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			tabUserData = list.get(0);

			String userId = list.get(0).getRowid();

			// 获取总分销人数

			Map<String, String> parame = new HashMap<>();
			parame.put("userid", userId);

			Long cc = tabUserDataService.findSumData(parame);
			map.put("cc", cc);

			// 获取我的 团队收入

		}

		return map;

	}

	// 获取用户明细

	@RequestMapping(value = "getSubUsers")
	@ResponseBody
	public Map<String, Object> getSubUsers(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");
		String type = request.getParameter("type");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			tabUserData = list.get(0);

			String userId = list.get(0).getRowid();

			TabUserData userData = new TabUserData();

			if (type.equals("1")) {
				userData.setParentid(userId);
				;
			} else if (type.equals("2")) {
				userData.setParentid1(userId);
				;

			} else if (type.equals("3")) {
				userData.setParenti3(userId);
				;
			}

			List<TabUserData> tabUserDatas = tabUserDataService.findList(userData);


			map.put("data", tabUserDatas);

		}

		return map;

	}

	// 保存理财数据

	@RequestMapping(value = "saveLiCaiData")
	@ResponseBody
	public Map<String, Object> saveLiCaiData(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);
			tabUserData = list.get(0);

			String userId = list.get(0).getRowid();

			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);

			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData.getCurrentmoney();
			String money = request.getParameter("money");
			String lv = request.getParameter("lv");
			String day = request.getParameter("day");

			if (m >= Double.valueOf(money)) {
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
				this.dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
				//getuservip(tabJiesuanLog.getUserid());
				jedis.quit();

			} else {

				map.put("flag", "false");
				map.put("msg", "Solde insuffisant");

			}

		}

		return map;

	}

	//

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
	public Map<String, Object> jisuan22(HttpServletRequest request, HttpServletResponse response) {

		String string = "/Users/gaofeng/Downloads/imglist";

		File file = new File(string);

		String[] ss = file.list();
		System.err.println(ss.length);
		for (String string2 : ss) {
			System.out.println(string2);
			String[] sp = string2.split("\\$");
			if (sp.length == 2) {
				String sp2 = sp[1];
				sp2 = sp2.substring(0, sp2.length() - 4).replaceAll(",", "");
				;

				file = new File(string + File.separator + string2);

				String pa = HttpServletRequestUtils.uploadFiles(file);

				TabProductData tabProductData = new TabProductData();

				tabProductData.setName(string2.substring(0, string2.length() - 4).replaceAll(",", ""));
				tabProductData.setImgsrc(pa);
				tabProductData.setRowid(UUID.randomUUID().toString());
				try {
					tabProductData.setBuymoney(Double.valueOf(sp2));
					tabProductData.setPrice(Double.valueOf(sp2));
				} catch (Exception e) {
				}
				tabProductData.setIsNewRecord(true);
				tabProductDataService.save(tabProductData);

			}
		}

		return map;

	}

	public static void main(String[] args) {

		File file  =new File("/root/apache-tomcat-9.0.64/webapps/ROOT/imgs/imgss") ;

		File file2 = new File("/Users/gaofeng/Downloads/imgs/imgss");

		String[] ll = file2.list();
		for (String string : ll) {
			System.out.println(string);
		}
	}

	@RequestMapping(value = "pingjia")
	public String pingjia(HttpServletRequest httpServletRequest, Model model) {

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

	// 获取我能购买的产品

	@RequestMapping(value = "getMyProductData")
	@ResponseBody
	public Map<String, Object> getMyProductData(HttpServletRequest request, HttpServletResponse response) {

		String vip = request.getParameter("vip");

		TabProductData obj = new TabProductData();
		obj.setLeval(vip);
		List<TabProductData> tabProductDatas = tabProductDataService.findList(obj);

		map.put("data", tabProductDatas);

		return map;

	}

	@RequestMapping(value = "getOrderJisuan")
	@ResponseBody
	public Map<String, Object> getOrderJisuan(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			String userId = list.get(0).getRowid();

			TabOrderData tabOrderData = new TabOrderData();
			tabOrderData.setUserid(userId);
			tabOrderData.setCreatetime_gte(DateUtils.getOfDayFirst(getjndDate((new Date()))));
//			tabOrderData.setStatus1("2")   ;

			List<TabOrderData> tabUserDatas = tabOrderDataService.findList(tabOrderData);

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
			map.put("sumPre", totalmoney);

			// 计算余额

			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);

			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = list.get(0).getCurrentmoney();
			map.put("m", m);

			map.put("data", list.get(0));

		}

		return map;

	}

	// 批量生成订单

	@RequestMapping(value = "piLiangOrder")
	@ResponseBody
	public Map<String, Object> piLiangOrder(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);

		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			String userId = list.get(0).getRowid();

			TabUserAddress tabUserAddress2 = tabUserAddressService.get(userId);

			if(tabUserAddress2 == null) {


				map.put("flag", "false");
				map.put("msg", "Veuillez préciser l'adresse\n"
						+ "\n"
						+ "");
				return map;

			}

			String vip = list.get(0).getVip() + "";

			if (vip.equals("0")) {

				map.put("flag", "false");
				map.put("msg", "Pas actuellement en VIP\n" + "\n" + "");
				return map;
			}

			TabVipConfig vipss = new TabVipConfig();
			vipss.setVip(vip);
			List<TabVipConfig> tabVipConfigs = tabVipConfigService.findList(vipss);

			vipss = tabVipConfigs.get(0);

			TabOrderData order2 = new TabOrderData();
			order2.setUserid(userId);
			order2.setStatus1("1");
			Long cc = tabOrderDataService.findCount(order2);

			if (cc == 0) {

				tabUserData = list.get(0);

				// 获取有没今日的订单

				order2 = new TabOrderData();
				order2.setUserid(userId);
				order2.setCreatetime_gte(DateUtils.getOfDayFirst(getjndDate(new Date())));

				cc = tabOrderDataService.findCount(order2);

				if (cc > 0) {

					map.put("flag", "false");
					map.put("msg", "Commande terminée aujourd'hui");

					return map;
				}

				Map<String, String> parame = new HashMap<String, String>();
				parame.put("userid", userId);

				//Double m = tabJiesuanLogService.getUserMoney(parame);
				Double m = tabUserData.getCurrentmoney();
				// 余额 大于 20 走余额
				if (m >= 10) {

					List<TabProductData> productDatas = getJisuanProduct(m,tabUserData.getVip());
					String orderId = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + getCode() + getCode();

					Double  bj =  0D;
					Double  yj = 0D ;
					List<TabOrderData> datas = new ArrayList<TabOrderData>();
					for (TabProductData tabProductData : productDatas) {

						TabOrderData tabOrderData = new TabOrderData();

						tabOrderData.setRowid(UUID.randomUUID().toString());
						tabOrderData.setUserid(userId);
						tabOrderData.setProductid(tabProductData.getRowid());
						tabOrderData.setOrdermonry(tabProductData.getBuymoney());
						bj  = bj + tabProductData.getBuymoney();

						Double m1 = vipss.getMgrwdyj() * tabProductData.getBuymoney();
						yj = yj + m1 ;

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

						tabOrderDataService.save(tabOrderData);
						datas.add(tabOrderData);

					}

					TabOrders tabOrders   =   new TabOrders();
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

					tabOrdersService.save(tabOrders);

					map.put("data", datas);

					map.put("orderId", orderId);

				} else {
					if (    tabUserData.getIstiyan().equals("1")  && tabUserData.getTyj() != null && tabUserData.getTyj() > 10) {
						m =  tabUserData.getTyj();
						List<TabProductData> productDatas = getJisuanProduct(m,tabUserData.getVip());

						String orderId = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + getCode() + getCode();

						Double  bj =  0D;
						Double  yj = 0D ;

						List<TabOrderData> datas = new ArrayList<TabOrderData>();
						for (TabProductData tabProductData : productDatas) {



							TabOrderData tabOrderData = new TabOrderData();

							tabOrderData.setRowid(UUID.randomUUID().toString());
							tabOrderData.setUserid(userId);
							tabOrderData.setProductid(tabProductData.getRowid());
							tabOrderData.setOrdermonry(tabProductData.getBuymoney());
							bj  = bj + tabProductData.getBuymoney();

							Double m1 = vipss.getMgrwdyj() * tabProductData.getBuymoney();
							yj = yj + m1 ;
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

							tabOrderDataService.save(tabOrderData);
							datas.add(tabOrderData);


						}

						TabOrders tabOrders   =   new TabOrders();
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
						tabOrdersService.save(tabOrders);
						map.put("orderId", orderId);
						map.put("data", datas);
					} else {

						map.put("flag", "false");
						map.put("msg", "Solde est insuffisant\n" + "\n" + "");
					}

				}

			} else {

				List<TabOrderData> ll = tabOrderDataService.findList(order2);

				map.put("data", ll);

				map.put("flag", "false");
				map.put("msg", "Il y a actuellement des commandes en cours d'exécution。\n" + "\n" + "");

			}

			System.err.println(11);

		}
		return map;
	}

	private List<TabProductData> getJisuanProduct(Double m,Long vip) {

		TabVipConfig tabVipConfig   = new TabVipConfig();
		tabVipConfig.setVip(vip+"");

		List<TabVipConfig> tabVipConfigs  = tabVipConfigService.findList(tabVipConfig) ;

		tabVipConfig =   tabVipConfigs.get(0);


		Double pre = tabVipConfig.getMaxgoumai();

		if (pre != null  &&   pre < m ) {
			m = pre;
		}
		pre   =   m / 5;

		Map<String, Object> parame = new HashMap<String, Object>();
		List<TabProductData> result = new ArrayList<TabProductData>();

		parame.put("money", pre);
		List<TabProductData> tabProductDatas2 = tabProductDataService.list(parame);

		List<TabProductData> temps = tabProductDatas2;
		Double  h =  m/10;

		for (int i = 0; i < 10; i++) {
			if(tabProductDatas2.size() ==0 ) {
				tabProductDatas2 = 	temps  ;
			}

			for (TabProductData tabProductData : tabProductDatas2) {
				String f =  "1";
				for (TabProductData tabProductData2 : tabProductDatas2) {

					if(!(tabProductData.getRowid().equals(tabProductData2.getRowid())) &&  (tabProductData.getBuymoney() +   tabProductData2.getBuymoney()) <  h) {
						result.add(tabProductData);
						result.add(tabProductData2);
						tabProductDatas2.remove(tabProductData);
						tabProductDatas2.remove(tabProductData2);


						f= "2";
						break;
					}
				}

				if(f .equals("2")) {
					break;

				}
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
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

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
			tabOrderData.setCreatetime(new Date());
			tabOrderData.setIsNewRecord(true);
			tabOrderData.setStatus1("1");
			tabOrderData.setRowid(UUID.randomUUID().toString());
			tabOrderData.setOrderid(DateUtils.formatDate(new Date(), "yyyyMMddHHmmss") + getCode() + getCode());
			tabOrderDataService.save(tabOrderData);

			map.put("data", tabOrderData);

		}
		return map;

	}

	// changeOrderData

	@RequestMapping(value = "changeOrderData")
	@ResponseBody
	public Map<String, Object> changeOrderData(HttpServletRequest request, HttpServletResponse response) {

		String rowid = request.getParameter("rowid");
		TabOrderData tabOrderData = tabOrderDataService.get(rowid);

		TabUserData tabUserData = tabUserDataService.get(tabOrderData.getUserid());

		Map<String, String> parame = new HashMap<String, String>();

		parame.put("userid", tabUserData.getRowid());
		//Double m = tabJiesuanLogService.getUserMoney(parame);
		Double m = tabUserData.getCurrentmoney();
		TabOrders taborders  = new TabOrders();

		taborders.setOrderid(tabOrderData.getOrderid());
		List<TabOrders> tabOrders  =  tabOrdersService.findList(taborders  );


		taborders =    tabOrders.get(0);


		if (m >= tabOrderData.getOrdermonry() && tabOrderData.getStatus1().equals("1") &&  tabOrderData.getIstiyan().equals("0")) {

			tabOrderData.setStatus1("2");
			tabOrderDataService.save(tabOrderData);

			taborders.setPaytime(getjndDate(new Date()));
			taborders.setPaycount(taborders.getPaycount()+1);
			taborders.setStatus1("2");

			tabOrdersService.save(taborders);

			TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
			tabJiesuanLog.setRowid(UUID.randomUUID().toString());
			tabJiesuanLog.setUserid(tabUserData.getRowid());
			tabJiesuanLog.setCreatetime(getjndDate(new Date()));
			tabJiesuanLog.setCmd("Achat de produits\n" + "");
			tabJiesuanLog.setType("购买商品");
			tabJiesuanLog.setMsg("Buy goods");
			tabJiesuanLog.setParentid1(tabUserData.getParentid());
			tabJiesuanLog.setAmont(tabOrderData.getOrdermonry() * -1); // 购买
			tabJiesuanLog.setStatus1(tabOrderData.getOrderid());
			tabJiesuanLog.setParentid2(tabUserData.getParentid1());
			tabJiesuanLog.setParentid3(tabUserData.getParenti3());
			tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
			tabJiesuanLog.setIsNewRecord(true);
			tabJiesuanLogService.save(tabJiesuanLog);
			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");
			this.dd(tabJiesuanLog,jedis);
			jedis.quit();
			//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
			//getuservip(tabJiesuanLog.getUserid());

			map.put("flag", "true");

		} else {

			if (tabOrderData.getIstiyan() != null && tabOrderData.getIstiyan().equals("1")  && tabOrderData.getStatus1().equals("1")) {

				Double tyj = tabUserData.getTyj();
				if (tyj >= tabOrderData.getOrdermonry() && tabOrderData.getStatus1().equals("1")) {
					tabOrderData.setStatus1("2");
					tabOrderDataService.save(tabOrderData);
					map.put("flag", "true");

					tabUserData.setTyj(tabUserData.getTyj() - tabOrderData.getOrdermonry());
					tabUserDataService.save(tabUserData);

					taborders.setPaytime(getjndDate(new Date()));
					taborders.setPaycount(taborders.getPaycount()+1);
					taborders.setStatus1("2");

					tabOrdersService.save(taborders);


				} else {

					map.put("flag", "false");
					map.put("msg", "Manque d'argent pour l'expérience\n" + "\n" + "");

				}

			} else {

				map.put("flag", "false");
				map.put("msg", "Solde est insuffisant\n" + "\n" + "");

			}
		}

		return map;

	}

	// savePingJiaData

	@RequestMapping(value = "savePingJiaData")
	@ResponseBody
	public Map<String, Object> savePingJiaData(HttpServletRequest request, HttpServletResponse response) {

		String rowid = request.getParameter("rowid");

		String pjia = request.getParameter("pjia");

		String index = request.getParameter("index");

		String array = request.getParameter("array");

		TabOrderData tabOrderData = tabOrderDataService.get(rowid);

		if (tabOrderData.getStatus1().equals("2")) {
			tabOrderData.setStatus1("3");

			tabOrderData.setPjnr(pjia);
			tabOrderData.setServicestart(index);
			tabOrderData.setFenleicontent(array);
			tabOrderDataService.save(tabOrderData);

			// 开始结算

		}

		return map;

	}

	@RequestMapping(value = "getOrderList")
	@ResponseBody
	public Map<String, Object> getOrderList(HttpServletRequest request, HttpServletResponse response) {

		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			String index = request.getParameter("type");

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

			}

			map.put("data", tabOrderDatas);

		}
		return map;

	}

	@RequestMapping(value = "getShouRuList")
	@ResponseBody
	public Map<String, Object> getShouRuList(HttpServletRequest request, HttpServletResponse response) {

		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			String userId = list.get(0).getRowid();

			Map<String, String> parame = new HashMap<String, String>();

			parame.put("userid", userId);

			List<TabJiesuanLog> tabJiesuanLogs = tabJiesuanLogService.getShouruList(parame);

			map.put("data", tabJiesuanLogs);

		}
		return map;

	}

	@RequestMapping(value = "getRechargeList")
	@ResponseBody
	public Map<String, Object> getRechargeList(HttpServletRequest request, HttpServletResponse response) {

		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			String userId = list.get(0).getRowid();

			Map<String, String> parame = new HashMap<String, String>();

			parame.put("userid", userId);

			List<TabJiesuanLog> tabJiesuanLogs = tabJiesuanLogService.getZhiChuList(parame);

			map.put("data", tabJiesuanLogs);

		}
		return map;

	}

	// getLiCaiData

	@RequestMapping(value = "getLiCaiData")
	@ResponseBody
	public Map<String, Object> getLiCaiData(HttpServletRequest request, HttpServletResponse response) {

		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

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
				if (tabLicaiData2.getCreatetime().getTime() >= DateUtils.getOfDayFirst(getjndDate(new Date())).getTime()) {
					m1 = m1 + tabLicaiData2.getLicaimoney();
				}

				m = m + tabLicaiData2.getLicaimoney();

			}

			map.put("curr", m1);
			map.put("total", m);

			map.put("data", tabLicaiDatas);

		}
		return map;

	}

	// 获取汇率

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

	@RequestMapping(value = "getHuiLvData")
	@ResponseBody
	public Map<String, Object> getHuiLvData(HttpServletRequest request, HttpServletResponse response) {
		List<TabHuilvConfig> tabHuilvConfigs = tabHuilvConfigService.findList(new TabHuilvConfig());
		map.put("data", tabHuilvConfigs);

		map.put("code", "0");

		return map;
	}

	@RequestMapping(value = "listTongDao")
	@ResponseBody
	public Map<String, Object> listTongDao(HttpServletRequest request, HttpServletResponse response) {

//		TabTongdao entity = new TabTongdao();
//		entity.setDaishou("2");
//		List<TabTongdao> tabTongdaos = tabTongdaoService.findList(entity);
//
//		map.put("data", tabTongdaos);

		String type = "USD";

		TabTongdao obj = new TabTongdao();
		obj.setType(type);
		List<TabTongdao> tabTongdaos = tabTongdaoService.findList(obj);

		map.put("data1", tabTongdaos);

		type = "CDF";
		obj.setType(type);
		List<TabTongdao> tabTongdaos2 = tabTongdaoService.findList(obj);

		map.put("data2", tabTongdaos2);

		map.put("code", "0");

		return map;
	}

	@RequestMapping(value = "getUserYe")
	@ResponseBody
	public Map<String, Object> getUserYe(HttpServletRequest request, HttpServletResponse response) {

		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {

			String userId = list.get(0).getRowid();

			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", userId);

			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = list.get(0).getCurrentmoney();
			map.put("money", m);

			map.put("tyj", list.get(0).getTotalmoney());
		}

		return map;
	}

	@RequestMapping(value = "jisuanProduct")
	@ResponseBody
	public Map<String, Object> jisuanProduct(HttpServletRequest request, HttpServletResponse response) {
		// tab_product_data
		// https://z7nmix0e.oss-cn-hongkong.aliyuncs.com/z230.jpg
		for (int i = 0; i < 50; i++) {

			TabProductData tabProductData = new TabProductData();
			tabProductData.setRowid(UUID.randomUUID().toString());
			tabProductData.setLeval(2 + "");
			tabProductData.setBuymoney(i * 200 + 1D);
			tabProductData.setName("djs" + (i + 1));
			tabProductData.setRebate(tabProductData.getBuymoney() / 20);
			tabProductData.setImgsrc("https://z7nmix0e.oss-cn-hongkong.aliyuncs.com/z230.jpg");
			tabProductData.setIsNewRecord(true);
			tabProductDataService.save(tabProductData);

		}
		return map;

	}

	@RequestMapping(value = "getMyDetail")
	@ResponseBody
	public Map<String, Object> getMyDetail(HttpServletRequest request, HttpServletResponse response) {
		// tab_product_data

		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			TabUserData userData = list.get(0);

			map.put("data", userData);

		}
		return map;

	}

	@RequestMapping(value = "getShouru")
	@ResponseBody
	public Map<String, Object> getShouru(HttpServletRequest request, HttpServletResponse response) {
		// tab_product_data
		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			TabUserData userData = list.get(0);

			String userId = userData.getRowid();

			Map<String, String> parame = new HashMap<String, String>();

			parame.put("userid", userId);

			//Double shouru = tabJiesuanLogService.getShouruData(parame);
			Double shouru = userData.getGrsy();
			map.put("shouru", shouru);

		}
		return map;

	}

	@RequestMapping(value = "getShouru2")
	@ResponseBody
	public Map<String, Object> getShouru2(HttpServletRequest request, HttpServletResponse response) {
		// tab_product_data
		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			TabUserData userData = list.get(0);

			String userId = userData.getRowid();

			Map<String, String> parame = new HashMap<String, String>();

			parame.put("userid", userId);

			//Double shouru = tabJiesuanLogService.getShouruDataTuandui(parame);
			Double shouru = userData.getTdsy();
			map.put("shouru", shouru);

		}
		return map;

	}

	// 获取我的
	// 获取我的一级用户

	@CrossOrigin(origins = { "*", "null" })
	@RequestMapping(value = { "/getData51", "" })
	@ResponseBody
	public Map<String, Object> getData51(HttpServletRequest servletRequest) {

		Date date = getjndDate(new Date());

		map.put("date", date.getTime());

		return map;

	}

	@CrossOrigin(origins = { "*", "null" })
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

	@CrossOrigin(origins = { "*", "null" })
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

	@CrossOrigin(origins = { "*", "null" })
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

	@CrossOrigin(origins = { "*", "null" })
	@RequestMapping(value = { "/getData55", "" })
	@ResponseBody
	public Map<String, Object> getData55(HttpServletRequest servletRequest) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getjndDate(new Date()));
		map.put("min", DateUtils.formatDate(calendar.getTime(), "yyyy-MM-") + "01");
		map.put("max", DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd") + "");

		return map;

	}

	@CrossOrigin(origins = { "*", "null" })
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
				parame.put("name2", "%gb" + name2 + "%");

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
				parame.put("name2", "%gb" + name2 + "%");

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

	@Autowired
	private ViewShouchongService viewShouchongService;

	@RequestMapping(value = { "/getData3", "" })
	@ResponseBody
	public Map<String, Object> getData3(HttpServletRequest servletRequest) {

		String name1 = servletRequest.getParameter("name1");
		String name2 = servletRequest.getParameter("name2");

		String date1 = servletRequest.getParameter("date1");
		String date2 = servletRequest.getParameter("date2");

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
				parame.put("name2", "%gb" + name2 + "%");

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
				parame.put("name2", "%gb" + name2 + "%");

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
				parame.put("name2", "%gb" + name2 + "%");

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

		return map;
	}

	// 获取我的 进度

	@RequestMapping(value = { "/getVipJindu", "" })
	@ResponseBody
	public Map<String, Object> getVipJindu(HttpServletRequest request) {
		String   type = request.getParameter("type");

		String token = request.getParameter("token");
		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			TabUserData userData = list.get(0);
			if(type.equals("1")) {
				userData.setVip(0l);
			}

			Long vip = userData.getVip() + 1;

			//

			TabVipConfig tabVipConfig = new TabVipConfig();
			tabVipConfig.setVip(vip + "");
			List<TabVipConfig> tabVipConfigs = tabVipConfigService.findList(tabVipConfig);

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

	@Autowired
	private UserService userService;

	//

	@Autowired
	private TabTongdaoService tabTongdaoService;
	@Autowired
	private TabJiesuanLogService tabJiesuanLogService;

	@Autowired
	private TabTaskjJobService tabTaskjJobService;

	// updatePassword

	// getConfig2

	@RequestMapping(value = { "/getConfig2", "" })
	@ResponseBody
	public Map<String, Object> getConfig2(HttpServletRequest request) {

		if (UserUtils.getLoginInfo() != null) {

			String id = UserUtils.getLoginInfo().getId();

			User user = userService.get(id);

			String acc = user.getLoginCode();

			TabUserData tabUserData = new TabUserData();
			tabUserData.setSysuserid(acc);

			List<TabUserData> tabUserDatas = tabUserDataService.findList(tabUserData);

			if (tabUserDatas.size() > 0) {

				map.put("data", tabUserDatas.get(0).getQuanxian());
			}

		} else {
			String token = (String) request.getSession().getAttribute("token");

			TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
			map.put("data", tabYuangongData.getQuanxian());

		}

		return map;
	}

	@RequestMapping(value = "dailiDl")
	public String dailiDl(HttpServletRequest httpServletRequest) {
		return "modules/daili/login";
	}
	// dailiPost

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

		} else {
			map.put("flag", "false");
			map.put("message", "账号密码错误或账号锁定");

		}

		return map;

	}

	@RequestMapping(value = { "home3", "" })
	public String home3(HttpServletRequest servletRequest) {
		return "modules/daili/home3";

	}

	@Autowired
	private TabYuangongDataService tabYuangongDataService;

	@RequestMapping(value = { "daPing2", "" })
	public String daPing2(HttpServletRequest servletRequest, Model model) {
		String token = (String) servletRequest.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}
		model.addAttribute("acc", tabYuangongData.getQtzh());

		return "modules/daili/daPing2";

	}

//tabUserDataList

	@RequestMapping(value = { "tabUserDataList", "" })
	public String tabUserDataList(HttpServletRequest request ,TabUserData tabUserData, Model model) {
		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}
		model.addAttribute("tabUserData", tabUserData);
		return "modules/daili/tabUserDataList";
	}

	@RequestMapping(value = { "tabUserDataListKefu", "" })
	public String tabUserDataListKefu(HttpServletRequest request ,TabUserData tabUserData, Model model) {
		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}
		model.addAttribute("tabUserData", tabUserData);
		return "modules/daili/tabUserDataListKefu";
	}

	@RequestMapping(value = { "tabUserBankListKefu", "" })
	public String tabUserBankListKefu(HttpServletRequest request ,TabUserBank tabUserBank, Model model) {
		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}
		model.addAttribute("tabUserBank", tabUserBank);
		return "modules/daili/tabUserBankListKefu";
	}

	@RequestMapping(value = { "tabContaceServiceList", "" })
	public String tabContaceServiceList(HttpServletRequest request ,TabUserData tabUserData, Model model) {
		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}
		model.addAttribute("tabUserData", tabUserData);
		return "modules/daili/tabContaceServiceList";
	}

	@RequestMapping(value = "tabContaceServicefrom")
	public String form(TabContaceService tabContaceService, Model model) {
		model.addAttribute("tabContaceService", tabContaceService);
		return "modules/daili/tabContaceServiceForm";
	}
	// tabUserDataList2

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

	@RequestMapping(value = { "tabOrderDataList", "" })
	public String tabOrderDataList(HttpServletRequest request,TabOrderData tabOrderData, Model model) {
		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}
		model.addAttribute("tabOrderData", tabOrderData);

		return "modules/daili/tabOrderDataList";
	}

	@RequestMapping(value = { "tabRechangeLogList", "" })
	public String tabRechangeLogList(HttpServletRequest request,TabRechangeLog tabRechangeLog, Model model) {
		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}
		TabTongdao onbj = new TabTongdao();
		onbj.setDaishou("2");
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
	public String tabTixianLogList(HttpServletRequest request , TabTixianLog tabTixianLog, Model model) {
		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}
		TabTongdao onbj = new TabTongdao();
		onbj.setDaifu("2");
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

	// tabUserDataListData

	@RequestMapping(value = "tabUserDataListData")
	@ResponseBody
	public Page<TabUserData> listData(TabUserData tabUserData, HttpServletRequest request,
									  HttpServletResponse response) {

		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();

		tabUserData.setShangjilink(acc);

		tabUserData.setPage(new Page<>(request, response));
		Page<TabUserData> page = tabUserDataService.findPage(tabUserData);

		List<TabUserData> tabUserDatas = page.getList();

		for (TabUserData tabUserData2 : tabUserDatas) {

			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData2.getRowid());
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData2.getCurrentmoney();
			Double czzje = tabRechangeLogService.getSum(parame);
			tabUserData2.setSumBalance(czzje);

			Double ztx = tabTixianLogService.getSum(parame);
			tabUserData2.setTotalmoney(ztx);

			tabUserData2.setCurrentmoney(m);

			//tabUserData2.setJrczje(tabJiesuanLogService.getShouruData(parame));
			tabUserData2.setJrczje(tabUserData2.getGrsy());
			parame.put("userid", "%" + tabUserData2.getRowid() + "%");

			//Double tdje = tabJiesuanLogService.getTuandui(parame);
			Double tdje = tabUserData2.getTdsy();
			tabUserData2.setTdZgwje(tdje);
		}

		page.setList(tabUserDatas);
		return page;
	}

	// tabOrderDataListData

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

	// tabRechangeLogListData
	@RequestMapping(value = "tabRechangeLogListData")
	@ResponseBody
	public Page<TabRechangeLog> tabRechangeLogListData(TabRechangeLog tabRechangeLog, HttpServletRequest request,
													   HttpServletResponse response) {

		if (tabRechangeLog.getUpdatetime_lte() != null) {
			tabRechangeLog.setUpdatetime_lte(DateUtils.getOfDayLast(tabRechangeLog.getUpdatetime_lte()));

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

	// tabTixianLogListData

	@RequestMapping(value = "tabTixianLogListData")
	@ResponseBody
	public Page<TabTixianLog> tabTixianLogListData(TabTixianLog tabTixianLog, HttpServletRequest request,
												   HttpServletResponse response) {
		if (tabTixianLog.getUpdatetime_lte() != null) {
			tabTixianLog.setUpdatetime_lte(DateUtils.getOfDayLast(tabTixianLog.getUpdatetime_lte()));

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

			if (tabTixianLog2.getType().equals("2")) {
				TabUserData userData = tabUserDataService.get(tabTixianLog2.getUserid());
				tabTixianLog2.setCardtype(userData.getEtransfername() + "," + userData.getEtransferaccount());
				tabTixianLog2.setUseraddress(userData.getEtransfername() + "," + userData.getEtransferaccount());

			} else if (tabTixianLog2.getType().equals("3")) {
				TabUserData userData = tabUserDataService.get(tabTixianLog2.getUserid());

				tabTixianLog2.setCardtype(userData.getDigitaladdress());
				tabTixianLog2.setUseraddress(userData.getDigitaladdress());

			}
		}

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

		if (tabRechangeLog.getUpdatetime_lte() != null) {
			tabRechangeLog.setUpdatetime_lte(DateUtils.getOfDayLast(tabRechangeLog.getUpdatetime_lte()));

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

		Double zczje = tabRechangeLogService.getCzje(map);
		Long bs = tabRechangeLogService.getCzbs(map);

		map.put("czje", zczje);
		map.put("bs", bs);

		return map;

	}

//tabJiesuanLogList

	@RequestMapping(value = { "tabJiesuanLogList", "" })
	public String tabJiesuanLogList(HttpServletRequest servletRequest , TabJiesuanLog tabJiesuanLog, Model model) {
		String token = (String) servletRequest.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}
		model.addAttribute("tabJiesuanLog", tabJiesuanLog);

		return "modules/daili/tabJiesuanLogList";
	}

	// tabJiesuanLogListData

	@RequestMapping(value = "tabJiesuanLogListData")
	@ResponseBody
	public Page<TabJiesuanLog> tabJiesuanLogListData(TabJiesuanLog tabJiesuanLog, HttpServletRequest request,
													 HttpServletResponse response) {

		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();
		tabJiesuanLog.setShangjilink(acc);

		tabJiesuanLog.setPage(new Page<>(request, response));
		Page<TabJiesuanLog> page = tabJiesuanLogService.findPage(tabJiesuanLog);
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

				//this.dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
				//getuservip(tabJiesuanLog.getUserid());


			}
		}
		jedis.quit();
		return map;
	}

	// tabUserDataForm

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
	public String tabUserDataSave(TabUserData tabUserData, HttpServletRequest request) {

		String txpassword = tabUserData.getTxpassword();
		String rowid = tabUserData.getRowid();

		Double m = tabUserData.getCurrentmoney();

		tabUserData = tabUserDataService.get(rowid);

		TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();

		String orderId = DateUtils.formatDate(new Date()).replaceAll("-", "") + new HomeController().getCode();

		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);

		tabJiesuanLog.setUserid(tabUserData.getRowid());
		tabJiesuanLog.setCmd("Don du système");

		tabJiesuanLog.setType(txpassword + ":操作人:" + tabYuangongData.getAcccount());
		tabJiesuanLog.setAmont(m);
		tabJiesuanLog.setMsg("System operation");
		tabJiesuanLog.setRowid(UUID.randomUUID().toString());
		tabJiesuanLog.setCreatetime(new Date());
		tabJiesuanLog.setCurrentmoney(tabUserData.getCurrentmoney());
		tabJiesuanLog.setIsNewRecord(true);
		tabJiesuanLog.setStatus1(orderId);

		tabJiesuanLog.setParentid1(tabUserData.getParentid());
		tabJiesuanLog.setParentid2(tabUserData.getParentid1());
		tabJiesuanLog.setParentid3(tabUserData.getParenti3());
		tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());

		tabJiesuanLogService.save(tabJiesuanLog);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		this.dd(tabJiesuanLog,jedis);
		//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
		//getuservip(tabJiesuanLog.getUserid());

		//

		Map<String, String> parame2 = new HashMap<String, String>();
		parame2.put("userid", tabUserData.getRowid());
		//Double money = tabJiesuanLogService.getUserMoney(parame2);
		Double money = tabUserData.getCurrentmoney();
		if (money >= 10) {

			tabUserData.setIstiyan("0");
			tabUserData.setValidate(DateUtils.addDays(new Date(), 99999));
			tabUserDataService.save(tabUserData);
		}
		jedis.quit();
		return renderResult(Global.TRUE, text("上分成功"));
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
		tabJiesuanLog.setCmd("Récupération du système\n" + "");
		tabJiesuanLog.setType(txpassword + ":操作人:" + tabYuangongData.getAcccount());
		tabJiesuanLog.setMsg("System operation");
		tabJiesuanLog.setAmont(m * -1);
		tabJiesuanLog.setRowid(UUID.randomUUID().toString());
		tabJiesuanLog.setCreatetime(new Date());
		tabJiesuanLog.setCurrentmoney(tabUserData.getCurrentmoney());
		tabJiesuanLog.setIsNewRecord(true);
		tabJiesuanLog.setStatus1(orderId);

		tabJiesuanLog.setParentid1(tabUserData.getParentid());
		tabJiesuanLog.setParentid2(tabUserData.getParentid1());
		tabJiesuanLog.setParentid3(tabUserData.getParenti3());
		tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
		tabJiesuanLogService.save(tabJiesuanLog);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		this.dd(tabJiesuanLog,jedis);
		//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
		//getuservip(tabJiesuanLog.getUserid());
		jedis.quit();
		return renderResult(Global.TRUE, text("下分成功"));
	}

	//
	@RequestMapping(value = { "/tabUserDataUpdatePassword", "" })
	@ResponseBody
	public Map<String, Object> tabUserDataUpdatePassword(HttpServletRequest request) {

		String rowids = request.getParameter("rowids");

		TabUserData tabUserData = tabUserDataService.get(rowids);

		tabUserData.setPassword(new HomeController().getCode());

		tabUserDataService.save(tabUserData);

		map.put("data", tabUserData);

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

	//private Jedis jedis =  new Jedis("41.72.149.115", 6379);

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

	/*@RequestMapping(value = "gengxinTiYan")
	@ResponseBody
	public String gengxinTiYan(HttpServletRequest servletRequest) {
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

			tabUserData.setVip(1L);
			tabUserData.setTyj(obj.getTyj());
			tabUserData.setIstiyan("1");

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(HomeController.getjndDate(new Date()));
			calendar.add(Calendar.DAY_OF_MONTH, obj.getTianshu());

			tabUserDataService.save(tabUserData);

			List<TabUserData> list = new ArrayList<>();
			list.add(tabUserData);
			jedis.auth("hask071bend");
			jedis.set("UserDataByToken:"+tabUserData.getAccesstoken(), JSON.toJSONString(list));
			jedis.expire("UserDataByToken:"+tabUserData.getAccesstoken(),7200);

			*//*String data = jedis.get(TOKEN+tabUserData.getAccesstoken());
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
			jedis.expire(TOKEN+tabUserData.getAccesstoken(), 7200);*//*

		}

		// 设置体验会员

		return renderResult(Global.TRUE, text("下分成功"));

	}*/

	// tabUserDataListData2
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

		for (TabUserData tabUserData2 : tabUserDatas) {

			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData2.getRowid());
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData2.getCurrentmoney();
			Double czzje = tabRechangeLogService.getSum(parame);
			tabUserData2.setSumBalance(czzje);

			Double ztx = tabTixianLogService.getSum(parame);
			tabUserData2.setTotalmoney(ztx);

			tabUserData2.setCurrentmoney(m);

			//tabUserData2.setJrczje(tabJiesuanLogService.getShouruData(parame));
			tabUserData2.setJrczje(tabUserData2.getGrsy());
			parame.put("userid", "%" + tabUserData2.getRowid() + "%");

			//Double tdje = tabJiesuanLogService.getTuandui(parame);
			Double tdje = tabUserData2.getTdsy();
			tabUserData2.setTdZgwje(tdje);
		}
		page.setList(tabUserDatas);

		return page;
	}

	@RequestMapping(value = "tabUserDataListData3")
	@ResponseBody
	public Page<TabUserData> tabUserDataListData3(TabUserData tabUserData, HttpServletRequest request,
												  HttpServletResponse response) {
		Page<TabUserData> page = new Page<>();

		List<TabUserData> tabUserDatas = new ArrayList<TabUserData>();

		TabUserData userData = new TabUserData();
		userData.setShangjilink(tabUserData.getRowid());
		tabUserDatas = tabUserDataService.findList(userData);
		for (TabUserData tabUserData2 : tabUserDatas) {

			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData2.getRowid());
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData2.getCurrentmoney();
			tabUserData2.setCurrentmoney(m);

		}

		for (TabUserData tabUserData2 : tabUserDatas) {

			Map<String, String> parame = new HashMap<String, String>();
			parame.put("userid", tabUserData2.getRowid());
			//Double m = tabJiesuanLogService.getUserMoney(parame);
			Double m = tabUserData2.getCurrentmoney();

			Double czzje = tabRechangeLogService.getSum(parame);
			tabUserData2.setSumBalance(czzje);

			Double ztx = tabTixianLogService.getSum(parame);
			tabUserData2.setTotalmoney(ztx);

			tabUserData2.setCurrentmoney(m);

			//tabUserData2.setJrczje(tabJiesuanLogService.getShouruData(parame));
			tabUserData2.setJrczje(tabUserData2.getGrsy());
			parame.put("userid", "%" + tabUserData2.getRowid() + "%");

			//Double tdje = tabJiesuanLogService.getTuandui(parame);
			Double tdje = tabUserData2.getTdsy();
			tabUserData2.setTdZgwje(tdje);

		}
		page.setList(tabUserDatas);
		return page;
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
	private TabTyjJiesuanService tabTyjJiesuanService;

	@Autowired
	private TabTempsService tabTempsService;

	@RequestMapping(value = "tabRechangeLogChangeData")
	@ResponseBody
	public Map<String, Object> tabRechangeLogChangeData(HttpServletRequest request, HttpServletResponse response) {
		String id =  UserUtils.getLoginInfo().getId();
		User user   =  userService.get(id);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		if(user == null || !user.getLoginCode().equals("admin")){
			return null;
		}
		String rowids = request.getParameter("rowids");

		String[] ss = rowids.split(",");

		for (String string : ss) {

			TabRechangeLog tabRechangeLog = tabRechangeLogService.get(string);

			String userId = tabRechangeLog.getUserid();

			if (tabRechangeLog.getStatus1().equals("1") || tabRechangeLog.getStatus1().equals("3")) {
				tabRechangeLog.setStatus1("2");
				tabRechangeLog.setBeizhu("支付成功tabRechangeLogChangeData2");
				tabRechangeLog.setUpdatetime(new HomeController().getjndDate(new Date()));
				System.out.println("查看充值数据tabRechangeLogChangeData2："+ JSONObject.toJSONString(tabRechangeLog));

				TabUserData tabUserData = tabUserDataService.get(tabRechangeLog.getUserid());
				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setUserid(tabRechangeLog.getUserid());
				tabJiesuanLog.setCmd("Recharger");
				tabJiesuanLog.setType("充值成功");
				tabJiesuanLog.setMsg("Recharge successfully");
				tabJiesuanLog.setAmont(tabRechangeLog.getAmont());
				tabJiesuanLog.setRowid(UUID.randomUUID().toString());
				tabJiesuanLog.setCreatetime(new HomeController().getjndDate(new Date()));
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
				Map<String, String> parame = new HashMap<String, String>();
				parame.put("userid", tabRechangeLog.getUserid());


				if(tabUserData.getIstiyan().equals("1")){
					Double money = tabUserData.getCurrentmoney() + tabJiesuanLog.getAmont();

					List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
					Double mm = 15.0;
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
				tabJiesuanLogService.save(tabJiesuanLog);

				this.dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
				//getuservip(tabJiesuanLog.getUserid());

			}
		}
		jedis.quit();
		return null;
	}
	private static String UserDataByToken = "UserDataByToken:";

	@RequestMapping(value = "tabRechangeLogChangeData2")
	@ResponseBody
	public Map<String, Object> tabRechangeLogChangeData2(HttpServletRequest request, HttpServletResponse response) {
		String rowids = request.getParameter("rowids");

		String[] ss = rowids.split(",");

		for (String string : ss) {

			TabRechangeLog tabRechangeLog = tabRechangeLogService.get(string);

			String userId = tabRechangeLog.getUserid();
			TabUserData tabUserData = tabUserDataService.get(userId);
			if (tabRechangeLog.getStatus1().equals("2")) {

				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
				tabJiesuanLog.setUserid(tabRechangeLog.getUserid());
				tabJiesuanLog.setCmd("deposit");
				tabJiesuanLog.setType("充值失败");
				tabJiesuanLog.setMsg("Recharge failed");
				tabJiesuanLog.setAmont(tabRechangeLog.getAmont() * -1);
				tabJiesuanLog.setRowid(UUID.randomUUID().toString());
				tabJiesuanLog.setCreatetime(new HomeController().getjndDate(new Date()));
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLog.setStatus1(tabRechangeLog.getOrderid());
				tabJiesuanLog.setShangjilink(tabRechangeLog.getShangjilink());
				tabJiesuanLog.setParentid1(tabRechangeLog.getParentid1());
				tabJiesuanLog.setParentid2(tabRechangeLog.getParentid2());
				tabJiesuanLog.setParentid3(tabRechangeLog.getParentid3());

				Map<String, String> parame = new HashMap<String, String>();
				parame.put("userid", tabRechangeLog.getUserid());

				if(tabUserData.getIstiyan().equals("1")){
					List<TabVipConfig> tabVipConfigs = getVipConfigList(new TabVipConfig());
					Double mm = 10000.0;
					if(tabVipConfigs.size() > 0){
						mm = tabVipConfigs.get(0).getCurrentmoney();
					}
					if (tabUserData.getCurrentmoney() +tabJiesuanLog.getAmont() >= mm) {
						tabUserData.setIstiyan("0");
						tabUserData.setValidate(DateUtils.addDays(new Date(), 99999));
						tabUserDataService.save(tabUserData);
					}
				}
				tabJiesuanLogService.save(tabJiesuanLog);
				Jedis jedis =  new Jedis("41.72.149.115", 6379);
				jedis.auth("hask071bend");
				this.dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
				//getuservip(tabJiesuanLog.getUserid());

				tabRechangeLog.setStatus1("3");
				tabRechangeLog.setBeizhu("支付失败");
				tabRechangeLog.setUpdatetime(new HomeController().getjndDate(new Date()));
				System.out.println("查看充值数据tabRechangeLogChangeData222："+ JSONObject.toJSONString(tabRechangeLog));
				tabRechangeLogService.save(tabRechangeLog);
				jedis.quit();
			}
		}

		return null;
	}

	// tabTixianLogChangeData

	@PostMapping(value = "tabTixianLogChangeData")
	@ResponseBody
	public String tabTixianLogChangeData(HttpServletRequest httpServletRequest) {

		// 设置成功

		String rowids = httpServletRequest.getParameter("rowids");

		String[] s = rowids.split(",");

		for (String string : s) {

			TabTixianLog tabTixianLog = tabTixianLogService.get(string);

			TabUserData userData = tabUserDataService.get(tabTixianLog.getUserid());

			String status1 = tabTixianLog.getStatus1();

			if (status1.equals("1") || status1.equals("12") || status1.equals("4")) {

				tabTixianLog.setStatus1("2");
				tabTixianLog.setUpdatetime(HomeController.getjndDate(new Date()));
				tabTixianLogService.save(tabTixianLog);
			} else if (status1.equals("3")) {
				tabTixianLog.setStatus1("2");
				tabTixianLog.setUpdatetime(HomeController.getjndDate(new Date()));
				tabTixianLogService.save(tabTixianLog);

				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();

				tabJiesuanLog.setUserid(userData.getRowid());
				tabJiesuanLog.setAmont(Double.valueOf(tabTixianLog.getMoney()) * -1);
				tabJiesuanLog.setRowid(UUID.randomUUID().toString());
				tabJiesuanLog.setCmd("Retrait");
				tabJiesuanLog.setType("提现成功");
				tabJiesuanLog.setMsg("Withdraw successfully");
				tabJiesuanLog.setCreatetime(HomeController.getjndDate(new Date()));
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLog.setParentid1(userData.getParentid());
				tabJiesuanLog.setParentid2(userData.getParentid1());
				tabJiesuanLog.setParentid3(userData.getParenti3());
				tabJiesuanLog.setShangjilink(userData.getShangjilink());
				tabJiesuanLog.setStatus1(tabTixianLog.getOrderid());

				tabJiesuanLogService.save(tabJiesuanLog);
				Jedis jedis =  new Jedis("41.72.149.115", 6379);
				jedis.auth("hask071bend");
				this.dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
				//getuservip(tabJiesuanLog.getUserid());
				jedis.quit();
			}

		}

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

			if (status1.equals("1") || status1.equals("12") || status1.equals("4") || status1.equals("2")) {

				TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();

				tabJiesuanLog.setUserid(tabUserData.getRowid());
				tabJiesuanLog.setAmont(Double.valueOf(tabTixianLog.getMoney()));
				tabJiesuanLog.setRowid(UUID.randomUUID().toString());
				tabJiesuanLog.setCmd("Retrait");
				tabJiesuanLog.setType("提现失败");
				tabJiesuanLog.setMsg("Withdraw failed");
				tabJiesuanLog.setCreatetime(HomeController.getjndDate(new Date()));
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLog.setParentid1(tabUserData.getParentid());
				tabJiesuanLog.setParentid2(tabUserData.getParentid1());
				tabJiesuanLog.setParentid3(tabUserData.getParenti3());
				tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
				tabJiesuanLog.setStatus1(tabTixianLog.getOrderid());

				tabJiesuanLogService.save(tabJiesuanLog);

				this.dd(tabJiesuanLog,jedis);
				//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
				//getuservip(tabJiesuanLog.getUserid());

			}

			tabTixianLog.setStatus1("3");
			tabTixianLog.setUpdatetime(HomeController.getjndDate(new Date()));
			tabTixianLogService.save(tabTixianLog);
			String orderid = tabTixianLog.getOrderid();

			TabJiesuanLog tabJiesuanLog = new TabJiesuanLog();
			tabJiesuanLog.setStatus1(orderid);
			List<TabJiesuanLog> tabJiesuanLogs = tabJiesuanLogService.findList(tabJiesuanLog);

			for (TabJiesuanLog tabJiesuanLog2 : tabJiesuanLogs) {
				tabJiesuanLog2.setZt("failed ");
				tabJiesuanLogService.save(tabJiesuanLog2);

				//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog2));
				//getuservip(tabJiesuanLog.getUserid());


			}

		}
		jedis.quit();
		return renderResult(Global.TRUE, text("保存成功！"));
	}

	// tabTixianLogChangeData9

	@PostMapping(value = "tabTixianLogChangeData9")
	@ResponseBody
	public String tabTixianLogChangeData9(HttpServletRequest httpServletRequest) {
		String rowid = httpServletRequest.getParameter("rowids");

		TabTixianLog tabTixianLog = tabTixianLogService.get(rowid);

		tabTixianLog.setStatus1("12");
		tabTixianLog.setUpdatetime(HomeController.getjndDate(new Date()));
		tabTixianLogService.save(tabTixianLog);

		return "";
	}

	//

	// http://localhost:8980/js/api/saveBankData

	@PostMapping(value = "saveBankData")
	@ResponseBody
	public Map saveBankData(HttpServletRequest httpServletRequest) {
		String rowid = httpServletRequest.getParameter("rowid");
		String name = httpServletRequest.getParameter("name");
		String phone = httpServletRequest.getParameter("phone");
		String type = httpServletRequest.getParameter("type");

		String token = httpServletRequest.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list == null || list.size() == 0) {
			map.put("code", 1);
		} else {
			map.put("code", 0);

			TabUserData userData = list.get(0);
			token = userData.getRowid();
			if (!StringUtils.isBlank(rowid)) {

				TabUserBank userBank = tabUserBankService.get(rowid);
				userBank.setFirstname(name);
				userBank.setLastname(phone);
				userBank.setTransitnumber(type);
				userBank.setUserid(token);
				tabUserBankService.save(userBank);

			} else {
				TabUserBank userBank = new TabUserBank();
				userBank.setFirstname(name);
				userBank.setLastname(phone);
				userBank.setTransitnumber(type);
				userBank.setUserid(token);
				userBank.setRowid(UUID.randomUUID().toString());
				userBank.setIsNewRecord(true);
				userBank.setCreatetime(new Date());
				tabUserBankService.save(userBank);

			}

		}

		map.put("flag", "true");

		return map;
	}

	// getAllBack

	@PostMapping(value = "getAllBack")
	@ResponseBody
	public Map getAllBack(HttpServletRequest httpServletRequest) {

		String token = httpServletRequest.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

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
				parame.put("name2", "%gb" + name2 + "%");

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
		Double czje = tabOrderDataService.getTotalMoney(parame);

		map.put("xdye", czje);

		Long cc = tabOrderDataService.getTotalRenshu(parame);
		map.put("xdrs", cc);



		return map;

	}


	@RequestMapping(value = "getHys")
	@ResponseBody
	public Map getHys(HttpServletRequest servletRequest) {




		List<TabProductData>  tabProductDatas =   tabProductDataService.findList(new TabProductData());

		for (TabProductData tabProductData : tabProductDatas) {
			String name =  tabProductData.getName();

			name =  name.split("\\$")[0];
			System.out.println(name);
			tabProductData.setName(name);
			tabProductDataService.save(tabProductData);


		}


		return map;



	}
	@Autowired
	private TabXiadanSuccessTongjiService tabXiadanSuccessTongjiService ;

	@Autowired
	private  TabRechangeLogService tabRechargeSuccessTongjiService;

	@Autowired
	private  TabTixianLogService tabTixianSuccessTongjiService ;

	//重新计算VIP

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
							if(renshu >= tabVipConfig.getXiaji() && tabVipConfig.getXiaji() >0){
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
		}



		return "0";
	}

	//getTiXian

	@RequestMapping(value = "getTiXian")
	@ResponseBody
	public Map getTiXian(HttpServletRequest httpServletRequest) {

		String token = httpServletRequest.getParameter("token");

		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);

		if (list.size() >  0  ) {

			TabUserData  userData  =   list.get(0);

			String  tx =  userData.getTxpassword();
			if (!StringUtils.isBlank(tx)) {
				map.put("rr", "1");
			}else {
				map.put("rr", "0");

			}
		}

		return  map;
	}


	//

	//

	@RequestMapping(value = "getJinDuTiao")
	@ResponseBody
	public Map getJinDuTiao(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getParameter("token");
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		List<TabRechargeJiangli>  tabRechargeJianglis  =   tabRechargeJiangliService.findList(new TabRechargeJiangli())  ;



		List<TabYaoqingJiangli>  tabYaoqingJianglis  =   tabYaoqingJiangliService.findList(new TabYaoqingJiangli());




		TabUserData tabUserData = new TabUserData();
		tabUserData.setAccesstoken(token);
		List<TabUserData> list = tabUserDataService.findList(tabUserData);




		if(list.size() > 0) {
			tabUserData   = list.get(0);
			String userId =  list.get(0).getRowid();

			Map<String, String> parame = new HashMap<String, String>();

			parame.put("userId", userId);


			Long  cc2  =   tabUserDataService.getVipCount(parame );

			if(cc2  == null) {
				cc2=  0L ;
			}

			Long  max1 = 0L;

			for (TabYaoqingJiangli tabYaoqingJiangli : tabYaoqingJianglis) {

				max1 =  tabYaoqingJiangli.getXjrs();
				if(tabYaoqingJiangli.getXjrs() > cc2) {
					break;
				}else {
					TabYaoqingJiangliLog tabYaoqingJiangliLog  = new TabYaoqingJiangliLog();

					tabYaoqingJiangliLog.setUserid(userId);

					tabYaoqingJiangliLog.setJlbh(tabYaoqingJiangli.getJlbh());

					Long  ccN  =      tabYaoqingJiangliLogService.findCount(tabYaoqingJiangliLog);

					if(ccN ==0) {

						tabYaoqingJiangliLog.setXjrs(tabYaoqingJiangli.getXjrs());
						tabYaoqingJiangliLog.setJlje(tabYaoqingJiangli.getJlje());
						tabYaoqingJiangliLog.setCreatetime(getjndDate(new Date()));
						tabYaoqingJiangliLog.setRowid(userId + tabYaoqingJiangli.getJlbh());

						tabYaoqingJiangliLog.setIsNewRecord(true);

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

						this.dd(tabJiesuanLog,jedis);
						//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
						//getuservip(tabJiesuanLog.getUserid());


					}
				}

			}

			map.put("curr", cc2);

			map.put("max1", max1);



			//获取充值金额

			Double  czje =   tabRechangeLogService.getXjcz(parame);

			Double   max2 = 0D;



			for (TabRechargeJiangli tabRechargeJiangli : tabRechargeJianglis) {


				max2 =   tabRechargeJiangli.getXjrs().doubleValue() ;
				if (tabRechargeJiangli.getXjrs() >czje.longValue()) {

					break;
				}else {

					TabRechargeJiangliLog tabYaoqingJiangliLog  = new TabRechargeJiangliLog();

					tabYaoqingJiangliLog.setUserid(userId);

					tabYaoqingJiangliLog.setJlbh(tabRechargeJiangli.getJlbh());

					Long  ccN  =      tabRechargeJiangliLogService.findCount(tabYaoqingJiangliLog);

					if(ccN ==0) {

						tabYaoqingJiangliLog.setXjrs(tabRechargeJiangli.getXjrs());
						tabYaoqingJiangliLog.setJlje(tabRechargeJiangli.getJlje());
						tabYaoqingJiangliLog.setCreatetime(getjndDate(new Date()));
						tabYaoqingJiangliLog.setRowid(userId + tabRechargeJiangli.getJlbh());

						tabYaoqingJiangliLog.setIsNewRecord(true);

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
						this.dd(tabJiesuanLog,jedis);
						//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
						//getuservip(tabJiesuanLog.getUserid());


					}

				}

			}


			map.put("czje", czje);
			map.put("max2", max2);





		}

		jedis.quit();
		return  map;

	}

	@RequestMapping(value = "tabOrdersListData")
	@ResponseBody
	public Page<TabOrders> tabOrdersListData(TabOrders tabOrders, HttpServletRequest request, HttpServletResponse response) {



		if (tabOrders.getCreatetime_lte() != null) {
			tabOrders.setCreatetime_lte(DateUtils.getOfDayLast(tabOrders.getCreatetime_lte()));

		}

		if (tabOrders.getCreatetime1_lte() != null) {
			tabOrders.setCreatetime1_lte(DateUtils.getOfDayLast(tabOrders.getCreatetime1_lte()));

		}
		String token = (String) request.getSession().getAttribute("token");

		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		map.put("data", tabYuangongData.getQuanxian());

		String acc = tabYuangongData.getQtzh();

		tabOrders.setShangjilink(acc);



		tabOrders.setPage(new Page<>(request, response));
		Page<TabOrders> page = tabOrdersService.findPage(tabOrders);
		return page;
	}

	//tabOrderDatalist2



	@Autowired
	private  TabYaoqingJiangliLogService  tabYaoqingJiangliLogService;

	@Autowired
	private  TabYaoqingJiangliService tabYaoqingJiangliService ;


	@Autowired
	private  TabRechargeJiangliService  tabRechargeJiangliService ;


	@Autowired
	private TabRechargeJiangliLogService  tabRechargeJiangliLogService   ;


	@Autowired
	private  TabOrdersService  tabOrdersService ;
	//tabYaoqingJiangliLogList

	@RequestMapping(value = {"tabYaoqingJiangliLogList", ""})
	public String tabYaoqingJiangliLogList(HttpServletRequest request,TabYaoqingJiangliLog tabYaoqingJiangliLog, Model model) {
		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}

		model.addAttribute("tabYaoqingJiangliLog", tabYaoqingJiangliLog);
		return "modules/daili/tabYaoqingJiangliLogList";
	}


	@RequestMapping(value = {"tabRechargeJiangliLogList", ""})
	public String list(HttpServletRequest request ,TabRechargeJiangliLog tabRechargeJiangliLog, Model model) {
		String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}

		model.addAttribute("tabRechargeJiangliLog", tabRechargeJiangliLog);
		return "modules/daili/tabRechargeJiangliLogList";
	}
	//tabRechargeJiangliLogList
	//tabFacebook


	@RequestMapping(value = {"tabFacebookList", ""})
	public String list(TabFacebook tabFacebook, Model model) {
		model.addAttribute("tabFacebook", tabFacebook);
		return "modules/daili/tabFacebookList";
	}
	//tabYaoqingJiangliHuodongLogList
	@RequestMapping(value = {"tabYaoqingJiangliHuodongLogList", ""})
	public String tabYaoqingJiangliHuodongLogList(TabYaoqingJiangliHuodongLog  tabFacebook, Model model) {
		model.addAttribute("tabFacebook", tabFacebook);
		return "modules/daili/tabYaoqingJiangliHuodongLogList";
	}

	private static final String UNKNOWN = "unknown";
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

	public TabAutoConfig getAutoConfig(){
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String autoConfigStr = jedis.get(AutoConfig);
		TabAutoConfig tabAutoConfig = new TabAutoConfig();
		if(StringUtils.isBlank(autoConfigStr)){
			List<TabAutoConfig> list = tabAutoConfigService.findList(new TabAutoConfig());
			tabAutoConfig = list.get(0);
			jedis.set(AutoConfig, JSON.toJSONString(list.get(0)));
		}else{
			tabAutoConfig = JSON.parseObject(autoConfigStr, new TypeReference<TabAutoConfig>(){});
		}
		jedis.quit();
		return tabAutoConfig;
	}
}