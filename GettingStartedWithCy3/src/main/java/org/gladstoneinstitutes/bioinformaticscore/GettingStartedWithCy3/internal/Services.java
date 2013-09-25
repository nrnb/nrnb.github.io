package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.TaskManager;
import org.osgi.framework.ServiceReference;
import org.cytoscape.task.read.LoadNetworkFileTaskFactory;
import org.cytoscape.task.visualize.ApplyPreferredLayoutTaskFactory;
import org.cytoscape.task.NetworkViewTaskFactory;

public class Services {
    public static CyApplicationManager appMgr = null;
    public static TaskManager taskMgr = null;
    public static ServiceReference loadNetTFRef = null;
    public static LoadNetworkFileTaskFactory loadNetTF = null;
    public static ServiceReference preferredLayoutTFRef = null;
    public static ApplyPreferredLayoutTaskFactory preferredLayoutTF = null;
    public static ServiceReference zoomInTFRef = null;
    public static NetworkViewTaskFactory zoomInTF = null;
    public static ServiceReference zoomOutTFRef = null;
    public static NetworkViewTaskFactory zoomOutTF = null;
    public static ServiceReference zoomFitSelectedTFRef = null;
    public static NetworkViewTaskFactory zoomFitSelectedTF = null;
    public static ServiceReference zoomFitAllTFRef = null;
    public static NetworkViewTaskFactory zoomFitAllTF = null;
}
