package com.agilent.AdaptiveJavaHelp;

/**
 * Represents an object that provides
 * some text to be displayed in
 * a JavaHelp page. Typically the text returned
 * is a description of some aspect of the application's
 * varying state. The {@link #getText} method
 * is periodically invoked, and the JavaHelp page is updated
 * accordingly. Thus the text returned by this object
 * can change in reflection of the changing application state.
 */
public interface DynamicText
{
    /**
     * This method should return quickly, otherwise
     * it can slow the user interface.
     */
    public String getText();
}
