/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var questionModule = angular.module('segFault.question');

// Directives
questionModule.directive('sfQuestionEntry', function () {
    return {
        templateUrl: 'modules/question/templates/_questionEntry.html'
    };
});

questionModule.directive('sfQuestionVotes', function () {
    return {
        scope: {
            post: '=',
            votes: '='
        },
        templateUrl: 'modules/question/templates/_voteCount.html'
    };
});