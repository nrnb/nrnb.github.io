package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import com.agilent.AdaptiveJavaHelp.Condition;

public class UserClickedTextImportOptions implements Condition {
    public UserClickedTextImportOptions() {
        ShowTextImportOptionsHandler.didUserClick = false;
    }
    public boolean isMet() {
        return ShowTextImportOptionsHandler.didUserClick;
    }
}
