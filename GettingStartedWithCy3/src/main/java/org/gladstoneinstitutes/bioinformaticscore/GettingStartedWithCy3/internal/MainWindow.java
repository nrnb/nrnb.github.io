package org.gladstoneinstitutes.bioinformaticscore.GettingStartedWithCy3.internal;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.help.*;
import java.net.URL;
import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.*;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.java.help.impl.ViewAwareComponent;

import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTableUtil;

import com.agilent.AdaptiveJavaHelp.DynamicTextMonitors;
import com.agilent.AdaptiveJavaHelp.DynamicText;

public class MainWindow {
    static final Logger logger = LoggerFactory.getLogger(MainWindow.class);
    static final String PATH_TO_HELPSET = "/gettingstarted-helpset/gettingstarted.hs";
    static final String PATH_TO_TOC     = "/gettingstarted-helpset/toc.xml";

    private static HelpSet loadHelpSet(final String pathToHelpSet) {
        try {
            final URL hsURL = MainWindow.class.getResource(pathToHelpSet);
            return new HelpSet(null, hsURL);
        } catch (Exception e) {
            logger.error("Unable to read helpset: " + e.getMessage());
            return null;
        }
    }


    private static DefaultMutableTreeNode parseTOC(final String pathToTOC, final HelpSet hs) {
        try {
            final URL url = MainWindow.class.getResource(pathToTOC);
            return TOCView.parse(url, hs, Locale.getDefault(), new TOCView.DefaultTOCFactory());
        } catch (Exception e) {
            logger.error("Unable to read TOC: " + e.getMessage());
            return null;
        }
    }

    JDialog dialog = null;
    boolean haveSetupAJH = false;

    public MainWindow(final CySwingApplication swingApp) {
        final HelpSet helpSet = loadHelpSet(PATH_TO_HELPSET);
        if (helpSet == null)
            return;
        final DefaultMutableTreeNode toc = parseTOC(PATH_TO_TOC, helpSet);
        if (toc == null)
            return;

        dialog = new JDialog(swingApp.getJFrame(), "Getting Started with Cytoscape 3.0");
        dialog.setPreferredSize(new Dimension(1000, 500));
        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        final JTree tocTree = setupTOCTree(toc);
        final JEditorPane editorPane = setupEditorPane();
        setupTOCSelection(tocTree, editorPane);
        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tocTree), new JScrollPane(editorPane));

        dialog.add(splitPane);
        dialog.pack();
    }

    private static Object attemptToGetClass(final String className) {
        try {
            Class c = Class.forName(className);
            Object o = c.newInstance();
            return o;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private JTree setupTOCTree(final DefaultMutableTreeNode toc) {
        final JTree tocTree = new JTree(toc) {
            public String convertValueToText(Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                if (value != null && value instanceof DefaultMutableTreeNode) {
                    final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    final Object obj = node.getUserObject();
                    if (obj != null && obj instanceof TreeItem) {
                        final TreeItem item = (TreeItem) obj;
                        return item.getName();
                    }
                }
                return super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
            }
        };
        tocTree.setRootVisible(false);
        tocTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        return tocTree;
    }

    private static class InternalObjectView extends ObjectView {
        public InternalObjectView(Element elem) {
            super(elem);
        }

        protected Component createComponent() {
            AttributeSet attrs = getElement().getAttributes();
            String classname = ((String) attrs.getAttribute(HTML.Attribute.CLASSID)).trim();
            try {
                Component comp = (Component) attemptToGetClass(classname);
                setParameters(comp, attrs);
                if (comp instanceof ViewAwareComponent) {
                    ViewAwareComponent vac = (ViewAwareComponent) comp;
                    vac.setViewData(this);
                }
                return comp;
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
            return getUnloadableRepresentation();
        }

        // Copied from javax.swing.text.html.ObjectView with modifications to how exceptions are reported

        Component getUnloadableRepresentation() {
            Component comp = new JLabel("??");
            comp.setForeground(Color.red);
            return comp;
        }
            
        private void setParameters(Component comp, AttributeSet attr) {
            Class k = comp.getClass();
            BeanInfo bi;
            try {
                bi = Introspector.getBeanInfo(k);
            } catch (IntrospectionException ex) {
                logger.warn("introspector failed, ex: "+ex);
                return;             // quit for now
            }
            PropertyDescriptor props[] = bi.getPropertyDescriptors();
            for (int i=0; i < props.length; i++) {
                //      System.err.println("checking on props[i]: "+props[i].getName());
                Object v = attr.getAttribute(props[i].getName());
                if (v instanceof String) {
                    // found a property parameter
                    String value = (String) v;
                    Method writer = props[i].getWriteMethod();
                    if (writer == null) {
                        // read-only property. ignore
                        return;     // for now
                    }
                    Class[] params = writer.getParameterTypes();
                    if (params.length != 1) {
                        // zero or more than one argument, ignore
                        return;     // for now
                    }
                    Object [] args = { value };
                    try {
                        writer.invoke(comp, args);
                    } catch (Exception ex) {
                        logger.warn("Invocation failed: " + ex.getMessage());
                        // invocation code
                    }
                }
            }
        }
    }

    private JEditorPane setupEditorPane() {
        final JEditorPane editorPane = new JEditorPane();
        editorPane.setEditorKitForContentType("text/html", new HTMLEditorKit() {
            public ViewFactory getViewFactory() {
                return new HTMLEditorKit.HTMLFactory() {
                    public View create(Element elem) {
                        if (elem.getName().equalsIgnoreCase("object"))
                            return new InternalObjectView(elem);
                        else
                            return super.create(elem);
                    }
                };
            }
        });
        editorPane.setEditable(false);
        return editorPane;
    }

    private void setupTOCSelection(final JTree tocTree, final JEditorPane editorPane) {
        tocTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                final DefaultMutableTreeNode node = (DefaultMutableTreeNode) tocTree.getLastSelectedPathComponent();
                if (node == null)
                    return;
                final TreeItem item = (TreeItem) node.getUserObject();
                try {
                    editorPane.setPage(item.getID().getURL());
                } catch (Exception ex) {}
            }
        });
    }

    public void open() {
        if (dialog == null)
            return;
        dialog.setVisible(true);
        setupAJH();
    }

    private void setupAJH() {
        if (haveSetupAJH)
            return;
        DynamicTextMonitors.start();
        DynamicTextMonitors.register("\\%current-network-name\\%", new DynamicText() {
            public String getText() {
                final CyNetwork net = Services.appMgr.getCurrentNetwork();
                if (net != null)
                    return net.getRow(net).get(CyNetwork.NAME, String.class);
                else
                    return "";
            }
        });
        DynamicTextMonitors.register("\\%current-source-column-name\\%", new DynamicText() {
            public String getText() {
                final String source = ImportTableDialogListener.sourceInteractionColumn;
                return (source == null ? "" : source);
            }
        });
        DynamicTextMonitors.register("\\%current-target-column-name\\%", new DynamicText() {
            public String getText() {
                final String target = ImportTableDialogListener.targetInteractionColumn;
                return (target == null ? "" : target);
            }
        });
        DynamicTextMonitors.register("\\%num-selected-nodes\\%", new DynamicText() {
            public String getText() {
                final CyNetwork net = Services.appMgr.getCurrentNetwork();
                if (net == null)
                    return "";
                return Integer.toString(CyTableUtil.getNodesInState(net, "selected", true).size());
            }
        });
        haveSetupAJH = true;
    }
}
