angular.module('app', []).controller('indexController', function($scope, $http){
	$scope.msg = "欢迎";

	/*鼠标放到新闻上显示效果*/
	$("#news ul li").mouseover(function(){
		$(this).addClass("newBarHover");
	}).mouseout(function(){
		$(this).removeClass("newBarHover");
	});
 
	/*news随鼠标动而变化*/
	$("#news ul li").hover(function(){
		var index = $(this).index();
		$(".Content").eq(index).show().siblings(".Content").hide();
		$(this).find("a").addClass("ys").parent().siblings().find("a").removeClass("ys");
		$("#news ul li").eq(index).addClass("newBarHover").siblings().removeClass("newBarHover");
	});

	/*news选中背景变化*/
	$(".Content dl").mouseover(function(){
		$(this).addClass("hover");
	}).mouseout(function(){
		$(this).removeClass("hover");
	});

	/*top phone随鼠标动而变化*/
	$("#products .phone ul li").hover(function(){
		var index = $(this).index();
		$("#products .phone .container").eq(index).show().siblings("#products .phone .container").hide();
	});

	/*top tv随鼠标动而变化*/
	$("#products .tv ul li").hover(function(){
		var index = $(this).index();
		$("#products .tv .container").eq(index).show().siblings("#products .tv .container").hide();
	});

});