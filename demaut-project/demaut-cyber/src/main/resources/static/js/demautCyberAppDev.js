
var ngDemautCyberDev = angular.module('ngDemautCyber', ['ngDemautApp', 'portail']);
ngDemautCyberDev.directive('demautContainer', ['$route', function ($route) {
    return {
        restrict: 'E',
        templateUrl: prestationContext+'/html/demaut.html'
    };
}]);
