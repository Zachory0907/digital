angular.module('app', []).controller('indexController', function($scope, $http){
	$scope.msg = "Angular is on……";
	$scope.table = {};
	$scope.testCheck = function(){
		$http.get("./testMysql")
			.success(function(data){
				$scope.table = data;
			})
			.error(function(){
				alert("网络错误！");
			});
	};
});