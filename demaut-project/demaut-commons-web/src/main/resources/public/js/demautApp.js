var ngDemautApp = angular.module('ngDemautApp', ['ngSanitize', 'ngRoute', 'ngAnimate', 'commonsModule', 'ui.bootstrap']);


ngDemautApp
    .config(['$routeProvider', '$locationProvider', '$httpProvider', function ($routeProvider, $locationProvider, $httpProvider) {

        $routeProvider
            .when('/Demaut/recherche', {
                templateUrl: prestationContext + '/template/recherche.html',
                controller: 'RechercheController',
                controllerAs: 'recherche'
            })
            .when('/Demaut/aide', {
                templateUrl: prestationContext + '/template/aide.html',
                controller: 'AideController',
                controllerAs: 'aide'
            })
            .when('/Demaut/formulaire', {
                templateUrl: prestationContext + '/template/formulaire.html',
                controller: 'FormulaireController',
                controllerAs: 'demande'
            })
            .when('/Demaut/demande/professionSante', {
                templateUrl: prestationContext + '/template/demande/professionSante.html',
                controller: 'ProfessionSanteController',
                controllerAs: 'professionSante'
            })
            .when('/Demaut/demande/donneesPerso', {
                templateUrl: prestationContext + '/template/demande/donneesPerso.html',
                controller: 'DonneesPersoController',
                controllerAs: 'donneesPerso'
            })
            .when('/Demaut/demande/donneesDiplomes', {
                templateUrl: prestationContext + '/template/demande/donneesDiplomes.html',
                controller: 'DonneesDiplomesController',
                controllerAs: 'donneesDiplomes'
            })
            .when('/Demaut/demande/donneesActivites', {
                templateUrl: 'template/demande/donneesActivites.html',
                controller: 'DonneesActivitesController',
                controllerAs: 'donneesActivites'
            })
            .when('/Demaut/demande/annexes', {
                templateUrl: prestationContext + '/template/demande/annexes.html',
                controller: 'AnnexesController',
                controllerAs: 'annexes'
            })
            .when('/Demaut/demande/recapitulatif', {
                templateUrl: prestationContext + '/template/demande/recapitulatif.html',
                controller: 'RecapitulatifController',
                controllerAs: 'recapitulatif'
            })
            .when('/Demaut/demande/soumission', {
                templateUrl: prestationContext + '/template/demande/soumission.html',
                controller: 'SoumissionController',
                controllerAs: 'soumission'
            })
            .when('/Demaut/cockpit', {
                templateUrl: prestationContext + '/template/cockpit.html',
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
    }])
    .controller('CockpitController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$interval', '$log',
        function ($scope, $rootScope, $routeParams, $http, $location, $interval, urlPrefix, $log) {
            $rootScope.contextMenu = "Cockpit";
            $scope.indexStep = 0;
            this.name = "CockpitController";
            this.params = $routeParams;
        }])
    .controller('ProfessionSanteController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$log', 'professionTest',
        function ($scope, $rootScope, $routeParams, $http, $location, $log, professionTest) {
            $rootScope.contextMenu = "Profession Santé";
            $scope.indexStep = 1;
            this.name = "ProfessionSanteController";
            this.params = $routeParams;
            $scope.professionData = {};
            $scope.professionData.professions = [];
            $scope.professionData.professionsCodeGLN = [];

            if ($scope.professionData.professions.length == 0) {
                $http.get(urlPrefix + '/profession/professionsList')
                    .success(function (data, status, headers, config) {
                        $scope.professionData.professions = angular.fromJson(data.response);
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error downloading ../profession/professionsList';
                    });
            }

            if ($scope.professionData.professionsCodeGLN.length == 0) {
                $http.get(urlPrefix + '/profession/professionsCodeGLN')
                    .success(function (data, status, headers, config) {
                        $scope.professionData.professionsCodeGLN = angular.fromJson(data.response);
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error downloading ../profession/professionsCodeGLN';
                    });
            }

            //Etape suivante
            $scope.nextStep = function () {
                $scope.wouldStepNext = true;
                if ($scope.professionSante.professionDataForm.$valid &&
                    !(professionTest.isProfessionNecessiteCodeGLN($scope.professionData.profession, $scope.professionData.professionsCodeGLN) &&
                    ($scope.professionData.gln == null || $scope.professionData.gln == undefined))) {

                    $http.get(urlPrefix + '/demande/initialiser', {
                        params: {
                            professionId: $scope.professionData.profession.id,
                            codeGln: $scope.professionData.gln != undefined ? $scope.professionData.gln : null,
                        }
                    })
                        .success(function (data, status, headers, config) {
                            var refDemande = angular.fromJson(data.response);
                            //TODO : Ajouter le error handling if refDemande null ou undefined
                            $log.info('Reference de la nouvelle demande :' + refDemande[0].value);
                        })
                        .error(function (data, status, headers, config) {
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

            $scope.isProfessionNecessiteCodeGLN = function() {
                return $scope.professionData.profession != null && $scope.professionData.profession != undefined &&
                    professionTest.isProfessionNecessiteCodeGLN($scope.professionData.profession, $scope.professionData.professionsCodeGLN)
            };
        }])
    .controller('DonneesPersoController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$interval', '$log', 'nationalityTest',
        function ($scope, $rootScope, $routeParams, $http, $location, $interval, $log, nationalityTest) {
            $rootScope.contextMenu = "Données Personnelles";
            $scope.indexStep = 2;
            this.name = "DonneesPersoController";
            this.params = $routeParams;
            $scope.testSuisse = nationalityTest;//récupération du service de test des nationnalité
            $rootScope.personalData = {};
            $rootScope.personalData.nationalites = [];
            $rootScope.personalData.langues = [];
            $rootScope.personalData.paysList = [];
            $rootScope.personalData.datePicker = {};
            $rootScope.personalData.datePicker.status = {};

            if ($rootScope.personalData.nationalites.length == 0) {
                $http.get(urlPrefix + '/personal/nationalites').
                    success(function (data, status, headers, config) {
                        $rootScope.personalData.nationalites = angular.fromJson(data.response);
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error downloading ../personal/nationalites';
                    });
            }

            if ($rootScope.personalData.langues.length == 0) {
                $http.get(urlPrefix + '/personal/langues').
                    success(function (data, status, headers, config) {
                        $rootScope.personalData.langues = angular.fromJson(data.response);
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error downloading ../personal/langues';
                    });
            }

            if ($rootScope.personalData.paysList.length == 0) {
                $http.get(urlPrefix + '/diplomes/paysList').
                    success(function (data, status, headers, config) {
                        $rootScope.personalData.paysList = angular.fromJson(data.response);
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error downloading ../diplomes/paysList';
                    });
            }

            $rootScope.personalData.datePicker.status.dateDeNaissance = {
                opened: false
            };

            $scope.isPermisRequired = function () {
                return $rootScope.personalData.nationalite != null && $rootScope.personalData.nationalite != undefined && !nationalityTest.isSuisse($rootScope.personalData.nationalite.libl) && !$rootScope.personalData.permis;
            };

            $scope.isSuisse = function () {
                return $rootScope.personalData.nationalite != null && $rootScope.personalData.nationalite != undefined && nationalityTest.isSuisse($rootScope.personalData.nationalite.libl);
            };

            $scope.resetPermis = function () {
                if ($scope.isSuisse()) {
                    $rootScope.personalData.permis = null;
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
                    doCreateDonneesPerso();
                    $scope.indexStep += 1;
                    $location.path('/Demaut/demande/donneesDiplomes');
                }
                else {
                    $log.info('Formulaire invalide !');
                }
            };

            function doCreateDonneesPerso() {
                $http.get(urlPrefix + '/personal/ajouter', {
                    params: {
                        nom: $rootScope.personalData.nom,
                        prenom: $rootScope.personalData.prenom,
                        nomDeCelibataire: $rootScope.personalData.nomDeCelibataire,
                        adressePersonnelle: $rootScope.personalData.adressePersonnelle,
                        localite: $rootScope.personalData.localite,
                        npa: $rootScope.personalData.npa,
                        pays: $rootScope.personalData.pays.id,
                        telephonePrive: $rootScope.personalData.telephonePrive,
                        telephoneMobile: $rootScope.personalData.telephoneMobile,
                        email: $rootScope.personalData.email,
                        fax: $rootScope.personalData.fax,
                        genre: $rootScope.personalData.genre,
                        dateDeNaissance: $rootScope.personalData.dateDeNaissance,
                        nationalite: $rootScope.personalData.nationalite.id,
                        langue: $rootScope.personalData.langue.id,
                        permis: $rootScope.personalData.permis,
                        permisOther: $rootScope.personalData.permisOther
                    }
                })
                    .success(function (data, status, headers, config) {
                        $log.info('Une donnée personnelle a été crée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error ' + urlPrefix + '/personal/ajouter/ \n Status :' + status;
                    });
            };
        }])
    .controller('DonneesDiplomesController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$log', 'nationalityTest',
        function ($scope, $rootScope, $routeParams, $http, $location, $log, nationalityTest) {
            $rootScope.contextMenu = "Données Diplômes";
            $scope.indexStep = 3;
            this.name = "DonneesDiplomes";
            this.params = $routeParams;
            $scope.testSuisse = nationalityTest;
            $scope.diplomeData = {};
            $scope.diplomeData.typeDiplomes = [];
            $scope.diplomeData.typeFormations = [];
            $scope.diplomeData.paysList = [];
            $scope.diplomeData.diplomes = [];
            $scope.diplomeData.diplome = {};
            $scope.diplomeData.datePicker = {};
            $scope.diplomeData.datePicker.status = {};

            if ($scope.diplomeData.typeDiplomes.length == 0) {
                $http.get(urlPrefix + '/diplomes/typeDiplomesList').
                    success(function (data, status, headers, config) {
                        $scope.diplomeData.typeDiplomes = angular.fromJson(data.response);
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error downloading ../diplomes/typeDiplomesList';
                    });
            }

            if ($scope.diplomeData.paysList.length == 0) {
                $http.get(urlPrefix + '/diplomes/paysList').
                    success(function (data, status, headers, config) {
                        $scope.diplomeData.paysList = angular.fromJson(data.response);
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error downloading ../diplomes/paysList';
                    });
            }

            $scope.diplomeData.datePicker.status.dateObtention = {
                opened: false
            };
            $scope.diplomeData.datePicker.status.dateReconnaissance = {
                opened: false
            };

            $scope.isDateReconnaissanceRequired = function () {
                return $scope.diplomeData.diplome.paysObtention != null && $scope.diplomeData.diplome.paysObtention != undefined && !nationalityTest.isSuisse($scope.diplomeData.diplome.paysObtention.libl);
            };

            $scope.isDateReconnaissanceSaisie = function () {
                return ($scope.diplomeData.diplome.paysObtention == null && $scope.diplomeData.diplome.paysObtention == undefined)
                    ||
                    ($scope.diplomeData.diplome.paysObtention != null && $scope.diplomeData.diplome.paysObtention != undefined &&
                    nationalityTest.isSuisse($scope.diplomeData.diplome.paysObtention.libl))
                    ||
                    (!nationalityTest.isSuisse($scope.diplomeData.diplome.paysObtention.libl) &&
                    $scope.diplomeData.diplome.dateReconnaissance != null && $scope.diplomeData.diplome.dateReconnaissance != undefined && $scope.diplomeData.diplome.dateReconnaissance != '');
            };

            $scope.fetchListeFormations = function () {
                $http.get(urlPrefix + '/diplomes/typeFormationsList', {
                    params: {typeDiplome: $scope.diplomeData.diplome.typeDiplome.id}
                }).
                    success(function (data, status, headers, config) {
                        $scope.diplomeData.typeFormations = angular.fromJson(data.response);
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error downloading ../diplomes/typeFormationsList/' + $scope.diplomeData.diplome.typeDiplome.id;
                    });
            };

            $scope.backStep = function () {
                $scope.indexStep -= 1;
                $location.path('/Demaut/demande/donneesPerso');
            };

            $scope.nextStep = function () {
                $scope.wouldStepNext = true;
                if ($scope.donneesDiplome.diplomeDataForm.$valid) {
                    $log.info('Formulaire valide !');
                    $scope.indexStep += 1;
                    $location.path('/Demaut/demande/donneesActivites');
                }
                else {
                    $log.info('Formulaire invalide !');
                }
            };

            $scope.addAnotherDiplome = function () {
                $scope.wouldAddDiplome = true;
                var isValidTypeDiplome = $scope.diplomeData.diplome.typeDiplome != null && $scope.diplomeData.diplome.typeDiplome != undefined;
                var isValidTypeFormation = $scope.diplomeData.diplome.typeFormation != null && $scope.diplomeData.diplome.typeFormation != undefined;
                var isValidDateObtention = $scope.diplomeData.diplome.dateObtention != null && $scope.diplomeData.diplome.dateObtention != undefined && $scope.diplomeData.diplome.dateObtention != '';
                var isValidPaysObtention = $scope.diplomeData.diplome.paysObtention != null && $scope.diplomeData.diplome.paysObtention != undefined;

                if (isValidTypeDiplome && isValidTypeFormation && isValidDateObtention && isValidPaysObtention && $scope.isDateReconnaissanceSaisie()) {
                    var diplomeKey = $scope.diplomeData.diplome.typeDiplome.id + '#' + $scope.diplomeData.diplome.typeFormation.id + '#' + $scope.diplomeData.diplome.dateObtention;
                    var diplome = angular.copy($scope.diplomeData.diplome);
                    diplome.referenceDeDiplome = diplomeKey.replace(/\s/g, '');
                    diplome.dateObtention = new Date(diplome.dateObtention);
                    diplome.dateReconnaissance = diplome.dateReconnaissance == null || diplome.dateReconnaissance == undefined || diplome.dateReconnaissance == '' ? '' : new Date(diplome.dateObtention);

                    // TODO attendre la reponse server avant de faire le push
                    $scope.diplomeData.diplomes.push(diplome);
                    doCreateDiplome(diplome);
                    $scope.diplomeData.diplome = {};
                    $scope.donneesDiplome.diplomeDataForm.$valid = true;
                    $scope.donneesDiplome.diplomeDataForm.$error = {};
                    $scope.donneesDiplome.diplomeDataForm.$setPristine();
                    $scope.wouldAddDiplome = false;
                }
            };

            $scope.editDiplome = function (targetDiplome) {
                for (var indexI = 0; indexI < $scope.diplomeData.diplomes.length; indexI++) {
                    if ($scope.diplomeData.diplomes[indexI].referenceDeDiplome == targetDiplome.referenceDeDiplome) {
                        $scope.diplomeData.diplome = angular.copy(targetDiplome);
                        $scope.diplomeData.diplome.referenceDeDiplome = '';

                        // TODO attendre la reponse server avant de faire le splice
                        $scope.diplomeData.diplomes.splice(indexI, 1);
                        doDeleteDiplome(targetDiplome);
                        $scope.donneesDiplome.diplomeDataForm.$valid = true;
                        $scope.donneesDiplome.diplomeDataForm.$error = {};
                        $scope.donneesDiplome.diplomeDataForm.$setPristine();
                        $scope.wouldEditDiplome = true;
                        //$scope.$apply();
                        break;
                    }
                }
            };

            $scope.deleteDiplome = function (targetDiplome) {
                for (var indexI = 0; indexI < $scope.diplomeData.diplomes.length; indexI++) {
                    if ($scope.diplomeData.diplomes[indexI].referenceDeDiplome == targetDiplome.referenceDeDiplome) {
                        // TODO attendre la reponse server avant de faire le splice
                        $scope.diplomeData.diplomes.splice(indexI, 1);
                        doDeleteDiplome(targetDiplome);
                        break;
                    }
                }
            };

            function doCreateDiplome(targetDiplome) {
                $http.get(urlPrefix + '/diplomes/ajouter', {
                    params: {
                        referenceDeDiplome: targetDiplome.referenceDeDiplome,
                        typeDiplome: targetDiplome.typeDiplome.id,
                        typeFormation: targetDiplome.typeFormation.id,
                        complement: targetDiplome.complement,
                        dateObtention: targetDiplome.dateObtention,
                        paysObtention: targetDiplome.paysObtention.id,
                        dateReconnaissance: targetDiplome.dateReconnaissance != undefined ? targetDiplome.dateReconnaissance : null
                    }
                })
                    .success(function (data, status, headers, config) {
                        $log.info('Un diplôme a été crée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error ' + urlPrefix + '/diplomes/ajouter/ \n Status :' + status;
                    });
            };

            function doDeleteDiplome(targetDiplome) {
                $http.get(urlPrefix + '/diplomes/supprimer', {
                    params: {
                        referenceDeDiplome: targetDiplome.referenceDeDiplome
                    }
                })
                    .success(function (data, status, headers, config) {
                        $log.info('Un diplôme a été supprimé avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error ' + urlPrefix + '/diplomes/supprimer/ \n Status :' + status;
                    });
            };
        }])
    .controller('DonneesActivitesController', ['$scope', '$rootScope', '$routeParams', '$location', '$log',
        function ($scope, $rootScope, $routeParams, $location, $log) {
            $rootScope.contextMenu = "Données Activités";
            $scope.indexStep = 4;
            this.name = "DonneesActivitesController";
            this.params = $routeParams;
            $scope.activiteData = {};
            $scope.activiteData.activities = [];
            $scope.activiteData.activitie = {};
            $scope.activiteData.datePicker = {};
            $scope.activiteData.datePicker.status = {};

            $scope.activiteData.datePicker.status.dateDebutIndependant = {
                opened: false
            };

            $scope.activiteData.datePicker.status.dateDebutDependant = {
                opened: false
            };

            $scope.backStep = function () {
                $scope.indexStep -= 1;
                $location.path('/Demaut/demande/DonneesDiplomes');
            };

            $scope.nextStep = function () {
                $scope.wouldStepNext = true;
                if ($scope.donneesActivite.donneesActiviteForm.$valid) {
                    $log.info('Formulaire valide !');
                    $scope.indexStep += 1;
                    $location.path('/Demaut/demande/annexes');
                }
                else {
                    $log.info('Formulaire invalide !');
                }
            };

            $scope.addAnotherActivite = function () {
                $scope.wouldAddActivite = true;
                var keyActivite = $scope.activiteData.activitie.etablissement + '#' + $scope.activiteData.activitie.npa + '#' + $scope.activiteData.activitie.flagTauxIndependant;
                var activite = angular.copy($scope.activiteData.activitie);
                activite.referenceDeActivite = keyActivite.replace(/\s/g, '');

                $scope.activiteData.activities.push(activite);
                doCreateActivite(activite);
                $scope.activiteData.activitie = {};
                $scope.donneesActivite.donneesActiviteForm.$valid = true;
                $scope.donneesActivite.donneesActiviteForm.$error = null;
                $scope.donneesActivite.donneesActiviteForm.$setPristine();
                $scope.wouldAddActivite = false;
            };

            $scope.editActivite = function (targetActivite) {
                for (var indexI = 0; indexI < $scope.activiteData.activities.length; indexI++) {
                    if ($scope.activiteData.activities[indexI].referenceDeActivite == targetActivite.referenceDeActivite) {
                        $s$scope.activiteData.activitie = angular.copy(targetActivite);
                        $$scope.activiteData.activitie.referenceDeActivite = null;

                        // TODO attendre la reponse server avant de faire le splice
                        $scope.activiteData.activities.splice(indexI, 1);
                        doDeleteActivite(targetActivite);
                        $scope.donneesActivite.diplomeDataForm.$valid = true;
                        $scope.donneesActivite.diplomeDataForm.$setPristine();
                        $scope.wouldEditActivite = true;
                        //$scope.$apply();
                        break;
                    }
                }
            };

            $scope.deleteActivite = function (targetActivite) {
                for (var indexI = 0; indexI < $scope.activiteData.activities.length; indexI++) {
                    if ($scope.activiteData.activities[indexI].referenceDeActivite == targetActivite.referenceDeActivite) {
                        // TODO attendre la reponse server avant de faire le splice
                        $scope.activiteData.activities.splice(indexI, 1);
                        doDeleteActivite(targetActivite);
                        break;
                    }
                }
            };

            function doCreateActivite(targetActivite) {
                $http.get(urlPrefix + '/activites/ajouter', {
                    params: {
                        referenceDeActivite: targetActivite.referenceDeActivite,
                        etablissement: targetActivite.etablissement,
                        adresseRue: targetActivite.adresseRue,
                        npa: targetActivite.npa,
                        localite: targetActivite.localite,
                        telephoneProf: targetActivite.telephoneProf,
                        telephoneMobile: targetActivite.telephoneMobile,
                        email: targetActivite.email,
                        fax: targetActivite.fax,
                        site: targetActivite.site,
                        flagTauxIndependant: targetActivite.flagTauxIndependant,
                        tauxIndependant: targetActivite.tauxIndependant,
                        dateDebutIndependant: targetActivite.dateDebutIndependant,
                        flagTauxDependant: targetActivite.flagTauxDependant,
                        tauxDependant: targetActivite.tauxDependant,
                        dateDebutDependant: targetActivite.dateDebutDependant,
                        pratiquerBase: targetActivite.pratiquerBase
                    }
                })
                    .success(function (data, status, headers, config) {
                        $log.info('Une activité a été crée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error ' + urlPrefix + '/activites/ajouter/ \n Status :' + status;
                    });
            };

            function doDeleteActivite(targetActivite) {
                $http.get(urlPrefix + '/activites/supprimer', {
                    params: {
                        referenceDeActivite: targetActivite.referenceDeActivite
                    }
                })
                    .success(function (data, status, headers, config) {
                        $log.info('Une activité a été supprimée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error ' + urlPrefix + '/activites/supprimer/ \n Status :' + status;
                    });
            };
        }])
    .controller('AnnexesController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$log', '$q', '$timeout',
        function ($scope, $rootScope, $routeParams, $http, $location, $log, $q, $timeout) {
            $rootScope.contextMenu = "Annexes";
            $scope.indexStep = 5;
            this.name = "AnnexesController";
            this.params = $routeParams;
            $scope.annexesData = {};
            $scope.annexesData.annexeFormats = ["application/pdf", "image/jpg", "image/jpeg", "image/png"];
            $scope.annexesData.annexeTypes = [];
            $scope.annexesData.annexeTypesObligatoires = [];
            $scope.annexesData.referenceFiles = [];
            $scope.annexesData.annexeTypeSelected = {};

            if ($scope.annexesData.annexeTypes.length == 0) {
                $http.get(urlPrefix + '/annexes/typesList').
                    success(function (data, status, headers, config) {
                        $scope.annexesData.annexeTypes = angular.fromJson(data.response);
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error downloading ../annexes/typesList';
                    });
            }

            if ($scope.annexesData.annexeTypesObligatoires.length == 0) {
                $http.get(urlPrefix + '/annexes/typesObligatoiresList').
                    success(function (data, status, headers, config) {
                        $scope.annexesData.annexeTypesObligatoires = angular.fromJson(data.response);
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error downloading ../annexes/typesObligatoiresList';
                    });
            }

            $scope.backStep = function () {
                $scope.indexStep -= 1;
                $location.path('/Demaut/demande/donneesActivites');
            };

            $scope.nextStep = function () {
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

            $scope.isAnnexeTypeNotSelected = function () {
                return $scope.annexesData.annexeTypeSelected == null || $scope.annexesData.annexeTypeSelected == undefined ||
                    $scope.annexesData.annexeTypeSelected.id == null;
            };

            $scope.filesChanged = function (targetFiles) {
                $scope.files = [];

                var fileThreshold = 3; // MB
                var newFilesList = targetFiles.files;
                var typeAnnexe = $scope.annexesData.annexeTypeSelected.id;
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
                        $scope.annexesData.referenceFiles.length > 0) {
                        var existAnnexesList = $scope.annexesData.referenceFiles;
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
                                $scope.annexesData.referenceFiles.push(currentNewFile);
                                var promise = $timeout(doUploadFile, 2000);
                                $timeout.cancel($timeout(doUploadFile, 2000));
                                // TODO attendre la reponse server avant de faire le push
                                doUploadFile(currentNewFile);
                            }
                        }
                        $scope.files = $scope.annexesData.referenceFiles;
                        $scope.annexesData.annexeTypeSelected = {};
                        $log.info('Document(s) valide(s) !');
                    } else {
                        $scope.annexesData.referenceFiles = [];
                        for (var indexH = 0; indexH < newFilesList.length; indexH++) {
                            var currentValidFile = newFilesList[indexH];
                            // TODO attendre la reponse server avant de faire le push
                            $scope.annexesData.referenceFiles.push(currentValidFile);
                            doUploadFile(currentValidFile);
                        }
                        $scope.files = $scope.annexesData.referenceFiles;
                        $scope.annexesData.annexeTypeSelected = {};
                        $log.info('Document(s) valide(s) !');
                    }
                } else {
                    $scope.files = $scope.annexesData.referenceFiles;
                    $scope.annexesData.annexeTypeSelected = {};
                    $rootScope.error = typeAnnexe + ' : une/plusieurs pièces ne respectent pas les règles de nommage ou ne correspondent pas aux formats supportés (pdf, image)';
                    $log.info($rootScope.error);
                }
                //$scope.$apply();
            };

            $scope.viewAnnexe = function (file) {
                $scope.filename = file.name;
                $http.get(urlPrefix + '/annexes/afficher/', {
                    responseType: 'arraybuffer',
                    params: {
                        annexeFileName: file.name,
                        annexeType: file.typeAnnexe
                    }
                })
                    .success(function (data, status, headers, config) {
                        displayAnnexeFromBinary(data);
                    }).
                    error(function (data, status, headers, config) {
                        alert('Error downloading ../annexes/afficher/' + file.name);
                    });

                function displayAnnexeFromBinary(data) {
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

            $scope.deleteAnnexe = function (file) {
                if ($scope.annexesData.referenceFiles != null && $scope.annexesData.referenceFiles != undefined &&
                    $scope.annexesData.referenceFiles.length > 0) {

                    for (var indexH = 0; indexH < $scope.annexesData.referenceFiles.length; indexH++) {
                        var currentFetchedFile = $scope.annexesData.referenceFiles[indexH];
                        if (file.name == currentFetchedFile.name) {
                            // TODO attendre la reponse server avant de faire le splice
                            $scope.annexesData.referenceFiles.splice(indexH, 1);
                            doDeleteFile(currentFetchedFile, file.typeAnnexe);
                            break;
                        }
                    }
                }
                $$scope.files = $scope.annexesData.referenceFiles;
                $scope.annexesData.annexeTypeSelected = {};
            };

            function doDeleteFile(file) {
                $http.get(urlPrefix + '/annexes/supprimer', {
                    params: {
                        annexeFileName: file.name,
                        annexeType: file.typeAnnexe
                    }
                })
                    .success(function (data, status, headers, config) {
                        $log.info('Une annexe a été supprimée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error ' + urlPrefix + '/annexes/supprimer/ \n Status :' + status;
                    });
            };

            function doUploadFile(currentFetchedFile) {
                var formData = new FormData();
                formData.append('ajaxAction', 'upload');
                formData.append("annexeFile", currentFetchedFile);
                formData.append("annexeFileName", currentFetchedFile.name);
                formData.append("annexeFileSize", currentFetchedFile.size);
                formData.append("annexeFileType", currentFetchedFile.type);
                formData.append("annexeType", currentFetchedFile.typeAnnexe);
                var deferred = $q.defer();
                $http.post(urlPrefix + '/annexes/attacher', formData, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': 'multipart/form-data'},
                    timeout: deferred.promise
                })
                    .success(function (data, status, headers, config) {
                        $log.info('Une annexe a été envoyée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error Upload: [' + urlPrefix + '/annexes/attacher' + status;
                    });
                $timeout(function () {
                    deferred.resolve();
                }, 10000);
            };
        }])
    .controller('RecapitulatifController', ['$scope', '$rootScope', '$routeParams', '$location', '$log',
        function ($scope, $rootScope, $routeParams, $location, $log) {
            $rootScope.contextMenu = "Recapitulatif";
            $scope.indexStep = 6;
            this.name = "RecapitulatifController";
            this.params = $routeParams;

            $scope.backStep = function () {
                $scope.indexStep -= 1;
                $location.path('/Demaut/demande/annexes');
            };

            $scope.nextStep = function () {
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
    .run(function ($rootScope, $sce, $location, $http) {

        $rootScope.datePicker = {};
        $rootScope.datePicker.minDate = new Date('1900-01-01');
        $rootScope.datePicker.naxDate = new Date();
        $rootScope.datePicker.placeholder = 'jj.mm.yyyy';
        $rootScope.datePicker.format = 'dd.MM.yyyy';
        $rootScope.datePicker.options = {
            formatYear: 'yyyyy',
            startingDay: 1
        };

        $rootScope.openDatePicker = function (targetDatepicker) {
            targetDatepicker.opened = true;
        };

        $rootScope.$on('$viewContentLoaded', function () {
        });

        $rootScope.$on('$routeChangeStart', function () {
        });

    });