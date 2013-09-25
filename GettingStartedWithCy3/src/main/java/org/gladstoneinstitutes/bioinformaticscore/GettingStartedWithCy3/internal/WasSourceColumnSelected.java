package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import com.agilent.AdaptiveJavaHelp.Condition;

public class WasSourceColumnSelected implements Condition {
    public WasSourceColumnSelected() {
        ImportTableDialogListener.sourceInteractionColumn = null;
    }
    public boolean isMet() {
        return ImportTableDialogListener.sourceInteractionColumn != null
            && !ImportTableDialogListener.sourceInteractionColumn.equals("Select Source node column...");
    }
}
