/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var questionModule = angular.module('segFault.question');

// Define controllers
questionModule.controller('HomeCtrl', function($scope, $http, API, PageService)
{
    PageService.setTitle(PageService.default());

    $http.get(API + '/api/question/home')
        .then(function(response)
        {
            let data = response.data;

            $scope.recents = data.recents;
            $scope.questionsByCat = data.questionsByCat;
            $scope.randomSentence = data.randomSentence;
        });
});

questionModule.controller('QuestionDisplayCtrl', function($scope, $http, $routeParams, API, PageService)
{
    $http.get(API + '/api/question/display/' + $routeParams.id)
        .then(function(response)
        {
            let data = response.data;

            let question = data.question;
            $scope.question = question;
            PageService.setTitle(question.title + ' - ' + PageService.default());

            $scope.questionVotes = data.questionVotes;
            $scope.answers = data.sortedAnswers;
        });
});

questionModule.controller('RedactQuestionCtrl', function($scope, $http, $translate, API, PageService)
{
    $translate('question.redact.title.page').then(function (translatedKey)
    {
        PageService.setTitle(translatedKey + ' - ' + PageService.default());
    });

    $http.get(API + '/api/tagValue/list')
        .then(function(response)
        {
            let data = response.data;

            $scope.availableTags = data.tags;
        });
});
