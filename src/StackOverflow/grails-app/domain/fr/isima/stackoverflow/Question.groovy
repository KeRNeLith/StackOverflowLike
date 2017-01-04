package fr.isima.stackoverflow

class Question extends Post
{
	// Attributes
	String title
	Integer nbViews

	// Constraints
    static constraints = {
		title blank: false, maxSize: 150
		nbViews min: 0
    }
	
	// Relations
	static hasMany = [tags: Tag, answers: Answer]
}
