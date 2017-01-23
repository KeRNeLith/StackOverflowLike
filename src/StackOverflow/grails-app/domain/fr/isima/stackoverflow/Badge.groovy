package fr.isima.stackoverflow

class Badge
{
	enum Rank
	{
		GOLD,
		SILVER,
		BRONZE
	}

    // Attributes
	String name
	Rank rank

	// Constraints
    static constraints = {
		name blank: false, maxSize: 50, unique: ['rank']
    }
}
