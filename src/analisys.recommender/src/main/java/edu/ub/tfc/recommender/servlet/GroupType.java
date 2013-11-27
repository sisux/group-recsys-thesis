package edu.ub.tfc.recommender.servlet;

public enum GroupType {
	all ("*"),
	similar ("S"),
	dissimilar ("D");
	    
	private final String name;       

    private GroupType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }

}