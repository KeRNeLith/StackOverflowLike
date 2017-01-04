package fr.isima.stackoverflow

import fr.isima.stackoverflow.Post

class Answer extends Post
{
	// Attributes

	// Constraints
    static constraints = {
    }
	
	// Relations
	static hasMany = [comments: Comment]
	static belongsTo = [question: Question]
}
