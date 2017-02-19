/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var segFaultAuthModule = angular.module('segFault.auth');

class UserService
{
    constructor($http, API, AuthService)
    {
    }

    handleRequest(response)
    {
        AuthService.save(response.data.token);
        return response.data.message;
    }

    // Authentication methods
    //...
}

segFaultAuthModule.service('UserService', UserService);