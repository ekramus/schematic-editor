package cz.cvut.fel.schematicEditor.graphNode.xstreamConverter;

import java.util.LinkedList;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.SerializableConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.DefaultMapper;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;

/**
 * This class implements converter for <code>GroupNode</code>. Conversion is conditional based on enabled status of
 * node.
 *
 * @author Urban Kravjansky
 */
public class GroupNodeLinkedListConditionalCoverter implements Converter {

    /**
     * @see com.thoughtworks.xstream.converters.Converter#marshal(java.lang.Object,
     *      com.thoughtworks.xstream.io.HierarchicalStreamWriter,
     *      com.thoughtworks.xstream.converters.MarshallingContext)
     */
    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        LinkedList<GroupNode> ll = (LinkedList<GroupNode>) value;

        for (GroupNode groupNode : ll) {
            if (!groupNode.isDisabled()) {
                context.convertAnother(groupNode);
            }
        }
    }

    /**
     * @see com.thoughtworks.xstream.converters.Converter#unmarshal(com.thoughtworks.xstream.io.HierarchicalStreamReader,
     *      com.thoughtworks.xstream.converters.UnmarshallingContext)
     */
    public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.thoughtworks.xstream.converters.ConverterMatcher#canConvert(java.lang.Class)
     */
    public boolean canConvert(Class arg0) {
        // can convert only LinkedList of GroupNodes
        LinkedList<GroupNode> ll = new LinkedList<GroupNode>();
        return arg0.equals(ll.getClass());
    }
}
