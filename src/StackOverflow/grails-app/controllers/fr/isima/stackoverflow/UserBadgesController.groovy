package fr.isima.stackoverflow

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class UserBadgesController
{
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max)
    {
        params.max = Math.min(max ?: 10, 100)
        respond UserBadges.list(params), model:[userBadgesCount: UserBadges.count()]
    }

    def show(UserBadges userBadges)
    {
        respond userBadges
    }

    def create()
    {
        respond new UserBadges(params)
    }

    @Transactional
    def save(UserBadges userBadges)
    {
        if (userBadges == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (userBadges.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond userBadges.errors, view:'create'
            return
        }

        userBadges.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userBadges.label', default: 'UserBadges'), userBadges.id])
                redirect userBadges
            }
            '*' { respond userBadges, [status: CREATED] }
        }
    }

    def edit(UserBadges userBadges)
    {
        respond userBadges
    }

    @Transactional
    def update(UserBadges userBadges)
    {
        if (userBadges == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (userBadges.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond userBadges.errors, view:'edit'
            return
        }

        userBadges.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'userBadges.label', default: 'UserBadges'), userBadges.id])
                redirect userBadges
            }
            '*'{ respond userBadges, [status: OK] }
        }
    }

    @Transactional
    def delete(UserBadges userBadges)
    {

        if (userBadges == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        userBadges.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'userBadges.label', default: 'UserBadges'), userBadges.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userBadges.label', default: 'UserBadges'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
