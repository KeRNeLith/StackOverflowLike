package fr.isima.stackoverflow

import com.sun.org.apache.xpath.internal.operations.Bool
import stackoverflow.BootStrap

class Question extends Post
{
	// Attributes
	String title
	Integer nbViews = 0
	Boolean isResolved = false

	// Constraints
    static constraints = {
		title blank: false, maxSize: 150, unique: 'user'
		nbViews min: 0
    }

	// Relations
	static hasMany = [tags: Tag, answers: Answer]

	static mapping = {
		tags cascade: "all-delete-orphan"
	}
}
