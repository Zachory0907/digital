var app = angular.module('app', []).controller(
		'cartController',
		function($scope, $http, queue) {
			$scope.msg = "欢迎";
			$scope.TYPE = null;
			$scope.TYPENAME = "";
			$scope.Types = 0;
			$scope.allGoods = [];
			$scope.showPics = "";
			$scope.chooseNum = 0;
			$scope.choosePrice = 0;
			$scope.chooseItems = [];
			
			$scope.toPay = function(){
				var ids = [];
				for(var i=0; i<$scope.allGoods.length; i++){
					for(var j=0; j<$scope.allGoods[i].goods.length; j++){
						if($scope.allGoods[i].goods[j].checked == true){
							ids.push($scope.allGoods[i].goods[j].C_ID);
						}
					}
				}
				var a = ids.toString();
				$http.get("./toPay?ids=" + a)
				.success(function(data) {
					if (data.status == "ok"){
						var url = "../pay/index";
						location.href = url;
//						alert("成功");
					}
	    			else if(data.status == "error"){
	    				alert("删除失败！");
	    			}
				}).error(function() {
					alert("网络错误");
				});
			};
			
			$scope.chooseGood = function(){
				$scope.chooseNum = 0;
				$scope.choosePrice = 0;
				for(var i=0; i<$scope.allGoods.length; i++){
					for(var j=0; j<$scope.allGoods[i].goods.length; j++){
						if($scope.allGoods[i].goods[j].checked){
							$scope.chooseNum += 1;
							$scope.choosePrice += parseFloat($scope.allGoods[i].goods[j].PRICE);
						}
					}
				}
			};
			
			$scope.toGood = function(i){
				var url = "../goods/indes?id=" + i.good.C_ID;
				location.href = url;
			};
			
			$scope.deleteIt = function(i){
				$http.get("./deleteIt?id=" + i.good.C_ID)
				.success(function(data) {
					if (data.status == "ok"){
						var url = "./index?type=" + $scope.TYPE;
						location.href = url;
					}
	    			else if(data.status == "error"){
	    				alert("删除失败！");
	    			}
				}).error(function() {
					alert("网络错误");
				});
			};
			
			$scope.likeIt = function(i){
				$http.get("./likeIt?id=" + i.good.C_ID)
				.success(function(data) {
					if (data.status == "ok"){
						var url = "./index?type=" + $scope.TYPE;
						location.href = url;
					}
	    			else if(data.status == "error"){
	    				alert("删除失败！");
	    			}
				}).error(function() {
					alert("网络错误");
				});
			};

			var getTypes = function() {
				if ($scope.TYPE == 0) {
					$scope.TYPENAME = "购物车";
				}else{
					$scope.TYPENAME = "所有订单";
				}
				$http.get("./getTypes?status="+$scope.TYPE)
					.success(function(data) {
						if (data != null){
							for(var i=0; i<data.length; i++){
								var opt = "";
								for(var j=0; j<data[i].goods.length; j++){
									data[i].goods[j].OPTIONS = JSON.parse(data[i].goods[j].OPTIONS);
									var props = [];
									for (var k in data[i].goods[j].OPTIONS) {
										props.push(data[i].goods[j].OPTIONS[k].type);
									}
									data[i].goods[j].props = props.join(" ");
								}
							}
							$scope.allGoods = data;
						}
		    			else{
		    				location.href = "../home/login";
		    			}
					}).error(function() {
						alert("网络错误");
					});
			};

			var getURLInfo = function(q) {
				var url = window.location.href;
				var reg = /type=\d+/g;
				var match = reg.exec(url);
				if (match) {
					$scope.TYPE = url.substr(match.index).substr(5);
					if (q)
						q.success();
				} else {
					alert("网络错误！");
					if (q)
						q.error();
				}
			}

			var preparing = function() {
				queue.add(getURLInfo).add(getTypes).finish(function() {
				}).start(function() {
				})
			};

			preparing();

		});