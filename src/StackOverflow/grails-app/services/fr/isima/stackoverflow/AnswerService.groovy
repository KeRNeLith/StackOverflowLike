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
     * @return Valid string if comment is created, otherwise list of error codes.
     */
    def addCommentToAnswer(String message, User writer, Long answerId)
    {
        // Post comment feature not enabled
        if (!featuresFlippingService.isCommentPostingEnabled())
            return '"error.service.unavailable.post.answer"'

        def retCodes = []
        def answer = Answer.get(answerId)
        if (writer != null && answer != null)
        {
            Comment comment = new Comment(message: message, answer: answer, user: writer)
            if (!comment.validate())
            {
                transactionStatus.setRollbackOnly()

                if (comment.errors['message'] != null)
                {
                    if (comment.errors['message'].code == 'maxSize.exceeded')
                        retCodes << '"error.answer.add.comment.message.tooLong"'
                    else if (comment.errors['message'].code == 'nullable')
                        retCodes << '"error.answer.add.comment.message.notSet"'
                }
            }
            else
            {
                answer.addToComments(comment).save()
                retCodes = '"success.answer.add.comment"'

                userService.updateUserReputation(writer, 10)
                badgeService.addFirstCommentBadge(writer)
            }
        }
        else
        {
            if (writer == null)
                retCodes << '"error.user.notFound"'

            if (answer == null)
                retCodes << '"error.answer.add.comment.notAnswer"'
        }

        return retCodes
    }
}
