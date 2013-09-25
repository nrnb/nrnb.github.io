package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import com.agilent.AdaptiveJavaHelp.Condition;

public class WasImportTableDialogOpened implements Condition {
    public WasImportTableDialogOpened() {
        ImportTableDialogListener.dialogOpen = false;
    }
    public boolean isMet() {
        return ImportTableDialogListener.dialogOpen;
    }
}
