package cz.cvut.fel.schematicEditor.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.ElementPotential;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.imperial.Mil;

/**
 * This class implements methods for serialization.
 *
 * @author Urban Kravjansky
 *
 */
public class Serialization {
    /**
     * Serializes given {@link PartNode} into given file.
     *
     * @param partNode {@link PartNode} file to serialize.
     * @param file Path to file, where should be {@link PartNode} serialized.
     */
    public static void serialize(PartNode partNode, File file) {
        XStream xstream = new XStream(new DomDriver());

        try {
            // ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file));
            // processAnnotations(xstream);
            // xstream.toXML(partNode, zos);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            processAnnotations(xstream);
            xstream.toXML(partNode, bw);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Serializes given {@link GroupNode} into given file.
     *
     * @param sceneGraph scene graph's {@link GroupNode} to serialize.
     * @param file Path to file, where should be {@link GroupNode} serialized.
     */
    public static void serialize(SceneGraph sceneGraph, File file) {
        XStream xstream = new XStream(new DomDriver());

        try {
            // ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file));
            // processAnnotations(xstream);
            // xstream.toXML(sceneGraph.getTopNode(), zos);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            processAnnotations(xstream);
            xstream.toXML(sceneGraph.getTopNode(), bw);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Serializes given {@link SceneGraph}'s {@link GroupNode} and return it as a String.
     *
     * @param sceneGraph scene graph to serialize.
     * @return Serialized group node.
     */
    public static String serialize(SceneGraph sceneGraph) {
        XStream xstream = new XStream(new DomDriver());
        String result;

        processAnnotations(xstream);
        result = xstream.toXML(sceneGraph.getTopNode());

        return result;
    }

    /**
     * Deserializes {@link Node} from given file.
     *
     * @param clazz Class of deserialized {@link Node}.
     * @param file Path to file, where is serialized {@link Node}.
     * @return Deserialized {@link Node} class.
     */
    public static Node deserialize(Class<?> clazz, File file) {
        XStream xstream = new XStream(new DomDriver());

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            processAnnotations(xstream);
            return (Node) xstream.fromXML(br);
        } catch (IOException e) {
            return null;
        }
    }

    /** Function may load part from HTML file     * 
     * @param clazz
     * @param address - URL of file with part
     * @return
     */
    public static Node deserializeURL(Class<?> clazz, String address) {
        XStream xstream = new XStream(new DomDriver());
        InputStreamReader content = null;

        URL url = null;
		try{
            url=new URL(address);
            }catch(MalformedURLException me){
           	 Logger.getRootLogger().error("URL for web saving is wrong formatted");
            }
            catch(Exception unknown ){
         		Logger.getRootLogger().error("Unknown error:" + unknown.toString()); }
            try{
            HttpURLConnection  connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //connection.connect();
             content =  new InputStreamReader(connection.getInputStream());
            
           
            
            }
            	catch(IOException ie){
            		Logger.getRootLogger().error("Impossible to save to web, because " + ie.toString());}
            	catch(Exception unknown ){
            		Logger.getRootLogger().error("Unknown error:" + unknown.toString()); }
           
       
        
        
        
        try {
            processAnnotations(xstream);
            return (Node) xstream.fromXML(content);
        } catch (Exception e) {
            return null;
        }
    }
    
    
    /**
     * Deserializes {@link GroupNode} from given file.
     *
     * @param clazz Class of deserialized {@link GroupNode}.
     * @param session Serialized session in form of {@link String}.
     * @return Deserialized {@link GroupNode} class.
     */
    public static GroupNode deserialize(Class<? extends GroupNode> clazz, String session) {
        XStream xstream = new XStream(new DomDriver());

        processAnnotations(xstream);
        return (GroupNode) xstream.fromXML(session);
    }

    /**
     * Processes all {@link XStream} annotations in entered classes.
     *
     * @param xstream {@link XStream} instance to configure.
     */
    private static void processAnnotations(XStream xstream) {
        xstream.processAnnotations(NodeFactory.getInstance().getClassArray());
        xstream.processAnnotations(Pixel.class);
        xstream.processAnnotations(Mil.class);
    }
}
