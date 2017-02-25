/**
 * Created by kernelith on 24/02/17.
 */

'use strict';

var postModule = angular.module('segFault.post');

// Define controllers
postModule.controller('RedactPostCtrl', function($route, PostService, RedirectionService)
{
    var self = this;

    self.send = function()
    {
        let ret = PostService.send(self.message);
        if (ret != null)
        {
            ret.then(function successCallback(response)
                    {
                        // TODO Check if errors
                        RedirectionService.redirectToLastURL();
                        $route.reload();
                    },
                    function errorCallback(response)
                    {
                        // TODO Handle error
                        //$scope.errorMessage = 'error.register.signUp.impossible';
                    });
        }
    };

    self.setPost = function(targetId)
    {
        PostService.setPostId(targetId);
        
        // Save redirection after posting
        RedirectionService.saveLastURL();
    };
});