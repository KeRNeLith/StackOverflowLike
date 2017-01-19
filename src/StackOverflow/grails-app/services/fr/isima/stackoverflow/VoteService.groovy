package fr.isima.stackoverflow

import grails.transaction.Transactional

@Transactional
class VoteService
{

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
     */
    def updateVotes(Post post, User user, Vote.Value state)
    {
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
        }
        // Update previous vote only if it is different
        else if (resultVote.vote != state)
        {
            resultVote.vote = state
            resultVote.save()
        }
        // Else do nothing
    }
}
