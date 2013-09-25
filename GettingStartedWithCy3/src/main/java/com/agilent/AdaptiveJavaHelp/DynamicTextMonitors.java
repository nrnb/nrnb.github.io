package com.agilent.AdaptiveJavaHelp;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.WeakHashMap;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * A singleton class that manages all instances of 
 * {@link DynamicTextMonitor}.
 *
 * <p><i>NOTE:</i> in order to use Adaptive JavaHelp,
 * the {@link #start} method <i>must</i> be invoked before
 * the JavaHelp page is shown.</p>
 */
public class DynamicTextMonitors
{
    /**
     * The units of how often {@link DynamicTextMonitor}s should be updated.
     */
    static final TimeUnit UPDATE_DELAY_UNIT = TimeUnit.SECONDS;

    /**
     * How often {@link DynamicTextMonitor}s should be updated.
     */
    static final long UPDATE_DELAY = 1;

    private DynamicTextMonitors() {}

    /**
     * A set of {@link DynamicTextMonitor}s that are to be informed to be updated.
     * Using a {@link WeakHashMap}, we can simulate a weak set by using {@link #value}
     * as the values for all map entries.
     */
    static final Map<DynamicTextMonitor,Object> monitors = new WeakHashMap<DynamicTextMonitor,Object>();

    /**
     * Used in the {@link #monitors} map as the value in order to simulate a weak set.
     */
    static final Object value = new Object();

    /**
     * A container of regular-expression-based dynamic texts.
     */
    static final Map<String,DynamicText> dynamicTexts = new HashMap<String,DynamicText>();

    static ScheduledExecutorService service = null;

    /**
     * This method <i>must</i> be invoked before displaying JavaHelp pages.
     */
    public static void start()
    {
	if (service != null)
	    return;
	service = Executors.newSingleThreadScheduledExecutor(new InternalThreadFactory());
	service.scheduleAtFixedRate(new ExecuteUpdateMonitors(), UPDATE_DELAY, UPDATE_DELAY, UPDATE_DELAY_UNIT);
    }

    static class InternalThreadFactory implements ThreadFactory
    {
	public Thread newThread(Runnable r)
	{
	    Thread t = new Thread(r, "AdaptiveJavaHelp-DynamicTextMonitorsUpdater");
	    t.setDaemon(true);
	    return t;
	}
    }

    static class ExecuteUpdateMonitors implements Runnable
    {
	public void run()
	{
	    // remove all monitors that aren't shown
	    Iterator<DynamicTextMonitor> iterator = monitors.keySet().iterator();
	    while (iterator.hasNext())
		{
		    DynamicTextMonitor monitor = iterator.next();
		    if (!monitor.isShowing())
			iterator.remove();
		}

	    // go through each monitor and update it
	    for (final DynamicTextMonitor monitor : monitors.keySet())
		monitor.updateHTMLDocument();
	}
    }

    /**
     * This method should not be called directly, as it is
     * called automatically when {@link DynamicTextMonitor}s are
     * constructed.
     */
    public static void register(DynamicTextMonitor monitor)
    {
	monitors.put(monitor, value);
    }

    /**
     * Registers a regular-expression-based dynamic text.
     *
     * <p>Regular-expression-based dynamic texts are used in the true or
     * false texts of conditions. Any of the conditions' texts are modified
     * so that any occurance of {@code regexp} is replaced by
     * the text provided by {@code dynamicText}.</p>
     *
     * <p>For example, let's say a condition has this text:
     * <tt>You have clicked %click-number% number of times</tt>,
     * and {@code register} was called beforehand where
     * {@code regexp = "\\%click-numebr%\\"},
     * then <tt>%click-number%</tt> will be replaced by the text in
     * {@code dynamicText}.
     */
    public static void register(String regexp, DynamicText dynamicText)
    {
	dynamicTexts.put(regexp, dynamicText);
    }

    /**
     * Removes a given regular-expression-based dynamic text.
     */
    public static void deregister(String regexp)
    {
	dynamicTexts.remove(regexp);
    }

    /**
     * (This method does not need to be called directly,
     * as it is automatically called by {@link ConditionMonitor})
     * Fills in regular-expression-based dynamic texts for a given {@code text}.
     */
    public static String fillInDynamicTexts(String text)
    {
	for (final Map.Entry<String,DynamicText> entry : dynamicTexts.entrySet())
	    {
		final String regexp = entry.getKey();
		final DynamicText dynamicText = entry.getValue();
		text = text.replaceAll(regexp, dynamicText.getText());
	    }
	return text;
    }
}
