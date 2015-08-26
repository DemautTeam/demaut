var microbizAppVersion = "v0.1.0";
var microbizAppEnviron = "Demo";
var microbizAppFullame = "Back-Office-Demaut";
var microbizAppUserame = "Back-Office-User";

var ngApp = angular.module('ngApp', ['ngSanitize', 'ngRoute', 'ngAnimate']);

/*Necessaire si les services ne sont pas dans la même arborescence que la page html*/
ngApp.constant('urlPrefix', '/outils/demaut-microbiz');
function settingScopeVariable($scope) {
    $scope.environment = microbizAppEnviron;
    $scope.buildVersion = microbizAppVersion;
    $scope.project = microbizAppFullame;
    $scope.user = microbizAppUserame;
}
ngApp
    .config([ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {

        $routeProvider
            .when('/Demaut/:demandeId', {
                templateUrl: 'partiels/demande.html',
                controller: 'DemandeController',
                controllerAs: 'demande'
            })
            .when('/Demaut/:demandeId/ch/:annexeId', {
                templateUrl: 'partiels/annexe.html',
                controller: 'AnnexeController',
                controllerAs: 'annexe'
            })
            .otherwise({
                templateUrl: 'partiels/annexe.html',
                controller: 'AnnexeController',
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
        settingScopeVariable($scope);

    }])
    .controller('DemandeController', ['$scope','$routeParams', function($scope, $routeParams) {
        settingScopeVariable($scope);

        this.name = "DemandeController";
        this.params = $routeParams;
        $scope.indexStep = 2;
    }])
    .controller('AnnexeController', ['$scope', '$routeParams', function($scope, $routeParams) {
        settingScopeVariable($scope);

        this.name = "AnnexeController";
        this.params = $routeParams;
        $scope.indexStep = 4;
        $scope.files = [];

        $scope.annexeFormats = ["pdf", "jpg", "jpeg", "png", "bmp"];
        $scope.annexeTypes = [
            "Curriculum vitae",
            "Diplôme (médecin)",
            "Titre (pédiatre)",
            "Certificats de Travail",
            "Casier judiciaire"
        ];

        $scope.filesChanged = function(targetFiles) {
            var filesList = targetFiles.files;
            var typeAnnexe = targetFiles.name;

            for (var i = 0; i < filesList.length; i++) {
                filesList[i]["typeAnnexe"] = typeAnnexe;
            }
            $scope.files[typeAnnexe] = filesList;

            $scope.$apply();
            //alert(typeAnnexe + " : Uploaded files " + $scope.files[typeAnnexe].length);
        };

        $scope.viewAnnexe = function(file, annexeType) {
            alert(annexeType + " : Not implemented view " + file.name);
        };

        $scope.deleteAnnexe = function(file, annexeType) {
            alert(annexeType + " : Not implemented delete " + file.name);
        };
    }]);