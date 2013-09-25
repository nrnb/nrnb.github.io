package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.application.swing.events.CytoPanelComponentSelectedListener;
import org.cytoscape.application.swing.events.CytoPanelComponentSelectedEvent;
import java.awt.Component;

public class CytoPanelListener implements CytoPanelComponentSelectedListener {
    public static String selectedCytoPanel = null;

    public void handleEvent(final CytoPanelComponentSelectedEvent e) {
        final int i = e.getSelectedIndex();
        if (!e.getCytoPanel().getCytoPanelName().equals(CytoPanelName.SOUTH))
            return;
        final Component c = e.getCytoPanel().getComponentAt(i);
        if (c instanceof CytoPanelComponent) {
            final CytoPanelComponent panel = (CytoPanelComponent) c;
            selectedCytoPanel = panel.getTitle();
        } else {
            selectedCytoPanel = null;
        }
    }
}
