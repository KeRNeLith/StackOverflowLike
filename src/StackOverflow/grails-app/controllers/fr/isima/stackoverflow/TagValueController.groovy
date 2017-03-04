package fr.isima.stackoverflow

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class TagValueController
{
    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    // Actions
    @Secured('ROLE_ANONYMOUS')
    def list()
    {
        respond tags: TagValue.findAll()
    }

    // Default Grails routes
    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond TagValue.list(params), model:[tagValueCount: TagValue.count()]
    }

    def show(TagValue tagValue)
    {
        respond tagValue
    }

    def create()
    {
        respond new TagValue(params)
    }

    @Transactional
    def save(TagValue tagValue)
    {
        if (tagValue == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tagValue.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond tagValue.errors, view:'create'
            return
        }

        tagValue.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tagValue.label', default: 'TagValue'), tagValue.id])
                redirect tagValue
            }
            '*' { respond tagValue, [status: CREATED] }
        }
    }

    def edit(TagValue tagValue)
    {
        respond tagValue
    }

    @Transactional
    def update(TagValue tagValue)
    {
        if (tagValue == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tagValue.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond tagValue.errors, view:'edit'
            return
        }

        tagValue.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tagValue.label', default: 'TagValue'), tagValue.id])
                redirect tagValue
            }
            '*'{ respond tagValue, [status: OK] }
        }
    }

    @Transactional
    def delete(TagValue tagValue)
    {
        if (tagValue == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        tagValue.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tagValue.label', default: 'TagValue'), tagValue.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tagValue.label', default: 'TagValue'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
