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
                    $log.info('Error ' + urlPrefix + '/demande/recupererListBrouillons/ \n Status :' + status);
                });

            $scope.viewDemande = function (referenceDeDemande) {
                $scope.indexStep += 1;
                $window.localStorage.setItem('referenceDeDemande', referenceDeDemande);
                $location.path('/Demaut/demande/professionSante');
            };

            $scope.deleteDemande = function (referenceDeDemande) {
                if($scope.cockpitData.doDelete) {
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
                        $log.info('Error ' + urlPrefix + '/demande/supprimer/ \n Status :' + status);
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
	
	        $scope.isProfessionNecessiteCodeGLN = function() {
	            return $scope.professionData.profession != null && $scope.professionData.profession != undefined &&
	                professionTest.isProfessionNecessiteCodeGLN($scope.professionData.profession, $scope.professionData.professionsCodeGLN)
	        };
        
	        $scope.initListesProfessions = function() {
	            $scope.professionData = {};

	            //TODO: Extract in a function
	            $scope.professionData.professions = [];
	            $http.get(urlPrefix + '/profession/professionsList')
	            .success(function (data, status, headers, config) {
	                $scope.professionData.professions = angular.fromJson(data.response);
	                $log.info('Liste professions a été récupérée avec succès!');
	            })
	            .error(function (data, status, headers, config) {
	                $rootScope.error = 'Error fetching ../profession/professionsList';
	                $log.info('Error ' + urlPrefix + '/profession/professionsList/ \n Status :' + status);
	            });            	

	            
	            //TODO: Extract in a function
	        	$scope.professionData.professionsCodeGLN = [];
	            $http.get(urlPrefix + '/profession/professionsCodeGLNObligatoire')
	            .success(function (data, status, headers, config) {
	                $scope.professionData.professionsCodeGLN = angular.fromJson(data.response);
	                $log.info('Liste professions exigeant code GLN a été récupérée avec succès!');
	            })
	            .error(function (data, status, headers, config) {
	                $rootScope.error = 'Error fetching ../profession/professionsCodeGLNObligatoire';
	                $log.info('Error ' + urlPrefix + '/profession/professionsCodeGLNObligatoire/ \n Status :' + status);
	            });
	        }
    	
    		//////////////// Controller main flow

	        $rootScope.contextMenu = 'DemandeAutorisation';
            $scope.indexStep = 1;
            this.name = "ProfessionSanteController";
            this.params = $routeParams;
            
            $scope.initListesProfessions();
            
            if($scope.isBrouillonExistant()) {
                $http
                    .get(urlPrefix + '/profession/professionDeDemande', {
	                    params: {
	                        referenceDeDemande: $window.localStorage.getItem('referenceDeDemande')
	                    }
                    })
                    .success(function (data, status, headers, config) {
                        var response = angular.fromJson(data.response);
                        for (var indexI = 0; indexI < $scope.professionData.professions.length; indexI++) {
                            if ($scope.professionData.professions[indexI].id == response[0]) {
                                $scope.professionData.profession = $scope.professionData.professions[indexI];
                                break;
                            }
                        }
                        $scope.professionData.gln = response[1] == null || response[1] == undefined ? '' : response[1];
                        if($scope.professionData.profession != null && $scope.professionData.profession != undefined) {
                            $scope.professionSante.professionDataForm.$pristine = false;
                        }
                        $log.info('Profession de la demande a été récupérée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../profession/professionDeDemande';
                        $log.info('Error ' + urlPrefix + '/profession/professionDeDemande/ \n Status :' + status);

                    });
            }

            //Etape suivante
            $scope.nextStep = function () {
                $rootScope.wouldStepNext = true;
                
                if ($scope.professionSante.professionDataForm.$valid &&
                    !(professionTest.isProfessionNecessiteCodeGLN($scope.professionData.profession, $scope.professionData.professionsCodeGLN) &&
                    ($scope.professionData.gln == null || $scope.professionData.gln == undefined))) {

                    if(!$scope.isBrouillonExistant()) {
                        $http.get(urlPrefix + '/demande/initialiser', {
                            params: {
                                professionId: $scope.professionData.profession.id,
                                codeGln: $scope.professionData.gln != undefined ? $scope.professionData.gln : null,
                            }
                        })
                            .success(function (data, status, headers, config) {
                                var referenceDeDemande = angular.fromJson(data.response);
                                $window.localStorage.setItem('referenceDeDemande', referenceDeDemande.value);
                                $log.info('Une nouvelle demande a été intitialisée avec succès!');
                                $location.path('/Demaut/demande/donneesPerso');
                            })
                            .error(function (data, status, headers, config) {
                                $rootScope.error = 'Error fetching ../demande/initialiser/';
                                $log.info('Error ' + urlPrefix + '/demande/initialiser/ \n Status :' + status);
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
                                $rootScope.error = 'Error fetching ../profession/updateCodeGLN';
                                $log.info('Error ' + urlPrefix + '/profession/updateCodeGLN/ \n Status :' + status);
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

            $scope.nationalites = [];
            $scope.langues = [];
            $scope.paysList = [];

            $scope.personalData = {};
            $scope.personalData.datePicker = {};
            $scope.personalData.datePicker.status = {};

            if ($scope.nationalites.length == 0) {
                $http.get(urlPrefix + '/shared/nationalites').
                    success(function (data, status, headers, config) {
                        $scope.nationalites = angular.fromJson(data.response);
                        $log.info('Liste nationalites a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../shared/nationalites';
                        $log.info('Error ' + urlPrefix + '/shared/nationalites/ \n Status :' + status);
                    });
            }

            if ($scope.paysList.length == 0) {
                $http.get(urlPrefix + '/shared/paysList').
                    success(function (data, status, headers, config) {
                        $scope.paysList = angular.fromJson(data.response);
                        $log.info('Liste pays a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../shared/paysList';
                        $log.info('Error ' + urlPrefix + '/shared/paysList/ \n Status :' + status);
                    });
            }
            //TODO faire un service ou un provider pour la gestion de l'état de la demande
            $scope.isBrouillonExistant = function () {
                return window.localStorage && $window.localStorage.getItem('referenceDeDemande');
            };

            if($scope.isBrouillonExistant()) {
                $http.get(urlPrefix + '/personal/recuperer', {
                    params: {
                        referenceDeDemande: $window.localStorage.getItem('referenceDeDemande')
                    }
                }).
                    success(function (data, status, headers, config) {
                        var responseValues = angular.fromJson(data.response);
                        var donneesPerso = [];
                        angular.forEach(responseValues, function(value, key) {
                            $log.info("Filtrage : " + key + " : " + value);
                            this.push(key + ': ' + value);
                        }, donneesPerso);

                        $scope.personalData.nom = responseValues.nom.value;
                        $scope.personalData.prenom = responseValues.prenom.value;
                        $scope.personalData.nomDeCelibataire = responseValues.nomDeCelibataire.value;

                        if(utils.isNotEmpty(responseValues.adresse)) {
                            $scope.personalData.adressePersonnelle = responseValues.adresse.voie;
                            $scope.personalData.complement = responseValues.adresse.complement;
                            $scope.personalData.localite = responseValues.adresse.localite.value;
                            $scope.personalData.npa = responseValues.adresse.npa.value;

                            for (var indexI = 0; indexI < $scope.paysList.length; indexI++) {
                                if ($scope.paysList[indexI].name == responseValues.adresse.pays) {
                                    $scope.personalData.pays = $scope.paysList[indexI];
                                    break;
                                }
                            }
                        }

                        $scope.personalData.telephonePrive = responseValues.telephonePrive.value;
                        $scope.personalData.telephoneMobile = responseValues.telephoneMobile.value;
                        $scope.personalData.email = responseValues.email.value;
                        $scope.personalData.fax = responseValues.fax.value;
                        $scope.personalData.genre = responseValues.genre;
                        $scope.personalData.dateDeNaissance = new Date(responseValues.dateDeNaissance.value);
                        $scope.personalData.langue = responseValues.langue;

                        for (var indexJ = 0; indexJ < $scope.nationalites.length; indexJ++) {
                            if ($scope.nationalites[indexJ].name == responseValues.nationalite) {
                                $scope.personalData.nationalite = $scope.nationalites[indexJ];
                                break;
                            }
                        }

                        if(utils.isNotEmpty(responseValues.permis)) {
                            $scope.personalData.permis = responseValues.permis.typePermis;
                            if(utils.isNotEmpty(responseValues.permis.autrePermis)) {
                                $scope.personalData.permisOther = responseValues.permis.autrePermis.value;
                            }
                        }
                        if(utils.isNotEmpty(responseValues)) {
                            $scope.donneesPerso.donneesPersoDataForm.$pristine = false;
                        }
                        $log.debug('Objet Données personnelles a été récupéré avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../personal/recuperer';
                        $log.error('Error ' + urlPrefix + '/personal/recuperer/ \n Status :' + status);
                    });
            }

            $scope.personalData.datePicker.status.dateDeNaissance = {
                opened: false
            };

            $scope.isPermisRequired = function () {
                return !$scope.isSuisse();
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
                        $rootScope.error = 'Error ' + urlPrefix + '/personal/renseigner/ \n Status :' + status;
                        $log.info('Error ' + urlPrefix + '/personal/renseigner/ \n Status :' + status);
                    });
            };

            $scope.changeTelephoneRequirement = function(telephonePrive, telephoneMobile) {

                if ((telephonePrive.$isEmpty(telephonePrive.$viewValue) && !telephoneMobile.$isEmpty(telephoneMobile.$viewValue)) ||
                    (!telephonePrive.$isEmpty(telephonePrive.$viewValue) && !telephoneMobile.$isEmpty(telephoneMobile.$viewValue)) ||
                    (!telephonePrive.$isEmpty(telephonePrive.$viewValue) && telephoneMobile.$isEmpty(telephoneMobile.$viewValue))){
                    return false;
                }
                return true;
            };


        }])
    .controller('DonneesDiplomesController', ['$scope', '$rootScope', '$routeParams', '$http', '$location', '$log', '$window', 'nationalityTest',
        function ($scope, $rootScope, $routeParams, $http, $location, $log, $window, nationalityTest) {
            $rootScope.contextMenu = 'DemandeAutorisation';
            $scope.indexStep = 3;
            this.name = "DonneesDiplomes";
            this.params = $routeParams;
            $scope.testSuisse = nationalityTest;
            $scope.diplomeData = {};
            $scope.diplomeData.typeDiplomes = [];
            $scope.diplomeData.typeFormationsAll = [];
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
                        $log.info('Liste types diplomes a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../diplomes/typeDiplomesList';
                        $log.info('Error ' + urlPrefix + '/diplomes/typeDiplomesList/ \n Status :' + status);
                    });
            }

            if ($scope.diplomeData.typeFormationsAll.length == 0) {
                $http.get(urlPrefix + '/diplomes/typeFormationsAll').
                    success(function (data, status, headers, config) {
                        $scope.diplomeData.typeFormationsAll = angular.fromJson(data.response);
                        $log.info('Liste types diplomes a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../diplomes/typeFormationsAll';
                        $log.info('Error ' + urlPrefix + '/diplomes/typeFormationsAll/ \n Status :' + status);
                    });
            }

            if ($scope.diplomeData.paysList.length == 0) {
                $http.get(urlPrefix + '/shared/paysList').
                    success(function (data, status, headers, config) {
                        $scope.diplomeData.paysList = angular.fromJson(data.response);
                        $log.info('Liste pays a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../shared/paysList';
                        $log.info('Error ' + urlPrefix + '/shared/paysList/ \n Status :' + status);
                    });
            }

            $scope.isBrouillonExistant = function () {
                return window.localStorage && $window.localStorage.getItem('referenceDeDemande');
            };

            $scope.fetchListeFormations = function () {
                $http.get(urlPrefix + '/diplomes/typeFormationsList', {
                    params: {typeDiplome: $scope.diplomeData.diplome.typeDiplome.id}
                }).
                    success(function (data, status, headers, config) {
                        $scope.diplomeData.typeFormations = angular.fromJson(data.response);
                        $log.info('Liste types fromations a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../diplomes/typeFormationsList/';
                        $log.info('Error ' + urlPrefix + '/diplomes/typeFormationsList/ \n Status :' + status);
                    });
            };

            if($scope.isBrouillonExistant()) {
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

                                for (var indexJ = 0; indexJ < $scope.diplomeData.typeDiplomes.length; indexJ++) {
                                    if ($scope.diplomeData.typeDiplomes[indexJ].name == currentDiplome.typeDiplomeAccepte) {
                                        displayedDiplome.typeDiplome = $scope.diplomeData.typeDiplomes[indexJ];
                                        break;
                                    }
                                }

                                for (var indexZ = 0; indexZ < $scope.diplomeData.typeFormationsAll.length; indexZ++) {
                                    if ($scope.diplomeData.typeFormationsAll[indexZ].name == currentDiplome.titreFormation.value) {
                                        displayedDiplome.typeFormation = $scope.diplomeData.typeFormationsAll[indexZ];
                                        break;
                                    }
                                }
                                displayedDiplome.complement = currentDiplome.complement;
                                displayedDiplome.dateObtention = new Date(currentDiplome.dateObtention.value);

                                for (var indexK = 0; indexK < $scope.diplomeData.paysList.length; indexK++) {
                                    if ($scope.diplomeData.paysList[indexK].name == currentDiplome.paysObtention.value) {
                                        displayedDiplome.paysObtention = $scope.diplomeData.paysList[indexK];
                                        break;
                                    }
                                }
                                displayedDiplome.dateReconnaissance = currentDiplome.dateReconnaissance == null || currentDiplome.dateReconnaissance == undefined || currentDiplome.dateReconnaissance.value == null ? '' : new Date(currentDiplome.dateReconnaissance.value);
                                $scope.diplomeData.diplomes.push(displayedDiplome);
                            }
                        }
                        if($scope.diplomeData.diplomes.length > 0) {
                            $scope.donneesDiplome.diplomeDataForm.$pristine = false;
                        }
                        $log.info('liste de diplomes saisis a été récupérée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../diplomes/diplomesSaisis';
                        $log.info('Error ' + urlPrefix + '/diplomes/diplomesSaisis/ \n Status :' + status);
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

            $scope.backStep = function () {
                $rootScope.wouldStepBack = true;
                $location.path('/Demaut/demande/donneesPerso');
            };

            $scope.nextStep = function () {
                $rootScope.wouldStepNext = true;
                if ($scope.donneesDiplome.diplomeDataForm.$valid) {
                    $log.info('Formulaire valide !');
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
                    diplome.dateReconnaissance = diplome.dateReconnaissance == null || diplome.dateReconnaissance == undefined || diplome.dateReconnaissance == '' ? '' : new Date(diplome.dateReconnaissance);

                    // TODO attendre la reponse server avant de faire le push
                    $scope.diplomeData.diplomes.push(diplome);
                    doCreateDiplome(diplome);
                    $scope.diplomeData.diplome = {};
                    $scope.donneesDiplome.diplomeDataForm.$valid = true;
                    $scope.donneesDiplome.diplomeDataForm.$error = {};
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
                        $rootScope.error = 'Error ' + urlPrefix + '/diplomes/ajouter/ \n Status :' + status;
                        $log.info('Error ' + urlPrefix + '/diplomes/ajouter/ \n Status :' + status);
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
                        $rootScope.error = 'Error ' + urlPrefix + '/diplomes/supprimer/ \n Status :' + status;
                        $log.info('Error ' + urlPrefix + '/diplomes/supprimer/ \n Status :' + status);
                    });
            };
        }])
    .controller('DonneesActivitesController', ['$scope', '$rootScope', '$routeParams', '$location', '$log',
        function ($scope, $rootScope, $routeParams, $location, $log) {
            $rootScope.contextMenu = 'DemandeAutorisation';
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
                $rootScope.wouldStepBack = true;
                $location.path('/Demaut/demande/DonneesDiplomes');
            };

            $scope.nextStep = function () {
                $rootScope.wouldStepNext = true;
                if ($scope.donneesActivite.donneesActiviteForm.$valid) {
                    $log.info('Formulaire valide !');
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
                        $log.info('Error ' + urlPrefix + '/activites/ajouter/ \n Status :' + status);
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
                        $log.info('Error ' + urlPrefix + '/activites/supprimer/ \n Status :' + status);
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
            $scope.annexesData.annexeTypes = [];
            $scope.annexesData.annexeTypesObligatoires = [];
            $scope.annexesData.referenceFiles = [];
            $scope.annexesData.annexeTypeSelected = {};

            if ($scope.annexesData.annexeTypes.length == 0) {
                $http.get(urlPrefix + '/annexes/typesList').
                    success(function (data, status, headers, config) {
                        $scope.annexesData.annexeTypes = angular.fromJson(data.response);
                        $log.info('Liste types annexes a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../annexes/typesList';
                        $log.info('Error ' + urlPrefix + '/annexes/typesList/ \n Status :' + status);
                    });
            }

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
                        $rootScope.error = 'Error fetching ../annexes/typesObligatoiresList';
                        $log.info('Error ' + urlPrefix + '/annexes/typesObligatoiresList/ \n Status :' + status);
                    });
            }

            $scope.isBrouillonExistant = function () {
                return window.localStorage && $window.localStorage.getItem('referenceDeDemande');
            };

            if($scope.isBrouillonExistant()) {
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

                                for (var indexJ = 0; indexJ < $scope.annexesData.annexeTypes.length; indexJ++) {
                                    if ($scope.annexesData.annexeTypes[indexJ].name == currentAnnexe.typeAnnexe) {
                                        displayedAnnexe.annexeType = $scope.annexesData.annexeTypes[indexJ];
                                        break;
                                    }
                                }

                                displayedAnnexe.name = currentAnnexe.nomFichier.nomFichier;
                                $scope.annexesData.referenceFiles.push(displayedAnnexe);
                            }
                        }
                        $scope.files = $scope.annexesData.referenceFiles;
                        if($scope.files.length > 0) {
                            $scope.annexes.annexesDataForm.$pristine = false;
                        }
                        $log.info('liste de annexes saisis a été récupérée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error fetching ../annexes/annexesSaisis';
                        $log.info('Error ' + urlPrefix + '/annexes/annexesSaisis/ \n Status :' + status);
                    });
            }

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
                        referenceDeDemande: $window.localStorage.getItem('referenceDeDemande'),
                        annexeFileName: file.name,
                        annexeType: file.typeAnnexe
                    }
                })
                    .success(function (data, status, headers, config) {
                        displayAnnexeFromBinary(data);
                        $log.info('Une annexes a été récupérée avec succès!');
                    }).
                    error(function (data, status, headers, config) {
                        $rootScope.error = 'Error ' + urlPrefix + '/annexes/afficher/ \n Status :' + status;
                        $log.info('Error ' + urlPrefix + '/annexes/afficher/ \n Status :' + status);
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
                        referenceDeDemande: $window.localStorage.getItem('referenceDeDemande'),
                        annexeFileName: file.name,
                        annexeType: file.typeAnnexe
                    }
                })
                    .success(function (data, status, headers, config) {
                        $log.info('Une annexe a été supprimée avec succès!');
                    })
                    .error(function (data, status, headers, config) {
                        $rootScope.error = 'Error ' + urlPrefix + '/annexes/supprimer/ \n Status :' + status;
                        $log.info('Error ' + urlPrefix + '/annexes/supprimer/ \n Status :' + status);
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
                formData.append("annexeType", currentFetchedFile.typeAnnexe);
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
                        $rootScope.error = 'Error Upload: [' + urlPrefix + '/annexes/attacher' + status;
                        $log.info('Error ' + urlPrefix + '/annexes/attacher/ \n Status :' + status);
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
            var isContextDemande = $rootScope.contextMenu != undefined && $rootScope.contextMenu == 'DemandeAutorisation';
            var notStepNext = $rootScope.wouldStepNext == null || $rootScope.wouldStepNext == undefined || $rootScope.wouldStepNext != true;
            var notStepBack = $rootScope.wouldStepBack == null || $rootScope.wouldStepBack == undefined || $rootScope.wouldStepBack != true;
            if(isContextDemande && notStepNext && notStepBack) {
                $location.path('/Demaut/cockpit');
            }
        });

        $rootScope.$on('$routeChangeSuccess', function () {
            $rootScope.wouldStepNext = false;
            $rootScope.wouldStepBack = false;
        });
    });