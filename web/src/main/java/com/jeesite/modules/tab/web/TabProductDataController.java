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
import com.jeesite.modules.tab.entity.TabProductData;
import com.jeesite.modules.tab.service.TabProductDataService;

/**
 * tab_product_dataController
 * @author 1
 * @version 2021-12-18
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabProductData")
public class TabProductDataController extends BaseController {

	@Autowired
	private TabProductDataService tabProductDataService;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabProductData get(String rowid, boolean isNewRecord) {
		return tabProductDataService.get(rowid, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabProductData:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabProductData tabProductData, Model model) {
		model.addAttribute("tabProductData", tabProductData);
		return "modules/tab/tabProductDataList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabProductData:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabProductData> listData(TabProductData tabProductData, HttpServletRequest request, HttpServletResponse response) {
		//tabProductDataService.setNames();
		tabProductData.setPage(new Page<>(request, response));
		Page<TabProductData> page = tabProductDataService.findPage(tabProductData);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabProductData:view")
	@RequestMapping(value = "form")
	public String form(TabProductData tabProductData, Model model) {
		model.addAttribute("tabProductData", tabProductData);
		return "modules/tab/tabProductDataForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabProductData:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabProductData tabProductData,HttpServletRequest request) {

		String imgsrc=  tabProductData.getImgsrc();
		if (imgsrc.indexOf("http")  == -1 ) {

			tabProductData.setImgsrc("https://file.twcreaotr.com/"+imgsrc);
			String str = FileUtil.downloadFile(tabProductData.getImgsrc(),"/www/wwwroot/file.e-creatoerzw.com/newPic/");
			tabProductData.setImgsrc(str);
			/*String path =    request.getRealPath("/userfiles/fileupload");
			System.out.println(path);
			String[] split =   imgsrc.split("fileupload");

			File file  = new File(path + split[1]);
			String  pp =  HttpServletRequestUtils.uploadFiles(file);

			System.err.println(split[1]);
			tabProductData.setImgsrc(pp);*/

		}
		tabProductData.setBuymoney(tabProductData.getPrice());
		tabProductDataService.save(tabProductData);
		return renderResult(Global.TRUE, text("保存tab_product_data成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabProductData:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabProductData tabProductData) {
		tabProductDataService.delete(tabProductData);
		return renderResult(Global.TRUE, text("删除tab_product_data成功！"));
	}

}