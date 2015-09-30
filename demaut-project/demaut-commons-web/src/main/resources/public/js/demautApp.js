var ngDemautApp = angular.module('ngDemautApp', ['ngSanitize', 'ngRoute', 'ngAnimate', 'commonsModule','ngStorage', ]);

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
            .when('/Demaut/demande/recapitulatif', {
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
    .controller('ProfessionSanteController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', 'urlPrefix', '$log', 'professionTest', '$sessionStorage', function ($scope, $rootScope, $routeParams, $http, $location, urlPrefix, $log, professionTest, $sessionStorage) {
        $rootScope.contextMenu = "Profession Santé";
        $scope.indexStep = 1;
        this.name = "ProfessionSanteController";
        this.params = $routeParams;
        $scope.$storage = $sessionStorage;
        $scope.professionData = {};
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
            $scope.wouldStepNext = true;
            if ($scope.professionSante.professionDataForm.$valid) {
                $http.get(urlPrefix + '/demande/initialiser/' + $scope.professionData.profession.id).
                    success(function (data, status, headers, config) {
                    	var refDemande = angular.fromJson(data.response);
                    	//TODO : Ajouter le error handling if refDemande null ou undefined 
                    	$scope.$storage.refDemande = refDemande.value;
                        $log.info('Reference de la nouvelle demande :' + $scope.$storage.refDemande);
                    }).
                    error(function (data, status, headers, config) {
                    	delete $scope.$storage.refDemande;
                        $rootScope.error = 'Error from response /demande/initialiser/';
                    });
                //Go to next page
                $scope.indexStep += 1;
                $location.path('/Demaut/demande/donneesPerso');
            }
            else {
                $log.info('Formulaire invalide !');
            }
        };
    }])
    .controller('DonneesPersoController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$interval', 'urlPrefix', '$log', 'nationalityTest', '$sessionStorage', function ($scope, $rootScope, $routeParams, $http, $location, $interval, urlPrefix, $log, nationalityTest, $sessionStorage) {
        $rootScope.contextMenu = "Données Personnelles";
        $scope.indexStep = 2;
        this.name = "DonneesPersoController";
        this.params = $routeParams;
        $scope.testSuisse = nationalityTest;//récupération du service de test des nationnalité
        $scope.personalData = {};
        $scope.personalData.nationalites = [];

        if ($scope.personalData.nationalites == null || $scope.personalData.nationalites == undefined || $scope.personalData.nationalites.length == 0) {
            $http.get(urlPrefix + '/personal/nationalites').
                success(function (data, status, headers, config) {
                    $scope.personalData.nationalites = angular.fromJson(data.response);
                }).
                error(function (data, status, headers, config) {
                    $rootScope.error = 'Error downloading ../personal/nationalites';
                });
        }

        $scope.isPermisRequired = function () {
            return $scope.personalData.nationalite != null && $scope.personalData.nationalite != undefined && !nationalityTest.isSuisse($scope.personalData.nationalite.libl) && !$scope.personalData.permis;
        };

        $scope.isSuisse = function () {
            return $scope.personalData.nationalite != null && $scope.personalData.nationalite != undefined && nationalityTest.isSuisse($scope.personalData.nationalite.libl);
        };

        $scope.resetPermis = function () {
            if ($scope.isSuisse()) {
                $scope.personalData.permis = null;
            }
        };

        $scope.backStep = function () {
            $scope.indexStep -= 1;
            $location.path('/Demaut/demande/professionSante');
        };

        $scope.nextStep = function () {
            $scope.wouldStepNext = true;
            if ($scope.donneesPerso.donneesPersoDataForm.$valid) {
                $log.info('Formulaire valide !');
                $scope.indexStep += 1;
                $location.path('/Demaut/demande/donneesDiplomes');
            }
            else {
                $log.info('Formulaire invalide !');
            }
        };
    }])
    .controller('DonneesDiplomesController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', 'urlPrefix', '$log', 'nationalityTest', '$sessionStorage', function ($scope, $rootScope, $routeParams, $http, $location, urlPrefix, $log, nationalityTest, $sessionStorage) {
        $rootScope.contextMenu = "Données Diplômes";
        $scope.indexStep = 3;
        this.name = "DonneesDiplomes";
        this.params = $routeParams;
        $scope.$storage = $sessionStorage;
        $scope.testSuisse = nationalityTest;
        $scope.diplomeData = {};
        $scope.diplomeData.typeDiplomes = [];
        $scope.diplomeData.typeFormations = [];
        $scope.diplomeData.paysList = [];
        $scope.diplomeData.diplomes = [];

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

        $scope.isDateReconnaissanceRequired = function () {
            return $scope.diplomeData.paysObtention != null && $scope.diplomeData.paysObtention != undefined &&
                !nationalityTest.isSuisse($scope.diplomeData.paysObtention.libl);
        };

        $scope.isDateReconnaissanceSaisie = function () {
            return ($scope.diplomeData.paysObtention == null &&  $scope.diplomeData.paysObtention == undefined)
                ||
                ($scope.diplomeData.paysObtention != null &&  $scope.diplomeData.paysObtention != undefined &&
                    nationalityTest.isSuisse($scope.diplomeData.paysObtention.libl))
                ||
                (!nationalityTest.isSuisse($scope.diplomeData.paysObtention.libl) &&
                    $scope.diplomeData.dateReconnaissance != null && $scope.diplomeData.dateReconnaissance != undefined && $scope.diplomeData.dateReconnaissance != '');
        };

        $scope.fetchListeFormations = function(){
            $http.get(urlPrefix + '/diplomes/typeFormationsList/' + $scope.diplomeData.typeDiplome.id).
                success(function (data, status, headers, config) {
                    $scope.diplomeData.typeFormations = angular.fromJson(data.response);
                }).
                error(function (data, status, headers, config) {
                    $rootScope.error = 'Error downloading ../diplomes/typeFormationsList/' + $scope.diplomeData.typeDiplome.id;
                });
        };

        $scope.backStep = function(){
            $scope.indexStep -= 1;
            $location.path('/Demaut/demande/donneesPerso');
        };

        $scope.nextStep = function () {
            $scope.wouldStepNext = true;
            if ($scope.donneesDiplome.diplomeDataForm.$valid) {
                $log.info('Formulaire valide !');
                $scope.indexStep += 1;
                $location.path('/Demaut/demande/annexes');
            }
            else {
                $log.info('Formulaire invalide !');
            }
        };

        $scope.addAnotherDiplome = function(){
            $scope.wouldAddDiplome = true;
            var isValidTypeDiplome = $scope.diplomeData.typeDiplome != null && $scope.diplomeData.typeDiplome != undefined;
            var isValidTypeFormation = $scope.diplomeData.typeFormation != null && $scope.diplomeData.typeFormation != undefined;
            var isValidDateObtention = $scope.diplomeData.dateObtention != null && $scope.diplomeData.dateObtention != undefined && $scope.diplomeData.dateObtention != '';
            var isValidPaysObtention = $scope.diplomeData.paysObtention != null && $scope.diplomeData.paysObtention != undefined;

            if (isValidTypeDiplome && isValidTypeFormation && isValidDateObtention && isValidPaysObtention && $scope.isDateReconnaissanceSaisie()) {
                var diplomeKey = $scope.diplomeData.typeDiplome.id + '#' + $scope.diplomeData.typeFormation.id + '#' + $scope.diplomeData.dateObtention;
                var diplome = [
                    {   keyDiplome: diplomeKey.replace(/\s/g, ''),
                        typeDiplome: $scope.diplomeData.typeDiplome,
                        typeFormation: $scope.diplomeData.typeFormation,
                        dateObtention: $scope.diplomeData.dateObtention,
                        paysObtention: $scope.diplomeData.paysObtention,
                        dateReconnaissance: $scope.diplomeData.dateReconnaissance
                    }
                ];

                // TODO attendre la reponse server avant de faire le push
                $scope.diplomeData.diplomes.push(diplome);
                doCreateDiplome(diplome)
                $scope.diplomeData.typeDiplome = null;
                $scope.diplomeData.typeFormation = null;
                $scope.diplomeData.dateObtention = null;
                $scope.diplomeData.paysObtention = null;
                $scope.diplomeData.dateReconnaissance = null;
                $scope.donneesDiplome.diplomeDataForm.$valid = true;
                $scope.donneesDiplome.diplomeDataForm.$error = null;
                $scope.donneesDiplome.diplomeDataForm.$setPristine();
                $scope.wouldAddDiplome = false;
            }
        };

        $scope.editDiplome = function(targetDiplome){
            for (var indexI = 0; indexI < $scope.diplomeData.diplomes.length; indexI++) {
                if ($scope.diplomeData.diplomes[indexI][0].keyDiplome == targetDiplome[0].keyDiplome) {
                    $scope.diplomeData.typeDiplome = targetDiplome[0].typeDiplome;
                    $scope.diplomeData.typeFormation = targetDiplome[0].typeFormation;
                    $scope.diplomeData.dateObtention = targetDiplome[0].dateObtention;
                    $scope.diplomeData.paysObtention = targetDiplome[0].paysObtention;
                    $scope.diplomeData.dateReconnaissance = targetDiplome[0].dateReconnaissance;
                    // TODO attendre la reponse server avant de faire le splice
                    $scope.diplomeData.diplomes.splice(indexI, 1);
                    doDeleteDiplome(targetDiplome);
                    $scope.donneesDiplome.diplomeDataForm.$valid = true;
                    $scope.donneesDiplome.diplomeDataForm.$setPristine();
                    $scope.wouldEditDiplome = true;
                    $scope.$apply();
                    break;
                }
            }
        };

        $scope.deleteDiplome = function(targetDiplome){
            for (var indexI = 0; indexI < $scope.diplomeData.diplomes.length; indexI++) {
                if ($scope.diplomeData.diplomes[indexI][0].keyDiplome == targetDiplome[0].keyDiplome) {
                    // TODO attendre la reponse server avant de faire le splice
                    $scope.diplomeData.diplomes.splice(indexI, 1);
                    doDeleteDiplome(targetDiplome);
                    break;
                }
            }
        };

        function doCreateDiplome(targetDiplome) {
            $http.get(urlPrefix + '/diplomes/ajouter/' + $scope.$storage.refDemande, {
                    params: {
                        keyDiplome: targetDiplome[0].keyDiplome,
                        typeDiplome: targetDiplome[0].typeDiplome.id,
                        typeFormation: targetDiplome[0].typeFormation.id,
                        dateObtention: targetDiplome[0].dateObtention,
                        paysObtention: targetDiplome[0].paysObtention.id,
                        dateReconnaissance: targetDiplome[0].dateReconnaissance == null || targetDiplome[0].dateReconnaissance == undefined || targetDiplome[0].dateReconnaissance == '' ? '-' : targetDiplome[0].dateReconnaissance
                    }
                })
                .success(function (data, status, headers, config) {
                    $rootScope.error = 'Un diplôme a été supprimé avec succès: \n Status :' +  status;
                })
                .error(function (data, status, headers, config) {
                    $rootScope.error = 'Error ' + urlPrefix + '/diplomes/ajouter/ \n Status :' +  status;
                });
        };

        function doDeleteDiplome(targetDiplome) {
            $http.get(urlPrefix + '/diplomes/supprimer/' + $scope.$storage.refDemande,{
                params: {
                    keyDiplome: targetDiplome[0].keyDiplome
                }
            })
                .success(function (data, status, headers, config) {
                    $rootScope.error = 'Un diplôme a été supprimé avec succès: \n Status :' +  status;
                })
                .error(function (data, status, headers, config) {
                    $rootScope.error = 'Error ' + urlPrefix + '/diplomes/supprimer/ \n Status :' +  status;
                });
        };
    }])
    .controller('AnnexesController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', 'urlPrefix', '$log', '$sessionStorage', function ($scope, $rootScope, $routeParams, $http, $location, urlPrefix, $log, $sessionStorage) {
        $rootScope.contextMenu = "Annexes";
        $scope.indexStep = 4;
        this.name = "AnnexesController";
        this.params = $routeParams;
        $scope.$storage = $sessionStorage;
        $scope.annexesData = {};
        $scope.$storage = $sessionStorage;
        $scope.annexesData.annexeFormats = ["application/pdf", "image/jpg", "image/jpeg", "image/png"];
        $scope.annexesData.annexeTypes = [];
        $scope.annexesData.referenceFiles = [];

        if ($scope.$storage.refDemande == undefined) {
        	console.log("Pas de Reference de demande");
        }

        if ($scope.annexesData.annexeTypes == null || $scope.annexesData.annexeTypes.length == 0) {
            $http.get(urlPrefix + '/annexes/typesList').
                success(function (data, status, headers, config) {
                    $scope.annexesData.annexeTypes = angular.fromJson(data.response);
                }).
                error(function (data, status, headers, config) {
                    $rootScope.error ='Error downloading ../annexes/typesList';
                });
        }

        $scope.backStep = function(){
            $scope.indexStep -= 1;
            $location.path('/Demaut/demande/donneesDiplomes');
        };

        $scope.nextStep = function(){
            $scope.wouldStepNext = true;
            if ($scope.annexes.annexesDataForm.$valid && $scope.annexesData.referenceFiles.length > 0) {
                $log.info('Formulaire valide !');
                $scope.indexStep += 1;
                $location.path('/Demaut/demande/recapitulatif');
            }
            else {
                $log.info('Formulaire invalide !');
            }
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
                            // TODO attendre la reponse server avant de faire le push
                            doUploadFile(currentNewFile);
                        }
                    }
                    $scope.files = $scope.annexesData.referenceFiles;
                    $log.info('Document(s) valide(s) !');
                } else {
                    $scope.annexesData.referenceFiles[typeAnnexe] = [];
                    for (var indexH = 0; indexH < newFilesList.length; indexH++) {
                        var currentValidFile = newFilesList[indexH];
                        // TODO attendre la reponse server avant de faire le push
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

        $scope.viewAnnexe = function (file, annexeTypeId) {
        	$scope.filename = file.name;
            $http.get(urlPrefix + '/annexes/afficher/' + $scope.$storage.refDemande + '/' + $scope.filename + '/' + annexeTypeId, {responseType:'arraybuffer'}).
            	success(function (data, status, headers, config) {
                    displayAnnexeFromBinary(data);
                }).
                error(function (data, status, headers, config) {
                    alert('Error downloading ../annexes/afficher/' + file.name);
                });

            function displayAnnexeFromBinary(data){
            	//data = ArrayBuffer, ArrayBufferView, Blob ou DOMString. Voir https://developer.mozilla.org/fr/docs/Web/API/Blob
                var binary = new Blob([data], {type: data.type});
                var fileURL = URL.createObjectURL(binary);
                console.log(fileURL);
                var elementLink = document.createElement('A');
                elementLink.href = fileURL;
                elementLink.target = '_blank';
                elementLink.download = $scope.filename;
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
                        // TODO attendre la reponse server avant de faire le splice
                        $scope.annexesData.referenceFiles[annexeType].splice(indexH, 1);
                        doDeleteFile(currentFetchedFile, annexeType);
                        break;
                    }
                }
            }
            $scope.files = $scope.annexesData.referenceFiles;
        };

        function doDeleteFile(file, annexeType) {
            $http.get(urlPrefix + '/annexes/supprimer/' + $scope.$storage.refDemande + '/' + file.name + '/' + annexeType)
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
            formData.append("demandeReference", $scope.$storage.refDemande);
            formData.append("annexeFile", currentFetchedFile);
            formData.append("annexeFileName", currentFetchedFile.name);
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
    .controller('RecapitulatifController', ['$scope', '$rootScope', '$routeParams', '$location', 'urlPrefix', '$log', '$sessionStorage', function ($scope, $rootScope, $routeParams, $location, urlPrefix, $log, $sessionStorage) {
        $rootScope.contextMenu = "Recapitulatif";
        $scope.indexStep = 5;
        this.name = "RecapitulatifController";
        this.params = $routeParams;
        $scope.$storage = $sessionStorage;
        $scope.backStep = function(){
            $scope.indexStep -= 1;
            $location.path('/Demaut/demande/annexes');
        };
        $scope.nextStep = function(){
            $scope.wouldStepNext = true;
            if ($scope.recapitulatif.recapitulatifDataForm.$valid) {
                $log.info('Formulaire valide !');
                $scope.indexStep += 1;
                $location.path('/Demaut/demande/soumission');
            }
            else {
                $log.info('Formulaire invalide !');
            }
        };
    }]);

ngDemautApp
    .run(function($rootScope, $sce, $location, $http, urlPrefix) {

        $rootScope.$on('$viewContentLoaded', function() {
        });

        $rootScope.$on('$routeChangeStart', function () {
        });
    });