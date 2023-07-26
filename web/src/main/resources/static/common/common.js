/*!
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 * 项目自定义的公共JavaScript，可覆盖jeesite.js里的方法
 */
/*!
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * 项目自定义的公共JavaScript，可覆盖jeesite.js里的方法
 */

	$("input").attr("autocomplete","off");

	var FileUrl = "http://39.98.43.234:7079/";

	  function getThisWeekData() {//获得本周周一~周日的年月日  
		  var thisweek = {};
		  var date = new Date();
		  // 本周一的日期
		  date.setDate(date.getDate() - date.getDay() + 1);
		  thisweek.start_day = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() ;

		  // 本周日的日期
		  date.setDate(date.getDate() + 6);
		  thisweek.end_day = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
		  return thisweek
		}
	  function  backPage(){
		  history.back();
	  }
			
	function getImgage(v){
	     if(v){
	   if(v.indexOf("http")>=0){
	    return  v ;
	  }else{ return  FileUrl +v
	  }}else {
	    return  "../image/tx2.png";
	  }
	}

	String.prototype.replaceAll = function(s1, s2) {
	    return this.replace(new RegExp(s1, "gm"), s2);
	}
	
	

	var doAjax = function(url,type,parame,succ,err,isshow) {
		var  ll = null; 
		if(layer){
		ll  = 	layer.msg("loading.....")
		}
		isshow = isshow || 0 ;
		parame.token   =  window.localStorage.getItem("token") || "111";
	     var  date =  new  Date().getTime();
		$.ajax({
			
			url  :  url,
			type  : type  ,
			data : parame,
			headers:{
				
			},
			success : function(data){
				if(ll){
					layer.close(ll)
				}
				succ(data);
			},error : function(data){
				err(data);
			}
		})
	}
	
	function    getPageList (pageCount,curr){
		var   ll =   [] ; 
		var  f  =1; 
		if(pageCount<12){
			for(var i = 1 ;   i <=  pageCount ;i++){
				ll.push(parseInt(i));
			}
		}else {
			var middle  =    pageCount/3;
			if(middle >=5){
				middle  =  5;
			}
				for(var i = 1 ;   i < middle  ;i++){
 					ll.push(parseInt(i));

				}

			
			for(var i = pageCount ;i> (pageCount-5) ;i--){
				ll.push(parseInt(i));


			}
			if(curr!= 1){
				ll.push(parseInt(curr-1));
			}

			ll.push(parseInt(curr));

			ll.push(parseInt(curr+1));

		}
		ll =unique(ll);
		
		return ll.sort(function(a, b){return a - b}); 

	}
	
	
	function unique(arr){            
        for(var i=0; i<arr.length; i++){
            for(var j=i+1; j<arr.length; j++){
                if(arr[i]==arr[j]){         //第一个等同于第二个，splice方法删除第二个
                    arr.splice(j,1);
                    j--;
                }
            }
        }
return arr;
}
	
	$(".uicon-nav-back").click(function(){
		backPage();
		
	})
	
	
	function closeWin(){
				backPage();

	}