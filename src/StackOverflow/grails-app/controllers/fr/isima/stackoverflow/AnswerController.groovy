package fr.isima.stackoverflow

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class AnswerController
{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    // Services
    def springSecurityService
    def questionService

    // Actions
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Answer.list(params), model:[answerCount: Answer.count()]
    }

    def show(Answer answer)
    {
        respond answer
    }

    def create()
    {
        respond new Answer(params)
    }

    @Secured('ROLE_USER')
    def redact()
    {
        if (params.containsKey('question'))
        {
            def questionId = params.long('question')
            if (Question.exists(questionId))
            {
                respond new Answer(params), view: 'redact', model: [answerTo: questionId]
            }
            else
            {
                notFound()
            }
        }
        else
        {
            notFound()
        }
    }

    @Transactional
    def save(Answer answer)
    {
        if (answer == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (answer.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond answer.errors, view:'create'
            return
        }

        answer.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'answer.label', default: 'Answer'), answer.id])
                redirect answer
            }
            '*' { respond answer, [status: CREATED] }
        }
    }

    @Secured('ROLE_USER')
    @Transactional
    def addAnswer(Answer answer)
    {
        if (answer == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        // Validate message constraint
        if (!answer.validate(['message']))
        {
            transactionStatus.setRollbackOnly()
            respond answer.errors, view:'redact'
            return
        }

        // Add response
        if (params.containsKey('question'))
        {
            def questionId = params.long('question')
            def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

            if (questionService.addAnswerToQuestion(answer.message, user, questionId))
            {
                redirect(controller: 'question', action: 'display', id: questionId)
            }
            else
            {
                notFound()
            }
        }
        else
        {
            notFound()
        }
    }

    def edit(Answer answer)
    {
        respond answer
    }

    @Secured('ROLE_USER')
    def redactEdit(Answer answer)
    {
        respond answer, view: 'redactEdit'
    }

    @Transactional
    def update(Answer answer)
    {
        if (answer == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (answer.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond answer.errors, view:'edit'
            return
        }

        answer.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'answer.label', default: 'Answer'), answer.id])
                redirect answer
            }
            '*'{ respond answer, [status: OK] }
        }
    }

    @Secured('ROLE_USER')
    @Transactional
    def updateEdit(Answer answer)
    {
        if (answer == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (answer.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond answer.errors, view:'redactEdit'
            return
        }

        answer.save flush:true

        redirect(controller: 'question', action: 'display', id: answer.question.id)
    }

    @Transactional
    def delete(Answer answer)
    {
        if (answer == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        answer.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'answer.label', default: 'Answer'), answer.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'answer.label', default: 'Answer'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
