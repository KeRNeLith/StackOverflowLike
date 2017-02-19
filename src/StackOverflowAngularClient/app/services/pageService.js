/**
 * Created by kernelith on 19/02/17.
 */

var segFaultApp = angular.module('segFault');

class PageService
{
    constructor()
    {
        this._title = 'Segfault';
    }

    title()
    {
        return this._title;
    }

    setTitle(newTitle)
    {
        this._title = newTitle;
    }
}

segFaultApp.service('PageService', PageService);