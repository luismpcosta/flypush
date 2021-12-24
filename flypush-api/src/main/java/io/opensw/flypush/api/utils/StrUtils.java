package io.opensw.flypush.api.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;

/**
 * The Interface StrUtils.
 */
public interface StrUtils {

	/** The stop words. */
	String STOP_WORDS = "sim|nao";
	
	/**
	 * Add character to string .
	 *
	 * @param str to add char
	 * @param ch char to add
	 * @param position of char
	 * @return string changed
	 */
	static String addChar(final String str, final String ch, final int position) {
	    final StringBuilder sb = new StringBuilder(str);
	    sb.insert(position, ch);
	    return sb.toString();
	}
	
	
	/**
	 * Capitalize string - transform in camel case.
	 *
	 * @param str the str
	 * @return string in camel case
	 */
	static String capitalize( final String str ) {
		if (str == null || str.isEmpty() ) {
	        return str;
	    }
	    return str.substring(0, 1).toUpperCase().concat( str.substring(1).toLowerCase() );
	}
	
	/**
	 * Transform string in snake case convention.
	 *
	 * @param str the str
	 * @return string in snake case
	 */
	static String toSnakeCase( final String str ) {
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str).replaceAll( " ", " " ).toLowerCase();
	}
	
	/**
	 * remove special characters (lower case).
	 *
	 * @param text the text
	 * @return string withouth special characters (in lower case)
	 */
	static String removeAccentsAndSpecialCharsToLower(final String text) {
	    return text == null ? null :
	        Normalizer.normalize(text, Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase().trim();
	}

	/**
	 * remove special characters (upper case).
	 *
	 * @param text the text
	 * @return string without special characters (in upper case)
	 */
	static String removeAccentsAndSpecialCharsToUpper(final String text) {
		return text == null ? null :
			Normalizer.normalize(text, Form.NFD)
			.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toUpperCase().trim();
	}
	
	/**
	 * remove letters/characters from string.
	 *
	 * @param text the text
	 * @return numeric string
	 */
	static String removeLetters(final String text) {
 	    return text == null ? null :
	        Normalizer.normalize(text, Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").replaceAll("[^0-9 -]", "").trim();
	}
	
	/**
	 * remove special characters and stop words.
	 *
	 * @param text the text
	 * @return string without special characters and stop words
	 */
	static String removeAccentsAndStopWordsToLower(final String text) {
 	    return text == null ? null :
	        Normalizer.normalize(text, Form.NFD)
	            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "").replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().replaceAll("(" + STOP_WORDS + ")\\s*", " ").trim();
	}
	
	/**
	 * Process tags and remove special chars and underscore.
	 *
	 * @param str the str
	 * @return tag in corret format
	 */
	static String tagsArrange( String str ) {
		str = StrUtils.removeAccentsAndSpecialCharsToLower( str );
		str = StringUtils.stripAccents( str );
		return str.toLowerCase().replaceAll( " ", "_" );
	}

	/**
	 * add space between letters and numbers.
	 *
	 * @param str - string to process
	 * @return processed string
	 */
	static String addSpaceBetweenNumbersAndLetters( final String str ) {

		return str.replaceAll( "(?<=[A-Za-z])(?=[0-9])|(?<=[0-9])(?=[A-Za-z])", " " ).trim();
	}

}
