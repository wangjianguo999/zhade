package com.jeesite.modules.tab.web;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.jeesite.modules.tab.entity.*;
import com.jeesite.modules.tab.service.*;
import com.twelvemonkeys.util.LinkedMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
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
 * tab_tixian_logController
 * @author 1
 * @version 2021-12-17
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabTixianLog")
@Slf4j
public class TabTixianLogController extends BaseController {
	private static String JIESUAN_KEY = "JIESUAN:queue";
	@Autowired
	private TabTixianLogService tabTixianLogService;
	private static String TOKEN = "TOKEN:";

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabTixianLog get(String rowid, boolean isNewRecord) {
		return tabTixianLogService.get(rowid, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabTixianLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabTixianLog tabTixianLog, Model model) {
		List<User> users   =  userService.findList(new User());
		model.addAttribute("users", users);


		TabTongdao onbj = new TabTongdao();
		onbj.setDaifu("2");
		List<TabTongdao > tongdaos  =   tabTongdaoService.findList(onbj );

		TabTongdao tabTongdao  = new TabTongdao();
		tabTongdao.setTongdaoName("ERC20-USDT");

		tongdaos.add(tabTongdao) ;
		TabTongdao tabTongdao2  = new TabTongdao();

		tabTongdao2.setTongdaoName("TRC20-USDT");

		tongdaos.add(tabTongdao2) ;

		model.addAttribute("tongdaos", tongdaos);

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

		model.addAttribute("tabTixianLog", tabTixianLog);
		model.addAttribute("acc", acc);
		return "modules/tab/tabTixianLogList";
	}
	@Autowired
	private UserService userService;
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabTixianLog:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabTixianLog> listData(TabTixianLog tabTixianLog, HttpServletRequest request, HttpServletResponse response) {
		Date max  =  tabTixianLog.getCreatetime_lte();
		if(max != null) {
			String  s = DateUtils.formatDate(max,"yyyy-MM-dd HH:mm")  ;

			if(s.endsWith("00:00")) {
				tabTixianLog.setCreatetime_lte(DateUtils.getOfDayLast(tabTixianLog.getCreatetime_lte()));
			}
		}
		max  =  tabTixianLog.getUpdatetime_lte() ;
		if(max != null) {
			String  s =      DateUtils.formatDate(max,"yyyy-MM-dd HH:mm")  ;

			if(s.endsWith("00:00")) {
				tabTixianLog.setUpdatetime_lte(DateUtils.getOfDayLast(tabTixianLog.getUpdatetime_lte()));
			}
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
			tabTixianLog.setShangjilink(acc);
		}else {
			String shangjilink =  tabTixianLog.getShangjilink();
			if(!StringUtils.isBlank(shangjilink)) {
				TabUserData tabUserData  = new TabUserData();
				tabUserData.setSysuserid(shangjilink); ;
				List<TabUserData> tabUserDatas  =  tabUserDataService.findList(tabUserData);
				if (tabUserDatas.size() >  0 ) {
					tabTixianLog.setShangjilink(tabUserDatas.get(0).getRowid());

				}
			}

		}

		tabTixianLog.setPage(new Page<>(request, response));
		Page<TabTixianLog> page = tabTixianLogService.findPage(tabTixianLog);

		List<TabTixianLog> tabTixianLogs   =  page.getList();
		for (TabTixianLog tabTixianLog2 : tabTixianLogs) {

			String cardType =   tabTixianLog2.getCardtype();

			TabUserBank  tabUserBank  = 	tabUserBankService.get(cardType);

			if(tabUserBank != null) {
				tabTixianLog2.setCardtype(tabUserBank.getFirstname() );

				tabTixianLog2.setQbmc(tabUserBank.getLastname());
			}else {
				tabTixianLog2.setCardtype("账号已删除");
			}
			if (!acc.equals("admin") &&  tabTixianLog2.getStatus1().equals("12") ) {
				tabTixianLog2.setStatus1("13");
			}

			TabUserData tabUserData = tabUserDataService.get(tabTixianLog2.getUserid());
			tabTixianLog2.setXdzt1(tabUserData.getXdzt1());

		}
		page.setList(tabTixianLogs);

		return page;
	}

	@Autowired
	private  TabUserDataService tabUserDataService ;

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabTixianLog:view")
	@RequestMapping(value = "form")
	public String form(TabTixianLog tabTixianLog, Model model) {
		model.addAttribute("tabTixianLog", tabTixianLog);
		return "modules/tab/tabTixianLogForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabTixianLog:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabTixianLog tabTixianLog) {
		tabTixianLogService.save(tabTixianLog);
		return renderResult(Global.TRUE, text("保存tab_tixian_log成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabTixianLog:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabTixianLog tabTixianLog) {
		tabTixianLogService.delete(tabTixianLog);
		return renderResult(Global.TRUE, text("删除tab_tixian_log成功！"));
	}


	@PostMapping(value = "changeData9")
	@ResponseBody
	public String changeData9(HttpServletRequest httpServletRequest ) {
		String  rowids =  httpServletRequest.getParameter("rowids");
		String id =  UserUtils.getLoginInfo().getId();
		User user   =  userService.get(id);
		String[] s  =  rowids.split(",");
		for (String string : s) {
			TabTixianLog   tabTixianLog    =  tabTixianLogService.get(string);
			String  acc= user.getLoginCode();
			if (tabTixianLog.getStatus1().equals("1") ||   tabTixianLog.getStatus1().equals("12") || tabTixianLog.getStatus1().equals("7")) {
				if (acc.equals("admin")) {
					TabUserData tabUserData = tabUserDataService.get(tabTixianLog.getUserid());
					if(tabUserData.getCurrentmoney() < 0){
						continue;
					}
					Map<String, String> parame = new HashMap<>();
					String appid = "b7b31605-7172-4acb-957c-15d401fae6d9";
					String appset = "d18c4c73-3565-4aab-9431-1587255dd230";
					parame.put("appid", appid);
					String orderMoney =  tabTixianLog.getDzje() +"" ;
					String account =   tabTixianLog.getType();
					String  cardType     =    tabTixianLog.getCardtype();
					TabUserBank tabUserBank   =  tabUserBankService.get(cardType) ;
					String customName =     tabUserBank.getFirstname() ;
					String  customMobile   =   tabUserBank.getLastname() ;
					parame.put("userid",tabTixianLog.getUserid());
					parame.put("orderId", tabTixianLog.getOrderid());
					parame.put("orderMoney", orderMoney);
					parame.put("account", account);
					parame.put("customName", customName);
					parame.put("customMobile", customMobile);
					parame.put("notifyUrl", "http://41.72.149.115:8081/api/zfCallData2");
					//parame.put("notifyUrl", "http://127.0.0.1/js/zfCallData2");
					//String tempSign = GoldpaysUtil.getSign2(parame, appset);
					//parame.put("sign", tempSign);
					//System.out.println(" sign " + tempSign);

					String s1 = GoldpaysUtil.hanYuanUtils("http://41.72.149.115:8081/metaPay/api/createLouisDaiFu", "", parame);
					//String s1 = GoldpaysUtil.hanYuanUtils("https://e-creatoerzw.com/metaPay/api/createLouisDaiFu", "", parame);
					System.out.println("代付订单发送到支付后台信息订单号："+tabTixianLog.getOrderid() + " 返回值："+s1);
					org.json.JSONObject object2 = new org.json.JSONObject(s1);
					s1 = object2.getString("msg");
					if(s1.equals("SUCCESS")){
						tabTixianLog.setZftd(tabTixianLog.getType());
						tabTixianLog.setStatus1("4");
						tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
						tabTixianLogService.save(tabTixianLog);
					}
				}else {
					tabTixianLog.setStatus1("12");
					tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
					tabTixianLogService.save(tabTixianLog);
				}
			}
		}
		System.out.println(id);
		return  "";
	}

	@PostMapping(value = "paid")
	@ResponseBody
	public String paid(HttpServletRequest httpServletRequest ) {
		String  rowids =  httpServletRequest.getParameter("rowids");

		String id =  UserUtils.getLoginInfo().getId();
		User user   =  userService.get(id);
		String[] s  =  rowids.split(",");

		for (String string : s) {
			TabTixianLog   tabTixianLog    =  tabTixianLogService.get(string);
			String  acc= user.getLoginCode();
			if (tabTixianLog.getStatus1().equals("1") ||   tabTixianLog.getStatus1().equals("12")) {
				if (acc.equals("admin")) {

					String  cardType     =    tabTixianLog.getCardtype();
					TabUserBank tabUserBank   =  tabUserBankService.get(cardType) ;

					JSONObject parame = new JSONObject();
					parame.put("access_key","nEiYKpdbUfwm9Ie5Ge56KImgTOt5ztsZ");
					parame.put("wallet_sn","vZBCNswVNEtgyeQiAfqHgEWzMWC2NbM6TVLykCXTzMuR6UBmW4jXOV7fQnnccf5a");
					parame.put("method","order.create");
					parame.put("out_order_sn",tabTixianLog.getOrderid());
					parame.put("type","mobile");
					parame.put("bank_no",tabUserBank.getFirstname());
					parame.put("bank_account","225"+tabUserBank.getFirstname());
					parame.put("amount",String.valueOf(tabTixianLog.getDzje()));
					parame.put("name",tabUserBank.getLastname());
					parame.put("notify_url","http://41.72.149.115:8081/api/notify");
					String tempSign = GoldpaysUtil.getJSONTOSign2(parame, "DR646NqiBvGPxE4Bugbp9kB6ch64v8hh");
					parame.put("sign",tempSign.toUpperCase());
					String str = "";
					try {
						str = GoldpaysUtil.send("https://www.paynow.ltd/api/payout/index",parame,"utf-8");
					}catch (Exception e){

					}
					System.out.println("提现审核三方接口返回值："+str);
					com.alibaba.fastjson.JSONObject jsonpObject = JSON.parseObject(str);
					if(jsonpObject.getInteger("code") == 1 && "success".equals(jsonpObject.getString("msg"))){
						tabTixianLog.setZftd(tabTixianLog.getType());
						tabTixianLog.setStatus1("5");
						tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
						tabTixianLogService.save(tabTixianLog);
					}
				}else {
					tabTixianLog.setStatus1("12");
					tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
					tabTixianLogService.save(tabTixianLog);
				}
			}
		}
		return  "";
	}

	private static final String cinetPayPaidNotify = "http://41.72.149.115:8081/api/cinetPayPaidNotify";

	@PostMapping(value = "cinetPayPaid")
	@ResponseBody
	public String cinetPayPaid(HttpServletRequest httpServletRequest ) {
		String  rowids =  httpServletRequest.getParameter("rowids");
		String id =  UserUtils.getLoginInfo().getId();
		User user   =  userService.get(id);
		String[] s  =  rowids.split(",");
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
			StatusLine status3 = response3.getStatusLine();
			int state3 = status3.getStatusCode();
			if (state3 != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity responseEntity3 = response3.getEntity();
			String jsonString3 = EntityUtils.toString(responseEntity3);
			//System.out.println("cinetPayPaid代付获取token："+jsonString3);
			com.alibaba.fastjson.JSONObject jsonObject3 = com.alibaba.fastjson.JSONObject.parseObject(jsonString3);
			if(jsonObject3.getInteger("code") != 0 || !jsonObject3.getString("message").equals("OPERATION_SUCCES")){
				return null;
			}
			token = jsonObject3.getJSONObject("data").getString("token");

		}catch (Exception e){

		}
		for (String string : s) {
			TabTixianLog   tabTixianLog    =  tabTixianLogService.get(string);
			String  acc= user.getLoginCode();
			if (tabTixianLog.getStatus1().equals("1") ||   tabTixianLog.getStatus1().equals("12")) {
				if (acc.equals("admin")) {
					String  cardType     =    tabTixianLog.getCardtype();
					TabUserBank tabUserBank   =  tabUserBankService.get(cardType) ;

					try {
						com.alibaba.fastjson.JSONObject jsonObject22 = new com.alibaba.fastjson.JSONObject();
						jsonObject22.put("prefix","225");
						jsonObject22.put("phone",tabUserBank.getFirstname());
						jsonObject22.put("name",tabUserBank.getLastname());
						jsonObject22.put("surname",tabUserBank.getLastname());
						jsonObject22.put("email","email@example.com");
						/*String str1 = "data=[{\"prefix\":\"225\",\"phone\":\"0501223554\",\"name\":\"ceshic\",\"surname\":\"aaaa\",\"email\":\"email@example.com\"}]";
						String newStr = str1.replaceFirst("0501223554",tabUserBank.getFirstname()).replaceFirst("ceshic",tabUserBank.getLastname()).replaceFirst("aaaa",tabUserBank.getLastname());
						System.out.println("urlStr1："+newStr);
						String str = "data=[{\"prefix\":\"225\",\"phone\":\"0501223554\",\"name\":\"ceshic\",\"surname\":\"ceshic\",\"email\":\"email@example.com\"}]";
						System.out.println("urlStr2："+str);
						System.out.println(newStr.equals(str));*/
						HttpPost httpPost = new HttpPost("https://client.cinetpay.com/v1/transfer/contact?token="+token);// 创建httpPost
						httpPost.setHeader("Accept", "application/json");
						httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
						//StringEntity entity = new StringEntity(newStr, charSet);
						StringEntity entity = new StringEntity("data="+jsonObject22.toJSONString(), charSet);
						httpPost.setEntity(entity);
						CloseableHttpResponse response1 = null;
						CloseableHttpClient httpclient = HttpClients.createDefault();
						response1 = httpclient.execute(httpPost);

						HttpEntity responseEntity = response1.getEntity();
						String jsonString = EntityUtils.toString(responseEntity);
						System.out.println("cinetpay添加联系人返回参数:"+jsonString);
						JSONObject jsonObject = new JSONObject(jsonString);
						if(jsonObject.getInt("code") !=0 || !jsonObject.getString("message").equals("OPERATION_SUCCES")){
							continue;
						}

						Integer amount = tabTixianLog.getDzje().intValue();
						if(amount%5 != 0){
							amount = amount + (5 - amount%5);
						}

						com.alibaba.fastjson.JSONObject jsonObject33 = new com.alibaba.fastjson.JSONObject();
						jsonObject33.put("prefix","225");
						jsonObject33.put("phone",tabUserBank.getFirstname());
						jsonObject33.put("amount",amount.toString());
						jsonObject33.put("notify_url",cinetPayPaidNotify);
						jsonObject33.put("client_transaction_id",tabTixianLog.getOrderid());

						//String str2 = "data=[{ \"prefix\": \"225\", \"phone\": \"aaaa\",  \"amount\": \"bbbb\", \"notify_url\": \"dddd\", \"client_transaction_id\": \"cccc\" }]";
						//String newStr2 = str2.replaceFirst("aaaa",tabUserBank.getFirstname()).replaceFirst("bbbb",amount.toString()).replaceFirst("cccc",tabTixianLog.getOrderid()).replaceFirst("dddd",cinetPayPaidNotify);
						HttpPost httpPost1 = new HttpPost("https://client.cinetpay.com/v1/transfer/money/send/contact?token="+token+"&transaction_id="+tabTixianLog.getOrderid());// 创建httpPost
						httpPost1.setHeader("Accept", "application/json");
						httpPost1.setHeader("Content-Type", "application/x-www-form-urlencoded");
						StringEntity entity1 = new StringEntity("data="+jsonObject33.toJSONString(), charSet);
						httpPost1.setEntity(entity1);
						CloseableHttpResponse response2 = null;
						CloseableHttpClient httpclient2 = HttpClients.createDefault();
						response2 = httpclient2.execute(httpPost1);

						HttpEntity responseEntity2 = response2.getEntity();
						String jsonString2 = EntityUtils.toString(responseEntity2);
						System.out.println("cinetpay支付返回数据："+jsonString2);
						JSONObject jsonObject2 = new JSONObject(jsonString2);

						if(jsonObject2.getInt("code") == 0 && "OPERATION_SUCCES".equals(jsonObject2.getString("message"))){
							tabTixianLog.setZftd(tabTixianLog.getType());
							tabTixianLog.setStatus1("8");
							tabTixianLog.setDzje(amount.doubleValue());
							tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
							tabTixianLogService.save(tabTixianLog);
						}

					}catch (Exception e){
						e.printStackTrace();
					}

				}else {
					tabTixianLog.setStatus1("12");
					tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
					tabTixianLogService.save(tabTixianLog);
				}
			}
		}
		return  "";
	}

	@PostMapping(value = "changeData")
	@ResponseBody
	public String changeData(HttpServletRequest httpServletRequest ) {

		//设置成功

		String rowids =  httpServletRequest.getParameter("rowids");
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String[] s =   rowids.split(",");

		for (String string : s) {

			TabTixianLog tabTixianLog  =  tabTixianLogService.get(string);


			TabUserData userData   =  tabUserDataService.get(tabTixianLog.getUserid() ) ;



			String status1 =  tabTixianLog.getStatus1();

			if (status1.equals("1") || status1.equals("12") || status1.equals("4")   || status1.equals("7")  ) {

				tabTixianLog.setStatus1("2");
				tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
				tabTixianLogService.save(tabTixianLog);
			}else if(status1.equals("3")) {
				tabTixianLog.setStatus1("2");
				tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
				tabTixianLogService.save(tabTixianLog);


				TabJiesuanLog tabJiesuanLog     =  new TabJiesuanLog();

				tabJiesuanLog.setUserid(userData.getRowid());
				tabJiesuanLog.setAmont(Double.valueOf(tabTixianLog.getMoney()) *  -1);
				tabJiesuanLog.setRowid(UUID.randomUUID().toString());
				tabJiesuanLog.setCmd("Withdraw");
				tabJiesuanLog.setType("提现成功");
				tabJiesuanLog.setMsg("Withdraw successfully");
				tabJiesuanLog.setCreatetime( ApiController. getjndDate(new Date()));
				tabJiesuanLog.setIsNewRecord(true);
				tabJiesuanLog.setParentid1(userData.getParentid());
				tabJiesuanLog.setParentid2(userData.getParentid1());
				tabJiesuanLog.setParentid3(userData.getParenti3());
				tabJiesuanLog.setShangjilink(userData.getShangjilink());
				tabJiesuanLog.setStatus1(tabTixianLog.getOrderid());

				tabJiesuanLogService.save(tabJiesuanLog);

				jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
				//getuservip(tabJiesuanLog.getUserid());
			}
		}
		jedis.quit();
		return renderResult(Global.TRUE, text("保存成功！"));
	}



	@PostMapping(value = "changeData2")
	@ResponseBody
	public String changeData2(HttpServletRequest httpServletRequest ) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String rowids =  httpServletRequest.getParameter("rowids");


		String[] s =   rowids.split(",");

		String  id = 	UserUtils.getLoginInfo().getId();


		User user   =  userService.get(id);

		String  acc= user.getLoginCode();
		for (String string : s) {

			TabTixianLog tabTixianLog  =  tabTixianLogService.get(string);

			TabUserData tabUserData    =  tabUserDataService.get(tabTixianLog.getUserid())  ;



			String status1 =  tabTixianLog.getStatus1();
			if(acc.equals("admin")) {
				if (   status1.equals("1") ||  status1.equals("4")  ||  status1.equals("2") ||  status1.equals("12") || status1.equals("7")) {




					TabJiesuanLog tabJiesuanLog     =  new TabJiesuanLog();

					tabJiesuanLog.setUserid(tabUserData.getRowid());
					tabJiesuanLog.setAmont(Double.valueOf(tabTixianLog.getMoney()) );
					tabJiesuanLog.setRowid(UUID.randomUUID().toString());
					tabJiesuanLog.setCmd("Withdraw");
					tabJiesuanLog.setType("提现失败");
					tabJiesuanLog.setMsg("Withdraw failed");
					tabJiesuanLog.setCreatetime( ApiController. getjndDate(new Date()));
					tabJiesuanLog.setIsNewRecord(true);
					tabJiesuanLog.setParentid1(tabUserData.getParentid());
					tabJiesuanLog.setParentid2(tabUserData.getParentid1());
					tabJiesuanLog.setParentid3(tabUserData.getParenti3());
					tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
					tabJiesuanLog.setStatus1(tabTixianLog.getOrderid());

					tabJiesuanLogService.save(tabJiesuanLog);

					jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
					//getuservip(tabJiesuanLog.getUserid());

				}


				tabTixianLog.setStatus1("3");
				tabTixianLog.setNote(tabTixianLog.getNote()+";updatebyUser:"+acc);
				tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
				tabTixianLogService.save(tabTixianLog);
				String orderid =  tabTixianLog.getOrderid() ;

				TabJiesuanLog tabJiesuanLog  = new TabJiesuanLog();
				tabJiesuanLog.setStatus1(orderid);
				List<TabJiesuanLog> tabJiesuanLogs  =  tabJiesuanLogService.findList(tabJiesuanLog);

				for (TabJiesuanLog tabJiesuanLog2 : tabJiesuanLogs) {
					tabJiesuanLog2.setZt("failed ");
					tabJiesuanLogService.save(tabJiesuanLog2);

					//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog2));
					//getuservip(tabJiesuanLog2.getUserid());


				}

			}else {
				if (   status1.equals("1")) {





					TabJiesuanLog tabJiesuanLog     =  new TabJiesuanLog();

					tabJiesuanLog.setUserid(tabUserData.getRowid());
					tabJiesuanLog.setAmont(Double.valueOf(tabTixianLog.getMoney()) );
					tabJiesuanLog.setRowid(UUID.randomUUID().toString());
					tabJiesuanLog.setCmd("Withdraw");
					tabJiesuanLog.setType("提现失败");
					tabJiesuanLog.setCreatetime( ApiController. getjndDate(new Date()));
					tabJiesuanLog.setIsNewRecord(true);
					tabJiesuanLog.setParentid1(tabUserData.getParentid());
					tabJiesuanLog.setParentid2(tabUserData.getParentid1());
					tabJiesuanLog.setParentid3(tabUserData.getParenti3());
					tabJiesuanLog.setShangjilink(tabUserData.getShangjilink());
					tabJiesuanLog.setStatus1(tabTixianLog.getOrderid());

					tabJiesuanLogService.save(tabJiesuanLog);

					jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
					//getuservip(tabJiesuanLog.getUserid());


					tabTixianLog.setNote(tabTixianLog.getNote()+";updatebyUser:"+acc);
					tabTixianLog.setStatus1("3");
					tabTixianLog.setUpdatetime(ApiController.getjndDate(new Date()));
					tabTixianLogService.save(tabTixianLog);
					String orderid =  tabTixianLog.getOrderid() ;
					tabJiesuanLog  = new TabJiesuanLog();
					tabJiesuanLog.setStatus1(orderid);
					List<TabJiesuanLog> tabJiesuanLogs  =  tabJiesuanLogService.findList(tabJiesuanLog);


					for (TabJiesuanLog tabJiesuanLog2 : tabJiesuanLogs) {
						tabJiesuanLog2.setZt("failed ");
						tabJiesuanLogService.save(tabJiesuanLog2);

						//jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog2));
						//getuservip(tabTixianLog.getUserid());
					}


				}
			}
		}

		jedis.quit();
		return renderResult(Global.TRUE, text("保存成功！"));
	}


	@RequestMapping(value = "tongJi")
	@ResponseBody
	public Map<String, Object > tongJi(TabTixianLog tabRechangeLog, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (tabRechangeLog.getCreateDate_lte()!=  null) {
			tabRechangeLog.setCreateDate_lte(DateUtils.getOfDayLast(tabRechangeLog.getCreatetime_lte()));
		}

		if (tabRechangeLog.getUpdatetime_lte() != null) {
			tabRechangeLog.setUpdatetime_lte(DateUtils.getOfDayLast(tabRechangeLog.getUpdatetime_lte()));

		}
		String userid=  tabRechangeLog.getUserid();
		Date min  =  tabRechangeLog.getCreatetime_gte();
		Date max =  tabRechangeLog.getCreatetime_lte();
		Date min1  =  tabRechangeLog.getUpdatetime_gte();
		Date max1 =  tabRechangeLog.getUpdatetime_lte();

		String type =  tabRechangeLog.getType();

		if (!StringUtils.isBlank(type)) {

			map.put("type", type);
		}


		if (!StringUtils.isBlank(userid)) {

			map.put("userid", userid);
		}

		String  id = 	UserUtils.getLoginInfo().getId();


		User user   =  userService.get(id);

		String  acc= user.getLoginCode();


		if (!acc.equals("admin")) {
			tabRechangeLog.setShangjilink(acc);
		}



		String shangjilink =  tabRechangeLog.getShangjilink();
		if(!StringUtils.isBlank(shangjilink)) {


			TabUserData tabUserData  = new TabUserData();
			tabUserData.setSysuserid(shangjilink); ;
			List<TabUserData> tabUserDatas  =  tabUserDataService.findList(tabUserData);
			if (tabUserDatas.size() >  0 ) {
				map.put("shangjis", "%" +  tabUserDatas.get(0).getRowid() +"%");


			}

		}



		if (min != null) {

			map.put("min", DateUtils.formatDate(min, "yyyy-MM-dd HH:mm:ss"));
		}

		if (max != null) {

			map.put("max", DateUtils.formatDate(max, "yyyy-MM-dd HH:mm:ss"));
		}

		if (min1 != null) {

			map.put("min1", DateUtils.formatDate(min1, "yyyy-MM-dd HH:mm:ss"));
		}

		if (max1 != null) {

			map.put("max1", DateUtils.formatDate(max1, "yyyy-MM-dd HH:mm:ss"));
		}


		Double  zczje2=     tabTixianLogService.getCzje2(map);

		Double  zczje =     tabTixianLogService.getCzje(map);
		Long  bs  = tabTixianLogService.getCzbs(map);

		map.put("czje", zczje);
		map.put("bs", bs);
		map.put("zczje2", zczje2);

		return  map ;
	}
	private Jedis jedis =  new Jedis("41.72.149.115", 6379);

	@Autowired
	private TabJiesuanLogService  tabJiesuanLogService;

	@Autowired
	private TabTongdaoService tabTongdaoService ;

	public  String    getUserVip(String  userId )   {
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

	@Autowired
	private TabOrderDataService tabOrderDataService;
	@Autowired
	private TabOrdersService tabOrdersService;
	@Autowired
	private  TabVipConfigService  tabVipConfigService;

	@Autowired
	private TabUserBankService  tabUserBankService ;
}