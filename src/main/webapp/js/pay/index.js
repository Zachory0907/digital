var app = angular.module('app', []).controller('payController', function($scope, $http){
    $scope.msg = "欢迎";
    $scope.provinces = ["北京市","山西省","山东省","河南省","河北省","湖南省"];
    $scope.province = "";
    $scope.citys = ["北京市","山西省","山东省","河南省","河北省","湖南省"];
    $scope.city = "";
    $scope.counties = ["北京市","山西省","山东省","河南省","河北省","湖南省"];
    $scope.county = "";

});