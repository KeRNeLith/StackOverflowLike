package fr.isima.stackoverflow

import grails.transaction.Transactional

@Transactional
class QuestionService
{
    // Services
    def tagService
    def userService
    def badgeService
    def featuresFlippingService

    /**
     * Create a question.
     * @param title Question title.
     * @param message Question message.
     * @param writer Question writer.
     * @param tags Optionals tags
     * @return Created question id, otherwise -1.
     */
    def int createQuestion(String title, String message, User writer, def tags)
    {
        int ret = -1;

        // Post question feature not enabled
        if (!featuresFlippingService.isPostQuestionEnabled())
            return ret

        Question question = new Question(title: title, message: message, user: writer)
        if (question.save(flush: true))
        {
            ret = question.id

            tagService.updateTagsToQuestion(question, tags)

            question.save()

            userService.updateUserReputation(writer, 10)
            badgeService.addFirstQuestionBadge(writer)
        }

        return ret
    }

    /**
     * Mark the input question as resolved.
     * @param question Question to mark
     */
    def resolveQuestion(Question question)
    {
        if (!question.isResolved)
        {
            question.isResolved = true
            question.save(flush: true)
        }
    }

    /**
     * Get most recent questions
     * @param maxResult Max result wanted.
     * @return List of questions.
     */
    def getMostRecentQuestions(Integer maxResult)
    {
        def c = Question.createCriteria()
        def results = c.list (max: maxResult) {
            order("dateCreated", "desc")
        }

        return results.sort { !it.dateCreated }
    }

    /**
     * Get most recent questions for the given tag.
     * @param maxResult Max result wanted.
     * @param tag Question tag.
     * @return List of questions.
     */
    def getQuestionsForTag(Integer maxResult, TagValue tag)
    {
        def c = Tag.createCriteria()
        def results = c.list (max: maxResult) {
            eq("tag", tag)
            question { order("dateCreated", "desc") }
        }

        def questions = []
        results.each { questions.add(it.question) }

        return questions
    }

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
            // Sort comments
            it.comments = it.comments.sort { a, b -> a.dateCreated <=> b.dateCreated }

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

        // Post answer feature not enabled
        if (!featuresFlippingService.isAnswerPostingEnabled())
            return ret

        def question = Question.get(questionId)
        if (writer != null && question != null)
        {
            question.addToAnswers(message: message, question: question, user: writer).save()

            ret = true

            userService.updateUserReputation(writer, 10)
            badgeService.addFirstAnswerBadge(writer)
        }

        return ret
    }
}
