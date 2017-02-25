/**
 * Created by kernelith on 24/02/17.
 */

'use strict';

var postModule = angular.module('segFault.post');

// Define controllers
postModule.controller('RedactPostCtrl', function($scope, $route, PostService, RedirectionService)
{
    var self = this;

    self.send = function()
    {
        let ret = PostService.send(self.message);
        if (ret != null)
        {
            ret.then(function successCallback(response)
                    {
                        RedirectionService.redirectToLastURL();
                        $route.reload();
                    },
                    function errorCallback(response)
                    {
                        resetForm($scope.redactAnswerForm);

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

    self.setPost = function(targetId)
    {
        PostService.setPostId(targetId);

        // Save redirection after posting
        RedirectionService.saveLastURL();
    };
});