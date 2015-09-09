var commonsModule = angular.module('commonsModule', []);

commonsModule.service('nationalityTest', ['$log', function ($log) {
    this.suissePattern = new RegExp('[Ss]uisse');
    this.isSuisse = function(textValue){
        return this.suissePattern.test(textValue);
    };
}]);

commonsModule.directive('stepActions', function(){
    return {
        restrict: 'E',
        scope:{previewStepFn:'&',nextStepFn:'&', cancelAction:'&'},
        templateUrl: 'template/directive/step-actions.html',
        link: function (scope,element, attrs) {
            scope.showNext = element.attr('next-step-fn');
            scope.showPreview = element.attr('preview-step-fn');
        }
    };
});