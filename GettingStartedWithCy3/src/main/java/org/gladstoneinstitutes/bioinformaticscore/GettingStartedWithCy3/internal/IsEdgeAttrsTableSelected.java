package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import com.agilent.AdaptiveJavaHelp.Condition;

public class IsEdgeAttrsTableSelected implements Condition {
    public boolean isMet() {
        return "Edge Attributes".equals(CytoPanelListener.selectedCytoPanel);
    }
}
