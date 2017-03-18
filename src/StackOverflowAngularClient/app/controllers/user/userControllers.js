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

userModule.controller('EditProfileCtrl', function($scope, $controller, EditPostAnswerService)
{
  var self = this;
  // Instantiate base controller
  //angular.extend(self, $controller('ProfileCtrl', { $scope: $scope }));

  self.send = function()
  {
      EditPostAnswerService.setMessage($scope.message);

      let ret = EditPostAnswerService.send();

      self.handleSendResult(ret);
  };

  self.setPost = function(targetId)
  {
      EditPostAnswerService.setPostId(targetId);

      self.saveRedirection();
  };
});
