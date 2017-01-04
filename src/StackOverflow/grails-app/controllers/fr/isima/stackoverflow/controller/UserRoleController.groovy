package fr.isima.stackoverflow.controller

import fr.isima.stackoverflow.UserRole
import grails.plugin.springsecurity.annotation.Secured;

@Secured('ROLE_ADMIN')
class UserRoleController
{
    def scaffold = UserRole

    //def index() { }
}
