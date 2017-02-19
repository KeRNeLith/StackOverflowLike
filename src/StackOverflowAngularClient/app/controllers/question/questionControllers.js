/**
 * Created by kernelith on 19/02/17.
 */

var questionModule = angular.module('segFault.question');

// Define controllers
questionModule.controller('HomeCtrl', function($scope, $http)
{
    $http.get('http://localhost:8080/api/question/home')
        .then(function(response)
        {
            let data = response.data;

            $scope.recents = data.recents;
            $scope.questionsByCat = data.questionsByCat;
            $scope.randomSentence = data.randomSentence;
        });
});

questionModule.controller('QuestionDisplayCtrl', function($scope, $http, $routeParams, PageService)
{
    $http.get('http://localhost:8080/api/question/display/' + $routeParams.id)
        .then(function(response)
        {
            let data = response.data;

            let question = data.question;
            $scope.question = question;
            PageService.setTitle(question.title + ' - ' + PageService.title());

            $scope.questionVotes = data.questionVotes;
            $scope.answers = data.sortedAnswers;
        });
});