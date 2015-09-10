var ngDemautApp = angular.module('ngDemautApp', ['ngSanitize', 'ngRoute', 'ngAnimate', 'commonsModule']);

/*Necessaire si les services ne sont pas dans la même arborescence que la page html*/
// TODO Dev à corrigert URL Prefix et Main : ngDemautApp.constant('urlPrefix', '/outils/demautMicrobiz-api');
ngDemautApp.constant('urlPrefix', 'http://localhost:8083/outils/demautMicrobiz-api');
ngDemautApp.constant('urlMain', 'http://localhost:8083/demautMicrobiz');

ngDemautApp
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
                controller: 'RecapitulatifController',
                controllerAs: 'recapitulatif'
            })
            .when('/Demaut/demande/soumission', {
                templateUrl: 'template/demande/soumission.html',
                controller: 'SoumissionController',
                controllerAs: 'soumission'
            })
            .when('/Demaut/cockpit', {
                templateUrl: 'template/cockpit.html',
                controller: 'CockpitController',
                controllerAs: 'cockpit'
            })
            .otherwise({
                redirectTo: '/Demaut/cockpit'
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
                        $location.path("/Demaut/aide");
                    } else {
                        // TODO resolve errors
                         $rootScope.error = method + ' on ' + url + ' failed with status ' + status + '<br>' +
                            (data != null && data != undefined ? data.substring(data.indexOf('<body>') + 6, data.indexOf('</body>')) : "data empty!");
                        angular.element("#errorModal").modal('toggle');
                        angular.element("#errorModal").modal('show');
                    }
                    return $q.reject(rejection);
                }
            };
        });
    }
    ])
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
        $scope.testSuisse = nationalityTest;//récupération du service de test des nationnalité
        $scope.personalData = {};
        $scope.isPermisRequired = function(){
            return !nationalityTest.isSuisse($scope.personalData.nationality) && !$scope.personalData.permis;
        };
        $scope.resetPermis = function(){
            if(nationalityTest.isSuisse($scope.personalData.nationality)){
                $scope.personalData.permis = null;
            }
        }
        $scope.nextStep = function(){
            if($scope.donneesPerso.personalDataForm.$valid) {
                $log.info('Formulaire valide !');
                $scope.indexStep += 1;
                $location.path('/Demaut/demande');
            }
            else {
                $log.info('Formulaire invalide !');
            }
        };


    }])
    .controller('DemandeController', ['$scope', '$rootScope', '$routeParams', '$location', function ($scope, $rootScope, $routeParams, $location) {
        $rootScope.contextMenu = "demande";
        $scope.indexStep = 2;
        this.name = "DemandeController";
        this.params = $routeParams;
        $scope.previewStep = function(){
            $scope.indexStep -= 1;
            $location.path('/Demaut/demande/donneesPerso');
        };
        $scope.nextStep = function(){
            $scope.indexStep += 1;
            $location.path('/Demaut/demande/donneesProf');
        };
    }])
    .controller('DonneesProfController', ['$scope', '$rootScope', '$routeParams', '$location', function ($scope, $rootScope, $routeParams, $location) {
        $rootScope.contextMenu = "DonneesProfessionnelle";
        $scope.indexStep = 3;
        this.name = "DonneesProfController";
        this.params = $routeParams;
        $scope.previewStep = function(){
            $scope.indexStep -= 1;
            $location.path('/Demaut/demande');
        };
        $scope.nextStep = function(){
            $scope.indexStep += 1;
            $location.path('/Demaut/demande/annexes');
        };
    }])
    .controller('AnnexesController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', 'urlPrefix', function ($scope, $rootScope, $routeParams, $http, $location, urlPrefix) {
        $rootScope.contextMenu = "demande";
        $scope.demandeReference = "toBeDefinedLater";
        $scope.profession = "aSaisirEtape2";
        $scope.indexStep = 4;
        $scope.annexeFormats = ["application/pdf", "image/jpg", "image/jpeg", "image/png"];
        $scope.annexeTypes = [
            "Liste locale",
            "Curriculum vitae",
            "Diplôme (médecin)",
            "Titre (pédiatre)",
            "Certificats de Travail",
            "Casier judiciaire"
        ];

        $http.get(urlPrefix + '/services/annexes/typesList/' + $scope.profession).
            success(function (data, status, headers, config) {
                $scope.annexeTypes = angular.fromJson(data.response);
            }).
            error(function (data, status, headers, config) {
                $rootScope.error ='Error downloading ../services/annexes/typesList/' + $scope.profession;
            });

        $scope.referenceFiles = [];
        $scope.name = "AnnexeController";
        $scope.params = $routeParams;
        $scope.previewStep = function(){
            $scope.indexStep -= 1;
            $location.path('/Demaut/demande/donneesProf');
        };
        $scope.nextStep = function(){
            $scope.indexStep += 1;
            $location.path('/Demaut/demande/recapitulation');
        };

        $scope.filesChanged = function (targetFiles) {
            $scope.files = [];

            var fileThreshold = 3; // MB
            var newFilesList = targetFiles.files;
            var typeAnnexe = targetFiles.name;
            var isValidList = true;

            // Check format for all files list
            function validateFileName(fileName) {
                var forbiddenChars = /^(?!\.)[^\|\*\?\\:<>/$"]*[^\.\|\*\?\\:<>/$"]+$/
                // Ne doit pas etre vide
                // Ne doit pas commencer par .
                // Ne doit pas contenir | * ? \ : < > $
                // Ne doit pas finir avec .
                // Ne doit pas excèder 255 chars
                return fileName != null && fileName != undefined && forbiddenChars.test(fileName) && fileName.length <= 255;
            }

            for (var indexI = 0; indexI < newFilesList.length; indexI++) {
                var currentFile = newFilesList[indexI];
                currentFile["typeAnnexe"] = typeAnnexe;
                var fileType = currentFile.type;
                var fileSize = currentFile.size;
                var fileName = currentFile.name;

                var isValidName = validateFileName(fileName);
                var isValidFormat = $scope.annexeFormats.indexOf(fileType) != -1;
                if (isValidName == false || isValidFormat == false || fileSize <= 0 || fileSize > 1024 * 1024 * fileThreshold) {
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
                            doUploadFile(currentNewFile);
                        }
                    }
                    $scope.files = $scope.referenceFiles;
                } else {
                    $scope.referenceFiles[typeAnnexe] = [];
                    for (var indexH = 0; indexH < newFilesList.length; indexH++) {
                        var currentValidFile = newFilesList[indexH];
                        $scope.referenceFiles[typeAnnexe].push(currentValidFile);
                        doUploadFile(currentValidFile);
                    }
                    $scope.files = $scope.referenceFiles;
                }
            } else {
                $scope.files = $scope.referenceFiles;
                $rootScope.error = typeAnnexe + ' : une/plusieurs pièces ne respectent pas les règles de nommage ou ne correspondent pas aux formats supportés : \n' + $scope.annexeFormats;
            }
            $scope.$apply();
        };

        $scope.viewAnnexe = function (file, annexeType) {
            $http.get(urlPrefix + '/services/annexes/afficher/' + $scope.demandeReference + '/' + file.name, {responseType:'arraybuffer'}).
                success(function (data, status, headers, config) {
                    displayAnnexeFromBinary(data);
                }).
                error(function (data, status, headers, config) {
                    alert('Error downloading ../services/annexes/afficher/' + file.name);
                });

            function displayAnnexeFromBinary(file){
                var binary = new Blob([file], {type: file.type});
                var fileURL = URL.createObjectURL(binary);
                console.log(fileURL);
                var elementLink = document.createElement('A');
                elementLink.href = fileURL;
                elementLink.target = '_blank';
                elementLink.download = file.name;
                document.body.appendChild(elementLink);
                elementLink.click();
            }
        };

        $scope.deleteAnnexe = function (file, annexeType) {
            if ($scope.referenceFiles != null && $scope.referenceFiles != undefined &&
                $scope.referenceFiles[annexeType] != null && $scope.referenceFiles[annexeType] != undefined &&
                $scope.referenceFiles[annexeType].length > 0) {

                for (var indexH = 0; indexH < $scope.referenceFiles[annexeType].length; indexH++) {
                    var currentFetchedFile = $scope.referenceFiles[annexeType][indexH];
                    if (file.name == currentFetchedFile.name) {
                        $scope.referenceFiles[annexeType].splice(indexH, 1);
                        doDeleteFile(currentFetchedFile, annexeType);
                        break;
                    }
                }
            }
            $scope.files = $scope.referenceFiles;
        };

        function doDeleteFile(file, annexeFileType) {
            $http.get(urlPrefix + '/services/annexes/supprimer/' + $scope.demandeReference + '/' + file.name + '/' + annexeFileType)
                .success(function (data, status, headers, config) {
                    $rootScope.error = 'Une annexe a été supprimée avec succès: \n Status :' +  status;
                })
                .error(function (data, status, headers, config) {
                    $rootScope.error = 'Error ' + urlPrefix + '/services/annexes/supprimer/ \n Status :' +  status;
                });
        };

        function doUploadFile(currentFetchedFile) {
            var formData = new FormData();
            formData.append('ajaxAction', 'upload');
            formData.append("demandeReference", $scope.demandeReference);
            formData.append("annexeFile", currentFetchedFile);
            formData.append("annexeFileName", currentFetchedFile.name);
            formData.append("annexeFileSize", currentFetchedFile.size);
            formData.append("annexeFileType", currentFetchedFile.type);
            formData.append("annexeType", currentFetchedFile.typeAnnexe);
            $http.post(urlPrefix + '/services/annexes/attacher', formData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': 'multipart/form-data'}
            })
                .success(function (data, status, headers, config) {
                    $rootScope.error = 'Une annexe a été envoyée avec succès: \n Status :' +  status;
                })
                .error(function (data, status, headers, config) {
                    $rootScope.error = 'Error Upload: [' + urlPrefix + '/services/annexes/attacher' +  status;
                });
        };
    }])
    .controller('RecapitulatifController', ['$scope', '$rootScope', '$routeParams', '$location', function ($scope, $rootScope, $routeParams, $location) {
        $rootScope.contextMenu = "demande";
        $scope.indexStep = 5;
        this.name = "RecapitulatifController";
        this.params = $routeParams;
        $scope.previewStep = function(){
            $scope.indexStep -= 1;
            $location.path('/Demaut/demande/annexes');
        };
        $scope.nextStep = function(){
            $scope.indexStep += 1;
            $location.path('/Demaut/demande/soumission');
        };
    }]);

ngDemautApp
    .run(function($rootScope, $sce, $location, $http, urlMain) {
        $http.get(urlMain + '/camel/main')
            .success(function (data, status, headers, config) {
                var fromJson = angular.fromJson(data.main);
                if (fromJson != null && fromJson != undefined) {
                    $rootScope.environment = fromJson.environment != null && fromJson.environment != undefined ? fromJson.environment : null;
                    $rootScope.user = fromJson.user != null && fromJson.user != undefined ? fromJson.user : null;
                    $rootScope.buildVersion = fromJson.buildVersion != null && fromJson.buildVersion != undefined ? fromJson.buildVersion : null;
                    $rootScope.project = fromJson.project != null && fromJson.project != undefined ? fromJson.project : null;
                }
            })
            .error(function (data, status, headers, config) {
                $rootScope.error = 'Error ' + urlMain + '/camel/main \n Status :' +  status;
            });

        $rootScope.$on('$viewContentLoaded', function() {
        });

        $rootScope.$on('$routeChangeStart', function () {
        });
    });