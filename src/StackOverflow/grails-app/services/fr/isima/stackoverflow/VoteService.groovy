package fr.isima.stackoverflow

import grails.transaction.Transactional

@Transactional
class VoteService
{
    // Services
    def userService
    def badgeService
    def featuresFlippingService

    /**
     * Add of update the vote for the specified user for the given post
     * @param postId Post id of the targeted post.
     * @param userId User id of the user that vote.
     * @param state Vote state (UP or DOWN).
     */
    def updateVotes(Long postId, Long userId, Vote.Value state)
    {
        def post = Post.get(postId)
        def user = User.read(userId)

        updateVotes(post, user, state)
    }

    /**
     * Add of update the vote for the specified user for the given post
     * @param post Post to vote.
     * @param user User that vote.
     * @param state Vote state (UP or DOWN).
     * @return 0 = no changes, 1 = positive vote added, -1 = negative vote added, 2 = modified.
     */
    def updateVotes(Post post, User user, Vote.Value state)
    {
        int ret = 0 // No changes

        // Vote feature not enabled
        if (!featuresFlippingService.isVotesEnabled())
            return ret

        def alreadyVotes = Vote.createCriteria()
        def resultVote = alreadyVotes.get {
            eq('post', post)
            eq('user', user)
        }

        // No vote for this post
        if (resultVote == null)
        {
            post.addToVotes(user: user, vote: state)
            post.save()

            // Up post's writer reputation
            if (state == Vote.Value.UP)
            {
                ret = 1
                userService.updateUserReputation(post.user, 10)
                badgeService.checkThumbUpBadge(post)
            }
            // Down post's writer reputation
            else
            {
                ret = -1
                userService.updateUserReputation(post.user, -10)
            }
        }
        // Update previous vote only if it is different
        else if (resultVote.vote != state)
        {
            resultVote.vote = state
            resultVote.save()
            ret = 2

            // Change to a thumb up
            if (state == Vote.Value.UP)
            {
                badgeService.checkThumbUpBadge(post)
            }
        }
        // Else do nothing

        return ret
    }
}
