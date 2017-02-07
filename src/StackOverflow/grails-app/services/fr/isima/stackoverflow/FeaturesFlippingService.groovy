package fr.isima.stackoverflow

import grails.transaction.Transactional
import org.springframework.beans.factory.annotation.Value

@Transactional
class FeaturesFlippingService
{
    @Value('${features.posting.answer.enabled}')
    private boolean postAnswerEnabled;

    /**
     * Posting answers feature enabled.
     * @return True if enabled.
     */
    def boolean isAnswerPostingEnabled()
    {
        return postAnswerEnabled
    }

    @Value('${features.posting.comment.enabled}')
    private boolean postCommentEnabled;

    /**
     * Posting comments feature enabled.
     * @return True if enabled.
     */
    def boolean isCommentPostingEnabled()
    {
        return postCommentEnabled
    }

    @Value('${features.signup.enabled}')
    private boolean signUpEnabled;

    /**
     * Signing up feature enabled.
     * @return True if enabled.
     */
    def boolean isSignUpEnabled()
    {
        return signUpEnabled
    }

    @Value('${features.signin.enabled}')
    private boolean signInEnabled;

    /**
     * Signing in feature enabled.
     * @return True if enabled.
     */
    def boolean isSignInEnabled()
    {
        return signInEnabled
    }

    @Value('${features.question.ask.enabled}')
    private boolean postQuestionEnabled;

    /**
     * Posting question feature enabled.
     * @return True if enabled.
     */
    def boolean isPostQuestionEnabled()
    {
        return postQuestionEnabled
    }

    @Value('${features.profile.edit.enabled}')
    private boolean editProfileEnabled;

    /**
     * Edit user profile feature enabled.
     * @return True if enabled.
     */
    def boolean isEditProfileEnabled()
    {
        return editProfileEnabled
    }

    @Value('${features.badges.enabled}')
    private boolean badgesEnabled;

    /**
     * Badges feature enabled.
     * @return True if enabled.
     */
    def boolean isBadgesEnabled()
    {
        return badgesEnabled
    }

    @Value('${features.votes.enabled}')
    private boolean votesEnabled;

    /**
     * Badges feature enabled.
     * @return True if enabled.
     */
    def boolean isVotesEnabled()
    {
        return votesEnabled
    }
}
