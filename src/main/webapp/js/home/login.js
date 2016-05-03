angular.module('app', []).controller('loginController', function($scope, $http){

	$scope.msg = "欢迎";
	$scope.user = {};


	window.onresize = function(){
		debugger;
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

/*	$scope.submit = function() {
		$("#Content .C_box .Method .Tip").show();
		var username = $("#username").val();
		var password = $("#password").val();

		if (isEmpty(username)) {
			alert("请输入用户名");
			$("#username").focus();
			return false;
		}
		if (isEmpty(password)) {
			alert("请输入密码");
			$("#password").focus();
			return false;
		}
		$http.post("./login", $scope.user).success(function(data){
			alert("登录成功！")
		}).error(function(){
			alert("登录失败！");
		});
	}

	$scope.isEmpty = function(val) {
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
	}*/
});