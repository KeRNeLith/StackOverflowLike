package fr.isima.stackoverflow

import grails.transaction.Transactional

@Transactional
class UserService
{
    // Services
    def featuresFlippingService

    /**
     * Create a new user based on input params.
     * @param user User to register.
     * @return Valid string if user is created, otherwise list of error codes.
     */
    def createUser(User user)
    {
        // Sign up feature not enabled
        if (!featuresFlippingService.isSignUpEnabled())
            return [ '"error.service.unavailable.signUp"' ]

        if (user == null)
        {
            transactionStatus.setRollbackOnly()
            return [ '"error.register.impossible"' ]
        }

        user.validate()
        if (user.hasErrors())
        {
            transactionStatus.setRollbackOnly()

            def errors = []
            // Check username
            if (user.errors['username'] != null)
            {
                if (user.errors['username'].code == 'minSize.notmet')
                    errors << '"error.register.invalid.username.tooSmall"'
                else if (user.errors['username'].code == 'maxSize.exceeded')
                    errors << '"error.register.invalid.username.tooLong"'
                else if (user.errors['username'].code == 'nullable')
                    errors << '"error.register.invalid.username.notSet"'
                else if (user.errors['username'].code == 'unique')
                    errors << '"error.register.invalid.user.alreadyExists"'
            }

            // Check password
            if (user.errors['password'] != null)
                errors << '"error.register.invalid.password.incorrect"'

            return errors
        }

        user = user.save(flush: true, insert: true)
        if (user != null)
        {
            UserRole.create user, Role.findByAuthority('ROLE_USER')
            return '"success.register.user"'
        }
        // Should never arrive here
        else
        {
            return [ '"error.register.invalid.user.alreadyExists"' ]
        }
    }

    /**
     * Update user reputation.
     * @param user User reputation.
     * @param change Value changes.
     */
    def updateUserReputation(User user, Integer change)
    {
        user.reputation = Math.max(0, user.reputation + change)
        user.save()
    }

    /**
     * Change user description.
     * @param newDesc New description.
     * @param user User to update.
     * @return Valid string if description is edited, otherwise list of error codes.
     */
    def editProfileDescription(String newDesc, User user)
    {
        // Edit user profile feature not enabled
        if (!featuresFlippingService.isEditProfileEnabled())
            return '"error.service.unavailable.edit.profile"'

        def retCodes = []
        if (user != null)
        {
            user.description = newDesc
            println user.description.size()
            if (!user.validate())
            {
                transactionStatus.setRollbackOnly()

                if (user.errors['description'] != null)
                {
                    if (user.errors['description'].code == 'maxSize.exceeded')
                        retCodes << '"error.user.edit.profile.description.tooLong"'
                }
            }
            else
            {
                user.save()
                retCodes = '"success.user.edit.profile"'
            }
        }
        else
        {
            retCodes << '"error.user.notFound"'
        }

        return retCodes
    }
}
