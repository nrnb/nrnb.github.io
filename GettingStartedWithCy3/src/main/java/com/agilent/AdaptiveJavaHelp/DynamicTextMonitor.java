package com.agilent.AdaptiveJavaHelp;

import java.awt.Component;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.html.HTMLDocument;
import com.sun.java.help.impl.ViewAwareComponent;

/**
 * (This class should not be directly instantiated or called
 * in Java--rather, it should be employed through
 * JavaHelp HTML pages) Monitors changes in the value returned by
 * {@link DynamicText} and updates the JavaHelp page
 * accordingly.
 */
public class DynamicTextMonitor extends Component implements ViewAwareComponent
{
    private HTMLDocument htmlDocument = null;
    protected String errorMsg = null;

    protected DynamicText dynamicText = null;
    protected String lastDynamicText = null;
    private String pID = null;
    private Element pElement = null;

    public DynamicTextMonitor()
    {
	DynamicTextMonitors.register(this);
    }

    /**
     * Called by JavaHelp through the {@code ViewAwareComponent} interface;
     * this gives us access to the DOM representation of the HTML document
     * in JavaHelp.
     */
    public void setViewData(View v)
    {
	htmlDocument = (HTMLDocument) v.getDocument();
	updatePElement();
    }

    public void setDynamicText(String classFqn)
    {
	dynamicText = createInstance(classFqn);
    }

    protected <T> T createInstance(String classFqn)
    {
	errorMsg = null;
	try
	    {
		Class klass = Class.forName(classFqn);
		Object instance = klass.newInstance();
		return (T) instance;
	    }
	catch (Exception e)
	    {
		errorMsg = String.format("<font style=background-color:red color=white>" +
					 "Adaptive JavaHelp: unable to instantiate class <tt>%s</tt>." +
					 "<br><br>%s: %s</font>",
					 classFqn, e.getClass().getName(), e.getMessage());
		return null;
	    }
    }

    public String getDynamicText()
    {
	if (dynamicText == null)
	    return null;
	else
	    return dynamicText.getClass().getName();
    }

    public void setParagraphID(String pID)
    {
	this.pID = pID;
	updatePElement();
    }

    public String getParagraphID()
    {
	return pID;
    }

    /**
     * Called by {@link DynamicTextMonitors} to inform it
     * to update the HTML document.
     */
    public void updateHTMLDocument()
    {
	if (pElement == null)
	    return;
	try
	    {
            if (errorMsg != null)
                htmlDocument.setInnerHTML(pElement, errorMsg);
            if (dynamicText == null)
                return;

            final String currentDynamicText = dynamicText.getText();
            if (lastDynamicText != null && lastDynamicText.equals(currentDynamicText))
                return;

            lastDynamicText = currentDynamicText;
            htmlDocument.setInnerHTML(pElement, currentDynamicText);
	    }
	catch (Exception e) {}
    }

    private void updatePElement()
    {
	if (htmlDocument != null && pID != null)
	    pElement = htmlDocument.getElement(pID);
    }
}
