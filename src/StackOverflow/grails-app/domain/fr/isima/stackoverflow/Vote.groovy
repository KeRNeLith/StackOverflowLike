package fr.isima.stackoverflow

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
		user unique: ['post']
    }
	
	// Relations
	static belongsTo = [user: User, post: Post]
}
