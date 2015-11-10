"use strict";
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

        $httpProvider.interceptors.push('globalDefaultError');
    }]);

ngDemautApp.controller('CockpitController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$interval', '$log', '$window',
    function ($scope, $rootScope, $routeParams, $http, $location, $interval, $log, $window) {
        $rootScope.contextMenu = 'Cockpit';
        $scope.indexStep = 0;
        this.name = "CockpitController";
        this.params = $routeParams;
        $scope.cockpitData = {};
        $scope.cockpitData.demandesBrouillons = [];
        $scope.cockpitData.doDelete = false;
        $window.localStorage.removeItem('referenceDeDemande');

        $http.get(urlPrefix + '/demande/recupererListBrouillons')
            .success(function (data, status, headers, config) {
                $scope.cockpitData.demandesBrouillons = angular.fromJson(data.response);
                $log.info('Liste de demandes a été crée avec succès!');
            })
            .error(function (data, status, headers, config) {
                $rootScope.error = 'Error fetching ../demande/recupererListBrouillons';
                $log.debug('Error ' + urlPrefix + '/demande/recupererListBrouillons/ \n Status :' + status);
            });

        $scope.viewDemande = function (referenceDeDemande) {
            $scope.indexStep += 1;
            $window.localStorage.setItem('referenceDeDemande', referenceDeDemande);
            $location.path('/Demaut/demande/donneesPerso');
        };

        $scope.deleteDemande = function (referenceDeDemande) {
            if ($scope.cockpitData.doDelete) {
                for (var indexI = 0; indexI < $scope.cockpitData.demandesBrouillons.length; indexI++) {
                    if ($scope.cockpitData.demandesBrouillons[indexI]['referenceDeDemande']['value'] == referenceDeDemande) {
                        $scope.cockpitData.demandesBrouillons.splice(indexI, 1);
                        doDeleteDemande(referenceDeDemande);
                        break;
                    }
                }
            }
        };

        function doDeleteDemande(referenceDeDemande) {
            $http.get(urlPrefix + '/demande/supprimer', {
                params: {
                    referenceDeDemande: referenceDeDemande
                }
            })
                .success(function (data, status, headers, config) {
                    $log.info('Une demande a été supprimée avec succès!');
                })
                .error(function (data, status, headers, config) {
                    $rootScope.error = 'Error ' + urlPrefix + '/demande/supprimer/ \n Status :' + status;
                    $log.debug('Error ' + urlPrefix + '/demande/supprimer/ \n Status :' + status);
                });
        };

        // indispensable pour le rendering initiale du cockpit
        $scope.initilized = true;
    }])
    .controller('ProfessionSanteController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$log', '$window', 'professionTest',
        function ($scope, $rootScope, $routeParams, $http, $location, $log, $window, professionTest) {

            //////////////// Private functions
            $scope.isBrouillonExistant = function () {
                return window.localStorage && $window.localStorage.getItem('referenceDeDemande');
            };

            $scope.isProfessionNecessiteCodeGLN = function () {
                return $scope.professionData.profession != null && $scope.professionData.profession != undefined &&
                    professionTest.isProfessionNecessiteCodeGLN($scope.professionData.profession, $scope.professionsCodeGLN)
            };

            $scope.initListesProfessions = function () {
                $scope.professionData = {};

                //TODO: Extract in a function
                $scope.professions = [];
                $http.get(urlPrefix + '/profession/professionsList')
                    .success(function (data, status, headers, config) {
                        $scope.professions = angular.fromJson(data.response);
                        $log.info('Liste professions a été récupérée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        //$rootScope.error = 'Error fetching ../profession/professionsList';
                        $log.debug('Error ' + urlPrefix + '/profession/professionsList/ \n Status :' + status);
                    });


                //TODO: Extract in a function
                $scope.professionsCodeGLN = [];
                $http.get(urlPrefix + '/profession/professionsCodeGLNObligatoire')
                    .success(function (data, status, headers, config) {
                        $scope.professionsCodeGLN = angular.fromJson(data.response);
                        $log.info('Liste professions exigeant code GLN a été récupérée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../profession/professionsCodeGLNObligatoire';
                        $log.debug('Error ' + urlPrefix + '/profession/professionsCodeGLNObligatoire/ \n Status :' + status);
                    });

                //TODO : faire une directive pour faire une custom validation
                $scope.validateGln = function (inputGln) {
                    if ($scope.professionData.gln.length == 13) {
                        $http.get(urlPrefix + '/demande/validerGln', {
                            params: {
                                codegln: $scope.professionData.gln
                            }
                        })
                            .success(function (data, status, headers, config) {
                                var serviceResponse = data.response;
                                if (serviceResponse.indexOf('OK') != -1 ) {
                                    inputGln.$setValidity('glnValidator',true);
                                }
                                else {
                                    inputGln.$setValidity('glnValidator',false);
                                }

                            })
                            .error(function (data, status, headers, config) {
                                $log.error('Problème lors de la requete du GLN');
                            });
                    }
                }
            }

            //////////////// Controller main flow

            $rootScope.contextMenu = 'DemandeAutorisation';
            $scope.indexStep = 1;
            this.name = "ProfessionSanteController";
            this.params = $routeParams;

            $scope.initListesProfessions();

            if ($scope.isBrouillonExistant()) {
                $http
                    .get(urlPrefix + '/profession/professionDeDemande', {
                        params: {
                            referenceDeDemande: $window.localStorage.getItem('referenceDeDemande')
                        }
                    })
                    .success(function (data, status, headers, config) {
                        var response = angular.fromJson(data.response);
                        for (var indexI = 0; indexI < $scope.professions.length; indexI++) {
                            if ($scope.professions[indexI].id == response[0]) {
                                $scope.professionData.profession = $scope.professions[indexI];
                                break;
                            }
                        }
                        $scope.professionData.gln = response[1] == null || response[1] == undefined ? '' : response[1];
                        if ($scope.professionData.profession != null && $scope.professionData.profession != undefined) {
                            $scope.professionSante.professionDataForm.$pristine = false;
                        }
                        $log.info('Profession de la demande a été récupérée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../profession/professionDeDemande';
                        $log.debug('Error ' + urlPrefix + '/profession/professionDeDemande/ \n Status :' + status);

                    });
            }

            //Etape suivante
            $scope.nextStep = function () {
                $rootScope.wouldStepNext = true;

                if ($scope.professionSante.professionDataForm.$valid && !(professionTest.isProfessionNecessiteCodeGLN($scope.professionData.profession, $scope.professionsCodeGLN) &&
                    ($scope.professionData.gln == null || $scope.professionData.gln == undefined))) {

                    if (!$scope.isBrouillonExistant()) {
                        $http.get(urlPrefix + '/demande/initialiser', {
                            params: {
                                professionId: $scope.professionData.profession.id,
                                codeGln: $scope.professionData.gln != undefined ? $scope.professionData.gln : null,
                            }
                        })
                            .success(function (data, status, headers, config) {
                                var referenceDeDemande = angular.fromJson(data.response);
                                $window.localStorage.setItem('referenceDeDemande', referenceDeDemande.value);
                                $log.info('Une nouvelle demande a été initialisée avec succès!');
                                $location.path('/Demaut/demande/donneesPerso');
                            })
                            .error(function (data, status, headers, config) {
                                $rootScope.error = 'Error fetching ../demande/initialiser/';
                                $log.debug('Error ' + urlPrefix + '/demande/initialiser/ \n Status :' + status);
                            });
                    }
                    else {
                        $http.get(urlPrefix + '/profession/updateCodeGLN', {
                            params: {
                                referenceDeDemande: $window.localStorage.getItem('referenceDeDemande'),
                                codeGln: $scope.professionData.gln
                            }
                        })
                            .success(function (data, status, headers, config) {
                                $log.info('Code GLN de la demande a été updaté avec succès!');
                                $location.path('/Demaut/demande/donneesPerso');
                            })
                            .error(function (data, status, headers, config) {
                                $log.debug('Error ' + urlPrefix + '/profession/updateCodeGLN/ \n Status :' + status);
                            });
                    }
                }
                else {
                    $log.info('Formulaire invalide !');
                }
            };

        }])
    .controller('DonneesPersoController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$interval', '$log', '$window', 'nationalityTest', 'utils',
        function ($scope, $rootScope, $routeParams, $http, $location, $interval, $log, $window, nationalityTest, utils) {
            $rootScope.contextMenu = 'DemandeAutorisation';
            $scope.indexStep = 2;
            this.name = "DonneesPersoController";
            this.params = $routeParams;
            $scope.testSuisse = nationalityTest;//récupération du service de test des nationnalités

            $scope.langues = [];
            $scope.paysList = [];

            $scope.personalData = {};
            $scope.personalData.datePicker = {};
            $scope.personalData.datePicker.status = {};

            if ($scope.paysList.length == 0) {
                $http.get(urlPrefix + '/shared/paysList').
                    success(function (data, status, headers, config) {
                        $scope.paysList = angular.fromJson(data.response);
                        $log.info('Liste pays a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $log.debug('Error ' + urlPrefix + '/shared/paysList/ \n Status :' + status);
                    });
            }
            $http.get(urlPrefix + '/personal/recuperer', {
                params: {
                    referenceDeDemande: $window.localStorage.getItem('referenceDeDemande')
                }
            }).
                success(function (data, status, headers, config) {
                    var responseValues = angular.fromJson(data.response);
                    var donneesPerso = [];
                    angular.forEach(responseValues, function (value, key) {
                        $log.info("Filtrage : " + key + " : " + value);
                        this.push(key + ': ' + value);
                    }, donneesPerso);

                    $scope.personalData.nom = responseValues.nom;
                    $scope.personalData.prenom = responseValues.prenom;
                    $scope.personalData.nomDeCelibataire = responseValues.nomDeCelibataire;

                    if (utils.isNotEmpty(responseValues.adresse)) {
                        $scope.personalData.adressePersonnelle = responseValues.adresse.voie;
                        $scope.personalData.complement = responseValues.adresse.complement;
                        $scope.personalData.localite = responseValues.adresse.localite;
                        $scope.personalData.npa = responseValues.adresse.npa;

                        for (var indexI = 0; indexI < $scope.paysList.length; indexI++) {
                            if ($scope.paysList[indexI].id == responseValues.adresse.pays.id) {
                                $scope.personalData.pays = $scope.paysList[indexI];
                                break;
                            }
                        }
                    }

                    $scope.personalData.telephonePrive = responseValues.telephonePrive;
                    $scope.personalData.telephoneMobile = responseValues.telephoneMobile;
                    $scope.personalData.email = responseValues.email;
                    $scope.personalData.fax = responseValues.fax;
                    $scope.personalData.genre = responseValues.genre;
                    $scope.personalData.dateDeNaissance = new Date(responseValues.dateDeNaissance);
                    $scope.personalData.langue = responseValues.langue;

                    if (responseValues.nationalite != null && responseValues.nationalite != undefined) {
	                    for (var indexJ = 0; indexJ < $scope.paysList.length; indexJ++) {
	                        if ($scope.paysList[indexJ].id == responseValues.nationalite.id) {
	                            $scope.personalData.nationalite = $scope.paysList[indexJ];
	                            break;
	                        }
	                    }
                    }

                    if (utils.isNotEmpty(responseValues.permis)) {
                        $scope.personalData.permis = responseValues.permis.typePermis;
                        if (utils.isNotEmpty(responseValues.permis.autrePermis)) {
                            $scope.personalData.permisOther = responseValues.permis.autrePermis;
                        }
                    }
                    if (utils.isNotEmpty(responseValues)) {
                        $scope.donneesPerso.donneesPersoDataForm.$pristine = false;
                    }
                    $log.debug('Objet Données personnelles a été récupéré avec succès!');
                }).
                error(function (data, status, headers, config) {
                    $log.error('Error ' + urlPrefix + '/personal/recuperer/ \n Status :' + status);
                });

            $scope.personalData.datePicker.status.dateDeNaissance = {
                opened: false
            };

            $scope.isPermisRequired = function () {
                return !$scope.isSuisse();
            };

            $scope.isSuisse = function () {
                return utils.isNotEmpty($scope.personalData.nationalite) && nationalityTest.isSuisse($scope.personalData.nationalite.libl);
            };

            $scope.resetPermis = function () {
                if ($scope.isSuisse()) {
                    $scope.personalData.permis = null;
                }
            };

            $scope.backStep = function () {
                $rootScope.wouldStepBack = true;
                $location.path('/Demaut/demande/professionSante');
            };

            $scope.nextStep = function () {
                $rootScope.wouldStepNext = true;

                if ($scope.donneesPerso.donneesPersoDataForm.$valid) {
                    $log.info('Formulaire valide !');
                    doUpdateDonneesPerso();
                    $location.path('/Demaut/demande/donneesDiplomes');
                }
                else {
                    $log.info('Formulaire invalide !');
                }
            };

            function doUpdateDonneesPerso() {
                //TODO envoyer en POST ou PUT
                $http.get(urlPrefix + '/personal/renseigner', {
                    params: {
                        referenceDeDemande: $window.localStorage.getItem('referenceDeDemande'),
                        nom: $scope.personalData.nom,
                        prenom: $scope.personalData.prenom,
                        nomDeCelibataire: $scope.personalData.nomDeCelibataire,
                        adressePersonnelle: $scope.personalData.adressePersonnelle,
                        localite: $scope.personalData.localite,
                        npa: $scope.personalData.npa,
                        pays: $scope.personalData.pays.id,
                        telephonePrive: $scope.personalData.telephonePrive,
                        telephoneMobile: $scope.personalData.telephoneMobile,
                        email: $scope.personalData.email,
                        fax: $scope.personalData.fax,
                        genre: $scope.personalData.genre,
                        dateDeNaissance: $scope.personalData.dateDeNaissance,
                        nationalite: $scope.personalData.nationalite.id,
                        langue: $scope.personalData.langue,
                        permis: $scope.personalData.permis,
                        permisOther: $scope.personalData.permisOther
                    }
                })
                    .success(function (data, status, headers, config) {
                        $log.info('Une donnée personnelle a été crée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $log.debug('Error ' + urlPrefix + '/personal/renseigner/ \n Status :' + status);
                    });
            };

            $scope.changeTelephoneRequirement = function (telephonePrive, telephoneMobile) {

                if ((telephonePrive.$isEmpty(telephonePrive.$viewValue) && !telephoneMobile.$isEmpty(telephoneMobile.$viewValue)) ||
                    (!telephonePrive.$isEmpty(telephonePrive.$viewValue) && !telephoneMobile.$isEmpty(telephoneMobile.$viewValue)) ||
                    (!telephonePrive.$isEmpty(telephonePrive.$viewValue) && telephoneMobile.$isEmpty(telephoneMobile.$viewValue))) {
                    return false;
                }
                return true;
            };


        }])
    .controller('DonneesDiplomesController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$log', '$window', 'nationalityTest', 'utils',
        function ($scope, $rootScope, $routeParams, $http, $location, $log, $window, nationalityTest, utils) {
            $rootScope.contextMenu = 'DemandeAutorisation';
            $scope.indexStep = 3;
            this.name = "DonneesDiplomes";
            this.params = $routeParams;
            $scope.testSuisse = nationalityTest;

            $scope.diplomeData = {};
            $scope.diplomeData.diplome = {};
            $scope.diplomeData.datePicker = {};
            $scope.diplomeData.datePicker.status = {};
            $scope.diplomeData.diplomes = [];

            $scope.typeDiplomes = [];
            $scope.typeFormationsAll = [];
            $scope.typeFormations = [];
            $scope.paysList = [];


            if ($scope.typeDiplomes.length == 0) {
                $http.get(urlPrefix + '/diplomes/typeDiplomesList').
                    success(function (data, status, headers, config) {
                        $scope.typeDiplomes = angular.fromJson(data.response);
                        $log.info('Liste types diplomes a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $log.warn('Error ' + urlPrefix + '/diplomes/typeDiplomesList/ \n Status :' + status);
                    });
            }

            if ($scope.typeFormationsAll.length == 0) {
                $http.get(urlPrefix + '/diplomes/typeFormationsAll').
                    success(function (data, status, headers, config) {
                        $scope.typeFormationsAll = angular.fromJson(data.response);
                        $log.info('Liste types diplomes a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $log.debug('Error ' + urlPrefix + '/diplomes/typeFormationsAll/ \n Status :' + status);
                    });
            }

            if ($scope.paysList.length == 0) {
                $http.get(urlPrefix + '/shared/paysList').
                    success(function (data, status, headers, config) {
                        $scope.paysList = angular.fromJson(data.response);
                        $log.info('Liste pays a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $log.debug('Error ' + urlPrefix + '/shared/paysList/ \n Status :' + status);
                    });
            }

            $scope.fetchListeFormations = function () {
                $http.get(urlPrefix + '/diplomes/typeFormationsList', {
                    params: {typeDiplome: $scope.diplomeData.diplome.typeDiplome.id}
                }).
                    success(function (data, status, headers, config) {
                        $scope.typeFormations = angular.fromJson(data.response);
                        $log.info('Liste types fromations a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $log.debug('Error ' + urlPrefix + '/diplomes/typeFormationsList/ \n Status :' + status);
                    });
            };

            $http.get(urlPrefix + '/diplomes/diplomesSaisis', {
                params: {
                    referenceDeDemande: $window.localStorage.getItem('referenceDeDemande')
                }
            })
                .success(function (data, status, headers, config) {
                    var fetchedDiplomes = angular.fromJson(data.response);

                    for (var indexOut = 0; indexOut < fetchedDiplomes.length; indexOut++) {
                        if (fetchedDiplomes[indexOut] != null && fetchedDiplomes[indexOut] != undefined) {

                            var currentDiplome = fetchedDiplomes[indexOut];
                            var displayedDiplome = {};

                            displayedDiplome.referenceDeDiplome = currentDiplome.referenceDeDiplome.value;

                            for (var indexJ = 0; indexJ < $scope.typeDiplomes.length; indexJ++) {
                                if ($scope.typeDiplomes[indexJ].id == currentDiplome.typeDiplomeAccepte.id) {
                                    displayedDiplome.typeDiplome = $scope.typeDiplomes[indexJ];
                                    break;
                                }
                            }

                            for (var indexZ = 0; indexZ < $scope.typeFormationsAll.length; indexZ++) {
                                if ($scope.typeFormationsAll[indexZ].name == currentDiplome.titreFormation.value) {
                                    displayedDiplome.typeFormation = $scope.typeFormationsAll[indexZ];
                                    break;
                                }
                            }
                            displayedDiplome.complement = currentDiplome.complement;
                            displayedDiplome.dateObtention = new Date(currentDiplome.dateObtention.value);

                            for (var indexK = 0; indexK < $scope.paysList.length; indexK++) {
                                if ($scope.paysList[indexK].id == currentDiplome.paysObtention.id) {
                                    displayedDiplome.paysObtention = $scope.paysList[indexK];
                                    break;
                                }
                            }
                            displayedDiplome.dateReconnaissance = currentDiplome.dateReconnaissance == null || currentDiplome.dateReconnaissance == undefined || currentDiplome.dateReconnaissance.value == null ? '' : new Date(currentDiplome.dateReconnaissance.value);
                            $scope.diplomeData.diplomes.push(displayedDiplome);
                        }
                    }
                    if ($scope.diplomeData.diplomes.length > 0) {
                        $scope.donneesDiplome.diplomeDataForm.$pristine = false;
                    }
                    $log.info('liste de diplomes saisis a été récupérée avec succès!');
                })
                .error(function (data, status, headers, config) {
                    $log.debug('Error ' + urlPrefix + '/diplomes/diplomesSaisis/ \n Status :' + status);
                });

            $scope.diplomeData.datePicker.status.dateObtention = {
                opened: false
            };
            $scope.diplomeData.datePicker.status.dateReconnaissance = {
                opened: false
            };

            $scope.isDateReconnaissanceRequired = function () {
                return utils.isNotEmpty($scope.diplomeData.diplome.paysObtention) && !nationalityTest.isSuisse($scope.diplomeData.diplome.paysObtention.libl);
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

            $scope.backStep = function () {
                $rootScope.wouldStepBack = true;
                $location.path('/Demaut/demande/donneesPerso');
            };

            $scope.nextStep = function () {
                $rootScope.wouldStepNext = true;
                $log.debug('Formulaire pristine ' + $scope.donneesDiplome.diplomeDataForm.$pristine);
                $log.debug('Formulaire untouched ' + $scope.donneesDiplome.diplomeDataForm.$untouched);

                $location.path('/Demaut/demande/donneesActivites');
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
                    diplome.dateReconnaissance = diplome.dateReconnaissance == null || diplome.dateReconnaissance == undefined || diplome.dateReconnaissance == '' ? '' : new Date(diplome.dateReconnaissance);

                    // TODO attendre la reponse server avant de faire le push
                    $scope.diplomeData.diplomes.push(diplome);
                    doCreateDiplome(diplome);
                    $scope.diplomeData.diplome = {};
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
                        referenceDeDemande: $window.localStorage.getItem('referenceDeDemande'),
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
                        $log.debug('Error ' + urlPrefix + '/diplomes/ajouter/ \n Status :' + status);
                    });
            };

            function doDeleteDiplome(targetDiplome) {
                $http.get(urlPrefix + '/diplomes/supprimer', {
                    params: {
                        referenceDeDemande: $window.localStorage.getItem('referenceDeDemande'),
                        referenceDeDiplome: targetDiplome.referenceDeDiplome
                    }
                })
                    .success(function (data, status, headers, config) {
                        $log.info('Un diplôme a été supprimé avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $log.debug('Error ' + urlPrefix + '/diplomes/supprimer/ \n Status :' + status);
                    });
            };
        }])
    //------------------- DonneesActivitesController ----------------------------------
    .controller('DonneesActivitesController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$log', 'utils',
        function ($scope, $rootScope, $routeParams, $http, $location, $log, utils) {
            $rootScope.contextMenu = 'DemandeAutorisation';
            $scope.indexStep = 4;
            this.name = "DonneesActivitesController";
            this.params = $routeParams;
            $scope.activiteData = {};
            $scope.activiteData.activities = [];
            $scope.activiteData.activitie = {};
            $scope.activiteData.datePicker = {};
            $scope.activiteData.datePicker.status = {};
            $scope.activeSaisiActivite = false;

            $scope.activiteData.datePicker.status.dateDebutIndependant = {
                opened: false
            };

            $scope.activiteData.datePicker.status.dateDebutDependant = {
                opened: false
            };

            $scope.backStep = function () {
                $rootScope.wouldStepBack = true;
                $location.path('/Demaut/demande/DonneesDiplomes');
            };

            $scope.nextStep = function () {
                $rootScope.wouldStepNext = true;
                $log.info('Formulaire valide !');
                $location.path('/Demaut/demande/annexes');
            };

            $scope.afficheFormulaire = function (active){
                $scope.activeSaisiActivite = active;
            };

            $scope.addAnotherActivite = function () {
                $scope.wouldAddActivite = true;

                if ($scope.donneesActivite.donneesActiviteForm.$valid) {
                    $log.info('Formulaire valide !');
                    var activiteFutureFK = $scope.activiteData.activiteFutureFK;
                    var activite = angular.copy($scope.activiteData.activite);
                    $scope.activiteData.activities.push(activite);
                    doCreateActivite($scope.activiteData.activite);
                    $scope.activiteData.activitie = {};
                    $scope.donneesActivite.donneesActiviteForm.$valid = true;
                    $scope.donneesActivite.donneesActiviteForm.$error = null;
                    $scope.donneesActivite.donneesActiviteForm.$setPristine();
                }
                else {
                    $log.info('Formulaire activité future invalide !');
                }

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
                $http.post(urlPrefix + '/activitesFutures/ajouter', {
                    params: {
                        nomEtablissement:targetActivite.etablissement.nomEtablissement,
                        voie:targetActivite.etablissement.voie,
                        npaProfessionnel: targetActivite.etablissement.npaProfessionnel,
                        localite: targetActivite.etablissement.localite,
                        telephoneProf: targetActivite.etablissement.telephoneProf,
                        telephoneMobile: targetActivite.etablissement.telephoneMobile,
                        fax: targetActivite.etablissement.fax,
                        email: targetActivite.etablissement.email,
                        siteInternet: targetActivite.etablissement.siteInternet,
                        typePratiqueLamal:targetActivite.typePratiqueLamal,
                        typeActivite:targetActivite.activiteEnvisagee.typeActivite,
                        nombreJourParSemaine:targetActivite.activiteEnvisagee.nombreJourParSemaine,
                        datePrevueDebut:targetActivite.activiteEnvisagee.datePrevueDebut,
                        superviseur:targetActivite.activiteEnvisagee.superviseur
                    }

                })
                    .success(function (data, status, headers, config) {
                        $log.info('Une activité a été crée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $log.debug('Error ' + urlPrefix + '/activites/ajouter/ \n Status :' + status);
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
                        $log.debug('Error ' + urlPrefix + '/activites/supprimer/ \n Status :' + status);
                    });
            };
        }])
    .controller('AnnexesController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$log', '$q', '$timeout', '$window',
        function ($scope, $rootScope, $routeParams, $http, $location, $log, $q, $timeout, $window) {
            $rootScope.contextMenu = 'DemandeAutorisation';
            $scope.indexStep = 5;
            this.name = "AnnexesController";
            this.params = $routeParams;
            $scope.annexesData = {};
            $scope.annexesData.annexeFormats = ["application/pdf", "image/jpg", "image/jpeg", "image/png"];
            $scope.annexesData.annexeTypesObligatoires = [];
            $scope.annexesData.referenceFiles = [];

            if ($scope.annexesData.annexeTypesObligatoires.length == 0) {
                $http.get(urlPrefix + '/annexes/typesObligatoiresList', {
                    params: {
                        referenceDeDemande: $window.localStorage.getItem('referenceDeDemande')
                    }
                }).
                    success(function (data, status, headers, config) {
                        $scope.annexesData.annexeTypesObligatoires = angular.fromJson(data.response);
                        $log.info('Liste types annexes obligatoires a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $log.debug('Error ' + urlPrefix + '/annexes/typesObligatoiresList/ \n Status :' + status);
                    });
            }

            $http.get(urlPrefix + '/annexes/annexesSaisis', {
                params: {
                    referenceDeDemande: $window.localStorage.getItem('referenceDeDemande')
                }
            })
                .success(function (data, status, headers, config) {
                    var fetchedAnnexes = angular.fromJson(data.response);

                    for (var indexOut = 0; indexOut < fetchedAnnexes.length; indexOut++) {
                        if (fetchedAnnexes[indexOut] != null && fetchedAnnexes[indexOut] != undefined) {

                            var currentAnnexe = fetchedAnnexes[indexOut];
                            var displayedAnnexe = {};
                            displayedAnnexe.name = currentAnnexe.nomFichier.nomFichier;
                            $scope.annexesData.referenceFiles.push(displayedAnnexe);
                        }
                    }
                    $scope.files = $scope.annexesData.referenceFiles;
                    if ($scope.files.length > 0) {
                        $scope.annexes.annexesDataForm.$pristine = false;
                    }
                    $log.info('liste de annexes saisis a été récupérée avec succès!');
                })
                .error(function (data, status, headers, config) {
                    $log.debug('Error ' + urlPrefix + '/annexes/annexesSaisis/ \n Status :' + status);
                });

            $scope.backStep = function () {
                $rootScope.wouldStepBack = true;
                $location.path('/Demaut/demande/donneesActivites');
            };

            $scope.nextStep = function () {
                $rootScope.wouldStepNext = true;
                if ($scope.annexes.annexesDataForm.$valid && $scope.annexesData.referenceFiles.length > 0) {
                    $log.info('Formulaire valide !');
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
                        $log.info('Document(s) valide(s) !');
                    }
                } else {
                    $scope.files = $scope.annexesData.referenceFiles;
                    $rootScope.error = 'une/plusieurs pièces ne respectent pas les règles de nommage ou ne correspondent pas aux formats supportés (pdf, image)';
                    $log.info($rootScope.error);
                }
            };

            $scope.viewAnnexe = function (file) {
                $scope.filename = file.name;
                $http.get(urlPrefix + '/annexes/afficher/', {
                    responseType: 'arraybuffer',
                    params: {
                        referenceDeDemande: $window.localStorage.getItem('referenceDeDemande'),
                        annexeFileName: file.name
                    }
                })
                    .success(function (data, status, headers, config) {
                        displayAnnexeFromBinary(data);
                        $log.info('Une annexes a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $log.debug('Error ' + urlPrefix + '/annexes/afficher/ \n Status :' + status);
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
                            doDeleteFile(currentFetchedFile);
                            break;
                        }
                    }
                }
                $$scope.files = $scope.annexesData.referenceFiles;
            };

            function doDeleteFile(file) {
                $http.get(urlPrefix + '/annexes/supprimer', {
                    params: {
                        referenceDeDemande: $window.localStorage.getItem('referenceDeDemande'),
                        annexeFileName: file.name
                    }
                })
                    .success(function (data, status, headers, config) {
                        $log.info('Une annexe a été supprimée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $log.debug('Error ' + urlPrefix + '/annexes/supprimer/ \n Status :' + status);
                    });
            };

            function doUploadFile(currentFetchedFile) {
                var formData = new FormData();
                formData.append('ajaxAction', 'upload');
                formData.append("referenceDeDemande", $window.localStorage.getItem('referenceDeDemande'));
                formData.append("annexeFile", currentFetchedFile);
                formData.append("annexeFileName", currentFetchedFile.name);
                formData.append("annexeFileSize", currentFetchedFile.size);
                formData.append("annexeFileType", currentFetchedFile.type);
                var deferred = $q.defer();
                $http.post(urlPrefix + '/annexes/attacher', formData, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': 'multipart/form-data'},
                    timeout: deferred.promise
                })
                    .success(function (data, status, headers, config) {
                        $log.info('Une annexe a été uplodée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $log.debug('Error ' + urlPrefix + '/annexes/attacher/ \n Status :' + status);
                    });
                $timeout(function () {
                    deferred.resolve();
                }, 10000);
            };
        }])
    .controller('RecapitulatifController', ['$scope', '$rootScope', '$routeParams', '$location', '$log',
        function ($scope, $rootScope, $routeParams, $location, $log) {
            $rootScope.contextMenu = 'DemandeAutorisation';
            $scope.indexStep = 6;
            this.name = "RecapitulatifController";
            this.params = $routeParams;

            $scope.backStep = function () {
                $rootScope.wouldStepBack = true;
                $location.path('/Demaut/demande/annexes');
            };

            $scope.nextStep = function () {
                $rootScope.wouldStepNext = true;
                if ($scope.recapitulatif.recapitulatifDataForm.$valid) {
                    $log.info('Formulaire valide !');
                    $location.path('/Demaut/demande/soumission');
                }
                else {
                    $log.info('Formulaire invalide !');
                }
            };
        }]);

ngDemautApp
    .run(function ($rootScope, $sce, $location, $http, globalMessagesService) {
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
            var isContextDemande = $rootScope.contextMenu != undefined && $rootScope.contextMenu == 'DemandeAutorisation';
            var notStepNext = $rootScope.wouldStepNext == null || $rootScope.wouldStepNext == undefined || $rootScope.wouldStepNext != true;
            var notStepBack = $rootScope.wouldStepBack == null || $rootScope.wouldStepBack == undefined || $rootScope.wouldStepBack != true;
            if (isContextDemande && notStepNext && notStepBack) {
                $location.path('/Demaut/cockpit');
            }
        });

        $rootScope.$on('$routeChangeSuccess', function () {
            $rootScope.wouldStepNext = false;
            $rootScope.wouldStepBack = false;
            globalMessagesService.clearMessages();
        });
    });