package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import com.agilent.AdaptiveJavaHelp.Condition;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTableUtil;

public class AreThereNodesSelected implements Condition {
    public boolean isMet() {
        final CyNetwork net = Services.appMgr.getCurrentNetwork();
        if (net == null)
            return false;
        return CyTableUtil.getNodesInState(net, "selected", true).size() > 0;
    }
}
