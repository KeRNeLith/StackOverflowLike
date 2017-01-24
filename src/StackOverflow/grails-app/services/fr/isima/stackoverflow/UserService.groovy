package fr.isima.stackoverflow

import grails.transaction.Transactional

@Transactional
class UserService
{
    /**
     * Create a new user based on input params.
     * @param user User to register.
     */
    def createUser(User user)
    {
        user.save(flush: true)
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
