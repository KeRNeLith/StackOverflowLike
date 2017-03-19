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
            $scope.description = data.description;
            $scope.username = data.username;
            $scope.registeredDate = data.registerDate;
            $scope.answers = data.answers;
            $scope.reputation = data.reputation;
            $scope.questions = data.questions;
            $scope.votes = data.votes;
        });
});

userModule.controller('EditProfileCtrl', function($scope, $http, $routeParams, API, UserService, PageService, RedirectionService)
{
  var self = this;

  $http.get(API + '/api/user/profile?username=' + $routeParams.username)
  .then(function(response)
  {
      PageService.setTitle($routeParams.username + ' - ' + PageService.default());

      let data = response.data.user;
      $scope.description = data.description;

  });

  self.handleErrors = function (response)
  {
      if (response.data.message)
      {
          let errors = JSON.parse(response.data.message);

          let titleError = null;
          let messageError = null;
          let otherErrors = [];

          // Check error's type
          if (!angular.isArray(errors))
          {
              errors = [ errors ];    // Make it array
          }

          angular.forEach(errors, function (errorCode) {
              if (errorCode.search('title') != -1)
              {
                  titleError = errorCode;
              }
              else if (errorCode.search('message') != -1)
              {
                  messageError = errorCode;
              }
              else
              {
                  otherErrors.push(errorCode);
              }
          });

          $scope.titleError = titleError;
          $scope.messageError = messageError;
          $scope.otherErrors = otherErrors;
      }
  };

  self.handleSendResult = function(result, form = null)
  {
      if (result != null)
      {
          result.then(function successCallback(response)
                  {
                      if (response.status < 400)
                      {
                          RedirectionService.redirectToLastURL();
                          $route.reload();
                      }
                  },
                  function errorCallback(response)
                  {
                      if (form != null)
                          resetForm(form);

                      self.handleErrors(response);
                  });
      }
      // Post id not set => redirect to last saved URL (should be home page in case of page refresh)
      else
      {
          RedirectionService.redirectToLastURL();
      }
  };

  self.edit = function()
  {
      let ret = UserService.changeDescription($scope.description);

      self.handleSendResult(ret, $scope.userDescriptionEdit);
  };

  self.saveCurrentURL = function()
  {
    // Save last URL to be redirected after update
    RedirectionService.saveLastURL();
  }
});
