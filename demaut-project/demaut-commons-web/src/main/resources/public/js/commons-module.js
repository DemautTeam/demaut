var commonsModule = angular.module('commonsModule', []);

commonsModule.service('nationalityTest', ['$log', function ($log) {
    this.suissePattern = new RegExp('[Ss]uisse');
    this.isSuisse = function(textValue){
        return this.suissePattern.test(textValue);
    };
}]);
