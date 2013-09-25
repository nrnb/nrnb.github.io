package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import com.agilent.AdaptiveJavaHelp.Condition;

public class WasImportTableDialogOked implements Condition {
    public WasImportTableDialogOked() {
        ImportTableDialogListener.dialogOk = false;
    }
    public boolean isMet() {
        return ImportTableDialogListener.dialogOk;
    }
}
