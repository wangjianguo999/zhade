package com.jeesite.modules.api;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.tab.entity.TabFenxiaoConfig;
import com.jeesite.modules.tab.entity.TabJiesuanLog;
import com.jeesite.modules.tab.entity.TabOrderData;
import com.jeesite.modules.tab.entity.TabOrders;
import com.jeesite.modules.tab.entity.TabTaskjJob;
import com.jeesite.modules.tab.entity.TabTongjiDays;
import com.jeesite.modules.tab.entity.TabTyjJiesuan;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.service.TabFenxiaoConfigService;
import com.jeesite.modules.tab.service.TabJiesuanLogService;
import com.jeesite.modules.tab.service.TabLicaiDataService;
import com.jeesite.modules.tab.service.TabOrderDataService;
import com.jeesite.modules.tab.service.TabOrdersService;
import com.jeesite.modules.tab.service.TabRechangeLogService;
import com.jeesite.modules.tab.service.TabTaskjJobService;
import com.jeesite.modules.tab.service.TabTixianLogService;
import com.jeesite.modules.tab.service.TabTongjiDaysService;
import com.jeesite.modules.tab.service.TabTyjJiesuanService;
import com.jeesite.modules.tab.service.TabUserBankService;
import com.jeesite.modules.tab.service.TabUserDataService;
import com.jeesite.modules.tab.service.TabYuangongDataService;
import com.jeesite.modules.view.service.ViewShouchongService;
import redis.clients.jedis.Jedis;

@Component
@EnableScheduling
public class TaskJob {
	private String v = "1" ;

	@Autowired
	private TabUserBankService tabUserBankService ;

	private static String JIESUAN_KEY = "JIESUAN:queue";
	private static String JIESUAN_KEY1 = "JIESUAN:queue:1";
	private static String JIESUAN_KEY2 = "JIESUAN:queue:2";
	private static String JIESUAN_KEY3 = "JIESUAN:queue:3";
	//	@Scheduled(cron = "0/30 * * * * ?")
	public void jisuan2() {
		System.err.println(DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		String line = "";

		//checkVip
		//getKeFuDh
		//activeHomeDetail
		//changeOrderData
		//orderDetail
		//savePingJiaData
		//listUserAddress
		//saveUserDetail3
		//piLiangOrder
		//getMyProductData
		//getOrderJisuan
		//listDataTabVipConfig
		//getUserDetail
		//getSubUsers
		//getShouru2
		//getTotalSubUsers
		//loginPostBody
		//getOrderList
		//changePasswordData2
		//saveFaceBooks
		//getZhiChuList
		//getShouRuList
		//saveUserAddress
		//listLiCai
		//getLiCaiData
		//saveLiCaiData
		//listLiCaiDetail
		//listDataTabRechangeLog
		//rechangeData
		//activeHome
		//getUserBanks
		//getTiXian
		//listDataTabSiderData
		//listDataTabVipConfig
		//getSubUserRecharge
		//getUserTyj
		//getShouru
		//getUserYe
		//getJinDuTiao
		//saveBankData
		//listTongDao
		//getChouJingData
		//getHuoDongJiangLi
		//getContents
		//listHuoDongJiangli
		//listDataWithDraw
		//tixianPostData
		//getUserBanks2
		//registerPost
		//

//
//		String yrl = "https://pay.aladdin-e-commerce.com/js2/shoudong" + line;
//		GoldpaysUtil.hanYuanUtils(yrl, "", new HashedMap());
		//jedis.auth("hask071bend");
		try {

			if(v.equals("2")) {
				List<TabOrderData> tabOrderDatas    =  tabOrderDataService.findList2();

				Map<String, TabOrderData>  map =    new HashMap<String, TabOrderData>() ;
				for (TabOrderData tabOrderData : tabOrderDatas) {
					map.put(tabOrderData.getOrderid(), tabOrderData) ;
				}
				Set<String>  keys  =    map.keySet();
				for (String string : keys) {
					TabOrderData oo   = new TabOrderData();
					oo.setOrderid(string);
					oo.setStatus1("1");
					Long  cc  =   tabOrderDataService.findCount(oo   );
					if(cc  ==0) {
						// 开始更新订单
						Map<String, String>  parame = new HashMap<String, String>();

						parame.put("orderid", string);

						tabOrderDataService.updateJiesuan(parame);

						TabOrderData  tabOrderData      =   map.get(string);
						TabOrders taborders  = new TabOrders();

						taborders.setOrderid(tabOrderData.getOrderid());
						List<TabOrders> tabOrders  =  tabOrdersService.findList(taborders  );

						taborders = tabOrders.get(0);
						taborders.setPaycount(taborders.getOrdercont());
						taborders.setStatus1("4");
						tabOrdersService.save(taborders);
						//更新订单

						//获取总的 金额
						Double m  =   tabOrderDataService.getBenJin(parame);

						Double m1 =  tabOrderDataService.getYOujin2(parame);

						System.out.println(m +",," + m1);

						String userId =  tabOrderData.getUserid();

						TabUserData tabUserData    =  tabUserDataService.get(userId);

						TabJiesuanLog  tabJiesuanLog    =  new TabJiesuanLog() ;
						tabJiesuanLog.setUserid(userId);
						tabJiesuanLog.setRowid(userId +  tabOrderData.getOrderid() +"yj");
						tabJiesuanLog.setType("Retour à la Commission");
						tabJiesuanLog.setCmd("Commission");
						tabJiesuanLog.setCreatetime(ApiController.getjndDate(new Date()));
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

						//开始给一级 分销
						TabFenxiaoConfig  tabFenxiaoConfig   =  tabFenxiaoConfigService.get("1");
						if (!StringUtils.isBlank(tabUserData.getParentid())) {
							//一级数据
							TabUserData   useTabUserDataLeval1 =  tabUserDataService.get(tabUserData.getParentid()) ;
							if (useTabUserDataLeval1 != null  ) {
								tabJiesuanLog    =  new TabJiesuanLog() ;
								tabJiesuanLog.setUserid(useTabUserDataLeval1.getRowid());
								tabJiesuanLog.setRowid(useTabUserDataLeval1.getRowid() +  tabOrderData.getOrderid() +"yj");
								tabJiesuanLog.setType("Prime Commission de niveau 1");
								tabJiesuanLog.setCmd("Commission");
								tabJiesuanLog.setCreatetime(ApiController.getjndDate(new Date()));
								tabJiesuanLog.setAmont(m1 *  tabFenxiaoConfig.getLeval1()/100.0);
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
							//二级数据
							TabUserData   useTabUserDataLeval2 =  tabUserDataService.get(tabUserData.getParentid1()) ;
							if (useTabUserDataLeval2 != null  ) {

								tabJiesuanLog    =  new TabJiesuanLog() ;
								tabJiesuanLog.setUserid(useTabUserDataLeval2.getRowid());
								tabJiesuanLog.setRowid(useTabUserDataLeval2.getRowid() +  tabOrderData.getOrderid() +"yj");
								tabJiesuanLog.setType("Prime Commission de niveau 2");
								tabJiesuanLog.setCmd("Commission");
								tabJiesuanLog.setCreatetime(ApiController.getjndDate(new Date()));
								tabJiesuanLog.setAmont(m1 *  tabFenxiaoConfig.getLeval2()/100.0);
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
							//二级数据
							TabUserData   useTabUserDataLeval3 =  tabUserDataService.get(tabUserData.getParenti3()) ;
							if (useTabUserDataLeval3 != null  ) {
								tabJiesuanLog    =  new TabJiesuanLog() ;
								tabJiesuanLog.setUserid(useTabUserDataLeval3.getRowid());
								tabJiesuanLog.setRowid(useTabUserDataLeval3.getRowid() +  tabOrderData.getOrderid() +"yj");
								tabJiesuanLog.setType("Prime Commission de niveau 3");
								tabJiesuanLog.setCmd("Commission");
								tabJiesuanLog.setCreatetime(ApiController.getjndDate(new Date()));
								tabJiesuanLog.setAmont(m1 *  tabFenxiaoConfig.getLeval3()/100.0);
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

						if(tabOrderData.getIstiyan()!= null    &&   tabOrderData.getIstiyan().equals("1")) {
							//这个是 体验订单
							TabTyjJiesuan obj22 = new TabTyjJiesuan();
							obj22.setRowid(tabOrderData.getOrderid() +  tabOrderData.getUserid());
							obj22.setUserid(tabOrderData.getUserid());
							obj22.setAmont(m);
							obj22.setCreatetime(ApiController. getjndDate(new Date()));
							obj22.setIsNewRecord(true);
							tabTyjJiesuanService.save(obj22 );
						}else {
							//不是体验订单 需要返回本金
							System.out.println("非体坛订单");
							tabJiesuanLog    =  new TabJiesuanLog() ;
							tabJiesuanLog.setUserid(userId);
							tabJiesuanLog.setRowid(userId +  tabOrderData.getOrderid() +"bj");
							tabJiesuanLog.setType("Remboursement du principal\n"
									+ "\n"
									+ "");
							tabJiesuanLog.setCmd("capitale");
							tabJiesuanLog.setCreatetime(ApiController.getjndDate(new Date()));
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

				TabTaskjJob tasks  = new TabTaskjJob();
				tasks.setStatus1("1");
				List<TabTaskjJob> tabTaskjJobs  =  tabTaskjJobService.findList(tasks  );

				for (TabTaskjJob tabTaskjJob : tabTaskjJobs) {

					//开始自己算

					String string  =  tabTaskjJob.getShangjis();

					String[] splits =  string.split(",");

					for (String ss :  splits) {

						if (!StringUtils.isBlank(ss)) {

							TabUserData tabUserData  =  tabUserDataService.get(ss) ;

							if (tabUserData!= null ) {
								if(tabUserData.getShangji2s() ==null ) {
									tabUserData.setShangji2s("");
								}
								tabUserData.setShangji2s(tabUserData.getShangji2s()+","+tabTaskjJob.getUserid()+",");
								tabUserDataService.save(tabUserData);
							}
						}
					}
					tabTaskjJob.setStatus1("2");
					tabTaskjJobService.save(tabTaskjJob);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> parame =  new  HashMap<String, String>();
		parame.put("date", DateUtils.formatDate(ApiController.getjndDate(new Date()), "yyyy-MM-dd HH:mm:ss")) ;
	}

	@Autowired
	private TabRechangeLogService  tabRechangeLogService;
	@Autowired
	private TabTixianLogService  tabTixianLogService;
	@Autowired
	private TabLicaiDataService  tabLicaiDataService;
	public static void main(String[] args) throws Exception, MalformedURLException, IOException {

	}

	@Autowired
	private TabTaskjJobService tabTaskjJobService ;

	@Autowired
	private TabOrderDataService tabOrderDataService ;

	@Autowired
	private   TabFenxiaoConfigService  tabFenxiaoConfigService ;

	@Autowired
	private  TabUserDataService tabUserDataService ;

	@Autowired
	private  TabJiesuanLogService  tabJiesuanLogService ;

	@Autowired
	private TabYuangongDataService  tabYuangongDataService;

	@Autowired
	private TabOrdersService  tabOrdersService;

	//@Scheduled(cron = "0 05 7 * * ?")
	public void initShangjiaZ() throws Throwable {
	}
	private Jedis jedis =  new Jedis("41.72.149.115", 6379);
	//@Scheduled(cron = "0 30 11 * * ?")
	public void jisuan31() {

		if(v.equals("1")) {
			List<TabUserData>  tabUserDatas  = new ArrayList<>();
			TabUserData obj = new TabUserData();
			obj.setRowid("-1");
			obj.setUsertype("1");
			tabUserDatas.add(0,obj );

			TabUserData param = new TabUserData();
			param.setUsertype("1");
			List<TabUserData>  tabUserDatas1  =   tabUserDataService.findList(param);
			tabUserDatas.addAll(tabUserDatas1);

			param.setUsertype("2");
			List<TabUserData>  tabUserDatas2  =   tabUserDataService.findList(param);
			tabUserDatas.addAll(tabUserDatas2);

			for (TabUserData tabUserData : tabUserDatas) {
				try {
					if(tabUserData.getUsertype().equals("1") || tabUserData.getUsertype().equals("2")) {

						Calendar calendar  = Calendar.getInstance();
						calendar.setTime(ApiController.getjndDate(new Date()));

						calendar.add(Calendar.DAY_OF_MONTH ,-1);
						Date  min = 	  calendar.getTime();

						String  userId =  tabUserData.getRowid() ;

						System.out.println(DateUtils.formatDate(min));


						Date   date1 =  min ;

						Date  date2 =  DateUtils.getOfDayLast(date1);


						Map<String, String>  parame =  new  HashMap<String, String>();
						if(!userId.equals("-1")) {

							parame.put("name2", "%" + userId + "%");
						}
						parame.put("date1", DateUtils.formatDate(DateUtils.getOfDayFirst(date1),"yyyy-MM-dd HH:mm:ss"));
						parame.put("date2", DateUtils.formatDate(DateUtils.getOfDayLast(date1),"yyyy-MM-dd HH:mm:ss"));

						Double czje = tabRechangeLogService.getTotalMoney(parame);


						Long cc = tabRechangeLogService.getTotalRenshu(parame);


						Double scje = viewShouchongService.getTotalMoney(parame);


						Long scrs = viewShouchongService.getTotalRenshu(parame);


						Double txje = tabTixianLogService.getTotalMoney(parame);

						Long txcs = tabTixianLogService.getTotalRenshu(parame);

						Long zcrs =   tabUserDataService.getRenShu(parame);

						Double  txc  =   czje - txje ;
						Long   zdrs =  tabOrdersService.getZdrs(parame);

						TabTongjiDays obj11    = new  TabTongjiDays();

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

						tabTongjiDaysService.save(obj11  );
					}
				}catch (Exception e){
					System.out.println("每日报表统计出错："+tabUserData.getUserid());
					e.printStackTrace();
					continue;
				}

			}
		}
	}

	public void dd(TabJiesuanLog tabJiesuanLog,Jedis jedis){
		jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
		/*Integer d = Integer.parseInt(tabJiesuanLog.getRowid().substring(0,1));
		if(d == 0){
			jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
		}else{
			jedis.lpush(JIESUAN_KEY1,JSON.toJSONString(tabJiesuanLog));
		}*/

		/*if(d ==0 || d ==1 || d ==2 ){
			jedis.lpush(JIESUAN_KEY,JSON.toJSONString(tabJiesuanLog));
		}else if(d == 3 || d == 4 || d == 5){
			jedis.lpush(JIESUAN_KEY1,JSON.toJSONString(tabJiesuanLog));
		}else{
			jedis.lpush(JIESUAN_KEY2,JSON.toJSONString(tabJiesuanLog));
		}*/

	}

	@Autowired
	private  TabTongjiDaysService tabTongjiDaysService ;

	@Autowired
	private  ViewShouchongService viewShouchongService ;

	@Autowired
	private TabTyjJiesuanService  tabTyjJiesuanService;
}