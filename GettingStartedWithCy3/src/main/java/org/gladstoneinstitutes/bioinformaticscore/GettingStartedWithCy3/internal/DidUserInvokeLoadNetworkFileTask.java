package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import com.agilent.AdaptiveJavaHelp.Condition;

public class DidUserInvokeLoadNetworkFileTask implements Condition {
    public DidUserInvokeLoadNetworkFileTask() {
        LoadNetworkFileTaskListener.loadNetworkTaskInvoked = false;
    }
    public boolean isMet() {
        return LoadNetworkFileTaskListener.loadNetworkTaskInvoked;
    }
}
