package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import com.agilent.AdaptiveJavaHelp.Condition;

public class WasTargetColumnSelected implements Condition {
    public WasTargetColumnSelected() {
        ImportTableDialogListener.targetInteractionColumn = null;
    }
    public boolean isMet() {
        return ImportTableDialogListener.targetInteractionColumn != null
            && !ImportTableDialogListener.targetInteractionColumn.equals("Select Target node column...");
    }
}
