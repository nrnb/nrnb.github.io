package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import com.agilent.AdaptiveJavaHelp.Condition;

public class IsThereANetwork implements Condition {
    public boolean isMet() {
        return Services.appMgr.getCurrentNetworkView() != null;
    }
}
