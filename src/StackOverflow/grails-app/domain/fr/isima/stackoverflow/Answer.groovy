package fr.isima.stackoverflow

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
