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

public class Cost_code_info_v1_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable {
    private static final QName ns1_id_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "id");
    private static final QName ns2_unsignedInt_TYPE_QNAME = SchemaConstants.QNAME_TYPE_UNSIGNED_INT;
    private CombinedSerializer myns2_unsignedInt__long_UnsignedInt_Serializer;
    private static final QName ns1_name_QNAME = new QName("http://stamps.com/xml/namespace/2014/07/swsim/swsimv37", "name");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer myns2_string__java_lang_String_String_Serializer;
    
    public Cost_code_info_v1_LiteralSerializer(QName type) {
        this(type,  false);
    }
    
    public Cost_code_info_v1_LiteralSerializer(QName type, boolean encodeType) {
        super(type, true, encodeType);
        setSOAPVersion(SOAPVersion.SOAP_11);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        myns2_unsignedInt__long_UnsignedInt_Serializer = (CombinedSerializer)registry.getSerializer("", long.class, ns2_unsignedInt_TYPE_QNAME);
        myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
    }
    
    public java.lang.Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.Cost_code_info_v1 instance = new com.aasc.erp.carrier.stampsws.proxy.Cost_code_info_v1();
        java.lang.Object member=null;
        QName elementName;
        List values;
        java.lang.Object value;
        
        reader.nextElementContent();
        java.util.HashSet requiredElements = new java.util.HashSet();
        requiredElements.add("id");
        requiredElements.add("name");
        for (int memberIndex = 0; memberIndex <2; memberIndex++) {
            elementName = reader.getName();
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_id_QNAME))) {
                myns2_unsignedInt__long_UnsignedInt_Serializer.setNullable( false );
                member = myns2_unsignedInt__long_UnsignedInt_Serializer.deserialize(ns1_id_QNAME, reader, context);
                requiredElements.remove("id");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setId(((Long)member).longValue());
                context.setXmlFragmentWrapperName( null );
                reader.nextElementContent();
            }
            if ((reader.getState() == XMLReader.START) && (matchQName(elementName, ns1_name_QNAME))) {
                myns2_string__java_lang_String_String_Serializer.setNullable( false );
                member = myns2_string__java_lang_String_String_Serializer.deserialize(ns1_name_QNAME, reader, context);
                requiredElements.remove("name");
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull",DeserializationException.FAULT_CODE_CLIENT);
                }
                instance.setName((java.lang.String)member);
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
        com.aasc.erp.carrier.stampsws.proxy.Cost_code_info_v1 instance = (com.aasc.erp.carrier.stampsws.proxy.Cost_code_info_v1)obj;
        
    }
    public void doSerializeAnyAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.Cost_code_info_v1 instance = (com.aasc.erp.carrier.stampsws.proxy.Cost_code_info_v1)obj;
        
    }
    public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.aasc.erp.carrier.stampsws.proxy.Cost_code_info_v1 instance = (com.aasc.erp.carrier.stampsws.proxy.Cost_code_info_v1)obj;
        
        myns2_unsignedInt__long_UnsignedInt_Serializer.setNullable( false );
        myns2_unsignedInt__long_UnsignedInt_Serializer.serialize(new Long(instance.getId()), ns1_id_QNAME, null, writer, context);
        myns2_string__java_lang_String_String_Serializer.setNullable( false );
        myns2_string__java_lang_String_String_Serializer.serialize(instance.getName(), ns1_name_QNAME, null, writer, context);
    }
}