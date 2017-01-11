package fr.isima.stackoverflow

class TagValue
{
    // Attributes
    String tagName

    // Constraints
    static constraints = {
        tagName unique: true, blank: false, maxSize: 50
    }
}
