package cz.cvut.fel.schematicEditor.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;

public class NetListExport implements Export {

    private final boolean monochromaticColor = false;

    PrintStream           out;
    File                  file;

    public void export(SceneGraph sg, Object output) {

        this.file = (File) output;
        FileOutputStream fos = null;
        TransformationNode tn = null;
        ParameterNode pn = null;
        ElementNode en = null;

        try {
            fos = new FileOutputStream(this.file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.out = new PrintStream(fos);

        for (Node node : sg) {
            if (node instanceof TransformationNode) {
                tn = (TransformationNode) node;
            } else if (node instanceof ParameterNode) {
                pn = (ParameterNode) node;
            } else if (node instanceof PartNode) {
                PartNode partNode = (PartNode) node;
                drawNode(partNode, pn, tn);
            }
        }

        this.out.close();
    }

    private void drawNode(PartNode en, ParameterNode pn, TransformationNode tn) {

        switch (en.getElement().getElementType()) {

            case T_PART:
                Part part = (Part) en.getElement();
                this.out.println(part.getPartProperties().getNetList());
                break;

            default:
                break;
        }

    }

}
