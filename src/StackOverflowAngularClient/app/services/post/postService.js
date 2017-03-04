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

        this._message = '';
    }

    message()
    {
        return this._message;
    }

    setMessage(newMessage)
    {
        this._message = newMessage;
    }
}

segFaultPostModule.service('PostService', PostService);

// ------------------------------------------------------------------------
class PostQuestionService extends PostService
{
    constructor($http, API)
    {
        super($http, API);

        this._title = '';
        this._tagsIds = [];
    }

    title()
    {
        return this._title;
    }

    setTitle(newTitle)
    {
        this._title = newTitle;
    }

    tags()
    {
        return this._tagsIds;
    }

    setTags(newTags)
    {
        this._tagsIds = newTags;
    }

    send()
    {
        return this.$http.post(this.API + '/api/question/saveQuestion', {
            title: this._title,
            message: this._message,
            tags: this._tagsIds
        });
    }
}

segFaultPostModule.service('PostQuestionService', PostQuestionService);

// ------------------------------------------------------------------------
class SubPostService extends PostService
{
    constructor($http, API)
    {
        super($http, API);

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

    send()
    {
        // Valid id
        return this._postId >= 0;
    }
}

segFaultPostModule.service('SubPostService', SubPostService);

// ------------------------------------------------------------------------
class PostAnswerService extends SubPostService
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
class EditPostAnswerService extends SubPostService
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
            ret = this.$http.put(this.API + '/api/answer/updateAnswer', {
                answer: this._postId,
                message: this._message
            });
        }

        return ret;
    }
}

segFaultPostModule.service('EditPostAnswerService', EditPostAnswerService);

// ------------------------------------------------------------------------
class PostCommentService extends SubPostService
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

// ------------------------------------------------------------------------
class EditPostCommentService extends SubPostService
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
            ret = this.$http.put(this.API + '/api/comment/updateComment', {
                comment: this._postId,
                message: this._message
            });
        }

        return ret;
    }
}

segFaultPostModule.service('EditPostCommentService', EditPostCommentService);
