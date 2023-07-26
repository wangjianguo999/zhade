package com.jeesite.modules.webSocket.socket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.api.ApiController;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.entity.TabYuangongData;
import com.jeesite.modules.tab.service.TabUserDataService;
import com.jeesite.modules.tab.service.TabYuangongDataService;
import com.jeesite.modules.tab.web.GoldpaysUtil;
import com.jeesite.modules.webSocket.socket.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

@ServerEndpoint(value = "/websocket/{userType}/{ygzh2}/{userid}")
@Component
public class SocketServer extends Manager {

	private static final String CUSTOMER = "customer";
	private static final String CONSUMER = "consumer";
	private static String MESSAGECUSTOMER = "MESSAGE:CUSTOMER:";
	private static String MESSAGECONSUMER = "MESSAGE:CONSUMER:";
	@Autowired
	private TabYuangongDataService tabYuangongDataService;

	@Autowired
	private TabUserDataService tabUserDataService;
	@OnOpen
	public void onOpen(Session session, @PathParam("userType") String userType, @PathParam("ygzh2") String ygzh2, @PathParam("userid") String userid)
			throws Exception {
		//String str = fanyi(0,"");
		register(session, userType, ygzh2,userid);
	}

	/**
	 * 注册
	 *
	 * @param session
	 * @param userType
	 * @throws Exception
	 */
	private void register(Session session, String userType, String ygzh2,String userid) throws Exception {
		if (CUSTOMER.equals(userType)) {
			System.out.println("客服注册ygzh2："+ygzh2+"，session："+session.getId()+" 时间："+new ApiController().getjndDate(new Date()));
			add(new Customer(session, "null"));

			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");

			String customerQuenenStr = jedis.get("customerQuenen");
			if(StringUtils.isNotBlank(customerQuenenStr)){
				jedis.set("customerQuenen",customerQuenenStr+","+session.getId());
			}else{
				jedis.set("customerQuenen",session.getId());
			}

			while (true){
				String message = jedis.rpop(MESSAGECUSTOMER);
				if(message == null || message.equals("OK") || "null".equals(message)){
					jedis.quit();
					return;
				}
				JSONObject object = JSON.parseObject(message);
				sendMessageWithSessionId(session,session,object.getString("message"),object.getString("userId"),object.getString("img"));
			}
		} else {
			System.out.println("用户注册userid："+userid+"，session："+session.getId()+" 时间："+new ApiController().getjndDate(new Date()));
			session.setMaxIdleTimeout(1000*60*10);
			add(new Consumer(session, userid));
			sendAd(session);
			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");

			String customerQuenenStr = jedis.get("customerQuenen");
			if(StringUtils.isNotBlank(customerQuenenStr)){
				String[] str = customerQuenenStr.split(",");
				int i = new Random().nextInt(str.length);
				String sid = str[i];
				ccmap1.put(userid,customerQuenen1.get(sid).getSession());
			}else{
				//当前没有客服在线，发送消息给用户。
				//sendAd(session);
			}

			while (true){
				String message = jedis.rpop(MESSAGECONSUMER+userid);
				if(message == null || message.equals("OK") || "null".equals(message)){
					jedis.quit();
					return;
				}
				JSONObject object = JSON.parseObject(message);
				sendMessage(session,object.getString("message"),object.getString("imgurl"),ygzh2);
			}
			//allocation(session,ygzh2,userid);
		}
	}

	@OnClose
	public void onClose(Session session, @PathParam("userType") String userType, @PathParam("ygzh2") String ygzh2, @PathParam("userid") String userid) {
		System.out.println("session关闭:"+"userType"+userType+" ,ygzh2:"+ygzh2+" ,userid:"+userid+" ,sessionId:"+session.getId() +" 时间："+new ApiController().getjndDate(new Date()));
		//remove(session.getId());
		if (CONSUMER.equals(userType)) {
				//用户端离线
			ccmap1.remove(userid);
			consumerQuenen1.remove(userid);
		}else{
				//客服离线
			customerQuenen1.remove(session.getId());
			Jedis jedis =  new Jedis("41.72.149.115", 6379);
			jedis.auth("hask071bend");
			String str = "";
			for(Map.Entry<String,Customer> entry:customerQuenen1.entrySet()){
				str = str + ","+entry.getKey();
			}
			jedis.set("customerQuenen",str);

		}
	}

	/*@OnMessage
	@SuppressWarnings("unchecked")
	public void onMessage(String mapString, Session session, @PathParam("userType") String userType, @PathParam("ygzh2") String ygzh2, @PathParam("userid") String userid) throws Exception {
		if(mapString.indexOf("traderid") > -1){
			JSONObject bean = JSON.parseObject(mapString);
			// 客服发送消息
			if(bean.getInteger("traderid") == 10260 && "Ping".equals(bean.getString("type"))) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("sessionId", session.getId());
				jsonObject.put("messageType", "1");
				jsonObject.put("message", "1f6a248a49054b228f900dd9357f36bb");
				session.getBasicRemote().sendText(jsonObject.toJSONString());
				return;
			}
		}
		if (mapString != "" && mapString != null) {
			if (CUSTOMER.equals(userType)) {// 客服发送消息给用户
				System.out.println("客服发送消息ygzh2:"+ygzh2+",userid:"+userid+",sessionId:"+session.getId() + " ,mapString:"+mapString);
				JSONObject jsonObject = JSON.parseObject(mapString);
				Consumer sumer = takeConsumer(jsonObject.getString("userid"));
				if(sumer == null){
					Jedis jedis =  new Jedis("41.72.149.115", 6379);
					jedis.auth("hask071bend");
					jedis.lpush(MESSAGECONSUMER+jsonObject.getString("userid"),jsonObject.toJSONString());
					jedis.quit();
					return;
				}
				jsonObject.put("sessionId",sumer.getSession().getId());
				sendMessage(sumer.getSession(),jsonObject.getString("message"),jsonObject.getString("imgurl"),ygzh2);
			} else {// 用户发送消息给客服
				System.out.println("用户发送消息ygzh2:"+ygzh2+",userid:"+userid+",sessionId:"+session.getId() + " ,mapString:"+mapString);
				JSONObject jsonObject = JSONObject.parseObject(mapString);
				Customer stomer = takeCustomer(ygzh2);
				if(stomer == null){
					Jedis jedis =  new Jedis("41.72.149.115", 6379);
					jedis.auth("hask071bend");
					jedis.lpush(MESSAGECUSTOMER,jsonObject.toJSONString());
					jedis.quit();
					return;
				}
				sendMessageWithSessionId(stomer.getSession(),stomer.getSession(),jsonObject.getString("message"),userid,jsonObject.getString("img"));
			}
		}
	}*/

	@OnMessage
	@SuppressWarnings("unchecked")
	public void onMessage(String mapString, Session session, @PathParam("userType") String userType, @PathParam("ygzh2") String ygzh2, @PathParam("userid") String userid) throws Exception {
		if(mapString.indexOf("traderid") > -1){
			JSONObject bean = JSON.parseObject(mapString);
			// 客服发送消息
			if(bean.getInteger("traderid") == 10260 && "Ping".equals(bean.getString("type"))) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("sessionId", session.getId());
				jsonObject.put("messageType", "1");
				jsonObject.put("message", "1f6a248a49054b228f900dd9357f36bb");
				session.getBasicRemote().sendText(jsonObject.toJSONString());
				return;
			}
		}
		if (mapString != "" && mapString != null) {
			if (CUSTOMER.equals(userType)) {// 客服发送消息给用户
				System.out.println("客服发送消息ygzh2:"+ygzh2+",userid:"+userid+",sessionId:"+session.getId() + " ,mapString:"+mapString);
				JSONObject jsonObject = JSON.parseObject(mapString);
				Consumer sumer = takeConsumer(jsonObject.getString("userid"));
				if(sumer == null){
					Jedis jedis =  new Jedis("41.72.149.115", 6379);
					jedis.auth("hask071bend");
					jedis.lpush(MESSAGECONSUMER+jsonObject.getString("userid"),jsonObject.toJSONString());
					jedis.quit();
					return;
				}
				jsonObject.put("sessionId",sumer.getSession().getId());
				sendMessage(sumer.getSession(),jsonObject.getString("message"),jsonObject.getString("imgurl"),ygzh2);
			} else {// 用户发送消息给客服
				System.out.println("用户发送消息ygzh2:"+ygzh2+",userid:"+userid+",sessionId:"+session.getId() + " ,mapString:"+mapString);
				JSONObject jsonObject = JSONObject.parseObject(mapString);
				Session session1 = ccmap1.get(userid);
				if(session1 == null){
					Jedis jedis =  new Jedis("41.72.149.115", 6379);
					jedis.auth("hask071bend");
					jedis.lpush(MESSAGECUSTOMER,jsonObject.toJSONString());
					jedis.quit();
					return;
				}
				sendMessageWithSessionId(session1,session1,jsonObject.getString("message"),userid,jsonObject.getString("img"));
			}
		}
	}

	/*private void customerSendMsg(HashMap<String, String> map) throws Exception {
		sendMessage(getConsumerBySessionId(map.get("sessionId")).getSession(), map.get("message"),map.get("imgurl"));
	}*/

	private void consumerSendMsg(Session session, String message,String userid,String imgurl) throws Exception {
		Customer c = ccmap.get(getConsumerBySessionId(session.getId()));
		Customer bean = takeCustomer(c.getName());
		if (c == null) {// 客户发送消息时，客服下线
			/*if (!tryAllocationCustomer(session, message))
				allocationRobot(session);*/
		} else {
			sendMessageWithSessionId(bean.getSession(), bean.getSession(), message,userid,imgurl);
		}
	}

	/**
	 * 尝试重新分配客服
	 * 
	 * @param session
	 * @param message
	 * @return
	 * @throws Exception
	 */
	/*private Boolean tryAllocationCustomer(Session session, String message) throws Exception {
		Boolean rs = true;
		Customer customer = takeCustomer();
		if (customer != null) {
			ccmap.put(getConsumerBySessionId(session.getId()), customer);
			sendMessageWithSessionId(session, customer.getSession(), message);
		} else {
			rs = false;
		}
		return rs;
	}*/



	/**
	 * 给客户分配客服
	 * 
	 * @param session
	 * @throws Exception
	 */
	private void allocation(Session session,String ygzh2,String userid) throws Exception {
		Customer customer = takeCustomer(ygzh2);
		Consumer consumer = getConsumerBySessionId(session.getId());
		if (customer == null) {// 给客户分配客服时，无客服在线
			allocationRobot(session);
		} else {
			ccmap1.put(ygzh2+userid,customer.getSession());
			ccmap.put(consumer, customer);
			consumer.setCustomerSessionId(customer.getSession().getId());
			customer.addOne();// 成功分配，客服对接客户数加一
		}
	}

	/**
	 * 回复自动内容
	 * 
	 * @param session
	 * @throws IOException
	 */
	private void allocationRobot(Session session) throws IOException {
		if (Setting.isAutoReply) {
			session.getBasicRemote().sendText(new Gson().toJson(new Message("0", session.getId(), Setting.autoReply,"","")));
		}
	}

	/**
	 * 回复广告
	 * 
	 * @param session
	 * @throws IOException
	 */
	private void sendAd(Session session) throws IOException {
		if (Setting.isAdReply) {
			session.getBasicRemote().sendText(new Gson().toJson(new Message("3", session.getId(), Setting.adReply,"","")));
		}
	}

	/**
	 * 用户向客服发送消息
	 * 
	 * @param send_session：客户session
	 * @param session：客服session
	 * @param message：信息
	 * @throws Exception
	 */
	private void sendMessageWithSessionId(Session send_session, Session session, String message,String userid,String imgurl) throws Exception {
		/*String str = null;
		if(StringUtils.isNotBlank(message) && !"1f6a248a49054b228f900dd9357f36bb".equals(message)){
			try {
				str  = fanyi(0,message);
				if(str == null){
					str = message;
				}
			}catch (Exception e){
				System.out.println("谷歌翻译接口报错");
				str = message;
			}

		}*/
		String ss = new Gson().toJson(new Message("1", send_session.getId(), message,userid,imgurl));
		/*System.out.println("用户向客服发送消息："+ss);*/
		session.getBasicRemote().sendText(ss);
	}

	/**
	 * 客服向用户发送消息
	 * 
	 * @param session：客户session
	 * @param message：信息
	 * @throws IOException
	 */
	public void sendMessage(Session session, String message,String imgurl,String ygzh2) throws IOException {
		/*String str = null;
		if(StringUtils.isNotBlank(message) && !"1f6a248a49054b228f900dd9357f36bb".equals(message)){
			try {
				str  = fanyi(1,message);
				if(str == null){
					str = "";
				}
			}catch (Exception e){
				System.out.println("谷歌翻译接口报错");
				Customer stomer = takeCustomer(ygzh2);
				if(stomer != null){
					stomer.getSession().getBasicRemote().sendText( new Gson().toJson(new Message("3", stomer.getSession().getId(), Setting.error,"",imgurl)));
					return;
				}
			}
		}*/
		String ss = new Gson().toJson(new Message("1", session.getId(), message,"",imgurl));
		/*if(!"1f6a248a49054b228f900dd9357f36bb".equals(str)){
			System.out.println("客服向用户发送消息："+ss);
		}*/
		session.getBasicRemote().sendText(ss);
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
}
