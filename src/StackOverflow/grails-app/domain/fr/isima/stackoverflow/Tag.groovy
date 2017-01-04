package fr.isima.stackoverflow

class Tag 
{
	// Attributes
	String tagName

	// Constraints
    static constraints = {
    }
	
	// Relations
	static belongsTo = [question: Question]
}
