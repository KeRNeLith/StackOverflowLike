package fr.isima.stackoverflow

import fr.isima.marshallers.AnswersMarshallers
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class AnswerController
{
    static responseFormats = ['json']
    static allowedMethods = [saveAnswer: "POST", updateAnswer: "PUT",
                             // Default grails methods
                             save: "POST", update: "PUT", delete: "DELETE"]

    // Services
    def springSecurityService
    def questionService
    def answerService
    def featuresFlippingService

    // Actions
    @Secured('ROLE_USER')
    @Transactional
    def saveAnswer()
    {
        if (!featuresFlippingService.isAnswerPostingEnabled())
        {
            render status: SERVICE_UNAVAILABLE, message: '"error.service.unavailable.post.answer"'
            return
        }

        def inputRequest = request.JSON

        def status = BAD_REQUEST
        String retCode = '"error.question.add.answer.wrong.parameters"'
        // Add answer
        if (inputRequest.message != null && inputRequest.question != null)
        {
            def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

            Long questionId = -1
            if (inputRequest.question instanceof String)
            {
                questionId = Long.parseLong(inputRequest.question)
            }
            else
            {
                questionId = inputRequest.question
            }

            retCode = questionService.addAnswerToQuestion(inputRequest.message, user, questionId)
            if (retCode == '"success.question.add.answer"')
            {
                status = CREATED
            }
        }

        render status: status, message: retCode
    }

    @Secured('ROLE_USER')
    def redactEdit(Answer answer)
    {
        JSON.use(AnswersMarshallers.LIGHT_ANSWER) {
            respond answer
        }
    }

    @Secured('ROLE_USER')
    @Transactional
    def updateAnswer()
    {
        if (!featuresFlippingService.isAnswerPostingEnabled())
        {
            render status: SERVICE_UNAVAILABLE, message: '"error.service.unavailable.post.answer"'
            return
        }

        def inputRequest = request.JSON

        def status = BAD_REQUEST
        String retCode = '"error.answer.edit.wrong.parameters"'

        // Edit answer
        if (inputRequest.message != null && inputRequest.answer != null)
        {
            def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

            Long answerId = -1
            if (inputRequest.answer instanceof String)
            {
                answerId = Long.parseLong(inputRequest.answer)
            }
            else
            {
                answerId = inputRequest.answer
            }

            retCode = answerService.editAnswerToQuestion(answerId, inputRequest.message, user)
            if (retCode == '"success.question.edit.answer"')
            {
                status = OK
            }
        }

        render status: status, message: retCode
    }

    // Default Grails routes
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

    def edit(Answer answer)
    {
        respond answer
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
