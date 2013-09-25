package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.net.URL;
import static org.cytoscape.work.ServiceProperties.*;

public class ImportNetworkFromFileBtn extends JButton {
    public ImportNetworkFromFileBtn() {
        super();

        final String iconUrl = (String) Services.loadNetTFRef.getProperty(LARGE_ICON_URL);
        try {
            final ImageIcon icon = new ImageIcon(new URL(iconUrl));
            super.setIcon(icon);
        } catch (Exception e) {}

        super.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Services.taskMgr.execute(Services.loadNetTF.createTaskIterator());
            }
        });
    }
}
