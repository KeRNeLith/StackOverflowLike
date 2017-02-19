/**
 * Created by kernelith on 19/02/17.
 */

var questionModule = angular.module('segFault.question');

// Directives
questionModule.directive('sfQuestionEntry', function () {
    return {
        templateUrl: 'question/templates/_questionEntry.html'
    };
});