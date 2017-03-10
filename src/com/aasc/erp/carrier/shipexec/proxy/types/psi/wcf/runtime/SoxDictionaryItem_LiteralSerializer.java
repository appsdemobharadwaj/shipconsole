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

public class SoxDictionaryItem_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_key_QNAME = new QName("http://PSI.Wcf", "key");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    private static final QName ns1_value_QNAME = new QName("http://PSI.Wcf", "value");
    private static final QName ns2_anyType_TYPE_QNAME = SchemaConstants.QNAME_TYPE_URTYPE;
    private CombinedSerializer ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer;
    
    public SoxDictionaryItem_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public SoxDictionaryItem_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
        ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.Object.class, ns2_anyType_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem instance = new com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        for (int memberIndex = 0; memberIndex <2; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_key_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_key_QNAME, reader, context);
                requiredElements.remove("key");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setKey((java.lang.String)member);
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_value_QNAME))) {
                ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer.setNullable( false );
                member = ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer.deserialize(ns1_value_QNAME, reader, context);
                requiredElements.remove("value");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setValue((java.lang.Object)member);
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
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem instance = (com.aasc.erp.carrier.shipexec.proxy.types.psi.wcf.SoxDictionaryItem)obj;
        
        if (instance.getKey() != null) {
            myns2_string__java_lang_String_String_Serializer.setNullable( false );
            myns2_string__java_lang_String_String_Serializer.serialize(instance.getKey(), ns1_key_QNAME, null, writer, context);
        }
        if (instance.getValue() != null) {
            ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer.setNullable( false );
            ns2_anyType_TYPE_QNAMEjava$2e$lang$2e$Object_Serializer.serialize(instance.getValue(), ns1_value_QNAME, null, writer, context);
        }
    }
}
