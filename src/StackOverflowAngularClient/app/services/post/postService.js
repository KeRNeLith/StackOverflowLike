/**
 * Created by kernelith on 24/02/17.
 */

'use strict';

var segFaultPostModule = angular.module('segFault.post', []);

class PostService
{
    constructor()
    {
        this._postId = -1;
    }

    postId()
    {
        return this._postId;
    }

    setPostId(newId)
    {
        this._postId = newId;
    }
}

segFaultPostModule.service('PostService', PostService);