# StackOverflowLike

Web developpement project of a StackOverflow like website.

## Repository achitecture
	- analysis : Conception of the website.
	- doc : Documentation and explanation.
	- src : Source code of the Grails project.
	
# Todo

## UserBadges
Defines **when** a User receives a Badge.
 
------
 
## Expected features

- [ ] The project will offer a system to ask and answer questions.
- [x] An answer can be upvoted. It means it's the more accurate answer for the question. Of course, they can also be downvoted.
- [x] The answers will be ordered by number of votes, this feature should be a matter of presentation.
- [x] Questions can also be voted to reflect their interests in the community.
- [ ] A question can be tagged to appear in categories.
- [ ] A question or an answer can be edited to add some additional information or to fix a typo.
- [ ] A reputation mechanism brings a gamification to the application (The rules have to be defined).
- [ ] Some actions (to be defined) will allow the user to gain some badges (part of the gamification).
- [ ] Users can fill a profile.
- [ ] The profile will sum up all the user's actions (questions asked, answers given, edits, ...) and the earned reputation.
- [ ] The profile will compute the grand total of the user's reputation.
- [x] Admins should act as moderators (Can use the Grails generated CRUD).