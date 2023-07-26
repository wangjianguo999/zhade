package com.jeesite.modules.tab.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.jeesite.common.codec.Md5Utils;
import org.json.JSONObject;

public class GoldpaysUtil {
	private static  	String  merchant = "C1635339478444";
	public  static String getSign2(Map<String, String> parame,String key) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> entry : parame.entrySet()) {
			if (entry.getValue() != "") {
				//System.err.println(entry.getValue());
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
			 
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result  = result.substring(0,result.length()-1);
		//System.out.println("--"+ result);

		 result =  result  +"&key="+  key;
		 //System.out.println("加密前" + result);
		 result  = Md5Utils.md5(result).toLowerCase();
		  
		return result;
	}

	public  static String getJSONTOSign2(JSONObject jsonObject,String key) {
		ArrayList<String> list = new ArrayList<String>();
		Iterator iter = jsonObject.keys();
		while (iter.hasNext()) {
			Object entry = iter.next();
			list.add(entry + "=" + jsonObject.getString(entry.toString()) + "&");
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result  = result.substring(0,result.length()-1);
		//System.out.println("--"+ result);

		result =  result  +"&key="+  key;
		//System.out.println("加密前" + result);
		result  = Md5Utils.md5(result).toLowerCase();

		return result;
	}

	public  static String getSign3(Map<String, String> parame,String key) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, String> entry : parame.entrySet()) {
			if (entry.getValue() != "") {
				System.err.println(entry.getValue());
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
			 
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result  = result.substring(0,result.length()-1);
		System.out.println("--"+ result);
		 result =  result  +"&key="+  key;
		 result =   sha256_HMAC(result, key);
		  
		return result;
	}

	public static String httpGet(String url ,Map map){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse ;
		String finalString = null;
		Set<String> sets = map.keySet();
		url = url+"?";
		for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			url = url +key+"="+map.get(key)+"&";
		}
		HttpGet httpGet = new HttpGet(url);
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			finalString= EntityUtils.toString(entity, "UTF-8");
			try {
				httpResponse.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return finalString;
	}

	 /**
    * sha256_HMAC加密
    * @param message 消息
    * @param secret  秘钥
    * @return 加密后字符串
    */
   public static String sha256_HMAC(String message, String secret) {
       String hash = "";
       try {
           Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
           SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
           sha256_HMAC.init(secret_key);
           byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
           hash = byteArrayToHexString(bytes);
       } catch (Exception e) {
           System.out.println("Error HmacSHA256 ===========" + e.getMessage());
       }
       return hash;
   }
	
	/**
    * 字节数组转16进制
    *
    * @param b .
    * @return .
    */
   public static String byteArrayToHexString(byte[] b) {
       StringBuilder hs = new StringBuilder();
       String stmp;
       for (int n = 0; b != null && n < b.length; n++) {
           stmp = Integer.toHexString(b[n] & 0XFF);
           if (stmp.length() == 1) {
               hs.append('0');
           }

           hs.append(stmp);
       }
       return hs.toString().toLowerCase();
   }

	
	public static void main(String[] args) {
		
		String url = "https://www.goldpays.in/openApi/pay/createOrder";
		
		Map<String, String>  parame = new HashMap<>();
		
//		parame.put("merchant", merchant);
//		parame.put("orderId", UUID.randomUUID().toString());
//		parame.put("amount", "111");
//		parame.put("customName", "什么");
//		parame.put("customMobile", "13914717235");
//
//		parame.put("customEmail", "w@qq.com");
//
//		  		parame.put("notifyUrl", "http://www.baidu.com");
//		  		parame.put("callbackUrl", "http://www.baidu.com");
//
//		String key = "t6H7aEMXUNImDiw5gQ2iJAu7otBvv5vShQ0jip";
//		
//		String sign  =   GoldpaysUtil.getSign2(parame, key );
//		parame.put("sign", sign);
//		System.out.println(parame);
//		String s =  hanYuanUtils(url, "", parame);
//
//		System.err.println(s);
		
		url = "http://localhost:8980/js/api/createOrder";
		parame.put("appid", "3e3af87395f440c2a79419a88cb8b965");
		parame.put("orderId", UUID.randomUUID().toString());
		parame.put("orderMoney", "200");
		parame.put("customName", "什么");
		parame.put("customMobile", "13914717235");

		  		parame.put("notifyUrl", "http://www.baidu.com");

		String key = "0000110000,0000110100,0000110107,";
		
		String sign  =   GoldpaysUtil.getSign2(parame, key );
		parame.put("sign", sign);
		System.out.println(parame);
		String s =  hanYuanUtils(url, "", parame);
		System.out.println(s);
		
	}
	
	public static String hanYuanUtils(String url, String method, Map<String, String> map) {
		HttpPost post = new HttpPost( url+  method);
		CloseableHttpClient closeableHttpClient  =   HttpClients.createDefault();
		
		Set<String> sets = map.keySet();
		List<BasicNameValuePair> data = new ArrayList<>();
 
		post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		for (Iterator iterator = sets.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			data.add(new BasicNameValuePair(key, map.get(key))) ;
		}
		String reString = "";
		try {
		 	post.setEntity(new UrlEncodedFormEntity(data,"utf-8"));
		 	HttpResponse httpResponse   = closeableHttpClient.execute(post);
			reString =EntityUtils.toString( httpResponse.getEntity(),"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reString;
	}

	public static String hanYuanUtils1(String url,Map<String,Object> map) {
		HttpPost post = new HttpPost( url);
		CloseableHttpClient closeableHttpClient  =   HttpClients.createDefault();

		post.addHeader("Content-Type", "application/json;charset=utf-8");
		post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
		post.addHeader("X-Locale", "zh_CN");
		JSONObject jsonObject = new JSONObject(map);
		StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
		s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		post.setEntity(s);
		String reString = "";
		try {
			HttpResponse httpResponse   = closeableHttpClient.execute(post);
			reString = EntityUtils.toString( httpResponse.getEntity(),"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reString;
	}

	public static String send(String url, JSONObject jsonObject, String encoding) throws ParseException, IOException {
		String body ="";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		StringEntity s = new StringEntity(jsonObject.toString(),"utf-8");
		s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
		httpPost.setEntity(s);

		httpPost.setHeader("Content-type","application/json");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		CloseableHttpResponse response = client.execute(httpPost);
		//获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			//按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		//释放链接
		response.close();
		return body;
	}
		
}
