var module = angular.module('demautApp', ['ngSanitize', 'ngRoute', 'ngCookies', 'demautApp.services', 'LocalStorageModule', 'ngIdle']);
module
	.config([ '$routeProvider', '$locationProvider', '$httpProvider', 'KeepaliveProvider', 'IdleProvider', function($routeProvider, $locationProvider, $httpProvider, KeepaliveProvider, IdleProvider) {


			$routeProvider.when('/index', {
				templateUrl: 'index.html',
				controller: IndexController
			});
			
			$routeProvider.otherwise({
				templateUrl: 'index.html',
				controller: IndexController
			});
			
			$locationProvider.hashPrefix('!');
			
			/* Register error provider that shows message on failed requests or redirects to login page on
			 * unauthenticated requests */
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
                                angular.element("#confirmationModal").modal('toggle');
                                angular.element("#confirmationModal").modal('show');
			        		}
			        		return $q.reject(rejection);
			        	}
			        };
			    });
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;
		        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
		        			var authToken = $rootScope.authToken;
							config.headers['X-Auth-Token'] = authToken;
		        		}
		        		return config || $q.when(config);
		        	}
		        };
		    });

            IdleProvider.idle(900);
            IdleProvider.timeout(900);
            KeepaliveProvider.interval(10);
		   
		} ])
    .run(function($rootScope, $sce, $location, $cookieStore, TranslationService, localStorageService, Idle) {

        /* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});

		$rootScope.$on('$routeChangeStart', function () {
            /* Try getting valid user from localStorage or go to login page */
            if(localStorageService.isSupported) {
                var originalPath = $location.path();
                var authToken = localStorageService.get('authToken');
                if (authToken != null && authToken != undefined) {
                    $rootScope.authToken = authToken;
					$location.path(originalPath);
                } else {
                    $location.path("/index");
                }
            }
			else{
                /* Try getting valid user from cookie or go to login page */
                var originalPath = $location.path();
                var authToken = $cookieStore.get('authToken');
                if (authToken !== null && authToken != undefined) {
                    $rootScope.authToken = authToken;
					$location.path(originalPath);
                } else {
                    $location.path("/index");
                }
            }
		});

		$rootScope.getHtml = function(html){
			return $sce.trustAsHtml(html);
		}

        // indispensable pour le rendering initial
        $rootScope.initialized = true;

        Idle.watch();

        $rootScope.$on('IdleStart', function() {
            alert('Session : IdleStart');
        });

        $rootScope.$on('IdleTimeout', function() {
            alert('Session : IdleTimeout');
            delete $rootScope.authToken;
            $cookieStore.remove('authToken');
            $location.path("/index");
            $rootScope.error = "demaut.timeout.text";
            $rootScope.success = false;
            angular.element("#confirmationModal").modal('toggle');
            angular.element("#confirmationModal").modal('show');
        });
	});

function IndexController($scope, $sce, AnnexesService) {
	$scope.files = [];
	$scope.onClickTest1 = function() {
		$scope.files = AnnexesService.query();
	}
	$scope.onClickTest2 = function() {
		$scope.file = NewsService.get({nsme: 'Test_annexe.pdf'});
	}
};


function AttachController($scope, $rootScope, $location, $cookieStore, $http, localStorageService) {
	$scope.files = [];
}

var services = angular.module('demautApp.services', ['ngResource']);

services.factory('AnnexeService', function($resource) {
	return $resource('services/annexes/:name', {name: '@name'});
});

services.factory('AnnexesService', function($resource) {
	return $resource('services/annexes/all');
});