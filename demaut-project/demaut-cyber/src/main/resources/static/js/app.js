var backofficeAppVersion = "v0.1.0";
var backofficeAppFullame = "Cyber-Demaut";

var ngApp = angular.module('ngApp', ['ngSanitize', 'ngRoute', 'ngAnimate']);

/*Necessaire si les services ne sont pas dans la même arborescence que la page html*/
ngApp.constant('urlPrefix', '/outils/demaut-microbiz-api');

ngApp
    .config(['$routeProvider', '$locationProvider', '$httpProvider', function ($routeProvider, $locationProvider, $httpProvider) {

        $routeProvider
            .when('/Demaut/recherche', {
                templateUrl: 'template/recherche.html',
                controller: 'RechercheController',
                controllerAs: 'recherche'
            })
            .when('/Demaut/aide', {
                templateUrl: 'template/aide.html',
                controller: 'AideController',
                controllerAs: 'aide'
            })
            .when('/Demaut/formulaire', {
                templateUrl: 'template/formulaire.html',
                controller: 'DemandeController',
                controllerAs: 'demande'
            })
            .when('/Demaut/demande', {
                templateUrl: 'template/demande/infoDemande.html',
                controller: 'DemandeController',
                controllerAs: 'demande'
            })
            .when('/Demaut/demande/donneesPerso', {
                templateUrl: 'template/demande/donneesPerso.html',
                controller: 'DonneesPersoController',
                controllerAs: 'donneesPerso'
            })
            .when('/Demaut/demande/donneesProf', {
                templateUrl: 'template/demande/donneesProf.html',
                controller: 'DonneesProfController',
                controllerAs: 'donneesProf'
            })
            .when('/Demaut/demande/donneesProf', {
                templateUrl: 'template/demande/donneesProf.html',
                controller: 'DonneesProfController',
                controllerAs: 'donneesProf'
            })
            .when('/Demaut/demande/annexes', {
                templateUrl: 'template/demande/annexes.html',
                controller: 'AnnexesController',
                controllerAs: 'annexes'
            })
            .when('/Demaut/demande/recapitulation', {
                templateUrl: 'template/demande/recapitulatif.html',
                controller: 'RecapitulationController',
                controllerAs: 'recapitulation'
            })
            .when('/Demaut/demande/soumission', {
                templateUrl: 'template/demande/soumission.html',
                controller: 'SoumissionController',
                controllerAs: 'soumission'
            })
            .otherwise({
                templateUrl: 'template/cockpit.html',
                controller: 'CockpitController',
                controllerAs: 'cockpit'
            });

        $locationProvider.html5Mode(false);
        $locationProvider.hashPrefix('!');

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
    .service('nationalityTest', ['$log', function ($log) {
        this.suissePattern = new RegExp('[Ss]uisse');
        this.isSuisse = function(textValue){
            return this.suissePattern.test(textValue);
        };
    }])
    .controller('CockpitController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$interval', 'urlPrefix', '$log', function ($scope, $rootScope, $routeParams, $http, $location, $interval, urlPrefix, $log) {
        $rootScope.contextMenu = "cockpit";
        $scope.indexStep = 0;
        this.name = "CockpitController";
        this.params = $routeParams;


    }])
    .controller('DonneesPersoController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$interval', 'urlPrefix', '$log', 'nationalityTest', function ($scope, $rootScope, $routeParams, $http, $location, $interval, urlPrefix, $log, nationalityTest) {
        $rootScope.contextMenu = "Données Personnelles";
        $scope.indexStep = 1;
        this.name = "DonneesPersoController";
        this.params = $routeParams;
        $scope.testSuisse = nationalityTest;
        $scope.nextStep = function(){
            $scope.indexStep += 1;
            $location.path('/Demaut/demande');
        };

    }])
    .controller('DemandeController', ['$scope', '$rootScope', '$routeParams', function ($scope, $rootScope, $routeParams) {

        $rootScope.contextMenu = "demande";
        $scope.indexStep = 2;
        this.name = "DemandeController";
        this.params = $routeParams;

        $scope.nextStep = function(){
            $scope.indexStep += 1;
            $location.path('/Demaut/demande');
        };
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
        $http.get(urlPrefix + '/camel/main')
            .success(function (data, status, headers, config) {
                var fromJson = angular.fromJson(data.main);
                $rootScope.environment = fromJson.environment;
                $rootScope.user = fromJson.user;
                $rootScope.buildVersion = backofficeAppVersion;
                $rootScope.project = backofficeAppFullame;
            })
            .error(function (data, status, headers, config) {
                $rootScope.buildVersion = backofficeAppVersion;
                $rootScope.project = backofficeAppFullame;
                $rootScope.error = 'Error ' + urlPrefix + '/camel/main \n Status :' +  status;
            });

        $rootScope.$on('$viewContentLoaded', function() {
        });

        $rootScope.$on('$routeChangeStart', function () {
        });
    });