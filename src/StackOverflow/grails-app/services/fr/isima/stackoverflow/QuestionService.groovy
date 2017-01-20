package fr.isima.stackoverflow

import grails.transaction.Transactional

@Transactional
class QuestionService
{
    /**
     * Use question answers and sort them. Additionally compute their number of votes (positive plus negative)
     * @param id Question id
     */
    def sortAnswersByVotes(Long id)
    {
        def question = Question.read(id)

        sortAnswersByVotes(question)
    }

    /**
     * Use question answers and sort them. Additionally compute their number of votes (positive plus negative)
     * @param id Question.
     */
    def sortAnswersByVotes(Question question)
    {
        def sortedAnswers = [:]

        def answers = question.answers
        answers.each {
            // Associate answer to it's vote ratio
            sortedAnswers.put(it, voteCount(it.votes))
        }

        // Sort first on number of votes and after on posting date
        return sortedAnswers.sort { a, b -> b.value <=> a.value ?: a.key.dateCreated <=> b.key.dateCreated }
    }

    /**
     * Compute the vote count (ratio between positive and negative votes)
     * @param votes List of votes.
     * @return Vote ratio.
     */
    def int voteCount(def votes)
    {
        def nbPositives = votes.count { it.vote == Vote.Value.UP }
        def nbNegatives = - (votes.size() - nbPositives)

        return nbPositives + nbNegatives
    }

    /**
     * Add a response to the given question.
     * @param message Response message.
     * @param writer User that write answer.
     * @param questionId Question id.
     * @return true if succeed.
     */
    def addAnswerToQuestion(String message, User writer, Long questionId)
    {
        def ret = false

        def question = Question.get(questionId)
        if (writer != null && question != null)
        {
            question.addToAnswers(message: message, question: question, user: writer).save()

            ret = true
        }

        return ret
    }
}
