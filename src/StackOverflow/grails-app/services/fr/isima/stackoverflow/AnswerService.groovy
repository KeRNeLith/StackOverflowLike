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
     * Edit a response.
     * @param answerId Answer to edit.
     * @param message Response message.
     * @param writer User that should be the writer.
     * @return Valid string if answer is created, otherwise list of error codes.
     */
    def editAnswerToQuestion(Long answerId, String message, User writer)
    {
        // Post answer feature not enabled
        if (!featuresFlippingService.isAnswerPostingEnabled())
            return '"error.service.unavailable.post.answer"'

        def retCodes = []
        Answer answer = Answer.get(answerId)
        if (writer != null && answer != null)
        {
            // Trying to edit an answer that the user is not the owner
            if (writer != answer.user)
                return '"error.answer.edit.wrong.writer"'

            answer.message = message
            if (!answer.validate())
            {
                transactionStatus.setRollbackOnly()

                if (answer.errors['message'] != null)
                {
                    if (answer.errors['message'].code == 'maxSize.exceeded')
                        retCodes << '"error.answer.edit.message.tooLong"'
                    else if (answer.errors['message'].code == 'nullable')
                        retCodes << '"error.answer.edit.message.notSet"'
                }
            }
            else
            {
                answer.save()
                retCodes = '"success.question.edit.answer"'
            }
        }
        else
        {
            if (writer == null)
                retCodes << '"error.user.notFound"'

            if (answer == null)
                retCodes << '"error.answer.edit.answer.notFound"'
        }

        return retCodes
    }

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
            return '"error.service.unavailable.post.comment"'

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
