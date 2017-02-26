/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var commentModule = angular.module('segFault.comment');

// Define controllers
commentModule.controller('RedactCommentCtrl', function($translate, PageService)
{
    $translate('comment.title.page').then(function (translatedKey)
    {
        PageService.setTitle(translatedKey + ' - ' + PageService.default());
    });
});
