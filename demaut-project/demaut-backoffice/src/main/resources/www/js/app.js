var microbizAppVersion = "v0.1.0";
var microbizAppEnviron = "Demo";
var microbizAppFullame = "Back-Office-Demaut";
var microbizAppUserame = "Back-Office-User";

var ngApp = angular.module('ngApp', ['ngSanitize', 'ngRoute', 'ngAnimate']);

/*Necessaire si les services ne sont pas dans la même arborescence que la page html*/
ngApp.constant('urlPrefix', '/outils/demaut-microbiz-api');

ngApp
    .config(['$routeProvider', '$locationProvider', '$httpProvider', function ($routeProvider, $locationProvider, $httpProvider) {

        $routeProvider
            .when('/Demaut/recherche', {
                templateUrl: 'partiels/recherche.html',
                controller: 'RechercheController',
                controllerAs: 'recherche'
            })
            .when('/Demaut/aide', {
                templateUrl: 'partiels/aide.html',
                controller: 'AideController',
                controllerAs: 'aide'
            })
            .when('/Demaut/formaulaire', {
                templateUrl: 'partiels/formaulaire.html',
                controller: 'DemandeController',
                controllerAs: 'demande'
            })
            .when('/Demaut/demande', {
                templateUrl: 'partiels/demande/demande.html',
                controller: 'DemandeController',
                controllerAs: 'demande'
            })
            .when('/Demaut/demande/donneesPerso', {
                templateUrl: 'partiels/demande/donneesPerso.html',
                controller: 'DonneesPersoController',
                controllerAs: 'donneesPerso'
            })
            .when('/Demaut/demande/donneesProf', {
                templateUrl: 'partiels/demande/donneesProf.html',
                controller: 'DonneesProfController',
                controllerAs: 'donneesProf'
            })
            .when('/Demaut/demande/donneesProf', {
                templateUrl: 'partiels/demande/donneesProf.html',
                controller: 'DonneesProfController',
                controllerAs: 'donneesProf'
            })
            .when('/Demaut/demande/annexes', {
                templateUrl: 'partiels/demande/annexes.html',
                controller: 'AnnexesController',
                controllerAs: 'annexes'
            })
            .when('/Demaut/demande/recapitulation', {
                templateUrl: 'partiels/demande/recapitulation.html',
                controller: 'RecapitulationController',
                controllerAs: 'recapitulation'
            })
            .when('/Demaut/demande/soumission', {
                templateUrl: 'partiels/demande/soumission.html',
                controller: 'SoumissionController',
                controllerAs: 'soumission'
            })
            .otherwise({
                templateUrl: 'partiels/dashboard.html',
                controller: 'DashboardController',
                controllerAs: 'dashboard'
            });

        $locationProvider.html5Mode(true);

        $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
            return {
                'responseError': function (rejection) {
                    var status = rejection.status;
                    var config = rejection.config;
                    var data = rejection.data;
                    var method = config.method;
                    var url = config.url;

                    if (status == 401) {
                        $location.path("/index");
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
    .controller('DashboardController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$interval', 'urlPrefix', '$log', function ($scope, $rootScope, $routeParams, $http, $location, $interval, urlPrefix, $log) {
        $rootScope.contextMenu = "dashboard";
        $scope.indexStep = 0;
        this.name = "DashboardController";
        this.params = $routeParams;


    }])
    .controller('DemandeController', ['$scope', '$rootScope', '$routeParams', function ($scope, $rootScope, $routeParams) {

        $rootScope.contextMenu = "demande";
        $scope.indexStep = 1;
        this.name = "DemandeController";
        this.params = $routeParams;
    }])
    .controller('AnnexesController', ['$scope', '$rootScope', '$routeParams', function ($scope, $rootScope, $routeParams) {
        $rootScope.contextMenu = "demande";
        $scope.indexStep = 4;
        $scope.annexeFormats = ["application/pdf", "image/jpg", "image/jpeg", "image/png", "image/bmp"];
        $scope.annexeTypes = [
            "Curriculum vitae",
            "Diplôme (médecin)",
            "Titre (pédiatre)",
            "Certificats de Travail",
            "Casier judiciaire"
        ];
        $scope.referenceFiles = [];
        $scope.name = "AnnexeController";
        $scope.params = $routeParams;

        $scope.filesChanged = function (targetFiles) {
            $scope.files = [];

            var newFilesList = targetFiles.files;
            var typeAnnexe = targetFiles.name;
            var isValidList = true;

            // Check format for all files list
            for (var indexI = 0; indexI < newFilesList.length; indexI++) {
                var currentFile = newFilesList[indexI];
                currentFile["typeAnnexe"] = typeAnnexe;
                var fileType = currentFile.type;
                var isValidFormat = $scope.annexeFormats.indexOf(fileType) != -1;
                if (isValidFormat == false) {
                    isValidList = false;
                    break;
                }
            }

            // Add new add files to existing lists
            if (isValidList) {
                if ($scope.referenceFiles != null && $scope.referenceFiles != undefined &&
                    $scope.referenceFiles[typeAnnexe] != null && $scope.referenceFiles[typeAnnexe] != undefined &&
                    $scope.referenceFiles[typeAnnexe].length > 0) {
                    var existAnnexesList = $scope.referenceFiles[typeAnnexe];
                    for (var indexJ = 0; indexJ < newFilesList.length; indexJ++) {
                        var currentNewFile = newFilesList[indexJ];
                        var isNewFileDejaVu = false;
                        for (var indexK = 0; indexK < existAnnexesList.length; indexK++) {
                            var currentExistFile = existAnnexesList[indexK];
                            if (currentNewFile.name == currentExistFile.name) {
                                isNewFileDejaVu = true;
                                break;
                            }
                        }
                        if (isNewFileDejaVu == false) {
                            $scope.referenceFiles[typeAnnexe].push(currentNewFile);
                        }
                    }
                    $scope.files = $scope.referenceFiles;
                } else {
                    $scope.referenceFiles[typeAnnexe] = [];
                    for (var indexH = 0; indexH < newFilesList.length; indexH++) {
                        var currentValidFile = newFilesList[indexH];
                        $scope.referenceFiles[typeAnnexe].push(currentValidFile);
                    }
                    $scope.files = $scope.referenceFiles;
                }
            } else {
                $scope.files = $scope.referenceFiles;
                alert(typeAnnexe + " : une/plusieurs pièces ne correspondent pas aux formats supportés : [" + $scope.annexeFormats + "]");
            }

            $scope.$apply();
        };

        $scope.viewAnnexe = function (file, annexeType) {
            var binary = new Blob([file], {type: file.type});
            var fileURL = URL.createObjectURL(binary);
            console.log(fileURL);
            var elementLink = document.createElement('A');
            elementLink.href = fileURL;
            elementLink.target = '_blank';
            elementLink.download = file.name;
            document.body.appendChild(elementLink);
            elementLink.click();
        };

        $scope.deleteAnnexe = function (file, annexeType) {
            if ($scope.referenceFiles != null && $scope.referenceFiles != undefined &&
                $scope.referenceFiles[annexeType] != null && $scope.referenceFiles[annexeType] != undefined &&
                $scope.referenceFiles[annexeType].length > 0) {

                for (var indexH = 0; indexH < $scope.referenceFiles[annexeType].length; indexH++) {
                    var currentFetchedFile = $scope.referenceFiles[annexeType][indexH];
                    if (file.name == currentFetchedFile.name) {
                        $scope.referenceFiles[annexeType].splice(indexH, 1);
                        break;
                    }
                }
            }
            $scope.files = $scope.referenceFiles;
        };
    }]);

ngApp
    .run(function($rootScope, $sce, $location, $http, urlPrefix) {
        $http.get(urlPrefix + '/services/main')
            .success(function (data, status, headers, config) {
                var fromJson = angular.fromJson(data.main);
                $rootScope.environment = fromJson.environment;
                $rootScope.buildVersion = fromJson.buildVersion;
                $rootScope.project = fromJson.project;
                $rootScope.user = fromJson.user;
            })
            .error(function (data, status, headers, config) {
                $rootScope.environment = microbizAppEnviron;
                $rootScope.buildVersion = microbizAppVersion;
                $rootScope.project = microbizAppFullame;
                $rootScope.user = microbizAppUserame;
            });

        $rootScope.$on('$viewContentLoaded', function() {
            delete $rootScope.error;
        });

        $rootScope.$on('$routeChangeStart', function () {

        });
    });