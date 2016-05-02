angular.module('app', []).controller('goodsController', function($scope, $http){
	$scope.msg = "欢迎";
	$scope.bigNo = "../images/goods/big2.jpg";
	$scope.ys="";
	$scope.bb="";
	$scope.fs="";

	/*大图随小图变化而变化*/
	$("#goodsContainer .g-box .item-preview .good-list .good-items ul li img").hover(function(){
		var index = $(this).parent().index();
		$scope.bigNo = "../images/goods/big" + (index+1) + ".jpg";
		if (!$scope.$$phase) {
			$scope.$apply();
		}
	});
	$scope.test = function(){
		alert($scope.ys+$scope.bb+$scope.fs);
	}
});