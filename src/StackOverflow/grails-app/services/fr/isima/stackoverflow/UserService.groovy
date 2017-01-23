package fr.isima.stackoverflow

import grails.transaction.Transactional

@Transactional
class UserService
{
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
