package fr.isima.stackoverflow

import fr.isima.stackoverflow.User
import fr.isima.stackoverflow.Post

class Vote 
{
	enum Value 
	{
		UP,
		DOWN
	}
	
    // Attributes
	Value vote
	
	// Constraints
    static constraints = {
    }
	
	// Relations
	static belongsTo = [user: User, post: Post]
}
