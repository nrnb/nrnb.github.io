package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import org.osgi.service.event.EventHandler;
import org.osgi.service.event.Event;

public class ApplyPreferredLayoutTaskListener implements EventHandler {
    public static boolean applyPreferredLayoutTaskInvoked = false;

    public void handleEvent(final Event event) {
        final String action = (String) event.getProperty("action");
        if (action == null || action.length() == 0)
            return;
        if (action.equals("apply preferred layout invoked"))
           applyPreferredLayoutTaskInvoked = true;
    }
}
