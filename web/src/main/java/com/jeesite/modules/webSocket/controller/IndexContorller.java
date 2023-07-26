package com.jeesite.modules.webSocket.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.tab.entity.TabYuangongData;
import com.jeesite.modules.tab.service.TabYuangongDataService;
import com.jeesite.modules.tab.web.GoldpaysUtil;
import com.jeesite.modules.webSocket.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.jeesite.modules.webSocket.service.IndexService;
import com.jeesite.modules.webSocket.socket.Setting;

@Controller
public class IndexContorller {

	@Autowired
	private IndexService service;

	/**
	 * 控制台
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("data", service.getIndexPageInfo());
		return "modules/socket/index";
	}

	@Autowired
	private TabYuangongDataService tabYuangongDataService;

	/**
	 * 客服接口
	 * 
	 * @return
	 */
	@RequestMapping("/customer")
	public String customerSocket(HttpServletRequest request,Model model) {
		/*String token = (String) request.getSession().getAttribute("token");
		if(StringUtils.isBlank(token)){
			return "modules/daili/login";
		}
		TabYuangongData tabYuangongData = tabYuangongDataService.get(token);
		if(tabYuangongData == null){
			return "modules/daili/login";
		}*/
		//model.addAttribute("ygzh2", tabYuangongData.getAcccount());
		model.addAttribute("ygzh2", "test");
		return "modules/socket/customerSocket";
	}

	/**
	 * 客户接口
	 * 
	 * @return
	 */
	@RequestMapping("/consumer")
	public String consumerSocket() {
		return "modules/socket/consumerSocket";
	}

	/**
	 * 编辑广告接口
	 * 
	 * @return
	 */
	@RequestMapping("/editAd")
	public String editAd(Model model) {
		model.addAttribute("old_ad", Setting.adReply);
		return "modules/socket/editAd";
	}

	@ResponseBody
	@RequestMapping("/saveAd")
	public String saveAd(@ModelAttribute("context") String context) {
		Setting.adReply = context;
		return new Gson().toJson("保存成功！");
	}

	/**
	 * 编辑回复接口
	 * 
	 * @return
	 */
	@RequestMapping("/editReply")
	public String editReply(Model model) {
		model.addAttribute("old_reply", Setting.autoReply);
		return "modules/socket/editReply";
	}

	@ResponseBody
	@RequestMapping("/saveReply")
	public String saveReply(@ModelAttribute("context") String context) {
		Setting.autoReply = context;
		return new Gson().toJson("保存成功！");
	}

	/**
	 * 文件/图片上传接口
	 * 
	 * @param
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/file/socketUpload")
	public Map<String,Object> fileUpload(@RequestParam MultipartFile file) throws IOException {
		System.out.println("name:"+file.getName()+"contentType:"+file.getContentType()+ "file.getOriginalFilename():"+file.getOriginalFilename());
		Date date = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("MMdd");
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssS");
		String suffix = getExtensionName(file.getOriginalFilename());
		System.out.println("suffix:"+suffix);
		String nowStr =format.format(date);
		try{
			String fileName =  nowStr + "." + suffix;
			String path = "/www/wwwroot/file/file.e-creatoerzw.com/socket/" + fileName;
			System.out.println("path:"+path);
			File dest = new File(path).getCanonicalFile();
			if (!dest.getParentFile().exists()) {
				if (!dest.getParentFile().mkdirs()) {
					System.out.println("was not successful.");
				}
			}
			file.transferTo(dest);
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("chmod +777 -R "+path);
			String str = path.replace("/www/wwwroot/file/file.e-creatoerzw.com","https://file.twcreaotr.com");
			Map<String,Object> map = new HashMap<>();
			map.put("data",str);
			return map;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 广告推送open/close
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/contorAd")
	public String contorAd() {
		return service.contorAd();
	}

	/**
	 * 自动回复open/close
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/contorAuto")
	public String contorAuto() {
		return service.contorAuto();
	}

	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}


}
