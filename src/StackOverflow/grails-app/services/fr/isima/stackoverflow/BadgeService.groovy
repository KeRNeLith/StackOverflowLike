package fr.isima.stackoverflow

import grails.transaction.Transactional

@Transactional
class BadgeService
{
    def featuresFlippingService

    /**
     * Add the badge if it is the first question of the user.
     * @param user Concerned user.
     */
    def addFirstQuestionBadge(User user)
    {
        // Badges feature not enabled
        if (!featuresFlippingService.isBadgesEnabled())
            return

        def c = Question.createCriteria()
        def result = c.count {
            eq('user', user)
        }

        if (result == 1)
        {
            addBadgeToUser(Badge.findById(1), user)
        }
    }

    /**
     * Add the badge if it is the first answer of the user.
     * @param user Concerned user.
     */
    def addFirstAnswerBadge(User user)
    {
        // Badges feature not enabled
        if (!featuresFlippingService.isBadgesEnabled())
            return

        def c = Answer.createCriteria()
        def result = c.count {
            eq('user', user)
        }

        if (result == 1)
        {
            addBadgeToUser(Badge.findById(2), user)
        }
    }

    /**
     * Add the badge if it is the first comment of the user.
     * @param user Concerned user.
     */
    def addFirstCommentBadge(User user)
    {
        // Badges feature not enabled
        if (!featuresFlippingService.isBadgesEnabled())
            return

        def c = Comment.createCriteria()
        def result = c.count {
            eq('user', user)
        }

        if (result == 1)
        {
            addBadgeToUser(Badge.findById(3), user)
        }
    }

    /**
     * Check if the user can gain a badge linked to votes
     * @param post Post on which the vote has been put.
     */
    def checkThumbUpBadge(Post post)
    {
        // Badges feature not enabled
        if (!featuresFlippingService.isBadgesEnabled())
            return

        def thumbUpCount = 0
        post.votes.each {
            if (it.vote == Vote.Value.UP)
            {
                ++thumbUpCount
            }
        }

        if (thumbUpCount >= 50)
        {
            addBadgeToUser(Badge.findById(6), post.user)
        }
        else if (thumbUpCount >= 10)
        {
            addBadgeToUser(Badge.findById(5), post.user)
        }
        else if (thumbUpCount == 1)
        {
            addBadgeToUser(Badge.findById(4), post.user)
        }
    }

    /**
     * Add the given badge to the given user.
     * @param badge Badge to give.
     * @param user User that will receive the badge.
     */
    private def addBadgeToUser(Badge badge, User user)
    {
        UserBadges userBadge = new UserBadges(user: user, badge: badge, dateEarned: new Date()).save()
    }

    /**
     * Get all badges related to given user.
     * @param user User.
     */
    def getUserBadges(User user)
    {
        // Badges feature not enabled
        if (!featuresFlippingService.isBadgesEnabled())
            return []

        def c = UserBadges.createCriteria()
        def results = c.list {
            eq('user', user)
            projections {
                property('badge')
            }
        }
    }

    /**
     * Get all badges related as a map associating badge to the number of this same badge obtained.
     * @param List of badges.
     */
    def getUserBadgesSorted(def badges)
    {
        def badgesAssoc = [:]
        badges.each {
            if (badgesAssoc.containsKey(it))
            {
                ++badgesAssoc[it]
            }
            else
            {
                badgesAssoc.put(it, 1)
            }
        }

        def badgesList = []
        badgesAssoc.each {
            badgesList.add([badge: it.key, number: it.value])
        }

        return badgesList
    }
}
