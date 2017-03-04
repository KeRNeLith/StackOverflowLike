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
     * @return Created question id, otherwise list of error codes.
     */
    def createQuestion(String title, String message, User writer, def tags)
    {
        // Post question feature not enabled
        if (!featuresFlippingService.isQuestionPostingEnabled())
            return '"error.service.unavailable.post.question"'

        def retCodes = []
        if (writer != null)
        {
            Question question = new Question(title: title, message: message, user: writer)

            // Errors
            if (!question.validate())
            {
                transactionStatus.setRollbackOnly()

                if (question.errors['title'] != null)
                {
                    if (question.errors['title'].code == 'maxSize.exceeded')
                        retCodes << '"error.create.question.title.tooLong"'
                    else if (question.errors['title'].code == 'nullable')
                        retCodes << '"error.create.question.title.notSet"'
                    else if (question.errors['title'].code == 'unique')
                        retCodes << '"error.create.question.title.unique"'
                }

                if (question.errors['message'] != null)
                {
                    if (question.errors['message'].code == 'maxSize.exceeded')
                        retCodes << '"error.create.question.message.tooLong"'
                    else if (question.errors['message'].code == 'nullable')
                        retCodes << '"error.create.question.message.notSet"'
                }
            }
            // Save question
            else
            {
                question.save(flush: true)

                tagService.updateTagsToQuestion(question, tags)

                question.save()

                userService.updateUserReputation(writer, 10)
                badgeService.addFirstQuestionBadge(writer)

                retCodes = question.id
            }
        }
        else
        {
            retCodes << '"error.user.notFound"'
        }

        return retCodes
    }

    /**
     * Mark the input question as resolved.
     * @param questionId Question to mark as resolved.
     * @param user User that has asked to mark question as resolved.
     */
    def resolveQuestion(Long questionId, User user)
    {
        String retCode = '"error.question.notFound"'
        Question question = Question.get(questionId)

        if (question != null)
        {
            // User that mark question as resolved is not the owner
            if (question.user != user)
            {
                retCode = '"error.question.mark.resolved.invalid.user"'
            }
            else if (!question.isResolved)
            {
                question.isResolved = true
                question.save(flush: true)

                retCode = '"success.question.mark.resolved.added"'
            }
            else
            {
                retCode = '"success.question.mark.resolved.notChanged"'
            }
        }
        else
        {
            retCode = '"error.question.notFound"'
        }

        return retCode
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
     * @return List of sorted answers (by votes and dates).
     */
    def sortAnswersByVotes(Question question)
    {
        def sortedAnswers = []

        def answers = question.answers
        answers.each {
            // Sort comments
            it.comments = it.comments.sort { a, b -> a.dateCreated <=> b.dateCreated }

            // Associate answer to it's vote ratio
            sortedAnswers.add([answer: it, votes: voteCount(it.votes)])
        }

        // Sort first on number of votes and after on posting date
        return sortedAnswers.sort { a, b -> b.votes <=> a.votes ?: a.answer.dateCreated <=> b.answer.dateCreated }
    }

    /**
     * Compute the vote count (ratio between positive and negative votes)
     * @param votes List of votes.
     * @return Vote ratio.
     */
    int voteCount(def votes)
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
     * @return Valid string if answer is created, otherwise list of error codes.
     */
    def addAnswerToQuestion(String message, User writer, Long questionId)
    {
        // Post answer feature not enabled
        if (!featuresFlippingService.isAnswerPostingEnabled())
            return '"error.service.unavailable.post.answer"'

        def retCodes = []
        def question = Question.get(questionId)
        if (writer != null && question != null)
        {
            Answer answer = new Answer(message: message, question: question, user: writer)
            if (!answer.validate())
            {
                transactionStatus.setRollbackOnly()

                if (answer.errors['message'] != null)
                {
                    if (answer.errors['message'].code == 'maxSize.exceeded')
                        retCodes << '"error.question.add.answer.message.tooLong"'
                    else if (answer.errors['message'].code == 'nullable')
                        retCodes << '"error.question.add.answer.message.notSet"'
                }
            }
            else
            {
                question.addToAnswers(answer).save()
                retCodes = '"success.question.add.answer"'

                userService.updateUserReputation(writer, 10)
                badgeService.addFirstAnswerBadge(writer)
            }
        }
        else
        {
            if (writer == null)
                retCodes << '"error.user.notFound"'

            if (question == null)
                retCodes << '"error.question.add.answer.notQuestion"'
        }

        return retCodes
    }
}
