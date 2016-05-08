var app = angular.module('app', []).controller('goodsController', function($scope, $http, queue){
	$scope.msg = "欢迎";
	$scope.bigNo = "../images/goods/big2.jpg";
	$scope.totalPrice=0;
	$scope.number = 1;
	$scope.good = {};
	$scope.shop = {};
	$scope.showPics = [];
	$scope.ys="";
	$scope.bb="";
	$scope.fs="";
	
	$scope.calcuTotal = function(opt){
		$scope.totalPrice = (parseFloat($scope.totalPrice) + parseFloat(opt))*$scope.number;
	};
	
	/*$scope.cusOpt = function(i){
		calcuTotal(i.o.price, 0);
	};*/
	
	/*大图随小图变化而变化*/
	$scope.s2b = function(i){
		$scope.bigNo = i.p;
		console.log($scope.bigNo);
		if (!$scope.$$phase) {
			$scope.$apply();
		}
	};

	/*大图随小图变化而变化*/
	/*$("#goodsContainer .g-box .item-preview .good-list .good-items ul li img").hover(function(){
		var index = $(this).parent().index();
		$scope.bigNo = $scope.showPics[index];
		console.log($scope.bigNo);
		if (!$scope.$$phase) {
			$scope.$apply();
		}
	});*/
	$scope.test = function(){
		alert($scope.ys+$scope.bb+$scope.fs);
	}
	
	var getShop = function(q){
		$http.get("./getShop?id=" + $scope.good.G_SHOP).success(
				function(data) {
					$scope.shop = data;
				})
			.error(function() {
				alert("网络错误");
			});
	};

	var getGood = function(q){
		$http.get("./getGood?id=" + $scope.ID).success(
				function(data) {
					$scope.totalPrice = data.G_COST; 
					data.G_DISCONT = JSON.parse(data.G_DISCONT);
					data.G_OPTIONS = JSON.parse(data.G_OPTIONS);
					$scope.good = data;
					$scope.showPics = data.G_PHOTOS.split(",");
					if(q)
						q.success();
				})
			.error(function() {
				alert("网络错误");
				if(q)
					q.error();
			});
	};
	
	var getURLInfo = function(q) {
		var url = window.location.href;
		var reg = /id=\d+/g;
		var match = reg.exec(url);
		if (match) {
			$scope.ID = url.substr(match.index).substr(3);
			if(q)
				q.success();
		} else {
			alert("网络错误！");
			if(q)
				q.error();
		}
	};
	
	var preparing = function(){
		queue
		.add(getURLInfo)
		.add(getGood)
		.add(getShop)
		.finish(function(){
		}).start(function(){
		})
	};
	
	preparing();

});