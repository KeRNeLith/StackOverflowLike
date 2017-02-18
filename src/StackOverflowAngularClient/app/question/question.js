'use strict';

var questionModule = angular.module('segFault.question', ['ngRoute']);

// Define available routes
questionModule.config(['$routeProvider', function($routeProvider)
{
  $routeProvider.when('/', {
    templateUrl: 'question/home.html',
    controller: 'HomeCtrl'
  });
  $routeProvider.when('/question/home', {
      templateUrl: 'question/home.html',
      controller: 'HomeCtrl'
  });
}]);

// Define controller
questionModule.controller('HomeCtrl', function($scope, $http)
{
    $http .get('http://localhost:8080/api/question/home')
          .then(function(response)
          {
              var data = response.data;

              // TMP
              $scope.homeData = data;

              $scope.randomSentence = data.randomSentence;
          });
});