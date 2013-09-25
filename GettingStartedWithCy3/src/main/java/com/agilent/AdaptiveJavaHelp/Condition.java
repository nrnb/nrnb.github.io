package com.agilent.AdaptiveJavaHelp;

/**
 * Represents some aspect of the application state
 * in terms of a boolean value. The {@link #isMet} method
 * is periodically invoked, and so the value returned can
 * vary according to the changes in application state.
 */
public interface Condition
{
    /**
     * This method should return quickly, otherwise
     * it can slow the user interface.
     */
    public boolean isMet();
}
