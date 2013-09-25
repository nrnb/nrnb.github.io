package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3;

import java.util.Properties;

import org.cytoscape.service.util.AbstractCyActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventHandler;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.framework.InvalidSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.cytoscape.work.ServiceProperties.*;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.events.CytoPanelComponentSelectedListener;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.swing.DialogTaskManager;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Task;
import org.cytoscape.work.TunableRecorder;
import org.cytoscape.work.TunableHandler;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.read.LoadNetworkFileTaskFactory;
import org.cytoscape.task.visualize.ApplyPreferredLayoutTaskFactory;

import org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal.MainWindow;
import org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal.Services;
import org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal.ShowTextImportOptionsHandler;
import org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal.ImportTableDialogListener;
import org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal.LoadNetworkFileTaskListener;
import org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal.ApplyPreferredLayoutTaskListener;
import org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal.CytoPanelListener;

public class CyActivator extends AbstractCyActivator
{
    static final Logger logger = LoggerFactory.getLogger(CyActivator.class);

	public CyActivator()
    {
		super();
	}

    static Properties ezProps(String... args) {
        final Properties props = new Properties();
        for (int i = 0; i < args.length; i += 2)
            props.setProperty(args[i], args[i + 1]);
        return props;
    }

	public void start(BundleContext bc)
    {
		registerService(bc, new ShowTextImportOptionsHandler(), EventHandler.class, ezProps(
            EventConstants.EVENT_TOPIC, "org/cytoscape/gettingstarted"
        ));
		registerService(bc, new ImportTableDialogListener(), EventHandler.class, ezProps(
            EventConstants.EVENT_TOPIC, "org/cytoscape/gettingstarted"
        ));
		registerService(bc, new LoadNetworkFileTaskListener(), EventHandler.class, ezProps(
            EventConstants.EVENT_TOPIC, "org/cytoscape/gettingstarted"
        ));
		registerService(bc, new ApplyPreferredLayoutTaskListener(), EventHandler.class, ezProps(
            EventConstants.EVENT_TOPIC, "org/cytoscape/gettingstarted"
        ));
        registerService(bc, new TunableRecorder<TunableHandler>() {
            public void recordTunableState(Object o) {
                logger.info(o.getClass().getName());
            }
        }, TunableRecorder.class, ezProps());

        registerService(bc, new CytoPanelListener(), CytoPanelComponentSelectedListener.class, ezProps());

		registerService(bc, new EventHandler() {
                public void handleEvent(Event e) {
                    StringBuffer buffer = new StringBuffer();
                    for (String name : e.getPropertyNames()) {
                        buffer.append(String.format("%s: %s<br>", name, e.getProperty(name)));
                    }
                    logger.info(buffer.toString());
                }
            }, EventHandler.class, ezProps(
            EventConstants.EVENT_TOPIC, "org/cytoscape/gettingstarted"
        ));


        final CySwingApplication swingApp = getService(bc, CySwingApplication.class);
        Services.appMgr = getService(bc, CyApplicationManager.class);
        final MainWindow mainWindow = new MainWindow(swingApp);
        Services.taskMgr = getService(bc, DialogTaskManager.class);
        Services.loadNetTFRef = bc.getServiceReference(LoadNetworkFileTaskFactory.class.getName());
        Services.loadNetTF = (LoadNetworkFileTaskFactory) bc.getService(Services.loadNetTFRef);
        Services.preferredLayoutTFRef = bc.getServiceReference(ApplyPreferredLayoutTaskFactory.class.getName());
        Services.preferredLayoutTF = (ApplyPreferredLayoutTaskFactory) bc.getService(Services.preferredLayoutTFRef);
        Services.zoomInTFRef = getNetworkViewTFServiceRef(bc, "zoom-in");
        Services.zoomInTF = (NetworkViewTaskFactory) bc.getService(Services.zoomInTFRef);
        Services.zoomOutTFRef = getNetworkViewTFServiceRef(bc, "zoom-out");
        Services.zoomOutTF = (NetworkViewTaskFactory) bc.getService(Services.zoomOutTFRef);
        Services.zoomFitSelectedTFRef = getNetworkViewTFServiceRef(bc, "fit-selected");
        Services.zoomFitSelectedTF = (NetworkViewTaskFactory) bc.getService(Services.zoomFitSelectedTFRef);
        Services.zoomFitAllTFRef = getNetworkViewTFServiceRef(bc, "fit-content");
        Services.zoomFitAllTF = (NetworkViewTaskFactory) bc.getService(Services.zoomFitAllTFRef);

        registerService(bc, new AbstractTaskFactory() {
			public TaskIterator createTaskIterator() {
				return new TaskIterator(new Task() {
					public void cancel() {}
					public void run(TaskMonitor monitor) {
                        mainWindow.open();
					}
				});
			}
		}, TaskFactory.class, ezProps(
			PREFERRED_MENU, "Apps",
			TITLE, "Get Started with Cytoscape 3.0...", MENU_GRAVITY, "10.0")
		);
    }

    private static ServiceReference getNetworkViewTFServiceRef(final BundleContext bc, final String command) {
        final String filter = String.format("(&(%s=%s)(%s=%s))", COMMAND, command, COMMAND_NAMESPACE, "network-view");
        try {
            ServiceReference[] refs = bc.getServiceReferences(NetworkViewTaskFactory.class.getName(), filter);
            if (refs.length == 0) {
                logger.error("Failed to get service: " + filter);
                return null;
            }
            return refs[0];
         } catch (InvalidSyntaxException e) {
             logger.error("Failed to get service: " + e.getMessage());
             return null;
         }
    }
}
