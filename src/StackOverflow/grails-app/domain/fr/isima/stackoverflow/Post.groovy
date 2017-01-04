package fr.isima.stackoverflow

class Post 
{
	// Attributes
	String message
	Date postedDate
	Date lastModified

	// Constraints
    static constraints = {
		message blank: false, maxSize: 65535
    }
	
	// Relations
	static hasMany = [votes: Vote]
	static belongsTo = [user: User]
}
