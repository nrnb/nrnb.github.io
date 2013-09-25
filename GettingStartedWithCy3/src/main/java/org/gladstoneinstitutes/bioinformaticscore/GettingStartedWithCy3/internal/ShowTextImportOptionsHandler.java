package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import org.osgi.service.event.EventHandler;
import org.osgi.service.event.Event;

public class ShowTextImportOptionsHandler implements EventHandler {
    public static boolean didUserClick = false;

    public void handleEvent(final Event event) {
        final String action = (String) event.getProperty("action");
        if (action != null && action.equals("show text file import options"))
            didUserClick = true;
    }
}
