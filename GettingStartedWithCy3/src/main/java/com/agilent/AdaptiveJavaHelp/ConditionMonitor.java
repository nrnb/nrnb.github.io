package com.agilent.AdaptiveJavaHelp;

/**
 * (This class should not be directly instantiated or called
 * in Java--rather, it should be employed through
 * JavaHelp HTML pages) Monitors changes in the value returned by
 * {@link Condition} and updates the JavaHelp page
 * accordingly.
 */
public class ConditionMonitor extends DynamicTextMonitor
{
    Condition condition = null;
    String trueText = null;
    String falseText = null;

    public ConditionMonitor()
    {
	super.dynamicText = new InternalDynamicText();
    }

    public void setCondition(String classFqn)
    {
	condition = super.createInstance(classFqn);
    }

    public String getCondition()
    {
	if (condition == null)
	    return null;
	else
	    return condition.getClass().getName();
    }

    public void setTrue(String trueText)
    {
	this.trueText = trueText;
    }

    public String getTrue()
    {
	return trueText;
    }

    public void setFalse(String falseText)
    {
	this.falseText = falseText;
    }

    public String getFalse()
    {
	return falseText;
    }

    class InternalDynamicText implements DynamicText
    {
	public String getText()
	{
	    if (condition == null)
		return null;
	    String text = condition.isMet() ? trueText : falseText;
	    if (text == null)
		return "";
	    return DynamicTextMonitors.fillInDynamicTexts(text);
	}
    }
}
