var app = angular.module('app', []).controller('loginController', function($scope, $http){

	$scope.msg = "欢迎";
	$scope.u = {};


	window.onresize = function(){
		var Height = document.documentElement.clientHeight;//获得可视区的高度
		var Main = document.getElementById("Main");
		Main.style.height = Height+"px";//将可视区的高度给了Main
	};

	$("#Content .C_box .choose ul li").click(function(){
		var _left = $(this).position().left;
		$("p.Line").stop();
		$("p.Line").animate({left:_left}, 350);

		var index = $(this).index();
		$(".Method").eq(index).show().siblings(".Method").hide();

		$(this).find("a").addClass("ys").parent().siblings().find("a").removeClass("ys");
	});

	$scope.submit = function() {
		//$("#Content .C_box .Method .Tip").show();
		var user = $scope.u;
		if (isEmpty(user.logname)) {
			alert("请输入用户名");
			$("#username").focus();
			return false;
		}
		if (isEmpty(user.logpass)) {
			alert("请输入密码");
			$("#password").focus();
			return false;
		}
		
		$http.post("./loginValidate", user).success(function(data){
			if (data.status == "ok")
				location.href = "/digital/home/index";
			else
				alert("登录失败！");
		}).error(function(){
			alert("网络错误！");
		});
	}

	var isEmpty = function(val) {
		val = $.trim(val);
		if (val == null) {
			return true;
		}
		if (val == undefined || val == 'undefined') {
			return true;
		}
		if (val == "") {
			return true;
		}
		if (val.length == 0) {
			return true;
		}
		return false;
	}
});