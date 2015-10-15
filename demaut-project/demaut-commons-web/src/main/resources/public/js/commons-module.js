var commonsModule = angular.module('commonsModule', ['ngResource']);

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

commonsModule.service('configService', ['$resource', function ($resource) {
    this.config = $resource('api/config/urlprefix');
    this.getUrlPrefix = function () {
        return this.config.get();
    }

}]);

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