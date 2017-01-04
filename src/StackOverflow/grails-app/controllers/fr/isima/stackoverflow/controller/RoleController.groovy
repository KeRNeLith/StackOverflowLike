package fr.isima.stackoverflow.controller

import fr.isima.stackoverflow.Role
import grails.plugin.springsecurity.annotation.Secured;

@Secured('ROLE_ADMIN')
class RoleController
{
    def scaffold = Role

    //def index() { }
}
