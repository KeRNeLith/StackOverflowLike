package fr.isima.stackoverflow.controller

import fr.isima.stackoverflow.User
import grails.plugin.springsecurity.annotation.Secured;

@Secured('ROLE_ADMIN')
class UserController
{
    def scaffold = User

    //def index() { }
}
