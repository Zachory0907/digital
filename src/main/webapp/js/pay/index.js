var app = angular.module('app', []).controller(
		'payController',
		function($scope, $http, queue) {
			$scope.msg = "欢迎";
			$scope.provinces = [ "北京市", "山西省", "山东省", "河南省", "河北省", "湖南省" ];
			$scope.citys = [ "北京市", "山西省", "山东省", "河南省", "河北省", "湖南省" ];
			$scope.counties = [ "北京市", "山西省", "山东省", "河南省", "河北省", "湖南省" ];
			$scope.add = {};

			$scope.addresses = [];
			$scope.goods = [];
			$scope.totalPrice = 0;
			$scope.ids = [];
			
			$scope.payNow = function(){
				var dd = {};
				dd.good = $scope.goods;
				dd.add = $scope.add;
				console.log(dd);
				var ids = $scope.ids.toString();
				alert("ids:"+ids);
				$http.get("./changeStatus?ids="+ids).success(function(data) {
					if(data.status == "ok"){
						alert("支付成功！");
						location.href = "../goods/index?type=1";
					}else{
						alert("支付失败！")
					}
				}).error(function() {
					alert("网络错误");
				});
			};

			$scope.addAddr = function() {
				var addr = {};
				addr.shr = "";
				addr.province = "";
				addr.city = "";
				addr.county = "";
				addr.detail = "";
				addr.tel = "";
				addr.mail = "";
				$scope.addresses.push(addr);
			};

			$scope.removeAddr = function(i) {
				$scope.addresses.splice(i.$index, 1);
			};

			$scope.sureAddr = function(i) {
				$scope.addresses[i.$index];
				getAdd($scope.addresses[i.$index]);
			};

			var getAdd = function(i) {
				//$scope.add = i.province + " " + i.city + " " + i.county + " " + i.detail + " | 联系人: " + i.shr + " | 联系电话：" + i.tel;
				$scope.add = i;
			};

			$scope.saveAddr = function() {
				for (var i = 0; i < $scope.addresses.length; i++) {
					if ($scope.addresses[i].shr == ""
							|| $scope.addresses[i].province == ""
							|| $scope.addresses[i].city == ""
							|| $scope.addresses[i].county == ""
							|| $scope.addresses[i].detail == ""
							|| $scope.addresses[i].tel == ""
							|| $scope.addresses[i].mail == ""
							|| $scope.addresses[i].shr == ""
							|| $scope.addresses[i].province == ""
							|| $scope.addresses[i].city == ""
							|| $scope.addresses[i].county == ""
							|| $scope.addresses[i].detail == ""
							|| $scope.addresses[i].tel == ""
							|| $scope.addresses[i].mail == "") {
						alert("请完善第" + (i + 1) + "条收货地址！");
						return;
					} else {
						for (var i = 0; i < $scope.addresses.length; i++) {
							delete $scope.addresses[i].$$hashKey;
						}
						var addr = JSON.stringify($scope.addresses);
						$http.post("./saveAddr", addr).success(function(data) {
							location.href = "./index"
						}).error(function() {
							alert("失败！");
						});
					}
				}
			};

			var loadAddr = function(q) {
				$http.get("./loadAddr").success(function(data) {
					$scope.addresses = JSON.parse(data.ADDR);
					if (q)
						q.success();
				}).error(function() {
					alert("网络错误");
					if (q)
						q.error();
				});
			};
			
			var loadItems = function(q){
				$http.get("./loadItems").success(function(data) {
					$scope.goods = data;
					for(var i=0; i<data.length; i++){
						$scope.totalPrice += parseFloat(data[i].PRICE);
						$scope.ids.push(data[i].C_ID);
					}
					if (q)
						q.success();
				}).error(function() {
					alert("网络错误");
					if (q)
						q.error();
				});
			};

			var preparing = function() {
				queue.add(loadAddr)
				.add(loadItems)
				.finish(function() {
				}).start(function() {
				})
			};

			preparing();
		});