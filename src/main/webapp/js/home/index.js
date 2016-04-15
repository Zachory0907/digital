angular.module('app', []).controller('indexController', function($scope, $http){
	$scope.msg = "欢迎";

	/*鼠标放到新闻上显示效果*/
	$("#news ul li").mouseover(function(){
		$(this).addClass("newBarHover");
	}).mouseout(function(){
		$(this).removeClass("newBarHover");
	});
});