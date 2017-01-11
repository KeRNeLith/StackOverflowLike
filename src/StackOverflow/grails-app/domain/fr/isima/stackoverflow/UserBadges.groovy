package fr.isima.stackoverflow

class UserBadges
{
	User user
	Badge badge
	Date dateEarned
	
    static constraints = {
		user unique: ['badge', 'dateEarned']
    }
}
