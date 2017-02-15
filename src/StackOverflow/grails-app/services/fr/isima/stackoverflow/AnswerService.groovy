package fr.isima.stackoverflow

import grails.transaction.Transactional

@Transactional
class AnswerService
{
    // Services
    def userService
    def badgeService
    def featuresFlippingService

    /**
     * Add a comment to the given answer.
     * @param message Comment message.
     * @param writer User that write comment.
     * @param answerId Answer id.
     * @return true if succeed.
     */
    boolean addCommentToAnswer(String message, User writer, Long answerId)
    {
        boolean ret = false

        // Post comment feature not enabled
        if (!featuresFlippingService.isCommentPostingEnabled())
            return ret

        def answer = Answer.get(answerId)
        if (writer != null && answer != null)
        {
            answer.addToComments(message: message, answer: answer, user: writer).save()

            ret = true

            userService.updateUserReputation(writer, 10)
            badgeService.addFirstCommentBadge(writer)
        }

        return ret
    }
}
