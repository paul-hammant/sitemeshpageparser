package com.paulhammant.sitemeshpageparser.html;

import com.paulhammant.sitemeshpageparser.html.rules.RegexReplacementTextFilter;

/**
 * TextFilters can be added to the HTMLProcessor (or specific States) and allow a simple means of filtering text content.
 *
 * <p>More than one TextFilter may be added to each HTMLProcessor/State and they will be called in the order they were added.</p>
 *
 * @author Joe Walnes
 *
 * @see HTMLProcessor
 * @see State
 * @see RegexReplacementTextFilter
 */
public interface TextFilter {

    String filter(String content);
    
}
