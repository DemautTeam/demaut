var microbizAppVersion = "dev";

var ngApp = angular.module('ngApp', ['ngSanitize']);

ngApp.controller('Demo1Controller', ['$scope', '$http', function ($scope, $http, $location, focus, $interval) {

    $http.get('../services/main').
        success(function (data, status, headers, config) {
            $scope.main = data;
        }).
        error(function (data, status, headers, config) {
            //TODO: ALERTER L'ERREUR
        });

    $scope.onClickTest1 = function () {
        $http.get('../services/annexes/all').
            success(function (data, status, headers, config) {
                alert(data.response);
            }).
            error(function (data, status, headers, config) {
                //TODO: ALERTER L'ERREUR
            });
    }

    $scope.onClickTest2 = function () {
        $http.get('../services/test2').
            success(function (data, status, headers, config) {
                alert(data.response);
            }).
            error(function (data, status, headers, config) {
                //TODO: ALERTER L'ERREUR
            });
    }

}]);