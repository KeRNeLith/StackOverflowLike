package fr.isima.stackoverflow.controller

import fr.isima.stackoverflow.TagValue
import grails.plugin.springsecurity.annotation.Secured;

@Secured('ROLE_ADMIN')
class TagValueController
{
    def scaffold = TagValue

    //def index() { }
}
