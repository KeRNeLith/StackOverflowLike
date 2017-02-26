/**
 * Created by kernelith on 24/02/17.
 */

'use strict';

var postModule = angular.module('segFault.post');

// Define controllers
postModule.controller('RedactPostCtrl', function($scope, $route, RedirectionService)
{
    var self = this;

    self.handleSendResult = function(result, form)
    {
        if (result != null)
        {
            result.then(function successCallback(response)
                    {
                        RedirectionService.redirectToLastURL();
                        $route.reload();
                    },
                    function errorCallback(response)
                    {
                        resetForm(form);

                        if (response.data.message)
                        {
                            let errors = JSON.parse(response.data.message);

                            let messageError = null;
                            let otherErrors = [];

                            // Check error's type
                            if (!angular.isArray(errors))
                            {
                                errors = [ errors ];    // Make it array
                            }

                            angular.forEach(errors, function (errorCode) {
                                if (errorCode.search('message') != -1)
                                {
                                    messageError = errorCode;
                                }
                                else
                                {
                                    otherErrors.push(errorCode);
                                }
                            });

                            $scope.messageError = messageError;
                            $scope.otherErrors = otherErrors;
                        }
                    });
        }
        // Post id not set => redirect to last saved URL (should be home page in case of page refresh)
        else
        {
            RedirectionService.redirectToLastURL();
        }
    };

    self.saveRedirection = function()
    {
        // Save redirection after posting
        RedirectionService.saveLastURL();
    };
});

// ------------------------------------------------------------------------
postModule.controller('RedactPostAnswerCtrl', function($scope, $controller, PostAnswerService)
{
    var self = this;
    // Instantiate base controller
    angular.extend(self, $controller('RedactPostCtrl', { $scope: $scope }));

    self.send = function()
    {
        PostAnswerService.setMessage(self.message);

        let ret = PostAnswerService.send();

        self.handleSendResult(ret, $scope.redactAnswerForm);
    };

    self.setPost = function(targetId)
    {
        PostAnswerService.setPostId(targetId);

        self.saveRedirection();
    };
});


// ------------------------------------------------------------------------
postModule.controller('RedactPostCommentCtrl', function($scope, $controller, PostCommentService)
{
    var self = this;
    // Instantiate base controller
    angular.extend(self, $controller('RedactPostCtrl', { $scope: $scope }));

    self.send = function()
    {
        PostCommentService.setMessage(self.message);

        let ret = PostCommentService.send();

        self.handleSendResult(ret, $scope.redactCommentForm);
    };

    self.setPost = function(targetId)
    {
        PostCommentService.setPostId(targetId);

        self.saveRedirection();
    };
});