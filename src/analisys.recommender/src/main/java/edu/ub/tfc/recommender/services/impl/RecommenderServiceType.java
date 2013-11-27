package edu.ub.tfc.recommender.services.impl;

public enum RecommenderServiceType {

	GROUP_EUCLIDEAN_SERVICE("itemCosineService"),
	GROUP_AVERAGE_SERVICE("itemPearsonService");
	
	private final String name;       

    private RecommenderServiceType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }

}
