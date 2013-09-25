package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import org.osgi.service.event.EventHandler;
import org.osgi.service.event.Event;

public class ImportTableDialogListener implements EventHandler {
    public static String sourceInteractionColumn = null;
    public static String targetInteractionColumn = null;
    public static boolean dialogOpen = false;
    public static boolean dialogOk   = false;

    public void handleEvent(final Event event) {
        final String action = (String) event.getProperty("action");
        if (action == null || action.length() == 0)
            return;
        if (action.equals("set source interaction column"))
            sourceInteractionColumn = (String) event.getProperty("column");
        else if (action.equals("set target interaction column"))
            targetInteractionColumn = (String) event.getProperty("column");
        else if (action.equals("show import table dialog"))
            dialogOpen = true;
        else if (action.equals("import table dialog ok"))
            dialogOk = true;
    }
}
