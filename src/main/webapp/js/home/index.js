var app = angular.module('app', []).controller('indexController', function($scope, $http){
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

	//广告
	$("#fashions .ImgList").eq(0).show().siblings("div").hide();
	var _advIndex = 0;
	var _advTimer = null;
	$("ul.Button li").hover(function(){
		clearInterval(_advTimer);
		_advIndex = $(this).index();
		$(this).addClass("hover").siblings().removeClass("hover");
		$("#fashions .ImgList").eq(_advIndex).fadeIn().siblings("div").fadeOut();
	}, function(){
		autoPlay();
	});
	function autoPlay(){
		_advTimer = setInterval(function(){
			_advIndex++;
			if(_advIndex<5) {
				if(_advIndex==4){_advIndex=-1}
				$("ul.Button li").eq(_advIndex).addClass("hover").siblings().removeClass("hover");
				$("#fashions .ImgList").eq(_advIndex).fadeIn().siblings("div").fadeOut();
			}
		}, 2000);
	};
	autoPlay();

});