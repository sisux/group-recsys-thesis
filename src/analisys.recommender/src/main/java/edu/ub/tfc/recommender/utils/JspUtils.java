package edu.ub.tfc.recommender.utils;

public class JspUtils {

	public static String getStringForHtmlCell(String theBase) {
		if(theBase == null || theBase.isEmpty()) {
			return String.valueOf(Character.SPACE_SEPARATOR);
		}
	return theBase;
	}
}
