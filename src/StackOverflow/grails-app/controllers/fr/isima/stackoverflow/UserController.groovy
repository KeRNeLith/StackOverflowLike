package fr.isima.stackoverflow

import fr.isima.marshallers.UserMarshallers
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class UserController
{
    static responseFormats = ['json']
    static allowedMethods = [register: "POST", updateProfile: "PUT",
                             // Default grails methods
                             save: "POST", update: "PUT", delete: "DELETE"]

    // Services
    def badgeService
    def userService
    def springSecurityService
    def featuresFlippingService

    // Actions
    @Secured('ROLE_ANONYMOUS')
    def profile()
    {
        String username = params.username
        User user = User.findByUsername(username)

        JSON.use(UserMarshallers.MEDIUM_USER_INFO) {
            respond user: user, badges: badgeService.getUserBadgesSorted(badgeService.getUserBadges(user))
        }
    }

    @Secured('ROLE_ANONYMOUS')
    @Transactional
    def register()
    {
        if (!featuresFlippingService.isSignUpEnabled())
        {
            render status: SERVICE_UNAVAILABLE, message: '"error.service.unavailable.signUp"'
            return
        }

        User user = new User(request.JSON)

        def retCode = userService.createUser(user)

        def status = BAD_REQUEST
        // If succeed
        if (retCode == '"success.register.user"')
        {
            status = CREATED
        }

        render status: status, message: retCode
    }

    @Secured('ROLE_ANONYMOUS')
    def available()
    {
        if (!featuresFlippingService.isSignUpEnabled())
        {
            render status: SERVICE_UNAVAILABLE, message: '"error.service.unavailable.signUp"'
            return
        }

        String username = params.username
        User user = User.findByUsername(username)

        def status = NOT_ACCEPTABLE
        // If succeed
        if (user == null)
        {
            status = OK
        }

        render status: status
    }

    @Secured('ROLE_USER')
    @Transactional
    def updateProfile()
    {
        if (!featuresFlippingService.isEditProfileEnabled())
        {
            render status: SERVICE_UNAVAILABLE, message: '"error.service.unavailable.profile.edit"'
            return
        }

        def inputRequest = request.JSON

        def status = BAD_REQUEST
        String retCode = '"error.user.profile.edit.wrong.parameters"'

        // Edit user description
        if (inputRequest.description != null)
        {
            def user = springSecurityService.isLoggedIn() ? springSecurityService.currentUser : null

            retCode = userService.editProfileDescription(inputRequest.description, user)
            if (retCode == '"success.user.edit.profile"')
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
        respond User.list(params), model:[userCount: User.count()]
    }

    def show(User user)
    {
        respond user
    }

    def create()
    {
        respond new User(params)
    }

    @Transactional
    def save(User user)
    {
        if (user == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'create'
            return
        }

        user.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    def edit(User user)
    {
        respond user
    }

    @Transactional
    def update(User user)
    {
        if (user == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors())
        {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'edit'
            return
        }

        user.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ respond user, [status: OK] }
        }
    }

    @Transactional
    def delete(User user)
    {
        if (user == null)
        {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        user.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound()
    {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
