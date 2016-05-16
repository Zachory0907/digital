var app = angular.module('app', []).controller('registerController', function($scope, $http){
	$scope.msg = "欢迎";
	$scope.user = {};
	$scope.send = 0;
	$scope.mailMessage = "";
	
	var sendEmail = function(CHECK){
		$scope.send = 1;
		$http.get("./sendMail?email=" + $scope.user.EMAIL + "&check=" + CHECK).success(
				function(data) {
					if (data.status == "ok")
						$scope.mailMessage = "邮件已成功发送至您的邮箱，请验证！"
					else
						$scope.mailMessage = "邮件发送失败！"
				})
			.error(function() {
				alert("网络错误");
				$scope.mailMessage = "邮件发送失败！"
			});
	};
	
	$scope.regist = function(){
		var u = $scope.user;
		if(u.NICKNAME == null){
			alert("昵称不能为空");
			return;
		}else if(u.PASSWORD == null){
			alert("密码不能为空");
			return;
		}else if(u.EMAIL == null){
			alert("邮箱不能为空");
			return;
		}else if(u.TEL == null){
			alert("联系方式不能为空");
			return;
		}else if(u.HEAD == null){
			u.HEAD = "../images/head/mr.jpg";
		}else if(u.PASSWORD != $scope.confirmPass){
			alert("两次密码不一致！");
		}else{
			$http.post("./regist", u).success(
				function(data) {
					sendEmail(data);
				})
			.error(function() {
				alert("网络错误");
			});
		}
	};
});