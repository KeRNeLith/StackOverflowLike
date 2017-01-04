package fr.isima.stackoverflow.controller

import fr.isima.stackoverflow.Question
import grails.plugin.springsecurity.annotation.Secured;

@Secured('ROLE_ADMIN')
class QuestionController
{
    def scaffold = Question

    //def index() { }
}
