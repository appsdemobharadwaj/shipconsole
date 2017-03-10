/*
 * AascXmlParser.java
 * Created on January 4, 2005, 2:37 PM
 */
package com.aasc.erp.carrier;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Static methods to parse xml responses returned by server.
 * @author  ajay
 */
public class

AascXmlParser {

    /** Creates a new instance of AascXmlParser.*/
    public AascXmlParser() {
    }

    /** gets the text value of an Element node with the given name (tag name).
     * @param root Element
     * @param tagName String 
     * @return tagValue String 
     */
    public static String getValue(Element root, String tagName) {
        Node textNode = null;
        NodeList nl = root.getElementsByTagName(tagName);
        String tagValue = "";

        if (nl != null) {
            if (nl.getLength() != 0) {
                // Node parentTag = nl.item(nl.getLength() - 1);           
                Node parentTag = nl.item(0);

                if (parentTag != null) {
                    if (parentTag.hasChildNodes()) {
                        textNode = parentTag.getFirstChild();
                    }
                }
            } else {
                tagValue = "";
            }
        }
        if (textNode != null) {
            tagValue = textNode.getNodeValue().trim();
        } else {
            tagValue = "";
        }
        return tagValue;
    }
}
