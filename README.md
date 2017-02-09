# StackOverflowLike

Web developpement project of a StackOverflow like website.

## Repository achitecture
	- analysis : Conception of the website.
	- doc : Documentation and explanation.
	- rendu : Dossier contenant l'archive de rendu du projet.
	- src : Source code of the Grails project.
	- UIDesign : Idée de design utilisables.
	
# Todo
------

## Part 1 
### Expected features

- [x] The project will offer a system to ask and answer questions.
- [x] An answer can be upvoted. It means it's the more accurate answer for the question. Of course, they can also be downvoted.
- [x] The answers will be ordered by number of votes, this feature should be a matter of presentation.
- [x] Questions can also be voted to reflect their interests in the community.
- [x] A question can be tagged to appear in categories.
- [x] A question or an answer can be edited to add some additional information or to fix a typo.
- [x] A reputation mechanism brings a gamification to the application (The rules have to be defined). => when posting and receiving votes
- [x] Some actions (to be defined) will allow the user to gain some badges (part of the gamification). => when posting and receiving positives votes
- [ ] Users can fill a profile.
- [x] The profile will sum up all the user's actions (questions asked, answers given, edits, ...) and the earned reputation.
- [x] The profile will compute the grand total of the user's reputation.
- [x] Admins should act as moderators (Can use the Grails generated CRUD).

## Part 2 
### Expected features

- [ ] Provide Feature flipping capabilities (Partly with Spring Cloud : Config server + ifs)
- [ ] Be Stateless (Grails AngularsJS)
- [x] With an authentication mechanism (spring-security-rest)
- [ ] Embed Health Check for all underlying components
- [x] Implement Circuit Breaker & Graceful degradation with a third party service (seems to be with Spring Cloud)
