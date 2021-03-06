package edu.ub.tfc.recommender.servlet;

/*
 * Type of selectable group to perform the GRecSys 
 */
public enum GroupType {
	all ("%"),
	similar ("S"),
	dissimilar ("D");

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
	
    private GroupType(String s) {
        name = s;
    }
}