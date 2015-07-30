var microbizAppVersion = "v0.0.1";
var microbizAppEnviron = "Dev";
var microbizAppFullame = "Poc-Demaut";
var ngApp = angular.module('ngApp', ['ngSanitize']);
/*Necessaire si les services ne sont pas dans la même arborescence que la page html*/
ngApp.constant('urlPrefix', '/outils/poc-demaut-api');
ngApp
    .config([ '$locationProvider', '$httpProvider', function($locationProvider, $httpProvider) {

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
    .run(function($rootScope, $sce, $location, $http, urlPrefix) {

        var myLocation = $location.path();

        $rootScope.$on('$viewContentLoaded', function() {
            delete $rootScope.error;
        });

        $rootScope.$on('$routeChangeStart', function () {

        });

        $http.get(urlPrefix + '/services/main').
            success(function (data, status, headers, config) {
                $rootScope.environment = microbizAppEnviron;
                $rootScope.buildVersion = microbizAppVersion;
                $rootScope.project = microbizAppFullame;
                $rootScope.user = angular.fromJson(data.main).user;
            }).
            error(function (data, status, headers, config) {
                alert('Error ../services/main\nStatus :' +  status);
            });

        // indispensable pour le rendering initial
        $rootScope.initialized = true;
    });

ngApp.controller('IndexController', ['$scope', '$http', '$location',  '$interval', 'urlPrefix', '$log', function ($scope, $http, $location, $interval, urlPrefix, $log ) {
    $http.get(urlPrefix + '/services/annexes/all').
        success(function (data, status, headers, config) {
            $scope.annexes = angular.fromJson(data.response);
        }).
        error(function (data, status, headers, config) {
            alert('Error ../services/annexes/all');
        });

    $scope.isDocument = function(annexe) {
        return annexe.type == 'application/pdf';
    };

    $scope.viewEntry = function(annexe) {
        $http.get(urlPrefix + '/services/annexe/binary/' + annexe.name, {responseType:'arraybuffer'}).
            success(function (data, status, headers, config) {
                displayAnnexeFromBinary(annexe, data);
            }).
            error(function (data, status, headers, config) {
                alert('Error ../services/annexe/binary/' + annexe.name);
            });
    };

    $scope.deleteEntry = function(annexe) {
        alert('Delete ' + annexe.name + ', not implemented yet');
    };
    $scope.editEntry = function(annexe) {
        alert('Edit ' + annexe.name + ', not implemented yet');
    };
}]);

function displayAnnexeFromBinary(annexe, data) {
    var binary = new Blob([data], { type: annexe.type });
    var fileURL = URL.createObjectURL(binary);
    console.log(fileURL);
    var elementLink = document.createElement('A');
    elementLink.href = fileURL;
    elementLink.target = '_blank';
    elementLink.download = annexe.name;
    document.body.appendChild(elementLink);
    elementLink.click();
}