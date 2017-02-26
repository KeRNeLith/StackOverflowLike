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
        this._message = '';
    }

    postId()
    {
        return this._postId;
    }

    setPostId(newId)
    {
        this._postId = newId;
    }

    message()
    {
        return this._message;
    }

    setMessage(newMessage)
    {
        this._message = newMessage;
    }

    send()
    {
        // Valid id
        return this._postId >= 0;
    }
}

segFaultPostModule.service('PostService', PostService);

// ------------------------------------------------------------------------
class PostAnswerService extends PostService
{
    constructor($http, API)
    {
        super($http, API);
    }

    send()
    {
        let ret = null;

        if (super.send())
        {
            ret = this.$http.post(this.API + '/api/answer/saveAnswer', {
                message: this._message,
                question: this._postId
            });
        }

        return ret;
    }
}

segFaultPostModule.service('PostAnswerService', PostAnswerService);

// ------------------------------------------------------------------------
class PostCommentService extends PostService
{
    constructor($http, API)
    {
        super($http, API);
    }

    send()
    {
        let ret = null;

        if (super.send())
        {
            ret = this.$http.post(this.API + '/api/comment/saveComment', {
                message: this._message,
                answer: this._postId
            });
        }

        return ret;
    }
}

segFaultPostModule.service('PostCommentService', PostCommentService);