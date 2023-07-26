package com.jeesite.modules.tab.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.jeesite.common.utils.FileUtil;
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
import com.jeesite.modules.tab.entity.TabGonggao;
import com.jeesite.modules.tab.service.TabGonggaoService;
import redis.clients.jedis.Jedis;

/**
 * tab_gonggaoController
 * @author 1
 * @version 2022-04-10
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabGonggao")
public class TabGonggaoController extends BaseController {

	@Autowired
	private TabGonggaoService tabGonggaoService;

	private Jedis jedis =  new Jedis("41.72.149.115", 6379);
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabGonggao get(String rowid, boolean isNewRecord) {
		return tabGonggaoService.get(rowid, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabGonggao:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabGonggao tabGonggao, Model model) {
		model.addAttribute("tabGonggao", tabGonggao);
		return "modules/tab/tabGonggaoList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabGonggao:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabGonggao> listData(TabGonggao tabGonggao, HttpServletRequest request, HttpServletResponse response) {
		tabGonggao.setPage(new Page<>(request, response));
		Page<TabGonggao> page = tabGonggaoService.findPage(tabGonggao);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabGonggao:view")
	@RequestMapping(value = "form")
	public String form(TabGonggao tabGonggao, Model model) {
		model.addAttribute("tabGonggao", tabGonggao);
		return "modules/tab/tabGonggaoForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabGonggao:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabGonggao tabGonggao) {
		String imgsrc=  tabGonggao.getImgsrc();
		if (imgsrc.indexOf("http")  == -1 ) {
			tabGonggao.setImgsrc("https://file.twcreaotr.com/"+imgsrc);
			String str = FileUtil.downloadFile(tabGonggao.getImgsrc(),"/www/wwwroot/file.e-creatoerzw.com/newPic/");
			tabGonggao.setImgsrc(str);
		}
		tabGonggaoService.save(tabGonggao);
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		jedis.set("tabGonggaos", JSON.toJSONString(tabGonggaoService.findList(new TabGonggao())));
		jedis.quit();
		return renderResult(Global.TRUE, text("保存tab_gonggao成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabGonggao:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabGonggao tabGonggao) {
		tabGonggaoService.delete(tabGonggao);
		return renderResult(Global.TRUE, text("删除tab_gonggao成功！"));
	}

}