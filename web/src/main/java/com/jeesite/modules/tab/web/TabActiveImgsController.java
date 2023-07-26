package com.jeesite.modules.tab.web;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeesite.modules.tab.entity.TabActiveImgs;
import com.jeesite.modules.tab.service.TabActiveImgsService;

/**
 * tab_active_imgsController
 * @author 额
 * @version 2022-04-21
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabActiveImgs")
public class TabActiveImgsController extends BaseController {

	@Autowired
	private TabActiveImgsService tabActiveImgsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabActiveImgs get(String rowid, boolean isNewRecord) {
		return tabActiveImgsService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabActiveImgs:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabActiveImgs tabActiveImgs, Model model) {
		model.addAttribute("tabActiveImgs", tabActiveImgs);
		return "modules/tab/tabActiveImgsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabActiveImgs:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabActiveImgs> listData(TabActiveImgs tabActiveImgs, HttpServletRequest request, HttpServletResponse response) {
		tabActiveImgs.setPage(new Page<>(request, response));
		Page<TabActiveImgs> page = tabActiveImgsService.findPage(tabActiveImgs);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabActiveImgs:view")
	@RequestMapping(value = "form")
	public String form(TabActiveImgs tabActiveImgs, Model model) {
		model.addAttribute("tabActiveImgs", tabActiveImgs);
		return "modules/tab/tabActiveImgsForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabActiveImgs:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabActiveImgs tabActiveImgs,HttpServletRequest request) {
		String imgsrc=  tabActiveImgs.getImgsrc();
		if (tabActiveImgs.getImgsrc().indexOf("http")  == -1 ) {
			tabActiveImgs.setImgsrc("https://file.twcreaotr.com/"+tabActiveImgs.getImgsrc());
			tabActiveImgs.setImgsrc1("https://file.twcreaotr.com/"+tabActiveImgs.getImgsrc1());
			System.out.println("原图片链接："+tabActiveImgs.getImgsrc());
			String str = FileUtil.downloadFile(tabActiveImgs.getImgsrc(),"/www/wwwroot/file.e-creatoerzw.com/newPic/");
			String str1 = FileUtil.downloadFile(tabActiveImgs.getImgsrc1(),"/www/wwwroot/file.e-creatoerzw.com/newPic/");
			tabActiveImgs.setImgsrc(str);
			tabActiveImgs.setImgsrc1(str1);
		}
		tabActiveImgsService.save(tabActiveImgs);
		return renderResult(Global.TRUE, text("保存tab_active_imgs成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabActiveImgs:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabActiveImgs tabActiveImgs) {
		tabActiveImgsService.delete(tabActiveImgs);
		return renderResult(Global.TRUE, text("删除tab_active_imgs成功！"));
	}
	
}