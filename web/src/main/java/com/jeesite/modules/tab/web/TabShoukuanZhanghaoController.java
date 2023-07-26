/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.tab.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeesite.modules.tab.entity.TabShoukuanZhanghao;
import com.jeesite.modules.tab.service.TabShoukuanZhanghaoService;

/**
 * tab_shoukuan_zhanghaoController
 * @author 2
 * @version 2022-01-14
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabShoukuanZhanghao")
public class TabShoukuanZhanghaoController extends BaseController {

	@Autowired
	private TabShoukuanZhanghaoService tabShoukuanZhanghaoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabShoukuanZhanghao get(String rowid, boolean isNewRecord) {
		return tabShoukuanZhanghaoService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabShoukuanZhanghao:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabShoukuanZhanghao tabShoukuanZhanghao, Model model) {
		model.addAttribute("tabShoukuanZhanghao", tabShoukuanZhanghao);
		return "modules/tab/tabShoukuanZhanghaoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabShoukuanZhanghao:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabShoukuanZhanghao> listData(TabShoukuanZhanghao tabShoukuanZhanghao, HttpServletRequest request, HttpServletResponse response) {
		tabShoukuanZhanghao.setPage(new Page<>(request, response));
		Page<TabShoukuanZhanghao> page = tabShoukuanZhanghaoService.findPage(tabShoukuanZhanghao);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabShoukuanZhanghao:view")
	@RequestMapping(value = "form")
	public String form(TabShoukuanZhanghao tabShoukuanZhanghao, Model model) {
		
		
		
		
		model.addAttribute("tabShoukuanZhanghao", tabShoukuanZhanghao);
		return "modules/tab/tabShoukuanZhanghaoForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabShoukuanZhanghao:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabShoukuanZhanghao tabShoukuanZhanghao,HttpServletRequest request) {
		
		String imgsrc=  tabShoukuanZhanghao.getImg1();
		if (imgsrc.indexOf("http")  == -1 ) {
			
			String path =    request.getRealPath("/userfiles/fileupload");
			System.out.println(path);
			String[] split =   imgsrc.split("fileupload");
			
			File file  = new File(path + split[1]); 
			String  pp =  HttpServletRequestUtils.uploadFiles(file);
			
			System.err.println(split[1]);
			tabShoukuanZhanghao.setImg1(pp);

			}
		
		  imgsrc=  tabShoukuanZhanghao.getImg2 ();
		if (imgsrc.indexOf("http")  == -1 ) {
			
			String path =    request.getRealPath("/userfiles/fileupload");
			System.out.println(path);
			String[] split =   imgsrc.split("fileupload");
			
			File file  = new File(path + split[1]); 
			String  pp =  HttpServletRequestUtils.uploadFiles(file);
			
			System.err.println(split[1]);
			tabShoukuanZhanghao.setImg2(pp);

			}
		
		
		tabShoukuanZhanghaoService.save(tabShoukuanZhanghao);
		return renderResult(Global.TRUE, text("保存tab_shoukuan_zhanghao成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabShoukuanZhanghao:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabShoukuanZhanghao tabShoukuanZhanghao) {
		tabShoukuanZhanghaoService.delete(tabShoukuanZhanghao);
		return renderResult(Global.TRUE, text("删除tab_shoukuan_zhanghao成功！"));
	}
	
}