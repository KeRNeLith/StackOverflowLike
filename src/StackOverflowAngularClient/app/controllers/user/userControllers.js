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
            $scope.id = data.id;
            $scope.username = data.username;
            $scope.registeredDate = data.registerDate;
            $scope.answers = data.answers;
            $scope.reputation = data.reputation;
            $scope.questions = data.questions;
            $scope.votes = data.votes;
        });
});

userModule.controller('ProfileedfCtrl', function($scope, $http, $routeParams, API, PageService)
{
  /* create a forme with the user's profile info  with which when the user will
  * call this controller  it will "validate" everything and call the correct API service
  */
  /*  $http.get(API + '/api/user/profile?username=' + $routeParams.username)
        .then(function(response)
        {
            PageService.setTitle($routeParams.username + ' - ' + PageService.default());


        });*/
});
