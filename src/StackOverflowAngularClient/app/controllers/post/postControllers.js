/**
 * Created by kernelith on 24/02/17.
 */

'use strict';

var postModule = angular.module('segFault.post');

// Define controllers
postModule.controller('RedactPostQuestionCtrl', function($scope, RedirectionService, PostQuestionService)
{
    var self = this;

    self.send = function()
    {
        PostQuestionService.setTitle(self.title);
        PostQuestionService.setTags(self.tags);
        PostQuestionService.setMessage(self.message);

        let result = PostQuestionService.send();
        result.then(function successCallback(response)
                    {
                        let id = response.data.message;
                        let questionId = parseInt(id);
                        if (!isNaN(questionId))
                        {
                            RedirectionService.redirectTo('/question/display/' + questionId);
                        }
                        // Should never arrive : No question id found => redirect to home
                        else
                        {
                            RedirectionService.redirectToHome();
                        }
                    },
                    function errorCallback(response)
                    {
                        resetForm($scope.redactQuestionForm);

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
                    });
    };
});

// ------------------------------------------------------------------------
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