package com.jeesite.modules.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.tab.entity.*;
import com.jeesite.modules.tab.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.*;

@Component
public class Newjj implements CommandLineRunner {
    private static  String JIESUANERROR = "JIESUAN:error";
    private static  String JIESUAN_KEY = "JIESUAN:queue";
    private static  String JIESUAN_KEY1 = "JIESUAN:queue1";
    private static  String JIESUAN_KEY2 = "JIESUAN:queue2";
    private static  String JIESUAN_KEY3 = "JIESUAN:queue3";
    private static  String JIESUAN_KEY4 = "JIESUAN:queue4";
    private static String UserDataByToken = "UserDataByToken:";
    private static String YUE = "YUE:";
    private static String UserDataByRowId = "UserDataByRowId:";
    private static String isBuyProDuct = "IsBuyProDuct:";
    private static String TOKEN = "TOKEN:";
    private static Integer lo = 3600*48;
    @Autowired
    private TabUserDataService tabUserDataService;

    @Autowired
    private TabOrdersService tabOrdersService;

    @Autowired
    private ApiController apiController;

    @Autowired
    private TabOrderDataService tabOrderDataService;

    @Autowired
    private TabJiesuanLogService tabJiesuanLogService;

    @Autowired
    private TabTempsService tabTempsService;

    @Override
    public void run(String... args) throws Exception {

    }
    @Scheduled(fixedDelay = 500)
    public void test(){
        Jedis jedis =  new Jedis("41.72.149.115", 6379);
        jedis.auth("hask071bend");

        while (true){
            Long startTime = new Date().getTime();
            String jiesuandata = jedis.rpop(JIESUAN_KEY);
            if(jiesuandata == null || jiesuandata.equals("OK") || "null".equals(jiesuandata)){
                try {
                    Thread.sleep(500);
                    continue;
                }catch (Exception e){

                }
            }
            //System.out.println("jiesuandata======"  +  jiesuandata);
            TabJiesuanLog jiesuanLog = new TabJiesuanLog();
            String zt = "";
            try {
                jiesuanLog = JSON.parseObject(jiesuandata,TabJiesuanLog.class);
                if(jiesuanLog.getAmont() == null){
                    continue;
                }
                TabUserData tabUserData = tabUserDataService.getByUserId(jiesuanLog.getUserid());
                if(tabUserData == null){
                    //jedis.lpush(JIESUAN_KEY,JSON.toJSONString(jiesuanLog));
                    zt = "tabUserData = null";
                    jiesuanLog.setIsNewRecord(false);
                    jiesuanLog.setZt(zt);
                    tabJiesuanLogService.save(jiesuanLog);
                    System.out.println("jiesuandata======"  +  jiesuandata);
                    continue;
                }
                Double currentmoney = tabUserData.getCurrentmoney();
                String yue = jedis.get(YUE+jiesuanLog.getUserid());
                if(yue != null){
                    currentmoney = Double.parseDouble(yue);
                }
                Long oldVip = tabUserData.getVip();
               /* String bb = new BigDecimal(jiesuanLog.getAmont()).setScale(7,BigDecimal.ROUND_UP).toString();
                if(bb.indexOf("E") > -1){
                    return;
                }
                jiesuanLog.setAmont(Double.parseDouble(bb.substring(0,bb.length()-1)));*/
                zt += jiesuanLog.getUserid() + " money=" + currentmoney + " amont=" + jiesuanLog.getAmont() + " oldVip=" + oldVip;
                Long vip = 0L;
                Double m = currentmoney+jiesuanLog.getAmont();
                List<TabVipConfig> tabVipConfigs = apiController.getVipConfigList1(new TabVipConfig(),jedis);
                //if("Buy goods".equals(jiesuanLog.getMsg())){
                String str = jedis.get(isBuyProDuct+jiesuanLog.getUserid());
                if(str != null ){
                    vip = JSON.parseObject(str).getLong("vip");
                }else{
                    Map<String, Object> parame = new HashMap<String, Object>();
                    //parame.put("userid", tabUserData.getRowid());

                    if (!StringUtils.isBlank(tabUserData.getIstiyan()) && tabUserData.getIstiyan().equals("1")) {
                        vip = 1L;
                    }else{
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
                                    if(Integer.parseInt(vip_config.getVip()) <= 4){
                                        for(int j = i - 1; j >= 0; j--){
                                            TabVipConfig tabVipConfig = tabVipConfigs.get(j);
                                            // 查看下级vip有多少人
                                            parame = new HashMap<String, Object>();
                                            parame.put("yue", tabVipConfig.getVip());
                                            parame.put("userid", tabUserData.getRowid());
                                            Long renshu = tabUserDataService.getVipLevelCount(parame);
                                            if(renshu >= vip_config.getXiaji() && vip_config.getXiaji() >0){
                                                new_two_level = Integer.valueOf(tabVipConfig.getVip()) + 1;
                                                break;
                                            }
                                        }
                                    }else{
                                        for(int j = i ; j >= 0; j--){
                                            TabVipConfig tabVipConfig = tabVipConfigs.get(j);
                                            // 查看下级vip有多少人
                                            parame = new HashMap<String, Object>();
                                            parame.put("yue",3);
                                            parame.put("userid", tabUserData.getRowid());
                                            Long renshu = tabUserDataService.getVipLevelCount(parame);
                                            if(renshu >= tabVipConfig.getXiaji() && tabVipConfig.getXiaji() >0){
                                                new_two_level = Integer.valueOf(tabVipConfig.getVip());
                                                break;
                                            }
                                        }
                                    }

                                }else{
                                    new_two_level = 1;
                                }

                                if(new_two_level > 0){
                                    break;
                                }
                            }
                        }

                        vip = (long)(new_one_level > new_two_level ? new_one_level : new_two_level);

                        if(jiesuanLog.getType().equals("充值成功")){
                            if(str != null){
                                String istiyan = JSON.parseObject(str).getString("istiyan");
                                if(istiyan != null && "1".equals(istiyan) ){
                                    //查找未完成的体验单
                                    TabOrderData obj  = new TabOrderData ();
                                    obj.setUserid(tabUserData.getRowid());
                                    obj.setStatus1("1");
                                    obj.setIstiyan("1");
                                    List<TabOrderData> tabOrderDatas  =   tabOrderDataService.findList(obj);

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
                                        List<TabOrders>  tabOrders1 =  tabOrdersService.findList(ooooo  );

                                        for (TabOrders tabOrders2 : tabOrders1) {
                                            tabOrdersService.delete(tabOrders2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                List<TabUserData> lists = new ArrayList<>();
                tabUserData.setVip(vip);
                if(m != 0){
                    //String ssss = new BigDecimal(m).setScale(7,BigDecimal.ROUND_UP).toString();
                    Long lon = m.longValue();
                    tabUserData.setCurrentmoney(lon.doubleValue());
                }else{
                    tabUserData.setCurrentmoney(0d);
                }


                zt += " money1=" + tabUserData.getCurrentmoney() + " vip1=" + vip;

                if(jiesuanLog.getType().equals("充值成功") || jiesuanLog.getType().equals("充值失败")){
                    tabUserData.setCzje(tabUserData.getCzje() + jiesuanLog.getAmont());
                }
                if(jiesuanLog.getType().equals("提现申请") || jiesuanLog.getType().equals("提现成功") || jiesuanLog.getType().equals("提现失败")){
                    tabUserData.setTxje(tabUserData.getTxje() + jiesuanLog.getAmont()*-1);
                }
                if(jiesuanLog.getCmd().equals("Commission")){
                    Double grsy = tabUserData.getGrsy() + jiesuanLog.getAmont();
                    Long g = grsy.longValue();
                    tabUserData.setGrsy(g.doubleValue());
                }
                if(jiesuanLog.getType().equals("Commission1") || jiesuanLog.getType().equals("Commission2") || jiesuanLog.getType().equals("Commission3")){
                    Double grsy = tabUserData.getTdsy() + jiesuanLog.getAmont();
                    Long gg = grsy.longValue();
                    tabUserData.setTdsy(gg.doubleValue());
                }
                if(jiesuanLog.getType().equals("Commission") && jiesuanLog.getCmd().equals("Commission")){
                    tabUserData.setXdzt1(3);
                    System.out.println("用户做完单userid："+jiesuanLog.getUserid()+ " vip："+vip);
                }
                if((jiesuanLog.getType().equals("结算返回本金") && jiesuanLog.getCmd().equals("结算返回本金")) || (tabUserData.getIstiyan().equals("1") && jiesuanLog.getType().equals("Commission"))){
                    jedis.del(isBuyProDuct+jiesuanLog.getUserid());
                }
                if("购买商品".equals(jiesuanLog.getType()) && "Buy goods".equals(jiesuanLog.getMsg())){
                    jiesuanLog.setIsNewRecord(true);
                }else{
                    jiesuanLog.setIsNewRecord(false);
                }
                jiesuanLog.setZt(zt);
                jiesuanLog.setCurrentmoney(tabUserData.getCurrentmoney());
                jiesuanLog.setYgzh(tabUserData.getYgzh());
                jiesuanLog.setYgzh2(tabUserData.getYgzh2());
                if(!"购买商品".equals(jiesuanLog.getType())){
                    tabJiesuanLogService.save(jiesuanLog);
                }

                tabUserData.setIsNewRecord(false);
                tabUserDataService.save(tabUserData);

                jedis.set(YUE+tabUserData.getRowid(),tabUserData.getCurrentmoney().toString());
                jedis.expire(YUE+tabUserData.getRowid(),lo);

                lists.add(tabUserData);
                jedis.set(UserDataByToken+tabUserData.getAccesstoken(),JSON.toJSONString(lists));
                jedis.expire(UserDataByToken+tabUserData.getAccesstoken(),7200);


                if(tabUserData.getIstiyan().equals("0") && !"Buy goods".equals(jiesuanLog.getMsg()) && !"Principal refund".equals(jiesuanLog.getMsg()) && !"Commission".equals(jiesuanLog.getType()) && !"Commission1".equals(jiesuanLog.getType()) && !"Commission2".equals(jiesuanLog.getType()) ) {

                    //用户资金变动 上级vip等级变化
                    if (StringUtils.isNotBlank(tabUserData.getParentid())) {
                        TabUserData parentUserData = tabUserDataService.get(tabUserData.getParentid());
                        Long newVip = parentUserData.getVip() + 1;
                        TabVipConfig tabVipConfig = new TabVipConfig();
                        tabVipConfig.setVip(newVip.toString());
                        List<TabVipConfig> vipConfigs = apiController.getVipConfigList1(tabVipConfig, jedis);
                        if (vipConfigs != null && vipConfigs.size() >0) {
                            System.out.println("充值用户明细变动对上级影响userid：" + tabUserData.getRowid() + " vip：" + vip + " 结算表rowid：" + jiesuanLog.getRowid() + " parentid：" + tabUserData.getParentid() + " currentMoney：" + parentUserData.getCurrentmoney() + " ParentVip：" + parentUserData.getVip());
                            if (parentUserData != null && parentUserData.getCurrentmoney() >= vipConfigs.get(0).getCurrentmoney()) {
                                this.test1(parentUserData, jedis);
                            }
                        }
                    }
                }
            }catch (Exception e){
                System.out.println("jiesuandata======"  +  jiesuandata);
                jedis.lpush(JIESUANERROR,JSON.toJSONString(jiesuanLog));
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                continue;
            }
            Long time = new Date().getTime() - startTime;
            System.out.println("newjj的消耗时间："+time);
        }
    }

    public void test1(TabUserData tabUserData,Jedis jedis){
        Long oldVip = tabUserData.getVip();

        Long vip = 0l;
        Double m = tabUserData.getCurrentmoney();

        List<TabVipConfig> tabVipConfigs = apiController.getVipConfigList1(new TabVipConfig(),jedis);
        String str = jedis.get(isBuyProDuct+tabUserData.getRowid());

        if(str != null ){
            vip = JSON.parseObject(str).getLong("vip");
        } else {
            Map<String, String> parame = new HashMap<String, String>();
            //parame.put("userid", tabUserData.getRowid());

            if (!StringUtils.isBlank(tabUserData.getIstiyan()) && tabUserData.getIstiyan().equals("1")) {
                vip = 1L;
            }else{
                int new_one_level = 0; //当前余额落在哪个区间
                for(int i = tabVipConfigs.size() - 1; i >= 0; i--){
                    TabVipConfig vip_config = tabVipConfigs.get(i);

                    if(vip_config.getMaxmoney() > 0 && (m >= (vip_config.getMaxmoney() == null ? 5000 : vip_config.getMaxmoney()))){
                        new_one_level = Integer.valueOf(vip_config.getVip());
                        System.out.println("判断余额区间 userid："+tabUserData.getRowid()+" vip:"+new_one_level);
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
                                    System.out.println("判断下级vip人数 userid："+tabUserData.getRowid()+" vip:"+new_two_level+" 人数："+renshu);
                                    new_two_level = Integer.valueOf(tabVipConfig.getVip()) + 1;
                                    break;
                                }
                            }
                        }else{
                            new_two_level = 1;
                            System.out.println("判断下级vip人数 i==0 userid："+tabUserData.getRowid()+" vip:"+new_two_level);
                        }

                        if(new_two_level > 0){
                            break;
                        }
                    }
                }

                vip = (long)(new_one_level > new_two_level ? new_one_level : new_two_level);
            }
        }
        System.out.println("上1级vip等级userid："+tabUserData.getRowid() + " oldVip等级："+oldVip + " newVip等级："+vip);
        List<TabUserData> lists = new ArrayList<>();
        tabUserData.setVip(vip);
        tabUserData.setIsNewRecord(false);

        lists.add(tabUserData);
        tabUserDataService.save(tabUserData);

        jedis.set(UserDataByToken+tabUserData.getAccesstoken(),JSON.toJSONString(lists));
        jedis.expire(UserDataByToken+tabUserData.getAccesstoken(),7200);

    }

    public static Date getjndDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -6);

        return calendar.getTime();
    }

    public TabUserData getUserDataByRowId(String rowid,Jedis jedis){
        if(StringUtils.isNotBlank(rowid)){
            String userDataByToken = jedis.get(UserDataByRowId+rowid);
            TabUserData bean = new TabUserData();
            if(StringUtils.isBlank(userDataByToken)){
                bean = tabUserDataService.get(rowid);
                jedis.set(UserDataByRowId+rowid, JSON.toJSONString(bean));
                jedis.expire(UserDataByRowId+rowid,7200);
            }else{
                bean = JSON.parseObject(userDataByToken, new TypeReference<TabUserData>(){});
            }
            return bean;
        }
        return null;
    }

    /*@Scheduled(fixedDelay = 50)
    public void test11111(){
        apiController.clearOrderStatus2();
    }*/

}
