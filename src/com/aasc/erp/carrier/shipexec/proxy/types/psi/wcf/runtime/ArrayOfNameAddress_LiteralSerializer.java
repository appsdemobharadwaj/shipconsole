// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.runtime;

import oracle.j2ee.ws.common.encoding.*;
import oracle.j2ee.ws.common.encoding.literal.*;
import oracle.j2ee.ws.common.encoding.literal.DetailFragmentDeserializer;
import oracle.j2ee.ws.common.encoding.simpletype.*;
import oracle.j2ee.ws.common.soap.SOAPEncodingConstants;
import oracle.j2ee.ws.common.soap.SOAPEnvelopeConstants;
import oracle.j2ee.ws.common.soap.SOAPVersion;
import oracle.j2ee.ws.common.streaming.*;
import oracle.j2ee.ws.common.wsdl.document.schema.SchemaConstants;
import oracle.j2ee.ws.common.util.xml.UUID;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.AttachmentPart;

public class ArrayOfNameAddress_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_NameAddress_QNAME = new QName("http://PSI.Wcf", "NameAddress");
    private static final QName ns1_NameAddress_TYPE_QNAME = new QName("http://PSI.Wcf", "NameAddress");
    private CombinedSerializer myns1_NameAddress__NameAddress_InterfaceSOAPSerializer;
    
    public ArrayOfNameAddress_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public ArrayOfNameAddress_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns1_NameAddress__NameAddress_InterfaceSOAPSerializer = (CombinedSerializer)registry.getSerializer("", com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.NameAddress.class, ns1_NameAddress_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfNameAddress instance = new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfNameAddress();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        elementName = reader.getName();
        if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_NameAddress_QNAME))) {
            values = new ArrayList();
            for(;;) {
                elementName = reader.getName();
                if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_NameAddress_QNAME))) {
                    myns1_NameAddress__NameAddress_InterfaceSOAPSerializer.setNullable( true );
                    value = myns1_NameAddress__NameAddress_InterfaceSOAPSerializer.deserialize(ns1_NameAddress_QNAME, reader, context);
                    requiredElements.remove("NameAddress");
                    values.add(value);
                    reader.nextElementContent();
                } else {
                    break;
                }
            }
            member = new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.NameAddress[values.size()];
            member = values.toArray((java.lang.Object[]) member);
            instance.setNameAddress((com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.NameAddress[])member);
        }
        else {
            if (instance.getNameAddress() == null)
            instance.setNameAddress(new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.NameAddress[0]);
        }
        if (!requiredElements.isEmpty()) {
            throw new DeserializationException( "literal.expectedElementName" , requiredElements.iterator().next().toString(), DeserializationException.FAULT_CODE_CLIENT );
        }
        
        if( reader.getState() != XMLReader.END)
        {
            reader.skipElement();
        }
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (java.lang.Object)instance;
    }
    
    public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfNameAddress instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfNameAddress)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfNameAddress instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfNameAddress)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfNameAddress instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.ArrayOfNameAddress)obj;
        
        if (instance.getNameAddress() != null) {
            for (int i = 0; i < instance.getNameAddress().length; ++i) {
                myns1_NameAddress__NameAddress_InterfaceSOAPSerializer.setNullable( true );
                myns1_NameAddress__NameAddress_InterfaceSOAPSerializer.serialize(instance.getNameAddress()[i], ns1_NameAddress_QNAME, null, writer, context);
            }
        }
    }
}
