package edu.ub.tfc.recommender.servlet;

public enum GroupLength {
	all ("%"),
	length_3 ("3"),
	length_5 ("5"),
	length_7 ("7");

	/* ****************************
			ATTRIBUTES
	 * *************************** */
	
	private final String name;

	/* ****************************
			PUBLIC METHODS
	 * *************************** */
	
    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }

	/* ****************************
		   PRIVATE CONSTRUCTORS
	 * *************************** */
	
    private GroupLength(String s) {
        name = s;
    }
}