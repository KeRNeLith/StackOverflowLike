package fr.isima.stackoverflow

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class CommentController
{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    // Services
    def springSecurityService
    def answerService

    // Actions
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond Comment.list(params), model:[commentCount: Comment.count()]
    }

    def show(Comment comment)
    {
        respond comment
    }

    def create()
    {
        respond new Comment(params)
    }

    @Secured('ROLE_USER')
    def redactEdit(Comment comment)
    {
        respond comment, view: 'redactEdit'
    }

    @Transactional
    def save(Comment comment)
    {
        if (comment == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comment.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond comment.errors, view:'create'
            return
        }

        comment.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect comment
            }
            '*' { respond comment, [status: CREATED] }
        }
    }

    @Secured('ROLE_USER')
    @Transactional
    def saveComment()
    {
        def inputRequest = request.JSON

        def status = BAD_REQUEST
        String retCode = '"error.answer.add.comment.wrong.parameters"'
        // Add comment
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

            retCode = answerService.addCommentToAnswer(inputRequest.message, user, answerId)
            if (retCode == '"success.answer.add.comment"')
            {
                status = CREATED
            }
        }

        render status: status, message: retCode
    }

    def edit(Comment comment)
    {
        respond comment
    }

    @Transactional
    def update(Comment comment)
    {
        if (comment == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comment.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond comment.errors, view:'edit'
            return
        }

        comment.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect comment
            }
            '*'{ respond comment, [status: OK] }
        }
    }

    @Secured('ROLE_USER')
    @Transactional
    def updateEdit(Comment comment)
    {
        if (comment == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comment.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond comment.errors, view:'redactEdit'
            return
        }

        comment.save flush:true

        redirect(controller: 'question', action: 'display', id: comment.answer.question.id)
    }

    @Transactional
    def delete(Comment comment)
    {
        if (comment == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        comment.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), comment.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
