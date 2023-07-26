package com.jeesite.modules.tab.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.jeesite.common.utils.FileUtil;
import com.jeesite.modules.tab.entity.TabGonggao;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.tab.entity.TabSiderData;
import com.jeesite.modules.tab.service.TabSiderDataService;
import redis.clients.jedis.Jedis;

/**
 * tab_sider_dataController
 * @author 3333
 * @version 2021-12-11
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabSiderData")
public class TabSiderDataController extends BaseController {

	@Autowired
	private TabSiderDataService tabSiderDataService;

	private Jedis jedis =  new Jedis("41.72.149.115", 6379);
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabSiderData get(String rowid, boolean isNewRecord) {
		return tabSiderDataService.get(rowid, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabSiderData:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabSiderData tabSiderData, Model model) {
		model.addAttribute("tabSiderData", tabSiderData);
		return "modules/tab/tabSiderDataList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabSiderData:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabSiderData> listData(TabSiderData tabSiderData, HttpServletRequest request, HttpServletResponse response) {
		tabSiderData.setPage(new Page<>(request, response));
		Page<TabSiderData> page = tabSiderDataService.findPage(tabSiderData);
		return page;
	}

	//uploadFile



	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabSiderData:view")
	@RequestMapping(value = "form")
	public String form(TabSiderData tabSiderData, Model model) {

		model.addAttribute("tabSiderData", tabSiderData);
		return "modules/tab/tabSiderDataForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabSiderData:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabSiderData tabSiderData,HttpServletRequest request ) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		String imgsrc=  tabSiderData.getImgsrc();
		if (imgsrc.indexOf("http")  == -1 ) {
			tabSiderData.setImgsrc("https://file.twcreaotr.com/"+imgsrc);

			String str = FileUtil.downloadFile(tabSiderData.getImgsrc(),"/www/wwwroot/file.e-creatoerzw.com/newPic/");
			tabSiderData.setImgsrc(str);
			/*String path =    request.getRealPath("/userfiles/fileupload");
			System.out.println(path);
			String[] split =   imgsrc.split("fileupload");

			File file  = new File(path + split[1]);
			String  pp =  HttpServletRequestUtils.uploadFiles(file);

			System.err.println(split[1]);
			tabSiderData.setImgsrc(pp);*/

		}


		tabSiderDataService.save(tabSiderData);


		jedis.set("tabSiderDatasStr", JSON.toJSONString(tabSiderDataService.findList(new TabSiderData())));
		jedis.quit();
		return renderResult(Global.TRUE, text("保存tab_sider_data成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabSiderData:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabSiderData tabSiderData) {
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		tabSiderDataService.delete(tabSiderData);
		jedis.set("tabSiderDatasStr", JSON.toJSONString(tabSiderDataService.findList(new TabSiderData())));
		jedis.quit();
		return renderResult(Global.TRUE, text("删除tab_sider_data成功！"));
	}

}