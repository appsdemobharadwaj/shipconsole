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

public class FP_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_FP1_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "FP1");
    private static final QName ns2_base64Binary_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BASE64_BINARY;
    private CombinedSerializer myns2_base64Binary__byte_Base64Binary_Serializer;
    private static final QName ns1_FP2_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "FP2");
    private static final QName ns1_FP3_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "FP3");
    private static final QName ns1_FP4_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "FP4");
    private static final QName ns1_FP5_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "FP5");
    
    public FP_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public FP_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2_base64Binary__byte_Base64Binary_Serializer = (CombinedSerializer)registry.getSerializer("", byte[].class, ns2_base64Binary_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.FP instance = new com.aasc.erp.carrier.stampsws.proxy.FP();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        for (int memberIndex = 0; memberIndex <5; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_FP1_QNAME))) {
                myns2_base64Binary__byte_Base64Binary_Serializer.setNullable( false );
                member = myns2_base64Binary__byte_Base64Binary_Serializer.deserialize(ns1_FP1_QNAME, reader, context);
                requiredElements.remove("FP1");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setFP1((byte[])member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_FP2_QNAME))) {
                myns2_base64Binary__byte_Base64Binary_Serializer.setNullable( false );
                member = myns2_base64Binary__byte_Base64Binary_Serializer.deserialize(ns1_FP2_QNAME, reader, context);
                requiredElements.remove("FP2");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setFP2((byte[])member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_FP3_QNAME))) {
                myns2_base64Binary__byte_Base64Binary_Serializer.setNullable( false );
                member = myns2_base64Binary__byte_Base64Binary_Serializer.deserialize(ns1_FP3_QNAME, reader, context);
                requiredElements.remove("FP3");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setFP3((byte[])member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_FP4_QNAME))) {
                myns2_base64Binary__byte_Base64Binary_Serializer.setNullable( false );
                member = myns2_base64Binary__byte_Base64Binary_Serializer.deserialize(ns1_FP4_QNAME, reader, context);
                requiredElements.remove("FP4");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setFP4((byte[])member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_FP5_QNAME))) {
                myns2_base64Binary__byte_Base64Binary_Serializer.setNullable( false );
                member = myns2_base64Binary__byte_Base64Binary_Serializer.deserialize(ns1_FP5_QNAME, reader, context);
                requiredElements.remove("FP5");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setFP5((byte[])member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
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
        com.aasc.erp.carrier.stampsws.proxy.FP instance = (com.aasc.erp.carrier.stampsws.proxy.FP)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.FP instance = (com.aasc.erp.carrier.stampsws.proxy.FP)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.FP instance = (com.aasc.erp.carrier.stampsws.proxy.FP)obj;
        
        if (instance.getFP1() != null) {
            myns2_base64Binary__byte_Base64Binary_Serializer.setNullable( false );
            myns2_base64Binary__byte_Base64Binary_Serializer.serialize(instance.getFP1(), ns1_FP1_QNAME, null, writer, context);
        }
        if (instance.getFP2() != null) {
            myns2_base64Binary__byte_Base64Binary_Serializer.setNullable( false );
            myns2_base64Binary__byte_Base64Binary_Serializer.serialize(instance.getFP2(), ns1_FP2_QNAME, null, writer, context);
        }
        if (instance.getFP3() != null) {
            myns2_base64Binary__byte_Base64Binary_Serializer.setNullable( false );
            myns2_base64Binary__byte_Base64Binary_Serializer.serialize(instance.getFP3(), ns1_FP3_QNAME, null, writer, context);
        }
        if (instance.getFP4() != null) {
            myns2_base64Binary__byte_Base64Binary_Serializer.setNullable( false );
            myns2_base64Binary__byte_Base64Binary_Serializer.serialize(instance.getFP4(), ns1_FP4_QNAME, null, writer, context);
        }
        if (instance.getFP5() != null) {
            myns2_base64Binary__byte_Base64Binary_Serializer.setNullable( false );
            myns2_base64Binary__byte_Base64Binary_Serializer.serialize(instance.getFP5(), ns1_FP5_QNAME, null, writer, context);
        }
    }
}
