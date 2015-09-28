var ngDemautApp = angular.module('ngDemautApp', ['ngSanitize', 'ngRoute', 'ngAnimate', 'commonsModule']);

/* Necessaire si les services ne sont pas dans la même arborescence que la page html */
// TODO Dev à corrigert URL Prefix : ngDemautApp.constant('urlPrefix', '/outils/demautMicrobiz');
ngDemautApp.constant('urlPrefix', 'http://localhost:8083/outils/demautMicrobiz');

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
                controller: 'FormulaireController',
                controllerAs: 'demande'
            })
            .when('/Demaut/demande/professionSante', {
                templateUrl: 'template/demande/professionSante.html',
                controller: 'ProfessionSanteController',
                controllerAs: 'professionSante'
            })
            .when('/Demaut/demande/donneesPerso', {
                templateUrl: 'template/demande/donneesPerso.html',
                controller: 'DonneesPersoController',
                controllerAs: 'donneesPerso'
            })
            .when('/Demaut/demande/donneesDiplomes', {
                templateUrl: 'template/demande/donneesDiplomes.html',
                controller: 'DonneesDiplomesController',
                controllerAs: 'donneesDiplomes'
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

        /* Registers auth token interceptor, auth token is either passed by header (ex : an authenticated user token) */
        //$httpProvider.interceptors.push(function ($q, $rootScope, $location) {
        //    return {
        //        'request': function(config) {
        //            config.headers['X-iam-With'] = 'iamToken';
        //            config.headers['X-Demaut-Reference'] = 'demautReference';
        //            return config || $q.when(config);
        //        }
        //    };
        //});

    }
    ])
    .controller('CockpitController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$interval', 'urlPrefix', '$log', function ($scope, $rootScope, $routeParams, $http, $location, $interval, urlPrefix, $log) {
        $rootScope.contextMenu = "Cockpit";
        $scope.indexStep = 0;
        this.name = "CockpitController";
        this.params = $routeParams;
    }])
    .controller('ProfessionSanteController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', 'urlPrefix', '$log', 'professionTest', function ($scope, $rootScope, $routeParams, $http, $location, urlPrefix, $log, professionTest) {
        $rootScope.contextMenu = "Profession Santé";
        $scope.indexStep = 2;
        this.name = "ProfessionSanteController";
        this.params = $routeParams;
        $scope.professionData = {};
        $scope.professionData.demandeReference = null;
        $scope.professionData.profession = null;
        $scope.professionData.professions = [];

        //Récupère liste des professions
        //TODO: créer une methode isProfessionsListInitialized + Ne pas appeler le service a chaque fois (à initialiser au départ)
        if ($scope.professionData.professions.length == 0) {
            $http.get(urlPrefix + '/profession/professionsList').
                success(function (data, status, headers, config) {
                    $scope.professionData.professions = angular.fromJson(data.response);
                }).
                error(function (data, status, headers, config) {
                    $rootScope.error = 'Error downloading ../profession/professionsList';
                });
        }

        //Etape suivante
        $scope.nextStep = function(){
            if ($scope.professionSante.professionDataForm.$valid) {
                $http.get(urlPrefix + '/profession/donnees/' + $scope.professionData.demandeReference + '/' + $scope.professionData.profession + '/' + $scope.professionData.gln).
                    success(function (data, status, headers, config) {
                        $scope.professionData.demandeReference = angular.fromJson(data.response);
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error downloading ../profession/donnees/' + $scope.professionData.demandeReference;
                    });
                
                $scope.indexStep += 1;
                $location.path('/Demaut/demande/donneesProf');
            }
            else {
                $scope.professionSante.professionDataForm.gln.$valid = false;
                $scope.professionSante.professionDataForm.gln.$invalid = true;
                angular.element("#gln").focus();
                $log.info('Formulaire invalide !');
            }
        };
    }])
    .controller('DonneesPersoController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$interval', 'urlPrefix', '$log', 'nationalityTest', function ($scope, $rootScope, $routeParams, $http, $location, $interval, urlPrefix, $log, nationalityTest) {
        $rootScope.contextMenu = "Données Personnelles";
        $scope.indexStep = 1;
        this.name = "DonneesPersoController";
        this.params = $routeParams;
        $scope.testSuisse = nationalityTest;//récupération du service de test des nationnalité
        $scope.personalData = {};

        $scope.isPermisRequired = function () {
            return !nationalityTest.isSuisse($scope.personalData.nationalite) && !$scope.personalData.permis;
        };

        $scope.resetPermis = function () {
            if (nationalityTest.isSuisse($scope.personalData.nationalite)) {
                $scope.personalData.permis = null;
            }
        };

        $scope.backStep = function () {
            if ($scope.donneesPerso.personalDataForm.$valid) {
                $log.info('Formulaire valide !');
                $scope.indexStep += 1;
                $location.path('/Demaut/demande/professionSante');
            }
            else {
                $log.info('Formulaire invalide !');
            }
        };
        $scope.nextStep = function () {
            if ($scope.donneesPerso.personalDataForm.$valid) {
                $log.info('Formulaire valide !');
                $scope.indexStep += 1;
                $location.path('/Demaut/demande/donneesProf');
            }
            else {
                $log.info('Formulaire invalide !');
            }
        };
    }])
    .controller('DonneesDiplomesController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', 'urlPrefix', '$log', function ($scope, $rootScope, $routeParams, $http, $location, urlPrefix, $log) {
        $rootScope.contextMenu = "Données Diplômes";
        $scope.indexStep = 3;
        this.name = "DonneesDiplomes";
        this.params = $routeParams;
        $scope.diplomeData = {};
        $scope.diplomeData.typeDiplomes = [];
        $scope.diplomeData.typeFormations = [];
        $scope.diplomeData.paysList = [];
        $scope.diplomeData.diplomes = null;
        $scope.diplomeData.diplome = null;

        if ($scope.diplomeData.typeDiplomes == null || $scope.diplomeData.typeDiplomes == undefined || $scope.diplomeData.typeDiplomes.length == 0) {
            $http.get(urlPrefix + '/diplomes/typeDiplomesList').
                success(function (data, status, headers, config) {
                    $scope.diplomeData.typeDiplomes = angular.fromJson(data.response);
                }).
                error(function (data, status, headers, config) {
                    $rootScope.error = 'Error downloading ../diplomes/typeDiplomesList';
                });
        }

        if ($scope.diplomeData.paysList == null || $scope.diplomeData.paysList == undefined || $scope.diplomeData.paysList.length == 0) {
            $http.get(urlPrefix + '/diplomes/paysList').
                success(function (data, status, headers, config) {
                    $scope.diplomeData.paysList = angular.fromJson(data.response);
                }).
                error(function (data, status, headers, config) {
                    $rootScope.error = 'Error downloading ../diplomes/paysList';
                });
        }

        $scope.fetchListeFormations = function(){
            $http.get(urlPrefix + '/diplomes/typeFormationsList/' + $scope.diplomeData.typeDiplome).
                success(function (data, status, headers, config) {
                    $scope.diplomeData.typeFormations = angular.fromJson(data.response);
                }).
                error(function (data, status, headers, config) {
                    $rootScope.error = 'Error downloading ../diplomes/typeFormationsList/' + $scope.diplomeData.typeDiplome;
                });
        };

        $scope.previewStep = function(){
            $scope.indexStep -= 1;
            $location.path('/Demaut/demande/professionSante');
        };

        $scope.nextStep = function () {
            $scope.indexStep += 1;
            $location.path('/Demaut/demande/annexes');
        };

        $scope.addAnotherDiplome = function(){
            var diplome = [
                {"key": $scope.diplomeData.typeDiplome.id + $scope.diplomeData.typeFormation.id + $scope.diplomeData.dateObtention},
                {"typeDiplome": $scope.diplomeData.typeDiplome},
                {"typeFormation": $scope.diplomeData.typeFormation},
                {"dateObtention": $scope.diplomeData.dateObtention},
                {"paysObtention": $scope.diplomeData.paysObtention},
                {"dateReconnaissance": $scope.diplomeData.dateReconnaissance}
            ];
            $scope.diplomeData.diplomes.push(diplome);
            $scope.diplomeData.typeDiplome = null;
            $scope.diplomeData.typeFormation = null;
            $scope.diplomeData.dateObtention = null;
            $scope.diplomeData.paysObtention = null;
            $scope.diplomeData.dateReconnaissance = null;
        };
    }])
    .controller('AnnexesController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', 'urlPrefix', '$log', function ($scope, $rootScope, $routeParams, $http, $location, urlPrefix, $log) {
        $rootScope.contextMenu = "Annexes";
        $scope.indexStep = 4;
        this.name = "AnnexesController";
        this.params = $routeParams;
        $scope.annexesData = {};
        $scope.annexesData.demandeReference = "toBeDefinedLater";
        $scope.annexesData.profession = "aSaisirEtape2";
        $scope.annexesData.annexeFormats = ["application/pdf", "image/jpg", "image/jpeg", "image/png"];
        $scope.annexesData.annexeTypes = [];
        $scope.annexesData.referenceFiles = [];

        if ($scope.annexesData.annexeTypes == null || $scope.annexesData.annexeTypes.length == 0) {
            $http.get(urlPrefix + '/annexes/typesList/' + $scope.annexesData.profession).
                success(function (data, status, headers, config) {
                    $scope.annexesData.annexeTypes = angular.fromJson(data.response);
                }).
                error(function (data, status, headers, config) {
                    $rootScope.error ='Error downloading ../annexes/typesList/' + $scope.annexesData.profession;
                });
        }

        $scope.backStep = function(){
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
                var isValidFormat = $scope.annexesData.annexeFormats.indexOf(fileType) != -1;
                if (isValidName == false || isValidFormat == false || fileSize <= 0 || fileSize > 1024 * 1024 * fileThreshold) {
                    isValidList = false;
                    break;
                }
            }

            // Add new add files to existing lists
            if (isValidList) {
                if ($scope.annexesData.referenceFiles != null && $scope.annexesData.referenceFiles != undefined &&
                    $scope.annexesData.referenceFiles[typeAnnexe] != null && $scope.annexesData.referenceFiles[typeAnnexe] != undefined &&
                    $scope.annexesData.referenceFiles[typeAnnexe].length > 0) {
                    var existAnnexesList = $scope.annexesData.referenceFiles[typeAnnexe];
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
                            $scope.annexesData.referenceFiles[typeAnnexe].push(currentNewFile);
                            doUploadFile(currentNewFile);
                        }
                    }
                    $scope.files = $scope.annexesData.referenceFiles;
                    $log.info('Document(s) valide(s) !');
                } else {
                    $scope.annexesData.referenceFiles[typeAnnexe] = [];
                    for (var indexH = 0; indexH < newFilesList.length; indexH++) {
                        var currentValidFile = newFilesList[indexH];
                        $scope.annexesData.referenceFiles[typeAnnexe].push(currentValidFile);
                        doUploadFile(currentValidFile);
                    }
                    $scope.files = $scope.annexesData.referenceFiles;
                    $log.info('Document(s) valide(s) !');
                }
            } else {
                $scope.files = $scope.annexesData.referenceFiles;
                $rootScope.error = typeAnnexe + ' : une/plusieurs pièces ne respectent pas les règles de nommage ou ne correspondent pas aux formats supportés (pdf, image)';
                $log.info('Document(s) invalide(s) !');
            }
            $scope.$apply();
        };

        $scope.viewAnnexe = function (file, annexeType) {
            $http.get(urlPrefix + '/annexes/afficher/' + $scope.annexesData.demandeReference + '/' + file.name.replace(/\s/g, ''), {responseType:'arraybuffer'}).
                success(function (data, status, headers, config) {
                    displayAnnexeFromBinary(data);
                }).
                error(function (data, status, headers, config) {
                    alert('Error downloading ../annexes/afficher/' + file.name);
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
            if ($scope.annexesData.referenceFiles != null && $scope.annexesData.referenceFiles != undefined &&
                $scope.annexesData.referenceFiles[annexeType] != null && $scope.annexesData.referenceFiles[annexeType] != undefined &&
                $scope.annexesData.referenceFiles[annexeType].length > 0) {

                for (var indexH = 0; indexH < $scope.annexesData.referenceFiles[annexeType].length; indexH++) {
                    var currentFetchedFile = $scope.annexesData.referenceFiles[annexeType][indexH];
                    if (file.name == currentFetchedFile.name) {
                        $scope.annexesData.referenceFiles[annexeType].splice(indexH, 1);
                        doDeleteFile(currentFetchedFile, annexeType);
                        break;
                    }
                }
            }
            $scope.files = $scope.annexesData.referenceFiles;
        };

        function doDeleteFile(file, annexeFileType) {
            $http.get(urlPrefix + '/annexes/supprimer/' + $scope.annexesData.demandeReference + '/' + file.name.replace(/\s/g, '') + '/' + annexeFileType)
                .success(function (data, status, headers, config) {
                    $rootScope.error = 'Une annexe a été supprimée avec succès: \n Status :' +  status;
                })
                .error(function (data, status, headers, config) {
                    $rootScope.error = 'Error ' + urlPrefix + '/annexes/supprimer/ \n Status :' +  status;
                });
        };

        function doUploadFile(currentFetchedFile) {
            var formData = new FormData();
            formData.append('ajaxAction', 'upload');
            formData.append("demandeReference", $scope.annexesData.demandeReference);
            formData.append("annexeFile", currentFetchedFile);
            formData.append("annexeFileName", currentFetchedFile.name.replace(/\s/g, ''));
            formData.append("annexeFileSize", currentFetchedFile.size);
            formData.append("annexeFileType", currentFetchedFile.type);
            formData.append("annexeType", currentFetchedFile.typeAnnexe);
            $http.post(urlPrefix + '/annexes/attacher', formData, {
                transformRequest: angular.identity,
                headers: {'Content-Type': 'multipart/form-data'}
            })
                .success(function (data, status, headers, config) {
                    $rootScope.error = 'Une annexe a été envoyée avec succès: \n Status :' +  status;
                })
                .error(function (data, status, headers, config) {
                    $rootScope.error = 'Error Upload: [' + urlPrefix + '/annexes/attacher' +  status;
                });
        };
    }])
    .controller('RecapitulatifController', ['$scope', '$rootScope', '$routeParams', '$location', function ($scope, $rootScope, $routeParams, $location) {
        $rootScope.contextMenu = "Recapitulatif";
        $scope.indexStep = 5;
        this.name = "RecapitulatifController";
        this.params = $routeParams;
        $scope.backStep = function(){
            $scope.indexStep -= 1;
            $location.path('/Demaut/demande/annexes');
        };
        $scope.nextStep = function(){
            $scope.indexStep += 1;
            $location.path('/Demaut/demande/soumission');
        };
    }]);

ngDemautApp
    .run(function($rootScope, $sce, $location, $http, urlPrefix) {

        $rootScope.$on('$viewContentLoaded', function() {
        });

        $rootScope.$on('$routeChangeStart', function () {
        });
    });