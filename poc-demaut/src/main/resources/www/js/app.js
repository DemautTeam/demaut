var microbizAppVersion = "v0.0.1";
var microbizAppEnviron = "Dev";
var microbizAppFullame = "Poc-Demaut";
var ngApp = angular.module('ngApp', ['ngSanitize']);
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
    .run(function($rootScope, $sce, $location, $http) {

        var myLocation = $location.path();

        $rootScope.$on('$viewContentLoaded', function() {
            delete $rootScope.error;
        });

        $rootScope.$on('$routeChangeStart', function () {

        });

        $http.get('../services/main').
            success(function (data, status, headers, config) {
                $rootScope.environment = microbizAppEnviron;
                $rootScope.buildVersion = microbizAppVersion;
                $rootScope.project = microbizAppFullame;
                $rootScope.user = angular.fromJson(data.main).user;
            }).
            error(function (data, status, headers, config) {
                alert('Error ../services/main');
            });

        // indispensable pour le rendering initial
        $rootScope.initialized = true;
    });

ngApp.controller('IndexController', ['$scope', '$http', function ($scope, $http, $location, focus, $interval) {

    $http.get('../services/annexes/all').
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

        $http.get('../services/annexe/binary/' + annexe.name).
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
    var binary = new Blob([data], {
        type: 'application/octet-stream' //TODO encoding binary
    });
    var fileURL = URL.createObjectURL(binary);
    var elementLink = document.createElement('a');
    elementLink.href = fileURL;
    elementLink.target = '_blank';
    elementLink.download = annexe.name;
    document.body.appendChild(elementLink);
    elementLink.click();
}