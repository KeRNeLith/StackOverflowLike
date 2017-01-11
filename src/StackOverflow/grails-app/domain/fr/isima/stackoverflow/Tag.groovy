package fr.isima.stackoverflow

class Tag 
{
	// Attributes
	TagValue tag

	// Constraints
    static constraints = {
    }

    static belongsTo = [questions: Question]
}
