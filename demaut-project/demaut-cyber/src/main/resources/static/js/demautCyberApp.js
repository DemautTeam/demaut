var ngDemautCyber = angular.module('ngDemautCyber', ['ngDemautApp']);
/**
 * l'injection du service $route dans la directive est un contournement d'un bug de ngRoute
 */
ngDemautCyber.directive('demautContainer', ['$route', function ($route) {
    return {
        restrict: 'E',
        templateUrl: prestationContext+'/html/demaut.html'
    };
}]);
