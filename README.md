# StackOverflowLike

Web developpement project of a StackOverflow like website.

## Repository achitecture
	- analysis : Conception of the website.
	- doc : Documentation and explanation.
	- src : Source code of the Grails project.
	
# Todo
======
# Answer
------
Needs a Question (understand post) to be created but does not list any
# Badge
------
Can create two badges with the same name
# Comment
------
Currently needs a reference to an Answer to be created (Answer currently cannot be created)
# Vote
------
A Post has currently no title
There is no way to add an Answer on a post
Can currently be voted multiple times by the same user
A user can create a post with the same message multiple times (be careful when adding Title)

# Question
------
**WTF IS THIS?**
Does nothing, to be deleted. Currently raises an Error 
Error 500: Internal Server Error
  
 To be replaced by Post
 
# Tag
------
Currently Cannot be created because it needs a Question as a reference **wrong**
A ~~Question~~ Post needs a list of Tags.
A Tag should be able to be created independently (Enum)
Two tags with the same name should not exist

# UserBadges
------
 Defines **when** a User receives a Badge.
 Currently **a user** can receive **the same badge** at **the same time** multiple times 

# USER
------
A certain checkbox "enable" (to true)   don't know what it means (without looking at the code)
Currently can add vote on a user? - even during the creation(redirects to the vote creation)
HOWEVER, from a user profile, one can see all the votes he has casted, click on it and go to the vote description page (sees vote details)
Currently can edit the Reputation during the creation and add negative values

# Vote
------