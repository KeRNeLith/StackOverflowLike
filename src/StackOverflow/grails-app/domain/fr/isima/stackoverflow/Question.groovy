package fr.isima.stackoverflow

import fr.isima.stackoverflow.Post

class Question extends Post 
{
	// Attributes
	String title
	Integer nbViews

	// Constraints
    static constraints = {
		title blank: false, maxSize: 150
		nbViews min: 0d
    }
	
	// Relations
	static hasMany = [tags: Tag, answers: Answer]
}
