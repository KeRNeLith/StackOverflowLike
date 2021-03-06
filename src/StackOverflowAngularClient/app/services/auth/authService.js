/**
 * Created by kernelith on 19/02/17.
 */

'use strict';

var segFaultAuthModule = angular.module('segFault.auth', []);

class AuthService
{
    constructor($window)
    {
        this.$window = $window;
    }

    // JWT methods
    handleRequest(response)
    {
        let token = response.data ? response.data.access_token : null;
        if(token)
        {
            this.saveToken(token);
        }
    }

    parseJWTToken(token)
    {
        // Decode token payload
        let base64Url = token.split('.')[1];
        let base64 = base64Url.replace('-', '+').replace('_', '/');
        return JSON.parse(this.$window.atob(base64));
    }

    saveToken(token)
    {
        this.$window.localStorage['jwtToken'] = token;
    }

    getToken()
    {
        return this.$window.localStorage['jwtToken'];
    }

    logout()
    {
        this.$window.localStorage.removeItem('jwtToken');
    }

    // Auth methods
    isAuthenticated()
    {
        let token = this.getToken();
        if (token)
        {
            let params = this.parseJWTToken(token);
            // Check is token had expired
            return Math.round(new Date().getTime() / 1000) <= params.exp;   // Conversion because token is Unix Time is in seconds while JavaScript returns milliseconds
        }
        else
        {
            return false;
        }
    }

    currentUser()
    {
        let currentUser = null;

        let token = this.getToken();
        if (token)
        {
            let params = this.parseJWTToken(token);
            currentUser = params.sub;
        }

        return currentUser;
    }
}

segFaultAuthModule.service('AuthService', AuthService);