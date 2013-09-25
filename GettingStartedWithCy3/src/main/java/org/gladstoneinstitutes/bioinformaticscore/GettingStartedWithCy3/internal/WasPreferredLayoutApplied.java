package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import com.agilent.AdaptiveJavaHelp.Condition;

public class WasPreferredLayoutApplied implements Condition {
    public WasPreferredLayoutApplied() {
        ApplyPreferredLayoutTaskListener.applyPreferredLayoutTaskInvoked = false;
    }
    public boolean isMet() {
        return ApplyPreferredLayoutTaskListener.applyPreferredLayoutTaskInvoked;
    }
}
