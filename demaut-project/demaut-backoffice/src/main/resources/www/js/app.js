var microbizAppVersion = "v0.1.0";
var microbizAppEnviron = "Demo";
var microbizAppFullame = "Back-Office-Demaut";
var microbizAppUserame = "Back-Office-User";
var ngApp = angular.module('ngApp', ['ngSanitize', 'ngRoute', 'ngAnimate']);
/*Necessaire si les services ne sont pas dans la même arborescence que la page html*/
ngApp.constant('urlPrefix', '/outils/demaut-microbiz');
ngApp
    .config([ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {

        $routeProvider
            .when('/Demaut/:demandeId', {
                templateUrl: 'partiels/demande.html',
                controller: 'DemandeCtrl',
                controllerAs: 'demande'
            })
            .when('/Demaut/:demandeId/ch/:annexeId', {
                templateUrl: 'partiels/annexe.html',
                controller: 'AnnexeCtrl',
                controllerAs: 'annexe'
            })
            .otherwise({
                templateUrl: 'partiels/annexe.html',
                controller: 'AnnexeCtrl',
                controllerAs: 'annexe'
            });

        $locationProvider.html5Mode(true);

        $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
            return {
                'responseError': function(rejection) {
                    var status = rejection.status;
                    var config = rejection.config;
                    var data = rejection.data;
                    var method = config.method;
                    var url = config.url;

                    if (status == 401) {
                        $location.path( "/index" );
                    } else {
                        $rootScope.error = method + ' on ' + url + ' failed with status ' + status + '<br>' + data.substring(data.indexOf('<body>') + 6, data.indexOf('</body>'));
                        angular.element("#errorModal").modal('toggle');
                        angular.element("#errorModal").modal('show');
                    }
                    return $q.reject(rejection);
                }
            };
        });
    }
    ])
    .controller('IndexController', ['$scope', '$http', '$location',  '$interval', 'urlPrefix', '$log', function ($scope, $http, $location, $interval, urlPrefix, $log ) {
        $scope.environment = microbizAppEnviron;
        $scope.buildVersion = microbizAppVersion;
        $scope.project = microbizAppFullame;
        $scope.user = microbizAppUserame;

    }])
    .controller('DemandeCtrl', ['$scope','$routeParams', function($scope, $routeParams) {
        $scope.environment = microbizAppEnviron;
        $scope.buildVersion = microbizAppVersion;
        $scope.project = microbizAppFullame;
        $scope.user = microbizAppUserame;

        this.name = "DemandeCtrl";
        this.params = $routeParams;
        $scope.indexStep = 2;
    }])
    .controller('AnnexeCtrl', ['$scope', '$routeParams', function($scope, $routeParams) {
        $scope.environment = microbizAppEnviron;
        $scope.buildVersion = microbizAppVersion;
        $scope.project = microbizAppFullame;
        $scope.user = microbizAppUserame;

        this.name = "AnnexeCtrl";
        this.params = $routeParams;
        $scope.indexStep = 4;
    }]);