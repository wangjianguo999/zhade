package com.jeesite.modules.webSocket.socket;

/**
 * 应答/广告设置
 * 
 * @author Administrator
 *
 */
public class Setting {

	/**
	 * 无客服在线时，是否开启自动应答。默认开启
	 */
	public static volatile Boolean isAutoReply = true;

	/**
	 * 客户连接时是否推送广告。默认不推送
	 */
	public static volatile Boolean isAdReply = true;

	// 无可客服在线时自动答复内容
	public static String autoReply = "<font color='red'>Customer service is busy, please consult later</font>";

	// 推荐/广告
	public static String adReply = "Dear user,What can help you!";
			//+ "<br><br><a href=''>在线续约下单</a>       <a href=''>在线续约下单</a>      <a href=''>在线续约下单</a>";
	public static String error = "自动翻译异常，消息发送失败，请联系技术排查问题！";
}
