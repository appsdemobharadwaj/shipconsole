// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (10.1.3.4.0, build 080709.0800.28953)

package com.aasc.erp.carrier.stampsws.proxy.runtime;

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

public class ArrayOfInt_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_int_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "int");
    private static final QName ns2_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer myns2__int__int_Int_Serializer;
    
    public ArrayOfInt_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public ArrayOfInt_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer("", int.class, ns2_int_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.ArrayOfInt instance = new com.aasc.erp.carrier.stampsws.proxy.ArrayOfInt();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        elementName = reader.getName();
        if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_int_QNAME))) {
            values = new ArrayList();
            for(;;) {
                elementName = reader.getName();
                if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_int_QNAME))) {
                    myns2__int__int_Int_Serializer.setNullable( false );
                    value = myns2__int__int_Int_Serializer.deserialize(ns1_int_QNAME, reader, context);
                    requiredElements.remove("int");
                    if (value == null) {
                        throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                    }
                    values.add(value);
                    reader.nextElementContent();
                } else {
                    break;
                }
            }
            member = new int[values.size()];
            for (int i = 0; i < values.size(); ++i) {
                ((int[]) member)[i] = ((Integer)(values.get(i))).intValue();
            }
            instance.set_int((int[])member);
        }
        else {
            if (instance.get_int() == null)
            instance.set_int(new int[0]);
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
        com.aasc.erp.carrier.stampsws.proxy.ArrayOfInt instance = (com.aasc.erp.carrier.stampsws.proxy.ArrayOfInt)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.ArrayOfInt instance = (com.aasc.erp.carrier.stampsws.proxy.ArrayOfInt)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.ArrayOfInt instance = (com.aasc.erp.carrier.stampsws.proxy.ArrayOfInt)obj;
        
        if (instance.get_int() != null) {
            for (int i = 0; i < instance.get_int().length; ++i) {
                myns2__int__int_Int_Serializer.setNullable( false );
                myns2__int__int_Int_Serializer.serialize(new Integer(instance.get_int()[i]), ns1_int_QNAME, null, writer, context);
            }
        }
    }
}
