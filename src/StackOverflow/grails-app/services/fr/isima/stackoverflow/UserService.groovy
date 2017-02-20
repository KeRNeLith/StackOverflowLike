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
     */
    def createUser(User user)
    {
        // Post question feature not enabled
        if (!featuresFlippingService.isSignUpEnabled())
            return null

        user = user.save(flush: true, insert: true)
        if (user != null)
        {
            UserRole.create user, Role.findByAuthority('ROLE_USER')
        }

        return user
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
}
