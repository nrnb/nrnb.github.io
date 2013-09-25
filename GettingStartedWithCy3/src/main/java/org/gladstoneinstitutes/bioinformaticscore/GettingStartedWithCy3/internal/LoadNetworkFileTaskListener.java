package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import org.osgi.service.event.EventHandler;
import org.osgi.service.event.Event;

public class LoadNetworkFileTaskListener implements EventHandler {
    public static boolean loadNetworkTaskInvoked = false;

    public void handleEvent(final Event event) {
        final String action = (String) event.getProperty("action");
        if (action == null || action.length() == 0)
            return;
        if (action.equals("network file task invoked"))
            loadNetworkTaskInvoked = true;
    }
}
