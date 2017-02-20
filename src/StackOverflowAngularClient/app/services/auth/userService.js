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

    handleRequest(response)
    {
        this.AuthService.save(response.data.token);
        return response.data.message;
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
}

segFaultAuthModule.service('UserService', UserService);