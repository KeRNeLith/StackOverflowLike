package fr.isima.stackoverflow

import grails.transaction.Transactional

@Transactional
class CommentService
{
    // Services
    def featuresFlippingService

    /**
     * Edit a comment.
     * @param commentId Comment to edit.
     * @param message Comment message.
     * @param writer User that should be the writer.
     * @return Valid string if comment is created, otherwise list of error codes.
     */
    def editCommentToAnswer(Long commentId, String message, User writer)
    {
        // Post comment feature not enabled
        if (!featuresFlippingService.isCommentPostingEnabled())
            return '"error.service.unavailable.post.comment"'

        def retCodes = []
        Comment comment = Comment.get(commentId)
        if (writer != null && comment != null)
        {
            // Trying to edit a comment that the user is not the owner
            if (writer != comment.user)
                return '"error.comment.edit.wrong.writer"'

            comment.message = message
            if (!comment.validate())
            {
                transactionStatus.setRollbackOnly()

                if (comment.errors['message'] != null)
                {
                    if (comment.errors['message'].code == 'maxSize.exceeded')
                        retCodes << '"error.comment.edit.message.tooLong"'
                    else if (comment.errors['message'].code == 'nullable')
                        retCodes << '"error.comment.edit.message.notSet"'
                }
            }
            else
            {
                comment.save()
                retCodes = '"success.answer.edit.comment"'
            }
        }
        else
        {
            if (writer == null)
                retCodes << '"error.user.notFound"'

            if (comment == null)
                retCodes << '"error.comment.edit.comment.notFound"'
        }

        return retCodes
    }
}
