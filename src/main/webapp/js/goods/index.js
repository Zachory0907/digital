var app = angular.module('app', []).controller(
		'goodsController',
		function($scope, $http, queue) {
    $scope.msg = "欢迎";
    $scope.bigNo = "";
    $scope.totalPrice = 0;
    $scope.number = 1;
    $scope.good = {};
    $scope.shop = {};
    $scope.showPics = [];
    $scope.ys = "";
    $scope.bb = "";
    $scope.fs = "";

    $scope.buy = function(i){
        var add = {};
        add.good = $scope.good.G_ID;
        add.shop = $scope.shop.S_ID;
        var gLength = 0;
        for(g in goodsMap){
            gLength++;
        }
        if(gLength < $scope.good.G_OPTIONS.length){
            alert("请选择条件！");
            return;
        }
        add.options = JSON.stringify(goodsMap);
        add.number = parseInt($scope.number||1);
        add.price = $scope.totalPrice+"";
        add.status = i;
        console.log(add);
        $http.post("./add", add).success(
            function(data) {
            	if (data.status == "ok")
    				alert("添加购物车成功！");
    			else if(data.status == "error")
    				alert("添加购物车失败！");
    			else
    				location.href = "../home/login";
            })
            .error(function() {
                alert("网络错误");
            });
    };

    var goodsMap = {};

    $scope.calcuTotal = function (o) {
        //cal(price, name);
        if ($scope.number <= 0) {
            $scope.number = 1;
        }
        if (o) {
            delete o.$$hashKey;
            goodsMap[o.name] = o;
        }
        $scope.totalPrice = (parseFloat($scope.good.G_COST));
        for (var key in goodsMap) {
            $scope.totalPrice += parseFloat(goodsMap[key].price || 0);
        }
        //console.log(JSON.stringify(goodsMap));
        $scope.totalPrice = $scope.totalPrice * $scope.number;
    };

    var cal = function () {
        $scope.totalPrice = (parseFloat($scope.good.G_COST));
        for (var i = 0; i < $scope.good.G_OPTIONS.length; i++) {
            var name = $scope.good.G_OPTIONS[i].opts[0].name;
            alert($("." + name).val());
            $scope.totalPrice += parseFloat($("." + name).val());
        }
    };

    /*大图随小图变化而变化*/
    $scope.s2b = function (i) {
        $scope.bigNo = i.p;
        //console.log($scope.bigNo);
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
    $scope.test = function () {
        alert($scope.ys + $scope.bb + $scope.fs);
    }

    var getShop = function (q) {
        $http.get("./getShop?id=" + $scope.good.G_SHOP).success(
            function (data) {
                $scope.shop = data;
            })
            .error(function () {
                alert("网络错误");
            });
    };

var getGood = function (q) {
    $http.get("./getGood?id=" + $scope.ID).success(
        function (data) {
            $scope.totalPrice = data.G_COST;
            data.G_DISCONT = JSON.parse(data.G_DISCONT);
            data.G_OPTIONS = JSON.parse(data.G_OPTIONS);
            $scope.good = data;
            $scope.showPics = data.G_PHOTOS.split(",");
            if (q)
                q.success();
        })
        .error(function () {
            alert("网络错误");
            if (q)
                q.error();
        });
};

var getURLInfo = function (q) {
    var url = window.location.href;
    var reg = /id=\d+/g;
    var match = reg.exec(url);
    if (match) {
        $scope.ID = url.substr(match.index).substr(3);
        if (q)
            q.success();
    } else {
        alert("网络错误！");
        if (q)
            q.error();
    }
};

var preparing = function () {
    queue
        .add(getURLInfo)
        .add(getGood)
        .add(getShop)
        .finish(function () {
        	$scope.bigNo = $scope.showPics[0];
        }).start(function () {
        })
};

preparing();

})
;