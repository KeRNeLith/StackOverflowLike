/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var questionModule = angular.module('segFault.question');

// Directives
questionModule.directive('sfQuestionEntry', function () {
    return {
        scope: {
          question: '='
        },
        templateUrl: 'modules/question/templates/_questionEntry.html'
    };
});

questionModule.directive('sfQuestionVotes', function () {
    return {
        scope: {
            post: '=',
            nbVotes: '='
        },
        templateUrl: 'modules/question/templates/_voteCount.html'
    };
});
