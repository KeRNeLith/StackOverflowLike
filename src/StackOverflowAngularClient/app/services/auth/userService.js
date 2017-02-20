/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var segFaultAuthModule = angular.module('segFault.auth');

class UserService
{
    constructor($http, API, AuthService)
    {
        this.$http = $http;
        this.API = API;
        this.AuthService = AuthService;
    }

    // Authentication methods
    register(username, password)
    {
        return this.$http.post(this.API + '/api/user/register', {
            username: username,
            password: password
        });
    }

    login(username, password)
    {
        return this.$http.post(this.API + '/api/login', {
            username: username,
            password: password
        });
    }

    logout()
    {
        this.AuthService.logout();
    }
}

segFaultAuthModule.service('UserService', UserService);