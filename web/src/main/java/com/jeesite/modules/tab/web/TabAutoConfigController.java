package com.jeesite.modules.tab.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
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
import com.jeesite.modules.tab.entity.TabAutoConfig;
import com.jeesite.modules.tab.service.TabAutoConfigService;
import redis.clients.jedis.Jedis;

/**
 * tab_auto_configController
 * @author 1
 * @version 2022-03-14
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabAutoConfig")
public class TabAutoConfigController extends BaseController {

	@Autowired
	private TabAutoConfigService tabAutoConfigService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabAutoConfig get(String rowid, boolean isNewRecord) {
		return tabAutoConfigService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabAutoConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabAutoConfig tabAutoConfig, Model model) {
		model.addAttribute("tabAutoConfig", tabAutoConfig);
		return "modules/tab/tabAutoConfigList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabAutoConfig:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabAutoConfig> listData(TabAutoConfig tabAutoConfig, HttpServletRequest request, HttpServletResponse response) {
		tabAutoConfig.setPage(new Page<>(request, response));
		Page<TabAutoConfig> page = tabAutoConfigService.findPage(tabAutoConfig);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabAutoConfig:view")
	@RequestMapping(value = "form")
	public String form(TabAutoConfig tabAutoConfig, Model model) {
		model.addAttribute("tabAutoConfig", tabAutoConfig);
		return "modules/tab/tabAutoConfigForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabAutoConfig:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabAutoConfig tabAutoConfig) {
		
		String isqy =   tabAutoConfig.getIsqidong();
		
		if (isqy.equals("1")) {
			TabAutoConfig autoConfig  = new TabAutoConfig();
			List<TabAutoConfig> autoConfigs  =   tabAutoConfigService.findList(autoConfig);
			
			for (TabAutoConfig tabAutoConfig2 : autoConfigs) {
				tabAutoConfig2.setIsqidong("0");
				tabAutoConfigService.save(tabAutoConfig2); ;
			}
		}
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		tabAutoConfigService.save(tabAutoConfig);
		jedis.set("AutoConfig", JSON.toJSONString(tabAutoConfig));
		return renderResult(Global.TRUE, text("保存tab_auto_config成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabAutoConfig:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabAutoConfig tabAutoConfig) {
		tabAutoConfigService.delete(tabAutoConfig);
		return renderResult(Global.TRUE, text("删除tab_auto_config成功！"));
	}
	
}