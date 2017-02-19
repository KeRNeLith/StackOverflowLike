'use strict';

var questionModule = angular.module('segFault.question', ['ngRoute']);

// Define available routes
questionModule.config(['$routeProvider', function($routeProvider)
{
  // Home page
  $routeProvider.when('/', {
    templateUrl: 'question/home.html',
    controller: 'HomeCtrl'
  });
  $routeProvider.when('/question/home', {
      templateUrl: 'question/home.html',
      controller: 'HomeCtrl'
  });

  // Display question
  $routeProvider.when('/question/display/:id', {
      templateUrl: 'question/display.html',
      controller: 'QuestionDisplayCtrl'
  });
}]);

// Define controllers
questionModule.controller('HomeCtrl', function($scope, $http)
{
    $http .get('http://localhost:8080/api/question/home')
          .then(function(response)
          {
              var data = response.data;

              $scope.recents = data.recents;
              $scope.questionsByCat = data.questionsByCat;
              $scope.randomSentence = data.randomSentence;
          });
});

questionModule.controller('QuestionDisplayCtrl', function($scope, $http, $routeParams)
{
    //$routeParams.id
    // TODO
});

// Directives
questionModule.directive('sfQuestionEntry', function () {
    return {
        templateUrl: 'question/templates/_questionEntry.html'
    };
});