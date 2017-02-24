/**
 * Created by kernelith on 24/02/17.
 */

'use strict';

var postModule = angular.module('segFault.post');

// Define controllers
postModule.controller('RedactPostCtrl', function(PostService)
{
    var self = this;

    self.send = function()
    {
        console.log("Send for post: " + PostService.postId());
        // TODO send post (check if not -1)
    };

    self.setPost = function(targetId)
    {
        PostService.setPostId(targetId);
    };
});