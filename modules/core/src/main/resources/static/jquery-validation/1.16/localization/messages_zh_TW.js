(function ($) {
	$.extend( $.validator.messages, {
		required: "必須填寫",
		remote: "請修正此欄位",
		email: "請輸入有效的電子郵件",
		url: "請輸入有效的網址",
		date: "請輸入有效的日期",
		dateISO: "請輸入有效的日期 (YYYY-MM-DD)",
		number: "請輸入正確的數值",
		digits: "只可輸入數字",
		equalTo: "請重複輸入一次",
		maxlength: $.validator.format( "最多 {0} 個字" ),
		minlength: $.validator.format( "最少 {0} 個字" ),
		rangelength: $.validator.format( "請輸入長度為 {0} 至 {1} 之間的字串" ),
		range: $.validator.format( "請輸入 {0} 至 {1} 之間的數值" ),
		max: $.validator.format( "請輸入不大於 {0} 的數值" ),
		min: $.validator.format( "請輸入不小於 {0} 的數值" ),
		errorMessage: "您填寫的信息有誤，請根據提示修正。",
		userName: "登錄賬號只能包括中文字、英文字母、數字和下劃線",
		realName: "姓名只能爲2-30個漢字",
		abc: "請輸入字母數字或下劃線",
		noEqualTo: "請再次輸入不同的值",
		mobile: "請正確填寫您的手機號碼，只能是13,14,15,16,17,18,19號段",
		simplePhone: "請正確填寫您的電話號碼，固話爲區號(3-4位)號碼(7-9位)",
		phone: "請正確填寫您的電話號碼，固話爲區號(3-4位)號碼(7-9位),手機爲13,14,15,16,17,18,19號段",
		zipCode: "請正確填寫您的郵政編碼",
		integer: "請輸入一個整數",
		ipv4: "請輸入一個有效的 IP v4 地址",
		ipv6: "請輸入一個有效的 IP v6 地址",
		qq: "請正確填寫您的QQ號碼",
		isBlank: "不能輸入全空格",
		idcard: "請輸入正確的身份證號碼(15-18位)"
	});
}(jQuery));
