/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var userModule = angular.module('segFault.user');

// Define controllers
userModule.controller('ProfileCtrl', function($scope, $http, $routeParams, API, PageService)
{
    $http.get(API + '/api/user/profile?username=' + $routeParams.username)
        .then(function(response)
        {
            PageService.setTitle($routeParams.username + ' - ' + PageService.default());

            let data = response.data.user;
            $scope.username = data.username;
            $scope.registeredDate = data.registerDate;
            $scope.answers = data.answers;
            $scope.reputation = data.reputation;
            $scope.questions = data.questions;
            $scope.votes = data.votes;
        });
});

/*userModule.controller('rhzshr', function($scope, $http, $routeParams, API, PageService)
{
    $http.get(API + '/api/question/display/' + $routeParams.id) //TODO  change here
        .then(function(response)
        {
            let data = response.data;

            let question = data.question;
            $scope.question = question;
            PageService.setTitle(question.title + ' - ' + PageService.default());

            $scope.questionVotes = data.questionVotes;
            $scope.answers = data.sortedAnswers;
        });
});*/
