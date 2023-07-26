package com.jeesite.modules.tab.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.api.ApiController;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.tab.entity.TabTaskjJob;
import com.jeesite.modules.tab.entity.TabUserData;
import com.jeesite.modules.tab.entity.TabYuangongData;
import com.jeesite.modules.tab.service.TabTaskjJobService;
import com.jeesite.modules.tab.service.TabUserDataService;
import com.jeesite.modules.tab.service.TabYuangongDataService;

/**
 * tab_yuangong_dataController
 * @author 1
 * @version 2022-03-22
 */
@Controller
@RequestMapping(value = "${adminPath}/tab/tabYuangongData")
public class TabYuangongDataController extends BaseController {

	@Autowired
	private TabYuangongDataService tabYuangongDataService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public TabYuangongData get(String rowid, boolean isNewRecord) {
		return tabYuangongDataService.get(rowid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("tab:tabYuangongData:view")
	@RequestMapping(value = {"list", ""})
	public String list(TabYuangongData tabYuangongData, Model model) {
		model.addAttribute("tabYuangongData", tabYuangongData);
		return "modules/tab/tabYuangongDataList";
	}
	@RequestMapping(value = { "home3", "" })
	public String home3(HttpServletRequest servletRequest) {
		return "modules/daili/home3";

	}
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("tab:tabYuangongData:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<TabYuangongData> listData(TabYuangongData tabYuangongData, HttpServletRequest request, HttpServletResponse response) {
		

		String  id = 	UserUtils.getLoginInfo().getId();
		

		User user   =  userService.get(id);
		
		String  acc= user.getLoginCode();
		if (!acc.equals("admin")) {
			
		tabYuangongData.setDaili(acc);		
		}
		tabYuangongData.setPage(new Page<>(request, response));
		Page<TabYuangongData> page = tabYuangongDataService.findPage(tabYuangongData);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("tab:tabYuangongData:view")
	@RequestMapping(value = "form")
	public String form(TabYuangongData tabYuangongData, Model model) {
		model.addAttribute("tabYuangongData", tabYuangongData);
		
		
		List<DictData>  dictDatas  = DictUtils.getDictList("qxmc");		
		
		
		
		List<DictData> datas  =   new ArrayList<DictData>();
		

		String  id = 	UserUtils.getLoginInfo().getId();
		

		User user   =  userService.get(id);
		
		String  acc= user.getLoginCode();
		
		TabUserData  tabUserData  =  new TabUserData();
		tabUserData.setSysuserid(acc);
		
		List<TabUserData> tabUserDatas  =   tabUserDataService.findList(tabUserData);
		
		if (tabUserDatas.size() >  0  ) {
				String qx =   tabUserDatas.get(0).getQuanxian();
				if (!StringUtils.isBlank(qx)) {
					String[]  ll = qx.split(",");
					for (String string : ll) {
						
					for (DictData dictData : dictDatas) {
						System.out.println(dictData.getDictValue());
						if (string.endsWith(dictData.getDictValue())  ) {
							
							datas.add(dictData) ; 
							break;
						}
						}
					}
					
				}
		}
		
		model.addAttribute("datas", datas);
		
		
		
		
		
		return "modules/tab/tabYuangongDataForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("tab:tabYuangongData:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated TabYuangongData tabYuangongData) {
		
		if (tabYuangongData.getIsNewRecord()) {
			
			
			String  id = 	UserUtils.getLoginInfo().getId();
			

			User user   =  userService.get(id);
			String  acc= user.getLoginCode();
			System.out.println(acc);
			tabYuangongData.setDaili(acc);
			
			TabUserData  tabUserData  =  new TabUserData();
			tabUserData.setSysuserid(acc);
			
			List<TabUserData> tabUserDatas  =   tabUserDataService.findList(tabUserData);
			
			if (tabUserDatas.size() >  0  ) {

				tabUserData =      tabUserDataService.get(tabYuangongData.getQtzh());
				
				if(tabUserData== null ) {
				
				  	tabUserData = new TabUserData();
				tabUserData.setParentid(tabUserDatas.get(0).getRowid());
				tabUserData.setShangjilink(tabUserDatas.get(0).getRowid());
				tabUserData.setUserleval(0L);
				tabUserData.setPassword(tabYuangongData.getQtmm());
				tabUserData.setUsername(tabYuangongData.getQtzh());
				tabUserData.setRowid(tabYuangongData.getQtzh());
				tabUserData.setTycs(0L);
				tabUserData.setSumMember(0D);
				tabUserData.setUsertype("2");

				tabUserData.setCurrentmoney(0D);
				tabUserData.setTotalmoney(0D);
				tabUserData.setIsNewRecord(true);
				tabUserData.setCreatetime(ApiController. getjndDate((new Date())));
				tabUserData.setVip(0L);
				tabUserData.setQuanxian(tabYuangongData.getQuanxian());
				tabUserData.setCodes(new ApiController(). getCode() + new ApiController().getCode());
				tabUserData.setStatus1("2");
				tabUserDataService.save(tabUserData);

				
				
				TabTaskjJob tabTaskjJob  = new TabTaskjJob(); 
				tabTaskjJob.setRowid(UUID.randomUUID().toString());
				tabTaskjJob.setUserid(tabUserData.getRowid());
				tabTaskjJob.setShangjis(tabUserData.getShangjilink()); 
				tabTaskjJob.setCreatetime(new Date());
				tabTaskjJob.setStatus1("1");
				tabTaskjJob.setIsNewRecord(true);
				
				tabTaskjJobService.save(tabTaskjJob);
				
				
				
				}else {

					
				tabUserData.setPassword(tabYuangongData.getQtmm());
				tabUserData.setSumMember(0D);
				tabUserData.setUsertype("2");

 				tabUserData.setQuanxian(tabYuangongData.getQuanxian());
 				tabUserDataService.save(tabUserData);


				}
				
				
				// 开始更新

			  
			}else {
				
			}
			
			
			
		}
		
		tabYuangongDataService.save(tabYuangongData);
		
		
		
		return renderResult(Global.TRUE, text("保存tab_yuangong_data成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("tab:tabYuangongData:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(TabYuangongData tabYuangongData) {
		tabYuangongDataService.delete(tabYuangongData);
		return renderResult(Global.TRUE, text("删除tab_yuangong_data成功！"));
	}
	
	@Autowired
	private  TabUserDataService tabUserDataService ; 
	
	@Autowired
	private UserService  userService ;
	
	@Autowired
	private TabTaskjJobService tabTaskjJobService ; 
	
	
}