package com.jeesite.modules.tab.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.utils.FileUtil;
import com.jeesite.modules.tab.entity.TabUserData;
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
import com.jeesite.modules.tab.entity.TabTongdao;
import com.jeesite.modules.tab.service.TabTongdaoService;
import redis.clients.jedis.Jedis;

/**
 * 通道Controller
 * @author 1
 * @version 2022-03-29
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabTongdao")
public class TabTongdaoController extends BaseController {

	@Autowired
	private TabTongdaoService tabTongdaoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabTongdao get(String rowid, boolean isNewRecord) {
		return tabTongdaoService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabTongdao:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabTongdao tabTongdao, Model model) {
		model.addAttribute("tabTongdao", tabTongdao);
		return "modules/tab/tabTongdaoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabTongdao:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabTongdao> listData(TabTongdao tabTongdao, HttpServletRequest request, HttpServletResponse response) {
		tabTongdao.setPage(new Page<>(request, response));
		tabTongdao.setOrderBy(" orderindex asc");
		Page<TabTongdao> page = tabTongdaoService.findPage(tabTongdao);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabTongdao:view")
	@RequestMapping(value = "form")
	public String form(TabTongdao tabTongdao, Model model) {
		model.addAttribute("tabTongdao", tabTongdao);
		return "modules/tab/tabTongdaoForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabTongdao:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabTongdao tabTongdao) {
		/*if(tabTongdao.getImgsrc().indexOf("http") == -1){
			tabTongdao.setImgsrc("https://file.twcreaotr.com/"+tabTongdao.getImgsrc());
			String str = FileUtil.downloadFile(tabTongdao.getImgsrc(),"/www/wwwroot/file.e-creatoerzw.com/newPic/");
			tabTongdao.setImgsrc(str);
		}*/
		tabTongdao.setImgsrc("https://file.twcreaotr.com/newPic/111111111.png.png");
		tabTongdao.setDaishou("2");
		tabTongdao.setDaifu("2");
		tabTongdao.setTongdaoName(tabTongdao.getTongdaoName().toUpperCase());;
		if(tabTongdao.getStatus1() == 0){
			tabTongdao.setShelftime(null);
			tabTongdao.setOffshelftime(null);
		}
		if(tabTongdao.getStatus1() == 1){
			tabTongdao.setShelftime(getjndDate(new Date()));
			tabTongdao.setOffshelftime(null);
		}
		if(tabTongdao.getStatus1() == 2){
			tabTongdao.setShelftime(null);
			tabTongdao.setOffshelftime(getjndDate(new Date()));
		}
		if(StringUtils.isBlank(tabTongdao.getRowid())){
			tabTongdao.setEdu("小额");
		}
		Jedis jedis =  new Jedis("41.72.149.115", 6379);
		jedis.auth("hask071bend");
		jedis.del("Tongdaos");
		jedis.del("Tongdao");
		jedis.quit();
		tabTongdaoService.save(tabTongdao);
		return renderResult(Global.TRUE, text("保存通道成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabTongdao:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabTongdao tabTongdao) {
		tabTongdaoService.delete(tabTongdao);
		return renderResult(Global.TRUE, text("删除通道成功！"));
	}

	@RequestMapping(value = "changeData2")
	@ResponseBody
	public String changeData2(HttpServletRequest request) {
		String rowid =   request.getParameter("rowid");
		String  status =   request.getParameter("status");
		String  dae =   request.getParameter("dae");
		TabTongdao tongdao    = tabTongdaoService.get(rowid);
		tongdao.setStatus1(Integer.parseInt(status));
		if(StringUtils.isBlank(dae)){
			tongdao.setEdu("小额");
		}else{
			tongdao.setEdu(dae);
		}
		tabTongdaoService.save(tongdao);;
		
		
		return renderResult(Global.TRUE, text(" "));
	}



	@RequestMapping(value = "piliangChangeData0")
	@ResponseBody
	public String piliangChangeData0(HttpServletRequest request) {
		String rowids = request.getParameter("rowids");
		String[] split = rowids.split(",");
		for (String rowid : split) {
			TabTongdao tabTongdao = tabTongdaoService.get(rowid);
			tabTongdao.setEdu("小额");
			tabTongdao.setStatus1(0);
			tabTongdaoService.save(tabTongdao);
		}

		return renderResult(Global.TRUE, text(" "));
	}

	@RequestMapping(value = "piliangqingchu")
	@ResponseBody
	public String piliangqingchu(HttpServletRequest request) {
		String rowids = request.getParameter("rowids");
		String[] split = rowids.split(",");
		for (String rowid : split) {
			TabTongdao tabTongdao = tabTongdaoService.get(rowid);
			tabTongdao.setNeworderindex(0L);
			tabTongdaoService.save(tabTongdao);
		}

		return renderResult(Global.TRUE, text(" "));
	}

	@RequestMapping(value = "piliangChangeData2")
	@ResponseBody
	public String piliangChangeData2(HttpServletRequest request) {
		String rowids = request.getParameter("rowids");
		String[] split = rowids.split(",");
		for (String rowid : split) {
			TabTongdao tabTongdao = tabTongdaoService.get(rowid);
			tabTongdao.setStatus1(2);
			tabTongdao.setEdu("小额");
			tabTongdaoService.save(tabTongdao);
		}

		return renderResult(Global.TRUE, text(" "));
	}

	
	//changeData1
	
	@RequestMapping(value = "changeData1")
	@ResponseBody
	public String changeData1(HttpServletRequest request) {
		String rowid =   request.getParameter("rowid");
		String  status =   request.getParameter("status");
		
//		if (status.equals("2")) {
//			
//			TabTongdao tabTongdao = new  TabTongdao();
//			
//			List<TabTongdao>  tabTongdaos =  tabTongdaoService.findList(tabTongdao);
//			
//			for (TabTongdao tabTongdao2 : tabTongdaos) {
//				tabTongdao2.setDaifu("1");
//				tabTongdaoService.save(tabTongdao2);
//			}
//			
//			
//		} 
		


		TabTongdao tongdao    = tabTongdaoService.get(rowid);
		tongdao.setDaifu(status);
		tabTongdaoService.save(tongdao);;
	
		
		
		
		return renderResult(Global.TRUE, text(" "));
	}

	public static Date getjndDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -6);
		return calendar.getTime();
	}
}