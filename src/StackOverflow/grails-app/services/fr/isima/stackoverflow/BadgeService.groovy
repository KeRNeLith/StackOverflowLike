package fr.isima.stackoverflow

import grails.transaction.Transactional
import javafx.geometry.Pos

@Transactional
class BadgeService
{
    /**
     * Add the badge if it is the first question of the user.
     * @param user Concerned user.
     */
    def addFirstQuestionBadge(User user)
    {
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
    def addBadgeToUser(Badge badge, User user)
    {
        UserBadges userBadge = new UserBadges(user: user, badge: badge, dateEarned: new Date()).save()
    }
}