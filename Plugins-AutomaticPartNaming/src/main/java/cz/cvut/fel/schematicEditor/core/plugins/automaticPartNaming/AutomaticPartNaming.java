package cz.cvut.fel.schematicEditor.core.plugins.automaticPartNaming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import cz.cvut.fel.schematicEditor.core.Plugin;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateEvent;
import cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateListener;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.parts.PartProperties;

/**
 * This plugin provides automatic naming of SceneGraph PINs.
 *
 * @author Urban Kravjansky
 */
public class AutomaticPartNaming implements Plugin, SceneGraphUpdateListener {
    private static final String PART_NAME_SET       = "part_name_set";
    private static final String PART_MAX_NUMBER_MAP = "part_max_number_map";

    /**
     * {@link SceneGraph} instance.
     */
    SceneGraph                  sceneGraph;

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#activate(SceneGraph)
     */
    public boolean activate(SceneGraph sg) {
        setSceneGraph(sg);
        return true;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#getDrawingButton()
     */
    public JButton getDrawingButton() {
        return null;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#implementsSceneGraphUpdateListener()
     */
    public boolean implementsSceneGraphUpdateListener() {
        return true;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#providesDrawingButton()
     */
    public boolean providesDrawingButton() {
        return false;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#providesMenuItem()
     */
    public boolean providesMenuItem() {
        return false;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateListener#sceneGraphUpdateOccured(cz
     *      .cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateEvent)
     */
    public void sceneGraphUpdateOccured(SceneGraphUpdateEvent e) {
        // TODO somehow store pluginPreferences with topNode
        HashMap<String, Object> pluginPreferences = getSceneGraph().getPluginData().getData(getIdentificator());
        HashSet<String> partNameSet;
        HashMap<String, Integer> partMaxNumberMap;

        // if plugin preferences are not yet initialized, initialize them
        if (pluginPreferences == null) {
            pluginPreferences = new HashMap<String, Object>();
            partNameSet = new HashSet<String>();
            partMaxNumberMap = new HashMap<String, Integer>();
        } else {
            partNameSet = (HashSet<String>) pluginPreferences.get(PART_NAME_SET);
            partMaxNumberMap = (HashMap<String, Integer>) pluginPreferences.get(PART_MAX_NUMBER_MAP);
        }

        ArrayList<Node> nal = getSceneGraph().getSceneGraphArray();
        for (Node node : nal) {
            if (node instanceof PartNode) {
                // do automatic part naming
                PartProperties pp = ((Part) ((PartNode) node).getElement()).getPartProperties();
                // get max i for given part variant
                int i = 0;
                try {
                    i = partMaxNumberMap.get(pp.getPartType().getVariant());
                } catch (NullPointerException npe) {
                    // nothing to do
                }

                // retrieve part property
                String name = pp.getProperty("name");

                // name part
                if (name.equals("")) {
                    // check, whether proposed name already exist in set
                    String pn = pp.getPartType().getVariant() + i;
                    while (partNameSet.contains(pn)) {
                        pn = pp.getPartType().getVariant() + ++i;
                    }
                    pp.setProperty("name", pn);
                    partMaxNumberMap.put(pp.getPartType().getVariant(), i);
                    partNameSet.add(pn);
                }

                // do automatic part pin naming
                ArrayList<String> pinValues = pp.getPartPinValues();
                for (int j = 0; j < pinValues.size(); j++) {
                    String pinValue = pinValues.get(j);
                    if (pinValue.equals("")) {
                        pinValues.set(j, "part_" + i + "_" + j);
                    }
                }
                pp.setPartPinValues(pinValues);
            }
        }

        // add modified preferences and store them
        pluginPreferences.put(PART_MAX_NUMBER_MAP, partMaxNumberMap);
        pluginPreferences.put(PART_NAME_SET, partNameSet);
        getSceneGraph().getPluginData().setData(getIdentificator(), pluginPreferences);
    }

    /**
     * @param sceneGraph the sceneGraph to set
     */
    private void setSceneGraph(SceneGraph sceneGraph) {
        this.sceneGraph = sceneGraph;
    }

    /**
     * @return the sceneGraph
     */
    private SceneGraph getSceneGraph() {
        return this.sceneGraph;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#getMenuItem()
     */
    public JMenuItem getMenuItem() {
        return null;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.core.Plugin#getIdentificator()
     */
    public String getIdentificator() {
        return "AutomaticPartNaming";
    }
}
