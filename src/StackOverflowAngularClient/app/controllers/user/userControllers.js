/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var userModule = angular.module('segFault.user');

// Define controllers
userModule.controller('ProfileCtrl', function($scope, $http, $routeParams, $location, $anchorScroll, API, PageService, AuthService)
{
    $http.get(API + '/api/user/profile?username=' + $routeParams.username)
        .then(function(response)
        {
            PageService.setTitle($routeParams.username + ' - ' + PageService.default());

            let data = response.data;

            let user = data.user;
            $scope.id = user.id;
            $scope.description = user.description;
            $scope.username = user.username;
            $scope.registeredDate = user.registerDate;
            $scope.answers = user.answers;
            $scope.reputation = user.reputation;
            $scope.questions = user.questions;
            $scope.votes = user.votes;
            $scope.badges = data.badges;

            $scope.isOwner = AuthService.isAuthenticated() && AuthService.currentUser() == user.username;
        });

    // Anchors
    $scope.goToTop = function()
    {
        $location.hash('Top');

        // call $anchorScroll()
        $anchorScroll();
    };

    $scope.goToMyQuestions = function()
    {
        $location.hash('MyQuestions');

        // call $anchorScroll()
        $anchorScroll();
    };

    $scope.goToMyAnswers = function()
    {
        $location.hash('MyAnswers');

        // call $anchorScroll()
        $anchorScroll();
    };

    $scope.goToMyBadges = function()
    {
        $location.hash('MyBadges');

        // call $anchorScroll()
        $anchorScroll();
    };
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

  self.handleErrors = function (response, form = null)
  {
      if (form != null)
          resetForm(form);

      if (response.data.message)
      {
          let errors = JSON.parse(response.data.message);

          let descriptionError = null;
          let otherErrors = [];

          // Check error's type
          if (!angular.isArray(errors))
          {
              errors = [ errors ];    // Make it array
          }

          angular.forEach(errors, function (errorCode) {
              if (errorCode.search('description') != -1)
              {
                  descriptionError = errorCode;
              }
              else
              {
                  otherErrors.push(errorCode);
              }
          });

          $scope.descriptionError = descriptionError;
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
                      }
                      else
                      {
                          self.handleErrors(response, form);
                      }
                  },
                  function errorCallback(response)
                  {
                      self.handleErrors(response, form);
                  });
      }
  };

  self.edit = function()
  {
      let ret = UserService.changeDescription($scope.description);

      self.handleSendResult(ret, $scope.userEditProfileForm);
  };

  self.saveCurrentURL = function()
  {
    // Save last URL to be redirected after update
    RedirectionService.saveLastURL();
  }
});
