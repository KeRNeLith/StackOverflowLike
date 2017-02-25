/**
 * Created by kernelith on 24/02/17.
 */

'use strict';

var segFaultPostModule = angular.module('segFault.post', []);

class PostService
{
    constructor($http, API)
    {
        this.$http = $http;
        this.API = API;

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

    send(message)
    {
        if (this._postId < 0)
            return null;

        return this.$http.post(this.API + '/api/answer/saveAnswer', {
            message: message,
            question: this._postId
        });
    }
}

segFaultPostModule.service('PostService', PostService);