package fr.isima.stackoverflow

import fr.isima.stackoverflow.Post

class Comment extends Post
{
    // Attributes

	// Constraints
    static constraints = {
		message blank: false, maxSize: 4096
    }
	
	// Relations
	static belongsTo = [answer: Answer]
}
