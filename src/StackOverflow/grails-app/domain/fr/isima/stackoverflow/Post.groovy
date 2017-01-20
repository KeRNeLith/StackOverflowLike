package fr.isima.stackoverflow

abstract class Post
{
	// Attributes
	String message
	Date dateCreated
	Date lastUpdated

	// Constraints
    static constraints = {
		message blank: false, maxSize: 65535
    }

	static mapping = {
		autoTimestamp true
	}

	// Relations
	static hasMany = [votes: Vote]
	static belongsTo = [user: User]
}
