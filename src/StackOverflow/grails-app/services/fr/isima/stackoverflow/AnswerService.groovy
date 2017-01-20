package fr.isima.stackoverflow

import grails.transaction.Transactional

@Transactional
class AnswerService
{
    /**
     * Add a comment to the given answer.
     * @param message Comment message.
     * @param writer User that write comment.
     * @param answerId Answer id.
     * @return true if succeed.
     */
    def addCommentToAnswer(String message, User writer, Long answerId)
    {
        def ret = false

        def answer = Answer.get(answerId)
        if (writer != null && answer != null)
        {
            answer.addToComments(message: message, answer: answer, user: writer).save()

            ret = true
        }

        return ret
    }
}
