package cz.cvut.fel.schematicEditor.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;

public class NetListExport implements Export {

    private final boolean monochromaticColor = false;

    PrintStream           out;
    File                  file;

    /**
     * @see Export#export(SceneGraph, double, Object)
     */
    public String export(SceneGraph sg) {
        String result = "";

        TransformationNode tn = null;
        ParameterNode pn = null;

        for (Node node : sg) {
            if (node instanceof TransformationNode) {
                tn = (TransformationNode) node;
            } else if (node instanceof ParameterNode) {
                pn = (ParameterNode) node;
            } else if (node instanceof PartNode) {
                PartNode partNode = (PartNode) node;
                result += drawNode(partNode, pn, tn) + "\n";
            }
        }

        return result;
    }

    /**
     * @see Export#export(SceneGraph, double, Object)
     */
    public void export(SceneGraph sg, double zoomFactor, Object output) {
        this.file = (File) output;
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(this.file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.out = new PrintStream(fos);
        this.out.println(export(sg));
        this.out.close();
    }

    private String drawNode(PartNode en, ParameterNode pn, TransformationNode tn) {
        String result = "";

        switch (en.getElement().getElementType()) {

            case T_PART:
                Part part = (Part) en.getElement();
                result = part.getPartProperties().getNetlist();
                break;

            default:
                break;
        }

        return result;
    }

}
