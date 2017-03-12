package fr.isima.stackoverflow

import fr.isima.marshallers.CommentsMarshallers
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class CommentController
{
    static responseFormats = ['json']
    static allowedMethods = [saveComment: "POST", updateComment: "PUT",
                             // Default grails methods
                             save: "POST", update: "PUT", delete: "DELETE"]

    // Services
    def springSecurityService
    def answerService
    def commentService
    def featuresFlippingService

    // Actions
    @Secured('ROLE_USER')
    def redactEdit(Comment comment)
    {
        JSON.use(CommentsMarshallers.LIGHT_COMMENT) {
            respond comment
        }
    }

    @Secured('ROLE_USER')
    @Transactional
    def saveComment()
    {
        if (!featuresFlippingService.isCommentPostingEnabled())
        {
            render status: SERVICE_UNAVAILABLE, message: '"error.service.unavailable.post.comment"'
            return
        }

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

    @Secured('ROLE_USER')
    @Transactional
    def updateComment()
    {
        if (!featuresFlippingService.isCommentPostingEnabled())
        {
            render status: SERVICE_UNAVAILABLE, message: '"error.service.unavailable.post.comment"'
            return
        }

        def inputRequest = request.JSON

        def status = BAD_REQUEST
        String retCode = '"error.comment.edit.wrong.parameters"'

        // Edit comment
        if (inputRequest.message != null && inputRequest.comment != null)
        {
            def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

            Long commentId = -1
            if (inputRequest.comment instanceof String)
            {
                commentId = Long.parseLong(inputRequest.comment)
            }
            else
            {
                commentId = inputRequest.comment
            }

            retCode = commentService.editCommentToAnswer(commentId, inputRequest.message, user)
            if (retCode == '"success.answer.edit.comment"')
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
