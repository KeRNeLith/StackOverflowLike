/**
 * Created by kernelith on 24/02/17.
 */

'use strict';

var postModule = angular.module('segFault.post');

// Define controllers
postModule.controller('PostQuestionCtrl', function($scope)
{
    var self = this;

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
});

// ------------------------------------------------------------------------
postModule.controller('RedactPostQuestionCtrl', function($scope, $controller, RedirectionService, PostQuestionService)
{
    var self = this;
    // Instantiate base controller
    angular.extend(self, $controller('PostQuestionCtrl', { $scope: $scope }));

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
                    },
                    function errorCallback(response)
                    {
                        resetForm($scope.redactQuestionForm);

                        self.handleErrors(response);
                    });
    };
});

// ------------------------------------------------------------------------
postModule.controller('EditPostQuestionCtrl', function($scope, $controller, $route, RedirectionService, EditPostQuestionService)
{
    var self = this;
    // Instantiate base controller
    angular.extend(self, $controller('PostQuestionCtrl', { $scope: $scope }));

    self.setQuestionId = function(targetId)
    {
        EditPostQuestionService.setQuestionId(targetId);

        // Save last URL to be redirected after update
        RedirectionService.saveLastURL();
    };

    self.send = function()
    {
        EditPostQuestionService.setTitle($scope.title);
        EditPostQuestionService.setTags($scope.tags);// TODO check
        EditPostQuestionService.setMessage($scope.message);

        let result = EditPostQuestionService.send();
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
                            self.handleErrors(response);
                        });
        }
        // No question set => should never arrive unless refresh of the page
        else
        {
            console.log("Impossible to update question: No question set");
            RedirectionService.redirectToLastURL();
        }
    };
});

// ------------------------------------------------------------------------
// ------------------------------------------------------------------------
// ------------------------------------------------------------------------
postModule.controller('RedactPostCtrl', function($scope, $route, RedirectionService)
{
    var self = this;

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

    self.handleErrors = function (response)
    {
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
postModule.controller('EditPostAnswerCtrl', function($scope, $controller, EditPostAnswerService)
{
    var self = this;
    // Instantiate base controller
    angular.extend(self, $controller('RedactPostCtrl', { $scope: $scope }));

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

// ------------------------------------------------------------------------
postModule.controller('EditPostCommentCtrl', function($scope, $controller, EditPostCommentService)
{
    var self = this;
    // Instantiate base controller
    angular.extend(self, $controller('RedactPostCtrl', { $scope: $scope }));

    self.send = function()
    {
        EditPostCommentService.setMessage($scope.message);

        let ret = EditPostCommentService.send();

        self.handleSendResult(ret);
    };

    self.setPost = function(targetId)
    {
        EditPostCommentService.setPostId(targetId);

        self.saveRedirection();
    };
});
