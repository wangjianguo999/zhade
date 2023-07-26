var _websocket = null;
// 当前聊天用户sessionId
var selectedSessionId = null;
var baseUrl = "ws://41.72.149.115:8081/api/websocket/customer/";
var selectedUserid = null;
// 当前聊天用户数
var consumerlength = 0;
var music = null
var editor = null;
var ygzh2 = null;
var heartbeatInterval = null
var reconnectTimeOut = null
var isOpen = true
$(function() {
	customerSocket.init();
})

var customerSocket = {

	init: function() {
		customerSocket.page();
		customerSocket.socket();
	},

	page: function() {
		win_width = $(document).width();
		win_height = $(document).height();
		$("#content").height(win_height - 70);
		$("#content_right").width(win_width - $("#content_left").outerWidth());
		$("#content_right_top").height($("#content_right").height() - 300);

		$(window).resize(function() {
			$("#content").height($(window).height() - 70);
			$("#content_right").width($(document).width() - $("#content_left").outerWidth());
			$("#content_right_top").height($("#content_right").height() - 300);
		});

		$(document).keydown(function(event) {
			if (event.keyCode == 13) {
				event.preventDefault();
				customerSocket.send();
			}
		});

		customerSocket.emotion();
	},

	emotion: function() {
		var E = window.wangEditor
		editor = new E('#input_menu', '#msgInput');
		editor.customConfig.menus = [
			'emoticon',
			'fontName',
			'fontSize',
			'bold',
			'italic',
			'underline',
			'foreColor',
			'image'
		];
		editor.customConfig.uploadImgShowBase64 = true;
		editor.customConfig.emotions = [{
			title: '表情',
			type: 'image',
			content: customerSocket.initEmotion()
		}];
		editor.customConfig.onfocus = function() {
			customerSocket.readMessage();
		};
		editor.customConfig.uploadImgServer = "http://41.72.149.115:8081/api/file/socketUpload"; //上传URL
		editor.customConfig.uploadFileName = 'file';
		editor.customConfig.uploadImgHooks = {
			customInsert: function(insertImg, result, editor) {
				// 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
				// insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果
				//debugger;
				// 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：

				var url = result.data;
				console.log(url);
				customerSocket.imgsend(url);
				//insertImg(url);

				// result 必须是一个 JSON 格式字符串！！！否则报错
			}
		}
		editor.create();
	},

	initEmotion: function() {
		var a = [];
		for (var i = 1; i < 76; i++) {
			var temp = {};
			temp.alt = "";
			temp.src = "/api/static/img/emotion/arclist/" + i + ".gif";
			a.push(temp);
		}
		return a;
	},

	socket: function() {
		music = new Audio('/api/static/js/page/abc.mp3');
		ygzh2 = $('#ygzh2').text();
		if ('WebSocket' in window) {
			var urls = baseUrl + ygzh2 + "/" + ygzh2;
			_websocket = new WebSocket(urls);
		} else {
			alert("当前浏览器不支持WebSocket")
		}

		// 连接发生错误时回调
		_websocket.onerror = function(event) {
			console.log(event.data);
			clearInterval(heartbeatInterval)
			clearInterval(reconnectTimeOut)
			console.log("断线重连");
			reconnectTimeOut = setInterval(() => {
				var urls = baseUrl + ygzh2 + "/" + ygzh2;
				_websocket = new WebSocket(urls);
			}, 5000)

		};

		// 建立连接时回调
		_websocket.onopen = function(event) {
			console.log("建立连接时回调");
			$("#kefuStatus").text("在线");
			clearInterval(reconnectTimeOut)
		}

		// 接收到消息时回调su
		_websocket.onmessage = function(event) {

			var json = JSON.parse(event.data);
			console.log("接受消息session:" + json.sessionId + " ,message:" + json.message);
			if (json.message == "1f6a248a49054b228f900dd9357f36bb") {
				return;
			}
			$("#kefuStatus").text("在线");
			/*if (isOpen == true) {
				music.play();
				music.loop = false;
			}*/
			flashTitle();

			if (json.message != "") {
		    let datastr = {
    		    type: 0,
    		    msg:json.message
	        }
    		var newmsg=null;
            let datajson = JSON.stringify(datastr);
    			$.ajax({
    				url: "http://41.72.149.115:8081/api/socketTranslate",
    				type: 'POST',
    				async : false,
    				contentType :'application/json',
    				dataType: "JSON",
    				data: datajson,
    				success: function(arg) {
    					newmsg = arg.msg;

    				},
    			});

    		}
    		console.log("收到的用户消息翻译文："+newmsg);
    		let jsonstr = {
    		    sessionId :json.sessionId,
    		    message : "原文："+json.message+"</br>翻译："+newmsg,
    		    userid : json.userid,
    		    img :json.img
    		}
			customerSocket.recevie(JSON.stringify(jsonstr));
			customerSocket.moveCursorEnd();
		}

		// 连接关闭时回调
		_websocket.onclose = function(event) {
			//alert("连接已关闭")
			console.log("连接关闭时回调");
			$("#kefuStatus").text("离线");
			clearInterval(heartbeatInterval);
			//location.reload();
			/*var urls = "ws://13.246.6.178:8081/api/websocket/customer/"+ygzh2+"/"+ygzh2;
            _websocket = new WebSocket(urls);*/

		}
		heartbeatInterval = setInterval(() => {
			_websocket.send(JSON.stringify({
				"traderid": 10260,
				"type": "Ping"
			}));
		}, 15000)
	},
	moveCursorEnd: function() { // 移动光标到最低端
		$("#content_right_top").children("div:visible").children("div")[[$("#content_right_top").children(
			"div:visible").children("div").length - 1]].scrollIntoView();
	},
	recevie: function(data) {
		var json = JSON.parse(data);
		if (json.messageType == 2) { // 下线消息
			customerSocket.offlineConsumer(json);
		} else { // 输出消息
			customerSocket.addConsumerList(json);
		}
	},

	send: function(img) {
		var sendObj = $('#msgInput').children("div").children("p");
		/*var msg1 = sendObj.html();*/
		var msg1 = sendObj.text();
		console.log("msg1："+sendObj.text());
		if(msg1.length == 0){
		    return;
		}
		let datastr = {
		    type: 1,
		    msg:msg1
		}

        let datajson = JSON.stringify(datastr);
		console.log("send:userid:" + selectedUserid + " ,sessionId:" + selectedSessionId);
		if (editor.txt.html() != "") {
			$.ajax({
				url: "http://41.72.149.115:8081/api/socketTranslate",
				type: 'POST',
				contentType :'application/json',
				dataType: "JSON",
				data: datajson,
				success: function(arg) {
					console.log(arg);
            		if(/.*[\u4e00-\u9fa5]+.*$/.test(arg.msg)) {
            			alert("翻译异常，请重新输入内容！");
            			return;
            		}
					customerSocket.showMessageTab("原文："+sendObj.html()+"</br>翻译："+arg.msg, selectedSessionId, selectedUserid, img);
					_websocket.send(JSON.stringify({
						"sessionId": selectedSessionId,
						"message": arg.msg,
						"userid": selectedUserid,
						"imgurl": img,
					}));
					editor.txt.clear();
				},
			});

		}
		customerSocket.moveCursorEnd();
	},

    imgsend: function(img) {
		customerSocket.showMessageTab("", selectedSessionId, selectedUserid, img);
					_websocket.send(JSON.stringify({
						"sessionId": selectedSessionId,
						"message": '',
						"userid": selectedUserid,
						"imgurl": img,
					}));
					editor.txt.clear();
		customerSocket.moveCursorEnd();
	},

    mbsend: function(img) {
        var mbvalue= $("#mb option:selected").val();
        var mbtext= $("#mb option:selected").text();
        console.log(mbvalue);
        console.log(mbtext);
		let datastr = {
		    type: 1,
		    msg:mbvalue
		}
        customerSocket.showMessageTab("原文："+mbtext+"</br>翻译："+mbvalue, selectedSessionId, selectedUserid, img);
        _websocket.send(JSON.stringify({
            "sessionId": selectedSessionId,
            "message": mbvalue,
            "userid": selectedUserid,
            "imgurl": img,
        }));
        editor.txt.clear();

		customerSocket.moveCursorEnd();
	},

	closets: function() {
		console.log("关闭消息提醒");
		isOpen = false;
	},

	opents: function() {
		console.log("开启消息提醒");
		isOpen = true;
	},


	addConsumerList: function(json) {
		if (!($("#list_div_" + json.userid).length > 0)) { //客户列表去重
			consumerlength++; // 记录当前聊天用户数
			if ($("#content_left").find("div").length > 0) {
				$("#content_left").append("<div msg_num=0 id='list_div_" + json.userid +
					"' onmouseover='customerSocket.showRemoveIco(\"" + json.userid +
					"\",this);' onmouseout='customerSocket.hideRemoveIco(\"" + json.userid +
					"\",this);' onclick='customerSocket.clickConsumer(\"" + json.userid +
					"\",this);' style='position: relative; height: 50px; line-height: 3; border-radius: 3px; margin: 4px; cursor: pointer;'><img src='/img/timg.jpg' style='border-radius: 5px; position: absolute; left: 10px; top: 3px;' width='42px' height='42px'></img><span>用户账号" +
					json.userid + "</span><label id='msgTip_" + json.userid +
					"' class='msgTip'>0</label></div>");
			} else {
				$("#content_left").append("<div msg_num=0 id='list_div_" + json.userid +
					"' onmouseover='customerSocket.showRemoveIco(\"" + json.userid +
					"\",this);' onmouseout='customerSocket.hideRemoveIco(\"" + json.userid +
					"\",this);' onclick='customerSocket.clickConsumer(\"" + json.userid +
					"\",this);' class='selected' style='position: relative; height: 50px; line-height: 3; border-radius: 3px; margin: 4px; cursor: pointer;'><img src='/img/timg.jpg' style='border-radius: 5px; position: absolute; left: 10px; top: 3px;' width='42px' height='42px'></img><span>用户账号" +
					json.userid + "</span><label id='msgTip_" + json.userid +
					"' class='msgTip'>0</label></div>");
			}
			$("#list_div_" + json.userid).append("<label id='label_remove_" + json.userid +
				"' style='cursor: pointer;display: none;position: absolute;right: 12px;text-align: center;color: white;background: #171616d9;height: 16px;line-height: 1;border-radius: 10px;padding: 3px 6px 0 5px;font-size: 12px;top: 15px;' onclick='customerSocket.deleteConsumerList(\"" +
				json.userid + "\",this);'>X</label>");
		}
		customerSocket.recevieMessageTab(json);
	},

	recevieMessageTab: function(json) {
		var html = "";

		if ($("#msg_div_" + json.userid).length > 0) {
			/*html += "<div style='clear:both'>";
			html += "<div style='float: right;background-color: #ececec;padding: .5em .6em;border-radius: 5px;float: left;margin-right: 30%;margin-top: 10px;margin-bottom: 5px;'>"+json.message+"</div>"
			html += "</div><br/>";*/
			html += "<div style='clear:both'>";
			if (json.img) {
				html +=
					"<div style='float: right;background-color: #ececec;padding: .5em .6em;border-radius: 5px;float: left;margin-right: 30%;margin-top: 10px;margin-bottom: 5px;'>" +
					"<img src='" + json.img + "'" + "style='width: 200px;height: auto;' >" + "</div>"
			} else {
				html +=
					"<div style='float: right;background-color: #ececec;padding: .5em .6em;border-radius: 5px;float: left;margin-right: 30%;margin-top: 10px;margin-bottom: 5px;'>" +
					json.message + "</div>"
			}
			html += "</div><br/>";
			$("#msg_div_" + json.userid).append(html);
		} else {
			if (consumerlength == 1) {
				html += "<div id='msg_div_" + json.userid +
					"' style='height: 100%; overflow:auto;padding: 0 15px 0 15px;'>";
				selectedSessionId = json.sessionId;
				selectedUserid = json.userid;
			} else {
				html += "<div id='msg_div_" + json.userid +
					"' style='height: 100%; overflow:auto;padding: 0 15px 0 15px;display: none;'>";
			}
			html += "<div style='clear:both'>";
			/*html += "<div style='float: right;background-color: #ececec;padding: .5em .6em;border-radius: 5px;float: left;margin-right: 30%;margin-top: 10px;margin-bottom: 5px;'>"+json.message+"</div>"
			html += "</div><br/>";
			html += "</div>";*/
			if (json.img) {
				html +=
					"<div style='float: right;background-color: #ececec;padding: .5em .6em;border-radius: 5px;float: left;margin-right: 30%;margin-top: 10px;margin-bottom: 5px;'>" +
					"<img src='" + json.img + "'" + "style='width: 200px;height: auto;' >" + "</div>"
			} else {
				html +=
					"<div style='float: right;background-color: #ececec;padding: .5em .6em;border-radius: 5px;float: left;margin-right: 30%;margin-top: 10px;margin-bottom: 5px;'>" +
					json.message + "</div>"
			}
			html += "</div><br/>";
			html += "</div>";
			$("#content_right_top").append(html);
		}

		// 设置信息数和消息提示
		var old_m = $("#list_div_" + json.userid).attr("msg_num");
		var new_m = parseInt(old_m) + 1;
		$("#list_div_" + json.userid).attr("msg_num", new_m);
		customerSocket.messageTip(json);
	},

	//设置消息提示
	messageTip: function(json) {
		$("#msgTip_" + json.userid).show();
		$("#msgTip_" + json.userid).html($("#list_div_" + json.userid).attr("msg_num"));
	},

	// 在客户列表点击用户
	clickConsumer: function(sessionId, obj) {
		console.log("点击切换用户sessionid:" + sessionId);
		selectedSessionId = sessionId;
		selectedUserid = sessionId;
		// tab页设置
		$("#content_right_top").children("div").hide();
		$("#msg_div_" + sessionId).show();
		// 客户列表设置
		$("#content_left").children("div").removeClass("selected");
		$(obj).addClass("selected");

		customerSocket.readMessage(sessionId);
	},

	// 鼠标移到列表上，显示删除ICO
	showRemoveIco: function(session_id, obj) {
		$("#label_remove_" + session_id).show();
	},
	// 鼠标移到列表上，隐藏删除ICO
	hideRemoveIco: function(session_id, obj) {
		$("#label_remove_" + session_id).hide();
	},

	// 删除列表用户
	deleteConsumerList: function(session_id, obj) {
		$("#list_div_" + session_id).remove();
		$("#msg_div_" + session_id).remove();

		$("#content_left").children("div:first").addClass("selected");
		$("#content_right_top").children("div:first").show();
		event.stopPropagation();
	},

	// 消息已读，去除红点提示
	readMessage: function(sessionId) {
		if (sessionId) {
			$("#msgTip_" + sessionId).hide();
			$("#list_div_" + sessionId).attr("msg_num", 0);
		} else {
			$("#msgTip_" + selectedSessionId).hide();
			$("#list_div_" + selectedSessionId).attr("msg_num", 0);
		}
	},

	// 用户下线消息处理:1.图像置灰 2.在列表中往后排
	offlineConsumer: function(json) {

	},

	// 客服自己发送的消息显示在tab页面上
	showMessageTab: function(message, consumerSessionId, selectedUserid, img) {
		console.log("客服发送消息到自己页面selectedUserid" + selectedUserid + " ,consumerSessionId" + consumerSessionId +
			" ,message：" + message + " ,img:" + img)
		var html = "";
		html += "<div style='clear:both'>";
		if (img) {
			html +=
				"<div style='float: right;background-color: #89e871;padding: .5em .6em;border-radius: 5px;margin-left: 30%;margin-top: 10px;margin-bottom: 5px;'>" +
				"<img src='" + img + "'" + "style='width: 200px;height: auto;' >" + "</div>"
		} else {
			html +=
				"<div style='float: right;background-color: #89e871;padding: .5em .6em;border-radius: 5px;margin-left: 30%;margin-top: 10px;margin-bottom: 5px;'>" +
				message + "</div>"
		}
		html += "</div><br/>";
		$("#msg_div_" + selectedUserid).append(html);
	}

}


//================================================浏览器标题消息提示开始==========================================
var isWindowFocus = true;

function focusin() {
	isWindowFocus = true;
}

function focusout() {
	isWindowFocus = false;
}

//注册焦点变化监听器
if ("onfocusin" in document) { //for IE
	document.onfocusin = focusin;
	document.onfocusout = focusout;
} else {
	window.onblur = focusout;
	window.onfocus = focusin;
}

var flag = true;

function flashTitle() {
	//仅窗口不在焦点时闪烁title，回到焦点时停止闪烁并将title恢复正常
	if (isWindowFocus) { //当前处于焦点
		document.title = "客服";
		return; //退出循环
	}

	if (flag) {
		document.title = "【您有新的消息】";
		flag = false;
	} else {
		document.title = "【　　　　　　】";
		flag = true;
	}
	setTimeout("flashTitle()", 10); //循环
}
//================================================浏览器标题消息提示结束==========================================


Date.prototype.Format = function(fmt) {
	var o = {
		"M+": this.getMonth() + 1, // 月份
		"d+": this.getDate(), // 日
		"h+": this.getHours(), // 小时
		"m+": this.getMinutes(), // 分
		"s+": this.getSeconds(), // 秒
		"q+": Math.floor((this.getMonth() + 3) / 3), // 季度
		"S": this.getMilliseconds() // 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : ((
			"00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}