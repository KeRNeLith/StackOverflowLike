package fr.isima.stackoverflow.controller

import fr.isima.stackoverflow.Answer
import grails.plugin.springsecurity.annotation.Secured;

@Secured('ROLE_ADMIN')
class AnswerController
{
    def scaffold = Answer

   // def index() { }
}
