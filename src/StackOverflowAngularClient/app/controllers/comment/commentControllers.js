/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var commentModule = angular.module('segFault.comment');

// Define controllers
commentModule.controller('RedactCommentCtrl', function($translate, PageService)
{
    $translate('comment.redact.title.page').then(function (translatedKey)
    {
        PageService.setTitle(translatedKey + ' - ' + PageService.default());
    });
});

answerModule.controller('EditCommentCtrl', function($scope, $http, $routeParams, $translate, API, PageService)
{
    $translate('comment.edit.title.page').then(function (translatedKey)
    {
        PageService.setTitle(translatedKey + ' - ' + PageService.default());
    });

    $http.get(API + '/api/comment/redactEdit/' + $routeParams.id)
        .then(function(response)
        {
            let data = response.data;

            $scope.message = data.message;
        });
});
