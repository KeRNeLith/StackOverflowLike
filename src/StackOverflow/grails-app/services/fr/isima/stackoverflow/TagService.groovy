package fr.isima.stackoverflow

import grails.transaction.Transactional

@Transactional
class TagService
{
    /**
     * Add given list of tags to the question.
     * @param question Question to add or update tags.
     * @param tags Tags.
     * @return True if operation succeed, otherwise false.
     */
    def boolean updateTagsToQuestion(Question question, def tags)
    {
        if (question.tags)
            question.tags.clear()

        tags.each {
            TagValue tagVal = TagValue.findById(it)

            Tag newTag = new Tag(question: question, tag: tagVal)
            if (newTag.save(flush: true))
            {
                question.addToTags(newTag)
            }
        }
    }

    /**
     * Get unique list of tags.
     * @param maxTags Max number of tags used.
     * @return List of tags.
     */
    def getMostRecentTags(Integer maxTags)
    {
        def c = Tag.createCriteria()
        def results = c.list (max: maxTags) {
            distinct("tag")
        }

        return results
    }
}
