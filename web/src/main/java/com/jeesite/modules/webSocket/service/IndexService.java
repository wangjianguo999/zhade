package com.jeesite.modules.webSocket.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.jeesite.modules.webSocket.vo.IndexPageInfo;
import org.springframework.stereotype.Service;

import com.jeesite.modules.webSocket.socket.Consumer;
import com.jeesite.modules.webSocket.socket.Customer;
import com.jeesite.modules.webSocket.socket.Manager;
import com.jeesite.modules.webSocket.socket.Setting;
import com.jeesite.modules.webSocket.vo.IndexPageInfo;

/**
 * 业务实现
 * 
 * @author Administrator
 *
 */
@Service
public class IndexService {

	/**
	 * 查询监控页信息
	 * 
	 * @return
	 */
	public IndexPageInfo getIndexPageInfo() {

		IndexPageInfo ip = new IndexPageInfo();

		List<Consumer> consumer = new ArrayList<Consumer>();
		Manager.consumerQuenen.forEach((c) -> {
			c.setDurationTime(new Date());
			consumer.add(c);
		});
		ip.setConsumerList(consumer);

		List<Customer> customer = new ArrayList<Customer>();
		Manager.customerQuenen.forEach((c) -> {
			c.setDurationTime(new Date());
			customer.add(c);
		});
		ip.setCustomerList(customer);

		return ip;
	}

	public String contorAd() {
		if (Setting.isAdReply) {
			Setting.isAdReply = false;
		} else {
			Setting.isAdReply = true;
		}
		return Setting.isAdReply.toString();
	}

	public String contorAuto() {
		if (Setting.isAutoReply) {
			Setting.isAutoReply = false;
		} else {
			Setting.isAutoReply = true;
		}
		return Setting.isAutoReply.toString();
	}
}
