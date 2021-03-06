"use strict";
var commonsModule = angular.module('commonsModule', ['ngResource']);

commonsModule.service('globalMessagesService', ['$rootScope',function($rootScope) {

    $rootScope.alertMessages = [];

    this.showMessage = function(text,level){
        $rootScope.alertMessages.push({
            text:text,
            level:level
        });
    }

    this.clearMessages = function(){
        $rootScope.alertMessages = [];
    }

}]);

commonsModule.factory('globalDefaultError', ['$q', '$rootScope', '$log','globalMessagesService', function ($q, $rootScope, $log, globalMessagesService) {
    return {
        'responseError': function (rejection) {
            var status = rejection.status;
            var config = rejection.config;
            var data = rejection.data;
            var method = config.method;
            var url = config.url;

            $log.debug('Error status=' + status);

            if (status == 417) {//Erreur dans le domaine
                globalMessagesService.showMessage(data.message,'warning');
            }
            else {
                var errorMessage = method + ' on ' + url + ' failed with status ' + status + '<br>' +
                    (data != null && data != undefined ? data.substring(data.indexOf('<body>') + 6, data.indexOf('</body>')) : "data empty!");
                globalMessagesService.showMessage(errorMessage,'error');

            }
            return $q.reject(rejection);
        }
    };
}]);

//------------------------- Service -------------------------
commonsModule.service('nationalityTest', ['$log', function ($log) {
    this.suissePattern = new RegExp('[Ss]uisse');
    this.isSuisse = function (textValue) {
        return this.suissePattern.test(textValue);
    };
}]);

commonsModule.service('professionTest', ['$log', function ($log) {
    this.isProfessionNecessiteCodeGLN = function (professionValue, listProfessions) {
        if (professionValue != null && professionValue != undefined) {
            for (var indexI = 0; indexI < listProfessions.length; indexI++) {
                if (listProfessions[indexI].id == professionValue.id) {
                    return true;
                }
            }
        }
        return false;
    };
}]);

/**
 * Le but de ce service est de fournir des methodes utilitaires
 */
commonsModule.service('utils', ['$log', function ($log) {
    this.isNotEmpty = function (value){
        return value != undefined || value != null;
    };
    this.isEmpty = function (value){
        return value == undefined || value == null;
    };
}]);

commonsModule.service('configService', ['$resource', function ($resource) {
    this.config = $resource('api/config/urlprefix');
    this.getUrlPrefix = function () {
        return this.config.get();
    }

}]);

//------------------------ Directive -------------------------
commonsModule.directive('stepActions', function () {
    return {
        restrict: 'E',
        scope: {backStepFn: '&', nextStepFn: '&', cancelAction: '&'},
        templateUrl: prestationContext + '/template/directive/step-actions.html',
        link: function (scope, element, attrs) {
            scope.showNext = element.attr('next-step-fn');
            scope.showBack = element.attr('back-step-fn');
        }
    };
});

commonsModule.directive('ngConfirmClick', [
    function(){
        return {
            link: function (scope, element, attr) {
                var msg = attr.ngConfirmClick || "Voulez-vous poursuivre l'opération en cours ?";
                var clickAction = attr.confirmedClick;
                element.bind('click',function (event) {
                    if (window.confirm(msg)) {
                        scope.cockpitData.doDelete = true;
                    }
                });
            }
        };
    }])

